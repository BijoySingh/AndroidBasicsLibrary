package com.github.bijoysingh.starter.util;

import android.content.Context;
import android.util.Log;

import com.github.bijoysingh.starter.Functions;

import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by bijoy on 1/1/16.
 * The file manager class
 */
public class FileManager {

    /**
     * It store the file data into the file given by the filename
     *
     * @param context  the activity context
     * @param filename the filename to store in
     * @param filedata the string to be stored
     */
    public static void write(Context context, String filename, String filedata) {
        try {
            FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
            fos.write(filedata.getBytes());
            fos.close();
        } catch (Exception e) {
            Log.e(Functions.class.getSimpleName(), e.getMessage(), e);
        }
    }

    /**
     * Reads the data stored in the file given by the filename
     *
     * @param context  the activity context
     * @param filename the name of the filename
     * @return the string read
     */
    public static String read(Context context, String filename) {

        try {
            FileInputStream fis = context.openFileInput(filename);
            StringBuilder builder = new StringBuilder();
            int ch;
            while ((ch = fis.read()) != -1) {
                builder.append((char) ch);
            }

            return builder.toString();
        } catch (Exception e) {
            Log.e(Functions.class.getSimpleName(), e.getMessage(), e);
        }

        return "";
    }

}
