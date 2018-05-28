package com.wiatec.blive.service;

import com.wiatec.blive.common.result.EnumResult;
import com.wiatec.blive.common.result.ResultInfo;
import com.wiatec.blive.common.result.ResultMaster;
import com.wiatec.blive.common.result.XException;
import com.wiatec.blive.common.utils.TextUtil;
import com.wiatec.blive.common.utils.TimeUtil;
import com.wiatec.blive.dto.ProDetailInfo;
import com.wiatec.blive.orm.dao.*;
import com.wiatec.blive.orm.pojo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author patrick
 */
@Service
public class ProService {

    private final Logger logger = LoggerFactory.getLogger(ProService.class);

    @Resource
    private ProDao proDao;
    @Resource
    private CoinVoucherDao coinVoucherDao;
    @Resource
    private AuthRegisterUserDao authRegisterUserDao;
    @Resource private CoinService coinService;

    public ResultInfo<ProDetailInfo> getProDetails(int userId){
        AuthRegisterUserInfo userInfo = authRegisterUserDao.selectOneById(userId);
        if(userInfo == null){
            throw new XException(EnumResult.ERROR_INTERNAL_SERVER_SQL);
        }
        ProDetailInfo proDetailInfo = new ProDetailInfo();
        proDetailInfo.setUserId(userId);
        proDetailInfo.setLevel(userInfo.getLevel());
        proDetailInfo.setExpiresDate(userInfo.getExpiresTime());

        int days = TimeUtil.diffDays(userInfo.getExpiresTime(), new Date());
        if (days == 0) {
            days = 1;
        }
        if(days < 0){
            days = 0;
        }
        proDetailInfo.setLeftDays(days+"");
        return ResultMaster.success(proDetailInfo);
    }

    public ResultInfo getPurchaseList(){
        List<ProInfo> proInfoList = proDao.selectAll();
        if(proInfoList == null || proInfoList.size() <= 0){
            throw new XException(EnumResult.ERROR_NO_FOUND);
        }
        return ResultMaster.success(proInfoList);
    }

    @Transactional(rollbackFor = Exception.class)
    public ResultInfo purchasePro(int id, int userId, String voucherId, String platform){
        ProInfo proInfo = proDao.selectOnyById(id);
        if(proInfo == null){
            throw new XException(EnumResult.ERROR_INTERNAL_SERVER_SQL);
        }
        //通过voucher获取减免金额
        int offCoins = 0;
        if(!TextUtil.isEmpty(voucherId)){
            CoinVoucherInfo coinVoucherInfo = coinVoucherDao.selectOnyByVoucherId(voucherId);
            if(coinVoucherInfo == null){
                throw new XException("voucher does not exists");
            }
            if(coinVoucherInfo.getStatus() == CoinVoucherInfo.STATUS_USED){
                throw new XException("voucher has been used");
            }
            if(coinVoucherInfo.getExpiresDate().before(new Date())){
                throw new XException("voucher expires");
            }
            offCoins = coinVoucherInfo.getAmount();
        }
        int amount = 0;
        if(proInfo.isOnSale()){
            amount = proInfo.getOnSalePrice() - offCoins;
        }else{
            amount = proInfo.getPrice() - offCoins;
        }
        ResultInfo resultInfo = coinService.consumeCoin(userId, 0, CoinBillInfo.CATEGORY_CONSUME_PRO,
                amount, proInfo.getLevel(), proInfo.getMonths(), platform,
                "purchase pro " + proInfo.getMonths() + " months, consume " + amount + " coins",
                "use voucher, id: " + voucherId);
        if(!resultInfo.isSuccess()){
            throw new XException(EnumResult.ERROR_INTERNAL_SERVER_SQL);
        }
        return getProDetails(userId);
    }

}
