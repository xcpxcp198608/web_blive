package com.wiatec.blive.api;

import com.wiatec.blive.entity.ResultInfo;
import com.wiatec.blive.orm.pojo.TokenInfo;
import com.wiatec.blive.service.TokenService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/token")
public class Token {

    @Resource
    private TokenService tokenService;

    @RequestMapping(value = "/")
    public @ResponseBody TokenInfo get(@ModelAttribute TokenInfo tokenInfo){
        return tokenService.selectOne(tokenInfo);
    }

    @PostMapping(value = "/validate")
    public @ResponseBody ResultInfo validate(@ModelAttribute TokenInfo tokenInfo){
        return tokenService.validate(tokenInfo);
    }
}
