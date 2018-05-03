package com.wiatec.blive.api;

import com.wiatec.blive.common.result.ResultInfo;
import com.wiatec.blive.orm.pojo.PayResultInfo;
import com.wiatec.blive.service.PayService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author patrick
 */
@RestController
@RequestMapping(value ="/pay")
public class PayPal {

    @Resource
    private PayService payService;

    @PostMapping(value = "/verify/{payerName}/{publisherId}/{paymentId}")
    public ResultInfo<PayResultInfo> verify(@PathVariable("payerName") String payerName,
                                            @PathVariable("publisherId") int publisherId,
                                            @PathVariable("paymentId") String paymentId){
        return payService.verify(payerName, publisherId, paymentId);
    }

    @PostMapping(value = "/verify/{payerName}/{publisherId}")
    public ResultInfo<PayResultInfo> verify1(@PathVariable("payerName") String payerName,
                                            @PathVariable("publisherId") int publisherId){
        return payService.verify(payerName, publisherId, "");
    }
}
