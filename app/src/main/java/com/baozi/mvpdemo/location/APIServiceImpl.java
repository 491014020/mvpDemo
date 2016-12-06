package com.baozi.mvpdemo.location;

import com.baozi.mvpdemo.location.retrofit.RetrofitUtil;

/**
 */
public class APIServiceImpl {
    private APIServiceImpl() {

    }

    public static APIService getInstance() {
        return Instance.getInstance();
    }

    private static class Instance {
        private static APIService apiService = RetrofitUtil.getInstance().create(APIService.class);

        public static APIService getInstance() {
            return apiService;
        }
    }
}
