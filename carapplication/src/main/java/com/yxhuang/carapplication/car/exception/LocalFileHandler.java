package com.yxhuang.carapplication.car.exception;

import android.content.Context;

import com.yxhuang.carapplication.car.utils.JFileKit;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.text.DateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2015/10/10.
 */
public class LocalFileHandler  extends BaseExceptionHandler{
    private Context context;
    private DateFormat dateFormat;

    public LocalFileHandler(Context context) {
        this.context = context;
    }

    // 保存错误日志
    public void saveLog(Throwable ex){

        try {
        File errorFile = new File(JFileKit.getDiskCacheDir(context) + "/log/crash.log");
        if (!errorFile.exists()){
                errorFile.createNewFile();
            }

            OutputStream out = new FileOutputStream(errorFile, true);
            out.write(("\n\n----------我是错误的分割线 " + dateFormat.format(new Date()) + "---------\n\n").getBytes());
            PrintStream stream = new PrintStream(out);
            ex.printStackTrace(stream);
            stream.flush();
            out.flush();
            stream.close();
            out.close();

        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean handleException(Throwable ex) {
        return false;
    }
}
