/**
 * @(#)BaseLogic.java, Aug 12, 2013. 
 * 
 */
package com.cloudstone.cloudhand.logic;

import com.cloudstone.cloudhand.storage.PreferenceStorage;

/**
 * @author xuhongfeng
 *
 */
public class BaseLogic {

    protected PreferenceStorage pref() {
        return PreferenceStorage.getInstance();
    }
}
