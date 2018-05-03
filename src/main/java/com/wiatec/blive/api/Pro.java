package com.wiatec.blive.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author patrick
 */
@RestController
@RequestMapping(value = "/pro")
public class Pro {

    private final Logger logger = LoggerFactory.getLogger(Pro.class);

}
