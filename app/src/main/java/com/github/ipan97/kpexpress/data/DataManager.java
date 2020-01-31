package com.github.ipan97.kpexpress.data;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author Ipan Taupik Rahman.
 */
public class DataManager {

    public static SharedPreferences data(Context context) {
        return context.getSharedPreferences("kp-express", Context.MODE_PRIVATE);
    }

    public static SharedPreferences.Editor edit(Context context) {
        return data(context).edit();
    }
}
