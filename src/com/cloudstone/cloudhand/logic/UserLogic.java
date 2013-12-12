/**
 * @(#)Logic.java, Aug 12, 2013. 
 * 
 */
package com.cloudstone.cloudhand.logic;

import android.content.Context;

import com.cloudstone.cloudhand.data.User;
import com.cloudstone.cloudhand.storage.UserStorage;

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
        pref().cookiePreference().edit().clear().commit();
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
    
    //向数据库插入用户名
    public void insertUser(Context context, String[] params) {
        UserStorage userStorage = new UserStorage(context);
        userStorage.insertUser(params);
    }
    
    //清空数据库的用户名
    public void clearUser(Context context) {
        UserStorage userStorage = new UserStorage(context);
        userStorage.clearUser();
    }
    
    //获取数据库的用户名
    public String[] getAllUser(Context context) {
        UserStorage userStorage = new UserStorage(context);
        return userStorage.getAllUser();
    }
    
//    //创建数据库的用户表
//    public void createUserTable(Context context) {
//        UserStorage userStorage = new UserStorage(context);
//        userStorage.createUserTable();
//    }
    
    //删除数据库的用户表
    public void dropUserTable(Context context) {
        UserStorage userStorage = new UserStorage(context);
        userStorage.dropUserTable();
    }
}
