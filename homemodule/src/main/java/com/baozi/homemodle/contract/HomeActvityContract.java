package com.baozi.homemodle.contract;

import android.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.TableLayout;

import com.baozi.jmvp.ui.BaseActivityView;
import com.baozi.jmvp.ui.TempletActivityView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by baozi on 2017/3/20.
 */

public class HomeActvityContract {
    public interface View extends TempletActivityView {
        //contentview为viewpage+fragment
        ViewPager getContentViewPager();

        List<Fragment> getFragments();

        List<String> getTabs();
    }

    public interface Presenter {

    }

    public interface Model {
    }


}