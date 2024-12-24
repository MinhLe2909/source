package tamhoang.ldpro4.ui.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import tamhoang.ldpro4.formula.Congthuc;
import tamhoang.ldpro4.ui.home.LoginActivity;
import tamhoang.ldpro4.ui.home.MainActivity;
import tamhoang.ldpro4.ui.notification.NotificationBindObject;
import tamhoang.ldpro4.R;
import tamhoang.ldpro4.data.Database;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Frag_Database extends Fragment {
    String[] ArrayGiai;
    String Imei = null;
    Button btnDelete;
    Button btn_tt;

    Database db;
    RadioGroup gr1;
    RadioGroup gr2;
    RadioButton ketquanet;
    WebView mWebView;
    RadioButton minhngoc;
    RadioButton nazzy;

    View f202v;
    RadioButton xosome;
    RadioButton xsme;
    RadioButton xsmn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_database, container, false);
        this.db = new Database(getActivity());
        this.btn_tt =  v.findViewById(R.id.btn_tt);
        this.btnDelete =  v.findViewById(R.id.btn_Delete);
        this.xosome = (RadioButton) v.findViewById(R.id.xosome);
        this.minhngoc = (RadioButton) v.findViewById(R.id.minhngoc);
        this.nazzy = (RadioButton) v.findViewById(R.id.nazzy);
        this.ketquanet = (RadioButton) v.findViewById(R.id.ketquanet);
        this.xsme = (RadioButton) v.findViewById(R.id.xsme);
        this.xsmn = (RadioButton) v.findViewById(R.id.xsmn);
        this.gr1 = (RadioGroup) v.findViewById(R.id.gr1);
        this.gr2 = (RadioGroup) v.findViewById(R.id.gr2);
        this.nazzy.setOnCheckedChangeListener((compoundButton, b) -> {
            gr1.clearCheck();
            if (nazzy.isChecked()) {
                try {
                    new MainActivity();
                    String ngay = MainActivity.Get_ngay();
                    String str_date = MainActivity.Get_date();
                    String url = "http://thongke.nazzy.vn/handler/thongke.ashx?t=kqxsmb&date=" + (ngay.substring(3, 5) + "/" + ngay.substring(0, 2) + "/" + ngay.substring(6));
                    Request request = new StringRequest(1, url, response -> {
                        StringBuilder sb;
                        String Str = "";
                        try {
                            try {
                                JSONObject outerObject = new JSONObject(response);
                                if (outerObject.getString("Ngay").contains(MainActivity.Get_ngay())) {
                                    db.QueryData("Delete From ketqua WHERE ngay = '" + str_date + "'");
                                    Str = ((((((((((((((((((((((((("'" + outerObject.getString("GDB") + "',") + "'" + outerObject.getString("G1") + "',") + "'" + outerObject.getString("G21") + "',") + "'" + outerObject.getString("G22") + "',") + "'" + outerObject.getString("G31") + "',") + "'" + outerObject.getString("G32") + "',") + "'" + outerObject.getString("G33") + "',") + "'" + outerObject.getString("G34") + "',") + "'" + outerObject.getString("G35") + "',") + "'" + outerObject.getString("G36") + "',") + "'" + outerObject.getString("G41") + "',") + "'" + outerObject.getString("G42") + "',") + "'" + outerObject.getString("G43") + "',") + "'" + outerObject.getString("G44") + "',") + "'" + outerObject.getString("G51") + "',") + "'" + outerObject.getString("G52") + "',") + "'" + outerObject.getString("G53") + "',") + "'" + outerObject.getString("G54") + "',") + "'" + outerObject.getString("G55") + "',") + "'" + outerObject.getString("G56") + "',") + "'" + outerObject.getString("G61") + "',") + "'" + outerObject.getString("G62") + "',") + "'" + outerObject.getString("G63") + "',") + "'" + outerObject.getString("G71") + "',") + "'" + outerObject.getString("G72") + "',") + "'" + outerObject.getString("G73") + "',";
                                    Str = Str + "'" + outerObject.getString("G74") + "')";
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                if (Str.length() > 0) {
                                    String Str_sql = "InSert Into KETQUA VALUES(null,'" + str_date + "'," + Str;
                                    db.QueryData(Str_sql);
                                    sb = new StringBuilder();
                                }
                            }
                            if (Str.length() > 0) {
                                String Str_sql2 = "InSert Into KETQUA VALUES(null,'" + str_date + "'," + Str;
                                db.QueryData(Str_sql2);
                                sb = new StringBuilder();
                                sb.append("Đã tải xong kết quả ngày: ");
                                sb.append(MainActivity.Get_ngay());
                                Toast.makeText(requireActivity(), sb.toString(), Toast.LENGTH_LONG).show();
                                return;
                            }
                            Toast.makeText(getActivity(), "Không có kết quả phù hợp!", Toast.LENGTH_LONG).show();
                        } catch (Throwable th) {
                            if (Str.length() > 0) {
                                String Str_sql3 = "InSert Into KETQUA VALUES(null,'" + str_date + "'," + Str;
                                db.QueryData(Str_sql3);
                                Toast.makeText(getActivity(), "Đã tải xong kết quả ngày: " + MainActivity.Get_ngay(), Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getActivity(), "Không có kết quả phù hợp!", Toast.LENGTH_LONG).show();
                            }
                            throw th;
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    });
                    RequestQueue requestQueue = Volley.newRequestQueue(requireActivity());
                    requestQueue.add(request);
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "Kiểm tra kết nối mạng!", Toast.LENGTH_LONG).show();
                }
            }
        });
        this.mWebView = (WebView) v.findViewById(R.id.fragment_main_webview);
        TelephonyManager telemamanger = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        this.Imei = telemamanger.getDeviceId();
        if (isNetworkConnected() && this.Imei != null) {
//            check();
        }
        this.btn_tt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v2) {
                new MainActivity();
                String mDate = MainActivity.Get_date();
                String mNgay = MainActivity.Get_ngay();
                Cursor cursor = db.GetData("Select * From Ketqua WHERE ngay = '" + mDate + "'");
                cursor.moveToFirst();
                int i2 = 2;
                while (i2 < 29) {
                    try {
                        if (cursor.isNull(i2) || !Congthuc.isNumeric(cursor.getString(i2))) {
                            break;
                        } else {
                            i2++;
                        }
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "Chưa có kết quả ngày: " + mNgay, Toast.LENGTH_LONG).show();
                    }
                }
                if (i2 >= 29) {
                    try {
                        db.Tinhtien(mDate);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(getActivity(), "Đã tính tiền xong ngày " + mNgay, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), "Chưa có kết quả ngày " + mNgay + " hãy cập nhật thủ công.", Toast.LENGTH_LONG).show();
                }
                if (cursor == null || cursor.isClosed()) {
                    return;
                }
                cursor.close();
            }
        });
        this.btnDelete.setOnClickListener(new DeleteKListener());
        this.minhngoc.setOnCheckedChangeListener((buttonView, isChecked) -> {
            gr1.clearCheck();
            if (minhngoc.isChecked()) {
                DisplayKQnet();
            }
        });
        this.ketquanet.setOnCheckedChangeListener((buttonView, isChecked) -> {
            gr2.clearCheck();
            if (ketquanet.isChecked()) {
                DisplayKQnetNew();
            }
        });
        this.xosome.setOnCheckedChangeListener((buttonView, isChecked) -> {
            gr1.clearCheck();
            if (xosome.isChecked()) {
                DisplayXSme();
            }
        });
        this.xsme.setOnCheckedChangeListener((buttonView, isChecked) -> {
            gr2.clearCheck();
            if (xsme.isChecked()) {
                DisplayXSmeNew();
            }
        });
        this.xsmn.setOnCheckedChangeListener((buttonView, isChecked) -> {
            gr2.clearCheck();
            if (xsmn.isChecked()) {
                DisplayXSMNNew();
            }
        });
        this.mWebView.addJavascriptInterface(new NotificationBindObject(getActivity().getApplicationContext()), "NotificationBind");
        setUpWebViewDefaults(this.mWebView);
        if (savedInstanceState != null) {
            this.mWebView.restoreState(savedInstanceState);
        }
        DisplayXSmeNew();
        return v;
    }

    class DeleteKListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            String mDate = MainActivity.Get_date();
            String[] menus = {"Xóa vẫn lưu lại công nợ", "Xóa hết cơ sở dữ liệu", "Xóa hết dữ liệu hôm nay"};
            PopupMenu popupL = new PopupMenu(getActivity(), view);
            for (int i = 0; i < menus.length; i++) {
                popupL.getMenu().add(1, i, i, menus[i]);
            }
            new AlertDialog.Builder(getActivity());
            popupL.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    int order = item.getOrder();
                    if (order == 0) {
                        DelAllSQL_Congno();
                    } else if (order == 1) {
                        DelAllSQL();
                    } else if (order == 2) {
                        AlertDialog.Builder bui = new AlertDialog.Builder(getActivity());
                        bui.setTitle("Xoá hết dữ liệu hôm nay?");
                        bui.setPositiveButton("OK", (dialog, which) -> {
                            String str = "DELETE FROM tbl_soctS WHERE ngay_nhan = '" + mDate + "'";
                            db.QueryData(str);
                            String str2 = "DELETE FROM tbl_tinnhanS WHERE ngay_nhan = '" + mDate + "'";
                            db.QueryData(str2);
                            db.QueryData("DELETE FROM Chat_database WHERE ngay_nhan = '" + mDate + "'");
                            Toast.makeText(getActivity(), "Đã xoá", Toast.LENGTH_LONG).show();
                        });
                        bui.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        bui.create().show();
                    }
                    return true;
                }
            });
            popupL.show();
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onStop() {
        super.onStop();
        this.mWebView.clearCache(true);
    }

    class C06999 implements Response.Listener<String> {
        C06999() {
        }

        @Override
        public void onResponse(String response) {
            try {
                JSONObject outerObject = new JSONObject(response);
                JSONArray listKHs = outerObject.getJSONArray("listKHs");
                MainActivity.listKH = listKHs.getJSONObject(0);
                String str_ngay = MainActivity.listKH.getString("date").replaceAll("-", "");
                String str_date_data = str_ngay.substring(6) + "/" + str_ngay.substring(4, 6) + "/" + str_ngay.substring(0, 4);
                MainActivity.myDate = str_date_data;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void check() {
        try {
            Request deviceRequest =  new StringRequest(1, "https://ldpro.pro/api/v1/account/expire", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject outerObject = new JSONObject(response);
                        JSONArray listKHs = outerObject.getJSONArray("listKHs");
                        MainActivity.listKH = listKHs.getJSONObject(0);
                        String str_ngay = MainActivity.listKH.getString("date").replaceAll("-", "");
                        String str_date_data = str_ngay.substring(6) + "/" + str_ngay.substring(4, 6) + "/" + str_ngay.substring(0, 4);
                        MainActivity.myDate = str_date_data;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, error -> {
            });
            RequestQueue requestQueue = Volley.newRequestQueue(requireActivity());
            requestQueue.add(deviceRequest);
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Kiểm tra kết nối mạng!", Toast.LENGTH_LONG).show();
        }
    }

    class DeviceRequest extends StringRequest {
        DeviceRequest(int x0, String x1, Response.Listener listener, Response.ErrorListener x3) {
            super(x0, x1, listener, x3);
        }

        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            Map<String, String> parameters = new HashMap<>();
            parameters.put("imei", Imei);
            parameters.put("serial", LoginActivity.serial);
            return parameters;
        }
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

    public void DisplayKQnetNew() {
        this.mWebView.setVisibility(View.GONE);
        new MainActivity();
        String mDate = MainActivity.Get_ngay().replaceAll("/", "-");
        String url = "https://ketqua.net/xo-so-mien-bac.php?ngay=" + mDate;
        this.mWebView.loadUrl(url);
        this.mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url2) {
                mWebView.setVisibility(View.VISIBLE);
                loadJavascript("(function() { return document.getElementsByClassName('table table-condensed kqcenter kqvertimarginw table-kq-border table-kq-hover-div table-bordered kqbackground table-kq-bold-border tb-phoi-border watermark table-striped')[0].innerText;; })();");
            }
        });
    }

    public void loadJavascript(String javascript) {
        if (Build.VERSION.SDK_INT >= 19) {
            this.mWebView.evaluateJavascript(javascript, new ValueCallback<String>() {

                @Override
                public void onReceiveValue(String s) {
                    String msg;
                    JsonReader reader = new JsonReader(new StringReader(s));
                    reader.setLenient(true);
                    try {
                        try {
                            try {
                                if (reader.peek() != JsonToken.NULL && reader.peek() == JsonToken.STRING && (msg = reader.nextString()) != null) {
                                    ArrayGiai = msg.trim().replaceAll("\t", "!").replaceAll("\n", "!").replaceAll("!!", "!").replaceAll("!!", "!").replaceAll("!!", "!").replaceAll("!!", "!").split("!");
                                    if (ArrayGiai.length <= 0) {
                                        Toast.makeText(getActivity(), "Kiểm tra lại kết nối Internet!", Toast.LENGTH_LONG).show();
                                    } else if (ArrayGiai.length > 16) {
                                        if (xosome.isChecked()) {
                                            PhantichXosome();
                                        } else if (xsme.isChecked()) {
                                            PhantichXosomeNew();
                                        } else if (xsmn.isChecked()) {
                                            PhantichXosomeNewNew();
                                        } else {
                                            PhantichMinhngoc();
                                        }
                                    }
                                }
                                reader.close();
                            } catch (IOException e) {
                                Log.e("TAG", "MainActivity: IOException", e);
                                reader.close();
                            }
                        } catch (Throwable th) {
                            try {
                                reader.close();
                            } catch (IOException e2) {
                            }
                            throw th;
                        }
                    } catch (IOException e3) {
                    }
                }
            });
            return;
        }
        this.mWebView.loadUrl("javascript:" + javascript);
    }

    class C068313 implements ValueCallback<String> {
        C068313() {
        }

        @Override
        public void onReceiveValue(String s) {
            String msg;
            JsonReader reader = new JsonReader(new StringReader(s));
            reader.setLenient(true);
            try {
                try {
                    try {
                        if (reader.peek() != JsonToken.NULL && reader.peek() == JsonToken.STRING && (msg = reader.nextString()) != null) {
                            ArrayGiai = msg.trim().replaceAll("\t", "!").replaceAll("\n", "!").replaceAll("!!", "!").replaceAll("!!", "!").replaceAll("!!", "!").replaceAll("!!", "!").split("!");
                            if (ArrayGiai.length <= 0) {
                                Toast.makeText(getActivity(), "Kiểm tra lại kết nối Internet!", Toast.LENGTH_LONG).show();
                            } else if (ArrayGiai.length > 16) {
                                if (xosome.isChecked()) {
                                    PhantichXosome();
                                } else if (xsme.isChecked()) {
                                    PhantichXosomeNew();
                                } else if (xsmn.isChecked()) {
                                    PhantichXosomeNewNew();
                                } else {
                                    PhantichMinhngoc();
                                }
                            }
                        }
                        reader.close();
                    } catch (IOException e) {
                        Log.e("TAG", "MainActivity: IOException", e);
                        reader.close();
                    }
                } catch (Throwable th) {
                    try {
                        reader.close();
                    } catch (IOException e2) {
                    }
                    throw th;
                }
            } catch (IOException e3) {
            }
        }
    }

    public void DelAllSQL() {
        AlertDialog.Builder bui = new AlertDialog.Builder(getActivity());
        bui.setTitle("Xoá hết cơ sở dữ liệu?");
        bui.setPositiveButton("OK", (dialog, which) -> {
            db.QueryData("DROP TABLE if exists Chat_database");
            db.QueryData("DROP TABLE if exists tbl_tinnhanS");
            db.QueryData("DROP TABLE if exists tbl_soctS");
            db.Creat_TinNhanGoc();
            db.Creat_SoCT();
            db.Create_table_Chat();
            Toast.makeText(getActivity(), "Đã xoá", Toast.LENGTH_LONG).show();
        });
        bui.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        bui.create().show();
    }
    

    public void DelAllSQL_Congno() {
        AlertDialog.Builder bui = new AlertDialog.Builder(getActivity());
        bui.setTitle("Xoá dữ liệu vẫn giữ công nợ?");
        List<String> mTenKH = new ArrayList<>();
        List<String> mSodt = new ArrayList<>();
        List<String> mSoTien = new ArrayList<>();
        bui.setPositiveButton("OK", (dialog, which) -> {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            calendar.add(5, -1);
            Date date = new Date(calendar.getTimeInMillis());
            String Ngay = sdf.format(date);
            Cursor cursor = db.GetData("Select tbl_soctS.ten_kh\n, SUM(tbl_soctS.ket_qua * (100-tbl_soctS.diem_khachgiu)/100)/1000  as NoCu,   \ntbl_soctS.so_dienthoai, tbl_kh_new.type_kh  \nFROM tbl_soctS INNER JOIN tbl_kh_new ON tbl_soctS.so_dienthoai = tbl_kh_new.sdt\nGROUP BY tbl_soctS.ten_kh ORDER BY tbl_soctS.type_kh DESC");
            mTenKH.clear();
            mSodt.clear();
            mSoTien.clear();
            while (cursor.moveToNext()) {
                mTenKH.add(cursor.getString(0));
                mSodt.add(cursor.getString(2));
                mSoTien.add((cursor.getDouble(1) * 1000.0d) + "");
            }
            db.QueryData("DROP TABLE if exists Chat_database");
            db.QueryData("DROP TABLE if exists tbl_tinnhanS");
            db.QueryData("DROP TABLE if exists tbl_soctS");
            db.Creat_TinNhanGoc();
            db.Creat_SoCT();
            db.Create_table_Chat();
            for (int i = 0; i < mTenKH.size(); i++) {
                String Str = "Insert Into tbl_soctS (ngay_nhan, ten_kh, so_dienthoai, the_loai, ket_qua) Values ('" + Ngay + "','" + ((String) mTenKH.get(i)) + "','" + ((String) mSodt.get(i)) + "', 'cn'," + ((String) mSoTien.get(i)) + ")";
                db.QueryData(Str);
            }
            Toast.makeText(getActivity(), "Đã xoá", Toast.LENGTH_LONG).show();
        });
        bui.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        bui.create().show();
    }

    public void DisplayXSme() {
        this.mWebView.setVisibility(View.GONE);
        new MainActivity();
        String mDate = MainActivity.Get_ngay().replaceAll("/", "-");
        String url = "https://xosodaiphat.com/xsmb-" + mDate + ".html";
        this.mWebView.loadUrl(url);
        this.mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url2) {
                String str;
                loadJavascript("document.getElementsByClassName('embeded-breadcrumb')[0].style.display = 'none';\ndocument.getElementsByClassName('tit-mien')[0].style.display = 'none';");
                mWebView.setVisibility(View.VISIBLE);
                if (xosome.isChecked()) {
                    str = "document.getElementsByClassName('table table-bordered table-striped table-xsmb')[0].innerText;";
                } else {
                    str = "document.getElementsByClassName('table-result')[0].innerText;";
                }
                loadJavascript("(function() { return " + str + "; })();");
            }
        });
    }

    class C068818 extends WebViewClient {
        C068818() {
        }

        @Override
        public void onPageFinished(WebView view, String url2) {
            String str;
            loadJavascript("document.getElementsByClassName('embeded-breadcrumb')[0].style.display = 'none';\ndocument.getElementsByClassName('tit-mien')[0].style.display = 'none';");
            mWebView.setVisibility(View.VISIBLE);
            if (xosome.isChecked()) {
                str = "document.getElementsByClassName('table table-bordered table-striped table-xsmb')[0].innerText;";
            } else {
                str = "document.getElementsByClassName('table-result')[0].innerText;";
            }
            loadJavascript("(function() { return " + str + "; })();");
        }
    }

    public void DisplayXSmeNew() {
        this.mWebView.setVisibility(View.GONE);
        new MainActivity();
        String mDate = MainActivity.Get_ngay().replaceAll("/", "-");
        String url = "https://xoso.me/embedded/kq-mienbac?ngay_quay=" + mDate;
        this.mWebView.loadUrl(url);
        this.mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url2) {
                mWebView.setVisibility(View.VISIBLE);
                loadJavascript("(function() { return document.getElementsByClassName('kqmb extendable')[0].innerText;; })();");
            }
        });
    }

    class C068919 extends WebViewClient {
        C068919() {
        }

        @Override
        public void onPageFinished(WebView view, String url2) {
            mWebView.setVisibility(View.VISIBLE);
            loadJavascript("(function() { return document.getElementsByClassName('kqmb extendable')[0].innerText;; })();");
        }
    }

    public void DisplayXSMNNew() {
        this.mWebView.setVisibility(View.GONE);
        new MainActivity();
        String mDate = MainActivity.Get_ngay().replaceAll("/", "-");
        String url = "https://xsmn.me/embedded/kq-mienbac?ngay_quay=" + mDate;
        this.mWebView.loadUrl(url);
        this.mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url2) {
                mWebView.setVisibility(View.VISIBLE);
                loadJavascript("(function() { return document.getElementsByClassName('extendable kqmb colgiai')[0].innerText;; })();");
            }
        });
    }

    class C069120 extends WebViewClient {
        C069120() {
        }

        @Override
        public void onPageFinished(WebView view, String url2) {
            mWebView.setVisibility(View.VISIBLE);
            loadJavascript("(function() { return document.getElementsByClassName('extendable kqmb colgiai')[0].innerText;; })();");
        }
    }

    public void DisplayKQnet() {
        this.mWebView.setVisibility(View.GONE);
        new MainActivity();
        String mDate = MainActivity.Get_ngay().replaceAll("/", "-");
        String url = "https://xoso.com.vn/xsmb-" + mDate + ".html";
        this.mWebView.loadUrl(url);
        this.mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url2) {
                mWebView.setVisibility(View.VISIBLE);
                loadJavascript("(function() { return document.getElementsByClassName('table-result')[0].innerText;; })();");
            }
        });
    }

    class C069221 extends WebViewClient {
        C069221() {
        }

        @Override
        public void onPageFinished(WebView view, String url2) {
            mWebView.setVisibility(View.VISIBLE);
            loadJavascript("(function() { return document.getElementsByClassName('table-result')[0].innerText;; })();");
        }
    }

    public void PhantichXosome() {
        new MainActivity();
        String str_date = MainActivity.Get_date();
        boolean Ktra = true;
        try {
            String Str_sql = "InSert Into KETQUA VALUES(null,'" + str_date + "',";
            for (int i = 0; i < this.ArrayGiai.length; i++) {
                if (Congthuc.isNumeric(this.ArrayGiai[i])) {
                    Str_sql = Str_sql + "'" + this.ArrayGiai[i] + "',";
                } else if (this.ArrayGiai[i].length() < 2) {
                    Ktra = false;
                }
            }
            if (!Ktra) {
                Toast.makeText(getActivity(), "Chưa có kết quả!", Toast.LENGTH_LONG).show();
                return;
            }
            this.db.QueryData("Delete From ketqua WHERE ngay = '" + str_date + "'");
            StringBuilder sb = new StringBuilder();
            sb.append(Str_sql.substring(0, Str_sql.length() - 1));
            sb.append(")");
            String Str_sql2 = sb.toString();
            this.db.QueryData(Str_sql2);
            Toast.makeText(getActivity(), "Đã tải xong kết quả ngày: " + MainActivity.Get_ngay(), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
        }
    }

    public void PhantichXosomeNew() {
        StringBuilder sb;
        new MainActivity();
        String str_date = MainActivity.Get_date();
        String Str = "";
        try {
            try {
            } catch (Exception e) {
                Toast.makeText(getActivity(), "Không có kết quả phù hợp!", Toast.LENGTH_LONG).show();
                if (Str.length() > 185) {
                    this.db.QueryData("Delete From ketqua WHERE ngay = '" + str_date + "'");
                    String Str_sql = "InSert Into KETQUA VALUES(null,'" + str_date + "'," + Str;
                    this.db.QueryData(Str_sql);
                    sb = new StringBuilder();
                }
            }
            if (!Congthuc.isNumeric(this.ArrayGiai[2])) {
                if ("".length() <= 185) {
                    Toast.makeText(getActivity(), "Không có kết quả phù hợp!", Toast.LENGTH_LONG).show();
                    return;
                }
                this.db.QueryData("Delete From ketqua WHERE ngay = '" + str_date + "'");
                String Str_sql2 = "InSert Into KETQUA VALUES(null,'" + str_date + "',";
                this.db.QueryData(Str_sql2);
                Toast.makeText(getActivity(), "Đã tải xong kết quả ngày: " + MainActivity.Get_ngay(), Toast.LENGTH_LONG).show();
                return;
            }
            String Str2 = "'" + this.ArrayGiai[2].trim() + "',";
            if (!Congthuc.isNumeric(this.ArrayGiai[4])) {
                if (Str2.length() <= 185) {
                    Toast.makeText(getActivity(), "Không có kết quả phù hợp!", Toast.LENGTH_LONG).show();
                    return;
                }
                this.db.QueryData("Delete From ketqua WHERE ngay = '" + str_date + "'");
                String Str_sql3 = "InSert Into KETQUA VALUES(null,'" + str_date + "'," + Str2;
                this.db.QueryData(Str_sql3);
                Toast.makeText(getActivity(), "Đã tải xong kết quả ngày: " + MainActivity.Get_ngay(), Toast.LENGTH_LONG).show();
                return;
            }
            String Str3 = Str2 + "'" + this.ArrayGiai[4].trim() + "',";
            if (!Congthuc.isNumeric(this.ArrayGiai[6])) {
                if (Str3.length() <= 185) {
                    Toast.makeText(getActivity(), "Không có kết quả phù hợp!", Toast.LENGTH_LONG).show();
                    return;
                }
                this.db.QueryData("Delete From ketqua WHERE ngay = '" + str_date + "'");
                String Str_sql4 = "InSert Into KETQUA VALUES(null,'" + str_date + "'," + Str3;
                this.db.QueryData(Str_sql4);
                Toast.makeText(getActivity(), "Đã tải xong kết quả ngày: " + MainActivity.Get_ngay(), Toast.LENGTH_LONG).show();
                return;
            }
            String Str4 = (Str3 + "'" + this.ArrayGiai[6].trim().substring(0, 5) + "',") + "'" + this.ArrayGiai[6].trim().substring(5, this.ArrayGiai[6].trim().length()) + "',";
            if (!Congthuc.isNumeric(this.ArrayGiai[8])) {
                if (Str4.length() <= 185) {
                    Toast.makeText(getActivity(), "Không có kết quả phù hợp!", Toast.LENGTH_LONG).show();
                    return;
                }
                this.db.QueryData("Delete From ketqua WHERE ngay = '" + str_date + "'");
                String Str_sql5 = "InSert Into KETQUA VALUES(null,'" + str_date + "'," + Str4;
                this.db.QueryData(Str_sql5);
                Toast.makeText(getActivity(), "Đã tải xong kết quả ngày: " + MainActivity.Get_ngay(), Toast.LENGTH_LONG).show();
                return;
            }
            String Str5 = (((((Str4 + "'" + this.ArrayGiai[8].trim().substring(0, 5) + "',") + "'" + this.ArrayGiai[8].trim().substring(5, 10) + "',") + "'" + this.ArrayGiai[8].trim().substring(10, 15) + "',") + "'" + this.ArrayGiai[8].trim().substring(15, 20) + "',") + "'" + this.ArrayGiai[8].trim().substring(20, 25) + "',") + "'" + this.ArrayGiai[8].trim().substring(25, this.ArrayGiai[8].trim().length()) + "',";
            if (!Congthuc.isNumeric(this.ArrayGiai[10])) {
                if (Str5.length() <= 185) {
                    Toast.makeText(getActivity(), "Không có kết quả phù hợp!", Toast.LENGTH_LONG).show();
                    return;
                }
                this.db.QueryData("Delete From ketqua WHERE ngay = '" + str_date + "'");
                String Str_sql6 = "InSert Into KETQUA VALUES(null,'" + str_date + "'," + Str5;
                this.db.QueryData(Str_sql6);
                Toast.makeText(getActivity(), "Đã tải xong kết quả ngày: " + MainActivity.Get_ngay(), Toast.LENGTH_LONG).show();
                return;
            }
            String Str6 = (((Str5 + "'" + this.ArrayGiai[10].trim().substring(0, 4) + "',") + "'" + this.ArrayGiai[10].trim().substring(4, 8) + "',") + "'" + this.ArrayGiai[10].trim().substring(8, 12) + "',") + "'" + this.ArrayGiai[10].trim().substring(12, this.ArrayGiai[10].trim().length()) + "',";
            if (!Congthuc.isNumeric(this.ArrayGiai[12])) {
                if (Str6.length() <= 185) {
                    Toast.makeText(getActivity(), "Không có kết quả phù hợp!", Toast.LENGTH_LONG).show();
                    return;
                }
                this.db.QueryData("Delete From ketqua WHERE ngay = '" + str_date + "'");
                String Str_sql7 = "InSert Into KETQUA VALUES(null,'" + str_date + "'," + Str6;
                this.db.QueryData(Str_sql7);
                Toast.makeText(getActivity(), "Đã tải xong kết quả ngày: " + MainActivity.Get_ngay(), Toast.LENGTH_LONG).show();
                return;
            }
            String Str7 = (((((Str6 + "'" + this.ArrayGiai[12].trim().substring(0, 4) + "',") + "'" + this.ArrayGiai[12].trim().substring(4, 8) + "',") + "'" + this.ArrayGiai[12].trim().substring(8, 12) + "',") + "'" + this.ArrayGiai[12].trim().substring(12, 16) + "',") + "'" + this.ArrayGiai[12].trim().substring(16, 20) + "',") + "'" + this.ArrayGiai[12].trim().substring(20, this.ArrayGiai[12].trim().length()) + "',";
            if (!Congthuc.isNumeric(this.ArrayGiai[14])) {
                if (Str7.length() <= 185) {
                    Toast.makeText(getActivity(), "Không có kết quả phù hợp!", Toast.LENGTH_LONG).show();
                    return;
                }
                this.db.QueryData("Delete From ketqua WHERE ngay = '" + str_date + "'");
                String Str_sql8 = "InSert Into KETQUA VALUES(null,'" + str_date + "'," + Str7;
                this.db.QueryData(Str_sql8);
                Toast.makeText(getActivity(), "Đã tải xong kết quả ngày: " + MainActivity.Get_ngay(), Toast.LENGTH_LONG).show();
                return;
            }
            String Str8 = ((Str7 + "'" + this.ArrayGiai[14].trim().substring(0, 3) + "',") + "'" + this.ArrayGiai[14].trim().substring(3, 6) + "',") + "'" + this.ArrayGiai[14].trim().substring(6, this.ArrayGiai[14].trim().length()) + "',";
            if (!Congthuc.isNumeric(this.ArrayGiai[16])) {
                if (Str8.length() <= 185) {
                    Toast.makeText(getActivity(), "Không có kết quả phù hợp!", Toast.LENGTH_LONG).show();
                    return;
                }
                this.db.QueryData("Delete From ketqua WHERE ngay = '" + str_date + "'");
                String Str_sql9 = "InSert Into KETQUA VALUES(null,'" + str_date + "'," + Str8;
                this.db.QueryData(Str_sql9);
                Toast.makeText(getActivity(), "Đã tải xong kết quả ngày: " + MainActivity.Get_ngay(), Toast.LENGTH_LONG).show();
                return;
            }
            Str = ((Str8 + "'" + this.ArrayGiai[16].trim().substring(0, 2) + "',") + "'" + this.ArrayGiai[16].trim().substring(2, 4) + "',") + "'" + this.ArrayGiai[16].trim().substring(4, 6) + "',";
            String Str9 = Str + "'" + this.ArrayGiai[16].trim().substring(6, this.ArrayGiai[16].trim().length()) + "')";
            if (Str9.length() > 185) {
                this.db.QueryData("Delete From ketqua WHERE ngay = '" + str_date + "'");
                String Str_sql10 = "InSert Into KETQUA VALUES(null,'" + str_date + "'," + Str9;
                this.db.QueryData(Str_sql10);
                sb = new StringBuilder();
                sb.append("Đã tải xong kết quả ngày: ");
                sb.append(MainActivity.Get_ngay());
                Toast.makeText(requireContext(), sb.toString(), Toast.LENGTH_LONG).show();
                return;
            }
            Toast.makeText(getActivity(), "Không có kết quả phù hợp!", Toast.LENGTH_LONG).show();
        } catch (Throwable th) {
            if (Str.length() <= 185) {
                Toast.makeText(getActivity(), "Không có kết quả phù hợp!", Toast.LENGTH_LONG).show();
                throw th;
            }
            this.db.QueryData("Delete From ketqua WHERE ngay = '" + str_date + "'");
            String Str_sql11 = "InSert Into KETQUA VALUES(null,'" + str_date + "'," + Str;
            this.db.QueryData(Str_sql11);
            Toast.makeText(getActivity(), "Đã tải xong kết quả ngày: " + MainActivity.Get_ngay(), Toast.LENGTH_LONG).show();
            throw th;
        }
    }

    public void PhantichXosomeNewNew() {
        StringBuilder sb;
        new MainActivity();
        String str_date = MainActivity.Get_date();
        String Str = "";
        try {
            try {
            } catch (Exception e) {
                Toast.makeText(getActivity(), "Không có kết quả phù hợp!", Toast.LENGTH_LONG).show();
                if (Str.length() > 185) {
                    this.db.QueryData("Delete From ketqua WHERE ngay = '" + str_date + "'");
                    String Str_sql = "InSert Into KETQUA VALUES(null,'" + str_date + "'," + Str;
                    this.db.QueryData(Str_sql);
                    sb = new StringBuilder();
                }
            }
            if (!Congthuc.isNumeric(this.ArrayGiai[3])) {
                if ("".length() <= 185) {
                    Toast.makeText(getActivity(), "Không có kết quả phù hợp!", Toast.LENGTH_LONG).show();
                    return;
                }
                this.db.QueryData("Delete From ketqua WHERE ngay = '" + str_date + "'");
                String Str_sql2 = "InSert Into KETQUA VALUES(null,'" + str_date + "',";
                this.db.QueryData(Str_sql2);
                Toast.makeText(getActivity(), "Đã tải xong kết quả ngày: " + MainActivity.Get_ngay(), Toast.LENGTH_LONG).show();
                return;
            }
            String Str2 = "'" + this.ArrayGiai[3].trim() + "',";
            if (!Congthuc.isNumeric(this.ArrayGiai[5])) {
                if (Str2.length() <= 185) {
                    Toast.makeText(getActivity(), "Không có kết quả phù hợp!", Toast.LENGTH_LONG).show();
                    return;
                }
                this.db.QueryData("Delete From ketqua WHERE ngay = '" + str_date + "'");
                String Str_sql3 = "InSert Into KETQUA VALUES(null,'" + str_date + "'," + Str2;
                this.db.QueryData(Str_sql3);
                Toast.makeText(getActivity(), "Đã tải xong kết quả ngày: " + MainActivity.Get_ngay(), Toast.LENGTH_LONG).show();
                return;
            }
            String Str3 = Str2 + "'" + this.ArrayGiai[5].trim() + "',";
            if (!Congthuc.isNumeric(this.ArrayGiai[7])) {
                if (Str3.length() <= 185) {
                    Toast.makeText(getActivity(), "Không có kết quả phù hợp!", Toast.LENGTH_LONG).show();
                    return;
                }
                this.db.QueryData("Delete From ketqua WHERE ngay = '" + str_date + "'");
                String Str_sql4 = "InSert Into KETQUA VALUES(null,'" + str_date + "'," + Str3;
                this.db.QueryData(Str_sql4);
                Toast.makeText(getActivity(), "Đã tải xong kết quả ngày: " + MainActivity.Get_ngay(), Toast.LENGTH_LONG).show();
                return;
            }
            String Str4 = (Str3 + "'" + this.ArrayGiai[7].trim().substring(0, 5) + "',") + "'" + this.ArrayGiai[7].trim().substring(5, this.ArrayGiai[7].trim().length()) + "',";
            if (!Congthuc.isNumeric(this.ArrayGiai[9])) {
                if (Str4.length() <= 185) {
                    Toast.makeText(getActivity(), "Không có kết quả phù hợp!", Toast.LENGTH_LONG).show();
                    return;
                }
                this.db.QueryData("Delete From ketqua WHERE ngay = '" + str_date + "'");
                String Str_sql5 = "InSert Into KETQUA VALUES(null,'" + str_date + "'," + Str4;
                this.db.QueryData(Str_sql5);
                Toast.makeText(getActivity(), "Đã tải xong kết quả ngày: " + MainActivity.Get_ngay(), Toast.LENGTH_LONG).show();
                return;
            }
            String Str5 = (((((Str4 + "'" + this.ArrayGiai[9].trim().substring(0, 5) + "',") + "'" + this.ArrayGiai[9].trim().substring(5, 10) + "',") + "'" + this.ArrayGiai[9].trim().substring(10, 15) + "',") + "'" + this.ArrayGiai[9].trim().substring(15, 20) + "',") + "'" + this.ArrayGiai[9].trim().substring(20, 25) + "',") + "'" + this.ArrayGiai[9].trim().substring(25, this.ArrayGiai[9].trim().length()) + "',";
            if (!Congthuc.isNumeric(this.ArrayGiai[11])) {
                if (Str5.length() <= 185) {
                    Toast.makeText(getActivity(), "Không có kết quả phù hợp!", Toast.LENGTH_LONG).show();
                    return;
                }
                this.db.QueryData("Delete From ketqua WHERE ngay = '" + str_date + "'");
                String Str_sql6 = "InSert Into KETQUA VALUES(null,'" + str_date + "'," + Str5;
                this.db.QueryData(Str_sql6);
                Toast.makeText(getActivity(), "Đã tải xong kết quả ngày: " + MainActivity.Get_ngay(), Toast.LENGTH_LONG).show();
                return;
            }
            String Str6 = (((Str5 + "'" + this.ArrayGiai[11].trim().substring(0, 4) + "',") + "'" + this.ArrayGiai[11].trim().substring(4, 8) + "',") + "'" + this.ArrayGiai[11].trim().substring(8, 12) + "',") + "'" + this.ArrayGiai[11].trim().substring(12, this.ArrayGiai[11].trim().length()) + "',";
            if (!Congthuc.isNumeric(this.ArrayGiai[13])) {
                if (Str6.length() <= 185) {
                    Toast.makeText(getActivity(), "Không có kết quả phù hợp!", Toast.LENGTH_LONG).show();
                    return;
                }
                this.db.QueryData("Delete From ketqua WHERE ngay = '" + str_date + "'");
                String Str_sql7 = "InSert Into KETQUA VALUES(null,'" + str_date + "'," + Str6;
                this.db.QueryData(Str_sql7);
                Toast.makeText(getActivity(), "Đã tải xong kết quả ngày: " + MainActivity.Get_ngay(), Toast.LENGTH_LONG).show();
                return;
            }
            String Str7 = (((((Str6 + "'" + this.ArrayGiai[13].trim().substring(0, 4) + "',") + "'" + this.ArrayGiai[13].trim().substring(4, 8) + "',") + "'" + this.ArrayGiai[13].trim().substring(8, 12) + "',") + "'" + this.ArrayGiai[13].trim().substring(12, 16) + "',") + "'" + this.ArrayGiai[13].trim().substring(16, 20) + "',") + "'" + this.ArrayGiai[13].trim().substring(20, this.ArrayGiai[13].trim().length()) + "',";
            if (!Congthuc.isNumeric(this.ArrayGiai[15])) {
                if (Str7.length() <= 185) {
                    Toast.makeText(getActivity(), "Không có kết quả phù hợp!", Toast.LENGTH_LONG).show();
                    return;
                }
                this.db.QueryData("Delete From ketqua WHERE ngay = '" + str_date + "'");
                String Str_sql8 = "InSert Into KETQUA VALUES(null,'" + str_date + "'," + Str7;
                this.db.QueryData(Str_sql8);
                Toast.makeText(getActivity(), "Đã tải xong kết quả ngày: " + MainActivity.Get_ngay(), Toast.LENGTH_LONG).show();
                return;
            }
            String Str8 = ((Str7 + "'" + this.ArrayGiai[15].trim().substring(0, 3) + "',") + "'" + this.ArrayGiai[15].trim().substring(3, 6) + "',") + "'" + this.ArrayGiai[15].trim().substring(6, this.ArrayGiai[15].trim().length()) + "',";
            if (!Congthuc.isNumeric(this.ArrayGiai[17])) {
                if (Str8.length() <= 185) {
                    Toast.makeText(getActivity(), "Không có kết quả phù hợp!", Toast.LENGTH_LONG).show();
                    return;
                }
                this.db.QueryData("Delete From ketqua WHERE ngay = '" + str_date + "'");
                String Str_sql9 = "InSert Into KETQUA VALUES(null,'" + str_date + "'," + Str8;
                this.db.QueryData(Str_sql9);
                Toast.makeText(getActivity(), "Đã tải xong kết quả ngày: " + MainActivity.Get_ngay(), Toast.LENGTH_LONG).show();
                return;
            }
            Str = ((Str8 + "'" + this.ArrayGiai[17].trim().substring(0, 2) + "',") + "'" + this.ArrayGiai[17].trim().substring(2, 4) + "',") + "'" + this.ArrayGiai[17].trim().substring(4, 6) + "',";
            String Str9 = Str + "'" + this.ArrayGiai[17].trim().substring(6, this.ArrayGiai[17].trim().length()) + "')";
            if (Str9.length() > 185) {
                this.db.QueryData("Delete From ketqua WHERE ngay = '" + str_date + "'");
                String Str_sql10 = "InSert Into KETQUA VALUES(null,'" + str_date + "'," + Str9;
                this.db.QueryData(Str_sql10);
                sb = new StringBuilder();
                sb.append("Đã tải xong kết quả ngày: ");
                sb.append(MainActivity.Get_ngay());
                Toast.makeText(requireActivity(), sb.toString(), Toast.LENGTH_LONG).show();
                return;
            }
            Toast.makeText(getActivity(), "Không có kết quả phù hợp!", Toast.LENGTH_LONG).show();
        } catch (Throwable th) {
            if (Str.length() <= 185) {
                Toast.makeText(getActivity(), "Không có kết quả phù hợp!", Toast.LENGTH_LONG).show();
                throw th;
            }
            this.db.QueryData("Delete From ketqua WHERE ngay = '" + str_date + "'");
            String Str_sql11 = "InSert Into KETQUA VALUES(null,'" + str_date + "'," + Str;
            this.db.QueryData(Str_sql11);
            Toast.makeText(getActivity(), "Đã tải xong kết quả ngày: " + MainActivity.Get_ngay(), Toast.LENGTH_LONG).show();
            throw th;
        }
    }

    public void PhantichMinhngoc() {
        new MainActivity();
        String str_date = MainActivity.Get_date();
        boolean Ktra = true;
        try {
            String Str_sql = "InSert Into KETQUA VALUES(null,'" + str_date + "',";
            for (int i = 0; i < this.ArrayGiai.length; i++) {
                String[] CacGiai = this.ArrayGiai[i].split(" ");
                for (int ii = 0; ii < CacGiai.length; ii++) {
                    if (Congthuc.isNumeric(CacGiai[ii]) && CacGiai[ii].length() > 1) {
                        Str_sql = Str_sql + "'" + CacGiai[ii] + "',";
                    } else if (CacGiai[ii].length() < 1) {
                        Ktra = false;
                    }
                }
            }
            if (Ktra) {
                this.db.QueryData("Delete From ketqua WHERE ngay = '" + str_date + "'");
                StringBuilder sb = new StringBuilder();
                sb.append(Str_sql.substring(0, Str_sql.length() - 1));
                sb.append(")");
                String Str_sql2 = sb.toString();
                this.db.QueryData(Str_sql2);
                Toast.makeText(getActivity(), "Đã tải xong kết quả ngày: " + MainActivity.Get_ngay(), Toast.LENGTH_LONG).show();
                return;
            }
            Toast.makeText(getActivity(), "Chưa có kết quả!", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
        }
    }
}