package com.wiatec.blive.service;

import com.wiatec.blive.common.http.HttpMaster;
import com.wiatec.blive.common.result.EnumResult;
import com.wiatec.blive.common.result.ResultInfo;
import com.wiatec.blive.common.result.ResultMaster;
import com.wiatec.blive.common.result.XException;
import com.wiatec.blive.common.utils.TextUtil;
import com.wiatec.blive.orm.dao.CoinDao;
import com.wiatec.blive.orm.dao.CoinIAPDao;
import com.wiatec.blive.orm.dao.LogCoinDao;
import com.wiatec.blive.orm.pojo.CoinIAPInfo;
import com.wiatec.blive.orm.pojo.LogCoinInfo;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
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

    @Resource
    private CoinDao coinDao;
    @Resource
    private CoinIAPDao coinIAPDao;
    @Resource
    private LogCoinDao logCoinDao;

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
     * @param numbers coin numbers
     * @return ResultInfo
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultInfo consumeCoin(int userId, int targetUserId, int numbers, String platform,
                                  String description){
        int coins = coinDao.countCoins(userId);
        if(coins < numbers){
            throw new XException("Coins not enough");
        }
        if(coinDao.updateOne(userId, CONSUME_ACTION_SUB, numbers) != 1){
            throw new XException(EnumResult.ERROR_INTERNAL_SERVER_SQL);
        }

        if(coinDao.countOne(targetUserId) == 1) {
            if (coinDao.updateOne(targetUserId, CONSUME_ACTION_PLUS, numbers) != 1) {
                throw new XException(EnumResult.ERROR_INTERNAL_SERVER_SQL);
            }
        }else{
            if (coinDao.insertOne(targetUserId, numbers) != 1) {
                throw new XException(EnumResult.ERROR_INTERNAL_SERVER_SQL);
            }
        }
        //log user consume info
        LogCoinInfo logCoinInfo = new LogCoinInfo();
        logCoinInfo.setUserId(userId);
        logCoinInfo.setAction(CONSUME_ACTION_SUB);
        logCoinInfo.setCoin(numbers);
        logCoinInfo.setPlatform(platform);
        logCoinInfo.setDescription(description);
        logCoinInfo.setRemark("targetUserId" + targetUserId);
        logger.info(logCoinInfo.toString());
        logCoinDao.insertOne(logCoinInfo);

        //log user add coin info
        LogCoinInfo logCoinInfo1 = new LogCoinInfo();
        logCoinInfo1.setUserId(targetUserId);
        logCoinInfo1.setAction(CONSUME_ACTION_PLUS);
        logCoinInfo1.setCoin(numbers);
        logCoinInfo1.setPlatform(platform);
        logCoinInfo1.setDescription(description);
        logCoinInfo1.setRemark("consumeUserId" + userId);
        logger.info(logCoinInfo.toString());
        logCoinDao.insertOne(logCoinInfo1);
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

            //log iap info
            LogCoinInfo logCoinInfo = new LogCoinInfo();
            logCoinInfo.setUserId(userId);
            logCoinInfo.setAction(CONSUME_ACTION_PLUS);
            logCoinInfo.setCoin(coinIAPInfo.getNumber());
            logCoinInfo.setAmount(coinIAPInfo.getAmount());
            logCoinInfo.setPlatform(platform);
            logCoinInfo.setTransactionId(transactionId);
            logCoinInfo.setDescription("IAP purchase: " + coinIAPInfo.getName());
            logCoinInfo.setRemark(result);
            logCoinDao.insertOne(logCoinInfo);
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

            //log iap info
            LogCoinInfo logCoinInfo = new LogCoinInfo();
            logCoinInfo.setUserId(userId);
            logCoinInfo.setAction(CONSUME_ACTION_PLUS);
            logCoinInfo.setCoin(coinIAPInfo.getNumber());
            logCoinInfo.setAmount(coinIAPInfo.getAmount());
            logCoinInfo.setPlatform(platform);
            logCoinInfo.setTransactionId(transactionId);
            logCoinInfo.setDescription("IAP purchase: " + coinIAPInfo.getName());
            logCoinInfo.setRemark(result);
            logCoinDao.insertOne(logCoinInfo);
        } catch (IOException e) {
            throw new XException("Apple server response error");
        }
        return ResultMaster.success(coinDao.countCoins(userId));
    }


    public ResultInfo<LogCoinInfo> getBills(int userId){
        List<LogCoinInfo> logCoinInfoList = logCoinDao.selectByUserId(userId);
        if(logCoinInfoList == null || logCoinInfoList.size() <= 0){
            throw new XException(EnumResult.ERROR_NO_FOUND);
        }
        return ResultMaster.success(logCoinInfoList);
    }

}
