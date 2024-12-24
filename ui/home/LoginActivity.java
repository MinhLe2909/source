package tamhoang.ldpro4.ui.home;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.provider.Telephony;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.IntentUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ToastUtils;
import tamhoang.ldpro4.R;
import tamhoang.ldpro4.akaman.AkaManSec;
import tamhoang.ldpro4.data.Database;
import tamhoang.ldpro4.ui.home.MainActivity;
import tamhoang.ldpro4.utils.Util;

import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_READ_PHONE_STATE = 999;
    public static String imei;
    public static String serial;
    public static String v3_4;
    Database db;
    Intent intent;
    LinearLayout linearLayout;
    Button login;


    public void createDatabase() {
        int i;
        try {
            db.Creat_TinNhanGoc();
            db.Create_table_Chat();
            db.Creat_SoCT();
            db.Creat_So_Om();
            db.List_Khach_Hang();
            db.Bang_KQ();
            db.ThayThePhu();
            db.Another_setting();
            db.Creat_Chaytrang_acc();
            db.Creat_Chaytrang_ticket();
            AkaManSec.initSecTable(db);
            createChayTrangWinWinTable();
            Cursor GetData = db.GetData("Select * From so_om");
            if (GetData.getCount() < 1) {
                int i2 = 0;
                while (true) {
                    if (i2 >= 10) {
                        break;
                    }
                    db.QueryData("Insert into so_om Values (null, '0" + i2 + "', 0, 0, 0, 0, 0, 0, 0, 0, 0, null, null)");
                    i2++;
                }
                for (i = 10; i < 100; i++) {
                    db.QueryData("Insert into so_om Values (null, '" + i + "', 0, 0, 0, 0, 0, 0, 0, 0, 0, null, null)");
                }
            }
            Cursor GetData2 = db.GetData("Select Om_Xi3 FROM So_om WHERE So = '05'");
            GetData2.moveToFirst();
            if (GetData2.getInt(0) == 0) {
                db.QueryData("UPDATE So_om SET Om_Xi3 = 18, Om_Xi4 = 15 WHERE So = '05'");
            }
            if (GetData != null && !GetData.isClosed()) {
                GetData.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createChayTrangWinWinTable() {
        db.QueryData("CREATE TABLE IF NOT EXISTS tbl_chaytrang_acc_win(Username VARCHAR(30) PRIMARY KEY, WinWinKey VARCHAR(40) NOT NULL, Setting TEXT NOT NULL, Status VARCHAR(20) DEFAULT NULL)");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        db = new Database(this);
        login = findViewById(R.id.btn_login);
        checkPermissions();
        login.setOnClickListener(view -> {
            if (checkPermissions()) {
                if (getImei() == null || getImei().isEmpty()) return;
                try {
                    createDatabase();
                    intent = new Intent(LoginActivity.this, tamhoang.ldpro4.ui.home.MainActivity.class);
                    startActivities(new Intent[]{intent});
                    finish();
                } catch (Exception e) {
                    Util.writeLog(e);
                }
            }
        });
    }

    public String getImei() {
        PermissionUtils.permission(Manifest.permission.READ_PHONE_STATE)
                .callback(new PermissionUtils.FullCallback() {
                    @Override
                    public void onGranted(@NonNull List<String> granted) {
                        if (!granted.isEmpty()) {
                            TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                            try {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    imei = telephonyManager.getImei();
                                } else {
                                    imei = telephonyManager.getDeviceId();
                                }
                                serial = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
                            } catch (Exception e) {
                                Log.e(this.getClass().getSimpleName(), e.getMessage());
                            }
                        }
                    }

                    @Override
                    public void onDenied(@NonNull List<String> deniedForever, @NonNull List<String> denied) {
                        ToastUtils.showShort("Bạn đã từ chối quyền truy cập");
                    }
                }).request();
        return imei;
    }

    private Boolean checkPermissions() {
        Boolean isGranted = PermissionUtils.isGranted(
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.RECEIVE_SMS,
                Manifest.permission.SEND_SMS,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (!isGranted) {
            PermissionUtils.permission(Manifest.permission.READ_CONTACTS,
                            Manifest.permission.RECEIVE_SMS,
                            Manifest.permission.SEND_SMS,
                            Manifest.permission.READ_PHONE_STATE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE).callback(new PermissionUtils.FullCallback() {
                        @Override
                        public void onGranted(@NonNull List<String> granted) {
                            if (granted.size() == 4) {
                                checkSmsDefault();
                            }
                        }

                        @Override
                        public void onDenied(@NonNull List<String> deniedForever, @NonNull List<String> denied) {
                            ToastUtils.showShort("Bạn đã từ chối quyền truy cập");
                        }
                    })
                    .request();
        }
        return isGranted;
    }

    public void checkSmsDefault() {
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            String defaultPackage = Telephony.Sms.getDefaultSmsPackage(getApplicationContext());
            if (defaultPackage != null && defaultPackage.equals(getApplicationContext().getPackageName())) {
                return;
            }
            // from class: tamhoang.ldpro4.3
            showAlertBox("Cài đặt mặc định!", "Cho phép ứng dụng cài làm mặc đnh để quản lý tin nhắn tốt hơn!").setPositiveButton(MainActivity.OK, (dialogInterface, i) -> {
                startActivity(IntentUtils.getLaunchAppDetailsSettingsIntent(getPackageName()));
            }).setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss()).show().setCanceledOnTouchOutside(false);
        }, 500);
    }

    public AlertDialog.Builder showAlertBox(String title, String message) {
        return new AlertDialog.Builder(this).setTitle(title).setMessage(message);
    }
}
