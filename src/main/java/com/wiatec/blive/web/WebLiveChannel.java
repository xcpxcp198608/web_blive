package com.wiatec.blive.web;

import com.wiatec.blive.common.result.ResultInfo;
import com.wiatec.blive.service.ChannelService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author patrick
 */
@Controller
@RequestMapping(value = "/channels")
public class WebLiveChannel {

    @Resource
    private ChannelService channelService;

    /**
     * the index page of user's channels analysis by user id
     * @param userId user id
     * @param model model
     * @return jsp->channel->analysis.jsp
     */
    @GetMapping("/analysis/{userId}")
    public String analysis(@PathVariable int userId, Model model){
        model.addAttribute("userId", userId);
        return channelService.liveViewAnalysis(userId, model);
    }

    /**
     * get the view times about user's channel by user id in every day
     * @param userId use id
     * @return ResultInfo
     */
    @GetMapping("/chart/days/{userId}")
    @ResponseBody
    public ResultInfo chartDaysDistribution(@PathVariable int userId){
        return channelService.getLiveChannelViewDaysDistribution(userId);
    }

    /**
     * get the view times about every hour duration by user id
     * @param userId user id
     * @return ResultInfos
     */
    @GetMapping("/chart/time/{userId}")
    @ResponseBody
    public ResultInfo chartTimeDistribution(@PathVariable int userId){
        return channelService.getLiveChannelViewTimeDistribution(userId);
    }


}
