package com.wiatec.blive.orm.dao;

import com.wiatec.blive.orm.pojo.LogLiveCommentInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author patrick
 */
@Repository
public interface LogLiveCommentDao {

    List<LogLiveCommentInfo> selectAll();
    int insertOne(LogLiveCommentInfo logLiveCommentInfo);
}
