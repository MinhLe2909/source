package tamhoang.ldpro4.ui.fragment;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import tamhoang.ldpro4.formula.Congthuc;
import tamhoang.ldpro4.ui.home.MainActivity;
import tamhoang.ldpro4.R;
import tamhoang.ldpro4.utils.Util;
import tamhoang.ldpro4.data.Database;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;


public class Frag_ChaytrangNew extends Frag_Chaytrang {
    Button btn_MaXuat;
    Button btn_Xuatso;
    SQLiteDatabase database;
    EditText edt_tien;
    LinearLayout li_loaide;
    LinearLayout li_loaixi;
    ListView lview;
    RadioButton radio_de;
    RadioButton radio_dea;
    RadioButton radio_deb;
    RadioButton radio_dec;
    RadioButton radio_ded;
    RadioButton radio_lo;
    RadioButton radio_xi;
    RadioButton radio_xi2;
    RadioButton radio_xi3;
    RadioButton radio_xi4;
    Spinner spr_trang;
    View v;
    JSONObject jsonStoped = new JSONObject();
    private Runnable runnable = new Runnable() {
        @Override // java.lang.Runnable
        public void run() {
            boolean z;
            boolean z2 = true;
            if (Curent_date_time > 0) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
                Date parseDate = parseDate("01:00");
                Date parseDate2 = parseDate("18:14");
                Date parseDate3 = parseDate("18:28");
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(Curent_date_time * 1000);
                Date parseDate4 = parseDate(calendar.get(11) + ":" + calendar.get(12));
                if (parseDate4.after(parseDate2) && parseDate4.before(parseDate3) && !LoLive) {
                    radio_xi.setEnabled(false);
                    radio_lo.setText("Lô Live");
                    LoLive = true;
                } else {
                    if (parseDate4.after(parseDate3)) {
                        handler.removeCallbacks(runnable);
                        btn_Xuatso.setEnabled(false);
                        btn_Xuatso.setText("Hết giờ");
                        btn_Xuatso.setTextColor(-7829368);
                    } else if (parseDate4.before(parseDate)) {
                        btn_Xuatso.setEnabled(false);
                        btn_Xuatso.setText("Chưa mở");
                        btn_Xuatso.setTextColor(-7829368);
                    }
                    z = false;
                    if (LoLive && radio_lo.isChecked()) {
                        Dem++;
                        if (Dem >= 4) {
                            Dem = 0;
                            Laygia();
                        }
                    }
                    if (z) {
                        btn_Xuatso.setText("Chạy trang (" + simpleDateFormat.format(calendar.getTime()) + ")");
                    }
                    z2 = z;
                }
                z = true;
                if (LoLive) {
                    Dem++;
                    if (Dem >= 4) {
                    }
                }
                if (z) {
                }
                z2 = z;
            } else {
                Curent_date_time = new Timestamp(System.currentTimeMillis()).getTime() / 1000;
            }
            if (z2) {
                Curent_date_time++;
                handler.postDelayed(this, 1000L);
            }
        }
    };

    public class AnonymousClass18 implements View.OnClickListener {
        final Button val$btn_chuyendi;
        final Dialog val$dialog;
        final EditText val$edt_XuatDan;
        final TextView val$edt_XuatErr;

        AnonymousClass18(final Button val$btn_chuyendi, final EditText val$edt_XuatDan, final TextView val$edt_XuatErr, final Dialog val$dialog) {
            this.val$btn_chuyendi = val$btn_chuyendi;
            this.val$edt_XuatDan = val$edt_XuatDan;
            this.val$edt_XuatErr = val$edt_XuatErr;
            this.val$dialog = val$dialog;
        }

        public void lambda$onClick$0$Frag_ChaytrangNew$18(AtomicReference str3, OkHttpClient okHttpClient, String Postjson, final SQLiteDatabase database, final DatabaseUtils.InsertHelper ih, final EditText edt_XuatDan, final Dialog dialog, final TextView edt_XuatErr, final Button btn_chuyendi) {
            try {
                Request.Builder header = new Request.Builder().url("https://lotto.lotusapi.com/game-play/player/play").header("Content-Type", "application/json");
                try {
                    StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());
                    str3.set(okHttpClient.newCall(header.header("Authorization", "Bearer " + MainActivity.MyToken).post(RequestBody.create(Postjson, MediaType.parse("application/json"))).build()).execute().body().string());
                    String atomicReference = str3.toString();
                    if (!atomicReference.startsWith("[")) {
                        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: tamhoang.ldpro4.Fragment.18.4
                            @Override // java.lang.Runnable
                            public void run() {
                                edt_XuatErr.setText("Kết nối kém, hãy xuất lại.");
                                edt_XuatErr.setVisibility(View.VISIBLE);
                                btn_chuyendi.setEnabled(true);
                            }
                        });
                    } else if (new JSONArray(atomicReference).getJSONObject(0).has("Tx")) {
                        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: tamhoang.ldpro4.Fragment.18.2
                            @Override // java.lang.Runnable
                            public void run() {
                                String str = "ngay_nhan";
                                String str2 = " ";
                                String str4 = "'";
                                try {
                                    try {
                                        database.beginTransaction();
                                        int i = 0;
                                        JSONObject jSONObject = null;
                                        while (i < jsonArray.length()) {
                                            jSONObject = jsonArray.getJSONObject(i);
                                            ih.prepareForInsert();
                                            DatabaseUtils.InsertHelper insertHelper = ih;
                                            String str5 = str2;
                                            String str6 = str4;
                                            insertHelper.bind(insertHelper.getColumnIndex("ID"), (byte[]) null);
                                            DatabaseUtils.InsertHelper insertHelper2 = ih;
                                            insertHelper2.bind(insertHelper2.getColumnIndex(str), jSONObject.getString(str));
                                            DatabaseUtils.InsertHelper insertHelper3 = ih;
                                            insertHelper3.bind(insertHelper3.getColumnIndex("type_kh"), 2);
                                            DatabaseUtils.InsertHelper insertHelper4 = ih;
                                            String str7 = str;
                                            insertHelper4.bind(insertHelper4.getColumnIndex("ten_kh"), mwebsite.get(spin_pointion));
                                            DatabaseUtils.InsertHelper insertHelper5 = ih;
                                            insertHelper5.bind(insertHelper5.getColumnIndex("so_dienthoai"), mwebsite.get(spin_pointion));
                                            DatabaseUtils.InsertHelper insertHelper6 = ih;
                                            insertHelper6.bind(insertHelper6.getColumnIndex("so_tin_nhan"), jSONObject.getInt("so_tin_nhan"));
                                            String str8 = the_loai.indexOf("xi") > -1 ? "xi" : the_loai;
                                            DatabaseUtils.InsertHelper insertHelper7 = ih;
                                            insertHelper7.bind(insertHelper7.getColumnIndex("the_loai"), str8);
                                            DatabaseUtils.InsertHelper insertHelper8 = ih;
                                            insertHelper8.bind(insertHelper8.getColumnIndex("so_chon"), jSONObject.getString("so_chon"));
                                            DatabaseUtils.InsertHelper insertHelper9 = ih;
                                            insertHelper9.bind(insertHelper9.getColumnIndex("diem"), jSONObject.getInt("diem"));
                                            DatabaseUtils.InsertHelper insertHelper10 = ih;
                                            insertHelper10.bind(insertHelper10.getColumnIndex("diem_quydoi"), jSONObject.getInt("diem"));
                                            DatabaseUtils.InsertHelper insertHelper11 = ih;
                                            insertHelper11.bind(insertHelper11.getColumnIndex("diem_khachgiu"), 0);
                                            DatabaseUtils.InsertHelper insertHelper12 = ih;
                                            insertHelper12.bind(insertHelper12.getColumnIndex("diem_dly_giu"), 0);
                                            DatabaseUtils.InsertHelper insertHelper13 = ih;
                                            insertHelper13.bind(insertHelper13.getColumnIndex("diem_ton"), jSONObject.getInt("diem"));
                                            DatabaseUtils.InsertHelper insertHelper14 = ih;
                                            insertHelper14.bind(insertHelper14.getColumnIndex("gia"), jSONObject.getInt("gia"));
                                            DatabaseUtils.InsertHelper insertHelper15 = ih;
                                            insertHelper15.bind(insertHelper15.getColumnIndex("lan_an"), jSONObject.getInt("lan_an"));
                                            DatabaseUtils.InsertHelper insertHelper16 = ih;
                                            insertHelper16.bind(insertHelper16.getColumnIndex("so_nhay"), 0);
                                            DatabaseUtils.InsertHelper insertHelper17 = ih;
                                            insertHelper17.bind(insertHelper17.getColumnIndex("tong_tien"), jSONObject.getInt("tong_tien"));
                                            DatabaseUtils.InsertHelper insertHelper18 = ih;
                                            insertHelper18.bind(insertHelper18.getColumnIndex("ket_qua"), 0);
                                            ih.execute();
                                            i++;
                                            str = str7;
                                            str2 = str5;
                                            str4 = str6;
                                        }
                                        String str9 = str2;
                                        String str10 = str4;
                                        database.setTransactionSuccessful();
                                        Calendar calendar = Calendar.getInstance();
                                        calendar.setTime(new Date());
                                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("HH:mm:ss");
                                        simpleDateFormat.setTimeZone(TimeZone.getDefault());
                                        simpleDateFormat2.setTimeZone(TimeZone.getDefault());
                                        String format = simpleDateFormat.format(calendar.getTime());
                                        String format2 = simpleDateFormat2.format(calendar.getTime());
                                        db.QueryData("Insert Into tbl_tinnhanS values (null, '" + format + "', '" + format2 + "', 2, '" + mwebsite.get(spin_pointion) + "', '" + mwebsite.get(spin_pointion) + "', 'ChayTrang', " + jSONObject.getInt("so_tin_nhan") + ", '" + edt_XuatDan.getText().toString().replace(str10, str9).trim() + "', '" + edt_XuatDan.getText().toString().replace(str10, str9).trim() + "', '" + edt_XuatDan.getText().toString().replace(str10, str9).trim().toLowerCase() + "', 'ok',0, 0, 0, null)");
                                    } catch (Exception e) {
                                        Util.writeLog(e);
                                        e.printStackTrace();
                                    }
                                    database.endTransaction();
                                    ih.close();
                                    xem_RecycView();
                                    dialog.dismiss();
                                    Toast.makeText(getActivity(), "Đã chạy thành công!", Toast.LENGTH_SHORT).show();
                                } catch (Throwable th) {
                                    database.endTransaction();
                                    ih.close();
                                    throw th;
                                }
                            }
                        });
                    } else {
                        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: tamhoang.ldpro4.Fragment.18.3
                            @Override // java.lang.Runnable
                            public void run() {
                                edt_XuatErr.setText("Kết nối kém, hãy xuất lại.");
                                edt_XuatErr.setVisibility(View.VISIBLE);
                                btn_chuyendi.setEnabled(true);
                            }
                        });
                    }
                } catch (Exception e) {
                    try {
                        Util.writeLog(e);
                        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: tamhoang.ldpro4.Fragment.18.5
                            @Override // java.lang.Runnable
                            public void run() {
                                edt_XuatErr.setText("Kết nối kém, hãy xuất lại.");
                                edt_XuatErr.setVisibility(View.VISIBLE);
                                btn_chuyendi.setEnabled(true);
                            }
                        });
                        e.printStackTrace();
                    } catch (Exception e2) {
                        Util.writeLog(e2);
                        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: tamhoang.ldpro4.Fragment.18.6
                            @Override // java.lang.Runnable
                            public void run() {
                                edt_XuatErr.setText("Kết nối kém, hãy xuất lại.");
                                edt_XuatErr.setVisibility(View.VISIBLE);
                                btn_chuyendi.setEnabled(true);
                            }
                        });
                        e2.printStackTrace();
                    }
                }
            } catch (Exception e3) {
                Util.writeLog(e3);
                new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: tamhoang.ldpro4.Fragment.18.7
                    @Override // java.lang.Runnable
                    public void run() {
                        edt_XuatErr.setText("Kết nối kém, hãy xuất lại.");
                        edt_XuatErr.setVisibility(View.VISIBLE);
                        btn_chuyendi.setEnabled(true);
                    }
                });
                e3.printStackTrace();
            }
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            try {
                this.val$btn_chuyendi.setEnabled(false);
                String trim = this.val$edt_XuatDan.getText().toString().replaceAll("'", " ").trim();
                String KiemTraTruocKhiChayTrang = KiemTraTruocKhiChayTrang(trim);
                if (checkStopedNumbers(this.val$edt_XuatErr, this.val$btn_chuyendi, trim)) {
                    Toast.makeText(getActivity(), "Không chạy trang được do có số stoped!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (KiemTraTruocKhiChayTrang == "") {
                    try {
                        KiemTraTruocKhiChayTrang = Laygia();
                    } catch (Exception e) {
                        Util.writeLog(e);
                        return;
                    }
                }
                if (KiemTraTruocKhiChayTrang != "") {
                    this.val$edt_XuatErr.setText(KiemTraTruocKhiChayTrang);
                    this.val$edt_XuatErr.setVisibility(View.VISIBLE);
                    this.val$btn_chuyendi.setEnabled(true);
                    return;
                }
                if (database == null || !database.isOpen()) {
                    database = db.getWritableDatabase();
                }
                final DatabaseUtils.InsertHelper insertHelper = new DatabaseUtils.InsertHelper(database, "tbl_soctS");
                jsonArray = new JSONArray();
                final OkHttpClient okHttpClient = new OkHttpClient();
                final AtomicReference atomicReference = new AtomicReference("");
                if (Build.VERSION.SDK_INT >= 24) {
                    CompletableFuture.runAsync(() -> {
                        AnonymousClass18 anonymousClass18 = AnonymousClass18.this;
                        anonymousClass18.lambda$onClick$0$Frag_ChaytrangNew$18(atomicReference, okHttpClient, CreateJson(), database, insertHelper, AnonymousClass18.this.val$edt_XuatDan, AnonymousClass18.this.val$dialog, AnonymousClass18.this.val$edt_XuatErr, AnonymousClass18.this.val$btn_chuyendi);
                    });
                }
            } catch (Exception e2) {
                Util.writeLog(e2);
                e2.printStackTrace();
                this.val$edt_XuatErr.setText("Có lỗi khi xuất tin!");
                this.val$edt_XuatErr.setVisibility(View.VISIBLE);
                this.val$btn_chuyendi.setEnabled(true);
            }
        }
    }

    class Ma_da_chay extends ArrayAdapter {

        class ViewHolder {
            TextView tv_HuyCuoc;
            TextView tv_NoiDung;
            TextView tv_SoTin;
            TextView tv_ThoiGian;
            TextView tv_TienCuoc;

            ViewHolder() {
            }
        }

        public Ma_da_chay(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            View inflate = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.frag_chaytrang_tinchay_lv, (ViewGroup) null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.tv_SoTin =  inflate.findViewById(R.id.tv_SoTin);
            viewHolder.tv_NoiDung =  inflate.findViewById(R.id.tv_NoiDung);
            viewHolder.tv_ThoiGian =  inflate.findViewById(R.id.tv_ThoiGan);
            viewHolder.tv_TienCuoc =  inflate.findViewById(R.id.tv_TienCuoc);
            viewHolder.tv_HuyCuoc =  inflate.findViewById(R.id.tv_HuyCuoc);
            viewHolder.tv_HuyCuoc.setFocusable(false);
            viewHolder.tv_HuyCuoc.setFocusableInTouchMode(false);
            viewHolder.tv_HuyCuoc.setOnClickListener(new View.OnClickListener() { // from class: tamhoang.ldpro4.Fragment.Ma_da_chay.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                }
            });
            if (TheLoai.get(position).intValue() == 0) {
                viewHolder.tv_NoiDung.setText("Đề: " + NoiDung.get(position));
            } else if (TheLoai.get(position).intValue() == 1) {
                viewHolder.tv_NoiDung.setText("Lô: " + NoiDung.get(position));
            } else if (TheLoai.get(position).intValue() == 2) {
                viewHolder.tv_NoiDung.setText("Xiên 2: " + NoiDung.get(position));
            } else if (TheLoai.get(position).intValue() == 3) {
                viewHolder.tv_NoiDung.setText("Xiên 3: " + NoiDung.get(position));
            } else if (TheLoai.get(position).intValue() == 4) {
                viewHolder.tv_NoiDung.setText("Xiên 4: " + NoiDung.get(position));
            } else if (TheLoai.get(position).intValue() == 20) {
                viewHolder.tv_NoiDung.setText("Lô Live: " + NoiDung.get(position));
            } else if (TheLoai.get(position).intValue() == 21) {
                viewHolder.tv_NoiDung.setText("Đề đầu: " + NoiDung.get(position));
            } else if (TheLoai.get(position).intValue() == 22) {
                viewHolder.tv_NoiDung.setText("Giải nhất: " + NoiDung.get(position));
            } else if (TheLoai.get(position).intValue() == 23) {
                viewHolder.tv_NoiDung.setText("Đầu giải nhất: " + NoiDung.get(position));
            }
            viewHolder.tv_ThoiGian.setText("Time: " + ThoiGian.get(position));
            viewHolder.tv_TienCuoc.setText("Tổng: " + TienCuoc.get(position));
            viewHolder.tv_SoTin.setText(SoTin.get(position));
            if (HuyCuoc.get(position).intValue() == 0) {
                viewHolder.tv_HuyCuoc.setTextColor(-7829368);
                viewHolder.tv_HuyCuoc.setEnabled(false);
                viewHolder.tv_HuyCuoc.setText("Đã huỷ");
            } else {
                viewHolder.tv_HuyCuoc.setVisibility(View.GONE);
            }
            return inflate;
        }
    }


    public class So_OmAdapter extends ArrayAdapter {
        class ViewHolder {
            TextView tview1;
            TextView tview2;
            TextView tview4;
            TextView tview5;
            TextView tview7;
            TextView tview8;

            ViewHolder() {
            }
        }

        public So_OmAdapter(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
        }

        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        public View getView(int position, View view, ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ViewHolder viewHolder = new ViewHolder();
            if (view == null) {
                view = layoutInflater.inflate(R.layout.frag_canchuyen_lv, (ViewGroup) null);
                viewHolder.tview2 =  view.findViewById(R.id.stt);
                viewHolder.tview5 =  view.findViewById(R.id.Tv_so);
                viewHolder.tview7 =  view.findViewById(R.id.tv_diemNhan);
                viewHolder.tview8 =  view.findViewById(R.id.tv_diemOm);
                viewHolder.tview1 =  view.findViewById(R.id.tv_diemChuyen);
                viewHolder.tview4 =  view.findViewById(R.id.tv_diemTon);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            if (mNhay.get(position).intValue() > 0) {
//                viewHolder.tview5.setTextColor(SupportMenu.CATEGORY_MASK);
//                viewHolder.tview7.setTextColor(SupportMenu.CATEGORY_MASK);
//                viewHolder.tview8.setTextColor(SupportMenu.CATEGORY_MASK);
//                viewHolder.tview1.setTextColor(SupportMenu.CATEGORY_MASK);
//                viewHolder.tview4.setTextColor(SupportMenu.CATEGORY_MASK);
                if (mNhay.get(position).intValue() == 1) {
                    viewHolder.tview5.setText(mSo.get(position) + "*");
                } else if (mNhay.get(position).intValue() == 2) {
                    viewHolder.tview5.setText(mSo.get(position) + "**");
                } else if (mNhay.get(position).intValue() == 3) {
                    viewHolder.tview5.setText(mSo.get(position) + "***");
                } else if (mNhay.get(position).intValue() == 4) {
                    viewHolder.tview5.setText(mSo.get(position) + "****");
                } else if (mNhay.get(position).intValue() == 5) {
                    viewHolder.tview5.setText(mSo.get(position) + "*****");
                } else if (mNhay.get(position).intValue() == 6) {
                    viewHolder.tview5.setText(mSo.get(position) + "******");
                }
                viewHolder.tview2.setText((position + 1) + "");
                viewHolder.tview7.setText(mTienTon.get(position));
                viewHolder.tview8.setText("0");
                viewHolder.tview1.setText(mMax.get(position));
                if (mGia.size() > 0) {
                    viewHolder.tview4.setText(mGia.get(position));
                }
            } else {
//                viewHolder.tview5.setTextColor(ViewCompat.MEASURED_STATE_MASK);
//                viewHolder.tview7.setTextColor(ViewCompat.MEASURED_STATE_MASK);
//                viewHolder.tview8.setTextColor(ViewCompat.MEASURED_STATE_MASK);
//                viewHolder.tview1.setTextColor(ViewCompat.MEASURED_STATE_MASK);
//                viewHolder.tview4.setTextColor(ViewCompat.MEASURED_STATE_MASK);
                viewHolder.tview2.setText((position + 1) + "");
                viewHolder.tview5.setText(mSo.get(position));
                viewHolder.tview7.setText(mTienTon.get(position));
                viewHolder.tview8.setText(mTienchuyen.get(position));
                viewHolder.tview1.setText(mMax.get(position));
                if (mGia.size() > 0) {
                    viewHolder.tview4.setText(mGia.get(position));
                    if (Integer.parseInt(mGia.get(position)) > Price) {
//                        viewHolder.tview4.setTextColor(SupportMenu.CATEGORY_MASK);
                    }
                }
            }
            return view;
        }
    }
    
    public String CreateJson() {
        JSONObject jSONObject = null;
        JSONArray jSONArray = null;
        JSONObject jSONObject2 = null;
        JSONArray jSONArray2 = null;
        int i = 0;
        Iterator<String> keys;
        JSONObject jSONObject3;
        JSONObject jSONObject4 = null;
        JSONObject jSONObject5;
        Object obj;
        int i2;
        JSONArray jSONArray3;
        Iterator<String> it;
        String str = "";
        String[] strArr;
        String str2 = ",";
        new JSONObject();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        simpleDateFormat.setTimeZone(TimeZone.getDefault());
        String format = simpleDateFormat.format(calendar.getTime());
        Cursor GetData = this.db.GetData("Select max(so_tin_nhan) from tbl_tinnhans where ngay_nhan = '" + format + "' And ten_kh = '" + this.mwebsite.get(this.spin_pointion) + "'");
        GetData.moveToFirst();
        int i3 = GetData.getInt(0) + 1;
        JSONObject jSONObject6 = new JSONObject();
        try {
            jSONObject6.put("Term", format);
            jSONObject6.put("IgnorePrice", true);
            jSONArray = new JSONArray();
            jSONObject2 = new JSONObject();
            jSONObject2.put("GameType", 0);
            jSONObject2.put("BetType", this.GameType);
            jSONArray2 = new JSONArray();
        } catch (JSONException e) {
            e = e;
            jSONObject = jSONObject6;
        }
        try {
            if (((this.GameType != 0 && this.GameType != 21 && this.GameType != 22 && this.GameType != 23) || this.Price <= 80000) && this.GameType != 1 && this.GameType != 20) {
                if (this.GameType == 2) {
                    i = 10000;
                } else if (this.GameType == 3) {
                    i = 40000;
                } else if (this.GameType == 4) {
                    i = 100000;
                } else if (this.GameType != 6) {
                    i = 0;
                }
                if (this.GameType == 0 && this.Price < 80000) {
                    i = 70000;
                }
                keys = this.jsonChayTrang.keys();
                while (keys.hasNext()) {
                    String next = keys.next();
                    try {
                        if (this.jsonChayTrang.getDouble(next) > 0.0d) {
                            JSONObject jSONObject7 = new JSONObject();
                            JSONArray jSONArray4 = new JSONArray();
                            String[] split = next.split(str2);
                            it = keys;
                            int length = split.length;
                            jSONObject5 = jSONObject6;
                            int i4 = 0;
                            jSONArray3 = jSONArray;
                            String str3 = "";
                            while (i4 < length) {
                                int i5 = length;
                                String str4 = split[i4];
                                if (str4.length() > 0) {
                                    jSONArray4.put(str4);
                                    strArr = split;
                                    str3 = str3 + str4 + str2;
                                } else {
                                    strArr = split;
                                }
                                i4++;
                                length = i5;
                                split = strArr;
                                str3 = str3;
                            }
                            try {
                                String substring = str3.endsWith(str2) ? str3.substring(0, str3.length() - 1) : str3;
                                JSONObject jSONObject8 = new JSONObject();
                                jSONObject8.put("ngay_nhan", format);
                                jSONObject8.put("type_kh", 2);
                                obj = jSONObject;
                                jSONObject8.put("ten_kh", this.mwebsite.get(this.spin_pointion));
                                jSONObject8.put("so_dienthoai", this.mwebsite.get(this.spin_pointion));
                                jSONObject8.put("so_tin_nhan", i3);
                                jSONObject8.put("the_loai", this.the_loai.indexOf("xi") > -1 ? "xi" : this.the_loai);
                                jSONObject8.put("so_chon", substring);
                                try {
                                    str = format;
                                    i2 = i3;
                                    jSONObject8.put("diem", this.jsonChayTrang.getDouble(next));
                                    jSONObject8.put("diem_quydoi", this.jsonChayTrang.getDouble(next));
                                    jSONObject8.put("diem_khachgiu", 0);
                                    jSONObject8.put("diem_dly_giu", 0);
                                    jSONObject8.put("diem_ton", this.jsonChayTrang.getDouble(next));
                                    if (!this.radio_xi.isChecked()) {
                                        jSONObject8.put("gia", this.jsonGia.has(next) ? this.Price + this.jsonGia.getInt(next) : this.Price);
                                    } else if (this.jsonTienxien.has(substring)) {
                                        jSONObject8.put("gia", this.jsonTienxien.getInt(substring));
                                    } else {
                                        jSONObject8.put("gia", this.Price);
                                    }
                                    jSONObject8.put("lan_an", i);
                                    jSONObject8.put("so_nhay", 0);
                                    double d = this.jsonGia.has(next) ? this.Price + this.jsonGia.getInt(next) : this.Price;
                                    double d2 = this.jsonChayTrang.getDouble(next);
                                    Double.isNaN(d);
                                    Double.isNaN(d);
                                    jSONObject8.put("tong_tien", d * d2);
                                    jSONObject8.put("ket_qua", 0);
                                    this.jsonArray.put(jSONObject8);
                                    jSONObject7.put("Numbers", jSONArray4);
                                    jSONObject7.put("Point", this.jsonChayTrang.getDouble(next));
                                    jSONObject7.put("Price", 705);
                                    jSONArray2.put(jSONObject7);
                                } catch (JSONException e4) {
                                    e4.printStackTrace();
                                    return "Kiểm tra lại số liệu";
                                }
                            } catch (JSONException e5) {
                                e5.printStackTrace();
                                return "Kiểm tra lại số liệu";
                            }
                        } else {
                            obj = jSONObject;
                            i2 = i3;
                            jSONObject5 = jSONObject6;
                            jSONArray3 = jSONArray;
                            it = keys;
                            str = format;
                        }
                        format = str;
                        keys = it;
                        jSONArray = jSONArray3;
                        jSONObject6 = jSONObject5;
                        i3 = i2;
                    } catch (JSONException e6) {
                        jSONObject5 = jSONObject6;
                    }
                }
                jSONObject3 = jSONObject6;
                JSONArray jSONArray5 = jSONArray;
                jSONObject2.put("Items", jSONArray2);
                jSONArray5.put(jSONObject2);
                jSONObject4 = jSONObject3;
                jSONObject4.put("Tickets", jSONArray5);
                return jSONObject4.toString();
            }
            return jSONObject4.toString();
        } catch (JSONException e7) {
            e7.printStackTrace();
            return jSONObject4.toString();
        }
    }

    private void init() {
        this.radio_de = (RadioButton) this.v.findViewById(R.id.radio_de);
        this.radio_lo = (RadioButton) this.v.findViewById(R.id.radio_lo);
        this.radio_xi = (RadioButton) this.v.findViewById(R.id.radio_xi);
        this.radio_dea = (RadioButton) this.v.findViewById(R.id.radio_dea);
        this.radio_deb = (RadioButton) this.v.findViewById(R.id.radio_deb);
        this.radio_dec = (RadioButton) this.v.findViewById(R.id.radio_dec);
        this.radio_ded = (RadioButton) this.v.findViewById(R.id.radio_ded);
        this.radio_xi2 = (RadioButton) this.v.findViewById(R.id.radio_xi2);
        this.radio_xi3 = (RadioButton) this.v.findViewById(R.id.radio_xi3);
        this.radio_xi4 = (RadioButton) this.v.findViewById(R.id.radio_xi4);
        this.btn_Xuatso =  this.v.findViewById(R.id.btn_Xuatso);
        this.lview = (ListView) this.v.findViewById(R.id.lview);
        this.li_loaide =  this.v.findViewById(R.id.li_loaide);
        this.li_loaixi =  this.v.findViewById(R.id.li_loaixi);
        this.spr_trang = (Spinner) this.v.findViewById(R.id.spr_trang);
        this.btn_MaXuat =  this.v.findViewById(R.id.btn_MaXuat);
        this.edt_tien =  this.v.findViewById(R.id.edt_tien);
    }

    public static Date parseDate(String date) {
        try {
            return new SimpleDateFormat("HH:mm", Locale.US).parse(date);
        } catch (ParseException unused) {
            return new Date(0L);
        }
    }

    @Override // tamhoang.ldpro4.Fragment.Frag_Chaytrang
    public void Dialog() {
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.frag_chaytrang_diaglog);
        dialog.getWindow().setLayout(-1, -2);
        EditText editText =  dialog.findViewById(R.id.edt_XuatDan);
        final TextView textView =  dialog.findViewById(R.id.taikhoan);
        final TextView textView2 =  dialog.findViewById(R.id.CreditLimit);
        final TextView textView3 =  dialog.findViewById(R.id.Balance);
        TextView textView4 =  dialog.findViewById(R.id.edt_XuatErr);
        textView4.setVisibility(View.GONE);
        Button button =  dialog.findViewById(R.id.btn_chuyendi);
        final OkHttpClient okHttpClient = new OkHttpClient();
        if (MainActivity.MyToken.length() > 0 && Build.VERSION.SDK_INT >= 24) {
            CompletableFuture.runAsync(new Runnable() { // from class: tamhoang.ldpro4.Fragment.17
                @Override // java.lang.Runnable
                public final void run() {
                    lambda$Dialog$0$Frag_ChaytrangNew(okHttpClient, textView, textView2, textView3);
                }
            });
        }
        editText.setText("");
        editText.setText(this.xuatDan.replaceAll(",x", "x"));
        checkStopedNumbers(textView4, button, this.xuatDan.replaceAll(",x", "x"));
        button.setOnClickListener(new AnonymousClass18(button, editText, textView4, dialog));
        dialog.setCancelable(true);
        dialog.setTitle("Xem dạng:");
        dialog.show();
    }

    @Override // tamhoang.ldpro4.Fragment.Frag_Chaytrang
    public void Dialog2() {
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.frag_chaytrang_tinchay);
        final DecimalFormat decimalFormat = new DecimalFormat("###,###");
        dialog.getWindow().setLayout(-1, -2);
        final ListView listView = (ListView) dialog.findViewById(R.id.lv_cacmachay);
        this.SoTin.clear();
        this.TheLoai.clear();
        this.NoiDung.clear();
        this.ThoiGian.clear();
        this.TienCuoc.clear();
        try {
            final OkHttpClient okHttpClient = new OkHttpClient();
            if (Build.VERSION.SDK_INT >= 24) {
                CompletableFuture.runAsync(new Runnable() { // from class: tamhoang.ldpro4.Fragment.21
                    @Override // java.lang.Runnable
                    public final void run() {
                        lambda$Dialog2$1$Frag_ChaytrangNew(okHttpClient, decimalFormat, listView);
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        dialog.getWindow().setLayout(-1, -2);
        dialog.setCancelable(true);
        dialog.setTitle("Xem dạng:");
        dialog.show();
    }

    /* JADX WARN: Removed duplicated region for block: B:162:0x0423  */
    @Override // tamhoang.ldpro4.Fragment.Frag_Chaytrang
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public String KiemTraTruocKhiChayTrang(String DanSo) throws JSONException {
        String str;
        Cursor cursor = null;
        String str2;
        JSONObject jSONObject;
        Cursor cursor2;
        String str3;
        Frag_ChaytrangNew frag_ChaytrangNew;
        int i = 0;
        Cursor cursor3;
        String str4;
        String str5;
        int parseInt;
        JSONObject jSONObject2;
        String str6;
        String[] strArr;
        Frag_ChaytrangNew frag_ChaytrangNew2 = this;
        String str7 = "Kiểm tra lại số liệu";
        frag_ChaytrangNew2.jsonChayTrang = new JSONObject();
        JSONObject jSONObject3 = new JSONObject();
        String Get_date = MainActivity.Get_date();
        if (frag_ChaytrangNew2.the_loai.indexOf("deb") > -1) {
            str = "Select so_chon, Sum(diem) FROM tbl_soctS Where ten_kh = '" + frag_ChaytrangNew2.mwebsite.get(frag_ChaytrangNew2.spin_pointion) + "' AND type_kh = 2 AND (the_loai = 'deb' or the_loai = 'det') AND ngay_nhan = '" + Get_date + "' Group by so_chon";
        } else if (frag_ChaytrangNew2.the_loai.indexOf("xi2") > -1) {
            str = "Select so_chon, Sum(diem) FROM tbl_soctS Where ten_kh = '" + frag_ChaytrangNew2.mwebsite.get(frag_ChaytrangNew2.spin_pointion) + "' AND type_kh = 2 AND the_loai = 'xi' AND length(so_chon) = 5  AND ngay_nhan = '" + Get_date + "' Group by so_chon";
        } else if (frag_ChaytrangNew2.the_loai.indexOf("xi3") > -1) {
            str = "Select so_chon, Sum(diem) FROM tbl_soctS Where ten_kh = '" + frag_ChaytrangNew2.mwebsite.get(frag_ChaytrangNew2.spin_pointion) + "' AND type_kh = 2 AND the_loai = 'xi' AND length(so_chon) = 8  AND ngay_nhan = '" + Get_date + "' Group by so_chon";
        } else if (frag_ChaytrangNew2.the_loai.indexOf("xi4") > -1) {
            str = "Select so_chon, Sum(diem) FROM tbl_soctS Where ten_kh = '" + frag_ChaytrangNew2.mwebsite.get(frag_ChaytrangNew2.spin_pointion) + "' AND type_kh = 2 AND the_loai = 'xi' AND length(so_chon) = 11  AND ngay_nhan = '" + Get_date + "' Group by so_chon";
        } else {
            str = "Select so_chon, Sum(diem) FROM tbl_soctS Where ten_kh = '" + frag_ChaytrangNew2.mwebsite.get(frag_ChaytrangNew2.spin_pointion) + "' AND type_kh = 2 AND the_loai = '" + frag_ChaytrangNew2.the_loai + "' AND ngay_nhan = '" + Get_date + "' Group by so_chon";
        }
        Cursor GetData = frag_ChaytrangNew2.db.GetData(str);
        while (GetData.moveToNext()) {
            try {
                jSONObject3.put(GetData.getString(0), GetData.getInt(1));
            } catch (Exception e) {
                cursor = GetData;
                try {
                    Util.writeLog(e);
                    e.printStackTrace();
                    if (!cursor.isClosed()) {
                        cursor.close();
                    }
                    return "Không phân tích được nội dung!";
                } catch (Throwable th) {
                    if (!cursor.isClosed()) {
                        cursor.close();
                    }
                }
            } catch (Throwable th2) {
                if (!cursor.isClosed()) {
                    cursor.close();
                }
            }
        }
        if (!GetData.isClosed()) {
            GetData.close();
        }
        String str8 = "k";
        String str9 = ",";
        if (frag_ChaytrangNew2.the_loai.contains("xi")) {
            str2 = "Kiểm tra lại số liệu";
            jSONObject = jSONObject3;
            cursor2 = GetData;
            String str10 = "k";
            str3 = ",";
            try {
                String[] split = DanSo.substring(DanSo.indexOf(":")).split(" ");
                i = 0;
                int i2 = 0;
                while (i2 < split.length) {
                    try {
                        String substring = split[i2].substring(split[i2].indexOf(":") + 1);
                        String substring2 = substring.substring(0, substring.indexOf("x"));
                        String str11 = str10;
                        String replaceAll = split[i2].substring(split[i2].indexOf("x")).replaceAll("x", "").replaceAll("n", "").replaceAll("d", "").replaceAll(str11, "");
                        try {
                            try {
                                if (this.jsonChayTrang.has(substring2)) {
                                    this.jsonChayTrang.put(substring2, this.jsonChayTrang.getInt(substring2) + Integer.parseInt(replaceAll));
                                } else {
                                    this.jsonChayTrang.put(substring2, replaceAll);
                                }
                                double d = i;
                                try {
                                    double parseDouble = Double.parseDouble(replaceAll) * this.jsonTienxien.getDouble(substring2);
                                    Double.isNaN(d);
                                    Double.isNaN(d);
                                    i = (int) (d + parseDouble);
                                    strArr = split;
                                } catch (Exception unused) {
                                    double parseDouble2 = Double.parseDouble(replaceAll);
                                    strArr = split;
                                    double d2 = this.Price + 20;
                                    Double.isNaN(d2);
                                    Double.isNaN(d);
                                    Double.isNaN(d2);
                                    Double.isNaN(d);
                                    i = (int) (d + (parseDouble2 * d2));
                                }
                                i2++;
                                split = strArr;
                                str10 = str11;
                            } catch (Exception e2) {
                                Util.writeLog(e2);
                                return "Không phân tích được nội dung!";
                            }
                        } catch (Exception e3) {
                            Util.writeLog(e3);
                            e3.printStackTrace();
                            return "Không phân tích được nội dung!";
                        }
                    } catch (Exception e4) {
                        e4.printStackTrace();
                        return "Không phân tích được nội dung!";
                    }
                }
                frag_ChaytrangNew = this;
            } catch (Exception e5) {
                e5.printStackTrace();
            }
        } else {
            try {
                String[] split2 = DanSo.substring(DanSo.indexOf(":")).split(" ");
                int i3 = 0;
                int i4 = 0;
                while (i3 < split2.length) {
                    try {
                        JSONObject jSONObject4 = jSONObject3;
                        String substring3 = split2[i3].substring(split2[i3].indexOf(":") + 1);
                        String str12 = str7;
                        String substring4 = substring3.substring(0, substring3.indexOf("x"));
                        String replaceAll2 = split2[i3].substring(split2[i3].indexOf("x")).replaceAll("x", "").replaceAll("n", "").replaceAll("d", "").replaceAll(str8, "");
                        String[] split3 = substring4.split(str9);
                        int length = split3.length;
                        String[] strArr2 = split2;
                        String str13 = str9;
                        int i5 = i4;
                        int i6 = 0;
                        while (i6 < length) {
                            int i7 = length;
                            try {
                                String str14 = split3[i6];
                                String[] strArr3 = split3;
                                if (frag_ChaytrangNew2.jsonChayTrang.has(str14)) {
                                    cursor3 = GetData;
                                    frag_ChaytrangNew2.jsonChayTrang.put(str14, frag_ChaytrangNew2.jsonChayTrang.getInt(str14) + Integer.parseInt(replaceAll2));
                                } else {
                                    cursor3 = GetData;
                                    frag_ChaytrangNew2.jsonChayTrang.put(str14, replaceAll2);
                                }
                                if (frag_ChaytrangNew2.jsonGia.has(str14)) {
                                    double d3 = i5;
                                    double parseDouble3 = Double.parseDouble(replaceAll2);
                                    str4 = str8;
                                    double d4 = frag_ChaytrangNew2.jsonGia.getInt(str14) + frag_ChaytrangNew2.Price;
                                    Double.isNaN(d4);
                                    Double.isNaN(d3);
                                    Double.isNaN(d4);
                                    Double.isNaN(d3);
                                    int i8 = (int) (d3 + (parseDouble3 * d4));
                                    str5 = replaceAll2;
                                    i5 = i8;
                                } else {
                                    str4 = str8;
                                    double d5 = i5;
                                    double parseDouble4 = Double.parseDouble(replaceAll2);
                                    str5 = replaceAll2;
                                    double d6 = frag_ChaytrangNew2.Price;
                                    Double.isNaN(d6);
                                    Double.isNaN(d5);
                                    Double.isNaN(d6);
                                    Double.isNaN(d5);
                                    i5 = (int) (d5 + (parseDouble4 * d6));
                                }
                                i6++;
                                frag_ChaytrangNew2 = this;
                                length = i7;
                                split3 = strArr3;
                                GetData = cursor3;
                                replaceAll2 = str5;
                                str8 = str4;
                            } catch (Exception e6) {
                                Util.writeLog(e6);
                                e6.printStackTrace();
                                return "Không phân tích được nội dung!";
                            }
                        }
                        i3++;
                        frag_ChaytrangNew2 = this;
                        split2 = strArr2;
                        i4 = i5;
                        jSONObject3 = jSONObject4;
                        str7 = str12;
                        str9 = str13;
                        GetData = GetData;
                        str8 = str8;
                    } catch (Exception e7) {
                        Util.writeLog(e7);
                        e7.printStackTrace();
                        return "Không phân tích được nội dung!";
                    }
                }
                str2 = str7;
                jSONObject = jSONObject3;
                cursor2 = GetData;
                str3 = str9;
                frag_ChaytrangNew = this;
                i = i4;
            } catch (Exception e8) {
                Util.writeLog(e8);
                e8.printStackTrace();
                return "Không phân tích được nội dung!";
            }
        }
        if (!cursor2.isClosed()) {
            cursor2.close();
        }
        if (i > myBalance) {
            return "Vượt quá số tiền còn lại";
        }
        Iterator<String> keys = jsonChayTrang.keys();
        String str15 = "";
        while (keys.hasNext()) {
            String next = keys.next();
            try {
                if (myMax.contains(".")) {
                    try {
                        parseInt = Integer.parseInt(myMax.replaceAll("\\.", ""));
                        jSONObject2 = jSONObject;
                        str6 = str3;
                    } catch (Exception e9) {
                        Util.writeLog(e9);
                        e9.printStackTrace();
                        return str2;
                    }
                } else {
                    str6 = str3;
                    parseInt = Integer.parseInt(myMax.replaceAll(str6, ""));
                    jSONObject2 = jSONObject;
                }
                try {
                    if (jSONObject2.has(next)) {
                        if (jsonChayTrang.getDouble(next) + jSONObject2.getDouble(next) > parseInt) {
                            try {
                                str15 = str15 + next + " ";
                            } catch (Exception e10) {
                                e10.printStackTrace();
                                return str2;
                            }
                        } else {
                            continue;
                        }
                    } else if (jsonChayTrang.getDouble(next) > parseInt) {
                        str15 = str15 + next + " ";
                    }
                    str3 = str6;
                    jSONObject = jSONObject2;
                } catch (Exception e11) {
                    Util.writeLog(e11);
                    e11.printStackTrace();
                    return str2;
                }
            } catch (Exception e12) {
                Util.writeLog(e12);
                e12.printStackTrace();
                return str2;
            }
        }
        if (str15.length() <= 0) {
            return "";
        }
        return "Các cặp: " + str15 + " vượt quá max của trang";
    }

    public String Laygia() {
        try {
            this.jsonGia = new JSONObject();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            simpleDateFormat.setTimeZone(TimeZone.getDefault());
            final String format = simpleDateFormat.format(calendar.getTime());
            final String[] strArr = {""};
            final OkHttpClient okHttpClient = new OkHttpClient();
            if (MainActivity.MyToken.length() > 0 && Build.VERSION.SDK_INT >= 24) {
                CompletableFuture.runAsync(new Runnable() { // from class: tamhoang.ldpro4.Fragment.25
                    @Override // java.lang.Runnable
                    public final void run() {
                        lambda$Laygia$3$Frag_ChaytrangNew(format, okHttpClient, strArr);
                    }
                });
            }
            return strArr[0];
        } catch (Exception e) {
            Util.writeLog(e);
            return "";
        }
    }

    @Override // tamhoang.ldpro4.Fragment.Frag_Chaytrang
    public String TaoTinDe() throws JSONException {
        int parseInt;
        String str = "";
        JSONObject jSONObject = new JSONObject();
        ArrayList arrayList = new ArrayList();
        try {
            parseInt = Integer.parseInt(this.myMax.replace(".", ""));
        } catch (Exception e) {
            Util.writeLog(e);
            parseInt = Integer.parseInt(this.myMax.replace(",", ""));
        }
        if (this.edt_tien.getText().toString().length() != 0) {
            if (Integer.parseInt(this.edt_tien.getText().toString()) > parseInt) {
                return "Số tiền vượt quá max ";
            }
            parseInt = Integer.parseInt(this.edt_tien.getText().toString());
        }
        int i = 0;
        int i2 = 0;
        while (i < this.mSo.size()) {
            try {
                str = this.mSo.get(i);
            } catch (Exception e2) {
                Util.writeLog(e2);
                e2.printStackTrace();
            }
            if (i2 >= 50) {
                break;
            }
            if (this.jsonGia.has(str)) {
                if (this.jsonGia.getInt(str) + this.Price <= this.MaxChay && i < this.mTienTon.size()) {
                    int parseInt2 = Integer.parseInt(this.mTienTon.get(i).replace(".", ""));
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.put("So_chon", str);
                    jSONObject2.put("Da_chuyen", jSONObject.has(str) ? jSONObject.getJSONObject(str).getInt("Da_chuyen") + parseInt2 : 0);
                    if (jSONObject2.getInt("Da_chuyen") + parseInt2 > parseInt) {
                        parseInt2 = parseInt - jSONObject2.getInt("Da_chuyen");
                    }
                    jSONObject2.put("Se_chuyen", parseInt2);
                    if (jSONObject2.getInt("Se_chuyen") > 0) {
                        arrayList.add(jSONObject2);
                        i2++;
                    }
                }
            } else if (this.Price <= this.MaxChay && i < this.mTienTon.size()) {
                int parseInt3 = Integer.parseInt(this.mTienTon.get(i).replace(".", ""));
                JSONObject jSONObject3 = new JSONObject();
                jSONObject3.put("So_chon", str);
                jSONObject3.put("Da_chuyen", jSONObject.has(str) ? jSONObject.getJSONObject(str).getInt("Da_chuyen") + parseInt3 : 0);
                if (jSONObject3.getInt("Da_chuyen") + parseInt3 > parseInt) {
                    parseInt3 = parseInt - jSONObject3.getInt("Da_chuyen");
                }
                jSONObject3.put("Se_chuyen", parseInt3);
                if (jSONObject3.getInt("Se_chuyen") > 0) {
                    arrayList.add(jSONObject3);
                    i2++;
                }
            }
            i++;
        }
        Collections.sort(arrayList, new Comparator<JSONObject>() { // from class: tamhoang.ldpro4.Fragment.15
            @Override // java.util.Comparator
            public int compare(JSONObject a, JSONObject b) {
                int i3 = 0;
                Integer num = 0;
                try {
                    i3 = Integer.valueOf(a.getInt("Se_chuyen")).intValue();
                    num = Integer.valueOf(b.getInt("Se_chuyen"));
                } catch (Exception e3) {
                    Util.writeLog(e3);
                }
                return num.compareTo(Integer.valueOf(i3));
            }
        });
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        while (i3 < arrayList.size()) {
            try {
                JSONObject jSONObject4 = (JSONObject) arrayList.get(i3);
                int i6 = jSONObject4.getInt("Se_chuyen");
                if (i6 > 0) {
                    if (i5 > i6) {
                        this.xuatDan += "x" + i5 + this.donvi;
                        this.xuatDan += jSONObject4.getString("So_chon") + ",";
                        i4 = 0;
                    } else {
                        this.xuatDan += jSONObject4.getString("So_chon") + ",";
                    }
                    i4++;
                    i5 = i6;
                }
                i3++;
            } catch (Exception e3) {
                Util.writeLog(e3);
                e3.printStackTrace();
            }
        }
        if (this.xuatDan.length() > 4 && i4 > 0) {
            this.xuatDan += "x" + i5 + this.donvi;
        }
        return i4 > 0 ? this.xuatDan : "";
    }

    @Override // tamhoang.ldpro4.Fragment.Frag_Chaytrang
    public String TaoTinXi() {
        JSONObject jSONObject = new JSONObject();
        ArrayList arrayList = new ArrayList();
        int parseInt = Integer.parseInt(this.myMax.replace(".", ""));
        try {
            if (this.edt_tien.getText().toString().trim().length() > 0 && Congthuc.isNumeric(this.edt_tien.getText().toString().trim())) {
                if (Integer.parseInt(this.edt_tien.getText().toString()) > parseInt) {
                    return "Số tiền vượt quá max ";
                }
                parseInt = Integer.parseInt(this.edt_tien.getText().toString());
            }
            int i = 0;
            for (int i2 = 0; i2 < this.mSo.size() && i < 50; i2++) {
                String str = this.mSo.get(i2);
                int parseInt2 = Integer.parseInt(this.mTienTon.get(i2).replace(".", ""));
                if (parseInt2 > 0 && Integer.parseInt(this.mGia.get(i2)) <= this.MaxChay) {
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.put("So_chon", str);
                    jSONObject2.put("Da_chuyen", jSONObject.has(str) ? jSONObject.getJSONObject(str).getInt("Da_chuyen") + parseInt2 : 0);
                    if (jSONObject2.getInt("Da_chuyen") + parseInt2 > parseInt) {
                        parseInt2 = parseInt - jSONObject2.getInt("Da_chuyen");
                    }
                    jSONObject2.put("Se_chuyen", parseInt2);
                    if (jSONObject2.getInt("Se_chuyen") > 0) {
                        arrayList.add(jSONObject2);
                        i++;
                    }
                }
            }
            Collections.sort(arrayList, new Comparator<JSONObject>() { // from class: tamhoang.ldpro4.Fragment.16
                @Override // java.util.Comparator
                public int compare(JSONObject a, JSONObject b) {
                    int i3 = 0;
                    Integer num = 0;
                    try {
                        i3 = Integer.valueOf(a.getInt("Se_chuyen")).intValue();
                        num = Integer.valueOf(b.getInt("Se_chuyen"));
                    } catch (Exception e) {
                        Util.writeLog(e);
                    }
                    return num.compareTo(Integer.valueOf(i3));
                }
            });
            for (int i3 = 0; i3 < arrayList.size(); i3++) {
                this.xuatDan += ((JSONObject) arrayList.get(i3)).getString("So_chon") + "x" + ((JSONObject) arrayList.get(i3)).getString("Se_chuyen") + this.donvi;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e2) {
            Util.writeLog(e2);
        }
        return this.xuatDan.length() > 5 ? this.xuatDan : "";
    }

    public boolean checkStopedNumbers(TextView edt_XuatErr, Button btn_chuyendi, String contentData) {
        Iterator<String> keys = this.jsonStoped.keys();
        ArrayList arrayList = new ArrayList();
        while (keys.hasNext()) {
            String next = keys.next();
            if (contentData.indexOf(next) > -1) {
                arrayList.add(next);
            }
        }
        if (arrayList.size() <= 0) {
            return false;
        }
        edt_XuatErr.setText("One789: Stoped [" + Frag_ChaytrangNew$$ExternalSynthetic0.m0(", ", arrayList) + "]");
        edt_XuatErr.setVisibility(View.VISIBLE);
        btn_chuyendi.setEnabled(true);
        return true;
    }

    public void lambda$Dialog$0$Frag_ChaytrangNew(OkHttpClient okHttpClient, final TextView taikhoan, final TextView CreditLimit, final TextView Balance) {
        try {
            if (MainActivity.MyToken.length() > 0) {
                ResponseBody body = okHttpClient.newCall(new Request.Builder().header("Authorization", "Bearer " + MainActivity.MyToken).url("https://id.lotusapi.com/wallets/player/my-wallet").get().build()).execute().body();
                if (body != null) {
                    final JSONObject jSONObject = new JSONObject(body.string());
                    if (!jSONObject.has("message")) {
                        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: tamhoang.ldpro4.Fragment.19
                            @Override // java.lang.Runnable
                            public void run() {
                                try {
                                    DecimalFormat decimalFormat = new DecimalFormat("###,###");
                                    taikhoan.setText(mwebsite.get(spin_pointion));
                                    CreditLimit.setText(decimalFormat.format(jSONObject.getDouble("CreditLimit")));
                                    Balance.setText(decimalFormat.format(jSONObject.getDouble("Balance")));
                                    myBalance = jSONObject.getDouble("Balance");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    } else if (jSONObject.getString("message").indexOf("Unauthorized") > -1) {
                        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: tamhoang.ldpro4.Fragment.20
                            @Override // java.lang.Runnable
                            public void run() {
                                Toast.makeText(getActivity(), "Tài khoản đăng nhập lỗi!", android.widget.Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public void lambda$Dialog2$1$Frag_ChaytrangNew(OkHttpClient okHttpClient, DecimalFormat decimalFormat, final ListView lv_cacmachay) {
        try {
            ResponseBody body = okHttpClient.newCall(new Request.Builder().header("Authorization", "Bearer " + MainActivity.MyToken).url("https://lotto.lotusapi.com/game-play/player/tickets/current?limit=100").get().build()).execute().body();
            if (body != null) {
                JSONArray jSONArray = new JSONArray(body.string());
                for (int i = 0; i < jSONArray.length(); i++) {
                    JSONObject jSONObject = jSONArray.getJSONObject(i);
                    this.SoTin.add(jSONObject.getString("TicketNumber"));
                    this.TheLoai.add(Integer.valueOf(jSONObject.getInt("BetType")));
                    this.NoiDung.add(jSONObject.getString("Numbers"));
                    String[] split = jSONObject.getString("CreatedAt").substring(11).substring(0, 8).split(":");
                    split[0] = (Integer.parseInt(split[0]) + 7) + "";
                    this.ThoiGian.add(split[0] + ":" + split[1] + ":" + split[2]);
                    this.TienCuoc.add(decimalFormat.format(jSONObject.getLong("Amount")));
                    if (jSONObject.has("CancelledAt")) {
                        this.HuyCuoc.add(0);
                    } else {
                        this.HuyCuoc.add(1);
                    }
                }
                new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: tamhoang.ldpro4.Fragment.22
                    @Override // java.lang.Runnable
                    public void run() {
                        lv_cacmachay.setAdapter((ListAdapter) new Ma_da_chay(getActivity(), R.layout.frag_chaytrang_tinchay_lv, NoiDung));
                    }
                });
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    public void lambda$Laygia$3$Frag_ChaytrangNew(String mNgayNhan, OkHttpClient okHttpClient, String[] loi) {
        String str;
        ResponseBody body;
        ResponseBody body2;
        try {
            if (MainActivity.MyToken.length() <= 0) {
                return;
            }
            if (this.radio_lo.isChecked() && this.LoLive) {
                this.GameType = 20;
                str = "https://lotto.lotusapi.com/odds/player/live?term=" + mNgayNhan + "&gameType=0&betType=20";
                body = okHttpClient.newCall(new Request.Builder().header("Authorization", "Bearer " + MainActivity.MyToken).url(str).get().build()).execute().body();
                if (body != null) {
                    if (this.LoLive && this.radio_lo.isChecked()) {
                        JSONObject jSONObject = new JSONObject(body.string());
                        if (!jSONObject.getBoolean("Active")) {
                            new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: tamhoang.ldpro4.Fragment.27
                                @Override // java.lang.Runnable
                                public void run() {
                                    btn_Xuatso.setText("Trang đã đóng");
                                    btn_Xuatso.setEnabled(false);
                                    handler.removeCallbacks(runnable);
                                }
                            });
                        }
                        this.Price = jSONObject.getInt("Price");
                        JSONArray jSONArray = jSONObject.getJSONArray("Numbers");
                        this.jsonGia = new JSONObject();
                        for (int i = 0; i < jSONArray.length(); i++) {
                            JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                            if (jSONObject2.has("Stop") && jSONObject2.getBoolean("Stop")) {
                                this.jsonStoped.put(jSONObject2.getString("Number"), true);
                            } else if (jSONObject2.has("ExtraPrice")) {
                                this.jsonGia.put(jSONObject2.getString("Number"), jSONObject2.getString("ExtraPrice"));
                            }
                        }
                        if (this.Price != this.PriceLive) {
                            new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: tamhoang.ldpro4.Fragment.28
                                @Override // java.lang.Runnable
                                public void run() {
                                    xem_RecycView();
                                }
                            });
                            this.PriceLive = this.Price;
                        }
                    }
                    JSONObject jSONObject3 = new JSONArray(body.string()).getJSONObject(0);
                    this.Price = jSONObject3.getInt("Price");
                    this.PriceLive = 0;
                    JSONArray jSONArray2 = jSONObject3.getJSONArray("Numbers");
                    this.jsonGia = new JSONObject();
                    for (int i2 = 0; i2 < jSONArray2.length(); i2++) {
                        JSONObject jSONObject4 = jSONArray2.getJSONObject(i2);
                        if (jSONObject4.has("Stop") && jSONObject4.getBoolean("Stop")) {
                            this.jsonStoped.put(jSONObject4.getString("Number"), true);
                        } else if (jSONObject4.has("ExtraPrice")) {
                            this.jsonGia.put(jSONObject4.getString("Number"), jSONObject4.getString("ExtraPrice"));
                        }
                    }
                    new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: tamhoang.ldpro4.Fragment.26
                        @Override // java.lang.Runnable
                        public void run() {
                            xem_RecycView();
                        }
                    });
                }
                body2 = okHttpClient.newCall(new Request.Builder().header("Authorization", "Bearer " + MainActivity.MyToken).url("https://comm.lotusapi.com/servers/current-date-time").get().build()).execute().body();
                if (body2 == null) {
                    JSONObject jSONObject5 = new JSONObject(body2.string());
                    if (jSONObject5.has("Timestamp")) {
                        Curent_date_time = jSONObject5.getLong("Timestamp");
                        return;
                    }
                    return;
                }
                return;
            }
            str = "https://lotto.lotusapi.com/odds/player?term=" + mNgayNhan + "&gameTypes=0&betTypes=" + this.GameType;
            body = okHttpClient.newCall(new Request.Builder().header("Authorization", "Bearer " + MainActivity.MyToken).url(str).get().build()).execute().body();
            if (body != null) {
            }
            body2 = okHttpClient.newCall(new Request.Builder().header("Authorization", "Bearer " + MainActivity.MyToken).url("https://comm.lotusapi.com/servers/current-date-time").get().build()).execute().body();
            if (body2 == null) {
            }
        } catch (Exception e) {
            Util.writeLog(e);
            loi[0] = "Kết nối kém, kiểm tra lại Internet";
            e.printStackTrace();
        }
    }

    public void lambda$login$2$Frag_ChaytrangNew(JSONObject Json, String Username, String PassWord, AtomicReference str3, OkHttpClient okHttpClient) {
        try {
            Json.put("Username", Username);
            Json.put("Password", PassWord);
            str3.set(okHttpClient.newCall(new Request.Builder().url("https://id.lotusapi.com/auth/sign-in").header("Content-Type", "application/json").post(RequestBody.create(Json.toString(), MediaType.parse("application/json"))).build()).execute().body().string());
            JSONObject jSONObject = new JSONObject(str3.toString());
            if (!jSONObject.has("IdToken")) {
                new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: tamhoang.ldpro4.Fragment.24
                    @Override // java.lang.Runnable
                    public void run() {
                        Toast.makeText(getActivity(), "Đăng nhập thất bại.", android.widget.Toast.LENGTH_SHORT).show();
                        MainActivity.MyToken = "";
                    }
                });
            } else {
                MainActivity.MyToken = jSONObject.getString("IdToken");
                Laygia();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public void login(final String Username, final String PassWord) {
        final OkHttpClient okHttpClient = new OkHttpClient();
        final JSONObject jSONObject = new JSONObject();
        final AtomicReference atomicReference = new AtomicReference("");
        if (Build.VERSION.SDK_INT >= 24) {
            CompletableFuture.runAsync(new Runnable() { // from class: tamhoang.ldpro4.Fragment.23
                @Override // java.lang.Runnable
                public final void run() {
                    lambda$login$2$Frag_ChaytrangNew(jSONObject, Username, PassWord, atomicReference, okHttpClient);
                }
            });
        }
    }

    @Override // tamhoang.ldpro4.Fragment.Frag_Chaytrang, android.support.v4.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (this.db == null) {
            this.db = new Database(getActivity());
        }
        this.v = inflater.inflate(R.layout.frag_chaytrang, container, false);
        init();
        this.ToDay = MainActivity.Get_date();
        this.radio_de.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: tamhoang.ldpro4.Fragment.2
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (radio_de.isChecked()) {
                    li_loaide.setVisibility(View.VISIBLE);
                    try {
                        Cursor GetData = db.GetData("Select sum((the_loai = 'dea')* diem) as de_a\n,sum((the_loai = 'deb')* diem) as de_b\n,sum((the_loai = 'det')* diem) as de_t\n,sum((the_loai = 'dec')* diem) as de_c\n,sum((the_loai = 'ded')* diem) as de_d\nFrom tbl_soctS \nWhere ngay_nhan = '" + ToDay + "'");
                        if (GetData.moveToFirst() && GetData != null) {
                            int[] iArr = new int[5];
                            if (GetData.getDouble(0) > 0.0d) {
                                iArr[0] = 1;
                                radio_dea.setEnabled(true);
                            } else {
                                iArr[0] = 0;
                                radio_dea.setEnabled(false);
                            }
                            if (GetData.getDouble(1) > 0.0d) {
                                iArr[1] = 1;
                                radio_deb.setEnabled(true);
                            } else {
                                iArr[1] = 0;
                                radio_deb.setEnabled(false);
                            }
                            if (GetData.getDouble(2) > 0.0d) {
                                iArr[2] = 1;
                            } else {
                                iArr[2] = 0;
                            }
                            if (GetData.getDouble(3) > 0.0d) {
                                iArr[3] = 1;
                                radio_dec.setEnabled(true);
                            } else {
                                iArr[3] = 0;
                                radio_dec.setEnabled(false);
                            }
                            if (GetData.getDouble(4) > 0.0d) {
                                iArr[4] = 1;
                                radio_ded.setEnabled(true);
                            } else {
                                iArr[4] = 0;
                                radio_ded.setEnabled(false);
                            }
                            if (iArr[0] == 0 && ((iArr[1] == 1 || iArr[2] == 1) && iArr[3] == 0 && iArr[4] == 0)) {
                                DangXuat = "(the_loai = 'deb' or the_loai = 'det')";
                                li_loaixi.setVisibility(View.GONE);
                                li_loaide.setVisibility(View.GONE);
                                radio_deb.setChecked(true);
                                xem_RecycView();
                            } else if (iArr[0] == 0 && iArr[1] == 0 && iArr[2] == 0 && iArr[3] == 0 && iArr[4] == 0) {
                                DangXuat = "(the_loai = 'deb' or the_loai = 'det')";
                                li_loaixi.setVisibility(View.GONE);
                                li_loaide.setVisibility(View.GONE);
                                radio_deb.setChecked(true);
                                xem_RecycView();
                            } else {
                                DangXuat = "(the_loai = 'deb' or the_loai = 'det')";
                                li_loaixi.setVisibility(View.GONE);
                                li_loaide.setVisibility(View.VISIBLE);
                                radio_deb.setChecked(true);
                                xem_RecycView();
                            }
                            if (!GetData.isClosed() && GetData != null && !GetData.isClosed()) {
                                GetData.close();
                            }
                            GameType = 0;
                            if (MainActivity.MyToken.length() > 0) {
                                Laygia();
                                return;
                            }
                            return;
                        }
                        DangXuat = "(the_loai = 'deb' or the_loai = 'det')";
                        GameType = 0;
                        if (MainActivity.MyToken.length() > 0) {
                            Laygia();
                        }
                    } catch (SQLException e) {
                        Util.writeLog(e);
                        DangXuat = "(the_loai = 'deb' or the_loai = 'det')";
                        GameType = 0;
                        if (MainActivity.MyToken.length() > 0) {
                            Laygia();
                        }
                    }
                }
            }
        });
        this.radio_dea.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: tamhoang.ldpro4.Fragment.3
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (radio_dea.isChecked()) {
                    DangXuat = "the_loai = 'dea'";
                    li_loaixi.setVisibility(View.GONE);
                    GameType = 21;
                    Laygia();
                }
            }
        });
        try {
            this.mwebsite.clear();
            this.mpassword.clear();
            Cursor GetData = this.db.GetData("Select * From tbl_chaytrang_acc");
            if (GetData.getCount() > 0) {
                while (GetData.moveToNext()) {
                    this.mwebsite.add(GetData.getString(0));
                    this.mpassword.add(GetData.getString(1));
                }
                if (GetData != null) {
                    GetData.close();
                }
                this.spr_trang.setAdapter((SpinnerAdapter) new ArrayAdapter(getActivity(), R.layout.spinner_item, this.mwebsite));
                if (this.mwebsite.size() > 0) {
                    this.spr_trang.setSelection(0);
                    this.spin_pointion = 0;
                }
            }
            GetData.close();
        } catch (Exception e) {
            Util.writeLog(e);
            Toast.makeText(getActivity(), "Đang copy dữ liệu bản mới!", android.widget.Toast.LENGTH_LONG).show();
        }
        this.radio_deb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: tamhoang.ldpro4.Fragment.4
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (radio_deb.isChecked()) {
                    DangXuat = "(the_loai = 'deb' or the_loai = 'det')";
                    li_loaixi.setVisibility(View.GONE);
                    GameType = 0;
                    if (MainActivity.MyToken.length() > 0) {
                        Laygia();
                    }
                }
            }
        });
        this.radio_dec.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: tamhoang.ldpro4.Fragment.5
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (radio_dec.isChecked()) {
                    DangXuat = "the_loai = 'dec'";
                    li_loaixi.setVisibility(View.GONE);
                    GameType = 23;
                    if (MainActivity.MyToken.length() > 0) {
                        Laygia();
                    }
                }
            }
        });
        this.radio_ded.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: tamhoang.ldpro4.Fragment.6
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (radio_ded.isChecked()) {
                    DangXuat = "the_loai = 'ded'";
                    li_loaixi.setVisibility(View.GONE);
                    GameType = 22;
                    if (MainActivity.MyToken.length() > 0) {
                        Laygia();
                    }
                }
            }
        });
        this.radio_lo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: tamhoang.ldpro4.Fragment.7
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (radio_lo.isChecked()) {
                    DangXuat = "the_loai = 'lo'";
                    li_loaixi.setVisibility(View.GONE);
                    li_loaide.setVisibility(View.GONE);
                    if (LoLive) {
                        GameType = 20;
                    } else {
                        GameType = 1;
                    }
                    if (MainActivity.MyToken.length() > 0) {
                        Laygia();
                    }
                }
            }
        });
        this.radio_xi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: tamhoang.ldpro4.Fragment.8
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (radio_xi.isChecked()) {
                    DangXuat = "the_loai = 'xi'";
                    li_loaixi.setVisibility(View.VISIBLE);
                    li_loaide.setVisibility(View.GONE);
                    radio_xi2.setChecked(true);
                    GameType = 2;
                    if (MainActivity.MyToken.length() > 0) {
                        Laygia();
                    }
                }
            }
        });
        this.radio_xi2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: tamhoang.ldpro4.Fragment.9
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (radio_xi2.isChecked()) {
                    DangXuat = "the_loai = 'xi'";
                    li_loaixi.setVisibility(View.VISIBLE);
                    li_loaide.setVisibility(View.GONE);
                    lay_xien = " length(so_chon) = 5 ";
                    GameType = 2;
                    if (MainActivity.MyToken.length() > 0) {
                        Laygia();
                    }
                }
            }
        });
        this.radio_xi3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: tamhoang.ldpro4.Fragment.10
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (radio_xi3.isChecked()) {
                    DangXuat = "the_loai = 'xi'";
                    li_loaixi.setVisibility(View.VISIBLE);
                    li_loaide.setVisibility(View.GONE);
                    lay_xien = " length(so_chon) = 8 ";
                    GameType = 3;
                    if (MainActivity.MyToken.length() > 0) {
                        Laygia();
                    }
                }
            }
        });
        this.radio_xi4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: tamhoang.ldpro4.Fragment.11
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (radio_xi4.isChecked()) {
                    DangXuat = "the_loai = 'xi'";
                    li_loaixi.setVisibility(View.VISIBLE);
                    li_loaide.setVisibility(View.GONE);
                    lay_xien = " length(so_chon) = 11 ";
                    GameType = 4;
                    if (MainActivity.MyToken.length() > 0) {
                        Laygia();
                    }
                }
            }
        });
        this.spr_trang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: tamhoang.ldpro4.Fragment.12
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                spin_pointion = position;
                login(mwebsite.get(position), mpassword.get(position));
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        if (this.mwebsite.size() > 0) {
            login(this.mwebsite.get(this.spin_pointion), this.mpassword.get(this.spin_pointion));
        }
        this.btn_Xuatso.setOnClickListener(new View.OnClickListener() { // from class: tamhoang.ldpro4.Fragment.13
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (spin_pointion == -1) {
                    Toast.makeText(getActivity(), "Không có trang để xuất", Toast.LENGTH_SHORT).show();
                }
                xuatDan = "";
                if (MainActivity.MyToken.length() > 0) {
                    int i = GameType;
                    if (i == 0) {
                        the_loai = "deb";
                        xuatDan = "De:";
                        donvi = "n ";
                        Dieukien = "(the_loai = 'deb' or the_loai = 'det')";
                        try {
                            TaoTinDe();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else if (i == 1) {
                        the_loai = "lo";
                        xuatDan = "Lo:";
                        donvi = "d ";
                        Dieukien = "the_loai = 'lo'";
                        try {
                            TaoTinDe();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else if (i == 2) {
                        the_loai = "xi2";
                        xuatDan = "Xi:";
                        donvi = "n ";
                        Dieukien = "the_loai = 'xi' AND length(so_chon) = 5";
                        TaoTinXi();
                    } else if (i == 3) {
                        the_loai = "xi3";
                        xuatDan = "Xi:";
                        donvi = "n ";
                        Dieukien = "the_loai = 'xi' AND length(so_chon) = 8";
                        TaoTinXi();
                    } else if (i != 4) {
                        switch (i) {
                            case 20:
                                the_loai = "lo";
                                xuatDan = "Lo:";
                                donvi = "d ";
                                Dieukien = "the_loai = 'lo'";
                                try {
                                    TaoTinDe();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                break;
                            case 21:
                                the_loai = "dea";
                                xuatDan = "De dau:";
                                donvi = "n ";
                                Dieukien = "the_loai = 'dea'";
                                try {
                                    TaoTinDe();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                break;
                            case 22:
                                the_loai = "ded";
                                xuatDan = "De giai 1:";
                                donvi = "n ";
                                Dieukien = "the_loai = 'ded'";
                                try {
                                    TaoTinDe();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                break;
                            case 23:
                                the_loai = "dec";
                                xuatDan = "De dau giai 1:";
                                donvi = "n ";
                                Dieukien = "the_loai = 'dec'";
                                try {
                                    TaoTinDe();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                break;
                        }
                    } else {
                        the_loai = "xi4";
                        xuatDan = "Xi:";
                        donvi = "n ";
                        Dieukien = "the_loai = 'xi' AND length(so_chon) = 11";
                        TaoTinXi();
                    }
                    Dialog();
                }
            }
        });
        this.btn_MaXuat.setOnClickListener(view -> Dialog2());
        Handler handler = new Handler();
        this.handler = handler;
        handler.postDelayed(this.runnable, 1000L);
        xemlv();
        return this.v;
    }

    @Override
    public void onDestroy() {
        SQLiteDatabase sQLiteDatabase = this.database;
        if (sQLiteDatabase != null && sQLiteDatabase.isOpen()) {
            this.database.close();
        }
        super.onDestroy();
    }

    public void refresh() {
        try {
            if (this.db == null) {
                this.db = new Database(getActivity());
            }
            Cursor GetData = this.db.GetData("Select * From tbl_chaytrang_acc");
            if (GetData.getCount() > 0) {
                this.mwebsite.clear();
                this.mpassword.clear();
                while (GetData.moveToNext()) {
                    this.mwebsite.add(GetData.getString(0));
                    this.mpassword.add(GetData.getString(1));
                }
                if (GetData != null) {
                    GetData.close();
                }
                this.spr_trang.setAdapter(new ArrayAdapter(getActivity(), R.layout.spinner_item, this.mwebsite));
                if (this.mwebsite.size() > 0) {
                    this.spr_trang.setSelection(0);
                    this.spin_pointion = 0;
                }
            }
            GetData.close();
        } catch (Exception unused) {
        }
    }

    @Override
    public void xem_RecycView() {
        String str;
        String str2;
        String str3;
        String str4;
        String str5;
        String str6;
        String str7;
        String Get_date = MainActivity.Get_date();
        this.jsonTienxien = new JSONObject();
        this.mSo.clear();
        this.mTienNhan.clear();
        this.mTienOm.clear();
        this.mTienchuyen.clear();
        this.mTienTon.clear();
        this.mMax.clear();
        this.mGia.clear();
        this.mNhay.clear();
        String str8 = this.DangXuat;
        if (str8 == "(the_loai = 'deb' or the_loai = 'det')") {
            StringBuilder sb = new StringBuilder();
            sb.append("Select tbl_soctS.So_chon\n, Sum((tbl_soctS.type_kh = 1) * (100-tbl_soctS.diem_khachgiu)*diem_quydoi/100) as diem\n, so_om.Om_deB + sum(tbl_soctS.diem_dly_giu*tbl_soctS.diem_quydoi/100) as So_Om\n, Sum((tbl_soctS.type_kh =2)");
            if (this.spin_pointion > -1) {
                str7 = "*(tbl_soctS.ten_kh='" + this.mwebsite.get(this.spin_pointion) + "')";
            } else {
                str7 = "";
            }
            sb.append(str7);
            sb.append("* tbl_soctS.diem_quydoi) as chuyen\n, Sum((tbl_soctS.type_kh =1) * (100-tbl_soctS.diem_khachgiu-tbl_soctS.diem_dly_giu)*diem_quydoi/100) - Sum((tbl_soctS.type_kh =2) * tbl_soctS.diem_quydoi) - so_om.Om_deB as ton\n, so_nhay  From so_om Left Join tbl_soctS On tbl_soctS.so_chon = so_om.So\n Where tbl_soctS.ngay_nhan='");
            sb.append(Get_date);
            sb.append("' AND (tbl_soctS.the_loai='deb' OR tbl_soctS.the_loai='det') GROUP by so_om.So Order by ton DESC");
            str = sb.toString();
        } else if (str8 == "the_loai = 'lo'") {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Select tbl_soctS.So_chon\n, Sum((tbl_soctS.type_kh = 1) * (100-tbl_soctS.diem_khachgiu)*diem_quydoi/100) as diem\n, so_om.Om_Lo + sum(tbl_soctS.diem_dly_giu*tbl_soctS.diem_quydoi/100) as So_Om\n, Sum((tbl_soctS.type_kh =2)");
            if (this.spin_pointion > -1) {
                str6 = "*(tbl_soctS.ten_kh='" + this.mwebsite.get(this.spin_pointion) + "')";
            } else {
                str6 = "";
            }
            sb2.append(str6);
            sb2.append(" * tbl_soctS.diem_quydoi) as chuyen\n, Sum((tbl_soctS.type_kh =1) * (100-tbl_soctS.diem_khachgiu-tbl_soctS.diem_dly_giu)*diem_quydoi/100) - Sum((tbl_soctS.type_kh =2) * tbl_soctS.diem_quydoi) - so_om.Om_Lo as ton\n, so_nhay  From so_om Left Join tbl_soctS On tbl_soctS.so_chon = so_om.So \n Where tbl_soctS.ngay_nhan='");
            sb2.append(Get_date);
            sb2.append("' AND tbl_soctS.the_loai='lo' \n GROUP by so_om.So Order by ton DESC");
            str = sb2.toString();
        } else if (str8 == "the_loai = 'dea'") {
            StringBuilder sb3 = new StringBuilder();
            sb3.append("Select tbl_soctS.So_chon\n, Sum((tbl_soctS.type_kh = 1) * (100-tbl_soctS.diem_khachgiu)*diem_quydoi/100) as diem\n, so_om.Om_DeA + sum(tbl_soctS.diem_dly_giu*tbl_soctS.diem_quydoi/100) as So_Om\n, Sum((tbl_soctS.type_kh =2)");
            if (this.spin_pointion > -1) {
                str5 = "*(tbl_soctS.ten_kh='" + this.mwebsite.get(this.spin_pointion) + "')";
            } else {
                str5 = "";
            }
            sb3.append(str5);
            sb3.append(" * tbl_soctS.diem_quydoi) as chuyen\n, Sum((tbl_soctS.type_kh =1) * (100-tbl_soctS.diem_khachgiu-tbl_soctS.diem_dly_giu)*diem_quydoi/100) - Sum((tbl_soctS.type_kh =2) * tbl_soctS.diem_quydoi) - so_om.Om_DeA as ton\n, so_nhay  From so_om Left Join tbl_soctS On tbl_soctS.so_chon = so_om.So \n Where tbl_soctS.ngay_nhan='");
            sb3.append(Get_date);
            sb3.append("' AND tbl_soctS.the_loai='dea' GROUP by so_chon Order by ton DESC");
            str = sb3.toString();
        } else if (str8 == "the_loai = 'dec'") {
            StringBuilder sb4 = new StringBuilder();
            sb4.append("Select tbl_soctS.So_chon\n, Sum((tbl_soctS.type_kh = 1) * (100-tbl_soctS.diem_khachgiu)*diem_quydoi/100) as diem\n, so_om.Om_DeC + sum(tbl_soctS.diem_dly_giu*tbl_soctS.diem_quydoi/100) as So_Om\n, Sum((tbl_soctS.type_kh =2)");
            if (this.spin_pointion > -1) {
                str4 = "*(tbl_soctS.ten_kh='" + this.mwebsite.get(this.spin_pointion) + "')";
            } else {
                str4 = "";
            }
            sb4.append(str4);
            sb4.append(" * tbl_soctS.diem_quydoi) as chuyen\n, Sum((tbl_soctS.type_kh =1) * (100-tbl_soctS.diem_khachgiu-tbl_soctS.diem_dly_giu)*diem_quydoi/100) - Sum((tbl_soctS.type_kh =2) * tbl_soctS.diem_quydoi) - so_om.Om_DeC as ton\n, so_nhay  From so_om Left Join tbl_soctS On tbl_soctS.so_chon = so_om.So \n Where tbl_soctS.ngay_nhan='");
            sb4.append(Get_date);
            sb4.append("' AND tbl_soctS.the_loai='dec' GROUP by so_chon Order by ton DESC");
            str = sb4.toString();
        } else if (str8 == "the_loai = 'ded'") {
            StringBuilder sb5 = new StringBuilder();
            sb5.append("Select tbl_soctS.So_chon\n, Sum((tbl_soctS.type_kh = 1) * (100-tbl_soctS.diem_khachgiu)*diem_quydoi/100) as diem\n, so_om.Om_DeD + sum(tbl_soctS.diem_dly_giu*tbl_soctS.diem_quydoi/100) as So_Om\n, Sum((tbl_soctS.type_kh =2)");
            if (this.spin_pointion > -1) {
                str3 = "*(tbl_soctS.ten_kh='" + this.mwebsite.get(this.spin_pointion) + "')";
            } else {
                str3 = "";
            }
            sb5.append(str3);
            sb5.append(" * tbl_soctS.diem_quydoi) as chuyen\n, Sum((tbl_soctS.type_kh =1) * (100-tbl_soctS.diem_khachgiu-tbl_soctS.diem_dly_giu)*diem_quydoi/100) - Sum((tbl_soctS.type_kh =2) * tbl_soctS.diem_quydoi) - so_om.Om_DeD as ton\n, so_nhay  From so_om Left Join tbl_soctS On tbl_soctS.so_chon = so_om.So \n Where tbl_soctS.ngay_nhan='");
            sb5.append(Get_date);
            sb5.append("' AND tbl_soctS.the_loai='ded' GROUP by so_chon Order by ton DESC");
            str = sb5.toString();
        } else if (str8 == "the_loai = 'xi'") {
            Cursor GetData = this.db.GetData("Select * From So_om WHERE ID = 1");
            GetData.moveToFirst();
            StringBuilder sb6 = new StringBuilder();
            sb6.append("SELECT so_chon, sum((type_kh =1)*(100-diem_khachgiu)*diem_quydoi)/100 AS diem, ((length(so_chon) = 5) * ");
            sb6.append(GetData.getString(7));
            sb6.append(" +(length(so_chon) = 8) * ");
            sb6.append(GetData.getString(8));
            sb6.append(" +(length(so_chon) = 11) * ");
            sb6.append(GetData.getString(9));
            sb6.append(" + sum(diem_dly_giu*diem_quydoi/100)) AS Om, SUm((type_kh =2)");
            if (this.spin_pointion > -1) {
                str2 = "*(tbl_soctS.ten_kh='" + this.mwebsite.get(this.spin_pointion) + "')";
            } else {
                str2 = "";
            }
            sb6.append(str2);
            sb6.append(" *diem) as chuyen , SUm((type_kh =1)*(100-diem_khachgiu-diem_dly_giu)*diem_quydoi/100)-SUm((type_kh =2)*diem) -  ((length(so_chon) = 5) * ");
            sb6.append(GetData.getString(7));
            sb6.append(" +(length(so_chon) = 8) * ");
            sb6.append(GetData.getString(8));
            sb6.append(" +(length(so_chon) = 11) * ");
            sb6.append(GetData.getString(9));
            sb6.append(") AS ton, so_nhay   From tbl_soctS Where ngay_nhan='");
            sb6.append(Get_date);
            sb6.append("' AND the_loai='xi' AND");
            sb6.append(this.lay_xien);
            sb6.append("  GROUP by so_chon Order by ton DESC, diem DESC");
            str = sb6.toString();
            if (GetData != null && !GetData.isClosed()) {
                GetData.close();
            }
        } else {
            str = null;
        }
        Cursor GetData2 = this.db.GetData(str);
        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        if (this.spin_pointion > -1) {
            Cursor GetData3 = this.db.GetData("select * from tbl_chaytrang_acc Where Username = '" + this.mwebsite.get(this.spin_pointion) + "'");
            GetData3.moveToFirst();
            try {
                JSONObject jSONObject = new JSONObject(GetData3.getString(2));
                int i = this.GameType;
                if (i == 0) {
                    this.myMax = decimalFormat.format(jSONObject.getInt("max_deb"));
                    this.MaxChay = jSONObject.getInt("gia_deb");
                } else if (i == 1) {
                    this.myMax = decimalFormat.format(jSONObject.getInt("max_lo"));
                    this.MaxChay = jSONObject.getInt("gia_lo");
                } else if (i == 2) {
                    this.myMax = decimalFormat.format(jSONObject.getInt("max_xi2"));
                    this.MaxChay = jSONObject.has("gia_xi2") ? jSONObject.getInt("gia_xi2") : 560;
                } else if (i == 3) {
                    this.myMax = decimalFormat.format(jSONObject.getInt("max_xi3"));
                    this.MaxChay = jSONObject.has("gia_xi3") ? jSONObject.getInt("gia_xi3") : 520;
                } else if (i != 4) {
                    switch (i) {
                        case 20:
                            this.myMax = decimalFormat.format(jSONObject.getInt("max_lo"));
                            this.MaxChay = jSONObject.getInt("gia_lo");
                            break;
                        case 21:
                            this.myMax = decimalFormat.format(jSONObject.getInt("max_dea"));
                            this.MaxChay = jSONObject.getInt("gia_dea");
                            break;
                        case 22:
                            this.myMax = decimalFormat.format(jSONObject.getInt("max_ded"));
                            this.MaxChay = jSONObject.getInt("gia_ded");
                            break;
                        case 23:
                            this.myMax = decimalFormat.format(jSONObject.getInt("max_dec"));
                            this.MaxChay = jSONObject.getInt("gia_dec");
                            break;
                    }
                } else {
                    this.myMax = decimalFormat.format(jSONObject.getInt("max_xi4"));
                    this.MaxChay = jSONObject.has("gia_xi4") ? jSONObject.getInt("gia_xi4") : 450;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Throwable th) {
                GetData3.close();
                throw th;
            }
            GetData3.close();
        } else {
            this.myMax = "0";
        }
        if (GetData2 != null) {
            if (this.radio_xi.isChecked()) {
                while (GetData2.moveToNext()) {
                    try {
                        this.mSo.add(GetData2.getString(0));
                        this.mTienNhan.add(decimalFormat.format(GetData2.getInt(1)));
                        this.mTienOm.add(decimalFormat.format(GetData2.getInt(2)));
                        this.mTienchuyen.add(decimalFormat.format(GetData2.getInt(3)));
                        this.mTienTon.add(decimalFormat.format(GetData2.getInt(4)));
                        this.mNhay.add(Integer.valueOf(GetData2.getInt(5)));
                        this.mMax.add(this.myMax);
                        if (this.radio_xi2.isChecked()) {
                            String[] split = GetData2.getString(0).split(",");
                            if (split.length >= 2) {
                                int i2 = !this.jsonGia.has(split[0]) ? this.Price : this.Price + this.jsonGia.getInt(split[0]);
                                int i3 = !this.jsonGia.has(split[1]) ? this.Price : this.jsonGia.getInt(split[1]) + this.Price;
                                List<String> list = this.mGia;
                                StringBuilder sb7 = new StringBuilder();
                                sb7.append("");
                                int i4 = i2 + i3;
                                sb7.append(i4 / 2);
                                list.add(sb7.toString());
                                this.jsonTienxien.put(GetData2.getString(0), i4 / 2);
                            }
                        }
                        if (this.radio_xi3.isChecked()) {
                            String[] split2 = GetData2.getString(0).split(",");
                            if (split2.length >= 3) {
                                int i5 = !this.jsonGia.has(split2[0]) ? this.Price : this.Price + this.jsonGia.getInt(split2[0]);
                                int i6 = !this.jsonGia.has(split2[1]) ? this.Price : this.Price + this.jsonGia.getInt(split2[1]);
                                int i7 = !this.jsonGia.has(split2[2]) ? this.Price : this.jsonGia.getInt(split2[2]) + this.Price;
                                List<String> list2 = this.mGia;
                                StringBuilder sb8 = new StringBuilder();
                                sb8.append("");
                                int i8 = i5 + i6 + i7;
                                sb8.append(i8 / 3);
                                list2.add(sb8.toString());
                                this.jsonTienxien.put(GetData2.getString(0), i8 / 3);
                            }
                        }
                        if (this.radio_xi4.isChecked()) {
                            String[] split3 = GetData2.getString(0).split(",");
                            if (split3.length >= 4) {
                                int i9 = !this.jsonGia.has(split3[0]) ? this.Price : this.Price + this.jsonGia.getInt(split3[0]);
                                int i10 = !this.jsonGia.has(split3[1]) ? this.Price : this.Price + this.jsonGia.getInt(split3[1]);
                                int i11 = !this.jsonGia.has(split3[2]) ? this.Price : this.Price + this.jsonGia.getInt(split3[2]);
                                int i12 = !this.jsonGia.has(split3[3]) ? this.Price : this.jsonGia.getInt(split3[3]) + this.Price;
                                List<String> list3 = this.mGia;
                                StringBuilder sb9 = new StringBuilder();
                                sb9.append("");
                                int i13 = i9 + i10 + i11 + i12;
                                sb9.append(i13 / 4);
                                list3.add(sb9.toString());
                                this.jsonTienxien.put(GetData2.getString(0), i13 / 4);
                            }
                        }
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                    }
                }
            } else {
                while (GetData2.moveToNext()) {
                    this.mSo.add(GetData2.getString(0));
                    this.mTienNhan.add(decimalFormat.format(GetData2.getInt(1)));
                    this.mTienOm.add(decimalFormat.format(GetData2.getInt(2)));
                    this.mTienchuyen.add(decimalFormat.format(GetData2.getInt(3)));
                    this.mTienTon.add(decimalFormat.format(GetData2.getInt(4)));
                    this.mNhay.add(Integer.valueOf(GetData2.getInt(5)));
                    if (this.jsonGia.has(GetData2.getString(0))) {
                        try {
                            this.mGia.add("" + (this.Price + this.jsonGia.getInt(GetData2.getString(0))));
                        } catch (JSONException e3) {
                            e3.printStackTrace();
                        }
                    } else {
                        this.mGia.add(this.Price + "");
                    }
                    this.mMax.add(this.myMax);
                }
            }
            if (GetData2 != null && !GetData2.isClosed()) {
                GetData2.close();
            }
        }
        if (getActivity() != null) {
            this.lview.setAdapter((ListAdapter) new So_OmAdapter(getActivity(), R.layout.frag_canchuyen_lv, this.mSo));
        }
    }

    @Override // tamhoang.ldpro4.Fragment.Frag_Chaytrang
    public void xemlv() {
        if (this.DangXuat != null) {
            xem_RecycView();
        } else {
            this.radio_de.setChecked(true);
        }
    }
}
