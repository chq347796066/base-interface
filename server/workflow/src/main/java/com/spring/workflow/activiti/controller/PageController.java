package com.spring.workflow.activiti.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 打开activiti编辑器
 *
 * @author dell
 * @auther: Ace Lee
 * @date: 2019/3/7 20:15
 */
@Controller
public class PageController {
	
    @GetMapping("/editor")
    public String modelOpen(){

        return "modeler";
    }
}
