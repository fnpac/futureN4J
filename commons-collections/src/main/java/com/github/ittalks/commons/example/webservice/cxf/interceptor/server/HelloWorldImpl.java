package com.github.ittalks.commons.example.webservice.cxf.interceptor.server;

import javax.jws.WebService;

/**
 * Created by 刘春龙 on 2017/10/30.
 */
@WebService
public class HelloWorldImpl implements HelloWorld {

    public String sayHello(String str) {
        return "hello，" + str;
    }
}
