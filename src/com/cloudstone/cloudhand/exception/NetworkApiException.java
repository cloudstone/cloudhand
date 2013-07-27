/**
 * @(#)NetworkApiException.java, Jul 26, 2013. 
 * 
 */
package com.cloudstone.cloudhand.exception;

/**
 * @author xuhongfeng
 *
 */
public class NetworkApiException extends ApiException {
    private static final long serialVersionUID = 6648271780378932352L;

    public NetworkApiException() {
        super();
    }

    public NetworkApiException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public NetworkApiException(String detailMessage) {
        super(detailMessage);
    }

    public NetworkApiException(Throwable throwable) {
        super(throwable);
    }
}
