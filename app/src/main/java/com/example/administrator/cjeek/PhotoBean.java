package com.example.administrator.cjeek;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/18.
 */

public class PhotoBean {
    public PhotosData data;

    public class PhotosData {
        public ArrayList<PhotoNews> news;
    }

    public class PhotoNews {
        public int id;
        public String listimage;
        public String title;
    }
}
