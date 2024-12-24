package tamhoang.ldpro4.ui.notification;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

import androidx.core.app.NotificationCompat;

import tamhoang.ldpro4.formula.Congthuc;
import tamhoang.ldpro4.data.models.Contact;
import tamhoang.ldpro4.data.Database;
import tamhoang.ldpro4.ui.home.MainActivity;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.TimeZone;


public class NotificationReader extends NotificationListenerService {
    public static final String VIBER = "com.viber.voip";
    public static final String WHATSAPP = "com.whatsapp";
    public static final String ZALO = "com.zing.zalo";
    static boolean replied;
    String Ten_KH;
    private ArrayList<NotificationCompat.Action> actions;
    JSONObject caidat_tg;
    Context context;
    Database db;
    JSONObject json;
    int soTN;
    String body = "";
    String mWhat = "";
    String ID = "";

    public void NotificationWearReader(String str, String str2) {
        int indexOf = MainActivity.arr_TenKH.indexOf(str);
        if (indexOf > -1) {
            try {
                Intent intent = new Intent();
                Bundle bundle = MainActivity.contactslist.get(indexOf).remoteExtras;
                RemoteInput[] remoteInputArr = {MainActivity.contactslist.get(indexOf).remoteInput};
                if (Build.VERSION.SDK_INT >= 20) {
                    bundle.putCharSequence(remoteInputArr[0].getResultKey(), str2);
                    RemoteInput.addResultsToIntent(remoteInputArr, intent, bundle);
                }
                MainActivity.contactslist.get(indexOf).pendingIntent.send(MainActivity.Notifi, 0, intent);
                if (MainActivity.Json_Tinnhan.has(str)) {
                    MainActivity.Json_Tinnhan.remove(str);
                }
            } catch (PendingIntent.CanceledException e) {
                e.printStackTrace();
            } catch (Exception e2) {
                e2.getMessage();
            }
        }
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        this.actions = new ArrayList<>();
        this.db = new Database(getBaseContext());
    }

    @Override
    public void onNotificationPosted(StatusBarNotification statusBarNotification) {
        String str;
        String str2;
        String str3;
        String str4;
        String str5;
        StringBuilder sb;
        String str6;
        JSONObject jSONObject;
        JSONObject jSONObject2;
        String str7;
        String str8;
        Iterator it;
        String charSequence;
        int i = 0;
        int i2 = 0;
        JSONObject jSONObject3 = new JSONObject();
        JSONObject jSONObject4 = new JSONObject();
        String str9 = "";
        String charSequence2;
        JSONObject jSONObject5 = new JSONObject();
        String str10 = "";
        String jSONObject6 = "";
        JSONObject jSONObject7;
        JSONObject jSONObject8 = new JSONObject();
        String str11 = "";
        if (statusBarNotification.getPackageName().equals(ZALO) || statusBarNotification.getPackageName().equals(WHATSAPP) || statusBarNotification.getPackageName().equals(VIBER)) {
            if (this.context == null) {
                this.context = this;
            }
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("HH:mm:ss");
            simpleDateFormat.setTimeZone(TimeZone.getDefault());
            simpleDateFormat2.setTimeZone(TimeZone.getDefault());
            String format = simpleDateFormat.format(calendar.getTime());
            String format2 = simpleDateFormat2.format(calendar.getTime());
            try {
                this.ID = "";
                Bundle bundle = statusBarNotification.getNotification().extras;
                String charSequence3 = bundle.getCharSequence(NotificationCompat.EXTRA_TEXT).toString();
                Notification notification = statusBarNotification.getNotification();
                String charSequence4 = bundle.getCharSequence(NotificationCompat.EXTRA_TITLE).toString();
                String str12 = "ZL";
                if (statusBarNotification.getPackageName().equals(ZALO) && charSequence3.indexOf("tin nhắn chưa đọc.") == -1 && charSequence3.indexOf("đã gửi tập tin cho bạn") == -1 && charSequence3.indexOf("cảm xúc với tin nhắn bạn gửi") == -1 && charSequence3.indexOf("hình động cho bạn") == -1 && charSequence3.indexOf("thành viên của nhóm") == -1 && charSequence3.indexOf("thêm vào nhóm") == -1 && charSequence3.indexOf("gửi ảnh cho bạn") == -1 && charSequence3.indexOf("cuộc trò chuyện") == -1) {
                    String charSequence5 = bundle.getCharSequence(NotificationCompat.EXTRA_TITLE).toString();
                    this.ID = charSequence5;
                    if (charSequence5.indexOf(" (") > -1) {
                        String str13 = this.ID;
                        str = format2;
                        this.ID = str13.substring(0, str13.indexOf("(")).trim();
                    } else {
                        str = format2;
                    }
                    if (charSequence4.indexOf("Nhóm") == 0) {
                        String str14 = this.ID;
                        this.ID = str14.substring(str14.indexOf(":") + 2).trim();
                        charSequence3 = charSequence3.substring(charSequence3.indexOf(":") + 1).trim();
                    }
                    charSequence3 = charSequence3.replaceAll("'", "");
                    if (MainActivity.Json_Tinnhan.has("ZL - " + this.ID.trim())) {
                        JSONObject jSONObject9 = MainActivity.Json_Tinnhan;
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("ZL");
                        sb2.append(" - ");
                        str2 = "'";
                        sb2.append(this.ID.trim());
                        jSONObject7 = new JSONObject(jSONObject9.getString(sb2.toString()));
                        if (!jSONObject7.has(charSequence3.trim())) {
                            jSONObject7.put(charSequence3.trim(), "OK");
                            jSONObject8 = MainActivity.Json_Tinnhan;
                            str11 = "ZL - " + this.ID.trim();
                        }
                    } else {
                        str2 = "'";
                        jSONObject7 = new JSONObject();
                        jSONObject7.put(charSequence3.trim(), "OK");
                        jSONObject8 = MainActivity.Json_Tinnhan;
                        str11 = "ZL - " + this.ID.trim();
                    }
                    jSONObject8.put(str11, jSONObject7.toString());
                    if (statusBarNotification.getPackageName().equals(VIBER) && charSequence3.indexOf("Bạn có các tin nhắn") == -1 && charSequence3.indexOf("thêm bạn vào") == -1 && charSequence3.indexOf("uộc gọi") == -1 && charSequence3.indexOf("tin nhắn chưa đọc") == -1) {
                        charSequence2 = bundle.getCharSequence(NotificationCompat.EXTRA_TITLE).toString();
                        this.ID = charSequence2;
                        if (charSequence2.indexOf("trong") > -1) {
                            String str15 = this.ID;
                            this.ID = str15.substring(str15.indexOf("trong") + 6);
                        }
                        if (this.ID.indexOf("tin nhắn chưa đọc") == -1) {
                            if (MainActivity.Json_Tinnhan.has("VB - " + this.ID.trim())) {
                                JSONObject jSONObject10 = new JSONObject(MainActivity.Json_Tinnhan.getString("VB - " + this.ID.trim()));
                                if (jSONObject10.has(charSequence3.trim())) {
                                    str12 = null;
                                } else {
                                    jSONObject10.put(charSequence3.trim(), "OK");
                                    jSONObject5 = MainActivity.Json_Tinnhan;
                                    str10 = "VB - " + this.ID.trim();
                                    jSONObject6 = jSONObject10.toString();
                                }
                            } else {
                                JSONObject jSONObject11 = new JSONObject();
                                jSONObject11.put(charSequence3.trim(), "OK");
                                jSONObject5 = MainActivity.Json_Tinnhan;
                                str10 = "VB - " + this.ID.trim();
                                jSONObject6 = jSONObject11.toString();
                            }
                            jSONObject5.put(str10, jSONObject6);
                            str12 = "VB";
                        }
                    }
                    if (statusBarNotification.getPackageName().equals(WHATSAPP) && charSequence3.indexOf("tin nhắn") == -1) {
                        charSequence = bundle.getCharSequence(NotificationCompat.EXTRA_TITLE).toString();
                        this.ID = charSequence;
                        if (charSequence.indexOf(":") > -1) {
                            String str16 = this.ID;
                            this.ID = str16.substring(0, str16.indexOf(":")).trim();
                            this.mWhat = charSequence4.substring(charSequence4.indexOf(":") + 1).trim();
                        }
                        if (this.ID.indexOf("@") > -1) {
                            String str17 = this.ID;
                            this.mWhat = str17.substring(0, str17.indexOf("@"));
                            String str18 = this.ID;
                            this.ID = str18.substring(str18.indexOf("@") + 1).trim();
                        }
                        if (this.ID.indexOf(" (") > -1) {
                            String str19 = this.ID;
                            this.ID = str19.substring(0, str19.indexOf("(")).trim();
                        }
                        this.ID = this.ID.trim();
                        for (i = 0; i < 68; i++) {
                            this.ID = this.ID.replace("ăâàằầáắấảẳẩãẵẫạặậễẽểẻéêèềếẹệôòồơờóốớỏổởõỗỡọộợưúùứừủửũữụựìíỉĩịỳýỷỹỵđ×".charAt(i), "aaaaaaaaaaaaaaaaaeeeeeeeeeeeooooooooooooooooouuuuuuuuuuuiiiiiyyyyydx".charAt(i));
                        }
                        for (i2 = 0; i2 < this.ID.length(); i2++) {
                            if (this.ID.charAt(i2) > 127 || this.ID.charAt(i2) < 31) {
                                this.ID = this.ID.substring(0, i2) + this.ID.substring(i2 + 1);
                            }
                        }
                        str12 = "WA";
                        if (charSequence3.indexOf(this.mWhat.trim()) > -1 || this.mWhat.length() <= 0) {
                            this.mWhat = charSequence3;
                            if (MainActivity.Json_Tinnhan.has("WA - " + this.ID.trim())) {
                                jSONObject3 = new JSONObject();
                                jSONObject3.put(charSequence3.trim(), "OK");
                                jSONObject4 = MainActivity.Json_Tinnhan;
                                str9 = "WA - " + this.ID.trim();
                            } else {
                                jSONObject3 = new JSONObject(MainActivity.Json_Tinnhan.getString("WA - " + this.ID.trim()));
                                if (!jSONObject3.has(charSequence3.trim())) {
                                    jSONObject3.put(charSequence3.trim(), "OK");
                                    jSONObject4 = MainActivity.Json_Tinnhan;
                                    str9 = "WA - " + this.ID.trim();
                                }
                            }
                            jSONObject4.put(str9, jSONObject3.toString());
                        }
                        str12 = null;
                    }
                    str3 = str12 + " - " + this.ID.trim();
                    if (MainActivity.arr_TenKH.indexOf(str3) == -1 && str3.indexOf("null") == -1) {
                        Notification.WearableExtender wearableExtender = null;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
                            wearableExtender = new Notification.WearableExtender(notification);
                            ArrayList arrayList = new ArrayList();
                            arrayList.addAll(wearableExtender.getActions());
                            it = arrayList.iterator();
                            while (it.hasNext()) {
                                Notification.Action action = (Notification.Action) it.next();
                                if (action.title.toString().indexOf("Trả lời") > -1 || action.title.toString().indexOf("Reply") > -1) {
                                    MainActivity.arr_TenKH.add(str3);
                                    Contact contact = new Contact();
                                    contact.name = str3;
                                    contact.app = str12;
                                    contact.pendingIntent = action.actionIntent;
                                    contact.remoteInput = action.getRemoteInputs()[0];
                                    contact.remoteExtras = statusBarNotification.getNotification().extras;
                                    MainActivity.contactslist.add(contact);
                                    if (MainActivity.Notifi == null) {
                                        MainActivity.Notifi = this;
                                    }
                                }
                            }
                        }
                    }
                    if (charSequence3.toLowerCase().indexOf("LDVN") > -1 && charSequence3.trim().length() == 5) {
                        MainActivity.Notifi = this;
                        NotificationWearReader(str3, "Tin mồi!");
                    }
                    if (str12 == null && str3.indexOf("null") == -1 && str12 != "null") {
                        Database database = this.db;
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append("Select * From Chat_database WHERE ngay_nhan = '");
                        sb3.append(format);
                        sb3.append("' And Ten_kh = '");
                        sb3.append(str3);
                        sb3.append("' AND nd_goc = '");
                        sb3.append(charSequence3);
                        str5 = str2;
                        sb3.append(str5);
                        Cursor GetData = database.GetData(sb3.toString());
                        if (GetData.getCount() == 0) {
                            StringBuilder sb4 = new StringBuilder();
                            sb4.append("Insert into Chat_database Values( null,'");
                            sb4.append(format);
                            sb4.append("', '");
                            str4 = str;
                            sb4.append(str4);
                            sb4.append("', 1, '");
                            sb4.append(str3);
                            sb4.append("', '");
                            sb4.append(str3);
                            sb4.append("', '");
                            sb4.append(str12);
                            sb4.append("','");
                            sb4.append(charSequence3);
                            sb4.append("',1)");
                            this.db.QueryData(sb4.toString());
                            MainActivity.sms = true;
                            str8 = str12;
                        } else {
                            str4 = str;
                            str8 = null;
                        }
                        GetData.close();
                        str12 = str8;
                    } else {
                        str4 = str;
                        str5 = str2;
                    }
                    if (str12 != null || str3.indexOf("null") != -1 || str12 == "null" || charSequence3.length() <= 5) {
                        return;
                    }
                    this.body = charSequence3.replaceAll(str5, " ");
                    this.Ten_KH = str3;
                    if ((MainActivity.DSkhachhang.indexOf(this.Ten_KH) <= -1 || this.body.startsWith(MainActivity.OK) || this.body.startsWith(MainActivity.BO) || this.body.toLowerCase().startsWith("LDVN") || this.body.startsWith(MainActivity.THIEU) || this.body.startsWith("Success")) && this.body.indexOf(MainActivity.TRA_LAI) <= -1) {
                        return;
                    }
                    Cursor GetData2 = this.db.GetData("Select * FROM tbl_kh_new WHERE ten_kh ='" + this.Ten_KH + str5);
                    GetData2.moveToFirst();
                    JSONObject jSONObject12 = new JSONObject(GetData2.getString(5));
                    this.json = jSONObject12;
                    JSONObject jSONObject13 = jSONObject12.getJSONObject("caidat_tg");
                    this.caidat_tg = jSONObject13;
                    if (Congthuc.CheckTime(jSONObject13.getString("tg_debc"))) {
                        Cursor GetData3 = this.db.GetData("Select max(so_tin_nhan) from tbl_tinnhanS WHERE ngay_nhan = '" + format + "' AND ten_kh = '" + this.Ten_KH + str5);
                        GetData3.moveToFirst();
                        this.soTN = GetData3.getInt(0) + 1;
                        this.db.QueryData("Insert Into tbl_tinnhanS values (null, '" + format + "', '" + str4 + "',1, '" + this.Ten_KH + "', '" + GetData2.getString(1) + "','" + str12 + "', " + this.soTN + ", '" + this.body + "',null,'" + this.body + "', 'Hết giờ nhận số!',0,1,1, null)");
                        GetData3.close();
                        GetData2.close();
                        if (!Congthuc.CheckTime("18:30") && MainActivity.jSon_Setting.getInt("tin_qua_gio") == 1) {
                            NotificationWearReader(this.Ten_KH, MainActivity.HET_GIO_NHAN);
                        }
                    } else {
                        Cursor GetData4 = this.db.GetData("Select max(so_tin_nhan) from tbl_tinnhanS WHERE ngay_nhan = '" + format + "' AND ten_kh = '" + this.Ten_KH + "' AND type_kh = 1");
                        GetData4.moveToFirst();
                        this.soTN = GetData4.getInt(0) + 1;
                        if (this.body.indexOf(MainActivity.TRA_LAI) == -1) {
                            sb = new StringBuilder();
                            sb.append("Insert Into tbl_tinnhanS values (null, '");
                            sb.append(format);
                            sb.append("', '");
                            sb.append(str4);
                            sb.append("',1, '");
                            sb.append(this.Ten_KH);
                            sb.append("', '");
                            sb.append(GetData2.getString(1));
                            sb.append("','");
                            sb.append(str12);
                            sb.append("', ");
                            sb.append(this.soTN);
                            sb.append(", '");
                            sb.append(this.body);
                            sb.append("',null,'");
                            sb.append(this.body);
                            str6 = "', 'ko',0,1,1, null)";
                        } else {
                            sb = new StringBuilder();
                            sb.append("Insert Into tbl_tinnhanS values (null, '");
                            sb.append(format);
                            sb.append("', '");
                            sb.append(str4);
                            sb.append("',1, '");
                            sb.append(this.Ten_KH);
                            sb.append("', '");
                            sb.append(GetData2.getString(1));
                            sb.append("','");
                            sb.append(str12);
                            sb.append("', ");
                            sb.append(this.soTN);
                            sb.append(", '");
                            sb.append(this.body);
                            sb.append("',null,'");
                            sb.append(this.body);
                            str6 = "', 'ko',0,0,0, null)";
                        }
                        sb.append(str6);
                        this.db.QueryData(sb.toString());
                        if (Congthuc.CheckDate(MainActivity.myDate)) {
                            Cursor GetData5 = this.db.GetData("Select * from tbl_tinnhanS WHERE ngay_nhan = '" + format + "' AND ten_kh = '" + this.Ten_KH + "' AND so_tin_nhan = " + this.soTN + " AND type_kh = 1");
                            GetData5.moveToFirst();
                            try {
                                this.db.Update_TinNhanGoc(GetData5.getInt(0), 1);
                            } catch (Exception unused) {
                                this.db.QueryData("Update tbl_tinnhanS set phat_hien_loi = 'ko' WHERE id = " + GetData5.getInt(0));
                                this.db.QueryData("Delete From tbl_soctS WHERE ngay_nhan = '" + format + "' AND ten_kh = '" + this.Ten_KH + "' AND so_tin_nhan = " + this.soTN + " AND type_kh = 1");
                            }
                            if (!Congthuc.CheckTime("18:30") && this.body.indexOf(MainActivity.TRA_LAI) == -1) {
                                if (MainActivity.handler == null) {
                                    MainActivity.handler = new Handler();
//                                    MainActivity.handler.postDelayed(new Runnable(), 1000L);
                                }
                                if (MainActivity.json_Tinnhan.has(this.Ten_KH)) {
                                    jSONObject = new JSONObject(MainActivity.json_Tinnhan.getString(this.Ten_KH));
                                    jSONObject.put("Time", 0);
                                    jSONObject2 = MainActivity.json_Tinnhan;
                                    str7 = this.Ten_KH;
                                } else {
                                    jSONObject = new JSONObject();
                                    jSONObject.put("Time", 0);
                                    jSONObject2 = MainActivity.json_Tinnhan;
                                    str7 = this.Ten_KH;
                                }
                                jSONObject2.put(str7, jSONObject.toString());
                                this.db.Gui_Tin_Nhan(GetData5.getInt(0));
                            }
                            GetData5.close();
                        }
                        GetData4.close();
                        GetData2.close();
                    }
                    if (GetData2.isClosed()) {
                        return;
                    }
                    GetData2.close();
                    return;
                }
                str = format2;
                str2 = "'";
                str12 = null;
                if (statusBarNotification.getPackageName().equals(VIBER)) {
                    charSequence2 = bundle.getCharSequence(NotificationCompat.EXTRA_TITLE).toString();
                    this.ID = charSequence2;
                    if (charSequence2.indexOf("trong") > -1) {
                    }
                    if (this.ID.indexOf("tin nhắn chưa đọc") == -1) {
                    }
                }
                if (statusBarNotification.getPackageName().equals(WHATSAPP)) {
                    charSequence = bundle.getCharSequence(NotificationCompat.EXTRA_TITLE).toString();
                    this.ID = charSequence;
                    if (charSequence.indexOf(":") > -1) {
                    }
                    if (this.ID.indexOf("@") > -1) {
                    }
                    if (this.ID.indexOf(" (") > -1) {
                    }
                    this.ID = this.ID.trim();
                    while (i < 68) {
                    }
                    while (i2 < this.ID.length()) {
                    }
                    str12 = "WA";
                    if (charSequence3.indexOf(this.mWhat.trim()) > -1) {
                    }
                    this.mWhat = charSequence3;
                    if (MainActivity.Json_Tinnhan.has("WA - " + this.ID.trim())) {
                    }
                    jSONObject4.put(str9, jSONObject3.toString());
                }
                str3 = str12 + " - " + this.ID.trim();
                if (MainActivity.arr_TenKH.indexOf(str3) == -1) {
                    Notification.WearableExtender wearableExtender2 = null;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
                        wearableExtender2 = new Notification.WearableExtender(notification);
                        ArrayList arrayList2 = new ArrayList();
                        arrayList2.addAll(wearableExtender2.getActions());
                        it = arrayList2.iterator();
                        while (it.hasNext()) {
                        }
                    }
                }
                if (charSequence3.toLowerCase().indexOf("LDVN") > -1) {
                    MainActivity.Notifi = this;
                    NotificationWearReader(str3, "Tin mồi!");
                }
                if (str12 == null) {
                }
                str4 = str;
                str5 = str2;
                if (str12 != null) {
                }
            } catch (Exception unused2) {
            }
        }
    }

    @Override // android.service.notification.NotificationListenerService
    public void onNotificationRemoved(StatusBarNotification statusBarNotification) {
        super.onNotificationRemoved(statusBarNotification);
    }
}
