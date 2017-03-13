package com.baozi.jmvp.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * Created by baozi on 2016/11/24.
 */
public interface BaseFragmentView extends UIView {

    Bundle getBundle();

    FragmentManager getFragmentManager();
    Fragment getFragment();
}
