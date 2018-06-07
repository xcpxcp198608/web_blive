package com.wiatec.blive.api;

import com.github.pagehelper.PageInfo;
import com.wiatec.blive.Constant;
import com.wiatec.blive.common.result.ResultInfo;
import com.wiatec.blive.common.result.XException;
import com.wiatec.blive.orm.dao.LdIllegalWordDao;
import com.wiatec.blive.orm.pojo.ChannelInfo;
import com.wiatec.blive.service.LiveChannelService;
import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;


/**
 * @author patrick
 */
@RestController
@RequestMapping(value = "/channel")
public class LiveChannel {

    @Resource
    private LiveChannelService liveChannelService;
    @Resource
    private LdIllegalWordDao ldIllegalWordDao;

    @GetMapping(value = "/")
    public ResultInfo<PageInfo<ChannelInfo>> getWithUserInfo(@RequestParam(required = false, defaultValue = "1") int pageNum,
                                                                 @RequestParam(required = false, defaultValue = "50") int pageSize){
        return liveChannelService.selectAllAvailableWithUser(pageNum, pageSize);
    }


    @GetMapping(value = "/btv")
    public ResultInfo<ChannelInfo> getChannelWithUserInfoForBtv(){
        return liveChannelService.selectAllAvailableForBtv();
    }

    @PostMapping(value = "/create")
    public ResultInfo create(int userId, String username){
        return liveChannelService.create(userId, username);
    }

    @GetMapping(value = "/{userId}")
    public ResultInfo getLiveChannel(@PathVariable int userId){
        return liveChannelService.selectOneByUserId(userId);
    }

    @GetMapping(value = "/search/{key}")
    public List<ChannelInfo> searchByLikeTitle(@PathVariable String key){
        return liveChannelService.searchByLikeTitle(key);
    }


    /**
     *  update channel setting
     * @param action 0-> all, 1-> title and message, 2->title, 3-> message, 4-> price
     * @param channelInfo channelInfo
     * @return ResultInfo
     */
    @PutMapping("/update/{action}")
    public ResultInfo updateChannel(@PathVariable int action, @RequestBody ChannelInfo channelInfo){
        List<String> words = ldIllegalWordDao.selectAll();
        if(words != null && words.size() > 0){
            for (String s: words) {
                if(channelInfo.getTitle().contains(s)){
                    throw new XException("title contains illegal word");
                }
                if(channelInfo.getMessage().contains(s)){
                    throw new XException("content contains illegal word");
                }
            }
        }
        return liveChannelService.updateChannel(action, channelInfo);
    }


    @PutMapping("/status/{action}/{userId}")
    public ResultInfo updateChannelUnavailable(@PathVariable int action, @PathVariable int userId){
        return liveChannelService.updateChannelStatus(action, userId);
    }


    @PostMapping("/upload/{userId}")
    public ResultInfo<ChannelInfo> uploadPreviewImage(@PathVariable int userId,
                                                      @RequestParam MultipartFile file,
                                                      HttpServletRequest request) throws IOException {
        if (file.isEmpty()) {
            throw new XException("icon error");
        }
        String path = request.getSession().getServletContext().getRealPath("/Resource/channel_preview/");
        File localFile = new File(path, file.getOriginalFilename());
        FileUtils.copyInputStreamToFile(file.getInputStream(), localFile);
        String preview = Constant.url.BASE_RESOURCE + "channel_preview/" + file.getOriginalFilename();
        ChannelInfo channelInfo = new ChannelInfo();
        channelInfo.setUserId(userId);
        channelInfo.setPreview(preview);
        return liveChannelService.updatePreview(channelInfo);
    }

    @GetMapping("/verify/{channelId}/{viewerId}")
    public ResultInfo verifyViewPermission(@PathVariable int channelId, @PathVariable int viewerId){
        return liveChannelService.checkViewPermission(channelId, viewerId);
    }

    @PostMapping("/purchase/{channelId}/{viewerId}/{duration}/{coins}")
    public ResultInfo purchasePermission(@PathVariable int channelId, @PathVariable int viewerId,
                                         @PathVariable int duration, @PathVariable int coins,
                                         String platform){
        return liveChannelService.purchasePermission(channelId, viewerId, duration, coins, platform);
    }
}
