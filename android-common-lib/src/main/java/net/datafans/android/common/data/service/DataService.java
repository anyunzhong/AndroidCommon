package net.datafans.android.common.data.service;

import net.datafans.android.common.AndroidCommon;
import net.datafans.android.common.helper.LogHelper;
import net.datafans.android.common.network.NetworkDetector;

import org.apache.http.Header;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public abstract class DataService {

    private DataServiceDelegate delegate;

    private RequestType requestType;

    private RequestParams params = new RequestParams();

    public void execute() {

        if (!NetworkDetector.isAvailable(AndroidCommon.getContext())) {
            onError(-2, null, null);
        }
        if (requestType == null) {
            requestType = RequestType.GET;
        }
        setRequestParams(params);
        executeRequest();
    }

    protected void executeRequest() {
        switch (requestType) {
            case GET:
                AsyncHttpClientHelper.get(getRequestUrl(), params, responseHandler);
                break;
            case POST:
                AsyncHttpClientHelper
                        .post(getRequestUrl(), params, responseHandler);
                break;

            default:
                break;
        }

    }

    @SuppressWarnings("deprecation")
    private AsyncHttpResponseHandler responseHandler = new AsyncHttpResponseHandler() {

        @Override
        public void onFailure(int statusCode, Header[] headers,
                              byte[] errorResponse, Throwable throwable) {
            LogHelper.error(throwable);
            onError(statusCode, errorResponse, throwable);
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] response) {
            DataService.this.onSuccess(response);
        }
    };

    private void onError(int statusCode, byte[] errorResponse,
                         Throwable throwable) {
        if (delegate == null) {
            return;
        }

        delegate.onRequestError(statusCode, errorResponse, throwable, this);
    }

    private void onSuccess(byte[] response) {
        String data = new String(response);
        if (data.equals("")) {
            onError(-1, null, null);
            return;
        }

        BaseResponse baseResponse = JSON.parseObject(data, BaseResponse.class);
        if (baseResponse == null) {
            // 数据解析错误
            LogHelper.error("data_parse_error");
            onError(-1, null, null);
            return;
        }


        if (baseResponse.getStatus() == 1) {
            JSONObject object = JSON.parseObject(baseResponse.getData());
            parseResponse(object);
            if (delegate != null) {
                delegate.onStatusOk(baseResponse, this);
            }
        } else {
            if (delegate != null) {
                delegate.onStatusError(baseResponse, this);
            }

        }
    }

    protected String getRequestDomain() {
        return "";
    }

    protected String getRequestPath() {
        return "";
    }

    protected String getRequestUrl() {
        return getRequestDomain() + getRequestPath();
    }

    protected void parseResponse(JSONObject data) {

    }

    public DataServiceDelegate getDelegate() {
        return delegate;
    }

    public void setDelegate(DataServiceDelegate delegate) {
        this.delegate = delegate;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    protected void setRequestParams(RequestParams params) {

    }

}
