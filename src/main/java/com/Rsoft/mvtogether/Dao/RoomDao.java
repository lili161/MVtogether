package com.Rsoft.mvtogether.Dao;

import com.Rsoft.mvtogether.Entity.Viewer;
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
    //预约房间
    @Insert("insert into room (ownerName,ownerGender,customerName,customerGender) values (#{ownerName},#{ownerGender},#{customerName},#{customerGender})")
    public void bookAroom(@Param("ownerName") String ownerName,@Param("ownerGender")String ownerGender,@Param("customerName") String customerName,@Param("customerGender")String customerGender);

    //修改房间的电影号
    @Update("update room set MvNum = #{MvNum} where roomNum = #{roomNum}")
    public void chooseMv(@Param("roomNum") String roomNum,@Param("MvNum") String MvNum);

    //删除房间信息
    @Delete("delete from room where roomNum = #{roomNum}")
    public void delRoom(@Param("roomNum") String roomNum);

    //修改房间的电影号
    @Update("update room set MvNum = #{MvNum} where roomNum = #{roomNum}")
    public void changeMv(@Param("roomNum") String roomNum, @Param("MvNum") String MvNum);

    //根据房主名查询房间信息
    @Select("select * from room where ownerName =#{ownerName} limit 1")
    Viewer getInfoByOwnerName(@Param("ownerName") String ownerName);

    //根据客人名查询房间信息
    @Select("select * from room where customerName =#{customerName} limit 1")
    Viewer getInfoByCustomerName(@Param("customerName") String customerName);

    @Delete("DELETE FROM room where ownerName = #{ownerName}")
    void delRoomByOwnerName(@Param("ownerName") String ownerName);
}
