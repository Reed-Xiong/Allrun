package com.example.reedxiong.allrun.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.reedxiong.allrun.R;
import com.example.reedxiong.allrun.entity.UserEntity;

import java.util.ArrayList;

/**
 * Created by Reed.Xiong on 2016/7/18.
 */
public class SportAdapter extends BaseAdapter {
    Context context;
    ArrayList<UserEntity> userEntityArrayList;

    public SportAdapter(Context context,ArrayList<UserEntity> list){
        this.context=context;
        userEntityArrayList=list;
    }
    @Override
    public int getCount() {
        return userEntityArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return userEntityArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //but you need to judge the view is not null
        ViewHolder viewHolder=null;
        if(view==null){
            view=View.inflate(context, R.layout.nearby_user_item,null);
            viewHolder=new ViewHolder();
            viewHolder.textView=(TextView)view.findViewById(R.id.tv_nearby_user_item_name);
            view.setTag(viewHolder);
        }
        else{
            viewHolder=(ViewHolder)view.getTag();
        }
        viewHolder.textView.setText("");
        return view;
    }
    class ViewHolder{
        ImageView imageView;
        TextView textView;
    }
}
