package net.datafans.android.common.widget.controller;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import net.datafans.android.common.data.service.BaseResponse;
import net.datafans.android.common.data.service.DataService;
import net.datafans.android.common.data.service.DataServiceDelegate;

/**
 * Created by zhonganyun on 15/11/6.
 */
public class DataServiceFragment extends Fragment implements DataServiceDelegate {


    @Override
    public void onStatusOk(BaseResponse response, DataService service) {

    }

    @Override
    public void onStatusError(BaseResponse response, DataService service) {
        Log.e("ANDROID_COMMON", response.toString());
        Toast toast = Toast.makeText(getActivity(), response.getErrorMsg(),
                Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void onRequestError(int errorCode, byte[] errorResponse,
                               Throwable throwable, DataService service) {
        if (errorCode == -2) {
            Log.e("ANDROID_COMMON", "network exception");
            Toast toast = Toast.makeText(getActivity(), "网络异常", Toast.LENGTH_SHORT);
            toast.show();
        }

        if (errorCode == -1) {
            Log.e("ANDROID_COMMON", "data_parse_exception");
            Toast toast = Toast.makeText(getActivity(), "数据解析错误", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
