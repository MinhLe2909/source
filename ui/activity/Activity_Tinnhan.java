package tamhoang.ldpro4.ui.activity;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Html;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import tamhoang.ldpro4.base.BaseToolBarActivity;
import tamhoang.ldpro4.R;
import tamhoang.ldpro4.data.Database;

public class Activity_Tinnhan extends BaseToolBarActivity {
    Button btn_suatin;
    Button btn_xoatin;
    Database db;
    EditText editText_suatin;
    JSONObject json;
    ListView lv_suatin;
    int typeKH;
    String id = "";
    int lv_position = -1;
    String ngay_nhan = "";
    String soTN = "";
    String tenKH = "";
    private final List<String> mDanGoc = new ArrayList();
    private final List<String> mPhantich = new ArrayList();


    class TN_Adapter extends ArrayAdapter {
        public TN_Adapter(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
        }

        @Override
        public View getView(int position, View v, ViewGroup parent) {
            View inflate = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.frag_suatin_lv1, (ViewGroup) null);
            TextView textView = inflate.findViewById(R.id.dan_goc);
            final TextView textView2 = inflate.findViewById(R.id.dan_phantich);
            textView.setText(mDanGoc.get(position));
            textView.setOnClickListener(v2 -> {
                if (textView2.getVisibility() == View.VISIBLE) {
                    textView2.setVisibility(View.GONE);
                } else {
                    textView2.setVisibility(View.VISIBLE);
                }
            });
            textView2.setText(mPhantich.get(position));
            textView2.setVisibility(View.GONE);
            return inflate;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_tinnhan;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        super.onContextItemSelected(item);
        if (item.getItemId() == 1) {
            ((ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("Tin chốt:", this.mDanGoc.get(this.lv_position) + "\n" + this.mPhantich.get(this.lv_position)));
            Toast.makeText(this, "Đã copy thành công", Toast.LENGTH_LONG).show();
        }
        return true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tinnhan);
        this.id = getIntent().getStringExtra("m_ID");
        this.db = new Database(this);
        this.btn_suatin = findViewById(R.id.btn_suatin_suatin);
        this.btn_xoatin = findViewById(R.id.btn_suatin_xoatin);
        this.editText_suatin = findViewById(R.id.editText_suatin);
        this.lv_suatin = findViewById(R.id.lv_suatin);
        Cursor GetData = this.db.GetData("Select * From tbl_tinnhanS WHere id = " + this.id);
        GetData.moveToFirst();
        if (GetData.getString(6).indexOf("ChayTrang") > -1) {
            Toast.makeText(this, "Không sửa được tin chạy vào trang", Toast.LENGTH_LONG).show();
            GetData.close();
            finish();
            return;
        }
        this.ngay_nhan = GetData.getString(1);
        this.tenKH = GetData.getString(4);
        this.soTN = GetData.getString(7);
        this.typeKH = GetData.getInt(3);
        if (GetData.getString(11).indexOf("ok") > -1) {
            try {
                this.mDanGoc.clear();
                this.mPhantich.clear();
                this.json = new JSONObject(GetData.getString(15));
                this.editText_suatin.setText(GetData.getString(9));
                Iterator<String> keys = this.json.keys();
                while (keys.hasNext()) {
                    JSONObject jSONObject = this.json.getJSONObject(keys.next());
                    this.mDanGoc.add(jSONObject.getString("du_lieu") + " (" + jSONObject.getString("so_luong") + ")");
                    this.mPhantich.add(jSONObject.getString("dan_so") + "x" + jSONObject.getString("so_tien"));
                }
                this.lv_suatin.setAdapter(new TN_Adapter(this, R.layout.frag_suatin_lv1, this.mDanGoc));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            this.editText_suatin.setText(GetData.getString(9));
        }
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        View currentFocus = getCurrentFocus();
        if (currentFocus == null) {
            currentFocus = new View(this);
        }
        inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
        this.btn_suatin.setOnClickListener(v -> {
            db.QueryData("DELETE FROM tbl_soctS WHERE ngay_nhan = '" + ngay_nhan + "' AND ten_kh = '" + tenKH + "'  AND so_tin_nhan = " + soTN + " And type_kh = " + typeKH);
            StringBuilder sb = new StringBuilder();
            sb.append("Update tbl_tinnhanS Set nd_phantich = '");
            sb.append(editText_suatin.getText().toString());
            sb.append("', phat_hien_loi = 'ko' WHERE id = ");
            sb.append(id);
            db.QueryData(sb.toString());
            try {
                db.Update_TinNhanGoc(Integer.parseInt(id), typeKH);
            } catch (Exception unused) {
                Toast.makeText(Activity_Tinnhan.this, "Đã xảy ra lỗi!", Toast.LENGTH_LONG).show();
            }
            Cursor GetData2 = db.GetData("Select * FROM tbl_tinnhanS Where id = " + id);
            GetData2.moveToFirst();
            if (GetData2.getString(11).indexOf("Không hiểu") > -1) {
                editText_suatin.setText(Html.fromHtml(GetData2.getString(10).replace("LDVN", "<font color='#FF0000'>").replace("LdWolf", "<font color='#FF0000'>")));
                if (GetData2.getString(10).indexOf("LDVN") > -1) {
                    editText_suatin.setSelection(GetData2.getString(10).indexOf("LDVN"));
                } else if (GetData2.getString(10).indexOf("LdWolf") > -1) {
                    editText_suatin.setSelection(GetData2.getString(10).indexOf("LdWolf"));
                }
                mDanGoc.clear();
                mPhantich.clear();
                ListView listView = lv_suatin;
                Activity_Tinnhan activity_Tinnhan = Activity_Tinnhan.this;
                listView.setAdapter(new TN_Adapter(activity_Tinnhan, R.layout.frag_suatin_lv1, activity_Tinnhan.mDanGoc));
                return;
            }
            editText_suatin.setText(GetData2.getString(9));
            mDanGoc.clear();
            mPhantich.clear();
            try {
                json = new JSONObject(GetData2.getString(15));
                Iterator<String> keys2 = json.keys();
                while (keys2.hasNext()) {
                    JSONObject jSONObject2 = json.getJSONObject(keys2.next());
                    mDanGoc.add(jSONObject2.getString("du_lieu") + " (" + jSONObject2.getString("so_luong") + ")");
                    mPhantich.add(jSONObject2.getString("dan_so") + "x" + jSONObject2.getString("so_tien"));
                }
                lv_suatin.setAdapter(new TN_Adapter(this, R.layout.frag_suatin_lv1, mDanGoc));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        });
        this.lv_suatin.setOnItemLongClickListener((adapterView, view, position, id) -> {
            lv_position = position;
            return false;
        });
        this.btn_xoatin.setOnClickListener(v -> finish());
        registerForContextMenu(this.lv_suatin);
        GetData.close();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, 1, 0, "Copy ?");
    }
}
