package com.example.reedxiong.allrun.view;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.reedxiong.allrun.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiscoverFragment extends Fragment {


    public DiscoverFragment() {
        // Required empty public constructor
    }

    public void doFindFriend(View v){
        Intent intent=new Intent(getActivity(),ClubActivity.class);
        startActivity(intent);
    }
    public void doNearByAction(View v){
        Intent intent=new Intent(getActivity(),NearbyUserActivity.class);
        startActivity(intent);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_discover, container, false);
    }

}
