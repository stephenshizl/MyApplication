//package com.yxhuang.aidl;
//
//import android.app.Service;
//import android.content.Intent;
//import android.os.Binder;
//import android.os.IBinder;
//import android.os.RemoteException;
//
//import java.util.List;
//import java.util.concurrent.CopyOnWriteArrayList;
//
///**
// *  远程服务端 Service 的实现
// */
//public class BookManagerService extends Service {
//
//    private static final String TAG = "BMS";
//
//    private CopyOnWriteArrayList<IBook> mIBookList = new CopyOnWriteArrayList<>();
//
//    private Binder mBinder = new IBookManager.Stub(){
//
//        @Override
//        public List<IBook> getBookList() throws RemoteException {
//            return mIBookList;
//        }
//
//        @Override
//        public void addBook(IBook book) throws RemoteException {
//                mIBookList.add(book);
//        }
//    };
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//
//        mIBookList.add(new IBook() {
//        })
//    }
//
//    @Override
//    public IBinder onBind(Intent intent) {
//        return mBinder;
//    }
//}
