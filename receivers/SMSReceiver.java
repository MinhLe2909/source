package tamhoang.ldpro4.receivers;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;

import androidx.core.app.NotificationCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import tamhoang.ldpro4.formula.Congthuc;
import tamhoang.ldpro4.ui.home.MainActivity;
import tamhoang.ldpro4.R;
import tamhoang.ldpro4.data.Database;
import tamhoang.ldpro4.services.SaveSmsService;

/* loaded from: classes.dex */
public class SMSReceiver extends BroadcastReceiver {
    String Ten_KH;
    JSONObject caidat_gia;
    JSONObject caidat_tg;
    Database db;
    JSONObject json;
    Context mContext;
    String mGionhan;
    String mNgayNhan;
    String mSDT;
    int soTN;
    String body = "";
    SmsMessage[] messages = null;

    private SmsMessage getIncomingMessage(Object obj, Bundle bundle) {
        if (Build.VERSION.SDK_INT < 23) {
            return SmsMessage.createFromPdu((byte[]) obj);
        }
        return SmsMessage.createFromPdu((byte[]) obj, bundle.getString("format"));
    }

    private void issueNotification(Context context, String str, String str2) {
        ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).notify(101,
                new NotificationCompat.Builder(context).setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.notifi1)).setSmallIcon(R.drawable.notifi1).setContentTitle(str).setStyle(new NotificationCompat.BigTextStyle().bigText(str2)).setAutoCancel(true).setContentText(str2).build());
    }

    private void saveSmsInInbox(Context context, SmsMessage smsMessage) {
        Intent intent = new Intent(context, (Class<?>) SaveSmsService.class);
        intent.putExtra("sender_no", smsMessage.getDisplayOriginatingAddress());
        intent.putExtra("message", smsMessage.getDisplayMessageBody());
        intent.putExtra("date", smsMessage.getTimestampMillis());
        context.startService(intent);
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        String displayMessageBody;
        String trim;
        boolean z;
        Cursor GetData;
        StringBuilder sb;
        this.db = new Database(context);
        this.mContext = context;
        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
            Bundle extras = intent.getExtras();
            Object[] objArr = (Object[]) extras.get("pdus");
            this.messages = new SmsMessage[objArr.length];
            for (int i = 0; i < objArr.length; i++) {
                this.messages[i] = SmsMessage.createFromPdu((byte[]) objArr[i]);
                SmsMessage incomingMessage = getIncomingMessage(objArr[i], extras);
                issueNotification(context, incomingMessage.getDisplayOriginatingAddress(), incomingMessage.getDisplayMessageBody());
                saveSmsInInbox(context, incomingMessage);
            }
            SmsMessage smsMessage = this.messages[0];
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("HH:mm:ss");
            simpleDateFormat.setTimeZone(TimeZone.getDefault());
            simpleDateFormat2.setTimeZone(TimeZone.getDefault());
            this.mNgayNhan = simpleDateFormat.format(calendar.getTime());
            this.mGionhan = simpleDateFormat2.format(calendar.getTime());
            this.mSDT = "";
            try {
                if (this.messages.length != 1 && !smsMessage.isReplace()) {
                    StringBuilder sb2 = new StringBuilder();
                    int i2 = 0;
                    while (true) {
                        SmsMessage[] smsMessageArr = this.messages;
                        if (i2 >= smsMessageArr.length) {
                            break;
                        }
                        sb2.append(smsMessageArr[i2].getMessageBody());
                        i2++;
                    }
                    displayMessageBody = sb2.toString();
                    this.body = displayMessageBody.replace("'", "");
                    trim = smsMessage.getDisplayOriginatingAddress().replace(" ", "").trim();
                    this.mSDT = trim;
                    if (trim.startsWith("0")) {
                        this.mSDT = "+84" + this.mSDT.substring(1);
                    }
                    if (MainActivity.DSkhachhang.size() == 0) {
                        this.db.LayDanhsachKH();
                    }
                    if ((MainActivity.DSkhachhang.indexOf(this.mSDT) > -1 || this.body.indexOf(MainActivity.OK) == 0 || this.body.indexOf(MainActivity.BO) == 0 || this.body.indexOf(MainActivity.THIEU) == 0) && this.body.indexOf(MainActivity.TRA_LAI) <= -1) {
                    }
                    MainActivity.sms = true;
                    try {
                        if (MainActivity.jSon_Setting.getInt("tin_trung") > 0) {
                            Cursor GetData2 = this.db.GetData("Select id From tbl_tinnhanS WHERE so_dienthoai = '" + this.mSDT + "' AND ngay_nhan = '" + this.mNgayNhan + "' AND nd_goc = '" + this.body + "'");
                            GetData2.moveToFirst();
                            z = GetData2.getCount() <= 0;
                            if (!GetData2.isClosed()) {
                                GetData2.close();
                            }
                        } else {
                            z = true;
                        }
                    } catch (JSONException e2) {
                        z = true;
                        e2.printStackTrace();
                    }
                    GetData = this.db.GetData("Select * FROM tbl_kh_new WHERE sdt ='" + this.mSDT + "'");
                    GetData.moveToFirst();
                    if (z) {
                        try {
                            JSONObject jSONObject = new JSONObject(GetData.getString(5));
                            this.json = jSONObject;
                            this.caidat_gia = jSONObject.getJSONObject("caidat_gia");
                            this.caidat_tg = this.json.getJSONObject("caidat_tg");
                        } catch (JSONException e3) {
                            e3.printStackTrace();
                        }
                        try {
                            try {
                                if (Congthuc.CheckTime(this.caidat_tg.getString("tg_debc"))) {
                                    Cursor GetData3 = this.db.GetData("Select max(so_tin_nhan) from tbl_tinnhanS WHERE ngay_nhan = '" + this.mNgayNhan + "' AND so_dienthoai = '" + this.mSDT + "' AND type_kh = 1");
                                    GetData3.moveToFirst();
                                    this.Ten_KH = GetData.getString(0);
                                    this.soTN = GetData3.getInt(0) + 1;
                                    this.db.QueryData("Insert Into tbl_tinnhanS values (null, '" + this.mNgayNhan + "', '" + this.mGionhan + "',1, '" + this.Ten_KH + "', '" + GetData.getString(1) + "','sms', " + this.soTN + ", '" + this.body + "',null,'" + this.body + "', 'Hết giờ nhận số!',0,1,1, null)");
                                    if (GetData3 != null && !GetData3.isClosed()) {
                                        GetData3.close();
                                    }
                                    if (!Congthuc.CheckTime("18:30") && MainActivity.jSon_Setting.getInt("tin_qua_gio") == 1) {
                                        this.db.SendSMS(GetData.getString(1), MainActivity.HET_GIO_NHAN);
                                    }
                                } else {
                                    Cursor GetData4 = this.db.GetData("Select max(so_tin_nhan) from tbl_tinnhanS WHERE ngay_nhan = '" + this.mNgayNhan + "' AND so_dienthoai = '" + this.mSDT + "' AND type_kh = 1");
                                    GetData4.moveToFirst();
                                    this.Ten_KH = GetData.getString(0);
                                    this.soTN = GetData4.getInt(0) + 1;
                                    if (this.body.indexOf(MainActivity.TRA_LAI) == -1) {
                                        sb = new StringBuilder();
                                        sb.append("Insert Into tbl_tinnhanS values (null, '");
                                        sb.append(this.mNgayNhan);
                                        sb.append("', '");
                                        sb.append(this.mGionhan);
                                        sb.append("',1, '");
                                        sb.append(this.Ten_KH);
                                        sb.append("', '");
                                        sb.append(GetData.getString(1));
                                        sb.append("','sms', ");
                                        sb.append(this.soTN);
                                        sb.append(", '");
                                        sb.append(this.body);
                                        sb.append("',null,'");
                                        sb.append(this.body);
                                        sb.append("', 'ko',0,1,1, null)");
                                    } else {
                                        sb = new StringBuilder();
                                        sb.append("Insert Into tbl_tinnhanS values (null, '");
                                        sb.append(this.mNgayNhan);
                                        sb.append("', '");
                                        sb.append(this.mGionhan);
                                        sb.append("',1, '");
                                        sb.append(this.Ten_KH);
                                        sb.append("', '");
                                        sb.append(GetData.getString(1));
                                        sb.append("','sms', ");
                                        sb.append(this.soTN);
                                        sb.append(", '");
                                        sb.append(this.body);
                                        sb.append("',null,'");
                                        sb.append(this.body);
                                        sb.append("', 'ko',0,0,0, null)");
                                    }
                                    this.db.QueryData(sb.toString());
                                    if (Congthuc.CheckDate(MainActivity.myDate)) {
                                        Cursor GetData5 = this.db.GetData("Select * from tbl_tinnhanS WHERE ngay_nhan = '" + this.mNgayNhan + "' AND so_dienthoai = '" + this.mSDT + "' AND so_tin_nhan = " + this.soTN + " AND type_kh = 1");
                                        GetData5.moveToFirst();
                                        try {
                                            this.db.Update_TinNhanGoc(GetData5.getInt(0), 1);
                                        } catch (Exception unused) {
                                            this.db.QueryData("Update tbl_tinnhanS set phat_hien_loi = 'ko' WHERE id = " + GetData5.getInt(0));
                                            this.db.QueryData("Delete From tbl_soctS WHERE ngay_nhan = '" + this.mNgayNhan + "' AND so_dienthoai = '" + this.mSDT + "' AND so_tin_nhan = " + this.soTN + " AND type_kh = 1");
                                        }
                                        if (!Congthuc.CheckTime("18:30") && this.body.indexOf(MainActivity.TRA_LAI) == -1) {
                                            this.db.Gui_Tin_Nhan(GetData5.getInt(0));
                                        }
                                        GetData5.close();
                                    }
                                    if (GetData4 != null && !GetData4.isClosed()) {
                                        GetData4.close();
                                    }
                                }
                            } catch (SQLException unused2) {
                            }
                        } catch (JSONException e4) {
                            e4.printStackTrace();
                        }
                    }
                    if (GetData != null || GetData.isClosed()) {
                        return;
                    }
                    GetData.close();
                    return;
                }
                displayMessageBody = smsMessage.getDisplayMessageBody();
                this.body = displayMessageBody.replace("'", "");
                trim = smsMessage.getDisplayOriginatingAddress().replace(" ", "").trim();
                this.mSDT = trim;
                if (trim.startsWith("0")) {
                }
                if (MainActivity.DSkhachhang.size() == 0) {
                }
                if (MainActivity.DSkhachhang.indexOf(this.mSDT) > -1) {
                }
            } catch (Exception unused3) {
            }
        }
    }
}
