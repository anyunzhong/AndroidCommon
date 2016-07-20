package net.datafans.android.common.data.service;

import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.RequestParams;

import net.datafans.android.common.widget.controller.PlainTableViewController;

/**
 * Created by zhonganyun on 16/7/19.
 */
public abstract class ListDataService extends DataService {

    private String start = "0";

    @Override
    protected void setRequestParams(RequestParams params) {
        super.setRequestParams(params);
        params.put("start", start);
        params.put("size", 20);
    }

    public void refresh() {
        start = "0";
        execute();
    }

    public void loadMore() {
        execute();
    }

    @Override
    protected void parseResponse(JSONObject data) {
        super.parseResponse(data);

        int more = data.getIntValue("more");
        start = data.getString("start");

        DataServiceDelegate delegate = getDelegate();
        if (delegate instanceof PlainTableViewController) {
            PlainTableViewController controller = (PlainTableViewController) delegate;
            controller.loadOver(more == 0);
        }
    }
}
