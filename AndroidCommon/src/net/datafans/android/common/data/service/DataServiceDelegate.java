package net.datafans.android.common.data.service;

public interface DataServiceDelegate {

	void onStatusOk(BaseResponse response, Class<?> type);
	void onStatusError(BaseResponse response);
	void onRequestError(int errorCode, byte[] errorResponse, Throwable throwable);
}
