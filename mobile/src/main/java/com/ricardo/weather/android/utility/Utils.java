package com.ricardo.weather.android.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.ricardo.weather.android.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class Utils
{
	
	// General

	public static final String TAG = "WeatherForecast";

    public static final String GET_METHOD = "GET";

    public static final String QUESTION_MARK = "?";

    public static final String AND = "&";

    public static final String SLASH = "/";


    // Typefaces

    private static final String APP_TYPEFACE = "RobotoTTF";

        private static final String LIGHT_TYPEFACE = "Roboto-Light.ttf";
        private static final String REGULAR_TYPEFACE = "Roboto-Regular.ttf";
        private static final String BOLD_TYPEFACE = "Roboto-Bold.ttf";

    public static Typeface robotoLight;
    public static Typeface robotoRegular;
    public static Typeface robotoBold;

    public static SharedPreferences preferences;

    // Images

    private static ImageLoader mImageLoader;

    private static DisplayImageOptions mDisplayImageOptions;



    public static void init(Context context) {

        initTypefaces(context);

        initPreferences(context);

    }


    private static void initPreferences(Context context) {


        preferences = PreferenceManager.getDefaultSharedPreferences(context);


        // Init Speed Unit

        if(!preferences.contains(context.getString(R.string.speed_unit)))

            preferences.edit().putString(context.getString(R.string.speed_unit), context.getString(R.string.kilometers_ph_key)).commit();


        // Init Temperature Unit

        if(!preferences.contains(context.getString(R.string.temperature_unit)))

            preferences.edit().putString(context.getString(R.string.temperature_unit), context.getString(R.string.temperature_unit_key)).commit();


    }


    public static void initTypefaces(Context context) {

        robotoLight = Typeface.createFromAsset(context.getAssets(), APP_TYPEFACE + SLASH + LIGHT_TYPEFACE);
        robotoRegular = Typeface.createFromAsset(context.getAssets(), APP_TYPEFACE + SLASH + REGULAR_TYPEFACE);
        robotoBold = Typeface.createFromAsset(context.getAssets(), APP_TYPEFACE + SLASH + BOLD_TYPEFACE);

    }


    // Conversions

    public static ArrayList<Object> toArrayList(JSONArray array) {

        ArrayList<Object> result = null;

        if(array != null && array.length() > 0) {

            result = new ArrayList<Object>();

            for(int i = 0; i < array.length(); i++) {

                try {

                    result.add(array.get(i));

                } catch (JSONException exception) { }

            }

        }

        return result;

    }


    // Preferences

    public static String getPreferenceValue(String key) {

        return preferences.getString(key, "");

    }

    // Images

    private static boolean alreadyLoaded(String url, ImageView imageView) {

        if (imageView.getTag() != null && imageView.getTag().equals(url)) {
            return true;
        } else {
            imageView.setTag(url);
            return false;
        }
    }

    public static void displayImage(final Context context, final String url, final ImageView imageView) {

        new AsyncTask<Void, Void, Void>() {

            boolean loaded;

            public Void doInBackground(Void... params) {


                loaded = alreadyLoaded(url, imageView);

                if (!loaded) {

                    if (mImageLoader == null) {

                        mImageLoader = ImageLoader.getInstance();

                        ImageLoaderConfiguration imageLoaderConfiguration = new ImageLoaderConfiguration.Builder(context)
                                .memoryCache(new WeakMemoryCache())
                                .build();

                        mImageLoader.init(imageLoaderConfiguration);
                    }

                    if (mDisplayImageOptions == null) {


                        mDisplayImageOptions = new DisplayImageOptions.Builder()
                                .cacheInMemory(true)
                                .cacheOnDisk(true)
                                .bitmapConfig(Bitmap.Config.RGB_565)
                                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                                .build();


                    }

                }

                return null;

            }

            public void onPostExecute(Void result) {

                if(!loaded)

                    mImageLoader.displayImage(url, imageView, mDisplayImageOptions);

            }


        }.execute();

    }

}
