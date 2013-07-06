/**
 * @(#)Cloudhand.java, 2013-7-6. 
 * 
 */
package com.cloudstone.cloudhand;

import android.app.Application;

/**
 * @author xuhongfeng
 *
 */
public class Cloudhand extends Application {
    private static Cloudhand me;
    
    @Override
    public void onCreate() {
        super.onCreate();
        me = this;
    }
    
    public static Cloudhand getInstance() {
        return me;
    }
}
