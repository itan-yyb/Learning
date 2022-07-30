package com.core.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 新建枚举类定义返回码，前端可根据返回码判断消息提示类型
 * 根据业务划分错误返回码
 * 507:重要提示信息，提示用户要解决
 * 500:一般提示信息，页面停留几秒自动消失
 */
@AllArgsConstructor
@Getter
public enum StatusCodeEnum {
    /**
     * 一般错误异常，前端识别后错误显示几秒后自动消失
     */
    AUTO_DISAPPEAR_PROMPT(500),
    /**
     * 重要提示，用户需手动取消
     */
    HAND_CANCEL_PROMPT(507);
    /**
     * 错误码
     */
    private int code;
}
