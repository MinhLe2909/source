package tamhoang.ldpro4.ui.notification;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.webkit.JavascriptInterface;

import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;

import tamhoang.ldpro4.ui.fragment.TructiepXoso;
import tamhoang.ldpro4.ui.home.MainActivity;

public class NotificationBindObject {
    private final Context mContext;

    public NotificationBindObject(Context context) {
        this.mContext = context;
    }

    @JavascriptInterface
    public void showNotification(String message) {
        NotificationCompat.Builder autoCancel = new NotificationCompat.Builder(this.mContext).setContentText(message).setAutoCancel(true);
        Intent intent = new Intent(this.mContext, MainActivity.class);
        intent.putExtra(TructiepXoso.EXTRA_FROM_NOTIFICATION, true);
        TaskStackBuilder create = TaskStackBuilder.create(this.mContext);
        create.addParentStack(MainActivity.class);
        create.addNextIntent(intent);
        autoCancel.setContentIntent(create.getPendingIntent(0, 134217728));
        ((NotificationManager) this.mContext.getSystemService(Context.NOTIFICATION_SERVICE)).notify(-1, autoCancel.build());
    }
}
