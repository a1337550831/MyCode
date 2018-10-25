package com.sun.mail.quartz.job;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

@Component
@Configuration
@EnableScheduling
public class TestQuartzJob {


    public void sayHello(){
        System.out.println("Hello");
    }


    public void sayWorld(){
        System.out.println("World!");
    }



}
