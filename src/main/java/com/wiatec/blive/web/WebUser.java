package com.wiatec.blive.web;

import com.wiatec.blive.common.result.ResultInfo;
import com.wiatec.blive.orm.pojo.AuthRegisterUserInfo;
import com.wiatec.blive.orm.pojo.ChannelInfo;
import com.wiatec.blive.orm.pojo.LiveChannelInfo;
import com.wiatec.blive.service.LiveChannelService;
import com.wiatec.blive.service.WebUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author patrick
 */
@Controller
@RequestMapping(value = "/users")
public class WebUser {

    @Resource
    private WebUserService webUserService;
    @Resource
    private LiveChannelService liveChannelService;

    @PostMapping(value = "/signin")
    @ResponseBody
    public ResultInfo signIn(HttpServletRequest request, String username, String password){
        return webUserService.signIn(request, username, password);
    }

    @GetMapping(value = "/home")
    public String details(HttpServletRequest request, Model model){
        AuthRegisterUserInfo userInfo = webUserService.getUserInfo(request);
        model.addAttribute("userInfo", userInfo);
        ChannelInfo channelInfo = liveChannelService.selectOneByUserId(userInfo.getId()).getData();
        model.addAttribute("channelInfo", channelInfo);
        return "webuser/home";
    }

}
