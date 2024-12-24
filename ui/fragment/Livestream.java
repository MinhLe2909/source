package tamhoang.ldpro4.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import tamhoang.ldpro4.ui.home.MainActivity;
import tamhoang.ldpro4.R;
import tamhoang.ldpro4.data.Database;

/* loaded from: classes.dex */
public class Livestream extends Fragment {
    MainActivity activity;
    Database db;
    Handler handler;
    LinearLayout ln1;
    ListView lvLivestrem;
    String mDate;
    RadioButton radio_dea;
    RadioButton radio_deb;
    RadioButton radio_dec;
    RadioButton radio_ded;
    int so;
    Switch switch1;
    TextView tvChuy;
    public List<Integer> mDemso = new ArrayList();
    public List<String> mDiem = new ArrayList();
    public List<Double> mThangthua = new ArrayList();
    public List<Double> mTongTien = new ArrayList();
    String th_loai = "the_loai = 'deb'";
    private final Runnable runnable = new Runnable() { // from class: tamhoang.ldpro4.Fragment.Livestream.1
        @Override // java.lang.Runnable
        public void run() {
            if (MainActivity.sms) {
                xem_lv();
                MainActivity.sms = false;
            }
            handler.postDelayed(this, 1000L);
        }
    };

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: tamhoang.ldpro4.Fragment.Livestream$1 */
    /* loaded from: classes.dex */
    public class AnonymousClass1 implements Runnable {
        AnonymousClass1() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (MainActivity.sms) {
                xem_lv();
                MainActivity.sms = false;
            }
            handler.postDelayed(this, 1000L);
        }
    }

    /* renamed from: tamhoang.ldpro4.Fragment.Livestream$2 */
    /* loaded from: classes.dex */
    class AnonymousClass2 implements AdapterView.OnItemClickListener {
        AnonymousClass2() {
        }

        @Override // android.widget.AdapterView.OnItemClickListener
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        }
    }

    /* renamed from: tamhoang.ldpro4.Fragment.Livestream$3 */
    /* loaded from: classes.dex */
    class AnonymousClass3 implements CompoundButton.OnCheckedChangeListener {
        AnonymousClass3() {
        }

        @Override // android.widget.CompoundButton.OnCheckedChangeListener
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (switch1.isChecked()) {
                ln1.setVisibility(View.VISIBLE);
            } else {
                ln1.setVisibility(View.GONE);
            }
        }
    }

    /* renamed from: tamhoang.ldpro4.Fragment.Livestream$4 */
    /* loaded from: classes.dex */
    class AnonymousClass4 implements CompoundButton.OnCheckedChangeListener {
        AnonymousClass4() {
        }

        @Override // android.widget.CompoundButton.OnCheckedChangeListener
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (radio_dea.isChecked()) {
                th_loai = "the_loai = 'dea'";
                xem_lv();
            }
        }
    }

    /* renamed from: tamhoang.ldpro4.Fragment.Livestream$5 */
    /* loaded from: classes.dex */
    class AnonymousClass5 implements CompoundButton.OnCheckedChangeListener {
        AnonymousClass5() {
        }

        @Override // android.widget.CompoundButton.OnCheckedChangeListener
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (radio_deb.isChecked()) {
                th_loai = "the_loai = 'deb'";
                xem_lv();
            }
        }
    }

    /* renamed from: tamhoang.ldpro4.Fragment.Livestream$6 */
    /* loaded from: classes.dex */
    class AnonymousClass6 implements CompoundButton.OnCheckedChangeListener {
        AnonymousClass6() {
        }

        @Override // android.widget.CompoundButton.OnCheckedChangeListener
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (radio_dec.isChecked()) {
                th_loai = "the_loai = 'dec'";
                xem_lv();
            }
        }
    }

    /* renamed from: tamhoang.ldpro4.Fragment.Livestream$7 */
    /* loaded from: classes.dex */
    class AnonymousClass7 implements CompoundButton.OnCheckedChangeListener {
        AnonymousClass7() {
        }

        @Override // android.widget.CompoundButton.OnCheckedChangeListener
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (radio_ded.isChecked()) {
                th_loai = "the_loai = 'ded'";
                xem_lv();
            }
        }
    }

    /* loaded from: classes.dex */
    public class TNGAdapter extends ArrayAdapter {
        DecimalFormat decimalFormat;
        String pattern;

        public TNGAdapter(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
            this.pattern = "###,###";
            this.decimalFormat = new DecimalFormat(this.pattern);
        }

        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            View inflate = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.activity_livestream_lv, (ViewGroup) null);
            ((TextView) inflate.findViewById(R.id.tv_diem)).setText(mDiem.get(position));
            ((TextView) inflate.findViewById(R.id.tv_so)).setText(mDemso.get(position) + "");
            ((TextView) inflate.findViewById(R.id.tv_tiengiu)).setText(this.decimalFormat.format(mTongTien.get(position)));
            ((TextView) inflate.findViewById(R.id.tv_ThangThua)).setText(this.decimalFormat.format(mThangthua.get(position)));
            return inflate;
        }
    }

    @Override // android.support.v4.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View inflate = inflater.inflate(R.layout.activity_livestream, container, false);
        this.db = new Database(getActivity());
        this.lvLivestrem = (ListView) inflate.findViewById(R.id.lv_livestrem);
        this.tvChuy =  inflate.findViewById(R.id.tv_chu_y);
        this.switch1 = (Switch) inflate.findViewById(R.id.switch1);
        this.ln1 =  inflate.findViewById(R.id.ln1);
        this.radio_dea = (RadioButton) inflate.findViewById(R.id.radio_Dea);
        this.radio_deb = (RadioButton) inflate.findViewById(R.id.radio_Deb);
        this.radio_dec = (RadioButton) inflate.findViewById(R.id.radio_Dec);
        this.radio_ded = (RadioButton) inflate.findViewById(R.id.radio_Ded);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        simpleDateFormat.setTimeZone(TimeZone.getDefault());
        this.mDate = simpleDateFormat.format(calendar.getTime());
        this.lvLivestrem.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: tamhoang.ldpro4.Fragment.Livestream.2
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            }
        });
        this.switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: tamhoang.ldpro4.Fragment.Livestream.3
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (switch1.isChecked()) {
                    ln1.setVisibility(View.VISIBLE);
                } else {
                    ln1.setVisibility(View.GONE);
                }
            }
        });
        Handler handler = new Handler();
        this.handler = handler;
        handler.postDelayed(this.runnable, 1000L);
        this.radio_dea.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: tamhoang.ldpro4.Fragment.Livestream.4
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (radio_dea.isChecked()) {
                    th_loai = "the_loai = 'dea'";
                    xem_lv();
                }
            }
        });
        this.radio_deb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: tamhoang.ldpro4.Fragment.Livestream.5
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (radio_deb.isChecked()) {
                    th_loai = "the_loai = 'deb'";
                    xem_lv();
                }
            }
        });
        this.radio_dec.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: tamhoang.ldpro4.Fragment.Livestream.6
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (radio_dec.isChecked()) {
                    th_loai = "the_loai = 'dec'";
                    xem_lv();
                }
            }
        });
        this.radio_ded.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: tamhoang.ldpro4.Fragment.Livestream.7
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (radio_ded.isChecked()) {
                    th_loai = "the_loai = 'ded'";
                    xem_lv();
                }
            }
        });
        xem_lv();
        return inflate;
    }

    @Override // android.support.v4.app.Fragment
    public void onDestroy() {
        this.mDiem.clear();
        this.mDemso.clear();
        this.mTongTien.clear();
        this.mThangthua.clear();
        this.lvLivestrem.setAdapter(null);
        this.db.close();
        super.onDestroy();
    }

    @Override // android.support.v4.app.Fragment
    public void onStop() {
        super.onStop();
        this.handler.removeCallbacks(this.runnable);
    }

    public void xem_lv() {
        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        String Get_date = MainActivity.Get_date();
        Cursor GetData = this.db.GetData("SELECT count(mycount) AS dem FROM (SELECT sum((type_kh = 1) *diem_ton) - sum((type_kh = 2) * diem_ton) AS mycount FROM tbl_soctS WHERE " + this.th_loai + " AND ngay_nhan = '" + Get_date + "' GROUP BY so_chon ) a");
        GetData.moveToFirst();
        int i = GetData.getInt(0);
        this.so = i;
        if (i > 0) {
            this.tvChuy.setText("Có " + (100 - GetData.getInt(0)) + " số 0 đồng");
        } else {
            this.tvChuy.setText("Chưa có dữ liệu ngày hôm nay.");
        }
        this.mDiem.clear();
        this.mDemso.clear();
        this.mTongTien.clear();
        this.mThangthua.clear();
        Cursor GetData2 = this.db.GetData("SELECT moctien, count(moctien) AS dem\nFROM (Select (Sum((tbl_soctS.type_kh =1) * tbl_soctS.diem_ton) - Sum((tbl_soctS.type_kh =2) * tbl_soctS.diem_ton) - so_om.Om_DeB ) as moctien\nFrom so_om Left Join tbl_soctS On tbl_soctS.so_chon = so_om.So \nWhere tbl_soctS.ngay_nhan='" + Get_date + "' AND (tbl_soctS." + this.th_loai + " OR tbl_soctS.the_loai='det') \nGROUP by so_om.So order by moctien DESC) as a \nGROUP BY moctien ORDER BY moctien DESC");
        while (GetData2.moveToNext()) {
            this.mDiem.add(decimalFormat.format(GetData2.getDouble(0)));
            this.mDemso.add(Integer.valueOf(GetData2.getInt(1)));
        }
        if (this.mDiem.size() > 0) {
            for (int i2 = 0; i2 < this.mDiem.size(); i2++) {
                int parseInt = Integer.parseInt(this.mDiem.get(i2).replaceAll("\\.", ""));
                double d = parseInt * 100;
                if (i2 < this.mDiem.size()) {
                    for (int i3 = i2 + 1; i3 < this.mDiem.size(); i3++) {
                        double parseInt2 = (parseInt - Integer.parseInt(this.mDiem.get(i3).replaceAll("\\.", ""))) * this.mDemso.get(i3).intValue();
                        Double.isNaN(parseInt2);
                        Double.isNaN(parseInt2);
                        d -= parseInt2;
                    }
                }
                List<Double> list = this.mTongTien;
                double d2 = (100 - this.so) * parseInt;
                Double.isNaN(d2);
                Double.isNaN(d2);
                list.add(Double.valueOf(((d - d2) * 715.0d) / 1000.0d));
                List<Double> list2 = this.mThangthua;
                double d3 = (100 - this.so) * parseInt;
                Double.isNaN(d3);
                double d4 = parseInt * 70;
                Double.isNaN(d4);
                Double.isNaN(d3);
                Double.isNaN(d4);
                list2.add(Double.valueOf((((d - d3) * 715.0d) / 1000.0d) - d4));
            }
        }
        if (getActivity() != null) {
            this.lvLivestrem.setAdapter((ListAdapter) new TNGAdapter(getActivity(), R.layout.activity_livestream_lv, this.mDiem));
        }
        if (GetData2 != null) {
            GetData2.close();
        }
        if (GetData != null) {
            GetData.close();
        }
    }
}
