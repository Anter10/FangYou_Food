package com.jjkj.guoyouchao.fangyou_food.Activitys;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.jjkj.guoyouchao.fangyou_food.R;

public class Main2Activity extends AppCompatActivity {


    private ImageView shopImg;
    private ImageView orderImg;
    private ImageView setImg;
    private FragmentManager fragmentManager;
    private Fragment[] mFragments;
    private FragmentTransaction fragmentTransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        FrameLayout bottom = (FrameLayout)findViewById(R.id.bottommenu);
        bottom.setBackgroundColor(Color.LTGRAY);
        shopImg = (ImageView)findViewById(R.id.dm);
        orderImg = (ImageView)findViewById(R.id.dd);
        setImg = (ImageView)findViewById(R.id.dz);
//
//        mFragments = new Fragment[3];
//        fragmentManager = getSupportFragmentManager();
//        mFragments[0] = (Fragment)fragmentManager.findFragmentById(R.layout.fragment_shop);
//        mFragments[1] = fragmentManager.findFragmentById(R.id.fragement_search);
//        mFragments[2] = fragmentManager
//                .findFragmentById(R.id.fragement_setting);
//        fragmentTransaction = fragmentManager.beginTransaction()
//                .hide(mFragments[0]).hide(mFragments[1]).hide(mFragments[2]);
//
//        fragmentTransaction.show(mFragments[0]).commit();



    }
}
