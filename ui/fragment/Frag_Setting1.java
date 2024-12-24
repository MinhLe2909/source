package tamhoang.ldpro4.ui.fragment;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import tamhoang.ldpro4.ui.activity.Activity_AddKH;
import tamhoang.ldpro4.ui.activity.Activity_AddKH2;
import tamhoang.ldpro4.R;
import tamhoang.ldpro4.data.Database;

/* loaded from: classes.dex */
public class Frag_Setting1 extends Fragment {
    Button btn_themKH;
    Database db;
    ListView lview;
    int mPoint;
    TextView tv_Sodt;
    TextView tv_tenKH;
    TextView tv_xoaKH;
    View v;
    public List<String> mAddress = new ArrayList();
    public List<String> mAppuse = new ArrayList();
    public List<String> mDate = new ArrayList();
    public List<String> mPerson = new ArrayList();
    public List<Integer> mtype = new ArrayList();
    int mPoistion = 0;
    int type = 1;

    /* renamed from: tamhoang.ldpro4.Fragment.Frag_Setting1$1 */
    /* loaded from: classes.dex */
    class AnonymousClass1 implements View.OnClickListener {
        AnonymousClass1() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), (Class<?>) Activity_AddKH.class);
            intent.putExtra("tenKH", "");
            intent.putExtra("use_app", "sms");
            intent.putExtra("kh_new", "");
            startActivity(intent);
        }
    }

    /* renamed from: tamhoang.ldpro4.Fragment.Frag_Setting1$2 */
    /* loaded from: classes.dex */
    class AnonymousClass2 implements AdapterView.OnItemClickListener {
        AnonymousClass2() {
        }

        @Override // android.widget.AdapterView.OnItemClickListener
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            mPoistion = position;
            lview.showContextMenuForChild(view);
        }
    }

    /* renamed from: tamhoang.ldpro4.Fragment.Frag_Setting1$3 */
    /* loaded from: classes.dex */
    class AnonymousClass3 implements AdapterView.OnItemLongClickListener {
        AnonymousClass3() {
        }

        @Override // android.widget.AdapterView.OnItemLongClickListener
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
            mPoistion = position;
            return false;
        }
    }

    /* loaded from: classes.dex */
    public class KHAdapter extends ArrayAdapter {

        public KHAdapter(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
        }

        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        public View getView(final int position, View convertView, ViewGroup parent) {
            View inflate = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.frag_setting1_lv, (ViewGroup) null);
            TextView textView =  inflate.findViewById(R.id.st1_tenkh);
            textView.setText(mPerson.get(position));
            ((TextView) inflate.findViewById(R.id.st1_sdt)).setText(mAddress.get(position));
            tv_xoaKH =  inflate.findViewById(R.id.tv_xoaKH);
            tv_xoaKH.setOnClickListener(new View.OnClickListener() { // from class: tamhoang.ldpro4.Fragment.Frag_Setting1.KHAdapter.1
               

                @Override // android.view.View.OnClickListener
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Xoá Khách");
                    builder.setMessage("Xoá bỏ " + mPerson.get(position) + " ra khỏi danh sách?");
                    builder.setNegativeButton("Có", (dialog, which) -> {
                        db.QueryData("Delete FROM tbl_kh_new where ten_kh = '" + mPerson.get(position) + "'");
                        db.QueryData("Delete FROM tbl_tinnhanS where ten_kh = '" + mPerson.get(position) + "'");
                        db.QueryData("Delete FROM tbl_soctS where ten_kh = '" + mPerson.get(position) + "'");
                        db.QueryData("Delete FROM tbl_chuyenthang where kh_nhan = '" + mPerson.get(position) + "'");
                        db.QueryData("Delete FROM tbl_chuyenthang where kh_chuyen = '" + mPerson.get(position) + "'");
                        db.LayDanhsachKH();
                        xem_lv();
                        dialog.dismiss();
                        Toast.makeText(getActivity(), "Xoá thành công!", Toast.LENGTH_LONG).show();
                    });
                    builder.setPositiveButton("Không", (dialog, which) -> dialog.dismiss());
                    builder.show();
                }
            });
            if (mtype.get(position).intValue() != 1) {
                textView.setTextColor(-16776961);
            }
            return inflate;
        }
    }

    @Override // android.support.v4.app.Fragment
    public void onActivityResult(int i, int i2, Intent intent) {
        if (i2 == -1) {
            if (i == 1) {
                try {
                    Uri data = intent.getData();
                    ContentResolver contentResolver = getActivity().getContentResolver();
                    Cursor query = contentResolver.query(data, null, null, null, null);
                    if (query.getCount() > 0) {
                        String str = null;
                        String str2 = null;
                        while (query.moveToNext()) {
                            String string = query.getString(query.getColumnIndex("_id"));
                            String string2 = query.getString(query.getColumnIndex("display_name"));
                            query.getString(query.getColumnIndex("display_name"));
                            if (Integer.parseInt(query.getString(query.getColumnIndex("has_phone_number"))) > 0 && Integer.parseInt(query.getString(query.getColumnIndex("has_phone_number"))) > 0) {
                                Cursor query2 = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, "contact_id = ?", new String[]{string}, null);
                                while (query2.moveToNext()) {
                                    try {
                                        if (query2.getInt(query2.getColumnIndex("data2")) == 2) {
                                            query2.getString(query2.getColumnIndex("data1"));
                                        }
                                    } catch (Throwable unused) {
                                    }
                                }
                                if (query2 != null) {
                                    query2.close();
                                }
                            }
                            str2 = string2;
                        }
                        if (str.length() > 0) {
                            String replaceAll = str.replaceAll(" ", "");
                            if (replaceAll.length() < 12) {
                                replaceAll = "+84" + replaceAll.substring(1);
                            }
                            this.tv_Sodt.setText(replaceAll);
                            this.tv_tenKH.setText(str2);
                        }
                    }
                } catch (Exception unused2) {
                }
            }
            getActivity().setResult(-1, intent);
        }
    }

    @Override // android.support.v4.app.Fragment
    public boolean onContextItemSelected(MenuItem item) {
        super.onContextItemSelected(item);
        if (item.getItemId() == 1) {
            Intent intent = new Intent(getActivity(), (Class<?>) Activity_AddKH.class);
            intent.putExtra("tenKH", this.mPerson.get(this.mPoistion));
            intent.putExtra("kh_new", "");
            intent.putExtra("use_app", this.mAppuse.get(this.mPoistion));
            startActivity(intent);
        } else if (item.getItemId() == 2) {
            Intent intent2 = new Intent(getActivity(), (Class<?>) Activity_AddKH2.class);
            intent2.putExtra("tenKH", this.mPerson.get(this.mPoistion));
            startActivity(intent2);
        }
        return true;
    }

    @Override // android.support.v4.app.Fragment, android.view.View.OnCreateContextMenuListener
    public void onCreateContextMenu(ContextMenu menu, View v2, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v2, menuInfo);
        menu.add(0, 1, 0, "Cài đặt lại giá");
        menu.add(0, 2, 0, "Cài đặt thời gian, giữ %");
    }

    @Override // android.support.v4.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.v = inflater.inflate(R.layout.frag_setting1, container, false);
        this.db = new Database(getActivity());
        this.lview = (ListView) this.v.findViewById(R.id.lv_setting1);
        Button button =  this.v.findViewById(R.id.btn_them_KH);
        this.btn_themKH = button;
        button.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), (Class<?>) Activity_AddKH.class);
            intent.putExtra("tenKH", "");
            intent.putExtra("use_app", "sms");
            intent.putExtra("kh_new", "");
            startActivity(intent);
        });
        this.lview.setOnItemClickListener((adapterView, view, position, id) -> {
            mPoistion = position;
            lview.showContextMenuForChild(view);
        });
        this.lview.setOnItemLongClickListener((adapterView, view, position, l) -> {
            mPoistion = position;
            return false;
        });
        xem_lv();
        registerForContextMenu(this.lview);
        return this.v;
    }

    @Override
    public void onResume() {
        xem_lv();
        super.onResume();
    }

    public void xem_lv() {
        this.mAddress.clear();
        this.mPerson.clear();
        this.mtype.clear();
        this.mAppuse.clear();
        Cursor GetData = this.db.GetData("select * from tbl_kh_new Order by type_kh DESC, ten_kh");
        if (GetData != null) {
            while (GetData.moveToNext()) {
                this.mPerson.add(GetData.getString(0));
                this.mAddress.add(GetData.getString(1));
                this.mtype.add(Integer.valueOf(GetData.getInt(3)));
                this.mAppuse.add(GetData.getString(2));
            }
            if (GetData != null && !GetData.isClosed()) {
                GetData.close();
            }
        }
        if (getActivity() != null) {
            this.lview.setAdapter((ListAdapter) new KHAdapter(getActivity(), R.layout.frag_setting1_lv, this.mPerson));
        }
    }
}
