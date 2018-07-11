package com.mg.kode.kodebrowser.utils;


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.ColorInt;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.ColorUtils;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;

public class UIUtils {

    /**
     * Sets {@link View#INVISIBLE} property to views.
     *
     * @param views views to be applied property to.
     */
    public static void hideViews(View... views) {
        for (View v :
                views) {
            v.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * Sets {@link View#GONE} property to views.
     *
     * @param views views to be applied property to.
     */
    public static void removeViews(View... views) {
        for (View v :
                views) {
            v.setVisibility(View.GONE);
        }
    }

    /**
     * Sets {@link View#VISIBLE} property to views.
     *
     * @param views views to be applied property to.
     */
    public static void showViews(View... views) {
        for (View v :
                views) {
            v.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Show short short toast message.
     *
     * @param context
     * @param msg
     */
    public static void showShortToast(Context context, String msg) {
        if (context != null)
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * Shows short toast message.
     *
     * @param context
     * @param stringId
     */
    public static void showShortToast(Context context, @StringRes int stringId) {
        if (context != null) {
            showShortToast(context, context.getString(stringId));
        }
    }

    /**
     * Return new color value with alpha channel. Alpha can be a float from [0,1]. If value exceeds allowed range, it will be rounded to nearest one.
     *
     * @param color resource int pointing to a color value.
     * @param alpha alpha value in range [0,1], where 0 means full transparency and 1 means full color.
     * @return new color with alpha channel.
     */
    @ColorInt
    public static int adjustAlpha(@ColorInt int color, float alpha) {
        Log.e("UIUtils", "Alpha is " + alpha + ". Alpha must be in [0,1] range.");
        if (alpha < 0) {
            alpha = 0;
        } else if (alpha > 1) {
            alpha = 1;
        }
        int a = Math.round(255 * alpha);
        return ColorUtils.setAlphaComponent(color, a);
    }

    public static int getStatusBarSize(Activity activity) {
        Rect r = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
        return r.top;
    }

    public static Point getNavigationBarSize(Context context) {
        Point appUsableSize = getAppUsableScreenSize(context);
        Point realScreenSize = getRealScreenSize(context);

        // navigation bar on the side
        if (appUsableSize.x < realScreenSize.x) {
            return new Point(realScreenSize.x - appUsableSize.x, appUsableSize.y);
        }

        // navigation bar at the bottom
        if (appUsableSize.y < realScreenSize.y) {
            return new Point(appUsableSize.x, realScreenSize.y - appUsableSize.y);
        }

        // navigation bar is not present
        return new Point();
    }

    public static Point getAppUsableScreenSize(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size;
    }

    public static Point getRealScreenSize(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();

        if (Build.VERSION.SDK_INT >= 17) {
            display.getRealSize(size);
        } else if (Build.VERSION.SDK_INT >= 16) {
            try {
                size.x = (Integer) Display.class.getMethod("getRawWidth").invoke(display);
                size.y = (Integer) Display.class.getMethod("getRawHeight").invoke(display);
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            } catch (NoSuchMethodException e) {
            }
        }

        return size;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setStatusBarColor(Activity activity, int colorRes) {
        Window window = activity.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(activity, colorRes));
    }

    public static void showKeyboard(View view) {
        view.requestFocus();
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null)
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null && activity.getCurrentFocus() != null)
            imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }
}
