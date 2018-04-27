package com.wiatec.blive.api;


import com.wiatec.blive.common.result.ResultInfo;
import com.wiatec.blive.service.ClipsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author patrick
 */
@Controller
@ResponseBody
@RequestMapping(value = "/clips")
public class BVisionClips {

    @Resource
    private ClipsService clipsService;

    @GetMapping("/")
    public ResultInfo getAllVisible(){
        return clipsService.getAllVisible();
    }
}
