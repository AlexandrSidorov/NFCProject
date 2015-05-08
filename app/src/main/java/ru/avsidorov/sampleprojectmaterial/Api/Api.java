package ru.avsidorov.sampleprojectmaterial.Api;

import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;

/**
 * Created by Сидоров on 23.04.2015.
 */
public interface Api {
    @GET("/Values")
    Response values(@Header("Authorization") String authorization);

    @POST("/Values/{value}")
    Response valuesPost(@Header("Authorization")String authorization, String value );



}
