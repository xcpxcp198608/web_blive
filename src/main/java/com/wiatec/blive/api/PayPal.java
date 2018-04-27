package com.wiatec.blive.api;

import com.wiatec.blive.common.result.ResultInfo;
import com.wiatec.blive.orm.pojo.PayResultInfo;
import com.wiatec.blive.service.PayService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author patrick
 */
@Controller
@RequestMapping(value ="/pay")
public class PayPal {

    @Resource
    private PayService payService;

    @PostMapping(value = "/verify/{payerName}/{publisherId}/{paymentId}")
    @ResponseBody
    public ResultInfo<PayResultInfo> verify(@PathVariable("payerName") String payerName,
                                            @PathVariable("publisherId") int publisherId,
                                            @PathVariable("paymentId") String paymentId){
        return payService.verify(payerName, publisherId, paymentId);
    }

    @PostMapping(value = "/verify/{payerName}/{publisherId}")
    @ResponseBody
    public ResultInfo<PayResultInfo> verify1(@PathVariable("payerName") String payerName,
                                            @PathVariable("publisherId") int publisherId){
        return payService.verify(payerName, publisherId, "");
    }
}
