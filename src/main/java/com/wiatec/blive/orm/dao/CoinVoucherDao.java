package com.wiatec.blive.orm.dao;

import com.wiatec.blive.orm.pojo.CoinVoucherInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author patrick
 */
public interface CoinVoucherDao {

    List<CoinVoucherInfo> selectAll();
    CoinVoucherInfo selectOnyByVoucherId(String voucherId);

}
