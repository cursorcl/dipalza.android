package cl.eos.dipalza.rest;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIServiceAdapter {

	
	private static APIService API_SERVICE;
	
	
	public static APIService getApiService() {
        if (API_SERVICE == null) {
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd hh:mm:ss")
                    .create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(APIService.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            API_SERVICE = retrofit.create(APIService.class);
        }
        return API_SERVICE;
    }

}
