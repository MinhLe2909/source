package tamhoang.ldpro4.ui.activity;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import tamhoang.ldpro4.base.BaseToolBarActivity;
import tamhoang.ldpro4.ui.home.MainActivity;
import tamhoang.ldpro4.R;
import tamhoang.ldpro4.data.Database;

public class Activity_khach extends BaseToolBarActivity {
    String DangXuat;
    Database db;
    ListView lv_khach;
    private final List<String> mDiem = new ArrayList();
    private final List<String> mDiemGiu = new ArrayList();
    private final List<Integer> mNhay = new ArrayList();
    private final List<String> mSo = new ArrayList();
    private final List<String> mThanhTien = new ArrayList();
    String message;
    RadioButton radio_bc;
    RadioButton radio_de;
    RadioButton radio_lo;
    RadioButton radio_xi;
    TextView textView;

    public class Khach_Adapter extends ArrayAdapter {
        private ViewHolder holder;
        private final LayoutInflater mInflater;

        public class ViewHolder {
            private TextView sochon;
            private TextView stt;
            private TextView tv_diemgiu;
            private TextView tv_diemnhan;
            private TextView tv_thanhtien;

            public ViewHolder() {
            }
        }

        public Khach_Adapter(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(int position, View v, ViewGroup parent) {
            if (v == null) {
                v = this.mInflater.inflate(R.layout.activity_khach_lv, (ViewGroup) null);
                ViewHolder viewHolder = new ViewHolder();
                this.holder = viewHolder;
                viewHolder.stt =  v.findViewById(R.id.stt);
                this.holder.sochon =  v.findViewById(R.id.Tv_so);
                this.holder.tv_diemnhan =  v.findViewById(R.id.tv_diemNhan);
                this.holder.tv_diemgiu =  v.findViewById(R.id.tv_diemGiu);
                this.holder.tv_thanhtien =  v.findViewById(R.id.tv_thanhtien);
                v.setTag(this.holder);
            } else {
                this.holder = (ViewHolder) v.getTag();
            }
            if (((Integer) mNhay.get(position)).intValue() > 0) {
//                this.holder.sochon.setTextColor(SupportMenu.CATEGORY_MASK);
//                this.holder.tv_diemnhan.setTextColor(SupportMenu.CATEGORY_MASK);
//                this.holder.tv_diemgiu.setTextColor(SupportMenu.CATEGORY_MASK);
//                this.holder.tv_thanhtien.setTextColor(SupportMenu.CATEGORY_MASK);
                if (((Integer) mNhay.get(position)).intValue() == 1) {
                    this.holder.sochon.setText(mSo.get(position) + "*");
                } else if (((Integer) mNhay.get(position)).intValue() == 2) {
                    this.holder.sochon.setText(mSo.get(position) + "**");
                } else if (((Integer) mNhay.get(position)).intValue() == 3) {
                    this.holder.sochon.setText(mSo.get(position) + "***");
                } else if (((Integer) mNhay.get(position)).intValue() == 4) {
                    this.holder.sochon.setText(mSo.get(position) + "****");
                }
                this.holder.stt.setText((position + 1) + "");
                this.holder.tv_diemnhan.setText(mDiem.get(position));
                this.holder.tv_diemgiu.setText(mDiemGiu.get(position));
                this.holder.tv_thanhtien.setText(mThanhTien.get(position));
            } else {
//                this.holder.sochon.setTextColor(ViewCompat.MEASURED_STATE_MASK);
//                this.holder.tv_diemnhan.setTextColor(ViewCompat.MEASURED_STATE_MASK);
//                this.holder.tv_diemgiu.setTextColor(ViewCompat.MEASURED_STATE_MASK);
//                this.holder.tv_thanhtien.setTextColor(ViewCompat.MEASURED_STATE_MASK);
                this.holder.stt.setText((position + 1) + "");
                this.holder.sochon.setText(mSo.get(position));
                this.holder.tv_diemnhan.setText(mDiem.get(position));
                this.holder.tv_diemgiu.setText(mDiemGiu.get(position));
                this.holder.tv_thanhtien.setText(mThanhTien.get(position));
            }
            return v;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_khach;
    }

    public void init() {
        this.radio_de = findViewById(R.id.radio_de);
        this.radio_lo = findViewById(R.id.radio_lo);
        this.radio_xi = findViewById(R.id.radio_xi);
        this.radio_bc = findViewById(R.id.radio_bc);
        this.lv_khach = findViewById(R.id.lv_khach);
        this.textView = findViewById(R.id.textView);
    }

    public void lv_Khach() {
        String Get_date = MainActivity.Get_date();
        Cursor GetData = this.db.GetData("Select so_chon, sum(diem_quydoi) as diem, sum(diem_khachgiu*diem_quydoi)/100 as diemgiu, sum((100-diem_khachgiu)*diem_quydoi/100) as diemton, so_nhay From tbl_soctS where ngay_nhan = '" + Get_date + "' AND ten_kh = '" + this.message + "' AND " + this.DangXuat + " GRoup by so_chon order by diem DESC;");
        this.mSo.clear();
        this.mDiem.clear();
        this.mDiemGiu.clear();
        this.mThanhTien.clear();
        this.mNhay.clear();
        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        while (GetData.moveToNext()) {
            this.mSo.add(GetData.getString(0));
            this.mDiem.add(decimalFormat.format(GetData.getDouble(1)));
            this.mDiemGiu.add(decimalFormat.format(GetData.getDouble(2)));
            this.mThanhTien.add(decimalFormat.format(GetData.getDouble(3)));
            this.mNhay.add(Integer.valueOf(GetData.getInt(4)));
        }
        this.lv_khach.setAdapter((ListAdapter) new Khach_Adapter(this, R.layout.activity_khach_lv, this.mSo));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khach);
        this.db = new Database(this);
        init();
        this.message = getIntent().getStringExtra("tenKH");
        ((TextView) findViewById(R.id.textView)).setText("Khách hàng: " + this.message);
        this.radio_de.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (radio_de.isChecked()) {
                DangXuat = "(the_loai = 'deb' or the_loai = 'det')";
                lv_Khach();
            }
        });
        this.radio_lo.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (radio_lo.isChecked()) {
                DangXuat = "the_loai = 'lo'";
                lv_Khach();
            }
        });
        this.radio_xi.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (radio_xi.isChecked()) {
                DangXuat = "the_loai = 'xi'";
                lv_Khach();
            }
        });
        this.radio_bc.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (radio_bc.isChecked()) {
                DangXuat = "the_loai = 'bc'";
                lv_Khach();
            }
        });
        this.radio_de.setChecked(true);
    }
}
