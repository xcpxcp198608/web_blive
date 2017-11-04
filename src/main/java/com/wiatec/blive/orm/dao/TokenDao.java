package com.wiatec.blive.orm.dao;

import com.wiatec.blive.orm.pojo.TokenInfo;

public interface TokenDao {

    int countOne(TokenInfo tokenInfo);
    TokenInfo selectOne(TokenInfo tokenInfo);
    int countUserId(TokenInfo tokenInfo);
    void insertOne(TokenInfo tokenInfo);
    void updateOne(TokenInfo tokenInfo);
}
