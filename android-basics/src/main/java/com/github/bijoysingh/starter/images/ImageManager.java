package com.github.bijoysingh.starter.images;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Image Manager, for basic image tasks
 * Created by bijoy on 1/6/16.
 */
public class ImageManager {

    public static final Integer PICK_IMAGE_REQUEST = 2139;
    Activity activity;

    /**
     * ImageManager constructor
     *
     * @param activity the activty making the image request
     */
    public ImageManager(Activity activity) {
        this.activity = activity;
    }

    /**
     * Converts the bitmap to Base64
     *
     * @param bmp the bitmap
     * @return the base 64 format
     */
    public static String toBase64(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    /**
     * Resize the bitmap to a fixed width and height
     *
     * @param bitmap the bitmap image
     * @param width  the final width of the image
     * @param height the final height of the image
     * @return the resized bitmap image
     */
    public static Bitmap resizeBitmap(Bitmap bitmap, Integer width, Integer height) {
        return Bitmap.createScaledBitmap(bitmap, width, height, true);
    }

    /**
     * Scales the bitmap by a scale factor
     *
     * @param bitmap the bitmap image
     * @param scale  the scale factor
     * @return the scaled bitmap image
     */
    public static Bitmap getScaledBitmap(Bitmap bitmap, float scale) {
        Integer originalHeight = bitmap.getHeight();
        Integer originalWidth = bitmap.getWidth();

        Integer requiredHeight = Math.round(originalHeight * scale);
        Integer requiredWidth = Math.round(originalWidth * scale);

        return resizeBitmap(bitmap, requiredWidth, requiredHeight);
    }

    /**
     * Scales the bitmap to a fixed height
     *
     * @param bitmap the bitmap image
     * @param height the final height of the image
     * @return the scaled bitmap
     */
    public static Bitmap getScaledBitmapWithHeight(Bitmap bitmap, Integer height) {
        Integer originalHeight = bitmap.getHeight();
        float scale = height * 1.0f / originalHeight * 1.0f;
        return getScaledBitmap(bitmap, scale);
    }

    public static Bitmap getScaledBitmapWithWidth(Bitmap bitmap, Integer width) {
        Integer originalWidth = bitmap.getWidth();
        float scale = width * 1.0f / originalWidth * 1.0f;
        return getScaledBitmap(bitmap, scale);
    }

    public void showFileChooser(String chooserTitle) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        activity.startActivityForResult(
            Intent.createChooser(intent, chooserTitle),
            PICK_IMAGE_REQUEST);
    }

    public void showFileChooser() {
        showFileChooser("Select Picture");
    }

    public Bitmap handleResponse(Integer requestCode, Integer resultCode, Intent data)
        throws Exception {
        if (requestCode.equals(PICK_IMAGE_REQUEST)
            && resultCode.equals(Activity.RESULT_OK)
            && data != null) {
            final Uri imageUri = data.getData();
            final InputStream imageStream = activity.getContentResolver().openInputStream(imageUri);
            return BitmapFactory.decodeStream(imageStream);
        }

        return null;
    }
}
