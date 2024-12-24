package tamhoang.ldpro4.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import tamhoang.ldpro4.data.Database;

/* loaded from: classes.dex */
public class UpdateKQ extends BroadcastReceiver {
    Database db;

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        this.db = new Database(context);
        String format = new SimpleDateFormat("HH:mm").format(new Date());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        if ((Integer.parseInt(format.substring(0, 2)) <= 17 || Integer.parseInt(format.substring(3, 5)) <= 18) && Integer.parseInt(format.substring(0, 2)) <= 18) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, -1);
            simpleDateFormat.format(calendar.getTime());
        } else {
            Calendar calendar2 = Calendar.getInstance();
            calendar2.add(Calendar.DATE, 0);
            simpleDateFormat.format(calendar2.getTime());
        }
    }
}
