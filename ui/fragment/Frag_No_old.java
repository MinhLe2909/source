package tamhoang.ldpro4.ui.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import tamhoang.ldpro4.formula.Congthuc;
import tamhoang.ldpro4.ui.home.MainActivity;
import tamhoang.ldpro4.ui.notification.NotificationNewReader;
import tamhoang.ldpro4.R;
import tamhoang.ldpro4.data.Database;

/* loaded from: classes.dex */
public class Frag_No_old extends Fragment {
    Button btn_nt;
    JSONObject caidat_tg;
    int currentIndex;
    Database db;
    Handler handler;
    JSONObject json;
    List<JSONObject> jsonKhachHang;
    LinearLayout lnDea;
    LinearLayout lnDec;
    LinearLayout lnDed;
    LinearLayout lnDet;
    LinearLayout lnXn;
    LinearLayout ln_bca;
    LinearLayout ln_loa;
    LinearLayout ln_xi2;
    LinearLayout ln_xia2;
    ListView lv_no_tinnhan;
    LayoutInflater mInflate;
    int position;
    private ProgressBar progressBar;
    TextView tvAnBC1;
    TextView tvAnBCa;
    TextView tvAnDea;
    TextView tvAnDeb;
    TextView tvAnDec;
    TextView tvAnDed;
    TextView tvAnDet;
    TextView tvAnLo1;
    TextView tvAnLoa;
    TextView tvAnXi2;
    TextView tvAnXia2;
    TextView tvAnXn1;
    TextView tvDiemBc1;
    TextView tvDiemBca;
    TextView tvDiemDea;
    TextView tvDiemDeb;
    TextView tvDiemDec;
    TextView tvDiemDed;
    TextView tvDiemDet;
    TextView tvDiemLo1;
    TextView tvDiemLoa;
    TextView tvDiemXi2;
    TextView tvDiemXia2;
    TextView tvDiemXn1;
    TextView tvKQBc1;
    TextView tvKQBca;
    TextView tvKQDea;
    TextView tvKQDeb;
    TextView tvKQDec;
    TextView tvKQDed;
    TextView tvKQDet;
    TextView tvKQLo1;
    TextView tvKQLoa;
    TextView tvKQXi2;
    TextView tvKQXia2;
    TextView tvKQXn1;
    TextView tvTongTien1;
    View v;
    boolean isSuccess = false;
    private List<String> mSDT = new ArrayList();
    private List<String> mTenKH = new ArrayList();
    private List<String> mTypeKH = new ArrayList();
    private Runnable runnable = new Runnable() { // from class: tamhoang.ldpro4.Fragment.Frag_No_old.1
        @Override // java.lang.Runnable
        public void run() {
            if (MainActivity.sms) {
                lv_No2_KH();
                MainActivity.sms = false;
            }
            handler.postDelayed(this, 1000L);
        }
    };

    /* loaded from: classes.dex */
    public class NoRP_TN_Adapter extends ArrayAdapter {
        public NoRP_TN_Adapter(Context context, int resource, List<JSONObject> objects) {
            super(context, resource, objects);
        }

        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        public View getView(int position, View v, ViewGroup parent) {
            String str;
            String str2;
            View inflate = requireActivity().getLayoutInflater().inflate(R.layout.frag_norp2_lv, null);
            TextView textView = inflate.findViewById(R.id.tv_KhachHang);
            textView.setText(mTenKH.get(position) + "");
            if (mTypeKH.get(position).contains("2")) {
                textView.setTextColor(-16776961);
            }
            JSONObject jSONObject = jsonKhachHang.get(position);
            if (jSONObject.has("dea")) {
                str = "xn";
                try {
                    ( inflate.findViewById(R.id.ln_dea)).setVisibility(View.VISIBLE);
                    JSONObject jSONObject2 = new JSONObject(jSONObject.getString("dea"));
                    ((TextView) inflate.findViewById(R.id.tv_diemDea)).setText(jSONObject2.getString("DiemNhan"));
                    ((TextView) inflate.findViewById(R.id.tv_AnDea)).setText(jSONObject2.getString("AnNhan"));
                    ((TextView) inflate.findViewById(R.id.tv_KQDea)).setText(jSONObject2.getString("KQNhan"));

                    if (jSONObject.has("deb")) {
                        JSONObject jSONObject3 = new JSONObject(jSONObject.getString("deb"));
                        ((TextView) inflate.findViewById(R.id.tv_diemDeb)).setText(jSONObject3.getString("DiemNhan"));
                        ((TextView) inflate.findViewById(R.id.tv_AnDeb)).setText(jSONObject3.getString("AnNhan"));
                        ((TextView) inflate.findViewById(R.id.tv_KQDeb)).setText(jSONObject3.getString("KQNhan"));
                    }
                    if (jSONObject.has("det")) {
                        ( inflate.findViewById(R.id.ln_det)).setVisibility(View.VISIBLE);
                        JSONObject jSONObject4 = new JSONObject(jSONObject.getString("det"));
                        ((TextView) inflate.findViewById(R.id.tv_diemDet)).setText(jSONObject4.getString("DiemNhan"));
                        ((TextView) inflate.findViewById(R.id.tv_AnDet)).setText(jSONObject4.getString("AnNhan"));
                        ((TextView) inflate.findViewById(R.id.tv_KQDet)).setText(jSONObject4.getString("KQNhan"));
                    }
                    if (jSONObject.has("dec")) {
                        ( inflate.findViewById(R.id.ln_dec)).setVisibility(View.VISIBLE);
                        JSONObject jSONObject5 = new JSONObject(jSONObject.getString("dec"));
                        ((TextView) inflate.findViewById(R.id.tv_diemDec)).setText(jSONObject5.getString("DiemNhan"));
                        ((TextView) inflate.findViewById(R.id.tv_AnDec)).setText(jSONObject5.getString("AnNhan"));
                        ((TextView) inflate.findViewById(R.id.tv_KQDec)).setText(jSONObject5.getString("KQNhan"));
                    }
                    if (jSONObject.has("ded")) {
                        ( inflate.findViewById(R.id.ln_ded)).setVisibility(View.VISIBLE);
                        JSONObject jSONObject6 = new JSONObject(jSONObject.getString("ded"));
                        ((TextView) inflate.findViewById(R.id.tv_diemDed)).setText(jSONObject6.getString("DiemNhan"));
                        ((TextView) inflate.findViewById(R.id.tv_AnDed)).setText(jSONObject6.getString("AnNhan"));
                        ((TextView) inflate.findViewById(R.id.tv_KQDed)).setText(jSONObject6.getString("KQNhan"));
                    }
                    if (jSONObject.has("lo")) {
                        JSONObject jSONObject7 = new JSONObject(jSONObject.getString("lo"));
                        ((TextView) inflate.findViewById(R.id.tv_diemLo1)).setText(jSONObject7.getString("DiemNhan"));
                        ((TextView) inflate.findViewById(R.id.tv_AnLo1)).setText(jSONObject7.getString("AnNhan"));
                        ((TextView) inflate.findViewById(R.id.tv_KQLo1)).setText(jSONObject7.getString("KQNhan"));
                    }
                    if (jSONObject.has("loa")) {
                        ( inflate.findViewById(R.id.ln_loa)).setVisibility(View.VISIBLE);
                        JSONObject jSONObject8 = new JSONObject(jSONObject.getString("loa"));
                        ((TextView) inflate.findViewById(R.id.tv_diemLoa1)).setText(jSONObject8.getString("DiemNhan"));
                        ((TextView) inflate.findViewById(R.id.tv_AnLoa1)).setText(jSONObject8.getString("AnNhan"));
                        ((TextView) inflate.findViewById(R.id.tv_KQLoa1)).setText(jSONObject8.getString("KQNhan"));
                    }
                    if (jSONObject.has("xi")) {
                        ( inflate.findViewById(R.id.ln_xi2)).setVisibility(View.VISIBLE);
                        JSONObject jSONObject9 = new JSONObject(jSONObject.getString("xi"));
                        ((TextView) inflate.findViewById(R.id.tv_diemXi2)).setText(jSONObject9.getString("DiemNhan"));
                        ((TextView) inflate.findViewById(R.id.tv_AnXi2)).setText(jSONObject9.getString("AnNhan"));
                        ((TextView) inflate.findViewById(R.id.tv_KQXi2)).setText(jSONObject9.getString("KQNhan"));
                    }
                    if (jSONObject.has("xia")) {
                        ( inflate.findViewById(R.id.ln_xia2)).setVisibility(View.VISIBLE);
                        JSONObject jSONObject10 = new JSONObject(jSONObject.getString("xia"));
                        ((TextView) inflate.findViewById(R.id.tv_diemXia2)).setText(jSONObject10.getString("DiemNhan"));
                        ((TextView) inflate.findViewById(R.id.tv_AnXia2)).setText(jSONObject10.getString("AnNhan"));
                        ((TextView) inflate.findViewById(R.id.tv_KQXia2)).setText(jSONObject10.getString("KQNhan"));
                    }
                    str2 = str;
                    if (jSONObject.has(str2)) {
                        ( inflate.findViewById(R.id.lnXn)).setVisibility(View.VISIBLE);
                        JSONObject jSONObject11 = new JSONObject(jSONObject.getString(str2));
                        ((TextView) inflate.findViewById(R.id.tv_diemXn1)).setText(jSONObject11.getString("DiemNhan"));
                        ((TextView) inflate.findViewById(R.id.tv_AnXn1)).setText(jSONObject11.getString("AnNhan"));
                        ((TextView) inflate.findViewById(R.id.tv_KQXn1)).setText(jSONObject11.getString("KQNhan"));
                    }
                    if (jSONObject.has("bc")) {
                        JSONObject jSONObject12 = new JSONObject(jSONObject.getString("bc"));
                        ((TextView) inflate.findViewById(R.id.tv_diemBc1)).setText(jSONObject12.getString("DiemNhan"));
                        ((TextView) inflate.findViewById(R.id.tv_AnBc1)).setText(jSONObject12.getString("AnNhan"));
                        ((TextView) inflate.findViewById(R.id.tv_KQBc1)).setText(jSONObject12.getString("KQNhan"));
                    }
                    if (jSONObject.has("bca")) {
                        ( inflate.findViewById(R.id.ln_bca)).setVisibility(View.VISIBLE);
                        JSONObject jSONObject13 = new JSONObject(jSONObject.getString("bca"));
                        ((TextView) inflate.findViewById(R.id.tv_diemBca)).setText(jSONObject13.getString("DiemNhan"));
                        ((TextView) inflate.findViewById(R.id.tv_AnBca)).setText(jSONObject13.getString("AnNhan"));
                        ((TextView) inflate.findViewById(R.id.tv_KQBca)).setText(jSONObject13.getString("KQNhan"));
                    }
                    ((TextView) inflate.findViewById(R.id.tv_TongTien1)).setText(jSONObject.getString("Tien_Nhan"));
                } catch (JSONException unused) {
                    unused.printStackTrace();
                }
                return inflate;
            }
            return inflate;
        }
    }

    public void TinhlaitienKhachnay(String mDate, String tenkh) {
        long currentTimeMillis = System.currentTimeMillis();
        this.db.QueryData("Delete From tbl_soctS WHERE  ngay_nhan = '" + mDate + "' AND ten_kh = '" + this.mTenKH.get(this.position) + "'");
        Cursor GetData = this.db.GetData("Select * FROM tbl_tinnhanS WHERE  ngay_nhan = '" + mDate + "' AND phat_hien_loi = 'ok' AND ten_kh = '" + this.mTenKH.get(this.position) + "'");
        while (GetData.moveToNext()) {
            String replaceAll = GetData.getString(10).replaceAll("\\*", "");
            this.db.QueryData("Update tbl_tinnhanS set nd_phantich = '" + replaceAll + "' WHERE id = " + GetData.getInt(0));
            this.db.NhapSoChiTiet(GetData.getInt(0));
        }
        if (!GetData.isClosed()) {
            GetData.close();
        }
        double currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
        Double.isNaN(currentTimeMillis2);
        double d = currentTimeMillis2 / 1000.0d;
        Log.i("Frag_No_old", "NhapSoChiTiet: " + d);
        long currentTimeMillis3 = System.currentTimeMillis();
        Tinhtien();
        double currentTimeMillis4 = (double) (System.currentTimeMillis() - currentTimeMillis3);
        Double.isNaN(currentTimeMillis4);
        Log.i("Frag_No_old", "Tinhtien: " + (currentTimeMillis4 / 1000.0d));
        System.currentTimeMillis();
        lv_No2_KH();
        System.currentTimeMillis();
        Log.i("Frag_No_old", "lv_No2_KH: " + d);
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0040, code lost:
    
        if (r1.isClosed() == false) goto L56;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0042, code lost:
    
        r1.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x005d, code lost:
    
        if (r1.isClosed() == false) goto L56;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void Tinhtien() {
        String Get_date = MainActivity.Get_date();
        Cursor GetData = this.db.GetData("Select * From Ketqua WHERE ngay = '" + Get_date + "'");
        GetData.moveToFirst();
        int i = 2;
        while (i < 29) {
            try {
                if (GetData.isNull(i) || !Congthuc.isNumeric(GetData.getString(i))) {
                    break;
                } else {
                    i++;
                }
            } catch (Exception unused) {
                if (GetData != null) {
                }
            } catch (Throwable th) {
                if (GetData != null && !GetData.isClosed()) {
                    GetData.close();
                }
                throw th;
            }
        }
        if (GetData != null) {
        }
        if (i >= 29) {
            try {
                this.db.Tinhtien(Get_date);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /*  JADX ERROR: Types fix failed
        jadx.core.utils.exceptions.JadxOverflowException: Type inference error: updates count limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:96)
        */
    public void Dialog(String r42) {
        /*
            Method dump skipped, instructions count: 1230
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: tamhoang.ldpro4.Fragment.Frag_No_old.Dialog(java.lang.String):void");
    }

    public void istart() {
        this.lnDea =  this.v.findViewById(R.id.ln_dea);
        this.lnDet =  this.v.findViewById(R.id.ln_det);
        this.lnDec =  this.v.findViewById(R.id.ln_dec);
        this.lnDed =  this.v.findViewById(R.id.ln_ded);
        this.lnXn =  this.v.findViewById(R.id.lnXn);
        this.ln_xi2 =  this.v.findViewById(R.id.ln_xi2);
        this.ln_xia2 =  this.v.findViewById(R.id.ln_xia2);
        this.ln_loa =  this.v.findViewById(R.id.ln_loa);
        this.ln_bca =  this.v.findViewById(R.id.ln_bca);
        this.tvDiemDea =  this.v.findViewById(R.id.tv_diemDea);
        this.tvDiemDeb =  this.v.findViewById(R.id.tv_diemDeb);
        this.tvDiemDet =  this.v.findViewById(R.id.tv_diemDet);
        this.tvDiemDec =  this.v.findViewById(R.id.tv_diemDec);
        this.tvDiemDed =  this.v.findViewById(R.id.tv_diemDed);
        this.tvDiemLo1 =  this.v.findViewById(R.id.tv_diemLo1);
        this.tvDiemLoa =  this.v.findViewById(R.id.tv_diemLoa1);
        this.tvDiemXi2 =  this.v.findViewById(R.id.tv_diemXi2);
        this.tvDiemXia2 =  this.v.findViewById(R.id.tv_diemXia2);
        this.tvDiemXn1 =  this.v.findViewById(R.id.tv_diemXn1);
        this.tvDiemBc1 =  this.v.findViewById(R.id.tv_diemBc1);
        this.tvDiemBca =  this.v.findViewById(R.id.tv_diemBca);
        this.tvAnDea =  this.v.findViewById(R.id.tv_AnDea);
        this.tvAnDeb =  this.v.findViewById(R.id.tv_AnDeb);
        this.tvAnDet =  this.v.findViewById(R.id.tv_AnDet);
        this.tvAnDec =  this.v.findViewById(R.id.tv_AnDec);
        this.tvAnDed =  this.v.findViewById(R.id.tv_AnDed);
        this.tvAnLo1 =  this.v.findViewById(R.id.tv_AnLo1);
        this.tvAnLoa =  this.v.findViewById(R.id.tv_AnLoa1);
        this.tvAnXi2 =  this.v.findViewById(R.id.tv_AnXi2);
        this.tvAnXia2 =  this.v.findViewById(R.id.tv_AnXia2);
        this.tvAnXn1 =  this.v.findViewById(R.id.tv_AnXn1);
        this.tvAnBC1 =  this.v.findViewById(R.id.tv_AnBc1);
        this.tvAnBCa =  this.v.findViewById(R.id.tv_AnBca);
        this.tvKQDea =  this.v.findViewById(R.id.tv_KQDea);
        this.tvKQDeb =  this.v.findViewById(R.id.tv_KQDeb);
        this.tvKQDet =  this.v.findViewById(R.id.tv_KQDet);
        this.tvKQDec =  this.v.findViewById(R.id.tv_KQDec);
        this.tvKQDed =  this.v.findViewById(R.id.tv_KQDed);
        this.tvKQLo1 =  this.v.findViewById(R.id.tv_KQLo1);
        this.tvKQLoa =  this.v.findViewById(R.id.tv_KQLoa1);
        this.tvKQXi2 =  this.v.findViewById(R.id.tv_KQXi2);
        this.tvKQXia2 =  this.v.findViewById(R.id.tv_KQXia2);
        this.tvKQXn1 =  this.v.findViewById(R.id.tv_KQXn1);
        this.tvKQBc1 =  this.v.findViewById(R.id.tv_KQBc1);
        this.tvKQBca =  this.v.findViewById(R.id.tv_KQBca);
        this.tvTongTien1 =  this.v.findViewById(R.id.tv_TongTien1);
    }

    public void lv_No2_KH() {
        String str;
        String str2;
        String str3;
        String str4;
        String str5 = "";
        String str6 = "xi";
        String str7 = "xn";
        String str8 = "loa";
        String str9 = "lo";
        String str10 = "ded";
        String Get_date = MainActivity.Get_date();
        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        Cursor GetData = this.db.GetData("Select the_loai\n, sum((type_kh = 1)*(100-diem_khachgiu)*diem/100) as mDiem\n, CASE WHEN the_loai = 'xi' OR the_loai = 'xia' \n THEN sum((type_kh = 1)*(100-diem_khachgiu)*diem/100*so_nhay*lan_an/1000) \n ELSE sum((type_kh = 1)*(100-diem_khachgiu)*diem/100*so_nhay)  END nAn\n, sum((type_kh = 1)*ket_qua*(100-diem_khachgiu)/100) as mKetqua\n, sum((type_kh = 2)*(100-diem_khachgiu)*diem/100) as mDiem\n, CASE WHEN the_loai = 'xi' OR the_loai = 'xia' \n THEN sum((type_kh = 2)*(100-diem_khachgiu)*diem/100*so_nhay*lan_an/1000) \n ELSE sum((type_kh = 2)*(100-diem_khachgiu)*diem/100*so_nhay)  END nAn \n, sum((type_kh = 2)*ket_qua*(100-diem_khachgiu)/100) as mKetqua\n  From tbl_soctS Where ngay_nhan = '" + Get_date + "'\n   AND the_loai <> 'tt' GROUP by the_loai");
        try {
            JSONObject jSONObject = new JSONObject();
            if (GetData != null) {
                double d = 0.0d;
                while (true) {
                    str = str6;
                    str2 = str7;
                    str3 = str8;
                    if (!GetData.moveToNext()) {
                        break;
                    }
                    try {
                        JSONObject jSONObject2 = new JSONObject();
                        str4 = str9;
                        try {
                            str5 = str10;
                            try {
                                jSONObject2.put("DiemNhan", decimalFormat.format(GetData.getDouble(1) - GetData.getDouble(4)));
                                jSONObject2.put("AnNhan", decimalFormat.format(GetData.getDouble(2) - GetData.getDouble(5)));
                                jSONObject2.put("KQNhan", decimalFormat.format(-(GetData.getDouble(3) + GetData.getDouble(6))));
                                d = (d - GetData.getDouble(3)) - GetData.getDouble(6);
                                jSONObject.put(GetData.getString(0), jSONObject2.toString());
                            } catch (SQLException unused) {
                            } catch (JSONException e) {
                                e = e;
                                e.printStackTrace();
                                GetData.close();
                                xemListview();
                            }
                        } catch (SQLException unused2) {
                            str5 = str10;
                            str6 = str;
                            str7 = str2;
                            str8 = str3;
                            str9 = str4;
                            str10 = str5;
                        }
                    } catch (SQLException unused3) {
                        str4 = str9;
                    }
                    str6 = str;
                    str7 = str2;
                    str8 = str3;
                    str9 = str4;
                    str10 = str5;
                }
                String str11 = str9;
                String str12 = str10;
                if (jSONObject.has("dea")) {
                    this.lnDea.setVisibility(View.VISIBLE);
                    JSONObject jSONObject3 = new JSONObject(jSONObject.getString("dea"));
                    this.tvDiemDea.setText(jSONObject3.getString("DiemNhan"));
                    this.tvAnDea.setText(jSONObject3.getString("AnNhan"));
                    this.tvKQDea.setText(jSONObject3.getString("KQNhan"));
                }
                if (jSONObject.has("deb")) {
                    JSONObject jSONObject4 = new JSONObject(jSONObject.getString("deb"));
                    this.tvDiemDeb.setText(jSONObject4.getString("DiemNhan"));
                    this.tvAnDeb.setText(jSONObject4.getString("AnNhan"));
                    this.tvKQDeb.setText(jSONObject4.getString("KQNhan"));
                }
                if (jSONObject.has("det")) {
                    this.lnDet.setVisibility(View.VISIBLE);
                    JSONObject jSONObject5 = new JSONObject(jSONObject.getString("det"));
                    this.tvDiemDet.setText(jSONObject5.getString("DiemNhan"));
                    this.tvAnDet.setText(jSONObject5.getString("AnNhan"));
                    this.tvKQDet.setText(jSONObject5.getString("KQNhan"));
                }
                if (jSONObject.has("dec")) {
                    this.lnDec.setVisibility(View.VISIBLE);
                    JSONObject jSONObject6 = new JSONObject(jSONObject.getString("dec"));
                    this.tvDiemDec.setText(jSONObject6.getString("DiemNhan"));
                    this.tvAnDec.setText(jSONObject6.getString("AnNhan"));
                    this.tvKQDec.setText(jSONObject6.getString("KQNhan"));
                }
                if (jSONObject.has(str12)) {
                    this.lnDed.setVisibility(View.VISIBLE);
                    JSONObject jSONObject7 = new JSONObject(jSONObject.getString(str12));
                    this.tvDiemDed.setText(jSONObject7.getString("DiemNhan"));
                    this.tvAnDed.setText(jSONObject7.getString("AnNhan"));
                    this.tvKQDed.setText(jSONObject7.getString("KQNhan"));
                }
                if (jSONObject.has(str11)) {
                    JSONObject jSONObject8 = new JSONObject(jSONObject.getString(str11));
                    this.tvDiemLo1.setText(jSONObject8.getString("DiemNhan"));
                    this.tvAnLo1.setText(jSONObject8.getString("AnNhan"));
                    this.tvKQLo1.setText(jSONObject8.getString("KQNhan"));
                }
                if (jSONObject.has(str3)) {
                    this.ln_loa.setVisibility(View.VISIBLE);
                    JSONObject jSONObject9 = new JSONObject(jSONObject.getString(str3));
                    this.tvDiemLoa.setText(jSONObject9.getString("DiemNhan"));
                    this.tvAnLoa.setText(jSONObject9.getString("AnNhan"));
                    this.tvKQLoa.setText(jSONObject9.getString("KQNhan"));
                }
                if (jSONObject.has(str2)) {
                    this.lnXn.setVisibility(View.VISIBLE);
                    JSONObject jSONObject10 = new JSONObject(jSONObject.getString(str2));
                    this.tvDiemXn1.setText(jSONObject10.getString("DiemNhan"));
                    this.tvAnXn1.setText(jSONObject10.getString("AnNhan"));
                    this.tvKQXn1.setText(jSONObject10.getString("KQNhan"));
                }
                if (jSONObject.has(str)) {
                    this.ln_xi2.setVisibility(View.VISIBLE);
                    JSONObject jSONObject11 = new JSONObject(jSONObject.getString(str));
                    this.tvDiemXi2.setText(jSONObject11.getString("DiemNhan"));
                    this.tvAnXi2.setText(jSONObject11.getString("AnNhan"));
                    this.tvKQXi2.setText(jSONObject11.getString("KQNhan"));
                }
                if (jSONObject.has("xia")) {
                    this.ln_xia2.setVisibility(View.VISIBLE);
                    JSONObject jSONObject12 = new JSONObject(jSONObject.getString("xia"));
                    this.tvDiemXia2.setText(jSONObject12.getString("DiemNhan"));
                    this.tvAnXia2.setText(jSONObject12.getString("AnNhan"));
                    this.tvKQXia2.setText(jSONObject12.getString("KQNhan"));
                }
                if (jSONObject.has("bc")) {
                    JSONObject jSONObject13 = new JSONObject(jSONObject.getString("bc"));
                    this.tvDiemBc1.setText(jSONObject13.getString("DiemNhan"));
                    this.tvAnBC1.setText(jSONObject13.getString("AnNhan"));
                    this.tvKQBc1.setText(jSONObject13.getString("KQNhan"));
                }
                if (jSONObject.has("bca")) {
                    this.ln_bca.setVisibility(View.VISIBLE);
                    JSONObject jSONObject14 = new JSONObject(jSONObject.getString("bca"));
                    this.tvDiemBca.setText(jSONObject14.getString("DiemNhan"));
                    this.tvAnBCa.setText(jSONObject14.getString("AnNhan"));
                    this.tvKQBca.setText(jSONObject14.getString("KQNhan"));
                }
                this.tvTongTien1.setText(decimalFormat.format(d));
                if (GetData != null && !GetData.isClosed()) {
                    GetData.close();
                }
            }
        } catch (SQLException unused4) {
        } catch (JSONException e4) {
            e4.printStackTrace();
        }
        if (GetData != null && !GetData.isClosed()) {
            GetData.close();
        }
        xemListview();
    }

    @Override // android.support.v4.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.v = inflater.inflate(R.layout.frag_norp2, container, false);
        this.db = new Database(getActivity());
        this.mInflate = inflater;
        istart();
        this.progressBar = (ProgressBar) this.v.findViewById(R.id.progressBar);
        this.lv_no_tinnhan = (ListView) this.v.findViewById(R.id.no_rp2_lv);
        this.btn_nt =  this.v.findViewById(R.id.btn_nt);
        this.lv_no_tinnhan.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() { // from class: tamhoang.ldpro4.Fragment.Frag_No_old.2
            @Override // android.widget.AdapterView.OnItemLongClickListener
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                position = i;
                Frag_No_old frag_No_old = Frag_No_old.this;
                frag_No_old.Dialog((String) frag_No_old.mTenKH.get(i));
                return false;
            }
        });
        if (!Congthuc.CheckTime("18:30")) {
            Handler handler = new Handler();
            this.handler = handler;
            handler.postDelayed(this.runnable, 1000L);
        }
        lv_No2_KH();
        this.btn_nt.setOnClickListener(view -> {
            if (Frag_No_old.this.jsonKhachHang.size() >= 1) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Frag_No_old.this.getActivity());
                builder.setMessage("Bạn có muốn nhắn tin chốt tiền tất cả không?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        Frag_No_old.this.progressBar.setVisibility(View.VISIBLE);
                        Frag_No_old.this.getActivity().getWindow().setFlags(16, 16);
                        for (int i = 0; i < Frag_No_old.this.jsonKhachHang.size(); i++) {
                            Frag_No_old.this.currentIndex = i;
                            Cursor GetData = Frag_No_old.this.db.GetData("Select * From tbl_kh_new Where ten_kh = '" + ((String) Frag_No_old.this.mTenKH.get(Frag_No_old.this.currentIndex)) + "'");
                            if (GetData != null && GetData.getCount() > 0) {
                                GetData.moveToFirst();
                                if (GetData.getString(2).indexOf("sms") > -1) {
                                    try {
                                        if (MainActivity.jSon_Setting.getInt("tachxien_tinchot") == 0) {
                                            Frag_No_old.this.db.SendSMS((String) Frag_No_old.this.mSDT.get(Frag_No_old.this.currentIndex), Frag_No_old.this.db.Tin_Chottien((String) Frag_No_old.this.mTenKH.get(Frag_No_old.this.currentIndex)));
                                        } else {
                                            Frag_No_old.this.db.SendSMS((String) Frag_No_old.this.mSDT.get(Frag_No_old.this.currentIndex), Frag_No_old.this.db.Tin_Chottien_xien((String) Frag_No_old.this.mTenKH.get(Frag_No_old.this.currentIndex)));
                                        }
                                        Frag_No_old.this.isSuccess = true;
                                    } catch (JSONException e) {
                                        Frag_No_old.this.isSuccess = false;
                                        e.printStackTrace();
                                    }
                                } else if (GetData.getString(2).indexOf("TL") > -1) {
                                    try {
                                        if (MainActivity.jSon_Setting.getInt("tachxien_tinchot") == 0) {
                                            MainActivity.sendMessage(Long.parseLong((String) Frag_No_old.this.mSDT.get(Frag_No_old.this.currentIndex)), Frag_No_old.this.db.Tin_Chottien((String) Frag_No_old.this.mTenKH.get(Frag_No_old.this.currentIndex)));
                                        } else {
                                            MainActivity.sendMessage(Long.parseLong((String) Frag_No_old.this.mSDT.get(Frag_No_old.this.currentIndex)), Frag_No_old.this.db.Tin_Chottien_xien((String) Frag_No_old.this.mTenKH.get(Frag_No_old.this.currentIndex)));
                                        }
                                        Frag_No_old.this.isSuccess = true;
                                        try {
                                            Thread.sleep(1000L);
                                        } catch (InterruptedException e2) {
                                            e2.printStackTrace();
                                        }
                                    } catch (JSONException e3) {
                                        Frag_No_old.this.isSuccess = false;
                                        e3.printStackTrace();
                                    }
                                } else if (MainActivity.arr_TenKH.indexOf(GetData.getString(1)) > -1) {
                                    NotificationNewReader notificationNewReader = new NotificationNewReader();
                                    try {
                                        if (MainActivity.jSon_Setting.getInt("tachxien_tinchot") == 0) {
                                            notificationNewReader.NotificationWearReader(GetData.getString(1), Frag_No_old.this.db.Tin_Chottien((String) Frag_No_old.this.mTenKH.get(Frag_No_old.this.currentIndex)));
                                        } else {
                                            notificationNewReader.NotificationWearReader(GetData.getString(1), Frag_No_old.this.db.Tin_Chottien_xien((String) Frag_No_old.this.mTenKH.get(Frag_No_old.this.currentIndex)));
                                        }
                                    } catch (JSONException e4) {
                                        Frag_No_old.this.isSuccess = false;
                                        e4.printStackTrace();
                                    }
                                    Frag_No_old.this.isSuccess = true;
                                } else {
                                    Frag_No_old.this.isSuccess = false;
                                    Toast.makeText(Frag_No_old.this.getActivity(), "Không có người này trong Chatbox", Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (Frag_No_old.this.isSuccess) {
                                    Toast.makeText(Frag_No_old.this.getActivity(), "Đã nhắn chốt tiền!", Toast.LENGTH_LONG).show();
                                }
                                Frag_No_old.this.progressBar.setVisibility(View.GONE);
                                Frag_No_old.this.getActivity().getWindow().clearFlags(16);
                            }
                        }, 2000L);
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                builder.create().show();
            }
        });
        return this.v;
    }

    @Override // android.support.v4.app.Fragment
    public void onDestroy() {
        try {
            this.handler.removeCallbacks(this.runnable);
            this.db.close();
        } catch (Exception unused) {
        }
        super.onDestroy();
    }

    public void xemListview() {
        String Get_date = MainActivity.Get_date();
        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        this.jsonKhachHang = new ArrayList();
        this.mTenKH.clear();
        this.mSDT.clear();
        this.mTypeKH.clear();
        Cursor GetData = this.db.GetData("Select ten_kh, so_dienthoai, type_kh, the_loai\n, sum((100-diem_khachgiu)*diem/100) as mDiem\n, CASE WHEN the_loai = 'xi' OR the_loai = 'xia' \n THEN sum((100-diem_khachgiu)*diem/100*so_nhay*lan_an/1000) \n ELSE sum((100-diem_khachgiu)*diem/100*so_nhay)  END nAn\n, sum(ket_qua*(100-diem_khachgiu)/100) as mKetqua\n  From tbl_soctS Where ngay_nhan = '" + Get_date + "'\n  AND the_loai <> 'tt' GROUP by type_kh, ten_kh, the_loai ORDER by type_kh DESC, ten_kh");
        try {
            JSONObject jSONObject = new JSONObject();
            if (GetData != null) {
                String str = "";
                double d = 0.0d;
                while (GetData.moveToNext()) {
                    if (str.length() == 0) {
                        this.mTenKH.add(GetData.getString(0));
                        this.mSDT.add(GetData.getString(1));
                        this.mTypeKH.add(GetData.getString(2));
                        str = GetData.getString(0);
                    } else if (str.indexOf(GetData.getString(0)) != 0) {
                        jSONObject.put("Tien_Nhan", decimalFormat.format(d));
                        this.jsonKhachHang.add(jSONObject);
                        this.mTenKH.add(GetData.getString(0));
                        this.mSDT.add(GetData.getString(1));
                        this.mTypeKH.add(GetData.getString(2));
                        d = 0.0d;
                        str = GetData.getString(0);
                        jSONObject = new JSONObject();
                    }
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.put("DiemNhan", decimalFormat.format(GetData.getDouble(4)));
                    jSONObject2.put("AnNhan", decimalFormat.format(GetData.getDouble(5)));
                    jSONObject2.put("KQNhan", decimalFormat.format(GetData.getDouble(6)));
                    d += GetData.getDouble(6);
                    jSONObject.put(GetData.getString(3), jSONObject2.toString());
                }
                jSONObject.put("Tien_Nhan", decimalFormat.format(d));
                if (GetData.getCount() > 0) {
                    this.jsonKhachHang.add(jSONObject);
                }
                if (GetData != null && !GetData.isClosed()) {
                    GetData.close();
                }
            }
        } catch (SQLException unused) {
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (getActivity() != null) {
            this.lv_no_tinnhan.setAdapter((ListAdapter) new NoRP_TN_Adapter(getActivity(), R.layout.frag_norp2_lv, this.jsonKhachHang));
        }
    }
}
