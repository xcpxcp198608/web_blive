package com.wiatec.blive.service;

import com.wiatec.blive.entity.ResultInfo;
import com.wiatec.blive.listener.SessionListener;
import com.wiatec.blive.orm.dao.TokenDao;
import com.wiatec.blive.orm.dao.UserDao;
import com.wiatec.blive.orm.pojo.TokenInfo;
import com.wiatec.blive.orm.pojo.UserInfo;
import com.wiatec.blive.xutils.AESUtil;
import com.wiatec.blive.xutils.EmailMaster;
import com.wiatec.blive.xutils.TextUtil;
import com.wiatec.blive.xutils.TokenUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
public class UserService {

    @Resource
    private UserDao userDao;

    @Resource
    private TokenDao tokenDao;

    @Transactional
    public ResultInfo<UserInfo> signUp(HttpServletRequest request, UserInfo userInfo){
        ResultInfo<UserInfo> resultInfo = new ResultInfo<>();
        if(userDao.countUsername(userInfo) == 1){
            resultInfo.setCode(ResultInfo.CODE_INVALID);
            resultInfo.setStatus(ResultInfo.STATUS_INVALID);
            resultInfo.setMessage("duplicate username");
            return resultInfo;
        }
        if(userDao.countEmail(userInfo) == 1){
            resultInfo.setCode(ResultInfo.CODE_INVALID);
            resultInfo.setStatus(ResultInfo.STATUS_INVALID);
            resultInfo.setMessage("duplicate email");
            return resultInfo;
        }
        if(userDao.countPhone(userInfo) == 1){
            resultInfo.setCode(ResultInfo.CODE_INVALID);
            resultInfo.setStatus(ResultInfo.STATUS_INVALID);
            resultInfo.setMessage("duplicate phone");
            return resultInfo;
        }
        userDao.insertOne(userInfo);
        String token = AESUtil.encrypt(System.currentTimeMillis() + userInfo.getUsername(), AESUtil.KEY);
        EmailMaster emailMaster = new EmailMaster();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        emailMaster.setEmailContent(basePath, userInfo.getUsername(), token);
        emailMaster.send(userInfo.getEmail());
        resultInfo.setCode(ResultInfo.CODE_OK);
        resultInfo.setStatus(ResultInfo.STATUS_CREATED);
        resultInfo.setMessage("Signup successfully, check email and activate your account");
        resultInfo.setT(userInfo);
        return resultInfo;
    }

    @Transactional
    public String activate(String token){
        String t = AESUtil.decrypt(token, AESUtil.KEY);
        String username = t.substring(13, t.length());
        if(!(userDao.countUsername(new UserInfo(username)) == 1)){
            return "user not exists";
        }
        try {
            userDao.updateStatus(new UserInfo(username));
        }catch (Exception e){
            return "activate failure";
        }
        return "activate successfully";
    }

    @Transactional
    public ResultInfo signIn(UserInfo userInfo){
        ResultInfo<TokenInfo> resultInfo = new ResultInfo<>();
        try {
            if (!(userDao.countUsername(userInfo) == 1)) {
                resultInfo.setCode(ResultInfo.CODE_UNAUTHORIZED);
                resultInfo.setStatus(ResultInfo.STATUS_UNAUTHORIZED);
                resultInfo.setMessage("user not exists!");
                return resultInfo;
            }
            if (userDao.countOne(userInfo) == 1) {
                if(userDao.validateStatus(userInfo) != 1){
                    resultInfo.setCode(ResultInfo.CODE_UNAUTHORIZED);
                    resultInfo.setStatus(ResultInfo.STATUS_UNAUTHORIZED);
                    resultInfo.setMessage("email not activate");
                    return resultInfo;
                }
                UserInfo userInfo1 = userDao.selectOne(userInfo);
                TokenInfo tokenInfo = new TokenInfo();
                tokenInfo.setToken(TokenUtil.create(userInfo.getUsername(),
                        System.currentTimeMillis() + "") +
                        TokenUtil.create(userInfo.getUsername(),
                        System.currentTimeMillis() + ""));
                tokenInfo.setUserId(userInfo1.getId());
                if(tokenDao.countUserId(tokenInfo)==1){
                    tokenDao.updateOne(tokenInfo);
                }else {
                    tokenDao.insertOne(tokenInfo);
                }
                tokenInfo = tokenDao.selectOne(tokenInfo);
                resultInfo.setCode(ResultInfo.CODE_OK);
                resultInfo.setStatus(ResultInfo.STATUS_OK);
                resultInfo.setMessage("Signin successfully");
                resultInfo.setT(tokenInfo);
            } else {
                resultInfo.setCode(ResultInfo.CODE_UNAUTHORIZED);
                resultInfo.setStatus(ResultInfo.STATUS_UNAUTHORIZED);
                resultInfo.setMessage("username and password not match");
            }
            return resultInfo;
        }catch (Exception e){
            resultInfo.setCode(ResultInfo.CODE_SERVER_ERROR);
            resultInfo.setStatus(ResultInfo.STATUS_SERVER_ERROR);
            resultInfo.setMessage("Signin error");
            return resultInfo;
        }
    }

    @Transactional
    public ResultInfo reset(HttpServletRequest request, UserInfo userInfo){
        ResultInfo resultInfo = new ResultInfo();
        if(!(userDao.countUsername(userInfo) == 1)){
            resultInfo.setCode(ResultInfo.CODE_UNAUTHORIZED);
            resultInfo.setStatus(ResultInfo.STATUS_UNAUTHORIZED);
            resultInfo.setMessage("user not exists");
            return resultInfo;
        }
        if(!(userDao.countEmail(userInfo) == 1)){
            resultInfo.setCode(ResultInfo.CODE_INVALID);
            resultInfo.setStatus(ResultInfo.STATUS_INVALID);
            resultInfo.setMessage("email not exists");
            return resultInfo;
        }
        if(!(userDao.validateUserNameAndEmail(userInfo) == 1)){
            resultInfo.setCode(ResultInfo.CODE_INVALID);
            resultInfo.setStatus(ResultInfo.STATUS_INVALID);
            resultInfo.setMessage("username and email not match");
            return resultInfo;
        }
        String token = AESUtil.encrypt(System.currentTimeMillis() + userInfo.getUsername(), AESUtil.KEY);
        EmailMaster emailMaster = new EmailMaster();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        emailMaster.setResetPasswordContent(basePath, userInfo.getUsername(), token);
        emailMaster.send(userInfo.getEmail());
        resultInfo.setCode(ResultInfo.CODE_OK);
        resultInfo.setStatus(ResultInfo.STATUS_CREATED);
        resultInfo.setMessage("request successfully, check email and reset your password");
        return resultInfo;
    }

    @Transactional
    public String go(String token){
        String t = AESUtil.decrypt(token, AESUtil.KEY);
        String username = t.substring(13, t.length());
        if(!(userDao.countUsername(new UserInfo(username)) == 1)){
            throw new RuntimeException("user not exists");
        }
        return username;
    }

    @Transactional
    public ResultInfo update(UserInfo userInfo){
        ResultInfo resultInfo = new ResultInfo();
        System.out.println(userInfo);
        if(TextUtil.isEmpty(userInfo.getPassword())){
            throw new RuntimeException("password is empty");
        }
        if(userInfo.getPassword().length() < 6){
            throw new RuntimeException("password format error");
        }
        userDao.update(userInfo);
        resultInfo.setCode(ResultInfo.CODE_OK);
        resultInfo.setStatus(ResultInfo.STATUS_OK);
        resultInfo.setMessage("reset password successfully");
        return resultInfo;
    }

    @Transactional
    public ResultInfo validate(UserInfo userInfo){
        ResultInfo resultInfo = new ResultInfo();
        if(!(userDao.countUsername(userInfo)==1)){
            resultInfo.setCode(ResultInfo.CODE_UNAUTHORIZED);
            resultInfo.setStatus(ResultInfo.STATUS_UNAUTHORIZED);
            resultInfo.setMessage("user not exists ");
            return resultInfo;
        }
        UserInfo userInfo1 = userDao.selectOne(userInfo);
        if(!userInfo1.isStatus()){
            resultInfo.setCode(ResultInfo.CODE_UNAUTHORIZED);
            resultInfo.setStatus(ResultInfo.STATUS_UNAUTHORIZED);
            resultInfo.setMessage("user status error");
            return resultInfo;
        }
        resultInfo.setCode(ResultInfo.CODE_OK);
        resultInfo.setStatus(ResultInfo.STATUS_OK);
        resultInfo.setMessage("validate successfully");
        return resultInfo;
    }

    @Transactional
    public ResultInfo signOut(HttpServletRequest request, UserInfo userInfo){
        ResultInfo resultInfo = new ResultInfo();
        HttpSession session = SessionListener.getSession(userInfo.getUsername());
        if(session != null){
            session.invalidate();
        }
        resultInfo.setCode(ResultInfo.CODE_OK);
        resultInfo.setStatus(ResultInfo.STATUS_OK);
        resultInfo.setMessage("signout successfully");
        return resultInfo;
    }

    @Transactional
    public ResultInfo<UserInfo> updateIcon(UserInfo userInfo){
        ResultInfo<UserInfo> resultInfo = new ResultInfo<>();
        if(userInfo.getId() <=0 || TextUtil.isEmpty(userInfo.getIcon())){
            resultInfo.setCode(ResultInfo.CODE_INVALID);
            resultInfo.setStatus(ResultInfo.STATUS_INVALID);
            resultInfo.setMessage("missing upload parameters");
            return resultInfo;
        }
        userDao.updateIcon(userInfo);
        resultInfo.setCode(ResultInfo.CODE_OK);
        resultInfo.setStatus(ResultInfo.STATUS_OK);
        resultInfo.setMessage("upload successfully");
        resultInfo.setT(userDao.selectOneById(userInfo));
        return resultInfo;
    }

    @Transactional(readOnly = true)
    public UserInfo selectOne(UserInfo userInfo){
        return userDao.selectUserAndChannels(userInfo);
    }
}
