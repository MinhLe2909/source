package tamhoang.ldpro4.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import tamhoang.ldpro4.base.BaseToolBarActivity;
import tamhoang.ldpro4.R;
import tamhoang.ldpro4.data.Database;


public class Activity_thaythe extends BaseToolBarActivity {
    Button btn_Luu;
    Database db;
    ListView lv_thaythe;
    private final List<String> mNoidung = new ArrayList();
    private final List<String> mThaythe = new ArrayList();
    TextView tvNDthaythe;
    TextView tvThaythe;


    public class Thaythe_Adapter extends ArrayAdapter {

        public Thaythe_Adapter(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
        }

        @Override
        public View getView(final int position, View v, ViewGroup parent) {
            View inflate = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.activity_thaythe_lv, null);
            ((TextView) inflate.findViewById(R.id.tv_stt)).setText((position + 1) + "");
            ((TextView) inflate.findViewById(R.id.tv_cumtu)).setText(Activity_thaythe.this.mNoidung.get(position));
            ((TextView) inflate.findViewById(R.id.tv_thaybang)).setText(Activity_thaythe.this.mThaythe.get(position));
            inflate.findViewById(R.id.tv_delete).setOnClickListener(v2 -> {
                Activity_thaythe.this.db.QueryData("Delete From thay_the_phu WHERE str = '" + Activity_thaythe.this.mNoidung.get(position) + "'");
                Activity_thaythe.this.listview_thaythe();
            });
            return inflate;
        }
    }

    public void listview_thaythe() {
        this.mNoidung.clear();
        this.mThaythe.clear();
        Cursor GetData = this.db.GetData("Select * FROM thay_the_phu");
        if (GetData != null) {
            while (GetData.moveToNext()) {
                this.mNoidung.add(GetData.getString(1));
                this.mThaythe.add(GetData.getString(2));
            }
        }
        if (!GetData.isClosed()) {
            GetData.close();
        }
        this.lv_thaythe.setAdapter(new Thaythe_Adapter(this, R.layout.activity_thaythe_lv, this.mNoidung));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_thaythe;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thaythe);
        this.tvThaythe = findViewById(R.id.tv_Thaythe);
        this.tvNDthaythe = findViewById(R.id.tv_ndThaythe);
        this.lv_thaythe = findViewById(R.id.lv_thaythe);
        Button button = findViewById(R.id.btn_luu);
        this.btn_Luu = button;
        button.setOnClickListener(v -> {
            Cursor GetData = Activity_thaythe.this.db.GetData("Select count(id) From thay_the_phu WHERE str = '" + Activity_thaythe.this.tvThaythe.getText().toString() + "'");
            GetData.moveToFirst();
            if (GetData.getInt(0) == 0) {
                Activity_thaythe.this.db.QueryData("Insert into thay_the_phu values (null, '" + Activity_thaythe.this.tvThaythe.getText().toString() + "', '" + Activity_thaythe.this.tvNDthaythe.getText().toString() + "')");
            } else {
                Activity_thaythe.this.db.QueryData("Update thay_the_phu set str_rpl = '" + Activity_thaythe.this.tvNDthaythe.getText().toString() + "' WHERE str = '" + Activity_thaythe.this.tvThaythe.getText().toString() + "'");
            }
            Activity_thaythe.this.tvThaythe.setText("");
            Activity_thaythe.this.tvNDthaythe.setText("");
            Activity_thaythe.this.listview_thaythe();
        });
        this.lv_thaythe.setOnItemClickListener((adapterView, view, position, id) -> {
            Activity_thaythe.this.tvThaythe.setText(Activity_thaythe.this.mNoidung.get(position));
            Activity_thaythe.this.tvNDthaythe.setText(Activity_thaythe.this.mThaythe.get(position));
        });
        this.db = new Database(this);
        listview_thaythe();
    }
}
