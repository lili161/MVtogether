package com.Rsoft.mvtogether.Controller;

import com.Rsoft.mvtogether.Constant.Constant;
import com.Rsoft.mvtogether.Entity.Movies;
import com.Rsoft.mvtogether.Entity.Viewer;
import com.Rsoft.mvtogether.Service.progress;
import com.Rsoft.mvtogether.Utils.RedisUtils;
import com.Rsoft.mvtogether.Utils.checkString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author LR
 * @date 2021/2/17 交流可加qq群 1027263551
 */
@Controller
public class apisController {
    //
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private progress progress;

    //  @Autowired
    //  private checkString checkString;
    //    @GetMapping("/test")
    //    @ResponseBody
    //    public String   test(){
    //        try{
    //        return  redisUtils.get("second").toString();
    //        }catch (NullPointerException e){
    //            return "NULL";
    //        }
    //    }
    //    @GetMapping("/add")
    //    @ResponseBody
    //    public String increase(){
    //        progress.beginProgress("second",1000,1);
    ////        redisUtils.increasePerSecond("second",1000,14400);
    //        return "OK";
    //    }
    //    @GetMapping("/reset")
    //    @ResponseBody
    //    public  String reset(){
    //        progress.setZeroProgress("second");
    //        return "reset";
    //    }
    @ResponseBody
    @PostMapping("/api-v1/verify")
    public String checkIfbooked(String name, HttpSession session) {
        if (checkString.isLegal(name) == 1) {
            int result = progress.isBookedByName(name, session);
            if (result == 0) {
                return Constant.NotBooked;
            } else if (result == 1) {
                return Constant.BookedAsOwner;
            } else {
                return Constant.BookedAsCustomer;
            }
        } else {
            return "error";
        }
    }

    @ResponseBody
    @PostMapping("/api-v1/book")
    public String book(
            String ownerName, String ownerGender, String customerName, String customerGender, HttpSession session) {
        if (checkString.isLegal(ownerName) == 1
                && ownerGender.length() == 1
                && checkString.isLegal(customerName) == 1
                && customerGender.length() == 1) {
            if (progress.isBookedByName(ownerName) == 0 && progress.isBookedByName(customerName) == 0) {
                progress.bookAroom(ownerName, ownerGender, customerName, customerGender);
                progress.isBookedByName(ownerName, session);
                return Constant.bookSuccess;
            } else {
                return Constant.NameAlreadyBooked;
            }
        } else {
            return "error";
        }
    }

    @ResponseBody
    @PostMapping("/api-v1/changeMvNum")
    public String changeMvNum(String MvNum, HttpSession session) {
        Viewer viewer = (Viewer) session.getAttribute(Constant.roomInfo);
        String roomNum = viewer.getRoomNum();
        progress.addMvNum(roomNum, MvNum);
        return Constant.ChangeMvNumSuccess;
    }

    @ResponseBody
    @PostMapping("api-v1/getMvList")
    public List<Movies> getMvList(String page) {
        return progress.getMvList(page);
    }

    @ResponseBody
    @PostMapping("api-v1/getAllMvList")
    public List<Movies> getAllMvList() {
        return progress.getMvList();
    }
}
