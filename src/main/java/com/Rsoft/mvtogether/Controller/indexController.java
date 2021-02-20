package com.Rsoft.mvtogether.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

/**
 * @author LR
 * @date 2021/2/16 交流可加qq群 1027263551
 */
@Controller
public class indexController {
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
    return "main/ownerWatch";
  }

  @GetMapping("/wait")
  public String waiting() {
    return "main/waitFriend";
  }

  @GetMapping("/choose")
  public String chooseMv() {
    return "main/chooseMv";
  }

  @GetMapping("/go")
  public String go() {
    return "main/test";
  }
}
