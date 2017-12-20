package com.wiatec.blive.api;

import com.wiatec.blive.entity.ResultInfo;
import com.wiatec.blive.orm.pojo.ChannelInfo;
import com.wiatec.blive.orm.pojo.UserInfo;
import com.wiatec.blive.service.ChannelService;
import com.wiatec.blive.service.TokenService;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;

import static com.wiatec.blive.instance.Constant.BASE_RESOURCE_URL;

@Controller
@RequestMapping(value = "/channel")
public class Channel {

    @Resource
    private ChannelService channelService;

    @Resource
    private TokenService tokenService;

    @RequestMapping(value = "/")
    public @ResponseBody List<ChannelInfo> get(){
        return channelService.selectAllAvailable();
    }

    @PutMapping("/update")
    public @ResponseBody ResultInfo<ChannelInfo> updateChannel(@RequestBody ChannelInfo channelInfo){
        return channelService.updateChannel(channelInfo);
    }

    @PutMapping("/title")
    public @ResponseBody ResultInfo<ChannelInfo> updateChannelTitle(@RequestBody ChannelInfo channelInfo){
        return channelService.updateChannelTitle(channelInfo);
    }

    @PutMapping("/title1")
    public @ResponseBody ResultInfo<ChannelInfo> updateChannelTitle1(@RequestBody ChannelInfo channelInfo){
        return channelService.updateChannelTitle1(channelInfo);
    }

    @PutMapping("/message")
    public @ResponseBody ResultInfo<ChannelInfo> updateChannelMessage(@RequestBody ChannelInfo channelInfo){
        return channelService.updateChannelMessage(channelInfo);
    }

    @PutMapping("/price")
    public @ResponseBody ResultInfo<ChannelInfo> updateChannelPrice(@RequestBody ChannelInfo channelInfo){
        return channelService.updateChannelPrice(channelInfo);
    }

    @PutMapping("/status/{activate}/{userId}")
    public @ResponseBody ResultInfo<ChannelInfo> updateChannelUnavailable(@PathVariable int activate,
                                                                          @PathVariable int userId){
        return channelService.updateChannelStatus(activate, userId);
    }

    @PostMapping("/upload/{userId}")
    public @ResponseBody ResultInfo<ChannelInfo> uploadPreviewImage(@PathVariable int userId,
                                                                    @RequestParam MultipartFile file,
                                                                    HttpServletRequest request){
        ResultInfo<ChannelInfo> resultInfo = new ResultInfo<>();
        resultInfo.setCode(ResultInfo.CODE_INVALID);
        resultInfo.setStatus(ResultInfo.STATUS_INVALID);
        resultInfo.setMessage("upload failure");
        try{
            if(!file.isEmpty()){
                String path = request.getSession().getServletContext().getRealPath("/Resource/channel_preview/");
                FileUtils.copyInputStreamToFile(file.getInputStream(), new File( path,  file.getOriginalFilename()));
                String preview = BASE_RESOURCE_URL + "channel_preview/" + file.getOriginalFilename();
                return channelService.updatePreview(new ChannelInfo(preview, userId));
            }
        }catch (Exception e){
            return resultInfo;
        }
        return resultInfo;
    }
}
