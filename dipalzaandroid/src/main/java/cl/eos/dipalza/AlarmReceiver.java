package cl.eos.dipalza;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;

import java.io.IOException;

import cl.eos.dipalza.rest.APIService;
import cl.eos.dipalza.rest.APIServiceAdapter;
import cl.eos.dipalza.rest.parameters.LoginData;
import cl.eos.dipalza.rest.parameters.PositionData;
import cl.eos.dipalza.rest.responses.PositionResponse;
import cl.eos.dipalza.rest.responses.User;
import cl.eos.dipalza.rest.responses.UserResponse;
import cl.eos.dipalza.rest.responses.Vendedor;
import cl.eos.dipalza.rest.responses.VendedorResponse;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Esta clase recibe la notificación desde la alarma.
 * <p>
 * Recibida la notificación  de la alarma procede a almacenar la posición actual del dispositivo.
 */
public class AlarmReceiver extends BroadcastReceiver {
    private static final String TAG = "DIPALZA.EOS.ALARM";
    private APIService restService;
    private Call<UserResponse> requestUserLogin;
    private Call<VendedorResponse> requestVendedor;


    private class AsyncCaller extends AsyncTask<Void, Void, Void>
    {

        private final Context context;

        public AsyncCaller(Context context) {
            this.context = context;
        }
        
        @Override
        protected Void doInBackground(Void... params) {

            FusedLocationProviderClient mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
            mFusedLocationClient.getLastLocation().addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.i(TAG, "FALLO EL SERVICIO");
                }
            }).addOnSuccessListener(location -> {
                try {
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                    String code = preferences.getString(ActivityConfiguracion.PREF_VENDEDOR, "");
                    if (code.isEmpty())
                        return;

                    Log.i(TAG, "OBTENIENDO EL SERVICIO");
                    restService = APIServiceAdapter.getApiService();
                    Log.i(TAG, "INTENTANDO ACCESO REMÓTO");
                    requestUserLogin = restService.login(new LoginData("cursor.cl@gmail.com", "_l2j1rs2"));
                    Response<UserResponse> userResponse = requestUserLogin.execute();
                    Log.i(TAG, "OBTENIENDO EL SERVICIO");
                    User user = userResponse.body().getData();
                    Log.i(TAG, "USUARIO: " + user);
                    String authHeader = "{'Authorization', 'Bearer '" + user.getToken() + "}";
                    Log.i(TAG, "INTENTANDO OBTENER VENDEDOR");
                    requestVendedor = restService.vendedorByCode(code, authHeader);
                    Response<VendedorResponse> vendedorResponse = requestVendedor.execute();
                    Vendedor vendedor = vendedorResponse.body().getData();
                    Log.i(TAG, "VENDEDOR: " + vendedor);

                    PositionData posData = new PositionData(vendedor.getId(), location.getLatitude(), location.getLongitude(), location.getBearing(), location.getSpeed());
                    Log.i(TAG, "INTENTANDO GRABAR POSICIÓN");
                    Call<PositionResponse> requestSavePoint = restService.savePosicion(vendedor.getId(), posData, user.getToken());
                    requestSavePoint.execute();
                    Log.i(TAG, "POSICIÓN GRABADA");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            return null;
        }

}


    @Override
    public void onReceive(Context context, Intent intent) {
        new AsyncCaller(context).execute();
    }
}
