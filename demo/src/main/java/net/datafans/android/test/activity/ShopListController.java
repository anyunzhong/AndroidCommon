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

public class ShopListController extends TableViewController<Shop> {

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
    protected String getNavTitle() {
        return "商家";
    }

    @Override
    protected boolean enableReturnButton() {
        return true;
    }

    @Override
    protected RefreshControlType getRefreshControlType() {
        return RefreshControlType.None;

    }

    @Override
    public int getRows(int section) {
        return shopList.size();
    }


    @Override
    public int getSections() {
        return 1;
    }

    @Override
    public TableViewCell<Shop> getTableViewCell(int section, int row) {
        if (row % 2 == 0)
            return new ShopListTableViewCell(R.layout.shop_list,this);
        else
            return new ShopListTableViewRedCell(R.layout.shop_list_red,
                    this);
    }


    @Override
    public String getSectionHeaderTitle(int section) {
        return "评论";
    }

    @Override
    public String getSectionFooterTitle(int section) {
        return "";
    }

    @Override
    public void onClickRow(int section, int row) {

        Log.d("section:row", section + ":" + row);
    }

    @Override
    public Shop getEntity(int section, int row) {
        return shopList.get(row);
    }


    @Override
    public int getItemViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int section, int row) {
        return row % 2;
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
        Log.d("response", response.toString());

    }

    @Override
    protected boolean enableAutoLoadMore() {
        return true;
    }

}
