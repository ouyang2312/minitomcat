package com.ouyang.config;


/**
 * @author oy
 * @description 配置信息
 * @date 2019/12/17
 */
public class ServerConfig {

    /**
     * 1.bio 2.nio
     */
    public static int MODEL = 2;

    /**
     * 端口
     */
    public static int PORT = 8080;

    /**
     * ip
     */
    public static String IP_ADDRESS = "127.0.0.1";

    /**
     * Session超时时间
     */
    public static Integer SESSION_TIMEOUT = 60 * 1000 * 10;

    /**
     * 全局编码
     */
    public static String ENCODE = "UTF-8";

    /**
     * 打开Gzip
     */
    public static boolean OPENGZIP=true;

    /**
     * MiniCat端口
     */
    public static Integer HTTP_PORT=80;

    /**
     * HTTP SessionId字段名
     */
    public static String SESSION_ID_FIELD_NAME="JSESSIONID";

    /**
     * MiniCat HTTP线程数量
     */
    public static Integer HTTP_THREAD_NUM = 500;

    /**
     * MiniCat 内务线程数量
     */
    public static Integer MINICAT_THREAD_NUM = 20;

    /**
     * 首页
     */
    public static String WELCOME_PATH="/index.do";

    /**
     * 最大Head长度
     */
    public static Integer MAX_HEADER_LENGTH=8192;

}
