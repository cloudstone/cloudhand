/**
 * @(#)HttpClientInstance.java, Jul 26, 2013. 
 * 
 */
package com.cloudstone.cloudhand.network;

import org.apache.http.params.HttpConnectionParams;

import android.net.http.AndroidHttpClient;

import com.cloudstone.cloudhand.Cloudhand;

/**
 * @author xuhongfeng
 *
 */
public class HttpClientInstance {
    private static final String USER_AGENT = "cloudhand-android";

    private static final int TIMEOUT_CONNECTION = 30 * 1000;
    private static final int TIMEOUT_SOCKET = 60 * 1000;

    public static AndroidHttpClient newInstance() {
        AndroidHttpClient client = AndroidHttpClient.newInstance(USER_AGENT,
                Cloudhand.getInstance());
        HttpConnectionParams.setConnectionTimeout(client.getParams(),
                TIMEOUT_CONNECTION);
        HttpConnectionParams.setSoTimeout(client.getParams(), TIMEOUT_SOCKET);
        
        /** Disable nagle **/
        HttpConnectionParams.setTcpNoDelay(client.getParams(), true);
        return client;
    }

}
