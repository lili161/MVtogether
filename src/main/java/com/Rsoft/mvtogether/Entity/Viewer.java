package com.Rsoft.mvtogether.Entity;

import java.io.Serializable;

/**
 * @author LR
 * @date 2021/2/19 交流可加qq群 1027263551
 */
public class Viewer implements Serializable {

  private int isOwner;
  private String ownerName;
  private String customerName;
  private String roomNum;
  private String MvNum;
  private String MvName;
  private String ownerGender;
  private String customerGender;

    public String getOwnerGender() {
        return ownerGender;
    }

    public void setOwnerGender(String ownerGender) {
        this.ownerGender = ownerGender;
    }

    public String getCustomerGender() {
        return customerGender;
    }

    public void setCustomerGender(String customerGender) {
        this.customerGender = customerGender;
    }

    public int getIsOwner() {
        return isOwner;
    }

    public void setIsOwner(int isOwner) {
        this.isOwner = isOwner;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(String roomNum) {
        this.roomNum = roomNum;
    }

    public String getMvNum() {
        return MvNum;
    }

    public void setMvNum(String mvNum) {
        MvNum = mvNum;
    }

    public String getMvName() {
        return MvName;
    }

    public void setMvName(String mvName) {
        MvName = mvName;
    }

    @Override
    public String toString() {
        return "Viewer{" +
                "isOwner=" + isOwner +
                ", ownerName='" + ownerName + '\'' +
                ", customerName='" + customerName + '\'' +
                ", roomNum='" + roomNum + '\'' +
                ", MvNum='" + MvNum + '\'' +
                ", MvName='" + MvName + '\'' +
                ", ownerGender='" + ownerGender + '\'' +
                ", customerGender='" + customerGender + '\'' +
                '}';
    }
}