package com.wiatec.blive.api;


import com.wiatec.blive.common.result.ResultInfo;
import com.wiatec.blive.service.ImageAdService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author patrick
 */
@Controller
@ResponseBody
@RequestMapping(value = "/images")
public class BVisionImageAd {

    @Resource
    private ImageAdService imageAdService;

    @GetMapping("/{position}")
    public ResultInfo getImagesByPosition(@PathVariable int position){
        return imageAdService.selectByPosition(position);
    }

}
