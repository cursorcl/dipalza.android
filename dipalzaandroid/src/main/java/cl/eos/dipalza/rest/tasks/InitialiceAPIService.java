package cl.eos.dipalza.rest.tasks;

import android.util.Log;

import cl.eos.dipalza.rest.APIService;
import cl.eos.dipalza.rest.parameters.LoginData;
import cl.eos.dipalza.rest.responses.User;
import cl.eos.dipalza.rest.responses.UserResponse;
import cl.eos.dipalza.rest.responses.Vendedor;
import cl.eos.dipalza.rest.responses.VendedorResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InitialiceTask  {

    private static final String TAG = "DIPALZA";
    private APIService restService ;
    private User user = null;
    private Call<UserResponse> requestUserLogin;

    private Vendedor vendedor = null;
    private Call<VendedorResponse> requestVendedor;
    private String code;

    public InitialiceTask(APIService restService, String vendedorCode)
    {
        this.restService =  restService;
        requestUserLogin = restService.login(new LoginData("cursor.cl@gmail.com", "_l2j1rs2"));
        this.code = vendedorCode;
    }

    public void execute()
    {
        if(requestUserLogin == null)
            return;
        requestUserLogin.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (!response.isSuccessful()) {
                    Log.e(TAG, "Error" + response.code());
                    user = null;
                } else {
                    UserResponse userResponse = response.body();
                    user = userResponse.getData();
                    String authHeader = "{'Authorization', 'Bearer '" + user.getToken() + "}";
                    requestVendedor = restService.vendedorByCode(code, authHeader);
                    requestVendedor.enqueue(new Callback<VendedorResponse>() {
                        @Override
                        public void onResponse(Call<VendedorResponse> call, Response<VendedorResponse> response) {
                            if (!response.isSuccessful()) {
                                Log.e(TAG, "Error" + response.code());
                                vendedor = null;
                            } else {
                                Log.i(TAG, vendedor.toString());
                                vendedor = response.body().getData();
                            }
                        }

                        @Override
                        public void onFailure(Call<VendedorResponse> call, Throwable t) {
                            Log.e(TAG, "Error: " + t.getMessage());
                            requestVendedor.cancel();
                            vendedor = null;

                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.e(TAG, "Error: " + t.getMessage());
                requestUserLogin.cancel();
                user = null;

            }
        });
    }

    public User getUser()  {
        return user;
    }
    /**
     * Entrega los datos del vendedor desde la BD.
     * @return Vendedor de la BD.
     */
    public Vendedor getSeller()  {
        if(user == null || user.getToken() == null || user.getToken().isEmpty())
            return null;

        return vendedor;
    }}
