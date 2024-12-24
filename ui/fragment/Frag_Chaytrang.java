package tamhoang.ldpro4.ui.fragment;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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

import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.helper.HttpConnection;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
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
import tamhoang.ldpro4.formula.Congthuc;
import tamhoang.ldpro4.ui.home.MainActivity;
import tamhoang.ldpro4.R;
import tamhoang.ldpro4.data.Database;


public class Frag_Chaytrang extends Fragment {
    static long Curent_date_time;
    int MaxChay;
    Button btn_MaXuat;
    Button btn_Xuatso;
    Database db;
    EditText edt_tien;
    Handler handler;
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
    String xuatDan = "De:";
    String Dieukien = "(the_loai = 'deb' or the_loai = 'det')";
    String donvi = "n ";
    double myBalance = 0.0d;
    int GameType = 0;
    int Price = 0;
    int PriceLive = 0;
    String ToDay = "";
    String DangXuat = null;
    String myMax = "";
    String the_loai = "deb";
    boolean LoLive = false;
    public List<String> mSo = new ArrayList();
    public List<String> mTienNhan = new ArrayList();
    public List<String> mTienOm = new ArrayList();
    public List<String> mTienchuyen = new ArrayList();
    public List<String> mTienTon = new ArrayList();
    public List<Integer> mNhay = new ArrayList();
    public List<String> mGia = new ArrayList();
    public List<String> mMax = new ArrayList();
    public List<String> mwebsite = new ArrayList();
    public List<String> mpassword = new ArrayList();
    public List<String> SoTin = new ArrayList();
    public List<Integer> TheLoai = new ArrayList();
    public List<String> NoiDung = new ArrayList();
    public List<String> ThoiGian = new ArrayList();
    public List<String> TienCuoc = new ArrayList();
    public List<Integer> HuyCuoc = new ArrayList();
    int spin_pointion = -1;
    String lay_xien = " length(so_chon) = 5 ";
    JSONObject jsonGia = new JSONObject();
    JSONObject jsonChayTrang = new JSONObject();
    JSONArray jsonArray = new JSONArray();
    JSONObject jsonTienxien = new JSONObject();
    int Dem = 0;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            boolean running = false;
            if (Frag_Chaytrang.Curent_date_time > 0) {
                DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
                Date gioBatdau = Frag_Chaytrang.parseDate("01:00");
                Date gioLoxien = Frag_Chaytrang.parseDate("18:14");
                Date gioKetthuc = Frag_Chaytrang.parseDate("18:28");
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(Frag_Chaytrang.Curent_date_time * 1000);
                int hour = calendar.get(11);
                int minute = calendar.get(12);
                Date date = Frag_Chaytrang.parseDate(hour + ":" + minute);
                if (date.after(gioLoxien) && date.before(gioKetthuc) && !Frag_Chaytrang.this.LoLive) {
                    Frag_Chaytrang.this.radio_xi.setEnabled(false);
                    Frag_Chaytrang.this.radio_lo.setText("Lô Live");
                    Frag_Chaytrang.this.LoLive = true;
                } else if (date.after(gioKetthuc)) {
                    Frag_Chaytrang.this.handler.removeCallbacks(Frag_Chaytrang.this.runnable);
                    running = false;
                    Frag_Chaytrang.this.btn_Xuatso.setEnabled(false);
                    Frag_Chaytrang.this.btn_Xuatso.setText("Hết giờ");
                    Frag_Chaytrang.this.btn_Xuatso.setTextColor(-7829368);
                } else if (date.before(gioBatdau)) {
                    Frag_Chaytrang.this.btn_Xuatso.setEnabled(false);
                    Frag_Chaytrang.this.btn_Xuatso.setText("Chưa mở");
                    Frag_Chaytrang.this.btn_Xuatso.setTextColor(-7829368);
                    running = false;
                }
                if (Frag_Chaytrang.this.LoLive && Frag_Chaytrang.this.radio_lo.isChecked()) {
                    Frag_Chaytrang.this.Dem++;
                    if (Frag_Chaytrang.this.Dem >= 4) {
                        Frag_Chaytrang.this.Dem = 0;
                        Frag_Chaytrang.this.Laygia();
                    }
                }
                if (running) {
                    Frag_Chaytrang.this.btn_Xuatso.setText("Chạy trang (" + formatter.format(calendar.getTime()) + ")");
                }
            } else {
                Frag_Chaytrang.Curent_date_time = new Timestamp(System.currentTimeMillis()).getTime() / 1000;
            }
            if (running) {
                Frag_Chaytrang.Curent_date_time++;
                Frag_Chaytrang.this.handler.postDelayed(this, 1000L);
            }

        }
    };

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

        public Ma_da_chay(Context context, int i, List<String> list) {
            super(context, i, list);
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            TextView textView;
            StringBuilder sb;
            String str;
            View inflate = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.frag_chaytrang_tinchay_lv, (ViewGroup) null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.tv_SoTin = inflate.findViewById(R.id.tv_SoTin);
            viewHolder.tv_NoiDung = inflate.findViewById(R.id.tv_NoiDung);
            viewHolder.tv_ThoiGian = inflate.findViewById(R.id.tv_ThoiGan);
            viewHolder.tv_TienCuoc = inflate.findViewById(R.id.tv_TienCuoc);
            viewHolder.tv_HuyCuoc = inflate.findViewById(R.id.tv_HuyCuoc);
            viewHolder.tv_HuyCuoc.setFocusable(false);
            viewHolder.tv_HuyCuoc.setFocusableInTouchMode(false);
            viewHolder.tv_HuyCuoc.setOnClickListener(new View.OnClickListener() { // from class: tamhoang.ldpro4.Fragment.Frag_Chaytrang.Ma_da_chay.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                }
            });
            if (TheLoai.get(i).intValue() == 0) {
                textView = viewHolder.tv_NoiDung;
                sb = new StringBuilder();
                str = "Đề: ";
            } else if (TheLoai.get(i).intValue() == 1) {
                textView = viewHolder.tv_NoiDung;
                sb = new StringBuilder();
                str = "Lô: ";
            } else if (TheLoai.get(i).intValue() == 2) {
                textView = viewHolder.tv_NoiDung;
                sb = new StringBuilder();
                str = "Xiên 2: ";
            } else if (TheLoai.get(i).intValue() == 3) {
                textView = viewHolder.tv_NoiDung;
                sb = new StringBuilder();
                str = "Xiên 3: ";
            } else if (TheLoai.get(i).intValue() == 4) {
                textView = viewHolder.tv_NoiDung;
                sb = new StringBuilder();
                str = "Xiên 4: ";
            } else if (TheLoai.get(i).intValue() == 20) {
                textView = viewHolder.tv_NoiDung;
                sb = new StringBuilder();
                str = "Lô Live: ";
            } else if (TheLoai.get(i).intValue() == 21) {
                textView = viewHolder.tv_NoiDung;
                sb = new StringBuilder();
                str = "Đề đầu: ";
            } else {
                if (TheLoai.get(i).intValue() != 22) {
                    if (TheLoai.get(i).intValue() == 23) {
                        textView = viewHolder.tv_NoiDung;
                        sb = new StringBuilder();
                        str = "Đầu giải nhất: ";
                    }
                    viewHolder.tv_ThoiGian.setText("Time: " + ThoiGian.get(i));
                    viewHolder.tv_TienCuoc.setText("Tổng: " + TienCuoc.get(i));
                    viewHolder.tv_SoTin.setText(SoTin.get(i));
                    if (HuyCuoc.get(i).intValue() != 0) {
                        viewHolder.tv_HuyCuoc.setTextColor(-7829368);
                        viewHolder.tv_HuyCuoc.setEnabled(false);
                        viewHolder.tv_HuyCuoc.setText("Đã huỷ");
                    } else {
                        viewHolder.tv_HuyCuoc.setVisibility(View.GONE);
                    }
                    return inflate;
                }
                textView = viewHolder.tv_NoiDung;
                sb = new StringBuilder();
                str = "Giải nhất: ";
            }
            sb.append(str);
            sb.append(NoiDung.get(i));
            textView.setText(sb.toString());
            viewHolder.tv_ThoiGian.setText("Time: " + ThoiGian.get(i));
            viewHolder.tv_TienCuoc.setText("Tổng: " + TienCuoc.get(i));
            viewHolder.tv_SoTin.setText(SoTin.get(i));
            if (HuyCuoc.get(i).intValue() != 0) {
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

        public So_OmAdapter(Context context, int i, List<String> list) {
            super(context, i, list);
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            StringBuilder sb = null;
            String str = "";
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ViewHolder viewHolder = new ViewHolder();
            if (view == null) {
                view = layoutInflater.inflate(R.layout.frag_canchuyen, (ViewGroup) null);
                // TODO: 7/4/2024 Xoa tview2 vi khong biet la gi
//                viewHolder.tview2 = view.findViewById(R.id.spread);
                viewHolder.tview5 = view.findViewById(R.id.Tv_so);
                viewHolder.tview7 = view.findViewById(R.id.tv_diemBc1);
                viewHolder.tview8 = view.findViewById(R.id.tv_diemBca);
                viewHolder.tview1 = view.findViewById(R.id.tv_anxi);
                viewHolder.tview4 = view.findViewById(R.id.tv_diemChuyen);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            if (mNhay.get(i).intValue() > 0) {

                if (mNhay.get(i).intValue() == 1) {
                    sb = new StringBuilder();
                    sb.append(mSo.get(i));
                    str = "*";
                } else if (mNhay.get(i).intValue() == 2) {
                    sb = new StringBuilder();
                    sb.append(mSo.get(i));
                    str = "**";
                } else if (mNhay.get(i).intValue() == 3) {
                    sb = new StringBuilder();
                    sb.append(mSo.get(i));
                    str = "***";
                } else if (mNhay.get(i).intValue() == 4) {
                    sb = new StringBuilder();
                    sb.append(mSo.get(i));
                    str = "****";
                } else if (mNhay.get(i).intValue() == 5) {
                    sb = new StringBuilder();
                    sb.append(mSo.get(i));
                    str = "*****";
                } else {
                    if (mNhay.get(i).intValue() == 6) {
                        sb = new StringBuilder();
                        sb.append(mSo.get(i));
                        str = "******";
                    }
                    viewHolder.tview2.setText((i + 1) + "");
                    viewHolder.tview7.setText(mTienTon.get(i));
                    viewHolder.tview8.setText("0");
                    viewHolder.tview1.setText(mMax.get(i));
                    viewHolder.tview4.setText(mGia.get(i));
                }
                sb.append(str);
                viewHolder.tview5.setText(sb.toString());
//                viewHolder.tview2.setText((i + 1) + "");
                viewHolder.tview7.setText(mTienTon.get(i));
                viewHolder.tview8.setText("0");
                viewHolder.tview1.setText(mMax.get(i));
                viewHolder.tview4.setText(mGia.get(i));
            } else {
//                viewHolder.tview5.setTextColor(ViewCompat.MEASURED_STATE_MASK);
//                viewHolder.tview7.setTextColor(ViewCompat.MEASURED_STATE_MASK);
//                viewHolder.tview8.setTextColor(ViewCompat.MEASURED_STATE_MASK);
//                viewHolder.tview1.setTextColor(ViewCompat.MEASURED_STATE_MASK);
//                viewHolder.tview4.setTextColor(ViewCompat.MEASURED_STATE_MASK);
//                viewHolder.tview2.setText((i + 1) + "");
                viewHolder.tview5.setText(mSo.get(i));
                viewHolder.tview7.setText(mTienTon.get(i));
                viewHolder.tview8.setText(mTienchuyen.get(i));
                viewHolder.tview1.setText(mMax.get(i));
                if (mGia.size() > 0) {
                    viewHolder.tview4.setText(mGia.get(i));
                    if (Integer.parseInt(mGia.get(i)) > Price) {
                        viewHolder.tview4.setTextColor(Color.GREEN);
                    }
                }
            }
            return view;
        }
    }

    public String CreateJson() {
        JSONObject jSONObject = new JSONObject();
        JSONObject r2 = new JSONObject();
        JSONObject jSONObject2;
        String str = "";
        String str2 = "";
        int i = 0;
        Iterator<String> it;
        String[] strArr;
        String str3 = ",";
        JSONObject jSONObject3 = new JSONObject();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        simpleDateFormat.setTimeZone(TimeZone.getDefault());
        String format = simpleDateFormat.format(calendar.getTime());
        Cursor GetData = this.db.GetData("Select max(so_tin_nhan) from tbl_tinnhans where ngay_nhan = '" + format + "' And ten_kh = '" + this.mwebsite.get(this.spin_pointion) + "'");
        GetData.moveToFirst();
        int i2 = GetData.getInt(0);
        JSONObject jSONObject4 = new JSONObject();
        try {
            jSONObject4.put("Term", format);
            jSONObject4.put("IgnorePrice", true);
            JSONArray jSONArray = new JSONArray();
            JSONObject jSONObject5 = new JSONObject();
            jSONObject5.put("GameType", 0);
            jSONObject5.put("BetType", this.GameType);
            JSONArray jSONArray2 = new JSONArray();
            new JSONObject();
            new JSONArray();
            int i3 = this.GameType;
            int i4 = 80000;
            if ((i3 == 0 || i3 == 21 || i3 == 22 || i3 == 23) && this.Price < 80000) {
                i4 = 70000;
            } else if (((i3 != 0 && i3 != 21 && i3 != 22 && i3 != 23) || this.Price <= 80000) && i3 != 1 && i3 != 20) {
                if (i3 == 2) {
                    i4 = 10000;
                } else if (i3 == 3) {
                    i4 = 40000;
                } else if (i3 == 4) {
                    i4 = 100000;
                } else if (i3 != 6) {
                    i4 = 0;
                }
            }
            Iterator<String> keys = this.jsonChayTrang.keys();
            while (keys.hasNext()) {
                String next = keys.next();
                try {
                    if (this.jsonChayTrang.getDouble(next) > 0.0d) {
                        JSONObject jSONObject6 = new JSONObject();
                        JSONArray jSONArray3 = new JSONArray();
                        String[] split = next.split(str3);
                        int length = split.length;
                        it = keys;
                        int i5 = 0;
                        jSONObject2 = jSONObject4;
                        String str4 = "";
                        while (i5 < length) {
                            int i6 = length;
                            String str5 = split[i5];
                            if (!str5.isEmpty()) {
                                jSONArray3.put(str5);
                                strArr = split;
                                str4 = str4 + str5 + str3;
                            } else {
                                strArr = split;
                            }
                            i5++;
                            if (str4.endsWith(str3)) {
                                str4 = str4.substring(0, str4.length() - 1);
                            }
                        }
                        JSONObject jSONObject7 = new JSONObject();
                        jSONObject7.put("ngay_nhan", format);
                        jSONObject7.put("type_kh", 2);
                        str = str3;
                        jSONObject7.put("ten_kh", this.mwebsite.get(this.spin_pointion));
                        jSONObject7.put("so_dienthoai", this.mwebsite.get(this.spin_pointion));
                        jSONObject7.put("so_tin_nhan", i2 + 1);
                        jSONObject7.put("the_loai", this.the_loai.indexOf("xi") > -1 ? "xi" : this.the_loai);
                        jSONObject7.put("so_chon", str4);
                        str2 = format;
                        i = i2;
                        jSONObject7.put("diem", this.jsonChayTrang.getDouble(next));
                        jSONObject7.put("diem_quydoi", this.jsonChayTrang.getDouble(next));
                        jSONObject7.put("diem_khachgiu", 0);
                        jSONObject7.put("diem_dly_giu", 0);
                        jSONObject7.put("diem_ton", this.jsonChayTrang.getDouble(next));
                        jSONObject7.put("gia", !this.radio_xi.isChecked() ? this.jsonGia.has(next) ? this.Price + this.jsonGia.getInt(next) : this.Price : this.jsonTienxien.has(str4) ? this.jsonTienxien.getInt(str4) : this.Price);
                        jSONObject7.put("lan_an", i4);
                        jSONObject7.put("so_nhay", 0);
                        double d = this.jsonGia.has(next) ? this.Price + this.jsonGia.getInt(next) : this.Price;
                        double d2 = this.jsonChayTrang.getDouble(next);
                        Double.isNaN(d);
                        Double.isNaN(d);
                        jSONObject7.put("tong_tien", d * d2);
                        jSONObject7.put("ket_qua", 0);
                        this.jsonArray.put(jSONObject7);
                        jSONObject6.put("Numbers", jSONArray3);
                        jSONObject6.put("Point", this.jsonChayTrang.getDouble(next));
                        jSONObject6.put("Price", 705);
                        jSONArray2.put(jSONObject6);
                    } else {
                        str = str3;
                        str2 = format;
                        i = i2;
                        jSONObject2 = jSONObject4;
                        it = keys;
                    }
                    format = str2;
                    jSONObject4 = jSONObject2;
                    keys = it;
                    str3 = str;
                    i2 = i;
                } catch (JSONException e3) {
                    e3.printStackTrace();
                }
            }
            JSONObject jSONObject8 = jSONObject4;
            try {
                jSONObject5.put("Items", jSONArray2);
                jSONArray.put(jSONObject5);
                r2 = jSONObject8;
                try {
                    r2.put("Tickets", jSONArray);
                    jSONObject = r2;
                } catch (JSONException e5) {
                    jSONObject3 = r2;
                    e5.printStackTrace();
                    jSONObject = jSONObject3;
                    return jSONObject.toString();
                }
            } catch (JSONException e6) {
                e6.printStackTrace();
            }
        } catch (JSONException e7) {
            e7.printStackTrace();
        }
        return jSONObject.toString();
    }

    public String Laygia() {
        this.jsonGia = new JSONObject();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        simpleDateFormat.setTimeZone(TimeZone.getDefault());
        String format = simpleDateFormat.format(calendar.getTime());
        String[] strArr = {""};
        OkHttpClient okHttpClient = new OkHttpClient();
        if (MainActivity.MyToken.length() > 0 && Build.VERSION.SDK_INT >= 24) {
            // TODO: 7/4/2024 Khong biet day la gi
//            CompletableFuture.runAsync(new _$.Lambda.Frag_Chaytrang.hzE2FSVfsxb0QfhTAHA5cgHOUQM(this, format, okHttpClient, strArr));
        }
        return strArr[0];
    }

    private void init() {
        this.radio_de = this.v.findViewById(R.id.radio_de);
        this.radio_lo = this.v.findViewById(R.id.radio_lo);
        this.radio_xi = this.v.findViewById(R.id.radio_xi);
        this.radio_dea = this.v.findViewById(R.id.radio_dea);
        this.radio_deb = this.v.findViewById(R.id.radio_deb);
        this.radio_dec = this.v.findViewById(R.id.radio_dec);
        this.radio_ded = this.v.findViewById(R.id.radio_ded);
        this.radio_xi2 = this.v.findViewById(R.id.radio_xi2);
        this.radio_xi3 = this.v.findViewById(R.id.radio_xi3);
        this.radio_xi4 = this.v.findViewById(R.id.radio_xi4);
        this.spr_trang = this.v.findViewById(R.id.spr_trang);
        this.btn_Xuatso = this.v.findViewById(R.id.btn_Xuatso);
        this.lview = this.v.findViewById(R.id.lview);
        this.li_loaide = this.v.findViewById(R.id.li_loaide);
        this.li_loaixi = this.v.findViewById(R.id.li_loaixi);
        this.btn_MaXuat = this.v.findViewById(R.id.btn_MaXuat);
        this.edt_tien = this.v.findViewById(R.id.edt_tien);
    }

    public void login(String str, String str2) {
        OkHttpClient okHttpClient = new OkHttpClient();
        JSONObject jSONObject = new JSONObject();
        AtomicReference atomicReference = new AtomicReference("");
        if (Build.VERSION.SDK_INT >= 24) {
            // TODO: 7/4/2024 Khong biet day la gi
//            CompletableFuture.runAsync(lambda$login$2$Frag_Chaytrang(jSONObject, str, str2, atomicReference, okHttpClient));
        }
    }

    public static Date parseDate(String str) {
        try {
            return new SimpleDateFormat("HH:mm", Locale.US).parse(str);
        } catch (ParseException unused) {
            return new Date(0L);
        }
    }

    public void Dialog() {
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.frag_chaytrang_diaglog);
        Window window = dialog.getWindow();
        window.setLayout(-1, -2);
        EditText edt_XuatDan =  dialog.findViewById(R.id.edt_XuatDan);
        final TextView taikhoan =  dialog.findViewById(R.id.taikhoan);
        final TextView CreditLimit =  dialog.findViewById(R.id.CreditLimit);
        final TextView Balance =  dialog.findViewById(R.id.Balance);
        TextView edt_XuatErr =  dialog.findViewById(R.id.edt_XuatErr);
        edt_XuatErr.setVisibility(View.GONE);
        Button btn_chuyendi =  dialog.findViewById(R.id.btn_chuyendi);
        final OkHttpClient okHttpClient = new OkHttpClient();
        if (MainActivity.MyToken.length() > 0 && Build.VERSION.SDK_INT >= 24) {
            CompletableFuture.runAsync(() -> Frag_Chaytrang.this.lambda$Dialog$0$Frag_Chaytrang(okHttpClient, taikhoan, CreditLimit, Balance));
        }
        edt_XuatDan.setText("");
        edt_XuatDan.setText(this.xuatDan.replaceAll(",x", "x"));
        btn_chuyendi.setOnClickListener(new ViewOnClickListenerC066318(btn_chuyendi, edt_XuatDan, dialog, edt_XuatErr));
        dialog.setCancelable(true);
        dialog.setTitle("Xem dạng:");
        dialog.show();


//        final Dialog dialog = new Dialog(requireActivity());
//        dialog.setContentView(R.layout.frag_chaytrang_diaglog);
//        dialog.getWindow().setLayout(-1, -2);
//        final EditText editText =  dialog.findViewById(R.id.edt_Gialo);
//        TextView textView = dialog.findViewById(R.id.staticLayout);
//        TextView textView2 = dialog.findViewById(R.id.CreditLimit);
//        TextView textView3 = dialog.findViewById(R.id.Balance);
//        final TextView textView4 = dialog.findViewById(R.id.edt_Giaxi2);
//        textView4.setVisibility(View.GONE);
//        final Button button =  dialog.findViewById(R.id.btn_active);
//        OkHttpClient okHttpClient = new OkHttpClient();
//        if (MainActivity.MyToken.length() > 0 && Build.VERSION.SDK_INT >= 24) {
//            CompletableFuture.runAsync(new _$.Lambda.Frag_Chaytrang.DGn7QqO0Xk9idVJ8MyHUxv0XV3o(this, okHttpClient, textView, textView2, textView3));
//        }
//        editText.setText("");
//        editText.setText(this.xuatDan.replaceAll(",x", "x"));
//        button.setOnClickListener(new View.OnClickListener() {
//            public void lambda$onClick$0$Frag_Chaytrang$18(AtomicReference atomicReference, OkHttpClient okHttpClient2, String str, final SQLiteDatabase sQLiteDatabase, final DatabaseUtils.InsertHelper insertHelper, final EditText editText2, final Dialog dialog2, final TextView textView5, final Button button2) {
//                Handler handler;
//                Runnable runnable;
//                try {
//                    atomicReference.set(okHttpClient2.newCall(new Request.Builder().url("https://lotto.lotusapi.com/game-play/player/play").header("Content-Type", "application/json").header("Authorization", "Bearer " + MainActivity.MyToken).post(RequestBody.INSTANCE.create(str, MediaType.INSTANCE.parse("application/json"))).build()).execute().body().string());
//                    String atomicReference2 = atomicReference.toString();
//                    if (!atomicReference2.startsWith("[")) {
//                        handler = new Handler(Looper.getMainLooper());
//                        runnable = () -> {
//                            textView5.setText("Kết nối kém, hãy xuất lại.");
//                            textView5.setVisibility(View.VISIBLE);
//                            button2.setEnabled(true);
//                        };
//                    } else if (new JSONArray(atomicReference2).getJSONObject(0).has("Tx")) {
//                        handler = new Handler(Looper.getMainLooper());
//                        runnable = new Runnable() { // from class: tamhoang.ldpro4.Fragment.Frag_Chaytrang.18.1
//                            @Override // java.lang.Runnable
//                            public void run() {
//                                String str2 = "ngay_nhan";
//                                String str3 = " ";
//                                String str4 = "'";
//                                try {
//                                    sQLiteDatabase.beginTransaction();
//                                    int i = 0;
//                                    JSONObject jSONObject = null;
//                                    while (i < jsonArray.length()) {
//                                        jSONObject = jsonArray.getJSONObject(i);
//                                        insertHelper.prepareForInsert();
//                                        DatabaseUtils.InsertHelper insertHelper2 = insertHelper;
//                                        String str5 = str3;
//                                        String str6 = str4;
//                                        insertHelper2.bind(insertHelper2.getColumnIndex("ID"), (byte[]) null);
//                                        DatabaseUtils.InsertHelper insertHelper3 = insertHelper;
//                                        insertHelper3.bind(insertHelper3.getColumnIndex(str2), jSONObject.getString(str2));
//                                        DatabaseUtils.InsertHelper insertHelper4 = insertHelper;
//                                        insertHelper4.bind(insertHelper4.getColumnIndex("type_kh"), 2);
//                                        DatabaseUtils.InsertHelper insertHelper5 = insertHelper;
//                                        String str7 = str2;
//                                        insertHelper5.bind(insertHelper5.getColumnIndex("ten_kh"), mwebsite.get(spin_pointion));
//                                        DatabaseUtils.InsertHelper insertHelper6 = insertHelper;
//                                        insertHelper6.bind(insertHelper6.getColumnIndex("so_dienthoai"), mwebsite.get(spin_pointion));
//                                        DatabaseUtils.InsertHelper insertHelper7 = insertHelper;
//                                        insertHelper7.bind(insertHelper7.getColumnIndex("so_tin_nhan"), jSONObject.getInt("so_tin_nhan"));
//                                        DatabaseUtils.InsertHelper insertHelper8 = insertHelper;
//                                        insertHelper8.bind(insertHelper8.getColumnIndex("the_loai"), the_loai.indexOf("xi") > -1 ? "xi" : the_loai);
//                                        DatabaseUtils.InsertHelper insertHelper9 = insertHelper;
//                                        insertHelper9.bind(insertHelper9.getColumnIndex("so_chon"), jSONObject.getString("so_chon"));
//                                        DatabaseUtils.InsertHelper insertHelper10 = insertHelper;
//                                        insertHelper10.bind(insertHelper10.getColumnIndex("diem"), jSONObject.getInt("diem"));
//                                        DatabaseUtils.InsertHelper insertHelper11 = insertHelper;
//                                        insertHelper11.bind(insertHelper11.getColumnIndex("diem_quydoi"), jSONObject.getInt("diem"));
//                                        DatabaseUtils.InsertHelper insertHelper12 = insertHelper;
//                                        insertHelper12.bind(insertHelper12.getColumnIndex("diem_khachgiu"), 0);
//                                        DatabaseUtils.InsertHelper insertHelper13 = insertHelper;
//                                        insertHelper13.bind(insertHelper13.getColumnIndex("diem_dly_giu"), 0);
//                                        DatabaseUtils.InsertHelper insertHelper14 = insertHelper;
//                                        insertHelper14.bind(insertHelper14.getColumnIndex("diem_ton"), jSONObject.getInt("diem"));
//                                        DatabaseUtils.InsertHelper insertHelper15 = insertHelper;
//                                        insertHelper15.bind(insertHelper15.getColumnIndex("gia"), jSONObject.getInt("gia"));
//                                        DatabaseUtils.InsertHelper insertHelper16 = insertHelper;
//                                        insertHelper16.bind(insertHelper16.getColumnIndex("lan_an"), jSONObject.getInt("lan_an"));
//                                        DatabaseUtils.InsertHelper insertHelper17 = insertHelper;
//                                        insertHelper17.bind(insertHelper17.getColumnIndex("so_nhay"), 0);
//                                        DatabaseUtils.InsertHelper insertHelper18 = insertHelper;
//                                        insertHelper18.bind(insertHelper18.getColumnIndex("tong_tien"), jSONObject.getInt("tong_tien"));
//                                        DatabaseUtils.InsertHelper insertHelper19 = insertHelper;
//                                        insertHelper19.bind(insertHelper19.getColumnIndex("ket_qua"), 0);
//                                        insertHelper.execute();
//                                        i++;
//                                        str2 = str7;
//                                        str3 = str5;
//                                        str4 = str6;
//                                    }
//                                    String str8 = str3;
//                                    String str9 = str4;
//                                    sQLiteDatabase.setTransactionSuccessful();
//                                    sQLiteDatabase.endTransaction();
//                                    insertHelper.close();
//                                    sQLiteDatabase.close();
//                                    Calendar calendar = Calendar.getInstance();
//                                    calendar.setTime(new Date());
//                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//                                    SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("HH:mm:ss");
//                                    simpleDateFormat.setTimeZone(TimeZone.getDefault());
//                                    simpleDateFormat2.setTimeZone(TimeZone.getDefault());
//                                    db.QueryData("Insert Into tbl_tinnhanS values (null, '" + simpleDateFormat.format(calendar.getTime()) + "', '" + simpleDateFormat2.format(calendar.getTime()) + "', 2, '" + mwebsite.get(spin_pointion) + "', '" + mwebsite.get(spin_pointion) + "', 'ChayTrang', " + jSONObject.getInt("so_tin_nhan") + ", '" + editText2.getText().toString().replace(str9, str8).trim() + "', '" + editText2.getText().toString().replace(str9, str8).trim() + "', '" + editText2.getText().toString().replace(str9, str8).trim().toLowerCase() + "', 'ok',0, 0, 0, null)");
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                                xem_RecycView();
//                                dialog2.dismiss();
//                                Toast.makeText(getActivity(), "Đã chạy thành công!", 0).show();
//                            }
//                        };
//                    } else {
//                        handler = new Handler(Looper.getMainLooper());
//                        // from class: tamhoang.ldpro4.Fragment.Frag_Chaytrang.18.2
//                        runnable = () -> {
//                            textView5.setText("Kết nối kém, hãy xuất lại.");
//                            textView5.setVisibility(View.VISIBLE);
//                            button2.setEnabled(true);
//                        };
//                    }
//                    handler.post(runnable);
//                } catch (Exception e) {
//                    new Handler(Looper.getMainLooper()).post(() -> {
//                        textView5.setText("Kết nối kém, hãy xuất lại.");
//                        textView5.setVisibility(View.VISIBLE);
//                        button2.setEnabled(true);
//                    });
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onClick(View view) {
//                String str;
//                button.setEnabled(false);
//                SQLiteDatabase writableDatabase = db.getWritableDatabase();
//                DatabaseUtils.InsertHelper insertHelper = new DatabaseUtils.InsertHelper(writableDatabase, "tbl_soctS");
//                try {
//                    try {
//                        str = KiemTraTruocKhiChayTrang(editText.getText().toString().replaceAll("'", " ").trim());
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                        str = null;
//                    }
//                    if (str == "") {
//                        str = Laygia();
//                    }
//                    if (str != "") {
//                        textView4.setText(str);
//                        textView4.setVisibility(View.VISIBLE);
//                        button.setEnabled(true);
//                        return;
//                    }
//                    jsonArray = new JSONArray();
//                    String CreateJson = CreateJson();
//                    OkHttpClient okHttpClient2 = new OkHttpClient();
//                    AtomicReference atomicReference = new AtomicReference("");
//                    if (Build.VERSION.SDK_INT >= 24) {
//                        CompletableFuture.runAsync(new _$.Lambda.Frag_Chaytrang
//                                = .18.Aa1GizXhuIa4XrlZ0t5mv9Cb18Q(this, atomicReference, okHttpClient2, CreateJson, writableDatabase, insertHelper, editText, dialog, textView4, button))
//                        ;
//                    }
//                } catch (Exception unused) {
//                    textView4.setText("Có lỗi khi xuất tin!");
//                    textView4.setVisibility(View.VISIBLE);
//                    button.setEnabled(true);
//                }
//            }
//        });
//        dialog.setCancelable(true);
//        dialog.setTitle("Xem dạng:");
//        dialog.show();
    }

    public void lambda$Dialog$0$Frag_Chaytrang(OkHttpClient okHttpClient, final TextView taikhoan, final TextView CreditLimit, final TextView Balance) {
        try {
            if (MainActivity.MyToken.length() > 0) {
                ResponseBody body = okHttpClient.newCall(new Request.Builder().header("Authorization", "Bearer " + MainActivity.MyToken).url("https://id.lotusapi.com/wallets/player/my-wallet").get().build()).execute().body();
                if (body != null) {
                    final JSONObject json = new JSONObject(body.string());
                    if (json.has("message")) {
                        if (json.getString("message").indexOf("Unauthorized") > -1) {
                            new Handler(Looper.getMainLooper()).post(() -> Toast.makeText(Frag_Chaytrang.this.getActivity(), "Tài khoản đăng nhập lỗi!", Toast.LENGTH_SHORT).show());
                            return;
                        }
                        return;
                    }
                    new Handler(Looper.getMainLooper()).post(() -> {
                        try {
                            DecimalFormat decimalFormat = new DecimalFormat("###,###");
                            taikhoan.setText(Frag_Chaytrang.this.mwebsite.get(Frag_Chaytrang.this.spin_pointion));
                            CreditLimit.setText(decimalFormat.format(json.getDouble("CreditLimit")));
                            Balance.setText(decimalFormat.format(json.getDouble("Balance")));
                            Frag_Chaytrang.this.myBalance = json.getDouble("Balance");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    });
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    class ViewOnClickListenerC066318 implements View.OnClickListener {
        final Button val$btn_chuyendi;
        final Dialog val$dialog;
        final EditText val$edt_XuatDan;
        final TextView val$edt_XuatErr;

        ViewOnClickListenerC066318(Button button, EditText editText, Dialog dialog, TextView textView) {
            this.val$btn_chuyendi = button;
            this.val$edt_XuatDan = editText;
            this.val$dialog = dialog;
            this.val$edt_XuatErr = textView;
        }

        @Override
        public void onClick(View view) {
            String Kiermtra;
            this.val$btn_chuyendi.setEnabled(false);
            final SQLiteDatabase database = db.getWritableDatabase();
            final DatabaseUtils.InsertHelper ih = new DatabaseUtils.InsertHelper(database, "tbl_soctS");
            String Kiermtra2 = null;
            try {
                try {
                    Kiermtra2 = Frag_Chaytrang.this.KiemTraTruocKhiChayTrang(this.val$edt_XuatDan.getText().toString().replaceAll("'", " ").trim());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (Kiermtra2 == "") {
                    String Kiermtra3 = Frag_Chaytrang.this.Laygia();
                    Kiermtra = Kiermtra3;
                } else {
                    Kiermtra = Kiermtra2;
                }
                if (Kiermtra != "") {
                    this.val$edt_XuatErr.setText(Kiermtra);
                    this.val$edt_XuatErr.setVisibility(View.VISIBLE);
                    this.val$btn_chuyendi.setEnabled(true);
                    return;
                }
                Frag_Chaytrang.this.jsonArray = new JSONArray();
                final String Postjson = Frag_Chaytrang.this.CreateJson();
                final OkHttpClient okHttpClient = new OkHttpClient();
                final AtomicReference<String> str3 = new AtomicReference<>("");
                if (Build.VERSION.SDK_INT >= 24) {
                    final EditText editText = this.val$edt_XuatDan;
                    final Dialog dialog = this.val$dialog;
                    final TextView textView = this.val$edt_XuatErr;
                    final Button button = this.val$btn_chuyendi;
                    CompletableFuture.runAsync(new Runnable() {
                        @Override
                        public final void run() {
                            lambda$onClick$0$Frag_Chaytrang$18(str3, okHttpClient, Postjson, database, ih, editText, dialog, textView, button);
                        }
                    });
                }
            } catch (Exception e2) {
                this.val$edt_XuatErr.setText("Có lỗi khi xuất tin!");
                this.val$edt_XuatErr.setVisibility(View.VISIBLE);
                this.val$btn_chuyendi.setEnabled(true);
            }
        }

        public void lambda$onClick$0$Frag_Chaytrang$18(AtomicReference str3, OkHttpClient okHttpClient, String Postjson, final SQLiteDatabase database, final DatabaseUtils.InsertHelper ih, final EditText edt_XuatDan, final Dialog dialog, final TextView edt_XuatErr, final Button btn_chuyendi) {
            try {
                try {
                    try {
                        try {
                            str3.set(okHttpClient.newCall(new Request.Builder().url("https://lotto.lotusapi.com/game-play/player/play").header(HttpConnection.CONTENT_TYPE, "application/json").header("Authorization", "Bearer " + MainActivity.MyToken).post(RequestBody.create(Postjson, MediaType.parse("application/json"))).build()).execute().body().string());
                            String Str = str3.toString();
                            if (!Str.startsWith("[")) {
                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        edt_XuatErr.setText("Kết nối kém, hãy xuất lại.");
                                        edt_XuatErr.setVisibility(View.VISIBLE);
                                        btn_chuyendi.setEnabled(true);
                                    }
                                });
                                return;
                            }
                            JSONArray json = new JSONArray(Str);
                            JSONObject jsonObject = json.getJSONObject(0);
                            if (!jsonObject.has("Tx")) {
                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        edt_XuatErr.setText("Kết nối kém, hãy xuất lại.");
                                        edt_XuatErr.setVisibility(View.VISIBLE);
                                        btn_chuyendi.setEnabled(true);
                                    }
                                });
                            } else {
                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        String str = "ngay_nhan";
                                        String str2 = " ";
                                        String str4 = "'";
                                        try {
                                            database.beginTransaction();
                                            JSONObject Json = null;
                                            int i = 0;
                                            while (i < Frag_Chaytrang.this.jsonArray.length()) {
                                                Json = Frag_Chaytrang.this.jsonArray.getJSONObject(i);
                                                ih.prepareForInsert();
                                                String str5 = str2;
                                                String str6 = str4;
                                                ih.bind(ih.getColumnIndex("ID"), (byte[]) null);
                                                ih.bind(ih.getColumnIndex(str), Json.getString(str));
                                                ih.bind(ih.getColumnIndex("type_kh"), 2);
                                                String str7 = str;
                                                ih.bind(ih.getColumnIndex("ten_kh"), Frag_Chaytrang.this.mwebsite.get(Frag_Chaytrang.this.spin_pointion));
                                                ih.bind(ih.getColumnIndex("so_dienthoai"), Frag_Chaytrang.this.mwebsite.get(Frag_Chaytrang.this.spin_pointion));
                                                ih.bind(ih.getColumnIndex("so_tin_nhan"), Json.getInt("so_tin_nhan"));
                                                ih.bind(ih.getColumnIndex("the_loai"), Frag_Chaytrang.this.the_loai.indexOf("xi") > -1 ? "xi" : Frag_Chaytrang.this.the_loai);
                                                ih.bind(ih.getColumnIndex("so_chon"), Json.getString("so_chon"));
                                                ih.bind(ih.getColumnIndex("diem"), Json.getInt("diem"));
                                                ih.bind(ih.getColumnIndex("diem_quydoi"), Json.getInt("diem"));
                                                ih.bind(ih.getColumnIndex("diem_khachgiu"), 0);
                                                ih.bind(ih.getColumnIndex("diem_dly_giu"), 0);
                                                ih.bind(ih.getColumnIndex("diem_ton"), Json.getInt("diem"));
                                                ih.bind(ih.getColumnIndex("gia"), Json.getInt("gia"));
                                                ih.bind(ih.getColumnIndex("lan_an"), Json.getInt("lan_an"));
                                                ih.bind(ih.getColumnIndex("so_nhay"), 0);
                                                ih.bind(ih.getColumnIndex("tong_tien"), Json.getInt("tong_tien"));
                                                ih.bind(ih.getColumnIndex("ket_qua"), 0);
                                                ih.execute();
                                                i++;
                                                str = str7;
                                                str2 = str5;
                                                str4 = str6;
                                            }
                                            String str8 = str2;
                                            String str9 = str4;
                                            database.setTransactionSuccessful();
                                            database.endTransaction();
                                            ih.close();
                                            database.close();
                                            Calendar calendar = Calendar.getInstance();
                                            calendar.setTime(new Date());
                                            SimpleDateFormat dmyFormat = new SimpleDateFormat("yyyy-MM-dd");
                                            SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
                                            dmyFormat.setTimeZone(TimeZone.getDefault());
                                            hourFormat.setTimeZone(TimeZone.getDefault());
                                            String mNgayNhan = dmyFormat.format(calendar.getTime());
                                            String mGionhan = hourFormat.format(calendar.getTime());
                                            String S = "Insert Into tbl_tinnhanS values (null, '" + mNgayNhan + "', '" + mGionhan + "', 2, '" + Frag_Chaytrang.this.mwebsite.get(Frag_Chaytrang.this.spin_pointion) + "', '" + Frag_Chaytrang.this.mwebsite.get(Frag_Chaytrang.this.spin_pointion) + "', 'ChayTrang', " + Json.getInt("so_tin_nhan") + ", '" + edt_XuatDan.getText().toString().replace(str9, str8).trim() + "', '" + edt_XuatDan.getText().toString().replace(str9, str8).trim() + "', '" + edt_XuatDan.getText().toString().replace(str9, str8).trim().toLowerCase() + "', 'ok',0, 0, 0, null)";
                                            db.QueryData(S);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        Frag_Chaytrang.this.xem_RecycView();
                                        dialog.dismiss();
                                        Toast.makeText(Frag_Chaytrang.this.getActivity(), "Đã chạy thành công!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        } catch (Exception e) {
                            new Handler(Looper.getMainLooper()).post(() -> {
                                edt_XuatErr.setText("Kết nối kém, hãy xuất lại.");
                                edt_XuatErr.setVisibility(View.VISIBLE);
                                btn_chuyendi.setEnabled(true);
                            });
                            e.printStackTrace();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    public void Dialog2() {
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.frag_chaytrang_tinchay);
        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        dialog.getWindow().setLayout(-1, -2);
        ListView lv_cacmachay = (ListView) dialog.findViewById(R.id.lv_cacmachay);
        this.SoTin.clear();
        this.TheLoai.clear();
        this.NoiDung.clear();
        this.ThoiGian.clear();
        this.TienCuoc.clear();
        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            if (Build.VERSION.SDK_INT >= 24) {
                CompletableFuture.runAsync(() -> Frag_Chaytrang.this.lambda$Dialog2$1$Frag_Chaytrang(okHttpClient, decimalFormat, lv_cacmachay));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        dialog.getWindow().setLayout(-1, -2);
        dialog.setCancelable(true);
        dialog.setTitle("Xem dạng:");
        dialog.show();
    }


    public String KiemTraTruocKhiChayTrang(String str) throws JSONException {
        StringBuilder sb;
        String str2;
        Cursor cursor;
        Cursor cursor2;
        JSONObject jSONObject;
        String str3;
        int i;
        double d;
        String str4;
        int parseInt;
        JSONObject jSONObject2;
        StringBuilder sb2;
        String str5 = "";
        Cursor cursor3;
        String str6 = "";
        String str7 = "";
        Cursor cursor4 = null;
        this.jsonChayTrang = new JSONObject();
        JSONObject jSONObject3 = new JSONObject();
        new MainActivity();
        String Get_date = MainActivity.Get_date();
        if (this.the_loai.indexOf("deb") > -1) {
            sb = new StringBuilder();
            sb.append("Select so_chon, Sum(diem) FROM tbl_soctS Where ten_kh = '");
            sb.append(this.mwebsite.get(this.spin_pointion));
            str2 = "' AND type_kh = 2 AND (the_loai = 'deb' or the_loai = 'det') AND ngay_nhan = '";
        } else if (this.the_loai.indexOf("xi2") > -1) {
            sb = new StringBuilder();
            sb.append("Select so_chon, Sum(diem) FROM tbl_soctS Where ten_kh = '");
            sb.append(this.mwebsite.get(this.spin_pointion));
            str2 = "' AND type_kh = 2 AND the_loai = 'xi' AND length(so_chon) = 5  AND ngay_nhan = '";
        } else if (this.the_loai.indexOf("xi3") > -1) {
            sb = new StringBuilder();
            sb.append("Select so_chon, Sum(diem) FROM tbl_soctS Where ten_kh = '");
            sb.append(this.mwebsite.get(this.spin_pointion));
            str2 = "' AND type_kh = 2 AND the_loai = 'xi' AND length(so_chon) = 8  AND ngay_nhan = '";
        } else if (this.the_loai.indexOf("xi4") > -1) {
            sb = new StringBuilder();
            sb.append("Select so_chon, Sum(diem) FROM tbl_soctS Where ten_kh = '");
            sb.append(this.mwebsite.get(this.spin_pointion));
            str2 = "' AND type_kh = 2 AND the_loai = 'xi' AND length(so_chon) = 11  AND ngay_nhan = '";
        } else {
            sb = new StringBuilder();
            sb.append("Select so_chon, Sum(diem) FROM tbl_soctS Where ten_kh = '");
            sb.append(this.mwebsite.get(this.spin_pointion));
            sb.append("' AND type_kh = 2 AND the_loai = '");
            sb.append(this.the_loai);
            str2 = "' AND ngay_nhan = '";
        }
        sb.append(str2);
        sb.append(Get_date);
        sb.append("' Group by so_chon");
        Cursor GetData = this.db.GetData(sb.toString());
        while (GetData.moveToNext()) {
            try {
                try {
                    jSONObject3.put(GetData.getString(0), GetData.getInt(1));
                } catch (JSONException e) {
                    e = e;
                    cursor2 = GetData;
                } catch (Throwable th) {
                    th = th;
                    cursor = GetData;
                }
            } catch (Throwable th2) {
                th2.printStackTrace();
            }
        }
        String str8 = ",";
        String str9 = "d";
        if (this.the_loai.contains("xi")) {
            jSONObject = jSONObject3;
            cursor2 = GetData;
            str3 = ",";
            String str10 = "d";
            try {
                String[] split = str.substring(str.indexOf(":")).split(" ");
                int i2 = 0;
                for (int i3 = 0; i3 < split.length; i3++) {
                    String substring = split[i3].substring(split[i3].indexOf(":") + 1);
                    String substring2 = substring.substring(0, substring.indexOf("x"));
                    String str11 = str10;
                    String replaceAll = split[i3].substring(split[i3].indexOf("x")).replaceAll("x", "").replaceAll("n", "").replaceAll(str11, "").replaceAll("k", "");
                    if (this.jsonChayTrang.has(substring2)) {
                        JSONObject jSONObject4 = this.jsonChayTrang;
                        jSONObject4.put(substring2, jSONObject4.getInt(substring2) + Integer.parseInt(replaceAll));
                    } else {
                        this.jsonChayTrang.put(substring2, replaceAll);
                    }
                    str10 = str11;
                    double d2 = i2;
                    try {
                        double parseDouble = Double.parseDouble(replaceAll);
                        double d3 = this.jsonTienxien.getDouble(substring2);
                        Double.isNaN(d2);
                        Double.isNaN(d2);
                        d = d2 + (parseDouble * d3);
                    } catch (Exception unused) {
                        double parseDouble2 = Double.parseDouble(replaceAll);
                        double d4 = this.Price + 20;
                        Double.isNaN(d4);
                        Double.isNaN(d2);
                        Double.isNaN(d4);
                        Double.isNaN(d2);
                        d = d2 + (parseDouble2 * d4);
                    }
                    i2 = (int) d;
                }
                i = i2;
            } catch (JSONException e2) {
                e2.printStackTrace();
                if (!cursor2.isClosed()) {
                    cursor2.close();
                }
                if (cursor2.isClosed()) {
                    return "Không phân tích được nội dung!";
                }
                cursor2.close();
                return "Không phân tích được nội dung!";
            }
        } else {
            String[] split2 = str.substring(str.indexOf(":")).split(" ");
            int i4 = 0;
            i = 0;
            while (i4 < split2.length) {
                String substring3 = split2[i4].substring(split2[i4].indexOf(":") + 1);
                int i5 = i;
                String substring4 = substring3.substring(0, substring3.indexOf("x"));
                String replaceAll2 = split2[i4].substring(split2[i4].indexOf("x")).replaceAll("x", "").replaceAll("n", "").replaceAll(str9, "").replaceAll("k", "");
                String[] split3 = substring4.split(str8);
                int length = split3.length;
                String[] strArr = split2;
                JSONObject jSONObject5 = jSONObject3;
                int i6 = 0;
                int i7 = i5;
                while (i6 < length) {
                    int i8 = length;
                    String str12 = split3[i6];
                    String[] strArr2 = split3;
                    if (this.jsonChayTrang.has(str12)) {
                        JSONObject jSONObject6 = this.jsonChayTrang;
                        try {
                            str5 = str8;
                            jSONObject6.put(str12, jSONObject6.getInt(str12) + Integer.parseInt(replaceAll2));
                        } catch (JSONException e3) {
                            cursor3 = GetData;
                            cursor2 = cursor3;
                            e3.printStackTrace();
                            if (!cursor2.isClosed()) {
                            }
                            if (cursor2.isClosed()) {
                            }
                        } catch (Throwable th3) {
                            cursor3 = GetData;
                            cursor = cursor3;
                            if (!cursor.isClosed()) {
                                cursor.close();
                            }
                            th3.printStackTrace();
                        }
                    } else {
                        str5 = str8;
                        this.jsonChayTrang.put(str12, replaceAll2);
                    }
                    if (this.jsonGia.has(str12)) {
                        cursor3 = GetData;
                        double d5 = i7;
                        try {
                            double parseDouble3 = Double.parseDouble(replaceAll2);
                            int i9 = this.jsonGia.getInt(str12) + this.Price;
                            str6 = str9;
                            double d6 = i9;
                            Double.isNaN(d6);
                            Double.isNaN(d5);
                            Double.isNaN(d6);
                            Double.isNaN(d5);
                            i7 = (int) (d5 + (parseDouble3 * d6));
                            cursor4 = cursor3;
                            str7 = replaceAll2;
                        } catch (JSONException e4) {
                            cursor2 = cursor3;
                            e4.printStackTrace();
                            if (!cursor2.isClosed()) {
                            }
                            if (cursor2.isClosed()) {
                            }
                        } catch (Throwable th4) {
                            cursor = cursor3;
                            if (!cursor.isClosed()) {
                            }
                        }
                    } else {
                        Cursor cursor5 = GetData;
                        str6 = str9;
                        double d7 = i7;
                        double parseDouble4 = Double.parseDouble(replaceAll2);
                        str7 = replaceAll2;
                        cursor4 = cursor5;
                        double d8 = this.Price;
                        Double.isNaN(d8);
                        Double.isNaN(d7);
                        Double.isNaN(d8);
                        Double.isNaN(d7);
                        i7 = (int) (d7 + (parseDouble4 * d8));
                    }
                    i6++;
                    str9 = str6;
                    length = i8;
                    split3 = strArr2;
                    replaceAll2 = str7;
                    GetData = cursor4;
                    str8 = str5;
                }
                i4++;
                split2 = strArr;
                i = i7;
                jSONObject3 = jSONObject5;
            }
            jSONObject = jSONObject3;
            cursor2 = GetData;
            str3 = str8;
        }
        if (!cursor2.isClosed()) {
            cursor2.close();
        }
        if (i > this.myBalance) {
            return "Vượt quá số tiền còn lại";
        }
        Iterator<String> keys = this.jsonChayTrang.keys();
        String str13 = "";
        while (keys.hasNext()) {
            String next = keys.next();
            try {
                if (this.myMax.contains(".")) {
                    parseInt = Integer.parseInt(this.myMax.replaceAll("\\.", ""));
                    jSONObject2 = jSONObject;
                    str4 = str3;
                } else {
                    str4 = str3;
                    parseInt = Integer.parseInt(this.myMax.replaceAll(str4, ""));
                    jSONObject2 = jSONObject;
                }
                if (jSONObject2.has(next)) {
                    if (this.jsonChayTrang.getDouble(next) + jSONObject2.getDouble(next) > parseInt) {
                        sb2 = new StringBuilder();
                        sb2.append(str13);
                        sb2.append(next);
                        sb2.append(" ");
                        str13 = sb2.toString();
                        str3 = str4;
                        jSONObject = jSONObject2;
                    } else {
                        str3 = str4;
                        jSONObject = jSONObject2;
                    }
                } else if (this.jsonChayTrang.getDouble(next) > parseInt) {
                    sb2 = new StringBuilder();
                    sb2.append(str13);
                    sb2.append(next);
                    sb2.append(" ");
                    str13 = sb2.toString();
                    str3 = str4;
                    jSONObject = jSONObject2;
                } else {
                    str3 = str4;
                    jSONObject = jSONObject2;
                }
            } catch (JSONException e5) {
                e5.printStackTrace();
                return "Kiểm tra lại số liệu";
            }
        }
        if (str13.length() <= 0) {
            return "";
        }
        return "Các cặp: " + str13 + " vượt quá max của trang";
    }

    public String TaoTinDe() throws JSONException {
        int parseInt;
        int parseInt2;
        JSONObject jSONObject;
        JSONObject jSONObject2 = new JSONObject();
        ArrayList arrayList = new ArrayList();
        try {
            parseInt = Integer.parseInt(this.myMax.replace(".", ""));
        } catch (Exception unused) {
            parseInt = Integer.parseInt(this.myMax.replace(",", ""));
        }
        if (this.edt_tien.getText().toString().length() != 0) {
            if (Integer.parseInt(this.edt_tien.getText().toString()) > parseInt) {
                return "Số tiền vượt quá max ";
            }
            parseInt = Integer.parseInt(this.edt_tien.getText().toString());
        }
        int i = 0;
        for (int i2 = 0; i2 < this.mSo.size(); i2++) {
            try {
                String str = this.mSo.get(i2);
                if (i >= 50) {
                    break;
                }
                if (this.jsonGia.has(str)) {
                    if (this.jsonGia.getInt(str) + this.Price > this.MaxChay) {
                    }
                    parseInt2 = Integer.parseInt(this.mTienTon.get(i2).replace(".", ""));
                    jSONObject = new JSONObject();
                    jSONObject.put("So_chon", str);
                    jSONObject.put("Da_chuyen", !jSONObject2.has(str) ? jSONObject2.getJSONObject(str).getInt("Da_chuyen") + parseInt2 : 0);
                    if (jSONObject.getInt("Da_chuyen") + parseInt2 <= parseInt) {
                        parseInt2 = parseInt - jSONObject.getInt("Da_chuyen");
                    }
                    jSONObject.put("Se_chuyen", parseInt2);
                    if (jSONObject.getInt("Se_chuyen") <= 0) {
                        arrayList.add(jSONObject);
                        i++;
                    }
                } else {
                    if (this.Price > this.MaxChay) {
                    }
                    parseInt2 = Integer.parseInt(this.mTienTon.get(i2).replace(".", ""));
                    jSONObject = new JSONObject();
                    jSONObject.put("So_chon", str);
                    jSONObject.put("Da_chuyen", !jSONObject2.has(str) ? jSONObject2.getJSONObject(str).getInt("Da_chuyen") + parseInt2 : 0);
                    if (jSONObject.getInt("Da_chuyen") + parseInt2 <= parseInt) {
                    }
                    jSONObject.put("Se_chuyen", parseInt2);
                    if (jSONObject.getInt("Se_chuyen") <= 0) {
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Collections.sort(arrayList, new Comparator<JSONObject>() { // from class: tamhoang.ldpro4.Fragment.Frag_Chaytrang.14
            @Override // java.util.Comparator
            public int compare(JSONObject jSONObject3, JSONObject jSONObject4) {
                Integer num;
                Integer num2 = 0;
                try {
                    num = Integer.valueOf(jSONObject3.getInt("Se_chuyen"));
                    try {
                        num2 = Integer.valueOf(jSONObject4.getInt("Se_chuyen"));
                    } catch (JSONException unused2) {
                    }
                } catch (JSONException unused3) {
                    num = num2;
                }
                return num2.compareTo(num);
            }
        });
        int i3 = 0;
        int i4 = 0;
        for (int i5 = 0; i5 < arrayList.size(); i5++) {
            try {
                JSONObject jSONObject3 = (JSONObject) arrayList.get(i5);
                int i6 = jSONObject3.getInt("Se_chuyen");
                if (i6 > 0) {
                    if (i4 > i6) {
                        this.xuatDan += "x" + i4 + this.donvi;
                        this.xuatDan += jSONObject3.getString("So_chon") + ",";
                        i3 = 0;
                    } else {
                        this.xuatDan += jSONObject3.getString("So_chon") + ",";
                    }
                    i3++;
                    i4 = i6;
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
        if (this.xuatDan.length() > 4 && i3 > 0) {
            this.xuatDan += "x" + i4 + this.donvi;
        }
        return i3 > 0 ? this.xuatDan : "";
    }

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
            Collections.sort(arrayList, new Comparator<JSONObject>() { // from class: tamhoang.ldpro4.Fragment.Frag_Chaytrang.15
                @Override // java.util.Comparator
                public int compare(JSONObject jSONObject3, JSONObject jSONObject4) {
                    Integer num;
                    Integer num2 = 0;
                    try {
                        num = Integer.valueOf(jSONObject3.getInt("Se_chuyen"));
                        try {
                            num2 = Integer.valueOf(jSONObject4.getInt("Se_chuyen"));
                        } catch (JSONException unused) {
                        }
                    } catch (JSONException unused2) {
                        num = num2;
                    }
                    return num2.compareTo(num);
                }
            });
            for (int i3 = 0; i3 < arrayList.size(); i3++) {
                this.xuatDan += ((JSONObject) arrayList.get(i3)).getString("So_chon") + "x" + ((JSONObject) arrayList.get(i3)).getString("Se_chuyen") + this.donvi;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception unused) {
        }
        return this.xuatDan.length() > 5 ? this.xuatDan : "";
    }

    public void lambda$Dialog2$1$Frag_Chaytrang(OkHttpClient okHttpClient, DecimalFormat decimalFormat, final ListView listView) {
        List<Integer> list;
        int i;
        try {
            ResponseBody body = okHttpClient.newCall(new Request.Builder().header("Authorization", "Bearer " + MainActivity.MyToken).url("https://lotto.lotusapi.com/game-play/player/tickets/current?limit=100").get().build()).execute().body();
            if (body != null) {
                JSONArray jSONArray = new JSONArray(body.string());
                for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                    JSONObject jSONObject = jSONArray.getJSONObject(i2);
                    this.SoTin.add(jSONObject.getString("TicketNumber"));
                    this.TheLoai.add(Integer.valueOf(jSONObject.getInt("BetType")));
                    this.NoiDung.add(jSONObject.getString("Numbers"));
                    String[] split = jSONObject.getString("CreatedAt").substring(11).substring(0, 8).split(":");
                    split[0] = (Integer.parseInt(split[0]) + 7) + "";
                    this.ThoiGian.add(split[0] + ":" + split[1] + ":" + split[2]);
                    this.TienCuoc.add(decimalFormat.format(jSONObject.getLong("Amount")));
                    if (jSONObject.has("CancelledAt")) {
                        list = this.HuyCuoc;
                        i = 0;
                    } else {
                        list = this.HuyCuoc;
                        i = 1;
                    }
                    list.add(i);
                }
                new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: tamhoang.ldpro4.Fragment.Frag_Chaytrang.19
                    @Override // java.lang.Runnable
                    public void run() {
                        ListView listView2 = listView;
                        Frag_Chaytrang frag_Chaytrang = Frag_Chaytrang.this;
                        listView2.setAdapter((ListAdapter) new Ma_da_chay(frag_Chaytrang.getActivity(), 2131427403, NoiDung));
                    }
                });
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    public void lambda$Laygia$3$Frag_Chaytrang(String str, OkHttpClient okHttpClient, String[] strArr) {
        StringBuilder sb;
        try {
            if (MainActivity.MyToken.length() > 0) {
                if (this.radio_lo.isChecked() && this.LoLive) {
                    this.GameType = 20;
                    sb = new StringBuilder();
                    sb.append("https://lotto.lotusapi.com/odds/player/live?term=");
                    sb.append(str);
                    sb.append("&gameType=0&betType=20");
                } else {
                    sb = new StringBuilder();
                    sb.append("https://lotto.lotusapi.com/odds/player?term=");
                    sb.append(str);
                    sb.append("&gameTypes=0&betTypes=");
                    sb.append(this.GameType);
                }
                String sb2 = sb.toString();
                ResponseBody body = okHttpClient.newCall(new Request.Builder().header("Authorization", "Bearer " + MainActivity.MyToken).url(sb2).get().build()).execute().body();
                if (body != null) {
                    if (this.LoLive && this.radio_lo.isChecked()) {
                        JSONObject jSONObject = new JSONObject(body.string());
                        if (!jSONObject.getBoolean("Active")) {
                            new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: tamhoang.ldpro4.Fragment.Frag_Chaytrang.22
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
                            this.jsonGia.put(jSONObject2.getString("Number"), jSONObject2.getString("ExtraPrice"));
                        }
                        if (this.Price != this.PriceLive) {
                            new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: tamhoang.ldpro4.Fragment.Frag_Chaytrang.23
                                @Override // java.lang.Runnable
                                public void run() {
                                    xem_RecycView();
                                }
                            });
                            this.PriceLive = this.Price;
                        }
                    } else {
                        JSONObject jSONObject3 = new JSONArray(body.string()).getJSONObject(0);
                        this.Price = jSONObject3.getInt("Price");
                        this.PriceLive = 0;
                        JSONArray jSONArray2 = jSONObject3.getJSONArray("Numbers");
                        this.jsonGia = new JSONObject();
                        for (int i2 = 0; i2 < jSONArray2.length(); i2++) {
                            JSONObject jSONObject4 = jSONArray2.getJSONObject(i2);
                            this.jsonGia.put(jSONObject4.getString("Number"), jSONObject4.getString("ExtraPrice"));
                        }
                        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: tamhoang.ldpro4.Fragment.Frag_Chaytrang.24
                            @Override // java.lang.Runnable
                            public void run() {
                                xem_RecycView();
                            }
                        });
                    }
                }
                ResponseBody body2 = okHttpClient.newCall(new Request.Builder().header("Authorization", "Bearer " + MainActivity.MyToken).url("https://comm.lotusapi.com/servers/current-date-time").get().build()).execute().body();
                if (body2 != null) {
                    JSONObject jSONObject5 = new JSONObject(body2.string());
                    if (jSONObject5.has("Timestamp")) {
                        Curent_date_time = jSONObject5.getLong("Timestamp");
                    }
                }
            }
        } catch (IOException | JSONException e) {
            strArr[0] = "Kết nối kém, kiểm tra lại Internet";
            e.printStackTrace();
        }
    }

    public void lambda$login$2$Frag_Chaytrang(JSONObject jSONObject, String str, String str2, AtomicReference atomicReference, OkHttpClient okHttpClient) {
        try {
            jSONObject.put("Username", str);
            jSONObject.put("Password", str2);
            atomicReference.set(okHttpClient.newCall(new Request.Builder().url("https://id.lotusapi.com/auth/sign-in").header("Content-Type", "application/json").post(RequestBody.create(jSONObject.toString(), MediaType.parse("application/json"))).build()).execute().body().string());
            JSONObject resultObj = new JSONObject(atomicReference.toString());
            if (resultObj.has("IdToken")) {
                MainActivity.MyToken = resultObj.getString("IdToken");
                Laygia();
            } else {
                new Handler(Looper.getMainLooper()).post(() -> {
                    Toast.makeText(getActivity(), "Đăng nhập thất bại.", Toast.LENGTH_SHORT).show();
                    MainActivity.MyToken = "";
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override // android.support.v4.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.v = layoutInflater.inflate(R.layout.frag_chaytrang, viewGroup, false);
        init();
        this.db = new Database(getActivity());
        new MainActivity();
        this.ToDay = MainActivity.Get_date();
        this.radio_de.setOnCheckedChangeListener((compoundButton, z) -> {
            Frag_Chaytrang frag_Chaytrang;
            Frag_Chaytrang frag_Chaytrang2;
            if (radio_de.isChecked()) {
                li_loaide.setVisibility(View.VISIBLE);
                try {
                    Cursor GetData = db.GetData("Select sum((the_loai = 'dea')* diem) as de_a\n,sum((the_loai = 'deb')* diem) as de_b\n,sum((the_loai = 'det')* diem) as de_t\n,sum((the_loai = 'dec')* diem) as de_c\n,sum((the_loai = 'ded')* diem) as de_d\nFrom tbl_soctS \nWhere ngay_nhan = '" + ToDay + "'");
                    if (!GetData.moveToFirst() || GetData == null) {
                        DangXuat = "(the_loai = 'deb' or the_loai = 'det')";
                        GameType = 0;
                        if (MainActivity.MyToken.length() <= 0) {
                            return;
                        } else {
                            frag_Chaytrang = Frag_Chaytrang.this;
                        }
                    } else {
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
                            frag_Chaytrang2 = Frag_Chaytrang.this;
                        } else if (iArr[0] == 0 && iArr[1] == 0 && iArr[2] == 0 && iArr[3] == 0 && iArr[4] == 0) {
                            DangXuat = "(the_loai = 'deb' or the_loai = 'det')";
                            li_loaixi.setVisibility(View.GONE);
                            li_loaide.setVisibility(View.GONE);
                            radio_deb.setChecked(true);
                            frag_Chaytrang2 = Frag_Chaytrang.this;
                        } else {
                            DangXuat = "(the_loai = 'deb' or the_loai = 'det')";
                            li_loaixi.setVisibility(View.GONE);
                            li_loaide.setVisibility(View.VISIBLE);
                            radio_deb.setChecked(true);
                            frag_Chaytrang2 = Frag_Chaytrang.this;
                        }
                        frag_Chaytrang2.xem_RecycView();
                        if (!GetData.isClosed() && GetData != null && !GetData.isClosed()) {
                            GetData.close();
                        }
                        GameType = 0;
                        if (MainActivity.MyToken.length() <= 0) {
                            return;
                        } else {
                            frag_Chaytrang = Frag_Chaytrang.this;
                        }
                    }
                    frag_Chaytrang.Laygia();
                } catch (SQLException unused) {
                    DangXuat = "(the_loai = 'deb' or the_loai = 'det')";
                    GameType = 0;
                    if (MainActivity.MyToken.length() > 0) {
                        Laygia();
                    }
                }
            }
        });
        this.radio_dea.setOnCheckedChangeListener((compoundButton, z) -> {
            if (radio_dea.isChecked()) {
                DangXuat = "the_loai = 'dea'";
                li_loaixi.setVisibility(View.GONE);
                GameType = 21;
                Laygia();
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
        } catch (Exception unused) {
            Toast.makeText(getActivity(), "Đang copy dữ liệu bản mới!", Toast.LENGTH_LONG).show();
        }
        this.radio_deb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: tamhoang.ldpro4.Fragment.Frag_Chaytrang.3
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
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
        this.radio_dec.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: tamhoang.ldpro4.Fragment.Frag_Chaytrang.4
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
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
        this.radio_ded.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: tamhoang.ldpro4.Fragment.Frag_Chaytrang.5
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
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
        this.radio_lo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: tamhoang.ldpro4.Fragment.Frag_Chaytrang.6
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                Frag_Chaytrang frag_Chaytrang;
                int i;
                if (radio_lo.isChecked()) {
                    DangXuat = "the_loai = 'lo'";
                    li_loaixi.setVisibility(View.GONE);
                    li_loaide.setVisibility(View.GONE);
                    if (LoLive) {
                        frag_Chaytrang = Frag_Chaytrang.this;
                        i = 20;
                    } else {
                        frag_Chaytrang = Frag_Chaytrang.this;
                        i = 1;
                    }
                    frag_Chaytrang.GameType = i;
                    if (MainActivity.MyToken.length() > 0) {
                        Laygia();
                    }
                }
            }
        });
        this.radio_xi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: tamhoang.ldpro4.Fragment.Frag_Chaytrang.7
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
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
        this.radio_xi2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: tamhoang.ldpro4.Fragment.Frag_Chaytrang.8
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
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
        this.radio_xi3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: tamhoang.ldpro4.Fragment.Frag_Chaytrang.9
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
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
        this.radio_xi4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: tamhoang.ldpro4.Fragment.Frag_Chaytrang.10
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
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
        this.spr_trang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: tamhoang.ldpro4.Fragment.Frag_Chaytrang.11
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                spin_pointion = i;
                Frag_Chaytrang frag_Chaytrang = Frag_Chaytrang.this;
                frag_Chaytrang.login(frag_Chaytrang.mwebsite.get(i), mpassword.get(i));
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        if (this.mwebsite.size() > 0) {
            login(this.mwebsite.get(this.spin_pointion), this.mpassword.get(this.spin_pointion));
        }
        this.btn_Xuatso.setOnClickListener(new View.OnClickListener() { // from class: tamhoang.ldpro4.Fragment.Frag_Chaytrang.12
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                String str;
                String str2 = "";
                if (spin_pointion == -1) {
                    Toast.makeText(getActivity(), "Không có trang để xuất", Toast.LENGTH_SHORT).show();
                }
                if (MainActivity.MyToken.length() > 0) {
                    int i = GameType;
                    if (i != 0) {
                        if (i != 1) {
                            if (i == 2) {
                                the_loai = "xi2";
                                xuatDan = "Xi:";
                                donvi = "n ";
                                str2 = "the_loai = 'xi' AND length(so_chon) = 5";
                            } else if (i == 3) {
                                the_loai = "xi3";
                                xuatDan = "Xi:";
                                donvi = "n ";
                                str2 = "the_loai = 'xi' AND length(so_chon) = 8";
                            } else if (i != 4) {
                                switch (i) {
                                    case 21:
                                        the_loai = "dea";
                                        xuatDan = "De dau:";
                                        donvi = "n ";
                                        str = "the_loai = 'dea'";
                                        break;
                                    case 22:
                                        the_loai = "ded";
                                        xuatDan = "De giai 1:";
                                        donvi = "n ";
                                        str = "the_loai = 'ded'";
                                        break;
                                    case 23:
                                        the_loai = "dec";
                                        xuatDan = "De dau giai 1:";
                                        donvi = "n ";
                                        str = "the_loai = 'dec'";
                                        break;
                                }
                            } else {
                                the_loai = "xi4";
                                xuatDan = "Xi:";
                                donvi = "n ";
                                str2 = "the_loai = 'xi' AND length(so_chon) = 11";
                            }
                            Dieukien = str2;
                            TaoTinXi();
                            Dialog();
                        }
                        the_loai = "lo";
                        xuatDan = "Lo:";
                        donvi = "d ";
                        str = "the_loai = 'lo'";
                    } else {
                        the_loai = "deb";
                        xuatDan = "De:";
                        donvi = "n ";
                        str = "(the_loai = 'deb' or the_loai = 'det')";
                    }
                    Dieukien = str;
                    try {
                        TaoTinDe();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Dialog();
                }
            }
        });
        this.btn_MaXuat.setOnClickListener(new View.OnClickListener() { // from class: tamhoang.ldpro4.Fragment.Frag_Chaytrang.13
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Dialog2();
            }
        });
        Handler handler = new Handler();
        this.handler = handler;
        handler.postDelayed(this.runnable, 1000L);
        xemlv();
        return this.v;
    }

    @Override // android.support.v4.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        this.handler.removeCallbacks(this.runnable);
    }

    public void xem_RecycView() {
        String str;
        String str2;
        StringBuilder sb;
        String str3;
        String str4;
        String str5;
        String str6;
        String str7;
        Cursor GetData;
        int i;
        String str8;
        new MainActivity();
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
        String str9 = this.DangXuat;
        if (str9 == "(the_loai = 'deb' or the_loai = 'det')") {
            sb = new StringBuilder();
            sb.append("Select tbl_soctS.So_chon\n, Sum((tbl_soctS.type_kh = 1) * (100-tbl_soctS.diem_khachgiu)*diem_quydoi/100) as diem\n, so_om.Om_deB + sum(tbl_soctS.diem_dly_giu*tbl_soctS.diem_quydoi/100) as So_Om\n, Sum((tbl_soctS.type_kh =2)");
            if (this.spin_pointion > -1) {
                str8 = "*(tbl_soctS.ten_kh='" + this.mwebsite.get(this.spin_pointion) + "')";
            } else {
                str8 = "";
            }
            sb.append(str8);
            sb.append("* tbl_soctS.diem_quydoi) as chuyen\n, Sum((tbl_soctS.type_kh =1) * (100-tbl_soctS.diem_khachgiu-tbl_soctS.diem_dly_giu)*diem_quydoi/100) - Sum((tbl_soctS.type_kh =2) * tbl_soctS.diem_quydoi) - so_om.Om_deB as ton\n, so_nhay  From so_om Left Join tbl_soctS On tbl_soctS.so_chon = so_om.So\n Where tbl_soctS.ngay_nhan='");
            sb.append(Get_date);
            str4 = "' AND (tbl_soctS.the_loai='deb' OR tbl_soctS.the_loai='det') GROUP by so_om.So Order by ton DESC";
        } else if (str9 == "the_loai = 'lo'") {
            sb = new StringBuilder();
            sb.append("Select tbl_soctS.So_chon\n, Sum((tbl_soctS.type_kh = 1) * (100-tbl_soctS.diem_khachgiu)*diem_quydoi/100) as diem\n, so_om.Om_Lo + sum(tbl_soctS.diem_dly_giu*tbl_soctS.diem_quydoi/100) as So_Om\n, Sum((tbl_soctS.type_kh =2)");
            if (this.spin_pointion > -1) {
                str7 = "*(tbl_soctS.ten_kh='" + this.mwebsite.get(this.spin_pointion) + "')";
            } else {
                str7 = "";
            }
            sb.append(str7);
            sb.append(" * tbl_soctS.diem_quydoi) as chuyen\n, Sum((tbl_soctS.type_kh =1) * (100-tbl_soctS.diem_khachgiu-tbl_soctS.diem_dly_giu)*diem_quydoi/100) - Sum((tbl_soctS.type_kh =2) * tbl_soctS.diem_quydoi) - so_om.Om_Lo as ton\n, so_nhay  From so_om Left Join tbl_soctS On tbl_soctS.so_chon = so_om.So \n Where tbl_soctS.ngay_nhan='");
            sb.append(Get_date);
            str4 = "' AND tbl_soctS.the_loai='lo' \n GROUP by so_om.So Order by ton DESC";
        } else if (str9 == "the_loai = 'dea'") {
            sb = new StringBuilder();
            sb.append("Select tbl_soctS.So_chon\n, Sum((tbl_soctS.type_kh = 1) * (100-tbl_soctS.diem_khachgiu)*diem_quydoi/100) as diem\n, so_om.Om_DeA + sum(tbl_soctS.diem_dly_giu*tbl_soctS.diem_quydoi/100) as So_Om\n, Sum((tbl_soctS.type_kh =2)");
            if (this.spin_pointion > -1) {
                str6 = "*(tbl_soctS.ten_kh='" + this.mwebsite.get(this.spin_pointion) + "')";
            } else {
                str6 = "";
            }
            sb.append(str6);
            sb.append(" * tbl_soctS.diem_quydoi) as chuyen\n, Sum((tbl_soctS.type_kh =1) * (100-tbl_soctS.diem_khachgiu-tbl_soctS.diem_dly_giu)*diem_quydoi/100) - Sum((tbl_soctS.type_kh =2) * tbl_soctS.diem_quydoi) - so_om.Om_DeA as ton\n, so_nhay  From so_om Left Join tbl_soctS On tbl_soctS.so_chon = so_om.So \n Where tbl_soctS.ngay_nhan='");
            sb.append(Get_date);
            str4 = "' AND tbl_soctS.the_loai='dea' GROUP by so_chon Order by ton DESC";
        } else if (str9 == "the_loai = 'dec'") {
            sb = new StringBuilder();
            sb.append("Select tbl_soctS.So_chon\n, Sum((tbl_soctS.type_kh = 1) * (100-tbl_soctS.diem_khachgiu)*diem_quydoi/100) as diem\n, so_om.Om_DeC + sum(tbl_soctS.diem_dly_giu*tbl_soctS.diem_quydoi/100) as So_Om\n, Sum((tbl_soctS.type_kh =2)");
            if (this.spin_pointion > -1) {
                str5 = "*(tbl_soctS.ten_kh='" + this.mwebsite.get(this.spin_pointion) + "')";
            } else {
                str5 = "";
            }
            sb.append(str5);
            sb.append(" * tbl_soctS.diem_quydoi) as chuyen\n, Sum((tbl_soctS.type_kh =1) * (100-tbl_soctS.diem_khachgiu-tbl_soctS.diem_dly_giu)*diem_quydoi/100) - Sum((tbl_soctS.type_kh =2) * tbl_soctS.diem_quydoi) - so_om.Om_DeC as ton\n, so_nhay  From so_om Left Join tbl_soctS On tbl_soctS.so_chon = so_om.So \n Where tbl_soctS.ngay_nhan='");
            sb.append(Get_date);
            str4 = "' AND tbl_soctS.the_loai='dec' GROUP by so_chon Order by ton DESC";
        } else {
            if (str9 != "the_loai = 'ded'") {
                if (str9 == "the_loai = 'xi'") {
                    Cursor GetData2 = this.db.GetData("Select * From So_om WHERE ID = 1");
                    GetData2.moveToFirst();
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("SELECT so_chon, sum((type_kh =1)*(100-diem_khachgiu)*diem_quydoi)/100 AS diem, ((length(so_chon) = 5) * ");
                    sb2.append(GetData2.getString(7));
                    sb2.append(" +(length(so_chon) = 8) * ");
                    sb2.append(GetData2.getString(8));
                    sb2.append(" +(length(so_chon) = 11) * ");
                    sb2.append(GetData2.getString(9));
                    sb2.append(" + sum(diem_dly_giu*diem_quydoi/100)) AS Om, SUm((type_kh =2)");
                    if (this.spin_pointion > -1) {
                        str2 = "*(tbl_soctS.ten_kh='" + this.mwebsite.get(this.spin_pointion) + "')";
                    } else {
                        str2 = "";
                    }
                    sb2.append(str2);
                    sb2.append(" *diem) as chuyen , SUm((type_kh =1)*(100-diem_khachgiu-diem_dly_giu)*diem_quydoi/100)-SUm((type_kh =2)*diem) -  ((length(so_chon) = 5) * ");
                    sb2.append(GetData2.getString(7));
                    sb2.append(" +(length(so_chon) = 8) * ");
                    sb2.append(GetData2.getString(8));
                    sb2.append(" +(length(so_chon) = 11) * ");
                    sb2.append(GetData2.getString(9));
                    sb2.append(") AS ton, so_nhay   From tbl_soctS Where ngay_nhan='");
                    sb2.append(Get_date);
                    sb2.append("' AND the_loai='xi' AND");
                    sb2.append(this.lay_xien);
                    sb2.append("  GROUP by so_chon Order by ton DESC, diem DESC");
                    str = sb2.toString();
                    if (GetData2 != null && !GetData2.isClosed()) {
                        GetData2.close();
                    }
                } else {
                    str = null;
                }
                GetData = this.db.GetData(str);
                DecimalFormat decimalFormat = new DecimalFormat("###,###");
                if (this.spin_pointion <= -1) {
                    GetData = this.db.GetData("select * from tbl_chaytrang_acc Where Username = '" + this.mwebsite.get(this.spin_pointion) + "'");
                    GetData.moveToFirst();
                    try {
                        try {
                            JSONObject jSONObject = new JSONObject(GetData.getString(2));
                            int i2 = this.GameType;
                            if (i2 != 0) {
                                if (i2 != 1) {
                                    if (i2 == 2) {
                                        this.myMax = decimalFormat.format(jSONObject.getInt("max_xi2"));
                                        i = jSONObject.has("gia_xi2") ? jSONObject.getInt("gia_xi2") : 560;
                                    } else if (i2 == 3) {
                                        this.myMax = decimalFormat.format(jSONObject.getInt("max_xi3"));
                                        i = jSONObject.has("gia_xi3") ? jSONObject.getInt("gia_xi3") : 520;
                                    } else if (i2 != 4) {
                                        switch (i2) {
                                            case 21:
                                                this.myMax = decimalFormat.format(jSONObject.getInt("max_dea"));
                                                i = jSONObject.getInt("gia_dea");
                                                break;
                                            case 22:
                                                this.myMax = decimalFormat.format(jSONObject.getInt("max_ded"));
                                                i = jSONObject.getInt("gia_ded");
                                                break;
                                            case 23:
                                                this.myMax = decimalFormat.format(jSONObject.getInt("max_dec"));
                                                i = jSONObject.getInt("gia_dec");
                                                break;
                                        }
                                    } else {
                                        this.myMax = decimalFormat.format(jSONObject.getInt("max_xi4"));
                                        i = jSONObject.has("gia_xi4") ? jSONObject.getInt("gia_xi4") : 450;
                                    }
                                }
                                this.myMax = decimalFormat.format(jSONObject.getInt("max_lo"));
                                i = jSONObject.getInt("gia_lo");
                            } else {
                                this.myMax = decimalFormat.format(jSONObject.getInt("max_deb"));
                                i = jSONObject.getInt("gia_deb");
                            }
                            this.MaxChay = i;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } finally {
                        GetData.close();
                    }
                } else {
                    this.myMax = "0";
                }
                if (GetData != null) {
                    if (this.radio_xi.isChecked()) {
                        while (GetData.moveToNext()) {
                            try {
                                this.mSo.add(GetData.getString(0));
                                this.mTienNhan.add(decimalFormat.format(GetData.getInt(1)));
                                this.mTienOm.add(decimalFormat.format(GetData.getInt(2)));
                                this.mTienchuyen.add(decimalFormat.format(GetData.getInt(3)));
                                this.mTienTon.add(decimalFormat.format(GetData.getInt(4)));
                                this.mNhay.add(Integer.valueOf(GetData.getInt(5)));
                                this.mMax.add(this.myMax);
                                if (this.radio_xi2.isChecked()) {
                                    String[] split = GetData.getString(0).split(",");
                                    if (split.length >= 2) {
                                        int i3 = !this.jsonGia.has(split[0]) ? this.Price : this.Price + this.jsonGia.getInt(split[0]);
                                        int i4 = !this.jsonGia.has(split[1]) ? this.Price : this.jsonGia.getInt(split[1]) + this.Price;
                                        List<String> list = this.mGia;
                                        StringBuilder sb3 = new StringBuilder();
                                        sb3.append("");
                                        int i5 = i3 + i4;
                                        sb3.append(i5 / 2);
                                        list.add(sb3.toString());
                                        this.jsonTienxien.put(GetData.getString(0), i5 / 2);
                                    }
                                }
                                if (this.radio_xi3.isChecked()) {
                                    String[] split2 = GetData.getString(0).split(",");
                                    if (split2.length >= 3) {
                                        int i6 = !this.jsonGia.has(split2[0]) ? this.Price : this.Price + this.jsonGia.getInt(split2[0]);
                                        int i7 = !this.jsonGia.has(split2[1]) ? this.Price : this.Price + this.jsonGia.getInt(split2[1]);
                                        int i8 = !this.jsonGia.has(split2[2]) ? this.Price : this.jsonGia.getInt(split2[2]) + this.Price;
                                        List<String> list2 = this.mGia;
                                        StringBuilder sb4 = new StringBuilder();
                                        sb4.append("");
                                        int i9 = i6 + i7 + i8;
                                        sb4.append(i9 / 3);
                                        list2.add(sb4.toString());
                                        this.jsonTienxien.put(GetData.getString(0), i9 / 3);
                                    }
                                }
                                if (this.radio_xi4.isChecked()) {
                                    String[] split3 = GetData.getString(0).split(",");
                                    if (split3.length >= 4) {
                                        int i10 = !this.jsonGia.has(split3[0]) ? this.Price : this.Price + this.jsonGia.getInt(split3[0]);
                                        int i11 = !this.jsonGia.has(split3[1]) ? this.Price : this.Price + this.jsonGia.getInt(split3[1]);
                                        int i12 = !this.jsonGia.has(split3[2]) ? this.Price : this.Price + this.jsonGia.getInt(split3[2]);
                                        int i13 = !this.jsonGia.has(split3[3]) ? this.Price : this.jsonGia.getInt(split3[3]) + this.Price;
                                        List<String> list3 = this.mGia;
                                        StringBuilder sb5 = new StringBuilder();
                                        sb5.append("");
                                        int i14 = i10 + i11 + i12 + i13;
                                        sb5.append(i14 / 4);
                                        list3.add(sb5.toString());
                                        this.jsonTienxien.put(GetData.getString(0), i14 / 4);
                                    }
                                }
                            } catch (JSONException e2) {
                                e2.printStackTrace();
                            }
                        }
                    } else {
                        while (GetData.moveToNext()) {
                            this.mSo.add(GetData.getString(0));
                            this.mTienNhan.add(decimalFormat.format(GetData.getInt(1)));
                            this.mTienOm.add(decimalFormat.format(GetData.getInt(2)));
                            this.mTienchuyen.add(decimalFormat.format(GetData.getInt(3)));
                            this.mTienTon.add(decimalFormat.format(GetData.getInt(4)));
                            this.mNhay.add(Integer.valueOf(GetData.getInt(5)));
                            if (this.jsonGia.has(GetData.getString(0))) {
                                try {
                                    this.mGia.add("" + (this.Price + this.jsonGia.getInt(GetData.getString(0))));
                                } catch (JSONException e3) {
                                    e3.printStackTrace();
                                }
                            } else {
                                this.mGia.add(this.Price + "");
                            }
                            this.mMax.add(this.myMax);
                        }
                    }
                    if (GetData != null && !GetData.isClosed()) {
                    }
                }
                if (getActivity() == null) {
                    this.lview.setAdapter((ListAdapter) new So_OmAdapter(getActivity(), 2131427395, this.mSo));
                    return;
                }
                return;
            }
            sb = new StringBuilder();
            sb.append("Select tbl_soctS.So_chon\n, Sum((tbl_soctS.type_kh = 1) * (100-tbl_soctS.diem_khachgiu)*diem_quydoi/100) as diem\n, so_om.Om_DeD + sum(tbl_soctS.diem_dly_giu*tbl_soctS.diem_quydoi/100) as So_Om\n, Sum((tbl_soctS.type_kh =2)");
            if (this.spin_pointion > -1) {
                str3 = "*(tbl_soctS.ten_kh='" + this.mwebsite.get(this.spin_pointion) + "')";
            } else {
                str3 = "";
            }
            sb.append(str3);
            sb.append(" * tbl_soctS.diem_quydoi) as chuyen\n, Sum((tbl_soctS.type_kh =1) * (100-tbl_soctS.diem_khachgiu-tbl_soctS.diem_dly_giu)*diem_quydoi/100) - Sum((tbl_soctS.type_kh =2) * tbl_soctS.diem_quydoi) - so_om.Om_DeD as ton\n, so_nhay  From so_om Left Join tbl_soctS On tbl_soctS.so_chon = so_om.So \n Where tbl_soctS.ngay_nhan='");
            sb.append(Get_date);
            str4 = "' AND tbl_soctS.the_loai='ded' GROUP by so_chon Order by ton DESC";
        }
        sb.append(str4);
        str = sb.toString();
        GetData = this.db.GetData(str);
    }

    public void xemlv() {
        if (this.DangXuat != null) {
            xem_RecycView();
        } else {
            this.radio_de.setChecked(true);
        }
    }
}
