package com.wiatec.blive.api;

import com.wiatec.blive.orm.pojo.UpgradeInfo;
import com.wiatec.blive.service.UpgradeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/upgrade")
public class Upgrade {

    @Resource
    private UpgradeService upgradeService;

    @RequestMapping(value = "/")
    public @ResponseBody UpgradeInfo get(){
        return upgradeService.selectOne();
    }

}
