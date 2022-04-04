package com.atguigu.crowd.exception;

/**
 * 修改 Admin 时如果检测到登录账户重复抛出这个异常
 */
public class LoginAcctAlyeadyInUseForUpdateException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public LoginAcctAlyeadyInUseForUpdateException() {
        super();
    }

    public LoginAcctAlyeadyInUseForUpdateException(String message) {
        super(message);
    }

    public LoginAcctAlyeadyInUseForUpdateException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginAcctAlyeadyInUseForUpdateException(Throwable cause) {
        super(cause);
    }

    protected LoginAcctAlyeadyInUseForUpdateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
