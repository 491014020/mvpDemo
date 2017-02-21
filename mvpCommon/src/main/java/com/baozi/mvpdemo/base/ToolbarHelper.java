package com.baozi.mvpdemo.base;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.Toolbar;
import android.view.ViewStub;

import com.baozi.mvpdemo.ui.view.BaseUIView;

/**
 * @author jlanglang  2017/2/21 16:31
 * @版本 2.0
 * @Change
 */
public abstract class ToolbarHelper {
    protected Context context;
    protected Toolbar mToolbar;

    public ToolbarHelper(Toolbar toolbar, Context context) {
        mToolbar = toolbar;
        this.context = context;
    }

    public abstract void setTitle(@Nullable String str);

    public abstract void setTitle(@StringRes int str);

    public abstract void initToolbar();


    public static ToolbarHelper Create(BaseUIView baseUIView, ViewStub viewStub) {
        return new DefuatlToolbarHelperImpl();
    }

    public abstract Toolbar getToolbar();
}
