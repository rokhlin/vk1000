package com.mycvapps.rav.vk1000;
/**
 * Класс реализует стабильную подгрузку фото в КЭШ
 */

import android.content.Context;

import com.squareup.picasso.Downloader;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

public class PicassoCache {

    private static Picasso picassoInstance = null;

    private PicassoCache (Context context) {

        Downloader downloader   = new OkHttpDownloader(context, Integer.MAX_VALUE);
        Picasso.Builder builder = new Picasso.Builder(context);
        builder.downloader(downloader);

        picassoInstance = builder.build();
    }


    public static Picasso getPicassoInstance (Context context) {

        if (picassoInstance == null) {

            new PicassoCache(context);
            return picassoInstance;
        }

        return picassoInstance;
    }
}
