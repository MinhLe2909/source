package tamhoang.ldpro4.ui.activity;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import okhttp3.internal.cache.DiskLruCache;

import org.json.JSONException;
import org.json.JSONObject;

import tamhoang.ldpro4.base.BaseToolBarActivity;
import tamhoang.ldpro4.formula.Congthuc;
import tamhoang.ldpro4.ui.activity.Activity_CTTinnhan;
import tamhoang.ldpro4.ui.activity.Activity_Tinnhan;
import tamhoang.ldpro4.ui.home.MainActivity;
import tamhoang.ldpro4.ui.notification.NotificationNewReader;
import tamhoang.ldpro4.R;
import tamhoang.ldpro4.utils.Util;
import tamhoang.ldpro4.data.Database;


public class Chatbox extends BaseToolBarActivity {
    String app_use;
    Database db;
    Handler handler;
    ListView listView;
    EditText messageS;
    int position;
    ImageView send;
    String so_dienthoai;
    String ten_kh;
    boolean Running = true;
    JSONObject TinXuly = new JSONObject();
    private final List<String> gio_nhan = new ArrayList();
    private final List<String> mApp = new ArrayList();
    private final List<String> mID = new ArrayList();
    private final List<String> mID_TinNhan = new ArrayList();
    private final List<String> mSDT = new ArrayList();
    private final List<String> mSo_TinNhan = new ArrayList();
    private final List<String> mTenKH = new ArrayList();
    private final List<String> mXulytin = new ArrayList();
    private final List<String> nd_goc = new ArrayList();
    private final List<String> type_kh = new ArrayList();
    private final Runnable runnable = new Runnable() { // from class: tamhoang.ldpro4.Activity.Chatbox.1
        @Override // java.lang.Runnable
        public void run() {
            if (MainActivity.sms) {
                Chatbox.this.Xem_lv();
                MainActivity.sms = false;
            }
            Chatbox.this.handler.postDelayed(this, 1000L);
        }
    };

    /* loaded from: classes.dex */
    public class Chat extends ArrayAdapter {
        public Chat(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
        }

        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            if (((String) Chatbox.this.type_kh.get(position)).indexOf("2") > -1) {
                convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.message_list_item_out, (ViewGroup) null);
                TextView textView =  convertView.findViewById(R.id.body_out);
                if (((String) Chatbox.this.mXulytin.get(position)).indexOf("ok") == 0) {
                    SpannableString spannableString = new SpannableString((CharSequence) Chatbox.this.nd_goc.get(position));
                    spannableString.setSpan(new StyleSpan(1), 0, spannableString.length(), 0);
                    textView.setText(spannableString);
                } else {
                    textView.setText((CharSequence) Chatbox.this.nd_goc.get(position));
                }
                ((TextView) convertView.findViewById(R.id.status_out)).setText((CharSequence) Chatbox.this.gio_nhan.get(position));
            }
            if (((String) Chatbox.this.type_kh.get(position)).indexOf(DiskLruCache.VERSION_1) > -1) {
                convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.message_list_item_in, (ViewGroup) null);
                TextView textView2 =  convertView.findViewById(R.id.body_in);
                if (((String) Chatbox.this.mXulytin.get(position)).indexOf("ok") == 0) {
                    SpannableString spannableString2 = new SpannableString((CharSequence) Chatbox.this.nd_goc.get(position));
                    spannableString2.setSpan(new StyleSpan(1), 0, spannableString2.length(), 0);
                    textView2.setText(spannableString2);
                } else {
                    textView2.setText((CharSequence) Chatbox.this.nd_goc.get(position));
                }
                ((TextView) convertView.findViewById(R.id.status_in)).setText((CharSequence) Chatbox.this.gio_nhan.get(position));
            }
            return convertView;
        }
    }

    public void GuiTinTrucTiep(String Ngay_gui, String Gio_gui, String Ten_kh, String mText) {
        Cursor GetData = this.db.GetData("Select * From tbl_kh_new Where ten_kh = '" + Ten_kh + "'");
        GetData.moveToFirst();
        if (GetData.getCount() > 0 && GetData.getInt(3) > 1) {
            Cursor GetData2 = this.db.GetData("Select max(so_tin_nhan) from tbl_tinnhanS WHERE ngay_nhan = '" + Ngay_gui + "' AND ten_kh = '" + Ten_kh + "' AND type_kh = 2");
            GetData2.moveToFirst();
            this.db.QueryData("Insert Into tbl_tinnhanS values (null, '" + Ngay_gui + "', '" + Gio_gui + "', 2, '" + Ten_kh + "', '" + GetData.getString(1) + "', '" + GetData.getString(2) + "', " + (GetData2.getInt(0) + 1) + ", '" + mText.replaceAll("'", " ").trim() + "', '" + mText.replaceAll("'", " ").trim() + "', '" + mText.replaceAll("'", " ").trim() + "', 'ko',0, 0, 0, null)");
            StringBuilder sb = new StringBuilder();
            sb.append("Select id From tbl_tinnhanS WHERE ngay_nhan = '");
            sb.append(Ngay_gui);
            sb.append("' AND ten_kh = '");
            sb.append(Ten_kh);
            sb.append("' AND so_tin_nhan = ");
            sb.append(GetData2.getInt(0) + 1);
            sb.append(" And type_kh = 2");
            Cursor GetData3 = this.db.GetData(sb.toString());
            GetData3.moveToFirst();
            if (Congthuc.CheckDate(MainActivity.myDate)) {
                try {
                    this.db.Update_TinNhanGoc(GetData3.getInt(0), GetData.getInt(3));
                } catch (Exception unused) {
                    this.db.QueryData("Update tbl_tinnhanS set phat_hien_loi = 'ko' WHERE id = " + GetData3.getInt(0));
                    this.db.QueryData("Delete From tbl_soctS WHERE ngay_nhan = '" + Ngay_gui + "' AND ten_kh = '" + Ten_kh + "' AND so_tin_nhan = " + (GetData2.getInt(0) + 1) + " And type_kh = 2");
                    Toast.makeText(this, "Đã xảy ra lỗi!", Toast.LENGTH_LONG).show();
                }
            } else if (MainActivity.Acc_manager.length() == 0) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Thông báo:");
                builder.setMessage("Kiểm tra kết nối Internet!");
                builder.setNegativeButton("Đóng", new DialogInterface.OnClickListener() { // from class: tamhoang.ldpro4.Activity.Chatbox.4
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.create().show();
            } else {
                try {
                    AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
                    builder2.setTitle("Thông báo:");
                    builder2.setMessage("Đã hết hạn sử dụng phần mềm\n\nHãy liên hệ đại lý hoặc SĐT: " + MainActivity.listKH.getString("k_tra") + " để gia hạn");
                    builder2.setNegativeButton("Đóng", (dialog, which) -> dialog.cancel());
                    builder2.create().show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        GetData.close();
    }

    public void Xem_lv() {
        this.mID.clear();
        this.mTenKH.clear();
        this.gio_nhan.clear();
        this.type_kh.clear();
        this.nd_goc.clear();
        this.mApp.clear();
        this.mXulytin.clear();
        this.mID_TinNhan.clear();
        this.mSo_TinNhan.clear();
        String Get_date = MainActivity.Get_date();
        Cursor GetData = this.db.GetData("Select chat_database.*, tbl_tinnhanS.phat_hien_loi, tbl_tinnhanS.id, tbl_tinnhanS.so_tin_nhan From chat_database \nLEFT JOIN tbl_tinnhanS ON chat_database.ngay_nhan = tbl_tinnhanS.ngay_nhan AND chat_database.gio_nhan = tbl_tinnhanS.gio_nhan AND chat_database.ten_kh = tbl_tinnhanS.ten_kh AND chat_database.nd_goc = tbl_tinnhanS.nd_goc\nWhere chat_database.ten_kh = '" + this.ten_kh + "'  AND chat_database.ngay_nhan = '" + Get_date + "' AND chat_database.del_sms = 1 ORDER by gio_nhan");
        if (GetData != null && GetData.getCount() > 0) {
            while (GetData.moveToNext()) {
                this.mID.add(GetData.getString(0));
                this.mTenKH.add(GetData.getString(4));
                this.mSDT.add(GetData.getString(5));
                this.gio_nhan.add(GetData.getString(2));
                this.type_kh.add(GetData.getString(3));
                this.nd_goc.add(GetData.getString(7));
                this.mApp.add(GetData.getString(6));
                if (GetData.isNull(9)) {
                    this.mXulytin.add("");
                    this.mID_TinNhan.add("");
                    this.mSo_TinNhan.add("");
                } else {
                    this.mXulytin.add(GetData.getString(9));
                    this.mID_TinNhan.add(GetData.getString(10));
                    this.mSo_TinNhan.add(GetData.getString(11));
                }
            }
            GetData.close();
        }
        this.listView.setAdapter((ListAdapter) new Chat(this, R.layout.message_list_item_in, this.mTenKH));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_chatbox;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        super.onContextItemSelected(item);
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        if (item.getTitle() == "Sửa tin") {
            if (this.mXulytin.get(this.position).length() > 0) {
                Intent intent = new Intent(this, (Class<?>) Activity_Tinnhan.class);
                intent.putExtra("m_ID", this.mID_TinNhan.get(this.position));
                startActivity(intent);
            }
        } else if (item.getTitle() == "Xem chi tiết") {
            if (this.mXulytin.get(this.position).indexOf("ok") == 0) {
                Intent intent2 = new Intent(this, (Class<?>) Activity_CTTinnhan.class);
                intent2.putExtra("m_ID", this.mID_TinNhan.get(this.position));
                intent2.putExtra("type_kh", this.type_kh.get(this.position));
                startActivity(intent2);
            }
        } else if (item.getTitle() == "Copy") {
            clipboardManager.setPrimaryClip(ClipData.newPlainText("Tin nhắn:", this.nd_goc.get(this.position)));
            Toast.makeText(this, "Đã copy vào bộ nhớ tạm!", Toast.LENGTH_LONG).show();
        } else if (item.getTitle() == "Xóa") {
            MainActivity.Get_date();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Xóa tin này");
            builder.setPositiveButton("YES", (dialog, which) -> {
                if (((String) Chatbox.this.mXulytin.get(Chatbox.this.position)).length() <= 0) {
                    Chatbox.this.db.QueryData("Update chat_database set del_sms = 0 WHERE ID = " + ((String) Chatbox.this.mID.get(Chatbox.this.position)));
                    Chatbox.this.Xem_lv();
                    return;
                }
                Cursor GetData = Chatbox.this.db.GetData("Select * From tbl_tinnhanS where ID = " + ((String) Chatbox.this.mID_TinNhan.get(Chatbox.this.position)));
                GetData.moveToFirst();
                Chatbox.this.db.QueryData("DELETE FROM tbl_tinnhanS WHERE ngay_nhan = '" + GetData.getString(1) + "' AND ten_kh = '" + GetData.getString(4) + "' AND so_tin_nhan = " + GetData.getString(7) + " AND type_kh = " + GetData.getString(3));
                Chatbox.this.db.QueryData("DELETE FROM tbl_soctS WHERE ngay_nhan = '" + GetData.getString(1) + "' AND ten_kh = '" + GetData.getString(4) + "' AND so_tin_nhan = " + GetData.getString(7) + " AND type_kh = " + GetData.getString(3));
                StringBuilder sb = new StringBuilder();
                sb.append("Update chat_database set del_sms = 0 WHERE ID = ");
                sb.append((String) Chatbox.this.mID.get(Chatbox.this.position));
                Chatbox.this.db.QueryData(sb.toString());
                Chatbox.this.Xem_lv();
                Toast.makeText(Chatbox.this, "Đã xóa!", Toast.LENGTH_LONG).show();
            });
            builder.setNegativeButton("No", (dialog, which) -> dialog.cancel());
            builder.create().show();
        }
        return true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbox);
        this.listView = (ListView) findViewById(R.id.Listview);
        this.send = (ImageView) findViewById(R.id.send);
        this.messageS =  findViewById(R.id.messageS);
        MainActivity.Get_date();
        this.TinXuly = new JSONObject();
        this.db = new Database(this);
        Intent intent = getIntent();
        this.ten_kh = intent.getStringExtra("tenKH");
        this.so_dienthoai = intent.getStringExtra("so_dienthoai");
        this.app_use = intent.getStringExtra("app");
        this.send.setOnClickListener(view -> {
            boolean isClosed = false;
            String obj = Chatbox.this.messageS.getText().toString();
            try {
                if (obj.replace(" ", "").length() > 0) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(new Date());
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("HH:mm:ss");
                    simpleDateFormat.setTimeZone(TimeZone.getDefault());
                    simpleDateFormat2.setTimeZone(TimeZone.getDefault());
                    String format = simpleDateFormat.format(calendar.getTime());
                    String format2 = simpleDateFormat2.format(calendar.getTime());
                    if (Chatbox.this.app_use.indexOf("TL") > -1) {
                        try {
                            String obj2 = Chatbox.this.messageS.getText().toString();
                            MainActivity.sendMessage(Long.parseLong(Chatbox.this.so_dienthoai), obj2);
                            Chatbox chatbox = Chatbox.this;
                            chatbox.GuiTinTrucTiep(format, format2, chatbox.ten_kh, obj2);
                            Chatbox.this.messageS.setText("");
                            return;
                        } catch (Exception e2) {
                            e2.printStackTrace();
                            return;
                        }
                    }
                    if (Chatbox.this.app_use.indexOf("sms") == -1) {
                        new NotificationNewReader().NotificationWearReader(Chatbox.this.ten_kh, obj);
                        String obj3 = Chatbox.this.messageS.getText().toString();
                        Chatbox.this.db.QueryData("Insert into Chat_database Values( null,'" + format + "', '" + format2 + "', 2, '" + Chatbox.this.ten_kh + "', '" + Chatbox.this.so_dienthoai + "', '" + Chatbox.this.app_use + "','" + obj3 + "',1)");
                        Chatbox.this.messageS.setText("");
                        Chatbox chatbox2 = Chatbox.this;
                        chatbox2.GuiTinTrucTiep(format, format2, chatbox2.ten_kh, obj3);
                        MainActivity.sms = true;
                        Chatbox.this.Xem_lv();
                        return;
                    }
                    String obj4 = Chatbox.this.messageS.getText().toString();
                    StringBuilder sb = new StringBuilder();
                    Cursor cursor = null;
                    try {
                        try {
                            sb.append("Select * From tbl_kh_new Where ten_kh = '");
                            sb.append(Chatbox.this.ten_kh);
                            sb.append("'");
                            cursor = Chatbox.this.db.GetData(sb.toString());
                            cursor.moveToFirst();
                            Chatbox.this.db.SendSMS(cursor.getString(1), obj4);
                            Chatbox.this.db.QueryData("Insert into Chat_database Values( null,'" + format + "', '" + format2 + "', 2, '" + Chatbox.this.ten_kh + "', '" + Chatbox.this.so_dienthoai + "', '" + Chatbox.this.app_use + "','" + obj4 + "',1)");
                            Chatbox.this.messageS.setText("");
                            Chatbox chatbox3 = Chatbox.this;
                            chatbox3.GuiTinTrucTiep(format, format2, chatbox3.ten_kh, obj4);
                            MainActivity.sms = true;
                            Chatbox.this.Xem_lv();
                        } catch (Exception e3) {
                            Util.writeLog(e3);
                            if (cursor == null || cursor.isClosed()) {
                                return;
                            }
                        }
                        if (cursor != null) {
                            if (isClosed) {
                                return;
                            }
                            return;
                        }
                        return;
                    } finally {
                        if (cursor != null && !cursor.isClosed()) {
                            cursor.close();
                        }
                    }

                }
            } catch (Exception e) {
                Util.writeLog(e);
                e.printStackTrace();
            }
        });
        this.listView.setOnItemLongClickListener((adapterView, view, i, l) -> {
            Chatbox.this.position = i;
            return false;
        });
        Handler handler = new Handler();
        this.handler = handler;
        handler.postDelayed(this.runnable, 1000L);
        registerForContextMenu(this.listView);
        Xem_lv();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        Cursor GetData = this.db.GetData("Select * From tbl_kh_new Where ten_kh = '" + this.ten_kh + "'");
        if (GetData.getCount() == 0) {
            menu.add("Copy");
            menu.add("Xóa");
        } else {
            menu.add("Sửa tin");
            menu.add("Xem chi tiết");
            menu.add("Copy");
            menu.add("Xóa");
        }
        GetData.close();
    }

    @Override
    public void onDestroy() {
        this.handler.removeCallbacks(this.runnable);
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        Xem_lv();
    }
}
