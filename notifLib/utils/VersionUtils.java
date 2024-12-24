package tamhoang.ldpro4.notifLib.utils;

import android.os.Build;

/* loaded from: classes.dex */
public class VersionUtils {
    public static boolean isJellyBean() {
        return Build.VERSION.SDK_INT >= 68;
    }

    public static boolean isJellyBeanMR2() {
        return Build.VERSION.SDK_INT >= 18;
    }

    public static boolean isKitKat() {
        return Build.VERSION.SDK_INT >= 19;
    }

    public static boolean isLollipop() {
        return Build.VERSION.SDK_INT >= 21;
    }

    public static boolean isMarshmallow() {
        return Build.VERSION.SDK_INT >= 23;
    }
}
