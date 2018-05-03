package com.wiatec.blive.api;


import com.wiatec.blive.common.result.ResultInfo;
import com.wiatec.blive.service.ImageAdService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author patrick
 */
@RestController
@RequestMapping(value = "/images")
public class BVisionImageAd {

    @Resource
    private ImageAdService imageAdService;

    @GetMapping("/{position}")
    public ResultInfo getImagesByPosition(@PathVariable int position){
        return imageAdService.selectByPosition(position);
    }

}
