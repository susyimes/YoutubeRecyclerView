package com.susyimes.youtubeplayonrecyclerview;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.susyimes.youtuberecyclerview.Constant;
import com.susyimes.youtuberecyclerview.VedioFragment;
import com.susyimes.youtuberecyclerview.rxbus.RxBus;
import com.susyimes.youtuberecyclerview.rxbus.SusAction;

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        VedioFragment vedioFragment=new VedioFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragmentc,vedioFragment).commit();
    }

    @Override
    public void onBackPressed() {
        if (Constant.fullscreen==1){
            RxBus.getDefault().post(new SusAction(Constant.fullscreenposition));
            Constant.fullscreen=0;
        }else {
        super.onBackPressed();}
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
