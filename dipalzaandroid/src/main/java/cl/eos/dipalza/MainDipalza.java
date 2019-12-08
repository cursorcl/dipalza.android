package cl.eos.dipalza;

import android.Manifest;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.LocationSettingsRequest;

import java.util.ArrayList;
import java.util.List;

public class MainDipalza extends DashboardActivity {

    public static final String TAG = "cl.eos.MainDipalza";

    private static Context context;

    public static final int REQUEST_CODE=777;
    public static final int INTERVAL_TIME = 1 * 60 * 5000;

    /**
     * Referencia a controlador de sistema.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        registerChannels();
        setContentView(R.layout.activity_home);

        validarPermisos();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        Intent lIntent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, REQUEST_CODE, lIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + INTERVAL_TIME, INTERVAL_TIME, pendingIntent);
    }
    static int MY_PERMISSIONS_REQUEST_POSITION = 7777;

    private void validarPermisos()
    {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED
        )
        {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_BACKGROUND_LOCATION},
                        MY_PERMISSIONS_REQUEST_POSITION);
        }
    }

    private void registerChannels() {
        final List<NotificationChannel> channels = new ArrayList<>();

        NotificationChannel channelInfo;
        channelInfo = new NotificationChannel(getString(R.string.INFO_CHANNEL_ID), getString(R.string.INFO_CHANNEL_NAME), NotificationManager.IMPORTANCE_DEFAULT);
        channelInfo.setDescription(getString(R.string.INFO_CHANNEL_DESC));


        NotificationChannel channelError = new NotificationChannel(getString(R.string.ERROR_CHANNEL_ID), getString(R.string.ERROR_CHANNEL_NAME), NotificationManager.IMPORTANCE_HIGH);
        channelError.setDescription(getString(R.string.ERROR_CHANNEL_DESC));


        channels.add(channelInfo);
        channels.add(channelError);
        final NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.createNotificationChannels(channels);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menumaindipalza, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemVentas:
                realizarVentas();
                return true;
            case R.id.itemInicializacion:
                realizarInicializacion();
                return true;
            case R.id.itemResumen:
                visaulizarResumenVentas();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Metodo que visualiza resumen de ventas.
     */
    private void visaulizarResumenVentas() {
        Intent intent = new Intent(this, VentasResumen.class);
        this.startActivity(intent);
    }

    /**
     * Metodo que solicita al controlador inicializar interfaz de
     * inicialziacion.
     */
    private void realizarInicializacion() {
        Intent intent = new Intent(this, ActivityConfiguracion.class);
        this.startActivity(intent);
    }

    /**
     * Metodo que solicita al controlador inicializar interfaz de
     * inicializacion.
     */
    private void realizarVentas() {
        Intent intent = new Intent(this, VentaRegistros.class);
        this.startActivity(intent);
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    /**
     * Stores activity data in the Bundle.
     */
/*	public void onSaveInstanceState(Bundle savedInstanceState) {
		if(mLastUpdateTime != null) {
			savedInstanceState.putBoolean(KEY_REQUESTING_LOCATION_UPDATES, mRequestingLocationUpdates);
			savedInstanceState.putParcelable(KEY_LOCATION, mCurrentLocation);
			savedInstanceState.putString(KEY_LAST_UPDATED_TIME_STRING, mLastUpdateTime.toString());
		}
		super.onSaveInstanceState(savedInstanceState);
	}*/

    public static Context getAppContext() {
        return context;
    }
}