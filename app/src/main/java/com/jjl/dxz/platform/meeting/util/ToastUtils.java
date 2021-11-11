package com.jjl.dxz.platform.meeting.util;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.StringRes;

public class ToastUtils {

    private static Toast toast;

    public static void showShort(Context context, String text) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        toast.show();
    }

    public static void showShort(Context context, @StringRes int resId) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(context, context.getString(resId), Toast.LENGTH_SHORT);
        toast.show();
    }

    public static void showLong(Context context, String text) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
        toast.show();
    }

    public static void showLong(Context context, @StringRes int resId) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(context, context.getString(resId), Toast.LENGTH_SHORT);
        toast.show();
    }
}
