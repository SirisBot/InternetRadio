package com.software.osirisgadson.internetradioapp.data.network.radio;


import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RadioUtil {

    private final static String BASE_URL = "https://raw.githubusercontent.com";

    private RadioService radioService;

    public RadioUtil() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        radioService = retrofit.create(RadioService.class);
    }

    public RadioService getService() {
        return radioService;
    }

}
