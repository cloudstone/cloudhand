/**
 * @(#)UIUtils.java, Aug 3, 2013. 
 * 
 */
package com.cloudstone.cloudhand.util;

import android.content.Context;
import android.widget.Toast;

/**
 * @author xuhongfeng
 *
 */
public class UIUtils {
    
    public static void toast(Context context, int strId) {
        toast(context, context.getString(strId));
    }
    
    public static void toast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
}