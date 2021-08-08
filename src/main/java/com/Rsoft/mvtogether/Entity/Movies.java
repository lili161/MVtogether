package com.Rsoft.mvtogether.Entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author LR
 * @date 2021/2/19
 * 交流可加qq群 1027263551
 **/

@Data
public class Movies  implements Serializable {
    /*电影号*/
    private String MvNum;

    /*电影名*/
    private String MvName;

    /*电影链接*/
    private String MvUrl;
}
