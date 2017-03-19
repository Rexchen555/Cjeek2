package com.example.administrator.cjeek;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;

import static android.R.attr.onClick;


/**
 * 菜单详情页-新闻
 * 
 * @author Kevin
 * @date 2015-10-18
 */
public class NewsMenuDetailPager extends BaseMenuDetailPager implements ViewPager.OnPageChangeListener{
	private ArrayList<NewsMenu.NewsTabData> mTabData;//页签网络数据
	@ViewInject(R.id.vp_news_menu_detail)
	private ViewPager mViewPager;
	private  ArrayList<TabDetailPager> mPagers;
	@ViewInject(R.id.indicator)
	private  TabPageIndicator mIndicator;
	public NewsMenuDetailPager(Activity activity, ArrayList<NewsMenu.NewsTabData> children) {
		super(activity);
		mTabData = children;
	}

	@Override
	public void onPageSelected(int position) {
		if (position ==0){
			setSlidingMenuEnable(true);
		}else{
			setSlidingMenuEnable(false);
		}
	}

	private void setSlidingMenuEnable(boolean enable) {
		MainActivity mainUI = (MainActivity)mActivity;
		SlidingMenu slidingMenu = mainUI.getSlidingMenu();
		if(enable){
			slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		}else{
			slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		}
	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

	}

	@Override
	public void onPageScrollStateChanged(int state) {

	}

	@Override
	public View initView() {
		View view = View.inflate(mActivity,R.layout.pager_news_menu_detail,null);

		ViewUtils.inject(this,view);
		return view;
	}

	@Override
	public void initData() {
		//初始化页签
		mPagers = new ArrayList<TabDetailPager>();
		for (int i = 0; i < mTabData.size(); i++) {
			TabDetailPager pager = new TabDetailPager(mActivity,mTabData.get(i));
			mPagers.add(pager);
		}

		mViewPager.setAdapter(new NewsMenuDetailAdapter());
		mIndicator.setViewPager(mViewPager);
		mIndicator.setOnPageChangeListener(this);
	}

	class NewsMenuDetailAdapter extends PagerAdapter{
		@Override
		public CharSequence getPageTitle(int position) {
			NewsMenu.NewsTabData data = mTabData.get(position);
			return data.title;
		}

		@Override
		public int getCount() {
			return mPagers.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {

			TabDetailPager pager = mPagers.get(position);

			View view = pager.mRootView;
			container.addView(view);

			pager.initData();

			return view;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View)object);

		}
	}


}
