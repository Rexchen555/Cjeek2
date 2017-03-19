package com.example.administrator.cjeek;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2017/3/8.
 */

public class prefUtils {
    public static boolean getBoolean(Context context, String key, boolean defValue){
        SharedPreferences sp = context.getSharedPreferences("config",context.MODE_PRIVATE);
        return sp.getBoolean(key,defValue);
    }
    public static void setBoolean(Context context,String key,boolean defValue){
        SharedPreferences sp = context.getSharedPreferences("config",context.MODE_PRIVATE);
        sp.edit().putBoolean(key,defValue).commit();
    }
    public static void setString(Context context,String key,String defValue){
        SharedPreferences sp = context.getSharedPreferences("config",context.MODE_PRIVATE);
        sp.edit().putString(key,defValue).commit();
    }
    public static String getString(Context context,String key,String defValue){
        SharedPreferences sp = context.getSharedPreferences("config",context.MODE_PRIVATE);
        return sp.getString(key,defValue);
    }
    public static void setInt(Context context,String key,int defValue){
        SharedPreferences sp = context.getSharedPreferences("config",context.MODE_PRIVATE);
        sp.edit().putInt(key,defValue).commit();
    }
    public static int getString(Context context,String key,int defValue){
        SharedPreferences sp = context.getSharedPreferences("config",context.MODE_PRIVATE);
        return sp.getInt(key,defValue);
    }
}
