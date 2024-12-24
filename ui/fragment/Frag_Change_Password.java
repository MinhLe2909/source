package tamhoang.ldpro4.ui.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;
import tamhoang.ldpro4.ui.activity.Activity_Active;
import tamhoang.ldpro4.R;
import tamhoang.ldpro4.akaman.AkaManSec;
import tamhoang.ldpro4.data.Database;

/* loaded from: classes.dex */
public class Frag_Change_Password extends Fragment {
    ImageButton btn_about;
    TextView btn_active;
    private Button btn_save_pwd;
    private Button btn_save_truncate;
    private CheckBox chk_pwd_mode;
    private CheckBox chk_truncate;
    private Database db;
    private EditText password;
    TextInputLayout password_error;
    private EditText re_password;
    TextInputLayout re_password_error;
    private EditText re_truncate_password;
    TextInputLayout re_truncate_password_error;
    private SwitchCompat swOnOff;
    private EditText truncate_password;
    TextInputLayout truncate_password_error;

    private void enableControls() {
        if (AkaManSec.pwdMode == 1) {
            this.chk_pwd_mode.setChecked(true);
            this.password.setEnabled(true);
            this.re_password.setEnabled(true);
        } else {
            this.chk_pwd_mode.setChecked(false);
            this.password.setEnabled(false);
            this.re_password.setEnabled(false);
        }
        if (AkaManSec.useTruncate == 1) {
            this.truncate_password.setEnabled(true);
            this.re_truncate_password.setEnabled(true);
            this.chk_truncate.setEnabled(true);
            this.chk_truncate.setChecked(true);
            this.swOnOff.setChecked(true);
            return;
        }
        this.truncate_password.setEnabled(false);
        this.re_truncate_password.setEnabled(false);
        this.chk_truncate.setEnabled(false);
        this.chk_truncate.setChecked(false);
        this.swOnOff.setChecked(false);
    }

    private void initViews(View view) {
        this.password =  view.findViewById(R.id.password);
        this.re_password =  view.findViewById(R.id.re_password);
        this.truncate_password =  view.findViewById(R.id.truncate_password);
        this.re_truncate_password =  view.findViewById(R.id.re_truncate_password);
        this.chk_truncate = (CheckBox) view.findViewById(R.id.chk_truncate);
        this.chk_pwd_mode = (CheckBox) view.findViewById(R.id.chk_pwd_mode);
        this.truncate_password_error = (TextInputLayout) view.findViewById(R.id.truncate_password_error);
        this.re_truncate_password_error = (TextInputLayout) view.findViewById(R.id.re_truncate_password_error);
        this.password_error = (TextInputLayout) view.findViewById(R.id.password_error);
        this.re_password_error = (TextInputLayout) view.findViewById(R.id.re_password_error);
        this.swOnOff = (SwitchCompat) view.findViewById(R.id.swOnOff);
        this.btn_save_truncate =  view.findViewById(R.id.btn_save_truncate);
        this.btn_active =  view.findViewById(R.id.btn_active);
        this.btn_save_pwd =  view.findViewById(R.id.btn_save_pwd);
        ImageButton imageButton = (ImageButton) view.findViewById(R.id.btn_about);
        this.btn_about = imageButton;
        imageButton.setOnClickListener(new View.OnClickListener() { // from class: tamhoang.ldpro4.Fragment.Frag_Change_Password.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Frag_Change_Password.this.getActivity());
                builder.setMessage("Mật khẩu phải:\n- Ít nhất 8 ký tự\n- Ít nhất phải có 1 chữ thường\n- Ít nhất phải có 1 chữ in hoa\n- Ít nhất phải có một ký tự đặc biệt sau: @#$%^&+=\n- Không chứa khoảng trống\n").setCancelable(true);
                AlertDialog create = builder.create();
                create.setCanceledOnTouchOutside(true);
                create.show();
            }
        });
        this.chk_pwd_mode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: tamhoang.ldpro4.Fragment.Frag_Change_Password.2
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    Frag_Change_Password.this.password.setEnabled(true);
                    Frag_Change_Password.this.re_password.setEnabled(true);
                } else {
                    Frag_Change_Password.this.password.setEnabled(false);
                    Frag_Change_Password.this.re_password.setEnabled(false);
                }
            }
        });
        this.btn_save_truncate.setEnabled(false);
        this.swOnOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: tamhoang.ldpro4.Fragment.Frag_Change_Password.3
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                try {
                    if (b) {
                        Frag_Change_Password.this.truncate_password.setEnabled(true);
                        Frag_Change_Password.this.re_truncate_password.setEnabled(true);
                        Frag_Change_Password.this.btn_save_truncate.setEnabled(true);
                        Frag_Change_Password.this.chk_truncate.setEnabled(true);
                        if (AkaManSec.truncatePwd.equals("")) {
                            Toast.makeText(Frag_Change_Password.this.getActivity(), "Bạn chưa có mật khẩu, hãy nhập mật khẩu rồi lưu lại!", Toast.LENGTH_SHORT).show();
                        } else {
                            AkaManSec.useTruncate = 1;
                            AkaManSec.updateAkaManSec(Frag_Change_Password.this.db);
                            AkaManSec.queryAkaManPwd(Frag_Change_Password.this.db);
                        }
                    } else {
                        Frag_Change_Password.this.truncate_password.setEnabled(false);
                        Frag_Change_Password.this.re_truncate_password.setEnabled(false);
                        Frag_Change_Password.this.btn_save_truncate.setEnabled(false);
                        Frag_Change_Password.this.chk_truncate.setEnabled(false);
                        AkaManSec.useTruncate = 0;
                        AkaManSec.updateAkaManSec(Frag_Change_Password.this.db);
                        AkaManSec.queryAkaManPwd(Frag_Change_Password.this.db);
                        Toast.makeText(Frag_Change_Password.this.getActivity(), "Đã tắt tính năng này!", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        this.btn_active.setOnClickListener(new View.OnClickListener() { // from class: tamhoang.ldpro4.Fragment.Frag_Change_Password.4
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Intent intent = new Intent(Frag_Change_Password.this.getActivity(), (Class<?>) Activity_Active.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("mode", "re-active");
                Frag_Change_Password.this.startActivity(intent);
            }
        });
        this.btn_save_pwd.setOnClickListener(new View.OnClickListener() { // from class: tamhoang.ldpro4.Fragment.Frag_Change_Password.5
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                try {
                    if (Frag_Change_Password.this.chk_pwd_mode.isChecked()) {
                        if (!Frag_Change_Password.this.validatePassword2(Frag_Change_Password.this.password.getText().toString().trim())) {
                            Frag_Change_Password.this.password_error.setErrorEnabled(true);
                            Frag_Change_Password.this.password_error.setError("Mật khẩu phải lớn hơn 5 ký tự và không chứa khoảng trống!");
                            Frag_Change_Password.this.re_password_error.setErrorEnabled(true);
                            Frag_Change_Password.this.re_password_error.setError("Mật khẩu phải lớn hơn 5 ký tự và không chứa khoảng trống!");
                            Toast.makeText(Frag_Change_Password.this.getActivity(), "Mật khẩu phải lớn hơn 5 ký tự!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        String md5 = AkaManSec.md5(Frag_Change_Password.this.password.getText().toString().trim());
                        String md52 = AkaManSec.md5(Frag_Change_Password.this.re_password.getText().toString().trim());
                        if (!md52.equals("") && !md5.equals("")) {
                            if (!md52.equals(md5)) {
                                Frag_Change_Password.this.password_error.setErrorEnabled(true);
                                Frag_Change_Password.this.password_error.setError("\"Mật khẩu\" và \"Nhập lại mật khẩu\" không giống nhau!");
                                Frag_Change_Password.this.re_password_error.setErrorEnabled(true);
                                Frag_Change_Password.this.re_password_error.setError("\"Mật khẩu\" và \"Nhập lại mật khẩu\" không giống nhau!");
                                Toast.makeText(Frag_Change_Password.this.getActivity(), "\"Mật khẩu\" và \"Nhập lại mật khẩu\" không giống nhau!", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if (md5.equals(AkaManSec.truncatePwd)) {
                                Frag_Change_Password.this.password_error.setErrorEnabled(true);
                                Frag_Change_Password.this.password_error.setError("Mật khẩu này đã sử dụng, hãy dùng mật khẩu khác!");
                                Toast.makeText(Frag_Change_Password.this.getActivity(), "Mật khẩu này đã sử dụng, hãy dùng mật khẩu khác!", Toast.LENGTH_SHORT).show();
                                return;
                            } else {
                                Frag_Change_Password.this.password_error.setErrorEnabled(false);
                                Frag_Change_Password.this.password_error.setError("");
                                AkaManSec.pwdMode = 1;
                                AkaManSec.userPwd = md5;
                            }
                        }
                        Frag_Change_Password.this.password_error.setErrorEnabled(true);
                        Frag_Change_Password.this.password_error.setError("Mật khẩu phải lớn hơn 5 ký tự và không chứa khoảng trống!");
                        Frag_Change_Password.this.re_password_error.setErrorEnabled(true);
                        Frag_Change_Password.this.re_password_error.setError("Mật khẩu phải lớn hơn 5 ký tự và không chứa khoảng trống!");
                        Toast.makeText(Frag_Change_Password.this.getActivity(), "Mật khẩu phải lớn hơn 5 ký tự!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    AkaManSec.pwdMode = 0;
                    AkaManSec.updateAkaManSec(Frag_Change_Password.this.db);
                    AkaManSec.queryAkaManPwd(Frag_Change_Password.this.db);
                    Toast.makeText(Frag_Change_Password.this.getActivity(), "Đã lưu cài đặt!", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        this.btn_save_truncate.setOnClickListener(new View.OnClickListener() { // from class: tamhoang.ldpro4.Fragment.Frag_Change_Password.6
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                try {
                    if (!Frag_Change_Password.this.validatePassword2(Frag_Change_Password.this.truncate_password.getText().toString().trim())) {
                        Frag_Change_Password.this.truncate_password_error.setErrorEnabled(true);
                        Frag_Change_Password.this.truncate_password_error.setError("Mật khẩu phải lớn hơn 5 ký tự và không chứa khoảng trống!");
                        Frag_Change_Password.this.re_truncate_password_error.setErrorEnabled(true);
                        Frag_Change_Password.this.re_truncate_password_error.setError("Mật khẩu phải lớn hơn 5 ký tự và không chứa khoảng trống!");
                        Toast.makeText(Frag_Change_Password.this.getActivity(), "Mật khẩu phải lớn hơn 5 ký tự!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String md5 = AkaManSec.md5(Frag_Change_Password.this.truncate_password.getText().toString().trim());
                    String md52 = AkaManSec.md5(Frag_Change_Password.this.re_truncate_password.getText().toString().trim());
                    if (!md52.equals("") && !md5.equals("") && md52.equals(md5)) {
                        if (!md52.equals(md5)) {
                            Frag_Change_Password.this.truncate_password_error.setErrorEnabled(true);
                            Frag_Change_Password.this.truncate_password_error.setError("\"Mật khẩu\" và \"Nhập lại mật khẩu\" không giống nhau!");
                            Frag_Change_Password.this.re_truncate_password_error.setErrorEnabled(true);
                            Frag_Change_Password.this.re_truncate_password_error.setError("\"Mật khẩu\" và \"Nhập lại mật khẩu\" không giống nhau!");
                            Toast.makeText(Frag_Change_Password.this.getActivity(), "\"Mật khẩu\" và \"Nhập lại mật khẩu\" không giống nhau!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (md5.equals(AkaManSec.userPwd)) {
                            Frag_Change_Password.this.truncate_password_error.setErrorEnabled(true);
                            Frag_Change_Password.this.truncate_password_error.setError("Mật khẩu này đã sử dụng, hãy dùng mật khẩu khác!");
                            Toast.makeText(Frag_Change_Password.this.getActivity(), "Mật khẩu này đã sử dụng, hãy dùng mật khẩu khác!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (Frag_Change_Password.this.chk_truncate.isChecked()) {
                            AkaManSec.truncateMode = 1;
                        } else {
                            AkaManSec.truncateMode = 0;
                        }
                        Frag_Change_Password.this.truncate_password_error.setErrorEnabled(false);
                        Frag_Change_Password.this.truncate_password_error.setError("");
                        AkaManSec.useTruncate = 1;
                        AkaManSec.truncatePwd = md5;
                        AkaManSec.updateAkaManSec(Frag_Change_Password.this.db);
                        AkaManSec.queryAkaManPwd(Frag_Change_Password.this.db);
                        Toast.makeText(Frag_Change_Password.this.getActivity(), "Đã lưu mật khẩu!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Frag_Change_Password.this.truncate_password_error.setErrorEnabled(true);
                    Frag_Change_Password.this.truncate_password_error.setError("Mật khẩu phải lớn hơn 5 ký tự và không chứa khoảng trống!");
                    Frag_Change_Password.this.re_truncate_password_error.setErrorEnabled(true);
                    Frag_Change_Password.this.re_truncate_password_error.setError("Mật khẩu phải lớn hơn 5 ký tự và không chứa khoảng trống!");
                    Toast.makeText(Frag_Change_Password.this.getActivity(), "Mật khẩu phải lớn hơn 5 ký tự và không chứa khoảng trống!", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override // android.support.v4.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.frag_change_password, container, false);
        this.db = new Database(getActivity());
        initViews(inflate);
        enableControls();
        return inflate;
    }

    @Override // android.support.v4.app.Fragment
    public void onResume() {
        super.onResume();
        enableControls();
    }

    public boolean validatePassword2(String passwd) {
        return !passwd.trim().contains(" ") && passwd.trim().length() >= 6;
    }
}
