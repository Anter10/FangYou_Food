package com.jjkj.guoyouchao.fangyou_food.Activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jjkj.guoyouchao.fangyou_food.R;

public class AllActivityBase extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_activity_base);
    }


    public boolean hasInternet(){
       boolean hasinternet = false;

       return hasinternet;
    }
}
