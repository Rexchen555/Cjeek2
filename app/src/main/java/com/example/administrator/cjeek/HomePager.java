package com.example.administrator.cjeek;

import android.app.Activity;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/12.
 */

public class HomePager extends BasePager {
    public HomePager(Activity activity){
        super(activity);
    }
    private  ArrayList<BaseMenuDetailPager> mMenuDetailPagers;
    private NewsMenu mNewsdata;
    @Override
    public void initData() {
        //TextView view = new TextView(mActivity);
       // view.setText("Home");
        //view.setTextColor(Color.RED);
       // view.setTextSize(22);
       // view.setGravity(Gravity.CENTER);
       // flContent.addView(view);
        tvTitle.setText("Cjeek");
        btnMenu.setVisibility(View.VISIBLE);
        //请求服务器获取数据
        //开元框架：XUtils
        String cache = CacheUtils.getCache(GlobalConstants.CATEGORY_URL,mActivity);
        if(!TextUtils.isEmpty(cache)){
            processData(cache);

        }
        getDataFromServer();
    }

    private void getDataFromServer() {
        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET,GlobalConstants.CATEGORY_URL , new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                //请求成功
                String result = responseInfo.result;//获得服务器访问结果
                Log.i(result,result);
                processData(result);
                CacheUtils.setCache(GlobalConstants.CATEGORY_URL,result,mActivity);
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                //请求失败
                error.printStackTrace();
                Toast.makeText(mActivity,msg,Toast.LENGTH_SHORT).show();
            }
        });
    }
//解析数据
    private void processData(String json) {
        Gson gson = new Gson();
         mNewsdata = gson.fromJson(json,NewsMenu.class);
        //获取侧边栏对象
        MainActivity mainUI = (MainActivity) mActivity;
        LeftMenuFragment fragment =  mainUI.getLeftMenuFragment();
        // 给侧边栏设置数据
        fragment.setMenuData(mNewsdata.data);
        //初始化四个菜单详情页
        mMenuDetailPagers = new ArrayList<BaseMenuDetailPager>();
        mMenuDetailPagers.add(new NewsMenuDetailPager(mActivity,mNewsdata.data.get(0).children));
        mMenuDetailPagers.add(new PhotosMenuDetailPager(mActivity,btnPhoto));
        setCurrentDetailPager(0);
    }

    public  void setCurrentDetailPager(int position){
        BaseMenuDetailPager pager =  mMenuDetailPagers.get(position);
        View view = pager.mRootView;
        flContent.removeAllViews();
        flContent.addView(view);
        pager.initData();


        // 如果是组图页面, 需要显示切换按钮
        if (pager instanceof PhotosMenuDetailPager) {
            btnPhoto.setVisibility(View.VISIBLE);
        } else {
            // 隐藏切换按钮
            btnPhoto.setVisibility(View.GONE);
        }
    }

}
