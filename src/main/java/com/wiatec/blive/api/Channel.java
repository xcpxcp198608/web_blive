package com.wiatec.blive.api;

import com.wiatec.blive.common.result.EnumResult;
import com.wiatec.blive.common.result.ResultInfo;
import com.wiatec.blive.common.result.ResultMaster;
import com.wiatec.blive.common.result.XException;
import com.wiatec.blive.orm.pojo.AuthRegisterUserInfo;
import com.wiatec.blive.orm.pojo.ChannelInfo;
import com.wiatec.blive.service.AuthRegisterUserService;
import com.wiatec.blive.service.ChannelService;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.wiatec.blive.instance.Constant.BASE_RESOURCE_URL;

/**
 * @author patrick
 */
@Controller
@RequestMapping(value = "/channel")
public class Channel {

    private final String[] FILTER_KEYWORD = {"CEO", "CTO", "COO", "fuck", "shit", "dick", "pussy",
            "mother fucker", "fucker", "president", "Vice President", "FUCK", "SHIT", "DICK",
            "PUSSY", "MOTHER FUCKER", "FUCKER", "PRESIDENT", "President"};


    @Resource
    private AuthRegisterUserService authRegisterUserService;
    @Resource
    private ChannelService channelService;

    @RequestMapping(value = "/")
    @ResponseBody
    public List<ChannelInfo> get(){
        return channelService.selectAllAvailable();
    }

    @PostMapping(value = "/create")
    @ResponseBody
    public ResultInfo create(int userId, String username){
        return channelService.create(userId, username);
    }

    @GetMapping(value = "/{userId}")
    @ResponseBody
    public ResultInfo getChannel(@PathVariable int userId){
        return channelService.selectOneByUserId(userId);
    }

    @GetMapping(value = "/search/{key}")
    @ResponseBody
    public List<ChannelInfo> searchByLikeTitle(@PathVariable String key){
        return channelService.searchByLikeTitle(key);
    }

    @PutMapping("/update")
    @ResponseBody
    public ResultInfo<ChannelInfo> updateChannelUrl(@RequestBody ChannelInfo channelInfo){
        ResultInfo<AuthRegisterUserInfo> resultInfo = authRegisterUserService.selectOneByUserId(channelInfo.getUserId());
        return channelService.updateChannelUrl(resultInfo.getData().getUsername(), channelInfo);
    }

    /**
     *  update channel setting
     * @param action 0-> all, 1-> title and message, 2->title, 3-> message, 4-> price
     * @param channelInfo channelInfo
     * @return ResultInfo
     */
    @PutMapping("/update/{action}")
    @ResponseBody
    public ResultInfo updateChannel(@PathVariable int action, @RequestBody ChannelInfo channelInfo){
        for (String s: FILTER_KEYWORD) {
            if(channelInfo.getTitle().contains(s)){
                throw new XException("title contains not allow word");
            }
            if(channelInfo.getMessage().contains(s)){
                throw new XException("content contains not allow word");
            }
        }
        if(action == 0) {
            return channelService.updateChannelAllSetting(channelInfo);
        }
        if(action == 1){
            return channelService.updateChannelTitleAndMessage(channelInfo);
        }
        if(action == 2){
            return channelService.updateChannelTitle(channelInfo);
        }
        if(action == 3){
            return channelService.updateChannelMessage(channelInfo);
        }
        if(action == 4){
            return channelService.updateChannelPrice(channelInfo);
        }
        return ResultMaster.error(1001, "update failure");
    }

    @PutMapping("/status/{action}/{userId}")
    @ResponseBody
    public ResultInfo<ChannelInfo> updateChannelUnavailable(@PathVariable int action, @PathVariable int userId){
        return channelService.updateChannelStatus(action, userId);
    }

    @PostMapping("/upload/{userId}")
    @ResponseBody
    public ResultInfo<ChannelInfo> uploadPreviewImage(@PathVariable int userId,
                                                      @RequestParam MultipartFile file,
                                                      HttpServletRequest request) throws IOException {
        if(file.isEmpty()){
            throw new XException("icon error");
        }
        String path = request.getSession().getServletContext().getRealPath("/Resource/channel_preview/");
        FileUtils.copyInputStreamToFile(file.getInputStream(), new File( path,  file.getOriginalFilename()));
        String preview = BASE_RESOURCE_URL + "channel_preview/" + file.getOriginalFilename();
        return channelService.updatePreview(new ChannelInfo(preview, userId));
    }
}
