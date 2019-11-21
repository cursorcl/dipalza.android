package cl.eos.dipalza.rest.tasks;

import android.util.Log;

import cl.eos.dipalza.rest.APIService;
import cl.eos.dipalza.rest.responses.User;
import cl.eos.dipalza.rest.responses.Vendedor;
import cl.eos.dipalza.rest.responses.VendedorResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ObtainSellerTask implements Callback<VendedorResponse> {

    private static final String TAG = "DIPALZA";
    private APIService restService ;
    private Vendedor vendedor = null;
    private Call<VendedorResponse> requestVendedor;
    private User user;

    public ObtainSellerTask(APIService restService, User user, String code)
    {
        this.user =  user;
        this.restService =  restService;
        String authHeader = "{'Authorization', 'Bearer '" + user.getToken() + "}";
        requestVendedor = restService.vendedorByCode(code, authHeader);

    }


    public void execute()
    {
        if(requestVendedor == null)
            return;

        requestVendedor.enqueue(this);
    }
    /**
     * Entrega los datos del vendedor desde la BD.
     * @return Vendedor de la BD.
     */
    public Vendedor getSeller()  {
        if(user == null || user.getToken() == null || user.getToken().isEmpty())
            return null;

        return vendedor;
    }

    @Override
    public void onResponse(Call<VendedorResponse> call, Response<VendedorResponse> response) {
        if (!response.isSuccessful()) {
            Log.e(TAG, "Error" + response.code());
            user = null;

        } else {
            vendedor = response.body().getData();
            Log.i(TAG, vendedor.toString());
        }
    }

    @Override
    public void onFailure(Call<VendedorResponse> call, Throwable t) {
            requestVendedor.cancel();
            user = null;
            Log.e(TAG, "Error: " + t.getMessage());
    }
}
