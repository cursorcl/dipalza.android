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

		Gson gson = new GsonBuilder()
                //.setLenient()
                .setDateFormat("yyyy-MM-dd hh:mm:ss")
                .create();
		
        // Creamos un interceptor y le indicamos el log level a usar
//        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        // Asociamos el interceptor a las peticiones
//        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
//        httpClient.addInterceptor(logging);

		
//		OkHttpClient okHttpClient = new OkHttpClient().newBuilder().addInterceptor(new Interceptor() {
//			
//            public okhttp3.Response intercept(Interceptor.Chain chain) throws IOException {
//                Request originalRequest = chain.request();
//                Request newRequest  = chain.request().newBuilder()
//                        .addHeader("Authorization", "Bearer " + token)
//                        .build();
//                    return chain.proceed(newRequest);
//
//                Request newRequest = builder.build();
//                return chain.proceed(newRequest);
//            }
//        }).build();
		
		

        if (API_SERVICE == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(APIService.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    //.client(httpClient.build()) // <-- usamos el log level
                    .build();
            API_SERVICE = retrofit.create(APIService.class);
        }

        return API_SERVICE;
    }
	
}
