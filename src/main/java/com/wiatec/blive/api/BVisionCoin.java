package com.wiatec.blive.api;

import com.wiatec.blive.common.result.ResultInfo;
import com.wiatec.blive.service.CoinService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author patrick
 */
@RestController
@RequestMapping(value = "/coin")
public class BVisionCoin {

    @Resource
    private CoinService coinService;

    /**
     * get user's coins
     * @param userId user id
     * @return ResultInfo
     */
    @GetMapping("/{userId}")
    public ResultInfo getCoins(@PathVariable int userId){
        return coinService.getCoins(userId);
    }

    /**
     * user consume coins
     * @param userId userId
     * @param targetUserId targetUserId
     * @param coins consume coins
     * @param platform platform
     * @return ResultInfo
     */
    @PutMapping("/consume/{userId}/{targetUserId}/{category}/{coins}")
    public ResultInfo consumeCoin(@PathVariable int userId, @PathVariable int targetUserId,
                                  @PathVariable int category, @PathVariable int coins,
                                  String platform){
        return coinService.consumeCoin(userId, targetUserId, category, coins, platform);
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
    public ResultInfo verify(@PathVariable int userId, String receiptData, String platform, String productIdentifier){
        return coinService.iapVerify(userId, receiptData, platform, productIdentifier);
    }


    @GetMapping("/bill/{userId}")
    public ResultInfo getBill(@PathVariable int userId){
        return coinService.getBills(userId);
    }


}
