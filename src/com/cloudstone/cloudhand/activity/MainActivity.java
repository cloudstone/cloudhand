/**
 * @(#)LoginActivity.java, 2013-7-6. 
 * 
 */
package com.cloudstone.cloudhand.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.cloudstone.cloudhand.R;

/**
 * @author xuhongchuan
 *
 */
public class MainActivity extends BaseActivity {
    private Button btnLogin;
    private Button btnOpenTable;
    private Button btnTabelInfo;
    private Button btnSetting;
    private AutoCompleteTextView textView;

    private String[]  items={"lorem", "ipsum", "dolor", "sit", "amet", "consectetuer", "adipiscing", "elit", "morbi", "vel", "ligula", "vitae", "arcu", "aliquet", "mollis", "etiam", "vel", "erat", "placerat", "ante", "porttitor", "sodales", "pellentesque", "augue", "purus"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        btnLogin = (Button)findViewById(R.id.btnLogin);
        btnOpenTable = (Button)findViewById(R.id.btnOpenTable);
        btnTabelInfo = (Button)findViewById(R.id.btnTabelInfo);
        btnSetting= (Button)findViewById(R.id.btnSetting);
        
        btnLogin.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                View view = inflater.inflate(R.layout.dialog_login, null);

                AlertDialog dialog = new AlertDialog.Builder(MainActivity.this).create();  
                dialog.setView(view);

                textView = (AutoCompleteTextView)view.findViewById(R.id.login_dialog_userName);
                textView.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_dropdown_item_1line, items));

                dialog.show();
            }
        });
    }
}
