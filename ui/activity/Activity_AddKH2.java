package tamhoang.ldpro4.ui.activity;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.util.Calendar;
import org.json.JSONException;
import org.json.JSONObject;
import tamhoang.ldpro4.base.BaseToolBarActivity;
import tamhoang.ldpro4.formula.Congthuc;
import tamhoang.ldpro4.R;
import tamhoang.ldpro4.data.Database;


public class Activity_AddKH2 extends BaseToolBarActivity {
    String[] baoloi_donvi;
    SeekBar bc_dly;
    SeekBar bc_khach;
    Button btn_all_khong;
    Button btn_exit;
    JSONObject caidat_tg;
    String[] chot_sodu;
    Database db;
    SeekBar de_dly;
    SeekBar de_khach;
    String[] dv_nhanXien;
    TextView edt_ten;
    String[] heso_de;
    JSONObject json;
    JSONObject json_KhongMax;
    String[] khach_de;
    LinearLayout li_nhanxien;
    SeekBar lo_dly;
    SeekBar lo_khach;
    String message;
    TextView pt_giu_bc_dly;
    TextView pt_giu_bc_khach;
    TextView pt_giu_de_dly;
    TextView pt_giu_de_khach;
    TextView pt_giu_lo_dly;
    TextView pt_giu_lo_khach;
    TextView pt_giu_xi_dly;
    TextView pt_giu_xi_khach;
    Spinner sp_Chot_sodu;
    Spinner sp_baoloidonvi;
    Spinner sp_hesode;
    Spinner sp_khachde;
    Spinner sp_nhanXien;
    Spinner sp_traloitn;
    String[] tl_tinnhan;
    TextView tv_KhongMax;
    TextView tv_Lo_xien;
    TextView tv_de_cang;
    SeekBar xi_dly;
    SeekBar xi_khach;

    public void UPdate() {
        String str;
        String str2;
        try {
            if (this.json_KhongMax.getString("danDe").length() == 0) {
                str = "  Đề: Không khống";
            } else {
                str = "  Đề: " + this.json_KhongMax.getString("danDe");
            }
            if (this.json_KhongMax.getString("danLo").length() == 0) {
                str2 = str + "\n  Lô: Không khống";
            } else {
                str2 = str + "\n  Lô: " + this.json_KhongMax.getString("danLo");
            }
            this.tv_KhongMax.setText(str2 + "\n  Xiên 2: " + this.json_KhongMax.getString("xien2") + "\n  Xiên 3: " + this.json_KhongMax.getString("xien3") + "\n  Xiên 4: " + this.json_KhongMax.getString("xien4") + "\n  Càng: " + this.json_KhongMax.getString("cang"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_kh2;
    }

    public void init() {
        this.edt_ten =  findViewById(R.id.edt_ten);
        this.btn_exit =  findViewById(R.id.btn_exit_KH2);
        this.pt_giu_de_khach =  findViewById(R.id.pt_giu_de_khach);
        this.pt_giu_lo_khach =  findViewById(R.id.pt_giu_lo_khach);
        this.pt_giu_xi_khach =  findViewById(R.id.pt_giu_xi_khach);
        this.pt_giu_bc_khach =  findViewById(R.id.pt_giu_bc_khach);
        this.pt_giu_de_dly =  findViewById(R.id.pt_giu_de_dly);
        this.pt_giu_lo_dly =  findViewById(R.id.pt_giu_lo_dly);
        this.pt_giu_xi_dly =  findViewById(R.id.pt_giu_xi_dly);
        this.pt_giu_bc_dly =  findViewById(R.id.pt_giu_bc_dly);
        this.de_khach = (SeekBar) findViewById(R.id.seek_GiuDekhach);
        this.lo_khach = (SeekBar) findViewById(R.id.seek_GiuLokhach);
        this.xi_khach = (SeekBar) findViewById(R.id.seek_GiuXikhach);
        this.bc_khach = (SeekBar) findViewById(R.id.seek_Giu3ckhach);
        this.de_dly = (SeekBar) findViewById(R.id.seek_GiuDedly);
        this.lo_dly = (SeekBar) findViewById(R.id.seek_GiuLodly);
        this.xi_dly = (SeekBar) findViewById(R.id.seek_GiuXidly);
        this.bc_dly = (SeekBar) findViewById(R.id.seek_Giu3cdly);
        this.sp_traloitn = (Spinner) findViewById(R.id.sp_traloitn);
        this.sp_nhanXien = (Spinner) findViewById(R.id.sp_nhanXien);
        this.sp_Chot_sodu = (Spinner) findViewById(R.id.sp_Chot_sodu);
        this.sp_hesode = (Spinner) findViewById(R.id.sp_hesode);
        this.sp_baoloidonvi = (Spinner) findViewById(R.id.sp_baoloidonvi);
        this.sp_khachde = (Spinner) findViewById(R.id.sp_khachde);
        this.li_nhanxien =  findViewById(R.id.ln_nhanXien);
        this.tv_Lo_xien =  findViewById(R.id.tv_Lo_xien);
        this.tv_de_cang =  findViewById(R.id.tv_de_cang);
        this.tv_KhongMax =  findViewById(R.id.tv_KhongMax);
        this.btn_all_khong =  findViewById(R.id.btn_all_khong);
    }

    @Override // tamhoang.ldpro4.Congthuc.BaseToolBarActivity, android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity, android.support.v4.app.SupportActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        String str;
        String str2;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_kh2);
        this.db = new Database(this);
        init();
        String stringExtra = getIntent().getStringExtra("tenKH");
        this.message = stringExtra;
        if (stringExtra.length() > 0) {
            this.edt_ten.setText(this.message);
            this.tl_tinnhan = new String[]{"1. Ok tin và nd phân tích", "2. Chỉ ok tin", "3. Không trả lời", "4. Ok tin nguyên mẫu", "5. Chỉ ok tin (ngay khi nhận)", "6. OK nguyên mẫu (ngay khi nhận)"};
            this.sp_traloitn.setAdapter((SpinnerAdapter) new ArrayAdapter(this, R.layout.spinner_item, this.tl_tinnhan));
            this.dv_nhanXien = new String[]{"1. Giữ nguyên giá", "2. Nhân 10 khi là điểm", "3. Nhân 10 tất cả xiên"};
            this.sp_nhanXien.setAdapter((SpinnerAdapter) new ArrayAdapter(this, R.layout.spinner_item, this.dv_nhanXien));
            this.chot_sodu = new String[]{"1. Chốt tiền trong ngày", "2. Chốt có công nợ"};
            this.sp_Chot_sodu.setAdapter((SpinnerAdapter) new ArrayAdapter(this, R.layout.spinner_item, this.chot_sodu));
            this.baoloi_donvi = new String[]{"1. Ko báo lỗi sai đơn vị", "2. Báo lỗi khi sai đơn vị"};
            this.sp_baoloidonvi.setAdapter((SpinnerAdapter) new ArrayAdapter(this, R.layout.spinner_item, this.baoloi_donvi));
            this.khach_de = new String[]{"1. Thường (de = deb, de8 = det)", "2. Đề 8 (de = det)"};
            this.sp_khachde.setAdapter((SpinnerAdapter) new ArrayAdapter(this, R.layout.spinner_item, this.khach_de));
            this.heso_de = new String[]{"1. Giữ nguyên (HS=1)", "2. Đề 8->7 (HS=1,143)", "3. Đề 7->8 (HS=0,875)"};
            this.sp_hesode.setAdapter((SpinnerAdapter) new ArrayAdapter(this, R.layout.spinner_item, this.heso_de));
            Cursor GetData = this.db.GetData("Select * From tbl_kh_new WHERE ten_kh ='" + this.message + "'");
            GetData.moveToFirst();
            try {
                JSONObject jSONObject = new JSONObject(GetData.getString(5));
                this.json = jSONObject;
                JSONObject jSONObject2 = jSONObject.getJSONObject("caidat_tg");
                this.caidat_tg = jSONObject2;
                this.sp_traloitn.setSelection(jSONObject2.getInt("ok_tin"));
                this.sp_nhanXien.setSelection(this.caidat_tg.getInt("xien_nhan"));
                this.sp_Chot_sodu.setSelection(this.caidat_tg.getInt("chot_sodu"));
                this.tv_Lo_xien.setText(this.caidat_tg.getString("tg_loxien"));
                this.tv_de_cang.setText(this.caidat_tg.getString("tg_debc"));
                this.sp_hesode.setSelection(this.caidat_tg.getInt("heso_de"));
                try {
                    this.sp_khachde.setSelection(this.caidat_tg.getInt("khach_de"));
                } catch (JSONException unused) {
                    this.caidat_tg.put("khach_de", 0);
                }
                this.sp_baoloidonvi.setSelection(this.caidat_tg.getInt("loi_donvi"));
                this.pt_giu_de_dly.setText(this.caidat_tg.getInt("dlgiu_de") + "%");
                this.pt_giu_lo_dly.setText(this.caidat_tg.getInt("dlgiu_lo") + "%");
                this.pt_giu_xi_dly.setText(this.caidat_tg.getInt("dlgiu_xi") + "%");
                this.pt_giu_bc_dly.setText(this.caidat_tg.getInt("dlgiu_bc") + "%");
                this.de_dly.setProgress(this.caidat_tg.getInt("dlgiu_de") / 5);
                this.lo_dly.setProgress(this.caidat_tg.getInt("dlgiu_lo") / 5);
                this.xi_dly.setProgress(this.caidat_tg.getInt("dlgiu_xi") / 5);
                this.bc_dly.setProgress(this.caidat_tg.getInt("dlgiu_bc") / 5);
                this.pt_giu_de_khach.setText(this.caidat_tg.getInt("khgiu_de") + "%");
                this.pt_giu_lo_khach.setText(this.caidat_tg.getInt("khgiu_lo") + "%");
                this.pt_giu_xi_khach.setText(this.caidat_tg.getInt("khgiu_xi") + "%");
                this.pt_giu_bc_khach.setText(this.caidat_tg.getInt("khgiu_bc") + "%");
                this.de_khach.setProgress(this.caidat_tg.getInt("khgiu_de") / 5);
                this.lo_khach.setProgress(this.caidat_tg.getInt("khgiu_lo") / 5);
                this.xi_khach.setProgress(this.caidat_tg.getInt("khgiu_xi") / 5);
                this.bc_khach.setProgress(this.caidat_tg.getInt("khgiu_bc") / 5);
                JSONObject jSONObject3 = new JSONObject(GetData.getString(6));
                this.json_KhongMax = jSONObject3;
                if (jSONObject3.getString("danDe").length() == 0) {
                    str = "  Đề: Không khống";
                } else {
                    str = "  Đề: " + this.json_KhongMax.getString("danDe");
                }
                if (this.json_KhongMax.getString("danLo").length() == 0) {
                    str2 = str + "\n  Lô: Không khống";
                } else {
                    str2 = str + "\n  Lô: " + this.json_KhongMax.getString("danLo");
                }
                this.tv_KhongMax.setText(str2 + "\n  Xiên 2: " + this.json_KhongMax.getString("xien2") + "\n  Xiên 3: " + this.json_KhongMax.getString("xien3") + "\n  Xiên 4: " + this.json_KhongMax.getString("xien4") + "\n  Càng: " + this.json_KhongMax.getString("cang"));
                if (GetData != null && !GetData.isClosed()) {
                    GetData.close();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        this.sp_traloitn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: tamhoang.ldpro4.Activity.Activity_AddKH2.1
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    Activity_AddKH2.this.caidat_tg.put("ok_tin", i);
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        this.sp_nhanXien.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: tamhoang.ldpro4.Activity.Activity_AddKH2.2
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    Activity_AddKH2.this.caidat_tg.put("xien_nhan", i);
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        this.sp_Chot_sodu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: tamhoang.ldpro4.Activity.Activity_AddKH2.3
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    Activity_AddKH2.this.caidat_tg.put("chot_sodu", i);
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        this.sp_khachde.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: tamhoang.ldpro4.Activity.Activity_AddKH2.4
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    Activity_AddKH2.this.caidat_tg.put("khach_de", i);
                } catch (JSONException unused2) {
                }
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        this.sp_hesode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: tamhoang.ldpro4.Activity.Activity_AddKH2.5
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    Activity_AddKH2.this.caidat_tg.put("heso_de", i);
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        this.sp_baoloidonvi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: tamhoang.ldpro4.Activity.Activity_AddKH2.6
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    Activity_AddKH2.this.caidat_tg.put("loi_donvi", i);
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        this.btn_exit.setOnClickListener(new View.OnClickListener() { // from class: tamhoang.ldpro4.Activity.Activity_AddKH2.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                try {
                    Activity_AddKH2.this.json.put("caidat_tg", Activity_AddKH2.this.caidat_tg);
                    Activity_AddKH2.this.db.QueryData("update tbl_kh_new set tbl_MB = '" + Activity_AddKH2.this.json.toString() + "', tbl_XS = '" + Activity_AddKH2.this.json_KhongMax.toString() + "' WHERE ten_kh = '" + Activity_AddKH2.this.message + "'");
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
                Activity_AddKH2.this.finish();
            }
        });
        this.de_khach.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: tamhoang.ldpro4.Activity.Activity_AddKH2.8
            int max;

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                TextView textView = Activity_AddKH2.this.pt_giu_de_khach;
                StringBuilder sb = new StringBuilder();
                int i = progress * 5;
                sb.append(i);
                sb.append("%");
                textView.setText(sb.toString());
                this.max = i;
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                try {
                    Activity_AddKH2.this.caidat_tg.put("khgiu_de", this.max);
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
                Toast.makeText(Activity_AddKH2.this, "Giữ cho khách " + this.max + "%", Toast.LENGTH_LONG).show();
            }
        });
        this.lo_khach.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: tamhoang.ldpro4.Activity.Activity_AddKH2.9
            int max;

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                TextView textView = Activity_AddKH2.this.pt_giu_lo_khach;
                StringBuilder sb = new StringBuilder();
                int i = progress * 5;
                sb.append(i);
                sb.append("%");
                textView.setText(sb.toString());
                this.max = i;
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                try {
                    Activity_AddKH2.this.caidat_tg.put("khgiu_lo", this.max);
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
                Toast.makeText(Activity_AddKH2.this, "Giữ cho khách " + this.max + "%", Toast.LENGTH_LONG).show();
            }
        });
        this.xi_khach.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: tamhoang.ldpro4.Activity.Activity_AddKH2.10
            int max;

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                TextView textView = Activity_AddKH2.this.pt_giu_xi_khach;
                StringBuilder sb = new StringBuilder();
                int i = progress * 5;
                sb.append(i);
                sb.append("%");
                textView.setText(sb.toString());
                this.max = i;
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                try {
                    Activity_AddKH2.this.caidat_tg.put("khgiu_xi", this.max);
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
                Toast.makeText(Activity_AddKH2.this, "Giữ cho khách " + this.max + "%", Toast.LENGTH_LONG).show();
            }
        });
        this.bc_khach.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: tamhoang.ldpro4.Activity.Activity_AddKH2.11
            int max;

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                TextView textView = Activity_AddKH2.this.pt_giu_bc_khach;
                StringBuilder sb = new StringBuilder();
                int i = progress * 5;
                sb.append(i);
                sb.append("%");
                textView.setText(sb.toString());
                this.max = i;
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                try {
                    Activity_AddKH2.this.caidat_tg.put("khgiu_bc", this.max);
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
                Toast.makeText(Activity_AddKH2.this, "Giữ cho khách " + this.max + "%", Toast.LENGTH_LONG).show();
            }
        });
        this.de_dly.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: tamhoang.ldpro4.Activity.Activity_AddKH2.12
            int max;

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                TextView textView = Activity_AddKH2.this.pt_giu_de_dly;
                StringBuilder sb = new StringBuilder();
                int i = progress * 5;
                sb.append(i);
                sb.append("%");
                textView.setText(sb.toString());
                this.max = i;
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                try {
                    Activity_AddKH2.this.caidat_tg.put("dlgiu_de", this.max);
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
                Toast.makeText(Activity_AddKH2.this, "Mình giữ " + this.max + "%", Toast.LENGTH_LONG).show();
            }
        });
        this.lo_dly.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: tamhoang.ldpro4.Activity.Activity_AddKH2.13
            int max;

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                TextView textView = Activity_AddKH2.this.pt_giu_lo_dly;
                StringBuilder sb = new StringBuilder();
                int i = progress * 5;
                sb.append(i);
                sb.append("%");
                textView.setText(sb.toString());
                this.max = i;
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                try {
                    Activity_AddKH2.this.caidat_tg.put("dlgiu_lo", this.max);
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
                Toast.makeText(Activity_AddKH2.this, "Mình giữ " + this.max + "%", Toast.LENGTH_LONG).show();
            }
        });
        this.xi_dly.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: tamhoang.ldpro4.Activity.Activity_AddKH2.14
            int max;

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                TextView textView = Activity_AddKH2.this.pt_giu_xi_dly;
                StringBuilder sb = new StringBuilder();
                int i = progress * 5;
                sb.append(i);
                sb.append("%");
                textView.setText(sb.toString());
                this.max = i;
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                try {
                    Activity_AddKH2.this.caidat_tg.put("dlgiu_xi", this.max);
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
                Toast.makeText(Activity_AddKH2.this, "Mình giữ " + this.max + "%", Toast.LENGTH_LONG).show();
            }
        });
        this.bc_dly.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: tamhoang.ldpro4.Activity.Activity_AddKH2.15
            int max;

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                TextView textView = Activity_AddKH2.this.pt_giu_bc_dly;
                StringBuilder sb = new StringBuilder();
                int i = progress * 5;
                sb.append(i);
                sb.append("%");
                textView.setText(sb.toString());
                this.max = i;
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                try {
                    Activity_AddKH2.this.caidat_tg.put("dlgiu_bc", this.max);
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
                Toast.makeText(Activity_AddKH2.this, "Mình giữ " + this.max + "%", Toast.LENGTH_LONG).show();
            }
        });
        this.tv_Lo_xien.setOnClickListener(new View.OnClickListener() { // from class: tamhoang.ldpro4.Activity.Activity_AddKH2.16
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                TimePickerDialog timePickerDialog = new TimePickerDialog(Activity_AddKH2.this, new TimePickerDialog.OnTimeSetListener() { // from class: tamhoang.ldpro4.Activity.Activity_AddKH2.16.1
                    @Override // android.app.TimePickerDialog.OnTimeSetListener
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        if (selectedMinute < 10) {
                            Activity_AddKH2.this.tv_Lo_xien.setText(selectedHour + ":0" + selectedMinute);
                            Toast.makeText(Activity_AddKH2.this, "Đặt không nhận lô, xiên sau: " + selectedHour + ":0" + selectedMinute, Toast.LENGTH_LONG).show();
                        } else {
                            Activity_AddKH2.this.tv_Lo_xien.setText(selectedHour + ":" + selectedMinute);
                            Toast.makeText(Activity_AddKH2.this, "Đặt không nhận lô, xiên sau: " + selectedHour + ":" + selectedMinute, Toast.LENGTH_LONG).show();
                        }
                        try {
                            Activity_AddKH2.this.caidat_tg.put("tg_loxien", Activity_AddKH2.this.tv_Lo_xien.getText().toString());
                        } catch (JSONException e2) {
                            e2.printStackTrace();
                        }
                    }
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
                timePickerDialog.setTitle("Select Time");
                timePickerDialog.show();
            }
        });
        this.tv_de_cang.setOnClickListener(new View.OnClickListener() { // from class: tamhoang.ldpro4.Activity.Activity_AddKH2.17
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                TimePickerDialog timePickerDialog = new TimePickerDialog(Activity_AddKH2.this, new TimePickerDialog.OnTimeSetListener() { // from class: tamhoang.ldpro4.Activity.Activity_AddKH2.17.1
                    @Override // android.app.TimePickerDialog.OnTimeSetListener
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        if (selectedMinute < 10) {
                            Activity_AddKH2.this.tv_de_cang.setText(selectedHour + ":0" + selectedMinute);
                            Toast.makeText(Activity_AddKH2.this, "Đặt không nhận đề/càng sau: " + selectedHour + ":0" + selectedMinute, Toast.LENGTH_LONG).show();
                        } else {
                            Activity_AddKH2.this.tv_de_cang.setText(selectedHour + ":" + selectedMinute);
                            Toast.makeText(Activity_AddKH2.this, "Đặt không nhận đề/càng sau: " + selectedHour + ":" + selectedMinute, Toast.LENGTH_LONG).show();
                        }
                        try {
                            Activity_AddKH2.this.caidat_tg.put("tg_debc", Activity_AddKH2.this.tv_de_cang.getText().toString());
                        } catch (JSONException e2) {
                            e2.printStackTrace();
                        }
                    }
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
                timePickerDialog.setTitle("Select Time");
                timePickerDialog.show();
            }
        });
        this.tv_KhongMax.setOnClickListener(new View.OnClickListener() { // from class: tamhoang.ldpro4.Activity.Activity_AddKH2.18
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Activity_AddKH2.this.showDialog2();
            }
        });
        this.btn_all_khong.setOnClickListener(new View.OnClickListener() { // from class: tamhoang.ldpro4.Activity.Activity_AddKH2.19
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                try {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Activity_AddKH2.this);
                    builder.setTitle("Xác nhận!");
                    builder.setMessage("Áp dụng cho tất cả khách hàng?");
                    builder.setPositiveButton("Có", new DialogInterface.OnClickListener() { // from class: tamhoang.ldpro4.Activity.Activity_AddKH2.19.1
                        @Override // android.content.DialogInterface.OnClickListener
                        public void onClick(DialogInterface dialog, int which) {
                            Activity_AddKH2.this.db.QueryData("update tbl_kh_new set tbl_XS = '" + Activity_AddKH2.this.json_KhongMax.toString() + "' WHERE type_kh = 1");
                            Toast.makeText(Activity_AddKH2.this, "Đã áp dụng khống cho tất cả khách hàng!", Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        }
                    });
                    builder.setNegativeButton("Không", new DialogInterface.OnClickListener() { // from class: tamhoang.ldpro4.Activity.Activity_AddKH2.19.2
                        @Override // android.content.DialogInterface.OnClickListener
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.create().show();
                } catch (Exception e2) {
                    Toast.makeText(Activity_AddKH2.this, "Có lỗi xẩy ra!", Toast.LENGTH_LONG).show();
                    e2.printStackTrace();
                }
            }
        });
    }

    public void showDialog2() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.frag_khongmax);
        dialog.getWindow().setLayout(-1, -2);
        Button button =  dialog.findViewById(R.id.btn_KhongDe);
        Button button2 =  dialog.findViewById(R.id.btn_XoaDe);
        Button button3 =  dialog.findViewById(R.id.btn_KhongLo);
        Button button4 =  dialog.findViewById(R.id.btn_XoaLo);
        Button button5 =  dialog.findViewById(R.id.btn_KhongXienCang);
        Button button6 =  dialog.findViewById(R.id.btn_XoaXien);
        final EditText editText =  dialog.findViewById(R.id.edt_NhapDanDe);
        final EditText editText2 =  dialog.findViewById(R.id.edt_NhapDanLo);
        final EditText editText3 =  dialog.findViewById(R.id.giuxien2);
        final EditText editText4 =  dialog.findViewById(R.id.giuxien3);
        final EditText editText5 =  dialog.findViewById(R.id.giuxien4);
        final EditText editText6 =  dialog.findViewById(R.id.giu3cang);
        try {
            editText.setText(this.json_KhongMax.getString("danDe"));
            editText2.setText(this.json_KhongMax.getString("danLo"));
            editText3.setText(this.json_KhongMax.getString("xien2"));
            editText4.setText(this.json_KhongMax.getString("xien3"));
            editText5.setText(this.json_KhongMax.getString("xien4"));
            editText6.setText(this.json_KhongMax.getString("cang"));
        } catch (JSONException unused) {
        }
        button.setOnClickListener(new View.OnClickListener() { // from class: tamhoang.ldpro4.Activity.Activity_AddKH2.20
            /* JADX WARN: Removed duplicated region for block: B:33:? A[RETURN, SYNTHETIC] */
            /* JADX WARN: Removed duplicated region for block: B:5:0x005b A[EXC_TOP_SPLITTER, SYNTHETIC] */
            @Override // android.view.View.OnClickListener
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public void onClick(View v) {
                boolean z;
                String str = "de " + editText.getText().toString();
                if (str.length() > 7) {
                    try {
                        str = Congthuc.NhanTinNhan(Congthuc.convertKhongDau(str)).replace("de dit db:", "de:");
                    } catch (Exception unused2) {
                        Toast.makeText(Activity_AddKH2.this, "Thêm bị lỗi, hãy sửa lại", Toast.LENGTH_LONG).show();
                    }
                    if (str.indexOf("Không hiểu") > -1) {
                        Toast.makeText(Activity_AddKH2.this, str, Toast.LENGTH_LONG).show();
                        z = false;
                        if (z) {
                            return;
                        }
                        try {
                            if (str.length() > 7) {
                                Activity_AddKH2.this.json_KhongMax.put("danDe", editText.getText().toString().replaceAll("\n", " "));
                                JSONObject jSONObject = new JSONObject();
                                do {
                                    String substring = str.substring(0, str.indexOf("\n") + 1);
                                    String substring2 = str.substring(substring.indexOf(":") + 1, substring.indexOf("\n") + 1);
                                    String[] split = substring2.substring(0, substring2.indexOf(",x")).split(",");
                                    String substring3 = substring.substring(substring.indexOf(",x") + 2, substring.indexOf("\n"));
                                    for (String str2 : split) {
                                        if (!jSONObject.has(str2)) {
                                            jSONObject.put(str2, substring3);
                                        } else if (Integer.parseInt(substring3) < jSONObject.getInt(str2)) {
                                            jSONObject.put(str2, substring3);
                                        }
                                    }
                                    str = str.replaceAll(substring, "");
                                } while (str.length() > 0);
                                Activity_AddKH2.this.json_KhongMax.put("soDe", jSONObject.toString());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Activity_AddKH2.this.UPdate();
                        return;
                    }
                }
                z = true;
                if (z) {
                }
            }
        });
        button2.setOnClickListener(new View.OnClickListener() { // from class: tamhoang.ldpro4.Activity.Activity_AddKH2.21
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                try {
                    Activity_AddKH2.this.json_KhongMax.put("danDe", "");
                    Activity_AddKH2.this.json_KhongMax.put("soDe", new JSONObject().toString());
                    editText.setText("");
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Throwable th) {
                    Activity_AddKH2.this.UPdate();
                    throw th;
                }
                Activity_AddKH2.this.UPdate();
            }
        });
        button3.setOnClickListener(new View.OnClickListener() { // from class: tamhoang.ldpro4.Activity.Activity_AddKH2.22
            /* JADX WARN: Removed duplicated region for block: B:33:? A[RETURN, SYNTHETIC] */
            /* JADX WARN: Removed duplicated region for block: B:5:0x005b A[EXC_TOP_SPLITTER, SYNTHETIC] */
            @Override // android.view.View.OnClickListener
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public void onClick(View v) {
                boolean z;
                String str = "de " + editText2.getText().toString();
                if (str.length() > 7) {
                    try {
                        str = Congthuc.NhanTinNhan(Congthuc.convertKhongDau(str)).replace("de dit db:", "de:");
                    } catch (Exception unused2) {
                        Toast.makeText(Activity_AddKH2.this, "Thêm bị lỗi, hãy sửa lại", Toast.LENGTH_LONG).show();
                    }
                    if (str.indexOf("Không hiểu") > -1) {
                        Toast.makeText(Activity_AddKH2.this, str, Toast.LENGTH_LONG).show();
                        z = false;
                        if (z) {
                            return;
                        }
                        try {
                            if (str.length() > 7) {
                                Activity_AddKH2.this.json_KhongMax.put("danLo", editText2.getText().toString().replaceAll("\n", " "));
                                JSONObject jSONObject = new JSONObject();
                                do {
                                    String substring = str.substring(0, str.indexOf("\n") + 1);
                                    String substring2 = str.substring(substring.indexOf(":") + 1, substring.indexOf("\n") + 1);
                                    String[] split = substring2.substring(0, substring2.indexOf(",x")).split(",");
                                    String substring3 = substring.substring(substring.indexOf(",x") + 2, substring.indexOf("\n"));
                                    for (String str2 : split) {
                                        if (!jSONObject.has(str2)) {
                                            jSONObject.put(str2, substring3);
                                        } else if (Integer.parseInt(substring3) < jSONObject.getInt(str2)) {
                                            jSONObject.put(str2, substring3);
                                        }
                                    }
                                    str = str.replaceAll(substring, "");
                                } while (str.length() > 0);
                                Activity_AddKH2.this.json_KhongMax.put("soLo", jSONObject.toString());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Activity_AddKH2.this.UPdate();
                        return;
                    }
                }
                z = true;
                if (z) {
                }
            }
        });
        button4.setOnClickListener(new View.OnClickListener() { // from class: tamhoang.ldpro4.Activity.Activity_AddKH2.23
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                try {
                    Activity_AddKH2.this.json_KhongMax.put("danLo", "");
                    Activity_AddKH2.this.json_KhongMax.put("soLo", new JSONObject().toString());
                    editText2.setText("");
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Throwable th) {
                    Activity_AddKH2.this.UPdate();
                    throw th;
                }
                Activity_AddKH2.this.UPdate();
            }
        });
        button5.setOnClickListener(new View.OnClickListener() { // from class: tamhoang.ldpro4.Activity.Activity_AddKH2.24
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                try {
                    Activity_AddKH2.this.json_KhongMax.put("xien2", editText3.getText().toString());
                    Activity_AddKH2.this.json_KhongMax.put("xien3", editText4.getText().toString());
                    Activity_AddKH2.this.json_KhongMax.put("xien4", editText5.getText().toString());
                    Activity_AddKH2.this.json_KhongMax.put("cang", editText6.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Throwable th) {
                    Activity_AddKH2.this.UPdate();
                    throw th;
                }
                Activity_AddKH2.this.UPdate();
            }
        });
        button6.setOnClickListener(new View.OnClickListener() { // from class: tamhoang.ldpro4.Activity.Activity_AddKH2.25
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                try {
                    Activity_AddKH2.this.json_KhongMax.put("xien2", 0);
                    Activity_AddKH2.this.json_KhongMax.put("xien3", 0);
                    Activity_AddKH2.this.json_KhongMax.put("xien4", 0);
                    Activity_AddKH2.this.json_KhongMax.put("cang", 0);
                    editText3.setText("0");
                    editText4.setText("0");
                    editText5.setText("0");
                    editText6.setText("0");
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Throwable th) {
                    Activity_AddKH2.this.UPdate();
                    throw th;
                }
                Activity_AddKH2.this.UPdate();
            }
        });
        dialog.setCancelable(true);
        dialog.show();
    }
}
