package com.Rsoft.mvtogether.Dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author LR
 * @date 2021/2/19
 * 交流可加qq群 1027263551
 **/
@Mapper
@Repository
public interface MoviesDao {
    @Select("")
    public String getNameByNum(String MvNum);

    @Insert("")
    public void addMv(String MvName,String MvNum,String MvUrl);

    @Select("")
    public String getNumByName(String MvName);
}
