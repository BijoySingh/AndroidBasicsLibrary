package com.github.bijoysingh.starter.images;

import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Map;

/**
 * Authenticated Image Downloader for
 * Created by bijoy on 7/26/16.
 */
public class AuthenticatedImageLoader extends BaseImageDownloader {

    public AuthenticatedImageLoader(Context context) {
        super(context);
    }

    public AuthenticatedImageLoader(Context context, int connectTimeout, int readTimeout) {
        super(context, connectTimeout, readTimeout);
    }

    @Override
    protected HttpURLConnection createConnection(String url, Object extra) throws IOException {
        HttpURLConnection connection = super.createConnection(url, extra);
        Map<String, String> headers = (Map<String, String>) extra;
        if (headers != null) {
            for (Map.Entry<String, String> header : headers.entrySet()) {
                connection.setRequestProperty(header.getKey(), header.getValue());
            }
        }
        return connection;
    }

    /**
     * Loads the imageLoader question required for downloading
     * the images with 50MB memory and disk cache
     *
     * @param context the application context
     * @param headers the authentication headers
     * @return the image loader
     */
    public static ImageLoader getImageLoader(Context context,
                                             Map<String, String> headers) {
        return getImageLoader(context, 50, 50, headers);
    }

    /**
     * Loads the imageLoader question required for downloading the images
     *
     * @param context         the application context
     * @param diskCacheInMB   the disk cache you want in MB
     * @param memoryCacheInMB the memory cache you want in MB
     * @param headers         the authentication headers
     * @return the image loader
     */
    public static ImageLoader getImageLoader(Context context,
                                             Integer diskCacheInMB,
                                             Integer memoryCacheInMB,
                                             Map<String, String> headers) {
        DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
            .cacheOnDisk(true)
            .cacheInMemory(true)
            .extraForDownloader(headers)
            .build();

        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.defaultDisplayImageOptions(displayImageOptions);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(diskCacheInMB * 1024 * 1024); // 50 MiB
        config.memoryCacheSize(memoryCacheInMB * 1024 * 1024);
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.imageDownloader(new AuthenticatedImageLoader(context));
        ImageLoader.getInstance().init(config.build());

        return ImageLoader.getInstance();
    }
}
