package com.yxhuang.boundService;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import android.widget.Toast;

import java.lang.ref.WeakReference;

public class MessengerService extends Service {
    public static final int MSG_FROM_CLIENTS = 1;

    // Target we publish for clients to send message to IncomingHandler
    private final Messenger mMessenger = new Messenger(new IncomingHandler(this));


    /**
     *  When binding to the service, we return an interface  to our messenger
     *  for sending message to the service
     * @param intent
     * @return
     */
    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(this, "binding", Toast.LENGTH_SHORT).show();
        return  mMessenger.getBinder();
    }


    /**
     *  Handler for incoming message form clients
     */
    private static class IncomingHandler extends Handler{

        private WeakReference<Context> mReference;
        private Context mContext;

        public IncomingHandler(Context context){
            mReference = new WeakReference<>(context);
            mContext = mReference.get();
        }
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MSG_FROM_CLIENTS:
                    Log.i("yxh", "messenger service  handle message");
                    Toast.makeText(mContext, "hello !", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    }
}
