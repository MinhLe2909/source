package tamhoang.ldpro4.services;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import tamhoang.ldpro4.constants.SmsContract;

/* loaded from: classes.dex */
public class SaveSmsService extends IntentService {
    public SaveSmsService() {
        super("SaveService");
    }

    @Override // android.app.IntentService
    protected void onHandleIntent(Intent intent) {
        String stringExtra = intent.getStringExtra("sender_no");
        String stringExtra2 = intent.getStringExtra("message");
        long longExtra = intent.getLongExtra("date", 0L);
        ContentValues contentValues = new ContentValues();
        contentValues.put("address", stringExtra);
        contentValues.put("body", stringExtra2);
        contentValues.put("date_sent", Long.valueOf(longExtra));
        getContentResolver().insert(SmsContract.ALL_SMS_URI, contentValues);
        sendBroadcast(new Intent("android.intent.action.MAIN").putExtra("new_sms", true));
    }
}
