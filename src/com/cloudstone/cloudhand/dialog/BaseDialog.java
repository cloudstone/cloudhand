package com.cloudstone.cloudhand.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cloudstone.cloudhand.R;

/**
 * 
 * @author xhc
 *
 */
public class BaseDialog extends Dialog {
    private ImageView ivIcon;
    private TextView tvMessage;
    private Button button1;
    private Button button2;
    
    public BaseDialog(Context context) {
        super(context, R.style.Dialog_base);
        setContentView(R.layout.dialog_base);
        ivIcon = (ImageView)findViewById(R.id.iv_icon);
        tvMessage = (TextView)findViewById(R.id.tv_message);
        button1 = (Button)findViewById(R.id.btn_button1);
        button2 = (Button)findViewById(R.id.btn_button2);
    }

    //设置图标
    public void setIcon(int icon) {
        ivIcon.setBackgroundResource(icon);
    }
    
    //设置文本
    public void setMessage(String message) {
        tvMessage.setText(message);
    }
    
    public void setMessage(int message) {
        tvMessage.setText(getContext().getResources().getString(message));
    }
    
    //添加按钮
    public void addButton(String text, final DialogInterface.OnClickListener onClickListener) {
        if(button1.getVisibility() != View.VISIBLE) {
            button1.setVisibility(View.VISIBLE);
            button1.setText(text);
            button1.setOnClickListener(new View.OnClickListener() {
                
                @Override
                public void onClick(View v) {
                    onClickListener.onClick(BaseDialog.this, button1.getId());
                }
            });
        } else {
            button2.setVisibility(View.VISIBLE);
            button2.setText(text);
            button2.setOnClickListener(new View.OnClickListener() {
                
                @Override
                public void onClick(View v) {
                    onClickListener.onClick(BaseDialog.this, button2.getId());
                }
            });
        }
    }
    
    //添加按钮
    public void addButton(int text, final DialogInterface.OnClickListener onClickListener) {
        if(button1.getVisibility() != View.VISIBLE) {
            button1.setVisibility(View.VISIBLE);
            button1.setText(getContext().getResources().getString(text));
            button1.setOnClickListener(new View.OnClickListener() {
                
                @Override
                public void onClick(View v) {
                    onClickListener.onClick(BaseDialog.this, button1.getId());
                }
            });
        } else {
            button2.setVisibility(View.VISIBLE);
            button2.setText(getContext().getResources().getString(text));
            button2.setOnClickListener(new View.OnClickListener() {
                
                @Override
                public void onClick(View v) {
                    onClickListener.onClick(BaseDialog.this, button2.getId());
                }
            });
        }
    }
    
  //获取按钮
    public Button get(int index) {
        if(index == 0) {
            return button1;
        } else if(index == 1) {
            return button2;
        } else {
            return null;
        }
    }
    
    @Override
    public void show() {
        if(button2.getVisibility() != View.VISIBLE) {
            //设置按钮宽度
            android.view.ViewGroup.LayoutParams editPara = button1.getLayoutParams();
            editPara.width = 150;
            button1.setLayoutParams(editPara);
        }
        super.show();
    }

}