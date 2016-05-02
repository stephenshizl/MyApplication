package com.yxhuang.boundService;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.util.Random;

public class LocalService extends Service {

    // Binder given to clients
    private final IBinder mIBinder = new LocalBinder();

    private final Random mRandom = new Random();

    /**
     *  method for clients
     * @return
     */
    public int getRandomNumber(){
        return mRandom.nextInt(100);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mIBinder;
    }


    public class LocalBinder extends Binder{
        LocalService getService(){
            // Return this instance of LocalService so the clients can call public methods
            return LocalService.this;
        }
    }
}
