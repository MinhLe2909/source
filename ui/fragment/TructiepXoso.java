package tamhoang.ldpro4.ui.fragment;

import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.io.IOException;
import java.io.StringReader;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import net.lingala.zip4j.util.InternalZipConstants;
import org.json.JSONException;
import org.json.JSONObject;
import tamhoang.ldpro4.formula.Congthuc;
import tamhoang.ldpro4.ui.home.MainActivity;
import tamhoang.ldpro4.ui.notification.NotificationBindObject;
import tamhoang.ldpro4.R;
import tamhoang.ldpro4.utils.Util;
import tamhoang.ldpro4.data.Database;

/* loaded from: classes.dex */
public class TructiepXoso extends Fragment {
    public static final String EXTRA_FROM_NOTIFICATION = "EXTRA_FROM_NOTIFICATION";
    Switch Switch1;
    Database db;
    Handler handler;
    List<JSONObject> jsonValues;
    ListView listView;
    WebView mWebView;
    RadioButton rdb_ThienPhu;
    RadioButton rdb_XemLo;
    RadioButton rdb_XemXien;
    RadioButton rdb_XsoMe;
    View v;
    String DangXuat = "lo";
    int So_giai = 0;
    ArrayList<String> listSo = new ArrayList<>();
    String mDate = "";
    String Url = "https://xoso.mobi/embedded/kq-mienbac?ngay_quay=" + MainActivity.Get_ngay().replaceAll(InternalZipConstants.ZIP_FILE_SEPARATOR, "-");
    private Runnable runnable = new Runnable() { // from class: tamhoang.ldpro4.Fragment.TructiepXoso.1
        @Override // java.lang.Runnable
        public void run() {
            if (listSo.size() > 26) {
                handler.removeCallbacks(runnable);
                return;
            }
            TructiepXoso tructiepXoso = TructiepXoso.this;
            if (tructiepXoso.rdb_ThienPhu.isChecked()) {
                tructiepXoso.loadJavascript("(function() { return document.getElementsByClassName('table table-lotto-xsmb')[0].innerText;; })();");
            } else {
                tructiepXoso.loadJavascript("(function() { return document.getElementsByClassName('firstlast-mb fl')[0].innerText;; })();");
            }
            handler.postDelayed(this, 2000L);
        }
    };

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: tamhoang.ldpro4.Fragment.TructiepXoso$1 */
    /* loaded from: classes.dex */
    public class AnonymousClass1 implements Runnable {
        AnonymousClass1() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (listSo.size() > 26) {
                handler.removeCallbacks(runnable);
                return;
            }
            TructiepXoso tructiepXoso = TructiepXoso.this;
            if (tructiepXoso.rdb_ThienPhu.isChecked()) {
                tructiepXoso.loadJavascript("(function() { return document.getElementsByClassName('table table-lotto-xsmb')[0].innerText;; })();");
            } else {
                tructiepXoso.loadJavascript("(function() { return document.getElementsByClassName('firstlast-mb fl')[0].innerText;; })();");
            }
            handler.postDelayed(this, 2000L);
        }
    }

    /* renamed from: tamhoang.ldpro4.Fragment.TructiepXoso$2 */
    /* loaded from: classes.dex */
    class AnonymousClass2 implements CompoundButton.OnCheckedChangeListener {
        AnonymousClass2() {
        }

        @Override // android.widget.CompoundButton.OnCheckedChangeListener
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (Switch1.isChecked()) {
                mWebView.setVisibility(View.VISIBLE);
            } else {
                mWebView.setVisibility(View.GONE);
            }
        }
    }

    /* renamed from: tamhoang.ldpro4.Fragment.TructiepXoso$3 */
    /* loaded from: classes.dex */
    class AnonymousClass3 implements CompoundButton.OnCheckedChangeListener {
        AnonymousClass3() {
        }

        @Override // android.widget.CompoundButton.OnCheckedChangeListener
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (rdb_XemLo.isChecked()) {
                DangXuat = "lo";
                Xem_lv();
            }
        }
    }

    /* renamed from: tamhoang.ldpro4.Fragment.TructiepXoso$4 */
    /* loaded from: classes.dex */
    class AnonymousClass4 implements CompoundButton.OnCheckedChangeListener {
        AnonymousClass4() {
        }

        @Override // android.widget.CompoundButton.OnCheckedChangeListener
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (rdb_XemXien.isChecked()) {
                DangXuat = "xi";
                Xem_lv();
            }
        }
    }

    /* renamed from: tamhoang.ldpro4.Fragment.TructiepXoso$5 */
    /* loaded from: classes.dex */
    class AnonymousClass5 implements CompoundButton.OnCheckedChangeListener {
        AnonymousClass5() {
        }

        @Override // android.widget.CompoundButton.OnCheckedChangeListener
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (rdb_XsoMe.isChecked()) {
                Url = "https://xoso.mobi/embedded/kq-mienbac?ngay_quay=" + MainActivity.Get_ngay().replaceAll(InternalZipConstants.ZIP_FILE_SEPARATOR, "-");
                mWebView.loadUrl(Url);
            }
        }
    }

    /* renamed from: tamhoang.ldpro4.Fragment.TructiepXoso$6 */
    /* loaded from: classes.dex */
    class AnonymousClass6 implements CompoundButton.OnCheckedChangeListener {
        AnonymousClass6() {
        }

        @Override // android.widget.CompoundButton.OnCheckedChangeListener
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (rdb_ThienPhu.isChecked()) {
                Url = "https://xosothienphu.com/ma-nhung/xsmb-" + MainActivity.Get_ngay().replaceAll(InternalZipConstants.ZIP_FILE_SEPARATOR, "-") + ".html";
                mWebView.loadUrl(Url);
            }
        }
    }

    /* renamed from: tamhoang.ldpro4.Fragment.TructiepXoso$7 */
    /* loaded from: classes.dex */
    class AnonymousClass7 extends WebViewClient {
        AnonymousClass7() {
        }

        @Override // android.webkit.WebViewClient
        public void onPageFinished(WebView view, String url) {
            loadJavascript("document.getElementsByClassName('embeded-breadcrumb')[0].style.display = 'none';\ndocument.getElementsByClassName('tit-mien')[0].style.display = 'none';");
            mWebView.setVisibility(View.VISIBLE);
            Switch1.setEnabled(true);
        }
    }

    /* renamed from: tamhoang.ldpro4.Fragment.TructiepXoso$8 */
    /* loaded from: classes.dex */
    public class AnonymousClass8 implements ValueCallback<String> {
        AnonymousClass8() {
        }

        @Override // android.webkit.ValueCallback
        public void onReceiveValue(String s) {
            String nextString;
            JsonReader jsonReader = new JsonReader(new StringReader(s));
            jsonReader.setLenient(true);
            try {
                try {
                    if (jsonReader.peek() != JsonToken.NULL && jsonReader.peek() == JsonToken.STRING && (nextString = jsonReader.nextString()) != null && nextString.indexOf("\n") > -1) {
                        String[] split = nextString.substring(nextString.indexOf("0")).split("\n");
                        for (int i = 0; i < split.length; i++) {
                            if (split[i].length() > 2) {
                                split[i] = split[i].substring(2);
                            } else {
                                split[i] = "";
                            }
                        }
                        if (split.length == 10) {
                            listSo = new ArrayList<>();
                            for (int i2 = 0; i2 < split.length; i2++) {
                                String[] split2 = split[i2].replaceAll(" ", "").split(",");
                                for (int i3 = 0; i3 < split2.length; i3++) {
                                    if (split2[i3].length() == 1) {
                                        listSo.add(i2 + split2[i3]);
                                    } else if (split2[i3].length() == 2) {
                                        listSo.add(i2 + split2[i3].substring(1));
                                    }
                                }
                            }
                            if (listSo.size() != So_giai) {
                                TructiepXoso tructiepXoso = TructiepXoso.this;
                                tructiepXoso.TinhTienTuDong(tructiepXoso.listSo);
                                Xem_lv();
                                TructiepXoso tructiepXoso2 = TructiepXoso.this;
                                tructiepXoso2.So_giai = tructiepXoso2.listSo.size();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Trang xoso.me đang bị lỗi!", Toast.LENGTH_LONG).show();
                            handler.removeCallbacks(runnable);
                        }
                    }
                } catch (Exception e) {
                    Log.e("TAG", "MainActivity: IOException", e);
                    Util.writeLog(e);
                }
                try {
                    jsonReader.close();
                } catch (IOException unused) {
                }
            } catch (Throwable th) {
                try {
                    jsonReader.close();
                } catch (IOException unused2) {
                }
                throw th;
            }
        }
    }

    /* renamed from: tamhoang.ldpro4.Fragment.TructiepXoso$9 */
    /* loaded from: classes.dex */
    public class AnonymousClass9 implements Comparator<JSONObject> {
        AnonymousClass9() {
        }

        @Override // java.util.Comparator
        public int compare(JSONObject a, JSONObject b) {
            int i = 0;
            Integer num = 0;
            try {
                i = Integer.valueOf(a.getInt("xep_diem")).intValue();
                num = Integer.valueOf(b.getInt("xep_diem"));
            } catch (JSONException unused) {
            }
            return num.compareTo(Integer.valueOf(i));
        }
    }

    /* loaded from: classes.dex */
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

        public So_OmAdapter(Context context, int resource, List<JSONObject> objects) {
            super(context, resource, objects);
        }

        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        public View getView(int position, View mView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (mView == null) {
                mView = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.frag_canchuyen_lv, (ViewGroup) null);
                viewHolder = new ViewHolder();
                viewHolder.tview5 =  mView.findViewById(R.id.Tv_so);
                viewHolder.tview7 =  mView.findViewById(R.id.tv_diemNhan);
                viewHolder.tview8 =  mView.findViewById(R.id.tv_diemOm);
                viewHolder.tview1 =  mView.findViewById(R.id.tv_diemChuyen);
                viewHolder.tview4 =  mView.findViewById(R.id.tv_diemTon);
                viewHolder.tview2 =  mView.findViewById(R.id.stt);
                mView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) mView.getTag();
            }
            JSONObject jSONObject = jsonValues.get(position);
            try {
                if (jSONObject.getInt("so_nhay") > 0) {
                    // TODO: 7/4/2024 Xem lai color
//                    viewHolder.tview5.setTextColor(SupportMenu.CATEGORY_MASK);
//                    viewHolder.tview7.setTextColor(SupportMenu.CATEGORY_MASK);
//                    viewHolder.tview8.setTextColor(SupportMenu.CATEGORY_MASK);
//                    viewHolder.tview1.setTextColor(SupportMenu.CATEGORY_MASK);
//                    viewHolder.tview4.setTextColor(SupportMenu.CATEGORY_MASK);
                    if (jSONObject.getInt("so_nhay") == 1) {
                        viewHolder.tview5.setText(((Object) Html.fromHtml(jSONObject.getString("so_chon"))) + "*");
                    } else if (jSONObject.getInt("so_nhay") == 2) {
                        viewHolder.tview5.setText(((Object) Html.fromHtml(jSONObject.getString("so_chon"))) + "**");
                    } else if (jSONObject.getInt("so_nhay") == 3) {
                        viewHolder.tview5.setText(((Object) Html.fromHtml(jSONObject.getString("so_chon"))) + "***");
                    } else if (jSONObject.getInt("so_nhay") == 4) {
                        viewHolder.tview5.setText(((Object) Html.fromHtml(jSONObject.getString("so_chon"))) + "****");
                    } else if (jSONObject.getInt("so_nhay") == 5) {
                        viewHolder.tview5.setText(((Object) Html.fromHtml(jSONObject.getString("so_chon"))) + "*****");
                    } else if (jSONObject.getInt("so_nhay") == 6) {
                        viewHolder.tview5.setText(((Object) Html.fromHtml(jSONObject.getString("so_chon"))) + "******");
                    }
                    viewHolder.tview7.setText(jSONObject.getString("tien_nhan"));
                    viewHolder.tview8.setText(jSONObject.getString("tien_om"));
                    viewHolder.tview1.setText(jSONObject.getString("tien_chuyen"));
                    viewHolder.tview4.setText(jSONObject.getString("tien_ton"));
                    viewHolder.tview2.setText((position + 1) + "");
                } else {
//                    viewHolder.tview5.setTextColor(ViewCompat.MEASURED_STATE_MASK);
//                    viewHolder.tview7.setTextColor(ViewCompat.MEASURED_STATE_MASK);
//                    viewHolder.tview8.setTextColor(ViewCompat.MEASURED_STATE_MASK);
//                    viewHolder.tview1.setTextColor(ViewCompat.MEASURED_STATE_MASK);
//                    viewHolder.tview4.setTextColor(ViewCompat.MEASURED_STATE_MASK);
                    viewHolder.tview5.setText(Html.fromHtml(jSONObject.getString("so_chon")));
                    viewHolder.tview7.setText(jSONObject.getString("tien_nhan"));
                    viewHolder.tview8.setText(jSONObject.getString("tien_om"));
                    viewHolder.tview1.setText(jSONObject.getString("tien_chuyen"));
                    viewHolder.tview4.setText(jSONObject.getString("tien_ton"));
                    viewHolder.tview2.setText((position + 1) + "");
                }
            } catch (Exception e) {
                e.printStackTrace();
                Util.writeLog(e);
            }
            return mView;
        }
    }

    private boolean isNetworkConnected() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void setUpWebViewDefaults(WebView webView) {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setBuiltInZoomControls(true);
        if (Build.VERSION.SDK_INT > 11) {
            settings.setDisplayZoomControls(false);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
    }

    public void TinhTienTuDong(ArrayList<String> ArraySo) {
        boolean z;
        this.db.QueryData("Update tbl_soctS Set so_nhay = 0, ket_qua = 0 WHERE ngay_nhan = '" + this.mDate + "' AND the_loai <> 'tt' AND the_loai <> 'cn'");
        String str = "";
        for (int i = 0; i < ArraySo.size(); i++) {
            this.db.QueryData("Update tbl_soctS Set so_nhay = so_nhay + 1 Where the_loai = 'lo' and ngay_nhan = '" + this.mDate + "' And so_chon ='" + ArraySo.get(i) + "'");
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(ArraySo.get(i));
            sb.append(",");
            str = sb.toString();
        }
        Cursor GetData = this.db.GetData("Select * From tbl_soctS Where ngay_nhan = '" + this.mDate + "' AND the_loai = 'xi'");
        while (GetData.moveToNext()) {
            String[] split = GetData.getString(7).split(",");
            int i2 = 0;
            while (true) {
                if (i2 >= split.length) {
                    z = true;
                    break;
                } else {
                    if (str.indexOf(split[i2]) == -1) {
                        z = false;
                        break;
                    }
                    i2++;
                }
            }
            if (z) {
                this.db.QueryData("Update tbl_soctS Set so_nhay = 1 WHERE ID = " + GetData.getString(0));
            }
        }
        this.db.QueryData("Update tbl_soctS set ket_qua = diem * lan_an * so_nhay - tong_tien WHERE ngay_nhan = '" + this.mDate + "' AND type_kh = 1 AND the_loai <> 'tt' AND the_loai <> 'cn'");
        this.db.QueryData("Update tbl_soctS set ket_qua = -diem * lan_an * so_nhay + tong_tien WHERE ngay_nhan = '" + this.mDate + "' AND type_kh = 2 AND the_loai <> 'tt' AND the_loai <> 'cn'");
    }

    public void Xem_lv() {
        String str;
        String str2;
        this.jsonValues = new ArrayList();
        this.mDate = MainActivity.Get_date();
        if (this.DangXuat == "lo") {
            str = "Select tbl_soctS.So_chon\n, Sum((tbl_soctS.type_kh = 1) * (100-tbl_soctS.diem_khachgiu)*diem_quydoi/100) as diem\n, 0, Sum((tbl_soctS.type_kh =2) * tbl_soctS.diem_quydoi) as chuyen\n, Sum((tbl_soctS.type_kh =1) * (100-tbl_soctS.diem_khachgiu-tbl_soctS.diem_dly_giu)*diem_quydoi/100) - Sum((tbl_soctS.type_kh =2) * tbl_soctS.diem_quydoi) as ton\n, so_nhay  From so_om Left Join tbl_soctS On tbl_soctS.so_chon = so_om.So \n Where tbl_soctS.ngay_nhan='" + this.mDate + "' AND tbl_soctS.the_loai='lo' \n GROUP by so_om.So Order by ton DESC, diem DESC";
        } else {
            this.db.GetData("Select * From So_om WHERE ID = 1").moveToFirst();
            str = "SELECT so_chon, sum((type_kh =1)*(100-diem_khachgiu)*diem)/100 AS diem, 0, SUm((type_kh =2)*diem) as chuyen , SUm((type_kh =1)*(100-diem_khachgiu-diem_dly_giu)*diem/100)-SUm((type_kh =2)*diem) AS ton, so_nhay   From tbl_soctS Where ngay_nhan='" + this.mDate + "' AND the_loai='xi' GROUP by so_chon Order by ton DESC, diem DESC";
        }
        Cursor GetData = this.db.GetData(str);
        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        if (GetData != null) {
            while (GetData.moveToNext()) {
                try {
                    JSONObject jSONObject = new JSONObject();
                    int i = 4;
                    int i2 = 0;
                    if (this.DangXuat == "lo") {
                        try {
                            jSONObject.put("so_chon", GetData.getString(0));
                            jSONObject.put("xep_diem", GetData.getInt(5));
                        } catch (Exception e) {
                            e.printStackTrace();
                            GetData.close();
                            Util.writeLog(e);
                        }
                    } else if (this.listSo.size() > 0) {
                        String[] split = GetData.getString(0).split(",");
                        String str3 = "";
                        int i3 = 0;
                        while (i3 < split.length) {
                            if (this.listSo.indexOf(split[i3]) > -1) {
                                i2++;
                                str2 = str3 + "<font color='#FF0000'>" + split[i3] + "</font>,";
                            } else {
                                str2 = str3 + split[i3] + ",";
                            }
                            str3 = str2;
                            i3++;
                            i = 4;
                        }
                        if (i2 == i) {
                            jSONObject.put("xep_diem", 1000);
                        } else if (i2 == 3 && split.length == 3) {
                            jSONObject.put("xep_diem", 900);
                        } else if (i2 == 2 && split.length == 2) {
                            jSONObject.put("xep_diem", 800);
                        } else if (i2 == 3 && split.length == i) {
                            jSONObject.put("xep_diem", 100);
                        } else if (i2 == 2 && split.length == i) {
                            jSONObject.put("xep_diem", 70);
                        } else if (i2 == 1 && split.length == i) {
                            jSONObject.put("xep_diem", 50);
                        } else if (i2 == 2 && split.length == 3) {
                            jSONObject.put("xep_diem", 90);
                        } else {
                            int i4 = 1;
                            if (i2 == 1) {
                                if (split.length == 3) {
                                    jSONObject.put("xep_diem", 60);
                                } else {
                                    i4 = 1;
                                }
                            }
                            if (i2 == i4 && split.length == 2) {
                                jSONObject.put("xep_diem", 80);
                            } else {
                                jSONObject.put("xep_diem", 0);
                            }
                        }
                        jSONObject.put("so_chon", str3);
                    } else {
                        jSONObject.put("so_chon", GetData.getString(0));
                        jSONObject.put("xep_diem", 0);
                    }
                    jSONObject.put("tien_nhan", decimalFormat.format(GetData.getInt(1)));
                    jSONObject.put("tien_om", decimalFormat.format(GetData.getInt(2)));
                    jSONObject.put("tien_chuyen", decimalFormat.format(GetData.getInt(3)));
                    jSONObject.put("tien_ton", decimalFormat.format(GetData.getInt(4)));
                    jSONObject.put("so_nhay", GetData.getInt(5));
                    String str4 = this.DangXuat;
                    if (str4 == "lo") {
                        this.jsonValues.add(jSONObject);
                    } else if (str4 != "xi" || GetData.getInt(4) <= 0) {
                        break;
                    } else {
                        this.jsonValues.add(jSONObject);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    GetData.close();
                    Util.writeLog(e2);
                }
            }
            Collections.sort(this.jsonValues, new Comparator<JSONObject>() { // from class: tamhoang.ldpro4.Fragment.TructiepXoso.9
                @Override // java.util.Comparator
                public int compare(JSONObject a, JSONObject b) {
                    int i5 = 0;
                    Integer num = 0;
                    try {
                        i5 = Integer.valueOf(a.getInt("xep_diem")).intValue();
                        num = Integer.valueOf(b.getInt("xep_diem"));
                    } catch (JSONException unused) {
                    }
                    return num.compareTo(Integer.valueOf(i5));
                }
            });
            if (GetData != null && !GetData.isClosed()) {
                GetData.close();
            }
        }
        if (getActivity() != null) {
            this.listView.setAdapter((ListAdapter) new So_OmAdapter(getActivity(), R.layout.frag_canchuyen_lv, this.jsonValues));
        }
    }

    public void init() {
        this.Switch1 = (Switch) this.v.findViewById(R.id.Switch1);
        this.rdb_XemLo = (RadioButton) this.v.findViewById(R.id.rdb_XemLo);
        this.rdb_XemXien = (RadioButton) this.v.findViewById(R.id.rdb_XemXien);
        this.listView = (ListView) this.v.findViewById(R.id.ListviewTructiep);
        this.mWebView = (WebView) this.v.findViewById(R.id.fragment_main_webview);
        this.rdb_XsoMe = (RadioButton) this.v.findViewById(R.id.rdb_XsoMe);
        this.rdb_ThienPhu = (RadioButton) this.v.findViewById(R.id.rdb_ThienPhu);
    }

    public void loadJavascript(String javascript) {
        if (Build.VERSION.SDK_INT >= 19) {
            this.mWebView.evaluateJavascript(javascript, new ValueCallback<String>() { // from class: tamhoang.ldpro4.Fragment.TructiepXoso.8
                @Override // android.webkit.ValueCallback
                public void onReceiveValue(String s) {
                    String nextString;
                    JsonReader jsonReader = new JsonReader(new StringReader(s));
                    jsonReader.setLenient(true);
                    try {
                        try {
                            if (jsonReader.peek() != JsonToken.NULL && jsonReader.peek() == JsonToken.STRING && (nextString = jsonReader.nextString()) != null && nextString.indexOf("\n") > -1) {
                                String[] split = nextString.substring(nextString.indexOf("0")).split("\n");
                                for (int i = 0; i < split.length; i++) {
                                    if (split[i].length() > 2) {
                                        split[i] = split[i].substring(2);
                                    } else {
                                        split[i] = "";
                                    }
                                }
                                if (split.length == 10) {
                                    listSo = new ArrayList<>();
                                    for (int i2 = 0; i2 < split.length; i2++) {
                                        String[] split2 = split[i2].replaceAll(" ", "").split(",");
                                        for (int i3 = 0; i3 < split2.length; i3++) {
                                            if (split2[i3].length() == 1) {
                                                listSo.add(i2 + split2[i3]);
                                            } else if (split2[i3].length() == 2) {
                                                listSo.add(i2 + split2[i3].substring(1));
                                            }
                                        }
                                    }
                                    if (listSo.size() != So_giai) {
                                        TructiepXoso tructiepXoso = TructiepXoso.this;
                                        tructiepXoso.TinhTienTuDong(tructiepXoso.listSo);
                                        Xem_lv();
                                        TructiepXoso tructiepXoso2 = TructiepXoso.this;
                                        tructiepXoso2.So_giai = tructiepXoso2.listSo.size();
                                    }
                                } else {
                                    Toast.makeText(getActivity(), "Trang xoso.me đang bị lỗi!", Toast.LENGTH_LONG).show();
                                    handler.removeCallbacks(runnable);
                                }
                            }
                        } catch (Exception e) {
                            Log.e("TAG", "MainActivity: IOException", e);
                            Util.writeLog(e);
                        }
                        try {
                            jsonReader.close();
                        } catch (IOException unused) {
                        }
                    } catch (Throwable th) {
                        try {
                            jsonReader.close();
                        } catch (IOException unused2) {
                        }
                        throw th;
                    }
                }
            });
            return;
        }
        this.mWebView.loadUrl("javascript:" + javascript);
    }

    @Override // android.support.v4.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.v = inflater.inflate(R.layout.tructiepxoso, container, false);
        this.db = new Database(getActivity());
        init();
        Calendar.getInstance().setTime(new Date());
        new SimpleDateFormat("yyyy-MM-dd").setTimeZone(TimeZone.getDefault());
        this.Switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: tamhoang.ldpro4.Fragment.TructiepXoso.2
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (Switch1.isChecked()) {
                    mWebView.setVisibility(View.VISIBLE);
                } else {
                    mWebView.setVisibility(View.GONE);
                }
            }
        });
        this.rdb_XemLo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: tamhoang.ldpro4.Fragment.TructiepXoso.3
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (rdb_XemLo.isChecked()) {
                    DangXuat = "lo";
                    Xem_lv();
                }
            }
        });
        this.rdb_XemXien.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: tamhoang.ldpro4.Fragment.TructiepXoso.4
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (rdb_XemXien.isChecked()) {
                    DangXuat = "xi";
                    Xem_lv();
                }
            }
        });
        this.rdb_XsoMe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: tamhoang.ldpro4.Fragment.TructiepXoso.5
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (rdb_XsoMe.isChecked()) {
                    Url = "https://xoso.mobi/embedded/kq-mienbac?ngay_quay=" + MainActivity.Get_ngay().replaceAll(InternalZipConstants.ZIP_FILE_SEPARATOR, "-");
                    mWebView.loadUrl(Url);
                }
            }
        });
        this.rdb_ThienPhu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: tamhoang.ldpro4.Fragment.TructiepXoso.6
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (rdb_ThienPhu.isChecked()) {
                    Url = "https://xosothienphu.com/ma-nhung/xsmb-" + MainActivity.Get_ngay().replaceAll(InternalZipConstants.ZIP_FILE_SEPARATOR, "-") + ".html";
                    mWebView.loadUrl(Url);
                }
            }
        });
        this.handler = new Handler();
        if (!Congthuc.CheckTime("18:14") || Congthuc.CheckTime("24:30")) {
            this.mWebView.setVisibility(View.GONE);
        } else {
            this.Switch1.setText("Ẩn/hiện bảng Kết quả");
            this.handler.postDelayed(this.runnable, 3000L);
            this.mWebView.setVisibility(View.GONE);
        }
        this.mWebView.addJavascriptInterface(new NotificationBindObject(getActivity().getApplicationContext()), "NotificationBind");
        setUpWebViewDefaults(this.mWebView);
        if (savedInstanceState != null) {
            this.mWebView.restoreState(savedInstanceState);
        }
        if (this.mWebView.getUrl() == null) {
            this.mWebView.loadUrl(this.Url);
        }
        this.mWebView.setWebViewClient(new WebViewClient() { // from class: tamhoang.ldpro4.Fragment.TructiepXoso.7
            @Override // android.webkit.WebViewClient
            public void onPageFinished(WebView view, String url) {
                loadJavascript("document.getElementsByClassName('embeded-breadcrumb')[0].style.display = 'none';\ndocument.getElementsByClassName('tit-mien')[0].style.display = 'none';");
                mWebView.setVisibility(View.VISIBLE);
                Switch1.setEnabled(true);
            }
        });
        this.mWebView.setEnabled(false);
        Xem_lv();
        return this.v;
    }

    @Override // android.support.v4.app.Fragment
    public void onResume() {
        super.onResume();
        if (Congthuc.CheckTime("18:14") && !Congthuc.CheckTime("18:30") && isNetworkConnected()) {
            this.Switch1.setText("Ẩn/hiện bảng Kết quả");
            this.handler.postDelayed(this.runnable, 3000L);
        }
    }

    @Override // android.support.v4.app.Fragment
    public void onStop() {
        super.onStop();
        this.mWebView.clearCache(true);
        this.handler.removeCallbacks(this.runnable);
    }
}
