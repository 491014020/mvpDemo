package com.linfeng.rx_retrofit_network.location.rxandroid;

import com.linfeng.rx_retrofit_network.NetWorkManager;
import com.linfeng.rx_retrofit_network.location.APIExceptionCallBack;
import com.linfeng.rx_retrofit_network.location.APISucesssCallback;
import com.linfeng.rx_retrofit_network.location.model.BaseResponse;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * @param <T>
 */
public class NetWorkTransformer<T> implements ObservableTransformer<BaseResponse<T>, T> {
    private static final int DEFAULT_TIME_OUT = 5;
    private static final int DEFAULT_RETRY = 5;

    /**
     * 处理请求结果,BaseResponse
     *
     * @param response 请求结果
     * @return 过滤处理, 返回只有data的Observable
     */
    private Observable<T> flatResponse(final BaseResponse<T> response) {
        return Observable.just(response)
                .filter(new Predicate<BaseResponse<T>>() {
                    @Override
                    public boolean test(@NonNull BaseResponse<T> tBaseResponse) throws Exception {
                        if (tBaseResponse.getData() == null) {
                            throw new NullPointerException();
                        }
                        int code = tBaseResponse.getCode();
                        APIExceptionCallBack apiCallback = NetWorkManager.getApiCallback(code);
                        if (apiCallback != null) {
                            if (apiCallback instanceof APISucesssCallback) {
                                return true;
                            } else {
                                apiCallback.callback(tBaseResponse);
                                return false;
                            }
                        }
                        return true;
                    }
                })
                .map(new Function<BaseResponse<T>, T>() {
                    @Override
                    public T apply(@NonNull BaseResponse<T> tBaseResponse) throws Exception {
                        return tBaseResponse.getData();
                    }
                });
//        return Observable.create(new ObservableOnSubscribe<T>() {
//            @Override
//            public void subscribe(ObservableEmitter<T> e) throws Exception {
//                if (!e.isDisposed()) {
//                    if (response.getData() == null) {
//                        throw new APIException(response.getCode(), response.getMsg());
//                    } else if (response.getCode() == 0) {//请求成功
//                        e.onNext(response.getData());
//                    } else {//请求失败
////                        e.onError(new APIException(response.getCode(), response.getMsg()));
//                        throw new APIException(response.getCode(), response.getMsg());
//                    }
//                    //请求完成
//                    e.onComplete();
//                }
//            }
//        });
    }

    @Override
    public ObservableSource<T> apply(Observable<BaseResponse<T>> upstream) {
        return upstream
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .timeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
                .retry(DEFAULT_RETRY)
                .flatMap(new Function<BaseResponse<T>, ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> apply(BaseResponse<T> tBaseResponse) throws Exception {
                        return flatResponse(tBaseResponse);
                    }
                });
    }
}