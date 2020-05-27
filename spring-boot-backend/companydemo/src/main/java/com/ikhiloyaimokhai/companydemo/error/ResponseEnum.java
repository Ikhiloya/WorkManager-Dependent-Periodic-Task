package com.ikhiloyaimokhai.companydemo.error;

/**
 * Created by Ikhiloya Imokhai on 2019-12-18.
 */
public enum ResponseEnum {

    RESOURCE_NOT_FOUND("99"),
    ALL_EXCEPTION("99");

    private String code;
    private String message;


    ResponseEnum(String code) {
        this.code = code;
    }

    ResponseEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
