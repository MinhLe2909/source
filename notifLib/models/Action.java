package tamhoang.ldpro4.notifLib.models;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.RemoteInput;

import tamhoang.ldpro4.notifLib.models.RemoteInputParcel;

import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes.dex */
public class Action implements Parcelable {
    public static final Creator CREATOR = new Creator() { // from class: tamhoang.ldpro4.notifLib.models.Action.1
        @Override // android.os.Parcelable.Creator
        public Action createFromParcel(Parcel in) {
            return new Action(in);
        }

        @Override // android.os.Parcelable.Creator
        public Action[] newArray(int size) {
            return new Action[size];
        }
    };
    private final boolean isQuickReply;
    private final PendingIntent p;
    private final String packageName;
    private final ArrayList<RemoteInputParcel> remoteInputs;
    private final String text;
    private final String title;

    public Action(Parcel in) {
        ArrayList<RemoteInputParcel> arrayList = new ArrayList<>();
        this.remoteInputs = arrayList;
        this.title = in.readString();
        this.text = in.readString();
        this.packageName = in.readString();
        this.p = (PendingIntent) in.readParcelable(PendingIntent.class.getClassLoader());
        this.isQuickReply = in.readByte() != 0;
        in.readTypedList(arrayList, RemoteInputParcel.CREATOR);
    }

    public Action(NotificationCompat.Action action, String packageName, boolean isQuickReply) {
        this.remoteInputs = new ArrayList<>();
        this.title = action.title.toString();
        this.text = action.title.toString();
        this.packageName = packageName;
        this.p = action.actionIntent;
        if (action.getRemoteInputs() != null) {
            int length = action.getRemoteInputs().length;
            for (int i = 0; i < length; i++) {
                this.remoteInputs.add(new RemoteInputParcel(action.getRemoteInputs()[i]));
            }
        }
        this.isQuickReply = isQuickReply;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public PendingIntent getQuickReplyIntent() {
        if (this.isQuickReply) {
            return this.p;
        }
        return null;
    }

    public ArrayList<RemoteInputParcel> getRemoteInputs() {
        return this.remoteInputs;
    }

    public String getText() {
        return this.text;
    }

    public String getTitle() {
        return this.title;
    }

    public boolean isQuickReply() {
        return this.isQuickReply;
    }

    public void sendReply(Context context, String msg) throws PendingIntent.CanceledException {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        ArrayList arrayList = new ArrayList();
        Iterator<RemoteInputParcel> it = this.remoteInputs.iterator();
        while (it.hasNext()) {
            RemoteInputParcel next = it.next();
            Log.i("", "RemoteInput: " + next.getLabel());
            bundle.putCharSequence(next.getResultKey(), msg);
            RemoteInput.Builder builder = new RemoteInput.Builder(next.getResultKey());
            builder.setLabel(next.getLabel());
            builder.setChoices(next.getChoices());
            builder.setAllowFreeFormInput(next.isAllowFreeFormInput());
            builder.addExtras(next.getExtras());
            arrayList.add(builder.build());
        }
        RemoteInput.addResultsToIntent((RemoteInput[]) arrayList.toArray(new RemoteInput[arrayList.size()]), intent, bundle);
        this.p.send(context, 0, intent);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.title);
        parcel.writeString(this.text);
        parcel.writeString(this.packageName);
        parcel.writeParcelable(this.p, i);
        parcel.writeByte(this.isQuickReply ? (byte) 1 : (byte) 0);
        parcel.writeTypedList(this.remoteInputs);
    }
}
