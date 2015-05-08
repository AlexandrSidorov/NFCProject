package ru.avsidorov.sampleprojectmaterial.Api;

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

import retrofit.client.Response;

/**
 * Created by Сидоров on 23.04.2015.
 */
public class Request {
    public static class ValuesRequest extends RetrofitSpiceRequest<Response, Api> {
        String mAuth;
        public ValuesRequest(String basic_auth_string64) {
            super(Response.class, Api.class);
            mAuth=basic_auth_string64;
        }

        @Override
        public Response loadDataFromNetwork() throws Exception {
            return getService().values(mAuth);
        }
    }
    public static class ValuePostRequest extends RetrofitSpiceRequest<Response,Api> {
        String mAuth,mValue_for_post;

        public ValuePostRequest(String basic_auth_string64, String value_for_post) {
            super(Response.class, Api.class);
            mAuth=basic_auth_string64;
            mValue_for_post = value_for_post;
        }

        @Override
        public Response loadDataFromNetwork() throws Exception {

            return getService().valuesPost(mAuth, mValue_for_post);
        }
    }
}
