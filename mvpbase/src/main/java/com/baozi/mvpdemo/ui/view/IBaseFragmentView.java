package com.baozi.mvpdemo.ui.view;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.baozi.mvpdemo.base.BaseFragment;

/**
 * Created by baozi on 2016/11/24.
 */
public interface IBaseFragmentView extends IUIView {

    Bundle getBundle();

    FragmentManager getFragmentManager();
    BaseFragment getFragment();
}
