package tamhoang.ldpro4.ui.fragment;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

import tamhoang.ldpro4.formula.Congthuc;
import tamhoang.ldpro4.ui.home.MainActivity;
import tamhoang.ldpro4.ui.notification.NotificationReader;
import tamhoang.ldpro4.R;
import tamhoang.ldpro4.data.Database;
import tamhoang.ldpro4.ui.widget.rangeseekbar.OnRangeChangedListener;
import tamhoang.ldpro4.ui.widget.rangeseekbar.RangeSeekBar;

/* loaded from: classes.dex */
public class Frag_CanChuyen extends Fragment {
    Button btn_Xuatso;
    CheckBox check_x2;
    CheckBox check_x3;
    CheckBox check_x4;
    CheckBox check_xn;
    Database db;
    EditText edt_tien;
    Handler handler;
    JSONObject jsonKhongmax;
    String lay_x2;
    String lay_x3;
    String lay_x4;
    LinearLayout layout;
    LinearLayout li_loaide;
    LinearLayout ln_xi;
    ListView lview;
    RadioButton radio_bc;
    RadioButton radio_de;
    RadioButton radio_dea;
    RadioButton radio_deb;
    RadioButton radio_dec;
    RadioButton radio_ded;
    RadioButton radio_lo;
    RadioButton radio_xi;
    RangeSeekBar rangeSeekBar;
    String sapxep;
    public View v;
    String xuatDan;
    int min = 0;
    int max = 100;
    String DangXuat = null;
    int mSpiner = 0;
    public List<String> mSo = new ArrayList<>();
    public List<String> mTienNhan = new ArrayList<>();
    public List<String> mTienOm = new ArrayList<>();
    public List<String> mTienchuyen = new ArrayList<>();
    public List<String> mTienTon = new ArrayList<>();
    public List<Integer> mNhay = new ArrayList<>();
    public List<String> mContact = new ArrayList<>();
    public List<String> mMobile = new ArrayList<>();
    public List<String> mKhongMax = new ArrayList<>();
    public List<String> mAppuse = new ArrayList<>();
    boolean Dachuyen = false;
    private Runnable runnable = new Runnable() { // from class: tamhoang.ldpro4.Fragment.Frag_CanChuyen.16
        @Override // java.lang.Runnable
        public void run() {
            new MainActivity();
            if (MainActivity.sms) {
                xemlv();
                MainActivity.sms = false;
            }
            handler.postDelayed(this, 1000L);
        }
    };


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
            TextView textView;
            StringBuilder stringBuilder = new StringBuilder();
            String str = "";
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ViewHolder viewHolder = new ViewHolder();
            if (view == null) {
                view = layoutInflater.inflate(R.layout.frag_canchuyen, (ViewGroup) null);
                viewHolder.tview5 =  view.findViewById(R.id.Tv_so);
                viewHolder.tview7 =  view.findViewById(R.id.tv_diemBc1);
                viewHolder.tview8 =  view.findViewById(R.id.tv_diemBca);
                viewHolder.tview1 =  view.findViewById(R.id.tv_anxi);
                viewHolder.tview4 =  view.findViewById(R.id.tv_diemChuyen);
                // TODO: 7/4/2024 check spread id
//                viewHolder.tview2 =  view.findViewById(R.id.spread);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            if (mNhay.get(i).intValue() > 0) {
//                viewHolder.tview5.setTextColor(SupportMenu.CATEGORY_MASK);
//                viewHolder.tview7.setTextColor(SupportMenu.CATEGORY_MASK);
//                viewHolder.tview8.setTextColor(SupportMenu.CATEGORY_MASK);
//                viewHolder.tview1.setTextColor(SupportMenu.CATEGORY_MASK);
//                viewHolder.tview4.setTextColor(SupportMenu.CATEGORY_MASK);
                if (mNhay.get(i) == 1) {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(mSo.get(i));
                    str = "*";
                } else if (mNhay.get(i) == 2) {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(mSo.get(i));
                    str = "**";
                } else if (mNhay.get(i) == 3) {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(mSo.get(i));
                    str = "***";
                } else if (mNhay.get(i) == 4) {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(mSo.get(i));
                    str = "****";
                } else if (mNhay.get(i) == 5) {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(mSo.get(i));
                    str = "*****";
                } else {
                    if (mNhay.get(i) == 6) {
                        stringBuilder = new StringBuilder();
                        stringBuilder.append(mSo.get(i));
                        str = "******";
                    }
                    viewHolder.tview7.setText(mTienNhan.get(i));
                    viewHolder.tview8.setText(mTienOm.get(i));
                    viewHolder.tview1.setText(mTienchuyen.get(i));
                    viewHolder.tview4.setText(mTienTon.get(i));
                    textView = viewHolder.tview2;
                }
                stringBuilder.append(str);
                viewHolder.tview5.setText(stringBuilder.toString());
                viewHolder.tview7.setText(mTienNhan.get(i));
                viewHolder.tview8.setText(mTienOm.get(i));
                viewHolder.tview1.setText(mTienchuyen.get(i));
                viewHolder.tview4.setText(mTienTon.get(i));
                textView = viewHolder.tview2;
            } else {
//                viewHolder.tview5.setTextColor(ViewCompat.MEASURED_STATE_MASK);
//                viewHolder.tview7.setTextColor(ViewCompat.MEASURED_STATE_MASK);
//                viewHolder.tview8.setTextColor(ViewCompat.MEASURED_STATE_MASK);
//                viewHolder.tview1.setTextColor(ViewCompat.MEASURED_STATE_MASK);
//                viewHolder.tview4.setTextColor(ViewCompat.MEASURED_STATE_MASK);
                viewHolder.tview5.setText(mSo.get(i));
                viewHolder.tview7.setText(mTienNhan.get(i));
                viewHolder.tview8.setText(mTienOm.get(i));
                viewHolder.tview1.setText(mTienchuyen.get(i));
                viewHolder.tview4.setText(mTienTon.get(i));
                textView = viewHolder.tview2;
            }
            String sb = (i + 1) + "";
            textView.setText(sb);
            return view;
        }
    }

    public void Dialog(int i) {
        List<String> list;
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.frag_setting3);
        dialog.getWindow().setLayout(-1, -2);
        final String replaceAll = this.xuatDan.replaceAll(",x", "x");
        this.Dachuyen = false;
        Spinner spinner = dialog.findViewById(R.id.sp_hetgio);
        final EditText editText = dialog.findViewById(R.id.edt_Gialo);
        View btnActive = dialog.findViewById(R.id.btn_active);
        editText.setText("");
        editText.setText(this.xuatDan.replaceAll(",x", "x"));
        try {
            Cursor GetData = this.db.GetData("Select * From tbl_kh_new WHERE type_kh <> 1 ORDER BY ten_kh");
            this.mContact.clear();
            this.mMobile.clear();
            this.mKhongMax.clear();
            this.mAppuse.clear();
            while (GetData.moveToNext()) {
                if (GetData.getString(2).indexOf("sms") <= -1 && GetData.getString(2).indexOf("TL") <= -1) {
                    if (MainActivity.arr_TenKH.indexOf(GetData.getString(1)) > -1) {
                        this.mContact.add(GetData.getString(0));
                        this.mMobile.add(GetData.getString(1));
                        this.mKhongMax.add(GetData.getString(6));
                        list = this.mAppuse;
                        list.add(GetData.getString(2));
                    }
                }
                this.mContact.add(GetData.getString(0));
                this.mMobile.add(GetData.getString(1));
                this.mKhongMax.add(GetData.getString(6));
                list = this.mAppuse;
                list.add(GetData.getString(2));
            }
            if (GetData != null && !GetData.isClosed()) {
                GetData.close();
            }
            spinner.setAdapter(new ArrayAdapter(getActivity(), R.layout.spinner_item, this.mContact));
        } catch (SQLException unused) {
        }
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: tamhoang.ldpro4.Fragment.Frag_CanChuyen.21
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> adapterView, View view, int i2, long j) {
                EditText editText2;
                String str;
                mSpiner = i2;
                try {
                    jsonKhongmax = new JSONObject(mKhongMax.get(mSpiner));
                    if (radio_deb.isChecked() && radio_de.isChecked() && jsonKhongmax.getString("danDe").length() > 0) {
                        editText2 = editText;
                        Frag_CanChuyen frag_CanChuyen = Frag_CanChuyen.this;
                        str = frag_CanChuyen.TaoTinDe(frag_CanChuyen.mContact.get(mSpiner));
                    } else if (radio_lo.isChecked() && jsonKhongmax.getString("danLo").length() > 0) {
                        editText2 = editText;
                        Frag_CanChuyen frag_CanChuyen2 = Frag_CanChuyen.this;
                        str = frag_CanChuyen2.TaoTinLo(frag_CanChuyen2.mContact.get(mSpiner));
                    } else if (radio_xi.isChecked() && (jsonKhongmax.getInt("xien2") > 0 || jsonKhongmax.getInt("xien3") > 0 || jsonKhongmax.getInt("xien4") > 0)) {
                        editText2 = editText;
                        Frag_CanChuyen frag_CanChuyen3 = Frag_CanChuyen.this;
                        str = frag_CanChuyen3.TaoTinXi(frag_CanChuyen3.mContact.get(mSpiner));
                    } else if (!radio_bc.isChecked() || jsonKhongmax.getInt("cang") <= 0) {
                        editText2 = editText;
                        str = replaceAll;
                    } else {
                        editText2 = editText;
                        Frag_CanChuyen frag_CanChuyen4 = Frag_CanChuyen.this;
                        str = frag_CanChuyen4.TaoTinCang(frag_CanChuyen4.mContact.get(mSpiner));
                    }
                    editText2.setText(str);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        btnActive.setOnClickListener(new View.OnClickListener() { // from class: tamhoang.ldpro4.Fragment.Frag_CanChuyen.22
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                int i2;
                String str;
                Toast makeText;
                String[] strArr;
                StringBuilder sb = new StringBuilder();
                String str2 = "";
                String str3 = "";
                String str4 = "";
                StringBuilder sb2;
                new MainActivity();
                String Get_date = MainActivity.Get_date();
                int i3 = 0;
                try {
                    i2 = MainActivity.jSon_Setting.getInt("gioi_han_tin");
                } catch (JSONException e) {
                    e.printStackTrace();
                    i2 = 0;
                }
                int i4 = 2000;
                if (i2 == 1) {
                    i4 = 3000;
                } else if (i2 == 2) {
                    i4 = 155;
                } else if (i2 == 3) {
                    i4 = 315;
                } else if (i2 == 4) {
                    i4 = 475;
                } else if (i2 == 5) {
                    i4 = 995;
                }
                String str5 = "";
                if (mMobile.size() <= 0 || editText.getText().toString().length() <= 0 || Dachuyen) {
                    str = "";
                    if (editText.getText().toString().length() != 0) {
                        if (Dachuyen) {
                            dialog.cancel();
                        } else {
                            makeText = Toast.makeText(getActivity(), "Chưa có chủ để chuyển tin!", Toast.LENGTH_LONG);
                        }
                    }
                    xemlv();
                    min = 0;
                    max = 100;
                    rangeSeekBar.setRange(0, 100);
                    edt_tien.setText(str);
                }
                Dachuyen = true;
                String trim = editText.getText().toString().replaceAll("'", " ").trim();
                editText.setText("");
                dialog.dismiss();
                if (trim.trim().length() < i4) {
                    Cursor GetData2 = db.GetData("Select max(so_tin_nhan) from tbl_tinnhanS WHERE ngay_nhan = '" + Get_date + "' AND so_dienthoai = '" + mMobile.get(mSpiner) + "' AND type_kh = 2");
                    GetData2.moveToFirst();
                    Xulytin(GetData2.getInt(0) + 1, trim.replaceAll("'", " ").trim(), 1);
                    if (GetData2 != null) {
                        GetData2.close();
                    }
                    str = "";
                } else {
                    Cursor GetData3 = db.GetData("Select max(so_tin_nhan) from tbl_tinnhanS WHERE ngay_nhan = '" + Get_date + "' AND so_dienthoai = '" + mMobile.get(mSpiner) + "' AND type_kh = 2");
                    GetData3.moveToFirst();
                    int i5 = GetData3.getInt(0) + 1;
                    String str6 = "Lo:";
                    if (trim.substring(0, 3).indexOf("De") > -1) {
                        strArr = trim.replaceAll("De:", "").split(" ");
                        str6 = "De:";
                    } else if (trim.substring(0, 3).indexOf("Lo") > -1) {
                        strArr = trim.replaceAll("Lo:", "").split(" ");
                    } else if (trim.substring(0, 5).indexOf("Cang") > -1) {
                        strArr = trim.replaceAll("Cang:\n", "").split(" ");
                        str6 = "Cang:";
                    } else if (trim.substring(0, 3).indexOf("Xi") > -1) {
                        strArr = trim.replaceAll("Xien:\n", "").replaceAll("d:", "0").replaceAll("\n", " ").split(" ");
                        str6 = "Xien:";
                    } else {
                        str6 = "";
                        strArr = null;
                    }
                    if (str6 != "Xien:") {
                        String str7 = "";
                        int i6 = 0;
                        while (i6 < strArr.length) {
                            String str8 = "x";
                            String substring = strArr[i6].substring(i3, strArr[i6].indexOf("x"));
                            String replaceAll2 = strArr[i6].substring(strArr[i6].indexOf("x")).replaceAll(",", str5);
                            String[] split = substring.split(",");
                            String str9 = str5;
                            while (i3 < split.length) {
                                String replaceAll3 = str7.replaceAll(",x", str8);
                                if (replaceAll3.length() != 0) {
                                    if (replaceAll3.length() + replaceAll2.length() + replaceAll2.length() >= i4) {
                                        if (i3 > 0) {
                                            replaceAll3 = replaceAll3 + replaceAll2;
                                        }
                                        str3 = str8;
                                        Xulytin(i5, replaceAll3, 1);
                                        i5++;
                                        if (i3 < split.length - 1) {
                                            str7 = str6 + "\n" + split[i3] + ",";
                                            i3++;
                                            str8 = str3;
                                        } else {
                                            str4 = str6 + "\n" + split[i3] + "," + replaceAll2 + " ";
                                        }
                                    } else if (i3 < split.length - 1) {
                                        str7 = replaceAll3 + split[i3] + ",";
                                    } else {
                                        str4 = replaceAll3 + split[i3] + "," + replaceAll2 + " ";
                                    }
                                    str7 = str4;
                                    break;
                                }
                                if (split.length == 1) {
                                    sb2 = new StringBuilder();
                                    sb2.append(str6);
                                    sb2.append("\n");
                                    sb2.append(split[i3]);
                                    sb2.append(",");
                                    sb2.append(replaceAll2);
                                    sb2.append(" ");
                                } else {
                                    sb2 = new StringBuilder();
                                    sb2.append(str6);
                                    sb2.append("\n");
                                    sb2.append(split[i3]);
                                    sb2.append(",");
                                }
                                str7 = sb2.toString();
                                str3 = str8;
                                i3++;
                                str8 = str3;
                            }
                            i6++;
                            str5 = str9;
                            i3 = 0;
                        }
                        str = str5;
                        if (str7.length() > 0) {
                            Xulytin(i5, str7, 1);
                        }
                    } else {
                        str = "";
                        String str10 = str;
                        for (int i7 = 0; i7 < strArr.length; i7++) {
                            if (str10.length() == 0) {
                                sb = new StringBuilder();
                                sb.append(str6);
                                sb.append("\n");
                                str2 = strArr[i7];
                            } else if (str10.length() + strArr[i7].length() < i4) {
                                str10 = str10 + strArr[i7] + " ";
                            } else {
                                Xulytin(i5, str10, 1);
                                i5++;
                                sb = new StringBuilder();
                                sb.append(str6);
                                sb.append("\n");
                                str2 = strArr[i7];
                            }
                            sb.append(str2);
                            sb.append(" ");
                            str10 = sb.toString();
                        }
                        if (str10.length() > 0) {
                            Xulytin(i5, str10, 1);
                        }
                    }
                    if (GetData3 != null) {
                        GetData3.close();
                    }
                }
                makeText = Toast.makeText(getActivity(), "Đã chuyển tin!", Toast.LENGTH_LONG);
                makeText.show();
                xemlv();
                min = 0;
                max = 100;
                rangeSeekBar.setRange(min, max);
                edt_tien.setText(str);
            }
        });
        dialog.getWindow().setLayout(-1, -2);
        dialog.setCancelable(true);
        dialog.setTitle("Xem dạng:");
        dialog.show();
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0071, code lost:
    
        r0 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x0078, code lost:
    
        if (r0 >= r15.mSo.size()) goto L90;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x007a, code lost:
    
        r6 = r15.mSo.get(r0);
        r9 = r15.jsonKhongmax.getInt("cang");
        r11 = java.lang.Integer.parseInt(r15.mTienTon.get(r0).replace(".", ""));
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x00a3, code lost:
    
        if (r5.has(r6) == false) goto L34;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x00a5, code lost:
    
        if (r11 <= 0) goto L34;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x00a7, code lost:
    
        r12 = new org.json.JSONObject();
        r13 = r5.getJSONObject(r6).getDouble("Da_chuyen");
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x00b4, code lost:
    
        r7 = r11;
        java.lang.Double.isNaN(r7);
        java.lang.Double.isNaN(r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x00be, code lost:
    
        if ((r13 + r7) > r9) goto L28;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x00c0, code lost:
    
        r12.put("So_chon", r6);
        r12.put("Da_chuyen", r5.getJSONObject(r6).getInt("Da_chuyen") + r11);
        r12.put("Se_chuyen", r11);
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x00f2, code lost:
    
        r5.put(r6, r12);
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0118, code lost:
    
        r0 = r0 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x00d3, code lost:
    
        r12.put("So_chon", r6);
        r12.put("Da_chuyen", r9);
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x00e1, code lost:
    
        r7 = r5.getJSONObject(r6).getInt("Da_chuyen");
        java.lang.Double.isNaN(r9);
        java.lang.Double.isNaN(r7);
        java.lang.Double.isNaN(r9);
        java.lang.Double.isNaN(r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x00ef, code lost:
    
        r12.put("Se_chuyen", r9 - r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x00f8, code lost:
    
        r7 = new org.json.JSONObject();
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0100, code lost:
    
        if (r11 > r9) goto L37;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0102, code lost:
    
        r7.put("So_chon", r6);
        r7.put("Da_chuyen", r11);
        r7.put("Se_chuyen", r11);
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0115, code lost:
    
        r5.put(r6, r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x010c, code lost:
    
        r7.put("So_chon", r6);
        r7.put("Da_chuyen", r9);
        r7.put("Se_chuyen", r9);
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x00f6, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x011e, code lost:
    
        r0.printStackTrace();
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x006e, code lost:
    
        r6.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:94:0x006c, code lost:
    
        if (r6.isClosed() != false) goto L18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x005e, code lost:
    
        if (r6.isClosed() == false) goto L17;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public String TaoTinCang(String str) {
        JSONObject jSONObject = new JSONObject();
        new MainActivity();
        Cursor GetData = this.db.GetData("Select so_chon, Sum(diem) FROM tbl_soctS Where ten_kh = '" + str + "' AND type_kh = 2 AND the_loai = 'bc' AND ngay_nhan = '" + MainActivity.Get_date() + "' Group by so_chon");
        while (GetData.moveToNext()) {
            try {
                try {
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.put("Da_chuyen", GetData.getInt(1));
                    jSONObject2.put("Se_chuyen", 0);
                    jSONObject.put(GetData.getString(0), jSONObject2);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (Throwable th) {
                if (!GetData.isClosed()) {
                    GetData.close();
                }
                throw th;
            }
        }
        Iterator<String> keys = jSONObject.keys();
        ArrayList arrayList = new ArrayList();
        while (keys.hasNext()) {
            String next = keys.next();
            try {
                if (jSONObject.getJSONObject(next).getInt("Se_chuyen") > 0) {
                    arrayList.add(jSONObject.getJSONObject(next));
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
        Collections.sort(arrayList, new Comparator<JSONObject>() { // from class: tamhoang.ldpro4.Fragment.Frag_CanChuyen.19
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
        this.xuatDan = "Cang:";
        int i = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < arrayList.size(); i3++) {
            try {
                JSONObject jSONObject3 = (JSONObject) arrayList.get(i3);
                int i4 = jSONObject3.getInt("Se_chuyen");
                if (i4 > 0) {
                    if (i2 > i4) {
                        this.xuatDan += "x" + i2 + "n ";
                        this.xuatDan += jSONObject3.getString("So_chon") + ",";
                        i = 0;
                    } else {
                        this.xuatDan += jSONObject3.getString("So_chon") + ",";
                    }
                    i++;
                    i2 = i4;
                }
            } catch (JSONException e3) {
                e3.printStackTrace();
            }
        }
        if (this.xuatDan.length() > 4 && i > 0) {
            this.xuatDan += "x" + i2 + "n ";
        }
        return this.xuatDan;
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0071, code lost:
    
        r0 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x0078, code lost:
    
        if (r0 >= r15.mSo.size()) goto L93;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x007a, code lost:
    
        r6 = r15.mSo.get(r0);
        r9 = new org.json.JSONObject(r15.jsonKhongmax.getString("soDe"));
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x009b, code lost:
    
        if (r9.has(r15.mSo.get(r0)) == false) goto L26;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x009d, code lost:
    
        r9 = r9.getInt(r15.mSo.get(r0));
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x00b3, code lost:
    
        r11 = java.lang.Integer.parseInt(r15.mTienTon.get(r0).replace(".", ""));
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x00cb, code lost:
    
        if (r5.has(r6) == false) goto L38;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x00cd, code lost:
    
        if (r11 <= 0) goto L38;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x00cf, code lost:
    
        r12 = new org.json.JSONObject();
        r13 = r5.getJSONObject(r6).getDouble("Da_chuyen");
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x00dc, code lost:
    
        r7 = r11;
        java.lang.Double.isNaN(r7);
        java.lang.Double.isNaN(r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x00e6, code lost:
    
        if ((r13 + r7) > r9) goto L34;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x00e8, code lost:
    
        r12.put("So_chon", r6);
        r12.put("Da_chuyen", r5.getJSONObject(r6).getInt("Da_chuyen") + r11);
        r12.put("Se_chuyen", r11);
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0114, code lost:
    
        r5.put(r6, r12);
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0138, code lost:
    
        r0 = r0 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x00fb, code lost:
    
        r12.put("So_chon", r6);
        r12.put("Da_chuyen", r9);
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0109, code lost:
    
        r7 = r5.getJSONObject(r6).getInt("Da_chuyen");
        java.lang.Double.isNaN(r7);
        java.lang.Double.isNaN(r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0111, code lost:
    
        r12.put("Se_chuyen", r9 - r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0118, code lost:
    
        r7 = new org.json.JSONObject();
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0120, code lost:
    
        if (r11 > r9) goto L41;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0122, code lost:
    
        r7.put("So_chon", r6);
        r7.put("Da_chuyen", r11);
        r7.put("Se_chuyen", r11);
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0135, code lost:
    
        r5.put(r6, r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x012c, code lost:
    
        r7.put("So_chon", r6);
        r7.put("Da_chuyen", r9);
        r7.put("Se_chuyen", r9);
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x00ae, code lost:
    
        r9 = 100000.0d;
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x00ab, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x013e, code lost:
    
        r0.printStackTrace();
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x006e, code lost:
    
        r6.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:97:0x006c, code lost:
    
        if (r6.isClosed() != false) goto L18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x005e, code lost:
    
        if (r6.isClosed() == false) goto L17;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public String TaoTinDe(String str) {
        JSONObject jSONObject = new JSONObject();
        new MainActivity();
        Cursor GetData = this.db.GetData("Select so_chon, Sum(diem) FROM tbl_soctS Where ten_kh = '" + str + "' AND type_kh = 2 AND (the_loai = 'deb' or the_loai = 'det') AND ngay_nhan = '" + MainActivity.Get_date() + "' Group by so_chon");
        while (GetData.moveToNext()) {
            try {
                try {
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.put("Da_chuyen", GetData.getInt(1));
                    jSONObject2.put("Se_chuyen", 0);
                    jSONObject.put(GetData.getString(0), jSONObject2);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (Throwable th) {
                if (!GetData.isClosed()) {
                    GetData.close();
                }
                throw th;
            }
        }
        Iterator<String> keys = jSONObject.keys();
        ArrayList arrayList = new ArrayList();
        while (keys.hasNext()) {
            String next = keys.next();
            try {
                if (jSONObject.getJSONObject(next).getInt("Se_chuyen") > 0) {
                    arrayList.add(jSONObject.getJSONObject(next));
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
        Collections.sort(arrayList, new Comparator<JSONObject>() { // from class: tamhoang.ldpro4.Fragment.Frag_CanChuyen.17
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
        this.xuatDan = "De:";
        int i = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < arrayList.size(); i3++) {
            try {
                JSONObject jSONObject3 = (JSONObject) arrayList.get(i3);
                int i4 = jSONObject3.getInt("Se_chuyen");
                if (i4 > 0) {
                    if (i2 > i4) {
                        this.xuatDan += "x" + i2 + "n ";
                        this.xuatDan += jSONObject3.getString("So_chon") + ",";
                        i = 0;
                    } else {
                        this.xuatDan += jSONObject3.getString("So_chon") + ",";
                    }
                    i++;
                    i2 = i4;
                }
            } catch (JSONException e3) {
                e3.printStackTrace();
            }
        }
        if (this.xuatDan.length() > 4 && i > 0) {
            this.xuatDan += "x" + i2 + "n ";
        }
        return this.xuatDan;
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0071, code lost:
    
        r0 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x0078, code lost:
    
        if (r0 >= r15.mSo.size()) goto L93;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x007a, code lost:
    
        r6 = r15.mSo.get(r0);
        r9 = new org.json.JSONObject(r15.jsonKhongmax.getString("soLo"));
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x009b, code lost:
    
        if (r9.has(r15.mSo.get(r0)) == false) goto L26;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x009d, code lost:
    
        r9 = r9.getInt(r15.mSo.get(r0));
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x00b3, code lost:
    
        r11 = java.lang.Integer.parseInt(r15.mTienTon.get(r0).replace(".", ""));
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x00cb, code lost:
    
        if (r5.has(r6) == false) goto L38;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x00cd, code lost:
    
        if (r11 <= 0) goto L38;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x00cf, code lost:
    
        r12 = new org.json.JSONObject();
        r13 = r5.getJSONObject(r6).getDouble("Da_chuyen");
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x00dc, code lost:
    
        r7 = r11;
        java.lang.Double.isNaN(r7);
        java.lang.Double.isNaN(r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x00e6, code lost:
    
        if ((r13 + r7) > r9) goto L34;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x00e8, code lost:
    
        r12.put("So_chon", r6);
        r12.put("Da_chuyen", r5.getJSONObject(r6).getInt("Da_chuyen") + r11);
        r12.put("Se_chuyen", r11);
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0114, code lost:
    
        r5.put(r6, r12);
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0138, code lost:
    
        r0 = r0 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x00fb, code lost:
    
        r12.put("So_chon", r6);
        r12.put("Da_chuyen", r9);
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0109, code lost:
    
        r7 = r5.getJSONObject(r6).getInt("Da_chuyen");
        java.lang.Double.isNaN(r7);
        java.lang.Double.isNaN(r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0111, code lost:
    
        r12.put("Se_chuyen", r9 - r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0118, code lost:
    
        r7 = new org.json.JSONObject();
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0120, code lost:
    
        if (r11 > r9) goto L41;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0122, code lost:
    
        r7.put("So_chon", r6);
        r7.put("Da_chuyen", r11);
        r7.put("Se_chuyen", r11);
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0135, code lost:
    
        r5.put(r6, r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x012c, code lost:
    
        r7.put("So_chon", r6);
        r7.put("Da_chuyen", r9);
        r7.put("Se_chuyen", r9);
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x00ae, code lost:
    
        r9 = 100000.0d;
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x00ab, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x013e, code lost:
    
        r0.printStackTrace();
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x006e, code lost:
    
        r6.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:97:0x006c, code lost:
    
        if (r6.isClosed() != false) goto L18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x005e, code lost:
    
        if (r6.isClosed() == false) goto L17;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public String TaoTinLo(String str) {
        JSONObject jSONObject = new JSONObject();
        new MainActivity();
        Cursor GetData = this.db.GetData("Select so_chon, Sum(diem) FROM tbl_soctS Where ten_kh = '" + str + "' AND type_kh = 2 AND the_loai = 'lo' AND ngay_nhan = '" + MainActivity.Get_date() + "' Group by so_chon");
        while (GetData.moveToNext()) {
            try {
                try {
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.put("Da_chuyen", GetData.getInt(1));
                    jSONObject2.put("Se_chuyen", 0);
                    jSONObject.put(GetData.getString(0), jSONObject2);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (Throwable th) {
                if (!GetData.isClosed()) {
                    GetData.close();
                }
                throw th;
            }
        }
        Iterator<String> keys = jSONObject.keys();
        ArrayList arrayList = new ArrayList();
        while (keys.hasNext()) {
            String next = keys.next();
            try {
                if (jSONObject.getJSONObject(next).getInt("Se_chuyen") > 0) {
                    arrayList.add(jSONObject.getJSONObject(next));
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
        Collections.sort(arrayList, new Comparator<JSONObject>() { // from class: tamhoang.ldpro4.Fragment.Frag_CanChuyen.18
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
        this.xuatDan = "Lo:";
        int i = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < arrayList.size(); i3++) {
            try {
                JSONObject jSONObject3 = (JSONObject) arrayList.get(i3);
                int i4 = jSONObject3.getInt("Se_chuyen");
                if (i4 > 0) {
                    if (i2 > i4) {
                        this.xuatDan += "x" + i2 + "d ";
                        this.xuatDan += jSONObject3.getString("So_chon") + ",";
                        i = 0;
                    } else {
                        this.xuatDan += jSONObject3.getString("So_chon") + ",";
                    }
                    i++;
                    i2 = i4;
                }
            } catch (JSONException e3) {
                e3.printStackTrace();
            }
        }
        if (this.xuatDan.length() > 4 && i > 0) {
            this.xuatDan += "x" + i2 + "d ";
        }
        return this.xuatDan;
    }

    /* JADX WARN: Code restructure failed: missing block: B:106:0x0073, code lost:
    
        if (r9.isClosed() != false) goto L18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:10:0x0078, code lost:
    
        r0 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x007f, code lost:
    
        if (r0 >= r19.mSo.size()) goto L102;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0081, code lost:
    
        r9 = r19.mSo.get(r0);
        r13 = 100000;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0091, code lost:
    
        if (r9.length() != 5) goto L27;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0099, code lost:
    
        if (r19.jsonKhongmax.getInt("xien2") != 0) goto L26;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x00d4, code lost:
    
        r11 = java.lang.Integer.parseInt(r19.mTienTon.get(r0).replace(".", ""));
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x00ec, code lost:
    
        if (r8.has(r9) == false) goto L49;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x00ee, code lost:
    
        if (r11 <= 0) goto L49;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x00f0, code lost:
    
        r12 = new org.json.JSONObject();
        r14 = r8.getJSONObject(r9).getDouble("Da_chuyen");
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x00fd, code lost:
    
        r16 = r5;
        r17 = r6;
        r5 = r11;
        java.lang.Double.isNaN(r5);
        java.lang.Double.isNaN(r5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x010c, code lost:
    
        if ((r14 + r5) > r13) goto L47;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x010e, code lost:
    
        r12.put("So_chon", r9);
        r12.put("Da_chuyen", r8.getJSONObject(r9).getInt("Da_chuyen") + r11);
        r12.put("Se_chuyen", r11);
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0133, code lost:
    
        r8.put(r9, r12);
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x015a, code lost:
    
        r0 = r0 + 1;
        r5 = r16;
        r6 = r17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0121, code lost:
    
        r12.put("So_chon", r9);
        r12.put("Da_chuyen", r13);
        r12.put("Se_chuyen", r13 - r8.getJSONObject(r9).getInt("Da_chuyen"));
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0137, code lost:
    
        r16 = r5;
        r17 = r6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x013b, code lost:
    
        if (r11 <= 0) goto L101;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x013d, code lost:
    
        r5 = new org.json.JSONObject();
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0142, code lost:
    
        if (r11 > r13) goto L54;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0144, code lost:
    
        r5.put("So_chon", r9);
        r5.put("Da_chuyen", r11);
        r5.put("Se_chuyen", r11);
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0157, code lost:
    
        r8.put(r9, r5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x014e, code lost:
    
        r5.put("So_chon", r9);
        r5.put("Da_chuyen", r13);
        r5.put("Se_chuyen", r13);
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x009c, code lost:
    
        r13 = r19.jsonKhongmax.getInt("xien2");
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x00a9, code lost:
    
        if (r9.length() != 8) goto L33;
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x00b1, code lost:
    
        if (r19.jsonKhongmax.getInt(r6) != 0) goto L32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x00b4, code lost:
    
        r13 = r19.jsonKhongmax.getInt(r6);
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x00c1, code lost:
    
        if (r9.length() != 11) goto L39;
     */
    /* JADX WARN: Code restructure failed: missing block: B:88:0x00c9, code lost:
    
        if (r19.jsonKhongmax.getInt(r5) != 0) goto L38;
     */
    /* JADX WARN: Code restructure failed: missing block: B:90:0x00cc, code lost:
    
        r13 = r19.jsonKhongmax.getInt(r5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:91:0x00d3, code lost:
    
        r13 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:94:0x0162, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x0163, code lost:
    
        r0.printStackTrace();
     */
    /* JADX WARN: Code restructure failed: missing block: B:96:0x0075, code lost:
    
        r9.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x0065, code lost:
    
        if (r9.isClosed() == false) goto L17;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public String TaoTinXi(String str) {
        int i;
        String str2 = "xien4";
        String str3 = "xien3";
        JSONObject jSONObject = new JSONObject();
        new MainActivity();
        Cursor GetData = this.db.GetData("Select so_chon, Sum(diem) FROM tbl_soctS Where ten_kh = '" + str + "' AND type_kh = 2 AND the_loai = 'xi' AND ngay_nhan = '" + MainActivity.Get_date() + "' Group by so_chon");
        while (true) {
            try {
                try {
                    if (!GetData.moveToNext()) {
                        break;
                    }
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.put("Da_chuyen", GetData.getInt(1));
                    jSONObject2.put("Se_chuyen", 0);
                    jSONObject.put(GetData.getString(0), jSONObject2);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (Throwable th) {
                if (!GetData.isClosed()) {
                    GetData.close();
                }
                throw th;
            }
        }
        Iterator<String> keys = jSONObject.keys();
        ArrayList arrayList = new ArrayList();
        while (keys.hasNext()) {
            String next = keys.next();
            try {
                if (jSONObject.getJSONObject(next).getInt("Se_chuyen") > 0) {
                    arrayList.add(jSONObject.getJSONObject(next));
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
        Collections.sort(arrayList, new Comparator<JSONObject>() { // from class: tamhoang.ldpro4.Fragment.Frag_CanChuyen.20
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
        this.xuatDan = "Xien:\n";
        for (i = 0; i < arrayList.size(); i++) {
            JSONObject jSONObject3 = (JSONObject) arrayList.get(i);
            try {
                this.xuatDan = MainActivity.jSon_Setting.getInt("chuyen_xien") > 0 ? this.xuatDan + jSONObject3.getString("So_chon") + "x" + (jSONObject3.getInt("Se_chuyen") / 10) + "d " : this.xuatDan + jSONObject3.getString("So_chon") + "x" + jSONObject3.getInt("Se_chuyen") + "n ";
            } catch (JSONException e3) {
                e3.printStackTrace();
            }
        }
        return this.xuatDan;
    }

    public void Xulytin(int i, String str, int i2) {
        String str2 = "";
        String Get_date = MainActivity.Get_date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getDefault());
        String format = simpleDateFormat.format(calendar.getTime());
        if (this.mSpiner > -1) {
            Cursor GetData = this.db.GetData("Select * From tbl_kh_new Where sdt = '" + this.mMobile.get(this.mSpiner) + "'");
            if (GetData.moveToFirst()) {
                this.db.QueryData("Insert Into tbl_tinnhanS values (null, '" + Get_date + "', '" + format + "', " + (GetData.getInt(3) == 3 ? 2 : GetData.getInt(3)) + ", '" + this.mContact.get(this.mSpiner) + "', '" + this.mMobile.get(this.mSpiner) + "', 2, " + i + ", 'Tin " + i + ":\n" + str + "',null, '" + str + "', 'ko',0, 0, 1, null)");
                Database database = this.db;
                StringBuilder sb = new StringBuilder();
                sb.append("Select id From tbl_tinnhanS WHERE ngay_nhan = '");
                sb.append(Get_date);
                sb.append("' AND so_dienthoai = '");
                sb.append(this.mMobile.get(this.mSpiner));
                sb.append("' AND type_kh = 2 AND so_tin_nhan = ");
                sb.append(i);
                Cursor GetData2 = database.GetData(sb.toString());
                GetData2.moveToFirst();
                if (Congthuc.CheckDate(MainActivity.myDate)) {
                    try {
                        this.db.Update_TinNhanGoc(GetData2.getInt(0), GetData.getInt(3));
                        final String str3 = "Tin " + i + ":\n" + str;
                        if (i2 != 1 || GetData.getString(2).indexOf("TL") <= -1) {
                            int i3 = 1;
                            if (i2 == 1) {
                                if (GetData.getString(2).indexOf("sms") > -1) {
                                    this.db.SendSMS(this.mMobile.get(this.mSpiner), str3);
                                } else {
                                    i3 = 1;
                                }
                            }
                            if (i2 == i3 && GetData.getString(2).indexOf("sms") == -1) {
                                new NotificationReader().NotificationWearReader(this.mMobile.get(this.mSpiner), str3);
                                str2 = "Insert into Chat_database Values( null,'" + Get_date + "', '" + format + "', 2, '" + this.mContact.get(this.mSpiner) + "', '" + this.mMobile.get(this.mSpiner) + "', '" + GetData.getString(2) + "','" + str3 + "',1)";
                            }
                        } else {
                            new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: tamhoang.ldpro4.Fragment.Frag_CanChuyen.23
                                @Override // java.lang.Runnable
                                public void run() {
                                    MainActivity.sendMessage(Long.parseLong(mMobile.get(mSpiner)), str3);
                                }
                            });
                            str2 = "Insert into Chat_database Values( null,'" + Get_date + "', '" + format + "', 2, '" + this.mContact.get(this.mSpiner) + "', '" + this.mMobile.get(this.mSpiner) + "', '" + GetData.getString(2) + "','" + str3 + "',1)";
                        }
                        this.db.QueryData(str2);
                    } catch (Exception unused) {
                        this.db.QueryData("Update tbl_tinnhanS set phat_hien_loi = 'ko' WHERE id = " + GetData2.getInt(0));
                        this.db.QueryData("Delete From tbl_soctS WHERE ngay_nhan = '" + Get_date + "' AND so_dienthoai = '" + this.mMobile.get(this.mSpiner) + "' AND so_tin_nhan = " + i);
                        Toast.makeText(getActivity(), "Đã xảy ra lỗi!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    try {
                        Toast.makeText(getActivity(), "Đã hết hạn sử dụng\n\nHãy liên hệ đại lý hoặc SĐT: " + MainActivity.listKH.getString("k_tra") + " để gia hạn", Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (GetData2 != null) {
                    GetData2.close();
                }
                if (GetData != null) {
                    GetData.close();
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:112:0x0355  */
    /* JADX WARN: Removed duplicated region for block: B:118:0x0395 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:213:0x05ab  */
    /* JADX WARN: Removed duplicated region for block: B:215:0x05c4  */
    /* JADX WARN: Removed duplicated region for block: B:224:0x064b  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x0623  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0081  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void btn_click() throws JSONException {
        int i;
        int indexOf;
        String str = "";
        int i3;
        String str2;
        String obj = "";
        int i4 = 0;
        int i5 = 0;
        String str3 = "";
        String XuatDanTon2 = "";
        String str4 = "";
        String str5 = "";
        int i6 = 0;
        int parseInt;
        int i7;
        StringBuilder sb;
        String str6 = "";
        String str7 = "";
        String str8 = "";
        int i8 = 0;
        this.xuatDan = "";
        new MainActivity();
        String Get_date = MainActivity.Get_date();
        xemlv();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getDefault());
        simpleDateFormat2.setTimeZone(TimeZone.getDefault());
        String format = simpleDateFormat.format(calendar.getTime());
        int i9 = 100;
        if (MainActivity.jSon_Setting.getInt("lam_tron") != 0) {
            if (MainActivity.jSon_Setting.getInt("lam_tron") == 1) {
                i8 = 10;
            } else if (MainActivity.jSon_Setting.getInt("lam_tron") == 2) {
                i8 = 50;
            } else if (MainActivity.jSon_Setting.getInt("lam_tron") == 3) {
                i8 = 100;
            }
            i = i8;
            indexOf = format.indexOf(Get_date);
            int i10 = -1;
            if (indexOf <= -1) {
                if (this.edt_tien.getText().toString().length() != 0 && this.edt_tien.getText().toString() != "0") {
                    String replaceAll = this.edt_tien.getText().toString().replaceAll("%", "").replaceAll("n", "").replaceAll("k", "").replaceAll("d", "").replaceAll(">", "").replaceAll("\\.", "").replaceAll(",", "");
                    if (Congthuc.isNumeric(replaceAll)) {
                        i3 = Integer.parseInt(replaceAll);
                        str2 = this.DangXuat;
                        if (str2 != "(the_loai = 'deb' or the_loai = 'det')" || str2 == "the_loai = 'lo'" || str2 == "the_loai = 'dea'" || str2 == "the_loai = 'dec'" || str2 == "the_loai = 'ded'") {
                            if (str2 != "(the_loai = 'deb' or the_loai = 'det')") {
                                obj = this.edt_tien.getText().toString();
                                i4 = this.min;
                                i5 = this.max;
                                str3 = "deb";
                            } else if (str2 == "the_loai = 'dea'") {
                                obj = this.edt_tien.getText().toString();
                                i4 = this.min;
                                i5 = this.max;
                                str3 = "dea";
                            } else if (str2 == "the_loai = 'dec'") {
                                obj = this.edt_tien.getText().toString();
                                i4 = this.min;
                                i5 = this.max;
                                str3 = "dec";
                            } else if (str2 == "the_loai = 'ded'") {
                                obj = this.edt_tien.getText().toString();
                                i4 = this.min;
                                i5 = this.max;
                                str3 = "ded";
                            } else {
                                obj = this.edt_tien.getText().toString();
                                i4 = this.min;
                                i5 = this.max;
                                str3 = "lo";
                            }
                            XuatDanTon2 = db.XuatDanTon2(str3, obj, i4, i5);
                        } else {
                            if (str2 == "the_loai = 'xi'") {
                                this.xuatDan = "Xien:\n";
                                int i11 = this.min;
                                while (i11 < this.mSo.size()) {
                                    int parseInt2 = ((this.edt_tien.getText().toString().indexOf("%") <= i10 && this.edt_tien.getText().toString().indexOf(">") <= i10 && i3 != 0 && Integer.parseInt(this.mTienTon.get(i11).replace(".", "")) > i3) ? i3 / i : Integer.parseInt(this.mTienTon.get(i11).replace(".", "")) / i) * i;
                                    if (this.edt_tien.getText().toString().indexOf("%") > i10) {
                                        if (parseInt2 > 0) {
                                            try {
                                                if (MainActivity.jSon_Setting.getInt("chuyen_xien") > 0) {
                                                    str8 = this.xuatDan + this.mSo.get(i11) + "x" + ((parseInt2 * i3) / 1000) + "d ";
                                                } else if (MainActivity.jSon_Setting.getInt("chuyen_xien") == 0) {
                                                    str8 = this.xuatDan + this.mSo.get(i11) + "x" + ((parseInt2 * i3) / 100) + "n ";
                                                }
                                                this.xuatDan = str8;
                                            } catch (JSONException e2) {
                                                e2.printStackTrace();
                                            }
                                        }
                                    } else if (this.edt_tien.getText().toString().indexOf(">") > -1) {
                                        if (parseInt2 > i3) {
                                            if (MainActivity.jSon_Setting.getInt("chuyen_xien") > 0) {
                                                str7 = this.xuatDan + this.mSo.get(i11) + "x" + ((parseInt2 - i3) / 10) + "d ";
                                            } else if (MainActivity.jSon_Setting.getInt("chuyen_xien") == 0) {
                                                str7 = this.xuatDan + this.mSo.get(i11) + "x" + (parseInt2 - i3) + "n ";
                                            }
                                            this.xuatDan = str7;
                                        }
                                    } else if (this.edt_tien.getText().toString().indexOf(">") == -1 && this.edt_tien.getText().toString().indexOf("%") == -1 && parseInt2 > 0) {
                                        if (MainActivity.jSon_Setting.getInt("chuyen_xien") > 0) {
                                            str6 = this.xuatDan + this.mSo.get(i11) + "x" + (parseInt2 / 10) + "d ";
                                        } else if (MainActivity.jSon_Setting.getInt("chuyen_xien") == 0) {
                                            str6 = this.xuatDan + this.mSo.get(i11) + "x" + parseInt2 + "n ";
                                        }
                                        this.xuatDan = str6;
                                    }
                                    i11++;
                                    i10 = -1;
                                }
                            } else if (str2 == "the_loai = 'bc'") {
                                this.xuatDan = "Cang:\n";
                                int i12 = this.min;
                                int i13 = 0;
                                while (i12 < this.mSo.size()) {
                                    if (i3 != 0) {
                                        if (this.edt_tien.getText().toString().indexOf("%") > -1) {
                                            i6 = ((Integer.parseInt(this.mTienTon.get(i12).replace(".", "")) * i3) / i) / i9;
                                        } else if (this.edt_tien.getText().toString().indexOf(">") > -1) {
                                            parseInt = Integer.parseInt(this.mTienTon.get(i12).replace(".", "")) - i3;
                                            i6 = parseInt / i;
                                        } else if (Integer.parseInt(this.mTienTon.get(i12).replace(".", "")) > i3) {
                                            i6 = i3 / i;
                                        }
                                        i7 = i6 * i;
                                        if (i7 > 0) {
                                            if (i13 > i7) {
                                                this.xuatDan += "x" + i13 + "n ";
                                                sb = new StringBuilder();
                                            } else {
                                                sb = new StringBuilder();
                                            }
                                            sb.append(this.xuatDan);
                                            sb.append(this.mSo.get(i12));
                                            sb.append(",");
                                            this.xuatDan = sb.toString();
                                            i13 = i7;
                                        }
                                        i12++;
                                        i9 = 100;
                                    }
                                    parseInt = Integer.parseInt(this.mTienTon.get(i12).replace(".", ""));
                                    i6 = parseInt / i;
                                    i7 = i6 * i;
                                    if (i7 > 0) {
                                    }
                                    i12++;
                                    i9 = 100;
                                }
                                if (this.xuatDan.length() > 4) {
                                    XuatDanTon2 = this.xuatDan + "x" + i13 + "n";
                                }
                            } else if (str2 == "the_loai = 'xn'") {
                                this.xuatDan = "Xnhay:\n";
                                for (int i14 = this.min; i14 < this.mSo.size(); i14++) {
                                    int parseInt3 = ((this.edt_tien.getText().toString().indexOf("%") <= -1 && this.edt_tien.getText().toString().indexOf(">") <= -1 && i3 != 0 && Integer.parseInt(this.mTienTon.get(i14).replace(".", "")) > i3) ? i3 / i : Integer.parseInt(this.mTienTon.get(i14).replace(".", "")) / i) * i;
                                    if (this.edt_tien.getText().toString().indexOf("%") <= -1) {
                                        if (this.edt_tien.getText().toString().indexOf(">") > -1) {
                                            if (parseInt3 > i3) {
                                                try {
                                                    if (MainActivity.jSon_Setting.getInt("chuyen_xien") > 0) {
                                                        StringBuilder sb2 = new StringBuilder();
                                                        sb2.append(this.xuatDan);
                                                        sb2.append(this.mSo.get(i14));
                                                        sb2.append("x");
                                                        sb2.append((parseInt3 - i3) / 10);
                                                        sb2.append("d\n");
                                                        str5 = sb2.toString();
                                                    } else if (MainActivity.jSon_Setting.getInt("chuyen_xien") == 0) {
                                                        str5 = this.xuatDan + this.mSo.get(i14) + "x" + (parseInt3 - i3) + "n\n";
                                                    }
                                                    this.xuatDan = str5;
                                                } catch (JSONException e4) {
                                                    e4.printStackTrace();
                                                }
                                            }
                                        } else if (this.edt_tien.getText().toString().indexOf(">") == -1 && this.edt_tien.getText().toString().indexOf("%") == -1 && parseInt3 > 0) {
                                            if (MainActivity.jSon_Setting.getInt("chuyen_xien") > 0) {
                                                str4 = this.xuatDan + this.mSo.get(i14) + "x" + (parseInt3 / 10) + "d\n";
                                            } else if (MainActivity.jSon_Setting.getInt("chuyen_xien") == 0) {
                                                str4 = this.xuatDan + this.mSo.get(i14) + "x" + parseInt3 + "n\n";
                                            }
                                            this.xuatDan = str4;
                                        }
                                    } else if (parseInt3 > 0) {
                                        try {
                                            if (MainActivity.jSon_Setting.getInt("chuyen_xien") > 0) {
                                                this.xuatDan += this.mSo.get(i14) + "x" + ((parseInt3 * i3) / 1000) + "d\n";
                                            } else if (MainActivity.jSon_Setting.getInt("chuyen_xien") == 0) {
                                                StringBuilder sb3 = new StringBuilder();
                                                sb3.append(this.xuatDan);
                                                sb3.append(this.mSo.get(i14));
                                                sb3.append("x");
                                                sb3.append((parseInt3 * i3) / 100);
                                                sb3.append("n\n");
                                                this.xuatDan = sb3.toString();
                                            }
                                        } catch (JSONException e6) {
                                            e6.printStackTrace();
                                        }
                                    }
                                }
                            }
                            if (this.xuatDan.indexOf(":") > -1) {
                                String str9 = this.xuatDan;
                                if (str9.substring(str9.indexOf(":")).length() > 7) {
                                    if (getActivity().isFinishing()) {
                                        return;
                                    }
                                    Dialog(1);
                                    return;
                                }
                            }
                        }
                        this.xuatDan = XuatDanTon2;
                        if (this.xuatDan.indexOf(":") > -1) {
                        }
                    }
                }
                str2 = this.DangXuat;
                if (str2 != "(the_loai = 'deb' or the_loai = 'det')") {
                }
                if (str2 != "(the_loai = 'deb' or the_loai = 'det')") {
                }
                XuatDanTon2 = db.XuatDanTon2(str3, obj, i4, i5);
                this.xuatDan = XuatDanTon2;
                if (this.xuatDan.indexOf(":") > -1) {
                }
                str = "Không có số liệu!";
            } else {
                str = "Không làm việc với dữ liệu ngày cũ!";
            }
            Toast.makeText(requireActivity(), str, Toast.LENGTH_LONG).show();
        }
        Toast.makeText(requireActivity(), str, Toast.LENGTH_LONG).show();
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.frag_canchuyen, viewGroup, false);
        this.v = inflate;
        this.radio_de = (RadioButton) inflate.findViewById(R.id.radio_de);
        this.radio_lo = (RadioButton) this.v.findViewById(R.id.radio_lo);
        this.radio_xi = (RadioButton) this.v.findViewById(R.id.radio_xi);
        this.radio_bc = (RadioButton) this.v.findViewById(R.id.radio_bc);
        this.radio_dea = (RadioButton) this.v.findViewById(R.id.radio_dea);
        this.radio_deb = (RadioButton) this.v.findViewById(R.id.radio_deb);
        this.radio_dec = (RadioButton) this.v.findViewById(R.id.radio_dec);
        this.radio_ded = (RadioButton) this.v.findViewById(R.id.radio_ded);
        this.btn_Xuatso =  this.v.findViewById(R.id.btn_Xuatso);
        ln_xi =  this.v.findViewById(R.id.ln_xi);
        ln_xi.setVisibility(View.GONE);
        li_loaide =  this.v.findViewById(R.id.li_loaide);
        li_loaide.setVisibility(View.GONE);
        this.edt_tien =  this.v.findViewById(R.id.edt_giadet);
        this.check_x2 = (CheckBox) this.v.findViewById(R.id.check_x2);
        this.check_x3 = (CheckBox) this.v.findViewById(R.id.check_x3);
        this.check_x4 = (CheckBox) this.v.findViewById(R.id.check_x4);
        this.check_xn = (CheckBox) this.v.findViewById(R.id.check_xn);
        this.lview = (ListView) this.v.findViewById(R.id.lview);
        this.db = new Database(getActivity());
        Handler handler = new Handler();
        this.handler = handler;
        handler.postDelayed(this.runnable, 1000L);
        rangeSeekBar.setRange(0, 100);
        LinearLayout linearLayout3 = this.v.findViewById(R.id.scrollView);
        this.layout = linearLayout3;
        linearLayout3.addView(this.rangeSeekBar);
        rangeSeekBar.setOnRangeChangedListener(new OnRangeChangedListener() {
            @Override
            public void onRangeChanged(RangeSeekBar view, float leftValue, float rightValue, boolean isFromUser) {
                min = (int) leftValue;
                max = (int) rightValue;
            }

            @Override
            public void onStartTrackingTouch(RangeSeekBar view, boolean isLeft) {

            }

            @Override
            public void onStopTrackingTouch(RangeSeekBar view, boolean isLeft) {

            }
        });
        this.radio_de.setOnCheckedChangeListener((compoundButton, z) -> {
            if (radio_de.isChecked()) {
                layout.setVisibility(View.VISIBLE);
                new MainActivity();
                String Get_date = MainActivity.Get_date();
                try {
                    Cursor GetData = db.GetData("Select sum((the_loai = 'dea')* diem) as de_a\n,sum((the_loai = 'deb')* diem) as de_b\n,sum((the_loai = 'det')* diem) as de_t\n,sum((the_loai = 'dec')* diem) as de_c\n,sum((the_loai = 'ded')* diem) as de_d\nFrom tbl_soctS \nWhere ngay_nhan = '" + Get_date + "'");
                    if (!GetData.moveToFirst() || GetData == null) {
                        DangXuat = "(the_loai = 'deb' or the_loai = 'det')";
                        return;
                    }
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
                        ln_xi.setVisibility(View.GONE);
                        li_loaide.setVisibility(View.GONE);
                        radio_deb.setChecked(true);
                    } else if (iArr[0] == 0 && iArr[1] == 0 && iArr[2] == 0 && iArr[3] == 0 && iArr[4] == 0) {
                        DangXuat = "(the_loai = 'deb' or the_loai = 'det')";
                        ln_xi.setVisibility(View.GONE);
                        li_loaide.setVisibility(View.GONE);
                        radio_deb.setChecked(true);
                    } else {
                        DangXuat = "(the_loai = 'deb' or the_loai = 'det')";
                        ln_xi.setVisibility(View.GONE);
                        li_loaide.setVisibility(View.VISIBLE);
                        radio_deb.setChecked(true);
                    }
                    xem_RecycView();
                    if (GetData.isClosed() || GetData == null || GetData.isClosed()) {
                        return;
                    }
                    GetData.close();
                } catch (SQLException unused) {
                    DangXuat = "(the_loai = 'deb' or the_loai = 'det')";
                }
            }
        });
        this.radio_dea.setOnCheckedChangeListener((compoundButton, z) -> {
            if (radio_dea.isChecked()) {
                DangXuat = "the_loai = 'dea'";
                ln_xi.setVisibility(View.GONE);
                xem_RecycView();
            }
        });
        this.radio_deb.setOnCheckedChangeListener((compoundButton, z) -> {
            if (radio_deb.isChecked()) {
                DangXuat = "(the_loai = 'deb' or the_loai = 'det')";
                ln_xi.setVisibility(View.GONE);
                xem_RecycView();
            }
        });
        this.radio_dec.setOnCheckedChangeListener((compoundButton, z) -> {
            if (radio_dec.isChecked()) {
                DangXuat = "the_loai = 'dec'";
                ln_xi.setVisibility(View.GONE);
                xem_RecycView();
            }
        });
        this.radio_ded.setOnCheckedChangeListener((compoundButton, z) -> {
            if (radio_ded.isChecked()) {
                DangXuat = "the_loai = 'ded'";
                ln_xi.setVisibility(View.GONE);
                xem_RecycView();
            }
        });
        this.radio_lo.setOnCheckedChangeListener((compoundButton, z) -> {
            if (radio_lo.isChecked()) {
                DangXuat = "the_loai = 'lo'";
                ln_xi.setVisibility(View.GONE);
                li_loaide.setVisibility(View.GONE);
                layout.setVisibility(View.VISIBLE);
                xem_RecycView();
            }
        });
        this.radio_xi.setOnCheckedChangeListener((compoundButton, z) -> {
            if (radio_xi.isChecked()) {
                new MainActivity();
                DangXuat = "the_loai = 'xi'";
                layout.setVisibility(View.GONE);
                ln_xi.setVisibility(View.VISIBLE);
                li_loaide.setVisibility(View.GONE);
                try {
                    Cursor GetData = db.GetData("Select count(id) From tbl_soctS WHERE the_loai = 'xn' AND ngay_nhan = '" + MainActivity.Get_date() + "'");
                    if (GetData.moveToFirst() && GetData.getInt(0) > 0) {
                        check_xn.setVisibility(View.VISIBLE);
                    }
                    xem_RecycView();
                } catch (SQLException unused) {
                }
            }
        });
        this.radio_bc.setOnCheckedChangeListener((compoundButton, z) -> {
            if (radio_bc.isChecked()) {
                DangXuat = "the_loai = 'bc'";
                layout.setVisibility(View.GONE);
                ln_xi.setVisibility(View.GONE);
                li_loaide.setVisibility(View.GONE);
                xem_RecycView();
            }
        });
        this.check_x2.setOnCheckedChangeListener((compoundButton, z) -> {
            if (check_x2.isChecked()) {
                DangXuat = "the_loai = 'xi'";
                lay_x2 = "length(so_chon) = 5 ";
                check_xn.setChecked(false);
            } else {
                lay_x2 = "";
            }
            xem_RecycView();
        });
        this.check_x3.setOnCheckedChangeListener((compoundButton, z) -> {
            if (check_x3.isChecked()) {
                DangXuat = "the_loai = 'xi'";
                lay_x3 = "OR length(so_chon) = 8 ";
                check_xn.setChecked(false);
            } else {
                lay_x3 = "";
            }
            xem_RecycView();
        });
        this.check_x4.setOnCheckedChangeListener((compoundButton, z) -> {
            if (check_x4.isChecked()) {
                DangXuat = "the_loai = 'xi'";
                lay_x4 = "OR length(so_chon) = 11 ";
                check_xn.setChecked(false);
            } else {
                lay_x4 = "";
            }
            xem_RecycView();
        });
        this.check_xn.setOnClickListener(new View.OnClickListener() { // from class: tamhoang.ldpro4.Fragment.Frag_CanChuyen.13
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (check_xn.isChecked()) {
                    DangXuat = "the_loai = 'xn'";
                    check_x2.setChecked(false);
                    check_x3.setChecked(false);
                    check_x4.setChecked(false);
                    xem_RecycView();
                }
            }
        });
        this.btn_Xuatso.setOnClickListener(new View.OnClickListener() { // from class: tamhoang.ldpro4.Fragment.Frag_CanChuyen.14
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (Congthuc.isNumeric(edt_tien.getText().toString().replaceAll("%", "").replaceAll("n", "").replaceAll("k", "").replaceAll("d", "").replaceAll(">", "").replaceAll("\\.", "")) || edt_tien.getText().toString().length() == 0) {
                    try {
                        btn_click();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getActivity(), "Kiểm tra lại tiền!", Toast.LENGTH_LONG).show();
                }
            }
        });
        this.lay_x2 = "length(so_chon) = 5 ";
        this.lay_x3 = "OR length(so_chon) = 8 ";
        this.lay_x4 = "OR length(so_chon) = 11 ";
        this.lview.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: tamhoang.ldpro4.Fragment.Frag_CanChuyen.15
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                try {
                    new MainActivity();
                    Cursor GetData = db.GetData("Select ten_kh, sum(diem_quydoi) From tbl_soctS WHERE so_chon = '" + mSo.get(i) + "' AND ngay_nhan = '" + MainActivity.Get_date() + "' AND type_kh = 1 AND " + DangXuat + " GROUP BY so_dienthoai");
                    String str = "";
                    while (GetData.moveToNext()) {
                        str = str + GetData.getString(0) + ": " + GetData.getString(1) + "\n";
                    }
                    Toast.makeText(getActivity(), str, Toast.LENGTH_LONG).show();
                } catch (SQLException unused) {
                }
            }
        });
        try {
            this.sapxep = MainActivity.jSon_Setting.getInt("bao_cao_so") == 0 ? "diem DESC" : "ton DESC, diem DESC";
        } catch (JSONException e) {
            e.printStackTrace();
        }
        xemlv();
        return this.v;
    }

    @Override // android.support.v4.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        this.handler.removeCallbacks(this.runnable);
    }

    @Override // android.support.v4.app.Fragment
    public void onResume() {
        xem_RecycView();
        super.onResume();
    }


    public void xem_RecycView() {
        String str;
        StringBuilder sb;
        String str2;
        Cursor GetData;
        String str3;
        Cursor GetData2;
        new MainActivity();
        String Get_date = MainActivity.Get_date();
        this.mSo.clear();
        this.mTienNhan.clear();
        this.mTienOm.clear();
        this.mTienchuyen.clear();
        this.mTienTon.clear();
        this.mNhay.clear();
        String str4 = this.DangXuat;
        if (str4 == "(the_loai = 'deb' or the_loai = 'det')") {
            sb = new StringBuilder();
            sb.append("Select tbl_soctS.So_chon\n, Sum((tbl_soctS.type_kh = 1) * (100-tbl_soctS.diem_khachgiu)*diem_quydoi/100) as diem\n, so_om.Om_deB + sum(tbl_soctS.diem_dly_giu*tbl_soctS.diem_quydoi/100) as So_Om\n, Sum((tbl_soctS.type_kh =2) * tbl_soctS.diem_quydoi) as chuyen\n, Sum((tbl_soctS.type_kh =1) * (100-tbl_soctS.diem_khachgiu-tbl_soctS.diem_dly_giu)*diem_quydoi/100) - Sum((tbl_soctS.type_kh =2) * tbl_soctS.diem_quydoi) - so_om.Om_deB as ton\n, so_nhay  From so_om Left Join tbl_soctS On tbl_soctS.so_chon = so_om.So\n Where tbl_soctS.ngay_nhan='");
            sb.append(Get_date);
            str3 = "' AND (tbl_soctS.the_loai='deb' OR tbl_soctS.the_loai='det') GROUP by so_om.So Order by ";
        } else if (str4 == "the_loai = 'lo'") {
            sb = new StringBuilder();
            sb.append("Select tbl_soctS.So_chon\n, Sum((tbl_soctS.type_kh = 1) * (100-tbl_soctS.diem_khachgiu)*diem_quydoi/100) as diem\n, so_om.Om_Lo + sum(tbl_soctS.diem_dly_giu*tbl_soctS.diem_quydoi/100) as So_Om\n, Sum((tbl_soctS.type_kh =2) * tbl_soctS.diem_quydoi) as chuyen\n, Sum((tbl_soctS.type_kh =1) * (100-tbl_soctS.diem_khachgiu-tbl_soctS.diem_dly_giu)*diem_quydoi/100) - Sum((tbl_soctS.type_kh =2) * tbl_soctS.diem_quydoi) - so_om.Om_Lo as ton\n, so_nhay  From so_om Left Join tbl_soctS On tbl_soctS.so_chon = so_om.So \n Where tbl_soctS.ngay_nhan='");
            sb.append(Get_date);
            str3 = "' AND tbl_soctS.the_loai='lo' \n GROUP by so_om.So Order by ";
        } else if (str4 == "the_loai = 'dea'") {
            sb = new StringBuilder();
            sb.append("Select tbl_soctS.So_chon\n, Sum((tbl_soctS.type_kh = 1) * (100-tbl_soctS.diem_khachgiu)*diem_quydoi/100) as diem\n, so_om.Om_DeA + sum(tbl_soctS.diem_dly_giu*tbl_soctS.diem_quydoi/100) as So_Om\n, Sum((tbl_soctS.type_kh =2) * tbl_soctS.diem_quydoi) as chuyen\n, Sum((tbl_soctS.type_kh =1) * (100-tbl_soctS.diem_khachgiu-tbl_soctS.diem_dly_giu)*diem_quydoi/100) - Sum((tbl_soctS.type_kh =2) * tbl_soctS.diem_quydoi) - so_om.Om_DeA as ton\n, so_nhay  From so_om Left Join tbl_soctS On tbl_soctS.so_chon = so_om.So \n Where tbl_soctS.ngay_nhan='");
            sb.append(Get_date);
            str3 = "' AND tbl_soctS.the_loai='dea' GROUP by so_chon Order by ";
        } else if (str4 == "the_loai = 'dec'") {
            sb = new StringBuilder();
            sb.append("Select tbl_soctS.So_chon\n, Sum((tbl_soctS.type_kh = 1) * (100-tbl_soctS.diem_khachgiu)*diem_quydoi/100) as diem\n, so_om.Om_DeC + sum(tbl_soctS.diem_dly_giu*tbl_soctS.diem_quydoi/100) as So_Om\n, Sum((tbl_soctS.type_kh =2) * tbl_soctS.diem_quydoi) as chuyen\n, Sum((tbl_soctS.type_kh =1) * (100-tbl_soctS.diem_khachgiu-tbl_soctS.diem_dly_giu)*diem_quydoi/100) - Sum((tbl_soctS.type_kh =2) * tbl_soctS.diem_quydoi) - so_om.Om_DeC as ton\n, so_nhay  From so_om Left Join tbl_soctS On tbl_soctS.so_chon = so_om.So \n Where tbl_soctS.ngay_nhan='");
            sb.append(Get_date);
            str3 = "' AND tbl_soctS.the_loai='dec' GROUP by so_chon Order by ";
        } else {
            if (str4 != "the_loai = 'ded'") {
                if (str4 == "the_loai = 'xi'") {
                    String str5 = "";
                    if (this.lay_x2 != "" || this.lay_x3 != "" || this.lay_x4 != "") {
                        str5 = (" And (" + this.lay_x2 + this.lay_x3 + this.lay_x4 + ")").replaceAll("\\(OR", "(");
                    }
                    GetData = this.db.GetData("Select * From So_om WHERE ID = 1");
                    GetData.moveToFirst();
                    str = "SELECT so_chon, sum((type_kh =1)*(100-diem_khachgiu)*diem_quydoi)/100 AS diem, ((length(so_chon) = 5) * " + GetData.getString(7) + " +(length(so_chon) = 8) * " + GetData.getString(8) + " +(length(so_chon) = 11) * " + GetData.getString(9) + " + sum(diem_dly_giu*diem_quydoi/100)) AS Om, SUm((type_kh =2)*diem) as chuyen , SUm((type_kh =1)*(100-diem_khachgiu-diem_dly_giu)*diem_quydoi/100)-SUm((type_kh =2)*diem) -  ((length(so_chon) = 5) * " + GetData.getString(7) + " +(length(so_chon) = 8) * " + GetData.getString(8) + " +(length(so_chon) = 11) * " + GetData.getString(9) + ") AS ton, so_nhay   From tbl_soctS Where ngay_nhan='" + Get_date + "' AND the_loai='xi'" + str5 + "  GROUP by so_chon Order by ton DESC, diem DESC";
                    if (GetData != null) {
                    }
                } else if (str4 == "the_loai = 'bc'") {
                    GetData = this.db.GetData("Select * From So_om WHERE ID = 1");
                    GetData.moveToFirst();
                    if (GetData.getInt(10) == 1) {
                        this.db.QueryData("Update so_om set om_bc=0 WHERE id = 1");
                        GetData = this.db.GetData("Select * From So_om WHERE ID = 1");
                        GetData.moveToFirst();
                    }
                    str = "SELECT so_chon, sum((type_kh = 1)*(100-diem_khachgiu)*diem_quydoi/100) AS diem, " + GetData.getString(10) + " + sum(diem_dly_giu*diem_quydoi)/100 AS Om, SUm((type_kh = 2)*diem) as Chuyen, sum((type_kh =1)*(100-diem_khachgiu-diem_dly_giu)*diem_quydoi/100) - sum((type_kh =2)*diem) -" + GetData.getString(10) + " AS ton, so_nhay   From tbl_soctS Where ngay_nhan='" + Get_date + "' AND the_loai='bc' GROUP by so_chon Order by ton DESC, diem DESC";
                    if (GetData != null) {
                    }
                } else if (str4 == "the_loai = 'xn'") {
                    sb = new StringBuilder();
                    sb.append("SELECT so_chon, sum((type_kh =1)*(diem_quydoi)) AS diem, sum(tbl_soctS.diem_dly_giu) AS Om, SUm((type_kh =2)*diem) as chuyen , SUm((type_kh =1)*diem_ton-(type_kh =2)*diem_ton) AS ton, so_nhay   From tbl_soctS Where ngay_nhan='");
                    sb.append(Get_date);
                    str2 = "' AND the_loai='xn' GROUP by so_chon Order by ton DESC, diem DESC";
                    sb.append(str2);
                    str = sb.toString();
                } else {
                    str = null;
                }
                GetData2 = this.db.GetData(str);
                DecimalFormat decimalFormat = new DecimalFormat("###,###");
                if (GetData2 != null) {
                    while (GetData2.moveToNext()) {
                        this.mSo.add(GetData2.getString(0));
                        this.mTienNhan.add(decimalFormat.format(GetData2.getInt(1)));
                        this.mTienOm.add(decimalFormat.format(GetData2.getInt(2)));
                        this.mTienchuyen.add(decimalFormat.format(GetData2.getInt(3)));
                        this.mTienTon.add(decimalFormat.format(GetData2.getInt(4)));
                        this.mNhay.add(Integer.valueOf(GetData2.getInt(5)));
                    }
                    if (GetData2 != null && !GetData2.isClosed()) {
                        GetData2.close();
                    }
                }
                if (getActivity() == null) {
                    this.lview.setAdapter((ListAdapter) new So_OmAdapter(getActivity(), 2131427395, this.mSo));
                    return;
                }
                return;
            }
            sb = new StringBuilder();
            sb.append("Select tbl_soctS.So_chon\n, Sum((tbl_soctS.type_kh = 1) * (100-tbl_soctS.diem_khachgiu)*diem_quydoi/100) as diem\n, so_om.Om_DeD + sum(tbl_soctS.diem_dly_giu*tbl_soctS.diem_quydoi/100) as So_Om\n, Sum((tbl_soctS.type_kh =2) * tbl_soctS.diem_quydoi) as chuyen\n, Sum((tbl_soctS.type_kh =1) * (100-tbl_soctS.diem_khachgiu-tbl_soctS.diem_dly_giu)*diem_quydoi/100) - Sum((tbl_soctS.type_kh =2) * tbl_soctS.diem_quydoi) - so_om.Om_DeD as ton\n, so_nhay  From so_om Left Join tbl_soctS On tbl_soctS.so_chon = so_om.So \n Where tbl_soctS.ngay_nhan='");
            sb.append(Get_date);
            str3 = "' AND tbl_soctS.the_loai='ded' GROUP by so_chon Order by ";
        }
        sb.append(str3);
        str2 = this.sapxep;
        sb.append(str2);
        str = sb.toString();
        GetData2 = this.db.GetData(str);
        DecimalFormat decimalFormat2 = new DecimalFormat("###,###");
        if (GetData2 != null) {
        }
        if (getActivity() == null) {
        }
    }

    public void xemlv() {
        if (this.DangXuat != null) {
            xem_RecycView();
        } else {
            this.radio_de.setChecked(true);
        }
    }
}
