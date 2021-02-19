package com.Rsoft.mvtogether.Dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author LR
 * @date 2021/2/19
 * 交流可加qq群 1027263551
 **/
@SpringBootTest
class RoomDaoTest {
@Autowired
private RoomDao roomDao;
  @Test
  void bookAroom() {
    roomDao.bookAroom("王老七","男","谢永强","男");
  }

  @Test
  void chooseMv() {
    roomDao.chooseMv("1005","11");

  }

  @Test
  void delRoom() {
    roomDao.delRoom("1003");
  }

  @Test
  void changeMv() {
    roomDao.chooseMv("1001","12");

  }

  @Test
  void getInfoByOwnerName() {
    System.out.println(roomDao.getInfoByOwnerName("王老七").toString());
  }

  @Test
  void getInfoByCustomerName() {
    System.out.println( roomDao.getInfoByCustomerName("王大炮").toString());
  }
}