package com.example.administrator.cjeek;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/3/19.
 */

public class SettingItemView extends RelativeLayout {
    private CheckBox cb_box;
    private TextView tv_des;
    private String mDestitle;
    private String mDesoff;
    private String mDeson;

    public SettingItemView(Context context) {
        this(context,null);
    }

    public SettingItemView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SettingItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        View.inflate(context, R.layout.setting_item_view, this);
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_des = (TextView) findViewById(R.id.tv_des);
        cb_box = (CheckBox) findViewById(R.id.cb_box);

    }
    public boolean isCheck(){
        //由checkBox的选中结果,决定当前条目是否开启
        return cb_box.isChecked();
    }
    public void setCheck(boolean isCheck){
        //当前条目在选择的过程中,cb_box选中状态也在跟随(isCheck)变化
        cb_box.setChecked(isCheck);
        if(isCheck){
            //开启
            tv_des.setText("update open");
        }else{
            //关闭
            tv_des.setText("update off");
        }
    }
}
