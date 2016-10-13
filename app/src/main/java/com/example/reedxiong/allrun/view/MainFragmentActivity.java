package com.example.reedxiong.allrun.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Button;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.example.reedxiong.allrun.R;

public class MainFragmentActivity extends FragmentActivity implements View.OnClickListener {

    Button[] btnArray=new Button[4];
    private DiscoverFragment discoverFragment;
    private MessageFragment messageFragment;
    private SportFragment sportFragment;
    private MeFragment meFragment;
    private Fragment[] fragmentArray;
    int currentIndex=0;
    int selectedIndex;
    private FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_fragment);
        setupView();
        // set the listener
    }

    private void setupView() {
        btnArray[0]=(Button)findViewById(R.id.btn_main_fragment_sport);
        btnArray[1]=(Button)findViewById(R.id.btn_main_fragment_discover);
        btnArray[2]=(Button)findViewById(R.id.btn_main_fragment_message);
        btnArray[3]=(Button)findViewById(R.id.btn_main_fragment_me);
        btnArray[0].setSelected(true);
        //
        for (Button button:btnArray
             ) {
            button.setOnClickListener(this);
        }

        sportFragment = new SportFragment();
        discoverFragment = new DiscoverFragment();
        messageFragment = new MessageFragment();
        meFragment = new MeFragment();
        fragmentArray = new Fragment[] { sportFragment, discoverFragment,
                messageFragment, meFragment };
        //for get the fragment manger
        manager=getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        //set up initial the showing on the view
        transaction.add(R.id.fragment_container,fragmentArray[currentIndex]);
        transaction.show(fragmentArray[currentIndex]);
        transaction.commit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_main_fragment_sport:
                selectedIndex=0;
                break;
            case R.id.btn_main_fragment_discover:
                selectedIndex=1;
                break;
            case R.id.btn_main_fragment_message:
                selectedIndex=2;
                break;
            case R.id.btn_main_fragment_me:
                selectedIndex=3;
                break;
        }
        if(selectedIndex!=currentIndex){
            // replace the fragment by transcation
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.fragment_container,fragmentArray[selectedIndex]);
            transaction.show(fragmentArray[selectedIndex]);
            transaction.commit();
            // set the button by selected
            btnArray[selectedIndex].setSelected(true);
            btnArray[currentIndex].setSelected(false);
        }
        else{
            Toast.makeText(this,"你傻呀...",Toast.LENGTH_SHORT).show();
        }
    }
}
