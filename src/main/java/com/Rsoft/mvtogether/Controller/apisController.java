package com.Rsoft.mvtogether.Controller;

import com.Rsoft.mvtogether.Constant.Constant;
import com.Rsoft.mvtogether.Entity.Movies;
import com.Rsoft.mvtogether.Entity.Viewer;
import com.Rsoft.mvtogether.Service.progress;
import com.Rsoft.mvtogether.Utils.RedisUtils;
import com.Rsoft.mvtogether.Utils.checkString;
//import com.Rsoft.mvtogether.Utils.kafkaConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

/**
 * @author LR
 * @date 2021/2/17 交流可加qq群 1027263551
 */
@Slf4j
@Controller
public class apisController {
    //
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private progress progress;


    @ResponseBody
    @PostMapping("/api-v1/verify")
    public String checkIfbooked(String name, HttpSession session) {
        if (checkString.isLegal(name) == 1) {
            int result = progress.isBookedByName(name, session);
            if (result == 0) {
                return Constant.NotBooked;
            } else if (result == 1) {
                Viewer viewer = (Viewer) session.getAttribute(Constant.roomInfo);
                redisUtils.set(viewer.getOwnerName(), 1);
                return Constant.BookedAsOwner;
            } else {
                Viewer viewer = (Viewer) session.getAttribute(Constant.roomInfo);
                redisUtils.set(viewer.getCustomerName(), 1);
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

    @ResponseBody
    @PostMapping("api-v1/isAllIn")
    public String isAllIn(HttpSession session) throws InterruptedException {
        Viewer viewer = (Viewer) session.getAttribute(Constant.roomInfo);
        int count = 0;
        while (progress.isAllIn(viewer.getOwnerName(), viewer.getCustomerName()) != 1) {
            count++;
            if (count >= 4)
                break;
            TimeUnit.SECONDS.sleep(1);
        }
        if (progress.isAllIn(viewer.getOwnerName(), viewer.getCustomerName()) == 1)
            return Constant.AllIn;
        else
            return Constant.NotAllIn;

    }

    @ResponseBody
    @PostMapping("api-v1/getMvName")
    public String getMvName(HttpSession session) {
        return progress.getMvName(session);
    }

    @ResponseBody
    @PostMapping("api-v1/getMvURL")
    public void sessionAddUrl(HttpSession session) {
        Viewer viewer = (Viewer) session.getAttribute(Constant.roomInfo);
        session.setAttribute(Constant.MovieUrl, progress.getUrl(viewer.getMvNum()));
    }
}
