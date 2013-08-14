package com.cloudstone.cloudhand.logic;

import com.cloudstone.cloudhand.data.URL;

/**
 * 
 * @author xhc
 *
 */
public class URLLogic extends BaseLogic {
    public URL getURL() {
        return pref().getURL();
    }
    
    public void saveURL(URL url) {
        pref().setURL(url);
    }
    
    public boolean isSetting() {
        return getURL() != null;
    }
    
    private static URLLogic me;
    
    private URLLogic() {
    }
    
    public static URLLogic getInstance() {
        if (me == null) {
            synchronized (URLLogic.class) {
                if (me == null) {
                    me = new URLLogic();
                }
            }
        }
        return me;
    }
}
