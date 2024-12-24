package tamhoang.ldpro4.notifLib.models;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.core.app.RemoteInput;

/* loaded from: classes.dex */
public class RemoteInputParcel implements Parcelable {
    public static final Creator CREATOR = new Creator() { // from class: tamhoang.ldpro4.notifLib.models.RemoteInputParcel.1
        @Override // android.os.Parcelable.Creator
        public RemoteInputParcel createFromParcel(Parcel in) {
            return new RemoteInputParcel(in);
        }

        @Override // android.os.Parcelable.Creator
        public RemoteInputParcel[] newArray(int size) {
            return new RemoteInputParcel[size];
        }
    };
    private boolean allowFreeFormInput;
    private String[] choices;
    private Bundle extras;
    private String label;
    private String resultKey;

    /* renamed from: tamhoang.ldpro4.notifLib.models.RemoteInputParcel$1 */
    /* loaded from: classes.dex */
    class AnonymousClass1 implements Creator {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public RemoteInputParcel createFromParcel(Parcel in) {
            return new RemoteInputParcel(in);
        }

        @Override // android.os.Parcelable.Creator
        public RemoteInputParcel[] newArray(int size) {
            return new RemoteInputParcel[size];
        }
    }

    public RemoteInputParcel(Parcel in) {
        this.choices = new String[0];
        this.label = in.readString();
        this.resultKey = in.readString();
        this.choices = in.createStringArray();
        this.allowFreeFormInput = in.readByte() != 0;
        this.extras = (Bundle) in.readParcelable(Bundle.class.getClassLoader());
    }

    public RemoteInputParcel(RemoteInput input) {
        this.choices = new String[0];
        this.label = input.getLabel().toString();
        this.resultKey = input.getResultKey();
        charSequenceToStringArray(input.getChoices());
        this.allowFreeFormInput = input.getAllowFreeFormInput();
        this.extras = input.getExtras();
    }

    public void charSequenceToStringArray(CharSequence[] charSequence) {
        if (charSequence != null) {
            int length = charSequence.length;
            this.choices = new String[charSequence.length];
            for (int i = 0; i < length; i++) {
                this.choices[i] = charSequence[i].toString();
            }
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public CharSequence[] getChoices() {
        return this.choices;
    }

    public Bundle getExtras() {
        return this.extras;
    }

    public String getLabel() {
        return this.label;
    }

    public String getResultKey() {
        return this.resultKey;
    }

    public boolean isAllowFreeFormInput() {
        return this.allowFreeFormInput;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.label);
        parcel.writeString(this.resultKey);
        parcel.writeStringArray(this.choices);
        parcel.writeByte(this.allowFreeFormInput ? (byte) 1 : (byte) 0);
        parcel.writeParcelable(this.extras, i);
    }
}
