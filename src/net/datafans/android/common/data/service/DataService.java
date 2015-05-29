package net.datafans.android.common.data.service;

import org.apache.http.Header;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public abstract class DataService {

	private DataServiceDelegate delegate;

	private RequestType requestType;

	private RequestParams params = new RequestParams();

	public void execute() {

		if (!NetworkDetector.isAvailable(getContext())) {
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
		BaseResponse baseResponse = JSON.parseObject(data, BaseResponse.class);
		if (baseResponse == null) {
			// 数据解析错误
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
	
	protected abstract Context getContext();

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
