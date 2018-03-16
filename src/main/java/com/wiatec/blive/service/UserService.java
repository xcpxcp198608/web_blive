//package com.wiatec.blive.service;
//
//import com.wiatec.blive.common.result.EnumResult;
//import com.wiatec.blive.common.result.ResultInfo;
//import com.wiatec.blive.common.result.ResultMaster;
//import com.wiatec.blive.common.result.XException;
//import com.wiatec.blive.listener.SessionListener;
//import com.wiatec.blive.orm.dao.ChannelDao;
//import com.wiatec.blive.orm.dao.RelationFriendDao;
//import com.wiatec.blive.orm.dao.TokenDao;
//import com.wiatec.blive.orm.dao.UserDao;
//import com.wiatec.blive.orm.pojo.ChannelInfo;
//import com.wiatec.blive.orm.pojo.TokenInfo;
//import com.wiatec.blive.orm.pojo.UserInfo;
//import com.wiatec.blive.common.utils.*;
//import com.wiatec.blive.rtmp.RtmpInfo;
//import com.wiatec.blive.rtmp.RtmpMaster;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//import java.util.List;
//
///**
// * @author patrick
// */
//@Service
//public class UserService {
//
//    private final int TIME_TOKEN_EXPIRES = 600000;
//
//    @Resource
//    private UserDao userDao;
//    @Resource
//    private ChannelDao channelDao;
//    @Resource
//    private TokenDao tokenDao;
//    @Resource
//    private RelationFriendDao relationFriendDao;
//
//    /**
//     * user sign up
//     * @param request   HttpServletRequest
//     * @param userInfo  UserInfo
//     * @return ResultInfo
//     */
//    @Transactional(rollbackFor = Exception.class)
//    public ResultInfo<UserInfo> signUp(HttpServletRequest request, UserInfo userInfo){
//        if(userDao.countByUsername(userInfo.getUsername()) == 1){
//            throw new XException(EnumResult.ERROR_USERNAME_EXISTS);
//        }
//        if(userDao.countByEmail(userInfo) == 1) {
//            throw new XException(EnumResult.ERROR_EMAIL_EXISTS);
//        }
//        // insert user information
//        userDao.insertOne(userInfo);
//        UserInfo userInfo1 = userDao.selectOneByUsername(userInfo.getUsername());
//        // get rtmp information and set in table channel
//        RtmpInfo rtmpInfo = new RtmpMaster().getRtmpInfo(userInfo1.getUsername());
//        if(rtmpInfo == null){
//            throw new XException("rtmp server error");
//        }
//        ChannelInfo channelInfo = new ChannelInfo();
//        channelInfo.setTitle(userInfo1.getUsername());
//        channelInfo.setUserId(userInfo1.getId());
//        channelInfo.setUrl(rtmpInfo.getPush_full_url());
//        channelInfo.setRtmpUrl(rtmpInfo.getPush_url());
//        channelInfo.setRtmpKey(rtmpInfo.getPush_key());
//        channelInfo.setPlayUrl(rtmpInfo.getPlay_url());
//        if(channelDao.insertChannel(channelInfo) != 1){
//            throw new XException("channel setting error");
//        }
//        // send validate email
//        String token = AESUtil.encrypt(System.currentTimeMillis() + userInfo1.getUsername(), AESUtil.KEY);
//        EmailMaster emailMaster = new EmailMaster();
//        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
//        emailMaster.setEmailContent(basePath, userInfo.getUsername(), token);
//        emailMaster.send(userInfo1.getEmail());
//        return ResultMaster.success("Please check your email to confirm and activate the account. " +
//                "The activation email may take up to 60 minutes to arrive, " +
//                "if you didn't get the email, please contact customer service.", userInfo1);
//    }
//
//    /**
//     * user activate by email link
//     * @param token token from system create in sign up
//     * @return activate result message
//     */
//    public String activate(String token){
//        String t = AESUtil.decrypt(token, AESUtil.KEY);
//        String username = t.substring(13, t.length());
//        if(!(userDao.countByUsername(username) == 1)){
//            return "user not exists";
//        }
//        if(userDao.updateStatusByUsername(username) != 1){
//            return "activate failure";
//        }
//        return "activate successfully";
//    }
//
//    /**
//     * user sign in
//     * @param userInfo UserInfo
//     * @return ResultInfo
//     */
//    public ResultInfo signIn(UserInfo userInfo){
//        if (userDao.countByUsername(userInfo.getUsername()) != 1) {
//            throw new XException(EnumResult.ERROR_USERNAME_NOT_EXISTS);
//        }
//        if (userDao.countOne(userInfo) != 1) {
//            throw new XException(EnumResult.ERROR_USERNAME_PASSWORD_NO_MATCH);
//        }
//        if(userDao.validateStatus(userInfo) != 1){
//            throw new XException(EnumResult.ERROR_EMAIL_NO_ACTIVATE);
//        }
//        UserInfo userInfo1 = userDao.selectOne(userInfo);
//        TokenInfo tokenInfo = new TokenInfo();
//        tokenInfo.setToken(TokenUtil.create32(userInfo.getUsername(), System.currentTimeMillis() + "") +
//                TokenUtil.create32(userInfo.getUsername(), System.currentTimeMillis() + ""));
//        tokenInfo.setUserId(userInfo1.getId());
//        int i = tokenDao.countByUserId(tokenInfo) == 1 ?
//                tokenDao.updateOne(tokenInfo) :
//                tokenDao.insertOne(tokenInfo);
//        tokenInfo = tokenDao.selectOneByUserId(userInfo1.getId());
//        return ResultMaster.success(tokenInfo);
//    }
//
//    /**
//     * request reset user password from user
//     * @param request HttpServletRequest
//     * @param userInfo UserInfo
//     * @return ResultInfo
//     */
//    public ResultInfo reset(HttpServletRequest request, UserInfo userInfo){
//        if(userDao.countByUsername(userInfo.getUsername()) != 1){
//            throw new XException(EnumResult.ERROR_USERNAME_NOT_EXISTS);
//        }
//        if(userDao.countByEmail(userInfo) != 1){
//            throw new XException(EnumResult.ERROR_EMAIL_NOT_EXISTS);
//        }
//        if(userDao.validateUserNameAndEmail(userInfo) != 1){
//            throw new XException("username and email not match");
//        }
//        String token = AESUtil.encrypt(System.currentTimeMillis() + userInfo.getUsername(), AESUtil.KEY);
//        EmailMaster emailMaster = new EmailMaster();
//        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
//        emailMaster.setResetPasswordContent(basePath, userInfo.getUsername(), token);
//        emailMaster.send(userInfo.getEmail());
//        return ResultMaster.success("successfully, please check email to finish reset your password");
//    }
//
//    /**
//     * validate token and return username
//     * @param token token
//     * @return username
//     */
//    public String go(String token){
//        String t = AESUtil.decrypt(token, AESUtil.KEY);
//        long time = Long.parseLong(t.substring(0, 13));
//        if(time + TIME_TOKEN_EXPIRES < System.currentTimeMillis()){
//            throw new XException("operation expires");
//        }
//        String username = t.substring(13, t.length());
//        if(userDao.countByUsername(username) != 1){
//            throw new XException(EnumResult.ERROR_USERNAME_NOT_EXISTS);
//        }
//        return username;
//    }
//
//    /**
//     * update user password
//     * @param userInfo UserInfo
//     * @return ResultInfo
//     */
//    public ResultInfo update(UserInfo userInfo){
//        if(TextUtil.isEmpty(userInfo.getPassword())){
//            throw new XException("password is empty");
//        }
//        if(userInfo.getPassword().length() < 6){
//            throw new XException("password format error");
//        }
//        userDao.update(userInfo);
//        return ResultMaster.success("reset successfully");
//    }
//
//    /**
//     * update user password by id and old password
//     * @param userId user id
//     * @return ResultInfo
//     */
//    public ResultInfo updateByOldPassword(int userId, String oldPassword, String newPassword){
//        if(TextUtil.isEmpty(oldPassword)){
//            throw new XException("password is incorrect");
//        }
//        if(TextUtil.isEmpty(newPassword) || newPassword.length() < 6){
//            throw new XException("new password format error");
//        }
//        int i = userDao.updateByOldPassword(userId, oldPassword, newPassword);
//        if(i != 1){
//            throw new XException(EnumResult.ERROR_USERNAME_PASSWORD_NO_MATCH);
//        }
//        return ResultMaster.success("password update successful");
//    }
//
//    /**
//     * user validate
//     * @param userInfo UserInfo
//     * @return ResultInfo
//     */
//    public ResultInfo validate(UserInfo userInfo){
//        if(userDao.countByUsername(userInfo.getUsername()) != 1){
//            throw new XException(EnumResult.ERROR_USERNAME_NOT_EXISTS);
//        }
//        UserInfo userInfo1 = userDao.selectOne(userInfo);
//        if(!userInfo1.isStatus()){
//            throw new XException("user status error");
//        }
//        return ResultMaster.success("validate successfully");
//    }
//
//    /**
//     * user sign out, invalidate session
//     * @param userInfo UserInfo
//     * @return ResultInfo
//     */
//    public ResultInfo signOut(UserInfo userInfo){
//        HttpSession session = SessionListener.getSession(userInfo.getUsername());
//        if(session != null){
//            session.invalidate();
//        }
//        return ResultMaster.success("sign out successfully");
//    }
//
//    /**
//     * update user icon
//     * @param userInfo UserInfo
//     * @return ResultInfo
//     */
//    public ResultInfo<UserInfo> updateIcon(UserInfo userInfo){
//        userDao.updateIcon(userInfo);
//        return ResultMaster.success(userDao.selectOneById(userInfo.getId()));
//    }
//
//    /**
//     * get all friend user info by user id
//     * @param userId user id
//     */
//    public ResultInfo<UserInfo> follows(int userId){
//        List<Integer> friendIds = relationFriendDao.selectFriendsIdByUserId(userId);
//        List<UserInfo> userInfoList = userDao.selectBefore(100);
//        if(friendIds != null && friendIds.size() > 0) {
//            List<UserInfo> userInfoList1 = userDao.selectMultiWithChannelByUserId(friendIds);
//            if (userInfoList1 != null && userInfoList1.size() > 0) {
//                userInfoList.addAll(userInfoList1);
//            }
//        }
//        if(userInfoList.size() <= 0){
//            throw new XException(EnumResult.ERROR_NO_FOUND);
//        }
//        return ResultMaster.success(userInfoList);
//    }
//
//
//    public ResultInfo followStatus(int userId, int friendId){
//        String status = "false";
//        if(relationFriendDao.selectOne(userId, friendId) >= 1){
//            status = "true";
//        }
//        return ResultMaster.success(status);
//    }
//
//    /**
//     * set user relation
//     * @param action 0->release follow, 1->follow
//     * @param userId  user id
//     * @param friendId target user id
//     * @return ResultInfo
//     */
//    public ResultInfo follow(int action, int userId, int friendId){
//        int i;
//        if(action == 0){
//            i = relationFriendDao.deleteOne(userId, friendId);
//        }else if(action == 1){
//            if(relationFriendDao.selectOne(userId, friendId) >=1){
//                throw new XException("relationship already exixts");
//            }
//            i = relationFriendDao.insertOne(userId, friendId);
//        }else{
//            throw new XException("action error");
//        }
//        if(i != 1) {
//            throw new XException("operation error");
//        }
//        return ResultMaster.success();
//    }
//
//
//    public UserInfo selectOneWithChannelByUserId(int userId){
//        return userDao.selectOneWithChannelByUserId(userId);
//    }
//}
