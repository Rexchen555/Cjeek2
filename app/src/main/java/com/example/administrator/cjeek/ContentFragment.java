package com.example.administrator.cjeek;

import java.util.ArrayList;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;


import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

/**
 * 主页面fragment
 * 
 * @author Kevin
 * @date 2015-10-18
 */
public class ContentFragment extends BaseFragment {

	private NoScrollViewPager mViewPager;
	private RadioGroup rgGroup;

	private ArrayList<BasePager> mPagers;// 五个标签页的集合

	@Override
	public View initView() {
		View view = View.inflate(mActivity, R.layout.fragment_content, null);
		mViewPager = (NoScrollViewPager) view.findViewById(R.id.vp_content);
		rgGroup = (RadioGroup) view.findViewById(R.id.rg_group);
		return view;
	}

	@Override
	public void initData() {
		mPagers = new ArrayList<BasePager>();
		mPagers.add(new HomePager(mActivity));
		mPagers.add(new SettingPager(mActivity));
		mViewPager.setAdapter(new ContentAdapter());
		rgGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId){
					case R.id.rb_Home:
						mViewPager.setCurrentItem(0, false);
						break;

					case R.id.rb_setting:
						// 设置
						mViewPager.setCurrentItem(4, false);
						break;

					default:
						break;
				}
			}
		});
		mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
				BasePager pager = mPagers.get(position);
				pager.initData();
				if ( position == mPagers.size() - 1) {
					// 首页和设置页要禁用侧边栏
					setSlidingMenuEnable(false);
				} else {
					// 其他页面开启侧边栏
					setSlidingMenuEnable(true);
				}
			}

			@Override
			public void onPageSelected(int position) {

			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});
		// 手动加载第一页数据
		mPagers.get(0).initData();
		// 首页禁用侧边栏
		setSlidingMenuEnable(false);
	}

	private void setSlidingMenuEnable(boolean enable) {
		// 获取侧边栏对象
		MainActivity mainUI = (MainActivity) mActivity;
		SlidingMenu slidingMenu = mainUI.getSlidingMenu();
		if(enable){
			slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		}else {
			slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		}
	}

	class ContentAdapter extends PagerAdapter{
		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		@Override
		public int getCount() {
			return mPagers.size();
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			BasePager pager = mPagers.get(position);
			View view = pager.mRootView;// 获取当前页面对象的布局
			//pager.initData();
			container.addView(view);
			return view;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}
	}

	public HomePager getNewsCenterPager(){
		HomePager pager = (HomePager) mPagers.get(0);
		return pager;
	}


}
