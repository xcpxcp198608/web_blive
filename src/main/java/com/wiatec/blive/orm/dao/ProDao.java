package com.wiatec.blive.orm.dao;

import com.wiatec.blive.orm.pojo.CoinVoucherInfo;
import com.wiatec.blive.orm.pojo.ProInfo;

import java.util.List;

/**
 * @author patrick
 */
public interface ProDao {

    List<ProInfo> selectAll();
    ProInfo selectOnyById(int id);

}
