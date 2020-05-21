package com.tools;

//import com.springapi.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;


public class TestF {

    public static void main(String...args) {
        Test t = new Test();
        Thread th = new Thread(t);
        th.start();
        Thread th2 = new Thread(t);
        th2.start();
        Thread th3 = new Thread(t);
        th3.start();

    }
}
