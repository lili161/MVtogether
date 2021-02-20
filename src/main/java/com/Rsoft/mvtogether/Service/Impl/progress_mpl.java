package com.Rsoft.mvtogether.Service.Impl;

import com.Rsoft.mvtogether.Constant.Constant;
import com.Rsoft.mvtogether.Dao.MoviesDao;
import com.Rsoft.mvtogether.Dao.RoomDao;
import com.Rsoft.mvtogether.Entity.Movies;
import com.Rsoft.mvtogether.Entity.Viewer;
import com.Rsoft.mvtogether.Service.progress;
import com.Rsoft.mvtogether.Utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author LR
 * @date 2021/2/18 交流可加qq群 1027263551
 */
@Service
public class progress_mpl implements progress {
  @Autowired
  private RedisUtils redisUtils;
  @Autowired
  private RoomDao roomDao;
  @Autowired
  private MoviesDao moviesDao;

  @Override
  public void beginProgress(String key, long expireSeconds, long DurationHours) {
    redisUtils.increasePerSecond(key, expireSeconds, DurationHours * 60 * 60);
  }

  @Override
  public void setZeroProgress(String key) {
    redisUtils.set(key, 0);
    //        redisUtils.remove(key+"inuse");
  }

  @Override
  public int isBookedByName(String name, HttpSession session) {
    Viewer viewerOwner = roomDao.getInfoByOwnerName(name);
    Viewer viewerCustomer = roomDao.getInfoByCustomerName(name);
    if (viewerOwner == null && viewerCustomer == null) {
      return 0;
    } else if (viewerOwner != null) {
      viewerOwner.setIsOwner(1);
      viewerOwner.setMvName(moviesDao.getNameByNum(viewerOwner.getMvNum()));
      session.setAttribute(Constant.roomInfo, viewerOwner);
      return 1;
    } else {
      viewerCustomer.setIsOwner(0);
      viewerCustomer.setMvName(moviesDao.getNameByNum(viewerCustomer.getMvNum()));
      session.setAttribute(Constant.roomInfo, viewerCustomer);
      return 2;
    }
  }

  @Override
  public int isBookedByName(String name) {
    Viewer viewerOwner = roomDao.getInfoByOwnerName(name);
    Viewer viewerCustomer = roomDao.getInfoByCustomerName(name);
    if (viewerOwner == null && viewerCustomer == null) {
      return 0;
    } else {
      return 1;
    }
  }

  @Override
  public void bookAroom(
          String ownerName, String ownerGender, String customerName, String customerGender) {
    roomDao.bookAroom(ownerName, ownerGender, customerName, customerGender);
  }

  @Override
  public void addMvNum(String roomNum, String MvNum) {
    roomDao.changeMv(roomNum, MvNum);
  }

  @Override
  public List<Movies> getMvList(String page) {
    // 后期加页数
    String capacity = "10";

    return moviesDao.getAllMoviesByPage(
            Integer.parseInt(page) * Integer.parseInt(capacity), Integer.parseInt(capacity));
  }

  @Override
  public List<Movies> getMvList() {
    return moviesDao.getAllMovies();
  }
}
