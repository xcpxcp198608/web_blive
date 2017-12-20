package com.wiatec.blive.web;

import com.wiatec.blive.entity.ResultInfo;
import com.wiatec.blive.orm.pojo.UserInfo;
import com.wiatec.blive.service.WebUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "/users")
public class WebUser {

    @Resource
    private WebUserService webUserService;

    @GetMapping(value = "/signin")
    @ResponseBody
    public ResultInfo signIn(HttpServletRequest request, HttpServletResponse response, UserInfo userInfo){
        return webUserService.signIn(request, response, userInfo);
    }

    @GetMapping(value = "/details")
    public String details(HttpServletRequest request, Model model){
        UserInfo userInfo = webUserService.details(request);
        model.addAttribute("userInfo", userInfo);
        return "webuser/details";
    }
}
