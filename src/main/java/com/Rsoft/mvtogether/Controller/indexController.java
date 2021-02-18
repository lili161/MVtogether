package com.Rsoft.mvtogether.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author LR
 * @date 2021/2/16
 * 交流可加qq群 1027263551
 **/
@Controller
public class indexController {
    @GetMapping("/index")
    public String index(){
        return "main/index";
    }
    @GetMapping("/")
    public String indexvoid(){
        return "main/index";
    }
    @GetMapping("/bookMv")
    public  String bookMv(){
        return "main/bookMv";
    }
}
