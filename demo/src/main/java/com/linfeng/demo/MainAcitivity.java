package com.linfeng.demo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baozi.mvpdemo.base.TempletActivity;
import com.baozi.mvpdemo.helper.ToolbarHelper;
import com.baozi.mvpdemo.presenter.TempletPresenter;
import com.linfeng.demo.contract.MainContract;

public class MainAcitivity extends TempletActivity <TempletPresenter> {


    @NonNull
    @Override
    protected View initContentView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getToolbarHelper().setTitle("首页");
        getToolbarHelper().setRightText("213", null);
        return inflater.inflate(R.layout.activity_main, null);
    }

    //这里偷懒,就不去单独写个PresenterImpl了
    @Override
    protected TempletPresenter initPresenter() {
        return new TempletPresenter<MainContract.View>() {
            @Override
            public void onCreate() {
                //设置滑动效果
                mView.getToolbarHelper().setScrollFlag(R.id.tl_costom, AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL |
                        AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);
            }

            @Override
            public void loadData() {

            }
        };
    }

    //重写该方法,设置ToolbarLayout
    @Override
    public int initToolbarLayout() {
        return ToolbarHelper.TOOLBAR_TEMPLET_DEFUATL;
    }


    @Override
    public void isNightMode(boolean isNight) {

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu, menu);
//        return super.onCreateOptionsMenu(menu);
//    }


    @Override
    public boolean isMaterialDesign() {
        return true;
    }


}
