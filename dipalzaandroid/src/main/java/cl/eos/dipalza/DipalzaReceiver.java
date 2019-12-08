package cl.eos.dipalza;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import static android.content.Context.ALARM_SERVICE;

public class DipalzaReceiver extends BroadcastReceiver {
    public static final int REQUEST_CODE=777;
    public static final int INTERVAL_TIME = 1 * 60 * 5000;
    private static final String TAG = "DIPALZA.EOS.RECEIVER";

    public DipalzaReceiver() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.i(TAG, "Recibido el un broadcast:" + intent.getAction());
        //Toast.makeText(context, "Recibido el un broadcast:" + intent.getAction(), Toast.LENGTH_LONG).show();
        if ("android.intent.action.BOOT_COMPLETED".equalsIgnoreCase(intent.getAction())) {
            Intent lIntent = new Intent(context, AlarmReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, REQUEST_CODE, lIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);

            alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + INTERVAL_TIME, INTERVAL_TIME, pendingIntent);

        }
        //Toast.makeText(context, "Booting Completed", Toast.LENGTH_LONG).show();
    }
}
