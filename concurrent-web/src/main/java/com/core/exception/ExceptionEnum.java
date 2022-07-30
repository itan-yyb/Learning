package com.core.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.HashMap;
import java.util.Map;

/**
 * 异常枚举
 */
@AllArgsConstructor
@Getter
public enum ExceptionEnum {

    //无效参数类型
    INVALID_PARAM_TYPE(400, "无效参数类型"),
    //用户名或密码错误
    INVALID_USERNAME_OR_PASSWORD(400, "用户名或密码错误"),
    //请求语法错误
    WRONG_GRAMMAR(400, "请求语法错误"),
    //请先登录
    LOGIN_FIRST(401, "请先登录"),
    //请先登录
    LOGIN_CONFLICT(401, "@login_conflict@"),
    //找不到资源
    CATEGORY_NOT_FOUND(404, "找不到资源"),
    //请求超时
    REQUEST_TIMEOUT(408, "请求超时"),
    //创建token错误
    CREAT_TOKEN_ERROR(500, "创建token错误"),
    //创建token错误
    SSE_UNKNOWN_EMITTER(601, "not found SSE emitter"),
    // oss资源处理失败
    OSS_PROCESS_ERROR(602, "oss process error"),
    // oss资源处理失败，详细
    OSS_RESOURCE_NO_MATCH_ERROR(603, "无法匹配到商品"),
    OSS_PUT_LOW_QUALITY_ERROR(604, "图片质量过低"),
    OSS_PUT_INVALID_TYPE_ERROR(605, "无效的文件类型"),
    OSS_PUT_INVALID_ORIGIN_TYPE_ERROR(606, "无效的原始文件类型"),
    OSS_RESOURCE_READ_ERROR(607, "系统无法读取相关资源"),
    OSS_RESOURCE_SETTING_ERROR(608, "图片资源服务配置错误"),
    OSS_PUT_INVALID_VIDEO(609, "无效的视频文件类型"),
    OSS_PUT_VIDEO_SIZE_ERROR(610, "视频文件过大!"),
    //未定义异常
    CUSTOM_ERROR(500, "未定义异常");

    /**
     * 错误码
     */
    private Integer code;
    /**
     * 提示信息
     */
    private String message;

    public static Map<String,String> getMap() {
        Map<String,String> map =new HashMap<>();
        for (ExceptionEnum value : values()) {
            map.put(value.getCode().toString(),value.getMessage());
        }
        return map;

    }

    /**
     * 初始化枚举对象
     *
     * @param status 错误码
     * @param msg    提示信息
     * @return 错误对象
     */
    public ExceptionEnum init(Integer status, String msg) {
        this.code = status;
        this.message = msg;
        return this;
    }

}
