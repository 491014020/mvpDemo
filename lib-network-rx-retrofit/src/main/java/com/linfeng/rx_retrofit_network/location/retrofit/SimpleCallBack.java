package com.linfeng.rx_retrofit_network.location.retrofit;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

/**
 * Created by baozi on 2017/6/21.
 */

public class SimpleCallBack<T> implements Callback<T> {
    @Override
    public void onResponse(Call<T> call, Response<T> response) {
    }


    @Override
    public void onFailure(Call<T> call, Throwable e) {
        String errormsg;
        if (e instanceof UnknownHostException) {
            errormsg = "请打开网络";
        } else if (e instanceof SocketTimeoutException) {
            errormsg = "请求超时";
        } else if (e instanceof ConnectException) {
            errormsg = "连接失败";
        } else if (e instanceof HttpException) {
            errormsg = "请求超时";
        } else {
            errormsg = "请求失败";
        }
        e.printStackTrace();
    }
}
