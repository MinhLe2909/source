package tamhoang.ldpro4.ui.activity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;
import tamhoang.ldpro4.base.BaseToolBarActivity;
import tamhoang.ldpro4.formula.Congthuc;
import tamhoang.ldpro4.R;
import tamhoang.ldpro4.data.Database;

public class Activity_GiuSo extends BaseToolBarActivity {
    Button btnThemXien;
    Button btnThemdan;
    Button btnXoaDan;
    Button btnXoaXien;
    Database db;
    EditText edtNhapDan;
    EditText giu3cang;
    EditText giuxien2;
    EditText giuxien3;
    EditText giuxien4;
    RadioButton radioDeA;
    RadioButton radioDeB;
    RadioButton radioDeC;
    RadioButton radioDeD;
    RadioButton radioLo;
    Spinner spr_KH;

    @Override
    public int getLayoutId() {
        return R.layout.activity_giu_so;
    }

    public void init() {
        this.btnThemdan =  findViewById(R.id.btn_Them_Om);
        this.btnXoaDan =  findViewById(R.id.btn_Xoa);
        this.btnThemXien =  findViewById(R.id.btn_GiuXien);
        this.btnXoaXien =  findViewById(R.id.btn_XoaXien);
        this.radioDeA = (RadioButton) findViewById(R.id.radio_DeA);
        this.radioDeB = (RadioButton) findViewById(R.id.radio_DeB);
        this.radioDeC = (RadioButton) findViewById(R.id.radio_DeC);
        this.radioDeD = (RadioButton) findViewById(R.id.radio_DeD);
        this.radioLo = (RadioButton) findViewById(R.id.radio_lo);
        this.edtNhapDan =  findViewById(R.id.edt_NhapDan);
        this.spr_KH = (Spinner) findViewById(R.id.spr_KH);
        this.giuxien2 =  findViewById(R.id.giuxien2);
        this.giuxien3 =  findViewById(R.id.giuxien3);
        this.giuxien4 =  findViewById(R.id.giuxien4);
        this.giu3cang =  findViewById(R.id.giu3cang);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giu_so);
        this.db = new Database(this);
        init();
        // android.view.View.OnClickListener
/*
    Code decompiled incorrectly, please refer to instructions dump.
*/
        this.btnThemdan.setOnClickListener(v -> {
            boolean z;
            String str = "de " + Activity_GiuSo.this.edtNhapDan.getText().toString();
            int i = 0;
            int i2 = 1;
            if (str.length() > 7) {
                try {
                    str = Congthuc.NhanTinNhan(Congthuc.convertKhongDau(str)).replace("de dit db:", "de:");
                } catch (Exception unused) {
                    Toast.makeText(Activity_GiuSo.this, "Thêm bị lỗi, hãy sửa lại", Toast.LENGTH_LONG).show();
                }
                if (str.indexOf("Không hiểu") > -1) {
                    Toast.makeText(Activity_GiuSo.this, str, Toast.LENGTH_LONG).show();
                    z = false;
                    if (z) {
                        return;
                    }
                    Toast.makeText(Activity_GiuSo.this, "Đã sửa dàn giữ!", Toast.LENGTH_LONG).show();
                    if (!Activity_GiuSo.this.radioDeB.isChecked()) {
                        if (Activity_GiuSo.this.radioDeA.isChecked()) {
                            if (str.length() > 7) {
                                Activity_GiuSo.this.db.QueryData("Update So_om Set Om_DeA =0");
                                Activity_GiuSo.this.db.QueryData("UPDATE So_om SET Sphu1 = '" + Activity_GiuSo.this.edtNhapDan.getText().toString() + "' WHERE ID = 20");
                                do {
                                    String substring = str.substring(0, str.indexOf("\n") + 1);
                                    String substring2 = str.substring(substring.indexOf(":") + 1, substring.indexOf("\n") + 1);
                                    String[] split = substring2.substring(0, substring2.indexOf(",x")).split(",");
                                    String substring3 = substring.substring(substring.indexOf(",x") + 2, substring.indexOf("\n"));
                                    for (String str2 : split) {
                                        Activity_GiuSo.this.db.QueryData("Update So_om Set Om_DeA = Om_DeA +" + substring3 + " WHERE So = '" + str2 + "'");
                                    }
                                    str = str.replaceAll(substring, "");
                                } while (str.length() > 0);
                                return;
                            }
                            return;
                        }
                        if (Activity_GiuSo.this.radioDeC.isChecked()) {
                            if (str.length() > 7) {
                                Activity_GiuSo.this.db.QueryData("Update So_om Set Om_DeC =0");
                                Activity_GiuSo.this.db.QueryData("UPDATE So_om SET Sphu1 = '" + Activity_GiuSo.this.edtNhapDan.getText().toString() + "' WHERE ID = 22");
                                do {
                                    String substring4 = str.substring(0, str.indexOf("\n") + 1);
                                    String substring5 = str.substring(substring4.indexOf(":") + 1, substring4.indexOf("\n") + 1);
                                    String[] split2 = substring5.substring(0, substring5.indexOf(",x")).split(",");
                                    String substring6 = substring4.substring(substring4.indexOf(",x") + 2, substring4.indexOf("\n"));
                                    for (String str3 : split2) {
                                        Activity_GiuSo.this.db.QueryData("Update So_om Set Om_DeC = Om_DeC +" + substring6 + " WHERE So = '" + str3 + "'");
                                    }
                                    str = str.replaceAll(substring4, "");
                                } while (str.length() > 0);
                                return;
                            }
                            return;
                        }
                        if (Activity_GiuSo.this.radioDeD.isChecked()) {
                            if (str.length() > 7) {
                                Activity_GiuSo.this.db.QueryData("Update So_om Set Om_DeD =0");
                                Activity_GiuSo.this.db.QueryData("UPDATE So_om SET Sphu1 = '" + Activity_GiuSo.this.edtNhapDan.getText().toString() + "' WHERE ID = 23");
                                do {
                                    String substring7 = str.substring(0, str.indexOf("\n") + 1);
                                    String substring8 = str.substring(substring7.indexOf(":") + 1, substring7.indexOf("\n") + 1);
                                    String[] split3 = substring8.substring(0, substring8.indexOf(",x")).split(",");
                                    String substring9 = substring7.substring(substring7.indexOf(",x") + 2, substring7.indexOf("\n"));
                                    for (String str4 : split3) {
                                        Activity_GiuSo.this.db.QueryData("Update So_om Set Om_DeD = Om_DeD +" + substring9 + " WHERE So = '" + str4 + "'");
                                    }
                                    str = str.replaceAll(substring7, "");
                                } while (str.length() > 0);
                                return;
                            }
                            return;
                        }
                        if (!Activity_GiuSo.this.radioLo.isChecked() || str.length() <= 7) {
                            return;
                        }
                        Activity_GiuSo.this.db.QueryData("Update So_om Set Om_Lo =0");
                        Activity_GiuSo.this.db.QueryData("UPDATE So_om SET Sphu1 = '" + Activity_GiuSo.this.edtNhapDan.getText().toString() + "' WHERE ID = 24");
                        do {
                            String substring10 = str.substring(0, str.indexOf("\n") + 1);
                            String substring11 = str.substring(substring10.indexOf(":") + 1, substring10.indexOf("\n") + 1);
                            String[] split4 = substring11.substring(0, substring11.indexOf(",x")).split(",");
                            String substring12 = substring10.substring(substring10.indexOf(",x") + 2, substring10.indexOf("\n"));
                            for (String str5 : split4) {
                                Activity_GiuSo.this.db.QueryData("Update So_om Set Om_Lo = Om_Lo +" + substring12 + " WHERE So = '" + str5 + "'");
                            }
                            str = str.replaceAll(substring10, "");
                        } while (str.length() > 0);
                        return;
                    }
                    if (str.length() <= 7) {
                        return;
                    }
                    Activity_GiuSo.this.db.QueryData("Update So_om Set Om_DeB =0");
                    Activity_GiuSo.this.db.QueryData("UPDATE So_om SET Sphu1 = '" + Activity_GiuSo.this.edtNhapDan.getText().toString() + "' WHERE ID = 21");
                    while (true) {
                        String substring13 = str.substring(i, str.indexOf("\n") + i2);
                        String substring14 = str.substring(substring13.indexOf(":") + i2, substring13.indexOf("\n") + i2);
                        String[] split5 = substring14.substring(i, substring14.indexOf(",x")).split(",");
                        String substring15 = substring13.substring(substring13.indexOf(",x") + 2, substring13.indexOf("\n"));
                        for (String str6 : split5) {
                            Activity_GiuSo.this.db.QueryData("Update So_om Set Om_DeB = Om_DeB +" + substring15 + " WHERE So = '" + str6 + "'");
                        }
                        str = str.replaceAll(substring13, "");
                        if (str.length() <= 0) {
                            return;
                        }
                        i = 0;
                        i2 = 1;
                    }
                }
            }
            z = true;
            if (z) {
            }
        });
        this.btnXoaDan.setOnClickListener(v -> {
            if (Activity_GiuSo.this.radioDeA.isChecked()) {
                Activity_GiuSo.this.db.QueryData("UPdate so_Om set Om_DeA = 0");
                Activity_GiuSo.this.db.QueryData("UPDATE So_om SET Sphu1 = null WHERE ID = 20");
            }
            if (Activity_GiuSo.this.radioDeB.isChecked()) {
                Activity_GiuSo.this.db.QueryData("UPdate so_Om set Om_DeB = 0");
                Activity_GiuSo.this.db.QueryData("UPDATE So_om SET Sphu1 = null WHERE ID = 21");
            }
            if (Activity_GiuSo.this.radioDeC.isChecked()) {
                Activity_GiuSo.this.db.QueryData("UPdate so_Om set Om_DeC = 0");
                Activity_GiuSo.this.db.QueryData("UPDATE So_om SET Sphu1 = null WHERE ID = 22");
            }
            if (Activity_GiuSo.this.radioDeD.isChecked()) {
                Activity_GiuSo.this.db.QueryData("UPdate so_Om set Om_DeD = 0");
                Activity_GiuSo.this.db.QueryData("UPDATE So_om SET Sphu1 = null WHERE ID = 23");
            }
            if (Activity_GiuSo.this.radioLo.isChecked()) {
                Activity_GiuSo.this.db.QueryData("UPdate so_Om set Om_Lo = 0");
                Activity_GiuSo.this.db.QueryData("UPDATE So_om SET Sphu1 = null WHERE ID = 24");
            }
            Activity_GiuSo.this.edtNhapDan.setText("");
            Toast.makeText(Activity_GiuSo.this, "Đã xóa dàn giữ!", Toast.LENGTH_LONG).show();
        });
        this.btnThemXien.setOnClickListener(view -> {
            int parseInt = Activity_GiuSo.this.giuxien2.getText().toString().length() > 0 ? Integer.parseInt(Activity_GiuSo.this.giuxien2.getText().toString()) : 0;
            int parseInt2 = Activity_GiuSo.this.giuxien3.getText().toString().length() > 0 ? Integer.parseInt(Activity_GiuSo.this.giuxien3.getText().toString()) : 0;
            int parseInt3 = Activity_GiuSo.this.giuxien4.getText().toString().length() > 0 ? Integer.parseInt(Activity_GiuSo.this.giuxien4.getText().toString()) : 0;
            int parseInt4 = Activity_GiuSo.this.giu3cang.getText().toString().length() > 0 ? Integer.parseInt(Activity_GiuSo.this.giu3cang.getText().toString()) : 0;
            Activity_GiuSo.this.db.QueryData("Update So_om Set Om_Xi2 = " + parseInt + ", Om_Xi3 = " + parseInt2 + ", Om_Xi4 = " + parseInt3 + ", Om_bc = " + parseInt4 + " WHERE ID = 1");
            Toast.makeText(Activity_GiuSo.this, "Đã lưu giữ xiên/càng!", Toast.LENGTH_LONG).show();
        });
        this.btnXoaXien.setOnClickListener(view -> {
            Activity_GiuSo.this.db.QueryData("Update So_om Set Om_Xi2 = 0, Om_Xi3 = 0, Om_Xi4 = 0, Om_bc = 0 WHERE ID = 1");
            Toast.makeText(Activity_GiuSo.this, "Đã xóa giữ xiên/càng!", Toast.LENGTH_LONG).show();
            Activity_GiuSo.this.giuxien2.setText("");
            Activity_GiuSo.this.giuxien3.setText("");
            Activity_GiuSo.this.giuxien4.setText("");
            Activity_GiuSo.this.giu3cang.setText("");
        });
        this.radioDeA.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                Cursor GetData = Activity_GiuSo.this.db.GetData("Select Sphu1 FROM So_om WHERE ID = 20");
                if (GetData.moveToFirst()) {
                    Activity_GiuSo.this.edtNhapDan.setText(GetData.getString(0));
                    if (GetData == null || GetData.isClosed()) {
                        return;
                    }
                    GetData.close();
                }
            }
        });
        this.radioDeB.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                Cursor GetData = Activity_GiuSo.this.db.GetData("Select Sphu1 FROM So_om WHERE ID = 21");
                if (GetData.moveToFirst()) {
                    Activity_GiuSo.this.edtNhapDan.setText(GetData.getString(0));
                    if (GetData == null || GetData.isClosed()) {
                        return;
                    }
                    GetData.close();
                }
            }
        });
        this.radioDeC.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                Cursor GetData = Activity_GiuSo.this.db.GetData("Select Sphu1 FROM So_om WHERE ID = 22");
                if (GetData.moveToFirst()) {
                    Activity_GiuSo.this.edtNhapDan.setText(GetData.getString(0));
                    if (GetData == null || GetData.isClosed()) {
                        return;
                    }
                    GetData.close();
                }
            }
        });
        this.radioDeD.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                Cursor GetData = Activity_GiuSo.this.db.GetData("Select Sphu1 FROM So_om WHERE ID = 23");
                if (GetData.moveToFirst()) {
                    Activity_GiuSo.this.edtNhapDan.setText(GetData.getString(0));
                    if (GetData == null || GetData.isClosed()) {
                        return;
                    }
                    GetData.close();
                }
            }
        });
        this.radioLo.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                Cursor GetData = Activity_GiuSo.this.db.GetData("Select Sphu1 FROM So_om WHERE ID = 24");
                if (GetData.moveToFirst()) {
                    Activity_GiuSo.this.edtNhapDan.setText(GetData.getString(0));
                    if (GetData == null || GetData.isClosed()) {
                        return;
                    }
                    GetData.close();
                }
            }
        });
        Cursor GetData = this.db.GetData("Select Sphu1 FROM So_om WHERE ID = 21");
        if (GetData.moveToFirst()) {
            this.edtNhapDan.setText(GetData.getString(0));
            if (GetData != null && !GetData.isClosed()) {
                GetData.close();
            }
        }
        Cursor GetData2 = this.db.GetData("Select * From so_om WHERE id = 1");
        if (GetData2.moveToFirst()) {
            this.giuxien2.setText(GetData2.getString(7) + "");
            this.giuxien3.setText(GetData2.getString(8) + "");
            this.giuxien4.setText(GetData2.getString(9) + "");
            this.giu3cang.setText(GetData2.getString(10) + "");
            if (GetData2 == null || GetData2.isClosed()) {
                return;
            }
            GetData2.close();
        }
    }
}
