package com.wiatec.blive.service;

import com.wiatec.blive.common.http.HttpMaster;
import com.wiatec.blive.common.result.EnumResult;
import com.wiatec.blive.common.result.ResultInfo;
import com.wiatec.blive.common.result.ResultMaster;
import com.wiatec.blive.common.result.XException;
import com.wiatec.blive.common.utils.TextUtil;
import com.wiatec.blive.common.utils.TimeUtil;
import com.wiatec.blive.dto.CoinBillChartDaysInfo;
import com.wiatec.blive.dto.CoinBillDaysInfo;
import com.wiatec.blive.dto.CoinBillChartMonthlyInfo;
import com.wiatec.blive.dto.YearMonthInfo;
import com.wiatec.blive.orm.dao.AuthRegisterUserDao;
import com.wiatec.blive.orm.dao.CoinBillDao;
import com.wiatec.blive.orm.dao.CoinDao;
import com.wiatec.blive.orm.dao.CoinIAPDao;
import com.wiatec.blive.orm.pojo.AuthRegisterUserInfo;
import com.wiatec.blive.orm.pojo.CoinBillInfo;
import com.wiatec.blive.orm.pojo.CoinIAPInfo;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author patrick
 */
@Service
public class CoinService {

    private final Logger logger = LoggerFactory.getLogger(CoinService.class);

    private final String VERIFY_RECEIPT_URL_PRODUCT = "https://buy.itunes.apple.com/verifyReceipt";
    private final String VERIFY_RECEIPT_URL_SANDBOX = "https://sandbox.itunes.apple.com/verifyReceipt";

    private final int CONSUME_ACTION_SUB = 0;
    private final int CONSUME_ACTION_PLUS = 1;

    private final int PRICE_PRO_MONTH = 688;

    @Resource
    private CoinDao coinDao;
    @Resource
    private CoinIAPDao coinIAPDao;
    @Resource
    private CoinBillDao coinBillDao;
    @Resource
    private AuthRegisterUserDao authRegisterUserDao;

    /**
     * get all coins number by user id
     * @param userId user id
     * @return ResultInfo
     */
    public ResultInfo getCoins(int userId){
        int coins = 0;
        if (coinDao.countOne(userId) == 1){
            coins = coinDao.countCoins(userId);
        }
        return ResultMaster.success(coins);
    }

    /**
     * user consume coins
     * @param userId consume coins user id
     * @param targetUserId get coins user id
     * @param consumeCoins coin numbers
     * @return ResultInfo
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultInfo consumeCoin(int userId, int targetUserId, int category, int consumeCoins,
                                  int level, int month, String platform, String description, String comment){
        //检查消费者账户金额是否足够
        int userCoins = coinDao.countCoins(userId);
        if(userCoins < consumeCoins){
            throw new XException("Coins not enough");
        }
        // 从消费者账户扣除金额
        if(coinDao.updateOne(userId, CONSUME_ACTION_SUB, consumeCoins) != 1){
            throw new XException(EnumResult.ERROR_INTERNAL_SERVER_SQL);
        }
        // 将金额增加到收入者的账户
        if(coinDao.countOne(targetUserId) == 1) {
            if (coinDao.updateOne(targetUserId, CONSUME_ACTION_PLUS, consumeCoins) != 1) {
                throw new XException(EnumResult.ERROR_INTERNAL_SERVER_SQL);
            }
        }else{
            if (coinDao.insertOne(targetUserId, consumeCoins) != 1) {
                throw new XException(EnumResult.ERROR_INTERNAL_SERVER_SQL);
            }
        }
        // 购买pro时修改用户等级到6
        if(category == CoinBillInfo.CATEGORY_CONSUME_PRO){
            AuthRegisterUserInfo userInfo = authRegisterUserDao.selectOneById(userId);
            if(userInfo == null){
                throw new XException(EnumResult.ERROR_INTERNAL_SERVER_SQL);
            }
            Date expiresDate = userInfo.getExpiresTime();
            if(expiresDate.after(new Date())){
                expiresDate = TimeUtil.getExpiresTime(expiresDate, month);
            }else{
                expiresDate = TimeUtil.getExpiresTime(new Date(), month);
            }
            if(authRegisterUserDao.updateLevelByUserId(userId, level, expiresDate) != 1){
                throw new XException(EnumResult.ERROR_INTERNAL_SERVER_SQL);
            }
        }

        //bill user consume info
        CoinBillInfo coinBillInfo = new CoinBillInfo();
        coinBillInfo.setUserId(userId);
        coinBillInfo.setRelationId(targetUserId);
        coinBillInfo.setType(CoinBillInfo.TYPE_CONSUME);
        coinBillInfo.setCategory(category);
        coinBillInfo.setCoins(consumeCoins);
        coinBillInfo.setPlatform(platform);
        coinBillInfo.setDescription(description);
        coinBillInfo.setComment(comment);
        logger.info(coinBillInfo.toString());
        coinBillDao.insertOne(coinBillInfo);

        //bill user add coin info
        CoinBillInfo coinBillInfo1 = new CoinBillInfo();
        coinBillInfo1.setUserId(targetUserId);
        coinBillInfo1.setRelationId(userId);
        coinBillInfo1.setType(CoinBillInfo.TYPE_INCOME);
        if(category == CoinBillInfo.CATEGORY_CONSUME_PRO){
            coinBillInfo1.setCategory(CoinBillInfo.CATEGORY_INCOME_PRO);
        }else if(category == CoinBillInfo.CATEGORY_CONSUME_VIEW){
            coinBillInfo1.setCategory(CoinBillInfo.CATEGORY_INCOME_VIEW);
        }else if(category == CoinBillInfo.CATEGORY_CONSUME_GIFT){
            coinBillInfo1.setCategory(CoinBillInfo.CATEGORY_INCOME_GIFT);
        }
        coinBillInfo1.setCoins(consumeCoins);
        coinBillInfo1.setPlatform(platform);
        coinBillInfo1.setDescription(description);
        coinBillInfo1.setComment(comment);
        logger.info(coinBillInfo1.toString());
        coinBillDao.insertOne(coinBillInfo1);

        return ResultMaster.success();
    }


    /**
     * verify IAP
     * @param userId user id
     * @param receiptData receiptData
     * @param platform platform
     * @param productIdentifier productIdentifier
     * @return ResultInfo
     */
    public ResultInfo iapVerify(int userId, String receiptData, String platform,
                                String productIdentifier){
        logger.info(receiptData);
        CoinIAPInfo coinIAPInfo = coinIAPDao.selectOneByIdentifier(productIdentifier);
        if(coinIAPInfo == null){
            throw new XException(EnumResult.ERROR_INTERNAL_SERVER_SQL);
        }
        //verify IAP result by receiptData with apple server
        Response response = HttpMaster.post(VERIFY_RECEIPT_URL_PRODUCT)
                .headers("Content-Type", "application/json")
                .jsonEncodeing(true)
                .param("receipt-data", receiptData)
                .execute();
        if(response == null){
            throw new XException("Apple server no response");
        }
        if(response.code() != EnumResult.SUCCESS.getCode()){
            throw new XException("Apple server response error");
        }
        try {
            String result = response.body().string();
            if(TextUtil.isEmpty(result)){
                throw new XException("Apple server response error");
            }
            logger.info(userId + " IAP purchase: " + coinIAPInfo.getName());
            logger.info(userId + " IAP verify result: " + result);
            JSONObject jsonResult = new JSONObject(result);
            int status = jsonResult.getInt("status");
            if(status != 0){
                if(status == 21007){
                    return iapVerifyInSandbox(userId, receiptData, platform, coinIAPInfo);
                }
                throw new XException(status, "purchase result verify failure");
            }
            JSONObject receipt = jsonResult.getJSONObject("receipt");
            JSONArray inApps = receipt.getJSONArray("in_app");
            JSONObject inApp = inApps.getJSONObject(0);
            String transactionId = inApp.getString("transaction_id");


            //insert or update user coin number
            if(coinDao.countOne(userId) == 1){
                coinDao.updateOne(userId, CONSUME_ACTION_PLUS, coinIAPInfo.getNumber());
            }else{
                coinDao.insertOne(userId, coinIAPInfo.getNumber());
            }


            //bill user add coin info
            CoinBillInfo coinBillInfo = new CoinBillInfo();
            coinBillInfo.setUserId(userId);
            coinBillInfo.setRelationId(0);
            coinBillInfo.setType(CoinBillInfo.TYPE_CHARGE);
            coinBillInfo.setCategory(CoinBillInfo.CATEGORY_CHARGE_IAP);
            coinBillInfo.setCoins(coinIAPInfo.getNumber());
            coinBillInfo.setAmount(coinIAPInfo.getAmount());
            coinBillInfo.setPlatform(platform);
            coinBillInfo.setTransactionId(transactionId);
            coinBillInfo.setDescription("charge by apple store: " + coinIAPInfo.getNumber());
            coinBillInfo.setComment(receiptData);
            logger.info(coinBillInfo.toString());
            coinBillDao.insertOne(coinBillInfo);

        } catch (IOException e) {
            throw new XException("Apple server response error");
        }
        return ResultMaster.success(coinDao.countCoins(userId));
    }


    private ResultInfo iapVerifyInSandbox(int userId, String receiptData, String platform,
                                                   CoinIAPInfo coinIAPInfo){
        Response response = HttpMaster.post(VERIFY_RECEIPT_URL_SANDBOX)
                .headers("Content-Type", "application/json")
                .jsonEncodeing(true)
                .param("receipt-data", receiptData)
                .execute();
        if(response == null){
            throw new XException("Apple server no response");
        }
        if(response.code() != EnumResult.SUCCESS.getCode()){
            throw new XException("Apple server response error");
        }
        try {
            String result = response.body().string();
            if(TextUtil.isEmpty(result)){
                throw new XException("Apple server response error");
            }
            logger.debug(userId + " IAP purchase: " + coinIAPInfo.getName());
            logger.debug(userId + " IAP verify result: " + result);
            JSONObject jsonResult = new JSONObject(result);
            int status = jsonResult.getInt("status");
            if(status != 0){
                throw new XException(status, "purchase result verify failure");
            }
            JSONObject receipt = jsonResult.getJSONObject("receipt");
            JSONArray inApps = receipt.getJSONArray("in_app");
            JSONObject inApp = inApps.getJSONObject(0);
            String transactionId = inApp.getString("transaction_id");

            //insert or update user coin number
            if(coinDao.countOne(userId) == 1){
                coinDao.updateOne(userId, CONSUME_ACTION_PLUS, coinIAPInfo.getNumber());
            }else{
                coinDao.insertOne(userId, coinIAPInfo.getNumber());
            }


            //bill user add coin info
            CoinBillInfo coinBillInfo = new CoinBillInfo();
            coinBillInfo.setUserId(userId);
            coinBillInfo.setRelationId(0);
            coinBillInfo.setType(CoinBillInfo.TYPE_CHARGE);
            coinBillInfo.setCategory(CoinBillInfo.CATEGORY_CHARGE_IAP);
            coinBillInfo.setCoins(coinIAPInfo.getNumber());
            coinBillInfo.setAmount(coinIAPInfo.getAmount());
            coinBillInfo.setPlatform(platform);
            coinBillInfo.setTransactionId(transactionId);
            coinBillInfo.setDescription("charge by sandbox apple store: " + coinIAPInfo.getNumber());
            coinBillInfo.setComment(receiptData);
            logger.info(coinBillInfo.toString());
            coinBillDao.insertOne(coinBillInfo);

        } catch (IOException e) {
            throw new XException("Apple server response error");
        }
        return ResultMaster.success(coinDao.countCoins(userId));
    }


    public ResultInfo getBills(int userId){
        List<CoinBillInfo> coinBillInfoList = coinBillDao.selectByUserId(userId);
        if(coinBillInfoList == null || coinBillInfoList.size() <= 0){
            throw new XException(EnumResult.ERROR_NO_FOUND);
        }
        return ResultMaster.success(coinBillInfoList);
    }






    public ResultInfo<CoinBillChartMonthlyInfo> chartMonth(int userId, int year, int month){
        YearMonthInfo yearMonthInfo = new YearMonthInfo(year, month);
        List<CoinBillInfo> coinBillInfoList = coinBillDao.selectMonthBillByUserId(userId,
                yearMonthInfo.getStart(),
                yearMonthInfo.getEnd());
        CoinBillChartMonthlyInfo coinBillChartMonthlyInfo = new CoinBillChartMonthlyInfo();
        int incomeCoins = 0;
        int consumeCoins = 0;
        int chargeCoins = 0;
        for (CoinBillInfo coinBillInfo: coinBillInfoList) {
            if(coinBillInfo.getType() == CoinBillInfo.TYPE_CHARGE){
                chargeCoins += coinBillInfo.getCoins();
            }else if(coinBillInfo.getType() == CoinBillInfo.TYPE_INCOME){
                incomeCoins += coinBillInfo.getCoins();
            }else if(coinBillInfo.getType() == CoinBillInfo.TYPE_CONSUME){
                consumeCoins += coinBillInfo.getCoins();
            }
        }
        coinBillChartMonthlyInfo.setChargeCoins(chargeCoins);
        coinBillChartMonthlyInfo.setConsumeCoins(consumeCoins);
        coinBillChartMonthlyInfo.setIncomeCoins(incomeCoins);
        return ResultMaster.success(coinBillChartMonthlyInfo);
    }

    public ResultInfo<CoinBillDaysInfo> chartDays(int userId, int year, int month){
        YearMonthInfo yearMonthInfo = new YearMonthInfo(year, month);
        List<CoinBillDaysInfo> coinBillDaysInfoList = coinBillDao.selectDaysBillByUserId(userId,
                yearMonthInfo.getStart(),
                yearMonthInfo.getEnd());
        return ResultMaster.success(coinBillDaysInfoList);
    }

}
