package com.wiatec.blive.api;

import com.wiatec.blive.common.result.ResultInfo;
import com.wiatec.blive.common.result.XException;
import com.wiatec.blive.orm.pojo.UserInfo;
import com.wiatec.blive.service.UserService;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

import static com.wiatec.blive.instance.Constant.BASE_RESOURCE_URL;

/**
 * @author patrick
 */
@Controller
@RequestMapping(value = "/user")
public class User {

    @Resource
    private UserService userService;

    @PostMapping("/signup")
    @ResponseBody
    public ResultInfo<UserInfo> signUp(HttpServletRequest request, UserInfo userInfo){
        return userService.signUp(request, userInfo);
    }

    @RequestMapping(value = "/activate/{token}")
    public String activate(@PathVariable String token, Model model){
        model.addAttribute("message", userService.activate(token));
        return "notice";
    }

    @PostMapping("/signin")
    @ResponseBody
    public ResultInfo signIn(UserInfo userInfo){
        return userService.signIn(userInfo);
    }

    @PostMapping("/validate")
    @ResponseBody
    public ResultInfo validate(UserInfo userInfo){
        return userService.validate(userInfo);
    }

    @PostMapping("/reset")
    @ResponseBody
    public ResultInfo resetPassword(HttpServletRequest request, UserInfo userInfo){
        System.out.println(userInfo);
        return userService.reset(request, userInfo);
    }

    @RequestMapping(value = "/go/{token}")
    public String goUpdatePage(Model model, @PathVariable String token){
        String username =  userService.go(token);
        model.addAttribute("username", username);
        return "go";
    }

    /**
     * 通过邮箱链接修改password
     * @param userInfo required: user id , new password
     * @param model Model
     * @return jsp -> notice.jsp
     */
    @PostMapping("/update")
    public String updatePassword(UserInfo userInfo, Model model){
        ResultInfo resultInfo = userService.update(userInfo);
        model.addAttribute("message", resultInfo.getMessage());
        return "notice";
    }

    /**
     * app内直接修改password
     * @param userId user id
     * @param oldPassword old password
     * @param newPassword new password
     * @return ResultInfo
     */
    @PostMapping("/update/{userId}")
    @ResponseBody
    public ResultInfo updatePassword(@PathVariable int userId, String oldPassword, String newPassword){
        System.out.println(userId);
        System.out.println(oldPassword);
        System.out.println(newPassword);
        return userService.updateByOldPassword(userId, oldPassword, newPassword);
    }

    @PostMapping("/signout")
    @ResponseBody
    public ResultInfo signOut(UserInfo userInfo){
        return userService.signOut(userInfo);
    }

    @PostMapping("/upload/{userId}")
    @ResponseBody
    public ResultInfo<UserInfo> uploadIcon(@RequestParam MultipartFile file, @PathVariable int userId,
                                    HttpServletRequest request) throws IOException {
        if(file.isEmpty()){
            throw new XException("icon error");
        }
        String path = request.getSession().getServletContext().getRealPath("/Resource/icon/");
        FileUtils.copyInputStreamToFile(file.getInputStream(), new File( path,  file.getOriginalFilename()));
        String icon = BASE_RESOURCE_URL + "icon/" + file.getOriginalFilename();
        return userService.updateIcon(new UserInfo(userId, icon));
    }

    @PostMapping("/follows/{userId}")
    @ResponseBody
    public ResultInfo<UserInfo> follows(@PathVariable int userId){
        return userService.follows(userId);
    }

    @PostMapping("/follow/{action}/{userId}/{friendId}")
    @ResponseBody
    public ResultInfo follow(@PathVariable int action, @PathVariable int userId, @PathVariable int friendId){
        return userService.follow(action, userId, friendId);
    }


    @PostMapping("/{userId}")
    @ResponseBody
    public UserInfo get(@PathVariable int userId){
        return userService.selectOneWithChannelByUserId(userId);
    }

}
