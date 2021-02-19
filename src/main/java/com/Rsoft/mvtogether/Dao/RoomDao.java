package com.Rsoft.mvtogether.Dao;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * @author LR
 * @date 2021/2/19
 * 交流可加qq群 1027263551
 **/
@Mapper
@Repository
public interface RoomDao {
    @Insert("")
    public void bookAroom(String ownerName,String customerName);
    @Update("")
    public void chooseMv(String roomNum,String MvNum);
    @Delete("")
    public void delRoom(String roomNum);
    @Update("")
    public void changeMv(String roomNum,String MvNum);
}
