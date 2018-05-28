package com.wiatec.blive.api;

import com.wiatec.blive.common.result.ResultInfo;
import com.wiatec.blive.service.ProService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author patrick
 */
@RestController
@RequestMapping(value = "/pro")
public class Pro {

    private final Logger logger = LoggerFactory.getLogger(Pro.class);

    @Resource private ProService proService;

    @GetMapping("/{userId}")
    public ResultInfo getProDetail(@PathVariable int userId){
        return proService.getProDetails(userId);
    }

    @GetMapping("/purchase")
    public ResultInfo getProPurchaseList(){
        return proService.getPurchaseList();
    }

    @PostMapping("/purchase/{id}/{userId}")
    public ResultInfo purchasePro(@PathVariable int id, @PathVariable int userId,
                                  @RequestParam(required = false) String voucherId,
                                  String platform){
        return proService.purchasePro(id, userId, voucherId, platform);
    }

}
