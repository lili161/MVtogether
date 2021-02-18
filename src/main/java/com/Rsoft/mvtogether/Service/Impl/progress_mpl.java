package com.Rsoft.mvtogether.Service.Impl;

import com.Rsoft.mvtogether.Service.progress;
import com.Rsoft.mvtogether.Utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author LR
 * @date 2021/2/18
 * 交流可加qq群 1027263551
 **/
@Service
public class progress_mpl implements progress {
    @Autowired
    RedisUtils redisUtils;
    @Override
    public void beginProgress(String key, long expireSeconds, long DurationHours) {
        redisUtils.increasePerSecond(key,expireSeconds,DurationHours*60*60);
    }

    @Override
    public void setZeroProgress(String key) {
        redisUtils.set(key,0);
//        redisUtils.remove(key+"inuse");
    }
}
