package com.Rsoft.mvtogether.Dao;

import com.Rsoft.mvtogether.Entity.Movies;
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
class MoviesDaoTest {
@Autowired
private MoviesDao moviesDao;
  @Test
  void getNameByNum() {
    System.out.println(moviesDao.getNameByNum("13"));
  }

  @Test
  void addMv() {
    moviesDao.addMv("时空恋旅人2","http://47.111.78.227:8080/aboutTime.mp4");
  }

  @Test
  void getNumByName() {
    System.out.println(moviesDao.getNumByName("时空恋旅人"));
  }

  @Test
  void getAllMovies() {

    ;
  }

  @Test
  void getUrlByName() {
    System.out.println(moviesDao.getUrlByName("时空恋旅人"));
  }

  @Test
  void getUrlByNum() {
    System.out.println(moviesDao.getUrlByNum("13"));

  }
}