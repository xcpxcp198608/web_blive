package com.wiatec.blive.orm.dao;

import com.wiatec.blive.orm.pojo.CoinIAPInfo;

/**
 * @author patrick
 */
public interface CoinIAPDao {

    CoinIAPInfo selectOneByIdentifier(String identifier);

}
