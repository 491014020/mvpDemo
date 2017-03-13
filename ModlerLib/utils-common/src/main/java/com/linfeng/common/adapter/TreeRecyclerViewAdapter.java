package com.linfeng.common.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.linfeng.common.adapter.recyclerview.base.ViewHolder;

import java.util.List;

public class TreeRecyclerViewAdapter<T extends TreeAdapterItem> extends RecyclerView.Adapter<ViewHolder> {

    protected Context mContext;
    /**
     * 存储所有可见的Node
     */
    protected List<T> mNodes;//处理后的展示数据

    /**
     * 点击item的回调接口
     */
    private OnTreeItemClickListener onTreeItemClickListener;

    public void setOnTreeItemClickListener(OnTreeItemClickListener onTreeItemClickListener) {
        this.onTreeItemClickListener = onTreeItemClickListener;
    }

    /**
     * @param context
     * @param datas
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public TreeRecyclerViewAdapter(Context context, List<T> datas) {
        mContext = context;
        mNodes = datas;
    }

    /**
     * 相应ListView的点击事件 展开或关闭某节点
     *
     * @param position 触发的条目
     */
    public void expandOrCollapse(int position) {
        TreeAdapterItem treeAdapterItem = mNodes.get(position);
        if (!treeAdapterItem.isParent()) {
            return;
        }
        boolean expand = treeAdapterItem.isExpand();
        if (expand) {
            mNodes.removeAll(treeAdapterItem.getAllChilds());
            treeAdapterItem.setExpand(false);
            treeAdapterItem.onCollapse();
        } else {
            mNodes.addAll(position + 1, treeAdapterItem.getChilds());
            treeAdapterItem.setExpand(true);
            treeAdapterItem.onExpand();
        }
        notifyDataSetChanged();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    TreeAdapterItem treeAdapterItem = mNodes.get(position);
                    if (treeAdapterItem.getSpanSize() == 0) {
                        return gridLayoutManager.getSpanCount();
                    }
                    return treeAdapterItem.getSpanSize();
                }
            });
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = ViewHolder.createViewHolder(mContext, parent, viewType);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final TreeAdapterItem treeAdapterItem = mNodes.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandOrCollapse(position);
                if (onTreeItemClickListener != null) {
                    onTreeItemClickListener.onClick(treeAdapterItem, position);
                }
            }
        });
        treeAdapterItem.onBindViewHolder(holder);
    }

    @Override
    public int getItemViewType(int position) {
        return mNodes.get(position).getLayoutId();
    }

    @Override
    public int getItemCount() {
        return mNodes == null ? 0 : mNodes.size();
    }

    public interface OnTreeItemClickListener {
        void onClick(TreeAdapterItem node, int position);
    }

    public void repalceAll(List<T> list) {
        mNodes = list;
        notifyDataSetChanged();
    }
}