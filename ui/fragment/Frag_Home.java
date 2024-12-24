package tamhoang.ldpro4.ui.fragment;

import static tamhoang.ldpro4.ui.home.LoginActivity.imei;
import static tamhoang.ldpro4.ui.home.LoginActivity.serial;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gw.swipeback.BuildConfig;
import tamhoang.ldpro4.R;
import tamhoang.ldpro4.data.Database;
import tamhoang.ldpro4.ui.home.MainActivity;
import tamhoang.ldpro4.utils.Util;

import net.lingala.zip4j.util.InternalZipConstants;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Frag_Home extends Fragment {
    public static String versionApp = "";
    public static String TAG = "Frag_Home";
    String TK = "";
    Button button_default;
    Database db;
    TextView edtImei;
    TextView edt_version;
    TextView tvHansd;
    TextView tvTaiKhoan;
    TextView tv_notification;
    TextView tv_notification_title;
    TextView tv_sodienthoai;
    String urlGetNews = "";
    String viewDate;

    private boolean isNetworkConnected() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) Objects.requireNonNull(requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE))).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void Kiemtra() {
        this.tvHansd.setText("");
        this.tvTaiKhoan.setText("");
        MainActivity.myDate = "";
        this.edtImei.setText(imei);
        if (!isNetworkConnected()) {
            Toast.makeText(getActivity(), "Kiểm tra kết nối Internet!", Toast.LENGTH_LONG).show();
        } else if (imei != null) {
            check();
        } else {
            Toast.makeText(getActivity(), "Không lấy được imei của thiết bị!", Toast.LENGTH_LONG).show();
//            startActivity(new Intent(getActivity(), Login.class));
        }
    }

    public void check() {
        if (imei != null) {
            try {
                Volley.newRequestQueue(getActivity()).add(new StringRequest(Request.Method.POST, this.viewDate, str -> {
                    try {
                        MainActivity.listKH = new JSONObject(str).getJSONArray("listKHs").getJSONObject(0);
                        String replaceAll = MainActivity.listKH.getString("date").replaceAll("-", "");
                        String str2 = replaceAll.substring(6) + InternalZipConstants.ZIP_FILE_SEPARATOR + replaceAll.substring(4, 6) + InternalZipConstants.ZIP_FILE_SEPARATOR + replaceAll.substring(0, 4);
                        Frag_Home.this.tvHansd.setText(str2);
                        Frag_Home.this.TK = "";
                        Frag_Home.this.TK = MainActivity.listKH.getString("acc");
                        MainActivity.myDate = str2;
                        MainActivity.Acc_manager = MainActivity.listKH.getString("acc");
                        Frag_Home.this.tvTaiKhoan.setText(Frag_Home.this.TK);
                        Frag_Home.this.tv_sodienthoai.setText(MainActivity.listKH.getString("k_tra"));
                        try {
                            Frag_Home.this.tv_notification_title.setVisibility(View.GONE);
                            Frag_Home.this.tv_notification.setVisibility(View.GONE);
                            String string = MainActivity.listKH.getString("warning");
                            if (string == null || !"".equals(string.trim())) {
                                Frag_Home.this.tv_notification_title.setVisibility(View.VISIBLE);
                                Frag_Home.this.tv_notification.setVisibility(View.VISIBLE);
                                Frag_Home.this.tv_notification.setText(string);
                            } else {
                                Volley.newRequestQueue(Frag_Home.this.getActivity(), new HurlStack()).add(new JsonObjectRequest(0, Frag_Home.this.urlGetNews, (JSONObject) null, new Response.Listener<JSONObject>() {
                                    public void onResponse(JSONObject jSONObject) {
                                        try {
                                            String string = jSONObject.getString("content_news");
                                            if ("".equals(string.trim())) {
                                                Frag_Home.this.tv_notification_title.setVisibility(View.GONE);
                                                Frag_Home.this.tv_notification.setVisibility(View.GONE);
                                                return;
                                            }
                                            Frag_Home.this.tv_notification.setText(string);
                                            Frag_Home.this.tv_notification_title.setVisibility(View.VISIBLE);
                                            Frag_Home.this.tv_notification.setVisibility(View.VISIBLE);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                            Frag_Home.this.tv_notification_title.setVisibility(View.GONE);
                                            Frag_Home.this.tv_notification.setVisibility(View.GONE);
                                            Util.writeLog(e);
                                        }
                                    }
                                }, volleyError -> Util.writeLog(volleyError)));
                            }
                            MainActivity.v3_6 = MainActivity.listKH;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            float time = (float) (((((new SimpleDateFormat("yyyy-MM-dd").parse(MainActivity.listKH.getString("date")).getTime() - new Date().getTime()) / 1000) / 60) / 60) / 24);
                            if (time >= 6.0f || time <= 0.0f) {
                                if (time < 0.0f && Frag_Home.this.getActivity() != null) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(Frag_Home.this.getActivity());
                                    builder.setTitle("Thông báo hạn sử dụng:");
                                    builder.setMessage("Đã hết hạn sử dụng phần mềm\n\nHãy liên hệ Đại lý để gia hạn");
                                    builder.setNegativeButton("Đóng", (dialogInterface, i) -> dialogInterface.cancel());
                                    builder.create().show();
                                }
                            } else if (Frag_Home.this.getActivity() != null) {
                                AlertDialog.Builder builder2 = new AlertDialog.Builder(Frag_Home.this.getActivity());
                                builder2.setTitle("Thông báo hạn sử dụng:");
                                builder2.setMessage("Hạn sử dụng còn lại " + ((int) time) + " ngày! \nHãy liên hệ Đại lý để gia hạn");
                                builder2.setNegativeButton("Đóng", (dialogInterface, i) -> dialogInterface.cancel());
                                builder2.create().show();
                            }
                        } catch (ParseException e2) {
                            e2.printStackTrace();
                        }
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                }, volleyError -> Log.e(TAG, volleyError.getMessage())) {

                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap hashMap = new HashMap();
                            hashMap.put("imei", imei);
                            hashMap.put("serial", serial);
                            return hashMap;
                    }
                });
            } catch (Exception unused) {
                Toast.makeText(getActivity(), "Kiểm tra kết nối mạng!", Toast.LENGTH_LONG).show();
            }
        }
    }

    public String currentAndroidVersion() {
        String str;
        double parseDouble = Double.parseDouble(Build.VERSION.RELEASE.replaceAll("(\\d+[.]\\d+)(.*)", "$1"));
        if (parseDouble >= 4.1d && parseDouble < 4.4d) {
            str = "Jelly Bean";
        } else if (parseDouble < 5.0d) {
            str = "Kit Kat";
        } else if (parseDouble < 6.0d) {
            str = "Lollipop";
        } else if (parseDouble < 7.0d) {
            str = "Marshmallow";
        } else if (parseDouble < 8.0d) {
            str = "Nougat";
        } else if (parseDouble < 9.0d) {
            str = "Oreo";
        } else if (parseDouble < 10.0d) {
            str = "Pie";
        } else if (parseDouble >= 10.0d) {
            str = "Android " + ((int) parseDouble);
        } else {
            str = "Unsupported";
        }
        return str + " v" + parseDouble + ", API Level: " + Build.VERSION.SDK_INT;
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.frag_home, viewGroup, false);
        this.db = new Database(getActivity());
        String Get_link = MainActivity.Get_link();
        this.viewDate = Get_link ;
        this.urlGetNews = Get_link + "notify";
        this.tvTaiKhoan =  inflate.findViewById(R.id.tv_taikhoan);
        this.tvHansd =  inflate.findViewById(R.id.tv_hansudung);
        this.edtImei =  inflate.findViewById(R.id.edt_imei);
        this.tv_sodienthoai =  inflate.findViewById(R.id.tv_sodienthoai);
        this.button_default =  inflate.findViewById(R.id.button_default);
        this.edt_version =  inflate.findViewById(R.id.edt_version);
        this.tv_notification =  inflate.findViewById(R.id.tv_notification);
        this.tv_notification_title =  inflate.findViewById(R.id.tv_notification_title);
        this.button_default.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MainActivity.myDate = "";
                Frag_Home.this.Kiemtra();
                if (MainActivity.myDate.length() > 5) {
                    Toast.makeText(requireActivity(), "Sử dụng đến: " + MainActivity.myDate, Toast.LENGTH_LONG).show();
                }
            }
        });
        try {
            versionApp = BuildConfig.VERSION_NAME;
            this.edt_version.setText(BuildConfig.VERSION_NAME);
            String str = versionApp + "\n" + currentAndroidVersion();
            versionApp = str;
            this.edt_version.setText(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Kiemtra();
        return inflate;
    }
}
