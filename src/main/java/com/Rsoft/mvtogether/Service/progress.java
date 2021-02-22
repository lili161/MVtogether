package com.Rsoft.mvtogether.Service;

import com.Rsoft.mvtogether.Entity.Movies;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author LR
 * @date 2021/2/18 交流可加qq群 1027263551
 */
public interface progress {
    // 开始计时
    void beginProgress(String key, long expireSeconds, long DurationHours);

    // 计时清零
    void setZeroProgress(String key);

    // 检查数据库是否有此人信息（是否预订了房间）
    // 0 无信息
    // 1  是房主
    // 2  是客人
    int isBookedByName(String name, HttpSession session);

    int isBookedByName(String name);

    void bookAroom(String ownerName, String ownerGender, String customerName, String customerGender);

    void addMvNum(String roomNum, String MvNum);

    List<Movies> getMvList(String page);

    List<Movies> getMvList();

    //俩人是否都进来了  0 没有  1 进来了
    int isAllIn(String myName, String youName);
}
