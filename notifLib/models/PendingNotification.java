package tamhoang.ldpro4.notifLib.models;

import android.service.notification.StatusBarNotification;

import androidx.core.app.NotificationCompat;

import java.util.concurrent.ScheduledFuture;
import tamhoang.ldpro4.notifLib.utils.VersionUtils;

/* loaded from: classes.dex */
public class PendingNotification {
    private String key;
    private StatusBarNotification sbn;
    private ScheduledFuture<?> scheduledFuture;

    public PendingNotification(StatusBarNotification sbn) {
        this.sbn = sbn;
        this.key = VersionUtils.isLollipop() ? sbn.getKey() : null;
    }

    public boolean equals(Object o) {
        if (!VersionUtils.isJellyBean()) {
            return ((PendingNotification) o).getSbn().getPackageName().equals(this.sbn.getPackageName());
        }
        String group = NotificationCompat.getGroup(((PendingNotification) o).getSbn().getNotification());
        String group2 = NotificationCompat.getGroup(this.sbn.getNotification());
        if (group == null || group2 == null) {
            return false;
        }
        return group.equals(group2);
    }

    public String getDismissKey() {
        return this.key;
    }

    public StatusBarNotification getSbn() {
        return this.sbn;
    }

    public ScheduledFuture<?> getScheduledFuture() {
        return this.scheduledFuture;
    }

    public void setDismissKey(String key) {
        this.key = key;
    }

    public void setScheduledFuture(ScheduledFuture<?> scheduledFuture) {
        this.scheduledFuture = scheduledFuture;
    }
}
