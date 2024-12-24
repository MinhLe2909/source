package tamhoang.ldpro4.data.models;

import android.app.PendingIntent;
import android.app.RemoteInput;
import android.os.Bundle;
import tamhoang.ldpro4.notifLib.models.RemoteInputParcel;

/* loaded from: classes.dex */
public class Contact {
    public String app;
    public String name;
    public PendingIntent pendingIntent;
    public Bundle remoteExtras;
    public RemoteInput remoteInput;
    public RemoteInputParcel remoteInput2;

    public String getApp() {
        return this.app;
    }

    public String getName() {
        return this.name;
    }

    public PendingIntent getPendingIntent() {
        return this.pendingIntent;
    }

    public Bundle getRemoteExtras() {
        return this.remoteExtras;
    }

    public RemoteInput getRemoteInput() {
        return this.remoteInput;
    }

    public void setApp(String app2) {
        this.app = app2;
    }

    public void setName(String name2) {
        this.name = name2;
    }

    public void setPendingIntent(PendingIntent pendingIntent2) {
        this.pendingIntent = pendingIntent2;
    }

    public void setRemoteExtras(Bundle remoteExtras2) {
        this.remoteExtras = remoteExtras2;
    }

    public void setRemoteInput(RemoteInput remoteInput2) {
        this.remoteExtras = this.remoteExtras;
    }
}
