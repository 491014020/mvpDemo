package com.linfeng.rx_retrofit_network.location;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;
import com.linfeng.rx_retrofit_network.factory.JSONFactory;

import java.util.HashMap;
import java.util.Set;

/**
 * Created by Jlanglang on 2017/9/4 0004.
 * 简书:http://www.jianshu.com/u/6bac141ea5fe
 */

public class SimpleParams extends HashMap<String, Object> {
    private HashMap<Object, String> checkParams = new HashMap<>();

    public static SimpleParams create() {
        return new SimpleParams();
    }

    public SimpleParams putP(String key, Object value) {
        this.putP(key, value, "");
        return this;
    }

    public SimpleParams putP(String key, Object value, String emptyMessage) {
        this.putP(key, value);
        checkParams.put(key, emptyMessage);
        return this;
    }

    /**
     * 检查params
     *
     * @param context
     * @return
     */
    public boolean checkMessage(Context context) {
        return checkMessage(context, null);
    }

    /**
     * 检查params
     *
     * @param context
     * @return
     */
    public boolean checkMessage(Context context, CheckParamsCallback checkParamsCallback) {
        Set<String> strings = keySet();
        for (String str : strings) {
            Object value = get(str);
            if (value == null || "".equals(value)) {
                String s = checkParams.get(str);
                //emptyMessage则说明,该参数不校验
                if (!TextUtils.isEmpty(s)) {
                    //传入回调,自定义处理
                    if (checkParamsCallback != null) {
                        checkParamsCallback.callBack(s);
                    } else {
                        //默认Toast提示.
                        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
                    }
                    return false;
                }
            }
        }
        return true;
    }

    public interface CheckParamsCallback {
        void callBack(String s);
    }
}

//    @Override
//    public String toString() {
//        return JSONFactory.toJson(this);
//    }
