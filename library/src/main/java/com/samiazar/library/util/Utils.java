package com.samiazar.library.util;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.EventLog;
import android.util.Log;
import android.util.TypedValue;

/**
 * Created by USER
 * on 10/13/2018.
 */

public class Utils {
    private static final String TAG = "XmlViewParser.Util";

    public static int parseDpSize(String dpValue, Context context) {
        if (dpValue.matches("(\\d+dp)")) {
            try {
                int dp = Integer.valueOf(dpValue.substring(0, dpValue.indexOf("dp")));
                Resources r = context.getResources();
                float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
                return (int) px;
            } catch (NumberFormatException e) {
                Log.e(TAG, "make sure you set dp value in right way");
                e.printStackTrace();
                return -2;
            }
        } else {
            Log.e(TAG, "make sure you set dp value in right way");
            return -1;
        }
    }


    public static int parseResource(String resource) {
        if (resource.charAt(0) == '@') {
            Log.e(TAG, "in this time only support # color string for resource");
            return 0;
        } else {
            Log.e(TAG, "in this time only support # color and @ address string for resource");
            return 0;
        }
    }

    public static int parseColor(String color) {
        if (color.charAt(0) == '#') {
            if (color.matches("^#[0-9a-fA-F]{8}$|#[0-9a-fA-F]{6}$|#[0-9a-fA-F]{4}$|#[0-9a-fA-F]{3}$"))
                return Color.parseColor(color);
            else {
                Log.e(TAG, "make sure you declare color in right way like #RGB or #ARGB or #RRGGBB or #AARRGGBB");
                return 0;
            }
        } else {
            Log.e(TAG, "in this time only support hex color string for color");
            return -1;
        }
    }
}
