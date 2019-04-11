package com.bwie.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bwie.R;
import com.bwie.bean.NewsBean;

import java.util.List;

public class MyAdapter extends BaseAdapter {

    private Context context;
    private List<NewsBean.Item> list;
    private final int EVEN = 1;
    private final int OJJ = 2;

    public MyAdapter(Context context, List<NewsBean.Item> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int viewType = getItemViewType(position);
        switch (viewType){
            case EVEN:
                ViewHolder viewHolder = null;
                if(convertView == null){
                    viewHolder = new ViewHolder();
                    convertView = LayoutInflater.from(context).inflate(R.layout.item1, null);
                    viewHolder.img = convertView.findViewById(R.id.img);
                    viewHolder.name = convertView.findViewById(R.id.titles);
                    viewHolder.design = convertView.findViewById(R.id.contents);
                    convertView.setTag(viewHolder);
                }else {
                    viewHolder = (ViewHolder) convertView.getTag();
                }
                if(list.get(position).imageUrl != null){
                    Glide.with(context).load(list.get(position).imageUrl).apply(RequestOptions.centerCropTransform()).into(viewHolder.img);
                }
                if(!TextUtils.isEmpty(list.get(position).name)){
                    viewHolder.name.setText(list.get(position).name);
                }
                if(!TextUtils.isEmpty(list.get(position).summary)){
                    viewHolder.design.setText(list.get(position).summary);
                }
                break;
            case OJJ:
                ViewHolder viewHolder1 = null;
                if(convertView == null){
                    viewHolder1 = new ViewHolder();
                    convertView = LayoutInflater.from(context).inflate(R.layout.item2, null);
                    viewHolder1.img = convertView.findViewById(R.id.img);
                    viewHolder1.name = convertView.findViewById(R.id.titles);
                    viewHolder1.design = convertView.findViewById(R.id.contents);
                    convertView.setTag(viewHolder1);
                }else {
                    viewHolder1 = (ViewHolder) convertView.getTag();
                }
                if(list.get(position).imageUrl != null){
                    Glide.with(context).load(list.get(position).imageUrl).apply(RequestOptions.centerCropTransform()).into(viewHolder1.img);
                }
                if(!TextUtils.isEmpty(list.get(position).name)){
                    viewHolder1.name.setText(list.get(position).name);
                }
                if(!TextUtils.isEmpty(list.get(position).summary)){
                    viewHolder1.design.setText(list.get(position).summary);
                }
                break;

        }
        return convertView;
    }

    class ViewHolder{
        ImageView img;
        TextView name;
        TextView design;
    }

    //获取条目类型
    @Override
    public int getItemViewType(int position) {
        //如果为偶数 则是一种形式 奇数相反
        if(position % 2 == 0){
            return EVEN;
        }else {
            return OJJ;
        }
    }

    //获取天目类型数量
    @Override
    public int getViewTypeCount() {
        return 2 + 1;
    }
}
