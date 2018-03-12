package com.wiatec.blive.orm.dao;

import com.wiatec.blive.orm.pojo.UserInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author patrick
 */
@Repository
public interface UserDao {

    int countByUsername(String username);
    int countByEmail(UserInfo userInfo);
    int countByPhone(UserInfo userInfo);

    int validateStatus(UserInfo userInfo);
    int validateUserNameAndEmail(UserInfo userInfo);

    void insertOne(UserInfo userInfo);
    int countOne(UserInfo userInfo);
    int updateStatusByUsername(String username);
    void updateIcon(UserInfo userInfo);
    void update(UserInfo userInfo);
    int updateByOldPassword(@Param("userId") int userId,
                            @Param("oldPassword") String oldPassword,
                            @Param("newPassword") String newPassword);
    UserInfo selectOne(UserInfo userInfo);
    UserInfo selectOneWithChannel(UserInfo userInfo);
    UserInfo selectOneById(int userId);
    UserInfo selectOneByUsername(String username);
    UserInfo selectOneWithChannelByUserId(int userId);

    List<UserInfo> selectBefore(int limit);
    List<UserInfo> selectMultiWithChannelByUserId(@Param("userIds") List<Integer> userIds);
}
