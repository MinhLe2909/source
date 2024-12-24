package tamhoang.ldpro4.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/* loaded from: classes.dex */
public class SentReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        String str;
        int resultCode = getResultCode();
        if (resultCode == 1) {
            str = "Sai số điện thoại";
        } else if (resultCode == 2) {
            str = "Không có mạng!";
        } else if (resultCode == 3) {
            str = "Null PDU";
        } else if (resultCode != 4) {
            return;
        } else {
            str = "Không có dịch vụ";
        }
        Toast.makeText(context, str, 0).show();
    }
}
