package tamhoang.ldpro4.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import com.google.android.material.textfield.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.channels.FileChannel;
import java.util.Objects;

import tamhoang.ldpro4.ui.activity.KetQuaActivity;
import tamhoang.ldpro4.ui.home.LoginActivity;
import tamhoang.ldpro4.ui.home.MainActivity;
import tamhoang.ldpro4.R;
import tamhoang.ldpro4.akaman.AkaManSec;
import tamhoang.ldpro4.akaman.InputStreamVolleyRequest;
import tamhoang.ldpro4.data.Database;

/* loaded from: classes.dex */
public class Activity_Active extends AppCompatActivity {
    private static final int PICKFILE_REQUEST_CODE = 10102;
    Button active;
    TextView btn_active;
    Database db;
    Button downloadFile;
    boolean isPasswordValid;
    boolean iskeyPathValid;
    EditText keyContent;
    TextInputLayout keyPathError;
    LinearLayout keyPathlayout;
    Button login;
    TextInputLayout passError;
    EditText password;
    Button pickFile;
    TextView txtImei;

    /* JADX INFO: Access modifiers changed from: private */
    public void activate() {
        try {
            String readKeyFile = AkaManSec.readKeyFile();
            String[] split = AkaManSec.getAkaManSec(readKeyFile).split(AkaManSec.separator);
            AkaManSec.akaMainURL = split[split.length - 1];
            AkaManSec.akaMainIMEI = split[0];
            if (!AkaManSec.akaMainIMEI.equals(LoginActivity.imei)) {
                this.keyPathError.setErrorEnabled(true);
                this.keyPathError.setError(getResources().getString(R.string.keyPathError));
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.keyPathError), Toast.LENGTH_SHORT).show();
                this.iskeyPathValid = false;
                return;
            }
            AkaManSec.saveAkaManSec(readKeyFile, this.db);
            AkaManSec.queryAkaManPwd(this.db);
            this.iskeyPathValid = true;
            this.keyPathError.setErrorEnabled(false);
            Toast.makeText(getApplicationContext(), "Đã kích hoạt!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, (Class<?>) MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            this.keyPathError.setErrorEnabled(true);
            this.keyPathError.setError(getResources().getString(R.string.keyPathError));
            this.iskeyPathValid = false;
        }
    }

    private void writeKeyFile(String fileEncriptContent) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(AkaManSec.getAkaMainPath(), false), "UTF-8"));
        try {
            bufferedWriter.append(fileEncriptContent);
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
        }
    }

    public void SetValidation(String mode) {
        if (!"active".equals(mode)) {
            if (!this.password.getText().toString().isEmpty()) {
                this.isPasswordValid = true;
                this.passError.setErrorEnabled(false);
                return;
            } else {
                this.passError.setErrorEnabled(true);
                this.passError.setError(getResources().getString(R.string.password_error));
                this.isPasswordValid = false;
                return;
            }
        }
        try {
            String trim = this.keyContent.getText().toString().trim();
            if (!trim.equals("")) {
                writeKeyFile(trim);
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.keyPathError.setErrorEnabled(true);
            this.keyPathError.setError("Mã kích hoạt không hợp lệ.");
            Toast.makeText(getApplicationContext(), "Mã kích hoạt không hợp lệ.", Toast.LENGTH_SHORT).show();
            this.iskeyPathValid = false;
        }
        File file = new File(AkaManSec.getAkaMainPath());
        if (this.keyContent.getText().toString().trim().isEmpty()) {
            this.keyPathError.setErrorEnabled(true);
            this.keyPathError.setError("Mã kích hoạt không hợp lệ.");
            Toast.makeText(getApplicationContext(), "Mã kích hoạt không hợp lệ.", Toast.LENGTH_SHORT).show();
            this.iskeyPathValid = false;
            return;
        }
        if (!file.exists() || !file.isFile()) {
            this.keyPathError.setErrorEnabled(true);
            this.keyPathError.setError("Mã kích hoạt không hợp lệ.");
            Toast.makeText(getApplicationContext(), "Mã kích hoạt không hợp lệ.", Toast.LENGTH_SHORT).show();
            this.iskeyPathValid = false;
            return;
        }
        try {
            activate();
        } catch (Exception unused) {
            this.keyPathError.setErrorEnabled(true);
            this.keyPathError.setError("Mã kích hoạt không hợp lệ.");
            Toast.makeText(getApplicationContext(), "Mã kích hoạt không hợp lệ.", Toast.LENGTH_SHORT).show();
            this.iskeyPathValid = false;
        }
    }

    public void copyFile(File sourceFile, File destFile) throws IOException {
        FileChannel fileChannel;
        FileChannel channel;
        if (!destFile.getParentFile().exists()) {
            destFile.getParentFile().mkdirs();
        }
        if (!destFile.exists()) {
            destFile.createNewFile();
        }
        channel = new FileInputStream(sourceFile).getChannel();
        try {
            fileChannel = new FileOutputStream(destFile).getChannel();
            fileChannel.transferFrom(channel, 0L, channel.size());
            if (channel != null) {
                channel.close();
            }
            if (fileChannel != null) {
                fileChannel.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void copyFileOrDirectory(String srcDir, String dstDir) {
        try {
            File file = new File(srcDir);
            File file2 = new File(dstDir, "akatmp");
            if (!file.isDirectory()) {
                copyFile(file, file2);
                return;
            }
            for (String str : file.list()) {
                copyFileOrDirectory(new File(file, str).getPath(), file2.getPath());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int i, int i2, Intent intent) {
        String readLine;
        super.onActivityResult(i, i2, intent);
        if (i != PICKFILE_REQUEST_CODE || i2 != -1) {
            return;
        }
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(getContentResolver().openInputStream(Objects.requireNonNull(intent.getData())))));
            StringBuilder sb = new StringBuilder();
            while (true) {
                readLine = bufferedReader.readLine();
                if (readLine == null) {
                    break;
                } else {
                    sb.append(readLine);
                }
            }
            writeKeyFile(sb.toString());
            activate();
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
            this.keyPathError.setErrorEnabled(true);
            this.keyPathError.setError(getResources().getString(R.string.keyPathError));
            this.iskeyPathValid = false;
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active);
        this.db = new Database(this);
        String stringExtra = getIntent().getStringExtra("mode");
        this.keyContent =  findViewById(R.id.key_content);
        this.password =  findViewById(R.id.password);
        this.txtImei =  findViewById(R.id.txt_imei);
        this.login =  findViewById(R.id.login);
        this.active =  findViewById(R.id.active);
        this.pickFile =  findViewById(R.id.pick_file);
        this.downloadFile =  findViewById(R.id.download_file);
        this.keyPathError = (TextInputLayout) findViewById(R.id.keyPathError);
        this.passError = (TextInputLayout) findViewById(R.id.passError);
        this.keyPathlayout =  findViewById(R.id.keyPathlayout);
        this.btn_active =  findViewById(R.id.btn_active);
        this.login.setOnClickListener(new View.OnClickListener() { // from class: tamhoang.ldpro4.Activity.Activity_Active.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                SetValidation("pwd");
                if (isPasswordValid) {
                    AkaManSec.queryAkaManPwd(db);
                    String md5 = AkaManSec.md5(password.getText().toString().trim());
                    if (md5.equals(AkaManSec.userPwd) || md5.equals(AkaManSec.resetPwd)) {
                        Intent intent = new Intent(Activity_Active.this, (Class<?>) MainActivity.class);
                        intent.putExtra("mode", "logon");
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                        return;
                    }
                    if (!md5.equals(AkaManSec.truncatePwd)) {
                        passError.setErrorEnabled(true);
                        passError.setError(getResources().getString(R.string.password_error));
                        isPasswordValid = false;
                        return;
                    }
                    if (AkaManSec.truncateMode == 1) {
                        try {
                            copyFileOrDirectory(Environment.getExternalStorageDirectory() + File.separator + "/LdWolf", Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/tmp");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        db.QueryData("DELETE FROM tbl_tinnhanS");
                        db.QueryData("DELETE FROM tbl_soctS");
                        db.QueryData("DELETE FROM tbl_kh_new");
                        db.QueryData("DELETE FROM tbl_chuyenthang");
                        db.QueryData("DELETE FROM tbl_chaytrang_ticket");
                        db.QueryData("DELETE FROM tbl_chaytrang_acc");
                        db.QueryData("DELETE FROM So_om");
                        db.QueryData("DELETE FROM KetQua");
                        db.QueryData("DELETE FROM Chat_database");
                    }
                    Intent intent2 = new Intent(Activity_Active.this, (Class<?>) KetQuaActivity.class);
                    intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent2);
                    finish();
                }
            }
        });
        this.btn_active.setOnClickListener(new View.OnClickListener() { // from class: tamhoang.ldpro4.Activity.Activity_Active.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Intent intent = new Intent(Activity_Active.this, (Class<?>) Activity_Active.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("mode", "re-active");
                startActivity(intent);
            }
        });
        this.active.setOnClickListener(new View.OnClickListener() { // from class: tamhoang.ldpro4.Activity.Activity_Active.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                String trim = keyContent.getText().toString().trim();
                if (!trim.contains("http://") && !trim.contains("https://")) {
                    SetValidation("active");
                    return;
                }
                keyPathError.setErrorEnabled(true);
                keyPathError.setError("Đây là đường dẫn http, hãy dùng tính năng tải tệp kích hoạt.");
                Toast.makeText(getApplicationContext(), "Đây là đường dẫn http, hãy dùng tính năng tải tệp kích hoạt.", Toast.LENGTH_SHORT).show();
            }
        });
        this.pickFile.setOnClickListener(new View.OnClickListener() { // from class: tamhoang.ldpro4.Activity.Activity_Active.4
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.GET_CONTENT");
                intent.addCategory("android.intent.category.OPENABLE");
                intent.setType("*/*");
                startActivityForResult(Intent.createChooser(intent, "Chọn tệp tin kích hoạt!"), Activity_Active.PICKFILE_REQUEST_CODE);
            }
        });
        // from class: tamhoang.ldpro4.Activity.Activity_Active.5
// android.view.View.OnClickListener
        this.downloadFile.setOnClickListener(v -> {
            String trim = keyContent.getText().toString().trim();
            if (!trim.contains("http://") && !trim.contains("https://")) {
                keyPathError.setErrorEnabled(true);
                keyPathError.setError("Đây không phải là đường dẫn http, liên  hệ đại lý để được hỗ trợ.");
                Toast.makeText(getApplicationContext(), "Đây không phải là đường dẫn http, liên  hệ đại lý để được hỗ trợ.", Toast.LENGTH_SHORT).show();
            }
            // from class: tamhoang.ldpro4.Activity.Activity_Active.5.2
// com.android.volley.Response.ErrorListener
            Volley.newRequestQueue(getApplicationContext(), new HurlStack()).add(new InputStreamVolleyRequest(0, trim, new Response.Listener<byte[]>() { // from class: tamhoang.ldpro4.Activity.Activity_Active.5.1
                @Override
                public void onResponse(byte[] response) {
                    if (response != null) {
                        FileOutputStream fileOutputStream = null;
                        try {
                            fileOutputStream = new FileOutputStream(AkaManSec.getAkaMainPath(), false);
                            fileOutputStream.write(response);
                            keyPathError.setErrorEnabled(false);
                            Toast.makeText(Activity_Active.this, "Tải tệp thành công", Toast.LENGTH_LONG).show();
                            activate();
                            if (fileOutputStream != null) {
                                fileOutputStream.close();
                            }
                        } catch (Throwable th2) {
                            keyPathError.setErrorEnabled(true);
                            keyPathError.setError("Xẩy ra lỗi khi tải tệp key, liên  hệ đại lý để được hỗ trợ.");
                            Toast.makeText(getApplicationContext(), "Xẩy ra lỗi khi tải tệp key, liên  hệ đại lý để được hỗ trợ.", Toast.LENGTH_SHORT).show();
                            th2.printStackTrace();
                        }
                    }
                }
            }, error -> {
                error.printStackTrace();
                keyPathError.setErrorEnabled(true);
                keyPathError.setError("Xẩy ra lỗi khi tải tệp key, liên  hệ đại lý để được hỗ trợ.");
                Toast.makeText(getApplicationContext(), "Xẩy ra lỗi khi tải tệp key, liên  hệ đại lý để được hỗ trợ.", Toast.LENGTH_SHORT).show();
            }, null));
        });
        if (stringExtra.equals("active")) {
            this.login.setVisibility(View.GONE);
            this.password.setVisibility(View.GONE);
            this.passError.setVisibility(View.GONE);
            this.btn_active.setVisibility(View.GONE);
            this.keyContent.setVisibility(View.VISIBLE);
            this.active.setVisibility(View.VISIBLE);
            this.keyPathError.setVisibility(View.VISIBLE);
            this.keyPathlayout.setVisibility(View.VISIBLE);
        } else if (stringExtra.equals("re-active")) {
            this.login.setVisibility(View.GONE);
            this.password.setVisibility(View.GONE);
            this.passError.setVisibility(View.GONE);
            this.btn_active.setVisibility(View.GONE);
            this.keyContent.setVisibility(View.VISIBLE);
            this.active.setVisibility(View.VISIBLE);
            this.keyPathError.setVisibility(View.VISIBLE);
            this.keyPathlayout.setVisibility(View.VISIBLE);
        } else {
            this.keyContent.setVisibility(View.GONE);
            this.active.setVisibility(View.GONE);
            this.keyPathError.setVisibility(View.GONE);
            this.keyPathlayout.setVisibility(View.GONE);
            this.login.setVisibility(View.VISIBLE);
            this.password.setVisibility(View.VISIBLE);
            this.passError.setVisibility(View.VISIBLE);
            this.btn_active.setVisibility(View.VISIBLE);
        }
        this.txtImei.setText("IMEI: " + LoginActivity.imei);
    }
}
