package com.zizehost.ysdlpapp.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by Guillermo on 06/04/2016.
 */
public class SyncServiceYSDLP extends Service {

    // Instancia del sync adapter
    private static SyncAdapterYSDLP syncAdapter = null;
    // Objeto para prevenir errores entre hilos
    private static final Object lock = new Object();

    @Override
    public void onCreate() {
        synchronized (lock) {
            if (syncAdapter == null) {
                syncAdapter = new SyncAdapterYSDLP(getApplicationContext(), true);
            }
        }
    }

    /* Retorna interfaz de comunicación para que el sistema llame al sync adapter */
    @Override
    public IBinder onBind(Intent intent) {
        return syncAdapter.getSyncAdapterBinder();
    }
}