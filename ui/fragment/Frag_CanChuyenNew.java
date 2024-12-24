package tamhoang.ldpro4.ui.fragment;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import tamhoang.ldpro4.formula.Congthuc;
import tamhoang.ldpro4.ui.home.MainActivity;
import tamhoang.ldpro4.R;
import tamhoang.ldpro4.data.Database;
import tamhoang.ldpro4.ui.widget.rangeseekbar.OnRangeChangedListener;
import tamhoang.ldpro4.ui.widget.rangeseekbar.RangeSeekBar;

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

/* loaded from: classes.dex */
public class Frag_CanChuyenNew extends Frag_CanChuyen {
    Button btn_Xuatso;
    CheckBox check_x2;
    CheckBox check_x3;
    CheckBox check_x4;
    CheckBox check_xn;
    EditText edt_tien;
    LinearLayout seekBarLayout;
    LinearLayout li_loaide;
    LinearLayout ln_xi;
    ListView no_rp_number;
    RadioButton radio_bc;
    RadioButton radio_de;
    RadioButton radio_dea;
    RadioButton radio_deb;
    RadioButton radio_dec;
    RadioButton radio_ded;
    RadioButton radio_lo;
    RadioButton radio_loa;
    RadioButton radio_xi;
    RangeSeekBar rangeSeekBar;
    private Runnable runnable = new Runnable() { // from class: tamhoang.ldpro4.Fragment.Frag_CanChuyenNew.1

        @Override // java.lang.Runnable
        public void run() {
            if (MainActivity.sms) {
                xemlv();
                MainActivity.sms = false;
            }
            handler.postDelayed(this, 1000L);
        }
    };
    public View v;

    public class So_OmAdapter extends ArrayAdapter {

        /* loaded from: classes.dex */
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
                viewHolder.tview5 =  view.findViewById(R.id.Tv_so);
                viewHolder.tview7 =  view.findViewById(R.id.tv_diemNhan);
                viewHolder.tview8 =  view.findViewById(R.id.tv_diemOm);
                viewHolder.tview1 =  view.findViewById(R.id.tv_diemChuyen);
                viewHolder.tview4 =  view.findViewById(R.id.tv_diemTon);
                viewHolder.tview2 =  view.findViewById(R.id.stt);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            if (mNhay.get(position).intValue() > 0) {
                // TODO: 7/4/2024 Xem lại color
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
                viewHolder.tview7.setText(mTienNhan.get(position));
                viewHolder.tview8.setText(mTienOm.get(position));
                viewHolder.tview1.setText(mTienchuyen.get(position));
                viewHolder.tview4.setText(mTienTon.get(position));
                viewHolder.tview2.setText((position + 1) + "");
            } else {
//                viewHolder.tview5.setTextColor(ViewCompat.MEASURED_STATE_MASK);
//                viewHolder.tview7.setTextColor(ViewCompat.MEASURED_STATE_MASK);
//                viewHolder.tview8.setTextColor(ViewCompat.MEASURED_STATE_MASK);
//                viewHolder.tview1.setTextColor(ViewCompat.MEASURED_STATE_MASK);
//                viewHolder.tview4.setTextColor(ViewCompat.MEASURED_STATE_MASK);
                viewHolder.tview5.setText(mSo.get(position));
                viewHolder.tview7.setText(mTienNhan.get(position));
                viewHolder.tview8.setText(mTienOm.get(position));
                viewHolder.tview1.setText(mTienchuyen.get(position));
                viewHolder.tview4.setText(mTienTon.get(position));
                viewHolder.tview2.setText((position + 1) + "");
            }
            return view;
        }
    }

    @Override // tamhoang.ldpro4.Fragment.Frag_CanChuyen
    public void Dialog(int poin) {
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.frag_canchuyen1);
        dialog.getWindow().setLayout(-1, -2);
        String replaceAll = this.xuatDan.replaceAll(",x", "x");
        this.Dachuyen = false;
        Spinner spinner = dialog.findViewById(R.id.sprin_tenkhach);
        EditText edtXuatDan = dialog.findViewById(R.id.edt_XuatDan);
        Button button = dialog.findViewById(R.id.btn_chuyendi);
        edtXuatDan.setText("");
        edtXuatDan.setText(this.xuatDan.replaceAll(",x", "x"));
        try {
            Cursor GetData = this.db.GetData("Select * From tbl_kh_new WHERE type_kh <> 1 ORDER BY ten_kh");
            this.mContact.clear();
            this.mMobile.clear();
            this.mKhongMax.clear();
            this.mAppuse.clear();
            while (GetData.moveToNext()) {
                if (GetData.getString(2).indexOf("sms") > -1) {
                    this.mContact.add(GetData.getString(0));
                    this.mMobile.add(GetData.getString(1));
                    this.mKhongMax.add(GetData.getString(6));
                    this.mAppuse.add(GetData.getString(2));
                } else if (GetData.getString(2).indexOf("TL") > -1) {
                    this.mContact.add(GetData.getString(0));
                    this.mMobile.add(GetData.getString(1));
                    this.mKhongMax.add(GetData.getString(6));
                    this.mAppuse.add(GetData.getString(2));
                } else if (MainActivity.arr_TenKH.indexOf(GetData.getString(1)) > -1) {
                    this.mContact.add(GetData.getString(0));
                    this.mMobile.add(GetData.getString(1));
                    this.mKhongMax.add(GetData.getString(6));
                    this.mAppuse.add(GetData.getString(2));
                }
            }
            if (GetData != null && !GetData.isClosed()) {
                GetData.close();
            }
            spinner.setAdapter(new ArrayAdapter(getActivity(), R.layout.spinner_item, this.mContact));
        } catch (SQLException unused) {
        }
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: tamhoang.ldpro4.Fragment.Frag_CanChuyenNew.18
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                mSpiner = position;
                try {
                    jsonKhongmax = new JSONObject(mKhongMax.get(mSpiner));
                    if (radio_deb.isChecked() && radio_de.isChecked() && jsonKhongmax.getString("danDe").length() > 0) {
                        Frag_CanChuyenNew frag_CanChuyenNew = Frag_CanChuyenNew.this;
                        edtXuatDan.setText(frag_CanChuyenNew.TaoTinDe(frag_CanChuyenNew.mContact.get(mSpiner)));
                        return;
                    }
                    if (radio_lo.isChecked() && jsonKhongmax.getString("danLo").length() > 0) {
                        Frag_CanChuyenNew frag_CanChuyenNew2 = Frag_CanChuyenNew.this;
                        edtXuatDan.setText(frag_CanChuyenNew2.TaoTinLo(frag_CanChuyenNew2.mContact.get(mSpiner)));
                        return;
                    }
                    if (radio_loa.isChecked() && jsonKhongmax.getString("danLo").length() > 0) {
                        Frag_CanChuyenNew frag_CanChuyenNew3 = Frag_CanChuyenNew.this;
                        edtXuatDan.setText(frag_CanChuyenNew3.TaoTinLoA(frag_CanChuyenNew3.mContact.get(mSpiner)));
                        return;
                    }
                    if (radio_xi.isChecked() && (jsonKhongmax.getInt("xien2") > 0 || jsonKhongmax.getInt("xien3") > 0 || jsonKhongmax.getInt("xien4") > 0)) {
                        Frag_CanChuyenNew frag_CanChuyenNew4 = Frag_CanChuyenNew.this;
                        edtXuatDan.setText(frag_CanChuyenNew4.TaoTinXi(frag_CanChuyenNew4.mContact.get(mSpiner)));
                        return;
                    }
                    if (radio_bc.isChecked() && jsonKhongmax.getInt("cang") > 0) {
                        Frag_CanChuyenNew frag_CanChuyenNew5 = Frag_CanChuyenNew.this;
                        edtXuatDan.setText(frag_CanChuyenNew5.TaoTinCang(frag_CanChuyenNew5.mContact.get(mSpiner)));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        button.setOnClickListener(v -> {
            int i;
            String str;
            String[] strArr;
            String str2;
            StringBuilder sb;
            String str3;
            String Get_date = MainActivity.Get_date();
            int i3 = 0;
            try {
                i = MainActivity.jSon_Setting.getInt("gioi_han_tin");
            } catch (JSONException e) {
                e.printStackTrace();
                i = 0;
            }
            int i4 = 2000;
            if (i == 1) {
                i4 = 3000;
            } else if (i == 2) {
                i4 = 155;
            } else if (i == 3) {
                i4 = 315;
            } else if (i == 4) {
                i4 = 475;
            } else if (i == 5) {
                i4 = 995;
            }
            String str4 = "";
            if (mMobile.size() <= 0 || edtXuatDan.getText().toString().length() <= 0 || Dachuyen) {
                str = "";
                if (edtXuatDan.getText().toString().length() != 0) {
                    if (Dachuyen) {
                        dialog.cancel();
                    } else {
                        Toast.makeText(getActivity(), "Chưa có chủ để chuyển tin!", Toast.LENGTH_LONG).show();
                    }
                }
            } else {
                Dachuyen = true;
                String trim = edtXuatDan.getText().toString().replaceAll("'", " ").trim();
                edtXuatDan.setText("");
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
                    Cursor GetData22 = db.GetData("Select max(so_tin_nhan) from tbl_tinnhanS WHERE ngay_nhan = '" + Get_date + "' AND so_dienthoai = '" + mMobile.get(mSpiner) + "' AND type_kh = 2");
                    GetData22.moveToFirst();
                    int i5 = GetData22.getInt(0) + 1;
                    String str5 = "Lo:";
                    if (trim.substring(0, 3).indexOf("De") > -1) {
                        str5 = "De:";
                        strArr = trim.replaceAll("De:", "").split(" ");
                    } else if (trim.substring(0, 3).indexOf("Loa") > -1) {
                        strArr = trim.replaceAll("Loa:", "").split(" ");
                        str5 = "Loa:";
                    } else if (trim.substring(0, 3).indexOf("Lo") > -1) {
                        strArr = trim.replaceAll("Lo:", "").split(" ");
                    } else if (trim.substring(0, 5).indexOf("Cang") > -1) {
                        strArr = trim.replaceAll("Cang:\n", "").split(" ");
                        str5 = "Cang:";
                    } else if (trim.substring(0, 3).indexOf("Xi") > -1) {
                        strArr = trim.replaceAll("Xien:\n", "").replaceAll("d:", "0").replaceAll("\n", " ").split(" ");
                        str5 = "Xien:";
                    } else {
                        str5 = "";
                        strArr = null;
                    }
                    if (str5 != "Xien:") {
                        String str6 = "";
                        int i6 = 0;
                        while (i6 < strArr.length) {
                            String str7 = "x";
                            String substring = strArr[i6].substring(i3, strArr[i6].indexOf("x"));
                            String replaceAll2 = strArr[i6].substring(strArr[i6].indexOf("x")).replaceAll(",", str4);
                            String[] split = substring.split(",");
                            String str8 = str4;
                            while (i3 < split.length) {
                                String replaceAll22 = str6.replaceAll(",x", str7);
                                if (replaceAll22.length() == 0) {
                                    str2 = str7;
                                    if (split.length == 1) {
                                        sb = new StringBuilder();
                                        sb.append(str5);
                                        sb.append("\n");
                                        sb.append(split[i3]);
                                        sb.append(",");
                                        sb.append(replaceAll2);
                                        sb.append(" ");
                                    } else {
                                        sb = new StringBuilder();
                                        sb.append(str5);
                                        sb.append("\n");
                                        sb.append(split[i3]);
                                        sb.append(",");
                                    }
                                    str6 = sb.toString();
                                } else if (replaceAll22.length() + replaceAll2.length() + replaceAll2.length() >= i4) {
                                    if (i3 > 0) {
                                        replaceAll22 = replaceAll22 + replaceAll2;
                                    }
                                    str2 = str7;
                                    Xulytin(i5, replaceAll22, 1);
                                    i5++;
                                    if (i3 >= split.length - 1) {
                                        str3 = str5 + "\n" + split[i3] + "," + replaceAll2 + " ";
                                        str6 = str3;
                                        break;
                                    }
                                    str6 = str5 + "\n" + split[i3] + ",";
                                } else {
                                    if (i3 >= split.length - 1) {
                                        str3 = replaceAll22 + split[i3] + "," + replaceAll2 + " ";
                                        str6 = str3;
                                        break;
                                    }
                                    str6 = replaceAll22 + split[i3] + ",";
                                    str2 = str7;
                                }
                                i3++;
                                str7 = str2;
                            }
                            i6++;
                            str4 = str8;
                            i3 = 0;
                        }
                        str = str4;
                        if (str6.length() > 0) {
                            Xulytin(i5, str6, 1);
                        }
                    } else {
                        str = "";
                        String str9 = str;
                        for (int i7 = 0; i7 < strArr.length; i7++) {
                            if (str9.length() == 0) {
                                str9 = str5 + "\n" + strArr[i7] + " ";
                            } else if (str9.length() + strArr[i7].length() < i4) {
                                str9 = str9 + strArr[i7] + " ";
                            } else {
                                Xulytin(i5, str9, 1);
                                i5++;
                                str9 = str5 + "\n" + strArr[i7] + " ";
                            }
                        }
                        if (str9.length() > 0) {
                            Xulytin(i5, str9, 1);
                            if (GetData22 != null) {
                                GetData22.close();
                            }
                        }
                    }
                }
                Toast.makeText(getActivity(), "Đã chuyển tin!", Toast.LENGTH_LONG).show();
            }
            xemlv();
            min = 0;
            max = 100;
            rangeSeekBar.setRange(min, max);
            edt_tien.setText(str);
        });
        dialog.getWindow().setLayout(-1, -2);
        dialog.setCancelable(true);
        dialog.setTitle("Xem dạng:");
        dialog.show();
    }

    public String TaoTinLoA(String str) {
        JSONObject jSONObject = new JSONObject();
        Cursor GetData = this.db.GetData("Select so_chon, Sum(diem) FROM tbl_soctS Where ten_kh = '" + str + "' AND type_kh = 2 AND the_loai = 'loa' AND ngay_nhan = '" + MainActivity.Get_date() + "' Group by so_chon");
        while (GetData.moveToNext()) {
            try {
                try {
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.put("Da_chuyen", GetData.getInt(1));
                    jSONObject2.put("Se_chuyen", 0);
                    jSONObject.put(GetData.getString(0), jSONObject2);
                } catch (Exception e) {
                    e.printStackTrace();
                    if (!GetData.isClosed()) {
                    }
                }
                if (!GetData.isClosed()) {
                    GetData.close();
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
        Collections.sort(arrayList, (Comparator<JSONObject>) (jSONObject3, jSONObject22) -> {
            Integer num;
            Integer num2 = 0;
            try {
                num = Integer.valueOf(jSONObject3.getInt("Se_chuyen"));
                num2 = Integer.valueOf(jSONObject22.getInt("Se_chuyen"));
            } catch (Exception unused) {
                num = num2;
            }
            return num2.compareTo(num);
        });
        this.xuatDan = "Loa:";
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
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
        if (this.xuatDan.length() > 4 && i > 0) {
            this.xuatDan += "x" + i2 + "d ";
        }
        return this.xuatDan;
    }

    public String XuatDanTon3(String arg20, String arg21, int arg22, int arg23) {
        String str;
        String str2 = "";
        int i;
        int i2;
        int i3;
        String str3;
        int i4;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        simpleDateFormat.setTimeZone(TimeZone.getDefault());
        String format = simpleDateFormat.format(calendar.getTime());
        String str4 = "";
        int parseInt = Integer.parseInt((arg21.length() == 0 ? "0" : arg21).replaceAll("%", "").replaceAll("n", "").replaceAll("k", "").replaceAll("d", "").replaceAll(">", ""));
        String str5 = "n ";
        String str6 = "d ";
        if (arg20 == "deb") {
            str = "De:";
            str4 = "Om_deB";
            str6 = "(the_loai = 'deb' or the_loai = 'det')";
        } else if (arg20 == "dea") {
            str = "Dau DB:";
            str4 = "Om_deA";
            str6 = "the_loai = 'dea'";
        } else if (arg20 == "dec") {
            str4 = "Om_deC";
            str = "the_loai = 'dec'";
            str6 = "n ";
            str5 = "Dau nhat:";
        } else if (arg20 == "ded") {
            str = "Dit nhat:";
            str4 = "Om_deD";
            str6 = "the_loai = 'ded'";
        } else {
            if (arg20 == "loa") {
                str2 = "Loa:";
                str = "the_loai = 'loa'";
            } else if (arg20 == "lo") {
                str2 = "Lo:";
                str = "the_loai = 'lo'";
            } else {
                str = null;
                str6 = "n ";
                str5 = "";
            }
            str5 = str2;
            str4 = "Om_lo";
        }
        Cursor GetData = this.db.GetData("Select tbl_soctS.So_chon\n, Sum((tbl_soctS.type_kh = 1) * (100-tbl_soctS.diem_khachgiu)*diem_quydoi/100) as diem\n, so_om." + str4 + " + sum(tbl_soctS.diem_dly_giu*tbl_soctS.diem_quydoi/100) as So_Om\n, Sum((tbl_soctS.type_kh =2) * tbl_soctS.diem_quydoi) as chuyen\n, Sum((tbl_soctS.type_kh =1) * (100-tbl_soctS.diem_khachgiu-tbl_soctS.diem_dly_giu)*diem_quydoi/100) - Sum((tbl_soctS.type_kh =2) * tbl_soctS.diem_quydoi) - so_om." + str4 + " as ton\n, so_nhay  From so_om Left Join tbl_soctS On tbl_soctS.so_chon = so_om.So\n Where tbl_soctS.ngay_nhan='" + format + "' AND " + str + " GROUP by so_om.So Order by ton DESC, diem DESC");
        try {
            if (MainActivity.jSon_Setting.getInt("lam_tron") != 3) {
                i = MainActivity.jSon_Setting.getInt("lam_tron") == 0 ? 1 : 100;
                if (MainActivity.jSon_Setting.getInt("lam_tron") == 1) {
                    i = 10;
                }
                if (MainActivity.jSon_Setting.getInt("lam_tron") == 2) {
                    i = 50;
                }
            } else {
                i = 100;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            i = 1;
        }
        int i5 = arg22 > 0 ? arg22 - 1 : arg22;
        int i6 = 0;
        int i7 = 0;
        int i8 = 0;
        while (GetData.moveToNext()) {
            if (i6 < i5 || i6 > arg23 - 1) {
                i2 = i5;
            } else {
                if (parseInt == 0) {
                    i3 = (GetData.getInt(4) / i) * i;
                    i2 = i5;
                } else {
                    i2 = i5;
                    i3 = (arg21.indexOf("%") > -1 ? ((GetData.getInt(4) * parseInt) / i) / 100 : arg21.indexOf(">") > -1 ? (GetData.getInt(4) - parseInt) / i : GetData.getInt(4) > parseInt ? parseInt / i : GetData.getInt(4) / i) * i;
                }
                if (i3 > 0) {
                    if (i8 > i3) {
                        str3 = (str5 + "x" + i8 + str6) + GetData.getString(0) + ",";
                        i4 = 1;
                        i7 = 0;
                    } else {
                        str3 = str5 + GetData.getString(0) + ",";
                        i4 = 1;
                    }
                    i7 += i4;
                    str5 = str3;
                    i8 = i3;
                }
            }
            i6++;
            i5 = i2;
        }
        if (str5.length() > 4 && i7 > 0) {
            str5 = str5 + "x" + i8 + str6;
        }
        if (GetData != null && !GetData.isClosed()) {
            GetData.close();
        }
        return str5;
    }

    /* JADX WARN: Removed duplicated region for block: B:256:0x0721  */
    /* JADX WARN: Removed duplicated region for block: B:259:0x07d4  */
    /* JADX WARN: Removed duplicated region for block: B:265:0x07f0  */
    /* JADX WARN: Removed duplicated region for block: B:266:0x073b  */
    /* JADX WARN: Removed duplicated region for block: B:284:0x07fd  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x007a  */
    @Override // tamhoang.ldpro4.Fragment.Frag_CanChuyen
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void btn_click() throws JSONException {
        int i;
        int i2;
        String str;
        String str2;
        CharSequence charSequence;
        int parseInt;
        int i5 = 0;
        this.xuatDan = "";
        String Get_date = MainActivity.Get_date();
        xemlv();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getDefault());
        simpleDateFormat2.setTimeZone(TimeZone.getDefault());
        String format = simpleDateFormat.format(calendar.getTime());
        if (MainActivity.jSon_Setting.getInt("lam_tron") != 0) {
            if (MainActivity.jSon_Setting.getInt("lam_tron") == 1) {
                i5 = 10;
            } else if (MainActivity.jSon_Setting.getInt("lam_tron") == 2) {
                i5 = 50;
            } else if (MainActivity.jSon_Setting.getInt("lam_tron") == 3) {
                i5 = 100;
            }
            i = i5;
            if (format.indexOf(Get_date) > -1) {
                Toast.makeText(getActivity(), "Không làm việc với dữ liệu ngày cũ!", Toast.LENGTH_LONG).show();
                return;
            }
            if (this.edt_tien.getText().toString().length() != 0 && this.edt_tien.getText().toString() != "0") {
                String replaceAll = this.edt_tien.getText().toString().replaceAll("%", "").replaceAll("n", "").replaceAll("k", "").replaceAll("d", "").replaceAll(">", "").replaceAll("\\.", "").replaceAll(",", "");
                if (Congthuc.isNumeric(replaceAll)) {
                    i2 = Integer.parseInt(replaceAll);
                    str = this.DangXuat;
                    if (str != "(the_loai = 'deb' or the_loai = 'det')" || str == "the_loai = 'lo'" || str == "the_loai = 'loa'" || str == "the_loai = 'dea'" || str == "the_loai = 'dec'" || str == "the_loai = 'ded'") {
                        str2 = this.DangXuat;
                        if (str2 != "(the_loai = 'deb' or the_loai = 'det')") {
                            this.xuatDan = this.db.XuatDanTon2("deb", this.edt_tien.getText().toString(), this.min, this.max);
                        } else if (str2 == "the_loai = 'dea'") {
                            this.xuatDan = this.db.XuatDanTon2("dea", this.edt_tien.getText().toString(), this.min, this.max);
                        } else if (str2 == "the_loai = 'dec'") {
                            this.xuatDan = this.db.XuatDanTon2("dec", this.edt_tien.getText().toString(), this.min, this.max);
                        } else if (str2 == "the_loai = 'ded'") {
                            this.xuatDan = this.db.XuatDanTon2("ded", this.edt_tien.getText().toString(), this.min, this.max);
                        } else if (str2 == "the_loai = 'loa'") {
                            try {
                                this.xuatDan = XuatDanTon3("loa", this.edt_tien.getText().toString(), this.min, this.max);
                            } catch (Exception e2) {
                                e2.printStackTrace();
                            }
                        } else {
                            this.xuatDan = this.db.XuatDanTon2("lo", this.edt_tien.getText().toString(), this.min, this.max);
                        }
                        if (this.xuatDan.indexOf(":") <= -1) {
                            String replaceAll2 = this.xuatDan.trim().replaceAll("\n", "");
                            if (replaceAll2.charAt(replaceAll2.length() - 1) != ':') {
                                Dialog(1);
                                return;
                            }
                        }
                        Toast.makeText(getActivity(), "Không có số liệu!", Toast.LENGTH_LONG).show();
                        return;
                    }
                    String str3 = ".";
                    if (str == "the_loai = 'xi'") {
                        this.xuatDan = "Xien:\n";
                        int i6 = this.min;
                        while (i6 < this.mSo.size()) {
                            int parseInt2 = (this.edt_tien.getText().toString().indexOf("%") > -1 ? Integer.parseInt(this.mTienTon.get(i6).replace(str3, "")) / i : this.edt_tien.getText().toString().indexOf(">") > -1 ? Integer.parseInt(this.mTienTon.get(i6).replace(str3, "")) / i : i2 == 0 ? Integer.parseInt(this.mTienTon.get(i6).replace(str3, "")) / i : Integer.parseInt(this.mTienTon.get(i6).replace(str3, "")) > i2 ? i2 / i : Integer.parseInt(this.mTienTon.get(i6).replace(str3, "")) / i) * i;
                            int i7 = i;
                            String str4 = str3;
                            if (this.edt_tien.getText().toString().indexOf("%") > -1) {
                                if (parseInt2 > 0) {
                                    try {
                                        if (MainActivity.jSon_Setting.getInt("chuyen_xien") > 0) {
                                            this.xuatDan += this.mSo.get(i6) + "x" + ((parseInt2 * i2) / 1000) + "d ";
                                        } else if (MainActivity.jSon_Setting.getInt("chuyen_xien") == 0) {
                                            this.xuatDan += this.mSo.get(i6) + "x" + ((parseInt2 * i2) / 100) + "n ";
                                        }
                                    } catch (JSONException e3) {
                                        e3.printStackTrace();
                                    }
                                }
                            } else if (this.edt_tien.getText().toString().indexOf(">") > -1) {
                                if (parseInt2 > i2) {
                                    try {
                                        if (MainActivity.jSon_Setting.getInt("chuyen_xien") > 0) {
                                            this.xuatDan += this.mSo.get(i6) + "x" + ((parseInt2 - i2) / 10) + "d ";
                                        } else if (MainActivity.jSon_Setting.getInt("chuyen_xien") == 0) {
                                            this.xuatDan += this.mSo.get(i6) + "x" + (parseInt2 - i2) + "n ";
                                        }
                                    } catch (JSONException e4) {
                                        e4.printStackTrace();
                                    }
                                }
                            } else if (this.edt_tien.getText().toString().indexOf(">") == -1 && this.edt_tien.getText().toString().indexOf("%") == -1 && parseInt2 > 0) {
                                try {
                                    if (MainActivity.jSon_Setting.getInt("chuyen_xien") > 0) {
                                        this.xuatDan += this.mSo.get(i6) + "x" + (parseInt2 / 10) + "d ";
                                    } else if (MainActivity.jSon_Setting.getInt("chuyen_xien") == 0) {
                                        this.xuatDan += this.mSo.get(i6) + "x" + parseInt2 + "n ";
                                    }
                                } catch (JSONException e5) {
                                    e5.printStackTrace();
                                }
                            }
                            i6++;
                            i = i7;
                            str3 = str4;
                        }
                        if (this.xuatDan.indexOf(":") > -1) {
                            String replaceAll3 = this.xuatDan.trim().replaceAll("\n", "");
                            if (replaceAll3.charAt(replaceAll3.length() - 1) != ':') {
                                Dialog(1);
                                return;
                            }
                            Toast.makeText(getActivity(), "Không có số liệu!", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        CharSequence charSequence2 = ".";
                        int i8 = i;
                        if (str == "the_loai = 'bc'") {
                            this.xuatDan = "Cang:\n";
                            int i9 = this.min;
                            int i10 = 0;
                            while (i9 < this.mSo.size()) {
                                if (i2 == 0) {
                                    charSequence = charSequence2;
                                    parseInt = Integer.parseInt(this.mTienTon.get(i9).replace(charSequence, "")) / i8;
                                } else {
                                    charSequence = charSequence2;
                                    parseInt = this.edt_tien.getText().toString().indexOf("%") > -1 ? ((Integer.parseInt(this.mTienTon.get(i9).replace(charSequence, "")) * i2) / i8) / 100 : this.edt_tien.getText().toString().indexOf(">") > -1 ? (Integer.parseInt(this.mTienTon.get(i9).replace(charSequence, "")) - i2) / i8 : Integer.parseInt(this.mTienTon.get(i9).replace(charSequence, "")) > i2 ? i2 / i8 : Integer.parseInt(this.mTienTon.get(i9).replace(charSequence, "")) / i8;
                                }
                                int i11 = parseInt * i8;
                                if (i11 > 0) {
                                    if (i10 > i11) {
                                        this.xuatDan += "x" + i10 + "n ";
                                        this.xuatDan += this.mSo.get(i9) + ",";
                                    } else {
                                        this.xuatDan += this.mSo.get(i9) + ",";
                                    }
                                    i10 = i11;
                                }
                                i9++;
                                charSequence2 = charSequence;
                            }
                            if (this.xuatDan.length() > 4) {
                                this.xuatDan += "x" + i10 + "n";
                            }
                            if (this.xuatDan.indexOf(":") > -1) {
                                String str5 = this.xuatDan;
                                if (str5.substring(str5.indexOf(":")).length() > 7) {
                                    if (getActivity().isFinishing()) {
                                        return;
                                    }
                                    Dialog(1);
                                    return;
                                }
                            }
                            Toast.makeText(getActivity(), "Không có số liệu!", Toast.LENGTH_LONG).show();
                            return;
                        }
                        CharSequence charSequence3 = charSequence2;
                        if (str == "the_loai = 'xn'") {
                            this.xuatDan = "Xnhay:\n";
                            int i12 = this.min;
                            while (i12 < this.mSo.size()) {
                                int parseInt3 = (this.edt_tien.getText().toString().indexOf("%") > -1 ? Integer.parseInt(this.mTienTon.get(i12).replace(charSequence3, "")) / i8 : this.edt_tien.getText().toString().indexOf(">") > -1 ? Integer.parseInt(this.mTienTon.get(i12).replace(charSequence3, "")) / i8 : i2 == 0 ? Integer.parseInt(this.mTienTon.get(i12).replace(charSequence3, "")) / i8 : Integer.parseInt(this.mTienTon.get(i12).replace(charSequence3, "")) > i2 ? i2 / i8 : Integer.parseInt(this.mTienTon.get(i12).replace(charSequence3, "")) / i8) * i8;
                                CharSequence charSequence4 = charSequence3;
                                if (this.edt_tien.getText().toString().indexOf("%") > -1) {
                                    if (parseInt3 > 0) {
                                        try {
                                            if (MainActivity.jSon_Setting.getInt("chuyen_xien") > 0) {
                                                this.xuatDan += this.mSo.get(i12) + "x" + ((parseInt3 * i2) / 1000) + "d\n";
                                            } else if (MainActivity.jSon_Setting.getInt("chuyen_xien") == 0) {
                                                StringBuilder sb = new StringBuilder();
                                                sb.append(this.xuatDan);
                                                sb.append(this.mSo.get(i12));
                                                sb.append("x");
                                                sb.append((parseInt3 * i2) / 100);
                                                sb.append("n\n");
                                                this.xuatDan = sb.toString();
                                            }
                                        } catch (JSONException e7) {
                                            e7.printStackTrace();
                                        }
                                    }
                                } else if (this.edt_tien.getText().toString().indexOf(">") > -1) {
                                    if (parseInt3 > i2) {
                                        try {
                                            if (MainActivity.jSon_Setting.getInt("chuyen_xien") > 0) {
                                                StringBuilder sb2 = new StringBuilder();
                                                sb2.append(this.xuatDan);
                                                sb2.append(this.mSo.get(i12));
                                                sb2.append("x");
                                                sb2.append((parseInt3 - i2) / 10);
                                                sb2.append("d\n");
                                                this.xuatDan = sb2.toString();
                                            } else if (MainActivity.jSon_Setting.getInt("chuyen_xien") == 0) {
                                                this.xuatDan += this.mSo.get(i12) + "x" + (parseInt3 - i2) + "n\n";
                                            }
                                        } catch (JSONException e9) {
                                            e9.printStackTrace();
                                        }
                                    }
                                } else if (this.edt_tien.getText().toString().indexOf(">") == -1 && this.edt_tien.getText().toString().indexOf("%") == -1 && parseInt3 > 0) {
                                    try {
                                        if (MainActivity.jSon_Setting.getInt("chuyen_xien") > 0) {
                                            this.xuatDan += this.mSo.get(i12) + "x" + (parseInt3 / 10) + "d\n";
                                        } else if (MainActivity.jSon_Setting.getInt("chuyen_xien") == 0) {
                                            this.xuatDan += this.mSo.get(i12) + "x" + parseInt3 + "n\n";
                                        }
                                    } catch (JSONException e10) {
                                        e10.printStackTrace();
                                    }
                                }
                                i12++;
                                charSequence3 = charSequence4;
                            }
                        }
                    }
                    if (this.xuatDan.indexOf(":") > -1) {
                        String replaceAll4 = this.xuatDan.trim().replaceAll("\n", "");
                        if (replaceAll4.charAt(replaceAll4.length() - 1) != ':') {
                            Dialog(1);
                            return;
                        }
                    }
                    Toast.makeText(getActivity(), "Không có số liệu!", Toast.LENGTH_LONG).show();
                    return;
                }
            }
            i2 = 0;
            str = this.DangXuat;
            if (str != "(the_loai = 'deb' or the_loai = 'det')") {
            }
            str2 = this.DangXuat;
            if (str2 != "(the_loai = 'deb' or the_loai = 'det')") {
            }
            if (this.xuatDan.indexOf(":") <= -1) {
            }
            Toast.makeText(getActivity(), "Không có số liệu!", Toast.LENGTH_LONG).show();
            return;
        }
        i5 = 1;
        i = i5;
        if (format.indexOf(Get_date) > -1) {
        }
    }

    @Override // tamhoang.ldpro4.Fragment.Frag_CanChuyen, android.support.v4.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.frag_canchuyen, container, false);
        this.v = inflate;
        this.radio_de = (RadioButton) inflate.findViewById(R.id.radio_de);
        this.radio_lo = (RadioButton) this.v.findViewById(R.id.radio_lo);
        this.radio_loa = (RadioButton) this.v.findViewById(R.id.radio_loa);
        this.radio_xi = (RadioButton) this.v.findViewById(R.id.radio_xi);
        this.radio_bc = (RadioButton) this.v.findViewById(R.id.radio_bc);
        this.radio_dea = (RadioButton) this.v.findViewById(R.id.radio_dea);
        this.radio_deb = (RadioButton) this.v.findViewById(R.id.radio_deb);
        this.radio_dec = (RadioButton) this.v.findViewById(R.id.radio_dec);
        this.radio_ded = (RadioButton) this.v.findViewById(R.id.radio_ded);
        this.btn_Xuatso =  this.v.findViewById(R.id.btn_Xuatso);
        LinearLayout linearLayout =  this.v.findViewById(R.id.ln_xi);
        this.ln_xi = linearLayout;
        linearLayout.setVisibility(View.GONE);
        LinearLayout linearLayout2 =  this.v.findViewById(R.id.li_loaide);
        this.li_loaide = linearLayout2;
        linearLayout2.setVisibility(View.GONE);
        this.edt_tien =  this.v.findViewById(R.id.edt_tien);
        this.check_x2 = (CheckBox) this.v.findViewById(R.id.check_x2);
        this.check_x3 = (CheckBox) this.v.findViewById(R.id.check_x3);
        this.check_x4 = (CheckBox) this.v.findViewById(R.id.check_x4);
        this.check_xn = (CheckBox) this.v.findViewById(R.id.check_xn);
        this.no_rp_number = (ListView) this.v.findViewById(R.id.lview);
        this.db = new Database(getActivity());
        this.handler = new Handler();
        this.handler.postDelayed(this.runnable, 1000L);
        this.rangeSeekBar = new RangeSeekBar(getActivity());
        rangeSeekBar.setRange(0, 100);
        this.seekBarLayout =  this.v.findViewById(R.id.scrollView);
        seekBarLayout.addView(this.rangeSeekBar);
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

        this.radio_de.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: tamhoang.ldpro4.Fragment.Frag_CanChuyenNew.3
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (radio_de.isChecked()) {
                    seekBarLayout.setVisibility(View.VISIBLE);
                    String Get_date = MainActivity.Get_date();
                    try {
                        Cursor GetData = db.GetData("Select sum((the_loai = 'dea')* diem) as de_a\n,sum((the_loai = 'deb')* diem) as de_b\n,sum((the_loai = 'det')* diem) as de_t\n,sum((the_loai = 'dec')* diem) as de_c\n,sum((the_loai = 'ded')* diem) as de_d\nFrom tbl_soctS \nWhere ngay_nhan = '" + Get_date + "'");
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
                                ln_xi.setVisibility(View.GONE);
                                li_loaide.setVisibility(View.GONE);
                                radio_deb.setChecked(true);
                                xem_RecycView();
                            } else if (iArr[0] == 0 && iArr[1] == 0 && iArr[2] == 0 && iArr[3] == 0 && iArr[4] == 0) {
                                DangXuat = "(the_loai = 'deb' or the_loai = 'det')";
                                ln_xi.setVisibility(View.GONE);
                                li_loaide.setVisibility(View.GONE);
                                radio_deb.setChecked(true);
                                xem_RecycView();
                            } else {
                                DangXuat = "(the_loai = 'deb' or the_loai = 'det')";
                                ln_xi.setVisibility(View.GONE);
                                li_loaide.setVisibility(View.VISIBLE);
                                radio_deb.setChecked(true);
                                xem_RecycView();
                            }
                            if (GetData.isClosed() || GetData == null || GetData.isClosed()) {
                                return;
                            }
                            GetData.close();
                            return;
                        }
                        DangXuat = "(the_loai = 'deb' or the_loai = 'det')";
                    } catch (SQLException unused) {
                        DangXuat = "(the_loai = 'deb' or the_loai = 'det')";
                    }
                }
            }
        });
        this.radio_dea.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: tamhoang.ldpro4.Fragment.Frag_CanChuyenNew.4
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (radio_dea.isChecked()) {
                    DangXuat = "the_loai = 'dea'";
                    ln_xi.setVisibility(View.GONE);
                    xem_RecycView();
                }
            }
        });
        this.radio_deb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: tamhoang.ldpro4.Fragment.Frag_CanChuyenNew.5
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (radio_deb.isChecked()) {
                    DangXuat = "(the_loai = 'deb' or the_loai = 'det')";
                    ln_xi.setVisibility(View.GONE);
                    xem_RecycView();
                }
            }
        });
        this.radio_dec.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: tamhoang.ldpro4.Fragment.Frag_CanChuyenNew.6
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (radio_dec.isChecked()) {
                    DangXuat = "the_loai = 'dec'";
                    ln_xi.setVisibility(View.GONE);
                    xem_RecycView();
                }
            }
        });
        this.radio_ded.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: tamhoang.ldpro4.Fragment.Frag_CanChuyenNew.7
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (radio_ded.isChecked()) {
                    DangXuat = "the_loai = 'ded'";
                    ln_xi.setVisibility(View.GONE);
                    xem_RecycView();
                }
            }
        });
        this.radio_lo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: tamhoang.ldpro4.Fragment.Frag_CanChuyenNew.8
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (radio_lo.isChecked()) {
                    DangXuat = "the_loai = 'lo'";
                    ln_xi.setVisibility(View.GONE);
                    li_loaide.setVisibility(View.GONE);
                    seekBarLayout.setVisibility(View.VISIBLE);
                    xem_RecycView();
                }
            }
        });
        this.radio_loa.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: tamhoang.ldpro4.Fragment.Frag_CanChuyenNew.9
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (radio_loa.isChecked()) {
                    DangXuat = "the_loai = 'loa'";
                    ln_xi.setVisibility(View.GONE);
                    li_loaide.setVisibility(View.GONE);
                    seekBarLayout.setVisibility(View.VISIBLE);
                    xem_RecycView();
                }
            }
        });
        this.radio_xi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: tamhoang.ldpro4.Fragment.Frag_CanChuyenNew.10
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (radio_xi.isChecked()) {
                    DangXuat = "the_loai = 'xi'";
                    seekBarLayout.setVisibility(View.GONE);
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
            }
        });
        this.radio_bc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: tamhoang.ldpro4.Fragment.Frag_CanChuyenNew.11
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (radio_bc.isChecked()) {
                    DangXuat = "the_loai = 'bc'";
                    seekBarLayout.setVisibility(View.GONE);
                    ln_xi.setVisibility(View.GONE);
                    li_loaide.setVisibility(View.GONE);
                    xem_RecycView();
                }
            }
        });
        this.check_x2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: tamhoang.ldpro4.Fragment.Frag_CanChuyenNew.12
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (check_x2.isChecked()) {
                    DangXuat = "the_loai = 'xi'";
                    lay_x2 = "length(so_chon) = 5 ";
                    check_xn.setChecked(false);
                } else {
                    lay_x2 = "";
                }
                xem_RecycView();
            }
        });
        this.check_x3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: tamhoang.ldpro4.Fragment.Frag_CanChuyenNew.13
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (check_x3.isChecked()) {
                    DangXuat = "the_loai = 'xi'";
                    lay_x3 = "OR length(so_chon) = 8 ";
                    check_xn.setChecked(false);
                } else {
                    lay_x3 = "";
                }
                xem_RecycView();
            }
        });
        this.check_x4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: tamhoang.ldpro4.Fragment.Frag_CanChuyenNew.14
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (check_x4.isChecked()) {
                    DangXuat = "the_loai = 'xi'";
                    lay_x4 = "OR length(so_chon) = 11 ";
                    check_xn.setChecked(false);
                } else {
                    lay_x4 = "";
                }
                xem_RecycView();
            }
        });
        this.check_xn.setOnClickListener(new View.OnClickListener() { // from class: tamhoang.ldpro4.Fragment.Frag_CanChuyenNew.15
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (check_xn.isChecked()) {
                    DangXuat = "the_loai = 'xn'";
                    check_x2.setChecked(false);
                    check_x3.setChecked(false);
                    check_x4.setChecked(false);
                    xem_RecycView();
                }
            }
        });
        this.btn_Xuatso.setOnClickListener(v -> {
            if (Congthuc.isNumeric(edt_tien.getText().toString().replaceAll("%", "").replaceAll("n", "").replaceAll("k", "").replaceAll("d", "").replaceAll(">", "").replaceAll("\\.", "")) || edt_tien.getText().toString().length() == 0) {
                try {
                    btn_click();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getActivity(), "Kiểm tra lại tiền!", Toast.LENGTH_LONG).show();
            }
        });
        this.lay_x2 = "length(so_chon) = 5 ";
        this.lay_x3 = "OR length(so_chon) = 8 ";
        this.lay_x4 = "OR length(so_chon) = 11 ";
        this.no_rp_number.setOnItemClickListener((adapterView, view, position, id) -> {
            try {
                Cursor GetData = db.GetData("Select ten_kh, sum(diem_quydoi) From tbl_soctS WHERE so_chon = '" + mSo.get(position) + "' AND ngay_nhan = '" + MainActivity.Get_date() + "' AND type_kh = 1 AND " + DangXuat + " GROUP BY so_dienthoai");
                StringBuilder str = new StringBuilder();
                while (GetData.moveToNext()) {
                    str.append(GetData.getString(0)).append(": ").append(GetData.getString(1)).append("\n");
                }
                if (!GetData.isClosed()) {
                    GetData.close();
                }
                Toast.makeText(getActivity(), str.toString(), Toast.LENGTH_LONG).show();
            } catch (SQLException unused) {
                unused.printStackTrace();
            }
        });
        try {
            if (MainActivity.jSon_Setting.getInt("bao_cao_so") == 0) {
                this.sapxep = "diem DESC";
            } else {
                this.sapxep = "ton DESC, diem DESC";
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        xemlv();
        return this.v;
    }

    @Override // tamhoang.ldpro4.Fragment.Frag_CanChuyen, android.support.v4.app.Fragment
    public void onDestroy() {
        this.db.close();
        if (this.handler != null && this.runnable != null) {
            this.handler.removeCallbacks(this.runnable);
        }
        try {
            super.onDestroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // tamhoang.ldpro4.Fragment.Frag_CanChuyen, android.support.v4.app.Fragment
    public void onResume() {
        xem_RecycView();
        super.onResume();
    }

    @Override // tamhoang.ldpro4.Fragment.Frag_CanChuyen
    public void xem_RecycView() {
        String str;
        String Get_date = MainActivity.Get_date();
        this.mSo.clear();
        this.mTienNhan.clear();
        this.mTienOm.clear();
        this.mTienchuyen.clear();
        this.mTienTon.clear();
        this.mNhay.clear();
        String str2 = this.DangXuat;
        if (str2 == "(the_loai = 'deb' or the_loai = 'det')") {
            str = "Select tbl_soctS.So_chon\n, Sum((tbl_soctS.type_kh = 1) * (100-tbl_soctS.diem_khachgiu)*diem_quydoi/100) as diem\n, so_om.Om_deB + sum(tbl_soctS.diem_dly_giu*tbl_soctS.diem_quydoi/100) as So_Om\n, Sum((tbl_soctS.type_kh =2) * tbl_soctS.diem_quydoi) as chuyen\n, Sum((tbl_soctS.type_kh =1) * (100-tbl_soctS.diem_khachgiu-tbl_soctS.diem_dly_giu)*diem_quydoi/100) - Sum((tbl_soctS.type_kh =2) * tbl_soctS.diem_quydoi) - so_om.Om_deB as ton\n, so_nhay  From so_om Left Join tbl_soctS On tbl_soctS.so_chon = so_om.So\n Where tbl_soctS.ngay_nhan='" + Get_date + "' AND (tbl_soctS.the_loai='deb' OR tbl_soctS.the_loai='det') GROUP by so_om.So Order by " + this.sapxep;
        } else if (str2 == "the_loai = 'loa'") {
            str = "Select tbl_soctS.So_chon\n, Sum((tbl_soctS.type_kh = 1) * (100-tbl_soctS.diem_khachgiu)*diem_quydoi/100) as diem\n, so_om.Om_Lo + sum(tbl_soctS.diem_dly_giu*tbl_soctS.diem_quydoi/100) as So_Om\n, Sum((tbl_soctS.type_kh =2) * tbl_soctS.diem_quydoi) as chuyen\n, Sum((tbl_soctS.type_kh =1) * (100-tbl_soctS.diem_khachgiu-tbl_soctS.diem_dly_giu)*diem_quydoi/100) - Sum((tbl_soctS.type_kh =2) * tbl_soctS.diem_quydoi) - so_om.Om_Lo as ton\n, so_nhay  From so_om Left Join tbl_soctS On tbl_soctS.so_chon = so_om.So \n Where tbl_soctS.ngay_nhan='" + Get_date + "' AND tbl_soctS.the_loai='loa' \n GROUP by so_om.So Order by " + this.sapxep;
        } else if (str2 == "the_loai = 'lo'") {
            str = "Select tbl_soctS.So_chon\n, Sum((tbl_soctS.type_kh = 1) * (100-tbl_soctS.diem_khachgiu)*diem_quydoi/100) as diem\n, so_om.Om_Lo + sum(tbl_soctS.diem_dly_giu*tbl_soctS.diem_quydoi/100) as So_Om\n, Sum((tbl_soctS.type_kh =2) * tbl_soctS.diem_quydoi) as chuyen\n, Sum((tbl_soctS.type_kh =1) * (100-tbl_soctS.diem_khachgiu-tbl_soctS.diem_dly_giu)*diem_quydoi/100) - Sum((tbl_soctS.type_kh =2) * tbl_soctS.diem_quydoi) - so_om.Om_Lo as ton\n, so_nhay  From so_om Left Join tbl_soctS On tbl_soctS.so_chon = so_om.So \n Where tbl_soctS.ngay_nhan='" + Get_date + "' AND tbl_soctS.the_loai='lo' \n GROUP by so_om.So Order by " + this.sapxep;
        } else if (str2 == "the_loai = 'dea'") {
            str = "Select tbl_soctS.So_chon\n, Sum((tbl_soctS.type_kh = 1) * (100-tbl_soctS.diem_khachgiu)*diem_quydoi/100) as diem\n, so_om.Om_DeA + sum(tbl_soctS.diem_dly_giu*tbl_soctS.diem_quydoi/100) as So_Om\n, Sum((tbl_soctS.type_kh =2) * tbl_soctS.diem_quydoi) as chuyen\n, Sum((tbl_soctS.type_kh =1) * (100-tbl_soctS.diem_khachgiu-tbl_soctS.diem_dly_giu)*diem_quydoi/100) - Sum((tbl_soctS.type_kh =2) * tbl_soctS.diem_quydoi) - so_om.Om_DeA as ton\n, so_nhay  From so_om Left Join tbl_soctS On tbl_soctS.so_chon = so_om.So \n Where tbl_soctS.ngay_nhan='" + Get_date + "' AND tbl_soctS.the_loai='dea' GROUP by so_chon Order by " + this.sapxep;
        } else if (str2 == "the_loai = 'dec'") {
            str = "Select tbl_soctS.So_chon\n, Sum((tbl_soctS.type_kh = 1) * (100-tbl_soctS.diem_khachgiu)*diem_quydoi/100) as diem\n, so_om.Om_DeC + sum(tbl_soctS.diem_dly_giu*tbl_soctS.diem_quydoi/100) as So_Om\n, Sum((tbl_soctS.type_kh =2) * tbl_soctS.diem_quydoi) as chuyen\n, Sum((tbl_soctS.type_kh =1) * (100-tbl_soctS.diem_khachgiu-tbl_soctS.diem_dly_giu)*diem_quydoi/100) - Sum((tbl_soctS.type_kh =2) * tbl_soctS.diem_quydoi) - so_om.Om_DeC as ton\n, so_nhay  From so_om Left Join tbl_soctS On tbl_soctS.so_chon = so_om.So \n Where tbl_soctS.ngay_nhan='" + Get_date + "' AND tbl_soctS.the_loai='dec' GROUP by so_chon Order by " + this.sapxep;
        } else if (str2 == "the_loai = 'ded'") {
            str = "Select tbl_soctS.So_chon\n, Sum((tbl_soctS.type_kh = 1) * (100-tbl_soctS.diem_khachgiu)*diem_quydoi/100) as diem\n, so_om.Om_DeD + sum(tbl_soctS.diem_dly_giu*tbl_soctS.diem_quydoi/100) as So_Om\n, Sum((tbl_soctS.type_kh =2) * tbl_soctS.diem_quydoi) as chuyen\n, Sum((tbl_soctS.type_kh =1) * (100-tbl_soctS.diem_khachgiu-tbl_soctS.diem_dly_giu)*diem_quydoi/100) - Sum((tbl_soctS.type_kh =2) * tbl_soctS.diem_quydoi) - so_om.Om_DeD as ton\n, so_nhay  From so_om Left Join tbl_soctS On tbl_soctS.so_chon = so_om.So \n Where tbl_soctS.ngay_nhan='" + Get_date + "' AND tbl_soctS.the_loai='ded' GROUP by so_chon Order by " + this.sapxep;
        } else if (str2 == "the_loai = 'xi'") {
            String str3 = "";
            if (this.lay_x2 != "" || this.lay_x3 != "" || this.lay_x4 != "") {
                str3 = (" And (" + this.lay_x2 + this.lay_x3 + this.lay_x4 + ")").replaceAll("\\(OR", "(");
            }
            Cursor GetData = this.db.GetData("Select * From So_om WHERE ID = 1");
            GetData.moveToFirst();
            str = "SELECT so_chon, sum((type_kh =1)*(100-diem_khachgiu)*diem_quydoi)/100 AS diem, ((length(so_chon) = 5) * " + GetData.getString(7) + " +(length(so_chon) = 8) * " + GetData.getString(8) + " +(length(so_chon) = 11) * " + GetData.getString(9) + " + sum(diem_dly_giu*diem_quydoi/100)) AS Om, SUm((type_kh =2)*diem) as chuyen , SUm((type_kh =1)*(100-diem_khachgiu-diem_dly_giu)*diem_quydoi/100)-SUm((type_kh =2)*diem) -  ((length(so_chon) = 5) * " + GetData.getString(7) + " +(length(so_chon) = 8) * " + GetData.getString(8) + " +(length(so_chon) = 11) * " + GetData.getString(9) + ") AS ton, so_nhay   From tbl_soctS Where ngay_nhan='" + Get_date + "' AND the_loai='xi'" + str3 + "  GROUP by so_chon Order by ton DESC, diem DESC";
            if (GetData != null && !GetData.isClosed()) {
                GetData.close();
            }
        } else if (str2 == "the_loai = 'bc'") {
            Cursor GetData2 = this.db.GetData("Select * From So_om WHERE ID = 1");
            GetData2.moveToFirst();
            if (GetData2.getInt(10) == 1) {
                this.db.QueryData("Update so_om set om_bc=0 WHERE id = 1");
                GetData2 = this.db.GetData("Select * From So_om WHERE ID = 1");
                GetData2.moveToFirst();
            }
            str = "SELECT so_chon, sum((type_kh = 1)*(100-diem_khachgiu)*diem_quydoi/100) AS diem, " + GetData2.getString(10) + " + sum(diem_dly_giu*diem_quydoi)/100 AS Om, SUm((type_kh = 2)*diem) as Chuyen, sum((type_kh =1)*(100-diem_khachgiu-diem_dly_giu)*diem_quydoi/100) - sum((type_kh =2)*diem) -" + GetData2.getString(10) + " AS ton, so_nhay   From tbl_soctS Where ngay_nhan='" + Get_date + "' AND the_loai='bc' GROUP by so_chon Order by ton DESC, diem DESC";
            if (GetData2 != null && !GetData2.isClosed()) {
                GetData2.close();
            }
        } else if (str2 == "the_loai = 'xn'") {
            str = "SELECT so_chon, sum((type_kh =1)*(diem_quydoi)) AS diem, sum(tbl_soctS.diem_dly_giu) AS Om, SUm((type_kh =2)*diem) as chuyen , SUm((type_kh =1)*diem_ton-(type_kh =2)*diem_ton) AS ton, so_nhay   From tbl_soctS Where ngay_nhan='" + Get_date + "' AND the_loai='xn' GROUP by so_chon Order by ton DESC, diem DESC";
        } else {
            str = null;
        }
        Cursor GetData3 = this.db.GetData(str);
        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        if (GetData3 != null) {
            while (GetData3.moveToNext()) {
                this.mSo.add(GetData3.getString(0));
                this.mTienNhan.add(decimalFormat.format(GetData3.getInt(1)));
                this.mTienOm.add(decimalFormat.format(GetData3.getInt(2)));
                this.mTienchuyen.add(decimalFormat.format(GetData3.getInt(3)));
                this.mTienTon.add(decimalFormat.format(GetData3.getInt(4)));
                this.mNhay.add(Integer.valueOf(GetData3.getInt(5)));
            }
            if (GetData3 != null && !GetData3.isClosed()) {
                GetData3.close();
            }
        }
        if (getActivity() != null) {
            this.no_rp_number.setAdapter((ListAdapter) new So_OmAdapter(getActivity(), R.layout.frag_canchuyen_lv, this.mSo));
        }
    }

    @Override // tamhoang.ldpro4.Fragment.Frag_CanChuyen
    public void xemlv() {
        if (this.DangXuat != null) {
            xem_RecycView();
        } else {
            this.radio_de.setChecked(true);
        }
    }
}
