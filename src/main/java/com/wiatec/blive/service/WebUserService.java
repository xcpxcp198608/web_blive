package com.wiatec.blive.service;

import com.wiatec.blive.common.utils.TextUtil;
import com.wiatec.blive.common.utils.TokenUtil;
import com.wiatec.blive.entity.ResultInfo;
import com.wiatec.blive.listener.SessionListener;
import com.wiatec.blive.orm.dao.ChannelDao;
import com.wiatec.blive.orm.dao.TokenDao;
import com.wiatec.blive.orm.dao.UserDao;
import com.wiatec.blive.orm.pojo.ChannelInfo;
import com.wiatec.blive.orm.pojo.TokenInfo;
import com.wiatec.blive.orm.pojo.UserInfo;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

@Service
public class WebUserService {

    private Logger logger = LoggerFactory.getLogger(WebUserService.class);

    @Resource
    private UserDao userDao;

    @Resource
    private TokenDao tokenDao;

    @Resource
    private ChannelDao channelDao;

    @Transactional
    public ResultInfo signIn(HttpServletRequest request, HttpServletResponse response,  UserInfo userInfo){
        ResultInfo<TokenInfo> resultInfo = new ResultInfo<>();
        try {
            if (!(userDao.countUsername(userInfo) == 1)) {
                resultInfo.setCode(ResultInfo.CODE_UNAUTHORIZED);
                resultInfo.setStatus(ResultInfo.STATUS_UNAUTHORIZED);
                resultInfo.setMessage("username not exists ");
                return resultInfo;
            }
            if (userDao.countOne(userInfo) == 1) {
                if(userDao.validateStatus(userInfo) != 1){
                    resultInfo.setCode(ResultInfo.CODE_UNAUTHORIZED);
                    resultInfo.setStatus(ResultInfo.STATUS_UNAUTHORIZED);
                    resultInfo.setMessage("email not activated");
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
                HttpSession session = request.getSession();
                session.setAttribute("username", userInfo1.getUsername());
                response.addCookie(new Cookie("JSESSIONID", session.getId()));
                setRtmpAddress(userInfo1);
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
            logger.debug(e.getMessage());
            resultInfo.setCode(ResultInfo.CODE_SERVER_ERROR);
            resultInfo.setStatus(ResultInfo.STATUS_SERVER_ERROR);
            resultInfo.setMessage("Signin error");
            return resultInfo;
        }
    }

    private void setRtmpAddress(UserInfo userInfo) {
//        String base = "http://apildlive.protv.company/v1/blive_get_url.do";
        String base = "http://apilive.bvision.live/v1/blive_get_url.do";
        String url = base + "?username="+userInfo.getUsername()+"&token=36d4284ce8e188eb75bda72cb1de28c7";
        String rtmpUrl = "1";
        String playUrl = "1";
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        StringBuilder builder = new StringBuilder();
        try {
            URL url1 = new URL(url);
            URLConnection urlConnection = url1.openConnection();
            inputStream = urlConnection.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            while((line = bufferedReader.readLine()) != null){
                builder.append(line);
            }
            String result =  builder.toString();
            System.out.println(result);
            JSONObject jsonObject = new JSONObject(result);
            rtmpUrl = jsonObject.getJSONObject("data").getString("push_full_url");
            playUrl = jsonObject.getJSONObject("data").getString("play_url");
        } catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            try {
                if(bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                logger.debug(e.getMessage());
            }
            try {
                if(inputStream != null){
                    inputStream.close();
                }
            } catch (IOException e) {
                logger.debug(e.getMessage());
            }
        }
        ChannelInfo channelInfo = new ChannelInfo();
        channelInfo.setTitle(userInfo.getUsername());
        channelInfo.setUserId(userInfo.getId());
        channelInfo.setUrl(rtmpUrl);
        channelInfo.setPlayUrl(playUrl);
        if(channelDao.countUserId(channelInfo) == 1){
            channelDao.updateChannel(channelInfo);
        }else{
//            channelInfo.setTitle(userDao.selectOneById(new UserInfo(channelInfo.getUserId())).getUsername());
            channelDao.insertChannel(channelInfo);
        }
    }

    @Transactional
    public UserInfo details(HttpServletRequest request){
        Cookie [] cookies = request.getCookies();
        String username = "";
        for(Cookie cookie: cookies){
            if("JSESSIONID".equals(cookie.getName())){
                username = (String) SessionListener.getIdSession(cookie.getValue()).getAttribute("username");
            }
        }
        if(TextUtil.isEmpty(username)){
            throw new RuntimeException("signin info error");
        }
        return userDao.selectOneWithChannel(new UserInfo(username));
    }


}
