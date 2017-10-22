package com.cg.dev.internet.things.webserver.controller;

import com.cg.dev.internet.things.webserver.pojo.Things;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
public class DefaultController {

    private Logger logger = LoggerFactory.getLogger(DefaultController.class);
    @RequestMapping("/index/newThings")
    public String index(Model model){
        Things things = new Things();
        things.setThingsId(UUID.randomUUID().toString());
        things.setThingsName("this is a sample things");
        model.addAttribute("things",things);
        logger.info("this is a sample things log");
        return "index";
    }
}
