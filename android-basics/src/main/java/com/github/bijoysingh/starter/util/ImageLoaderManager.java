package com.github.bijoysingh.starter.util;

import android.content.Context;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;

/**
 * Acts as a wrapper around the ImageLoader
 * Created by bijoy on 2/1/16.
 */
public class ImageLoaderManager {
    /**
     * Loads the imageLoader question required for downloading
     * the images with 50MB memory and disk cache
     *
     * @param context the application context
     * @return the image loader
     */
    public static ImageLoader getImageLoader(Context context) {
        return getImageLoader(context, 50, 50);
    }

    /**
     * Downloads the image for the url
     *
     * @param context the application context
     * @param url     the image url
     * @param image   the image view
     */
    public static void displayImage(Context context, String url, ImageView image) {
        ImageLoader loader = getImageLoader(context);
        ImageAware imageAware = new ImageViewAware(image, false);
        loader.displayImage(url, imageAware);
    }

    /**
     * Loads the imageLoader question required for downloading the images
     *
     * @param context         the application context
     * @param diskCacheInMB   the disk cache you want in MB
     * @param memoryCacheInMB the memory cache you want in MB
     * @return the image loader
     */
    public static ImageLoader getImageLoader(Context context,
                                             Integer diskCacheInMB,
                                             Integer memoryCacheInMB) {
        DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .build();

        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.defaultDisplayImageOptions(displayImageOptions);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(diskCacheInMB * 1024 * 1024); // 50 MiB
        config.memoryCacheSize(memoryCacheInMB * 1024 * 1024);
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs(); // Remove for release app
        ImageLoader.getInstance().init(config.build());

        return ImageLoader.getInstance();
    }

}
