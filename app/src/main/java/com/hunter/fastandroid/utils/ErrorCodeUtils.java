package com.hunter.fastandroid.utils;

import com.hunter.fastandroid.R;
import com.hunter.fastandroid.net.ResponseCode;

/**
 * 错误码工具类
 */
public class ErrorCodeUtils {
    public static int getRes(int errorCode) {
        int resCode;

        switch (errorCode) {
            case ResponseCode.INVALID_USER:
                // 无效用户
                resCode = R.string.errormsg_invalid_user;
                break;
            case ResponseCode.NOT_FOUND_USER:
                // 该用户不存在
                resCode = R.string.errormsg_not_found_user;
                break;
            case ResponseCode.ERROR_PASSWORD:
                // 密码错误
                resCode = R.string.errormsg_error_password;
                break;
            case ResponseCode.ERROR_PASSWORD_LOCK:
                // 密码错误次数过多,被锁定
                resCode = R.string.errormsg_error_password_lock;
                break;
            case ResponseCode.ERROR_CREATE_TOKEN:
                // 生成Token出错，请稍后重试或者与管理员联系
                resCode = R.string.errormsg_error_create_token;
                break;
            case ResponseCode.PLEASE_INPUT_USERNAME:
                // 请输入注册时填写的用户名、邮箱地址或者手机号码
                resCode = R.string.errormsg_please_input_username;
                break;
            case ResponseCode.USER_LOCK:
                // 该账号被锁定，请与管理员联系
                resCode = R.string.errormsg_user_lock;
                break;
            case ResponseCode.USER_BLACKLIST:
                // 当前账号被加入黑名单，如有疑问，请与管理员联系
                resCode = R.string.errormsg_user_blacklist;
                break;
            case ResponseCode.FORMAT_ERROR_USERNAME:
                // 用户名格式不正确
                resCode = R.string.errormsg_format_error_username;
                break;
            case ResponseCode.FORMAT_ERROR_EMAIL:
                // 电子邮箱地址格式不正确
                resCode = R.string.errormsg_format_error_email;
                break;
            case ResponseCode.FORMAT_ERROR_PASSWORD:
                // 密码格式不正确
                resCode = R.string.errormsg_format_error_password;
                break;
            case ResponseCode.FORMAT_ERROR_MOBILE:
                // 手机号码格式不正确
                resCode = R.string.errormsg_format_error_mobile;
                break;
            case ResponseCode.EMAIL_EXISTING:
                // 该邮箱地址已经被注册
                resCode = R.string.errormsg_email_existing;
                break;
            case ResponseCode.USERNAME_EXISTING:
                // 该用户名已经被注册
                resCode = R.string.errormsg_username_existing;
                break;
            case ResponseCode.MOBILE_EXISTING:
                // 该手机号码已经被注册
                resCode = R.string.errormsg_mobile_existing;
                break;
            case ResponseCode.USERNAME_NOT_CAN_EMPTY:
                // 用户名不能为空
                resCode = R.string.errormsg_username_not_can_empty;
                break;
            case ResponseCode.EMAIL_NOT_CAN_EMPTY:
                // email不能为空
                resCode = R.string.errormsg_email_not_can_empty;
                break;
            case ResponseCode.ERROR_NETWORK:
                // 网络异常
                resCode = R.string.errormsg_network;
                break;
            case ResponseCode.USER_INFO_INCOMPLETE:
                // 用户信息未完善
                resCode = R.string.errormsg_userinfo;
                break;
            case ResponseCode.ERROR_PARSE:
                // 解析异常
                resCode = R.string.errormsg_parse;
                break;
            default:
                // 通用错误提示
                resCode = R.string.errormsg_error;
                break;
        }

        return resCode;
    }
}
