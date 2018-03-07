package com.wiatec.blive.api;

import com.wiatec.blive.orm.pojo.UpgradeInfo;
import com.wiatec.blive.service.UpgradeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author patrick
 */
@Controller
@RequestMapping(value = "/upgrade")
public class Upgrade {

    @Resource
    private UpgradeService upgradeService;

    @RequestMapping(value = "/")
    @ResponseBody
    public UpgradeInfo get(){
        return upgradeService.selectOne();
    }

}
