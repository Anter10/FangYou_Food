package com.jjkj.guoyouchao.fangyou_food.TOOLS;

/**
 * Created by guoyouchao on 16/6/26.
 */

import java.io.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

import cz.msebera.android.httpclient.util.EncodingUtils;

public class FileManager {

    public static void writeFileSdcardFile(String fileName,String write_str) throws IOException{
        try{

            FileOutputStream fout = new FileOutputStream(fileName);
            byte [] bytes = write_str.getBytes();

            fout.write(bytes);
            fout.close();
        }

        catch(Exception e){
            e.printStackTrace();
        }
    }

    //读SD中的文件
    public static String readFileSdcardFile(String fileName) throws IOException{
        String res="";
        try{
            FileInputStream fin = new FileInputStream(fileName);

            int length = fin.available();

            byte [] buffer = new byte[length];
            fin.read(buffer);

            res = EncodingUtils.getString(buffer, "UTF-8");

            fin.close();
        }

        catch(Exception e){
            e.printStackTrace();
        }
        return res;
    }


}
