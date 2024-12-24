package tamhoang.ldpro4.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import tamhoang.ldpro4.R;


public class DeliverReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (getResultCode() != 0) {
            return;
        }
        Toast.makeText(context, R.string.sms_delivered, Toast.LENGTH_SHORT).show();
    }
}
