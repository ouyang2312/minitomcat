package com.ouyang.service;


import com.ouyang.builder.HttpBuilder;
import com.ouyang.builder.NioHttpBuilder;
import com.ouyang.config.ServerConfig;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @author oy
 * @description
 * @date 2019/12/17
 */
public class NIOServiceImpl implements IOService {

    private Selector selector;
    private ServerSocketChannel serverSocketChannel;

    public void start() {
        try {
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            serverSocketChannel.bind(new InetSocketAddress(ServerConfig.IP_ADDRESS,ServerConfig.PORT));
            serverSocketChannel.socket().setSoTimeout(ServerConfig.SESSION_TIMEOUT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handle() throws IOException {
        while (true){
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while(iterator.hasNext()){
                SelectionKey key = iterator.next();
                if(key.isAcceptable()){ //连接的时候 就会 触发
                    ServerSocketChannel serverSocketChannel = (ServerSocketChannel)key.channel();
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector,SelectionKey.OP_READ);
                }else if(key.isWritable()){//写事件 这边的selector 可以写 说明 应用程序有东西 像 系统内核写东西 然后传入网卡 给客户端
                    //这里不能直接写出去 需要使用http协议的方式 页面才能有效
                    SocketChannel channel = (SocketChannel) key.channel();
                    HttpBuilder builder = (HttpBuilder) key.attachment();
                    try {
                        builder.flushAndClose();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        channel.close();
                    }
                }else if(key.isReadable()){ //这边的selector可以读 说明系统内核 那边有信息过来了
                    SocketChannel channel = (SocketChannel) key.channel();
                    HttpBuilder builder = new NioHttpBuilder(channel);
                    builder.builder();
                    SelectionKey sKey = channel.register(selector, SelectionKey.OP_WRITE);
                    sKey.attach(builder);
                    //下面是 获取http过来的信息
//                    SocketChannel channel = (SocketChannel) key.channel();
//                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
//                    //14.读取数据
//                    int len = 0;
//                    while((len = channel.read(byteBuffer)) > 0){
//                        byteBuffer.flip();
//                        System.out.println(new String(byteBuffer.array(),0,len));
//                        byteBuffer.clear();
//                    }
//                    channel.register(selector,SelectionKey.OP_WRITE);
                }
                iterator.remove();
            }
        }
    }
}
