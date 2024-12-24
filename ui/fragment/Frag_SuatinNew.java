package tamhoang.ldpro4.ui.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import tamhoang.ldpro4.formula.Congthuc;
import tamhoang.ldpro4.ui.home.MainActivity;
import tamhoang.ldpro4.ui.notification.NotificationNewReader;
import tamhoang.ldpro4.R;
import tamhoang.ldpro4.utils.Util;
import tamhoang.ldpro4.data.Database;

public class Frag_SuatinNew extends Frag_Suatin {
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
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
            if (mID != null && mID.size() == 0) {
                lv_position = -1;
            }
            Cursor cursor = null;
            if (editTsuatin.getText().toString().length() < 6) {
                error = false;
            } else {
                if (lv_position >= 0 && Congthuc.CheckDate(MainActivity.myDate)) {
                    db.QueryData("Update tbl_tinnhanS Set nd_phantich = '" + editTsuatin.getText().toString() + "', nd_sua = '" + editTsuatin.getText().toString() + "' WHERE id = " + mID.get(lv_position));
                    StringBuilder sb = new StringBuilder();
                    sb.append("Select type_kh From tbl_tinnhanS WHERE id = ");
                    sb.append(mID.get(lv_position));
                    cursor = db.GetData(sb.toString());
                    cursor.moveToFirst();
                    try {
                        db.Update_TinNhanGoc(mID.get(lv_position).intValue(), cursor.getInt(0));
                    } catch (Exception e) {
                        Util.writeLog(e);
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
                        String replace = GetData.getString(10).replace("LDVN", "<font color='#FF0000'>").replace("LdWolf", "<font color='#FF0000'>");
                        editTsuatin.setText(Html.fromHtml(replace));
                        if (GetData.getString(10).indexOf("LDVN") > -1) {
                            editTsuatin.setSelection(replace.indexOf("<font"));
                        } else {
                            if (GetData.getString(10).indexOf("LdWolf") > -1) {
                                editTsuatin.setSelection(replace.indexOf("<font"));
                            }
                            error = false;
                        }
                        error = false;
                    } else {
                        editTsuatin.setText("");
                        xem_lv();
                        if (mND_DaSua.size() > 0) {
                            lv_position = 0;
                            if (mPhatHienLoi.get(0).indexOf("Không hiểu") > -1) {
                                editTsuatin.setText(Html.fromHtml(mND_PhanTich.get(0).replace("LDVN", "<font color='#FF0000'>").replace("LdWolf", "<font color='#FF0000'>")));
                                int indexOf = mND_PhanTich.get(0).indexOf("LDVN");
                                int indexOf2 = mND_PhanTich.get(0).indexOf("LdWolf");
                                if (indexOf > -1) {
                                    editTsuatin.setSelection(indexOf);
                                } else {
                                    if (indexOf2 > -1) {
                                        editTsuatin.setSelection(indexOf2);
                                    }
                                    sp_TenKH.setSelection(mContact.indexOf(mTenKH.get(0)));
                                    error = false;
                                }
                                sp_TenKH.setSelection(mContact.indexOf(mTenKH.get(0)));
                                error = false;
                            } else {
                                editTsuatin.setText(mND_DaSua.get(0));
                            }
                        } else {
                            lv_position = -1;
                            error = false;
                        }
                    }
                    if (cursor != null && !cursor.isClosed()) {
                        cursor.close();
                    }
                    if (GetData != null && !GetData.isClosed()) {
                        GetData.close();
                    }
                    if (error) {
                        handler.removeCallbacks(xulyTinnhan);
                        return;
                    } else {
                        handler.postDelayed(this, 300L);
                        return;
                    }
                }
                error = false;
                if (!Congthuc.CheckDate(MainActivity.myDate)) {
                    try {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("Thông báo:");
                        builder.setMessage("Đã hết hạn sử dụng phần mềm\n\nHãy liên hệ đại lý hoặc SĐT: " + MainActivity.listKH.getString("k_tra") + " để gia hạn");
                        builder.setNegativeButton("Đóng", new DialogInterface.OnClickListener() { // from class: tamhoang.ldpro4.Fragment.Frag_SuatinNew.2.1
                            @Override // android.content.DialogInterface.OnClickListener
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        builder.create().show();
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        Util.writeLog(e2);
                    }
                } else if (MainActivity.Acc_manager.length() == 0) {
                    AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity());
                    builder2.setTitle("Thông báo:");
                    builder2.setMessage("Kiểm tra kết nối Internet!");
                    builder2.setNegativeButton("Đóng", new DialogInterface.OnClickListener() { // from class: tamhoang.ldpro4.Fragment.Frag_SuatinNew.2.2
                        @Override // android.content.DialogInterface.OnClickListener
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder2.create().show();
                } else {
                    Add_tin();
                }
            }
            GetData = null;
            if (cursor != null) {
                cursor.close();
            }
            if (GetData != null) {
                GetData.close();
            }
            if (error) {
            }
        }
    };

    public class TNGAdapter extends ArrayAdapter {

        class ViewHolder {
            TextView tview5;
            TextView tview7;

            ViewHolder() {
            }
        }

        public TNGAdapter(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
        }

        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        public View getView(int position, View mView, ViewGroup parent) {
            ViewHolder viewHolder;

            if (mView == null) {
                mView = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.frag_suatin_lv, null);
                viewHolder = new ViewHolder();
                viewHolder.tview5 = mView.findViewById(R.id.tv_suatin_nd);
                viewHolder.tview7 = mView.findViewById(R.id.tv_suatin_err);
                mView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) mView.getTag();
            }
            viewHolder.tview5.setText(mTinNhanGoc.get(position));
            viewHolder.tview7.setText(mPhatHienLoi.get(position));
            return mView;
        }
    }

    public void Add_tin() {
        if (this.mContact.isEmpty() || this.editTsuatin.getText().toString().length() <= 6) {
            return;
        }
        final String Get_date = MainActivity.Get_date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getDefault());
        final String format = simpleDateFormat.format(calendar.getTime());
        this.str = "Select * From tbl_tinnhanS WHERE (trim(nd_goc) = '" + this.editTsuatin.getText().toString().replaceAll("'", "").trim() + "' OR trim(nd_sua) = '" + this.editTsuatin.getText().toString().replaceAll("'", "").trim() + "') AND ngay_nhan = '" + Get_date + "' AND so_dienthoai = '" + this.mMobile.get(this.spin_pointion) + "'";
        Cursor GetData = this.db.GetData(this.str);
        GetData.moveToFirst();
        Database database = this.db;
        StringBuilder sb = new StringBuilder();
        sb.append("Select * From tbl_kh_new Where sdt = '");
        sb.append(this.mMobile.get(this.spin_pointion));
        sb.append("'");
        final Cursor GetData2 = database.GetData(sb.toString());
        GetData2.moveToFirst();
        if (this.spin_pointion <= -1 || GetData.getCount() != 0) {
            if (GetData.getCount() > 0) {
                Toast.makeText(getActivity(), "Đã có tin này trong CSDL!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getActivity(), "Hãy chọn tên khách hàng", Toast.LENGTH_LONG).show();
            }
        } else if (GetData2.getInt(3) == 3) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Chọn loại tin nhắn:");
            builder.setMessage("Đây là khách vừa nhận vừa chuyển, thêm tin nhận hay tin chuyển?");
            builder.setNeutralButton("Tin nhận", new DialogInterface.OnClickListener() { // from class: tamhoang.ldpro4.Fragment.Frag_SuatinNew.11
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialog, int which) {
                    type_kh = 1;
                    Cursor GetData3 = db.GetData("Select max(so_tin_nhan) from tbl_tinnhanS WHERE ngay_nhan = '" + Get_date + "' AND so_dienthoai = '" + mMobile.get(spin_pointion) + "' AND type_kh = " + type_kh);
                    GetData3.moveToFirst();
                    db.QueryData("Insert Into tbl_tinnhanS values (null, '" + Get_date + "', '" + format + "', " + type_kh + ", '" + mContact.get(spin_pointion) + "', '" + mMobile.get(spin_pointion) + "', '" + GetData2.getString(2) + "', " + (GetData3.getInt(0) + 1) + ", '" + editTsuatin.getText().toString().replace("'", " ").trim() + "', '" + editTsuatin.getText().toString().replace("'", " ").trim() + "', '" + editTsuatin.getText().toString().replace("'", " ").trim() + "', 'ko',0, 0, 0, null)");
                    editTsuatin.setText("");
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("Select id From tbl_tinnhanS WHERE ngay_nhan = '");
                    sb2.append(Get_date);
                    sb2.append("' AND so_dienthoai = '");
                    sb2.append(mMobile.get(spin_pointion));
                    sb2.append("' AND so_tin_nhan = ");
                    sb2.append(GetData3.getInt(0) + 1);
                    sb2.append(" AND type_kh = ");
                    sb2.append(type_kh);
                    Cursor GetData4 = db.GetData(sb2.toString());
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
                        builder2.setNegativeButton("Đóng", new DialogInterface.OnClickListener() { // from class: tamhoang.ldpro4.Fragment.Frag_SuatinNew.11.1
                            @Override // android.content.DialogInterface.OnClickListener
                            public void onClick(DialogInterface dialog2, int which2) {
                                dialog2.cancel();
                            }
                        });
                        builder2.create().show();
                    } else {
                        try {
                            AlertDialog.Builder builder3 = new AlertDialog.Builder(getActivity());
                            builder3.setTitle("Thông báo:");
                            builder3.setMessage("Đã hết hạn sử dụng phần mềm\n\nHãy liên hệ đại lý hoặc SĐT: " + MainActivity.listKH.getString("k_tra") + " để gia hạn");
                            builder3.setNegativeButton("Đóng", new DialogInterface.OnClickListener() { // from class: tamhoang.ldpro4.Fragment.Frag_SuatinNew.11.2
                                @Override // android.content.DialogInterface.OnClickListener
                                public void onClick(DialogInterface dialog2, int which2) {
                                    dialog2.cancel();
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
                    dialog.cancel();
                    MainActivity.sms = true;
                }
            });
            builder.setPositiveButton("Tin Chuyển", new DialogInterface.OnClickListener() { // from class: tamhoang.ldpro4.Fragment.Frag_SuatinNew.12
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialog, int which) {
                    type_kh = 2;
                    Cursor GetData3 = db.GetData("Select max(so_tin_nhan) from tbl_tinnhanS WHERE ngay_nhan = '" + Get_date + "' AND so_dienthoai = '" + mMobile.get(spin_pointion) + "' AND type_kh = " + type_kh);
                    GetData3.moveToFirst();
                    db.QueryData("Insert Into tbl_tinnhanS values (null, '" + Get_date + "', '" + format + "', " + type_kh + ", '" + mContact.get(spin_pointion) + "', '" + mMobile.get(spin_pointion) + "', '" + GetData2.getString(2) + "', " + (GetData3.getInt(0) + 1) + ", '" + editTsuatin.getText().toString().replaceAll("'", " ").trim() + "', '" + editTsuatin.getText().toString().replaceAll("'", " ").trim() + "', '" + editTsuatin.getText().toString().replaceAll("'", " ").trim() + "', 'ko',0, 0, 0, null)");
                    editTsuatin.setText("");
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("Select id From tbl_tinnhanS WHERE ngay_nhan = '");
                    sb2.append(Get_date);
                    sb2.append("' AND so_dienthoai = '");
                    sb2.append(mMobile.get(spin_pointion));
                    sb2.append("' AND so_tin_nhan = ");
                    sb2.append(GetData3.getInt(0) + 1);
                    sb2.append(" AND type_kh = ");
                    sb2.append(type_kh);
                    Cursor GetData4 = db.GetData(sb2.toString());
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
                        builder2.setNegativeButton("Đóng", new DialogInterface.OnClickListener() { // from class: tamhoang.ldpro4.Fragment.Frag_SuatinNew.12.1
                            @Override // android.content.DialogInterface.OnClickListener
                            public void onClick(DialogInterface dialog2, int which2) {
                                dialog2.cancel();
                            }
                        });
                        builder2.create().show();
                    } else {
                        try {
                            AlertDialog.Builder builder3 = new AlertDialog.Builder(getActivity());
                            builder3.setTitle("Thông báo:");
                            builder3.setMessage("Đã hết hạn sử dụng phần mềm\n\nHãy liên hệ đại lý hoặc SĐT: " + MainActivity.listKH.getString("k_tra") + " để gia hạn");
                            builder3.setNegativeButton("Đóng", new DialogInterface.OnClickListener() { // from class: tamhoang.ldpro4.Fragment.Frag_SuatinNew.12.2
                                @Override // android.content.DialogInterface.OnClickListener
                                public void onClick(DialogInterface dialog2, int which2) {
                                    dialog2.cancel();
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
                    dialog.cancel();
                    MainActivity.sms = true;
                }
            });
            builder.create().show();
        } else {
            this.type_kh = GetData2.getInt(3);
            if (GetData.getCount() > 0) {
                Toast.makeText(getActivity(), "Đã có tin này trong CSDL!", Toast.LENGTH_LONG).show();
                return;
            }
            Cursor GetData3 = this.db.GetData("Select max(so_tin_nhan) from tbl_tinnhanS WHERE ngay_nhan = '" + Get_date + "' AND so_dienthoai = '" + this.mMobile.get(this.spin_pointion) + "' AND type_kh = " + this.type_kh);
            GetData3.moveToFirst();
            this.db.QueryData("Insert Into tbl_tinnhanS values (null, '" + Get_date + "', '" + format + "', " + this.type_kh + ", '" + this.mContact.get(this.spin_pointion) + "', '" + this.mMobile.get(this.spin_pointion) + "', '" + GetData2.getString(2) + "', " + (GetData3.getInt(0) + 1) + ", '" + this.editTsuatin.getText().toString().replaceAll("'", " ").trim() + "', '" + this.editTsuatin.getText().toString().replaceAll("'", " ").trim() + "', '" + this.editTsuatin.getText().toString().replaceAll("'", " ").trim() + "', 'ko',0, 0, 0, null)");
            this.editTsuatin.setText("");
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Select id From tbl_tinnhanS WHERE ngay_nhan = '");
            sb2.append(Get_date);
            sb2.append("' AND so_dienthoai = '");
            sb2.append(this.mMobile.get(this.spin_pointion));
            sb2.append("' AND so_tin_nhan = ");
            sb2.append(GetData3.getInt(0) + 1);
            sb2.append(" AND type_kh = ");
            sb2.append(this.type_kh);
            Cursor GetData4 = this.db.GetData(sb2.toString());
            GetData4.moveToFirst();
            if (Congthuc.CheckDate(MainActivity.myDate)) {
                try {
                    this.db.Update_TinNhanGoc(GetData4.getInt(0), GetData2.getInt(3));
                } catch (Exception unused) {
                    this.db.QueryData("Update tbl_tinnhanS set phat_hien_loi = 'ko' WHERE id = " + GetData4.getInt(0));
                    String str = "Delete From tbl_soctS WHERE ngay_nhan = '" + Get_date + "' AND so_dienthoai = '" + this.mMobile.get(this.spin_pointion) + "' AND so_tin_nhan = " + (GetData3.getInt(0) + 1) + " AND type_kh = " + this.type_kh;
                    this.str = str;
                    this.db.QueryData(str);
                    Toast.makeText(getActivity(), "Đã xảy ra lỗi!", Toast.LENGTH_LONG).show();
                }
            } else if (MainActivity.Acc_manager.length() == 0) {
                AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity());
                builder2.setTitle("Thông báo:");
                builder2.setMessage("Kiểm tra kết nối Internet!");
                builder2.setNegativeButton("Đóng", new DialogInterface.OnClickListener() { // from class: tamhoang.ldpro4.Fragment.Frag_SuatinNew.13
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder2.create().show();
            } else {
                try {
                    AlertDialog.Builder builder3 = new AlertDialog.Builder(getActivity());
                    builder3.setTitle("Thông báo:");
                    builder3.setMessage("Đã hết hạn sử dụng phần mềm\n\nHãy liên hệ đại lý hoặc SĐT: " + MainActivity.listKH.getString("k_tra") + " để gia hạn");
                    builder3.setNegativeButton("Đóng", new DialogInterface.OnClickListener() { // from class: tamhoang.ldpro4.Fragment.Frag_SuatinNew.14
                        @Override // android.content.DialogInterface.OnClickListener
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
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

    public void TralaiSO(int msgId) {
        Cursor cursor;
        Cursor cursor2 = null;
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("HH:mm:ss");
            simpleDateFormat.setTimeZone(TimeZone.getDefault());
            simpleDateFormat2.setTimeZone(TimeZone.getDefault());
            simpleDateFormat.format(calendar.getTime());
            simpleDateFormat2.format(calendar.getTime());
            Cursor GetData = this.db.GetData("Select * from tbl_tinnhanS where id = " + msgId);
            try {
                GetData.moveToFirst();
                Cursor GetData2 = this.db.GetData("Select * From tbl_kh_new Where ten_kh = '" + this.mTenKH.get(0) + "'");
                GetData2.moveToFirst();
                final String str = "Tra lai: \n" + GetData.getString(8);
                final long longValue = Long.valueOf(GetData2.getLong(1)).longValue();
                if (GetData2.getString(2).indexOf("TL") > -1) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: tamhoang.ldpro4.Fragment.Frag_SuatinNew.15
                        @Override // java.lang.Runnable
                        public void run() {
                            MainActivity.sendMessage(longValue, str);
                        }
                    });
                } else if (GetData2.getString(2).indexOf("sms") > -1) {
                    this.db.SendSMS(GetData2.getString(1), str);
                } else {
                    new NotificationNewReader().NotificationWearReader(GetData2.getString(1), str);
                }
                if (GetData != null && !GetData.isClosed()) {
                    GetData.close();
                }
                if (GetData2 == null || GetData2.isClosed()) {
                    return;
                }
                GetData2.close();
            } catch (Exception e) {
                e = e;
                cursor2 = GetData;
                cursor = null;
                try {
                    e.printStackTrace();
                    if (cursor2 != null && !cursor2.isClosed()) {
                        cursor2.close();
                    }
                    if (cursor == null || cursor.isClosed()) {
                        return;
                    }
                    cursor.close();
                } catch (Throwable th) {
                    th = th;
                    if (cursor2 != null && !cursor2.isClosed()) {
                        cursor2.close();
                    }
                    if (cursor != null && !cursor.isClosed()) {
                        cursor.close();
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                cursor2 = GetData;
                cursor = null;
                if (cursor2 != null) {
                    cursor2.close();
                }
                if (cursor != null) {
                    cursor.close();
                }
                th2.printStackTrace();
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            cursor = null;
        } catch (Throwable th3) {
            th3.printStackTrace();
            cursor = null;
        }
    }

    @Override
    public void control_RadioButton() {
        LinearLayout linearLayout = this.v.findViewById(R.id.li_KhachHang);
        LinearLayout linearLayout2 = this.v.findViewById(R.id.li_button);
        LinearLayout linearLayout3 = this.v.findViewById(R.id.edittinnhan);
        if (this.radio_SuaTin.isChecked()) {
            linearLayout3.setVisibility(View.VISIBLE);
            linearLayout.setVisibility(View.VISIBLE);
            linearLayout2.setVisibility(View.VISIBLE);
            this.btn_LoadTin.setVisibility(View.GONE);
            this.btn_suatin.setVisibility(View.VISIBLE);
            this.editTsuatin.setVisibility(View.VISIBLE);
            this.btn_tai_All.setVisibility(View.GONE);
            xem_lv();
            return;
        }
        if (this.radio_TaiTin.isChecked()) {
            linearLayout3.setVisibility(View.VISIBLE);
            linearLayout.setVisibility(View.VISIBLE);
            linearLayout2.setVisibility(View.VISIBLE);
            this.btn_suatin.setVisibility(View.GONE);
            this.btn_LoadTin.setVisibility(View.VISIBLE);
            this.editTsuatin.setVisibility(View.GONE);
            this.btn_tai_All.setVisibility(View.VISIBLE);
            xem_lv();
        }
    }

    @Override
    public void getFullSms(String mName) {
        try {
            super.getFullSms(mName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == 1) {
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
                    this.editTsuatin.setText(Html.fromHtml(this.mND_PhanTich.get(0).replace("LDVN", "<font color='#FF0000'>").replace("LdWolf", "<font color='#FF0000'>")));
                    int indexOf = this.mND_PhanTich.get(0).indexOf("LDVN");
                    int indexOf2 = this.mND_PhanTich.get(0).indexOf("LdWolf");
                    if (indexOf > -1) {
                        this.editTsuatin.setSelection(indexOf);
                    } else {
                        if (indexOf2 > -1) {
                            this.editTsuatin.setSelection(indexOf2);
                        }
                        this.sp_TenKH.setSelection(this.mContact.indexOf(this.mTenKH.get(0)));
                        this.error = false;
                    }
                    this.sp_TenKH.setSelection(this.mContact.indexOf(this.mTenKH.get(0)));
                    this.error = false;
                } else {
                    this.editTsuatin.setText(this.mND_DaSua.get(0));
                }
            } else {
                this.lv_position = -1;
                this.error = false;
            }
        } else if (item.getItemId() == 3) {
            if (this.lv_position >= 0) {
                TralaiSO(this.mID.get(this.lv_position).intValue());
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
                    this.editTsuatin.setText(Html.fromHtml(this.mND_PhanTich.get(0).replace("LDVN", "<font color='#FF0000'>").replace("LdWolf", "<font color='#FF0000'>")));
                    int indexOf3 = this.mND_PhanTich.get(0).indexOf("LDVN");
                    int indexOf4 = this.mND_PhanTich.get(0).indexOf("LdWolf");
                    if (indexOf3 > -1) {
                        this.editTsuatin.setSelection(indexOf3);
                    } else {
                        if (indexOf4 > -1) {
                            this.editTsuatin.setSelection(indexOf4);
                        }
                        this.sp_TenKH.setSelection(this.mContact.indexOf(this.mTenKH.get(0)));
                        this.error = false;
                    }
                    this.sp_TenKH.setSelection(this.mContact.indexOf(this.mTenKH.get(0)));
                    this.error = false;
                } else {
                    this.editTsuatin.setText(this.mND_DaSua.get(0));
                }
            } else {
                this.lv_position = -1;
                this.error = false;
            }
        }
        if (item.getItemId() == 2) {
            if (this.lv_position >= 0) {
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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v2, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(0, 1, 0, "Xóa tin này?");
        menu.add(0, 3, 0, "Trả lại/Xóa tin này?");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.v = inflater.inflate(R.layout.frag_suatin, container, false);
        this.db = new Database(getActivity());
        this.btn_suatin = this.v.findViewById(R.id.btn_suatin_suatin);
        this.btn_LoadTin = this.v.findViewById(R.id.btn_loadtin);
        this.editTsuatin = this.v.findViewById(R.id.editText_suatin);
        this.btn_tai_All = this.v.findViewById(R.id.btn_tai_All);
        this.sp_TenKH = (Spinner) this.v.findViewById(R.id.spr_KH);
        this.radio_SuaTin = (RadioButton) this.v.findViewById(R.id.radio_suaTin);
        this.radio_TaiTin = (RadioButton) this.v.findViewById(R.id.radio_TaiTin);
        this.lv_suatin = (ListView) this.v.findViewById(R.id.lv_suatin);
        this.lv_position = -1;
        this.handler = new Handler();
        this.handler.postDelayed(this.runnable, 1000L);
        final String Get_date = MainActivity.Get_date();
        this.btn_suatin.setOnClickListener(view -> {
            CurDate = MainActivity.Get_date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Cur_date = simpleDateFormat.format(new Date());
            handler = new Handler();
            handler.postDelayed(xulyTinnhan, 300L);
        });
        this.lv_suatin.setOnItemClickListener((adapterView, view, i, l) -> {
            lv_position = i;
            editTsuatin.setText(Html.fromHtml(mND_PhanTich.get(i).replace("LDVN", "<font color='#FF0000'>").replace("LdWolf", "<font color='#FF0000'>")));
            int indexOf = mND_PhanTich.get(i).indexOf("LDVN");
            int indexOf2 = mND_PhanTich.get(i).indexOf("LdWolf");
            if (indexOf == -1) {
                if (indexOf2 > -1) {
                    editTsuatin.setSelection(indexOf2);
                }
                sp_TenKH.setSelection(mContact.indexOf(mTenKH.get(i)));
            } else {
                editTsuatin.setSelection(indexOf);
                sp_TenKH.setSelection(mContact.indexOf(mTenKH.get(i)));
            }
        });
        this.lv_suatin.setOnItemLongClickListener((adapterView, view, position, id) -> {
            lv_position = position;
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
            this.sp_TenKH.setAdapter(new ArrayAdapter(getActivity(), R.layout.spinner_item, this.mContact));
            if (this.mContact.size() > 0) {
                this.sp_TenKH.setSelection(0);
            }
        } catch (Exception unused) {
            Toast.makeText(getActivity(), "Đang copy dữ liệu bản mới!", Toast.LENGTH_LONG).show();
        }
        this.sp_TenKH.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: tamhoang.ldpro4.Fragment.Frag_SuatinNew.6
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                spin_pointion = position;
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        this.radio_SuaTin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: tamhoang.ldpro4.Fragment.Frag_SuatinNew.7
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                control_RadioButton();
            }
        });
        this.radio_TaiTin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: tamhoang.ldpro4.Fragment.Frag_SuatinNew.8
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                control_RadioButton();
            }
        });
        this.btn_LoadTin.setOnClickListener(new View.OnClickListener() { // from class: tamhoang.ldpro4.Fragment.Frag_SuatinNew.9
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (spin_pointion <= -1 || mContact.size() <= 0) {
                    Toast.makeText(getActivity(), "Chưa có tên khách hàng!", Toast.LENGTH_LONG).show();
                    return;
                }
                if (mApp.get(spin_pointion).contains("sms")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Tải lại tin nhắn khách này?");
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() { // from class: tamhoang.ldpro4.Fragment.Frag_SuatinNew.9.1
                        @Override // android.content.DialogInterface.OnClickListener
                        public void onClick(DialogInterface dialog, int which) {
                            getFullSms(mMobile.get(spin_pointion));
                            db.QueryData("Update chat_database set del_sms = 1 WHERE ten_kh = '" + mContact.get(spin_pointion) + "' AND ngay_nhan = '" + Get_date + "'");
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() { // from class: tamhoang.ldpro4.Fragment.Frag_SuatinNew.9.2
                        @Override // android.content.DialogInterface.OnClickListener
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.create().show();
                    return;
                }
                AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity());
                builder2.setTitle("Tải lại tin nhắn khách này?");
                builder2.setPositiveButton("YES", new DialogInterface.OnClickListener() { // from class: tamhoang.ldpro4.Fragment.Frag_SuatinNew.9.3
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialog, int which) {
                        getAllChat(mType_kh.get(spin_pointion).intValue());
                        db.QueryData("Update chat_database set del_sms = 1 WHERE ten_kh = '" + mContact.get(spin_pointion) + "' AND ngay_nhan = '" + Get_date + "'");
                    }
                });
                builder2.setNegativeButton("No", new DialogInterface.OnClickListener() { // from class: tamhoang.ldpro4.Fragment.Frag_SuatinNew.9.4
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder2.create().show();
            }
        });
        this.btn_tai_All.setOnClickListener(new View.OnClickListener() { // from class: tamhoang.ldpro4.Fragment.Frag_SuatinNew.10
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Tải lại tin nhắn của tất cả khách?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() { // from class: tamhoang.ldpro4.Fragment.Frag_SuatinNew.10.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialog, int which) {
                        getFullSms("Full");
                        db.QueryData("Update chat_database set del_sms = 1 WHERE ngay_nhan = '" + Get_date + "'");
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() { // from class: tamhoang.ldpro4.Fragment.Frag_SuatinNew.10.2
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.create().show();
            }
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
    public void onPause() {
        super.onPause();
        if (this.db == null || !this.db.getWritableDatabase().isOpen()) {
            return;
        }
        this.db.close();
    }

    @Override // tamhoang.ldpro4.Fragment.Frag_Suatin
    public void xem_lv() {
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
            this.lv_suatin.setAdapter((ListAdapter) new TNGAdapter(getActivity(), R.layout.frag_suatin_lv, this.mTinNhanGoc));
        }
    }
}
