package com.Rsoft.mvtogether.Controller;

import com.Rsoft.mvtogether.Config.RedisConfig;
import com.Rsoft.mvtogether.Service.progress;
import com.Rsoft.mvtogether.Utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author LR
 * @date 2021/2/17
 * 交流可加qq群 1027263551
 **/
@Controller
public class apisController {
    //
    @Autowired
    public RedisUtils redisUtils;
    @Autowired
    public progress  progress;
    @GetMapping("/test")
    @ResponseBody
    public String   test(){
        try{
        return  redisUtils.get("second").toString();
        }catch (NullPointerException e){
            return "NULL";
        }
    }
    @GetMapping("/add")
    @ResponseBody
    public String increase(){
        progress.beginProgress("second",1000,1);
//        redisUtils.increasePerSecond("second",1000,14400);
        return "OK";
    }
    @GetMapping("/reset")
    @ResponseBody
    public  String reset(){
        progress.setZeroProgress("second");
        return "reset";
    }
}
