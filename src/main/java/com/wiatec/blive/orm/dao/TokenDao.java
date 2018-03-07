package com.wiatec.blive.orm.dao;

import com.wiatec.blive.orm.pojo.TokenInfo;
import org.springframework.stereotype.Repository;

/**
 * @author patrick
 */
@Repository
public interface TokenDao {

    int countOneByToken(String token);
    TokenInfo selectOneByToken(String token);
    TokenInfo selectOneByUserId(int userId);
    int countByUserId(TokenInfo tokenInfo);

    int insertOne(TokenInfo tokenInfo);
    int updateOne(TokenInfo tokenInfo);
}
