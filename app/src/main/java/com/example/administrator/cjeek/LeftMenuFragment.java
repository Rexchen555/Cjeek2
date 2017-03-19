package com.example.administrator.cjeek;

import java.util.ArrayList;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;


import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;


/**
 * 侧边栏fragment
 * 
 * @author Kevin
 * @date 2015-10-18
 */
public class LeftMenuFragment extends BaseFragment {
    @ViewInject(R.id.lv_list)
	private ListView lvList;
	private ArrayList<NewsMenu.NewsMenuData> mNewsMenuData;
	private int mCurrentPos;
	private LeftMenuAdapter mAdapter;
	@Override
	public View initView() {
		View view = View.inflate(mActivity, R.layout.fragment_left_menu, null);
		//lv_list = (ListView) view.findViewById(R.id.lv_list);
		ViewUtils.inject(this,view);
		return view;
	}

	@Override
	public void initData() {

	}
	public void setMenuData(ArrayList< NewsMenu.NewsMenuData> data){
		mCurrentPos =0;
		mNewsMenuData = data;
		mAdapter = new LeftMenuAdapter();
		lvList.setAdapter(mAdapter);
		lvList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				mCurrentPos = position;
				mAdapter.notifyDataSetChanged();
				toggle();
				setCurrentDetailPager(position);
			}
		});
	}

	private void toggle() {
		MainActivity mainUI = (MainActivity) mActivity;
		SlidingMenu slidingMenu = mainUI.getSlidingMenu();
		slidingMenu.toggle();
	}
	public void setCurrentDetailPager(int position){
		MainActivity mainUI =(MainActivity) mActivity;
		ContentFragment fragment = mainUI.getContentFragment();
		HomePager homePager = fragment.getNewsCenterPager();
		homePager.setCurrentDetailPager(position);
	}

	class LeftMenuAdapter extends BaseAdapter{
		@Override
		public int getCount() {
			return mNewsMenuData.size();
		}

		@Override
		public NewsMenu.NewsMenuData getItem(int position) {
			return mNewsMenuData.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = View.inflate(mActivity,R.layout.list_item_left_menu,null);
			TextView tv_menu = (TextView) view.findViewById(R.id.tv_menu);
			NewsMenu.NewsMenuData item = getItem(position);
			tv_menu.setText(item.title);
			if(position == mCurrentPos){
				tv_menu.setEnabled(true);
			}else{
				tv_menu.setEnabled(false);
			}
			return view;
		}
	}


}
