/**
 * @(#)Logic.java, Aug 12, 2013. 
 * 
 */
package com.cloudstone.cloudhand.logic;

import com.cloudstone.cloudhand.data.User;

/**
 * @author xuhongfeng
 *
 */
public class UserLogic extends BaseLogic {
    
    public User getUser() {
        return pref().getUser();
    }
    
    public void saveUser(User user) {
        pref().setUser(user);
    }
    
    public boolean isLogin() {
        return getUser() != null;
    }
    
    public void logout() {
        clearUserInfo();
    }
    
    public void clearUserInfo() {
        pref().setUser(null);
    }

    private static UserLogic me;
    
    private UserLogic() {
    }
    
    public static UserLogic getInstance() {
        if (me == null) {
            synchronized (UserLogic.class) {
                if (me == null) {
                    me = new UserLogic();
                }
            }
        }
        return me;
    }
}
