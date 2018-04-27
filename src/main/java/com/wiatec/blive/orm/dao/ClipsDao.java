package com.wiatec.blive.orm.dao;


import com.wiatec.blive.orm.pojo.ClipsInfo;

import java.util.List;

/**
 * @author patrick
 */
public interface ClipsDao {

    List<ClipsInfo> selectAllVisible();
}
