package net.datafans.android.common.data.service;

public interface DataServiceDelegate {

	void onStatusOk(BaseResponse response, DataService service);
	void onStatusError(BaseResponse response, DataService service);
	void onRequestError(int errorCode, byte[] errorResponse, Throwable throwable, DataService service);
}
