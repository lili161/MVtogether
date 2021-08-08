package com.Rsoft.mvtogether.Controller;

import com.Rsoft.mvtogether.Constant.Constant;
import com.Rsoft.mvtogether.Entity.Viewer;
import com.Rsoft.mvtogether.Service.progress;
import com.Rsoft.mvtogether.Utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author LR
 * @date 2021/2/16 交流可加qq群 1027263551
 */
@Controller
public class indexController {
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private progress progress;
    @Autowired
    private apisController apisController;

    @GetMapping("/index")
    public String index() {
        return "main/index";
    }

    @GetMapping("/")
    public String indexvoid() {
        return "main/index";
    }

  @GetMapping("/bookMv")
  public String bookMv() {
    return "main/bookMv";
  }

  @GetMapping("/transition")
  public String transition() {
    return "main/transition";
  }

  @GetMapping("/book")
  public String bookRoom() {
    return "main/bookMv";
  }

  @GetMapping("/watch")
  public String watch(HttpSession session) {
      //        String name = session.getAttribute("Name").toString();
      // 查询是房主 还是 客人 之后返回响应的房间
      Viewer viewer = (Viewer) session.getAttribute(Constant.roomInfo);
      session.setAttribute(Constant.MovieName, progress.getMvName(session));
      String redisRoomNum = "room" + viewer.getRoomNum();
      redisUtils.set(redisRoomNum, new Date().toString(), 1L, TimeUnit.DAYS);
      apisController.sessionAddUrl(session);
      if (viewer.getIsOwner() == 1) {
          return "main/ownerWatch";
      } else {
          return "main/customerWatch";
      }
  }

  @GetMapping("/wait")
  public String waiting(HttpSession session) {
    Viewer viewer = (Viewer) session.getAttribute(Constant.roomInfo);
    String ownerName = viewer.getOwnerName();
    String customerName = viewer.getCustomerName();
    int isOwner = viewer.getIsOwner();
    if (null != redisUtils.get(ownerName) && null != redisUtils.get(customerName)) {
//      if (isOwner == 1)
//        return "main/ownerWatch";
//      else
//        return "main/customerWatch";.
        return watch(session);
    }
    return "main/waitFriend";
  }

    /*选电影*/
    @GetMapping("/choose")
    public String chooseMv() {
        return "main/chooseMv";
    }

    /*不看了*/
    @GetMapping("/go")
    public String go(HttpSession session) {
        Viewer viewer = (Viewer) session.getAttribute(Constant.roomInfo);
        String ownerName = viewer.getOwnerName();
        String customerName = viewer.getCustomerName();
        String redisRoomNum = "room" + viewer.getRoomNum();
        /*用房主的名字删除房间*/
        progress.delRoomByName(ownerName);
        redisUtils.remove(redisRoomNum);
        session.invalidate();
        return "main/index";
    }

    /*上传文件*/
    @GetMapping("/upload")
    public String upload() {
        return "main/upload";
    }
}
