package tamhoang.ldpro4.ui.activity;

import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.helper.HttpConnection;
import tamhoang.ldpro4.base.BaseToolBarActivity;
import tamhoang.ldpro4.ui.home.LoginActivity;
import tamhoang.ldpro4.R;
import tamhoang.ldpro4.utils.Util;
import tamhoang.ldpro4.data.Database;

/* loaded from: classes.dex */
public class Activity_AccWeb_Win extends BaseToolBarActivity {
    Button btn_them_trang;
    Database db;
    EditText edt_Giabc;
    EditText edt_Giadea;
    EditText edt_Giadeb;
    EditText edt_Giadec;
    EditText edt_Giaded;
    EditText edt_Gialo;
    EditText edt_Giaxi2;
    EditText edt_Giaxi3;
    EditText edt_Giaxi4;
    EditText edt_account;
    EditText edt_password;
    LinearLayout liner_caidat;
    EditText tview_Maxbc;
    EditText tview_Maxdea;
    EditText tview_Maxdeb;
    EditText tview_Maxdec;
    EditText tview_Maxded;
    EditText tview_Maxlo;
    EditText tview_Maxxi2;
    EditText tview_Maxxi3;
    EditText tview_Maxxi4;
    TextView tview_tindung;
    String new_web = "";
    String gia_dea = "1200";
    String gia_deb = "705";
    String gia_dec = "1200";
    String gia_ded = "1200";
    String gia_lo = "21680";
    String gia_xi2 = "560";
    String gia_xi3 = "520";
    String gia_xi4 = "450";
    String max_dea = "20000";
    String max_deb = "50000";
    String max_dec = "20000";
    String max_ded = "20000";
    String max_lo = "10000";
    String max_xi2 = "15000";
    String max_xi3 = "15000";
    String max_xi4 = "10000";

    private void init() {
        this.tview_tindung =  findViewById(R.id.tview_tindung);
        this.liner_caidat =  findViewById(R.id.liner_caidat);
        this.btn_them_trang =  findViewById(R.id.btn_them_trang);
        this.edt_account =  findViewById(R.id.edt_account);
        this.edt_password =  findViewById(R.id.edt_password);
        this.edt_Giadea =  findViewById(R.id.edt_Giadea);
        this.edt_Giadeb =  findViewById(R.id.edt_Giadeb);
        this.edt_Giadec =  findViewById(R.id.edt_Giadec);
        this.edt_Giaded =  findViewById(R.id.edt_Giaded);
        this.edt_Gialo =  findViewById(R.id.edt_Gialo);
        this.tview_Maxdea =  findViewById(R.id.tview_Maxdea);
        this.tview_Maxdeb =  findViewById(R.id.tview_Maxdeb);
        this.tview_Maxdec =  findViewById(R.id.tview_Maxdec);
        this.tview_Maxded =  findViewById(R.id.tview_Maxded);
        this.tview_Maxlo =  findViewById(R.id.tview_Maxlo);
        this.tview_Maxxi2 =  findViewById(R.id.tview_Maxxi2);
        this.tview_Maxxi3 =  findViewById(R.id.tview_Maxxi3);
        this.tview_Maxxi4 =  findViewById(R.id.tview_Maxxi4);
        this.edt_Giaxi2 =  findViewById(R.id.edt_Giaxi2);
        this.edt_Giaxi3 =  findViewById(R.id.edt_Giaxi3);
        this.edt_Giaxi4 =  findViewById(R.id.edt_Giaxi4);
    }

    public void AddOrEdit() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("gia_dea", this.edt_Giadea.getText().toString().replaceAll("\\.", ""));
            jSONObject.put("gia_deb", this.edt_Giadeb.getText().toString().replaceAll("\\.", ""));
            jSONObject.put("gia_dec", this.edt_Giadec.getText().toString().replaceAll("\\.", ""));
            jSONObject.put("gia_ded", this.edt_Giaded.getText().toString().replaceAll("\\.", ""));
            jSONObject.put("gia_lo", this.edt_Gialo.getText().toString().replaceAll("\\.", ""));
            jSONObject.put("gia_xi2", this.edt_Giaxi2.getText().toString().replaceAll("\\.", ""));
            jSONObject.put("gia_xi3", this.edt_Giaxi3.getText().toString().replaceAll("\\.", ""));
            jSONObject.put("gia_xi4", this.edt_Giaxi4.getText().toString().replaceAll("\\.", ""));
            jSONObject.put("max_dea", this.tview_Maxdea.getText().toString().replaceAll("\\.", ""));
            jSONObject.put("max_deb", this.tview_Maxdeb.getText().toString().replaceAll("\\.", ""));
            jSONObject.put("max_dec", this.tview_Maxdec.getText().toString().replaceAll("\\.", ""));
            jSONObject.put("max_ded", this.tview_Maxded.getText().toString().replaceAll("\\.", ""));
            jSONObject.put("max_lo", this.tview_Maxlo.getText().toString().replaceAll("\\.", ""));
            jSONObject.put("max_xi2", this.tview_Maxxi2.getText().toString().replaceAll("\\.", ""));
            jSONObject.put("max_xi3", this.tview_Maxxi3.getText().toString().replaceAll("\\.", ""));
            jSONObject.put("max_xi4", this.tview_Maxxi4.getText().toString().replaceAll("\\.", ""));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Toast.makeText(this, "Đã lưu thành công", Toast.LENGTH_SHORT).show();
        this.db.QueryData("INSERT OR REPLACE Into tbl_chaytrang_acc_win (Username, WinWinKey, Setting) Values ('" + this.edt_account.getText().toString() + "', '" + this.edt_password.getText().toString() + "', '" + jSONObject.toString() + "')");
        finish();
    }

    public void RUN(final boolean update) {
        new OkHttpClient();
        new JSONObject();
        new AtomicReference("");
        if (Build.VERSION.SDK_INT >= 24) {
            CompletableFuture.runAsync(new Runnable() { // from class: tamhoang.ldpro4.Activity.Activity_AccWeb_Win.2
                @Override // java.lang.Runnable
                public final void run() {
                    Activity_AccWeb_Win.this.lambda$RUN$0$Activity_AccWeb_Win(update);
                }
            });
        }
    }

    @Override // tamhoang.ldpro4.Congthuc.BaseToolBarActivity
    public int getLayoutId() {
        return R.layout.activity_acc_web_win;
    }

    public void lambda$RUN$0$Activity_AccWeb_Win(final boolean update) {
        try {
            MediaType parse = MediaType.parse("application/json; charset=utf-8");
            OkHttpClient okHttpClient = new OkHttpClient();
            JSONObject jSONObject = new JSONObject(okHttpClient.newCall(new Request.Builder().url("http://gadgetman.website/json_winwin.php").post(RequestBody.create("{\"imei\":\"" + LoginActivity.imei + "\",\"game_type\":\"BL\",\"account\":\"" + ((Object) this.edt_account.getText()) + "\",\"key\":\"" + this.edt_password.getText().toString() + "\"}", parse)).build()).execute().body().string());
            if (jSONObject.getInt("code") < 0) {
                final String string = jSONObject.getString(NotificationCompat.CATEGORY_MESSAGE);
                new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: tamhoang.ldpro4.Activity.Activity_AccWeb_Win.3
                    @Override // java.lang.Runnable
                    public void run() {
                        Toast.makeText(Activity_AccWeb_Win.this, string, Toast.LENGTH_SHORT).show();
                    }
                });
                return;
            }
            JSONObject jSONObject2 = jSONObject.getJSONObject("data");
            JSONObject jSONObject3 = new JSONObject(okHttpClient.newCall(new Request.Builder().url(jSONObject2.getString("host")).post(RequestBody.create(jSONObject2.getJSONObject("data").toString(), MediaType.parse(HttpConnection.FORM_URL_ENCODED))).build()).execute().body().string());
            if (jSONObject3.getInt("code") == 0) {
                final String string2 = jSONObject3.getJSONObject("data").getString("balance");
                new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: tamhoang.ldpro4.Activity.Activity_AccWeb_Win.5
                    @Override // java.lang.Runnable
                    public void run() {
                        if (update) {
                            Activity_AccWeb_Win.this.AddOrEdit();
                        }
                        Activity_AccWeb_Win.this.tview_tindung.setText(string2);
                        Activity_AccWeb_Win.this.liner_caidat.setVisibility(View.VISIBLE);
                        Activity_AccWeb_Win.this.btn_them_trang.setText("Thêm / Sửa trang");
//                        Activity_AccWeb_Win.this.btn_them_trang.setTextColor(SupportMenu.CATEGORY_MASK);
                        Toast.makeText(Activity_AccWeb_Win.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    }
                });
                return;
            }
            final String str = "Win-Win: " + jSONObject3.getString(NotificationCompat.CATEGORY_MESSAGE);
            new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: tamhoang.ldpro4.Activity.Activity_AccWeb_Win.4
                @Override // java.lang.Runnable
                public void run() {
                    Toast.makeText(Activity_AccWeb_Win.this, str, Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Util.writeWinWinLog(e);
            e.printStackTrace();
            new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: tamhoang.ldpro4.Activity.Activity_AccWeb_Win.6
                @Override // java.lang.Runnable
                public void run() {
                    Toast.makeText(Activity_AccWeb_Win.this, "Đăng nhập thất bại.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override // tamhoang.ldpro4.Congthuc.BaseToolBarActivity, android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity, android.support.v4.app.SupportActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acc_web_win);
        this.db = new Database(this);
        this.new_web = getIntent().getStringExtra("new_web");
        init();
        Cursor GetData = this.db.GetData("Select * from tbl_chaytrang_acc_win Where Username = '" + this.new_web + "'");
        this.btn_them_trang.setText("Thêm / Sửa trang");
//        this.btn_them_trang.setTextColor(SupportMenu.CATEGORY_MASK);
        try {
            if (GetData.getCount() > 0) {
                GetData.moveToFirst();
                JSONObject jSONObject = new JSONObject(GetData.getString(2));
                this.edt_account.setText(GetData.getString(0));
                this.edt_password.setText(GetData.getString(1));
                this.gia_dea = jSONObject.getString("gia_dea");
                this.gia_deb = jSONObject.getString("gia_deb");
                this.gia_dec = jSONObject.getString("gia_dec");
                this.gia_ded = jSONObject.getString("gia_ded");
                this.gia_lo = jSONObject.getString("gia_lo");
                this.gia_xi2 = jSONObject.getString("gia_xi2");
                this.gia_xi3 = jSONObject.getString("gia_xi3");
                this.gia_xi4 = jSONObject.getString("gia_xi4");
                this.max_dea = jSONObject.getString("max_dea");
                this.max_deb = jSONObject.getString("max_deb");
                this.max_dec = jSONObject.getString("max_dec");
                this.max_ded = jSONObject.getString("max_ded");
                this.max_lo = jSONObject.getString("max_lo");
                this.max_xi2 = jSONObject.getString("max_xi2");
                this.max_xi3 = jSONObject.getString("max_xi3");
                this.max_xi4 = jSONObject.getString("max_xi4");
                RUN(false);
            }
            this.edt_Giadea.setText(this.gia_dea);
            this.edt_Giadeb.setText(this.gia_deb);
            this.edt_Giadec.setText(this.gia_dec);
            this.edt_Giaded.setText(this.gia_ded);
            this.edt_Gialo.setText(this.gia_lo);
            this.edt_Giaxi2.setText(this.gia_xi2);
            this.edt_Giaxi3.setText(this.gia_xi3);
            this.edt_Giaxi4.setText(this.gia_xi4);
            this.tview_Maxdea.setText(this.max_dea);
            this.tview_Maxdeb.setText(this.max_deb);
            this.tview_Maxdec.setText(this.max_dec);
            this.tview_Maxded.setText(this.max_ded);
            this.tview_Maxlo.setText(this.max_lo);
            this.tview_Maxxi2.setText(this.max_xi2);
            this.tview_Maxxi3.setText(this.max_xi3);
            this.tview_Maxxi4.setText(this.max_xi4);
            this.edt_Giadeb.setEnabled(false);
            this.edt_Gialo.setEnabled(false);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.btn_them_trang.setOnClickListener(new View.OnClickListener() { // from class: tamhoang.ldpro4.Activity.Activity_AccWeb_Win.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (Activity_AccWeb_Win.this.new_web.length() != 0) {
                    Activity_AccWeb_Win.this.RUN(true);
                    return;
                }
                if (Activity_AccWeb_Win.this.db.GetData("Select * from tbl_chaytrang_acc_win Where Username = '" + Activity_AccWeb_Win.this.edt_account.getText().toString() + "'").getCount() == 0) {
                    Activity_AccWeb_Win.this.RUN(true);
                } else {
                    Toast.makeText(Activity_AccWeb_Win.this, "Đã có tài khoản này trong hệ thống", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
