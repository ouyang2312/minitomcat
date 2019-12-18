package com.ouyang;

import com.ouyang.config.ServerConfig;
import com.ouyang.constant.ModelEnum;
import com.ouyang.service.IOService;

import java.io.IOException;
import java.util.Scanner;

/**
 * @author oy
 * @description
 * @date 2019/12/17
 */
public class Application {

    //启动
    public static void main(String[] args) throws IOException {
        //输入端口
        Scanner sc = new java.util.Scanner(System.in);
        System.out.println("请输入http端口:");
        int port = sc.nextInt();
        //使用模式
        System.out.println("请选择运行模式:");
        System.out.println("1、Bio");
        System.out.println("2、Nio");
        int model = sc.nextInt();
        ServerConfig.MODEL = model;
        ServerConfig.PORT = port;
        //启动服务
        try {
            Class<?> aClass = Class.forName(ModelEnum.get(model));
            try {
                IOService service = (IOService)aClass.newInstance();
                //启动对应的服务
                service.start();
                System.out.println("监听端口>>" + ServerConfig.PORT);
                //处理http请求
                //处理
                service.handle();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
