package com.Rsoft.mvtogether.Utils;

/**
 * @author LR
 * @date 2021/8/8 17:50
 * @description
 **/

public class fileUtil {
    public static String getFileExtend(String fileName) {
        return fileName.substring(fileName.lastIndexOf('.'));
    }
}
