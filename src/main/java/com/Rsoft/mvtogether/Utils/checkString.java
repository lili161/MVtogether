package com.Rsoft.mvtogether.Utils;

/**
 * @author LR
 * @date 2021/2/20
 * 交流可加qq群 1027263551
 **/

public class checkString {
    //合法 1
    //非法 0
    public static int isLegal(String s) {
        if (s.length() <= 0 || s.length() >= 10) {
            return 0;
        } else {
            return 1;
        }
    }
}
