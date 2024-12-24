package tamhoang.ldpro4.services;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import okhttp3.internal.cache.DiskLruCache;
import tamhoang.ldpro4.constants.Constants;

public class UpdateSMSService extends IntentService {
    public UpdateSMSService() {
        super("UpdateSMSReceiver");
    }

    public void markSmsRead(long j) {
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(Constants.READ, DiskLruCache.VERSION_1);
            getContentResolver().update(Uri.parse("content://sms/" + j), contentValues, null, null);
        } catch (Exception unused) {
        }
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        markSmsRead(intent.getLongExtra("id", -123L));
    }
}
