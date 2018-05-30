package com.wiatec.blive.api;

import com.github.pagehelper.PageInfo;
import com.wiatec.blive.common.result.ResultInfo;
import com.wiatec.blive.common.result.XException;
import com.wiatec.blive.orm.dao.LdIllegalWordDao;
import com.wiatec.blive.orm.pojo.ChannelInfo;
import com.wiatec.blive.orm.pojo.VodChannelInfo;
import com.wiatec.blive.service.VodChannelService;
import org.apache.commons.io.FileUtils;
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
@RestController
@RequestMapping(value = "/vod")
public class VodChannel {

    @Resource
    private VodChannelService vodChannelService;
    @Resource
    private LdIllegalWordDao ldIllegalWordDao;

    /**
     * get all available vod channel info
     * @param pageNum page num
     * @param pageSize page size
     * @return ResultInfo
     */
    @RequestMapping(value = "/")
    public ResultInfo getAvailableWithUserInfo(@RequestParam(required = false, defaultValue = "1") int pageNum,
                                                                         @RequestParam(required = false, defaultValue = "50") int pageSize){
        return vodChannelService.selectAllAvailableWithUser(pageNum, pageSize);
    }


    /**
     * get user's all vod channel
     * @param userId user id
     * @return ResultInfo
     */
    @GetMapping(value = "/{userId}")
    public ResultInfo getUserChannels(@PathVariable int userId){
        return vodChannelService.selectByUserId(userId);
    }


    /**
     * get a special channel by user id and video id
     * @param userId user id
     * @param videoId video id
     * @return ResultInfo
     */
    @GetMapping(value = "/{userId}/{videoId}")
    public ResultInfo getVodChannel(@PathVariable int userId, @PathVariable String videoId){
        return vodChannelService.selectByUserAndVideoId(userId, videoId);
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
                    throw new XException("content message contains illegal word");
                }
            }
        }
        return vodChannelService.updateChannelSetting(action, channelInfo);
    }


    /**
     * update channel status by user id and video id
     * @param action action
     * @param userId user id
     * @return ResultInfo
     */
    @PutMapping("/status/{action}/{userId}")
    public ResultInfo updateChannelUnavailable(@PathVariable int action, @PathVariable int userId,
                                               String[] videoIds){
        return vodChannelService.updateChannelStatus(action, userId, videoIds);
    }


    /**
     * update channel preview by video id
     * @param videoId video id
     * @param file preview image file
     * @param request HttpServletRequest
     * @return ResultInfo
     * @throws IOException IOException
     */
    @PostMapping("/upload/{videoId}")
    public ResultInfo uploadPreviewImage(@PathVariable String videoId, @RequestParam MultipartFile file,
                                                      HttpServletRequest request) throws IOException {
        if (file.isEmpty()) {
            throw new XException("icon error");
        }
        String path = request.getSession().getServletContext().getRealPath("/Resource/channel_preview/");
        FileUtils.copyInputStreamToFile(file.getInputStream(), new File(path, file.getOriginalFilename()));
        String preview = BASE_RESOURCE_URL + "channel_preview/" + file.getOriginalFilename();
        return vodChannelService.updatePreview(videoId, preview);
    }
}
