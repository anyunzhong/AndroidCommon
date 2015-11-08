package net.datafans.android.common.widget.table.refresh.adapter;

import net.datafans.android.common.R;
import net.datafans.android.common.widget.table.refresh.RefreshTableViewAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import cn.bingoogolapple.refreshlayout.BGAMoocStyleRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout.BGARefreshLayoutDelegate;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGAStickinessRefreshViewHolder;
import cn.trinea.android.common.view.DropDownListView;

public class BGARefreshTableViewAdapter extends RefreshTableViewAdapter implements
        BGARefreshLayoutDelegate {

    private BGARefreshLayout refreshLayout;
    private BGARefreshViewHolder refreshViewHolder;

    private DropDownListView listView;

    @SuppressLint("InflateParams")
    public BGARefreshTableViewAdapter(Context context, BaseAdapter adapter,
                                      RefreshType type) {
        View view = LayoutInflater.from(context).inflate(R.layout.bga, null);
        refreshLayout = (BGARefreshLayout) view
                .findViewById(R.id.rl_modulename_refresh);
        refreshLayout.setDelegate(this);

        switch (type) {
            case Normal:
                refreshViewHolder = new BGANormalRefreshViewHolder(context, false);
                break;
            case MoocStyle:
                refreshViewHolder = new BGAMoocStyleRefreshViewHolder(context,
                        false);
                break;
            case Stickiness:
                refreshViewHolder = new BGAStickinessRefreshViewHolder(context,
                        false);
                break;
            default:
                break;
        }

        refreshLayout.setRefreshViewHolder(refreshViewHolder);

        listView = (DropDownListView) view
                .findViewById(R.id.rl_modulename_refresh_listview);
        //listView.setAdapter(adapter);

        listView.setDropDownStyle(false);
        listView.setOnBottomStyle(true);
        listView.setShowFooterProgressBar(true);
        listView.setShowFooterWhenNoMore(true);
        listView.setHasMore(false);

        listView.setFooterDefaultText("查看更多");
        listView.setFooterLoadingText("加载中...");
        listView.setFooterNoMoreText("没有了");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

    }

    @Override
    public View getRootView() {
        return refreshLayout;
    }


    @Override
    public ListView getListView() {
        return listView;
    }


    @Override
    public void enableRefresh(boolean enable) {

    }

    @Override
    public void enableLoadMore(boolean enable) {

        listView.setOnBottomListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                listView.onBottomBegin();
                loadMore();
            }
        });

    }

    @Override
    public void enableAutoLoadMore(boolean enable) {
        listView.setAutoLoadOnBottom(enable);
    }

    @Override
    public void endRefresh() {
        refreshLayout.endRefreshing();
    }

    @Override
    public void endLoadMore() {
        listView.onBottomComplete();
    }

    @Override
    public void loadOver(boolean over) {
        listView.setHasMore(!over);
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing() {
        refresh();
    }

    @Override
    public void onBGARefreshLayoutBeginLoadingMore() {

        // loadMore();
    }

    public static enum RefreshType {
        Normal, MoocStyle, Stickiness
    }
}
