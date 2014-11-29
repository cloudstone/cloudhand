/**
 * @(#)BaseDialogFragment.java, Jul 27, 2013. 
 * 
 */
package com.cloudstone.cloudhand.dialog;

import android.support.v4.app.DialogFragment;

import com.cloudstone.cloudhand.logic.MiscLogic;
import com.cloudstone.cloudhand.logic.UserLogic;

/**
 * @author xuhongfeng
 *
 */
public class BaseAlertDialogFragment extends DialogFragment {

    
    protected UserLogic getUserLogic() {
        return UserLogic.getInstance();
    }
    
    protected MiscLogic getMiscLogic() {
        return MiscLogic.getInstance();
    }
}
