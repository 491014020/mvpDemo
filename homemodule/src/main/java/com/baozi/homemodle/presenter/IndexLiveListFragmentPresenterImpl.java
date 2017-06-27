package com.baozi.homemodle.presenter;

import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.baozi.frame.CarouselRecyclerView;
import com.baozi.frame.JBasePresenter;
import com.baozi.homemodle.R;
import com.baozi.homemodle.contract.IndexLiveListFragmentContract;
import com.baozi.jrecyclerviewadapter.adapter.recyclerview.SimpleRecyclerAdapter;
import com.baozi.jrecyclerviewadapter.adapter.recyclerview.ViewHolder;
import com.baozi.jrecyclerviewadapter.adapter.recyclerview.wrapper.HeaderAndFootWapper;
import com.linfeng.imageloder.BindImageFactory;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Administrator on 2017/03/30
 */

public class IndexLiveListFragmentPresenterImpl extends JBasePresenter<IndexLiveListFragmentContract.View>
        implements IndexLiveListFragmentContract.Presenter {
    private String[] mStrings = {"1","2","3"};
    private HeaderAndFootWapper<String> mHeaderAndFootWapper;

    @Override
    public void onCreate() {
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            strings.add(i + "");
        }
        init();
        initRecyclerView();
        mHeaderAndFootWapper.getItemManager().addAllItems(strings);
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = mView.findView(R.id.rl_content);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(mView.getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.top = AutoUtils.getPercentHeightSize(20);
            }
        });
        recyclerView.setAdapter(mHeaderAndFootWapper);

    }

    private void init() {
        SimpleRecyclerAdapter mSimpleRecyclerBaseAdapter = new SimpleRecyclerAdapter<String>() {
            @Override
            public void convert(ViewHolder holder, String o, int position) {

            }

            @Override
            public int getLayoutId() {
                return R.layout.home_item_live_list;
            }
        };
        mHeaderAndFootWapper = new HeaderAndFootWapper<String>(mSimpleRecyclerBaseAdapter);

        SimpleRecyclerAdapter<String> carouselAdapter = new SimpleRecyclerAdapter<String>() {
            @Override
            public int getLayoutId() {
                return R.layout.home_item_carouse_image;
            }

            @Override
            public void convert(ViewHolder holder, String o, int position) {
                ImageView itemView = (ImageView) holder.itemView;
                itemView.setBackground(ContextCompat.getDrawable(mView.getContext(),R.mipmap.ic_launcher));
//                BindImageFactory.bindImage(itemView.getContext(), o, itemView);
            }
        };
        CarouselRecyclerView contentView = new CarouselRecyclerView.Builder(mView.getContext())
                .setAdapter(carouselAdapter)
                .setItemChangeCallBack(new CarouselRecyclerView.ItemChangeCallBack() {
                    @Override
                    public void change(ViewGroup indicator, int index) {

                    }
                })
                .create();
        carouselAdapter.getItemManager().replaceAllItem(Arrays.asList(mStrings));
        mHeaderAndFootWapper.addHeaderView(contentView);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SwipeRefreshLayout swipeRefreshLayout = mView.findView(R.id.sw_refresh);
        swipeRefreshLayout.setRefreshing(false);
    }
}