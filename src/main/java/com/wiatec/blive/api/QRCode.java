package com.wiatec.blive.api;

import com.wiatec.blive.common.result.XException;
import com.wiatec.blive.common.utils.AESUtil;
import com.wiatec.blive.common.utils.TextUtil;
import com.wiatec.blive.orm.dao.LogQRCodeScanDao;
import org.bouncycastle.jce.provider.symmetric.AES;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author patrick
 */
@Controller
public class QRCode {

    // C304820649EBA3FA0648A86DCDD234B2

    @Resource
    private LogQRCodeScanDao logQRCodeScanDao;

    @RequestMapping(value = "/qrcode/{inviteCode}")
    public String getApk(@PathVariable String inviteCode, HttpServletRequest request){
        if(TextUtil.isEmpty(inviteCode)){
            throw new XException("invite code error");
        }
        String result = AESUtil.decrypt(inviteCode, AESUtil.KEY);
        if(result.length() < 11){
            throw new XException("invite code error");
        }
        result = result.substring(10);
        int userId = Integer.parseInt(result);
        String ip = request.getRemoteHost();
        logQRCodeScanDao.insertOne(userId, ip);
        return "apk/result";
    }



}
