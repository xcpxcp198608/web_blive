package com.wiatec.blive.orm.dao;

import com.wiatec.blive.orm.pojo.RelationFriendInfo;
import com.wiatec.blive.orm.pojo.UserInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author patrick
 */
@Repository
public interface RelationFriendDao {

    int insertOne(@Param("userId") int userId, @Param("friendId")int friendId);
    int deleteOne(@Param("userId") int userId, @Param("friendId")int friendId);
    List<Integer> selectFriendsIdByUserId(int userId);
}
