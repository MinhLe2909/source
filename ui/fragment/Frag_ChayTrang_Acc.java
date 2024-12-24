package tamhoang.ldpro4.ui.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import tamhoang.ldpro4.ui.activity.Activity_AccWeb;
import tamhoang.ldpro4.R;
import tamhoang.ldpro4.data.Database;

public class Frag_ChayTrang_Acc extends Fragment {
    public List<String> Account = new ArrayList();
    Button btn_them_trang;
    Database db;
    ListView lv_account;
    View v;

    public class KHAdapter extends ArrayAdapter {
        public KHAdapter(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View inflate = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.frag_chaytrang_acc_lv, (ViewGroup) null);
            ((TextView) inflate.findViewById(R.id.tv_acc_trang)).setText(Frag_ChayTrang_Acc.this.Account.get(position));
            inflate.findViewById(R.id.tv_edit).setOnClickListener(v -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(Frag_ChayTrang_Acc.this.getActivity());
                builder.setTitle("Sửa thông tin");
                builder.setMessage("Sửa thông tin trang " + Frag_ChayTrang_Acc.this.Account.get(position) + "?");
                builder.setNegativeButton("Có", (dialog, which) -> {
                    Intent intent = new Intent(Frag_ChayTrang_Acc.this.getActivity(), (Class<?>) Activity_AccWeb.class);
                    intent.putExtra("new_web", Frag_ChayTrang_Acc.this.Account.get(position));
                    intent.putExtra("kh_new", "");
                    Frag_ChayTrang_Acc.this.startActivity(intent);
                });
                builder.setPositiveButton("Không", (dialog, which) -> dialog.dismiss());
                builder.show();
            });
            inflate.findViewById(R.id.tv_delete).setOnClickListener(v -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(Frag_ChayTrang_Acc.this.getActivity());
                builder.setTitle("Xoá tài khoản");
                builder.setMessage("Xoá " + Frag_ChayTrang_Acc.this.Account.get(position) + " ra khỏi danh sách?");
                builder.setNegativeButton("Có", (dialog, which) -> {
                    Frag_ChayTrang_Acc.this.db.QueryData("Delete FROM tbl_chaytrang_acc where Username = '" + Frag_ChayTrang_Acc.this.Account.get(position) + "'");
                    Frag_ChayTrang_Acc.this.xem_lv();
                    dialog.dismiss();
                    Toast.makeText(Frag_ChayTrang_Acc.this.getActivity(), "Xoá thành công!", Toast.LENGTH_LONG).show();
                });
                builder.setPositiveButton("Không", (dialog, which) -> dialog.dismiss());
                builder.show();
            });
            return inflate;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.frag_chaytrang_acc, container, false);
        this.v = inflate;
        this.btn_them_trang = inflate.findViewById(R.id.btn_them_trang);
        this.lv_account = this.v.findViewById(R.id.lv_account);
        this.db = new Database(getActivity());
        this.btn_them_trang.setOnClickListener(view -> {
            Intent intent = new Intent(Frag_ChayTrang_Acc.this.getActivity(), Activity_AccWeb.class);
            intent.putExtra("new_web", "");
            Frag_ChayTrang_Acc.this.startActivity(intent);
        });
        xem_lv();
        return this.v;
    }

    @Override
    public void onResume() {
        super.onResume();
        xem_lv();
    }

    public void xem_lv() {
        this.Account.clear();
        Cursor GetData = this.db.GetData("select * from tbl_chaytrang_acc");
        if (GetData != null) {
            while (GetData.moveToNext()) {
                this.Account.add(GetData.getString(0));
            }
            if (GetData != null && !GetData.isClosed()) {
                GetData.close();
            }
        }
        if (getActivity() != null) {
            this.lv_account.setAdapter(new KHAdapter(getActivity(), R.layout.frag_setting1_lv, this.Account));
        }
    }
}
