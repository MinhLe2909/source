package tamhoang.ldpro4.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import tamhoang.ldpro4.ui.activity.Activity_Congno;
import tamhoang.ldpro4.ui.home.MainActivity;
import tamhoang.ldpro4.R;
import tamhoang.ldpro4.data.Database;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import okhttp3.internal.cache.DiskLruCache;

/* loaded from: classes.dex */
public class Frag_MoRP1 extends Fragment {
    TextView TienNoCu;
    TextView TienPhatSinh;
    TextView TienSoCuoi;
    Database db;
    Handler handler;
    ListView lv_Morp;
    String ngayChon;
    View v;
    public List<String> mKhachHang = new ArrayList();
    public List<String> mNocu = new ArrayList();
    public List<String> mPhatSinh = new ArrayList();
    public List<String> mSdt = new ArrayList();
    public List<String> mSoCuoi = new ArrayList();
    public List<String> mtype = new ArrayList();
    int mPoistion = 0;
    String pattern = "###,###";
    DecimalFormat decimalFormat = new DecimalFormat(this.pattern);
    private Runnable runnable = new Runnable() { // from class: tamhoang.ldpro4.Fragment.Frag_MoRP1.1
        @Override // java.lang.Runnable
        public void run() {
            if (MainActivity.sms) {
                Frag_MoRP1.this.money_lv();
                MainActivity.sms = false;
            }
            Frag_MoRP1.this.handler.postDelayed(this, 1000L);
        }
    };

    public class MoneyReport extends ArrayAdapter {
        public MoneyReport(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
        }

        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            View inflate = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.frag_morp1_lv, (ViewGroup) null);
            TextView textView =  inflate.findViewById(R.id.tv_KhachHang);
            textView.setText(Frag_MoRP1.this.mKhachHang.get(position));
            TextView textView2 =  inflate.findViewById(R.id.tv_nocu);
            textView2.setText(Frag_MoRP1.this.mNocu.get(position));
            TextView textView3 =  inflate.findViewById(R.id.tv_phatsinh);
            textView3.setText(Frag_MoRP1.this.mPhatSinh.get(position));
            TextView textView4 =  inflate.findViewById(R.id.tv_tienton);
            textView4.setText(Frag_MoRP1.this.mSoCuoi.get(position));
            if (Frag_MoRP1.this.mtype.get(position).indexOf(DiskLruCache.VERSION_1) == -1) {
                textView.setTextColor(-16776961);
                textView2.setTextColor(-16776961);
                textView3.setTextColor(-16776961);
                textView4.setTextColor(-16776961);
            }
            return inflate;
        }
    }

    public void itemClick(View v2) {
        String[] strArr = {"Xem phát sinh chi tiết", "Xóa khách này"};
        PopupMenu popupMenu = new PopupMenu(getActivity(), v2);
        for (int i = 0; i < 2; i++) {
            popupMenu.getMenu().add(1, i, i, strArr[i]);
        }
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() { // from class: tamhoang.ldpro4.Fragment.Frag_MoRP1.4
            @Override // android.widget.PopupMenu.OnMenuItemClickListener
            public boolean onMenuItemClick(MenuItem menuItem) {
                int order = menuItem.getOrder();
                if (order == 0) {
                    Intent intent = new Intent(Frag_MoRP1.this.getActivity(), (Class<?>) Activity_Congno.class);
                    intent.putExtra("tenKH", Frag_MoRP1.this.mKhachHang.get(Frag_MoRP1.this.mPoistion));
                    Frag_MoRP1.this.startActivity(intent);
                    return false;
                }
                if (order != 1) {
                    return false;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(Frag_MoRP1.this.getActivity());
                builder.setTitle("Xóa hết số liệu khách này?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() { // from class: tamhoang.ldpro4.Fragment.Frag_MoRP1.4.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialog, int which) {
                        Frag_MoRP1.this.db.QueryData("Delete FROM tbl_tinnhanS WHERE ten_kh = '" + Frag_MoRP1.this.mKhachHang.get(Frag_MoRP1.this.mPoistion) + "'");
                        Frag_MoRP1.this.db.QueryData("Delete FROM tbl_soctS WHERE ten_kh = '" + Frag_MoRP1.this.mKhachHang.get(Frag_MoRP1.this.mPoistion) + "'");
                        Frag_MoRP1.this.money_lv();
                        Toast.makeText(Frag_MoRP1.this.getActivity(), "Xoá thành công", Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() { // from class: tamhoang.ldpro4.Fragment.Frag_MoRP1.4.2
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.create().show();
                return false;
            }
        });
    }

    public void money_lv() {
        this.mKhachHang.clear();
        this.mSdt.clear();
        this.mNocu.clear();
        this.mPhatSinh.clear();
        this.mSoCuoi.clear();
        this.mtype.clear();
        String Get_date = MainActivity.Get_date();
        Cursor GetData = this.db.GetData("Select tbl_soctS.ten_kh\n, SUM((tbl_soctS.ngay_nhan < '" + Get_date + "') * tbl_soctS.ket_qua * (100-tbl_soctS.diem_khachgiu)/100)/1000  as NoCu   \n, SUM((tbl_soctS.ngay_nhan = '" + Get_date + "') * tbl_soctS.ket_qua * (100-tbl_soctS.diem_khachgiu)/100)/1000  as PhatSinh   \n, SUM((tbl_soctS.ngay_nhan <= '" + Get_date + "')*tbl_soctS.ket_qua*(100-tbl_soctS.diem_khachgiu)/100)/1000 as SoCuoi, tbl_soctS.so_dienthoai, tbl_kh_new.type_kh  \nFROM tbl_soctS INNER JOIN tbl_kh_new ON tbl_soctS.so_dienthoai = tbl_kh_new.sdt\nGROUP BY tbl_soctS.ten_kh ORDER BY tbl_soctS.type_kh DESC");
        if (GetData != null) {
            double d = 0.0d;
            double d2 = 0.0d;
            double d3 = 0.0d;
            while (GetData.moveToNext()) {
                this.mKhachHang.add(GetData.getString(0));
                this.mSdt.add(GetData.getString(4));
                this.mNocu.add(this.decimalFormat.format(GetData.getDouble(1)));
                this.mPhatSinh.add(this.decimalFormat.format(GetData.getDouble(2)));
                this.mSoCuoi.add(this.decimalFormat.format(GetData.getDouble(3)));
                this.mtype.add(GetData.getString(5));
                d += GetData.getDouble(1);
                d2 += GetData.getDouble(2);
                d3 += GetData.getDouble(3);
            }
            this.TienNoCu.setText(this.decimalFormat.format(-d));
            this.TienPhatSinh.setText(this.decimalFormat.format(-d2));
            this.TienSoCuoi.setText(this.decimalFormat.format(-d3));
        }
        if (getActivity() != null) {
            this.lv_Morp.setAdapter((ListAdapter) new MoneyReport(getActivity(), R.layout.frag_morp1_lv, this.mKhachHang));
        }
    }

    @Override // android.support.v4.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.v = inflater.inflate(R.layout.frag_morp1, container, false);
        this.db = new Database(getActivity());
        this.lv_Morp = (ListView) this.v.findViewById(R.id.lv_mo_rp1);
        this.TienNoCu =  this.v.findViewById(R.id.TienNoCu);
        this.TienPhatSinh =  this.v.findViewById(R.id.TienPhatSinh);
        this.TienSoCuoi =  this.v.findViewById(R.id.TienSoCuoi);
        money_lv();
        this.ngayChon = MainActivity.Get_date();
        this.lv_Morp.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() { // from class: tamhoang.ldpro4.Fragment.Frag_MoRP1.2
            @Override // android.widget.AdapterView.OnItemLongClickListener
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                Frag_MoRP1.this.mPoistion = position;
                return false;
            }
        });
        this.lv_Morp.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: tamhoang.ldpro4.Fragment.Frag_MoRP1.3
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Frag_MoRP1.this.mPoistion = i;
                Frag_MoRP1.this.itemClick(view);
            }
        });
        Handler handler = new Handler();
        this.handler = handler;
        handler.postDelayed(this.runnable, 1000L);
        return this.v;
    }

    @Override // android.support.v4.app.Fragment
    public void onDestroy() {
        try {
            this.mKhachHang.clear();
            this.mSdt.clear();
            this.mNocu.clear();
            this.mPhatSinh.clear();
            this.mSoCuoi.clear();
            this.mtype.clear();
            this.db.close();
        } catch (Exception unused) {
        }
        super.onDestroy();
    }

    @Override // android.support.v4.app.Fragment
    public void onResume() {
        super.onResume();
        money_lv();
    }

    @Override // android.support.v4.app.Fragment
    public void onStop() {
        super.onStop();
        this.handler.removeCallbacks(this.runnable);
    }
}
