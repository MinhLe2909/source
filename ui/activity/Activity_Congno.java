package tamhoang.ldpro4.ui.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import tamhoang.ldpro4.base.BaseToolBarActivity;
import tamhoang.ldpro4.formula.Congthuc;
import tamhoang.ldpro4.R;
import tamhoang.ldpro4.data.Database;

public class Activity_Congno extends BaseToolBarActivity {
    Button btn_congno;
    Database db;
    ListView lv_congno;
    public List<String> mKetQua = new ArrayList();
    public List<String> mLuy_ke = new ArrayList();
    public List<String> mNgay = new ArrayList();
    public List<String> mNgayNhan = new ArrayList();
    public List<String> mSdt = new ArrayList();
    public List<String> mThanhToan = new ArrayList();
    String message;
    TextView tv_tenKH;

    public class Congno_Adapter extends ArrayAdapter {
        public Congno_Adapter(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View inflate = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.activity_congno_lv, (ViewGroup) null);
            ((TextView) inflate.findViewById(R.id.tv_ngay_thanhtoan)).setText(Activity_Congno.this.mNgay.get(position));
            ((TextView) inflate.findViewById(R.id.tv_phatsinh)).setText(Activity_Congno.this.mKetQua.get(position));
            ((TextView) inflate.findViewById(R.id.tv_thanhtoan)).setText(Activity_Congno.this.mThanhToan.get(position));
            ((TextView) inflate.findViewById(R.id.tv_luyke)).setText(Activity_Congno.this.mLuy_ke.get(position));
            return inflate;
        }
    }

    public void Congno_report_listview() {
        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        this.mNgayNhan.clear();
        this.mSdt.clear();
        this.mNgay.clear();
        this.mKetQua.clear();
        this.mThanhToan.clear();
        this.mLuy_ke.clear();
        Cursor GetData = this.db.GetData("Select ngay_nhan, so_dienthoai,strftime('%d/%m/%Y',ngay_nhan) as Ngay\n, sum((the_loai <> 'tt') *ket_qua*(100 - diem_khachgiu)/100)/1000 as KQ \n, sum((the_loai = 'tt') *ket_qua)/1000 as TT \n, (Select sum(ket_qua*(100 - diem_khachgiu)/100) FROM tbl_soctS t2 \nWHERE tbl_soctS.ngay_nhan >= t2.ngay_nhan And tbl_soctS.ten_kh = t2.ten_kh)/1000 AS luy_ke \nFROM tbl_soctS \nWHERE ten_kh = '" + this.message + "' \nGROUP BY ngay_nhan ORDER BY ngay_nhan");
        while (GetData.moveToNext()) {
            this.mNgayNhan.add(GetData.getString(0));
            this.mSdt.add(GetData.getString(1));
            this.mNgay.add(GetData.getString(2));
            this.mKetQua.add(decimalFormat.format(GetData.getDouble(3)));
            this.mThanhToan.add(decimalFormat.format(GetData.getDouble(4)));
            this.mLuy_ke.add(decimalFormat.format(GetData.getDouble(5)));
        }
        this.lv_congno.setAdapter((ListAdapter) new Congno_Adapter(this, R.layout.activity_congno_lv, this.mNgay));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_congno;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congno);
        this.db = new Database(this);
        this.lv_congno = (ListView) findViewById(R.id.lv_congno);
        this.tv_tenKH = findViewById(R.id.tv_tenKH);
        this.btn_congno = findViewById(R.id.btn_congno);
        this.message = getIntent().getStringExtra("tenKH");
        this.tv_tenKH.setText("Khách hàng: " + this.message);
        this.btn_congno.setOnClickListener(view -> {
            if (Activity_Congno.this.isFinishing()) {
                return;
            }
            Activity_Congno.this.showDialog1(1);
        });
        this.lv_congno.setOnItemClickListener((adapterView, view, i, l) -> {
            if (Activity_Congno.this.isFinishing()) {
                return;
            }
            Activity_Congno.this.showDialog2(i);
        });
        Congno_report_listview();
    }

    public void showDialog1(int poin) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.frag_morp1_1);
        dialog.getWindow().setLayout(-1, -2);
        final EditText editText = dialog.findViewById(R.id.edt_thanhtoan);
        Button button = dialog.findViewById(R.id.btn_chinhsua);
        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        Cursor GetData = this.db.GetData("Select sum(ket_qua)/1000 From tbl_soctS WHere ten_kh = '" + this.message + "' AND the_loai = 'cn'");
        GetData.moveToFirst();
        editText.setText(decimalFormat.format(GetData.getDouble(0)));
        if (GetData != null && !GetData.isClosed()) {
            GetData.close();
        }
        editText.addTextChangedListener(new TextWatcher() {
            int len = 0;
            String str;

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                this.len = editText.getText().toString().length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String obj = editText.getText().toString();
                this.str = obj;
                if (obj.length() == 0) {
                    editText.setText("0");
                    return;
                }
                if (this.len == this.str.length() || this.len <= 2) {
                    return;
                }
                try {
                    DecimalFormat decimalFormat2 = new DecimalFormat("###,###");
                    String replaceAll = this.str.replaceAll("[$,.]", "");
                    this.str = replaceAll;
                    String format = decimalFormat2.format(Double.parseDouble(replaceAll));
                    this.str = format;
                    editText.setText(format);
                    editText.setSelection(this.str.length());
                } catch (Exception unused) {
                }
            }
        });
        button.setOnClickListener(view -> {
            if (Congthuc.isNumeric(editText.getText().toString().replaceAll("\\.", "").replace("-", ""))) {
                Cursor GetData2 = Activity_Congno.this.db.GetData("Select count(id) From tbl_soctS WHere ten_kh = '" + Activity_Congno.this.message + "' AND the_loai = 'cn'");
                GetData2.moveToFirst();
                if (GetData2.getInt(0) == 0) {
                    Cursor GetData3 = Activity_Congno.this.db.GetData("Select min(ngay_nhan), so_dienthoai From tbl_soctS Where ten_kh = '" + Activity_Congno.this.message + "'");
                    GetData3.moveToFirst();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Calendar calendar = Calendar.getInstance();
                    try {
                        calendar.setTime(simpleDateFormat.parse(GetData3.getString(0)));
                    } catch (Exception unused) {
                    }
                    calendar.add(Calendar.DATE, -1);
                    String format = simpleDateFormat.format(new Date(calendar.getTimeInMillis()));
                    Activity_Congno.this.db.QueryData("Insert Into tbl_soctS (ngay_nhan, ten_kh, so_dienthoai, the_loai, ket_qua, diem_quydoi) Values ('" + format + "','" + Activity_Congno.this.message + "','" + GetData3.getString(1) + "', 'cn'," + editText.getText().toString().replaceAll("\\.", "") + "000,1)");
                    Activity_Congno.this.Congno_report_listview();
                    if (GetData3 != null && !GetData3.isClosed()) {
                        GetData3.close();
                    }
                } else {
                    Activity_Congno.this.db.QueryData("Update tbl_soctS set ket_qua = " + editText.getText().toString().replaceAll("\\.", "") + "000 WHere ten_kh = '" + Activity_Congno.this.message + "' AND the_loai = 'cn'");
                    Activity_Congno.this.Congno_report_listview();
                }
                dialog.cancel();
            }
        });
        dialog.setCancelable(true);
        dialog.show();
    }

    public void showDialog2(final int poin) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.frag_morp1_2);
        dialog.getWindow().setLayout(-1, -2);
        final EditText editText = dialog.findViewById(R.id.edt_thanhtoan);
        Button button = dialog.findViewById(R.id.btn_chinhsua);
        ((TextView)dialog.findViewById(R.id.tv_ngaytt)).setText(this.mNgay.get(poin));
        editText.addTextChangedListener(new TextWatcher() {
            int len = 0;
            String str;

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                this.len = editText.getText().toString().length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String obj = editText.getText().toString();
                this.str = obj;
                if (obj.length() == 0) {
                    editText.setText("0");
                    return;
                }
                if (this.len == this.str.length() || this.len <= 2) {
                    return;
                }
                try {
                    DecimalFormat decimalFormat = new DecimalFormat("###,###");
                    String replaceAll = this.str.replaceAll("[$,.]", "");
                    this.str = replaceAll;
                    String format = decimalFormat.format(Double.parseDouble(replaceAll));
                    this.str = format;
                    editText.setText(format);
                    editText.setSelection(this.str.length());
                } catch (Exception unused) {
                }
            }
        });
        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        Cursor GetData = this.db.GetData("Select sum(ket_qua)/1000 From tbl_soctS WHere ten_kh = '" + this.message + "' AND the_loai = 'tt' And ngay_nhan = '" + this.mNgayNhan.get(poin) + "'");
        GetData.moveToFirst();
        editText.setText(decimalFormat.format(GetData.getDouble(0)));
        if (GetData != null && !GetData.isClosed()) {
            GetData.close();
        }
        button.setOnClickListener(view -> {
            if (Congthuc.isNumeric(editText.getText().toString().replaceAll("\\.", "").replace("-", ""))) {
                Cursor GetData2 = Activity_Congno.this.db.GetData("Select count(id) From tbl_soctS WHere ten_kh = '" + Activity_Congno.this.message + "' AND the_loai = 'tt' AND ngay_nhan = '" + Activity_Congno.this.mNgayNhan.get(poin) + "'");
                GetData2.moveToFirst();
                if (GetData2.getInt(0) == 0) {
                    Activity_Congno.this.db.QueryData("Insert Into tbl_soctS (ngay_nhan, ten_kh, so_dienthoai, the_loai, ket_qua, diem_quydoi) Values ('" + Activity_Congno.this.mNgayNhan.get(poin) + "','" + Activity_Congno.this.message + "','" + Activity_Congno.this.mSdt.get(poin) + "', 'tt'," + editText.getText().toString().replaceAll("\\.", "") + "000,1)");
                    Activity_Congno.this.Congno_report_listview();
                } else {
                    Activity_Congno.this.db.QueryData("Update tbl_soctS set ket_qua = " + editText.getText().toString().replaceAll("\\.", "") + "000 WHere ten_kh = '" + Activity_Congno.this.message + "' AND the_loai = 'tt' AND ngay_nhan = '" + Activity_Congno.this.mNgayNhan.get(poin) + "'");
                    Activity_Congno.this.Congno_report_listview();
                }
                if (GetData2 != null && !GetData2.isClosed()) {
                    GetData2.close();
                }
                dialog.cancel();
            }
        });
        dialog.setCancelable(true);
        dialog.show();
    }
}
