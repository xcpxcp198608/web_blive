package com.wiatec.blive.orm.dao;

import com.wiatec.blive.orm.pojo.UserInfo;
import org.springframework.stereotype.Repository;

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
    UserInfo selectOne(UserInfo userInfo);
    UserInfo selectOneWithChannel(UserInfo userInfo);
    UserInfo selectOneById(int userId);
    UserInfo selectOneByUsername(String username);
    UserInfo selectUserAndChannels(UserInfo userInfo);
}
