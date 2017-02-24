package com.baozi.mvpdemo.helper;

import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.baozi.mvpdemo.R;
import com.baozi.mvpdemo.ui.view.UIView;

/**
 * @author jlanglang  2017/2/21 16:31
 * @版本 2.0
 * @Change
 */
public abstract class ToolbarHelper {
    public static final int DEFUATL_BASE_TOOLBAR_V1 = R.layout.base_toolbar;

    public ToolbarHelper() {

    }

    public static ToolbarHelper Create(@NonNull UIView uiView, @LayoutRes int toolbarLayout) {
        if (toolbarLayout == DEFUATL_BASE_TOOLBAR_V1) {
            return new DefuatlToolbarHelperImplV1(uiView, toolbarLayout);
        } else if (toolbarLayout <= 0) {
            return new EmptyToolbarHelperImpl(uiView,toolbarLayout);
        } else {
            return new BaseToolBarHelperImpl(uiView, toolbarLayout);
        }
    }

    public abstract void initToolbar();

    public abstract Toolbar getToolbar();

    public abstract void setMaterialDesignEnabled(boolean isMaterialDesign);

    /**
     * 设置title
     *
     * @param str
     */

    public abstract void setTitle(@NonNull String str);

    public abstract void setTitle(@StringRes int str);

    /**
     * 设置左边
     *
     * @param strId
     */
    public abstract void setLeftText(@StringRes int strId, View.OnClickListener clickListener);

    public abstract void setLeftText(@NonNull String str, View.OnClickListener clickListener);

    public abstract void setLeftButton(Drawable drawable, View.OnClickListener clickListener);

    public abstract void setLeftButton(@DrawableRes int drawableId, View.OnClickListener clickListener);


    /**
     * 设置右边
     *
     * @param str
     */
    public abstract void setRightText(@NonNull String str, View.OnClickListener clickListener);

    public abstract void setRightText(@StringRes int strId, View.OnClickListener clickListener);

    public abstract void setRightButton(@NonNull Drawable drawable, View.OnClickListener clickListener);

    public abstract void setRightButton(@DrawableRes int drawableId, View.OnClickListener clickListener);
}
