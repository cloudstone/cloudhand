/**
 * @(#)ApiException.java, Jul 26, 2013. 
 * 
 */
package com.cloudstone.cloudhand.exception;

/**
 * @author xuhongfeng
 *
 */
public class ApiException extends Exception {

    private static final long serialVersionUID = -5650086869413334781L;

    public ApiException() {
        super();
    }

    public ApiException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public ApiException(String detailMessage) {
        super(detailMessage);
    }

    public ApiException(Throwable throwable) {
        super(throwable);
    }

}
