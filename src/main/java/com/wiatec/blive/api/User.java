package com.wiatec.blive.api;

import com.wiatec.blive.common.result.EnumResult;
import com.wiatec.blive.common.result.ResultInfo;
import com.wiatec.blive.common.result.XException;
import com.wiatec.blive.orm.pojo.AuthRegisterUserInfo;
import com.wiatec.blive.orm.pojo.FeedbackInfo;
import com.wiatec.blive.service.AuthRegisterUserService;
import com.wiatec.blive.service.BlackListService;
import com.wiatec.blive.service.FeedbackService;
import com.wiatec.blive.service.LogUserOperationService;
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
    private AuthRegisterUserService authRegisterUserService;
    @Resource
    private FeedbackService feedbackService;
    @Resource
    private BlackListService blackListService;
    @Resource
    private LogUserOperationService logUserOperationService;

    /**
     * sign up
     * @param request HttpServletRequest
     * @param userInfo required: username, password, email, phone
     * @return ResultInfo
     */
    @PostMapping("/signup")
    @ResponseBody
    public ResultInfo signUp(HttpServletRequest request, AuthRegisterUserInfo userInfo){
        return authRegisterUserService.signUp(request, userInfo);
    }

    /**
     * activate by email link
     * @param token token
     * @param model Model
     * @return jsp -> notice.jsp
     */
    @RequestMapping(value = "/activate/{token}")
    public String activate(@PathVariable String token, Model model){
        model.addAttribute("message", authRegisterUserService.activate(token));
        return "notice";
    }

    /**
     * sign in
     * required: username password
     * optional: country, region, city, timeZone, deviceModel, romVersion, uiVersion
     * @return ResultInfo
     */
    @PostMapping("/signin")
    @ResponseBody
    public ResultInfo signIn(AuthRegisterUserInfo authRegisterUserInfo){
        return authRegisterUserService.signIn(authRegisterUserInfo);
    }

    /**
     * validate access token
     * @param token token
     * @return ResultInfo
     */
    @PostMapping("/validate/{userId}/{token}")
    @ResponseBody
    public ResultInfo validate(@PathVariable int userId, @PathVariable String token){
        return authRegisterUserService.validateToken(userId, token);
    }

    /**
     * request update password in app
     * @param request HttpServletRequest
     * @param username username
     * @param email email
     * @return ResultInfo
     */
    @PostMapping("/reset")
    @ResponseBody
    public ResultInfo resetPassword(HttpServletRequest request, String username, String email){
        return authRegisterUserService.reset(request, username, email);
    }


    /**
     * show update password web page after click email link
     * @param model model
     * @param token token
     * @return go.jsp
     */
    @RequestMapping(value = "/go/{token}")
    public String goUpdatePage(Model model, @PathVariable String token){
        String username =  authRegisterUserService.go(token);
        model.addAttribute("username", username);
        return "go";
    }

    /**
     * change password by email link
     * @param model Model
     * @return jsp -> notice.jsp
     */
    @PostMapping("/update")
    public String updatePassword(String username, String password, Model model){
        ResultInfo resultInfo = authRegisterUserService.updatePasswordByUsername(username, password);
        model.addAttribute("message", resultInfo.getMessage());
        return "notice";
    }

    /**
     * update password by user id and old password
     * @param userId user id
     * @param oldPassword old password
     * @param newPassword new password
     * @return ResultInfo
     */
    @PostMapping("/update/{userId}")
    @ResponseBody
    public ResultInfo updatePassword(String oldPassword, String newPassword, @PathVariable int userId){
        return authRegisterUserService.updateByOldPassword(userId, oldPassword, newPassword);
    }

    @PostMapping("/signout")
    @ResponseBody
    public ResultInfo signOut(String username){
        return authRegisterUserService.signOut(username);
    }

    /**
     * upload user icon image
     * @param file image file
     * @param userId user id
     * @param request HttpServletRequest
     * @return ResultInfo
     * @throws IOException IOException
     */
    @PostMapping("/upload/{userId}")
    @ResponseBody
    public ResultInfo uploadIcon(@RequestParam MultipartFile file, @PathVariable int userId,
                                    HttpServletRequest request) throws IOException {
        if(file.isEmpty()){
            throw new XException("icon error");
        }
        String path = request.getSession().getServletContext().getRealPath("/Resource/icon/");
        FileUtils.copyInputStreamToFile(file.getInputStream(), new File( path,  file.getOriginalFilename()));
        String icon = BASE_RESOURCE_URL + "icon/" + file.getOriginalFilename();
        return authRegisterUserService.updateIcon(icon, userId);
    }


    /**
     *  get 2 user follow status
     * @param userId user id
     * @param friendId target user id
     * @return ResultInfo
     */
    @GetMapping("/follow/status/{userId}/{friendId}")
    @ResponseBody
    public ResultInfo follow(@PathVariable int userId, @PathVariable int friendId){
        return authRegisterUserService.followStatus(userId, friendId);
    }

    /**
     *  add or delete a follow relationship
     * @param action 0-> delete, 1-> add
     * @param userId user id
     * @param friendId target user id
     * @return ResultInfo
     */
    @PostMapping("/follow/{action}/{userId}/{friendId}")
    @ResponseBody
    public ResultInfo follow(@PathVariable int action, @PathVariable int userId, @PathVariable int friendId){
        return authRegisterUserService.follow(action, userId, friendId);
    }


    /**
     *  list of all follows by user id
     * @param userId use id
     * @return ResultInfo with list of userInfo
     */
    @GetMapping("/follows/{userId}")
    @ResponseBody
    public ResultInfo follows(@PathVariable int userId){
        return authRegisterUserService.follows(userId);
    }


    /**
     * list all of user's followers
     * @param userId user id
     * @return ResultInfo
     */
    @GetMapping("/followers/{userId}")
    @ResponseBody
    public ResultInfo getFollowers(@PathVariable int userId){
        return authRegisterUserService.getFollowers(userId);
    }

    /**
     * get user info with channel info by user id
     * @param userId user id
     * @return ResultInfo
     */
    @PostMapping("/{userId}")
    @ResponseBody
    public ResultInfo<AuthRegisterUserInfo> get(@PathVariable int userId){
        return authRegisterUserService.selectOneByUserId(userId);
    }

    /**
     * submit feedback
     * @param feedbackInfo feedbackInfo
     * @return ResultInfo
     */
    @PostMapping("/feedback")
    @ResponseBody
    public ResultInfo feedback(FeedbackInfo feedbackInfo){
        return feedbackService.insertOne(feedbackInfo);
    }


    /**
     * list all black list by user id
     * @param userId user id
     * @return ResultInfo
     */
    @GetMapping("/blacks/{userId}")
    @ResponseBody
    public ResultInfo getBlackList(@PathVariable int userId){
        return blackListService.listAll(userId);
    }


    /**
     * check target user does in user's black list
     * @param userId user id
     * @param targetUserId target user id
     * @return ResultInfo
     */
    @GetMapping("/black/{userId}/{targetUserId}")
    @ResponseBody
    public ResultInfo checkBlack(@PathVariable int userId, @PathVariable int targetUserId){
        return blackListService.checkBlack(userId, targetUserId);
    }


    /**
     * user set black list
     * @param action 1-> add to, 0-> remove
     * @param userId user id
     * @param username target user name
     * @return  ResultInfo
     */
    @PostMapping("/black/{action}/{userId}")
    @ResponseBody
    public ResultInfo addBlackList(@PathVariable int action, @PathVariable int userId, String username){
        if(action == 1){
            return blackListService.insertOne(userId, username);
        }else if(action == 0){
            return blackListService.deleteOne(userId, username);
        }else{
            throw new XException(EnumResult.ERROR_BAD_REQUEST);
        }
    }

    /**
     * get all of user operation recorder
     * @param userId user id
     * @return ResultInfo
     */
    @GetMapping("/operations/{userId}")
    @ResponseBody
    public ResultInfo getOperations(@PathVariable int userId){
        return logUserOperationService.getOperations(userId);
    }


    /**
     * set user gender
     * @param userId user id
     * @param gender gender
     * @return ResultInfo
     */
    @PutMapping("/gender/{userId}/{gender}")
    @ResponseBody
    public ResultInfo setGender(@PathVariable int userId, @PathVariable int gender){
        return authRegisterUserService.updateGender(userId, gender);
    }


    /**
     * set user profile
     * @param userId user id
     * @param profile profile
     * @return ResultInfo
     */
    @PutMapping("/profile/{userId}")
    @ResponseBody
    public ResultInfo setProfile(@PathVariable int userId, String profile){
        return authRegisterUserService.updateProfile(userId, profile);
    }
}
