/**
 * @(#)DecodeApiException.java, Jul 26, 2013. 
 * 
 */
package com.cloudstone.cloudhand.exception;

/**
 * @author xuhongfeng
 *
 */
public class DecodeApiException extends ApiException {
    private static final long serialVersionUID = -8219112915277197797L;

    public DecodeApiException() {
        super();
    }

    public DecodeApiException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public DecodeApiException(String detailMessage) {
        super(detailMessage);
    }

    public DecodeApiException(Throwable throwable) {
        super(throwable);
    }

    
}
