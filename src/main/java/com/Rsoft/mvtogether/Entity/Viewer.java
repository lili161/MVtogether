package com.Rsoft.mvtogether.Entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author LR
 * @date 2021/2/19 交流可加qq群 1027263551
 */
@Data
public class Viewer implements Serializable {
    /*是否是主人*/
  private int isOwner;
    /*主人名字*/
    private String ownerName;
    /*客人名字*/
    private String customerName;
    /*房间号*/
    private String roomNum;
    /*电影号*/
    private String MvNum;
    /*电影名*/
    private String MvName;
    /*主人 性别*/
    private String ownerGender;
    /*客人性别*/
    private String customerGender;

}
