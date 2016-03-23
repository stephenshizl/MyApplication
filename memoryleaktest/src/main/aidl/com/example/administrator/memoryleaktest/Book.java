package com.example.administrator.memoryleaktest;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/2/10.
 */
public class Book implements Parcelable {
    private int bookId;
    private String bookName;

    public Book(int bookId, String bookName){
        this.bookId = bookId;
        this.bookName = bookName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(bookId);
        out.writeString(bookName);
    }

    public static final Parcelable.Creator<Book> CREATOR = new Parcelable.Creator<Book>(){

        @Override
        public Book createFromParcel(Parcel source) {
            return new Book(source);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    private Book(Parcel source){
        bookId = source.readInt();
        bookName = source.readString();
    }
}
