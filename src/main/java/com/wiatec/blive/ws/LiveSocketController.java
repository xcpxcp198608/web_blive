package com.wiatec.blive.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author patrick
 */
@Controller
@RequestMapping("/ws")
public class LiveSocketController {
    private Logger logger = LoggerFactory.getLogger(LiveSocketController.class);

    @RequestMapping("/")
    public String start(HttpServletRequest request){
        return "ws/index";
    }

    @RequestMapping("/send")
    @ResponseBody
    public String send(){
        return "ok";
    }
}
