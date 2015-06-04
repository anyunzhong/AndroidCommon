package net.datafans.android.common.data.service;

import net.datafans.android.common.config.AndroidCommon;
import net.datafans.android.common.network.NetworkDetector;

import org.apache.http.Header;

import android.util.Log;

import com.alibaba.fastjson.JSON;
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

	private AsyncHttpResponseHandler responseHandler = new AsyncHttpResponseHandler() {
		@Override
		public void onFailure(int statusCode, Header[] headers,
				byte[] errorResponse, Throwable throwable) {
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

		delegate.onRequestError(statusCode, errorResponse, throwable);
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
			Log.e("data_parse_error", "data_parse_error");
			onError(-1, null, null);
		}

		if (delegate == null) {
			return;
		}
		if (baseResponse.getStatus() == 1) {
			parseResponse(baseResponse);
			delegate.onStatusOk(baseResponse, this.getClass());
		} else {

			delegate.onStatusError(baseResponse);
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

	protected void parseResponse(BaseResponse response) {

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
