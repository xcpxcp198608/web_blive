package com.wiatec.blive.common.result;

/**
 * @author patrick
 */

public enum EnumResult {

    /**
     * 请求处理成功
     */
    SUCCESS(200, "Successfully"),

    /**
     * 请求有语法错误，例如Content-Type与请求体不符或请求体无法解析
     */
    ERROR_BAD_REQUEST(400, "Bad request"),

    /**
     * 请求未授权或登录超时，需要登录
     */
    ERROR_RE_LOGIN(401, " Login time session expired, please login to your account again."),

    /**
     * 请求未授权或登录超时，需要登录
     */
    ERROR_UNAUTHORIZED(401, "Unauthorized"),


    /**
     * access token 超时
     */
    ERROR_ACCESS_TOKEN_TIMEOUT(402, "Access token timeout"),

    /**
     * 禁止操作该资源，例如权限不够等
     */
    ERROR_FORBIDDEN(403, "Forbidden, permission denied"),

    /**
     * 资源不存在
     */
    ERROR_NO_FOUND(404, "Data resource not exists"),

    /**
     * 请求方法错误
     */
    ERROR_METHOD_NOT_ALLOWED(405, "Request method not allowed"),

    /**
     * 请求超时
     */
    ERROR_TIME_OUT(406, "Request Timeout"),

    /**
     * 请求参数格式错误
     */
    ERROR_PARAMS_FORMAT(407, "Request params format incorrect"),

    /**
     * 服务器内部错误
     */
    ERROR_INTERNAL_SERVER(500, "Internal server error"),

    /**
     * 服务器内部,SQL执行错误
     */
    ERROR_INTERNAL_SERVER_SQL(501, "Internal server error#1"),



    ERROR_USERNAME_EXISTS(6002, "Username already exists"),
    ERROR_USERNAME_FORMAT(6003, "Username format error"),
    ERROR_PASSWORD_FORMAT(6004, "Password format error"),
    ERROR_PASSWORD(6004, "Password not match"),
    ERROR_USERNAME_NOT_EXISTS(6005, "Username not exists"),
    ERROR_USERNAME_PASSWORD_NO_MATCH(6006, "Username and password not match"),
    ERROR_FILL_IN_USERNAME(6006, "Username fill in error"),
    ERROR_FILL_IN_PASSWORD(6006, "Password fill in error"),
    ERROR_ACCESS_TOKEN(6006, "Access token error"),
    ERROR_FILL_IN_EMAIL(6006, "Email fill in error"),
    ERROR_FILL_IN_PHONE(6006, "Phone fill in error"),
    ERROR_USERNAME_MAC_NO_MATCH(6007, "Username and mac not match"),
    ERROR_EMAIL_NO_ACTIVATE(6008, "No activate"),
    ERROR_SSN_EXISTS(6009, "SSN is exists"),
    ERROR_EMAIL_EXISTS(6010, "Email is exists"),
    ERROR_EMAIL_NOT_EXISTS(6011, "Email not exists"),
    ERROR_EMAIL_NOT_MATCH(6012, "Email not match with register email"),
    ERROR_EMAIL_FORMAT(6012, "Email format error"),
    ERROR_SIGN_UP(6012, "Sign up failure, try again later"),
    ERROR_SIGN_IN(6012, "Sign in failure, try again later"),
    ERROR_UPDATE_FAILURE(6012, "Update failure, try again later"),

    ERROR_DEVICE_ALREADY_REGISTER(6013, "This device has been registered"),
    ERROR_DEVICE_NO_REGISTER(6014, "This device is not registered"),
    ERROR_DEVICE_NO_EXISTS(6015, "This device is not exists"),
    ERROR_DEVICE_NO_CHECK_IN(6015, "This device is not check in"),
    ERROR_DEVICE_LIMITED(6016, "This device is limited"),
    ERROR_DEVICE_USING(6017, "The device is using"),
    ERROR_KEY_INCORRECT(6018, "The key is incorrect"),
    ERROR_KEY_DEACTIVATE(6019, "The key is not activate"),
    ERROR_OUT_EXPIRATION(6020, "Out expiration date"),
    ERROR_TOKEN_NOT_EXISTS(6021, "Token not exists"),
    ERROR_MAC_FORMAT(6022, "Mac address format error"),
    ERROR_MAC_EXISTS(6023, "Mac address already error"),
    ERROR_MAC_NOT_EXISTS(6024, "Mac address not exists"),
    ERROR_TRANSACTION_FAILURE(6026, "transaction failure"),


    ERROR_AUTHORIZE(9001, "Authorize communication exception"),
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
