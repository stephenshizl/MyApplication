package com.yxhuang.socket;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class TCPServerService extends Service {

    private boolean mIsServiceDestoryed = false;
    private String[] mDefindedMessages = new String[]{
            "你好，呵呵",
            "-----",
            "今天的天气不好",
            "我的心情也不好"
    };

    public TCPServerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    private class TcpServer implements  Runnable{

        @Override
        public void run() {
            ServerSocket serverSocket = null;
            try {
                // 监听本地 8688 端口
                serverSocket = new ServerSocket(8688);
            } catch (IOException e) {
                System.err.println("establish tcp server failed, port : 8688");
                e.printStackTrace();
                return;
            }

            while (!mIsServiceDestoryed){
                try {
                    // 接收客服端的请求
                    final Socket client = serverSocket.accept();
                    System.out.print("accept");

                    new Thread(){
                        @Override
                        public void run() {
                            try {
                                responseClient(client);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void responseClient(Socket client) throws IOException{
        // 用于接收客户端消息
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        // 用于向客服端发送消息
        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);
        out.println("欢迎到聊天室");

        while (!mIsServiceDestoryed){
            String str = in.readLine();
            System.out.println("msg from client: " + str );
            if (str == null){
                // 客户端断开连接
                break;
            }

            int i = new Random().nextInt(mDefindedMessages.length);
            String msg = mDefindedMessages[i];
            out.println(msg);
            System.out.println("send: " + msg);
        }

        System.out.println("client quit.");
        // 关闭流
        close(out);
        close(in);

        client.close();
    }

  private void close(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
