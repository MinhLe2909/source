package tamhoang.ldpro4.ui.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import tamhoang.ldpro4.ui.activity.Activity_Tinnhan;
import tamhoang.ldpro4.ui.home.MainActivity;
import tamhoang.ldpro4.R;
import tamhoang.ldpro4.data.Database;

/* loaded from: classes.dex */
public class Frag_NoRP3 extends Fragment {
    Database db;
    List<JSONObject> jsonValues;
    ListView lv_no_tinnhan;
    String mDate;
    int sp_Position;
    Spinner sp_khachhang;
    String tenKhach;
    String str = "";
    private List<Integer> mID = new ArrayList();
    private ArrayList<String> mTen = new ArrayList<>();

    /* renamed from: tamhoang.ldpro4.Fragment.Frag_NoRP3$1, reason: invalid class name */
    /* loaded from: classes.dex */
    class AnonymousClass1 implements AdapterView.OnItemClickListener {
        AnonymousClass1() {
        }

        @Override // android.widget.AdapterView.OnItemClickListener
        public void onItemClick(AdapterView<?> adapterView, View view, final int position, long id) {
            String[] strArr = {"Sửa", "Xóa"};
            PopupMenu popupMenu = new PopupMenu(getActivity(), view);
            for (int i = 0; i < 2; i++) {
                popupMenu.getMenu().add(1, i, i, strArr[i]);
            }
            new AlertDialog.Builder(getActivity());
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() { // from class: tamhoang.ldpro4.Fragment.Frag_NoRP3.1.1
                @Override // android.widget.PopupMenu.OnMenuItemClickListener
                public boolean onMenuItemClick(MenuItem item) {
                    int order = item.getOrder();
                    if (order == 0) {
                        Intent intent = new Intent(getActivity(), (Class<?>) Activity_Tinnhan.class);
                        intent.putExtra("m_ID", mID.get(position) + "");
                        startActivity(intent);
                    } else if (order == 1) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("Xoá tin nhắn này?");
                        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() { // from class: tamhoang.ldpro4.Fragment.Frag_NoRP3.1.1.1
                            @Override // android.content.DialogInterface.OnClickListener
                            public void onClick(DialogInterface dialog, int which) {
                                Cursor GetData = db.GetData("Select * From tbl_tinnhanS where ID = " + mID.get(position));
                                GetData.moveToFirst();
                                db.QueryData("DELETE FROM tbl_tinnhanS WHERE ngay_nhan = '" + GetData.getString(1) + "' AND ten_kh = '" + GetData.getString(4) + "' AND so_tin_nhan = " + GetData.getString(7) + " AND type_kh = " + GetData.getString(3));
                                db.QueryData("DELETE FROM tbl_soctS WHERE ngay_nhan = '" + GetData.getString(1) + "' AND ten_kh = '" + GetData.getString(4) + "' AND so_tin_nhan = " + GetData.getString(7) + " AND type_kh = " + GetData.getString(3));
                                GetData.close();
                                Toast.makeText(getActivity(), "Đã xóa tin", Toast.LENGTH_LONG).show();
                                lv_report_sms();
                            }
                        });
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() { // from class: tamhoang.ldpro4.Fragment.Frag_NoRP3.1.1.2
                            @Override // android.content.DialogInterface.OnClickListener
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        builder.create().show();
                    }
                    return true;
                }
            });
            popupMenu.show();
        }
    }

    /* loaded from: classes.dex */
    public class TinNhan_Adapter extends ArrayAdapter {
        public TinNhan_Adapter(Context context, int resource, List<JSONObject> objects) {
            super(context, resource, objects);
        }

        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        public View getView(int position, View mView, ViewGroup parent) {
            View inflate = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.activity_tinnhan_lv, (ViewGroup) null);
            TextView textView =  inflate.findViewById(R.id.tv_no_Tong);
            TextView textView2 =  inflate.findViewById(R.id.tv_ThangThua);
            TextView textView3 =  inflate.findViewById(R.id.tv_ten_KH);
            TextView textView4 =  inflate.findViewById(R.id.tv_TG_nhan);
            TextView textView5 =  inflate.findViewById(R.id.tv_TinNhan);
            TextView textView6 =  inflate.findViewById(R.id.tv_NdGoc);
            TextView textView7 =  inflate.findViewById(R.id.tv_ndpt);
            LinearLayout linearLayout =  inflate.findViewById(R.id.liner_deA);
            LinearLayout linearLayout2 =  inflate.findViewById(R.id.liner_deC);
            LinearLayout linearLayout3 =  inflate.findViewById(R.id.liner_deD);
            LinearLayout linearLayout4 =  inflate.findViewById(R.id.liner_deT);
            LinearLayout linearLayout5 =  inflate.findViewById(R.id.liner_loa);
            LinearLayout linearLayout6 =  inflate.findViewById(R.id.lnxi2);
            LinearLayout linearLayout7 =  inflate.findViewById(R.id.lnxi3);
            LinearLayout linearLayout8 =  inflate.findViewById(R.id.lnxi4);
            LinearLayout linearLayout9 =  inflate.findViewById(R.id.lnxia2);
            LinearLayout linearLayout10 =  inflate.findViewById(R.id.lnxia3);
            LinearLayout linearLayout11 =  inflate.findViewById(R.id.lnxia4);
            LinearLayout linearLayout12 =  inflate.findViewById(R.id.liner_XN);
            LinearLayout linearLayout13 =  inflate.findViewById(R.id.liner_bca);
            TextView textView8 =  inflate.findViewById(R.id.tv_diemDeA);
            TextView textView9 =  inflate.findViewById(R.id.tv_AnDeA);
            TextView textView10 =  inflate.findViewById(R.id.tv_no_DeA);
            TextView textView11 =  inflate.findViewById(R.id.tv_no_ThangThuaDeA);
            TextView textView12 =  inflate.findViewById(R.id.tv_diemDe);
            TextView textView13 =  inflate.findViewById(R.id.tv_AnDe);
            TextView textView14 =  inflate.findViewById(R.id.tv_no_De);
            TextView textView15 =  inflate.findViewById(R.id.tv_no_ThangThuaDe);
            TextView textView16 =  inflate.findViewById(R.id.tv_diemDeT);
            TextView textView17 =  inflate.findViewById(R.id.tv_AnDeT);
            TextView textView18 =  inflate.findViewById(R.id.tv_no_DeT);
            TextView textView19 =  inflate.findViewById(R.id.tv_no_ThangThuaDeT);
            TextView textView20 =  inflate.findViewById(R.id.tv_diemDeC);
            TextView textView21 =  inflate.findViewById(R.id.tv_AnDeC);
            TextView textView22 =  inflate.findViewById(R.id.tv_no_DeC);
            TextView textView23 =  inflate.findViewById(R.id.tv_no_ThangThuaDeC);
            TextView textView24 =  inflate.findViewById(R.id.tv_diemDeD);
            TextView textView25 =  inflate.findViewById(R.id.tv_AnDeD);
            TextView textView26 =  inflate.findViewById(R.id.tv_no_DeD);
            TextView textView27 =  inflate.findViewById(R.id.tv_no_ThangThuaDeD);
            TextView textView28 =  inflate.findViewById(R.id.tv_diemlo);
            TextView textView29 =  inflate.findViewById(R.id.tv_anlo);
            TextView textView30 =  inflate.findViewById(R.id.tv_no_Lo);
            TextView textView31 =  inflate.findViewById(R.id.tv_no_ThangThuaLo);
            TextView textView32 =  inflate.findViewById(R.id.tv_diemloa);
            TextView textView33 =  inflate.findViewById(R.id.tv_anloa);
            TextView textView34 =  inflate.findViewById(R.id.tv_no_Loa);
            TextView textView35 =  inflate.findViewById(R.id.tv_no_ThangThuaLoa);
            TextView textView36 =  inflate.findViewById(R.id.tv_diemxi2);
            TextView textView37 =  inflate.findViewById(R.id.tv_anxi2);
            TextView textView38 =  inflate.findViewById(R.id.tv_no_Xi2);
            TextView textView39 =  inflate.findViewById(R.id.tv_no_ThangThuaXi2);
            TextView textView40 =  inflate.findViewById(R.id.tv_diemxi3);
            TextView textView41 =  inflate.findViewById(R.id.tv_anxi3);
            TextView textView42 =  inflate.findViewById(R.id.tv_no_Xi3);
            TextView textView43 =  inflate.findViewById(R.id.tv_no_ThangThuaXi3);
            TextView textView44 =  inflate.findViewById(R.id.tv_diemxi4);
            TextView textView45 =  inflate.findViewById(R.id.tv_anxi4);
            TextView textView46 =  inflate.findViewById(R.id.tv_no_Xi4);
            TextView textView47 =  inflate.findViewById(R.id.tv_no_ThangThuaXi4);
            TextView textView48 =  inflate.findViewById(R.id.tv_diemxia2);
            TextView textView49 =  inflate.findViewById(R.id.tv_anxia2);
            TextView textView50 =  inflate.findViewById(R.id.tv_no_Xia2);
            TextView textView51 =  inflate.findViewById(R.id.tv_no_ThangThuaXia2);
            TextView textView52 =  inflate.findViewById(R.id.tv_diemxia3);
            TextView textView53 =  inflate.findViewById(R.id.tv_anxia3);
            TextView textView54 =  inflate.findViewById(R.id.tv_no_Xia3);
            TextView textView55 =  inflate.findViewById(R.id.tv_no_ThangThuaXia3);
            TextView textView56 =  inflate.findViewById(R.id.tv_diemxia4);
            TextView textView57 =  inflate.findViewById(R.id.tv_anxia4);
            TextView textView58 =  inflate.findViewById(R.id.tv_no_Xia4);
            TextView textView59 =  inflate.findViewById(R.id.tv_no_ThangThuaXia4);
            TextView textView60 =  inflate.findViewById(R.id.tv_diemxn);
            TextView textView61 =  inflate.findViewById(R.id.tv_anxn);
            TextView textView62 =  inflate.findViewById(R.id.tv_no_Xn);
            TextView textView63 =  inflate.findViewById(R.id.tv_no_ThangThuaXn);
            TextView textView64 =  inflate.findViewById(R.id.tv_diembc);
            TextView textView65 =  inflate.findViewById(R.id.tv_anbc);
            TextView textView66 =  inflate.findViewById(R.id.tv_no_Bc);
            TextView textView67 =  inflate.findViewById(R.id.tv_no_ThangThuaBc);
            TextView textView68 =  inflate.findViewById(R.id.tv_diembca);
            TextView textView69 =  inflate.findViewById(R.id.tv_anbca);
            TextView textView70 =  inflate.findViewById(R.id.tv_no_Bca);
            TextView textView71 =  inflate.findViewById(R.id.tv_no_ThangThuaBca);
            JSONObject jSONObject = jsonValues.get(position);
            try {
                textView6.setText(jSONObject.getString("tin_goc"));
                textView3.setText(jSONObject.getString("ten_KH"));
                try {
                    int i = -1;
                    if (jSONObject.getString("type_kh").indexOf("2") > -1) {
                        textView3.setTextColor(Color.parseColor("#1a40ea"));
                    }
                    textView5.setText(jSONObject.getString("so_tinnhan"));
                    textView4.setText(jSONObject.getString("gio_nhan"));
                    textView.setText(jSONObject.getString("tong_tien"));
                    textView2.setText(jSONObject.getString("ket_qua"));
                    String string = jSONObject.getString("nd_phantich");
                    SpannableString spannableString = new SpannableString(string);
                    int i2 = 0;
                    while (i2 < string.length() - 1) {
                        if (string.substring(i2, i2 + 2).indexOf("*") > i) {
                            for (int i3 = i2; i3 > 0; i3--) {
                                int i4 = i3 + 1;
                                if (string.substring(i3, i4).indexOf(",") <= -1 && string.substring(i3, i4).indexOf(":") <= -1) {
                                    spannableString.setSpan(new ForegroundColorSpan(Color.GREEN), i3, i2 + 1, 33);
                                }
                            }
                        }
                        i2++;
                        i = -1;
                    }
                    textView7.setText(spannableString);
                    if (jSONObject.has("dea")) {
                        try {
                            linearLayout.setVisibility(View.VISIBLE);
                            JSONObject jSONObject2 = new JSONObject(jSONObject.getString("dea"));
                            textView8.setText(jSONObject2.getString("diem"));
                            textView9.setText(jSONObject2.getString("diem_an"));
                            textView10.setText(jSONObject2.getString("tong_tien"));
                            try {
                                textView11.setText(jSONObject2.getString("ket_qua"));
                            } catch (JSONException unused) {
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            return inflate;
                        }
                    }
                    if (jSONObject.has("deb")) {
                        try {
                            JSONObject jSONObject3 = new JSONObject(jSONObject.getString("deb"));
                            try {
                                textView12.setText(jSONObject3.getString("diem"));
                                try {
                                    textView13.setText(jSONObject3.getString("diem_an"));
                                    try {
                                        textView14.setText(jSONObject3.getString("tong_tien"));
                                        try {
                                            textView15.setText(jSONObject3.getString("ket_qua"));
                                        } catch (JSONException unused2) {
                                        }
                                    } catch (JSONException e2) {
                                        e2.printStackTrace();
                                        return inflate;
                                    }
                                } catch (JSONException e3) {
                                    e3.printStackTrace();
                                    return inflate;
                                }
                            } catch (JSONException e4) {
                                e4.printStackTrace();
                                return inflate;
                            }
                        } catch (JSONException e5) {
                            e5.printStackTrace();
                            return inflate;
                        }
                    }
                    if (jSONObject.has("det")) {
                        try {
                            linearLayout4.setVisibility(View.VISIBLE);
                            JSONObject jSONObject4 = new JSONObject(jSONObject.getString("det"));
                            try {
                                textView16.setText(jSONObject4.getString("diem"));
                                try {
                                    textView17.setText(jSONObject4.getString("diem_an"));
                                    try {
                                        textView18.setText(jSONObject4.getString("tong_tien"));
                                        textView19.setText(jSONObject4.getString("ket_qua"));
                                    } catch (JSONException e6) {
                                        e6.printStackTrace();
                                        return inflate;
                                    }
                                } catch (JSONException e7) {
                                    e7.printStackTrace();
                                    return inflate;
                                }
                            } catch (JSONException e8) {
                                e8.printStackTrace();
                                return inflate;
                            }
                        } catch (JSONException e9) {
                            e9.printStackTrace();
                            return inflate;
                        }
                    }
                    if (jSONObject.has("dec")) {
                        try {
                            linearLayout2.setVisibility(View.VISIBLE);
                            JSONObject jSONObject5 = new JSONObject(jSONObject.getString("dec"));
                            try {
                                textView20.setText(jSONObject5.getString("diem"));
                                try {
                                    textView21.setText(jSONObject5.getString("diem_an"));
                                    try {
                                        textView22.setText(jSONObject5.getString("tong_tien"));
                                        try {
                                            textView23.setText(jSONObject5.getString("ket_qua"));
                                        } catch (JSONException unused3) {
                                        }
                                    } catch (JSONException e10) {
                                        e10.printStackTrace();
                                        return inflate;
                                    }
                                } catch (JSONException e11) {
                                    e11.printStackTrace();
                                    return inflate;
                                }
                            } catch (JSONException e12) {
                                e12.printStackTrace();
                                return inflate;
                            }
                        } catch (JSONException e13) {
                            e13.printStackTrace();
                            return inflate;
                        }
                    }
                    if (jSONObject.has("ded")) {
                        try {
                            linearLayout3.setVisibility(View.VISIBLE);
                            JSONObject jSONObject6 = new JSONObject(jSONObject.getString("ded"));
                            try {
                                textView24.setText(jSONObject6.getString("diem"));
                                try {
                                    textView25.setText(jSONObject6.getString("diem_an"));
                                    try {
                                        textView26.setText(jSONObject6.getString("tong_tien"));
                                        textView27.setText(jSONObject6.getString("ket_qua"));
                                    } catch (JSONException e14) {
                                        e14.printStackTrace();
                                        return inflate;
                                    }
                                } catch (JSONException e15) {
                                    e15.printStackTrace();
                                    return inflate;
                                }
                            } catch (JSONException e16) {
                                e16.printStackTrace();
                                return inflate;
                            }
                        } catch (JSONException e17) {
                            e17.printStackTrace();
                            return inflate;
                        }
                    }
                    if (jSONObject.has("lo")) {
                        try {
                            JSONObject jSONObject7 = new JSONObject(jSONObject.getString("lo"));
                            try {
                                textView28.setText(jSONObject7.getString("diem"));
                                try {
                                    textView29.setText(jSONObject7.getString("diem_an"));
                                    try {
                                        textView30.setText(jSONObject7.getString("tong_tien"));
                                        textView31.setText(jSONObject7.getString("ket_qua"));
                                    } catch (JSONException e18) {
                                        e18.printStackTrace();
                                        return inflate;
                                    }
                                } catch (JSONException e19) {
                                    e19.printStackTrace();
                                    return inflate;
                                }
                            } catch (JSONException e20) {
                                e20.printStackTrace();
                                return inflate;
                            }
                        } catch (JSONException e21) {
                            e21.printStackTrace();
                            return inflate;
                        }
                    }
                    if (jSONObject.has("loa")) {
                        try {
                            linearLayout5.setVisibility(View.VISIBLE);
                            JSONObject jSONObject8 = new JSONObject(jSONObject.getString("loa"));
                            try {
                                textView32.setText(jSONObject8.getString("diem"));
                                try {
                                    textView33.setText(jSONObject8.getString("diem_an"));
                                    try {
                                        textView34.setText(jSONObject8.getString("tong_tien"));
                                        try {
                                            textView35.setText(jSONObject8.getString("ket_qua"));
                                        } catch (JSONException unused4) {
                                        }
                                    } catch (JSONException e22) {
                                        e22.printStackTrace();
                                        return inflate;
                                    }
                                } catch (JSONException e23) {
                                    e23.printStackTrace();
                                    return inflate;
                                }
                            } catch (JSONException e24) {
                                e24.printStackTrace();
                                return inflate;
                            }
                        } catch (JSONException e25) {
                            e25.printStackTrace();
                            return inflate;
                        }
                    }
                    if (jSONObject.has("xi2")) {
                        try {
                            linearLayout6.setVisibility(View.VISIBLE);
                            JSONObject jSONObject9 = new JSONObject(jSONObject.getString("xi2"));
                            try {
                                textView36.setText(jSONObject9.getString("diem"));
                                try {
                                    textView37.setText(jSONObject9.getString("diem_an"));
                                    try {
                                        textView38.setText(jSONObject9.getString("tong_tien"));
                                        textView39.setText(jSONObject9.getString("ket_qua"));
                                    } catch (JSONException e26) {
                                        e26.printStackTrace();
                                        return inflate;
                                    }
                                } catch (JSONException e27) {
                                    e27.printStackTrace();
                                    return inflate;
                                }
                            } catch (JSONException e28) {
                                e28.printStackTrace();
                                return inflate;
                            }
                        } catch (JSONException e29) {
                            e29.printStackTrace();
                            return inflate;
                        }
                    }
                    if (jSONObject.has("xi3")) {
                        try {
                            linearLayout7.setVisibility(View.VISIBLE);
                            JSONObject jSONObject10 = new JSONObject(jSONObject.getString("xi3"));
                            try {
                                textView40.setText(jSONObject10.getString("diem"));
                                try {
                                    textView41.setText(jSONObject10.getString("diem_an"));
                                    try {
                                        textView42.setText(jSONObject10.getString("tong_tien"));
                                        try {
                                            textView43.setText(jSONObject10.getString("ket_qua"));
                                        } catch (JSONException unused5) {
                                        }
                                    } catch (JSONException e30) {
                                        e30.printStackTrace();
                                        return inflate;
                                    }
                                } catch (JSONException e31) {
                                    e31.printStackTrace();
                                    return inflate;
                                }
                            } catch (JSONException e32) {
                                e32.printStackTrace();
                                return inflate;
                            }
                        } catch (JSONException e33) {
                            e33.printStackTrace();
                            return inflate;
                        }
                    }
                    if (jSONObject.has("xi4")) {
                        try {
                            linearLayout8.setVisibility(View.VISIBLE);
                            JSONObject jSONObject11 = new JSONObject(jSONObject.getString("xi4"));
                            try {
                                textView44.setText(jSONObject11.getString("diem"));
                                try {
                                    textView45.setText(jSONObject11.getString("diem_an"));
                                    try {
                                        textView46.setText(jSONObject11.getString("tong_tien"));
                                        textView47.setText(jSONObject11.getString("ket_qua"));
                                    } catch (JSONException e34) {
                                        e34.printStackTrace();
                                        return inflate;
                                    }
                                } catch (JSONException e35) {
                                    e35.printStackTrace();
                                    return inflate;
                                }
                            } catch (JSONException e36) {
                                e36.printStackTrace();
                                return inflate;
                            }
                        } catch (JSONException e37) {
                            e37.printStackTrace();
                            return inflate;
                        }
                    }
                    if (jSONObject.has("xia2")) {
                        try {
                            linearLayout9.setVisibility(View.VISIBLE);
                            JSONObject jSONObject12 = new JSONObject(jSONObject.getString("xia2"));
                            try {
                                textView48.setText(jSONObject12.getString("diem"));
                                try {
                                    textView49.setText(jSONObject12.getString("diem_an"));
                                    try {
                                        textView50.setText(jSONObject12.getString("tong_tien"));
                                        try {
                                            textView51.setText(jSONObject12.getString("ket_qua"));
                                        } catch (JSONException unused6) {
                                        }
                                    } catch (JSONException e38) {
                                        e38.printStackTrace();
                                        return inflate;
                                    }
                                } catch (JSONException e39) {
                                    e39.printStackTrace();
                                    return inflate;
                                }
                            } catch (JSONException e40) {
                                e40.printStackTrace();
                                return inflate;
                            }
                        } catch (JSONException e41) {
                            e41.printStackTrace();
                            return inflate;
                        }
                    }
                    if (jSONObject.has("xia3")) {
                        try {
                            linearLayout10.setVisibility(View.VISIBLE);
                            JSONObject jSONObject13 = new JSONObject(jSONObject.getString("xia3"));
                            try {
                                textView52.setText(jSONObject13.getString("diem"));
                                try {
                                    textView53.setText(jSONObject13.getString("diem_an"));
                                    try {
                                        textView54.setText(jSONObject13.getString("tong_tien"));
                                        textView55.setText(jSONObject13.getString("ket_qua"));
                                    } catch (JSONException e42) {
                                        e42.printStackTrace();
                                        return inflate;
                                    }
                                } catch (JSONException e43) {
                                    e43.printStackTrace();
                                    return inflate;
                                }
                            } catch (JSONException e44) {
                                e44.printStackTrace();
                                return inflate;
                            }
                        } catch (JSONException e45) {
                            e45.printStackTrace();
                            return inflate;
                        }
                    }
                    if (jSONObject.has("xia4")) {
                        try {
                            linearLayout11.setVisibility(View.VISIBLE);
                            JSONObject jSONObject14 = new JSONObject(jSONObject.getString("xia4"));
                            try {
                                textView56.setText(jSONObject14.getString("diem"));
                                try {
                                    textView57.setText(jSONObject14.getString("diem_an"));
                                    try {
                                        textView58.setText(jSONObject14.getString("tong_tien"));
                                        try {
                                            textView59.setText(jSONObject14.getString("ket_qua"));
                                        } catch (JSONException unused7) {
                                        }
                                    } catch (JSONException e46) {
                                        e46.printStackTrace();
                                        return inflate;
                                    }
                                } catch (JSONException e47) {
                                    e47.printStackTrace();
                                    return inflate;
                                }
                            } catch (JSONException e48) {
                                e48.printStackTrace();
                                return inflate;
                            }
                        } catch (JSONException e49) {
                            e49.printStackTrace();
                            return inflate;
                        }
                    }
                    if (jSONObject.has("xn")) {
                        try {
                            linearLayout12.setVisibility(View.VISIBLE);
                            JSONObject jSONObject15 = new JSONObject(jSONObject.getString("xn"));
                            try {
                                textView60.setText(jSONObject15.getString("diem"));
                                try {
                                    textView61.setText(jSONObject15.getString("diem_an"));
                                    try {
                                        textView62.setText(jSONObject15.getString("tong_tien"));
                                        textView63.setText(jSONObject15.getString("ket_qua"));
                                    } catch (JSONException e50) {
                                        e50.printStackTrace();
                                        return inflate;
                                    }
                                } catch (JSONException e51) {
                                    e51.printStackTrace();
                                    return inflate;
                                }
                            } catch (JSONException e52) {
                                e52.printStackTrace();
                                return inflate;
                            }
                        } catch (JSONException e53) {
                            e53.printStackTrace();
                            return inflate;
                        }
                    }
                    if (jSONObject.has("bc")) {
                        try {
                            JSONObject jSONObject16 = new JSONObject(jSONObject.getString("bc"));
                            try {
                                textView64.setText(jSONObject16.getString("diem"));
                                try {
                                    textView65.setText(jSONObject16.getString("diem_an"));
                                    try {
                                        textView66.setText(jSONObject16.getString("tong_tien"));
                                        textView67.setText(jSONObject16.getString("ket_qua"));
                                    } catch (JSONException e54) {
                                        e54.printStackTrace();
                                        return inflate;
                                    }
                                } catch (JSONException e55) {
                                    e55.printStackTrace();
                                    return inflate;
                                }
                            } catch (JSONException e56) {
                                e56.printStackTrace();
                                return inflate;
                            }
                        } catch (JSONException e57) {
                            e57.printStackTrace();
                            return inflate;
                        }
                    }
                    if (jSONObject.has("bca")) {
                        linearLayout13.setVisibility(View.VISIBLE);
                        try {
                            JSONObject jSONObject17 = new JSONObject(jSONObject.getString("bca"));
                            try {
                                textView68.setText(jSONObject17.getString("diem"));
                                try {
                                    textView69.setText(jSONObject17.getString("diem_an"));
                                    try {
                                        textView70.setText(jSONObject17.getString("tong_tien"));
                                        try {
                                            textView71.setText(jSONObject17.getString("ket_qua"));
                                        } catch (JSONException unused8) {
                                        }
                                    } catch (JSONException e58) {
                                        e58.printStackTrace();
                                        return inflate;
                                    }
                                } catch (JSONException e59) {
                                    e59.printStackTrace();
                                    return inflate;
                                }
                            } catch (JSONException e60) {
                                e60.printStackTrace();
                                return inflate;
                            }
                        } catch (JSONException e61) {
                            e61.printStackTrace();
                        }
                    }
                    return inflate;
                } catch (JSONException e62) {
                    e62.printStackTrace();
                    return inflate;
                }
            } catch (JSONException e63) {
                e63.printStackTrace();
                return inflate;
            }
        }
    }

    public void lv_report_sms() {
        String Get_date = MainActivity.Get_date();
        Cursor GetData = this.db.GetData("Select * From tbl_tinnhanS Where ngay_nhan = '" + Get_date + "' and ten_kh = '" + this.mTen.get(this.sp_Position) + "' AND phat_hien_loi = 'ok' Order by type_kh, so_tin_nhan");
        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        this.jsonValues = new ArrayList();
        if (GetData != null) {
            try {
                this.mID.clear();
                while (GetData.moveToNext()) {
                    Cursor GetData2 = this.db.GetData("SElect CASE \nWHEN the_loai = 'xi' And length(so_chon) = 5 THEN 'xi2' \nWHEN the_loai = 'xi' And length(so_chon) = 8 THEN 'xi3' \nWHEN the_loai = 'xi' And length(so_chon) = 11 THEN 'xi4' \nWHEN the_loai = 'xia' And length(so_chon) = 5 THEN 'xia2' \nWHEN the_loai = 'xia' And length(so_chon) = 8 THEN 'xia3' \nWHEN the_loai = 'xia' And length(so_chon) = 11 THEN 'xia4' \nELSE the_loai END theloai, sum(diem), sum(diem*so_nhay) as An\n, sum (tong_tien)/1000 as kq \n, sum(Ket_qua)/1000 as tienCuoi\n From tbl_soctS \n Where ten_kh = '" + this.mTen.get(this.sp_Position) + "' and ngay_nhan = '" + Get_date + "' and So_tin_nhan = " + GetData.getString(7) + " AND type_kh = " + GetData.getString(3) + " Group by theloai");
                    if (GetData2 != null) {
                        JSONObject jSONObject = new JSONObject();
                        int i = 0;
                        jSONObject.put("ID", GetData.getInt(0));
                        jSONObject.put("gio_nhan", GetData.getString(2));
                        jSONObject.put("type_kh", GetData.getString(3));
                        jSONObject.put("ten_KH", GetData.getString(4));
                        jSONObject.put("so_tinnhan", GetData.getString(7));
                        jSONObject.put("tin_goc", GetData.getString(8));
                        jSONObject.put("nd_phantich", GetData.getString(10));
                        JSONObject jSONObject2 = new JSONObject();
                        double d = 0.0d;
                        double d2 = 0.0d;
                        while (GetData2.moveToNext()) {
                            try {
                                jSONObject2.put("the_loai", GetData2.getString(i));
                                jSONObject2.put("diem", decimalFormat.format(GetData2.getInt(1)));
                                jSONObject2.put("diem_an", decimalFormat.format(GetData2.getInt(2)));
                                jSONObject2.put("tong_tien", decimalFormat.format(GetData2.getInt(3)));
                                try {
                                    jSONObject2.put("ket_qua", decimalFormat.format(GetData2.getInt(4)));
                                    try {
                                        d += GetData2.getDouble(3);
                                        d2 += GetData2.getDouble(4);
                                        jSONObject.put(GetData2.getString(0), jSONObject2.toString());
                                    } catch (JSONException unused) {
                                    }
                                } catch (JSONException unused2) {
                                    i = 0;
                                }
                            } catch (JSONException unused3) {
                            }
                            i = 0;
                        }
                        try {
                            jSONObject.put("tong_tien", decimalFormat.format(d));
                            jSONObject.put("ket_qua", decimalFormat.format(d2));
                            this.jsonValues.add(jSONObject);
                            this.mID.add(Integer.valueOf(GetData.getInt(0)));
                            GetData2.close();
                        } catch (JSONException unused4) {
                        }
                    }
                }
                GetData.close();
            } catch (JSONException unused5) {
            }
        }
        if (getActivity() != null) {
            this.lv_no_tinnhan.setAdapter((ListAdapter) new TinNhan_Adapter(getActivity(), R.layout.activity_tinnhan_lv, this.jsonValues));
        }
    }

    @Override // android.support.v4.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.frag_norp3, container, false);
        this.db = new Database(getActivity());
        ListView listView = (ListView) inflate.findViewById(R.id.no_rp_tinnhan);
        this.lv_no_tinnhan = listView;
        listView.setOnItemClickListener(new AnonymousClass1());
        this.sp_khachhang = (Spinner) inflate.findViewById(R.id.sp_khachhang);
        this.mTen.add("Lọc theo khách");
        this.sp_khachhang.setAdapter((SpinnerAdapter) new ArrayAdapter(getActivity(), R.layout.spinner_item, this.mTen));
        this.sp_khachhang.setSelection(0);
        this.sp_khachhang.setOnTouchListener(new View.OnTouchListener() { // from class: tamhoang.ldpro4.Fragment.Frag_NoRP3.2
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View v, MotionEvent event) {
                mDate = MainActivity.Get_date();
                mTen.clear();
                Cursor GetData = db.GetData("Select ten_kh From tbl_soctS WHERE ngay_nhan = '" + mDate + "' GROUP by ten_kh Order by ten_kh");
                while (GetData.moveToNext()) {
                    mTen.add(GetData.getString(0));
                }
                GetData.close();
                if (mTen.size() == 0) {
                    mTen.add("Hôm nay chưa có tin nhắn");
                }
                sp_khachhang.setAdapter((SpinnerAdapter) new ArrayAdapter(getActivity(), R.layout.spinner_item, mTen));
                try {
                    sp_khachhang.setSelection(mTen.indexOf(tenKhach));
                } catch (Exception unused) {
                }
                return false;
            }
        });
        this.sp_khachhang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: tamhoang.ldpro4.Fragment.Frag_NoRP3.3
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                sp_Position = position;
                str = " AND tbl_soctS.ten_kh = '" + ((String) mTen.get(sp_Position)) + "'";
                Frag_NoRP3 frag_NoRP3 = Frag_NoRP3.this;
                frag_NoRP3.tenKhach = (String) frag_NoRP3.mTen.get(sp_Position);
                lv_report_sms();
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        registerForContextMenu(this.lv_no_tinnhan);
        return inflate;
    }
}
