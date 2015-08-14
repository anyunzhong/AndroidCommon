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
        params.put("token", "8c3c3f96b7788451567b6518f1676adc");
        params.put("user_id", 100001);
    }

    @Override
    protected void parseResponse(BaseResponse response) {
        JSONObject o = (JSONObject) JSON.parse(response.getData());
        List<Shop> shops = JSON.parseArray(o.getString("friends"), Shop.class);
        for (int i = 0; i < 30; i++)
            shopList.addAll(shops);
    }

    public List<Shop> getShopList() {
        return shopList;
    }

}
