package com.bwie.bean;

import java.util.List;

public class NewsBean {

    public List<Item> result;

    public static class Item{
        public String name;  //获取电影名称
        public String imageUrl;  //获取图片
        public String summary;  //获取简介
    }
}
