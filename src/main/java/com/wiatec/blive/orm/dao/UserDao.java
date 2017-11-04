package com.wiatec.blive.orm.dao;

import com.wiatec.blive.orm.pojo.UserInfo;

public interface UserDao {

    int countUsername(UserInfo userInfo);
    int countEmail(UserInfo userInfo);
    int countPhone(UserInfo userInfo);
    int validateStatus(UserInfo userInfo);
    int validateUserNameAndEmail(UserInfo userInfo);

    void insertOne(UserInfo userInfo);
    int countOne(UserInfo userInfo);
    void updateStatus(UserInfo userInfo);
    void updateIcon(UserInfo userInfo);
    void update(UserInfo userInfo);
    UserInfo selectOne(UserInfo userInfo);
    UserInfo selectOneById(UserInfo userInfo);
    UserInfo selectUserAndChannels(UserInfo userInfo);
}
