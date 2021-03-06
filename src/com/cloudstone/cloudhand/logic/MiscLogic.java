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
        return pref().getIP();
    }
    
    public void saveServerIP(String IP) {
        pref().setIP(IP);
    }
    
    public String getServerUrl() {
        return "http://" +  getServerIP() + ":" + UrlConst.PORT;
    }
    
    public String getPassword(String userName) {
        return pref().getPassword(userName);
    }
    
    public void savePassword(String userName, String password) {
        pref().setPassword(userName, password);
    }
    
    public void removePassword(String userName) {
        pref().removePassword(userName);
    }
    
    public String getCurrentUser() {
        return pref().getCurrentUser();
    }
    
    public void saveCurrentUser(String userName) {
        pref().setCurrentUser(userName);
    }
}
