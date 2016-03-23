package ipc;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

/**
 *    在单独进程中运行的 Service
 */
public class MessengerService extends Service {
    private static final String TAG = "MessengerService";

    private static class MessengerHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MyConstents.MSG_FROM_CLIENT:
                    // 打印收到的信息
                    Log.i(TAG, "receive mag from Client: " + msg.getData().getString("msg"));

                    // 回复信息
                    Messenger client = msg.replyTo;
                    Message relpyMessage = Message.obtain(null, MyConstents.MSG_FROM_SERVICE);
                    Bundle bundle = new Bundle();
                    bundle.putString("reply", " 嗯，消息已经收到，稍后回复");
                    relpyMessage.setData(bundle);
                    try {
                        client.send(relpyMessage);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;

                default:
                    super.handleMessage(msg);

            }
        }
    }

    private final Messenger mMessenger = new Messenger(new MessengerHandler());


    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "Messenger handler");
        return mMessenger.getBinder();
    }
}