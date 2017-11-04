package com.wiatec.blive.api;

import com.wiatec.blive.entity.ResultInfo;
import com.wiatec.blive.orm.pojo.PayResultInfo;
import com.wiatec.blive.service.PayService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping(value ="/pay")
public class Pay {

    @Resource
    private PayService payService;

    @PostMapping(value = "/verify/{payerId}/{publisherId}/{paymentId}")
    @ResponseBody
    public ResultInfo<PayResultInfo> verify(@PathVariable("payerId") int payerId,
                                            @PathVariable("publisherId") int publisherId,
                                            @PathVariable("paymentId") String paymentId){
        return payService.verify(payerId, publisherId, paymentId);
    }
}
