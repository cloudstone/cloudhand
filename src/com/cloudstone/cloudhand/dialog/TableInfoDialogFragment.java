package com.cloudstone.cloudhand.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cloudstone.cloudhand.R;

/**
 * 
 * @author xhc
 *
 */
public class TableInfoDialogFragment extends BaseAlertDialogFragment {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, 0);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_table_info, container, false);
        return view;
    }
    
}