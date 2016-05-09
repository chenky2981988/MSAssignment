package com.chirag.msassignment.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;

/**
 * Created by chirag on 08/05/16.
 *
 * This class contain static variables and method which will used in application.
 */
public class AppUtils {

    public static String SEARCH_URL = "https://en.wikipedia.org/w/api.php?action=query&prop=pageimages&format=json&piprop=thumbnail&pithumbsize=%d&pilimit=50&generator=prefixsearch&gpssearch=%s";
    public static String PAGES_KEY = "pages";
    public static String QUERY_KEY = "query";
    public static String PAGEID_KEY = "pageid";
    public static String NS_KEY = "ns";
    public static String TITLE_KEY = "title";
    public static String INDEX_KEY = "index";
    public static String THUMBNAIL_KEY = "thumbnail";
    public static String WIKI_IMG_SEARCH = "wiki_img_search";

    public static int getDeviceWidth(Activity context){
        DisplayMetrics displaymetrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int width = displaymetrics.widthPixels;
        return width;
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
}
