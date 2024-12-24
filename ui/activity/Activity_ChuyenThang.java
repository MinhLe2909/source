package tamhoang.ldpro4.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import tamhoang.ldpro4.base.BaseToolBarActivity;
import tamhoang.ldpro4.R;
import tamhoang.ldpro4.data.Database;

public class Activity_ChuyenThang extends BaseToolBarActivity {
    Button add_chuyen;
    Database db;
    ListView lv_chuyenthang;
    RadioButton rad_chuyenthang;
    RadioButton rad_sauxuly;
    int sp_Chu;
    int sp_KH;
    Spinner spin_Chu;
    Spinner spin_KH;
    public List<String> nameChu = new ArrayList();
    public List<String> nameKhach = new ArrayList();
    public List<String> sdtChu = new ArrayList();
    public List<String> sdtKhach = new ArrayList();
    public List<String> sdt_Chu = new ArrayList();
    public List<String> sdt_KH = new ArrayList();
    public List<String> ten_Chu = new ArrayList();
    public List<String> ten_KH = new ArrayList();

    public class CTAdapter extends ArrayAdapter {
        public CTAdapter(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
        }

        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        public View getView(final int position, View convertView, ViewGroup parent) {
            View inflate = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.activity_chuyenthang_lv, (ViewGroup) null);
             ( (TextView)inflate.findViewById(R.id.tv_stt)).setText((position + 1) + "");
            ((TextView) inflate.findViewById(R.id.tv_khach)).setText(ten_KH.get(position));
            ((TextView) inflate.findViewById(R.id.tv_chu)).setText(ten_Chu.get(position));
            ((TextView) inflate.findViewById(R.id.tv_delete)).setOnClickListener(new View.OnClickListener() { // from class: tamhoang.ldpro4.Activity.Activity_ChuyenThang.CTAdapter.1
                @Override // android.view.View.OnClickListener
                public void onClick(View v) {
                    db.QueryData("Delete FROM tbl_chuyenthang WHERE sdt_nhan = '" + sdt_KH.get(position) + "'");
                    xem_lv();
                }
            });
            return inflate;
        }
    }

    @Override // tamhoang.ldpro4.Congthuc.BaseToolBarActivity
    public int getLayoutId() {
        return R.layout.activity_chuyenthang;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chuyenthang);
        this.db = new Database(this);
        this.add_chuyen =  findViewById(R.id.add_Chuyenthang);
        this.spin_KH = (Spinner) findViewById(R.id.spinter_KH);
        this.spin_Chu = (Spinner) findViewById(R.id.spinter_Chu);
        this.lv_chuyenthang = (ListView) findViewById(R.id.lv_ChuyenThang);
        this.nameKhach.clear();
        this.sdtKhach.clear();
        Cursor GetData = this.db.GetData("Select * From tbl_kh_new WHERE type_kh = 1 ORDER by ten_kh");
        if (GetData != null && GetData.getCount() > 0) {
            while (GetData.moveToNext()) {
                this.nameKhach.add(GetData.getString(0));
                this.sdtKhach.add(GetData.getString(1));
            }
            GetData.close();
        }
        this.spin_KH.setAdapter((SpinnerAdapter) new ArrayAdapter(this, R.layout.spinner_item, this.nameKhach));
        this.nameChu.clear();
        this.sdtChu.clear();
        Cursor GetData2 = this.db.GetData("Select * From tbl_kh_new WHERE type_kh <> 1 ORDER by ten_kh");
        if (GetData2 != null && GetData2.getCount() > 0) {
            while (GetData2.moveToNext()) {
                this.nameChu.add(GetData2.getString(0));
                this.sdtChu.add(GetData2.getString(1));
            }
            GetData2.close();
        }
        this.spin_Chu.setAdapter((SpinnerAdapter) new ArrayAdapter(this, R.layout.spinner_item, this.nameChu));
        this.spin_KH.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: tamhoang.ldpro4.Activity.Activity_ChuyenThang.1

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                sp_KH = position;
                Cursor GetData3 = db.GetData("Select * from tbl_chuyenthang where sdt_nhan = '" + sdtKhach.get(sp_KH) + "'");
                if (GetData3 == null || GetData3.getCount() <= 0) {
                    return;
                }
                GetData3.moveToFirst();
                spin_Chu.setSelection(sdtChu.indexOf(GetData3.getString(4)));
                GetData3.close();
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        this.spin_Chu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: tamhoang.ldpro4.Activity.Activity_ChuyenThang.
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                sp_Chu = position;
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        this.add_chuyen.setOnClickListener(new View.OnClickListener() { // from class: tamhoang.ldpro4.Activity.Activity_ChuyenThang.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Cursor cursor;
                try {
                    cursor = db.GetData("Select * From tbl_chuyenthang WHERE kh_nhan = '" + nameKhach.get(sp_KH) + "'");
                    cursor.moveToFirst();
                } catch (Exception unused) {
                    Toast.makeText(Activity_ChuyenThang.this, "Thêm lỗi!", Toast.LENGTH_LONG).show();
                    return;
                }
                if (cursor.getCount() == 0 && sp_Chu > -1) {
                    db.QueryData("Insert into tbl_chuyenthang Values (null, '" + nameKhach.get(sp_KH) + "', '" + sdtKhach.get(sp_KH) + "', '" + nameChu.get(sp_Chu) + "', '" + sdtChu.get(sp_Chu) + "')");
                    Toast.makeText(Activity_ChuyenThang.this, "Đã thêm!", Toast.LENGTH_LONG).show();
                    cursor.close();
                    xem_lv();
                }
                db.QueryData("UPDATE tbl_chuyenthang set kh_chuyen = '" + nameChu.get(sp_Chu) + "', sdt_chuyen = '" + sdtChu.get(sp_Chu) + "' Where sdt_nhan = '" + sdtKhach.get(sp_KH) + "'");
                Toast.makeText(Activity_ChuyenThang.this, "Đã sửa!", Toast.LENGTH_LONG).show();
                cursor.close();
                xem_lv();
            }
        });
        this.lv_chuyenthang.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: tamhoang.ldpro4.Activity.Activity_ChuyenThang.4
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                int indexOf = nameKhach.indexOf(ten_KH.get(position));
                int indexOf2 = nameChu.indexOf(ten_Chu.get(position));
                spin_KH.setSelection(indexOf);
                spin_Chu.setSelection(indexOf2);
            }
        });
        this.rad_chuyenthang = (RadioButton) findViewById(R.id.rad_chuyenngay);
        this.rad_sauxuly = (RadioButton) findViewById(R.id.rad_chuyensauxl);
        this.rad_chuyenthang.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: tamhoang.ldpro4.Activity.Activity_ChuyenThang.5
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (rad_chuyenthang.isChecked()) {
                    db.QueryData("UPDATE So_Om set Om_Xi3 = 0 WHERE ID = 13");
                }
            }
        });
        this.rad_sauxuly.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: tamhoang.ldpro4.Activity.Activity_ChuyenThang.6
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (rad_sauxuly.isChecked()) {
                    db.QueryData("UPDATE So_Om set Om_Xi3 = 1 WHERE ID = 13");
                }
            }
        });
        Cursor cusor = this.db.GetData("Select Om_Xi3 From so_om WHERE id = 13");
        if (cusor != null && cusor.moveToFirst()) {
            if (cusor.getInt(0) == 0) {
                this.rad_chuyenthang.setChecked(true);
                this.rad_sauxuly.setChecked(false);
            } else {
                this.rad_sauxuly.setChecked(true);
                this.rad_chuyenthang.setChecked(false);
            }
            if (!cusor.isClosed()) {
                cusor.close();
            }
        }
        xem_lv();
    }

    public void xem_lv() {
        this.ten_KH.clear();
        this.sdt_KH.clear();
        this.ten_Chu.clear();
        this.sdt_Chu.clear();
        Cursor GetData = this.db.GetData("Select * From tbl_chuyenthang");
        while (GetData.moveToNext()) {
            this.ten_KH.add(GetData.getString(1));
            this.sdt_KH.add(GetData.getString(2));
            this.ten_Chu.add(GetData.getString(3));
            this.sdt_Chu.add(GetData.getString(4));
        }
        GetData.close();
        this.lv_chuyenthang.setAdapter((ListAdapter) new CTAdapter(this, R.layout.activity_chuyenthang_lv, this.ten_KH));
    }
}
