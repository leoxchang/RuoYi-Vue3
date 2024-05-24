package com.truntao.common.exception;

import lombok.Getter;

import java.io.Serial;

/**
 * 全局异常
 *
 * @author truntao
 */
@Getter
public class GlobalException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 错误提示
     */
    private final String message;

    /**
     * 错误明细，内部调试错误
     * <p>
     * 和 {@link CommonResult#getDetailMessage()} 一致的设计
     */
    private String detailMessage;

    /**
     * 空构造方法，避免反序列化问题
     */
    public GlobalException() {
        super();
        this.message = "";
    }

    public GlobalException(String message) {
        super(message);
        this.message = message;
    }

    public GlobalException setDetailMessage(String detailMessage) {
        this.detailMessage = detailMessage;
        return this;
    }
}