/**
 * @(#)HttpStatusError.java, Jul 26, 2013. 
 * 
 */
package com.cloudstone.cloudhand.exception;

/**
 * @author xuhongfeng
 *
 */
public class HttpStatusError extends ApiException {
    private static final long serialVersionUID = -4336897418187218728L;
    
    private final int status;

    public HttpStatusError(int status) {
        super("status = " + status);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
