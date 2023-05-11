package com.cb.springframework;

import com.cb.springframework.service.UserService;

public class MyApplication {

    public static void main(String[] args) {
        CbApplicationContext applicationContext = new CbApplicationContext(AppConfig.class);
        UserService userService = (UserService) applicationContext.getBean("userService");
        userService.test();
    }
}