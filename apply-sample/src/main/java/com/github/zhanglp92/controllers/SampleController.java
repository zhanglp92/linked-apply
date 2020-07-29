package com.github.zhanglp92.controllers;

import com.github.zhanglp92.compose.Compose;
import com.github.zhanglp92.config.ApplyExecute;
import com.github.zhanglp92.context.Context;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@Log4j2
public class SampleController {

    @Resource(name = "defaultApplyConfig")
    private ApplyExecute applyExecute;

    @ResponseBody
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello(@RequestParam String name) throws Throwable {
        applyExecute.execute(new Context(), new Compose());

        return "hello " + name;
    }
}
