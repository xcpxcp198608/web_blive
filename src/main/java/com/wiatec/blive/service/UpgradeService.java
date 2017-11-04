package com.wiatec.blive.service;

import com.wiatec.blive.orm.dao.UpgradeDao;
import com.wiatec.blive.orm.pojo.UpgradeInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class UpgradeService {

    @Resource
    private UpgradeDao upgradeDao;

    @Transactional
    public UpgradeInfo selectOne(){
        return upgradeDao.selectOne();
    }
}
