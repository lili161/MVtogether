package com.Rsoft.mvtogether.Controller;

import com.Rsoft.mvtogether.Constant.Constant;
import com.Rsoft.mvtogether.Entity.Movies;
import com.Rsoft.mvtogether.Entity.Viewer;
import com.Rsoft.mvtogether.Service.progress;
import com.Rsoft.mvtogether.Utils.RedisUtils;
import com.Rsoft.mvtogether.Utils.checkString;
import com.Rsoft.mvtogether.Utils.fileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
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
    @Value("${mv_addr}")
    private String uploadAddr;
    @Value("${mv_file_ipAddr}")
    private String remote_ip;


    /*检查 此用户是否已经预订
     *
     * 返回 未预订  主人预定  客人预订
     * */
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

    /*换电影*/
    @ResponseBody
    @PostMapping("/api-v1/changeMvNum")
    public String changeMvNum(String MvNum, HttpSession session) {
        Viewer viewer = (Viewer) session.getAttribute(Constant.roomInfo);
        String roomNum = viewer.getRoomNum();
        progress.addMvNum(roomNum, MvNum);
        return Constant.ChangeMvNumSuccess;
    }

    /*获取电影列表*/
    @ResponseBody
    @PostMapping("api-v1/getMvList")
    public List<Movies> getMvList(String page) {
        return progress.getMvList(page);
    }

    /*获取全部列表*/
    @ResponseBody
    @PostMapping("api-v1/getAllMvList")
    public List<Movies> getAllMvList() {
        return progress.getMvList();
    }

    /*是否全部进入*/
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

    /*获取电影名*/
    @ResponseBody
    @PostMapping("api-v1/getMvName")
    public String getMvName(HttpSession session) {
        return progress.getMvName(session);
    }

    /*获取电影地址*/
    @ResponseBody
    @PostMapping("api-v1/getMvURL")
    public void sessionAddUrl(HttpSession session) {
        Viewer viewer = (Viewer) session.getAttribute(Constant.roomInfo);
        session.setAttribute(Constant.MovieUrl, progress.getUrl(viewer.getMvNum()));
    }

    /*同步*/
    @ResponseBody
    @PostMapping("api-v1/sync")
    public String sync(HttpSession session) {
        Viewer viewer = (Viewer) session.getAttribute(Constant.roomInfo);
        return progress.sync(viewer.getRoomNum());
    }

    /*上传文件*/
    @ResponseBody
    @PostMapping("api-v1/uploadFile")
    public String uploadFiles(@RequestParam("file") MultipartFile file) throws IOException {
        String MvCode = UUID.randomUUID().toString();
        String path = uploadAddr + MvCode + fileUtil.getFileExtend(Objects.requireNonNull(file.getOriginalFilename()));
        String remotePath = remote_ip + MvCode + fileUtil.getFileExtend(Objects.requireNonNull(file.getOriginalFilename()));
        File localFile = new File(path);
        if (!localFile.getParentFile().exists())
            localFile.mkdirs();
        file.transferTo(localFile);
        progress.addMv(file.getOriginalFilename(), remotePath);
        return null;
    }
}

