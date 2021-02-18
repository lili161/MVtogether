package com.Rsoft.mvtogether.Service;

/**
 * @author LR
 * @date 2021/2/18
 * 交流可加qq群 1027263551
 **/
public interface progress {
    //开始计时
     void  beginProgress(String key,long expireSeconds,long DurationHours);
     //计时清零
     void setZeroProgress(String key);
}
