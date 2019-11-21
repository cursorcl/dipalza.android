package cl.eos.dipalza.rest.tasks;

import android.util.Log;

import cl.eos.dipalza.rest.APIService;
import cl.eos.dipalza.rest.parameters.LoginData;
import cl.eos.dipalza.rest.parameters.PositionData;
import cl.eos.dipalza.rest.responses.PositionResponse;
import cl.eos.dipalza.rest.responses.User;
import cl.eos.dipalza.rest.responses.UserResponse;
import cl.eos.dipalza.rest.responses.Vendedor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SavePositionTask implements Callback<PositionResponse> {

    private static final String TAG = "DIPALZA";
    private final String authHeader;
    private APIService restService ;
    private User user;
    private Vendedor vendedor;
    private Call<UserResponse> requestUserLogin;

    public SavePositionTask(APIService restService, User user, Vendedor vendedor)
    {
        this.restService =  restService;
        this.user =  user;
        this.vendedor =  vendedor;
        authHeader = "{'Authorization', 'Bearer '" + user.getToken() + "}";
    }

    public void execute(PositionData position)
    {
        Call<PositionResponse> positionSaveRequest = restService.savePosicion(vendedor.getId(), position, authHeader);
        positionSaveRequest.enqueue(this);
    }

    public User getUser()  {
        return user;
    }

    @Override
    public void onResponse(Call<PositionResponse> call, Response<PositionResponse> response) {
        if (!response.isSuccessful()) {
            Log.e(TAG, "Error" + response.code());
            user = null;

        } else {
            PositionResponse posResponse = response.body();
            Log.i(TAG, posResponse.toString());
        }
    }

    @Override
    public void onFailure(Call<PositionResponse> call, Throwable t) {
            requestUserLogin.cancel();
            user = null;
            Log.e(TAG, "Error: " + t.getMessage());
    }
}
