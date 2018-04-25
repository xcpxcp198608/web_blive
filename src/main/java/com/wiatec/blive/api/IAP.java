package com.wiatec.blive.api;

import com.wiatec.blive.common.http.HttpMaster;
import com.wiatec.blive.common.result.ResultInfo;
import com.wiatec.blive.service.IAPService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author patrick
 */
@Controller
@RequestMapping(value = "/iap")
public class IAP {

    @Resource
    private IAPService iapService;

    @PostMapping(value = "/verify")
    @ResponseBody
    public ResultInfo verify(String encodeStr){
        return iapService.verify(encodeStr);
    }


}
