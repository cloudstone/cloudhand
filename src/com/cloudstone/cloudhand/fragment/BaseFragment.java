package com.cloudstone.cloudhand.fragment;

import android.support.v4.app.Fragment;

import com.cloudstone.cloudhand.logic.MiscLogic;
import com.cloudstone.cloudhand.logic.UserLogic;

/**
 * 
 * @author xhc
 *
 */
public class BaseFragment extends Fragment {

    
    protected UserLogic getUserLogic() {
        return UserLogic.getInstance();
    }
    
    protected MiscLogic getMiscLogic() {
        return MiscLogic.getInstance();
    }
}
