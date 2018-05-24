package com.wiatec.blive.apns;

import com.wiatec.blive.common.result.ResultInfo;
import com.wiatec.blive.service.DeviceTokenService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author patrick
 */
@RestController
@RequestMapping("/apns")
public class APNs {

    @Resource private DeviceTokenService deviceTokenService;

    @PostMapping("/{userId}")
    public ResultInfo uploadDeviceToken(@PathVariable int userId, String deviceToken){
        return deviceTokenService.storeDeviceToken(userId, deviceToken);
    }

}
