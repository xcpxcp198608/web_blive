package com.wiatec.blive.web;

import com.wiatec.blive.common.result.ResultInfo;
import com.wiatec.blive.service.CoinService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author patrick
 */
@Controller
@RequestMapping(value = "/coins")
public class WebBVisionCoin {

    @Resource
    private CoinService coinService;

    @GetMapping("/analysis/{userId}")
    public String analysis(@PathVariable int userId, Model model) {
        model.addAttribute("userId", userId);
        return "coin/analysis";
    }


    @GetMapping("/chart/{userId}/{year}/{month}")
    @ResponseBody
    public ResultInfo chartMonth(@PathVariable int userId, @PathVariable int year, @PathVariable int month){
        return coinService.chartMonth(userId, year, month);
    }


    @GetMapping("/chart/days/{userId}/{year}/{month}")
    @ResponseBody
    public ResultInfo chartDays(@PathVariable int userId, @PathVariable int year, @PathVariable int month){
        return coinService.chartDays(userId, year, month);
    }
}
