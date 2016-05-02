//package com.yxhuang.aidl;
//
//import android.os.Parcel;
//import android.os.Parcelable;
//
///**
// * Created by Administrator on 2016/4/25.
// */
//public class IBook implements Parcelable {
//
//    public int mBookId;
//    public String mBookName;
//
//    public IBook(int bookId, String bookName){
//        mBookId = bookId;
//        mBookName = bookName;
//
//    }
//
//    protected IBook(Parcel in) {
//        mBookId = in.readInt();
//        mBookName = in.readString();
//    }
//
//    public static final Creator<IBook> CREATOR = new Creator<IBook>() {
//        @Override
//        public IBook createFromParcel(Parcel in) {
//            return new IBook(in);
//        }
//
//        @Override
//        public IBook[] newArray(int size) {
//            return new IBook[size];
//        }
//    };
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeInt(mBookId);
//        dest.writeString(mBookName);
//    }
//}
