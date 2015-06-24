package net.datafans.android.test.activity;

import java.util.List;

import net.datafans.android.common.data.service.BaseResponse;
import net.datafans.android.common.data.service.DataServiceDelegate;
import net.datafans.android.common.widget.controller.TableViewController;
import net.datafans.android.common.widget.table.TableViewCell;
import net.datafans.android.common.widget.table.TableViewStyle;
import net.datafans.android.common.widget.table.refresh.RefreshControlType;
import net.datafans.android.test.R;
import net.datafans.android.test.data.service.Shop;
import net.datafans.android.test.data.service.ShopListDataService;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;

public class ShopListController extends TableViewController<Shop> implements
        DataServiceDelegate {

    private List<Shop> shopList;

    private ShopListDataService dataService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        dataService = new ShopListDataService();
        dataService.setDelegate(this);
        shopList = dataService.getShopList();


        setStyle(TableViewStyle.GROUP);

        super.onCreate(savedInstanceState);

        dataService.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.shop_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected RefreshControlType getRefreshControlType() {
        return RefreshControlType.BGANormal;

    }

    @Override
    public int getRows() {
        return shopList.size();
    }

    @Override
    public TableViewCell<Shop> getTableViewCell(int row) {
        return new ShopListTableViewCell(R.layout.shop_list,
                LayoutInflater.from(this));
    }

    @Override
    public void onClickRow(int row) {

        Log.e("row", "r:" + row);
    }

    @Override
    public Shop getEntity(int row) {
        return shopList.get(row);
    }

    @Override
    public void onRefresh() {

        if (shopList != null) {
            shopList.clear();
        }
        dataService.execute();

    }

    @Override
    public void onLoadMore() {
        dataService.execute();
    }

    @Override
    public void onStatusOk(BaseResponse response, Class<?> type) {

        if (shopList.size() > 4) {
            loadOver(true);
        } else {
            loadOver(false);
        }

        super.onStatusOk(response, type);
        Log.e("response", response.toString());

    }

    @Override
    protected boolean enableAutoLoadMore() {
        return true;
    }

}
