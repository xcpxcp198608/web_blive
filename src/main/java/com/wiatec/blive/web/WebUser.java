package com.wiatec.blive.web;

import com.wiatec.blive.entity.ResultInfo;
import com.wiatec.blive.orm.pojo.UserInfo;
import com.wiatec.blive.service.UsersService;
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
    private UsersService usersService;

    @GetMapping(value = "/signin")
    @ResponseBody
    public ResultInfo signIn(HttpServletRequest request, HttpServletResponse response, UserInfo userInfo){
        return usersService.signIn(request, response, userInfo);
    }

    @GetMapping(value = "/details")
    public String details(HttpServletRequest request, Model model){
        UserInfo userInfo = usersService.details(request);
        model.addAttribute("userInfo", userInfo);
        return "webuser/details";
    }
}
