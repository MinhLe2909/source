package tamhoang.ldpro4.ui.notification;

import android.app.Notification;
import android.os.Build;
import android.os.Bundle;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import tamhoang.ldpro4.data.models.Contact;
import tamhoang.ldpro4.notifLib.models.Action;
import tamhoang.ldpro4.notifLib.utils.NotificationUtils;
import tamhoang.ldpro4.ui.home.MainActivity;
import tamhoang.ldpro4.ui.notification.NotificationReader;

/* loaded from: classes.dex */
public class NotificationNewReader extends tamhoang.ldpro4.ui.notification.NotificationReader {
    @Override // tamhoang.ldpro4.NotificationReader
    public void NotificationWearReader(String customerName, String messages) {
        if (customerName.indexOf("ZL - ") <= -1) {
            super.NotificationWearReader(customerName, messages);
            return;
        }
        int lastIndexOf = MainActivity.arr_TenKH.lastIndexOf(customerName);
        if (lastIndexOf > -1) {
            try {
                if (MainActivity.contactslist.get(lastIndexOf).remoteInput == null) {
                    MainActivity.actionslist.get(lastIndexOf).sendReply(MainActivity.Notifi, messages);
                    if (MainActivity.Json_Tinnhan.has(customerName)) {
                        MainActivity.Json_Tinnhan.remove(customerName);
                    }
                } else {
                    super.NotificationWearReader(customerName, messages);
                }
            } catch (Exception unused) {
                super.NotificationWearReader(customerName, messages);
            }
        }
    }

    @Override // tamhoang.ldpro4.NotificationReader, android.app.Service
    public void onCreate() {
        super.onCreate();
    }

    @Override // tamhoang.ldpro4.NotificationReader, android.service.notification.NotificationListenerService
    public void onNotificationPosted(StatusBarNotification statusBarNotification) {
        Bundle bundle;
        Notification notification = statusBarNotification.getNotification();
        Notification.WearableExtender wearableExtender = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            wearableExtender = new Notification.WearableExtender(notification);
        }
        Bundle bundle2 = notification.extras;
        Log.d("onNotificationPosted", "onNotificationPosted: Print Keys");
        for (String str : bundle2.keySet()) {
            Log.d("onNotificationPosted", "KEY : " + str + " -> " + bundle2.get(str));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            if (statusBarNotification.getPackageName().equals(NotificationReader.ZALO) && wearableExtender.getActions().isEmpty() && (bundle = statusBarNotification.getNotification().extras) != null) {
                this.ID = bundle.getCharSequence(NotificationCompat.EXTRA_TITLE).toString();
                for (int i = 0; i < NotificationCompat.getActionCount(notification); i++) {
                    Action quickReplyAction = NotificationUtils.getQuickReplyAction(statusBarNotification.getNotification(), getPackageName());
                    MainActivity.actionslist.add(quickReplyAction);
                    if (quickReplyAction.getTitle().toString().contains("Trả lời") || quickReplyAction.getTitle().toString().indexOf("Reply") > -1) {
                        MainActivity.arr_TenKH.add("ZL - " + this.ID);
                        Contact contact = new Contact();
                        contact.name = "ZL - " + this.ID;
                        contact.app = "ZL";
                        contact.pendingIntent = quickReplyAction.getQuickReplyIntent();
                        contact.remoteInput2 = quickReplyAction.getRemoteInputs().get(0);
                        contact.remoteExtras = statusBarNotification.getNotification().extras;
                        MainActivity.contactslist.add(contact);
                        if (MainActivity.Notifi == null) {
                            MainActivity.Notifi = this;
                        }
                    }
                }
            }
        }
        super.onNotificationPosted(statusBarNotification);
    }

    @Override // tamhoang.ldpro4.NotificationReader, android.service.notification.NotificationListenerService
    public void onNotificationRemoved(StatusBarNotification var1) {
        super.onNotificationRemoved(var1);
    }
}
