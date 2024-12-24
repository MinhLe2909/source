package tamhoang.ldpro4.ui.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import org.json.JSONException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import tamhoang.ldpro4.formula.Congthuc;
import tamhoang.ldpro4.ui.home.MainActivity;
import tamhoang.ldpro4.R;
import tamhoang.ldpro4.data.Database;

/* loaded from: classes.dex */
public class Frag_Suatin extends Fragment {
    String CurDate;
    String Cur_date;
    Button btn_LoadTin;
    Button btn_suatin;
    Button btn_tai_All;
    Database db;
    EditText editTsuatin;
    boolean error;
    Handler handler = new Handler();
    ListView lv_suatin;
    RadioButton radio_SuaTin;
    RadioButton radio_TaiTin;
    Spinner sp_TenKH;
    String str;
    int type_kh;
    View v;
    public List<Integer> mID = new ArrayList();
    public List<String> mNgay = new ArrayList();
    public List<String> mSDT = new ArrayList();
    public List<String> mTenKH = new ArrayList();
    public List<Integer> mSoTinNhan = new ArrayList();
    public List<String> mTinNhanGoc = new ArrayList();
    public List<String> mND_DaSua = new ArrayList();
    public List<String> mND_PhanTich = new ArrayList();
    public List<String> mPhatHienLoi = new ArrayList();
    public List<Integer> mTypeKH = new ArrayList();
    int lv_position = -1;
    int spin_pointion = -1;
    public List<String> mContact = new ArrayList();
    public List<String> mMobile = new ArrayList();
    public List<String> mApp = new ArrayList();
    public List<Integer> mType_kh = new ArrayList();
    private Runnable runnable = new Runnable() {
        @Override // java.lang.Runnable
        public void run() {
            new MainActivity();
            if (MainActivity.sms) {
                xem_lv();
                MainActivity.sms = false;
            }
            handler.postDelayed(this, 1000L);
        }
    };
    private Runnable xulyTinnhan = new Runnable() {
        @Override
        public void run() {
            Cursor GetData;
            error = true;
            Cursor cursor = null;
            if (editTsuatin.getText().toString().length() < 6) {
                error = false;
            } else if (lv_position < 0 || !Congthuc.CheckDate(MainActivity.myDate)) {
                error = false;
                if (!Congthuc.CheckDate(MainActivity.myDate)) {
                    try {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("Thông báo:");
                        builder.setMessage("Đã hết hạn sử dụng phần mềm\n\nHãy liên hệ đại lý hoặc SĐT: " + MainActivity.listKH.getString("k_tra") + " để gia hạn");
                        builder.setNegativeButton("Đóng", new DialogInterface.OnClickListener() { // from class: tamhoang.ldpro4.Fragment.Frag_Suatin.14.2
                            @Override // android.content.DialogInterface.OnClickListener
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                        builder.create().show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (MainActivity.Acc_manager.length() == 0) {
                    AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity());
                    builder2.setTitle("Thông báo:");
                    builder2.setMessage("Kiểm tra kết nối Internet!");
                    builder2.setNegativeButton("Đóng", new DialogInterface.OnClickListener() { // from class: tamhoang.ldpro4.Fragment.Frag_Suatin.14.1
                        @Override // android.content.DialogInterface.OnClickListener
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    builder2.create().show();
                } else {
                    Add_tin();
                }
            } else {
                db.QueryData("Update tbl_tinnhanS Set nd_phantich = '" + editTsuatin.getText().toString() + "', nd_sua = '" + editTsuatin.getText().toString() + "' WHERE id = " + mID.get(lv_position));
                Database database = db;
                StringBuilder sb = new StringBuilder();
                sb.append("Select type_kh From tbl_tinnhanS WHERE id = ");
                sb.append(mID.get(lv_position));
                cursor = database.GetData(sb.toString());
                cursor.moveToFirst();
                try {
                    db.Update_TinNhanGoc(mID.get(lv_position).intValue(), cursor.getInt(0));
                } catch (Exception unused) {
                    db.QueryData("Update tbl_tinnhanS set phat_hien_loi = 'ko' WHERE id = " + mID.get(lv_position));
                    db.QueryData("Delete From tbl_soctS WHERE ngay_nhan = '" + mNgay.get(lv_position) + "' AND so_dienthoai = '" + mSDT.get(lv_position) + "' AND so_tin_nhan = " + mSoTinNhan.get(lv_position) + " AND type_kh = " + cursor.getString(0));
                    error = false;
                    Toast.makeText(getActivity(), "Đã xảy ra lỗi!", Toast.LENGTH_LONG).show();
                }
                if (!Congthuc.CheckTime("18:30") && Cur_date.contains(CurDate)) {
                    db.Gui_Tin_Nhan(mID.get(lv_position).intValue());
                }
                GetData = db.GetData("Select * FROM tbl_tinnhanS Where id = " + mID.get(lv_position));
                GetData.moveToFirst();
                if (GetData.getString(11).indexOf("Không hiểu") > -1) {
                    String replace = GetData.getString(10).replace("LDVN", "<font color='#FF0000'>");
                    editTsuatin.setText(Html.fromHtml(replace));
                    if (GetData.getString(10).indexOf("LDVN") > -1) {
                        try {
                            editTsuatin.setSelection(replace.indexOf("<font"));
                        } catch (Exception unused2) {
                        }
                    }
                } else {
                    editTsuatin.setText("");
                    xem_lv();
                    if (mND_DaSua.size() > 0) {
                        lv_position = 0;
                        if (mPhatHienLoi.get(0).indexOf("Không hiểu") <= -1) {
                            editTsuatin.setText(mND_DaSua.get(0));
                            if (cursor != null) {
                                cursor.close();
                            }
                            if (GetData != null) {
                                GetData.close();
                            }
                            if (error) {
                                handler.postDelayed(this, 300L);
                                return;
                            } else {
                                handler.removeCallbacks(xulyTinnhan);
                                return;
                            }
                        }
                        editTsuatin.setText(Html.fromHtml(mND_PhanTich.get(0).replace("LDVN", "<font color='#FF0000'>")));
                        int indexOf = mND_PhanTich.get(0).indexOf("LDVN");
                        if (indexOf > -1) {
                            try {
                                editTsuatin.setSelection(indexOf);
                            } catch (Exception unused3) {
                            }
                        }
                        sp_TenKH.setSelection(mContact.indexOf(mTenKH.get(0)));
                    } else {
                        lv_position = -1;
                    }
                }
                error = false;
            }
        }
    };

    public class TNGAdapter extends ArrayAdapter {
        class ViewHolder {
            TextView tinNhanGoc;
            TextView phatHienLoi;

            ViewHolder() {
            }
        }

        public TNGAdapter(Context context, int layout, List<String> list) {
            super(context, layout, list);
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            if (view == null) {
                view = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.frag_suatin_lv, (ViewGroup) null);
                viewHolder = new ViewHolder();
                viewHolder.tinNhanGoc = view.findViewById(R.id.tv_suatin_nd);
                viewHolder.phatHienLoi = view.findViewById(R.id.tv_suatin_err);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            viewHolder.tinNhanGoc.setText(mTinNhanGoc.get(i));
            viewHolder.phatHienLoi.setText(mPhatHienLoi.get(i));
            return view;
        }
    }

    public void Add_tin() {
        final MainActivity mainActivity = new MainActivity();
        if (this.mContact.size() <= 0 || this.editTsuatin.getText().toString().length() <= 6) {
            return;
        }
        new MainActivity();
        final String Get_date = MainActivity.Get_date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getDefault());
        final String format = simpleDateFormat.format(calendar.getTime());
        String str2 = "Select * From tbl_tinnhanS WHERE nd_goc = '" + this.editTsuatin.getText().toString().replaceAll("'", "").trim() + "' AND ngay_nhan = '" + Get_date + "' AND so_dienthoai = '" + this.mMobile.get(this.spin_pointion) + "'";
        this.str = str2;
        Cursor GetData = this.db.GetData(str2);
        GetData.moveToFirst();
        final Cursor GetData2 = this.db.GetData("Select * From tbl_kh_new Where sdt = '" + this.mMobile.get(this.spin_pointion) + "'");
        GetData2.moveToFirst();
        if (this.spin_pointion <= -1 || GetData.getCount() != 0) {
            if (GetData.getCount() > 0) {
                str = "Đã có tin này trong CSDL!";
            } else {
                str = "Hãy chọn tên khách hàng";
            }
            Toast.makeText(requireActivity(), str, Toast.LENGTH_LONG).show();
        } else if (GetData2.getInt(3) == 3) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Chọn loại tin nhắn:");
            builder.setMessage("Đây là khách vừa nhận vừa chuyển, thêm tin nhận hay tin chuyển?");
            builder.setNeutralButton("Tin nhận", new DialogInterface.OnClickListener() { // from class: tamhoang.ldpro4.Fragment.Frag_Suatin.9
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int i) {
                    type_kh = 1;
                    Cursor GetData3 = db.GetData("Select max(so_tin_nhan) from tbl_tinnhanS WHERE ngay_nhan = '" + Get_date + "' AND so_dienthoai = '" + mMobile.get(spin_pointion) + "' AND type_kh = " + type_kh);
                    GetData3.moveToFirst();
                    db.QueryData("Insert Into tbl_tinnhanS values (null, '" + Get_date + "', '" + format + "', " + type_kh + ", '" + mContact.get(spin_pointion) + "', '" + mMobile.get(spin_pointion) + "', '" + GetData2.getString(2) + "', " + (GetData3.getInt(0) + 1) + ", '" + editTsuatin.getText().toString().replace("'", " ").trim() + "', '" + editTsuatin.getText().toString().replace("'", " ").trim() + "', '" + editTsuatin.getText().toString().replace("'", " ").trim() + "', 'ko',0, 0, 0, null)");
                    editTsuatin.setText("");
                    Database database = db;
                    StringBuilder sb = new StringBuilder();
                    sb.append("Select id From tbl_tinnhanS WHERE ngay_nhan = '");
                    sb.append(Get_date);
                    sb.append("' AND so_dienthoai = '");
                    sb.append(mMobile.get(spin_pointion));
                    sb.append("' AND so_tin_nhan = ");
                    sb.append(GetData3.getInt(0) + 1);
                    sb.append(" AND type_kh = ");
                    sb.append(type_kh);
                    Cursor GetData4 = database.GetData(sb.toString());
                    GetData4.moveToFirst();
                    if (Congthuc.CheckDate(MainActivity.myDate)) {
                        try {
                            db.Update_TinNhanGoc(GetData4.getInt(0), GetData2.getInt(3));
                        } catch (Exception unused) {
                            db.QueryData("Update tbl_tinnhanS set phat_hien_loi = 'ko' WHERE id = " + GetData4.getInt(0));
                            str = "Delete From tbl_soctS WHERE ngay_nhan = '" + Get_date + "' AND so_dienthoai = '" + mMobile.get(spin_pointion) + "' AND so_tin_nhan = " + (GetData3.getInt(0) + 1) + " AND type_kh = " + type_kh;
                            db.QueryData(str);
                        }
                    } else if (MainActivity.Acc_manager.length() == 0) {
                        AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity());
                        builder2.setTitle("Thông báo:");
                        builder2.setMessage("Kiểm tra kết nối Internet!");
                        builder2.setNegativeButton("Đóng", new DialogInterface.OnClickListener() { // from class: tamhoang.ldpro4.Fragment.Frag_Suatin.9.1
                            @Override // android.content.DialogInterface.OnClickListener
                            public void onClick(DialogInterface dialogInterface2, int i2) {
                                dialogInterface2.cancel();
                            }
                        });
                        builder2.create().show();
                    } else {
                        try {
                            AlertDialog.Builder builder3 = new AlertDialog.Builder(getActivity());
                            builder3.setTitle("Thông báo:");
                            builder3.setMessage("Đã hết hạn sử dụng phần mềm\n\nHãy liên hệ đại lý hoặc SĐT: " + MainActivity.listKH.getString("k_tra") + " để gia hạn");
                            builder3.setNegativeButton("Đóng", new DialogInterface.OnClickListener() { // from class: tamhoang.ldpro4.Fragment.Frag_Suatin.9.2
                                @Override // android.content.DialogInterface.OnClickListener
                                public void onClick(DialogInterface dialogInterface2, int i2) {
                                    dialogInterface2.cancel();
                                }
                            });
                            builder3.create().show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    GetData3.close();
                    GetData2.close();
                    GetData4.close();
                    dialogInterface.cancel();
                    MainActivity.sms = true;
                }
            });
            builder.setPositiveButton("Tin Chuyển", new DialogInterface.OnClickListener() { // from class: tamhoang.ldpro4.Fragment.Frag_Suatin.10
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int i) {
                    type_kh = 2;
                    Cursor GetData3 = db.GetData("Select max(so_tin_nhan) from tbl_tinnhanS WHERE ngay_nhan = '" + Get_date + "' AND so_dienthoai = '" + mMobile.get(spin_pointion) + "' AND type_kh = " + type_kh);
                    GetData3.moveToFirst();
                    db.QueryData("Insert Into tbl_tinnhanS values (null, '" + Get_date + "', '" + format + "', " + type_kh + ", '" + mContact.get(spin_pointion) + "', '" + mMobile.get(spin_pointion) + "', '" + GetData2.getString(2) + "', " + (GetData3.getInt(0) + 1) + ", '" + editTsuatin.getText().toString().replaceAll("'", " ").trim() + "', '" + editTsuatin.getText().toString().replaceAll("'", " ").trim() + "', '" + editTsuatin.getText().toString().replaceAll("'", " ").trim() + "', 'ko',0, 0, 0, null)");
                    editTsuatin.setText("");
                    Database database = db;
                    StringBuilder sb = new StringBuilder();
                    sb.append("Select id From tbl_tinnhanS WHERE ngay_nhan = '");
                    sb.append(Get_date);
                    sb.append("' AND so_dienthoai = '");
                    sb.append(mMobile.get(spin_pointion));
                    sb.append("' AND so_tin_nhan = ");
                    sb.append(GetData3.getInt(0) + 1);
                    sb.append(" AND type_kh = ");
                    sb.append(type_kh);
                    Cursor GetData4 = database.GetData(sb.toString());
                    GetData4.moveToFirst();
                    if (Congthuc.CheckDate(MainActivity.myDate)) {
                        try {
                            db.Update_TinNhanGoc(GetData4.getInt(0), GetData2.getInt(3));
                        } catch (Exception unused) {
                            db.QueryData("Update tbl_tinnhanS set phat_hien_loi = 'ko' WHERE id = " + GetData4.getInt(0));
                            str = "Delete From tbl_soctS WHERE ngay_nhan = '" + Get_date + "' AND so_dienthoai = '" + mMobile.get(spin_pointion) + "' AND so_tin_nhan = " + (GetData3.getInt(0) + 1) + " AND type_kh = " + type_kh;
                            db.QueryData(str);
                            Toast.makeText(getActivity(), "Đã xảy ra lỗi!", Toast.LENGTH_LONG).show();
                        }
                    } else if (MainActivity.Acc_manager.length() == 0) {
                        AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity());
                        builder2.setTitle("Thông báo:");
                        builder2.setMessage("Kiểm tra kết nối Internet!");
                        builder2.setNegativeButton("Đóng", new DialogInterface.OnClickListener() { // from class: tamhoang.ldpro4.Fragment.Frag_Suatin.10.1
                            @Override // android.content.DialogInterface.OnClickListener
                            public void onClick(DialogInterface dialogInterface2, int i2) {
                                dialogInterface2.cancel();
                            }
                        });
                        builder2.create().show();
                    } else {
                        try {
                            AlertDialog.Builder builder3 = new AlertDialog.Builder(getActivity());
                            builder3.setTitle("Thông báo:");
                            builder3.setMessage("Đã hết hạn sử dụng phần mềm\n\nHãy liên hệ đại lý hoặc SĐT: " + MainActivity.listKH.getString("k_tra") + " để gia hạn");
                            builder3.setNegativeButton("Đóng", new DialogInterface.OnClickListener() { // from class: tamhoang.ldpro4.Fragment.Frag_Suatin.10.2
                                @Override // android.content.DialogInterface.OnClickListener
                                public void onClick(DialogInterface dialogInterface2, int i2) {
                                    dialogInterface2.cancel();
                                }
                            });
                            builder3.create().show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    GetData3.close();
                    GetData2.close();
                    GetData4.close();
                    dialogInterface.cancel();
                    MainActivity.sms = true;
                }
            });
            builder.create().show();
        } else {
            this.type_kh = GetData2.getInt(3);
            Cursor GetData3 = this.db.GetData("Select max(so_tin_nhan) from tbl_tinnhanS WHERE ngay_nhan = '" + Get_date + "' AND so_dienthoai = '" + this.mMobile.get(this.spin_pointion) + "' AND type_kh = " + this.type_kh);
            GetData3.moveToFirst();
            this.db.QueryData("Insert Into tbl_tinnhanS values (null, '" + Get_date + "', '" + format + "', " + this.type_kh + ", '" + this.mContact.get(this.spin_pointion) + "', '" + this.mMobile.get(this.spin_pointion) + "', '" + GetData2.getString(2) + "', " + (GetData3.getInt(0) + 1) + ", '" + this.editTsuatin.getText().toString().replaceAll("'", " ").trim() + "', '" + this.editTsuatin.getText().toString().replaceAll("'", " ").trim() + "', '" + this.editTsuatin.getText().toString().replaceAll("'", " ").trim() + "', 'ko',0, 0, 0, null)");
            this.editTsuatin.setText("");
            Database database = this.db;
            StringBuilder sb = new StringBuilder();
            sb.append("Select id From tbl_tinnhanS WHERE ngay_nhan = '");
            sb.append(Get_date);
            sb.append("' AND so_dienthoai = '");
            sb.append(this.mMobile.get(this.spin_pointion));
            sb.append("' AND so_tin_nhan = ");
            sb.append(GetData3.getInt(0) + 1);
            sb.append(" AND type_kh = ");
            sb.append(this.type_kh);
            Cursor GetData4 = database.GetData(sb.toString());
            GetData4.moveToFirst();
            if (Congthuc.CheckDate(MainActivity.myDate)) {
                try {
                    this.db.Update_TinNhanGoc(GetData4.getInt(0), GetData2.getInt(3));
                } catch (Exception unused) {
                    this.db.QueryData("Update tbl_tinnhanS set phat_hien_loi = 'ko' WHERE id = " + GetData4.getInt(0));
                    String str3 = "Delete From tbl_soctS WHERE ngay_nhan = '" + Get_date + "' AND so_dienthoai = '" + this.mMobile.get(this.spin_pointion) + "' AND so_tin_nhan = " + (GetData3.getInt(0) + 1) + " AND type_kh = " + this.type_kh;
                    this.str = str3;
                    this.db.QueryData(str3);
                    Toast.makeText(getActivity(), "Đã xảy ra lỗi!", Toast.LENGTH_LONG).show();
                }
            } else if (MainActivity.Acc_manager.length() == 0) {
                AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity());
                builder2.setTitle("Thông báo:");
                builder2.setMessage("Kiểm tra kết nối Internet!");
                builder2.setNegativeButton("Đóng", new DialogInterface.OnClickListener() { // from class: tamhoang.ldpro4.Fragment.Frag_Suatin.11
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder2.create().show();
            } else {
                try {
                    AlertDialog.Builder builder3 = new AlertDialog.Builder(getActivity());
                    builder3.setTitle("Thông báo:");
                    builder3.setMessage("Đã hết hạn sử dụng phần mềm\n\nHãy liên hệ đại lý hoặc SĐT: " + MainActivity.listKH.getString("k_tra") + " để gia hạn");
                    builder3.setNegativeButton("Đóng", new DialogInterface.OnClickListener() { // from class: tamhoang.ldpro4.Fragment.Frag_Suatin.12
                        @Override // android.content.DialogInterface.OnClickListener
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    builder3.create().show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            MainActivity.sms = true;
            GetData3.close();
            GetData2.close();
            GetData4.close();
        }
        xem_lv();
        if (GetData != null) {
            GetData.close();
        }
    }

    public void control_RadioButton() {
        LinearLayout liner_suatin =  this.v.findViewById(R.id.liner_suatin);
        LinearLayout li_KhachHang =  this.v.findViewById(R.id.li_KhachHang);
        LinearLayout li_editTinNhan =  this.v.findViewById(R.id.edittinnhan);
        if (this.radio_SuaTin.isChecked()) {
            li_editTinNhan.setVisibility(View.VISIBLE);
            liner_suatin.setVisibility(View.VISIBLE);
            li_KhachHang.setVisibility(View.VISIBLE);
            this.btn_LoadTin.setVisibility(View.GONE);
            this.btn_suatin.setVisibility(View.VISIBLE);
            this.editTsuatin.setVisibility(View.VISIBLE);
            this.btn_tai_All.setVisibility(View.GONE);
        } else {
            if (!this.radio_TaiTin.isChecked()) {
                return;
            }
            li_editTinNhan.setVisibility(View.VISIBLE);
            liner_suatin.setVisibility(View.VISIBLE);
            li_KhachHang.setVisibility(View.VISIBLE);
            this.btn_suatin.setVisibility(View.GONE);
            this.btn_LoadTin.setVisibility(View.VISIBLE);
            this.editTsuatin.setVisibility(View.GONE);
            this.btn_tai_All.setVisibility(View.VISIBLE);
        }
        xem_lv();
    }

    public void getAllChat(int i) {
        String str;
        StringBuilder sb;
        Cursor GetData = null;
        SQLiteDatabase writableDatabase = null;
        DatabaseUtils.InsertHelper insertHelper = null;
        new MainActivity();
        String Get_date = MainActivity.Get_date();
        StringBuilder sb2 = new StringBuilder();
        sb2.append("DELETE FROM tbl_soctS WHERE ngay_nhan = '");
        sb2.append(Get_date);
        sb2.append("' AND so_dienthoai = '");
        sb2.append(this.mMobile.get(this.spin_pointion));
        String str2 = "'";
        sb2.append("'");
        this.db.QueryData(sb2.toString());
        this.db.QueryData("DELETE FROM tbl_tinnhanS WHERE ngay_nhan = '" + Get_date + "' AND so_dienthoai = '" + this.mMobile.get(this.spin_pointion) + "'");
        StringBuilder sb3 = new StringBuilder();
        sb3.append("Select * From tbl_kh_new Where sdt = '");
        sb3.append(this.mMobile.get(this.spin_pointion));
        sb3.append("'");
        Cursor GetData2 = db.GetData(sb3.toString());
        GetData2.moveToFirst();
        try {
            if (i == 1) {
                sb = new StringBuilder();
                sb.append("Select * From Chat_database Where ngay_nhan = '");
                sb.append(Get_date);
                sb.append("' AND ten_kh = '");
                sb.append(this.mContact.get(this.spin_pointion));
                str2 = "' and type_kh = 1";
            } else if (i == 2) {
                sb = new StringBuilder();
                sb.append("Select * From Chat_database Where ngay_nhan = '");
                sb.append(Get_date);
                sb.append("' AND ten_kh = '");
                sb.append(this.mContact.get(this.spin_pointion));
                str2 = "' and type_kh = 2";
            } else {
                if (i != 3) {
                    str = null;
                    GetData = this.db.GetData(str);
                    writableDatabase = this.db.getWritableDatabase();
                    insertHelper = new DatabaseUtils.InsertHelper(writableDatabase, "tbl_tinnhanS");
                    writableDatabase.beginTransaction();
                    if (GetData.getCount() > 0) {
                        int i2 = 0;
                        while (GetData.moveToNext()) {
                            i2++;
                            insertHelper.prepareForInsert();
                            insertHelper.bind(insertHelper.getColumnIndex("ngay_nhan"), Get_date);
                            insertHelper.bind(insertHelper.getColumnIndex("gio_nhan"), GetData.getString(2));
                            insertHelper.bind(insertHelper.getColumnIndex("type_kh"), GetData.getString(3));
                            insertHelper.bind(insertHelper.getColumnIndex("ten_kh"), this.mContact.get(this.spin_pointion));
                            insertHelper.bind(insertHelper.getColumnIndex("so_dienthoai"), this.mMobile.get(this.spin_pointion));
                            insertHelper.bind(insertHelper.getColumnIndex("use_app"), GetData2.getInt(2));
                            insertHelper.bind(insertHelper.getColumnIndex("so_tin_nhan"), i2);
                            insertHelper.bind(insertHelper.getColumnIndex("nd_goc"), GetData.getString(7));
                            insertHelper.bind(insertHelper.getColumnIndex("nd_sua"), GetData.getString(7));
                            insertHelper.bind(insertHelper.getColumnIndex("nd_phantich"), GetData.getString(7));
                            insertHelper.bind(insertHelper.getColumnIndex("phat_hien_loi"), "ko");
                            insertHelper.bind(insertHelper.getColumnIndex("tinh_tien"), 0);
                            insertHelper.bind(insertHelper.getColumnIndex("ok_tn"), 0);
                            insertHelper.bind(insertHelper.getColumnIndex("del_sms"), 0);
                            insertHelper.execute();
                        }
                    }
                    GetData.close();
                    writableDatabase.setTransactionSuccessful();
                    writableDatabase.endTransaction();
                    insertHelper.close();
                    writableDatabase.close();
                    this.db.QueryData("Delete From tbl_tinnhanS where substr(nd_goc,0,7) = 'Ok Tin'");
                    this.db.QueryData("Delete From tbl_tinnhanS where length(nd_goc) < 4");
                    xem_lv();
                    Toast.makeText(getActivity(), "Đã tải xong tin nhắn!", Toast.LENGTH_LONG).show();
                    GetData2.close();
                    return;
                }
                sb = new StringBuilder();
                sb.append("Select * From Chat_database Where ngay_nhan = '");
                sb.append(Get_date);
                sb.append("' AND ten_kh = '");
                sb.append(this.mContact.get(this.spin_pointion));
            }
            GetData.close();
            writableDatabase.setTransactionSuccessful();
            writableDatabase.endTransaction();
            insertHelper.close();
            writableDatabase.close();
            this.db.QueryData("Delete From tbl_tinnhanS where substr(nd_goc,0,7) = 'Ok Tin'");
            this.db.QueryData("Delete From tbl_tinnhanS where length(nd_goc) < 4");
            xem_lv();
            Toast.makeText(getActivity(), "Đã tải xong tin nhắn!", Toast.LENGTH_LONG).show();
            GetData2.close();
        } catch (Throwable th) {
            writableDatabase.endTransaction();
            insertHelper.close();
            writableDatabase.close();
            th.printStackTrace();
        }
    }

    public void getFullSms(String r39) throws ParseException {
        /*
            Method dump skipped, instructions count: 1710
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: tamhoang.ldpro4.Fragment.Frag_Suatin.getFullSms(java.lang.String):void");
    }

    @Override // android.support.v4.app.Fragment
    public boolean onContextItemSelected(MenuItem menuItem) {
        super.onContextItemSelected(menuItem);
        if (menuItem.getItemId() == 1) {
            if (this.lv_position >= 0) {
                this.db.QueryData("Delete FROM tbl_tinnhanS WHERE id = " + this.mID.get(this.lv_position));
                this.lv_position = -1;
                xem_lv();
                Toast.makeText(getActivity(), "Xoá thành công", Toast.LENGTH_LONG).show();
                this.editTsuatin.setText("");
            }
            xem_lv();
            if (this.mND_DaSua.size() > 0) {
                this.lv_position = 0;
                if (this.mPhatHienLoi.get(0).indexOf("Không hiểu") > -1) {
                    this.editTsuatin.setText(Html.fromHtml(this.mND_PhanTich.get(0).replace("LDVN", "<font color='#FF0000'>")));
                    int indexOf = this.mND_PhanTich.get(0).indexOf("LDVN");
                    if (indexOf > -1) {
                        try {
                            this.editTsuatin.setSelection(indexOf);
                        } catch (Exception unused) {
                        }
                    }
                    this.sp_TenKH.setSelection(this.mContact.indexOf(this.mTenKH.get(0)));
                } else {
                    this.editTsuatin.setText(this.mND_DaSua.get(0));
                }
            } else {
                this.lv_position = -1;
            }
            this.error = false;
        }
        if (menuItem.getItemId() == 2) {
            if (this.lv_position >= 0) {
                new MainActivity();
                String Get_date = MainActivity.Get_date();
                this.db.QueryData("Delete FROM tbl_tinnhanS WHERE phat_hien_loi <> 'ok' And ngay_nhan = '" + Get_date + "'");
                this.lv_position = -1;
                xem_lv();
                Toast.makeText(getActivity(), "Xoá thành công", Toast.LENGTH_LONG).show();
                this.editTsuatin.setText("");
            }
            xem_lv();
        }
        return true;
    }

    @Override // android.support.v4.app.Fragment, android.view.View.OnCreateContextMenuListener
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        super.onCreateContextMenu(contextMenu, view, contextMenuInfo);
        contextMenu.add(0, 1, 0, "Xóa tin này?");
    }

    @Override // android.support.v4.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.v = layoutInflater.inflate(R.layout.frag_suatin, viewGroup, false);
        this.db = new Database(getActivity());
        this.btn_suatin =  this.v.findViewById(R.id.btn_login);
        this.btn_LoadTin =  this.v.findViewById(R.id.btn_congno);
        this.editTsuatin =  this.v.findViewById(R.id.editText_suatin);
        this.btn_tai_All =  this.v.findViewById(R.id.btn_nt);
        this.sp_TenKH = (Spinner) this.v.findViewById(R.id.sp_bo_tintrung);
        this.radio_SuaTin = (RadioButton) this.v.findViewById(R.id.radio_Deb);
        this.radio_TaiTin = (RadioButton) this.v.findViewById(R.id.rad_chuyenngay);
        this.lv_suatin = (ListView) this.v.findViewById(R.id.lv_Thaythe);
        handler.postDelayed(this.runnable, 1000L);
        new MainActivity();
        final String Get_date = MainActivity.Get_date();
        this.btn_suatin.setOnClickListener(view -> {
            new MainActivity();
            CurDate = MainActivity.Get_date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Cur_date = simpleDateFormat.format(new Date());
            handler.postDelayed(xulyTinnhan, 300L);
        });
        this.lv_suatin.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: tamhoang.ldpro4.Fragment.Frag_Suatin.2
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                lv_position = i;
                editTsuatin.setText(Html.fromHtml(mND_PhanTich.get(i).replace("LDVN", "<font color='#FF0000'>")));
                int indexOf = mND_PhanTich.get(i).indexOf("LDVN");
                if (indexOf > -1) {
                    try {
                        editTsuatin.setSelection(indexOf);
                    } catch (Exception unused) {
                    }
                }
                sp_TenKH.setSelection(mContact.indexOf(mTenKH.get(i)));
            }
        });
        this.lv_suatin.setOnItemLongClickListener((adapterView, view, i, j) -> {
            lv_position = i;
            return false;
        });
        this.mContact.clear();
        this.mMobile.clear();
        this.mType_kh.clear();
        this.mApp.clear();
        try {
            Cursor GetData = this.db.GetData("Select * From tbl_kh_new Order by type_kh, ten_kh");
            while (GetData.moveToNext()) {
                this.mContact.add(GetData.getString(0));
                this.mMobile.add(GetData.getString(1));
                this.mApp.add(GetData.getString(2));
                this.mType_kh.add(Integer.valueOf(GetData.getInt(3)));
            }
            if (GetData != null) {
                GetData.close();
            }
            this.sp_TenKH.setAdapter((SpinnerAdapter) new ArrayAdapter(getActivity(), R.layout.spinner_item, this.mContact));
            if (this.mContact.size() > 0) {
                this.sp_TenKH.setSelection(0);
            }
        } catch (Exception unused) {
            Toast.makeText(getActivity(), "Đang copy dữ liệu bản mới!", Toast.LENGTH_LONG).show();
        }
        this.sp_TenKH.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: tamhoang.ldpro4.Fragment.Frag_Suatin.4
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                spin_pointion = i;
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        this.radio_SuaTin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: tamhoang.ldpro4.Fragment.Frag_Suatin.5
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                control_RadioButton();
            }
        });
        this.radio_TaiTin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: tamhoang.ldpro4.Fragment.Frag_Suatin.6
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                control_RadioButton();
            }
        });
        this.btn_LoadTin.setOnClickListener(new View.OnClickListener() { // from class: tamhoang.ldpro4.Fragment.Frag_Suatin.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                AlertDialog.Builder builder;
                DialogInterface.OnClickListener onClickListener;
                if (spin_pointion <= -1 || mContact.isEmpty()) {
                    Toast.makeText(getActivity(), "Chưa có tên khách hàng!", Toast.LENGTH_LONG).show();
                    return;
                }
                if (mApp.get(spin_pointion).contains("sms")) {
                    builder = new AlertDialog.Builder(requireActivity());
                    builder.setTitle("Tải lại tin nhắn khách này?");
                    builder.setPositiveButton("YES", (dialogInterface, i) -> {
                        try {
                            getFullSms(mMobile.get(spin_pointion));
                            db.QueryData("Update chat_database set del_sms = 1 WHERE ten_kh = '" + mContact.get(spin_pointion) + "' AND ngay_nhan = '" + Get_date + "'");
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    });
                    onClickListener = (dialogInterface, i) -> dialogInterface.cancel();
                } else {
                    builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Tải lại tin nhắn khách này?");
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() { // from class: tamhoang.ldpro4.Fragment.Frag_Suatin.7.3
                        @Override // android.content.DialogInterface.OnClickListener
                        public void onClick(DialogInterface dialogInterface, int i) {
                            getAllChat(mType_kh.get(spin_pointion).intValue());
                            db.QueryData("Update chat_database set del_sms = 1 WHERE ten_kh = '" + mContact.get(spin_pointion) + "' AND ngay_nhan = '" + Get_date + "'");
                        }
                    });
                    onClickListener = (dialogInterface, i) -> dialogInterface.cancel();
                }
                builder.setNegativeButton("No", onClickListener);
                builder.create().show();
            }
        });
        this.btn_tai_All.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Tải lại tin nhắn của tất cả khách?");
            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() { // from class: tamhoang.ldpro4.Fragment.Frag_Suatin.8.1
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int i) {
                    try {
                        getFullSms("Full");
                        db.QueryData("Update chat_database set del_sms = 1 WHERE ngay_nhan = '" + Get_date + "'");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            });
            builder.setNegativeButton("No", (dialogInterface, i) -> dialogInterface.cancel());
            builder.create().show();
        });
        if (ContextCompat.checkSelfPermission(getContext(), "android.permission.READ_SMS") != 0) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), "android.permission.READ_SMS")) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{"android.permission.READ_SMS"}, 1);
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{"android.permission.READ_SMS"}, 1);
            }
        }
        control_RadioButton();
        registerForContextMenu(this.lv_suatin);
        return this.v;
    }

    @Override // android.support.v4.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        this.handler.removeCallbacks(this.runnable);
    }

    public void xem_lv() {
        new MainActivity();
        String Get_date = MainActivity.Get_date();
        this.mID.clear();
        this.mNgay.clear();
        this.mSDT.clear();
        this.mTenKH.clear();
        this.mSoTinNhan.clear();
        this.mTinNhanGoc.clear();
        this.mND_DaSua.clear();
        this.mND_PhanTich.clear();
        this.mPhatHienLoi.clear();
        this.mTypeKH.clear();
        Cursor GetData = this.db.GetData("select * from tbl_tinnhanS WHERE phat_hien_loi <> 'ok' AND ngay_nhan = '" + Get_date + "'");
        if (GetData != null && GetData.getCount() > 0) {
            while (GetData.moveToNext()) {
                this.mID.add(Integer.valueOf(GetData.getInt(0)));
                this.mNgay.add(GetData.getString(1));
                this.mTenKH.add(GetData.getString(4));
                this.mSDT.add(GetData.getString(5));
                this.mSoTinNhan.add(Integer.valueOf(Integer.parseInt(GetData.getString(7))));
                this.mTinNhanGoc.add(GetData.getString(8));
                this.mND_DaSua.add(GetData.getString(9));
                this.mND_PhanTich.add(GetData.getString(10));
                this.mPhatHienLoi.add(GetData.getString(11));
                this.mTypeKH.add(Integer.valueOf(GetData.getInt(3)));
            }
            GetData.close();
        }
        if (getActivity() != null) {
            this.lv_suatin.setAdapter(new TNGAdapter(getActivity(), R.layout.spinner_item, this.mTinNhanGoc));
        }
    }
}
