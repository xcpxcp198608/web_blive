package com.wiatec.blive.service;

import com.wiatec.blive.common.data_source.DataSource;
import com.wiatec.blive.common.data_source.DataSourceHolder;
import com.wiatec.blive.common.result.EnumResult;
import com.wiatec.blive.common.result.ResultInfo;
import com.wiatec.blive.common.result.ResultMaster;
import com.wiatec.blive.common.result.XException;
import com.wiatec.blive.common.utils.TextUtil;
import com.wiatec.blive.common.utils.TokenUtil;
import com.wiatec.blive.orm.dao.AuthRegisterUserDao;
import com.wiatec.blive.orm.dao.TokenDao;
import com.wiatec.blive.orm.pojo.AuthRegisterUserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author patrick
 */
@Service
public class WebUserService {

    private Logger logger = LoggerFactory.getLogger(WebUserService.class);

    @Resource
    private AuthRegisterUserDao authRegisterUserDao;

    /**
     * web user sign in
     * 1. 验证用户名，密码，email状态
     * 2. 创建新的token并插入或更新到table of auth_token
     * 3. 访问live server获取用户的rtmp上传地址和播放地址等信息
     * 4. 设置session, 将用户名存入session
     * @param request HttpServletRequest
     * @return ResultInfo
     */

    @Transactional(rollbackFor = Exception.class)
    public ResultInfo signIn(HttpServletRequest request, String username, String password){
        if (authRegisterUserDao.countByUsername(username) != 1) {
            throw new XException(EnumResult.ERROR_USERNAME_NOT_EXISTS);
        }
        if (authRegisterUserDao.countOneByUsernameAndPassowrd(username, password) != 1) {
            throw new XException(EnumResult.ERROR_USERNAME_PASSWORD_NO_MATCH);
        }
        if(authRegisterUserDao.selectEmailStatusByUsername(username) != 1){
            throw new XException(EnumResult.ERROR_EMAIL_NO_ACTIVATE);
        }
        String token = TokenUtil.create64(username);
        authRegisterUserDao.updateTokenByUsername(username, token);
        AuthRegisterUserInfo userInfo1 = authRegisterUserDao.selectOneByUsername(username);
        HttpSession session = request.getSession();
        session.setAttribute("username", userInfo1.getUsername());
        return ResultMaster.success(userInfo1);
    }

    /**
     * get user all information by the username from session
     * @param request HttpServletRequest
     * @return UserInfo
     */
    public AuthRegisterUserInfo getUserInfo(HttpServletRequest request){
        String username = (String) request.getSession().getAttribute("username");
        if(TextUtil.isEmpty(username)){
            throw new RuntimeException("sign in info error");
        }
        return authRegisterUserDao.selectOneByUsername(username);
    }


}
