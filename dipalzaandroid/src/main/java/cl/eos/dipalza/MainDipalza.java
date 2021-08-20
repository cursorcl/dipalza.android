package cl.eos.dipalza;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


import java.util.ArrayList;
import java.util.List;

public class MainDipalza extends DashboardActivity {

    public static final String TAG = "cl.eos.MainDipalza";

    private static Context context;

    /**
     * Referencia a controlador de sistema.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        registerChannels();
        setContentView(R.layout.activity_home);

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

    public static Context getAppContext() {
        return context;
    }
}