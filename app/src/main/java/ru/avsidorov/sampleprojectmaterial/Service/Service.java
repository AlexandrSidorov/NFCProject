package ru.avsidorov.sampleprojectmaterial.Service;

import android.content.Intent;

import com.octo.android.robospice.retrofit.RetrofitGsonSpiceService;

import retrofit.converter.Converter;
import ru.avsidorov.sampleprojectmaterial.Api.Api;

/**
 * Created by Сидоров on 24.04.2015.
 */
public class Service extends RetrofitGsonSpiceService {
    private final static String BASE_URL = "http://mdigitalnet-6.hosting.parking.ru/api";

    @Override
    public void onCreate() {
        super.onCreate();
        addRetrofitInterface(Api.class);

    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected String getServerUrl() {
        return BASE_URL;
    }

    @Override
    protected Converter createConverter() {
        return super.createConverter();
    }
}
