package com.github.ittalks.commons.example.webservice.cxf.interceptor.server;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;

import java.util.logging.Logger;

/**
 * Created by 刘春龙 on 2017/10/30.
 */
public class _Main {

    private static final Logger logger = Logger.getLogger(_Main.class.getName());

    public static void main(String[] args) {
        logger.info("web service start");

//        HelloWorld helloWorld = new HelloWorldImpl();
        String address = "http://127.0.0.1:9999/webservice";

        JaxWsServerFactoryBean factoryBean = new JaxWsServerFactoryBean();
        // 设置地址
        factoryBean.setAddress(address);
        /**
         * 指定实现该服务的类。
         *
         * @param serviceClass 服务实现类
         */
        // 方式一
        factoryBean.setServiceClass(HelloWorldImpl.class);
        /**
         * 设置实现服务的bean。
         * 如果设置了，则会为所提供的bean创建BeanInvoker
         *
         * @param serviceBean 一个实例化的实现对象
         */
        // 方式二
//        factoryBean.setServiceBean(helloWorld);

        // 设置拦截器
        factoryBean.getInInterceptors().add(new AuthInterceptor());
        factoryBean.getInInterceptors().add(new LoggingInInterceptor());// 添加in日志拦截器，可以看到soap消息
        factoryBean.getOutInterceptors().add(new LoggingOutInterceptor());// 添加out日志拦截器，可以看到soap消息

        factoryBean.create(); // 创建webservice接口
        logger.info("web service started");
        logger.info("请求地址为为：" + address + "?WSDL");
    }
}
