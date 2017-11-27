package com.wiatec.blive.common.result;

public enum  EnumResult {

    SUCCESS(200, "successfully"),

    ERROR_SYSTEM(1001, "system exception"),

    ERROR_PARAM(3001, "request param format error"),

    ERROR_AUTHORIZATION_DEFINED(4000, "authorization access undefined, sign in again"),
    ERROR_USERNAME_PASSWORD_NO_MATCH(4100, "username and password not match"),

    ERROR_USERNAME_EXISTS(4001, "username is exists"),
    ERROR_USERNAME_NOT_EXISTS(4002, "username not exists"),
    ERROR_SSN_EXISTS(4003, "ssn is exists"),
    ERROR_EMAIL_EXISTS(4004, "email is exists"),
    ERROR_MAC_EXISTS(4005, "mac is exists"),
    ERROR_DEVICE_ALREADY_REGISTER(4006, "this device has been registered"),
    ERROR_DEVICE_NO_REGISTER(4007, "this device is not registered"),
    ERROR_KEY_INCORRECT(4008, "the key is incorrect"),
    ERROR_KEY_DEACTIVATE(4009, "the key is not activate"),
    ERROR_OUT_EXPIRATION(40010, "out expiration date"),
    ERROR_MAC_USING(40011, "the device is using"),

    ERROR_SERVER_SQL(5003, "server sql exception"),

    ERROR_AUTHORIZE(9001, "authorize communication exception"),
    ;

    private int code;
    private String message;

    EnumResult(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
