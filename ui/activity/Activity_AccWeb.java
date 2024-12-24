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
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import tamhoang.ldpro4.base.BaseToolBarActivity;
import tamhoang.ldpro4.ui.home.MainActivity;
import tamhoang.ldpro4.R;
import tamhoang.ldpro4.data.Database;

/* loaded from: classes.dex */
public class Activity_AccWeb extends BaseToolBarActivity {
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
    String new_web = "";
    TextView tview_Maxbc;
    TextView tview_Maxdea;
    TextView tview_Maxdeb;
    TextView tview_Maxdec;
    TextView tview_Maxded;
    TextView tview_Maxlo;
    TextView tview_Maxxi2;
    TextView tview_Maxxi3;
    TextView tview_Maxxi4;

    private void init() {
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

    public void RUN() {
        final OkHttpClient okHttpClient = new OkHttpClient();
        final JSONObject jSONObject = new JSONObject();
        final AtomicReference atomicReference = new AtomicReference("");
        if (Build.VERSION.SDK_INT >= 24) {
            CompletableFuture.runAsync(new Runnable() { // from class: tamhoang.ldpro4.Activity.Activity_AccWeb.2
                @Override // java.lang.Runnable
                public final void run() {
                    Activity_AccWeb.this.lambda$RUN$0$Activity_AccWeb(jSONObject, atomicReference, okHttpClient);
                }
            });
        }
    }

    @Override // tamhoang.ldpro4.Congthuc.BaseToolBarActivity
    public int getLayoutId() {
        return R.layout.activity_acc_web;
    }

    public void lambda$RUN$0$Activity_AccWeb(JSONObject Json, AtomicReference str3, OkHttpClient okHttpClient) {
        try {
            Json.put("Username", this.edt_account.getText().toString());
            Json.put("Password", this.edt_password.getText().toString());
            str3.set(okHttpClient.newCall(new Request.Builder().url("https://id.lotusapi.com/auth/sign-in").header("Content-Type", "application/json").post(RequestBody.create(Json.toString(), MediaType.parse("application/json"))).build()).execute().body().string());
            JSONObject jSONObject = new JSONObject(str3.toString());
            if (!jSONObject.has("IdToken")) {
                new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: tamhoang.ldpro4.Activity.Activity_AccWeb.4
                    @Override // java.lang.Runnable
                    public void run() {
                        Toast.makeText(Activity_AccWeb.this, "Sai tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                    }
                });
                return;
            }
            MainActivity.MyToken = jSONObject.getString("IdToken");
            this.new_web = this.edt_account.getText().toString();
            ResponseBody body = okHttpClient.newCall(new Request.Builder().header("Authorization", "Bearer " + MainActivity.MyToken).url("https://lotto.lotusapi.com/user-game-settings/player").get().build()).execute().body();
            if (body != null) {
                new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: tamhoang.ldpro4.Activity.Activity_AccWeb.3
                    @Override // java.lang.Runnable
                    public void run() {
                        Activity_AccWeb.this.liner_caidat.setVisibility(View.VISIBLE);
                        Activity_AccWeb.this.edt_account.setEnabled(false);
                        Activity_AccWeb.this.edt_password.setEnabled(false);
                        Activity_AccWeb.this.btn_them_trang.setText("Thêm / Sửa trang");
//                        Activity_AccWeb.this.btn_them_trang.setTextColor(SupportMenu.CATEGORY_MASK);
                        Toast.makeText(Activity_AccWeb.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    }
                });
                JSONArray jSONArray = new JSONArray(body.string());
                for (int i = 0; i < jSONArray.length(); i++) {
                    JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                    if (jSONObject2.getInt("GameType") == 0 && jSONObject2.getInt("BetType") == 0) {
                        this.tview_Maxdeb.setText(jSONObject2.getString("MaxPointPerNumber"));
                    }
                    if (jSONObject2.getInt("GameType") == 0 && jSONObject2.getInt("BetType") == 1) {
                        this.tview_Maxlo.setText(jSONObject2.getString("MaxPointPerNumber"));
                    }
                    if (jSONObject2.getInt("GameType") == 0 && jSONObject2.getInt("BetType") == 2) {
                        this.tview_Maxxi2.setText(jSONObject2.getString("MaxPointPerNumber"));
                    }
                    if (jSONObject2.getInt("GameType") == 0 && jSONObject2.getInt("BetType") == 3) {
                        this.tview_Maxxi3.setText(jSONObject2.getString("MaxPointPerNumber"));
                    }
                    if (jSONObject2.getInt("GameType") == 0 && jSONObject2.getInt("BetType") == 4) {
                        this.tview_Maxxi4.setText(jSONObject2.getString("MaxPointPerNumber"));
                    }
                    if (jSONObject2.getInt("GameType") == 0 && jSONObject2.getInt("BetType") == 21) {
                        this.tview_Maxdea.setText(jSONObject2.getString("MaxPointPerNumber"));
                    }
                    if (jSONObject2.getInt("GameType") == 0 && jSONObject2.getInt("BetType") == 22) {
                        this.tview_Maxded.setText(jSONObject2.getString("MaxPointPerNumber"));
                    }
                    if (jSONObject2.getInt("GameType") == 0 && jSONObject2.getInt("BetType") == 23) {
                        this.tview_Maxdec.setText(jSONObject2.getString("MaxPointPerNumber"));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    @Override // tamhoang.ldpro4.Congthuc.BaseToolBarActivity, android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity, android.support.v4.app.SupportActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acc_web);
        this.db = new Database(this);
        this.new_web = getIntent().getStringExtra("new_web");
        init();
        if (this.new_web.isEmpty()) {
            this.liner_caidat.setVisibility(View.GONE);
        } else {
            Cursor GetData = this.db.GetData("Select * from tbl_chaytrang_acc Where Username = '" + this.new_web + "'");
            GetData.moveToFirst();
            this.edt_account.setText(GetData.getString(0));
            this.edt_password.setText(GetData.getString(1));
            this.edt_account.setEnabled(false);
            this.edt_password.setEnabled(false);
            this.btn_them_trang.setText("Thêm / Sửa trang");
//            this.btn_them_trang.setTextColor(SupportMenu.CATEGORY_MASK);
            try {
                JSONObject jSONObject = new JSONObject(GetData.getString(2));
                this.edt_Giadea.setText(jSONObject.getString("gia_dea"));
                this.edt_Giadeb.setText(jSONObject.getString("gia_deb"));
                this.edt_Giadec.setText(jSONObject.getString("gia_dec"));
                this.edt_Giaded.setText(jSONObject.getString("gia_ded"));
                this.edt_Gialo.setText(jSONObject.getString("gia_lo"));
                this.edt_Giaxi2.setText(!jSONObject.has("gia_xi2") ? "560" : jSONObject.getString("gia_xi2"));
                this.edt_Giaxi3.setText(!jSONObject.has("gia_xi3") ? "520" : jSONObject.getString("gia_xi3"));
                this.edt_Giaxi4.setText(!jSONObject.has("gia_xi4") ? "450" : jSONObject.getString("gia_xi4"));
                this.tview_Maxdea.setText(jSONObject.getString("max_dea"));
                this.tview_Maxdeb.setText(jSONObject.getString("max_deb"));
                this.tview_Maxdec.setText(jSONObject.getString("max_dec"));
                this.tview_Maxded.setText(jSONObject.getString("max_ded"));
                this.tview_Maxlo.setText(jSONObject.getString("max_lo"));
                this.tview_Maxxi2.setText(jSONObject.getString("max_xi2"));
                this.tview_Maxxi3.setText(jSONObject.getString("max_xi3"));
                this.tview_Maxxi4.setText(jSONObject.getString("max_xi4"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        this.btn_them_trang.setOnClickListener(new View.OnClickListener() { // from class: tamhoang.ldpro4.Activity.Activity_AccWeb.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (Activity_AccWeb.this.new_web.isEmpty()) {
                    if (Activity_AccWeb.this.db.GetData("Select * from tbl_chaytrang_acc Where Username = '" + Activity_AccWeb.this.edt_account.getText().toString() + "'").getCount() == 0) {
                        Activity_AccWeb.this.RUN();
                        return;
                    } else {
                        Toast.makeText(Activity_AccWeb.this, "Đã có tài khoản này trong hệ thống", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                JSONObject jSONObject2 = new JSONObject();
                try {
                    jSONObject2.put("gia_dea", Activity_AccWeb.this.edt_Giadea.getText().toString().replaceAll("\\.", ""));
                    jSONObject2.put("gia_deb", Activity_AccWeb.this.edt_Giadeb.getText().toString().replaceAll("\\.", ""));
                    jSONObject2.put("gia_dec", Activity_AccWeb.this.edt_Giadec.getText().toString().replaceAll("\\.", ""));
                    jSONObject2.put("gia_ded", Activity_AccWeb.this.edt_Giaded.getText().toString().replaceAll("\\.", ""));
                    jSONObject2.put("gia_lo", Activity_AccWeb.this.edt_Gialo.getText().toString().replaceAll("\\.", ""));
                    jSONObject2.put("gia_xi2", Activity_AccWeb.this.edt_Giaxi2.getText().toString().replaceAll("\\.", ""));
                    jSONObject2.put("gia_xi3", Activity_AccWeb.this.edt_Giaxi3.getText().toString().replaceAll("\\.", ""));
                    jSONObject2.put("gia_xi4", Activity_AccWeb.this.edt_Giaxi4.getText().toString().replaceAll("\\.", ""));
                    jSONObject2.put("max_dea", Activity_AccWeb.this.tview_Maxdea.getText().toString().replaceAll("\\.", ""));
                    jSONObject2.put("max_deb", Activity_AccWeb.this.tview_Maxdeb.getText().toString().replaceAll("\\.", ""));
                    jSONObject2.put("max_dec", Activity_AccWeb.this.tview_Maxdec.getText().toString().replaceAll("\\.", ""));
                    jSONObject2.put("max_ded", Activity_AccWeb.this.tview_Maxded.getText().toString().replaceAll("\\.", ""));
                    jSONObject2.put("max_lo", Activity_AccWeb.this.tview_Maxlo.getText().toString().replaceAll("\\.", ""));
                    jSONObject2.put("max_xi2", Activity_AccWeb.this.tview_Maxxi2.getText().toString().replaceAll("\\.", ""));
                    jSONObject2.put("max_xi3", Activity_AccWeb.this.tview_Maxxi3.getText().toString().replaceAll("\\.", ""));
                    jSONObject2.put("max_xi4", Activity_AccWeb.this.tview_Maxxi4.getText().toString().replaceAll("\\.", ""));
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
                Toast.makeText(Activity_AccWeb.this, "Đã lưu thành công", Toast.LENGTH_SHORT).show();
                Activity_AccWeb.this.db.QueryData("INSERT OR REPLACE Into tbl_chaytrang_acc (Username, Password, Setting) Values ('" + Activity_AccWeb.this.edt_account.getText().toString() + "', '" + Activity_AccWeb.this.edt_password.getText().toString() + "', '" + jSONObject2.toString() + "')");
                Activity_AccWeb.this.finish();
            }
        });
    }
}
