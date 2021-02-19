package com.Rsoft.mvtogether.Entity;

import java.io.Serializable;

/**
 * @author LR
 * @date 2021/2/19
 * 交流可加qq群 1027263551
 **/

public class Movies  implements Serializable {
    private String MvNum;

    private String MvName;

    private String MvUrl;

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

    public String getMvUrl() {
        return MvUrl;
    }

    public void setMvUrl(String mvUrl) {
        MvUrl = mvUrl;
    }

    @Override
    public String toString() {
        return "Movies{" +
                "MvNum='" + MvNum + '\'' +
                ", MvName='" + MvName + '\'' +
                ", MvUrl='" + MvUrl + '\'' +
                '}';
    }
}
