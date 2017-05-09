package com.linfeng.rx_retrofit_network.location.rxandroid;



import com.linfeng.rx_retrofit_network.location.BaseResponse;

import rx.Observable;

/**
 * @author jlanglang  2016/11/15 16:14
 */
public class ModelFilterFactory {

    private static Observable.Transformer transformer = new SimpleTransformer();

    public static void setTransformer(Observable.Transformer transformer) {
        if (transformer == null) return;
        ModelFilterFactory.transformer = transformer;
    }

    /**
     * 将Observable<BaseResponse<T>>转化Observable<T>,并处理BaseResponse
     *
     * @return Observable<T>
     */
    @SuppressWarnings("unchecked")
    public static <T> Observable<T> filter(Observable<BaseResponse<T>> observable) {
        return observable.compose(transformer);
    }
}
