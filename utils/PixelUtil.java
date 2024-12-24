package tamhoang.ldpro4.utils;

import android.content.Context;

/* loaded from: classes.dex */
public class PixelUtil {
    private PixelUtil() {
    }

    public static int dpToPx(Context context, int dp) {
        return Math.round(dp * getPixelScaleFactor(context));
    }

    private static float getPixelScaleFactor(Context context) {
        return context.getResources().getDisplayMetrics().xdpi / 160.0f;
    }

    public static int pxToDp(Context context, int px) {
        return Math.round(px / getPixelScaleFactor(context));
    }
}
