package com.morethan.game.exception;

/**
 * <pre>
 * 应用运行时异常，统一抛此类
 * </pre>
 * 
 * <small> 2018年4月6日 | Aron</small>
 */
public class GameAPIException extends RuntimeException {

    private static final long serialVersionUID = 6403925731816439878L;

    public GameAPIException() {
        super();
    }

    public GameAPIException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public GameAPIException(String message, Throwable cause) {
        super(message, cause);
    }

    public GameAPIException(String message) {
        super(message);
    }

    public GameAPIException(Throwable cause) {
        super(cause);
    }

}
