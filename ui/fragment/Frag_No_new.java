package tamhoang.ldpro4.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import tamhoang.ldpro4.formula.Congthuc;
import tamhoang.ldpro4.ui.home.MainActivity;
import tamhoang.ldpro4.R;
import tamhoang.ldpro4.data.Database;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Frag_No_new extends Fragment {
    TextView bc_Chuyen;
    TextView bc_ChuyenAn;
    TextView bc_Nhan;
    TextView bc_NhanAn;
    TextView bca_Chuyen;
    TextView bca_ChuyenAn;
    TextView bca_Nhan;
    TextView bca_NhanAn;
    Button btn_nt;
    JSONObject caidat_tg;
    int currentIndex;
    Database db;
    TextView dea_Chuyen;
    TextView dea_ChuyenAn;
    TextView dea_Nhan;
    TextView dea_NhanAn;
    TextView deb_Chuyen;
    TextView deb_ChuyenAn;
    TextView deb_Nhan;
    TextView deb_NhanAn;
    TextView dec_Chuyen;
    TextView dec_ChuyenAn;
    TextView dec_Nhan;
    TextView dec_NhanAn;
    TextView ded_Chuyen;
    TextView ded_ChuyenAn;
    TextView ded_Nhan;
    TextView ded_NhanAn;
    TextView det_Chuyen;
    TextView det_ChuyenAn;
    TextView det_Nhan;
    TextView det_NhanAn;
    Handler handler;
    JSONObject json;
    List<JSONObject> jsonKhachHang;
    LinearLayout li_bca;
    LinearLayout li_dea;
    LinearLayout li_dec;
    LinearLayout li_ded;
    LinearLayout li_det;
    LinearLayout li_loa;
    LinearLayout li_xia2;
    LinearLayout li_xn;
    TextView lo_Chuyen;
    TextView lo_ChuyenAn;
    TextView lo_Nhan;
    TextView lo_NhanAn;
    TextView loa_Chuyen;
    TextView loa_ChuyenAn;
    TextView loa_Nhan;
    TextView loa_NhanAn;
    ListView lv_baocaoKhach;
    int position;
    private ProgressBar progressBar;
    TextView tv_TongGiu;
    TextView tv_TongTienChuyen;
    TextView tv_TongTienNhan;
    View v;
    TextView xi2_Chuyen;
    TextView xi2_ChuyenAn;
    TextView xi2_Nhan;
    TextView xi2_NhanAn;
    TextView xia2_Chuyen;
    TextView xia2_ChuyenAn;
    TextView xia2_Nhan;
    TextView xia2_NhanAn;
    TextView xn_Chuyen;
    TextView xn_ChuyenAn;
    TextView xn_Nhan;
    TextView xn_NhanAn;
    boolean Running = true;
    boolean isSuccess = false;
    private List<String> mSDT = new ArrayList();
    private List<String> mTenKH = new ArrayList();
    private Runnable runnable = new Runnable() { // from class: tamhoang.ldpro4.Fragment.Frag_No_new.1
        @Override // java.lang.Runnable
        public void run() {
            if (MainActivity.sms) {
                Frag_No_new.this.lv_baoCao();
                MainActivity.sms = false;
            }
            Frag_No_new.this.handler.postDelayed(this, 1000L);
        }
    };

    public class NoRP_TN_Adapter extends ArrayAdapter {
        TextView bc_Chuyen;
        TextView bc_ChuyenAn;
        TextView bc_Nhan;
        TextView bc_NhanAn;
        TextView bca_Chuyen;
        TextView bca_ChuyenAn;
        TextView bca_Nhan;
        TextView bca_NhanAn;
        TextView dea_Chuyen;
        TextView dea_ChuyenAn;
        TextView dea_Nhan;
        TextView dea_NhanAn;
        TextView deb_Chuyen;
        TextView deb_ChuyenAn;
        TextView deb_Nhan;
        TextView deb_NhanAn;
        TextView dec_Chuyen;
        TextView dec_ChuyenAn;
        TextView dec_Nhan;
        TextView dec_NhanAn;
        TextView ded_Chuyen;
        TextView ded_ChuyenAn;
        TextView ded_Nhan;
        TextView ded_NhanAn;
        TextView det_Chuyen;
        TextView det_ChuyenAn;
        TextView det_Nhan;
        TextView det_NhanAn;
        LinearLayout li_bca;
        LinearLayout li_dea;
        LinearLayout li_dec;
        LinearLayout li_ded;
        LinearLayout li_det;
        LinearLayout li_loa;
        LinearLayout li_xi2;
        LinearLayout li_xia2;
        TextView lo_Chuyen;
        TextView lo_ChuyenAn;
        TextView lo_Nhan;
        TextView lo_NhanAn;
        TextView loa_Chuyen;
        TextView loa_ChuyenAn;
        TextView loa_Nhan;
        TextView loa_NhanAn;
        TextView tv_TongKhach;
        TextView tv_TongTienChuyen;
        TextView tv_TongTienNhan;
        TextView tv_tenKH;
        TextView tv_tongtien;
        TextView xi2_Chuyen;
        TextView xi2_ChuyenAn;
        TextView xi2_Nhan;
        TextView xi2_NhanAn;
        TextView xia2_Chuyen;
        TextView xia2_ChuyenAn;
        TextView xia2_Nhan;
        TextView xia2_NhanAn;

        public NoRP_TN_Adapter(Context context, int resource, List<JSONObject> objects) {
            super(context, resource, objects);
        }

        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        public View getView(int position, View mView, ViewGroup parent) {
            CharSequence charSequence;
            View view;
            CharSequence charSequence2;
            String str;
            String str2;
            view = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.frag_norp1_2, null);
            this.tv_tongtien =  view.findViewById(R.id.tv_TongTien);
            this.tv_tenKH =  view.findViewById(R.id.tv_tenKH);
            this.dea_Nhan =  view.findViewById(R.id.dea_Nhan);
            this.deb_Nhan =  view.findViewById(R.id.deb_Nhan);
            this.det_Nhan =  view.findViewById(R.id.det_Nhan);
            this.dec_Nhan =  view.findViewById(R.id.dec_Nhan);
            this.ded_Nhan =  view.findViewById(R.id.ded_Nhan);
            this.lo_Nhan =  view.findViewById(R.id.lo_Nhan);
            this.loa_Nhan =  view.findViewById(R.id.loa_Nhan);
            this.bc_Nhan =  view.findViewById(R.id.bc_Nhan);
            this.bca_Nhan =  view.findViewById(R.id.bca_Nhan);
            this.dea_NhanAn =  view.findViewById(R.id.dea_NhanAn);
            this.deb_NhanAn =  view.findViewById(R.id.deb_NhanAn);
            this.det_NhanAn =  view.findViewById(R.id.det_NhanAn);
            this.dec_NhanAn =  view.findViewById(R.id.dec_NhanAn);
            this.ded_NhanAn =  view.findViewById(R.id.ded_NhanAn);
            this.lo_NhanAn =  view.findViewById(R.id.lo_NhanAn);
            this.loa_NhanAn =  view.findViewById(R.id.loa_NhanAn);
            this.bc_NhanAn =  view.findViewById(R.id.bc_NhanAn);
            this.dea_Chuyen =  view.findViewById(R.id.dea_Chuyen);
            this.deb_Chuyen =  view.findViewById(R.id.deb_Chuyen);
            this.det_Chuyen =  view.findViewById(R.id.det_Chuyen);
            this.dec_Chuyen =  view.findViewById(R.id.dec_Chuyen);
            this.ded_Chuyen =  view.findViewById(R.id.ded_Chuyen);
            this.lo_Chuyen =  view.findViewById(R.id.lo_Chuyen);
            this.loa_Chuyen =  view.findViewById(R.id.loa_Chuyen);
            this.bc_Chuyen =  view.findViewById(R.id.bc_Chuyen);
            this.dea_ChuyenAn =  view.findViewById(R.id.dea_ChuyenAn);
            this.deb_ChuyenAn =  view.findViewById(R.id.deb_ChuyenAn);
            this.det_ChuyenAn =  view.findViewById(R.id.det_ChuyenAn);
            this.dec_ChuyenAn =  view.findViewById(R.id.dec_ChuyenAn);
            this.ded_ChuyenAn =  view.findViewById(R.id.ded_ChuyenAn);
            this.lo_ChuyenAn =  view.findViewById(R.id.lo_ChuyenAn);
            this.loa_ChuyenAn =  view.findViewById(R.id.loa_ChuyenAn);
            this.bc_ChuyenAn =  view.findViewById(R.id.bc_ChuyenAn);
            this.tv_TongKhach =  view.findViewById(R.id.tv_TongTien);
            this.tv_TongTienNhan =  view.findViewById(R.id.tv_TongTienNhan);
            this.tv_TongTienChuyen =  view.findViewById(R.id.tv_TongTienChuyen);
            this.li_dea =  view.findViewById(R.id.li_dea);
            this.li_det =  view.findViewById(R.id.li_det);
            this.li_dec =  view.findViewById(R.id.li_dec);
            this.li_ded =  view.findViewById(R.id.li_ded);
            this.li_loa =  view.findViewById(R.id.li_loa);
            this.li_bca =  view.findViewById(R.id.li_bca);
            this.li_xi2 =  view.findViewById(R.id.li_xi2);
            this.li_xia2 =  view.findViewById(R.id.li_xia2);
            this.xi2_Nhan =  view.findViewById(R.id.xi2_Nhan);
            this.xi2_NhanAn =  view.findViewById(R.id.xi2_NhanAn);
            this.xi2_Chuyen =  view.findViewById(R.id.xi2_Chuyen);
            this.xi2_ChuyenAn =  view.findViewById(R.id.xi2_ChuyenAn);
            this.xia2_Nhan =  view.findViewById(R.id.xia2_Nhan);
            this.xia2_NhanAn =  view.findViewById(R.id.xia2_NhanAn);
            this.xia2_Chuyen =  view.findViewById(R.id.xia2_Chuyen);
            this.xia2_ChuyenAn =  view.findViewById(R.id.xia2_ChuyenAn);
            this.bca_Nhan =  view.findViewById(R.id.bca_Nhan);
            this.bca_NhanAn =  view.findViewById(R.id.bca_NhanAn);
            this.bca_Chuyen =  view.findViewById(R.id.bca_Chuyen);
            this.bca_ChuyenAn =  view.findViewById(R.id.bca_ChuyenAn);
            JSONObject jSONObject = Frag_No_new.this.jsonKhachHang.get(position);
            try {
                charSequence = "OK";
                try {
                    this.tv_TongTienNhan.setText(jSONObject.getString("Tien_Nhan"));
                    this.tv_TongTienChuyen.setText(jSONObject.getString("Tien_Chuyen"));
                    this.tv_TongKhach.setText(jSONObject.getString("Tong_Tien"));
                    this.tv_tenKH.setText((CharSequence) Frag_No_new.this.mTenKH.get(position));
                    view = view;
                    if (jSONObject.has("dea")) {
                        try {
                            str = "ded";
                            this.li_dea.setVisibility(View.VISIBLE);
                            JSONObject jSONObject2 = new JSONObject(jSONObject.getString("dea"));
                            if (jSONObject2.getString("DiemNhan").length() > 0) {
                                TextView textView = this.dea_Nhan;
                                StringBuilder sb = new StringBuilder();
                                str2 = "dec";
                                sb.append(jSONObject2.getString("DiemNhan"));
                                sb.append("(");
                                sb.append(jSONObject2.getString("AnNhan"));
                                sb.append(")");
                                textView.setText(sb.toString());
                                this.dea_NhanAn.setText(jSONObject2.getString("KQNhan"));
                            } else {
                                str2 = "dec";
                            }
                            if (jSONObject2.getString("DiemChuyen").length() > 0) {
                                this.dea_Chuyen.setText(jSONObject2.getString("DiemChuyen") + "(" + jSONObject2.getString("AnChuyen") + ")");
                                this.dea_ChuyenAn.setText(jSONObject2.getString("KQChuyen"));
                            }
                        } catch (Exception unused) {
                            try {
                                charSequence2 = charSequence;
                            } catch (Exception unused2) {
                                charSequence2 = charSequence;
                            }
                            try {
                                Toast.makeText(Frag_No_new.this.getActivity(), charSequence2, Toast.LENGTH_LONG).show();
                                return view;
                            } catch (Exception unused3) {
                                Toast.makeText(Frag_No_new.this.getActivity(), charSequence2, Toast.LENGTH_LONG).show();
                                return view;
                            }
                        }
                    } else {
                        str = "ded";
                        str2 = "dec";
                    }
                    if (jSONObject.has("deb")) {
                        JSONObject jSONObject3 = new JSONObject(jSONObject.getString("deb"));
                        if (jSONObject3.getString("DiemNhan").length() > 0) {
                            this.deb_Nhan.setText(jSONObject3.getString("DiemNhan") + "(" + jSONObject3.getString("AnNhan") + ")");
                            this.deb_NhanAn.setText(jSONObject3.getString("KQNhan"));
                        }
                        if (jSONObject3.getString("DiemChuyen").length() > 0) {
                            this.deb_Chuyen.setText(jSONObject3.getString("DiemChuyen") + "(" + jSONObject3.getString("AnChuyen") + ")");
                            this.deb_ChuyenAn.setText(jSONObject3.getString("KQChuyen"));
                        }
                    }
                    if (jSONObject.has("det")) {
                        this.li_det.setVisibility(View.VISIBLE);
                        JSONObject jSONObject4 = new JSONObject(jSONObject.getString("det"));
                        if (jSONObject4.getString("DiemNhan").length() > 0) {
                            this.det_Nhan.setText(jSONObject4.getString("DiemNhan") + "(" + jSONObject4.getString("AnNhan") + ")");
                            this.det_NhanAn.setText(jSONObject4.getString("KQNhan"));
                        }
                        if (jSONObject4.getString("DiemChuyen").length() > 0) {
                            this.det_Chuyen.setText(jSONObject4.getString("DiemChuyen") + "(" + jSONObject4.getString("AnChuyen") + ")");
                            this.det_ChuyenAn.setText(jSONObject4.getString("KQChuyen"));
                        }
                    }
                    String str3 = str2;
                    if (jSONObject.has(str3)) {
                        this.li_dec.setVisibility(View.VISIBLE);
                        JSONObject jSONObject5 = new JSONObject(jSONObject.getString(str3));
                        if (jSONObject5.getString("DiemNhan").length() > 0) {
                            this.dec_Nhan.setText(jSONObject5.getString("DiemNhan") + "(" + jSONObject5.getString("AnNhan") + ")");
                            this.dec_NhanAn.setText(jSONObject5.getString("KQNhan"));
                        }
                        if (jSONObject5.getString("DiemChuyen").length() > 0) {
                            this.dec_Chuyen.setText(jSONObject5.getString("DiemChuyen") + "(" + jSONObject5.getString("AnChuyen") + ")");
                            this.dec_ChuyenAn.setText(jSONObject5.getString("KQChuyen"));
                        }
                    }
                    String str4 = str;
                    if (jSONObject.has(str4)) {
                        this.li_ded.setVisibility(View.VISIBLE);
                        JSONObject jSONObject6 = new JSONObject(jSONObject.getString(str4));
                        if (jSONObject6.getString("DiemNhan").length() > 0) {
                            this.ded_Nhan.setText(jSONObject6.getString("DiemNhan") + "(" + jSONObject6.getString("AnNhan") + ")");
                            this.ded_NhanAn.setText(jSONObject6.getString("KQNhan"));
                        }
                        if (jSONObject6.getString("DiemChuyen").length() > 0) {
                            this.ded_Chuyen.setText(jSONObject6.getString("DiemChuyen") + "(" + jSONObject6.getString("AnChuyen") + ")");
                            this.ded_ChuyenAn.setText(jSONObject6.getString("KQChuyen"));
                        }
                    }
                    if (jSONObject.has("lo")) {
                        JSONObject jSONObject7 = new JSONObject(jSONObject.getString("lo"));
                        if (jSONObject7.getString("DiemNhan").length() > 0) {
                            this.lo_Nhan.setText(jSONObject7.getString("DiemNhan") + "(" + jSONObject7.getString("AnNhan") + ")");
                            this.lo_NhanAn.setText(jSONObject7.getString("KQNhan"));
                        }
                        if (jSONObject7.getString("DiemChuyen").length() > 0) {
                            this.lo_Chuyen.setText(jSONObject7.getString("DiemChuyen") + "(" + jSONObject7.getString("AnChuyen") + ")");
                            this.lo_ChuyenAn.setText(jSONObject7.getString("KQChuyen"));
                        }
                    }
                    if (jSONObject.has("loa")) {
                        this.li_loa.setVisibility(View.VISIBLE);
                        JSONObject jSONObject8 = new JSONObject(jSONObject.getString("loa"));
                        if (jSONObject8.getString("DiemNhan").length() > 0) {
                            this.loa_Nhan.setText(jSONObject8.getString("DiemNhan") + "(" + jSONObject8.getString("AnNhan") + ")");
                            this.loa_NhanAn.setText(jSONObject8.getString("KQNhan"));
                        }
                        if (jSONObject8.getString("DiemChuyen").length() > 0) {
                            this.loa_Chuyen.setText(jSONObject8.getString("DiemChuyen") + "(" + jSONObject8.getString("AnChuyen") + ")");
                            this.loa_ChuyenAn.setText(jSONObject8.getString("KQChuyen"));
                        }
                    }
                    if (jSONObject.has("xi")) {
                        this.li_xi2.setVisibility(View.VISIBLE);
                        JSONObject jSONObject9 = new JSONObject(jSONObject.getString("xi"));
                        if (jSONObject9.getString("DiemNhan").length() > 0) {
                            this.xi2_Nhan.setText(jSONObject9.getString("DiemNhan") + "(" + jSONObject9.getString("AnNhan") + ")");
                            this.xi2_NhanAn.setText(jSONObject9.getString("KQNhan"));
                        }
                        if (jSONObject9.getString("DiemChuyen").length() > 0) {
                            this.xi2_Chuyen.setText(jSONObject9.getString("DiemChuyen") + "(" + jSONObject9.getString("AnChuyen") + ")");
                            this.xi2_ChuyenAn.setText(jSONObject9.getString("KQChuyen"));
                        }
                    }
                    if (jSONObject.has("xia")) {
                        this.li_xia2.setVisibility(View.VISIBLE);
                        JSONObject jSONObject10 = new JSONObject(jSONObject.getString("xia"));
                        if (jSONObject10.getString("DiemNhan").length() > 0) {
                            this.xia2_Nhan.setText(jSONObject10.getString("DiemNhan") + "(" + jSONObject10.getString("AnNhan") + ")");
                            this.xia2_NhanAn.setText(jSONObject10.getString("KQNhan"));
                        }
                        if (jSONObject10.getString("DiemChuyen").length() > 0) {
                            this.xia2_Chuyen.setText(jSONObject10.getString("DiemChuyen") + "(" + jSONObject10.getString("AnChuyen") + ")");
                            this.xia2_ChuyenAn.setText(jSONObject10.getString("KQChuyen"));
                        }
                    }
                    if (jSONObject.has("bc")) {
                        JSONObject jSONObject11 = new JSONObject(jSONObject.getString("bc"));
                        if (jSONObject11.getString("DiemNhan").length() > 0) {
                            this.bc_Nhan.setText(jSONObject11.getString("DiemNhan") + "(" + jSONObject11.getString("AnNhan") + ")");
                            this.bc_NhanAn.setText(jSONObject11.getString("KQNhan"));
                        }
                        if (jSONObject11.getString("DiemChuyen").length() > 0) {
                            this.bc_Chuyen.setText(jSONObject11.getString("DiemChuyen") + "(" + jSONObject11.getString("AnChuyen") + ")");
                            this.bc_ChuyenAn.setText(jSONObject11.getString("KQChuyen"));
                        }
                    }
                    if (jSONObject.has("bca")) {
                        this.li_bca.setVisibility(View.VISIBLE);
                        JSONObject jSONObject12 = new JSONObject(jSONObject.getString("bca"));
                        if (jSONObject12.getString("DiemNhan").length() > 0) {
                            this.bca_Nhan.setText(jSONObject12.getString("DiemNhan") + "(" + jSONObject12.getString("AnNhan") + ")");
                            this.bca_NhanAn.setText(jSONObject12.getString("KQNhan"));
                        }
                        if (jSONObject12.getString("DiemChuyen").length() > 0) {
                            this.bca_Chuyen.setText(jSONObject12.getString("DiemChuyen") + "(" + jSONObject12.getString("AnChuyen") + ")");
                            this.bca_ChuyenAn.setText(jSONObject12.getString("KQChuyen"));
                        }
                    }
                    return view;
                } catch (Exception unused4) {
                    view = view;
                    charSequence2 = charSequence;
                    Toast.makeText(Frag_No_new.this.getActivity(), charSequence2, Toast.LENGTH_LONG).show();
                    return view;
                }
            } catch (Exception unused5) {
                charSequence = "OK";
            }
            return view;
        }
    }

    public void TinhlaitienKhachnay(String mDate, String tenkh) {
        this.db.QueryData("Delete From tbl_soctS WHERE  ngay_nhan = '" + mDate + "' AND ten_kh = '" + this.mTenKH.get(this.position) + "'");
        Cursor GetData = this.db.GetData("Select * FROM tbl_tinnhanS WHERE  ngay_nhan = '" + mDate + "' AND phat_hien_loi = 'ok' AND ten_kh = '" + this.mTenKH.get(this.position) + "'");
        while (GetData.moveToNext()) {
            String replaceAll = GetData.getString(10).replaceAll("\\*", "");
            this.db.QueryData("Update tbl_tinnhanS set nd_phantich = '" + replaceAll + "' WHERE id = " + GetData.getInt(0));
            this.db.NhapSoChiTiet(GetData.getInt(0));
        }
        Tinhtien();
        lv_baoCao();
        if (GetData.isClosed()) {
            return;
        }
        GetData.close();
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0040, code lost:
    
        if (r1.isClosed() == false) goto L56;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0042, code lost:
    
        r1.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x005d, code lost:
    
        if (r1.isClosed() == false) goto L56;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void Tinhtien() {
        String Get_date = MainActivity.Get_date();
        Cursor GetData = this.db.GetData("Select * From Ketqua WHERE ngay = '" + Get_date + "'");
        GetData.moveToFirst();
        int i = 2;
        while (i < 29) {
            try {
                if (GetData.isNull(i) || !Congthuc.isNumeric(GetData.getString(i))) {
                    break;
                } else {
                    i++;
                }
            } catch (Exception unused) {
                if (GetData != null) {
                }
            } catch (Throwable th) {
                if (GetData != null && !GetData.isClosed()) {
                    GetData.close();
                }
                throw th;
            }
        }
        if (GetData != null) {
        }
        if (i >= 29) {
            try {
                this.db.Tinhtien(Get_date);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void XemListview() {
        JSONObject jSONObject;
        String Get_date = MainActivity.Get_date();
        this.jsonKhachHang = new ArrayList();
        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        this.mTenKH.clear();
        this.mSDT.clear();
        try {
            try {
                Cursor GetData = this.db.GetData("Select ten_kh, so_dienthoai, the_loai\n, sum((type_kh = 1)*(100-diem_khachgiu)*diem/100) as mDiem\n, CASE WHEN the_loai = 'xi' OR the_loai = 'xia' \n THEN sum((type_kh = 1)*(100-diem_khachgiu)*diem/100*so_nhay*lan_an/1000) \n ELSE sum((type_kh = 1)*(100-diem_khachgiu)*diem/100*so_nhay)  END nAn\n, sum((type_kh = 1)*ket_qua*(100-diem_khachgiu)/100/1000) as mKetqua\n, sum((type_kh = 2)*(100-diem_khachgiu)*diem/100) as mDiem\n, CASE WHEN the_loai = 'xi' OR the_loai = 'xia' \n THEN sum((type_kh = 2)*(100-diem_khachgiu)*diem/100*so_nhay*lan_an/1000) \n ELSE sum((type_kh = 2)*(100-diem_khachgiu)*diem/100*so_nhay)  END nAn\n, sum((type_kh = 2)* ket_qua/1000) as mKetqua\n  From tbl_soctS Where ngay_nhan = '" + Get_date + "'\n  AND the_loai <> 'tt' GROUP by ten_kh, the_loai");
                JSONObject jSONObject2 = new JSONObject();
                if (GetData != null) {
                    JSONObject jSONObject3 = null;
                    double d = 0.0d;
                    double d2 = 0.0d;
                    String str = "";
                    JSONObject jSONObject4 = jSONObject2;
                    while (GetData.moveToNext()) {
                        try {
                            if (str.length() == 0) {
                                this.mTenKH.add(GetData.getString(0));
                                this.mSDT.add(GetData.getString(1));
                                str = GetData.getString(0);
                            } else if (str.indexOf(GetData.getString(0)) != 0) {
                                try {
                                    jSONObject4.put("Tien_Nhan", decimalFormat.format(d));
                                    jSONObject4.put("Tien_Chuyen", decimalFormat.format(d2));
                                    try {
                                        jSONObject4.put("Tong_Tien", decimalFormat.format(d + d2));
                                        this.jsonKhachHang.add(jSONObject4);
                                        try {
                                            this.mTenKH.add(GetData.getString(0));
                                            this.mSDT.add(GetData.getString(1));
                                            str = GetData.getString(0);
                                            jSONObject4 = new JSONObject();
                                        } catch (SQLException unused) {
                                        }
                                        d = 0.0d;
                                        d2 = 0.0d;
                                    } catch (JSONException e3) {
                                        e3.printStackTrace();
                                    }
                                } catch (JSONException e4) {
                                    e4.printStackTrace();
                                    getActivity();
                                }
                            }
                        } catch (SQLException unused3) {
                        }
                        try {
                            jSONObject = new JSONObject();
                            jSONObject.put("DiemNhan", decimalFormat.format(GetData.getDouble(3)));
                            jSONObject.put("AnNhan", decimalFormat.format(GetData.getDouble(4)));
                            jSONObject.put("KQNhan", decimalFormat.format(GetData.getDouble(5)));
                            jSONObject.put("DiemChuyen", decimalFormat.format(GetData.getDouble(6)));
                            jSONObject.put("AnChuyen", decimalFormat.format(GetData.getDouble(7)));
                            jSONObject3 = jSONObject;
                        } catch (SQLException unused4) {
                            unused4.printStackTrace();
                        } catch (JSONException e5) {
                            e5.printStackTrace();
                        }
                        try {
                            jSONObject3.put("KQChuyen", decimalFormat.format(GetData.getDouble(8)));
                            d += GetData.getDouble(5);
                            d2 += GetData.getDouble(8);
                            jSONObject4.put(GetData.getString(2), jSONObject3.toString());
                        } catch (SQLException unused5) {
                        } catch (JSONException e6) {
                            e6.printStackTrace();
                            getActivity();
                        }
                    }
                    jSONObject4.put("Tien_Nhan", decimalFormat.format(d));
                    jSONObject4.put("Tien_Chuyen", decimalFormat.format(d2));
                    jSONObject4.put("Tong_Tien", decimalFormat.format(d + d2));
                    if (GetData.getCount() > 0) {
                        this.jsonKhachHang.add(jSONObject4);
                    }
                    if (GetData != null && !GetData.isClosed()) {
                        GetData.close();
                    }
                }
            } catch (SQLException unused6) {
            }
        } catch (JSONException e7) {
            e7.printStackTrace();
        }
        if (getActivity() != null) {
            this.lv_baocaoKhach.setAdapter((ListAdapter) new NoRP_TN_Adapter(getActivity(), R.layout.frag_norp1_2, this.jsonKhachHang));
        }
    }

    private void init() {
        this.xn_Nhan =  this.v.findViewById(R.id.xn_Nhan);
        this.xn_NhanAn =  this.v.findViewById(R.id.xn_NhanAn);
        this.xn_Chuyen =  this.v.findViewById(R.id.xn_Chuyen);
        this.xn_ChuyenAn =  this.v.findViewById(R.id.xn_ChuyenAn);
        this.dea_Nhan =  this.v.findViewById(R.id.dea_Nhan);
        this.deb_Nhan =  this.v.findViewById(R.id.deb_Nhan);
        this.det_Nhan =  this.v.findViewById(R.id.det_Nhan);
        this.dec_Nhan =  this.v.findViewById(R.id.dec_Nhan);
        this.ded_Nhan =  this.v.findViewById(R.id.ded_Nhan);
        this.lo_Nhan =  this.v.findViewById(R.id.lo_Nhan);
        this.loa_Nhan =  this.v.findViewById(R.id.loa_Nhan);
        this.bc_Nhan =  this.v.findViewById(R.id.bc_Nhan);
        this.bca_Nhan =  this.v.findViewById(R.id.bca_Nhan);
        this.dea_NhanAn =  this.v.findViewById(R.id.dea_NhanAn);
        this.deb_NhanAn =  this.v.findViewById(R.id.deb_NhanAn);
        this.det_NhanAn =  this.v.findViewById(R.id.det_NhanAn);
        this.dec_NhanAn =  this.v.findViewById(R.id.dec_NhanAn);
        this.ded_NhanAn =  this.v.findViewById(R.id.ded_NhanAn);
        this.lo_NhanAn =  this.v.findViewById(R.id.lo_NhanAn);
        this.loa_NhanAn =  this.v.findViewById(R.id.loa_NhanAn);
        this.bc_NhanAn =  this.v.findViewById(R.id.bc_NhanAn);
        this.dea_Chuyen =  this.v.findViewById(R.id.dea_Chuyen);
        this.deb_Chuyen =  this.v.findViewById(R.id.deb_Chuyen);
        this.det_Chuyen =  this.v.findViewById(R.id.det_Chuyen);
        this.dec_Chuyen =  this.v.findViewById(R.id.dec_Chuyen);
        this.ded_Chuyen =  this.v.findViewById(R.id.ded_Chuyen);
        this.lo_Chuyen =  this.v.findViewById(R.id.lo_Chuyen);
        this.loa_Chuyen =  this.v.findViewById(R.id.loa_Chuyen);
        this.bc_Chuyen =  this.v.findViewById(R.id.bc_Chuyen);
        this.dea_ChuyenAn =  this.v.findViewById(R.id.dea_ChuyenAn);
        this.deb_ChuyenAn =  this.v.findViewById(R.id.deb_ChuyenAn);
        this.det_ChuyenAn =  this.v.findViewById(R.id.det_ChuyenAn);
        this.dec_ChuyenAn =  this.v.findViewById(R.id.dec_ChuyenAn);
        this.ded_ChuyenAn =  this.v.findViewById(R.id.ded_ChuyenAn);
        this.lo_ChuyenAn =  this.v.findViewById(R.id.lo_ChuyenAn);
        this.loa_ChuyenAn =  this.v.findViewById(R.id.loa_ChuyenAn);
        this.bc_ChuyenAn =  this.v.findViewById(R.id.bc_ChuyenAn);
        this.tv_TongGiu =  this.v.findViewById(R.id.tv_TongGiu);
        this.tv_TongTienNhan =  this.v.findViewById(R.id.tv_TongTienNhan);
        this.tv_TongTienChuyen =  this.v.findViewById(R.id.tv_TongTienChuyen);
        this.li_dea =  this.v.findViewById(R.id.li_dea);
        this.li_det =  this.v.findViewById(R.id.li_det);
        this.li_dec =  this.v.findViewById(R.id.li_dec);
        this.li_ded =  this.v.findViewById(R.id.li_ded);
        this.li_loa =  this.v.findViewById(R.id.li_loa);
        this.li_bca =  this.v.findViewById(R.id.li_bca);
        this.li_xia2 =  this.v.findViewById(R.id.li_xia2);
        this.li_xn =  this.v.findViewById(R.id.li_xn);
        this.xi2_Nhan =  this.v.findViewById(R.id.xi2_Nhan);
        this.xi2_NhanAn =  this.v.findViewById(R.id.xi2_NhanAn);
        this.xi2_Chuyen =  this.v.findViewById(R.id.xi2_Chuyen);
        this.xi2_ChuyenAn =  this.v.findViewById(R.id.xi2_ChuyenAn);
        this.xia2_Nhan =  this.v.findViewById(R.id.xia2_Nhan);
        this.xia2_NhanAn =  this.v.findViewById(R.id.xia2_NhanAn);
        this.xia2_Chuyen =  this.v.findViewById(R.id.xia2_Chuyen);
        this.xia2_ChuyenAn =  this.v.findViewById(R.id.xia2_ChuyenAn);
        this.bca_Nhan =  this.v.findViewById(R.id.bca_Nhan);
        this.bca_NhanAn =  this.v.findViewById(R.id.bca_NhanAn);
        this.bca_Chuyen =  this.v.findViewById(R.id.bca_Chuyen);
        this.bca_ChuyenAn =  this.v.findViewById(R.id.bca_ChuyenAn);
        this.lv_baocaoKhach = (ListView) this.v.findViewById(R.id.lv_baocaoKhach);
    }

    /* JADX WARN: Removed duplicated region for block: B:101:0x0611 A[Catch: JSONException -> 0x0727, TryCatch #3 {JSONException -> 0x0727, blocks: (B:27:0x018c, B:29:0x0192, B:31:0x01a5, B:32:0x01d5, B:34:0x01df, B:35:0x020d, B:37:0x0215, B:39:0x022e, B:40:0x0259, B:42:0x0263, B:43:0x028e, B:45:0x0296, B:47:0x02af, B:48:0x02da, B:50:0x02e4, B:51:0x030f, B:53:0x0317, B:55:0x0330, B:56:0x035b, B:58:0x0365, B:59:0x0390, B:61:0x0398, B:63:0x03ab, B:64:0x03d6, B:66:0x03e0, B:67:0x040b, B:69:0x0413, B:71:0x042c, B:72:0x0457, B:74:0x0461, B:75:0x048c, B:77:0x0494, B:79:0x04a7, B:80:0x04d2, B:82:0x04dc, B:83:0x0507, B:85:0x050f, B:87:0x0528, B:88:0x0553, B:90:0x055d, B:91:0x0588, B:93:0x0590, B:95:0x05a9, B:96:0x05d4, B:98:0x05de, B:99:0x0609, B:101:0x0611, B:103:0x0624, B:104:0x064f, B:106:0x0659, B:107:0x0684, B:109:0x068c, B:111:0x06a5, B:112:0x06d0, B:114:0x06da, B:115:0x0705), top: B:26:0x018c }] */
    /* JADX WARN: Removed duplicated region for block: B:109:0x068c A[Catch: JSONException -> 0x0727, TryCatch #3 {JSONException -> 0x0727, blocks: (B:27:0x018c, B:29:0x0192, B:31:0x01a5, B:32:0x01d5, B:34:0x01df, B:35:0x020d, B:37:0x0215, B:39:0x022e, B:40:0x0259, B:42:0x0263, B:43:0x028e, B:45:0x0296, B:47:0x02af, B:48:0x02da, B:50:0x02e4, B:51:0x030f, B:53:0x0317, B:55:0x0330, B:56:0x035b, B:58:0x0365, B:59:0x0390, B:61:0x0398, B:63:0x03ab, B:64:0x03d6, B:66:0x03e0, B:67:0x040b, B:69:0x0413, B:71:0x042c, B:72:0x0457, B:74:0x0461, B:75:0x048c, B:77:0x0494, B:79:0x04a7, B:80:0x04d2, B:82:0x04dc, B:83:0x0507, B:85:0x050f, B:87:0x0528, B:88:0x0553, B:90:0x055d, B:91:0x0588, B:93:0x0590, B:95:0x05a9, B:96:0x05d4, B:98:0x05de, B:99:0x0609, B:101:0x0611, B:103:0x0624, B:104:0x064f, B:106:0x0659, B:107:0x0684, B:109:0x068c, B:111:0x06a5, B:112:0x06d0, B:114:0x06da, B:115:0x0705), top: B:26:0x018c }] */
    /* JADX WARN: Removed duplicated region for block: B:125:0x020b  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0192 A[Catch: JSONException -> 0x0727, TryCatch #3 {JSONException -> 0x0727, blocks: (B:27:0x018c, B:29:0x0192, B:31:0x01a5, B:32:0x01d5, B:34:0x01df, B:35:0x020d, B:37:0x0215, B:39:0x022e, B:40:0x0259, B:42:0x0263, B:43:0x028e, B:45:0x0296, B:47:0x02af, B:48:0x02da, B:50:0x02e4, B:51:0x030f, B:53:0x0317, B:55:0x0330, B:56:0x035b, B:58:0x0365, B:59:0x0390, B:61:0x0398, B:63:0x03ab, B:64:0x03d6, B:66:0x03e0, B:67:0x040b, B:69:0x0413, B:71:0x042c, B:72:0x0457, B:74:0x0461, B:75:0x048c, B:77:0x0494, B:79:0x04a7, B:80:0x04d2, B:82:0x04dc, B:83:0x0507, B:85:0x050f, B:87:0x0528, B:88:0x0553, B:90:0x055d, B:91:0x0588, B:93:0x0590, B:95:0x05a9, B:96:0x05d4, B:98:0x05de, B:99:0x0609, B:101:0x0611, B:103:0x0624, B:104:0x064f, B:106:0x0659, B:107:0x0684, B:109:0x068c, B:111:0x06a5, B:112:0x06d0, B:114:0x06da, B:115:0x0705), top: B:26:0x018c }] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0215 A[Catch: JSONException -> 0x0727, TryCatch #3 {JSONException -> 0x0727, blocks: (B:27:0x018c, B:29:0x0192, B:31:0x01a5, B:32:0x01d5, B:34:0x01df, B:35:0x020d, B:37:0x0215, B:39:0x022e, B:40:0x0259, B:42:0x0263, B:43:0x028e, B:45:0x0296, B:47:0x02af, B:48:0x02da, B:50:0x02e4, B:51:0x030f, B:53:0x0317, B:55:0x0330, B:56:0x035b, B:58:0x0365, B:59:0x0390, B:61:0x0398, B:63:0x03ab, B:64:0x03d6, B:66:0x03e0, B:67:0x040b, B:69:0x0413, B:71:0x042c, B:72:0x0457, B:74:0x0461, B:75:0x048c, B:77:0x0494, B:79:0x04a7, B:80:0x04d2, B:82:0x04dc, B:83:0x0507, B:85:0x050f, B:87:0x0528, B:88:0x0553, B:90:0x055d, B:91:0x0588, B:93:0x0590, B:95:0x05a9, B:96:0x05d4, B:98:0x05de, B:99:0x0609, B:101:0x0611, B:103:0x0624, B:104:0x064f, B:106:0x0659, B:107:0x0684, B:109:0x068c, B:111:0x06a5, B:112:0x06d0, B:114:0x06da, B:115:0x0705), top: B:26:0x018c }] */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0296 A[Catch: JSONException -> 0x0727, TryCatch #3 {JSONException -> 0x0727, blocks: (B:27:0x018c, B:29:0x0192, B:31:0x01a5, B:32:0x01d5, B:34:0x01df, B:35:0x020d, B:37:0x0215, B:39:0x022e, B:40:0x0259, B:42:0x0263, B:43:0x028e, B:45:0x0296, B:47:0x02af, B:48:0x02da, B:50:0x02e4, B:51:0x030f, B:53:0x0317, B:55:0x0330, B:56:0x035b, B:58:0x0365, B:59:0x0390, B:61:0x0398, B:63:0x03ab, B:64:0x03d6, B:66:0x03e0, B:67:0x040b, B:69:0x0413, B:71:0x042c, B:72:0x0457, B:74:0x0461, B:75:0x048c, B:77:0x0494, B:79:0x04a7, B:80:0x04d2, B:82:0x04dc, B:83:0x0507, B:85:0x050f, B:87:0x0528, B:88:0x0553, B:90:0x055d, B:91:0x0588, B:93:0x0590, B:95:0x05a9, B:96:0x05d4, B:98:0x05de, B:99:0x0609, B:101:0x0611, B:103:0x0624, B:104:0x064f, B:106:0x0659, B:107:0x0684, B:109:0x068c, B:111:0x06a5, B:112:0x06d0, B:114:0x06da, B:115:0x0705), top: B:26:0x018c }] */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0317 A[Catch: JSONException -> 0x0727, TryCatch #3 {JSONException -> 0x0727, blocks: (B:27:0x018c, B:29:0x0192, B:31:0x01a5, B:32:0x01d5, B:34:0x01df, B:35:0x020d, B:37:0x0215, B:39:0x022e, B:40:0x0259, B:42:0x0263, B:43:0x028e, B:45:0x0296, B:47:0x02af, B:48:0x02da, B:50:0x02e4, B:51:0x030f, B:53:0x0317, B:55:0x0330, B:56:0x035b, B:58:0x0365, B:59:0x0390, B:61:0x0398, B:63:0x03ab, B:64:0x03d6, B:66:0x03e0, B:67:0x040b, B:69:0x0413, B:71:0x042c, B:72:0x0457, B:74:0x0461, B:75:0x048c, B:77:0x0494, B:79:0x04a7, B:80:0x04d2, B:82:0x04dc, B:83:0x0507, B:85:0x050f, B:87:0x0528, B:88:0x0553, B:90:0x055d, B:91:0x0588, B:93:0x0590, B:95:0x05a9, B:96:0x05d4, B:98:0x05de, B:99:0x0609, B:101:0x0611, B:103:0x0624, B:104:0x064f, B:106:0x0659, B:107:0x0684, B:109:0x068c, B:111:0x06a5, B:112:0x06d0, B:114:0x06da, B:115:0x0705), top: B:26:0x018c }] */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0398 A[Catch: JSONException -> 0x0727, TryCatch #3 {JSONException -> 0x0727, blocks: (B:27:0x018c, B:29:0x0192, B:31:0x01a5, B:32:0x01d5, B:34:0x01df, B:35:0x020d, B:37:0x0215, B:39:0x022e, B:40:0x0259, B:42:0x0263, B:43:0x028e, B:45:0x0296, B:47:0x02af, B:48:0x02da, B:50:0x02e4, B:51:0x030f, B:53:0x0317, B:55:0x0330, B:56:0x035b, B:58:0x0365, B:59:0x0390, B:61:0x0398, B:63:0x03ab, B:64:0x03d6, B:66:0x03e0, B:67:0x040b, B:69:0x0413, B:71:0x042c, B:72:0x0457, B:74:0x0461, B:75:0x048c, B:77:0x0494, B:79:0x04a7, B:80:0x04d2, B:82:0x04dc, B:83:0x0507, B:85:0x050f, B:87:0x0528, B:88:0x0553, B:90:0x055d, B:91:0x0588, B:93:0x0590, B:95:0x05a9, B:96:0x05d4, B:98:0x05de, B:99:0x0609, B:101:0x0611, B:103:0x0624, B:104:0x064f, B:106:0x0659, B:107:0x0684, B:109:0x068c, B:111:0x06a5, B:112:0x06d0, B:114:0x06da, B:115:0x0705), top: B:26:0x018c }] */
    /* JADX WARN: Removed duplicated region for block: B:69:0x0413 A[Catch: JSONException -> 0x0727, TryCatch #3 {JSONException -> 0x0727, blocks: (B:27:0x018c, B:29:0x0192, B:31:0x01a5, B:32:0x01d5, B:34:0x01df, B:35:0x020d, B:37:0x0215, B:39:0x022e, B:40:0x0259, B:42:0x0263, B:43:0x028e, B:45:0x0296, B:47:0x02af, B:48:0x02da, B:50:0x02e4, B:51:0x030f, B:53:0x0317, B:55:0x0330, B:56:0x035b, B:58:0x0365, B:59:0x0390, B:61:0x0398, B:63:0x03ab, B:64:0x03d6, B:66:0x03e0, B:67:0x040b, B:69:0x0413, B:71:0x042c, B:72:0x0457, B:74:0x0461, B:75:0x048c, B:77:0x0494, B:79:0x04a7, B:80:0x04d2, B:82:0x04dc, B:83:0x0507, B:85:0x050f, B:87:0x0528, B:88:0x0553, B:90:0x055d, B:91:0x0588, B:93:0x0590, B:95:0x05a9, B:96:0x05d4, B:98:0x05de, B:99:0x0609, B:101:0x0611, B:103:0x0624, B:104:0x064f, B:106:0x0659, B:107:0x0684, B:109:0x068c, B:111:0x06a5, B:112:0x06d0, B:114:0x06da, B:115:0x0705), top: B:26:0x018c }] */
    /* JADX WARN: Removed duplicated region for block: B:77:0x0494 A[Catch: JSONException -> 0x0727, TryCatch #3 {JSONException -> 0x0727, blocks: (B:27:0x018c, B:29:0x0192, B:31:0x01a5, B:32:0x01d5, B:34:0x01df, B:35:0x020d, B:37:0x0215, B:39:0x022e, B:40:0x0259, B:42:0x0263, B:43:0x028e, B:45:0x0296, B:47:0x02af, B:48:0x02da, B:50:0x02e4, B:51:0x030f, B:53:0x0317, B:55:0x0330, B:56:0x035b, B:58:0x0365, B:59:0x0390, B:61:0x0398, B:63:0x03ab, B:64:0x03d6, B:66:0x03e0, B:67:0x040b, B:69:0x0413, B:71:0x042c, B:72:0x0457, B:74:0x0461, B:75:0x048c, B:77:0x0494, B:79:0x04a7, B:80:0x04d2, B:82:0x04dc, B:83:0x0507, B:85:0x050f, B:87:0x0528, B:88:0x0553, B:90:0x055d, B:91:0x0588, B:93:0x0590, B:95:0x05a9, B:96:0x05d4, B:98:0x05de, B:99:0x0609, B:101:0x0611, B:103:0x0624, B:104:0x064f, B:106:0x0659, B:107:0x0684, B:109:0x068c, B:111:0x06a5, B:112:0x06d0, B:114:0x06da, B:115:0x0705), top: B:26:0x018c }] */
    /* JADX WARN: Removed duplicated region for block: B:85:0x050f A[Catch: JSONException -> 0x0727, TryCatch #3 {JSONException -> 0x0727, blocks: (B:27:0x018c, B:29:0x0192, B:31:0x01a5, B:32:0x01d5, B:34:0x01df, B:35:0x020d, B:37:0x0215, B:39:0x022e, B:40:0x0259, B:42:0x0263, B:43:0x028e, B:45:0x0296, B:47:0x02af, B:48:0x02da, B:50:0x02e4, B:51:0x030f, B:53:0x0317, B:55:0x0330, B:56:0x035b, B:58:0x0365, B:59:0x0390, B:61:0x0398, B:63:0x03ab, B:64:0x03d6, B:66:0x03e0, B:67:0x040b, B:69:0x0413, B:71:0x042c, B:72:0x0457, B:74:0x0461, B:75:0x048c, B:77:0x0494, B:79:0x04a7, B:80:0x04d2, B:82:0x04dc, B:83:0x0507, B:85:0x050f, B:87:0x0528, B:88:0x0553, B:90:0x055d, B:91:0x0588, B:93:0x0590, B:95:0x05a9, B:96:0x05d4, B:98:0x05de, B:99:0x0609, B:101:0x0611, B:103:0x0624, B:104:0x064f, B:106:0x0659, B:107:0x0684, B:109:0x068c, B:111:0x06a5, B:112:0x06d0, B:114:0x06da, B:115:0x0705), top: B:26:0x018c }] */
    /* JADX WARN: Removed duplicated region for block: B:93:0x0590 A[Catch: JSONException -> 0x0727, TryCatch #3 {JSONException -> 0x0727, blocks: (B:27:0x018c, B:29:0x0192, B:31:0x01a5, B:32:0x01d5, B:34:0x01df, B:35:0x020d, B:37:0x0215, B:39:0x022e, B:40:0x0259, B:42:0x0263, B:43:0x028e, B:45:0x0296, B:47:0x02af, B:48:0x02da, B:50:0x02e4, B:51:0x030f, B:53:0x0317, B:55:0x0330, B:56:0x035b, B:58:0x0365, B:59:0x0390, B:61:0x0398, B:63:0x03ab, B:64:0x03d6, B:66:0x03e0, B:67:0x040b, B:69:0x0413, B:71:0x042c, B:72:0x0457, B:74:0x0461, B:75:0x048c, B:77:0x0494, B:79:0x04a7, B:80:0x04d2, B:82:0x04dc, B:83:0x0507, B:85:0x050f, B:87:0x0528, B:88:0x0553, B:90:0x055d, B:91:0x0588, B:93:0x0590, B:95:0x05a9, B:96:0x05d4, B:98:0x05de, B:99:0x0609, B:101:0x0611, B:103:0x0624, B:104:0x064f, B:106:0x0659, B:107:0x0684, B:109:0x068c, B:111:0x06a5, B:112:0x06d0, B:114:0x06da, B:115:0x0705), top: B:26:0x018c }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void lv_baoCao() {
        String str;
        String str2;
        String str3;
        String str4;
        String str5;
        String str6;
        Cursor cursor;
        double d;
        double d2;
        DecimalFormat decimalFormat = new DecimalFormat();
        String str7;
        String str8;
        String str9 = "xia";
        String str10 = "xn";
        String str11 = "bc";
        String str12 = "xi";
        String str13 = "loa";
        String str14 = "lo";
        String str15 = "ded";
        String str16 = "dec";
        String str17 = "det";
        String Get_date = MainActivity.Get_date();
        DecimalFormat decimalFormat2 = new DecimalFormat("###,###");
        Cursor GetData = this.db.GetData("Select the_loai\n, sum((type_kh = 1)*(100-diem_khachgiu)*diem/100) as mDiem\n, CASE WHEN the_loai = 'xi' OR the_loai = 'xia' \n THEN sum((type_kh = 1)*(100-diem_khachgiu)*diem/100*so_nhay*lan_an/1000) \n ELSE sum((type_kh = 1)*(100-diem_khachgiu)*diem/100*so_nhay)  END nAn\n, sum((type_kh = 1)*ket_qua*(100-diem_khachgiu)/100/1000) as mKetqua\n, sum((type_kh = 2)*(100-diem_khachgiu)*diem/100) as mDiem\n, CASE WHEN the_loai = 'xi' OR the_loai = 'xia' \n THEN sum((type_kh = 2)*(100-diem_khachgiu)*diem/100*so_nhay*lan_an/1000) \n ELSE sum((type_kh = 2)*(100-diem_khachgiu)*diem/100*so_nhay)  END nAn\n, sum((type_kh = 2)*ket_qua*(100-diem_khachgiu)/100/1000) as mKetqua\n  From tbl_soctS Where ngay_nhan = '" + Get_date + "'\n  AND the_loai <> 'tt' GROUP by the_loai");
        if (GetData != null) {
            JSONObject jSONObject = new JSONObject();
            double d3 = 0.0d;
            double d4 = 0.0d;
            while (true) {
                str = str11;
                str2 = str9;
                str3 = str10;
                str4 = str12;
                str5 = str13;
                str6 = str14;
                if (!GetData.moveToNext()) {
                    break;
                }
                String str18 = str15;
                JSONObject jSONObject2 = new JSONObject();
                str7 = str16;
                str8 = str17;
                try {
                    jSONObject2.put("DiemNhan", decimalFormat2.format(GetData.getDouble(1)));
                    jSONObject2.put("AnNhan", decimalFormat2.format(GetData.getDouble(2)));
                    jSONObject2.put("KQNhan", decimalFormat2.format(GetData.getDouble(3)));
                    jSONObject2.put("DiemChuyen", decimalFormat2.format(GetData.getDouble(4)));
                    jSONObject2.put("AnChuyen", decimalFormat2.format(GetData.getDouble(5)));
                    jSONObject2.put("KQChuyen", decimalFormat2.format(GetData.getDouble(6)));
                    d4 += GetData.getDouble(3);
                    d3 += GetData.getDouble(6);
                    jSONObject.put(GetData.getString(0), jSONObject2.toString());
                } catch (JSONException unused) {
                }
                str15 = str18;
                str11 = str;
                str9 = str2;
                str10 = str3;
                str12 = str4;
                str13 = str5;
                str14 = str6;
                str16 = str7;
                str17 = str8;
            }
            String str19 = str15;
            String str20 = str16;
            String str21 = str17;
            if (jSONObject.length() > 0) {
                cursor = GetData;
                if (jSONObject.has("dea")) {
                    d = d3;
                    try {
                        this.li_dea.setVisibility(View.VISIBLE);
                        JSONObject jSONObject3 = new JSONObject(jSONObject.getString("dea"));
                        if (jSONObject3.getString("DiemNhan").length() > 0) {
                            TextView textView = this.dea_Nhan;
                            StringBuilder sb = new StringBuilder();
                            try {
                                sb.append(jSONObject3.getString("DiemNhan"));
                                sb.append("(");
                                sb.append(jSONObject3.getString("AnNhan"));
                                sb.append(")");
                                textView.setText(sb.toString());
                                this.dea_NhanAn.setText(jSONObject3.getString("KQNhan"));
                            } catch (JSONException unused3) {
                            }
                        }
                        try {
                            if (jSONObject3.getString("DiemChuyen").length() > 0) {
                                this.dea_Chuyen.setText(jSONObject3.getString("DiemChuyen") + "(" + jSONObject3.getString("AnChuyen") + ")");
                                this.dea_ChuyenAn.setText(jSONObject3.getString("KQChuyen"));
                            }
                        } catch (JSONException unused4) {
                        }
                    } catch (JSONException unused5) {
                        d2 = 0.0d;
                        if (jSONObject.has("deb")) {
                        }
                        if (jSONObject.has(str21)) {
                        }
                        if (jSONObject.has(str20)) {
                        }
                        if (jSONObject.has(str19)) {
                        }
                        if (jSONObject.has(str6)) {
                        }
                        if (jSONObject.has(str5)) {
                        }
                        if (jSONObject.has(str4)) {
                        }
                        if (jSONObject.has(str3)) {
                        }
                        if (jSONObject.has(str2)) {
                        }
                        if (jSONObject.has(str)) {
                        }
                        if (jSONObject.has("bca")) {
                        }
                        DecimalFormat decimalFormat3 = decimalFormat;
                        this.tv_TongTienNhan.setText(decimalFormat3.format(d2));
                        double d5 = d;
                        this.tv_TongTienChuyen.setText(decimalFormat3.format(d5));
                        this.tv_TongGiu.setText(decimalFormat3.format((-d2) - d5));
                        if (cursor != null) {
                            cursor.close();
                        }
                        XemListview();
                    }
                } else {
                    d = d3;
                }
                d2 = d4;
                try {
                    if (jSONObject.has("deb")) {
                        decimalFormat = decimalFormat2;
                    } else {
                        JSONObject jSONObject4 = new JSONObject(jSONObject.getString("deb"));
                        if (jSONObject4.getString("DiemNhan").length() > 0) {
                            TextView textView2 = this.deb_Nhan;
                            StringBuilder sb2 = new StringBuilder();
                            decimalFormat = decimalFormat2;
                            sb2.append(jSONObject4.getString("DiemNhan"));
                            sb2.append("(");
                            sb2.append(jSONObject4.getString("AnNhan"));
                            sb2.append(")");
                            textView2.setText(sb2.toString());
                            this.deb_NhanAn.setText(jSONObject4.getString("KQNhan"));
                        } else {
                            decimalFormat = decimalFormat2;
                        }
                        if (jSONObject4.getString("DiemChuyen").length() > 0) {
                            this.deb_Chuyen.setText(jSONObject4.getString("DiemChuyen") + "(" + jSONObject4.getString("AnChuyen") + ")");
                            this.deb_ChuyenAn.setText(jSONObject4.getString("KQChuyen"));
                        }
                    }
                    if (jSONObject.has(str21)) {
                        this.li_det.setVisibility(View.VISIBLE);
                        JSONObject jSONObject5 = new JSONObject(jSONObject.getString(str21));
                        if (jSONObject5.getString("DiemNhan").length() > 0) {
                            this.det_Nhan.setText(jSONObject5.getString("DiemNhan") + "(" + jSONObject5.getString("AnNhan") + ")");
                            this.det_NhanAn.setText(jSONObject5.getString("KQNhan"));
                        }
                        if (jSONObject5.getString("DiemChuyen").length() > 0) {
                            this.det_Chuyen.setText(jSONObject5.getString("DiemChuyen") + "(" + jSONObject5.getString("AnChuyen") + ")");
                            this.det_ChuyenAn.setText(jSONObject5.getString("KQChuyen"));
                        }
                    }
                    if (jSONObject.has(str20)) {
                        this.li_dec.setVisibility(View.VISIBLE);
                        JSONObject jSONObject6 = new JSONObject(jSONObject.getString(str20));
                        if (jSONObject6.getString("DiemNhan").length() > 0) {
                            this.dec_Nhan.setText(jSONObject6.getString("DiemNhan") + "(" + jSONObject6.getString("AnNhan") + ")");
                            this.dec_NhanAn.setText(jSONObject6.getString("KQNhan"));
                        }
                        if (jSONObject6.getString("DiemChuyen").length() > 0) {
                            this.dec_Chuyen.setText(jSONObject6.getString("DiemChuyen") + "(" + jSONObject6.getString("AnChuyen") + ")");
                            this.dec_ChuyenAn.setText(jSONObject6.getString("KQChuyen"));
                        }
                    }
                    if (jSONObject.has(str19)) {
                        this.li_ded.setVisibility(View.VISIBLE);
                        JSONObject jSONObject7 = new JSONObject(jSONObject.getString(str19));
                        if (jSONObject7.getString("DiemNhan").length() > 0) {
                            this.ded_Nhan.setText(jSONObject7.getString("DiemNhan") + "(" + jSONObject7.getString("AnNhan") + ")");
                            this.ded_NhanAn.setText(jSONObject7.getString("KQNhan"));
                        }
                        if (jSONObject7.getString("DiemChuyen").length() > 0) {
                            this.ded_Chuyen.setText(jSONObject7.getString("DiemChuyen") + "(" + jSONObject7.getString("AnChuyen") + ")");
                            this.ded_ChuyenAn.setText(jSONObject7.getString("KQChuyen"));
                        }
                    }
                    if (jSONObject.has(str6)) {
                        JSONObject jSONObject8 = new JSONObject(jSONObject.getString(str6));
                        if (jSONObject8.getString("DiemNhan").length() > 0) {
                            this.lo_Nhan.setText(jSONObject8.getString("DiemNhan") + "(" + jSONObject8.getString("AnNhan") + ")");
                            this.lo_NhanAn.setText(jSONObject8.getString("KQNhan"));
                        }
                        if (jSONObject8.getString("DiemChuyen").length() > 0) {
                            this.lo_Chuyen.setText(jSONObject8.getString("DiemChuyen") + "(" + jSONObject8.getString("AnChuyen") + ")");
                            this.lo_ChuyenAn.setText(jSONObject8.getString("KQChuyen"));
                        }
                    }
                    if (jSONObject.has(str5)) {
                        this.li_loa.setVisibility(View.VISIBLE);
                        JSONObject jSONObject9 = new JSONObject(jSONObject.getString(str5));
                        if (jSONObject9.getString("DiemNhan").length() > 0) {
                            this.loa_Nhan.setText(jSONObject9.getString("DiemNhan") + "(" + jSONObject9.getString("AnNhan") + ")");
                            this.loa_NhanAn.setText(jSONObject9.getString("KQNhan"));
                        }
                        if (jSONObject9.getString("DiemChuyen").length() > 0) {
                            this.loa_Chuyen.setText(jSONObject9.getString("DiemChuyen") + "(" + jSONObject9.getString("AnChuyen") + ")");
                            this.loa_ChuyenAn.setText(jSONObject9.getString("KQChuyen"));
                        }
                    }
                    if (jSONObject.has(str4)) {
                        JSONObject jSONObject10 = new JSONObject(jSONObject.getString(str4));
                        if (jSONObject10.getString("DiemNhan").length() > 0) {
                            this.xi2_Nhan.setText(jSONObject10.getString("DiemNhan") + "(" + jSONObject10.getString("AnNhan") + ")");
                            this.xi2_NhanAn.setText(jSONObject10.getString("KQNhan"));
                        }
                        if (jSONObject10.getString("DiemChuyen").length() > 0) {
                            this.xi2_Chuyen.setText(jSONObject10.getString("DiemChuyen") + "(" + jSONObject10.getString("AnChuyen") + ")");
                            this.xi2_ChuyenAn.setText(jSONObject10.getString("KQChuyen"));
                        }
                    }
                    if (jSONObject.has(str3)) {
                        this.li_xn.setVisibility(View.VISIBLE);
                        JSONObject jSONObject11 = new JSONObject(jSONObject.getString(str3));
                        if (jSONObject11.getString("DiemNhan").length() > 0) {
                            this.xn_Nhan.setText(jSONObject11.getString("DiemNhan") + "(" + jSONObject11.getString("AnNhan") + ")");
                            this.xn_NhanAn.setText(jSONObject11.getString("KQNhan"));
                        }
                        if (jSONObject11.getString("DiemChuyen").length() > 0) {
                            this.xn_Chuyen.setText(jSONObject11.getString("DiemChuyen") + "(" + jSONObject11.getString("AnChuyen") + ")");
                            this.xn_ChuyenAn.setText(jSONObject11.getString("KQChuyen"));
                        }
                    }
                    if (jSONObject.has(str2)) {
                        this.li_xia2.setVisibility(View.VISIBLE);
                        JSONObject jSONObject12 = new JSONObject(jSONObject.getString(str2));
                        if (jSONObject12.getString("DiemNhan").length() > 0) {
                            this.xia2_Nhan.setText(jSONObject12.getString("DiemNhan") + "(" + jSONObject12.getString("AnNhan") + ")");
                            this.xia2_NhanAn.setText(jSONObject12.getString("KQNhan"));
                        }
                        if (jSONObject12.getString("DiemChuyen").length() > 0) {
                            this.xia2_Chuyen.setText(jSONObject12.getString("DiemChuyen") + "(" + jSONObject12.getString("AnChuyen") + ")");
                            this.xia2_ChuyenAn.setText(jSONObject12.getString("KQChuyen"));
                        }
                    }
                    if (jSONObject.has(str)) {
                        JSONObject jSONObject13 = new JSONObject(jSONObject.getString(str));
                        if (jSONObject13.getString("DiemNhan").length() > 0) {
                            this.bc_Nhan.setText(jSONObject13.getString("DiemNhan") + "(" + jSONObject13.getString("AnNhan") + ")");
                            this.bc_NhanAn.setText(jSONObject13.getString("KQNhan"));
                        }
                        if (jSONObject13.getString("DiemChuyen").length() > 0) {
                            this.bc_Chuyen.setText(jSONObject13.getString("DiemChuyen") + "(" + jSONObject13.getString("AnChuyen") + ")");
                            this.bc_ChuyenAn.setText(jSONObject13.getString("KQChuyen"));
                        }
                    }
                    if (jSONObject.has("bca")) {
                        this.li_bca.setVisibility(View.VISIBLE);
                        JSONObject jSONObject14 = new JSONObject(jSONObject.getString("bca"));
                        if (jSONObject14.getString("DiemNhan").length() > 0) {
                            this.bca_Nhan.setText(jSONObject14.getString("DiemNhan") + "(" + jSONObject14.getString("AnNhan") + ")");
                            this.bca_NhanAn.setText(jSONObject14.getString("KQNhan"));
                        }
                        if (jSONObject14.getString("DiemChuyen").length() > 0) {
                            this.bca_Chuyen.setText(jSONObject14.getString("DiemChuyen") + "(" + jSONObject14.getString("AnChuyen") + ")");
                            this.bca_ChuyenAn.setText(jSONObject14.getString("KQChuyen"));
                        }
                    }
                    DecimalFormat decimalFormat32 = decimalFormat;
                    this.tv_TongTienNhan.setText(decimalFormat32.format(d2));
                    double d52 = d;
                    this.tv_TongTienChuyen.setText(decimalFormat32.format(d52));
                    this.tv_TongGiu.setText(decimalFormat32.format((-d2) - d52));
                } catch (JSONException unused7) {
                }
            } else {
                cursor = GetData;
            }
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            XemListview();
        }
    }

    /*  JADX ERROR: Types fix failed
        jadx.core.utils.exceptions.JadxOverflowException: Type inference error: updates count limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:96)
        */
    public void Dialog(String r42) {
        /*
            Method dump skipped, instructions count: 1230
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: tamhoang.ldpro4.Fragment.Frag_No_new.Dialog(java.lang.String):void");
    }

    @Override // android.support.v4.app.Fragment
    public View onCreateView(LayoutInflater viewr, ViewGroup container, Bundle savedInstanceState) {
        this.v = viewr.inflate(R.layout.frag_norp1, container, false);
        this.db = new Database(getActivity());
        init();
        this.progressBar = (ProgressBar) this.v.findViewById(R.id.progressBar);
        this.btn_nt =  this.v.findViewById(R.id.btn_nt);
        this.lv_baocaoKhach.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() { // from class: tamhoang.ldpro4.Fragment.Frag_No_new.2
            @Override // android.widget.AdapterView.OnItemLongClickListener
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Frag_No_new.this.position = i;
                Frag_No_new frag_No_new = Frag_No_new.this;
                frag_No_new.Dialog((String) frag_No_new.mTenKH.get(i));
                return false;
            }
        });
        btn_nt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Congthuc.CheckTime("18:30")) {
                    Handler handler = new Handler();
                    handler.postDelayed(runnable, 1000L);
                }
                lv_baoCao();
            }
        });
        return this.v;
    }

    @Override // android.support.v4.app.Fragment
    public void onDestroy() {
        try {
            this.mTenKH.clear();
            this.mSDT.clear();
            this.lv_baocaoKhach.setAdapter(null);
            this.db.close();
        } catch (Exception unused) {
        }
        super.onDestroy();
    }

    @Override // android.support.v4.app.Fragment
    public void onStop() {
        this.Running = false;
        super.onStop();
        try {
            this.handler.removeCallbacks(this.runnable);
        } catch (Exception unused) {
        }
    }
}
