package tamhoang.ldpro4.ui.fragment;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import tamhoang.ldpro4.ui.activity.Activity_AddKH;
import tamhoang.ldpro4.ui.activity.Chatbox;
import tamhoang.ldpro4.ui.home.MainActivity;
import tamhoang.ldpro4.ui.notification.NotificationNewReader;
import tamhoang.ldpro4.R;
import tamhoang.ldpro4.utils.Util;
import tamhoang.ldpro4.data.Database;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class Frag_Chat_Manager extends Fragment {
    Button btn_Thongbao;
    Button btn_login;
    Database db;
    Handler handler;
    ListView listviewKH;
    public View v;
    boolean Running = true;
    private List<String> mApp = new ArrayList();
    private List<String> mNoiDung = new ArrayList();
    private List<String> mSDT = new ArrayList();
    private List<String> mTenKH = new ArrayList();
    private Runnable runnable = new Runnable() { // from class: tamhoang.ldpro4.Fragment.Frag_Chat_Manager.1
        @Override // java.lang.Runnable
        public void run() {
            if (MainActivity.sms) {
                Frag_Chat_Manager.this.XemListview();
                MainActivity.sms = false;
            }
            Frag_Chat_Manager.this.handler.postDelayed(this, 1000L);
        }
    };

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: tamhoang.ldpro4.Fragment.Frag_Chat_Manager$1 */
    /* loaded from: classes.dex */
    public class AnonymousClass1 implements Runnable {
        AnonymousClass1() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (MainActivity.sms) {
                Frag_Chat_Manager.this.XemListview();
                MainActivity.sms = false;
            }
            Frag_Chat_Manager.this.handler.postDelayed(this, 1000L);
        }
    }

    /* renamed from: tamhoang.ldpro4.Fragment.Frag_Chat_Manager$2 */
    /* loaded from: classes.dex */
    class AnonymousClass2 implements View.OnClickListener {
        AnonymousClass2() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View v) {
            if (Build.VERSION.SDK_INT >= 22) {
                Frag_Chat_Manager.this.startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
            } else {
                Frag_Chat_Manager.this.startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
            }
        }
    }

    /* renamed from: tamhoang.ldpro4.Fragment.Frag_Chat_Manager$3 */
    /* loaded from: classes.dex */
    class AnonymousClass3 implements AdapterView.OnItemClickListener {
        AnonymousClass3() {
        }

        @Override // android.widget.AdapterView.OnItemClickListener
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            if (Frag_Chat_Manager.this.mTenKH.size() == 0 || Frag_Chat_Manager.this.mSDT.size() == 0 || Frag_Chat_Manager.this.mApp.size() == 0) {
                Toast.makeText(Frag_Chat_Manager.this.getActivity(), "Không có khách hàng này!", Toast.LENGTH_LONG).show();
                return;
            }
            try {
                Intent intent = new Intent(Frag_Chat_Manager.this.getActivity(), (Class<?>) Chatbox.class);
                intent.putExtra("tenKH", (String) Frag_Chat_Manager.this.mTenKH.get(i));
                intent.putExtra("so_dienthoai", (String) Frag_Chat_Manager.this.mSDT.get(i));
                intent.putExtra("app", (String) Frag_Chat_Manager.this.mApp.get(i));
                Frag_Chat_Manager.this.startActivity(intent);
            } catch (Exception unused) {
                Toast.makeText(Frag_Chat_Manager.this.getActivity(), "Không có khách hàng này!", Toast.LENGTH_LONG).show();
            }
        }
    }

    /* renamed from: tamhoang.ldpro4.Fragment.Frag_Chat_Manager$4 */
    /* loaded from: classes.dex */
    public class AnonymousClass4 implements DialogInterface.OnClickListener {
        AnonymousClass4() {
        }

        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.dismiss();
        }
    }

    /* renamed from: tamhoang.ldpro4.Fragment.Frag_Chat_Manager$5 */
    /* loaded from: classes.dex */
    public class AnonymousClass5 implements DialogInterface.OnClickListener {
        AnonymousClass5() {
        }

        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialogInterface, int i) {
            if (Build.VERSION.SDK_INT >= 22) {
                Frag_Chat_Manager.this.getActivity().startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
            } else {
                Frag_Chat_Manager.this.getActivity().startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
            }
        }
    }

    /* loaded from: classes.dex */
    public class Chat_Main extends ArrayAdapter {

        /* loaded from: classes.dex */
        class ViewHolder {
            TextView TenKH;
            ImageButton add_contacts;
            ImageView imageView;
            TextView ndChat;
            TextView tv_delete;

            ViewHolder() {
            }
        }

        public Chat_Main(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
        }

        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        public View getView(final int position, View convertView, ViewGroup parent) {
            View inflate = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.frag_chat_manager_lv, (ViewGroup) null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.add_contacts = (ImageButton) inflate.findViewById(R.id.add_contacts);
            viewHolder.tv_delete =  inflate.findViewById(R.id.tv_delete);
            viewHolder.imageView = (ImageView) inflate.findViewById(R.id.imv_app);
            viewHolder.TenKH =  inflate.findViewById(R.id.tv_KhachHang);
            viewHolder.ndChat =  inflate.findViewById(R.id.tv_NoiDung);
            if (((String) Frag_Chat_Manager.this.mApp.get(position)).indexOf("WA") > -1) {
                viewHolder.imageView.setBackgroundResource(R.drawable.whatsapp);
            } else if (((String) Frag_Chat_Manager.this.mApp.get(position)).indexOf("VI") > -1) {
                viewHolder.imageView.setBackgroundResource(R.drawable.viicon);
            } else if (((String) Frag_Chat_Manager.this.mApp.get(position)).indexOf("ZL") > -1) {
                viewHolder.imageView.setBackgroundResource(R.drawable.zaloicon);
            } else if (((String) Frag_Chat_Manager.this.mApp.get(position)).indexOf("TL") > -1) {
                viewHolder.imageView.setBackgroundResource(R.drawable.tele);
                viewHolder.tv_delete.setVisibility(View.GONE);
            } else if (((String) Frag_Chat_Manager.this.mApp.get(position)).indexOf("sms") > -1) {
                viewHolder.imageView.setBackgroundResource(R.drawable.sms1);
                viewHolder.add_contacts.setVisibility(View.GONE);
                viewHolder.tv_delete.setVisibility(View.GONE);
            }
            viewHolder.add_contacts.setFocusable(false);
            viewHolder.add_contacts.setFocusableInTouchMode(false);
            viewHolder.add_contacts.setOnClickListener(new View.OnClickListener() { // from class: tamhoang.ldpro4.Fragment.Frag_Chat_Manager.Chat_Main.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (Frag_Chat_Manager.this.mTenKH.size() == 0 || Frag_Chat_Manager.this.mSDT.size() == 0 || Frag_Chat_Manager.this.mApp.size() == 0) {
                        Toast.makeText(Frag_Chat_Manager.this.getActivity(), "Không có khách hàng này!", Toast.LENGTH_LONG).show();
                        return;
                    }
                    Intent intent = new Intent(Frag_Chat_Manager.this.getActivity(), (Class<?>) Activity_AddKH.class);
                    intent.putExtra("tenKH", (String) Frag_Chat_Manager.this.mTenKH.get(position));
                    intent.putExtra("so_dienthoai", (String) Frag_Chat_Manager.this.mSDT.get(position));
                    intent.putExtra("use_app", (String) Frag_Chat_Manager.this.mApp.get(position));
                    Frag_Chat_Manager.this.startActivity(intent);
                }
            });
            try {
                Frag_Chat_Manager.this.db.LayDanhsachKH();
                if (Frag_Chat_Manager.this.mTenKH.size() > 0 && Frag_Chat_Manager.this.mSDT.size() > 0 && MainActivity.DSkhachhang.size() > 0 && (MainActivity.DSkhachhang.indexOf(Frag_Chat_Manager.this.mTenKH.get(position)) > -1 || MainActivity.DSkhachhang.indexOf(Frag_Chat_Manager.this.mSDT.get(position)) > -1)) {
                    viewHolder.add_contacts.setVisibility(View.GONE);
                }
            } catch (Exception e) {
                Util.writeLog(e);
                e.printStackTrace();
            }
            if (Frag_Chat_Manager.this.mNoiDung.size() == 0 && Frag_Chat_Manager.this.mTenKH.size() > 0) {
                viewHolder.add_contacts.setVisibility(View.GONE);
            } else if (Frag_Chat_Manager.this.mNoiDung.size() > 0 && ((String) Frag_Chat_Manager.this.mNoiDung.get(position)).equals("Hôm nay chưa có tin nhắn!")) {
                viewHolder.add_contacts.setVisibility(View.GONE);
            }
            viewHolder.tv_delete.setOnClickListener(new View.OnClickListener() { // from class: tamhoang.ldpro4.Fragment.Frag_Chat_Manager.Chat_Main.2
                @Override // android.view.View.OnClickListener
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Frag_Chat_Manager.this.getActivity());
                    builder.setTitle("Xoá Khách");
                    builder.setMessage("Sẽ xóa hết dữ liệu chat từ khách này, không thể khôi phục và không thể tải lại tin nhắn!");
                    builder.setNegativeButton("Có", new DialogInterface.OnClickListener() { // from class: tamhoang.ldpro4.Fragment.Frag_Chat_Manager.Chat_Main.2.1
                        @Override // android.content.DialogInterface.OnClickListener
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                int indexOf = MainActivity.arr_TenKH.indexOf(Frag_Chat_Manager.this.mTenKH.get(position));
                                if (MainActivity.arr_TenKH.size() > 0) {
                                    MainActivity.arr_TenKH.remove(indexOf);
                                }
                                boolean z = false;
                                if (MainActivity.contactslist.size() > 0) {
                                    MainActivity.contactslist.remove(indexOf);
                                } else {
                                    Toast.makeText(Frag_Chat_Manager.this.getActivity(), "Không có khách hàng này!", Toast.LENGTH_LONG).show();
                                    z = true;
                                }
                                Frag_Chat_Manager.this.XemListview();
                                dialog.dismiss();
                                if (z) {
                                    return;
                                }
                                Toast.makeText(Frag_Chat_Manager.this.getActivity(), "Đã xóa!", Toast.LENGTH_LONG).show();
                            } catch (Exception unused) {
                                Toast.makeText(Frag_Chat_Manager.this.getActivity(), "Có lỗi xẩy ra! \nHãy kiểm tra danh sách khách hàng", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                    builder.setPositiveButton("Không", new DialogInterface.OnClickListener() { // from class: tamhoang.ldpro4.Fragment.Frag_Chat_Manager.Chat_Main.2.2
                        @Override // android.content.DialogInterface.OnClickListener
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.show();
                }
            });
            viewHolder.TenKH.setText((CharSequence) Frag_Chat_Manager.this.mTenKH.get(position));
            viewHolder.ndChat.setText((CharSequence) Frag_Chat_Manager.this.mNoiDung.get(position));
            return inflate;
        }
    }

    public void XemListview() {
        this.mTenKH.clear();
        this.mNoiDung.clear();
        this.mApp.clear();
        this.mSDT.clear();
        String Get_date = MainActivity.Get_date();
        JSONObject jSONObject = new JSONObject();
        Cursor GetData = this.db.GetData("SELECT * FROM Chat_database WHERE ngay_nhan = '" + Get_date + "' ORDER BY Gio_nhan DESC, ID DESC");
        if (GetData != null) {
            while (GetData.moveToNext()) {
                if (MainActivity.arr_TenKH.indexOf(GetData.getString(4)) > -1 || GetData.getString(6).indexOf("sms") > -1 || GetData.getString(6).indexOf("TL") > -1) {
                    if (!jSONObject.has(GetData.getString(4))) {
                        try {
                            jSONObject.put(GetData.getString(4), "OK");
                            this.mTenKH.add(GetData.getString(4));
                            this.mSDT.add(GetData.getString(5));
                            this.mApp.add(GetData.getString(6));
                            this.mNoiDung.add(GetData.getString(7));
                        } catch (Exception e) {
                            Util.writeLog(e);
                            e.printStackTrace();
                        }
                    }
                }
            }
            if (GetData != null && !GetData.isClosed()) {
                GetData.close();
            }
        }
        Iterator<String> it = MainActivity.arr_TenKH.iterator();
        while (it.hasNext()) {
            String next = it.next();
            if (this.mTenKH.indexOf(next) == -1) {
                this.mTenKH.add(next);
                this.mNoiDung.add("Hôm nay chưa có tin nhắn!");
                if (next.indexOf("ZL") > -1) {
                    this.mApp.add("ZL");
                } else if (next.indexOf("VB") > -1) {
                    this.mApp.add("VB");
                } else if (next.indexOf("WA") > -1) {
                    this.mApp.add("WA");
                }
            }
        }
        if (getActivity() != null) {
            this.listviewKH.setAdapter((ListAdapter) new Chat_Main(getActivity(), R.layout.frag_chat_manager_lv, this.mTenKH));
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:38:0x0277  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x01f2 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Unreachable blocks removed: 2, instructions: 4 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void getSMS() {
        String str = "";
        String str2 = "";
        String str3 = "";
        JSONObject jSONObject = new JSONObject();
        String str4 = "type_kh";
        String str5 = "ten_kh";
        String str6 = "";
        String str7 = " ";
        String Get_date = MainActivity.Get_date();
        if (ContextCompat.checkSelfPermission(getActivity(), "android.permission.READ_SMS") == 0) {
            try {
                String str8 = "date>=" + new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(Get_date + "T00:00:00").getTime();
                this.db.QueryData("DELETE FROM Chat_database WHERE ngay_nhan = '" + Get_date + "' AND use_app = 'sms'");
                Cursor GetData = this.db.GetData("Select * From tbl_kh_new");
                JSONObject jSONObject2 = new JSONObject();
                while (GetData.moveToNext()) {
                    try {
                        try {
                            JSONObject jSONObject3 = new JSONObject();
                            jSONObject3.put("type_kh", GetData.getString(3));
                            jSONObject3.put("ten_kh", GetData.getString(0));
                            jSONObject3.put("sdt", GetData.getString(1));
                            jSONObject3.put("so_tn", 0);
                            jSONObject2.put(GetData.getString(1), jSONObject3);
                        } finally {
                            if (GetData != null && !GetData.isClosed()) {
                                GetData.close();
                            }
                        }
                    } catch (Exception e) {
                        Util.writeLog(e);
                        e.printStackTrace();
                        if (GetData != null && !GetData.isClosed()) {
                        }
                    }
                }
                Cursor query = getActivity().getContentResolver().query(Uri.parse("content://sms"), null, str8, null, "date ASC");
                getActivity().startManagingCursor(query);
                int count = query.getCount();
                if (query.moveToFirst()) {
                    SQLiteDatabase writableDatabase = this.db.getWritableDatabase();
                    DatabaseUtils.InsertHelper insertHelper = new DatabaseUtils.InsertHelper(writableDatabase, "Chat_database");
                    try {
                        writableDatabase.beginTransaction();
                        Long l = null;
                        int i = 0;
                        while (i < count) {
                            try {
                                l = Long.valueOf(query.getLong(query.getColumnIndexOrThrow("date")));
                            } catch (Exception e2) {
                                Util.writeLog(e2);
                                e2.printStackTrace();
                                writableDatabase.endTransaction();
                                insertHelper.close();
                                writableDatabase.close();
                            }
                            StringBuilder sb = new StringBuilder();
                            int i2 = count;
                            int i3 = i;
                            try {
                                String str9 = str4;
                                String str10 = str5;
                                try {
                                    sb.append((Object) DateFormat.format("HH:mm:ss", new Date(l.longValue())));
                                    sb.append(str6);
                                    String sb2 = sb.toString();
                                    String replaceAll = query.getString(query.getColumnIndexOrThrow("address")).replaceAll(str7, str6);
                                    String replaceAll2 = query.getString(query.getColumnIndexOrThrow("body")).toString().replaceAll("'", str7).replaceAll("\"", str7);
                                    String string = query.getString(query.getColumnIndexOrThrow("type"));
                                    if (replaceAll.startsWith("0")) {
                                        try {
                                            StringBuilder sb3 = new StringBuilder();
                                            str = str6;
                                            try {
                                                sb3.append("+84");
                                                str2 = str7;
                                                try {
                                                    sb3.append(replaceAll.substring(1));
                                                    replaceAll = sb3.toString();
                                                } catch (Exception e3) {
                                                    Util.writeLog(e3);
                                                    insertHelper.close();
                                                    writableDatabase.close();
                                                    this.db.QueryData("Update tbl_tinnhanS set gio_nhan ='" + sb2 + "' WHERE nd_goc = '" + replaceAll2 + "' AND so_dienthoai = '" + replaceAll + "' AND ngay_nhan = '" + Get_date + "'");
                                                    if (jSONObject2.has(replaceAll)) {
                                                    }
                                                    query.moveToNext();
                                                    i = i3 + 1;
                                                    str5 = str3;
                                                    count = i2;
                                                    str6 = str;
                                                    str7 = str2;
                                                }
                                            } catch (Exception e4) {
                                                str2 = str7;
                                                Util.writeLog(e4);
                                                e4.printStackTrace();
                                                insertHelper.close();
                                                writableDatabase.close();
                                                this.db.QueryData("Update tbl_tinnhanS set gio_nhan ='" + sb2 + "' WHERE nd_goc = '" + replaceAll2 + "' AND so_dienthoai = '" + replaceAll + "' AND ngay_nhan = '" + Get_date + "'");
                                                if (jSONObject2.has(replaceAll)) {
                                                }
                                                query.moveToNext();
                                                i = i3 + 1;
                                                str5 = str3;
                                                count = i2;
                                                str6 = str;
                                                str7 = str2;
                                            }
                                        } catch (Exception e5) {
                                            e5.printStackTrace();
                                        }
                                    } else {
                                        str = str6;
                                        str2 = str7;
                                    }
                                    this.db.QueryData("Update tbl_tinnhanS set gio_nhan ='" + sb2 + "' WHERE nd_goc = '" + replaceAll2 + "' AND so_dienthoai = '" + replaceAll + "' AND ngay_nhan = '" + Get_date + "'");
                                    if (jSONObject2.has(replaceAll)) {
                                        try {
                                            jSONObject = jSONObject2.getJSONObject(replaceAll);
                                            jSONObject.put("so_tn", jSONObject.getInt("so_tn") + 1);
                                            jSONObject.put(replaceAll2, replaceAll2);
                                            insertHelper.prepareForInsert();
                                            try {
                                                insertHelper.bind(insertHelper.getColumnIndex("ngay_nhan"), Get_date);
                                                insertHelper.bind(insertHelper.getColumnIndex("gio_nhan"), sb2);
                                                str4 = str9;
                                            } catch (Exception e6) {
                                                e6.printStackTrace();
                                            }
                                        } catch (Exception e7) {
                                            str4 = str9;
                                            str3 = str10;
                                            try {
                                                Util.writeLog(e7);
                                                e7.printStackTrace();
                                                writableDatabase.endTransaction();
                                                insertHelper.close();
                                                writableDatabase.close();
                                                i = i3;
                                                str5 = str3;
                                                count = i2;
                                            } catch (Exception e8) {
                                                Util.writeLog(e8);
                                                e8.printStackTrace();
                                                writableDatabase.endTransaction();
                                                insertHelper.close();
                                                writableDatabase.close();
                                                str5 = str3;
                                                count = i2;
                                                i = i3;
                                            }
                                            str6 = str;
                                            str7 = str2;
                                        }
                                        try {
                                            insertHelper.bind(insertHelper.getColumnIndex(str4), string);
                                            str3 = str10;
                                            try {
                                                insertHelper.bind(insertHelper.getColumnIndex(str3), jSONObject.getString(str3));
                                                insertHelper.bind(insertHelper.getColumnIndex("so_dienthoai"), replaceAll);
                                                insertHelper.bind(insertHelper.getColumnIndex("use_app"), "sms");
                                                insertHelper.bind(insertHelper.getColumnIndex("nd_goc"), replaceAll2);
                                                try {
                                                    insertHelper.bind(insertHelper.getColumnIndex("del_sms"), 1);
                                                    insertHelper.execute();
                                                    jSONObject2.put(replaceAll, jSONObject);
                                                } catch (Exception e9) {
                                                    try {
                                                        Util.writeLog(e9);
                                                        e9.printStackTrace();
                                                        writableDatabase.endTransaction();
                                                        insertHelper.close();
                                                        writableDatabase.close();
                                                        query.moveToNext();
                                                        i = i3 + 1;
                                                    } catch (Exception e10) {
                                                        Util.writeLog(e10);
                                                        e10.printStackTrace();
                                                        writableDatabase.endTransaction();
                                                        insertHelper.close();
                                                        writableDatabase.close();
                                                        i = i3;
                                                        str5 = str3;
                                                        count = i2;
                                                        str6 = str;
                                                        str7 = str2;
                                                    }
                                                    str5 = str3;
                                                    count = i2;
                                                    str6 = str;
                                                    str7 = str2;
                                                }
                                            } catch (Exception e11) {
                                                Util.writeLog(e11);
                                                e11.printStackTrace();
                                                writableDatabase.endTransaction();
                                                insertHelper.close();
                                                writableDatabase.close();
                                                query.moveToNext();
                                                i = i3 + 1;
                                                str5 = str3;
                                                count = i2;
                                                str6 = str;
                                                str7 = str2;
                                            }
                                        } catch (Exception e12) {
                                            str3 = str10;
                                            Util.writeLog(e12);
                                            e12.printStackTrace();
                                            writableDatabase.endTransaction();
                                            insertHelper.close();
                                            writableDatabase.close();
                                            query.moveToNext();
                                            i = i3 + 1;
                                            str5 = str3;
                                            count = i2;
                                            str6 = str;
                                            str7 = str2;
                                        }
                                    } else {
                                        str4 = str9;
                                        str3 = str10;
                                    }
                                    query.moveToNext();
                                    i = i3 + 1;
                                } catch (Exception e13) {
                                    str = str6;
                                    str2 = str7;
                                }
                            } catch (Exception e14) {
                                str = str6;
                                str2 = str7;
                                str3 = str5;
                            }
                            str5 = str3;
                            count = i2;
                            str6 = str;
                            str7 = str2;
                        }
                        writableDatabase.setTransactionSuccessful();
                        writableDatabase.endTransaction();
                        insertHelper.close();
                    } catch (Exception e15) {
                        Util.writeLog(e15);
                        e15.printStackTrace();
                        writableDatabase.endTransaction();
                        insertHelper.close();
                        writableDatabase.close();
                    }
                    writableDatabase.close();
                }
            } catch (Exception e16) {
                Util.writeLog(e16);
                e16.printStackTrace();
            }
        }
    }

    private void notificationPermission() {
        ComponentName componentName = new ComponentName(getActivity(), (Class<?>) NotificationNewReader.class);
        String string = Settings.Secure.getString(getActivity().getContentResolver(), "enabled_notification_listeners");
        if (string != null && string.contains(componentName.flattenToString())) {
            return;
        }
        showAlertBox("Truy cập thông báo!", "Hãy cho phép phần mềm được truy cập thông báo của điện thoại để kích hoạt chức năng nhắn tin.").setPositiveButton(MainActivity.OK, new DialogInterface.OnClickListener() { // from class: tamhoang.ldpro4.Fragment.Frag_Chat_Manager.5
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                if (Build.VERSION.SDK_INT >= 22) {
                    Frag_Chat_Manager.this.getActivity().startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
                } else {
                    Frag_Chat_Manager.this.getActivity().startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
                }
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() { // from class: tamhoang.ldpro4.Fragment.Frag_Chat_Manager.4
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).show().setCanceledOnTouchOutside(false);
    }

    @Override // android.support.v4.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.v = inflater.inflate(R.layout.frag_chat_manager, container, false);
        this.db = new Database(getActivity());
        this.btn_Thongbao =  this.v.findViewById(R.id.btn_Thongbao);
        this.btn_login =  this.v.findViewById(R.id.btn_login);
        this.listviewKH = (ListView) this.v.findViewById(R.id.listviewKH);
        this.btn_Thongbao.setOnClickListener(new View.OnClickListener() { // from class: tamhoang.ldpro4.Fragment.Frag_Chat_Manager.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 22) {
                    Frag_Chat_Manager.this.startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
                } else {
                    Frag_Chat_Manager.this.startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
                }
            }
        });
        this.listviewKH.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: tamhoang.ldpro4.Fragment.Frag_Chat_Manager.3
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (Frag_Chat_Manager.this.mTenKH.size() == 0 || Frag_Chat_Manager.this.mSDT.size() == 0 || Frag_Chat_Manager.this.mApp.size() == 0) {
                    Toast.makeText(Frag_Chat_Manager.this.getActivity(), "Không có khách hàng này!", Toast.LENGTH_LONG).show();
                    return;
                }
                try {
                    Intent intent = new Intent(Frag_Chat_Manager.this.getActivity(), (Class<?>) Chatbox.class);
                    intent.putExtra("tenKH", (String) Frag_Chat_Manager.this.mTenKH.get(i));
                    intent.putExtra("so_dienthoai", (String) Frag_Chat_Manager.this.mSDT.get(i));
                    intent.putExtra("app", (String) Frag_Chat_Manager.this.mApp.get(i));
                    Frag_Chat_Manager.this.startActivity(intent);
                } catch (Exception unused) {
                    Toast.makeText(Frag_Chat_Manager.this.getActivity(), "Không có khách hàng này!", Toast.LENGTH_LONG).show();
                }
            }
        });
        notificationPermission();
        Handler handler = new Handler();
        this.handler = handler;
        handler.postDelayed(this.runnable, 1000L);
        return this.v;
    }

    @Override // android.support.v4.app.Fragment
    public void onDestroy() {
        this.handler.removeCallbacks(this.runnable);
        super.onDestroy();
    }

    @Override // android.support.v4.app.Fragment
    public void onResume() {
        try {
            getSMS();
        } catch (Exception e) {
            e.printStackTrace();
            Util.writeLog(e);
        }
        try {
            XemListview();
        } catch (Exception e2) {
            e2.printStackTrace();
            Util.writeLog(e2);
        }
        super.onResume();
    }

    public AlertDialog.Builder showAlertBox(String title, String message) {
        return new AlertDialog.Builder(getActivity()).setTitle(title).setMessage(message);
    }
}
