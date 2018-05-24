package com.wiatec.blive.orm.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author patrick
 */
@Repository
public interface RelationFollowDao {

    int selectOne(@Param("userId") int userId, @Param("friendId")int friendId);

    int insertOne(@Param("userId") int userId, @Param("friendId")int friendId);
    int deleteOne(@Param("userId") int userId, @Param("friendId")int friendId);
    List<Integer> selectFriendsIdByUserId(int userId);
    List<Integer> selectUserIdByFriendsId(int userId);



    List<Integer> selectFollowersIdByUserId(int userId);
}
