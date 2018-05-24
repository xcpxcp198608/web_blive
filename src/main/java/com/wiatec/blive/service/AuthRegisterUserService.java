package com.wiatec.blive.service;

import com.wiatec.blive.common.base.BaseService;
import com.wiatec.blive.common.result.EnumResult;
import com.wiatec.blive.common.result.ResultInfo;
import com.wiatec.blive.common.result.ResultMaster;
import com.wiatec.blive.common.result.XException;
import com.wiatec.blive.common.utils.EmailMaster;
import com.wiatec.blive.common.utils.TextUtil;
import com.wiatec.blive.common.utils.TokenUtil;
import com.wiatec.blive.listener.SessionListener;
import com.wiatec.blive.orm.dao.*;
import com.wiatec.blive.orm.pojo.AuthRegisterUserInfo;
import com.wiatec.blive.orm.pojo.LiveChannelInfo;
import com.wiatec.blive.orm.pojo.LogUserOperationInfo;
import com.wiatec.blive.rongc.RCManager;
import com.wiatec.blive.rtmp.RtmpInfo;
import com.wiatec.blive.rtmp.RtmpMaster;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author patrick
 */
@Service
public class AuthRegisterUserService extends BaseService {

    @Resource
    private AuthRegisterUserDao authRegisterUserDao;
    @Resource
    private LiveChannelDao liveChannelDao;
    @Resource
    private RelationFollowDao relationFollowDao;
    @Resource
    private LogUserOperationDao logUserOperationDao;


    /**
     * user sign up
     * @param request   HttpServletRequest
     * @param userInfo  UserInfo
     * @return ResultInfo
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultInfo<AuthRegisterUserInfo> signUp(HttpServletRequest request, AuthRegisterUserInfo userInfo){
        if(userInfo.getUsername().toLowerCase().contains("james")){
            throw new XException(EnumResult.ERROR_USERNAME_EXISTS);
        }
        if(authRegisterUserDao.countByUsername(userInfo.getUsername()) == COUNT_1){
            throw new XException(EnumResult.ERROR_USERNAME_EXISTS);
        }
        if(authRegisterUserDao.countByEmail(userInfo.getEmail()) == COUNT_1) {
            throw new XException(EnumResult.ERROR_EMAIL_EXISTS);
        }
        String token = TokenUtil.create64(userInfo.getUsername());
        userInfo.setToken(token);
        // insert user information
        authRegisterUserDao.insertOne(userInfo);
        AuthRegisterUserInfo userInfo1 = authRegisterUserDao.selectOneByUsername(userInfo.getUsername());

        RtmpInfo rtmpInfo = new RtmpMaster().getRtmpInfo(userInfo1.getUsername());
        if(rtmpInfo == null){
            throw new XException("rtmp server error");
        }
        LiveChannelInfo channelInfo = new LiveChannelInfo();
        channelInfo.setTitle(userInfo1.getUsername());
        channelInfo.setUserId(userInfo1.getId());
        channelInfo.setUrl(rtmpInfo.getPush_full_url());
        channelInfo.setRtmpUrl(rtmpInfo.getPush_url());
        channelInfo.setRtmpKey(rtmpInfo.getPush_key());
        channelInfo.setPlayUrl(rtmpInfo.getPlay_url());
        if(liveChannelDao.insertChannel(channelInfo) != COUNT_1){
            throw new XException(EnumResult.ERROR_INTERNAL_SERVER_SQL);
        }

        // send validate email
        EmailMaster emailMaster = new EmailMaster();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        emailMaster.setEmailContent(basePath, userInfo.getUsername(), token);
        emailMaster.send(userInfo1.getEmail());
        return ResultMaster.success("Please check your email to confirm and activate the account. " +
                "The activation email may take up to 60 minutes to arrive, " +
                "if you didn't get the email, please contact customer service.");
    }

    /**
     * user activate by email link
     * @param token token from system create in sign up
     * @return activate result message
     */
    public String activate(String token){
        if(authRegisterUserDao.countByToken(token) != COUNT_1){
            return "access token error";
        }
        if(authRegisterUserDao.updateEmailStatusByToken(token) != COUNT_1){
            return "activate failure";
        }
        return "activate successfully";
    }

    /**
     * user sign in
     * @return ResultInfo
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultInfo signIn(AuthRegisterUserInfo userInfo){
        if (authRegisterUserDao.countByUsername(userInfo.getUsername()) != 1) {
            throw new XException(EnumResult.ERROR_USERNAME_NOT_EXISTS);
        }
        if (authRegisterUserDao.countOneByUsernameAndPassowrd(userInfo.getUsername(), userInfo.getPassword()) != COUNT_1) {
            throw new XException(EnumResult.ERROR_USERNAME_PASSWORD_NO_MATCH);
        }
        if(authRegisterUserDao.selectEmailStatusByUsername(userInfo.getUsername()) != COUNT_1){
            throw new XException(EnumResult.ERROR_EMAIL_NO_ACTIVATE);
        }
        String token = TokenUtil.create64(userInfo.getUsername());
        userInfo.setToken(token);
        authRegisterUserDao.updateSignInInfoByUsername(userInfo);

        AuthRegisterUserInfo userInfo1 = authRegisterUserDao.selectOneByUsername(userInfo.getUsername());

        if(TextUtil.isEmpty(userInfo1.getRcToken())){
            if(userInfo1.getIcon() == null){
                userInfo1.setIcon("sf");
            }
            String rcToken = RCManager.getToken(userInfo1.getId(), userInfo1.getUsername(), userInfo1.getIcon());
            authRegisterUserDao.updateRCTokenById(userInfo1.getId(), rcToken);
        }

        userInfo1 = authRegisterUserDao.selectOneByUsername(userInfo.getUsername());
        if(userInfo1 == null){
            throw new XException(EnumResult.ERROR_INTERNAL_SERVER_SQL);
        }
        logUserOperationDao.insertOne(userInfo1.getId(), LogUserOperationInfo.TYPE_SELECT, "sign in");
        return ResultMaster.success(userInfo1);
    }

    /**
     * request reset user password from user then send reset email
     * @param request HttpServletRequest
     * @param username username
     * @return ResultInfo
     */
    public ResultInfo reset(HttpServletRequest request, String username, String email){
        if(authRegisterUserDao.countByUsername(username) != COUNT_1){
            throw new XException(EnumResult.ERROR_USERNAME_NOT_EXISTS);
        }
        if(authRegisterUserDao.countByEmail(email) != COUNT_1){
            throw new XException(EnumResult.ERROR_EMAIL_NOT_EXISTS);
        }
        if(authRegisterUserDao.countOneByUserNameAndEmail(username, email) != COUNT_1){
            throw new XException("username and email not match");
        }
        String token = authRegisterUserDao.selectTokenByUsername(username);
        EmailMaster emailMaster = new EmailMaster();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        emailMaster.setResetPasswordContent(basePath, username, token);
        emailMaster.send(email);
        return ResultMaster.success("successfully, please check email to finish reset your password");
    }

    /**
     * validate token and return username
     * @param token token
     * @return username
     */
    public String go(String token){
        if(authRegisterUserDao.countByToken(token) != COUNT_1){
            throw new XException(EnumResult.ERROR_TOKEN_NOT_EXISTS);
        }
        return authRegisterUserDao.selectUsernameByToken(token);
    }

    /**
     * update user password
     * @param username username
     * @return ResultInfo
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultInfo updatePasswordByUsername(String username,  String password){
        if(TextUtil.isEmpty(password)){
            throw new XException("password is empty");
        }
        if(password.length() < LENGTH_6){
            throw new XException("password format error");
        }
        if(authRegisterUserDao.updatePasswordByUsername(username, password) != COUNT_1){
            throw new XException(EnumResult.ERROR_INTERNAL_SERVER_SQL);
        }
        AuthRegisterUserInfo userInfo = authRegisterUserDao.selectOneByUsername(username);
        logUserOperationDao.insertOne(userInfo.getId(), LogUserOperationInfo.TYPE_UPDATE, "update password by email");
        return ResultMaster.success("reset successfully");
    }

    /**
     * update user password by id and old password
     * @param userId user id
     * @return ResultInfo
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultInfo updateByOldPassword(int userId, String oldPassword, String newPassword){
        if(TextUtil.isEmpty(oldPassword)){
            throw new XException("password is incorrect");
        }
        if(TextUtil.isEmpty(newPassword) || newPassword.length() < LENGTH_6){
            throw new XException("new password format error");
        }
        if(!oldPassword.equals(authRegisterUserDao.selectPasswordByUserId(userId))){
            throw new XException("password is incorrect");
        }
        if(authRegisterUserDao.updatePasswordByUserId(userId, newPassword) != COUNT_1){
            throw new XException(EnumResult.ERROR_INTERNAL_SERVER_SQL);
        }
        logUserOperationDao.insertOne(userId, LogUserOperationInfo.TYPE_UPDATE, "update password in app");
        return ResultMaster.success("password update successful");
    }

    /**
     * user validate
     * @param token token
     * @return ResultInfo
     */
    public ResultInfo validateToken(int userId, String token){
        if(TextUtil.isEmpty(token) || token.length() != LENGTH_64){
            throw new XException(EnumResult.ERROR_ACCESS_TOKEN);
        }
        if(authRegisterUserDao.countByIdAndToken(userId, token) != COUNT_1){
            throw new XException(EnumResult.ERROR_ACCESS_TOKEN);
        }
        AuthRegisterUserInfo authRegisterUserInfo = authRegisterUserDao.selectOneById(userId);
        return ResultMaster.success(authRegisterUserInfo);
    }

    /**
     * user sign out, invalidate session
     * @return ResultInfo
     */
    public ResultInfo signOut(String username){
        HttpSession session = SessionListener.getSession(username);
        if(session != null){
            session.invalidate();
        }
        return ResultMaster.success("sign out successfully");
    }

    /**
     * update user icon
     * @param userId userId
     * @return ResultInfo
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultInfo updateIcon(String icon, int userId){
        if(authRegisterUserDao.updateIconByUserId(icon, userId) != COUNT_1){
            throw new XException(EnumResult.ERROR_INTERNAL_SERVER_SQL);
        }
        AuthRegisterUserInfo userInfo = authRegisterUserDao.selectOneById(userId);
        if(userInfo == null){
            throw new XException(EnumResult.ERROR_INTERNAL_SERVER_SQL);
        }
        String rcToken = RCManager.getToken(userInfo.getId(), userInfo.getUsername(), userInfo.getIcon());
        authRegisterUserDao.updateRCTokenById(userInfo.getId(), rcToken);
        userInfo = authRegisterUserDao.selectOneById(userId);
        if(userInfo == null){
            throw new XException(EnumResult.ERROR_INTERNAL_SERVER_SQL);
        }
        logUserOperationDao.insertOne(userId, LogUserOperationInfo.TYPE_UPDATE, "update icon");
        return ResultMaster.success(userInfo);
    }

    /**
     * get user info by user id
     * @param userId user id
     * @return ResultInfo
     */
    public ResultInfo<AuthRegisterUserInfo> selectOneByUserId(int userId){
        AuthRegisterUserInfo authRegisterUserInfo = authRegisterUserDao.selectOneById(userId);
        if(authRegisterUserInfo == null){
            throw new XException(EnumResult.ERROR_NO_FOUND);
        }
        return ResultMaster.success(authRegisterUserInfo);
    }

    /**
     * get all friend user info by user id
     * @param userId user id
     */
    public ResultInfo follows(int userId){
        List<Integer> friendIds = relationFollowDao.selectFriendsIdByUserId(userId);
        if(friendIds == null || friendIds.size() <= COUNT_0){
            throw new XException(EnumResult.ERROR_NO_FOUND);
        }
        List<AuthRegisterUserInfo> authRegisterUserInfoList = authRegisterUserDao
                .selectMultiByUserId(friendIds);
        if(authRegisterUserInfoList == null || authRegisterUserInfoList.size() <= COUNT_0){
            throw new XException(EnumResult.ERROR_NO_FOUND);
        }
        return ResultMaster.success(authRegisterUserInfoList);
    }

    public ResultInfo followStatus(int userId, int friendId){
        String status = "false";
        if(relationFollowDao.selectOne(userId, friendId) >= COUNT_1){
            status = "true";
        }
        return ResultMaster.success(status);
    }


    /**
     * set user relation
     * @param action 0->release follow, 1->follow
     * @param userId  user id
     * @param friendId target user id
     * @return ResultInfo
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultInfo follow(int action, int userId, int friendId){
        int i;
        if(userId == friendId){
            throw new XException("can not follow yourself");
        }
        if(action == 0){
            i = relationFollowDao.deleteOne(userId, friendId);
            logUserOperationDao.insertOne(userId, LogUserOperationInfo.TYPE_DELETE, "cancel follow " + friendId);

        }else if(action == 1){
            if(relationFollowDao.selectOne(userId, friendId) >= COUNT_1){
                throw new XException("relation already exists");
            }
            i = relationFollowDao.insertOne(userId, friendId);
            logUserOperationDao.insertOne(userId, LogUserOperationInfo.TYPE_INSERT, "create follow " + friendId);
        }else{
            throw new XException("action error");
        }
        if(i != COUNT_1) {
            throw new XException("operation error");
        }
        return ResultMaster.success();
    }


    public ResultInfo<AuthRegisterUserInfo> getFollowers(int userId){
        List<Integer> userIds = relationFollowDao.selectUserIdByFriendsId(userId);
        if(userIds == null || userIds.size() <= COUNT_0){
            throw new XException(EnumResult.ERROR_NO_FOUND);
        }
        List<AuthRegisterUserInfo> authRegisterUserInfoList = authRegisterUserDao
                .selectMultiByUserId(userIds);
        System.out.println(authRegisterUserInfoList);
        if(authRegisterUserInfoList == null || authRegisterUserInfoList.size() <= COUNT_0){
            throw new XException(EnumResult.ERROR_NO_FOUND);
        }
        return ResultMaster.success(authRegisterUserInfoList);
    }


    public ResultInfo updateGender(int userId, int gender){
        if(authRegisterUserDao.updateGender(userId, gender) != COUNT_1){
            throw new XException(EnumResult.ERROR_INTERNAL_SERVER_SQL);
        }
        return ResultMaster.success();
    }


    public ResultInfo updateProfile(int userId, String profile){
        if(authRegisterUserDao.updateProfile(userId, profile) != COUNT_1){
            throw new XException(EnumResult.ERROR_INTERNAL_SERVER_SQL);
        }
        return ResultMaster.success();
    }

}
