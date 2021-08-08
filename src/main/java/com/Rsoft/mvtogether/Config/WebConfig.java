/*
 * Copyright (c) 2020.  LR （lili161） All Rights Reserved.
 * FileName: WebConfig.java
 * @author: LR
 * @date: 10/13/20, 5:08 PM
 * @version: 1.0
 */

package com.Rsoft.mvtogether.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    /**
     * @author LR
     * @date 2020/10/13
     * 交流可加qq群 1027263551
     **/
    @Value("${mv_addr}")
    private String mv_addr;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/mv/**").addResourceLocations("file:" + mv_addr);
    }

}
