package net.datafans.android.test.data.service;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.RequestParams;

import net.datafans.android.common.data.service.BaseResponse;
import net.datafans.android.common.data.service.DataService;


public class ShopListDataService extends DataService {

    private List<Shop> shopList = new ArrayList<Shop>();

    @Override
    protected String getRequestDomain() {
        return "http://112.124.28.196:55555";
    }

    @Override
    protected String getRequestPath() {
        return "/contact/friend/list/";
    }

    @Override
    protected void setRequestParams(RequestParams params) {
        super.setRequestParams(params);
        params.put("api_version", 1);
        params.put("token", "37f54ae1713fddc11c387b66acb0ffe8");
        params.put("user_id", 100005);
    }

    @Override
    protected void parseResponse(JSONObject data) {
        List<Shop> shops = JSON.parseArray(data.getString("friends"), Shop.class);
        for (int i = 0; i < 5; i++)
            shopList.addAll(shops);
    }

    public List<Shop> getShopList() {
        return shopList;
    }

}
