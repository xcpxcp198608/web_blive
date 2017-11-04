package com.wiatec.blive.api;

import com.wiatec.blive.entity.ResultInfo;
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

@Controller
@RequestMapping(value = "/user")
public class User {

    @Resource
    private UserService userService;

    @PostMapping("/signup")
    public @ResponseBody ResultInfo<UserInfo> signUp(HttpServletRequest request,
                                           @RequestBody UserInfo userInfo){
        return userService.signUp(request, userInfo);
    }

    @RequestMapping(value = "/activate/{token}")
    public String activate(@PathVariable String token, Model model){
        model.addAttribute("message", userService.activate(token));
        return "notice";
    }

    @PostMapping("/signin")
    public @ResponseBody ResultInfo signIn(@RequestBody UserInfo userInfo){
        return userService.signIn(userInfo);
    }

    @PostMapping("/validate")
    public @ResponseBody ResultInfo validate(@RequestBody UserInfo userInfo){
        return userService.validate(userInfo);
    }

    @PostMapping("/reset")
    public @ResponseBody ResultInfo resetPassword(HttpServletRequest request,
                                    @RequestBody UserInfo userInfo){
        return userService.reset(request, userInfo);
    }

    @RequestMapping(value = "/go/{token}")
    public String updatePassword(HttpServletRequest request,
                                 Model model,
                                 @PathVariable String token){
        String username =  userService.go(token);
        model.addAttribute("username", username);
        return "go";
    }

    @PostMapping("/update")
    public @ResponseBody ResultInfo updatePassword(HttpServletRequest request,
                                     @ModelAttribute UserInfo userInfo){
        return userService.update(userInfo);
    }

    @PostMapping("/signout")
    public @ResponseBody ResultInfo signOut(HttpServletRequest request,
                                            @RequestBody UserInfo userInfo){
        return userService.signOut(request, userInfo);
    }

    @PostMapping("/upload/{userId}")
    public @ResponseBody ResultInfo<UserInfo> uploadIcon(@RequestParam MultipartFile file,
                                    @PathVariable int userId,
                                    HttpServletRequest request){
        ResultInfo<UserInfo> resultInfo = new ResultInfo<>();
        resultInfo.setCode(ResultInfo.CODE_INVALID);
        resultInfo.setStatus(ResultInfo.STATUS_INVALID);
        resultInfo.setMessage("upload failure");
        try{
            if(!file.isEmpty()){
                String path = request.getSession().getServletContext().getRealPath("/Resource/icon/");
                FileUtils.copyInputStreamToFile(file.getInputStream(), new File( path,  file.getOriginalFilename()));
                String icon = "http://blive.protv.company:8804/web/Resource/icon/" + file.getOriginalFilename();
                return userService.updateIcon(new UserInfo(userId, icon));
            }
        }catch (Exception e){
            return resultInfo;
        }
        return resultInfo;
    }

    @PostMapping("/{userId}")
    public @ResponseBody UserInfo get(@PathVariable int userId){
        return userService.selectOne(new UserInfo(userId));
    }

}
