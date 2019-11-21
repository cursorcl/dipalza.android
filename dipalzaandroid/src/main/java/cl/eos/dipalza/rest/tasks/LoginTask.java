package cl.eos.dipalza.rest.tasks;

import android.util.Log;

import cl.eos.dipalza.rest.APIService;
import cl.eos.dipalza.rest.parameters.LoginData;
import cl.eos.dipalza.rest.responses.User;
import cl.eos.dipalza.rest.responses.UserResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginTask implements Callback<UserResponse> {

    private static final String TAG = "DIPALZA";
    private APIService restService ;
    private User user = null;
    private Call<UserResponse> requestUserLogin;

    public LoginTask(APIService restService)
    {
        this.restService =  restService;
        requestUserLogin = restService.login(new LoginData("cursor.cl@gmail.com", "_l2j1rs2"));

    }

    public void execute()
    {
        if(requestUserLogin == null)
            return;

        requestUserLogin.enqueue(this);
    }

    public User getUser()  {
        return user;
    }

    @Override
    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
        if (!response.isSuccessful()) {
            Log.e(TAG, "Error" + response.code());
            user = null;

        } else {
            UserResponse userResponse = response.body();
            user = userResponse.getData();
            Log.i(TAG, userResponse.toString());
        }
    }

    @Override
    public void onFailure(Call<UserResponse> call, Throwable t) {
            requestUserLogin.cancel();
            user = null;
            Log.e(TAG, "Error: " + t.getMessage());
    }
}
