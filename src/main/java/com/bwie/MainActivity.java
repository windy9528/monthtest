package com.bwie;

import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bwie.adapter.MyAdapter;
import com.bwie.bean.ImageBean;
import com.bwie.bean.NewsBean;
import com.bwie.util.AsyncHttpClient;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button shows;
    private ImageView head;
    private TextView tv_indexs;
    private DrawerLayout drawerLayout;
    private Banner banner;
    private List<String> imageList = new ArrayList<>();
    private String imageUrl = "http://172.17.8.100/small/commodity/v1/bannerShow";
    private String showUrl = "http://172.17.8.100/movieApi/movie/v1/findHotMovieList?count=5&page=";
    private PullToRefreshListView pullToRefreshListView;
    private List<NewsBean.Item> list = new ArrayList<>();
    int page = 1;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        shows = findViewById(R.id.shows);
        head = findViewById(R.id.head);
        tv_indexs = findViewById(R.id.indexs);
        drawerLayout = findViewById(R.id.drawer_layout);
        banner = findViewById(R.id.banner);
        pullToRefreshListView = findViewById(R.id.lv);

        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //下拉刷新
                page = 1;
                initData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                //上拉加载更多
                page++;
                initData();
            }
        });

        //获取展示数据
        initData();
        myAdapter = new MyAdapter(this,list);
        pullToRefreshListView.setAdapter(myAdapter);

        //图片填充
        fillImages();

        //打开抽屉
        openDrop();

        //吐司
        handIndex();

        //处理轮播图
        iamgePlayer();

    }

    //获取条目数据啊
    private void initData() {
        AsyncHttpClient.getDataFromServer(showUrl + page, new AsyncHttpClient.AsyncCallBack() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                NewsBean newsBean = gson.fromJson(result, NewsBean.class);
                if(page == 1){
                    list.clear();
                }
                list.addAll(newsBean.result);
                //刷新适配器
                myAdapter.notifyDataSetChanged();
                pullToRefreshListView.onRefreshComplete();
            }

            @Override
            public void onFaild() {
                Toast.makeText(MainActivity.this,"访问失败",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void handIndex() {
        tv_indexs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"八维电商",Toast.LENGTH_LONG).show();
            }
        });
    }

    //轮播图
    private void iamgePlayer() {
        AsyncHttpClient.getDataFromServer(imageUrl, new AsyncHttpClient.AsyncCallBack() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                ImageBean imageBean = gson.fromJson(result, ImageBean.class);
                List<ImageBean.Item> images = imageBean.result;
                if(imageList.size() == 0){
                    for(int i = 0;i < images.size();i++){
                        imageList.add(images.get(i).imageUrl);
                    }
                }
                banner.isAutoPlay(true);
                banner.setImages(imageList);
                banner.setDelayTime(2000);
                banner.setImageLoader(new ImageLoader() {
                    @Override
                    public void displayImage(Context context, Object path, ImageView imageView) {
                        Glide.with(context).load(path).into(imageView);
                    }
                }).start();
            }

            @Override
            public void onFaild() {
                Toast.makeText(MainActivity.this,"访问失败",Toast.LENGTH_LONG).show();
            }
        });
    }

    //抽屉
    private void openDrop() {
        shows.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //如果不是打开状态 则打开
                if(!drawerLayout.isDrawerOpen(Gravity.START)){
                    drawerLayout.openDrawer(Gravity.START);
                }else {
                    drawerLayout.closeDrawer(Gravity.START);
                }
            }
        });
    }

    //头像
    private void fillImages() {
        Glide
            .with(this)
            .load(R.drawable.head)
            .apply(RequestOptions.centerCropTransform())
            .into(head);
    }
}
