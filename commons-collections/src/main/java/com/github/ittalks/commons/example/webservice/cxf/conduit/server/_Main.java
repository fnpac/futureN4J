package com.github.ittalks.commons.example.webservice.cxf.conduit.server;

import org.apache.cxf.jaxws.JaxWsServerFactoryBean;

/**
 * Created by liuchunlong on 2017/10/31.
 */
public class _Main {

    public static void main(String[] args) {
        JaxWsServerFactoryBean factory=new JaxWsServerFactoryBean();
        factory.setAddress("http://localhost:9999/webservice");
        factory.setServiceBean(new HelloServiceImpl());
        factory.create();
    }
}
