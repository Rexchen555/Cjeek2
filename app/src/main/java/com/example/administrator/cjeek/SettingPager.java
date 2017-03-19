package com.example.administrator.cjeek;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;


/**
 * 设置
 * 
 * @author Kevin
 * @date 2015-10-18
 */
public class SettingPager extends BasePager {

	public SettingPager(Activity activity) {
		super(activity);
	}
	private View view;
	@Override
	public void initData() {


		// 要给帧布局填充布局对象
		view = View.inflate(mActivity,R.layout.setting_item_menu,null);
		initUpdate();


		flContent.addView(view);

		// 修改页面标题
		tvTitle.setText("Setting");

		// 隐藏菜单按钮
		btnMenu.setVisibility(View.GONE);
	}

	private void initUpdate() {
		final SettingItemView siv_update = (SettingItemView)view.findViewById(R.id.siv_update);
		boolean update = SpUtil.getBoolean(mActivity,GlobalConstants.OPEN_UPDATE,false);
		siv_update.setCheck(update);
		siv_update.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				boolean isCheck = siv_update.isCheck();
				siv_update.setCheck(!isCheck);
				SpUtil.putBoolean(mActivity,GlobalConstants.OPEN_UPDATE,!isCheck);
			}
		});
	}

}
