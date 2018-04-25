package com.wiatec.blive.orm.dao;

import com.wiatec.blive.orm.pojo.AuthRegisterUserInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author patrick
 */
public interface AuthRegisterUserDao {

    int countByUsername(String username);
    int countByEmail(String email);
    int countByToken(String token);
    int countByIdAndToken(@Param("userId") int userId, @Param("token") String token);
    int countOneByUserNameAndEmail(@Param("username") String username,
                                   @Param("email") String email);
    int countOneByUsernameAndPassowrd(@Param("username") String username,
                                      @Param("password") String password);

    int insertOne(AuthRegisterUserInfo authRegisterUserInfo);


    int selectEmailStatusByUsername(String username);
    String selectTokenByUsername(String username);
    String selectUsernameByToken(String token);
    String selectPasswordByUserId(int userId);
    AuthRegisterUserInfo selectOneByUsername(String username);
    AuthRegisterUserInfo selectOneById(int userId);
    List<AuthRegisterUserInfo> selectMultiByUserId(@Param("userIds") List<Integer> userIds);


    int updateEmailStatusByToken(String token);
    int updateTokenByUsername(@Param("username") String username, @Param("token") String token);
    int updateSignInInfoByUsername(AuthRegisterUserInfo authRegisterUserInfo);
    int updateIconByUserId(@Param("icon") String icon, @Param("userId") int userId);
    int updatePasswordByUsername(@Param("username") String username, @Param("password") String password);
    int updatePasswordByUserId(@Param("userId") int userId, @Param("password") String password);
    int updateGender(@Param("userId") int userId, @Param("gender") int gender);
    int updateProfile(@Param("userId") int userId, @Param("profile") String profile);
}
