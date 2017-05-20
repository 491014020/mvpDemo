package com.linfeng.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.support.annotation.StringDef;
import android.support.design.widget.AppBarLayout;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;

import com.baozi.mvp.base.TempletActivity;
import com.baozi.mvp.helper.ToolbarHelper;
import com.baozi.mvp.presenter.BasePresenter;
import com.linfeng.demo.contract.MainContract;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static android.support.annotation.RestrictTo.Scope.LIBRARY_GROUP;

public class MainActivity extends TempletActivity<BasePresenter>
        implements MainContract.View {

    @NonNull
    @Override
    protected View initContentView(LayoutInflater inflater, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.activity_main, null);
    }

    //这里偷懒,就不去单独写个PresenterImpl了
    @Override
    protected BasePresenter initPresenter() {
        return new BasePresenter<MainContract.View>() {
            @Override
            public void onCreate() {
                mView.getToolbarHelper().setTitle("首页");
                mView.getToolbarHelper().setRightText("213", null);
                //设置滑动效果
                mView.getToolbarHelper().setScrollFlag(R.id.tl_custom, AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL |
                        AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);
            }

            @Override
            public void loadData() {

            }

            @Override
            public void cancleNetWork() {
            }
        };
    }

    //重写该方法,设置ToolbarLayout
    @Override
    public int initToolbarLayout() {
        return ToolbarHelper.TOOLBAR_TEMPLET_DEFUATL;
    }


    @Override
    public boolean isMaterialDesign() {
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            getToolbarHelper().setTitle("返回了桌面");
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
