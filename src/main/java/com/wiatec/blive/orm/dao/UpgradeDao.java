package com.wiatec.blive.orm.dao;

import com.wiatec.blive.orm.pojo.UpgradeInfo;
import org.springframework.stereotype.Repository;

/**
 * data operation interface of update table
 * @author patrick
 */
@Repository
public interface UpgradeDao {

    /**
     * select first row from table of upgrade
     * @return UpgradeInfo
     */
    UpgradeInfo selectOne();
}
