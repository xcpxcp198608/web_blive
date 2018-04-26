package com.wiatec.blive.api;

import com.wiatec.blive.common.result.ResultInfo;
import com.wiatec.blive.service.CoinService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author patrick
 */
@Controller
@RequestMapping(value = "/coin")
public class Coin {

    @Resource
    private CoinService coinService;

    /**
     * get user's coins
     * @param userId user id
     * @return ResultInfo
     */
    @GetMapping("/{userId}")
    @ResponseBody
    public ResultInfo getCoins(@PathVariable int userId){
        return coinService.getCoins(userId);
    }

    /**
     * user consume coins
     * @param userId userId
     * @param targetUserId targetUserId
     * @param numbers numbers
     * @param platform platform
     * @param description description
     * @return ResultInfo
     */
    @PutMapping("/consume/{userId}/{targetUserId}/{numbers}")
    @ResponseBody
    public ResultInfo consumeCoin(@PathVariable int userId, @PathVariable int targetUserId,
                                  @PathVariable int numbers, String platform, String description){
        return coinService.consumeCoin(userId, targetUserId, numbers, platform, description);
    }

    /**
     * verify apple IAP purchase result
     * @param userId user id
     * @param receiptData  receiptData
     * @param platform platform
     * @param productIdentifier productIdentifier
     * @return ResultInfo
     */
    @PutMapping("/iap/verify/{userId}")
    @ResponseBody
    public ResultInfo verify(@PathVariable int userId, String receiptData, String platform, String productIdentifier){
        return coinService.iapVerify(userId, receiptData, platform, productIdentifier);
    }
}
