package cl.eos.dipalza.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class DipalzaReceiver extends BroadcastReceiver {

    public DipalzaReceiver() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.i("EOOOOOOOOOS", "Recibido el un broadcast:" + intent.getAction());
        Toast.makeText(context, "Recibido el un broadcast:" + intent.getAction(), Toast.LENGTH_LONG).show();
        if("android.intent.action.BOOT_COMPLETED".equalsIgnoreCase(intent.getAction()))
            context.startService(new Intent(context, FusedLocationService.class));

        /*
        Log.w("boot_broadcast_poc", "starting service...");
        Toast.makeText(context, "Booting Completed (1)", Toast.LENGTH_LONG).show();
        context.startService(new Intent(context, NotifyingDailyService.class));
        */
    }
}
