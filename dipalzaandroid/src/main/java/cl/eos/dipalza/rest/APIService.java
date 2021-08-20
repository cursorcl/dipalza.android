package cl.eos.dipalza.rest;

import cl.eos.dipalza.rest.parameters.LoginData;
import cl.eos.dipalza.rest.parameters.PositionData;
import cl.eos.dipalza.rest.parameters.RegisterData;
import cl.eos.dipalza.rest.responses.PositionResponse;
import cl.eos.dipalza.rest.responses.User;
import cl.eos.dipalza.rest.responses.UserResponse;
import cl.eos.dipalza.rest.responses.VendedorResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/*
 * GET|HEAD  | api/code/{code}						       | api,auth:api
 * POST      | api/login                                   |api          
 * POST      | api/register                                |api          
 * POST      | api/vendedor                                |api,auth:api 
 * GET|HEAD  | api/vendedor                                |api          
 * DELETE    | api/vendedor/{vendedor}                     |api,auth:api 
 * PUT|PATCH | api/vendedor/{vendedor}                     |api,auth:api 
 * GET|HEAD  | api/vendedor/{vendedor}                     |api          
 * POST      | api/vendedor/{vendedor}/posicion            |api          
 * GET|HEAD  | api/vendedor/{vendedor}/posicion            |api          
 * DELETE    | api/vendedor/{vendedor}/posicion/{posicion} |api          
 * PUT|PATCH | api/vendedor/{vendedor}/posicion/{posicion} |api          
 * GET|HEAD  | api/vendedor/{vendedor}/posicion/{posicion} |api          
 */

public interface APIService {
	 //String BASE_URL = "http://servcamiones.test/api/";
	//String BASE_URL = "http://10.0.2.2:8000/api/";
	String BASE_URL = "http://www.eospruebas.com/servcamiones/api/";


	@Headers("Content-Type: application/json")

	@POST("register")
	Call<User> register(@Body RegisterData body);

	@POST("login")
	Call<UserResponse> login(@Body LoginData body);

	@GET("vendedor/{vendedor}")
	Call<VendedorResponse> vendedor(@Path("vendedor") int vendedor, @Header("Authorization") String authHeader);

	@GET("codigo/{codigo}")
	Call<VendedorResponse> vendedorByCode(@Path("codigo") String codigo, @Header("Authorization") String authHeader);
	//
	@GET("vendedor")
	Call<VendedorResponse> vendedor(@Header("Authorization") String authHeader);

	@POST("vendedor/{vendedor}/posicion")
	Call<PositionResponse> savePosicion(@Path("vendedor") int vendedor, @Body PositionData body, @Header("Authorization") String authHeader);
	

}
