package com.wiatec.blive.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author patrick
 */
@Controller
public class QRCode {

    @RequestMapping(value = "/qrcode")
    public String getApk(){
        return "apk/result";
    }

}
