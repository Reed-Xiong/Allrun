package com.example.reedxiong.allrun.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.reedxiong.allrun.R;
import com.example.reedxiong.allrun.TApplication;
import com.example.reedxiong.allrun.entity.UserEntity;
import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;

/**
 * Created by Reed.Xiong on 2016/7/18.
 */
public class NearbyUserAdapter extends BaseAdapter {
    Context context;
    ArrayList<UserEntity> userList;
    public NearbyUserAdapter(Context context,ArrayList<UserEntity> userList){
        this.context=context;
        this.userList=userList;
    }
    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int i) {
        return userList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder=null;
        if(view==null){
            view=View.inflate(context, R.layout.nearby_user_item,null);

           viewHolder= new ViewHolder();
            viewHolder.imageView=(ImageView)view.findViewById(R.id.iv_nearby_use_item_userIcon);
            viewHolder.textView=(TextView)view.findViewById(R.id.tv_nearby_user_item_name);
            view.setTag(viewHolder);
        }
        else {
            viewHolder=(ViewHolder)view.getTag();
        }
        viewHolder.textView.setText(userList.get(i).getUsername());
        // get the image
        //1.connect the tomact
        // 2. downlaod the imgae
        // 3.set the image
        //viewHolder.imageView.setImageBitmap(null);
        // 实际工作中，服务器返回的url要包含域名
        BitmapUtils bitmapUtils=new BitmapUtils(context);
        //bitmapUtils.display(T container,String uri);
        String imageUrl = "http://" + TApplication.host + ":8080"
                + userList.get(i).getIconUrl();
        try {

            bitmapUtils.display(viewHolder.imageView, imageUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  view;
    }
    class ViewHolder{
        TextView textView;
        ImageView imageView;
    }
}
