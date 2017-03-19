package com.example.administrator.cjeek;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/14.
 */

public class TabDetailPager extends BaseMenuDetailPager {
    private NewsMenu.NewsTabData mTabData;// 单个页签的网络数据
    private String mUrl;

    @ViewInject(R.id.vp_top_news)
    private TopNewsViewPager mViewPager;
;
    @ViewInject(R.id.tv_title)
    private TextView tvTitile;
    @ViewInject(R.id.indicator2)
    private CirclePageIndicator mIndicator2;
    @ViewInject(R.id.indicator)
    private CirclePageIndicator mIndicator;
    @ViewInject(R.id.lv_list)
    private PullToRefreshListView lvList;
   // private TextView view;
   private ArrayList<NewTabBean.TopNews> mTopNews;
    private ArrayList<NewTabBean.NewsData> mNewsList;
    public  TabDetailPager(Activity activity, NewsMenu.NewsTabData newsTabData){
        super(activity);
        mTabData = newsTabData;
        mUrl = GlobalConstants.SERVER_URL + mTabData.url;
    }

    @Override
    public View initView() {
        // 要给帧布局填充布局对象

        View view = View.inflate(mActivity, R.layout.pager_tab_detail, null);
        ViewUtils.inject(this, view);

        // 给listview添加头布局
       // View mHeaderView = View.inflate(mActivity, R.layout.list_item_header,
               // null);
        //ViewUtils.inject(this, mHeaderView);// 此处必须将头布局也注入
        View mHeaderView = View.inflate(mActivity, R.layout.list_item_head,
                null);
        ViewUtils.inject(this, mHeaderView);// 此处必须将头布局也注入
        lvList.addHeaderView(mHeaderView);
        lvList.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDataFromServer();
            }
        });
        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                int headerViewsCount = lvList.getHeaderViewsCount();// 获取头布局数量
                position = position - headerViewsCount;// 需要减去头布局的占位
                System.out.println("第" + position + "个被点击了");

                NewTabBean.NewsData news = mNewsList.get(position);

                // read_ids: 1101,1102,1105,1203,
                String readIds = prefUtils.getString(mActivity, "read_ids", "");

                if (!readIds.contains(news.id + "")) {// 只有不包含当前id,才追加,
                    // 避免重复添加同一个id
                    readIds = readIds + news.id + ",";// 1101,1102,
                    prefUtils.setString(mActivity, "read_ids", readIds);
                }

                // 要将被点击的item的文字颜色改为灰色, 局部刷新, view对象就是当前被点击的对象
                TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
                tvTitle.setTextColor(Color.GRAY);
                // mNewsAdapter.notifyDataSetChanged();//全局刷新, 浪费性能

                // 跳到新闻详情页面
                Intent intent = new Intent(mActivity, NewsDetailActivity.class);
                intent.putExtra("url", news.url);
                mActivity.startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void initData() {
        String cache = CacheUtils.getCache(mUrl, mActivity);
        if (!TextUtils.isEmpty(cache)) {
            processData(cache);
        }
        getDataFromServer();

    }

    private void getDataFromServer() {
        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, mUrl, new RequestCallBack<String>() {

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                processData(result);
                CacheUtils.setCache(mUrl,result,mActivity);
                //收起控件
                lvList.onRefreshComplete(true);
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                // 请求失败
                error.printStackTrace();
                Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
                lvList.onRefreshComplete(false);
            }
        });
    }
    protected void processData(String result) {
        Gson gson = new Gson();
        NewTabBean newsTabBean = gson.fromJson(result, NewTabBean.class);
        mTopNews =  newsTabBean.data.topnews;
        if (mTopNews != null) {
            mViewPager.setAdapter(new TopNewsAdapter());
            mIndicator2.setViewPager(mViewPager);
            mIndicator2.setSnap(true);
            mIndicator2.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    //更新头条新闻的标题
                    NewTabBean.TopNews topNews = mTopNews.get(position);
                    tvTitile.setText(topNews.title);
                }

                @Override
                public void onPageSelected(int position) {

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            tvTitile.setText(mTopNews.get(0).title);

                }
         mNewsList = newsTabBean.data.news;
        if (mNewsList !=null){
            NewsAdapter mNewsAdapter = new NewsAdapter();
            lvList.setAdapter(mNewsAdapter);
        }

    }
    class TopNewsAdapter extends PagerAdapter{
        private BitmapUtils mBitmapUtils;
        public TopNewsAdapter(){
            mBitmapUtils = new BitmapUtils(mActivity);

        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public int getCount() {
            return mTopNews.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView view = new ImageView(mActivity);
            view.setImageResource(R.drawable.topnews_item_default);
            view.setScaleType(ImageView.ScaleType.FIT_XY);// 设置图片缩放方式, 宽高填充父控件
            String imageUrl = mTopNews.get(position).topimage;// 图片下载链接
            mBitmapUtils.display(view, imageUrl);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }
    }
class NewsAdapter extends BaseAdapter{
    private BitmapUtils mBitmapUtils;

    public NewsAdapter() {
        mBitmapUtils = new BitmapUtils(mActivity);
        mBitmapUtils.configDefaultLoadingImage(R.drawable.news_pic_default);
    }
    @Override
    public int getCount() {
        return mNewsList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mActivity, R.layout.list_item_news,
                    null);
            holder = new ViewHolder();
            holder.ivIcon = (ImageView) convertView
                    .findViewById(R.id.iv_icon);
            holder.tvTitle = (TextView) convertView
                    .findViewById(R.id.tv_title);
            holder.tvDate = (TextView) convertView
                    .findViewById(R.id.tv_date);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        NewTabBean.NewsData news = (NewTabBean.NewsData)getItem(position);
        holder.tvTitle.setText(news.title);
        holder.tvDate.setText(news.pubdate);
        String readIds = prefUtils.getString(mActivity, "read_ids", "");
        // 根据本地记录来标记已读未读
        if (readIds.contains(news.id + "")) {
            holder.tvTitle.setTextColor(Color.GRAY);
        } else {
            holder.tvTitle.setTextColor(Color.BLACK);
        }
        mBitmapUtils.display(holder.ivIcon, news.listimage);
        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return mNewsList.get(position);
    }
}
    static class ViewHolder {
        public ImageView ivIcon;
        public TextView tvTitle;
        public TextView tvDate;
    }

}
