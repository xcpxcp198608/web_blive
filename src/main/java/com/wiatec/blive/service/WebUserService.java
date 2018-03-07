package com.wiatec.blive.service;

import com.wiatec.blive.common.result.EnumResult;
import com.wiatec.blive.common.result.ResultInfo;
import com.wiatec.blive.common.result.ResultMaster;
import com.wiatec.blive.common.result.XException;
import com.wiatec.blive.common.utils.TextUtil;
import com.wiatec.blive.common.utils.TokenUtil;
import com.wiatec.blive.orm.dao.ChannelDao;
import com.wiatec.blive.orm.dao.TokenDao;
import com.wiatec.blive.orm.dao.UserDao;
import com.wiatec.blive.orm.pojo.ChannelInfo;
import com.wiatec.blive.orm.pojo.TokenInfo;
import com.wiatec.blive.orm.pojo.UserInfo;
import com.wiatec.blive.rtmp.RtmpInfo;
import com.wiatec.blive.rtmp.RtmpMaster;
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
    private UserDao userDao;

    @Resource
    private TokenDao tokenDao;

    @Resource
    private ChannelDao channelDao;

    /**
     * web user sign in
     * 1. 验证用户名，密码，email状态
     * 2. 创建新的token并插入或更新到table of auth_token
     * 3. 访问live server获取用户的rtmp上传地址和播放地址等信息
     * 4. 设置session, 将用户名存入session
     * @param request HttpServletRequest
     * @param userInfo UserInfo
     * @return ResultInfo
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultInfo signIn(HttpServletRequest request, UserInfo userInfo){
        if (!(userDao.countByUsername(userInfo.getUsername()) == 1)) {
            throw new XException(EnumResult.ERROR_USERNAME_NOT_EXISTS);
        }
        if (userDao.countOne(userInfo) != 1) {
            throw new XException(EnumResult.ERROR_USERNAME_PASSWORD_NO_MATCH);
        }
        if(userDao.validateStatus(userInfo) != 1){
            throw new XException(EnumResult.ERROR_EMAIL_NO_ACTIVATE);
        }
        UserInfo userInfo1 = userDao.selectOne(userInfo);

        TokenInfo tokenInfo = new TokenInfo();
        tokenInfo.setToken(TokenUtil.create32(userInfo.getUsername(),System.currentTimeMillis() + "") +
                TokenUtil.create32(userInfo.getUsername(), System.currentTimeMillis() + ""));
        tokenInfo.setUserId(userInfo1.getId());
        if(tokenDao.countByUserId(tokenInfo)==1){
            tokenDao.updateOne(tokenInfo);
        }else {
            tokenDao.insertOne(tokenInfo);
        }
        tokenInfo = tokenDao.selectOneByUserId(userInfo1.getId());
        HttpSession session = request.getSession();
        session.setAttribute("username", userInfo1.getUsername());
        return ResultMaster.success(tokenInfo);
    }

    /**
     * get user all information by the username from session
     * @param request HttpServletRequest
     * @return UserInfo
     */
    public UserInfo getUserInfo(HttpServletRequest request){
        String username = (String) request.getSession().getAttribute("username");
        if(TextUtil.isEmpty(username)){
            throw new RuntimeException("sign in info error");
        }
        return userDao.selectOneWithChannel(new UserInfo(username));
    }


}
