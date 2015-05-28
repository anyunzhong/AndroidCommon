package net.datafans.android.common.data.service;

import org.apache.http.Header;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

public abstract class DataService {

	private AsyncHttpClient client = new AsyncHttpClient();

	private DataServiceDelegate delegate;

	public void execute() {
		// 需要检查网络是否可用

		executeRequest();
	}

	protected void executeRequest() {

		client.get(getRequestUrl(), new AsyncHttpResponseHandler() {
			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] errorResponse, Throwable throwable) {
				onError(statusCode, errorResponse, throwable);
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] response) {
				DataService.this.onSuccess(response);
			}
		});
	}

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

	public DataServiceDelegate getDelegate() {
		return delegate;
	}

	public void setDelegate(DataServiceDelegate delegate) {
		this.delegate = delegate;
	}
}
