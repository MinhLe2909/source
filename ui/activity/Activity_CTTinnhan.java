package tamhoang.ldpro4.ui.activity;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

import tamhoang.ldpro4.base.BaseToolBarActivity;
import tamhoang.ldpro4.R;
import tamhoang.ldpro4.utils.Util;
import tamhoang.ldpro4.data.Database;


public class Activity_CTTinnhan extends BaseToolBarActivity {
    Database db;
    String id = "";
    String type_kh = "";

    @Override // tamhoang.ldpro4.Congthuc.BaseToolBarActivity
    public int getLayoutId() {
        return R.layout.activity_cttinnhan;
    }

    /* JADX WARN: Can't wrap try/catch for region: R(12:126|127|128|129|(3:130|131|132)|133|134|135|136|137|138|139) */
    /* JADX WARN: Code restructure failed: missing block: B:201:0x086a, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:203:0x0871, code lost:
    
        tamhoang.ldpro4.Util.writeLog(r0);
        r28.close();
        r26.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:205:0x086c, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:206:0x086d, code lost:
    
        r12 = r6;
        r5 = r15;
        r15 = "diem";
     */
    /* JADX WARN: Removed duplicated region for block: B:126:0x07e3  */
    /* JADX WARN: Removed duplicated region for block: B:142:0x0887  */
    /* JADX WARN: Removed duplicated region for block: B:154:0x091c A[Catch: JSONException -> 0x09bb, TryCatch #7 {JSONException -> 0x09bb, blocks: (B:124:0x07dd, B:128:0x07e6, B:131:0x07fc, B:135:0x0823, B:138:0x082c, B:140:0x087e, B:152:0x0914, B:154:0x091c, B:155:0x0990, B:191:0x0907, B:203:0x0871, B:210:0x0816), top: B:123:0x07dd, outer: #14 }] */
    /* JADX WARN: Removed duplicated region for block: B:161:0x0a22 A[Catch: JSONException -> 0x0239, TryCatch #6 {JSONException -> 0x0239, blocks: (B:40:0x01c6, B:158:0x09cb, B:159:0x0a1a, B:161:0x0a22, B:165:0x0a35, B:167:0x0a44, B:169:0x0a51, B:173:0x0a62, B:182:0x0a65), top: B:39:0x01c6 }] */
    /* JADX WARN: Removed duplicated region for block: B:199:0x0911  */
    /* JADX WARN: Removed duplicated region for block: B:215:0x087b  */
    @Override
    // tamhoang.ldpro4.Congthuc.BaseToolBarActivity, android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity, android.support.v4.app.SupportActivity, android.app.Activity
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void onCreate(Bundle savedInstanceState) {
        String str;
        String str2;
        String str3;
        Cursor cursor;
        JSONException jSONException;
        double d = 0;
        double d2 = 0;
        String str4 = "";
        Cursor cursor2;
        String str5;
        String str6 = "";
        TextView textView;
        String string;
        int i;
        JSONObject jSONObject = new JSONObject();
        String str7 = "";
        String str8 = "";
        JSONObject jSONObject2 = new JSONObject();
        String str9;
        double d3 = 0;
        double d4 = 0;
        JSONException jSONException2;
        Exception exc;
        JSONObject jSONObject3;
        JSONObject jSONObject4;
        Exception exc2;
        JSONObject jSONObject5 = new JSONObject();
        JSONObject jSONObject6 = new JSONObject();
        String str10;
        String str11;
        String str12;
        String str13 = "lo";
        String str14 = "bca";
        String str15 = "dec";
        String str16 = "ded";
        String str17 = "loa";
        String str18 = "deb";
        String str19 = "xn";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cttinnhan);
        this.id = getIntent().getStringExtra("m_ID");
        this.db = new Database(this);
        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        Cursor GetData = this.db.GetData("Select * From tbl_tinnhanS Where ID = " + this.id);
        GetData.moveToFirst();
        Cursor GetData2 = this.db.GetData("SElect CASE \nWHEN the_loai = 'xi' And length(so_chon) = 5 THEN 'xi2' \nWHEN the_loai = 'xi' And length(so_chon) = 8 THEN 'xi3' \nWHEN the_loai = 'xi' And length(so_chon) = 11 THEN 'xi4' \nWHEN the_loai = 'xia' And length(so_chon) = 5 THEN 'xia2' \nWHEN the_loai = 'xia' And length(so_chon) = 8 THEN 'xia3' \nWHEN the_loai = 'xia' And length(so_chon) = 11 THEN 'xia4' \nELSE the_loai END theloai, sum(diem), sum(diem*so_nhay) as An\n, sum (tong_tien)/1000 as kq \n, sum(Ket_qua)/1000 as tienCuoi\n From tbl_soctS \n Where ten_kh = '" + GetData.getString(4) + "' and ngay_nhan = '" + GetData.getString(1) + "' and so_tin_nhan = " + GetData.getString(7) + " Group by theloai");
        JSONObject jSONObject7 = new JSONObject();
        while (true) {
            str = str13;
            str2 = str14;
            str3 = str17;
            if (!GetData2.moveToNext()) {
                break;
            }
            String str20 = str18;
            try {
                JSONObject jSONObject8 = new JSONObject();
                str10 = str15;
                str11 = str16;
                str12 = str19;
                try {
                    jSONObject8.put("diem", GetData2.getDouble(1));
                    jSONObject8.put("diem_an", GetData2.getDouble(2));
                    jSONObject8.put("tong_tien", GetData2.getDouble(3));
                    jSONObject8.put("ket_qua", GetData2.getDouble(4));
                    try {
                        jSONObject7.put(GetData2.getString(0), jSONObject8.toString());
                    } catch (Exception e) {
                        Util.writeLog(e);
                        e.printStackTrace();
                        GetData.close();
                        GetData2.close();
                    }
                } catch (Exception e2) {
                    Util.writeLog(e2);
                    GetData.close();
                    GetData2.close();
                    str18 = str20;
                    str13 = str;
                    str14 = str2;
                    str17 = str3;
                    str19 = str12;
                    str15 = str10;
                    str16 = str11;
                }
            } catch (Exception e3) {
                str10 = str15;
                str11 = str16;
                str12 = str19;
            }
            str18 = str20;
            str13 = str;
            str14 = str2;
            str17 = str3;
            str19 = str12;
            str15 = str10;
            str16 = str11;
        }
        String str21 = str15;
        String str22 = str16;
        String str23 = str18;
        String str24 = str19;
        if (jSONObject7.has("dea")) {
            try {
                ( findViewById(R.id.liner_deA)).setVisibility(View.VISIBLE);
                JSONObject jSONObject9 = new JSONObject(jSONObject7.getString("dea"));
                ((TextView) findViewById(R.id.tv_diemDeA)).setText(decimalFormat.format(jSONObject9.getDouble("diem")));
                ((TextView) findViewById(R.id.tv_AnDeA)).setText(decimalFormat.format(jSONObject9.getDouble("diem_an")));
                ((TextView) findViewById(R.id.tv_no_DeA)).setText(decimalFormat.format(jSONObject9.getDouble("tong_tien")));
                ((TextView) findViewById(R.id.tv_no_ThangThuaDeA)).setText(decimalFormat.format(jSONObject9.getDouble("ket_qua")));
                double d5 = jSONObject9.getDouble("tong_tien") + 0.0d;
                str4 = str21;
                d2 = jSONObject9.getDouble("ket_qua") + 0.0d;
                d = d5;
            } catch (JSONException e4) {
                jSONException = e4;
                cursor = GetData2;
                jSONException.printStackTrace();
                GetData.close();
                cursor.close();
                GetData.close();
                cursor.close();
            }
        } else {
            d = 0.0d;
            d2 = 0.0d;
            str4 = str21;
        }
        if (jSONObject7.has(str4)) {
            cursor = GetData2;
            cursor2 = GetData;
            try {
                ( findViewById(R.id.liner_deC)).setVisibility(View.VISIBLE);
                JSONObject jSONObject10 = new JSONObject(jSONObject7.getString(str4));
                double d6 = d2;
                ((TextView) findViewById(R.id.tv_diemDeC)).setText(decimalFormat.format(jSONObject10.getDouble("diem")));
                ((TextView) findViewById(R.id.tv_AnDeC)).setText(decimalFormat.format(jSONObject10.getDouble("diem_an")));
                ((TextView) findViewById(R.id.tv_no_DeC)).setText(decimalFormat.format(jSONObject10.getDouble("tong_tien")));
                ((TextView) findViewById(R.id.tv_no_ThangThuaDeC)).setText(decimalFormat.format(jSONObject10.getDouble("ket_qua")));
                d += jSONObject10.getDouble("tong_tien");
                d2 = d6 + jSONObject10.getDouble("ket_qua");
            } catch (JSONException e5) {
                jSONException = e5;
                GetData = cursor2;
                jSONException.printStackTrace();
                GetData.close();
                cursor.close();
                GetData.close();
                cursor.close();
            }
        } else {
            cursor2 = GetData;
            cursor = GetData2;
        }
        try {
            if (jSONObject7.has(str22)) {
                ( findViewById(R.id.liner_deD)).setVisibility(View.VISIBLE);
                JSONObject jSONObject11 = new JSONObject(jSONObject7.getString(str22));
                double d7 = d2;
                ((TextView) findViewById(R.id.tv_diemDeD)).setText(decimalFormat.format(jSONObject11.getDouble("diem")));
                ((TextView) findViewById(R.id.tv_AnDeD)).setText(decimalFormat.format(jSONObject11.getDouble("diem_an")));
                ((TextView) findViewById(R.id.tv_no_DeD)).setText(decimalFormat.format(jSONObject11.getDouble("tong_tien")));
                ((TextView) findViewById(R.id.tv_no_ThangThuaDeD)).setText(decimalFormat.format(jSONObject11.getDouble("ket_qua")));
                d += jSONObject11.getDouble("tong_tien");
                d2 = d7 + jSONObject11.getDouble("ket_qua");
            }
            if (jSONObject7.has("det")) {
                ( findViewById(R.id.liner_deT)).setVisibility(View.VISIBLE);
                JSONObject jSONObject12 = new JSONObject(jSONObject7.getString("det"));
                double d8 = d2;
                ((TextView) findViewById(R.id.tv_diemDeT)).setText(decimalFormat.format(jSONObject12.getDouble("diem")));
                ((TextView) findViewById(R.id.tv_AnDeT)).setText(decimalFormat.format(jSONObject12.getDouble("diem_an")));
                ((TextView) findViewById(R.id.tv_no_DeT)).setText(decimalFormat.format(jSONObject12.getDouble("tong_tien")));
                ((TextView) findViewById(R.id.tv_no_ThangThuaDeT)).setText(decimalFormat.format(jSONObject12.getDouble("ket_qua")));
                d += jSONObject12.getDouble("tong_tien");
                d2 = d8 + jSONObject12.getDouble("ket_qua");
            }
            if (jSONObject7.has(str24)) {
                ( findViewById(R.id.liner_XN)).setVisibility(View.VISIBLE);
                JSONObject jSONObject13 = new JSONObject(jSONObject7.getString(str24));
                double d9 = d2;
                ((TextView) findViewById(R.id.tv_diemxn)).setText(decimalFormat.format(jSONObject13.getDouble("diem")));
                ((TextView) findViewById(R.id.tv_anxn)).setText(decimalFormat.format(jSONObject13.getDouble("diem_an")));
                ((TextView) findViewById(R.id.tv_no_Xn)).setText(decimalFormat.format(jSONObject13.getDouble("tong_tien")));
                ((TextView) findViewById(R.id.tv_no_ThangThuaXn)).setText(decimalFormat.format(jSONObject13.getDouble("ket_qua")));
                d += jSONObject13.getDouble("tong_tien");
                d2 = d9 + jSONObject13.getDouble("ket_qua");
            }
            if (jSONObject7.has(str23)) {
                ( findViewById(R.id.liner_deB)).setVisibility(View.VISIBLE);
                JSONObject jSONObject14 = new JSONObject(jSONObject7.getString(str23));
                double d10 = d2;
                ((TextView) findViewById(R.id.tv_diemDe)).setText(decimalFormat.format(jSONObject14.getDouble("diem")));
                ((TextView) findViewById(R.id.tv_AnDe)).setText(decimalFormat.format(jSONObject14.getDouble("diem_an")));
                ((TextView) findViewById(R.id.tv_no_De)).setText(decimalFormat.format(jSONObject14.getDouble("tong_tien")));
                ((TextView) findViewById(R.id.tv_no_ThangThuaDe)).setText(decimalFormat.format(jSONObject14.getDouble("ket_qua")));
                d += jSONObject14.getDouble("tong_tien");
                d2 = d10 + jSONObject14.getDouble("ket_qua");
            }
            if (jSONObject7.has(str3)) {
                ( findViewById(R.id.liner_loa)).setVisibility(View.VISIBLE);
                JSONObject jSONObject15 = new JSONObject(jSONObject7.getString(str3));
                double d11 = d2;
                ((TextView) findViewById(R.id.tv_diemloa)).setText(decimalFormat.format(jSONObject15.getDouble("diem")));
                ((TextView) findViewById(R.id.tv_anloa)).setText(decimalFormat.format(jSONObject15.getDouble("diem_an")));
                ((TextView) findViewById(R.id.tv_no_Loa)).setText(decimalFormat.format(jSONObject15.getDouble("tong_tien")));
                ((TextView) findViewById(R.id.tv_no_ThangThuaLoa)).setText(decimalFormat.format(jSONObject15.getDouble("ket_qua")));
                d += jSONObject15.getDouble("tong_tien");
                d2 = d11 + jSONObject15.getDouble("ket_qua");
            }
            if (jSONObject7.has(str2)) {
                ( findViewById(R.id.liner_bca)).setVisibility(View.VISIBLE);
                JSONObject jSONObject16 = new JSONObject(jSONObject7.getString(str2));
                double d12 = d2;
                ((TextView) findViewById(R.id.tv_diembca)).setText(decimalFormat.format(jSONObject16.getDouble("diem")));
                ((TextView) findViewById(R.id.tv_anbca)).setText(decimalFormat.format(jSONObject16.getDouble("diem_an")));
                ((TextView) findViewById(R.id.tv_no_Bca)).setText(decimalFormat.format(jSONObject16.getDouble("tong_tien")));
                ((TextView) findViewById(R.id.tv_no_ThangThuaBca)).setText(decimalFormat.format(jSONObject16.getDouble("ket_qua")));
                d += jSONObject16.getDouble("tong_tien");
                d2 = d12 + jSONObject16.getDouble("ket_qua");
            }
            if (jSONObject7.has(str)) {
                ( findViewById(R.id.liner_lo)).setVisibility(View.VISIBLE);
                JSONObject jSONObject17 = new JSONObject(jSONObject7.getString(str));
                double d13 = d2;
                ((TextView) findViewById(R.id.tv_diemlo)).setText(decimalFormat.format(jSONObject17.getDouble("diem")));
                ((TextView) findViewById(R.id.tv_anlo)).setText(decimalFormat.format(jSONObject17.getDouble("diem_an")));
                ((TextView) findViewById(R.id.tv_no_Lo)).setText(decimalFormat.format(jSONObject17.getDouble("tong_tien")));
                ((TextView) findViewById(R.id.tv_no_ThangThuaLo)).setText(decimalFormat.format(jSONObject17.getDouble("ket_qua")));
                d += jSONObject17.getDouble("tong_tien");
                d2 = d13 + jSONObject17.getDouble("ket_qua");
            }
            if (jSONObject7.has("xi2")) {
                ( findViewById(R.id.lnxi2)).setVisibility(View.VISIBLE);
                JSONObject jSONObject18 = new JSONObject(jSONObject7.getString("xi2"));
                double d14 = d2;
                ((TextView) findViewById(R.id.tv_diemxi2)).setText(decimalFormat.format(jSONObject18.getDouble("diem")));
                ((TextView) findViewById(R.id.tv_anxi2)).setText(decimalFormat.format(jSONObject18.getDouble("diem_an")));
                ((TextView) findViewById(R.id.tv_no_Xi2)).setText(decimalFormat.format(jSONObject18.getDouble("tong_tien")));
                ((TextView) findViewById(R.id.tv_no_ThangThuaXi2)).setText(decimalFormat.format(jSONObject18.getDouble("ket_qua")));
                d += jSONObject18.getDouble("tong_tien");
                d2 = d14 + jSONObject18.getDouble("ket_qua");
            }
            if (jSONObject7.has("xi3")) {
                ( findViewById(R.id.lnxi3)).setVisibility(View.VISIBLE);
                JSONObject jSONObject19 = new JSONObject(jSONObject7.getString("xi3"));
                double d15 = d2;
                ((TextView) findViewById(R.id.tv_diemxi3)).setText(decimalFormat.format(jSONObject19.getDouble("diem")));
                ((TextView) findViewById(R.id.tv_anxi3)).setText(decimalFormat.format(jSONObject19.getDouble("diem_an")));
                ((TextView) findViewById(R.id.tv_no_Xi3)).setText(decimalFormat.format(jSONObject19.getDouble("tong_tien")));
                ((TextView) findViewById(R.id.tv_no_ThangThuaXi3)).setText(decimalFormat.format(jSONObject19.getDouble("ket_qua")));
                d += jSONObject19.getDouble("tong_tien");
                d2 = d15 + jSONObject19.getDouble("ket_qua");
            }
            if (jSONObject7.has("xi4")) {
                ( findViewById(R.id.lnxi4)).setVisibility(View.VISIBLE);
                JSONObject jSONObject20 = new JSONObject(jSONObject7.getString("xi4"));
                double d16 = d2;
                ((TextView) findViewById(R.id.tv_diemxi4)).setText(decimalFormat.format(jSONObject20.getDouble("diem")));
                ((TextView) findViewById(R.id.tv_anxi4)).setText(decimalFormat.format(jSONObject20.getDouble("diem_an")));
                ((TextView) findViewById(R.id.tv_no_Xi4)).setText(decimalFormat.format(jSONObject20.getDouble("tong_tien")));
                ((TextView) findViewById(R.id.tv_no_ThangThuaXi4)).setText(decimalFormat.format(jSONObject20.getDouble("ket_qua")));
                d += jSONObject20.getDouble("tong_tien");
                d2 = d16 + jSONObject20.getDouble("ket_qua");
            }
            if (jSONObject7.has("xia2")) {
                try {
                    ( findViewById(R.id.lnxia2)).setVisibility(View.VISIBLE);
                    jSONObject5 = new JSONObject(jSONObject7.getString("xia2"));
                    try {
                        ((TextView) findViewById(R.id.tv_diemxia2)).setText(decimalFormat.format(jSONObject5.getDouble("diem")));
                        try {
                            ((TextView) findViewById(R.id.tv_anxia2)).setText(decimalFormat.format(jSONObject5.getDouble("diem_an")));
                            jSONObject6 = jSONObject7;
                            str6 = "tong_tien";
                            str5 = "diem_an";
                        } catch (Exception e8) {
                            jSONObject6 = jSONObject7;
                            str6 = "tong_tien";
                            str5 = "diem_an";
                        }
                    } catch (Exception e9) {
                        str6 = "tong_tien";
                        str5 = "diem_an";
                        jSONObject7 = null;
                        Util.writeLog(e9);
                        cursor2.close();
                        cursor.close();
                        if (jSONObject7.has("xia3")) {
                        }
                        jSONObject2 = jSONObject;
                        if (!jSONObject2.has("xia4")) {
                        }
                        d4 = d3;
                        if (jSONObject2.has("bc")) {
                        }
                        ((TextView) findViewById(R.id.tv_no_Tong)).setText(decimalFormat.format(d));
                        ((TextView) findViewById(R.id.tv_no_ThangThua)).setText(decimalFormat.format(d4));
                        textView =  findViewById(R.id.tv_no_rp_nd);
                        GetData = cursor2;
                        textView.setText(GetData.getString(8));
                        ((TextView) findViewById(R.id.tv_no_KH)).setText(GetData.getString(4));
                        ((TextView) findViewById(R.id.tv_no_TinNhan)).setText(GetData.getString(7));
                        ((TextView) findViewById(R.id.tv_no_TG_nhan)).setText(GetData.getString(2));
                        TextView textView2 =  findViewById(R.id.tv_ndpt);
                        string = GetData.getString(10);
                        SpannableString spannableString = new SpannableString(string);
                        // TODO: 7/3/2024 Comment
//                        while (i < string.length() - 1) {
//                        }
                        textView2.setText(spannableString);
                        GetData.close();
                        cursor.close();
                    }
                } catch (Exception e10) {
                    str5 = "diem_an";
                    str6 = "tong_tien";
                }
                try {
                    ((TextView) findViewById(R.id.tv_no_Xia2)).setText(decimalFormat.format(jSONObject5.getDouble(str6)));
                    ((TextView) findViewById(R.id.tv_no_ThangThuaXia2)).setText(decimalFormat.format(jSONObject5.getDouble("ket_qua")));
                    d += jSONObject5.getDouble(str6);
                    d2 += jSONObject5.getDouble("ket_qua");
                } catch (Exception e11) {
                    try {
                        Util.writeLog(e11);
                        cursor2.close();
                        cursor.close();
                        jSONObject7 = jSONObject6;
                    } catch (Exception e12) {
                        exc2 = e12;
                        jSONObject7 = jSONObject6;
                        Util.writeLog(exc2);
                        cursor2.close();
                        cursor.close();
                        if (jSONObject7.has("xia3")) {
                        }
                        jSONObject2 = jSONObject;
                        if (!jSONObject2.has("xia4")) {
                        }
                        d4 = d3;
                        if (jSONObject2.has("bc")) {
                        }
                        ((TextView) findViewById(R.id.tv_no_Tong)).setText(decimalFormat.format(d));
                        ((TextView) findViewById(R.id.tv_no_ThangThua)).setText(decimalFormat.format(d4));
                        textView =  findViewById(R.id.tv_no_rp_nd);
                        GetData = cursor2;
                        textView.setText(GetData.getString(8));
                        ((TextView) findViewById(R.id.tv_no_KH)).setText(GetData.getString(4));
                        ((TextView) findViewById(R.id.tv_no_TinNhan)).setText(GetData.getString(7));
                        ((TextView) findViewById(R.id.tv_no_TG_nhan)).setText(GetData.getString(2));
                        TextView textView22 =  findViewById(R.id.tv_ndpt);
                        string = GetData.getString(10);
                        SpannableString spannableString2 = new SpannableString(string);
                        // TODO: 7/3/2024 Comment
//                        while (i < string.length() - 1) {
//                        }
                        textView22.setText(spannableString2);
                        GetData.close();
                        cursor.close();
                    }
                    if (jSONObject7.has("xia3")) {
                    }
                    jSONObject2 = jSONObject;
                    if (!jSONObject2.has("xia4")) {
                    }
                    d4 = d3;
                    if (jSONObject2.has("bc")) {
                    }
                    ((TextView) findViewById(R.id.tv_no_Tong)).setText(decimalFormat.format(d));
                    ((TextView) findViewById(R.id.tv_no_ThangThua)).setText(decimalFormat.format(d4));
                    textView =  findViewById(R.id.tv_no_rp_nd);
                    GetData = cursor2;
                    textView.setText(GetData.getString(8));
                    ((TextView) findViewById(R.id.tv_no_KH)).setText(GetData.getString(4));
                    ((TextView) findViewById(R.id.tv_no_TinNhan)).setText(GetData.getString(7));
                    ((TextView) findViewById(R.id.tv_no_TG_nhan)).setText(GetData.getString(2));
                    TextView textView222 =  findViewById(R.id.tv_ndpt);
                    string = GetData.getString(10);
                    SpannableString spannableString22 = new SpannableString(string);
                    // TODO: 7/3/2024 Comment
//                    while (i < string.length() - 1) {
//                    }
                    textView222.setText(spannableString22);
                    GetData.close();
                    cursor.close();
                }
                jSONObject7 = jSONObject6;
            } else {
                str5 = "diem_an";
                str6 = "tong_tien";
            }
            try {
                if (jSONObject7.has("xia3")) {
                    jSONObject = jSONObject7;
                    str7 = str5;
                    str8 = "diem";
                } else {
                    try {
                        ( findViewById(R.id.lnxia3)).setVisibility(View.VISIBLE);
                        jSONObject3 = new JSONObject(jSONObject7.getString("xia3"));
                    } catch (Exception e13) {
                        exc = e13;
                        jSONObject3 = null;
                    }
                    try {
                        JSONObject jSONObject21 = jSONObject7;
                        ((TextView) findViewById(R.id.tv_diemxia3)).setText(decimalFormat.format(jSONObject3.getDouble("diem")));
                        jSONObject4 = jSONObject21;
                    } catch (Exception e14) {
                        exc = e14;
                        Util.writeLog(exc);
                        cursor2.close();
                        cursor.close();
                        jSONObject4 = null;
                        jSONObject = jSONObject4;
                        str7 = str5;
                        str8 = "diem";
                        ((TextView) findViewById(R.id.tv_anxia3)).setText(decimalFormat.format(jSONObject3.getDouble(str7)));
                        ((TextView) findViewById(R.id.tv_no_Xia3)).setText(decimalFormat.format(jSONObject3.getDouble(str6)));
                        ((TextView) findViewById(R.id.tv_no_ThangThuaXia3)).setText(decimalFormat.format(jSONObject3.getDouble("ket_qua")));
                        d += jSONObject3.getDouble(str6);
                        d2 += jSONObject3.getDouble("ket_qua");
                        jSONObject2 = jSONObject;
                        if (!jSONObject2.has("xia4")) {
                        }
                        d4 = d3;
                        if (jSONObject2.has("bc")) {
                        }
                        ((TextView) findViewById(R.id.tv_no_Tong)).setText(decimalFormat.format(d));
                        ((TextView) findViewById(R.id.tv_no_ThangThua)).setText(decimalFormat.format(d4));
                        textView =  findViewById(R.id.tv_no_rp_nd);
                        GetData = cursor2;
                        textView.setText(GetData.getString(8));
                        ((TextView) findViewById(R.id.tv_no_KH)).setText(GetData.getString(4));
                        ((TextView) findViewById(R.id.tv_no_TinNhan)).setText(GetData.getString(7));
                        ((TextView) findViewById(R.id.tv_no_TG_nhan)).setText(GetData.getString(2));
                        TextView textView2222 =  findViewById(R.id.tv_ndpt);
                        string = GetData.getString(10);
                        SpannableString spannableString222 = new SpannableString(string);
                        // TODO: 7/3/2024 Comment
//                        while (i < string.length() - 1) {
//                        }
                        textView2222.setText(spannableString222);
                        GetData.close();
                        cursor.close();
                    }
                    jSONObject = jSONObject4;
                    str7 = str5;
                    str8 = "diem";
                    ((TextView) findViewById(R.id.tv_anxia3)).setText(decimalFormat.format(jSONObject3.getDouble(str7)));
                    ((TextView) findViewById(R.id.tv_no_Xia3)).setText(decimalFormat.format(jSONObject3.getDouble(str6)));
                    ((TextView) findViewById(R.id.tv_no_ThangThuaXia3)).setText(decimalFormat.format(jSONObject3.getDouble("ket_qua")));
                    d += jSONObject3.getDouble(str6);
                    d2 += jSONObject3.getDouble("ket_qua");
                }
                jSONObject2 = jSONObject;
            } catch (JSONException e15) {
                e15.printStackTrace();
                cursor2.close();
                cursor.close();
                textView = null;
            }
        } catch (JSONException e16) {
            GetData = cursor2;
            e16.printStackTrace();
            GetData.close();
            cursor.close();
            GetData.close();
            cursor.close();
        }
        if (!jSONObject2.has("xia4")) {
            try {
                ( findViewById(R.id.lnxia4)).setVisibility(View.VISIBLE);
                JSONObject jSONObject22 = new JSONObject(jSONObject2.getString("xia4"));
                str9 = str8;
                d3 = d2;
                try {
                    ((TextView) findViewById(R.id.tv_diemxia4)).setText(decimalFormat.format(jSONObject22.getDouble(str9)));
                } catch (JSONException e17) {
                    jSONObject2 = null;
                    Util.writeLog(e17);
                    cursor2.close();
                    cursor.close();
                    d4 = d3;
                    if (jSONObject2.has("bc")) {
                    }
                    ((TextView) findViewById(R.id.tv_no_Tong)).setText(decimalFormat.format(d));
                    ((TextView) findViewById(R.id.tv_no_ThangThua)).setText(decimalFormat.format(d4));
                    textView =  findViewById(R.id.tv_no_rp_nd);
                    GetData = cursor2;
                    textView.setText(GetData.getString(8));
                    ((TextView) findViewById(R.id.tv_no_KH)).setText(GetData.getString(4));
                    ((TextView) findViewById(R.id.tv_no_TinNhan)).setText(GetData.getString(7));
                    ((TextView) findViewById(R.id.tv_no_TG_nhan)).setText(GetData.getString(2));
                    TextView textView22222 =  findViewById(R.id.tv_ndpt);
                    string = GetData.getString(10);
                    SpannableString spannableString2222 = new SpannableString(string);
                    textView22222.setText(spannableString2222);
                    GetData.close();
                    cursor.close();
                }
                try {
                    ((TextView) findViewById(R.id.tv_anxia4)).setText(decimalFormat.format(jSONObject22.getDouble(str7)));
                    ((TextView) findViewById(R.id.tv_no_Xia4)).setText(decimalFormat.format(jSONObject22.getDouble(str6)));
                    ((TextView) findViewById(R.id.tv_no_ThangThuaXia4)).setText(decimalFormat.format(jSONObject22.getDouble("ket_qua")));
                    d += jSONObject22.getDouble(str6);
                    d4 = d3 + jSONObject22.getDouble("ket_qua");
                } catch (JSONException e18) {
                    jSONException2 = e18;
                    Util.writeLog(jSONException2);
                    cursor2.close();
                    cursor.close();
                    d4 = d3;
                    if (jSONObject2.has("bc")) {
                    }
                    ((TextView) findViewById(R.id.tv_no_Tong)).setText(decimalFormat.format(d));
                    ((TextView) findViewById(R.id.tv_no_ThangThua)).setText(decimalFormat.format(d4));
                    textView =  findViewById(R.id.tv_no_rp_nd);
                    GetData = cursor2;
                    textView.setText(GetData.getString(8));
                    ((TextView) findViewById(R.id.tv_no_KH)).setText(GetData.getString(4));
                    ((TextView) findViewById(R.id.tv_no_TinNhan)).setText(GetData.getString(7));
                    ((TextView) findViewById(R.id.tv_no_TG_nhan)).setText(GetData.getString(2));
                    TextView textView222222 =  findViewById(R.id.tv_ndpt);
                    string = GetData.getString(10);
                    SpannableString spannableString22222 = new SpannableString(string);
                    textView222222.setText(spannableString22222);
                    GetData.close();
                    cursor.close();
                }
            } catch (JSONException e19) {
                str9 = str8;
                d3 = d2;
            }
            if (jSONObject2.has("bc")) {
                findViewById(R.id.ln_bc).setVisibility(View.VISIBLE);
                JSONObject jSONObject23;
                try {
                    jSONObject23 = new JSONObject(jSONObject2.getString("bc"));
                    ((TextView) findViewById(R.id.tv_diembc)).setText(decimalFormat.format(jSONObject23.getDouble(str9)));
                    ((TextView) findViewById(R.id.tv_anbc)).setText(decimalFormat.format(jSONObject23.getDouble(str7)));
                    ((TextView) findViewById(R.id.tv_no_Bc)).setText(decimalFormat.format(jSONObject23.getDouble(str6)));
                    ((TextView) findViewById(R.id.tv_no_ThangThuaBc)).setText(decimalFormat.format(jSONObject23.getDouble("ket_qua")));
                    d += jSONObject23.getDouble(str6);
                    d4 += jSONObject23.getDouble("ket_qua");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            ((TextView) findViewById(R.id.tv_no_Tong)).setText(decimalFormat.format(d));
            ((TextView) findViewById(R.id.tv_no_ThangThua)).setText(decimalFormat.format(d4));
            textView =  findViewById(R.id.tv_no_rp_nd);
            GetData = cursor2;
            textView.setText(GetData.getString(8));
            ((TextView) findViewById(R.id.tv_no_KH)).setText(GetData.getString(4));
            ((TextView) findViewById(R.id.tv_no_TinNhan)).setText(GetData.getString(7));
            ((TextView) findViewById(R.id.tv_no_TG_nhan)).setText(GetData.getString(2));
            TextView textView2222222 =  findViewById(R.id.tv_ndpt);
            string = GetData.getString(10);
            SpannableString spannableString222222 = new SpannableString(string);
            for (i = 0; i < string.length() - 1; i++) {
                if (string.substring(i, i + 2).indexOf("*") > -1) {
                    for (int i2 = i; i2 > 0; i2--) {
                        int i3 = i2 + 1;
                        if (string.substring(i2, i3).indexOf(",") <= -1 && string.substring(i2, i3).indexOf(":") <= -1) {
                            spannableString222222.setSpan(new ForegroundColorSpan(Color.GREEN), i2, i + 1, 33);
                        }
                    }
                }
            }
            textView2222222.setText(spannableString222222);
            GetData.close();
            cursor.close();
        }
        str9 = str8;
        d3 = d2;
        d4 = d3;
        if (jSONObject2.has("bc")) {
        }
        ((TextView) findViewById(R.id.tv_no_Tong)).setText(decimalFormat.format(d));
        ((TextView) findViewById(R.id.tv_no_ThangThua)).setText(decimalFormat.format(d4));
        textView =  findViewById(R.id.tv_no_rp_nd);
        GetData = cursor2;
        textView.setText(GetData.getString(8));
        ((TextView) findViewById(R.id.tv_no_KH)).setText(GetData.getString(4));
        ((TextView) findViewById(R.id.tv_no_TinNhan)).setText(GetData.getString(7));
        ((TextView) findViewById(R.id.tv_no_TG_nhan)).setText(GetData.getString(2));
        TextView textView22222222 =  findViewById(R.id.tv_ndpt);
        string = GetData.getString(10);
        SpannableString spannableString2222222 = new SpannableString(string);
        // TODO: 7/3/2024 Remove
//        while (i < string.length() - 1) {
//        }
        textView22222222.setText(spannableString2222222);
        GetData.close();
        cursor.close();
    }
}
