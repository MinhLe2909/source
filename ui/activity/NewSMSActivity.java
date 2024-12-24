package tamhoang.ldpro4.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import tamhoang.ldpro4.R;
import tamhoang.ldpro4.receivers.DeliverReceiver;
import tamhoang.ldpro4.receivers.SentReceiver;

public class NewSMSActivity extends AppCompatActivity implements View.OnClickListener {
    private String message;
    private String phoneNo;
    private EditText txtMessage;
    private EditText txtphoneNo;
    BroadcastReceiver deliveryBroadcastReciever = new DeliverReceiver();
    BroadcastReceiver sendBroadcastReceiver = new SentReceiver();

    private void contactPicked(Intent data) {
        try {
            Cursor query = getContentResolver().query(data.getData(), null, null, null, null);
            query.moveToFirst();
            int columnIndex = query.getColumnIndex("data1");
            int columnIndex2 = query.getColumnIndex("display_name");
            String string = query.getString(columnIndex);
            query.getString(columnIndex2);
            this.txtphoneNo.setText(string);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init() {
        this.txtphoneNo =  findViewById(R.id.editText);
        this.txtMessage =  findViewById(R.id.editText2);
        findViewById(R.id.contact).setOnClickListener(v -> NewSMSActivity.this.startActivityForResult(new Intent("android.intent.action.PICK", ContactsContract.CommonDataKinds.Phone.CONTENT_URI), 85));
        findViewById(R.id.btnSendSMS).setOnClickListener(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != -1) {
            Log.e("MainActivity", "Failed to pick contact");
        } else if (requestCode == 85) {
            contactPicked(data);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnSendSMS) {
            this.phoneNo = this.txtphoneNo.getText().toString();
            this.message = this.txtMessage.getText().toString();
            String str = this.phoneNo;
            if (str == null || str.trim().length() <= 0) {
                this.txtphoneNo.setError(getString(R.string.please_write_number));
                return;
            }
            String str2 = this.message;
            if (str2 == null || str2.trim().length() <= 0) {
                this.txtMessage.setError(getString(R.string.please_write_message));
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_sms_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 16908332) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            unregisterReceiver(this.sendBroadcastReceiver);
            unregisterReceiver(this.deliveryBroadcastReciever);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pickContact(View v) {
        startActivityForResult(new Intent("android.intent.action.PICK", ContactsContract.CommonDataKinds.Phone.CONTENT_URI), 85);
    }
}
