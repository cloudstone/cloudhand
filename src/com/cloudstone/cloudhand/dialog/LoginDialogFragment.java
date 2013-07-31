/**
 * @(#)LoginDialogFragment.java, Jul 27, 2013. 
 * 
 */
package com.cloudstone.cloudhand.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.cloudstone.cloudhand.R;

/**
 * @author xuhongfeng
 *
 */
public class LoginDialogFragment extends BaseAlertDialogFragment {
    private AutoCompleteTextView textView;
    
    private String[]  items={"lorem", "ipsum", "dolor", "sit", "amet", "consectetuer", "adipiscing", "elit", "morbi", "vel", "ligula", "vitae", "arcu", "aliquet", "mollis", "etiam", "vel", "erat", "placerat", "ante", "porttitor", "sodales", "pellentesque", "augue", "purus"};
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, 0);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_login, container, false);
        textView = (AutoCompleteTextView)view.findViewById(R.id.login_dialog_userName);
        textView.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, items));
        return view;
    }
}
