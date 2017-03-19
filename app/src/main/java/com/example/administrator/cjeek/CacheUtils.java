package com.example.administrator.cjeek;

import android.content.Context;

/**
 * Created by Administrator on 2017/3/13.
 */

public class CacheUtils {

    public static void setCache(String url, String json, Context ctx){
        prefUtils.setString(ctx,url,json);
    }

    public static String getCache(String url,Context ctx){
        return prefUtils.getString(ctx,url,null);
    }

}
