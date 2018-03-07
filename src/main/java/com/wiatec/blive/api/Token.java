package com.wiatec.blive.api;

import com.wiatec.blive.common.result.ResultInfo;
import com.wiatec.blive.orm.pojo.TokenInfo;
import com.wiatec.blive.service.TokenService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author patrick
 */
@Controller
@RequestMapping(value = "/token")
public class Token {

    @Resource
    private TokenService tokenService;

    @RequestMapping(value = "/")
    @ResponseBody
    public ResultInfo<TokenInfo> get(String token){
        return tokenService.selectOne(token);
    }

    @PostMapping(value = "/validate")
    @ResponseBody
    public ResultInfo validate(String token){
        return tokenService.validate(token);
    }
}
