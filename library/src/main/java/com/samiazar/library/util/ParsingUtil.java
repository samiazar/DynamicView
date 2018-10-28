package com.samiazar.library.util;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.util.EventLog;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by USER
 * on 10/13/2018.
 */

public class ParsingUtil {
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


    public static int parseResource(String resource, Context context) {
        if (resource.charAt(0) == '@') {
            try {
                String resFolder = resource.substring(1, resource.indexOf("/"));
                Class ResFolder = Class.forName(context.getPackageName()+".R$"+resFolder);
                Field field = ResFolder.getField(resource.substring(resource.indexOf("/")+1, resource.length()));
                return (int) field.get(null);
            } catch (ClassNotFoundException e) {
                Log.e(TAG, "can not find R.java class");
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                Log.e(TAG, "the field of resource not defined");
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                Log.e(TAG, "can not get static field in R");
                e.printStackTrace();
            } catch (ClassCastException e) {
                Log.e(TAG, "the value of field is not integer");
                e.printStackTrace();
            }
            return 0;
        } else {
            Log.e(TAG, "in this time only support # color and @ address string for resource");
            return 0;
        }
    }

    public static int parseColor(String color) {
        if (color.charAt(0) == '#') {
            if (color.matches("^#[0-9a-fA-F]{8}$|#[0-9a-fA-F]{6}$"))
                return Color.parseColor(color);
            else {
                Log.e(TAG, "make sure you declare color in right way like #RRGGBB or #AARRGGBB");
                return 0;
            }
        } else {
            Log.e(TAG, "in this time only support hex color string for color");
            return 0;
        }
    }

    public static String parseLink(String str) {
        if (str.matches("https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)")) {
            return str;
        } else {
            return "";
        }
    }

    public static int parseId(String id) {
        String idName;
        if (id.startsWith("@+id/"))
            idName = id.substring(4, id.length()-1);
        else if (id.startsWith("@id/"))
            idName = id.substring(3, id.length()-1);
        else
            return -1;
        return (idName.hashCode() & 0xfffffff);
    }

    public static int generateViewId() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return (generateUniqueInteger());
        } else {
            return (View.generateViewId());
        }
    }

     private static int generateUniqueInteger() {
        AtomicInteger sNextGeneratedId = new AtomicInteger(1);
        for (;;) {
            final int result = sNextGeneratedId.get();
            // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
            int newValue = result + 1;
            if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
            if (sNextGeneratedId.compareAndSet(result, newValue)) {
                return result;
            }
        }
    }
}
