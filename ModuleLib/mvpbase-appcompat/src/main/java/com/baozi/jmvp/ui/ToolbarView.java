package com.baozi.jmvp.ui;

import android.support.annotation.LayoutRes;

import com.baozi.jmvp.helper.ToolbarHelper;

/**
 * @author jlanglang  2017/3/4 17:44
 * @版本 2.0
 * @Change
 */

public interface ToolbarView extends UIView {
    /**
     * 获得ToolbarHelper,Presenter可以通过ToolbarHelper的来控制toolbar
     */
    ToolbarHelper getToolbarHelper();

    /**
     * 是否使用MaterialDesign风格
     *
     * @return
     */
    boolean isMaterialDesign();

    /**
     * 通过这个修改toolbar的样式layout,不需要可以传0或者-1;
     *
     * @return
     */
    @LayoutRes
    int initToolbarLayout();

    /**
     * MaterialDesign风格,普通风格之间转换
     *
     * @param isMaterialDesign
     */
    void setMaterialDesignEnabled(boolean isMaterialDesign);


}
