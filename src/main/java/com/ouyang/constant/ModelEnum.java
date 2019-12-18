package com.ouyang.constant;


/**
 * @author oy
 * @description
 * @date 2019/12/17
 */
public enum  ModelEnum {

    /** 1.bio 2.nio */
    BIO(1,"com.ouyang.service.BIOServiceImpl"),
    NIO(2,"com.ouyang.service.NIOServiceImpl"),
    ;
    private int code;
    private String name;

    ModelEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static String get(int code) {
        for (ModelEnum modelEnum : ModelEnum.values()) {
            if (modelEnum.getCode() == code) {
                return modelEnum.getName();
            }
        }
        throw new RuntimeException("非法的Code:" + code);
    }
}
