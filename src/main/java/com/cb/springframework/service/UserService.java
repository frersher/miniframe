package com.cb.springframework.service;

import com.cb.springframework.*;
import com.cb.springframework.annotation.Autowired;
import com.cb.springframework.annotation.Component;
import com.cb.springframework.annotation.Transactional;

@Component
@Transactional
public class UserService implements BeanNameAware, ApplicationContextAware {

    @Autowired
    private OrderService orderService;

    private CbApplicationContext applicationContext;
    private String beanName;

    public void test(){
        System.out.println(orderService);
        System.out.println(applicationContext);
        System.out.println(beanName);
    }

    @Override
    public void setApplicationContext(CbApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setBeanName(String name) {
        this.beanName = name;
    }
}
