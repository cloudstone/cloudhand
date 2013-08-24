/**
 * @(#)MiscLogic.java, Aug 14, 2013. 
 * 
 */
package com.cloudstone.cloudhand.logic;

import com.cloudstone.cloudhand.constant.UrlConst;


/**
 * @author xuhongfeng
 *
 */
public class MiscLogic extends BaseLogic {
    
    private static MiscLogic me;
    public static MiscLogic getInstance() {
        if (me == null) {
            synchronized (MiscLogic.class) {
                if (me == null) {
                    me = new MiscLogic();
                }
            }
        }
        return me;
    }

    public String getServerIP() {
        return pref().getIp();
    }
    
    public String getServerUrl() {
        return "http://" +  getServerIP() + ":" + UrlConst.PORT;
    }
    
    public String getPassword(String user) {
        return pref().getPassword(user);
    }
    
    public void savePassword(String user, String password) {
        pref().setPassword(user, password);
    }
    
    public void removePassword(String user) {
        pref().removePassword(user);
    }
    
    public String getCurrentUser() {
        return pref().getCurrentUser();
    }
    
    public void saveCurrentUser(String user) {
        pref().setCurrentUser(user);
    }
}
