package tamhoang.ldpro4.ui.home;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import tamhoang.ldpro4.R;
import tamhoang.ldpro4.Telegram.TelegramClient;
import tamhoang.ldpro4.ui.notification.NotificationNewReader;
import tamhoang.ldpro4.ui.notification.NotificationReader;
import tamhoang.ldpro4.utils.Util;
import tamhoang.ldpro4.utils.ZBroadcast;
import tamhoang.ldpro4.ui.activity.Activity_Active;
import tamhoang.ldpro4.ui.activity.Activity_ChuyenThang;
import tamhoang.ldpro4.ui.activity.Activity_GiuSo;
import tamhoang.ldpro4.ui.activity.Activity_thaythe;
import tamhoang.ldpro4.formula.Congthuc;
import tamhoang.ldpro4.ui.fragment.Frag_CanChuyenNew;
import tamhoang.ldpro4.ui.fragment.Frag_Chat_Manager;
import tamhoang.ldpro4.ui.fragment.Frag_Database;
import tamhoang.ldpro4.ui.fragment.Frag_Home;
import tamhoang.ldpro4.ui.fragment.Frag_MoRP1;
import tamhoang.ldpro4.ui.fragment.Frag_No_new;
import tamhoang.ldpro4.ui.fragment.Frag_No_old;
import tamhoang.ldpro4.ui.fragment.Frag_SMS_Templates;
import tamhoang.ldpro4.ui.fragment.Frag_Setting1;
import tamhoang.ldpro4.ui.fragment.Frag_Setting3;
import tamhoang.ldpro4.ui.fragment.Livestream;
import tamhoang.ldpro4.ui.fragment.Tab_ChayTrang;
import tamhoang.ldpro4.ui.fragment.Tab_ChayTrang_Win;
import tamhoang.ldpro4.ui.fragment.Tab_Tinnhan;
import tamhoang.ldpro4.ui.fragment.TructiepXoso;
import tamhoang.ldpro4.akaman.AkaManSec;
import tamhoang.ldpro4.data.models.Contact;
import tamhoang.ldpro4.data.Database;
import tamhoang.ldpro4.notifLib.models.Action;

import net.lingala.zip4j.util.InternalZipConstants;

import org.drinkless.tdlib.Client;
import org.drinkless.tdlib.TdApi;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;


public class MainActivity extends AppCompatActivity implements TelegramClient.Callback {
    public static String Acc_manager = "";
    public static final String BO = "Bỏ";
    public static final String HET_GIO_NHAN = "Hết giờ nhận!";
    public static Context Main_activity = null;
    public static final String OK = "Ok";
    public static final String THIEU = "Thiếu";
    public static final int TIPO_DIALOGO = 0;
    public static final String TRA_LAI = "Tra lai";
    public static Client client;
    public static Context context;
    public static Handler handler;
    public static JSONObject jSon_Setting;
    public static List<Fragment> listFragments;
    public static JSONObject listKH;
    public static int mDay;
    public static int mMonth;
    public static int mYear;
    private static DatePickerDialog.OnDateSetListener onDateSetListener;
    public static JSONObject v3_6;
    TextView Text_Menu;
    TextView Text_date;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Database db;
    DrawerLayout drawerLayout;
    RelativeLayout drawerPane;
    String insertData;
    List<tamhoang.ldpro4.ui.home.NavItem> listNavItems;
    ListView lvNav;
    RelativeLayout notification;
    TextView textErrItemCount;
    String viewData;
    public static ArrayList<String> DSkhachhang = new ArrayList<>();
    public static JSONObject Json_Chat_Telegram = new JSONObject();
    public static JSONObject Json_Tinnhan = new JSONObject();
    public static String MyToken = "";
    public static String WinBalance = "";
    public static NotificationReader Notifi = null;
    public static ArrayList<String> arr_TenKH = new ArrayList<>();
    public static ArrayList<Contact> contactslist = new ArrayList<>();
    public static ArrayList<Action> actionslist = new ArrayList<>();
    public static ArrayList<HashMap<String, String>> formArray = new ArrayList<>();
    public static ArrayList<HashMap<String, String>> formList = new ArrayList<>();
    public static JSONObject json_Tinnhan = new JSONObject();
    public static String myDate = "";
    public static boolean sms = false;
    static int TIME_REMOVE = 0;
    static Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                Iterator<String> keys = MainActivity.json_Tinnhan.keys();
                while (true) {
                    if (!keys.hasNext()) {
                        break;
                    }
                    String next = keys.next();
                    JSONObject jSONObject = new JSONObject(MainActivity.json_Tinnhan.getString(next));
                    jSONObject.put("Time", jSONObject.getInt("Time") + 1);
                    MainActivity.json_Tinnhan.put(next, jSONObject.toString());
                    if (jSONObject.getInt("Time") > 3 && jSONObject.length() > 1) {
                        Iterator<String> keys2 = jSONObject.keys();
                        while (keys2.hasNext()) {
                            String next2 = keys2.next();
                            if (!next2.contains("Time")) {
                                NotificationNewReader notificationNewReader = new NotificationNewReader();
                                if (Build.VERSION.SDK_INT >= 20) {
                                    try {
                                        notificationNewReader.NotificationWearReader(next, next2);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                        JSONObject jSONObject2 = new JSONObject();
                        jSONObject2.put("Time", 0);
                        MainActivity.json_Tinnhan.put(next, jSONObject2.toString());
                    } else if (jSONObject.getInt("Time") > 100) {
                        break;
                    }
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            if (MainActivity.json_Tinnhan.length() == 0) {
                MainActivity.TIME_REMOVE++;
            } else {
                MainActivity.TIME_REMOVE = 0;
            }
            if (MainActivity.TIME_REMOVE < 100) {
                MainActivity.handler.postDelayed(this, 1000L);
            } else {
                MainActivity.handler.removeCallbacks(MainActivity.runnable);
                MainActivity.handler = null;
            }
        }
    };
    static String my_id = "";
    public static int mErrItemCount = 0;
    SimpleDateFormat dmyFormat = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
    boolean notifivationNavigated = false;
    int currentMenuPosition = -1;


    public class MyParam {
        String chatId;
        MainActivity context;
        String senderUserId;
        String ten_kh;
        String text;
        boolean tinHethong;
        int type_kh;

        public MyParam(boolean tinHethong, String ten_kh, String chatId, String senderUserId, int type_kh, String text, MainActivity context) {
            this.tinHethong = tinHethong;
            this.ten_kh = ten_kh;
            this.chatId = chatId;
            this.senderUserId = senderUserId;
            this.type_kh = type_kh;
            this.text = text;
            this.context = context;
        }
    }

    private class ProcessMessageAsyncTask extends AsyncTask<MyParam, Void, Void> {
        private ProcessMessageAsyncTask() {
        }

        @Override // android.os.AsyncTask
        public Void doInBackground(MyParam... myParams) {
            process(myParams[0].tinHethong, myParams[0].ten_kh, myParams[0].chatId, myParams[0].senderUserId, myParams[0].type_kh, myParams[0].text, myParams[0].context);
            return null;
        }

        @Override
        public void onPostExecute(Void result) {
            super.onPostExecute(result);
        }
    }

    public static String Get_date() {
        int i = mDay;
        if (i < 10 && mMonth + 1 < 10) {
            return mYear + "-0" + (mMonth + 1) + "-0" + mDay;
        }
        if (i < 10) {
            return mYear + "-" + (mMonth + 1) + "-0" + mDay;
        }
        if (mMonth + 1 < 10) {
            return mYear + "-0" + (mMonth + 1) + "-" + mDay;
        }
        return mYear + "-" + (mMonth + 1) + "-" + mDay;
    }

    public static String Get_link() {
        return (!AkaManSec.akaEnable || AkaManSec.akaMainURL == null || "".equals(AkaManSec.akaMainURL)) ? "https://ldvn.club/" : AkaManSec.akaMainURL;
//    return tamhoang.ldpro4.BuildConfig.HOST;
    }

    public static String Get_ngay() {
        int i = mDay;
        if (i < 10 && mMonth + 1 < 10) {
            return "0" + mDay + "/0" + (mMonth + 1) + InternalZipConstants.ZIP_FILE_SEPARATOR + mYear;
        }
        if (i < 10) {
            return "0" + mDay + InternalZipConstants.ZIP_FILE_SEPARATOR + (mMonth + 1) + InternalZipConstants.ZIP_FILE_SEPARATOR + mYear;
        }
        if (mMonth + 1 < 10) {
            return mDay + "/0" + (mMonth + 1) + InternalZipConstants.ZIP_FILE_SEPARATOR + mYear;
        }
        return mDay + InternalZipConstants.ZIP_FILE_SEPARATOR + (mMonth + 1) + InternalZipConstants.ZIP_FILE_SEPARATOR + mYear;
    }

    public static String Get_ngay_new() {
        int i = mDay;
        if (i < 10 && mMonth + 1 < 10) {
            return "0" + mDay + "/0" + (mMonth + 1) + InternalZipConstants.ZIP_FILE_SEPARATOR + mYear;
        }
        if (i < 10) {
            return "0" + mDay + InternalZipConstants.ZIP_FILE_SEPARATOR + (mMonth + 1) + InternalZipConstants.ZIP_FILE_SEPARATOR + mYear;
        }
        if (mMonth + 1 < 10) {
            return mDay + "/0" + (mMonth + 1) + InternalZipConstants.ZIP_FILE_SEPARATOR + mYear;
        }
        return mDay + InternalZipConstants.ZIP_FILE_SEPARATOR + (mMonth + 1) + InternalZipConstants.ZIP_FILE_SEPARATOR + mYear;
    }

    public static String LayDuLieuJson3() {
        byte[] bArr = new byte[39];
        for (int i = 0; i < 78; i += 2) {
            bArr[i / 2] = (byte) ((Character.digit("687474703a2f2f676164676574746573742e776562736974652f6a736f6e5f776f72642e706870".charAt(i), 16) << 4) + Character.digit("687474703a2f2f676164676574746573742e776562736974652f6a736f6e5f776f72642e706870".charAt(i + 1), 16));
        }
        return new String(bArr);
    }

    private void Xulytin(String chatId, String text, String format, String format2, int i) {
        String str5 = "";
        String str6 = "";
        String str7 = "";
        String string = "";
        String str8 = "";
        String str9 = "";
        String str10 = "";
        String str11 = "";
        String str12 = "";
        Cursor GetData;
        String r2 = chatId;
        Util.writeLogInfo("Xulytin step 1");
        int size = DSkhachhang.size();
        int indexOf = DSkhachhang.indexOf(chatId);
        int indexOf2 = text.indexOf(OK);
        int indexOf3 = text.indexOf(BO);
        int indexOf4 = text.indexOf(THIEU);
        int indexOf5 = text.indexOf(TRA_LAI);
        Util.writeLogInfo("=============================");
        StringBuilder sb2 = new StringBuilder();
        String str13 = "' AND so_tin_nhan = ";
        sb2.append("Xulytin mSDT: ");
        sb2.append(r2);
        Util.writeLogInfo(sb2.toString());
        Util.writeLogInfo("Xulytin body: " + text);
        Util.writeLogInfo("Xulytin DSkhachhang size: " + size);
        Util.writeLogInfo("Xulytin vị trí sdt: " + indexOf);
        Util.writeLogInfo("Xulytin vị trí \"OK\": " + indexOf2);
        Util.writeLogInfo("Xulytin vị trí \"Bỏ\": " + indexOf3);
        Util.writeLogInfo("Xulytin vị trí \"Thiếu\": " + indexOf4);
        Util.writeLogInfo("Xulytin vị trí \"Tra lai\": " + indexOf5);
        Util.writeLogInfo("=============================");
        if ((indexOf <= -1 || indexOf2 == 0 || indexOf3 == 0 || indexOf4 == 0) && indexOf5 <= -1) {
            return;
        }
        Util.writeLogInfo("Xulytin step 2");
        JSONObject jSONObject = null;
        Cursor GetData2 = this.db.GetData("Select * FROM tbl_kh_new WHERE sdt ='" + r2 + "'");
        Util.writeLogInfo("Xulytin step 3");
        GetData2.moveToFirst();
        try {
            Util.writeLogInfo("Xulytin step 4");
            jSONObject = new JSONObject(GetData2.getString(5)).getJSONObject("caidat_tg");
        } catch (Exception e) {
            e.printStackTrace();
            Util.writeLog(e);
        }
        try {
            Util.writeLogInfo("Xulytin step 5");
            String str14 = "',null,'";
            String str15 = ", '";
            if (Congthuc.CheckTime(jSONObject.getString("tg_debc"))) {
                str5 = "' AND type_kh = 1";
                str6 = "Select max(so_tin_nhan) from tbl_tinnhanS WHERE ngay_nhan = '";
                str13 = "','TL', ";
                str12 = r2;
            } else {
                try {
                    string = GetData2.getString(0);
                } catch (SQLException e2) {
                    str5 = "' AND type_kh = 1";
                }
                if (this.db.GetData("Select * From tbl_tinnhanS WHERE ngay_nhan = '" + format + "' And Ten_kh = '" + string + "' AND (trim(nd_goc) = '" + text.trim() + "' OR trim(nd_sua)= '" + text.trim() + "')").getCount() > 0) {
                    if (GetData2 == null || GetData2.isClosed()) {
                        return;
                    }
                    Util.writeLogInfo("Xulytin check trùng");
                    GetData2.close();
                    return;
                }
                Util.writeLogInfo("Xulytin step 6");
                Cursor GetData3 = this.db.GetData("Select max(so_tin_nhan) from tbl_tinnhanS WHERE ngay_nhan = '" + format + "' AND so_dienthoai = '" + r2 + "' AND type_kh = 1");
                GetData3.moveToFirst();
                str5 = "' AND type_kh = 1";
                int i2 = GetData3.getInt(0) + 1;
                try {
                    str6 = "Select max(so_tin_nhan) from tbl_tinnhanS WHERE ngay_nhan = '";
                    if (text.indexOf(TRA_LAI) == -1) {
                        try {
                            Util.writeLogInfo("Xulytin step 7");
                            StringBuilder sb3 = new StringBuilder();
                            sb3.append("Insert Into tbl_tinnhanS values (null, '");
                            sb3.append(format);
                            sb3.append("', '");
                            sb3.append(format2);
                            sb3.append("',");
                            sb3.append(i);
                            str8 = str15;
                            try {
                                sb3.append(str8);
                                sb3.append(string);
                                sb3.append("', '");
                                sb3.append(GetData2.getString(1));
                            } catch (SQLException e3) {
                                str15 = str8;
                                str13 = "','TL', ";
                                Util.writeLog(e3);
                                str7 = r2;
                                Util.writeLogInfo("Xulytin step 19");
                                str12 = str7;
                                if (GetData2 != null) {
                                }
                                Util.writeLogInfo("Xulytin step 21");
                                GetData = this.db.GetData(str6 + format + "' AND so_dienthoai = '" + str12 + str5);
                                GetData.moveToFirst();
                                Util.writeLogInfo("Xulytin step 22");
                                this.db.QueryData("Insert Into tbl_tinnhanS values (null, '" + format + "', '" + format2 + "',1, '" + GetData2.getString(0) + "', '" + GetData2.getString(1) + str13 + (GetData.getInt(0) + 1) + str15 + text + str14 + text + "', 'Hết giờ nhận số!',0,1,1, null)");
                                this.db.close();
                                Util.writeLogInfo("Xulytin step 23");
                                if (GetData != null) {
                                }
                                Util.writeLogInfo("Xulytin step 25");
                                if (!Congthuc.CheckTime("18:30")) {
                                }
                                Util.writeLogInfo("Xulytin step 27 -- Kết thúc xử lý tin");
                            }
                            try {
                                sb3.append("','TL', ");
                                sb3.append(i2);
                                sb3.append(str8);
                                sb3.append(text);
                                str9 = TRA_LAI;
                                str10 = str14;
                            } catch (SQLException e4) {
                                str13 = "','TL', ";
                                str15 = str8;
                                Util.writeLog(e4);
                                str7 = r2;
                                Util.writeLogInfo("Xulytin step 19");
                                str12 = str7;
                                if (GetData2 != null) {
                                }
                                Util.writeLogInfo("Xulytin step 21");
                                GetData = this.db.GetData(str6 + format + "' AND so_dienthoai = '" + str12 + str5);
                                GetData.moveToFirst();
                                Util.writeLogInfo("Xulytin step 22");
                                this.db.QueryData("Insert Into tbl_tinnhanS values (null, '" + format + "', '" + format2 + "',1, '" + GetData2.getString(0) + "', '" + GetData2.getString(1) + str13 + (GetData.getInt(0) + 1) + str15 + text + str14 + text + "', 'Hết giờ nhận số!',0,1,1, null)");
                                this.db.close();
                                Util.writeLogInfo("Xulytin step 23");
                                if (GetData != null) {
                                }
                                Util.writeLogInfo("Xulytin step 25");
                                if (!Congthuc.CheckTime("18:30")) {
                                }
                                Util.writeLogInfo("Xulytin step 27 -- Kết thúc xử lý tin");
                            }
                            try {
                                sb3.append(str10);
                                sb3.append(text);
                                sb3.append("', 'ko',0,1,1, null)");
                                str11 = "','TL', ";
                            } catch (SQLException e5) {
                                str13 = "','TL', ";
                                str14 = str10;
                                str15 = str8;
                                Util.writeLog(e5);
                                str7 = r2;
                                Util.writeLogInfo("Xulytin step 19");
                                str12 = str7;
                                if (GetData2 != null) {
                                }
                                Util.writeLogInfo("Xulytin step 21");
                                GetData = this.db.GetData(str6 + format + "' AND so_dienthoai = '" + str12 + str5);
                                GetData.moveToFirst();
                                Util.writeLogInfo("Xulytin step 22");
                                this.db.QueryData("Insert Into tbl_tinnhanS values (null, '" + format + "', '" + format2 + "',1, '" + GetData2.getString(0) + "', '" + GetData2.getString(1) + str13 + (GetData.getInt(0) + 1) + str15 + text + str14 + text + "', 'Hết giờ nhận số!',0,1,1, null)");
                                this.db.close();
                                Util.writeLogInfo("Xulytin step 23");
                                if (GetData != null) {
                                }
                                Util.writeLogInfo("Xulytin step 25");
                                if (!Congthuc.CheckTime("18:30")) {
                                }
                                Util.writeLogInfo("Xulytin step 27 -- Kết thúc xử lý tin");
                            }
                        } catch (SQLException e6) {
                        }
                    } else {
                        str11 = "','TL', ";
                        str8 = str15;
                        str9 = TRA_LAI;
                        str10 = str14;
                        try {
                            str14 = "Xulytin step 8";
                            Util.writeLogInfo("Xulytin step 8");
                        } catch (SQLException e7) {
                            str13 = str11;
                            str14 = str10;
                            str15 = str8;
                            Util.writeLog(e7);
                            str7 = r2;
                            Util.writeLogInfo("Xulytin step 19");
                            str12 = str7;
                            if (GetData2 != null) {
                            }
                            Util.writeLogInfo("Xulytin step 21");
                            GetData = this.db.GetData(str6 + format + "' AND so_dienthoai = '" + str12 + str5);
                            GetData.moveToFirst();
                            Util.writeLogInfo("Xulytin step 22");
                            this.db.QueryData("Insert Into tbl_tinnhanS values (null, '" + format + "', '" + format2 + "',1, '" + GetData2.getString(0) + "', '" + GetData2.getString(1) + str13 + (GetData.getInt(0) + 1) + str15 + text + str14 + text + "', 'Hết giờ nhận số!',0,1,1, null)");
                            this.db.close();
                            Util.writeLogInfo("Xulytin step 23");
                            if (GetData != null) {
                            }
                            Util.writeLogInfo("Xulytin step 25");
                            if (!Congthuc.CheckTime("18:30")) {
                            }
                            Util.writeLogInfo("Xulytin step 27 -- Kết thúc xử lý tin");
                        }
                        try {
                            this.db.QueryData("Insert Into tbl_tinnhanS values (null, '" + format + "', '" + format2 + "'," + i + str8 + string + "', '" + GetData2.getString(1) + str11 + i2 + str8 + text + str10 + text + "', 'ko',0,0,0, null)");
                        } catch (SQLException e8) {
                            r2 = chatId;
                            str13 = str11;
                            str14 = str10;
                            str15 = str8;
                            Util.writeLog(e8);
                            str7 = r2;
                            Util.writeLogInfo("Xulytin step 19");
                            str12 = str7;
                            if (GetData2 != null) {
                            }
                            Util.writeLogInfo("Xulytin step 21");
                            GetData = this.db.GetData(str6 + format + "' AND so_dienthoai = '" + str12 + str5);
                            GetData.moveToFirst();
                            Util.writeLogInfo("Xulytin step 22");
                            this.db.QueryData("Insert Into tbl_tinnhanS values (null, '" + format + "', '" + format2 + "',1, '" + GetData2.getString(0) + "', '" + GetData2.getString(1) + str13 + (GetData.getInt(0) + 1) + str15 + text + str14 + text + "', 'Hết giờ nhận số!',0,1,1, null)");
                            this.db.close();
                            Util.writeLogInfo("Xulytin step 23");
                            if (GetData != null) {
                            }
                            Util.writeLogInfo("Xulytin step 25");
                            if (!Congthuc.CheckTime("18:30")) {
                            }
                            Util.writeLogInfo("Xulytin step 27 -- Kết thúc xử lý tin");
                        }
                    }
                    this.db.close();
                    Util.writeLogInfo("Xulytin step 9");
                    try {
                        if (Congthuc.CheckDate(myDate)) {
                            Util.writeLogInfo("Xulytin step 10");
                            StringBuilder sb4 = new StringBuilder();
                            sb4.append("Select * from tbl_tinnhanS WHERE ngay_nhan = '");
                            sb4.append(format);
                            sb4.append("' AND so_dienthoai = '");
                            r2 = chatId;
                            sb4.append(r2);
                            sb4.append(str13);
                            sb4.append(i2);
                            str14 = str10;
                            try {
                                sb4.append(" AND type_kh = ");
                                sb4.append(i);
                                Cursor GetData4 = this.db.GetData(sb4.toString());
                                GetData4.moveToFirst();
                                try {
                                    Util.writeLogInfo("Xulytin step 11");
                                    str13 = str11;
                                    str15 = str8;
                                    try {
                                        this.db.Update_TinNhanGoc(GetData4.getInt(0), 1);
                                        this.db.close();
                                        Util.writeLogInfo("Xulytin step 12");
                                    } catch (Exception unused) {
                                        Util.writeLogInfo("Xulytin step 13");
                                        this.db.QueryData("Update tbl_tinnhanS set phat_hien_loi = 'ko' WHERE id = " + GetData4.getInt(0));
                                        this.db.close();
                                        this.db.QueryData("Delete From tbl_soctS WHERE ngay_nhan = '" + format + "' AND so_dienthoai = '" + r2 + str13 + i2 + " AND type_kh =" + i);
                                        this.db.close();
                                        Util.writeLogInfo("Xulytin step 14");
                                        if (!Congthuc.CheckTime("18:30")) {
                                            Util.writeLogInfo("Xulytin step 15");
                                            this.db.Gui_Tin_Nhan(GetData4.getInt(0));
                                        }
                                        Util.writeLogInfo("Xulytin step 16");
                                        r2 = r2;
                                        if (GetData4 != null) {
                                        }
                                        Util.writeLogInfo("Xulytin step 17");
                                        str7 = r2;
                                        if (GetData3 != null) {
                                        }
                                        Util.writeLogInfo("Xulytin step 19");
                                        str12 = str7;
                                        if (GetData2 != null) {
                                        }
                                        Util.writeLogInfo("Xulytin step 21");
                                        GetData = this.db.GetData(str6 + format + "' AND so_dienthoai = '" + str12 + str5);
                                        GetData.moveToFirst();
                                        Util.writeLogInfo("Xulytin step 22");
                                        this.db.QueryData("Insert Into tbl_tinnhanS values (null, '" + format + "', '" + format2 + "',1, '" + GetData2.getString(0) + "', '" + GetData2.getString(1) + str13 + (GetData.getInt(0) + 1) + str15 + text + str14 + text + "', 'Hết giờ nhận số!',0,1,1, null)");
                                        this.db.close();
                                        Util.writeLogInfo("Xulytin step 23");
                                        if (GetData != null) {
                                        }
                                        Util.writeLogInfo("Xulytin step 25");
                                        if (!Congthuc.CheckTime("18:30")) {
                                        }
                                        Util.writeLogInfo("Xulytin step 27 -- Kết thúc xử lý tin");
                                    }
                                } catch (Exception unused2) {
                                    str13 = str11;
                                    str15 = str8;
                                }
                                Util.writeLogInfo("Xulytin step 14");
                                if (!Congthuc.CheckTime("18:30") && text.indexOf(str9) == -1 && i == 1) {
                                    Util.writeLogInfo("Xulytin step 15");
                                    this.db.Gui_Tin_Nhan(GetData4.getInt(0));
                                }
                                Util.writeLogInfo("Xulytin step 16");
                                r2 = r2;
                                if (GetData4 != null) {
                                    r2 = r2;
                                    if (!GetData4.isClosed()) {
                                        GetData4.close();
                                        r2 = r2;
                                    }
                                }
                            } catch (SQLException e9) {
                                str13 = str11;
                                str15 = str8;
                                Util.writeLog(e9);
                                str7 = r2;
                                Util.writeLogInfo("Xulytin step 19");
                                str12 = str7;
                                if (GetData2 != null) {
                                }
                                Util.writeLogInfo("Xulytin step 21");
                                GetData = this.db.GetData(str6 + format + "' AND so_dienthoai = '" + str12 + str5);
                                GetData.moveToFirst();
                                Util.writeLogInfo("Xulytin step 22");
                                this.db.QueryData("Insert Into tbl_tinnhanS values (null, '" + format + "', '" + format2 + "',1, '" + GetData2.getString(0) + "', '" + GetData2.getString(1) + str13 + (GetData.getInt(0) + 1) + str15 + text + str14 + text + "', 'Hết giờ nhận số!',0,1,1, null)");
                                this.db.close();
                                Util.writeLogInfo("Xulytin step 23");
                                if (GetData != null) {
                                }
                                Util.writeLogInfo("Xulytin step 25");
                                if (!Congthuc.CheckTime("18:30")) {
                                }
                                Util.writeLogInfo("Xulytin step 27 -- Kết thúc xử lý tin");
                            }
                        } else {
                            r2 = chatId;
                            str13 = str11;
                            str14 = str10;
                            str15 = str8;
                        }
                        Util.writeLogInfo("Xulytin step 17");
                        str7 = r2;
                        if (GetData3 != null) {
                            str7 = r2;
                            if (!GetData3.isClosed()) {
                                Util.writeLogInfo("Xulytin step 18");
                                GetData3.close();
                                str7 = r2;
                            }
                        }
                    } catch (SQLException e10) {
                        Util.writeLog(e10);
                        str7 = r2;
                        Util.writeLogInfo("Xulytin step 19");
                        str12 = str7;
                        if (GetData2 != null) {
                        }
                        Util.writeLogInfo("Xulytin step 21");
                        GetData = this.db.GetData(str6 + format + "' AND so_dienthoai = '" + str12 + str5);
                        GetData.moveToFirst();
                        Util.writeLogInfo("Xulytin step 22");
                        this.db.QueryData("Insert Into tbl_tinnhanS values (null, '" + format + "', '" + format2 + "',1, '" + GetData2.getString(0) + "', '" + GetData2.getString(1) + str13 + (GetData.getInt(0) + 1) + str15 + text + str14 + text + "', 'Hết giờ nhận số!',0,1,1, null)");
                        this.db.close();
                        Util.writeLogInfo("Xulytin step 23");
                        if (GetData != null) {
                        }
                        Util.writeLogInfo("Xulytin step 25");
                        if (!Congthuc.CheckTime("18:30")) {
                        }
                        Util.writeLogInfo("Xulytin step 27 -- Kết thúc xử lý tin");
                    }
                } catch (SQLException e11) {
                    str6 = "Select max(so_tin_nhan) from tbl_tinnhanS WHERE ngay_nhan = '";
                    str13 = "','TL', ";
                    Util.writeLog(e11);
                    str7 = r2;
                    Util.writeLogInfo("Xulytin step 19");
                    str12 = str7;
                    if (GetData2 != null) {
                    }
                    Util.writeLogInfo("Xulytin step 21");
                    GetData = this.db.GetData(str6 + format + "' AND so_dienthoai = '" + str12 + str5);
                    GetData.moveToFirst();
                    Util.writeLogInfo("Xulytin step 22");
                    this.db.QueryData("Insert Into tbl_tinnhanS values (null, '" + format + "', '" + format2 + "',1, '" + GetData2.getString(0) + "', '" + GetData2.getString(1) + str13 + (GetData.getInt(0) + 1) + str15 + text + str14 + text + "', 'Hết giờ nhận số!',0,1,1, null)");
                    this.db.close();
                    Util.writeLogInfo("Xulytin step 23");
                    if (GetData != null) {
                        Util.writeLogInfo("Xulytin step 24");
                        GetData.close();
                    }
                    Util.writeLogInfo("Xulytin step 25");
                    if (!Congthuc.CheckTime("18:30")) {
                        Util.writeLogInfo("Xulytin step 26");
                        sendMessage(GetData2.getLong(1), HET_GIO_NHAN);
                    }
                    Util.writeLogInfo("Xulytin step 27 -- Kết thúc xử lý tin");
                }
                Util.writeLogInfo("Xulytin step 19");
                str12 = str7;
                if (GetData2 != null) {
                    str12 = str7;
                    if (!GetData2.isClosed()) {
                        Util.writeLogInfo("Xulytin step 20");
                        GetData2.close();
                        return;
                    }
                }
            }
            Util.writeLogInfo("Xulytin step 21");
            GetData = this.db.GetData(str6 + format + "' AND so_dienthoai = '" + str12 + str5);
            GetData.moveToFirst();
            Util.writeLogInfo("Xulytin step 22");
            this.db.QueryData("Insert Into tbl_tinnhanS values (null, '" + format + "', '" + format2 + "',1, '" + GetData2.getString(0) + "', '" + GetData2.getString(1) + str13 + (GetData.getInt(0) + 1) + str15 + text + str14 + text + "', 'Hết giờ nhận số!',0,1,1, null)");
            this.db.close();
            Util.writeLogInfo("Xulytin step 23");
            if (GetData != null && !GetData.isClosed()) {
                Util.writeLogInfo("Xulytin step 24");
                GetData.close();
            }
            Util.writeLogInfo("Xulytin step 25");
            if (!Congthuc.CheckTime("18:30") && jSon_Setting.getInt("tin_qua_gio") == 1) {
                Util.writeLogInfo("Xulytin step 26");
                sendMessage(GetData2.getLong(1), HET_GIO_NHAN);
            }
            Util.writeLogInfo("Xulytin step 27 -- Kết thúc xử lý tin");
        } catch (Exception e12) {
            e12.printStackTrace();
            Util.writeLog(e12);
        }
    }

    public static void deleteCache(Context context2) {
        try {
            deleteDir(context2.getCacheDir());
        } catch (Exception unused) {
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir == null || !dir.isDirectory()) {
            if (dir == null || !dir.isFile()) {
                return false;
            }
            return dir.delete();
        }
        for (String str : dir.list()) {
            if (!deleteDir(new File(dir, str))) {
                return false;
            }
        }
        return dir.delete();
    }

    public static String getApplicationHashKey(Context ctx) throws Exception {
        for (Signature signature : ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), PackageManager.GET_SIGNATURES).signatures) {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA");
            messageDigest.update(signature.toByteArray());
            String trim = Base64.encodeToString(messageDigest.digest(), 0).trim();
            if (trim.trim().length() > 0) {
                return trim;
            }
        }
        return null;
    }

    private void gotoAkaManSec() {
        AkaManSec.queryAkaManPwd(this.db);
        String queryAkaManSec = AkaManSec.queryAkaManSec(this.db);
        if ("".equals(queryAkaManSec)) {
            Intent intent = new Intent(this, (Class<?>) Activity_Active.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("mode", "active");
            startActivity(intent);
        } else {
            try {
                String[] split = AkaManSec.getAkaManSec(queryAkaManSec).split(AkaManSec.separator);
                AkaManSec.akaMainURL = split[split.length - 1];
                AkaManSec.akaMainIMEI = split[0];
                if (!AkaManSec.akaMainIMEI.equals(LoginActivity.imei)) {
                    Toast.makeText(this, "Phần mềm chưa được kích hoạt!", Toast.LENGTH_LONG).show();
                    Intent intent2 = new Intent(this, (Class<?>) Activity_Active.class);
                    intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent2.putExtra("mode", "active");
                    startActivity(intent2);
                    return;
                }
                if (AkaManSec.pwdMode == 0) {
                    notificationPermission();
                    return;
                }
                Intent intent3 = new Intent(this, (Class<?>) Activity_Active.class);
                intent3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent3.putExtra("mode", "login");
                startActivity(intent3);
            } catch (Exception unused) {
                Toast.makeText(this, "Phần mềm chưa được kích hoạt!", Toast.LENGTH_LONG).show();
                Intent intent4 = new Intent(this, (Class<?>) Activity_Active.class);
                intent4.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent4.putExtra("mode", "active");
                startActivity(intent4);
            }
        }
        finish();
    }

    private void notificationPermission() {
        ComponentName componentName = new ComponentName(this, (Class<?>) NotificationNewReader.class);
        String string = Settings.Secure.getString(getContentResolver(), "enabled_notification_listeners");
        if (string != null && string.contains(componentName.flattenToString())) {
            return;
        }
        showAlertBox("Truy cập thông báo!", "Hãy cho phép phần mềm được truy cập thông báo của điện thoại để kích hoạt chức năng nhắn tin.").setPositiveButton(OK, new DialogInterface.OnClickListener() { // from class: tamhoang.ldpro4.MainActivity.9
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                if (Build.VERSION.SDK_INT >= 22) {
                    startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
                } else {
                    startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
                }
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() { // from class: tamhoang.ldpro4.MainActivity.8
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).show().setCanceledOnTouchOutside(false);
    }
    private void onAuthStateUpdated(TdApi.AuthorizationState authorizationState) {
        int constructor = authorizationState.getConstructor();
        if (constructor == TdApi.AuthorizationStateWaitCode.CONSTRUCTOR) {
            new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: tamhoang.ldpro4.MainActivity.7
                @Override // java.lang.Runnable
                public void run() {
                    showDialog2();
                }
            });
            return;
        }
        if (constructor == TdApi.AuthorizationStateWaitTdlibParameters.CONSTRUCTOR) {
            TdApi.SetTdlibParameters tdlibParameters = new TdApi.SetTdlibParameters();
            tdlibParameters.apiId = 27623727;
            tdlibParameters.apiHash = "bf70f9cff620b2e7eb48c5191b1ebab0";
            tdlibParameters.useMessageDatabase = true;
            tdlibParameters.useSecretChats = true;
            tdlibParameters.systemLanguageCode = "en";
            tdlibParameters.databaseDirectory = getApplicationContext().getFilesDir().getAbsolutePath();
            tdlibParameters.deviceModel = "Sony";
            tdlibParameters.systemVersion = "7.0";
            tdlibParameters.applicationVersion = "0.1";
            client.send(tdlibParameters, this);
        }
    }

    public void process(boolean tinHethong, String ten_kh, String chatId, String senderUserId, int type_kh, String text, MainActivity context2) {
        String str;
        if (tinHethong) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            this.dmyFormat.setTimeZone(TimeZone.getDefault());
            this.hourFormat.setTimeZone(TimeZone.getDefault());
            String format = this.dmyFormat.format(calendar.getTime());
            String format2 = this.hourFormat.format(calendar.getTime());
            try {
                str = Json_Chat_Telegram.getJSONObject(chatId).getString("title");
            } catch (JSONException unused) {
                Cursor GetData = context2.db.GetData("Select * From tbl_kh_new Where sdt = '" + chatId + "'");
                if (GetData.getCount() > 0) {
                    GetData.moveToFirst();
                    String string = GetData.getString(0);
                    GetData.close();
                    str = string;
                } else {
                    str = "TL - " + chatId;
                }
            }
            int i = (chatId.indexOf(my_id) > -1 || senderUserId.indexOf(my_id) > -1) ? 2 : 1;
            Util.writeLogInfo("=========Begin execute TL message===========");
            Util.writeLogInfo("Add message to Chat_database...");
            Util.writeLogInfo("Message text [" + text + "]");
            context2.db.QueryData("Insert into Chat_database Values( null,'" + format + "', '" + format2 + "', " + i + ", '" + str + "','" + chatId + "', 'TL','" + text + "',1)");
            context2.db.close();
            Util.writeLogInfo("Added message to Chat_database");
            StringBuilder sb = new StringBuilder();
            sb.append("The message length: ");
            sb.append(text.length());
            Util.writeLogInfo(sb.toString());
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Message incomeMessage[");
            sb2.append(text);
            sb2.append("]");
            Util.writeLogInfo(sb2.toString());
            Cursor GetData2 = context2.db.GetData("Select * From tbl_tinnhanS WHERE ngay_nhan = '" + format + "' And Ten_kh = '" + str + "' AND (nd_goc = '" + text + "' OR nd_sua= '" + text + "')");
            Util.writeLogInfo("Check duplicate message...");
            try {
                if (GetData2.getCount() == 0) {
                    Util.writeLogInfo("No message duplicated");
                    Util.writeLogInfo("Check exist customer...");
                    GetData2 = context2.db.GetData("Select * From tbl_kh_new Where sdt = '" + chatId + "'");
                    try {
                        try {
                            int count = GetData2.getCount();
                            Util.writeLogInfo("=====================");
                            Util.writeLogInfo("= customer name: " + str);
                            Util.writeLogInfo("= customer TL chatId: " + chatId);
                            Util.writeLogInfo("= customer count in db: " + count);
                            Util.writeLogInfo("= customer message from notification center: " + text);
                            Util.writeLogInfo("= customer message from Chat table: " + text);
                            Util.writeLogInfo("=====================");
                            if (count > 0 && text.length() > 5) {
                                Util.writeLogInfo("Check type of customer...");
                                GetData2.moveToFirst();
                                Util.writeLogInfo("Check type of customer: type_kh=" + i);
                                if (GetData2.getInt(3) == 1 && i == 1) {
                                    Util.writeLogInfo("Xulytin 1");
                                    Xulytin(chatId, text, format, format2, i);
                                } else if (GetData2.getInt(3) == 2) {
                                    if (i == 1 && text.indexOf(TRA_LAI) == 0) {
                                        Util.writeLogInfo("Xulytin 2");
                                        Xulytin(chatId, text, format, format2, i);
                                    }
                                } else if (GetData2.getInt(3) == 3 && i == 1) {
                                    Util.writeLogInfo("Xulytin 3");
                                    Xulytin(chatId, text, format, format2, i);
                                }
                                if (GetData2 != null) {
                                }
                            }
                            Util.writeLogInfo("Not exist customer or message length too short!");
                            if (GetData2 != null) {
                            }
                        } catch (Exception e) {
                            Util.writeLog(e);
                            if (GetData2 != null && !GetData2.isClosed()) {
                                GetData2.close();
                            }
                        }
                    } finally {
                        if (GetData2 != null && !GetData2.isClosed()) {
                            GetData2.close();
                        }
                    }
                }
            } catch (Exception e2) {
                Util.writeLog(e2);
                if (GetData2 != null) {
                }
            }
            Util.writeLogInfo("=========End execute TL message===========");
        }
    }

    public static void sendMessage(long chatId, String message) {
        TdApi.InlineKeyboardButton[] inlineKeyboardButtonArr = {
                new TdApi.InlineKeyboardButton("https://telegram.org?1", new TdApi.InlineKeyboardButtonTypeUrl()),
                new TdApi.InlineKeyboardButton("https://telegram.org?2", new TdApi.InlineKeyboardButtonTypeUrl()),
                new TdApi.InlineKeyboardButton("https://telegram.org?3", new TdApi.InlineKeyboardButtonTypeUrl())};
                client.send(new TdApi.SendMessage(chatId, 0L, null, null,
                new TdApi.ReplyMarkupInlineKeyboard(
                        new TdApi.InlineKeyboardButton[][]{inlineKeyboardButtonArr, inlineKeyboardButtonArr, inlineKeyboardButtonArr}),
                new TdApi.InputMessageText(
                        new TdApi.FormattedText(message, null), new TdApi.LinkPreviewOptions(), true)), null);
    }

    public static void setListFragment(int i) {
        listFragments.remove(4);
        if (i == 1) {
            listFragments.add(4, new Frag_No_new());
        } else {
            listFragments.add(4, new Frag_No_old());
        }
    }

    public void setupBadge() {
        TextView textView = this.textErrItemCount;
        if (textView != null) {
            int i = mErrItemCount;
            if (i == 0) {
                if (textView.getVisibility() != View.GONE) {
                    this.textErrItemCount.setVisibility(View.GONE);
                }
            } else {
                textView.setText(String.valueOf(Math.min(i, 99)));
                if (this.textErrItemCount.getVisibility() != View.VISIBLE) {
                    this.textErrItemCount.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    public void LayDulieuJson1() {
        try {
            JSONArray jSONArray = new JSONObject(loadJSONFromAsset("kytuthaythe.json")).getJSONArray("formules");
            for (int i = 0; i < jSONArray.length(); i++) {
                JSONObject jSONObject = jSONArray.getJSONObject(i);
                String optString = jSONObject.optString("type", "");
                if (!optString.isEmpty()) {
                    JSONArray jSONArray2 = jSONObject.getJSONArray("datas");
                    jSONArray2.length();
                    for (int i2 = 0; i2 < jSONArray2.length(); i2++) {
                        String string = jSONArray2.getString(i2);
                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("type", optString);
                        hashMap.put("datas", string);
                        formList.add(hashMap);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void LayDulieuJson2() {
        String LayDuLieuJson3 = LayDuLieuJson3();
        if (formArray.size() == 0) {
            try {
                JSONArray jSONArray = new JSONObject(loadJSONFromAsset("thaythe.json")).getJSONArray("listKHs");
                for (int i = 0; i < jSONArray.length(); i++) {
                    JSONObject jSONObject = jSONArray.getJSONObject(i);
                    String optString = jSONObject.optString("str", "");
                    String optString2 = jSONObject.optString("repl_str", "");
                    if (!optString.isEmpty()) {
                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("str", optString);
                        hashMap.put("repl_str", optString2);
                        formArray.add(hashMap);
                        optString2.contains(LayDuLieuJson3);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void Suagia() {
        Cursor cursor;
        Cursor cursor2 = null;
        try {
            Cursor GetData = this.db.GetData("Select * From tbl_kh_new");
            try {
                if (GetData.getCount() > 0 && GetData.moveToFirst() && new JSONObject(new JSONObject(GetData.getString(5)).getString("caidat_gia")).getDouble("dea") > 10.0d) {
                    cursor2 = this.db.GetData("Select * From tbl_kh_new");
                    while (cursor2.moveToNext()) {
                        JSONObject jSONObject = new JSONObject(cursor2.getString(5));
                        JSONObject jSONObject2 = new JSONObject(jSONObject.getString("caidat_gia"));
                        Iterator<String> keys = jSONObject2.keys();
                        while (keys.hasNext()) {
                            String next = keys.next();
                            if (jSONObject2.getDouble(next) > 100.0d) {
                                jSONObject2.put(next, jSONObject2.getDouble(next) / 1000.0d);
                            }
                        }
                        jSONObject.put("caidat_gia", jSONObject2);
                        this.db.QueryData("update tbl_kh_new set tbl_mb = '" + jSONObject.toString() + "' WHERE ten_kh = '" + cursor2.getString(0) + "'");
                        this.db.close();
                    }
                }
                if (GetData != null && !GetData.isClosed()) {
                    GetData.isClosed();
                }
                if (cursor2 == null || cursor2.isClosed()) {
                    return;
                }
                cursor2.isClosed();
            } catch (JSONException e) {
                e = e;
                cursor = cursor2;
                cursor2 = GetData;
                try {
                    e.printStackTrace();
                    if (cursor2 != null && !cursor2.isClosed()) {
                        cursor2.isClosed();
                    }
                    if (cursor == null || cursor.isClosed()) {
                        return;
                    }
                    cursor.isClosed();
                } catch (Throwable th) {
                    th = th;
                    if (cursor2 != null && !cursor2.isClosed()) {
                        cursor2.isClosed();
                    }
                    if (cursor != null && !cursor.isClosed()) {
                        cursor.isClosed();
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                cursor = cursor2;
                cursor2 = GetData;
                if (cursor2 != null) {
                    cursor2.isClosed();
                }
                if (cursor != null) {
                    cursor.isClosed();
                }
            }
        } catch (Throwable th3) {
        }
    }

    public String loadJSONFromAsset(String Filename) {
        try {
            InputStream open = getAssets().open(Filename);
            byte[] bArr = new byte[open.available()];
            open.read(bArr);
            open.close();
            return new String(bArr, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        new AlertDialog.Builder(this).setMessage("Bạn có muốn thoát không?").setCancelable(true).setPositiveButton("Thoát", new DialogInterface.OnClickListener() { // from class: tamhoang.ldpro4.MainActivity.13
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent("android.intent.action.MAIN");
                intent.addCategory("android.intent.category.HOME");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }).setNegativeButton("Không", null).show();
    }

    public void onClick(View v) {
        showDialog(0);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Object valueOf;
        Object valueOf2;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT != 26) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        this.db = new Database(this);
        if (AkaManSec.akaEnable) {
            if ("logon".equals(getIntent().getStringExtra("mode"))) {
                notificationPermission();
            } else {
                gotoAkaManSec();
            }
            String queryAkaManSec = AkaManSec.queryAkaManSec(this.db);
            if (!"".equals(queryAkaManSec)) {
                String[] split = AkaManSec.getAkaManSec(queryAkaManSec).split(AkaManSec.separator);
                AkaManSec.akaMainURL = split[split.length - 1];
                AkaManSec.akaMainIMEI = split[0];
            }
        }
        Suagia();
        this.viewData = Get_link() + "json_data.php";
        this.insertData = Get_link() + "json_insert.php";
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        LayDulieuJson1();
        LayDulieuJson2();
        this.db.LayDanhsachKH();
        Cursor GetData = this.db.GetData("Select * From tbl_Setting WHERE ID = 1");
        if (GetData != null && GetData.moveToFirst()) {
            try {
                jSon_Setting = new JSONObject(GetData.getString(1));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            GetData.close();
        }
        View inflate = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.customactionbar, (ViewGroup) null);
        getSupportActionBar().setCustomView(inflate);
        Calendar calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DATE);
        this.Text_date =  inflate.findViewById(R.id.myTextDate);
        this.Text_Menu =  inflate.findViewById(R.id.myTextMenu);
        TextView textView = this.Text_date;
        StringBuilder sb = new StringBuilder();
        int i = mDay;
        if (i < 10) {
            valueOf = "0" + mDay;
        } else {
            valueOf = Integer.valueOf(i);
        }
        sb.append(valueOf);
        sb.append("-");
        int i2 = mMonth + 1;
        if (i2 < 10) {
            valueOf2 = "0" + (mMonth + 1);
        } else {
            valueOf2 = Integer.valueOf(i2);
        }
        sb.append(valueOf2);
        sb.append("-");
        sb.append(mYear);
        textView.setText(sb.toString());
        onDateSetListener = new DatePickerDialog.OnDateSetListener() { // from class: tamhoang.ldpro4.MainActivity.2
            @Override // android.app.DatePickerDialog.OnDateSetListener
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Object valueOf3;
                Object valueOf4;
                MainActivity.mYear = year;
                MainActivity.mMonth = monthOfYear;
                MainActivity.mDay = dayOfMonth;
                MainActivity.sms = true;
                TextView textView2 = Text_date;
                StringBuilder sb2 = new StringBuilder();
                if (MainActivity.mDay < 10) {
                    valueOf3 = "0" + MainActivity.mDay;
                } else {
                    valueOf3 = Integer.valueOf(MainActivity.mDay);
                }
                sb2.append(valueOf3);
                sb2.append("-");
                if (MainActivity.mMonth + 1 < 10) {
                    valueOf4 = "0" + (MainActivity.mMonth + 1);
                } else {
                    valueOf4 = Integer.valueOf(MainActivity.mMonth + 1);
                }
                sb2.append(valueOf4);
                sb2.append("-");
                sb2.append(MainActivity.mYear);
                textView2.setText(sb2.toString());
            }
        };
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff37474f")));
        this.drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        this.drawerPane = (RelativeLayout) findViewById(R.id.drawer_pane);
        this.lvNav = (ListView) findViewById(R.id.nav_list);
        listNavItems = new ArrayList();
        listNavItems.add(new tamhoang.ldpro4.ui.home.NavItem("Trang chủ", "Imei, hạn sử dụng", R.drawable.home));
        listNavItems.add(new tamhoang.ldpro4.ui.home.NavItem("Sửa tin nhắn", "Sửa/tải lại tin nhắn", R.drawable.edit));
        listNavItems.add(new tamhoang.ldpro4.ui.home.NavItem("Quản lý tin nhắn", "SMS, Zalo, Viber, WhatsApp", R.drawable.chat));
        listNavItems.add(new tamhoang.ldpro4.ui.home.NavItem("Chuyển số/Giữ số", "Chuyển số và giữ số", R.drawable.number_report));
        listNavItems.add(new tamhoang.ldpro4.ui.home.NavItem("Báo cáo thắng thua", "Báo cáo kết quả từng khách", R.drawable.licsence));
        listNavItems.add(new tamhoang.ldpro4.ui.home.NavItem("Chạy trang", "Vào trang One789", R.drawable.ld789));
        listNavItems.add(new tamhoang.ldpro4.ui.home.NavItem("Chạy trang Win-Win", "Vào trang winwin6", R.drawable.winwin));
        listNavItems.add(new tamhoang.ldpro4.ui.home.NavItem("Cân bảng", "Cân bảng trực tiếp", R.drawable.livestream));
        listNavItems.add(new tamhoang.ldpro4.ui.home.NavItem("Xổ số trực tiếp", "Quay và tính tiền trực tiếp", R.drawable.livekq));
        listNavItems.add(new tamhoang.ldpro4.ui.home.NavItem("Quản lý công nợ", "Công nợ/Thanh toán", R.drawable.money_report));
        listNavItems.add(new tamhoang.ldpro4.ui.home.NavItem("Danh sách khách hàng", "Thông tin khách hàng", R.drawable.contact));
        listNavItems.add(new tamhoang.ldpro4.ui.home.NavItem("Cài đặt", "Cài đặt cho ứng dụng", R.drawable.settings));
        listNavItems.add(new tamhoang.ldpro4.ui.home.NavItem("Các tin nhắn mẫu", "Các cú pháp chuẩn", R.drawable.guilde));
        listNavItems.add(new NavItem("Cơ sở dữ liệu", "Cập nhật KQ/Tính tiền", R.drawable.database));
        this.lvNav.setAdapter((ListAdapter) new NavListAdapter(getApplicationContext(), R.layout.item_nav_list, listNavItems));
        listFragments = new ArrayList<>();
        listFragments.add(new Frag_Home());
        listFragments.add(new Tab_Tinnhan());
        listFragments.add(new Frag_Chat_Manager());
        listFragments.add(new Frag_CanChuyenNew());
        try {
            if (jSon_Setting.getInt("kieu_bao_cao") == 1) {
                listFragments.add(new Frag_No_new());
            } else {
                listFragments.add(new Frag_No_old());
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            listFragments.add(new Frag_No_new());
        }
        listFragments.add(new Tab_ChayTrang());
        listFragments.add(new Tab_ChayTrang_Win());
        listFragments.add(new Livestream());
        listFragments.add(new TructiepXoso());
        listFragments.add(new Frag_MoRP1());
        listFragments.add(new Frag_Setting1());
        listFragments.add(new Frag_Setting3());
        listFragments.add(new Frag_SMS_Templates());
        listFragments.add(new Frag_Database());
        getSupportFragmentManager().beginTransaction().replace(R.id.main_content, listFragments.get(0)).commit();
        setTitle(listNavItems.get(0).getTitle());
        this.lvNav.setItemChecked(0, true);
        this.drawerLayout.closeDrawer(this.drawerPane);
        this.lvNav.setOnItemClickListener((adapterView, view, position, id) -> {
            notifivationNavigated = false;
            currentMenuPosition = position;
            getSupportFragmentManager().beginTransaction().replace(R.id.main_content, MainActivity.listFragments.get(position)).commit();
            MainActivity mainActivity = MainActivity.this;
            mainActivity.setTitle(mainActivity.listNavItems.get(position).getTitle());
            lvNav.setItemChecked(position, true);
            drawerLayout.closeDrawer(drawerPane);
        });
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, this.drawerLayout, R.string.drawer_opened, R.string.drawer_closed) { // from class: tamhoang.ldpro4.MainActivity.4
            @Override
            // android.support.v7.app.ActionBarDrawerToggle, android.support.v4.widget.DrawerLayout.DrawerListener
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            // android.support.v7.app.ActionBarDrawerToggle, android.support.v4.widget.DrawerLayout.DrawerListener
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                View currentFocus = getCurrentFocus();
                if (currentFocus == null) {
                    currentFocus = new View(MainActivity.this);
                }
                inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
            }

            @Override
            // android.support.v7.app.ActionBarDrawerToggle, android.support.v4.widget.DrawerLayout.DrawerListener
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                View currentFocus = getCurrentFocus();
                if (currentFocus == null) {
                    currentFocus = new View(MainActivity.this);
                }
                inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
            }
        };
        this.actionBarDrawerToggle = actionBarDrawerToggle;
        this.drawerLayout.setDrawerListener(actionBarDrawerToggle);
        this.actionBarDrawerToggle.syncState();
        ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).cancel(1);
        startService(new Intent(this, ZBroadcast.class));
        client = TelegramClient.getClient(this);
        this.textErrItemCount =  inflate.findViewById(R.id.error_badge);
        RelativeLayout relativeLayout = (RelativeLayout) inflate.findViewById(R.id.notification);
        this.notification = relativeLayout;
        relativeLayout.setOnClickListener(v -> {
            if (notifivationNavigated && currentMenuPosition != -1) {
                notifivationNavigated = false;
                getSupportFragmentManager().beginTransaction().replace(R.id.main_content, MainActivity.listFragments.get(currentMenuPosition)).commit();
                MainActivity mainActivity = MainActivity.this;
                mainActivity.setTitle(mainActivity.listNavItems.get(currentMenuPosition).getTitle());
                lvNav.setItemChecked(currentMenuPosition, true);
                drawerLayout.closeDrawer(drawerPane);
                return;
            }
            notifivationNavigated = true;
            Toast.makeText(MainActivity.this, "Sang màn sửa tin...", Toast.LENGTH_SHORT).show();
            getSupportFragmentManager().beginTransaction().replace(R.id.main_content, MainActivity.listFragments.get(1)).commit();
            MainActivity mainActivity2 = MainActivity.this;
            mainActivity2.setTitle(mainActivity2.listNavItems.get(1).getTitle());
            lvNav.setItemChecked(1, true);
            drawerLayout.closeDrawer(drawerPane);
        });
        final Handler handler2 = new Handler();
        handler2.postDelayed(new Runnable() {
            @Override
            public void run() {
                Cursor cursor = null;
                try {
                    try {
                        String Get_date = MainActivity.Get_date();
                        cursor = db.GetData("select * from tbl_tinnhanS WHERE phat_hien_loi <> 'ok' AND ngay_nhan = '" + Get_date + "'");
                        if (cursor != null) {
                            MainActivity.mErrItemCount = cursor.getCount();
                        } else {
                            MainActivity.mErrItemCount = 0;
                        }
                        if (cursor != null) {
                        }
                    } catch (Exception e3) {
                        e3.printStackTrace();
                        Util.writeLog(e3);
                        if (0 != 0) {
                        }
                    }
                    setupBadge();
                    handler2.postDelayed(this, 3000L);
                } catch (Throwable th) {
                    if (0 != 0 && !cursor.isClosed()) {
                        cursor.close();
                    }
                    throw th;
                }
            }
        }, 3000L);
        setupBadge();
    }

    @Override
    public Dialog onCreateDialog(int id) {
        if (id != 0) {
            return null;
        }
        return new DatePickerDialog(this, onDateSetListener, mYear, mMonth, mDay);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        deleteCache(getApplicationContext());
    }

    public void onMenu(View v) {
        Util.writeLogInfo("onMenu - my_id: " + my_id);
        String[] strArr = my_id != "" ? new String[]{"Từ điển cá nhân", "Nhập dàn giữ số", "Cài đặt chuyển thẳng", "Logout Telegram"} : new String[]{"Từ điển cá nhân", "Nhập dàn giữ số", "Cài đặt chuyển thẳng", "Login Telegram"};
        PopupMenu popupMenu = new PopupMenu(this, v);
        for (int i = 0; i < strArr.length; i++) {
            popupMenu.getMenu().add(1, i, i, strArr[i]);
        }
        new AlertDialog.Builder(this);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() { // from class: tamhoang.ldpro4.MainActivity.10
            @Override // android.widget.PopupMenu.OnMenuItemClickListener
            public boolean onMenuItemClick(MenuItem item) {
                notifivationNavigated = false;
                currentMenuPosition = -1;
                int order = item.getOrder();
                if (order == 0) {
                    startActivity(new Intent(MainActivity.this, (Class<?>) Activity_thaythe.class));
                } else if (order == 1) {
                    startActivity(new Intent(MainActivity.this, (Class<?>) Activity_GiuSo.class));
                } else if (order == 2) {
                    startActivity(new Intent(MainActivity.this, (Class<?>) Activity_ChuyenThang.class));
                } else if (order == 3) {
                    if (MainActivity.my_id != "") {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("Thoát Telegram?");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() { // from class: tamhoang.ldpro4.MainActivity.10.1
                            @Override // android.content.DialogInterface.OnClickListener
                            public void onClick(DialogInterface dialog, int which) {
                                db.QueryData("Update So_om set Sphu1 ='' where ID = 1");
                                db.close();
                                client.send(new TdApi.LogOut(), MainActivity.this, null);
                                MainActivity.my_id = "";
                                Toast.makeText(MainActivity.this, "Đã thoát Telegram", Toast.LENGTH_SHORT).show();
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() { // from class: tamhoang.ldpro4.MainActivity.10.2
                            @Override // android.content.DialogInterface.OnClickListener
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        builder.create().show();
                    } else {
                        showDialog1();
                    }
                }
                return true;
            }
        });
        popupMenu.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (this.actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        this.actionBarDrawerToggle.syncState();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode != 1) {
            if (requestCode != 2) {
                return;
            }
        } else if (grantResults.length <= 0 || grantResults[0] != 0) {
            Toast.makeText(getApplicationContext(), "Can't access messages.", Toast.LENGTH_LONG).show();
            return;
        } else if (ContextCompat.checkSelfPermission(this, "android.permission.READ_CONTACTS") != 0 && !ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.READ_CONTACTS")) {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.READ_CONTACTS"}, 2);
        }
        if (grantResults.length <= 0 || grantResults[0] != 0) {
            Toast.makeText(getApplicationContext(), "Can't access messages.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onResult(TdApi.Object object) {
        switch (object.getConstructor()) {
            case TdApi.UpdateNewMessage.CONSTRUCTOR:
                break;
            case TdApi.User.CONSTRUCTOR:
                my_id = ((TdApi.User) object).id + "";
                break;
            case TdApi.UpdateOption.CONSTRUCTOR:
                TdApi.UpdateOption updateOption = (TdApi.UpdateOption) object;
                if (updateOption.name.contains("my_id")) {
                    String optionValue = updateOption.value.toString();
                    if (optionValue.indexOf("=") > 0) {
                        my_id = optionValue;
                        String substring = optionValue.substring(optionValue.indexOf("=") + 1);
                        my_id = substring;
                        my_id = substring.substring(0, substring.indexOf("\n")).trim();
                        this.db.QueryData("Update So_Om set Sphu1 = '" + my_id + "' WHERE ID = 1");
                        this.db.close();
                        return;
                    }
                    return;
                }
                return;
            case TdApi.UpdateUser.CONSTRUCTOR:
                TdApi.UpdateUser updateUser = (TdApi.UpdateUser) object;
                try {
                    if (Json_Chat_Telegram.has(updateUser.user.id + "")) {
                        return;
                    }
                    JSONObject jSONObject = new JSONObject();
                    String userType = updateUser.user.type.toString();
                    jSONObject.put("type", userType.substring(0, userType.indexOf("{")).trim());
                    jSONObject.put("basicGroupId", updateUser.user.id);
                    jSONObject.put("title", "TL - " + updateUser.user.firstName + " " + updateUser.user.lastName);
                    StringBuilder sb = new StringBuilder();
                    sb.append(updateUser.user.id);
                    sb.append("");
                    Json_Chat_Telegram.put(sb.toString(), jSONObject);
                    return;
                } catch (JSONException e) {
                    e.printStackTrace();
                    return;
                }
            case TdApi.UpdateConnectionState.CONSTRUCTOR:
                if (((TdApi.UpdateConnectionState) object).state.getConstructor() == TdApi.ConnectionStateReady.CONSTRUCTOR) {
                    Log.d("AuthActivity", "onResult: ConnectionStateReady");
                    return;
                }
                return;
            case TdApi.UpdateAuthorizationState.CONSTRUCTOR:
                onAuthStateUpdated(((TdApi.UpdateAuthorizationState) object).authorizationState);
                return;
            case TdApi.UpdateNewChat.CONSTRUCTOR:
                TdApi.UpdateNewChat updateNewChat = (TdApi.UpdateNewChat) object;
                try {
                    if (Json_Chat_Telegram.has(updateNewChat.chat.id + "")) {
                        return;
                    }
                    JSONObject jSONObject2 = new JSONObject();
                    String chatType = updateNewChat.chat.type.toString();
                    jSONObject2.put("type", chatType.substring(0, chatType.indexOf("{")).trim());
                    jSONObject2.put("basicGroupId", updateNewChat.chat.id);
                    jSONObject2.put("title", "TL - " + updateNewChat.chat.title);
                    Json_Chat_Telegram.put(updateNewChat.chat.id + "", jSONObject2);
                    return;
                } catch (JSONException e2) {
                    e2.printStackTrace();
                    return;
                }
            default:
                return;
        }
        if (my_id == "") {
            Cursor GetData = this.db.GetData("Select Sphu1 from so_om where ID = 1");
            GetData.moveToFirst();
            my_id = GetData.getString(0);
            Util.writeLogInfo("onResult - my_id: " + my_id);
            GetData.close();
        }
        TdApi.UpdateNewMessage updateNewMessage = (TdApi.UpdateNewMessage) object;
        new ProcessMessageAsyncTask().execute(new MyParam(!updateNewMessage.message.isChannelPost && updateNewMessage.message.chatId != 777000 && updateNewMessage.message.chatId != 93372553, null, updateNewMessage.message.chatId + "", ((TdApi.MessageSenderUser) updateNewMessage.message.senderId).userId + "", 0, ((TdApi.MessageText) updateNewMessage.message.content).text.text.replace("'", ""), this));
    }

    public AlertDialog.Builder showAlertBox(String title, String message) {
        return new AlertDialog.Builder(this).setTitle(title).setMessage(message);
    }

    public void showDialog1() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_tele_login);
        dialog.getWindow().setLayout(-1, -2);
        final EditText editText =  dialog.findViewById(R.id.authPhone);
        ( dialog.findViewById(R.id.loginBtn)).setOnClickListener(new View.OnClickListener() { // from class: tamhoang.ldpro4.MainActivity.11
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                try {
                    Util.writeLogInfo("Login telegram");
                    String obj = editText.getText().toString();
                    Util.writeLogInfo("Login telegram phone: " + obj);
                    client.send(new TdApi.SetAuthenticationPhoneNumber(obj.replace(" ", ""), null), MainActivity.this);
                    dialog.dismiss();
                    return;
                } catch (Exception e) {
                    Util.writeLog(e);
                }
                Toast.makeText(MainActivity.this, "Hãy nhập 10 số của số điện thoại!", Toast.LENGTH_LONG).show();
            }
        });
        dialog.setCancelable(true);
        dialog.show();
    }

    public void showDialog2() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_auth);
        dialog.getWindow().setLayout(-1, -2);
        final EditText editText =  dialog.findViewById(R.id.authCode);
        ( dialog.findViewById(R.id.checkBtn)).setOnClickListener(new View.OnClickListener() { // from class: tamhoang.ldpro4.MainActivity.12
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                String obj = editText.getText().toString();
                if (obj.length() != 5) {
                    Toast.makeText(MainActivity.this, "Hãy nhập đủ 5 số được gửi về Telegram!", Toast.LENGTH_LONG).show();
                } else {
                    client.send(new TdApi.CheckAuthenticationCode(obj), MainActivity.this);
                    dialog.dismiss();
                }
            }
        });
        dialog.setCancelable(true);
        dialog.show();
    }
}
