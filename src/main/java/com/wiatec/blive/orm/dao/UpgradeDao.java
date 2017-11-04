package com.wiatec.blive.orm.dao;

import com.wiatec.blive.orm.pojo.UpgradeInfo;

/**
 * data operation interface of update table
 */
public interface UpgradeDao {
    //query the first row from the table of update
    UpgradeInfo selectOne();
}
