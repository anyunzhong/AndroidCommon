package net.datafans.android.common.widget.controller;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.datafans.android.common.data.service.BaseResponse;
import net.datafans.android.common.data.service.DataService;
import net.datafans.android.common.helper.LogHelper;
import net.datafans.android.common.widget.table.PlainTableView;
import net.datafans.android.common.widget.table.TableView;
import net.datafans.android.common.widget.table.TableViewCell;
import net.datafans.android.common.widget.table.TableViewDataSource;
import net.datafans.android.common.widget.table.TableViewDelegate;
import net.datafans.android.common.widget.table.refresh.RefreshControlType;

public abstract class PlainTableViewController<T> extends FragmentController implements
        TableViewDataSource<T>, TableViewDelegate {

    protected TableView<T> tableView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected Fragment getRootFragment() {

        if (tableView == null) {

            TableView.Builder<T> builder = new PlainTableView.Builder<>();
            builder.setRefreshType(getRefreshControlType());
            builder.setEnableLoadMore(enableRefresh());
            builder.setEnableLoadMore(enableLoadMore());
            builder.setEnableAutoLoadMore(enableAutoLoadMore());
            builder.setDataSource(this);
            builder.setDelegate(this);
            builder.setHeaderView(getTableHeaderView());
            builder.setFooterView(getTableFooterView());

            tableView = builder.build();

        }
        return new Fragment(){
            @Override
            public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                     Bundle savedInstanceState) {
                return tableView.getView();
            }
        };
    }

    @Override
    public void onStatusOk(BaseResponse response, DataService service) {
        super.onStatusOk(response, service);
        onEnd();
    }

    @Override
    public void onStatusError(BaseResponse response, DataService service) {
        super.onStatusError(response, service);
        onEnd();
    }

    @Override
    public void onRequestError(int errorCode, byte[] errorResponse,
                               Throwable throwable, DataService service) {
        super.onRequestError(errorCode, errorResponse, throwable, service);
        onEnd();
    }

    public void onEnd() {
        tableView.reloadData();
        tableView.endRefresh();
        tableView.endLoadMore();
    }

    protected RefreshControlType getRefreshControlType() {
        return RefreshControlType.None;
    }

    public void loadOver(boolean over) {
        if (over) {
            //Toast toast = Toast.makeText(this, "加载完毕", Toast.LENGTH_SHORT);
            //toast.show();
        }
        tableView.loadOver(over);
    }

    protected boolean enableRefresh() {
        return true;
    }

    protected boolean enableLoadMore() {
        return true;
    }

    protected boolean enableAutoLoadMore() {
        return false;
    }

    @Override
    public int getItemViewType(int section, int row) {
        return 0;
    }

    @Override
    public int getItemViewTypeCount() {
        return 1;
    }

    protected View getTableHeaderView() {
        return null;
    }

    protected View getTableFooterView() {
        return null;
    }

    @Override
    public int getRows(int i) {
        return 1;
    }

    @Override
    public void onClickRow(int section, int row) {
        LogHelper.info("click section: " + section + "  row: " +row );
    }

    @Override
    public void onRefresh() {
        LogHelper.info("refresh");
    }

    @Override
    public void onLoadMore() {
        LogHelper.info("load more");
    }

}
