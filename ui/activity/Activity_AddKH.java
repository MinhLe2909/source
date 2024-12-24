package tamhoang.ldpro4.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;
import org.json.JSONObject;
import tamhoang.ldpro4.base.BaseToolBarActivity;
import tamhoang.ldpro4.formula.Congthuc;
import tamhoang.ldpro4.ui.home.MainActivity;
import tamhoang.ldpro4.R;
import tamhoang.ldpro4.utils.Util;
import tamhoang.ldpro4.data.Database;

/* loaded from: classes.dex */
public class Activity_AddKH extends BaseToolBarActivity {
    String app_use;
    Button btn_danhba;
    Button btn_them_KH;
    JSONObject caidat_gia;
    JSONObject caidat_tg;
    Cursor cursor;
    Database db;
    EditText edt_an3c;
    EditText edt_anLo;
    EditText edt_anXN;
    EditText edt_andea;
    EditText edt_andeb;
    EditText edt_andec;
    EditText edt_anded;
    EditText edt_andet;
    EditText edt_anx2;
    EditText edt_anx3;
    EditText edt_anx4;
    EditText edt_gia3c;
    EditText edt_giaXN;
    EditText edt_giadea;
    EditText edt_giadeb;
    EditText edt_giadec;
    EditText edt_giaded;
    EditText edt_giadet;
    EditText edt_gialo;
    EditText edt_giax2;
    EditText edt_giax3;
    EditText edt_giax4;
    EditText edt_sdt;
    EditText edt_ten;
    JSONObject json;
    JSONObject json_KhongMax;
    LinearLayout linner_sodienthoai;
    RadioButton rad_chu;
    RadioButton rad_chu_khach;
    RadioButton rad_khach;
    String so_dienthoai;
    String ten_khach;
    int type;

    @Override // tamhoang.ldpro4.Congthuc.BaseToolBarActivity
    public int getLayoutId() {
        return R.layout.activity_add_kh;
    }

    public void init() {
        this.linner_sodienthoai =  findViewById(R.id.linner_sodienthoai);
        this.edt_ten =  findViewById(R.id.edt_ten);
        this.edt_sdt =  findViewById(R.id.edt_sdt);
        this.rad_chu = (RadioButton) findViewById(R.id.rad_chu);
        this.rad_khach = (RadioButton) findViewById(R.id.rad_khach);
        this.rad_chu_khach = (RadioButton) findViewById(R.id.rad_chu_khach);
        this.edt_giadea =  findViewById(R.id.edt_giadea);
        this.edt_andea =  findViewById(R.id.edt_andea);
        this.edt_giadeb =  findViewById(R.id.edt_giadeb);
        this.edt_andeb =  findViewById(R.id.edt_andeb);
        this.edt_giadec =  findViewById(R.id.edt_giadec);
        this.edt_andec =  findViewById(R.id.edt_andec);
        this.edt_giaded =  findViewById(R.id.edt_giaded);
        this.edt_anded =  findViewById(R.id.edt_anded);
        this.edt_giadet =  findViewById(R.id.edt_giadet);
        this.edt_andet =  findViewById(R.id.edt_andet);
        this.edt_gialo =  findViewById(R.id.edt_giaLo);
        this.edt_anLo =  findViewById(R.id.edt_anLo);
        this.edt_giax2 =  findViewById(R.id.edt_giaXien2);
        this.edt_anx2 =  findViewById(R.id.edt_anXien2);
        this.edt_giax3 =  findViewById(R.id.edt_giaXien3);
        this.edt_anx3 =  findViewById(R.id.edt_anXien3);
        this.edt_giax4 =  findViewById(R.id.edt_giaXien4);
        this.edt_anx4 =  findViewById(R.id.edt_anXien4);
        this.edt_giaXN =  findViewById(R.id.edt_giaXienNhay);
        this.edt_anXN =  findViewById(R.id.edt_anXienNhay);
        this.edt_gia3c =  findViewById(R.id.edt_gia3c);
        this.edt_an3c =  findViewById(R.id.edt_an3c);
        this.btn_them_KH =  findViewById(R.id.btn_them_KH);
        this.btn_danhba =  findViewById(R.id.btn_danhba);
    }

    @Override // android.support.v4.app.FragmentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 2015 && i2 == -1) {
            Cursor query = getContentResolver().query(intent.getData(), null, null, null, null);
            query.moveToFirst();
            int columnIndex = query.getColumnIndex("data1");
            query.getColumnIndex("display_name");
            String string = query.getString(query.getColumnIndex("display_name"));
            String replaceAll = query.getString(columnIndex).replaceAll(" ", "");
            if (replaceAll.length() < 12) {
                replaceAll = "+84" + replaceAll.substring(1);
            }
            this.edt_sdt.setText(replaceAll);
            this.edt_ten.setText(string);
        }
    }

    @Override // tamhoang.ldpro4.Congthuc.BaseToolBarActivity, android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity, android.support.v4.app.SupportActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        int i;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_kh);
        this.db = new Database(this);
        init();
        Intent intent = getIntent();
        this.ten_khach = intent.getStringExtra("tenKH");
        this.so_dienthoai = intent.getStringExtra("so_dienthoai");
        this.app_use = intent.getStringExtra("use_app");
        this.db = new Database(this);
        if (this.ten_khach.length() > 0) {
            this.edt_ten.setText(this.ten_khach);
            this.edt_sdt.setText(this.so_dienthoai);
            Cursor GetData = this.db.GetData("Select * From tbl_kh_new where ten_kh = '" + this.ten_khach + "'");
            this.cursor = GetData;
            GetData.moveToFirst();
            i = this.cursor.getCount();
        } else {
            i = 0;
        }
        if (i > 0) {
            this.edt_sdt.setText(this.cursor.getString(1));
            if (this.cursor.getString(2).indexOf("sms") == -1) {
                this.linner_sodienthoai.setEnabled(false);
                this.edt_ten.setEnabled(false);
                this.edt_sdt.setEnabled(false);
                this.btn_danhba.setEnabled(false);
            }
            if (this.cursor.getCount() > 0) {
                try {
                    JSONObject jSONObject = new JSONObject(this.cursor.getString(5));
                    this.json = jSONObject;
                    JSONObject jSONObject2 = jSONObject.getJSONObject("caidat_gia");
                    this.caidat_gia = jSONObject2;
                    this.edt_giadea.setText(jSONObject2.getString("dea"));
                    this.edt_andea.setText(this.caidat_gia.getString("an_dea"));
                    this.edt_giadeb.setText(this.caidat_gia.getString("deb"));
                    this.edt_andeb.setText(this.caidat_gia.getString("an_deb"));
                    this.edt_giadec.setText(this.caidat_gia.getString("dec"));
                    this.edt_andec.setText(this.caidat_gia.getString("an_dec"));
                    this.edt_giaded.setText(this.caidat_gia.getString("ded"));
                    this.edt_anded.setText(this.caidat_gia.getString("an_ded"));
                    this.edt_giadet.setText(this.caidat_gia.getString("det"));
                    this.edt_andet.setText(this.caidat_gia.getString("an_det"));
                    this.edt_gialo.setText(this.caidat_gia.getString("lo"));
                    this.edt_anLo.setText(this.caidat_gia.getString("an_lo"));
                    this.edt_giax2.setText(this.caidat_gia.getString("gia_x2"));
                    this.edt_anx2.setText(this.caidat_gia.getString("an_x2"));
                    this.edt_giax3.setText(this.caidat_gia.getString("gia_x3"));
                    this.edt_anx3.setText(this.caidat_gia.getString("an_x3"));
                    this.edt_giax4.setText(this.caidat_gia.getString("gia_x4"));
                    this.edt_anx4.setText(this.caidat_gia.getString("an_x4"));
                    this.edt_giaXN.setText(this.caidat_gia.getString("gia_xn"));
                    this.edt_anXN.setText(this.caidat_gia.getString("an_xn"));
                    this.edt_gia3c.setText(this.caidat_gia.getString("gia_bc"));
                    this.edt_an3c.setText(this.caidat_gia.getString("an_bc"));
                    if (this.cursor.getInt(3) == 1) {
                        this.rad_khach.setChecked(true);
                        this.rad_chu.setChecked(false);
                        this.rad_chu_khach.setChecked(false);
                    } else if (this.cursor.getInt(3) == 2) {
                        this.rad_khach.setChecked(false);
                        this.rad_chu.setChecked(true);
                        this.rad_chu_khach.setChecked(false);
                    } else if (this.cursor.getInt(3) == 3) {
                        this.rad_khach.setChecked(false);
                        this.rad_chu.setChecked(false);
                        this.rad_chu_khach.setChecked(true);
                    }
                    this.json_KhongMax = new JSONObject(this.cursor.getString(6));
                } catch (Exception e) {
                    e.printStackTrace();
                    Util.writeLog(e);
                }
                Cursor cursor = this.cursor;
                if (cursor != null && !cursor.isClosed()) {
                    this.cursor.close();
                }
            }
        } else if (this.app_use.indexOf("sms") <= -1) {
            this.edt_ten.setText(this.ten_khach);
            this.edt_sdt.setText(this.so_dienthoai);
            this.edt_ten.setEnabled(false);
            this.edt_sdt.setEnabled(false);
            this.btn_danhba.setEnabled(false);
        }
        this.btn_danhba.setOnClickListener(new View.OnClickListener() { // from class: tamhoang.ldpro4.Activity.Activity_AddKH.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Activity_AddKH.this.startActivityForResult(new Intent("android.intent.action.PICK", ContactsContract.CommonDataKinds.Phone.CONTENT_URI), 2015);
            }
        });
        this.btn_them_KH.setOnClickListener(new View.OnClickListener() { // from class: tamhoang.ldpro4.Activity.Activity_AddKH.2
            /* JADX WARN: Removed duplicated region for block: B:41:0x0572 A[Catch: Exception -> 0x05e1, TRY_LEAVE, TryCatch #7 {Exception -> 0x05e1, blocks: (B:39:0x0496, B:41:0x0572, B:47:0x059d, B:48:0x05b6, B:50:0x05c0, B:51:0x05d9, B:55:0x05cd, B:44:0x0591), top: B:38:0x0496, outer: #1, inners: #0 }] */
            /* JADX WARN: Removed duplicated region for block: B:50:0x05c0 A[Catch: Exception -> 0x05e1, TryCatch #7 {Exception -> 0x05e1, blocks: (B:39:0x0496, B:41:0x0572, B:47:0x059d, B:48:0x05b6, B:50:0x05c0, B:51:0x05d9, B:55:0x05cd, B:44:0x0591), top: B:38:0x0496, outer: #1, inners: #0 }] */
            /* JADX WARN: Removed duplicated region for block: B:55:0x05cd A[Catch: Exception -> 0x05e1, TryCatch #7 {Exception -> 0x05e1, blocks: (B:39:0x0496, B:41:0x0572, B:47:0x059d, B:48:0x05b6, B:50:0x05c0, B:51:0x05d9, B:55:0x05cd, B:44:0x0591), top: B:38:0x0496, outer: #1, inners: #0 }] */
            @Override // android.view.View.OnClickListener
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public void onClick(View v) {
                String str;
                String str2;
                String obj = Activity_AddKH.this.edt_ten.getText().toString();
                String obj2 = Activity_AddKH.this.edt_sdt.getText().toString();
                if (obj2.length() <= 0 || obj.length() <= 0) {
                    return;
                }
                if (obj2.startsWith("0") && Congthuc.isNumeric(obj2)) {
                    obj2 = "+84" + obj2.substring(1);
                }
                Activity_AddKH activity_AddKH = Activity_AddKH.this;
                activity_AddKH.cursor = activity_AddKH.db.GetData("Select * From tbl_kh_new Where ten_kh <> '" + Activity_AddKH.this.edt_ten.getText().toString() + "' AND sdt = '" + obj2 + "'");
                if (Activity_AddKH.this.cursor.getCount() > 0) {
                    Activity_AddKH.this.showAlertBox("Đã có số SĐT này!", "Mỗi khách hàng chỉ được dùng 1 số điện thoại và mỗi số điện thoại chỉ dùng cho 1 khách hàng.").setPositiveButton(MainActivity.OK, new DialogInterface.OnClickListener() { // from class: tamhoang.ldpro4.Activity.Activity_AddKH.2.1
                        @Override // android.content.DialogInterface.OnClickListener
                        public void onClick(DialogInterface dialogInterface, int i2) {
                        }
                    }).show().setCanceledOnTouchOutside(false);
                    return;
                }
                String obj3 = Activity_AddKH.this.edt_giadea.getText().toString();
                String obj4 = Activity_AddKH.this.edt_andea.getText().toString();
                String obj5 = Activity_AddKH.this.edt_giadeb.getText().toString();
                String obj6 = Activity_AddKH.this.edt_andeb.getText().toString();
                String obj7 = Activity_AddKH.this.edt_giadec.getText().toString();
                String obj8 = Activity_AddKH.this.edt_andec.getText().toString();
                String obj9 = Activity_AddKH.this.edt_giaded.getText().toString();
                String obj10 = Activity_AddKH.this.edt_anded.getText().toString();
                String obj11 = Activity_AddKH.this.edt_giadet.getText().toString();
                String obj12 = Activity_AddKH.this.edt_andet.getText().toString();
                String str3 = obj2;
                String obj13 = Activity_AddKH.this.edt_gialo.getText().toString();
                String obj14 = Activity_AddKH.this.edt_anLo.getText().toString();
                String obj15 = Activity_AddKH.this.edt_giax2.getText().toString();
                String obj16 = Activity_AddKH.this.edt_anx2.getText().toString();
                String obj17 = Activity_AddKH.this.edt_giax3.getText().toString();
                String obj18 = Activity_AddKH.this.edt_anx3.getText().toString();
                String obj19 = Activity_AddKH.this.edt_giax4.getText().toString();
                String obj20 = Activity_AddKH.this.edt_anx4.getText().toString();
                String obj21 = Activity_AddKH.this.edt_giaXN.getText().toString();
                String obj22 = Activity_AddKH.this.edt_anXN.getText().toString();
                String obj23 = Activity_AddKH.this.edt_gia3c.getText().toString();
                String obj24 = Activity_AddKH.this.edt_an3c.getText().toString();
                if (Activity_AddKH.this.rad_khach.isChecked()) {
                    str = obj14;
                    Activity_AddKH.this.type = 1;
                } else {
                    str = obj14;
                }
                if (Activity_AddKH.this.rad_chu.isChecked()) {
                    Activity_AddKH.this.type = 2;
                }
                if (Activity_AddKH.this.rad_chu_khach.isChecked()) {
                    Activity_AddKH.this.type = 3;
                }
                if (Activity_AddKH.this.app_use == null) {
                    Activity_AddKH.this.app_use = "sms";
                }
                Activity_AddKH activity_AddKH2 = Activity_AddKH.this;
                activity_AddKH2.cursor = activity_AddKH2.db.GetData("Select * From tbl_kh_new Where ten_kh = '" + Activity_AddKH.this.edt_ten.getText().toString() + "'");
                Activity_AddKH.this.cursor.moveToFirst();
                if (Activity_AddKH.this.cursor.getCount() > 0) {
                    try {
                        str2 = "'";
                        try {
                            Activity_AddKH.this.json = new JSONObject(Activity_AddKH.this.cursor.getString(5));
                            Activity_AddKH activity_AddKH3 = Activity_AddKH.this;
                            activity_AddKH3.caidat_gia = activity_AddKH3.json.getJSONObject("caidat_gia");
                            Activity_AddKH activity_AddKH4 = Activity_AddKH.this;
                            activity_AddKH4.caidat_tg = activity_AddKH4.json.getJSONObject("caidat_tg");
                            Activity_AddKH activity_AddKH5 = Activity_AddKH.this;
                            activity_AddKH5.app_use = activity_AddKH5.cursor.getString(2);
                            Activity_AddKH.this.json_KhongMax = new JSONObject(Activity_AddKH.this.cursor.getString(6));
                        } catch (Exception e) {
                            Util.writeLog(e);
                            Activity_AddKH.this.caidat_gia.put("dea", obj3);
                            Activity_AddKH.this.caidat_gia.put("an_dea", obj4);
                            Activity_AddKH.this.caidat_gia.put("deb", obj5);
                            Activity_AddKH.this.caidat_gia.put("an_deb", obj6);
                            Activity_AddKH.this.caidat_gia.put("det", obj11);
                            Activity_AddKH.this.caidat_gia.put("an_det", obj12);
                            Activity_AddKH.this.caidat_gia.put("dec", obj7);
                            Activity_AddKH.this.caidat_gia.put("an_dec", obj8);
                            Activity_AddKH.this.caidat_gia.put("ded", obj9);
                            try {
                                Activity_AddKH.this.caidat_gia.put("an_ded", obj10);
                                Activity_AddKH.this.caidat_gia.put("lo", obj13);
                                Activity_AddKH.this.caidat_gia.put("an_lo", str);
                                Activity_AddKH.this.caidat_gia.put("gia_x2", obj15);
                                obj3 = obj16;
                                Activity_AddKH.this.caidat_gia.put("an_x2", obj3);
                                obj3 = "Sai số liệu, hãy kiểm tra lại";
                            } catch (Exception e3) {
                                obj3 = "Sai số liệu, hãy kiểm tra lại";
                                Toast.makeText(Activity_AddKH.this, obj3, Toast.LENGTH_LONG).show();
                                Activity_AddKH.this.db.LayDanhsachKH();
                                Activity_AddKH.this.finish();
                                Util.writeLog(e3);
                            }
                            try {
                                Activity_AddKH.this.caidat_gia.put("gia_x3", obj17);
                                Activity_AddKH.this.caidat_gia.put("an_x3", obj18);
                                Activity_AddKH.this.caidat_gia.put("gia_x4", obj19);
                                Activity_AddKH.this.caidat_gia.put("an_x4", obj20);
                                Activity_AddKH.this.caidat_gia.put("gia_xn", obj21);
                                Activity_AddKH.this.caidat_gia.put("an_xn", obj22);
                                Activity_AddKH.this.caidat_gia.put("gia_bc", obj23);
                                Activity_AddKH.this.caidat_gia.put("an_bc", obj24);
                                Activity_AddKH.this.json.put("caidat_gia", Activity_AddKH.this.caidat_gia);
                                Activity_AddKH.this.json.put("caidat_tg", Activity_AddKH.this.caidat_tg);
                                Activity_AddKH.this.db.QueryData("REPLACE Into tbl_kh_new Values ('" + Activity_AddKH.this.edt_ten.getText().toString() + "','" + str3 + "','" + Activity_AddKH.this.app_use + "'," + Activity_AddKH.this.type + ",0,'" + Activity_AddKH.this.json.toString() + "','" + Activity_AddKH.this.json_KhongMax.toString() + "')");
                                if (Activity_AddKH.this.type != 2) {
                                }
                                if (Activity_AddKH.this.cursor.getCount() > 0) {
                                }
                                Activity_AddKH.this.db.LayDanhsachKH();
                            } catch (Exception unused) {
                                Toast.makeText(Activity_AddKH.this, obj3, Toast.LENGTH_LONG).show();
                                Activity_AddKH.this.db.LayDanhsachKH();
                                Activity_AddKH.this.finish();
                            }
                            Activity_AddKH.this.db.LayDanhsachKH();
                            Activity_AddKH.this.finish();
                        }
                    } catch (Exception e4) {
                        str2 = "'";
                    }
                } else {
                    str2 = "'";
                    Activity_AddKH.this.json = new JSONObject();
                    Activity_AddKH.this.caidat_gia = new JSONObject();
                    Activity_AddKH.this.caidat_tg = new JSONObject();
                    Activity_AddKH.this.json_KhongMax = new JSONObject();
                    try {
                        Activity_AddKH.this.caidat_tg.put("dlgiu_de", 0);
                        Activity_AddKH.this.caidat_tg.put("dlgiu_lo", 0);
                        Activity_AddKH.this.caidat_tg.put("dlgiu_xi", 0);
                        Activity_AddKH.this.caidat_tg.put("dlgiu_xn", 0);
                        Activity_AddKH.this.caidat_tg.put("dlgiu_bc", 0);
                        Activity_AddKH.this.caidat_tg.put("khgiu_de", 0);
                        Activity_AddKH.this.caidat_tg.put("khgiu_lo", 0);
                        Activity_AddKH.this.caidat_tg.put("khgiu_xi", 0);
                        Activity_AddKH.this.caidat_tg.put("khgiu_xn", 0);
                        Activity_AddKH.this.caidat_tg.put("khgiu_bc", 0);
                        Activity_AddKH.this.caidat_tg.put("ok_tin", 3);
                        Activity_AddKH.this.caidat_tg.put("xien_nhan", 0);
                        Activity_AddKH.this.caidat_tg.put("chot_sodu", 0);
                        Activity_AddKH.this.caidat_tg.put("tg_loxien", "18:13");
                        Activity_AddKH.this.caidat_tg.put("tg_debc", "18:20");
                        Activity_AddKH.this.caidat_tg.put("loi_donvi", 0);
                        Activity_AddKH.this.caidat_tg.put("heso_de", 0);
                        Activity_AddKH.this.caidat_tg.put("maxDe", 0);
                        Activity_AddKH.this.caidat_tg.put("maxLo", 0);
                        Activity_AddKH.this.caidat_tg.put("maxXi", 0);
                        Activity_AddKH.this.caidat_tg.put("maxCang", 0);
                        Activity_AddKH.this.json_KhongMax.put("danDe", "");
                        Activity_AddKH.this.json_KhongMax.put("danLo", "");
                        Activity_AddKH.this.json_KhongMax.put("soDe", new JSONObject().toString());
                        Activity_AddKH.this.json_KhongMax.put("soLo", new JSONObject().toString());
                        Activity_AddKH.this.json_KhongMax.put("xien2", 0);
                        Activity_AddKH.this.json_KhongMax.put("xien3", 0);
                        Activity_AddKH.this.json_KhongMax.put("xien4", 0);
                        Activity_AddKH.this.json_KhongMax.put("cang", 0);
                    } catch (Exception e5) {
                        e5.printStackTrace();
                        Util.writeLog(e5);
                    }
                }
                try {
                    Activity_AddKH.this.caidat_gia.put("dea", obj3);
                    Activity_AddKH.this.caidat_gia.put("an_dea", obj4);
                    Activity_AddKH.this.caidat_gia.put("deb", obj5);
                    Activity_AddKH.this.caidat_gia.put("an_deb", obj6);
                    Activity_AddKH.this.caidat_gia.put("det", obj11);
                    Activity_AddKH.this.caidat_gia.put("an_det", obj12);
                    Activity_AddKH.this.caidat_gia.put("dec", obj7);
                    Activity_AddKH.this.caidat_gia.put("an_dec", obj8);
                    Activity_AddKH.this.caidat_gia.put("ded", obj9);
                } catch (Exception unused2) {
                    obj3 = "Sai số liệu, hãy kiểm tra lại";
                }
                try {
                    Activity_AddKH.this.caidat_gia.put("an_ded", obj10);
                    Activity_AddKH.this.caidat_gia.put("lo", obj13);
                    Activity_AddKH.this.caidat_gia.put("an_lo", str);
                    Activity_AddKH.this.caidat_gia.put("gia_x2", obj15);
                    obj3 = obj16;
                    Activity_AddKH.this.caidat_gia.put("an_x2", obj3);
                    obj3 = "Sai số liệu, hãy kiểm tra lại";
                    Activity_AddKH.this.caidat_gia.put("gia_x3", obj17);
                    Activity_AddKH.this.caidat_gia.put("an_x3", obj18);
                    Activity_AddKH.this.caidat_gia.put("gia_x4", obj19);
                    Activity_AddKH.this.caidat_gia.put("an_x4", obj20);
                    Activity_AddKH.this.caidat_gia.put("gia_xn", obj21);
                    Activity_AddKH.this.caidat_gia.put("an_xn", obj22);
                    Activity_AddKH.this.caidat_gia.put("gia_bc", obj23);
                    Activity_AddKH.this.caidat_gia.put("an_bc", obj24);
                    Activity_AddKH.this.json.put("caidat_gia", Activity_AddKH.this.caidat_gia);
                    Activity_AddKH.this.json.put("caidat_tg", Activity_AddKH.this.caidat_tg);
                    Activity_AddKH.this.db.QueryData("REPLACE Into tbl_kh_new Values ('" + Activity_AddKH.this.edt_ten.getText().toString() + "','" + str3 + "','" + Activity_AddKH.this.app_use + "'," + Activity_AddKH.this.type + ",0,'" + Activity_AddKH.this.json.toString() + "','" + Activity_AddKH.this.json_KhongMax.toString() + "')");
                    if (Activity_AddKH.this.type != 2) {
                        Database database = Activity_AddKH.this.db;
                        StringBuilder sb = new StringBuilder();
                        sb.append("Delete FROM tbl_chuyenthang WHERE sdt_nhan = '");
                        sb.append(Activity_AddKH.this.edt_sdt.getText().toString());
                        try {
                            sb.append(str2);
                            database.QueryData(sb.toString());
                        } catch (Exception e6) {
                            Util.writeLog(e6);
                            Toast.makeText(Activity_AddKH.this, obj3, Toast.LENGTH_LONG).show();
                            Activity_AddKH.this.db.LayDanhsachKH();
                            Activity_AddKH.this.finish();
                        }
                    }
                    if (Activity_AddKH.this.cursor.getCount() > 0) {
                        Toast.makeText(Activity_AddKH.this, "Đã cập nhật thông tin!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(Activity_AddKH.this, "Đã thêm khách hàng!", Toast.LENGTH_LONG).show();
                    }
                    Activity_AddKH.this.db.LayDanhsachKH();
                } catch (Exception unused3) {
                    Toast.makeText(Activity_AddKH.this, obj3, Toast.LENGTH_LONG).show();
                    Activity_AddKH.this.db.LayDanhsachKH();
                    Activity_AddKH.this.finish();
                    Activity_AddKH.this.db.LayDanhsachKH();
                    Activity_AddKH.this.finish();
                }
                Activity_AddKH.this.db.LayDanhsachKH();
                Activity_AddKH.this.finish();
            }
        });
    }

    public AlertDialog.Builder showAlertBox(String title, String message) {
        return new AlertDialog.Builder(this).setTitle(title).setMessage(message);
    }
}
