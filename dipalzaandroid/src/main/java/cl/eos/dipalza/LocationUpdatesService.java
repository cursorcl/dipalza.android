package cl.eos.dipalza;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class LocationUpdatesService extends Service {
    public LocationUpdatesService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
