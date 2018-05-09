package com.wiatec.blive.orm.dao;

import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author patrick
 */
@Repository
public interface LdIllegalWordDao {

    List<String> selectAll();
}
