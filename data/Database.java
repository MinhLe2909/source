package tamhoang.ldpro4.data;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.telephony.SmsManager;

import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;

import java.io.File;
import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.TimeZone;
import net.lingala.zip4j.util.InternalZipConstants;
import org.json.JSONException;
import org.json.JSONObject;

import tamhoang.ldpro4.formula.Congthuc;
import tamhoang.ldpro4.ui.home.MainActivity;
import tamhoang.ldpro4.ui.notification.NotificationNewReader;

public class Database extends SQLiteOpenHelper {
    protected static SQLiteDatabase db;
    JSONObject caidat_gia;
    JSONObject caidat_tg;
    JSONObject json;
    JSONObject jsonDanSo;
    JSONObject json_Tralai;
    public String[][] mang;
    private Context mcontext;

    public Database(Context context) {
        super(context, context.getFilesDir() + File.separator + "win6868", null, 1);
        this.mcontext = context;
    }

    private void BaoLoiDan(int i) {
        if (this.mang[i][4].indexOf("Không hiểu") > -1) {
            String[][] strArr = this.mang;
            strArr[i][0] = Congthuc.ToMauError(strArr[i][4].substring(11), this.mang[i][0]);
        }
    }

    private void BaoLoiTien(int i) {
        try {
            String[][] strArr = this.mang;
            strArr[i][5] = Congthuc.XulyTien(strArr[i][3]);
            if (this.mang[i][5].indexOf("Không hiểu") > -1 && this.mang[i][5].trim().length() < 13) {
                String[][] strArr2 = this.mang;
                strArr2[i][0] = Congthuc.ToMauError(strArr2[i][5].substring(11), this.mang[i][0]);
            } else if (this.mang[i][5].indexOf("Không hiểu") > -1 && this.mang[i][5].trim().length() > 12) {
                String[][] strArr3 = this.mang;
                strArr3[i][0] = Congthuc.ToMauError(strArr3[i][3], strArr3[i][0]);
            }
        } catch (Exception unused) {
            String[][] strArr4 = this.mang;
            strArr4[i][0] = Congthuc.ToMauError(strArr4[i][3], strArr4[i][0]);
        }
    }

    private void XulyMang(int i) {
        String[] split;
        boolean z;
        boolean z2;
        boolean z3;
        int i2 = 5;
        int i3 = -1;
        String str = "";
        if (this.mang[i][1].indexOf("lo dau") > -1) {
            if (this.mang[i][2].indexOf("loa") <= -1 || this.mang[i][2].trim().indexOf("loa") <= 0) {
                String[][] strArr = this.mang;
                strArr[i][4] = strArr[i][2].replaceFirst("loa :", "");
                String[][] strArr2 = this.mang;
                strArr2[i][4] = strArr2[i][4].replaceFirst("loa:", "");
                String[][] strArr3 = this.mang;
                strArr3[i][4] = strArr3[i][4].replaceFirst("loa", "");
                String[][] strArr4 = this.mang;
                strArr4[i][4] = Congthuc.XulyLoDe(strArr4[i][4]);
            } else {
                String[] strArr5 = this.mang[i];
                StringBuilder sb = new StringBuilder();
                sb.append("Không hiểu ");
                String[][] strArr6 = this.mang;
                sb.append(strArr6[i][2].substring(0, strArr6[i][2].indexOf("loa")));
                strArr5[4] = sb.toString();
            }
        } else if (this.mang[i][1].indexOf("lo") > -1) {
            if (this.mang[i][2].indexOf("lo") <= -1 || this.mang[i][2].trim().indexOf("lo") <= 0) {
                String[][] strArr7 = this.mang;
                strArr7[i][4] = strArr7[i][2].replaceFirst("lo :", "");
                String[][] strArr8 = this.mang;
                strArr8[i][4] = strArr8[i][4].replaceFirst("lo:", "");
                String[][] strArr9 = this.mang;
                strArr9[i][4] = strArr9[i][4].replaceFirst("lo", "");
                String[][] strArr10 = this.mang;
                strArr10[i][4] = Congthuc.XulyLoDe(strArr10[i][4]);
            } else {
                String[] strArr11 = this.mang[i];
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Không hiểu ");
                String[][] strArr12 = this.mang;
                sb2.append(strArr12[i][2].substring(0, strArr12[i][2].indexOf("lo")));
                strArr11[4] = sb2.toString();
            }
        } else if (this.mang[i][1].indexOf("de dau db") > -1) {
            if (this.mang[i][2].indexOf("dea") <= -1 || this.mang[i][2].trim().indexOf("dea") <= 0) {
                String[][] strArr13 = this.mang;
                strArr13[i][4] = strArr13[i][2].replaceFirst("dea :", "");
                String[][] strArr14 = this.mang;
                strArr14[i][4] = strArr14[i][4].replaceFirst("dea:", "");
                String[][] strArr15 = this.mang;
                strArr15[i][4] = strArr15[i][4].replaceFirst("dea", "");
                String[][] strArr16 = this.mang;
                strArr16[i][4] = Congthuc.XulyLoDe(strArr16[i][4]);
            } else {
                String[] strArr17 = this.mang[i];
                StringBuilder sb3 = new StringBuilder();
                sb3.append("Không hiểu ");
                String[][] strArr18 = this.mang;
                sb3.append(strArr18[i][2].substring(0, strArr18[i][2].indexOf("de")));
                strArr17[4] = sb3.toString();
            }
        } else if (this.mang[i][1].indexOf("de dit db") > -1) {
            if (this.mang[i][2].indexOf("deb") <= -1 || this.mang[i][2].trim().indexOf("deb") <= 0) {
                String[][] strArr19 = this.mang;
                strArr19[i][4] = strArr19[i][2].replaceFirst("deb :", "");
                String[][] strArr20 = this.mang;
                strArr20[i][4] = strArr20[i][4].replaceFirst("deb:", "");
                String[][] strArr21 = this.mang;
                strArr21[i][4] = strArr21[i][4].replaceFirst("deb", "");
                String[][] strArr22 = this.mang;
                strArr22[i][4] = strArr22[i][4].replaceFirst("de :", "");
                String[][] strArr23 = this.mang;
                strArr23[i][4] = strArr23[i][4].replaceFirst("de:", "");
                String[][] strArr24 = this.mang;
                strArr24[i][4] = strArr24[i][4].replaceFirst("de ", "");
                String[][] strArr25 = this.mang;
                strArr25[i][4] = Congthuc.XulyLoDe(strArr25[i][4]);
            } else {
                String[] strArr26 = this.mang[i];
                StringBuilder sb4 = new StringBuilder();
                sb4.append("Không hiểu ");
                String[][] strArr27 = this.mang;
                sb4.append(strArr27[i][2].substring(0, strArr27[i][2].indexOf("de")));
                strArr26[4] = sb4.toString();
            }
        } else if (this.mang[i][1].indexOf("de dau nhat") > -1) {
            if (this.mang[i][2].indexOf("dec") <= -1 || this.mang[i][2].trim().indexOf("dec") <= 0) {
                String[][] strArr28 = this.mang;
                strArr28[i][4] = strArr28[i][2].replaceFirst("dec :", "");
                String[][] strArr29 = this.mang;
                strArr29[i][4] = strArr29[i][4].replaceFirst("dec:", "");
                String[][] strArr30 = this.mang;
                strArr30[i][4] = strArr30[i][4].replaceFirst("dec", "");
                String[][] strArr31 = this.mang;
                strArr31[i][4] = Congthuc.XulyLoDe(strArr31[i][4]);
            } else {
                String[] strArr32 = this.mang[i];
                StringBuilder sb5 = new StringBuilder();
                sb5.append("Không hiểu ");
                String[][] strArr33 = this.mang;
                sb5.append(strArr33[i][2].substring(0, strArr33[i][2].indexOf("de")));
                strArr32[4] = sb5.toString();
            }
        } else if (this.mang[i][1].indexOf("de dit nhat") > -1) {
            if (this.mang[i][2].indexOf("ded") <= -1 || this.mang[i][2].trim().indexOf("ded") <= 0) {
                String[][] strArr34 = this.mang;
                strArr34[i][4] = strArr34[i][2].replaceFirst("ded :", "");
                String[][] strArr35 = this.mang;
                strArr35[i][4] = strArr35[i][4].replaceFirst("ded:", "");
                String[][] strArr36 = this.mang;
                strArr36[i][4] = strArr36[i][4].replaceFirst("ded", "");
                String[][] strArr37 = this.mang;
                strArr37[i][4] = Congthuc.XulyLoDe(strArr37[i][4]);
            } else {
                String[] strArr38 = this.mang[i];
                StringBuilder sb6 = new StringBuilder();
                sb6.append("Không hiểu ");
                String[][] strArr39 = this.mang;
                sb6.append(strArr39[i][2].substring(0, strArr39[i][2].indexOf("de")));
                strArr38[4] = sb6.toString();
            }
        } else if (this.mang[i][1].indexOf("de 8") > -1) {
            if (this.mang[i][2].indexOf("det") <= -1 || this.mang[i][2].trim().indexOf("det") <= 0) {
                String[][] strArr40 = this.mang;
                strArr40[i][4] = strArr40[i][2].replaceFirst("det :", "");
                String[][] strArr41 = this.mang;
                strArr41[i][4] = strArr41[i][4].replaceFirst("det:", "");
                String[][] strArr42 = this.mang;
                strArr42[i][4] = strArr42[i][4].replaceFirst("det", "");
                String[][] strArr43 = this.mang;
                strArr43[i][4] = Congthuc.XulyLoDe(strArr43[i][4]);
            } else {
                String[] strArr44 = this.mang[i];
                StringBuilder sb7 = new StringBuilder();
                sb7.append("Không hiểu ");
                String[][] strArr45 = this.mang;
                sb7.append(strArr45[i][2].substring(0, strArr45[i][2].indexOf("de")));
                strArr44[4] = sb7.toString();
            }
        } else if (this.mang[i][1].indexOf("hai cua") > -1) {
            if (this.mang[i][2].indexOf("hc") <= -1 || this.mang[i][2].trim().indexOf("hc") <= 0) {
                String[][] strArr46 = this.mang;
                strArr46[i][4] = strArr46[i][2].replaceFirst("hc :", "");
                String[][] strArr47 = this.mang;
                strArr47[i][4] = strArr47[i][4].replaceFirst("hc:", "");
                String[][] strArr48 = this.mang;
                strArr48[i][4] = strArr48[i][4].replaceFirst("hc", "");
                String[][] strArr49 = this.mang;
                strArr49[i][4] = Congthuc.XulyLoDe(strArr49[i][4]);
            } else {
                String[] strArr50 = this.mang[i];
                StringBuilder sb8 = new StringBuilder();
                sb8.append("Không hiểu ");
                String[][] strArr51 = this.mang;
                sb8.append(strArr51[i][2].substring(0, strArr51[i][2].indexOf("hc")));
                strArr50[4] = sb8.toString();
            }
        } else if (this.mang[i][1].indexOf("xn") > -1) {
            if (this.mang[i][2].indexOf("xn") <= -1 || this.mang[i][2].trim().indexOf("xn") <= 0) {
                String[][] strArr52 = this.mang;
                strArr52[i][4] = strArr52[i][2].replaceFirst("xn :", "");
                String[][] strArr53 = this.mang;
                strArr53[i][4] = strArr53[i][4].replaceFirst("xn:", "");
                String[][] strArr54 = this.mang;
                strArr54[i][4] = strArr54[i][4].replaceFirst("xn", "");
                if (this.mang[i][2].indexOf("xn 2 ") > -1) {
                    String[][] strArr55 = this.mang;
                    strArr55[i][4] = strArr55[i][4].trim();
                    String[][] strArr56 = this.mang;
                    strArr56[i][4] = strArr56[i][4].substring(2);
                    String[][] strArr57 = this.mang;
                    strArr57[i][4] = Congthuc.XulySo(strArr57[i][4]);
                    Iterator<String> it = Congthuc.XulyXienGhep(this.mang[i][4], 2).iterator();
                    String str2 = "";
                    while (it.hasNext()) {
                        str2 = str2 + it.next() + " ";
                    }
                    this.mang[i][4] = str2;
                } else {
                    String[][] strArr58 = this.mang;
                    strArr58[i][4] = Congthuc.XulySo(strArr58[i][4].replaceAll("xn", " "));
                    this.mang[i][4] = Congthuc.XulyXien("2 " + this.mang[i][4].trim());
                }
                String[] split2 = this.mang[i][4].split(" ");
                for (int i4 = 0; i4 < split2.length; i4++) {
                    if (split2[i4].replaceAll(",", "").length() != 2 || !Congthuc.isNumeric(split2[i4].replaceAll(",", ""))) {
                        z3 = true;
                        break;
                    }
                }
                z3 = false;
                if (!z3 && split2.length < 5) {
                    String[][] strArr59 = this.mang;
                    strArr59[i][4] = Congthuc.XulySo(strArr59[i][4]);
                }
                String[] split3 = this.mang[i][4].split(" ");
                int i5 = 0;
                while (i5 < split3.length) {
                    String XulySo = Congthuc.XulySo(split3[i5]);
                    if (XulySo.length() < i2 || XulySo.length() > 6 || XulySo.indexOf("Không hiểu") > i3) {
                        if (split3[i5].length() > 4) {
                            this.mang[i][4] = "Không hiểu " + this.mang[i][2];
                        } else {
                            this.mang[i][4] = "Không hiểu " + this.mang[i][0];
                        }
                        if (this.mang[i][4].indexOf("Không hiểu") == -1) {
                            this.mang[i][4] = "";
                            String str3 = "";
                            for (int i6 = 0; i6 < split3.length; i6++) {
                                try {
                                    str3 = Congthuc.XulySo(split3[i6]);
                                } catch (Exception unused) {
                                    this.mang[i][4] = "Không hiểu " + split3[i6];
                                }
                                if (str3.indexOf("Không hiểu") != -1) {
                                    break;
                                }
                                boolean z4 = false;
                                for (String str4 : str3.split(",")) {
                                    if (str3.length() - str3.replaceAll(str4, "").length() > 2) {
                                        z4 = true;
                                    }
                                }
                                if (str3.length() < 5 || str3.length() > 6 || z4) {
                                    this.mang[i][4] = "Không hiểu " + this.mang[i][2];
                                    break;
                                }
                                this.mang[i][4] = this.mang[i][4] + Congthuc.sortXien(str3) + " ";
                            }
                        }
                    } else {
                        String[] split4 = XulySo.split(",");
                        for (int i7 = 0; i7 < split4.length; i7++) {
                            if (split4[i7].length() != 2 || !Congthuc.isNumeric(split4[i7])) {
                                if (this.mang[i][4].length() > 4) {
                                    this.mang[i][4] = "Không hiểu " + this.mang[i][2];
                                } else {
                                    this.mang[i][4] = "Không hiểu " + this.mang[i][0];
                                }
                            }
                        }
                        i5++;
                        i2 = 5;
                        i3 = -1;
                    }
                }
                if (this.mang[i][4].indexOf("Không hiểu") == -1) {
                }
            } else {
                String[] strArr60 = this.mang[i];
                StringBuilder sb9 = new StringBuilder();
                sb9.append("Không hiểu ");
                String[][] strArr61 = this.mang;
                sb9.append(strArr61[i][2].substring(0, strArr61[i][2].indexOf("xn")));
                strArr60[4] = sb9.toString();
            }
        } else if (this.mang[i][1].indexOf("bc dau") > -1) {
            if (this.mang[i][2].indexOf("bca") <= -1 || this.mang[i][2].trim().indexOf("bca") <= 0) {
                String[][] strArr62 = this.mang;
                strArr62[i][4] = strArr62[i][2].replaceFirst("bca :", "");
                String[][] strArr63 = this.mang;
                strArr63[i][4] = strArr63[i][4].replaceFirst("bca:", "");
                String[][] strArr64 = this.mang;
                strArr64[i][4] = strArr64[i][4].replaceFirst("bca", "");
                String[][] strArr65 = this.mang;
                strArr65[i][4] = Congthuc.Xu3cang(strArr65[i][4]);
            } else {
                String[] strArr66 = this.mang[i];
                StringBuilder sb10 = new StringBuilder();
                sb10.append("Không hiểu ");
                String[][] strArr67 = this.mang;
                sb10.append(strArr67[i][2].substring(0, strArr67[i][2].indexOf("bca")));
                strArr66[4] = sb10.toString();
            }
        } else if (this.mang[i][1].indexOf("bc") <= -1) {
            int i8 = 8;
            if (this.mang[i][1].indexOf("xi") > -1) {
                if (this.mang[i][2].indexOf("xi") <= -1 || this.mang[i][2].trim().indexOf("xi") <= 0) {
                    String[] split5 = this.mang[i][2].indexOf("xia") > -1 ? this.mang[i][2].split("xia") : this.mang[i][2].split("xi");
                    if (split5.length > 2) {
                        String str5 = "";
                        for (int i9 = 1; i9 < split5.length; i9++) {
                            if (split5[i9].length() > 4) {
                                str5 = str5 + Congthuc.XulySo(split5[i9]) + " ";
                                if (Congthuc.XulySo(split5[i9]).indexOf("Không hiểu") > -1) {
                                    this.mang[i][4] = "Không hiểu " + split5[i9];
                                }
                            }
                        }
                        this.mang[i][4] = str5;
                    } else if (this.mang[i][2].indexOf("xia") > -1) {
                        String[][] strArr68 = this.mang;
                        strArr68[i][4] = strArr68[i][2].replaceFirst("xia", "");
                    } else {
                        String[][] strArr69 = this.mang;
                        strArr69[i][4] = strArr69[i][2].replaceFirst("xi", "");
                    }
                    if (split5.length < 3) {
                        String[][] strArr70 = this.mang;
                        strArr70[i][4] = Congthuc.XulyXien(strArr70[i][4].trim());
                    }
                    String[] split6 = this.mang[i][4].split(" ");
                    for (int i10 = 0; i10 < split6.length; i10++) {
                        if (split6[i10].replaceAll(",", "").length() != 2 || !Congthuc.isNumeric(split6[i10].replaceAll(",", ""))) {
                            z2 = true;
                            break;
                        }
                    }
                    z2 = false;
                    if (!z2 && split6.length < 5) {
                        String[][] strArr71 = this.mang;
                        strArr71[i][4] = Congthuc.XulySo(strArr71[i][4]);
                    }
                    String[] split7 = this.mang[i][4].split(" ");
                    int i11 = 0;
                    while (i11 < split7.length) {
                        String XulySo2 = Congthuc.XulySo(split7[i11]);
                        if (XulySo2.length() < 5 || XulySo2.length() > 12 || XulySo2.indexOf("Không hiểu") > -1) {
                            if (split7[i11].length() > 4) {
                                this.mang[i][4] = "Không hiểu " + this.mang[i][2];
                            } else {
                                this.mang[i][4] = "Không hiểu " + this.mang[i][0];
                            }
                            if (this.mang[i][4].indexOf("Không hiểu") == -1) {
                                this.mang[i][4] = "";
                                String str6 = "";
                                for (int i12 = 0; i12 < split7.length; i12++) {
                                    try {
                                        str6 = Congthuc.XulySo(split7[i12]);
                                    } catch (Exception unused2) {
                                        this.mang[i][4] = "Không hiểu " + split7[i12];
                                    }
                                    if (str6.indexOf("Không hiểu") != -1) {
                                        break;
                                    }
                                    boolean z5 = false;
                                    for (String str7 : str6.split(",")) {
                                        if (str6.length() - str6.replaceAll(str7, "").length() > 2) {
                                            z5 = true;
                                        }
                                    }
                                    if (str6.length() < 5 || str6.length() > 12 || z5) {
                                        this.mang[i][4] = "Không hiểu " + this.mang[i][2];
                                        break;
                                    }
                                    this.mang[i][4] = this.mang[i][4] + Congthuc.sortXien(str6) + " ";
                                }
                            }
                        } else {
                            if (this.mang[i][1] != "xq" || XulySo2.length() >= i8) {
                                String[] split8 = XulySo2.split(",");
                                for (int i13 = 0; i13 < split8.length; i13++) {
                                    if (split8[i13].length() != 2 || !Congthuc.isNumeric(split8[i13])) {
                                        if (this.mang[i][4].length() > 4) {
                                            this.mang[i][4] = "Không hiểu " + this.mang[i][2];
                                        } else {
                                            this.mang[i][4] = "Không hiểu " + this.mang[i][0];
                                        }
                                    }
                                }
                            } else {
                                this.mang[i][4] = "Không hiểu " + this.mang[i][0];
                            }
                            i11++;
                            i8 = 8;
                        }
                    }
                    if (this.mang[i][4].indexOf("Không hiểu") == -1) {
                    }
                } else {
                    String[] strArr72 = this.mang[i];
                    StringBuilder sb11 = new StringBuilder();
                    sb11.append("Không hiểu ");
                    String[][] strArr73 = this.mang;
                    sb11.append(strArr73[i][2].substring(0, strArr73[i][2].indexOf("xi")));
                    strArr72[4] = sb11.toString();
                }
            } else if (this.mang[i][1].indexOf("xq") > -1) {
                if (this.mang[i][2].indexOf("xq") <= -1 || this.mang[i][2].trim().indexOf("xq") <= 0) {
                    if (this.mang[i][2].indexOf("xqa") > -1) {
                        split = this.mang[i][2].split("xqa");
                        this.mang[i][1] = "xq dau";
                    } else {
                        split = this.mang[i][2].split("xq");
                    }
                    if (split.length > 2) {
                        String str8 = "";
                        for (int i14 = 1; i14 < split.length; i14++) {
                            if (split[i14].length() > 4) {
                                str8 = str8 + Congthuc.XulySo(split[i14]) + " ";
                                if (Congthuc.XulySo(split[i14]).indexOf("Không hiểu") > -1) {
                                    this.mang[i][4] = "Không hiểu " + split[i14];
                                }
                            }
                        }
                        this.mang[i][4] = str8;
                    } else if (this.mang[i][2].indexOf("xqa") > -1) {
                        String[][] strArr74 = this.mang;
                        strArr74[i][4] = strArr74[i][2].replaceFirst("xqa", "");
                    } else {
                        String[][] strArr75 = this.mang;
                        strArr75[i][4] = strArr75[i][2].replaceFirst("xq", "");
                    }
                    String[][] strArr76 = this.mang;
                    strArr76[i][4] = Congthuc.XulyXien(strArr76[i][4].trim());
                    String[] split9 = this.mang[i][4].split(" ");
                    for (int i15 = 0; i15 < split9.length; i15++) {
                        if (split9[i15].replaceAll(",", "").length() != 2 || !Congthuc.isNumeric(split9[i15].replaceAll(",", ""))) {
                            z = true;
                            break;
                        }
                    }
                    z = false;
                    if (!z && split9.length < 8) {
                        String[][] strArr77 = this.mang;
                        strArr77[i][4] = Congthuc.XulySo(strArr77[i][4]);
                    }
                    String[] split10 = this.mang[i][4].split(" ");
                    for (int i16 = 0; i16 < split10.length; i16++) {
                        String XulySo3 = Congthuc.XulySo(split10[i16]);
                        if (XulySo3.length() < 8 || XulySo3.length() > 12 || XulySo3.indexOf("Không hiểu") > -1) {
                            if (split10[i16].length() > 8) {
                                this.mang[i][4] = "Không hiểu " + this.mang[i][2];
                            } else {
                                this.mang[i][4] = "Không hiểu " + this.mang[i][0];
                            }
                            if (this.mang[i][4].indexOf("Không hiểu") == -1) {
                                this.mang[i][4] = "";
                                String str9 = "";
                                for (int i17 = 0; i17 < split10.length; i17++) {
                                    try {
                                        str9 = Congthuc.XulySo(split10[i17]);
                                    } catch (Exception unused3) {
                                        this.mang[i][4] = "Không hiểu " + split10[i17];
                                    }
                                    if (str9.indexOf("Không hiểu") != -1) {
                                        break;
                                    }
                                    boolean z6 = false;
                                    for (String str10 : str9.split(",")) {
                                        if (str9.length() - str9.replaceAll(str10, "").length() > 2) {
                                            z6 = true;
                                        }
                                    }
                                    if (str9.length() < 5 || str9.length() > 12 || z6) {
                                        this.mang[i][4] = "Không hiểu " + this.mang[i][2];
                                        break;
                                    }
                                    this.mang[i][4] = this.mang[i][4] + Congthuc.sortXien(str9) + " ";
                                }
                            }
                        } else {
                            if (this.mang[i][1] != "xq" || XulySo3.length() >= 8) {
                                String[] split11 = XulySo3.split(",");
                                for (int i18 = 0; i18 < split11.length; i18++) {
                                    if (split11[i18].length() != 2 || !Congthuc.isNumeric(split11[i18])) {
                                        if (this.mang[i][4].length() > 4) {
                                            this.mang[i][4] = "Không hiểu " + this.mang[i][2];
                                        } else {
                                            this.mang[i][4] = "Không hiểu " + this.mang[i][0];
                                        }
                                    }
                                }
                            } else {
                                this.mang[i][4] = "Không hiểu " + this.mang[i][0];
                            }
                        }
                    }
                    if (this.mang[i][4].indexOf("Không hiểu") == -1) {
                    }
                } else {
                    String[] strArr78 = this.mang[i];
                    StringBuilder sb12 = new StringBuilder();
                    sb12.append("Không hiểu ");
                    String[][] strArr79 = this.mang;
                    sb12.append(strArr79[i][2].substring(0, strArr79[i][2].indexOf("xq")));
                    strArr78[4] = sb12.toString();
                }
            } else if (this.mang[i][1].indexOf("xg") > -1) {
                if (this.mang[i][2].indexOf("xg") <= -1 || this.mang[i][2].trim().indexOf("xg") <= 0) {
                    if (this.mang[i][1].indexOf("xg 2") > -1) {
                        String[][] strArr80 = this.mang;
                        strArr80[i][4] = strArr80[i][2].replaceFirst("xg 2 ", "");
                    } else if (this.mang[i][1].indexOf("xg 3") > -1) {
                        String[][] strArr81 = this.mang;
                        strArr81[i][4] = strArr81[i][2].replaceFirst("xg 3 ", "");
                    } else if (this.mang[i][1].indexOf("xg 4") > -1) {
                        String[][] strArr82 = this.mang;
                        strArr82[i][4] = strArr82[i][2].replaceFirst("xg 4 ", "");
                    }
                    ArrayList<String> arrayList = null;
                    String[][] strArr83 = this.mang;
                    strArr83[i][4] = Congthuc.XulySo(strArr83[i][4]);
                    if (this.mang[i][4].indexOf("Không hiểu") == -1) {
                        if (this.mang[i][1].indexOf("xg 2") > -1) {
                            arrayList = Congthuc.XulyXienGhep(this.mang[i][4], 2);
                        } else if (this.mang[i][1].indexOf("xg 3") > -1) {
                            arrayList = Congthuc.XulyXienGhep(this.mang[i][4], 3);
                        } else if (this.mang[i][1].indexOf("xg 4") > -1) {
                            arrayList = Congthuc.XulyXienGhep(this.mang[i][4], 4);
                        }
                        Iterator<String> it2 = arrayList.iterator();
                        while (it2.hasNext()) {
                            str = str + it2.next() + " ";
                        }
                        this.mang[i][4] = str;
                    }
                } else {
                    String[] strArr84 = this.mang[i];
                    StringBuilder sb13 = new StringBuilder();
                    sb13.append("Không hiểu ");
                    String[][] strArr85 = this.mang;
                    sb13.append(strArr85[i][2].substring(0, strArr85[i][2].indexOf("xg")));
                    strArr84[4] = sb13.toString();
                }
            }
        } else if (this.mang[i][2].indexOf("bc") <= -1 || this.mang[i][2].trim().indexOf("bc") <= 0) {
            String[][] strArr86 = this.mang;
            strArr86[i][4] = strArr86[i][2].replaceFirst("bc :", "");
            String[][] strArr87 = this.mang;
            strArr87[i][4] = strArr87[i][4].replaceFirst("bc:", "");
            String[][] strArr88 = this.mang;
            strArr88[i][4] = strArr88[i][4].replaceFirst("bc", "");
            String[][] strArr89 = this.mang;
            strArr89[i][4] = Congthuc.Xu3cang(strArr89[i][4]);
        } else {
            String[] strArr90 = this.mang[i];
            StringBuilder sb14 = new StringBuilder();
            sb14.append("Không hiểu ");
            String[][] strArr91 = this.mang;
            sb14.append(strArr91[i][2].substring(0, strArr91[i][2].indexOf("bc")));
            strArr90[4] = sb14.toString();
        }
        String[][] strArr92 = this.mang;
        if (strArr92[i][4] == null) {
            strArr92[i][4] = "Không hiểu " + this.mang[i][0].substring(0, 5);
            return;
        }
        if (strArr92[i][4].trim().length() != 10 || this.mang[i][4].indexOf("Không hiểu") <= -1) {
            return;
        }
        this.mang[i][4] = "Không hiểu " + this.mang[i][0];
    }

    private void createNotification(String str, Context context) {
        Intent intent = new Intent(context, (Class<?>) MainActivity.class);
        TaskStackBuilder create = TaskStackBuilder.create(context);
        create.addParentStack(MainActivity.class);
        create.addNextIntent(intent);
        new Intent(context, (Class<?>) MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = create.getPendingIntent(123, 134217728);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle("Ld.pro");
        builder.setContentText(str);
        builder.setContentIntent(pendingIntent);
        builder.setDefaults(1);
        builder.setVibrate(new long[]{100, 2000, 500, 2000});
        builder.setLights(-16711936, 400, 400);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel notificationChannel = new NotificationChannel("10001", "NOTIFICATION_CHANNEL_NAME", NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 2000, 500, 2000});
            builder.setChannelId("10001");
            notificationManager.createNotificationChannel(notificationChannel);
        }
        notificationManager.notify(0, builder.build());
    }

    private String xuly_Xq(String str) {
        StringBuilder sb;
        String str2;
        String[] split = str.split(",");
        String str3 = "";
        if (split.length == 3) {
            int i = 0;
            while (i < split.length - 1) {
                int i2 = i + 1;
                for (int i3 = i2; i3 < split.length; i3++) {
                    str3 = str3 + split[i] + "," + split[i3] + " ";
                }
                i = i2;
            }
            sb = new StringBuilder();
            sb.append(str3);
            sb.append(split[0]);
            sb.append(",");
            sb.append(split[1]);
            sb.append(",");
            str2 = split[2];
        } else {
            if (split.length != 4) {
                return "";
            }
            int i4 = 0;
            while (i4 < split.length - 1) {
                int i5 = i4 + 1;
                for (int i6 = i5; i6 < split.length; i6++) {
                    str3 = str3 + split[i4] + "," + split[i6] + " ";
                }
                i4 = i5;
            }
            int i7 = 0;
            while (i7 < split.length - 2) {
                int i8 = i7 + 1;
                int i9 = i8;
                while (i9 < split.length - 1) {
                    int i10 = i9 + 1;
                    for (int i11 = i10; i11 < split.length; i11++) {
                        str3 = str3 + split[i7] + "," + split[i9] + "," + split[i11] + " ";
                    }
                    i9 = i10;
                }
                i7 = i8;
            }
            sb = new StringBuilder();
            sb.append(str3);
            sb.append(split[0]);
            sb.append(",");
            sb.append(split[1]);
            sb.append(",");
            sb.append(split[2]);
            sb.append(",");
            str2 = split[3];
        }
        sb.append(str2);
        return sb.toString();
    }

    public void Another_setting() {
        QueryData("CREATE TABLE IF NOT EXISTS tbl_Setting(\n ID INTEGER PRIMARY KEY AUTOINCREMENT,\n Setting TEXT)");
        Cursor GetData = GetData("SELECT * FROM 'tbl_Setting'");
        if (GetData.getCount() != 0) {
            try {
                GetData.moveToFirst();
                JSONObject jSONObject = new JSONObject(GetData.getString(1));
                if (!jSONObject.has("canhbaodonvi")) {
                    jSONObject.put("canhbaodonvi", 0);
                }
                if (!jSONObject.has("tachxien_tinchot")) {
                    jSONObject.put("tachxien_tinchot", 0);
                }
                if (!jSONObject.has("baotinthieu")) {
                    jSONObject.put("baotinthieu", 0);
                }
                QueryData("Update tbl_Setting set Setting = '" + jSONObject.toString() + "'");
                return;
            } catch (JSONException e) {
                e.printStackTrace();
                return;
            }
        }
        JSONObject jSONObject2 = new JSONObject();
        try {
            jSONObject2.put("ap_man", 0);
            jSONObject2.put("chuyen_xien", 0);
            jSONObject2.put("lam_tron", 0);
            jSONObject2.put("gioi_han_tin", 1);
            jSONObject2.put("tin_qua_gio", 0);
            jSONObject2.put("tin_trung", 0);
            jSONObject2.put("kieu_bao_cao", 0);
            jSONObject2.put("bao_cao_so", 0);
            jSONObject2.put("tra_thuong_lo", 0);
            jSONObject2.put("canhbaodonvi", 0);
            jSONObject2.put("tudongxuly", 0);
            jSONObject2.put("tachxien_tinchot", 0);
            jSONObject2.put("baotinthieu", 0);
            QueryData("insert into tbl_Setting Values( null,'" + jSONObject2.toString() + "')");
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        GetData.close();
    }

    public void Bang_KQ() {
        QueryData("CREATE TABLE IF NOT EXISTS KetQua(  ID INTEGER PRIMARY KEY AUTOINCREMENT,  Ngay DATE DEFAULT NULL,  GDB VARCHAR(5) DEFAULT NULL,  G11 VARCHAR(5) DEFAULT NULL,  G21 VARCHAR(5) DEFAULT NULL,  G22 VARCHAR(5) DEFAULT NULL,  G31 VARCHAR(5) DEFAULT NULL,  G32 VARCHAR(5) DEFAULT NULL,  G33 VARCHAR(5) DEFAULT NULL,  G34 VARCHAR(5) DEFAULT NULL,  G35 VARCHAR(5) DEFAULT NULL,  G36 VARCHAR(5) DEFAULT NULL,  G41 VARCHAR(4) DEFAULT NULL,  G42 VARCHAR(4) DEFAULT NULL,  G43 VARCHAR(4) DEFAULT NULL,  G44 VARCHAR(4) DEFAULT NULL,  G51 VARCHAR(4) DEFAULT NULL,  G52 VARCHAR(4) DEFAULT NULL,  G53 VARCHAR(4) DEFAULT NULL,  G54 VARCHAR(4) DEFAULT NULL,  G55 VARCHAR(4) DEFAULT NULL,  G56 VARCHAR(4) DEFAULT NULL,  G61 VARCHAR(3) DEFAULT NULL,  G62 VARCHAR(3) DEFAULT NULL,  G63 VARCHAR(3) DEFAULT NULL,  G71 VARCHAR(2) DEFAULT NULL,  G72 VARCHAR(2) DEFAULT NULL,  G73 VARCHAR(2) DEFAULT NULL,  G74 VARCHAR(2) DEFAULT NULL);");
    }

    public void Creat_Chaytrang_acc() {
        QueryData("CREATE TABLE IF NOT EXISTS tbl_chaytrang_acc( \n Username VARCHAR(30) PRIMARY KEY,\n Password VARCHAR(20) NOT NULL,\n Setting TEXT NOT NULL,\n Status VARCHAR(20) DEFAULT NULL)");
    }

    public void Creat_Chaytrang_ticket() {
        QueryData("CREATE TABLE IF NOT EXISTS tbl_chaytrang_ticket( ID INTEGER PRIMARY KEY AUTOINCREMENT, \nngay_nhan DATE NOT NULL, \nCreatedAt VARCHAR(20) DEFAULT NULL, \nUsername VARCHAR(30), \nTicketNumber INTEGER DEFAULT 0, \nGameType INTEGER DEFAULT 0,\nNumbers Text DEFAULT NULL, \nPoint DOUBLE DEFAULT 0, \nAmount DOUBLE DEFAULT 0, \nCancelledAt INTEGER DEFAULT 1)");
    }

    public void Creat_SoCT() {
        QueryData("CREATE TABLE IF NOT EXISTS tbl_soctS(\n ID INTEGER PRIMARY KEY AUTOINCREMENT,\n ngay_nhan DATE NOT NULL,\n type_kh INTEGER DEFAULT 1,\n ten_kh VARCHAR(20) NOT NULL,\n so_dienthoai VARCHAR(20) NOT NULL,\n so_tin_nhan INTEGER DEFAULT 0,\n the_loai VARCHAR(4) DEFAULT NULL,\n so_chon VARCHAR(20) DEFAULT NULL,\n diem DOUBLE DEFAULT 0,\n diem_quydoi DOUBLE DEFAULT 0,\n diem_khachgiu DOUBLE DEFAULT 0,\n diem_dly_giu DOUBLE DEFAULT 0,\n diem_ton DOUBLE DEFAULT 0,\n gia DOUBLE DEFAULT 0,\n lan_an DOUBLE DEFAULT 0,\n so_nhay DOUBLE DEFAULT 0,\n tong_tien DOUBLE DEFAULT 0,\n ket_qua DOUBLE DEFAULT 0)");
        QueryData("CREATE TABLE IF NOT EXISTS tbl_chuyenthang ( ID INTEGER PRIMARY KEY AUTOINCREMENT, kh_nhan VARCHAR(20) NOT NULL, sdt_nhan VARCHAR(15) NOT NULL, kh_chuyen VARCHAR(20) NOT NULL, sdt_chuyen VARCHAR(15) NOT NULL)");
    }

    public void Creat_So_Om() {
        QueryData("CREATE TABLE IF NOT EXISTS So_om(  ID INTEGER PRIMARY KEY AUTOINCREMENT,  So VARCHAR(2) DEFAULT NULL,  Om_DeA INTEGER DEFAULT 0,  Om_DeB INTEGER DEFAULT 0,  Om_DeC INTEGER DEFAULT 0,  Om_DeD INTEGER DEFAULT 0,  Om_Lo INTEGER Default 0,  Om_Xi2 INTEGER Default 0,  Om_Xi3 INTEGER Default 0,  Om_Xi4 INTEGER Default 0,  Om_bc INTEGER Default 0,  Sphu1 VARCHAR(200) DEFAULT NULL,  Sphu2 VARCHAR(200) DEFAULT NULL)");
    }

    public void Creat_TinNhanGoc() {
        QueryData("CREATE TABLE IF NOT EXISTS tbl_tinnhanS(\n ID INTEGER PRIMARY KEY AUTOINCREMENT,\n ngay_nhan DATE NOT NULL,\n gio_nhan VARCHAR(8) NOT NULL,\n type_kh INTEGER DEFAULT 0,\n ten_kh VARCHAR(20) NOT NULL,\n so_dienthoai VARCHAR(20) NOT NULL,\n use_app VARCHAR(20) NOT NULL,\n so_tin_nhan INTEGER DEFAULT 0,\n nd_goc VARCHAR(500) DEFAULT NULL,\n nd_sua VARCHAR(500) DEFAULT NULL,\n nd_phantich VARCHAR(500) DEFAULT NULL,\n phat_hien_loi VARCHAR(100) DEFAULT NULL,\n tinh_tien INTEGER DEFAULT 0,\n ok_tn INTEGER DEFAULT 0,\n del_sms INTEGER DEFAULT 0,  phan_tich TEXT);");
    }

    public void Create_table_Chat() {
        try {
            Cursor GetData = GetData("Select * From Chat_database");
            if (GetData.getColumnCount() == 8) {
                QueryData("Drop table Chat_database");
            }
            GetData.close();
        } catch (Exception unused) {
        }
        QueryData("CREATE TABLE IF NOT EXISTS Chat_database( ID INTEGER PRIMARY KEY AUTOINCREMENT,\n ngay_nhan DATE NOT NULL,\n gio_nhan VARCHAR(8) NOT NULL,\n type_kh INTEGER DEFAULT 0,\n ten_kh VARCHAR(20) NOT NULL,\n so_dienthoai VARCHAR(20) NOT NULL,\n use_app VARCHAR(20) NOT NULL,\n nd_goc VARCHAR(500) DEFAULT NULL,\n del_sms INTEGER DEFAULT 0);");
    }

    public Cursor GetData(String str) {
        return getReadableDatabase().rawQuery(str, null);
    }

    public void Gui_Tin_Nhan(int i) {
        int i2;
        String str;
        String str2;
        String str3;
        String str4;
        String str5;
        String str6;
        int i3;
        String str7;
        String str8;
        int i4 = 0;
        String str9;
        String str10;
        String str11;
        String str12;
        String str13;
        String str14;
        String str15;
        String str16;
        String str17;
        String str18;
        String str19;
        Cursor GetData;
        Cursor cursor;
        Cursor cursor2;
        String str20;
        String str21;
        String str22;
        String str23;
        String str24;
        String str25;
        String str26;
        int i5;
        String str27;
        String str28;
        String str29;
        String str30;
        String str31;
        Object obj;
        final String str32;
        StringBuilder sb;
        Cursor GetData2;
        String str33 = "";
        String str34;
        Cursor cursor3;
        String str35;
        StringBuilder sb2;
        Cursor cursor4;
        String str36;
        int i6 = 0;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getDefault());
        simpleDateFormat2.setTimeZone(TimeZone.getDefault());
        String format = simpleDateFormat.format(calendar.getTime());
        String format2 = simpleDateFormat2.format(calendar.getTime());
        Cursor GetData3 = GetData("Select * From tbl_tinnhanS WHERE ID = " + i);
        GetData3.moveToFirst();
        int i7 = GetData3.getInt(7);
        final String string = GetData3.getString(5);
        String string2 = GetData3.getString(1);
        Cursor GetData4 = GetData("Select * From tbl_kh_new Where sdt = '" + GetData3.getString(5) + "'");
        GetData4.moveToFirst();
        try {
            JSONObject jSONObject = new JSONObject(GetData4.getString(5));
            this.json = jSONObject;
            JSONObject jSONObject2 = jSONObject.getJSONObject("caidat_tg");
            this.caidat_tg = jSONObject2;
            i2 = jSONObject2.getInt("ok_tin");
        } catch (JSONException e) {
            e.printStackTrace();
            i2 = 0;
        }
        if (GetData3.getInt(13) != 1) {
            str = "sms";
            str2 = "\n";
            str3 = "' AND so_tin_nhan = ";
            str4 = "','";
            str5 = "',1)";
        } else if (i2 == 5) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append("Ok Tin ");
            sb3.append(i7);
            sb3.append("\n");
            str2 = "\n";
            sb3.append(GetData3.getString(8));
            final String sb4 = sb3.toString();
            if (GetData3.getString(6).indexOf("sms") > -1) {
                SendSMS(string, sb4);
                str = "sms";
            } else {
                str = "sms";
                if (GetData3.getString(6).indexOf("TL") > -1) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            MainActivity.sendMessage(Long.parseLong(string), sb4);
                        }
                    });
                } else {
                    try {
                        JSONObject jSONObject3 = new JSONObject(MainActivity.json_Tinnhan.getString(string));
                        if (jSONObject3.getInt("Time") > 3) {
                            new NotificationNewReader().NotificationWearReader(GetData3.getString(5), sb4);
                        } else {
                            jSONObject3.put(sb4, "OK");
                            MainActivity.json_Tinnhan.put(string, jSONObject3);
                        }
                    } catch (Exception unused) {
                        new NotificationNewReader().NotificationWearReader(GetData3.getString(5), sb4);
                    }
                    StringBuilder sb5 = new StringBuilder();
                    sb5.append("Insert into Chat_database Values( null,'");
                    sb5.append(format);
                    sb5.append("', '");
                    sb5.append(format2);
                    sb5.append("', 2, '");
                    sb5.append(GetData3.getString(4));
                    sb5.append("', '");
                    sb5.append(GetData3.getString(5));
                    sb5.append("', '");
                    sb5.append(GetData3.getString(6));
                    str4 = "','";
                    sb5.append(str4);
                    sb5.append(sb4);
                    str5 = "',1)";
                    sb5.append(str5);
                    QueryData(sb5.toString());
                    StringBuilder sb6 = new StringBuilder();
                    sb6.append("Update tbl_tinnhanS set ok_tn = 0 WHERE ngay_nhan = '");
                    sb6.append(string2);
                    sb6.append("' AND so_dienthoai = '");
                    sb6.append(string);
                    str3 = "' AND so_tin_nhan = ";
                    sb6.append(str3);
                    sb6.append(i7);
                    QueryData(sb6.toString());
                }
            }
            str4 = "','";
            str5 = "',1)";
            StringBuilder sb62 = new StringBuilder();
            sb62.append("Update tbl_tinnhanS set ok_tn = 0 WHERE ngay_nhan = '");
            sb62.append(string2);
            sb62.append("' AND so_dienthoai = '");
            sb62.append(string);
            str3 = "' AND so_tin_nhan = ";
            sb62.append(str3);
            sb62.append(i7);
            QueryData(sb62.toString());
        } else {
            str = "sms";
            str2 = "\n";
            str3 = "' AND so_tin_nhan = ";
            str4 = "','";
            str5 = "',1)";
            if (i2 == 4) {
                final String str37 = "Ok Tin " + i7;
                i3 = i2;
                str7 = "Ok Tin ";
                if (GetData3.getString(6).indexOf(str) > -1) {
                    SendSMS(string, str37);
                } else if (GetData3.getString(6).indexOf("TL") > -1) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            MainActivity.sendMessage(Long.parseLong(string), str37);
                        }
                    });
                } else {
                    try {
                        JSONObject jSONObject4 = new JSONObject(MainActivity.json_Tinnhan.getString(string));
                        str6 = "Time";
                        try {
                            if (jSONObject4.getInt("Time") > 3) {
                                new NotificationNewReader().NotificationWearReader(GetData3.getString(5), str37);
                            } else {
                                jSONObject4.put(str37, "OK");
                                MainActivity.json_Tinnhan.put(string, jSONObject4);
                            }
                            i6 = 5;
                        } catch (Exception unused2) {
                            i6 = 5;
                            new NotificationNewReader().NotificationWearReader(GetData3.getString(5), str37);
                            QueryData("Insert into Chat_database Values( null,'" + format + "', '" + format2 + "', 2, '" + GetData3.getString(i6) + "', '" + string + "', '" + GetData3.getString(6) + str4 + str37 + str5);
                            StringBuilder sb7 = new StringBuilder();
                            sb7.append("Update tbl_tinnhanS set ok_tn = 0 WHERE ngay_nhan = '");
                            str8 = string2;
                            sb7.append(str8);
                            sb7.append("' AND so_dienthoai = '");
                            sb7.append(string);
                            sb7.append(str3);
                            sb7.append(i7);
                            QueryData(sb7.toString());
                            if (GetData3.getString(11).indexOf("ok") != 0) {
                            }
                            i4 = i7;
                            str9 = "Tin ";
                            str10 = str4;
                            str11 = str3;
                            str12 = "', 2, '";
                            str13 = str;
                            str14 = string;
                            str15 = str8;
                            str16 = "Insert into Chat_database Values( null,'";
                            str17 = format2;
                            str18 = format;
                            str19 = "' AND so_dienthoai = '";
                            StringBuilder sb8 = new StringBuilder();
                            sb8.append("Select * From tbl_tinnhanS WHERE ngay_nhan = '");
                            String str38 = str15;
                            sb8.append(str38);
                            sb8.append(str19);
                            sb8.append(str14);
                            sb8.append(str11);
                            int i8 = i4;
                            sb8.append(i8);
                            Cursor GetData5 = GetData(sb8.toString());
                            GetData5.moveToFirst();
                            StringBuilder sb9 = new StringBuilder();
                            String str39 = str19;
                            sb9.append("Select * From tbl_chuyenthang WHERE sdt_nhan = '");
                            sb9.append(str14);
                            sb9.append("'");
                            GetData = GetData(sb9.toString());
                            GetData.moveToFirst();
                            Cursor GetData6 = GetData("Select Om_Xi3 FROM so_Om WHERE id = 13");
                            GetData6.moveToFirst();
                            if (GetData.getCount() > 0) {
                            }
                            cursor = GetData5;
                            cursor2 = GetData6;
                            TralaiSO(i);
                            if (cursor != null) {
                            }
                            if (cursor2 != null) {
                            }
                            if (GetData3 != null) {
                            }
                            if (GetData4 != null) {
                            }
                            if (GetData == null) {
                            }
                        }
                    } catch (Exception unused3) {
                        str6 = "Time";
                    }
                    QueryData("Insert into Chat_database Values( null,'" + format + "', '" + format2 + "', 2, '" + GetData3.getString(i6) + "', '" + string + "', '" + GetData3.getString(6) + str4 + str37 + str5);
                    StringBuilder sb72 = new StringBuilder();
                    sb72.append("Update tbl_tinnhanS set ok_tn = 0 WHERE ngay_nhan = '");
                    str8 = string2;
                    sb72.append(str8);
                    sb72.append("' AND so_dienthoai = '");
                    sb72.append(string);
                    sb72.append(str3);
                    sb72.append(i7);
                    QueryData(sb72.toString());
                    if (GetData3.getString(11).indexOf("ok") != 0 && GetData3.getString(11).length() == 2 && GetData3.getInt(13) == 1) {
                        if (i3 == 0) {
                            StringBuilder sb10 = new StringBuilder();
                            sb10.append(str7);
                            sb10.append(i7);
                            sb10.append(str2);
                            str22 = "Tin ";
                            str23 = str3;
                            sb10.append(GetData3.getString(10).replaceAll("de dit db", "de"));
                            final String sb11 = sb10.toString();
                            str13 = str;
                            if (GetData3.getString(6).indexOf(str13) > -1) {
                                SendSMS(string, sb11);
                                str24 = str8;
                            } else {
                                str24 = str8;
                                if (GetData3.getString(6).indexOf("TL") > -1) {
                                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                                        @Override
                                        public void run() {
                                            MainActivity.sendMessage(Long.parseLong(string), sb11);
                                        }
                                    });
                                } else {
                                    try {
                                        JSONObject jSONObject5 = new JSONObject(MainActivity.json_Tinnhan.getString(string));
                                        String str40 = str6;
                                        try {
                                            str6 = str40;
                                            if (jSONObject5.getInt(str40) > 3) {
                                                new NotificationNewReader().NotificationWearReader(GetData3.getString(5), sb11);
                                            } else {
                                                jSONObject5.put(sb11, "OK");
                                                MainActivity.json_Tinnhan.put(string, jSONObject5);
                                            }
                                        } catch (Exception unused4) {
                                            str6 = str40;
                                            new NotificationNewReader().NotificationWearReader(GetData3.getString(5), sb11);
                                            QueryData("Insert into Chat_database Values( null,'" + format + "', '" + format2 + "', 2, '" + GetData3.getString(4) + "', '" + GetData3.getString(5) + "', '" + GetData3.getString(6) + str4 + sb11 + str5);
                                            str30 = str5;
                                            i5 = i7;
                                            str28 = str6;
                                            str25 = format2;
                                            obj = "OK";
                                            str10 = str4;
                                            str31 = "', 2, '";
                                            StringBuilder sb12 = new StringBuilder();
                                            String str41 = str28;
                                            sb12.append("Select * From tbl_chuyenthang WHERE sdt_nhan = '");
                                            sb12.append(string);
                                            sb12.append("'");
                                            GetData2 = GetData(sb12.toString());
                                            GetData2.moveToFirst();
                                            if (GetData2.getCount() <= 0) {
                                            }
                                            str15 = str24;
                                            str18 = format;
                                            str34 = "' AND so_dienthoai = '";
                                            str12 = str31;
                                            String str42 = str23;
                                            str9 = str22;
                                            str11 = str42;
                                            if (GetData3.getInt(3) == 1) {
                                            }
                                            str14 = str33;
                                            str19 = str34;
                                            StringBuilder sb82 = new StringBuilder();
                                            sb82.append("Select * From tbl_tinnhanS WHERE ngay_nhan = '");
                                            String str382 = str15;
                                            sb82.append(str382);
                                            sb82.append(str19);
                                            sb82.append(str14);
                                            sb82.append(str11);
                                            int i82 = i4;
                                            sb82.append(i82);
                                            Cursor GetData52 = GetData(sb82.toString());
                                            GetData52.moveToFirst();
                                            StringBuilder sb92 = new StringBuilder();
                                            String str392 = str19;
                                            sb92.append("Select * From tbl_chuyenthang WHERE sdt_nhan = '");
                                            sb92.append(str14);
                                            sb92.append("'");
                                            GetData = GetData(sb92.toString());
                                            GetData.moveToFirst();
                                            Cursor GetData62 = GetData("Select Om_Xi3 FROM so_Om WHERE id = 13");
                                            GetData62.moveToFirst();
                                            if (GetData.getCount() > 0) {
                                            }
                                            cursor = GetData52;
                                            cursor2 = GetData62;
                                            TralaiSO(i);
                                            if (cursor != null) {
                                            }
                                            if (cursor2 != null) {
                                            }
                                            if (GetData3 != null) {
                                            }
                                            if (GetData4 != null) {
                                            }
                                            if (GetData == null) {
                                            }
                                        }
                                    } catch (Exception unused5) {
                                    }
                                    QueryData("Insert into Chat_database Values( null,'" + format + "', '" + format2 + "', 2, '" + GetData3.getString(4) + "', '" + GetData3.getString(5) + "', '" + GetData3.getString(6) + str4 + sb11 + str5);
                                }
                            }
                            str30 = str5;
                            i5 = i7;
                            str28 = str6;
                            str25 = format2;
                            obj = "OK";
                            str10 = str4;
                            str31 = "', 2, '";
                        } else {
                            String str43 = str7;
                            int i9 = i3;
                            str22 = "Tin ";
                            str23 = str3;
                            String str44 = str2;
                            str13 = str;
                            str24 = str8;
                            if (i9 != 1) {
                                str25 = format2;
                                str26 = "', 2, '";
                                i5 = i7;
                                str27 = str5;
                                str28 = str6;
                                str29 = str4;
                                if (i9 != 3) {
                                    str30 = str27;
                                    str31 = str26;
                                    obj = "OK";
                                    str10 = str29;
                                } else if (GetData3.getString(10).indexOf("Bỏ ") == -1) {
                                    str32 = str43 + i5 + str44 + GetData3.getString(8);
                                    if (GetData3.getString(6).indexOf(str13) > -1) {
                                        SendSMS(string, str32);
                                    } else if (GetData3.getString(6).indexOf("TL") > -1) {
                                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                                            @Override
                                            public void run() {
                                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        new MainActivity();
                                                        MainActivity.sendMessage(Long.parseLong(string), str32);
                                                    }
                                                });
                                            }
                                        });
                                    } else {
                                        try {
                                            JSONObject jSONObject6 = new JSONObject(MainActivity.json_Tinnhan.getString(string));
                                            if (jSONObject6.getInt(str28) > 3) {
                                                new NotificationNewReader().NotificationWearReader(GetData3.getString(5), str32);
                                            } else {
                                                jSONObject6.put(str32, "OK");
                                                MainActivity.json_Tinnhan.put(string, jSONObject6);
                                            }
                                        } catch (Exception unused6) {
                                            new NotificationNewReader().NotificationWearReader(GetData3.getString(5), str32);
                                        }
                                        sb = new StringBuilder();
                                        sb.append("Insert into Chat_database Values( null,'");
                                        sb.append(format);
                                        sb.append("', '");
                                        sb.append(str25);
                                        sb.append(str26);
                                        sb.append(GetData3.getString(4));
                                        sb.append("', '");
                                        sb.append(GetData3.getString(5));
                                        sb.append("', '");
                                        sb.append(GetData3.getString(6));
                                        str4 = str29;
                                        sb.append(str4);
                                        sb.append(str32);
                                        str30 = str27;
                                        sb.append(str30);
                                        QueryData(sb.toString());
                                    }
                                    str4 = str29;
                                    str30 = str27;
                                } else {
                                    final String str45 = GetData3.getString(10).substring(0, GetData3.getString(10).indexOf(str44) - 1) + "\nOK Tin" + i5 + str44 + GetData3.getString(8);
                                    if (GetData3.getString(6).indexOf(str13) > -1) {
                                        SendSMS(string, str45);
                                    } else if (GetData3.getString(6).indexOf("TL") > -1) {
                                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                                            @Override
                                            public void run() {
                                                new MainActivity();
                                                MainActivity.sendMessage(Long.parseLong(string), str45);
                                            }
                                        });
                                    } else {
                                        try {
                                            JSONObject jSONObject7 = new JSONObject(MainActivity.json_Tinnhan.getString(string));
                                            if (jSONObject7.getInt(str28) > 3) {
                                                new NotificationNewReader().NotificationWearReader(GetData3.getString(5), str45);
                                            } else {
                                                jSONObject7.put(str45, "OK");
                                                MainActivity.json_Tinnhan.put(string, jSONObject7);
                                            }
                                        } catch (Exception unused7) {
                                            new NotificationNewReader().NotificationWearReader(GetData3.getString(5), str45);
                                        }
                                        StringBuilder sb13 = new StringBuilder();
                                        sb13.append("Insert into Chat_database Values( null,'");
                                        sb13.append(format);
                                        sb13.append("', '");
                                        str25 = str25;
                                        sb13.append(str25);
                                        str31 = str26;
                                        sb13.append(str31);
                                        obj = "OK";
                                        sb13.append(GetData3.getString(4));
                                        sb13.append("', '");
                                        sb13.append(GetData3.getString(5));
                                        sb13.append("', '");
                                        sb13.append(GetData3.getString(6));
                                        str10 = str29;
                                        sb13.append(str10);
                                        sb13.append(str45);
                                        str30 = str27;
                                        sb13.append(str30);
                                        QueryData(sb13.toString());
                                    }
                                    str30 = str27;
                                    str31 = str26;
                                    str25 = str25;
                                    obj = "OK";
                                    str10 = str29;
                                }
                            } else if (GetData3.getString(10).indexOf("Bỏ ") == -1) {
                                final String str46 = str43 + i7;
                                if (GetData3.getString(6).indexOf(str13) > -1) {
                                    SendSMS(string, str46);
                                } else if (GetData3.getString(6).indexOf("TL") > -1) {
                                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                                        @Override
                                        public void run() {
                                            MainActivity.sendMessage(Long.parseLong(string), str46);
                                        }
                                    });
                                } else {
                                    try {
                                        JSONObject jSONObject8 = new JSONObject(MainActivity.json_Tinnhan.getString(string));
                                        String str47 = str6;
                                        try {
                                            str6 = str47;
                                            if (jSONObject8.getInt(str47) > 3) {
                                                new NotificationNewReader().NotificationWearReader(GetData3.getString(5), str46);
                                            } else {
                                                jSONObject8.put(str46, "OK");
                                                MainActivity.json_Tinnhan.put(string, jSONObject8);
                                            }
                                        } catch (Exception unused8) {
                                            str6 = str47;
                                            new NotificationNewReader().NotificationWearReader(GetData3.getString(5), str46);
                                            StringBuilder sb14 = new StringBuilder();
                                            sb14.append("Insert into Chat_database Values( null,'");
                                            sb14.append(format);
                                            sb14.append("', '");
                                            str25 = format2;
                                            sb14.append(str25);
                                            sb14.append("', 2, '");
                                            str26 = "', 2, '";
                                            sb14.append(GetData3.getString(4));
                                            sb14.append("', '");
                                            sb14.append(GetData3.getString(5));
                                            sb14.append("', '");
                                            sb14.append(GetData3.getString(6));
                                            sb14.append(str4);
                                            sb14.append(str46);
                                            sb14.append(str5);
                                            QueryData(sb14.toString());
                                            str30 = str5;
                                            str28 = str6;
                                            i5 = i7;
                                            String str48 = str26;
                                            obj = "OK";
                                            str10 = str4;
                                            str31 = str48;
                                            StringBuilder sb122 = new StringBuilder();
                                            String str412 = str28;
                                            sb122.append("Select * From tbl_chuyenthang WHERE sdt_nhan = '");
                                            sb122.append(string);
                                            sb122.append("'");
                                            GetData2 = GetData(sb122.toString());
                                            GetData2.moveToFirst();
                                            if (GetData2.getCount() <= 0) {
                                            }
                                            str15 = str24;
                                            str18 = format;
                                            str34 = "' AND so_dienthoai = '";
                                            str12 = str31;
                                            String str422 = str23;
                                            str9 = str22;
                                            str11 = str422;
                                            if (GetData3.getInt(3) == 1) {
                                            }
                                            str14 = str33;
                                            str19 = str34;
                                            StringBuilder sb822 = new StringBuilder();
                                            sb822.append("Select * From tbl_tinnhanS WHERE ngay_nhan = '");
                                            String str3822 = str15;
                                            sb822.append(str3822);
                                            sb822.append(str19);
                                            sb822.append(str14);
                                            sb822.append(str11);
                                            int i822 = i4;
                                            sb822.append(i822);
                                            Cursor GetData522 = GetData(sb822.toString());
                                            GetData522.moveToFirst();
                                            StringBuilder sb922 = new StringBuilder();
                                            String str3922 = str19;
                                            sb922.append("Select * From tbl_chuyenthang WHERE sdt_nhan = '");
                                            sb922.append(str14);
                                            sb922.append("'");
                                            GetData = GetData(sb922.toString());
                                            GetData.moveToFirst();
                                            Cursor GetData622 = GetData("Select Om_Xi3 FROM so_Om WHERE id = 13");
                                            GetData622.moveToFirst();
                                            if (GetData.getCount() > 0) {
                                            }
                                            cursor = GetData522;
                                            cursor2 = GetData622;
                                            TralaiSO(i);
                                            if (cursor != null) {
                                            }
                                            if (cursor2 != null) {
                                            }
                                            if (GetData3 != null) {
                                            }
                                            if (GetData4 != null) {
                                            }
                                            if (GetData == null) {
                                            }
                                        }
                                    } catch (Exception unused9) {
                                    }
                                    StringBuilder sb142 = new StringBuilder();
                                    sb142.append("Insert into Chat_database Values( null,'");
                                    sb142.append(format);
                                    sb142.append("', '");
                                    str25 = format2;
                                    sb142.append(str25);
                                    sb142.append("', 2, '");
                                    str26 = "', 2, '";
                                    sb142.append(GetData3.getString(4));
                                    sb142.append("', '");
                                    sb142.append(GetData3.getString(5));
                                    sb142.append("', '");
                                    sb142.append(GetData3.getString(6));
                                    sb142.append(str4);
                                    sb142.append(str46);
                                    sb142.append(str5);
                                    QueryData(sb142.toString());
                                    str30 = str5;
                                    str28 = str6;
                                    i5 = i7;
                                }
                                str25 = format2;
                                str26 = "', 2, '";
                                str30 = str5;
                                str28 = str6;
                                i5 = i7;
                            } else {
                                str25 = format2;
                                str26 = "', 2, '";
                                StringBuilder sb15 = new StringBuilder();
                                sb15.append(GetData3.getString(10).substring(0, GetData3.getString(10).indexOf(str44) - 1));
                                sb15.append("\nOk tin ");
                                i5 = i7;
                                sb15.append(i5);
                                sb15.append(": ");
                                str32 = sb15.toString();
                                if (GetData3.getString(6).indexOf(str13) > -1) {
                                    SendSMS(string, str32);
                                    str27 = str5;
                                } else {
                                    str27 = str5;
                                    if (GetData3.getString(6).indexOf("TL") > -1) {
                                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                                            @Override
                                            public void run() {
                                                new MainActivity();
                                                MainActivity.sendMessage(Long.parseLong(string), str32);
                                            }
                                        });
                                    } else {
                                        try {
                                            JSONObject jSONObject9 = new JSONObject(MainActivity.json_Tinnhan.getString(string));
                                            String str49 = str6;
                                            try {
                                                str6 = str49;
                                                if (jSONObject9.getInt(str49) > 3) {
                                                    new NotificationNewReader().NotificationWearReader(GetData3.getString(5), str32);
                                                } else {
                                                    jSONObject9.put(str32, "OK");
                                                    MainActivity.json_Tinnhan.put(string, jSONObject9);
                                                }
                                            } catch (Exception unused10) {
                                                str6 = str49;
                                                new NotificationNewReader().NotificationWearReader(GetData3.getString(5), str32);
                                                sb = new StringBuilder();
                                                str28 = str6;
                                                str29 = str4;
                                                sb.append("Insert into Chat_database Values( null,'");
                                                sb.append(format);
                                                sb.append("', '");
                                                sb.append(str25);
                                                sb.append(str26);
                                                sb.append(GetData3.getString(4));
                                                sb.append("', '");
                                                sb.append(GetData3.getString(5));
                                                sb.append("', '");
                                                sb.append(GetData3.getString(6));
                                                str4 = str29;
                                                sb.append(str4);
                                                sb.append(str32);
                                                str30 = str27;
                                                sb.append(str30);
                                                QueryData(sb.toString());
                                                String str482 = str26;
                                                obj = "OK";
                                                str10 = str4;
                                                str31 = str482;
                                                StringBuilder sb1222 = new StringBuilder();
                                                String str4122 = str28;
                                                sb1222.append("Select * From tbl_chuyenthang WHERE sdt_nhan = '");
                                                sb1222.append(string);
                                                sb1222.append("'");
                                                GetData2 = GetData(sb1222.toString());
                                                GetData2.moveToFirst();
                                                if (GetData2.getCount() <= 0) {
                                                }
                                                str15 = str24;
                                                str18 = format;
                                                str34 = "' AND so_dienthoai = '";
                                                str12 = str31;
                                                String str4222 = str23;
                                                str9 = str22;
                                                str11 = str4222;
                                                if (GetData3.getInt(3) == 1) {
                                                }
                                                str14 = str33;
                                                str19 = str34;
                                                StringBuilder sb8222 = new StringBuilder();
                                                sb8222.append("Select * From tbl_tinnhanS WHERE ngay_nhan = '");
                                                String str38222 = str15;
                                                sb8222.append(str38222);
                                                sb8222.append(str19);
                                                sb8222.append(str14);
                                                sb8222.append(str11);
                                                int i8222 = i4;
                                                sb8222.append(i8222);
                                                Cursor GetData5222 = GetData(sb8222.toString());
                                                GetData5222.moveToFirst();
                                                StringBuilder sb9222 = new StringBuilder();
                                                String str39222 = str19;
                                                sb9222.append("Select * From tbl_chuyenthang WHERE sdt_nhan = '");
                                                sb9222.append(str14);
                                                sb9222.append("'");
                                                GetData = GetData(sb9222.toString());
                                                GetData.moveToFirst();
                                                Cursor GetData6222 = GetData("Select Om_Xi3 FROM so_Om WHERE id = 13");
                                                GetData6222.moveToFirst();
                                                if (GetData.getCount() > 0) {
                                                }
                                                cursor = GetData5222;
                                                cursor2 = GetData6222;
                                                TralaiSO(i);
                                                if (cursor != null) {
                                                }
                                                if (cursor2 != null) {
                                                }
                                                if (GetData3 != null) {
                                                }
                                                if (GetData4 != null) {
                                                }
                                                if (GetData == null) {
                                                }
                                            }
                                        } catch (Exception unused11) {
                                        }
                                        sb = new StringBuilder();
                                        str28 = str6;
                                        str29 = str4;
                                        sb.append("Insert into Chat_database Values( null,'");
                                        sb.append(format);
                                        sb.append("', '");
                                        sb.append(str25);
                                        sb.append(str26);
                                        sb.append(GetData3.getString(4));
                                        sb.append("', '");
                                        sb.append(GetData3.getString(5));
                                        sb.append("', '");
                                        sb.append(GetData3.getString(6));
                                        str4 = str29;
                                        sb.append(str4);
                                        sb.append(str32);
                                        str30 = str27;
                                        sb.append(str30);
                                        QueryData(sb.toString());
                                    }
                                }
                                str28 = str6;
                                str29 = str4;
                                str4 = str29;
                                str30 = str27;
                            }
                            String str4822 = str26;
                            obj = "OK";
                            str10 = str4;
                            str31 = str4822;
                        }
                        StringBuilder sb12222 = new StringBuilder();
                        String str41222 = str28;
                        sb12222.append("Select * From tbl_chuyenthang WHERE sdt_nhan = '");
                        sb12222.append(string);
                        sb12222.append("'");
                        GetData2 = GetData(sb12222.toString());
                        GetData2.moveToFirst();
                        if (GetData2.getCount() <= 0) {
                            String str50 = str30;
                            if (GetData3.getInt(14) == 1) {
                                StringBuilder sb16 = new StringBuilder();
                                sb16.append("Select max(so_tin_nhan) From tbl_tinnhanS WHERE so_dienthoai = '");
                                String str51 = str31;
                                sb16.append(GetData2.getString(4));
                                sb16.append("' AND ngay_nhan = '");
                                String str52 = str24;
                                sb16.append(str52);
                                sb16.append("' And type_kh = 2");
                                Cursor GetData7 = GetData(sb16.toString());
                                GetData7.moveToFirst();
                                int i10 = GetData7.getInt(0) + 1;
                                String str53 = str25;
                                String str54 = GetData2.getString(3).indexOf("TL") > -1 ? "TL" : GetData2.getString(3).indexOf("ZL") > -1 ? "ZL" : GetData2.getString(3).indexOf("VB") > -1 ? "VB" : GetData2.getString(3).indexOf("WA") > -1 ? "WA" : str13;
                                QueryData("Insert Into tbl_tinnhanS values (null, '" + GetData3.getString(1) + "', '" + GetData3.getString(2) + "',2, '" + GetData2.getString(3) + "', '" + GetData2.getString(4) + "', '" + str54 + "', " + i10 + ", '" + GetData3.getString(8) + "', '" + GetData3.getString(9) + str10 + GetData3.getString(10) + "', 'ok',0,0,1, '" + GetData3.getString(15) + "')");
                                StringBuilder sb17 = new StringBuilder();
                                sb17.append("Select * From tbl_tinnhanS WHERE ngay_nhan = '");
                                sb17.append(GetData3.getString(1));
                                sb17.append("' AND so_dienthoai = '");
                                sb17.append(GetData2.getString(4));
                                String str55 = str23;
                                sb17.append(str55);
                                sb17.append(i10);
                                sb17.append(" AND type_kh = 2");
                                Cursor GetData8 = GetData(sb17.toString());
                                GetData8.moveToFirst();
                                StringBuilder sb18 = new StringBuilder();
                                String str56 = str54;
                                sb18.append("Update tbl_tinnhanS set del_sms = 0 WHERE ngay_nhan = '");
                                sb18.append(str52);
                                sb18.append("' AND so_dienthoai = '");
                                sb18.append(string);
                                sb18.append(str55);
                                sb18.append(i5);
                                QueryData(sb18.toString());
                                NhapSoChiTiet(GetData8.getInt(0));
                                Cursor GetData9 = GetData("Select Om_Xi3 FROM so_Om WHERE id = 13");
                                GetData9.moveToFirst();
                                if (GetData9.getInt(0) == 0) {
                                    StringBuilder sb19 = new StringBuilder();
                                    i4 = i5;
                                    String str57 = str22;
                                    sb19.append(str57);
                                    sb19.append(i10);
                                    sb19.append(":\n");
                                    str11 = str55;
                                    sb19.append(GetData3.getString(8));
                                    final String sb20 = sb19.toString();
                                    if (GetData8.getString(6).indexOf(str13) > -1) {
                                        SendSMS(GetData2.getString(4), sb20);
                                    } else if (GetData8.getString(6).indexOf("TL") > -1) {
                                        Cursor finalGetData = GetData2;
                                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                                            @Override
                                            public void run() {
                                                MainActivity.sendMessage(finalGetData.getLong(4), sb20);
                                            }
                                        });
                                    } else {
                                        new NotificationNewReader().NotificationWearReader(GetData2.getString(4), sb20);
                                        StringBuilder sb21 = new StringBuilder();
                                        str16 = "Insert into Chat_database Values( null,'";
                                        sb21.append(str16);
                                        str18 = format;
                                        sb21.append(str18);
                                        sb21.append("', '");
                                        str33 = string;
                                        str17 = str53;
                                        sb21.append(str17);
                                        str34 = "' AND so_dienthoai = '";
                                        str12 = str51;
                                        sb21.append(str12);
                                        str15 = str52;
                                        cursor4 = GetData9;
                                        sb21.append(GetData2.getString(3));
                                        sb21.append("', '");
                                        sb21.append(GetData2.getString(4));
                                        sb21.append(str10);
                                        sb21.append(str56);
                                        sb21.append(str10);
                                        sb21.append(sb20);
                                        str36 = str50;
                                        sb21.append(str36);
                                        QueryData(sb21.toString());
                                        str9 = str57;
                                        str5 = str36;
                                    }
                                    str18 = format;
                                    str16 = "Insert into Chat_database Values( null,'";
                                    str33 = string;
                                    cursor4 = GetData9;
                                    str36 = str50;
                                    str17 = str53;
                                    str34 = "' AND so_dienthoai = '";
                                    str12 = str51;
                                    str15 = str52;
                                    str9 = str57;
                                    str5 = str36;
                                } else {
                                    i4 = i5;
                                    String str58 = str22;
                                    str16 = "Insert into Chat_database Values( null,'";
                                    str11 = str55;
                                    cursor4 = GetData9;
                                    str18 = format;
                                    str33 = string;
                                    str17 = str53;
                                    str34 = "' AND so_dienthoai = '";
                                    str12 = str51;
                                    str15 = str52;
                                    final String str59 = str58 + i10 + ":\n" + GetData3.getString(9);
                                    str9 = str58;
                                    if (GetData8.getString(6).indexOf(str13) > -1) {
                                        SendSMS(GetData2.getString(4), str59);
                                    } else if (GetData8.getString(6).indexOf("TL") > -1) {
                                        Cursor finalGetData1 = GetData2;
                                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                                            @Override
                                            public void run() {
                                                new MainActivity();
                                                MainActivity.sendMessage(finalGetData1.getLong(4), str59);
                                            }
                                        });
                                    } else {
                                        new NotificationNewReader().NotificationWearReader(GetData2.getString(4), str59);
                                        StringBuilder sb22 = new StringBuilder();
                                        sb22.append(str16);
                                        sb22.append(str18);
                                        sb22.append("', '");
                                        sb22.append(str17);
                                        sb22.append(str12);
                                        sb22.append(GetData2.getString(3));
                                        sb22.append("', '");
                                        sb22.append(GetData2.getString(4));
                                        sb22.append(str10);
                                        sb22.append(str56);
                                        sb22.append(str10);
                                        sb22.append(str59);
                                        str5 = str50;
                                        sb22.append(str5);
                                        QueryData(sb22.toString());
                                    }
                                    str5 = str50;
                                }
                                if (cursor4 != null && !cursor4.isClosed()) {
                                    cursor4.close();
                                }
                                if (GetData7 != null && !GetData7.isClosed()) {
                                    GetData7.close();
                                }
                                try {
                                    if (GetData3.getInt(3) == 1 || MainActivity.jSon_Setting.getInt("baotinthieu") <= 0) {
                                        str14 = str33;
                                    } else {
                                        StringBuilder sb23 = new StringBuilder();
                                        sb23.append("Select * From tbl_tinnhanS WHERE ngay_nhan = '");
                                        String str60 = str15;
                                        sb23.append(str60);
                                        String str61 = str34;
                                        sb23.append(str61);
                                        str14 = str33;
                                        sb23.append(str14);
                                        sb23.append("' AND type_kh = 1 ORDER BY so_tin_nhan");
                                        Cursor GetData10 = GetData(sb23.toString());
                                        JSONObject jSONObject10 = new JSONObject();
                                        str34 = str61;
                                        int i11 = 0;
                                        while (GetData10.moveToNext()) {
                                            sb2 = new StringBuilder();
                                            str15 = str60;
                                            try {
                                                sb2.append(GetData10.getString(7));
                                                sb2.append("-");
                                                jSONObject10.put(sb2.toString(), GetData10.getString(7));
                                                i11 = GetData10.getInt(7);
                                                str60 = str15;
                                            } catch (JSONException e3) {
                                                cursor3 = GetData10;
                                                e3.printStackTrace();
                                                if (cursor3 != null) {
                                                }
                                                str19 = str34;
                                                StringBuilder sb822222 = new StringBuilder();
                                                sb822222.append("Select * From tbl_tinnhanS WHERE ngay_nhan = '");
                                                String str3822222 = str15;
                                                sb822222.append(str3822222);
                                                sb822222.append(str19);
                                                sb822222.append(str14);
                                                sb822222.append(str11);
                                                int i822222 = i4;
                                                sb822222.append(i822222);
                                                Cursor GetData522222 = GetData(sb822222.toString());
                                                GetData522222.moveToFirst();
                                                StringBuilder sb922222 = new StringBuilder();
                                                String str3922222 = str19;
                                                sb922222.append("Select * From tbl_chuyenthang WHERE sdt_nhan = '");
                                                sb922222.append(str14);
                                                sb922222.append("'");
                                                GetData = GetData(sb922222.toString());
                                                GetData.moveToFirst();
                                                Cursor GetData622222 = GetData("Select Om_Xi3 FROM so_Om WHERE id = 13");
                                                GetData622222.moveToFirst();
                                                if (GetData.getCount() > 0) {
                                                }
                                                cursor = GetData522222;
                                                cursor2 = GetData622222;
                                                TralaiSO(i);
                                                if (cursor != null) {
                                                }
                                                if (cursor2 != null) {
                                                }
                                                if (GetData3 != null) {
                                                }
                                                if (GetData4 != null) {
                                                }
                                                if (GetData == null) {
                                                }
                                            }
                                        }
                                        str15 = str60;
                                        String str62 = "";
                                        cursor3 = GetData10;
                                        int i12 = 1;
                                        while (i12 < i11) {
                                            int i13 = i11;
                                            StringBuilder sb24 = new StringBuilder();
                                            sb24.append(i12);
                                            str35 = str5;
                                            sb24.append("-");
                                            if (!jSONObject10.has(sb24.toString())) {
                                                str62 = str62 + i12 + ",";
                                            }
                                            i12++;
                                            i11 = i13;
                                            str5 = str35;
                                        }
                                        str35 = str5;
                                        if (str62.length() > 0) {
                                            final String str63 = "Thiếu tin " + str62;
                                            if (GetData3.getString(6).indexOf(str13) > -1) {
                                                SendSMS(str14, str63);
                                            } else if (GetData3.getString(6).indexOf("TL") > -1) {
                                                String finalStr1 = str14;
                                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        new MainActivity();
                                                        MainActivity.sendMessage(Long.parseLong(finalStr1), str63);
                                                    }
                                                });
                                            } else {
                                                try {
                                                    JSONObject jSONObject11 = new JSONObject(MainActivity.json_Tinnhan.getString(str14));
                                                    if (jSONObject11.getInt(str41222) > 3) {
                                                        new NotificationNewReader().NotificationWearReader(GetData3.getString(5), str63);
                                                    } else {
                                                        jSONObject11.put(str63, obj);
                                                        MainActivity.json_Tinnhan.put(str14, jSONObject11);
                                                    }
                                                } catch (Exception unused12) {
                                                    new NotificationNewReader().NotificationWearReader(GetData3.getString(5), str63);
                                                }
                                                StringBuilder sb25 = new StringBuilder();
                                                sb25.append(str16);
                                                sb25.append(str18);
                                                sb25.append("', '");
                                                sb25.append(str17);
                                                sb25.append(str12);
                                                sb25.append(GetData3.getString(4));
                                                sb25.append("', '");
                                                sb25.append(GetData3.getString(5));
                                                sb25.append("', '");
                                                sb25.append(GetData3.getString(6));
                                                sb25.append(str10);
                                                sb25.append(str63);
                                                str5 = str35;
                                                sb25.append(str5);
                                                QueryData(sb25.toString());
                                                if (cursor3 != null) {
                                                    cursor3.close();
                                                }
                                            }
                                        }
                                        str5 = str35;
                                        if (cursor3 != null) {
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                str19 = str34;
                            } else {
                                str33 = string;
                                str17 = str25;
                                str16 = "Insert into Chat_database Values( null,'";
                                i4 = i5;
                                str5 = str50;
                            }
                        } else {
                            str33 = string;
                            str17 = str25;
                            str16 = "Insert into Chat_database Values( null,'";
                            i4 = i5;
                            str5 = str30;
                        }
                        str15 = str24;
                        str18 = format;
                        str34 = "' AND so_dienthoai = '";
                        str12 = str31;
                        String str42222 = str23;
                        str9 = str22;
                        str11 = str42222;
                        if (GetData3.getInt(3) == 1) {
                        }
                        str14 = str33;
                        str19 = str34;
                    } else {
                        i4 = i7;
                        str9 = "Tin ";
                        str10 = str4;
                        str11 = str3;
                        str12 = "', 2, '";
                        str13 = str;
                        str14 = string;
                        str15 = str8;
                        str16 = "Insert into Chat_database Values( null,'";
                        str17 = format2;
                        str18 = format;
                        str19 = "' AND so_dienthoai = '";
                    }
                    StringBuilder sb82222222222 = new StringBuilder();
                    sb82222222222.append("Select * From tbl_tinnhanS WHERE ngay_nhan = '");
                    String str382222222222 = str15;
                    sb82222222222.append(str382222222222);
                    sb82222222222.append(str19);
                    sb82222222222.append(str14);
                    sb82222222222.append(str11);
                    int i82222222222 = i4;
                    sb82222222222.append(i82222222222);
                    Cursor GetData52222222222 = GetData(sb82222222222.toString());
                    GetData52222222222.moveToFirst();
                    StringBuilder sb92222222222 = new StringBuilder();
                    String str392222222222 = str19;
                    sb92222222222.append("Select * From tbl_chuyenthang WHERE sdt_nhan = '");
                    sb92222222222.append(str14);
                    sb92222222222.append("'");
                    GetData = GetData(sb92222222222.toString());
                    GetData.moveToFirst();
                    Cursor GetData62222222222 = GetData("Select Om_Xi3 FROM so_Om WHERE id = 13");
                    GetData62222222222.moveToFirst();
                    if (GetData.getCount() > 0) {
                        String str64 = str14;
                        if (GetData62222222222.getInt(0) == 0) {
                            cursor2 = GetData62222222222;
                            if (GetData52222222222.getInt(14) == 1) {
                                StringBuilder sb26 = new StringBuilder();
                                sb26.append("Select max(so_tin_nhan) From tbl_tinnhanS WHERE so_dienthoai = '");
                                cursor = GetData52222222222;
                                sb26.append(GetData.getString(4));
                                sb26.append("' AND ngay_nhan = '");
                                sb26.append(str382222222222);
                                sb26.append("'");
                                Cursor GetData11 = GetData(sb26.toString());
                                GetData11.moveToFirst();
                                int i14 = GetData11.getInt(0) + 1;
                                if (GetData.getString(3).indexOf("ZL") > -1) {
                                    str21 = "ZL";
                                } else if (GetData.getString(3).indexOf("VB") > -1) {
                                    str21 = "VB";
                                } else if (GetData.getString(3).indexOf("WA") > -1) {
                                    str21 = "WA";
                                } else {
                                    str20 = str382222222222;
                                    str21 = GetData.getString(3).indexOf("TL") > -1 ? "TL" : str13;
                                    StringBuilder sb27 = new StringBuilder();
                                    sb27.append("Insert Into tbl_tinnhanS values (null, '");
                                    sb27.append(GetData3.getString(1));
                                    sb27.append("', '");
                                    sb27.append(GetData3.getString(2));
                                    sb27.append("',2, '");
                                    String str65 = str5;
                                    sb27.append(GetData.getString(3));
                                    sb27.append("', '");
                                    sb27.append(GetData.getString(4));
                                    sb27.append("', '");
                                    sb27.append(str21);
                                    sb27.append("', ");
                                    sb27.append(i14);
                                    sb27.append(", '");
                                    sb27.append(GetData3.getString(8));
                                    sb27.append("', null, '");
                                    sb27.append(GetData3.getString(10));
                                    sb27.append("', 'ko',0,0,1, null)");
                                    QueryData(sb27.toString());
                                    if (str21.indexOf(str13) <= -1) {
                                        SendSMS(GetData.getString(4), str9 + i14 + ":\n" + GetData3.getString(8));
                                    } else {
                                        String str66 = str9;
                                        if (GetData3.getString(6).indexOf("TL") > -1) {
                                            final String str67 = str66 + i14 + ":\n" + GetData3.getString(8);
                                            Cursor finalGetData2 = GetData;
                                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    new MainActivity();
                                                    MainActivity.sendMessage(finalGetData2.getLong(4), str67);
                                                }
                                            });
                                        } else {
                                            String str68 = str66 + i14 + ":\n" + GetData3.getString(8);
                                            new NotificationNewReader().NotificationWearReader(GetData.getString(4), str68);
                                            QueryData(str16 + str18 + "', '" + str17 + str12 + GetData.getString(3) + "', '" + GetData.getString(4) + "', '" + str21 + str10 + str68 + str65);
                                        }
                                    }
                                    QueryData("Update tbl_tinnhanS set del_sms = 0 WHERE ngay_nhan = '" + str20 + str392222222222 + str64 + str11 + i82222222222);
                                    if (GetData11 != null) {
                                        GetData11.close();
                                    }
                                }
                                str20 = str382222222222;
                                StringBuilder sb272 = new StringBuilder();
                                sb272.append("Insert Into tbl_tinnhanS values (null, '");
                                sb272.append(GetData3.getString(1));
                                sb272.append("', '");
                                sb272.append(GetData3.getString(2));
                                sb272.append("',2, '");
                                String str652 = str5;
                                sb272.append(GetData.getString(3));
                                sb272.append("', '");
                                sb272.append(GetData.getString(4));
                                sb272.append("', '");
                                sb272.append(str21);
                                sb272.append("', ");
                                sb272.append(i14);
                                sb272.append(", '");
                                sb272.append(GetData3.getString(8));
                                sb272.append("', null, '");
                                sb272.append(GetData3.getString(10));
                                sb272.append("', 'ko',0,0,1, null)");
                                QueryData(sb272.toString());
                                if (str21.indexOf(str13) <= -1) {
                                }
                                QueryData("Update tbl_tinnhanS set del_sms = 0 WHERE ngay_nhan = '" + str20 + str392222222222 + str64 + str11 + i82222222222);
                                if (GetData11 != null) {
                                }
                            } else {
                                cursor = GetData52222222222;
                            }
                            TralaiSO(i);
                            if (cursor != null) {
                                cursor.close();
                            }
                            if (cursor2 != null) {
                                cursor2.close();
                            }
                            if (GetData3 != null) {
                                GetData3.close();
                            }
                            if (GetData4 != null) {
                                GetData4.close();
                            }
                            if (GetData == null) {
                                GetData.close();
                                return;
                            }
                            return;
                        }
                    }
                    cursor = GetData52222222222;
                    cursor2 = GetData62222222222;
                    TralaiSO(i);
                    if (cursor != null) {
                    }
                    if (cursor2 != null) {
                    }
                    if (GetData3 != null) {
                    }
                    if (GetData4 != null) {
                    }
                    if (GetData == null) {
                    }
                }
                str6 = "Time";
                StringBuilder sb722 = new StringBuilder();
                sb722.append("Update tbl_tinnhanS set ok_tn = 0 WHERE ngay_nhan = '");
                str8 = string2;
                sb722.append(str8);
                sb722.append("' AND so_dienthoai = '");
                sb722.append(string);
                sb722.append(str3);
                sb722.append(i7);
                QueryData(sb722.toString());
                if (GetData3.getString(11).indexOf("ok") != 0) {
                }
                i4 = i7;
                str9 = "Tin ";
                str10 = str4;
                str11 = str3;
                str12 = "', 2, '";
                str13 = str;
                str14 = string;
                str15 = str8;
                str16 = "Insert into Chat_database Values( null,'";
                str17 = format2;
                str18 = format;
                str19 = "' AND so_dienthoai = '";
                StringBuilder sb822222222222 = new StringBuilder();
                sb822222222222.append("Select * From tbl_tinnhanS WHERE ngay_nhan = '");
                String str3822222222222 = str15;
                sb822222222222.append(str3822222222222);
                sb822222222222.append(str19);
                sb822222222222.append(str14);
                sb822222222222.append(str11);
                int i822222222222 = i4;
                sb822222222222.append(i822222222222);
                Cursor GetData522222222222 = GetData(sb822222222222.toString());
                GetData522222222222.moveToFirst();
                StringBuilder sb922222222222 = new StringBuilder();
                String str3922222222222 = str19;
                sb922222222222.append("Select * From tbl_chuyenthang WHERE sdt_nhan = '");
                sb922222222222.append(str14);
                sb922222222222.append("'");
                GetData = GetData(sb922222222222.toString());
                GetData.moveToFirst();
                Cursor GetData622222222222 = GetData("Select Om_Xi3 FROM so_Om WHERE id = 13");
                GetData622222222222.moveToFirst();
                if (GetData.getCount() > 0) {
                }
                cursor = GetData522222222222;
                cursor2 = GetData622222222222;
                TralaiSO(i);
                if (cursor != null) {
                }
                if (cursor2 != null) {
                }
                if (GetData3 != null) {
                }
                if (GetData4 != null) {
                }
                if (GetData == null) {
                }
            }
        }
        i3 = i2;
        str6 = "Time";
        str7 = "Ok Tin ";
        str8 = string2;
        if (GetData3.getString(11).indexOf("ok") != 0) {
        }
        i4 = i7;
        str9 = "Tin ";
        str10 = str4;
        str11 = str3;
        str12 = "', 2, '";
        str13 = str;
        str14 = string;
        str15 = str8;
        str16 = "Insert into Chat_database Values( null,'";
        str17 = format2;
        str18 = format;
        str19 = "' AND so_dienthoai = '";
        StringBuilder sb8222222222222 = new StringBuilder();
        sb8222222222222.append("Select * From tbl_tinnhanS WHERE ngay_nhan = '");
        String str38222222222222 = str15;
        sb8222222222222.append(str38222222222222);
        sb8222222222222.append(str19);
        sb8222222222222.append(str14);
        sb8222222222222.append(str11);
        int i8222222222222 = i4;
        sb8222222222222.append(i8222222222222);
        Cursor GetData5222222222222 = GetData(sb8222222222222.toString());
        GetData5222222222222.moveToFirst();
        StringBuilder sb9222222222222 = new StringBuilder();
        String str39222222222222 = str19;
        sb9222222222222.append("Select * From tbl_chuyenthang WHERE sdt_nhan = '");
        sb9222222222222.append(str14);
        sb9222222222222.append("'");
        GetData = GetData(sb9222222222222.toString());
        GetData.moveToFirst();
        Cursor GetData6222222222222 = GetData("Select Om_Xi3 FROM so_Om WHERE id = 13");
        GetData6222222222222.moveToFirst();
        if (GetData.getCount() > 0) {
        }
        cursor = GetData5222222222222;
        cursor2 = GetData6222222222222;
        TralaiSO(i);
        if (cursor != null) {
        }
        if (cursor2 != null) {
        }
        if (GetData3 != null) {
        }
        if (GetData4 != null) {
        }
        if (GetData == null) {
        }
    }

    public void LayDanhsachKH() {
        MainActivity.DSkhachhang = new ArrayList<>();
        Cursor GetData = GetData("Select * From tbl_kh_new WHERE type_kh <> 2");
        if (GetData != null) {
            while (GetData.moveToNext()) {
                MainActivity.DSkhachhang.add(GetData.getString(1));
            }
            if (GetData == null || GetData.isClosed()) {
                return;
            }
            GetData.close();
        }
    }

    public void List_Khach_Hang() {
        QueryData("CREATE TABLE IF NOT EXISTS tbl_kh_new (ten_kh VARCHAR(30) PRIMARY KEY,sdt VARCHAR(15),use_app Varchar(10), type_kh INTEGER default 0, type_pt Integer default 0, tbl_MB TEXT, tbl_XS TEXT)");
        QueryData("Delete From tbl_kh_new Where substr(sdt,0,3) = 'TL'");
    }

    public void NhanTinNhan(Integer num, int i) throws JSONException {
        String str;
        Cursor cursor;
        String str2="";
        String str3="";
        String str4="";
        String str5="";
        String str6="";
        String str7="";
        String str8="";
        String str9="";
        int i2 = 0;
        String str10="";
        String PhanTichTinNhan;
        String str11="";
        int indexOf;
        String str12="";
        String str13="";
        String str14="";
        String str15="";
        String str16="";
        String str17="";
        String str18="";
        String str19="";
        String str20="";
        String str21="";
        String str22="";
        String str23="";
        String str24="";
        String str25="";
        String str26="";
        String str27="";
        int i3;
        int i4;
        StringBuilder sb = null;
        String str28="";
        String str29="";
        String str30="";
        String str31="";
        String str32="";
        boolean z;
        JSONObject jSONObject;
        String str33="";
        String str34="";
        String str35="";
        String str36="";
        String str37="";
        String str38="";
        String str39="";
        String str40="";
        String str41="";
        String str42="";
        String str43="";
        int i5;
        String str44= "";
        String str45= "";
        String str46= "";
        String str47= "";
        String str48= "";
        String str49= "";
        String str50= "";
        String str51= "";
        String str52= "";
        StringBuilder sb2;
        int length;
        JSONObject jSONObject2;
        String str53= "";
        String str54= "";
        char c;
        String str55= "";
        String str56= "";
        String str57= "";
        String str58= "";
        Database database2;
        String str59= "";
        int i6;
        String str60= "";
        String str61= "";
        String str62 = "";
        String str63 = "";
        String str64= "";
        String str65= "";
        String str66= "";
        String str67 = "";
        String str68= "";
        String str69= "";
        String str70= "";
        String str71= "";
        String str72= "";
        String str73= "";
        String str74 = "";
        String str75= "";
        String str76= "";
        String str77= "";
        String str78= "";
        String str79= "";
        String str80= "";
        String str81 = "";
        String str82 = "";
        String str83 = "";
        String str84= "";
        String str85= "";
        int i7 = 0;
        String[][] strArr = null;
        String[][] strArr2 =null;
        String str86 = "";
        String[][] strArr3=null;
        String[][] strArr4 = null;
        String str87 = "";
        boolean z2=false;
        int i8=0;
        String str88= "";
        String[] strArr5;
        String str89 = "";
        String substring = "";
        String str90= "";
        String str91= "";
        String str92= "";
        this.mang = (String[][]) Array.newInstance((Class<?>) String.class, 1000, 6);
        Cursor GetData = GetData("Select nd_phantich, ten_kh, ok_tn, ngay_nhan From tbl_tinnhanS WHERE id = " + num);
        GetData.moveToFirst();
        String str93 = ",";
        String str94 = " ";
        String str95 = "bc";
        String str96 = "lo";
        String str97 = "de";
        String str98 = "xi";
        String str99 = "xq";
        String str100 = "xn";
        String replaceAll = GetData.getString(0).replace("ldpro", "").replace("</font>", "").replaceAll("-", ",").replaceAll(InternalZipConstants.ZIP_FILE_SEPARATOR, ";").replaceAll("\n", " ").replaceAll("\\.", ",").replaceAll("x ", " x ").replaceAll("ba bc", "bc").replaceAll(";,", ";").replaceAll("; ,", ";").replaceAll("; lo", "lo").replaceAll(";lo", "lo").replaceAll("; de", "de").replaceAll(";de", "de").replaceAll("; xi", "xi").replaceAll(";xi", "xi").replaceAll("; bc", "bc").replaceAll(";bc", "bc").replaceAll("; xq", "xq").replaceAll(";xq", "xq").replaceAll("; xn", "xn").replaceAll(";xn", "xn").replaceAll("bo", " bo").replaceAll("duoi", "dit").replaceAll("dit 10", "duoi 10").replaceAll("tong dit", "tong <");
        Cursor GetData2 = GetData("Select * From tbl_kh_new Where ten_kh = '" + GetData.getString(1) + "'");
        GetData2.moveToFirst();
        JSONObject jSONObject3 = new JSONObject(GetData2.getString(5));
        this.json = jSONObject3;
        this.caidat_tg = jSONObject3.getJSONObject("caidat_tg");
        String Xuly_DauTN = Congthuc.Xuly_DauTN(replaceAll);
        String str101 = Xuly_DauTN.length() < 3 ? "Không hiểu " + Xuly_DauTN : "";
        if (Xuly_DauTN.startsWith("tin")) {
            Xuly_DauTN.replaceFirst("tin", "t");
        }
        String fixTinNhan = Congthuc.fixTinNhan(Xuly_DauTN);
        str = str101;
        try {
            if (this.caidat_tg.getInt("khach_de") == 1) {
                fixTinNhan = fixTinNhan.replaceAll("de ", "det").replaceAll("deb", "det");
            }
        } catch (JSONException unused) {
        }
        String str102 = "\n";
        if (!fixTinNhan.trim().startsWith("t") || MainActivity.jSon_Setting.getInt("baotinthieu") <= 0) {
            cursor = GetData2;
            str2 = "xn";
            str3 = "bc";
            str102 = "\n";
            str4 = "";
        } else {
            fixTinNhan = fixTinNhan + " ";
            String str103 = "";
            int i9 = -1;
            while (true) {
                str90 = str103;
                try {
                    try {
                        i9 = fixTinNhan.indexOf("tin", i9 + 1);
                        if (i9 == -1) {
                            break;
                        }
                        int i10 = i9 + 5;
                        while (true) {
                            cursor = GetData2;
                            if (i10 >= i9 + 10) {
                                break;
                            }
                            try {
                                if (!Congthuc.isNumeric(fixTinNhan.substring(i9 + 4, i10))) {
                                    break;
                                }
                                i10++;
                                GetData2 = cursor;
                            } catch (Exception unused3) {
                                str2 = str100;
                                str3 = str95;
                                str4 = str90;
                                if (str4.length() > 0) {
                                }
                                String str104 = str102;
                                String str105 = " x ";
                                String str106 = "hc";
                                String str107 = str4;
                                if (fixTinNhan.indexOf("Không hiểu") > -1) {
                                }
                                str10 = str;
                                String str108 = "xg";
                                String str109 = str10;
                                PhanTichTinNhan = Congthuc.PhanTichTinNhan(fixTinNhan);
                                String str110 = fixTinNhan;
                                if (PhanTichTinNhan.indexOf("Không hiểu") <= i2) {
                                }
                                if (Congthuc.CheckTime(this.caidat_tg.getString("tg_loxien"))) {
                                }
                                indexOf = str11.indexOf("Không hiểu");
                                String str111 = "de dit db";
                                String str112 = str11;
                                String str113 = "hai cua";
                                String str114 = "Không hiểu";
                                String str115 = "Không hiểu ";
                                String str116 = "</font>";
                                String str117 = "ldpro";
                                String str118 = "de dit nhat";
                                if (indexOf != -1) {
                                }
                                JSONObject jSONObject4 = new JSONObject();
                                String str119 = str24;
                                String str120 = str14;
                                String str121 = str17;
                                String str122 = str121;
                                i3 = 0;
                                i4 = 1;
                                while (i4 < 1000) {
                                }
                                String str123 = str13;
                                JSONObject jSONObject5 = jSONObject4;
                                String str124 = str17;
                                if (i3 != 0) {
                                }
                                sb.append(str29);
                                sb.append(num);
                                QueryData(sb.toString());
                                if (cursor != null) {
                                }
                                if (GetData != null) {
                                }
                            }
                        }
                        str3 = str95;
                        if (i10 - i9 > 5) {
                            try {
                                String trim = fixTinNhan.substring(0, i10).replace("tin", "").trim();
                                if (Congthuc.isNumeric(trim)) {
                                    str90 = trim;
                                }
                                try {
                                    StringBuilder sb3 = new StringBuilder();
                                    str2 = str100;
                                    try {
                                        sb3.append(fixTinNhan.substring(0, i9));
                                        sb3.append(fixTinNhan.substring(i10));
                                        fixTinNhan = sb3.toString();
                                        str103 = str90;
                                        GetData2 = cursor;
                                        str95 = str3;
                                        str100 = str2;
                                    } catch (Exception unused4) {
                                        str4 = str90;
                                        if (str4.length() > 0) {
                                        }
                                        String str1042 = str102;
                                        String str1052 = " x ";
                                        String str1062 = "hc";
                                        String str1072 = str4;
                                        if (fixTinNhan.indexOf("Không hiểu") > -1) {
                                        }
                                        str10 = str;
                                        String str1082 = "xg";
                                        String str1092 = str10;
                                        PhanTichTinNhan = Congthuc.PhanTichTinNhan(fixTinNhan);
                                        String str1102 = fixTinNhan;
                                        if (PhanTichTinNhan.indexOf("Không hiểu") <= i2) {
                                        }
                                        if (Congthuc.CheckTime(this.caidat_tg.getString("tg_loxien"))) {
                                        }
                                        indexOf = str11.indexOf("Không hiểu");
                                        String str1112 = "de dit db";
                                        String str1122 = str11;
                                        String str1132 = "hai cua";
                                        String str1142 = "Không hiểu";
                                        String str1152 = "Không hiểu ";
                                        String str1162 = "</font>";
                                        String str1172 = "ldpro";
                                        String str1182 = "de dit nhat";
                                        if (indexOf != -1) {
                                        }
                                        JSONObject jSONObject42 = new JSONObject();
                                        String str1192 = str24;
                                        String str1202 = str14;
                                        String str1212 = str17;
                                        String str1222 = str1212;
                                        i3 = 0;
                                        i4 = 1;
                                        while (i4 < 1000) {
                                        }
                                        String str1232 = str13;
                                        JSONObject jSONObject52 = jSONObject42;
                                        String str1242 = str17;
                                        if (i3 != 0) {
                                        }
                                        sb.append(str29);
                                        sb.append(num);
                                        QueryData(sb.toString());
                                        if (cursor != null) {
                                        }
                                        if (GetData != null) {
                                        }
                                    }
                                } catch (Throwable th) {
                                    th = th;
                                    str91 = str90;
                                    if (str91.length() > 0) {
                                        Cursor GetData3 = GetData("Select * From tbl_tinnhanS WHERE ngay_nhan = '" + GetData.getString(3) + "' AND ten_kh = '" + GetData.getString(1) + "' AND so_tin_nhan = " + str91);
                                        GetData3.getCount();
                                        GetData3.close();
                                    }
                                    throw th;
                                }
                            } catch (Exception unused5) {
                                str2 = str100;
                                str4 = str90;
                                if (str4.length() > 0) {
                                    Cursor GetData4 = GetData("Select * From tbl_tinnhanS WHERE ngay_nhan = '" + GetData.getString(3) + "' AND ten_kh = '" + GetData.getString(1) + "' AND so_tin_nhan = " + str4);
                                    int count = GetData4.getCount();
                                    String str125 = fixTinNhan;
                                    str92 = str125;
                                }
                                String str10422 = str102;
                                String str10522 = " x ";
                                String str10622 = "hc";
                                String str10722 = str4;
                                if (fixTinNhan.indexOf("Không hiểu") > -1) {
                                }
                                str10 = str;
                                String str10822 = "xg";
                                String str10922 = str10;
                                PhanTichTinNhan = Congthuc.PhanTichTinNhan(fixTinNhan);
                                String str11022 = fixTinNhan;
                                if (PhanTichTinNhan.indexOf("Không hiểu") <= i2) {
                                }
                                if (Congthuc.CheckTime(this.caidat_tg.getString("tg_loxien"))) {
                                }
                                indexOf = str11.indexOf("Không hiểu");
                                String str11122 = "de dit db";
                                String str11222 = str11;
                                String str11322 = "hai cua";
                                String str11422 = "Không hiểu";
                                String str11522 = "Không hiểu ";
                                String str11622 = "</font>";
                                String str11722 = "ldpro";
                                String str11822 = "de dit nhat";
                                if (indexOf != -1) {
                                }
                                JSONObject jSONObject422 = new JSONObject();
                                String str11922 = str24;
                                String str12022 = str14;
                                String str12122 = str17;
                                String str12222 = str12122;
                                i3 = 0;
                                i4 = 1;
                                while (i4 < 1000) {
                                }
                                String str12322 = str13;
                                JSONObject jSONObject522 = jSONObject422;
                                String str12422 = str17;
                                if (i3 != 0) {
                                }
                                sb.append(str29);
                                sb.append(num);
                                QueryData(sb.toString());
                                if (cursor != null) {
                                }
                                if (GetData != null) {
                                }
                            }
                        } else {
                            str103 = str90;
                            GetData2 = cursor;
                            str95 = str3;
                        }
                    } catch (Exception unused6) {
                        cursor = GetData2;
                    }
                } catch (Throwable th2) {
                    str4 = str90;
                    th2.printStackTrace();
                }
            }
            cursor = GetData2;
            str2 = str100;
            str3 = str95;
            str92 = fixTinNhan.trim();
            str4 = str90;
            for (int i11 = 6; i11 > 0; i11--) {
                try {
                    String substring2 = str92.substring(0, i11);
                    if (substring2.trim().indexOf("t") > -1) {
                        str90 = substring2.replaceAll("t", "").replaceAll(" ", "").replaceAll(",", "");
                        if (Congthuc.isNumeric(str90)) {
                            try {
                                str92 = str92.substring(i11);
                                str4 = str90;
                            } catch (Exception unused7) {
                                fixTinNhan = str92;
                                str4 = str90;
                                if (str4.length() > 0) {
                                }
                                String str104222 = str102;
                                String str105222 = " x ";
                                String str106222 = "hc";
                                String str107222 = str4;
                                if (fixTinNhan.indexOf("Không hiểu") > -1) {
                                }
                                str10 = str;
                                String str108222 = "xg";
                                String str109222 = str10;
                                PhanTichTinNhan = Congthuc.PhanTichTinNhan(fixTinNhan);
                                String str110222 = fixTinNhan;
                                if (PhanTichTinNhan.indexOf("Không hiểu") <= i2) {
                                }
                                if (Congthuc.CheckTime(this.caidat_tg.getString("tg_loxien"))) {
                                }
                                indexOf = str11.indexOf("Không hiểu");
                                String str111222 = "de dit db";
                                String str112222 = str11;
                                String str113222 = "hai cua";
                                String str114222 = "Không hiểu";
                                String str115222 = "Không hiểu ";
                                String str116222 = "</font>";
                                String str117222 = "ldpro";
                                String str118222 = "de dit nhat";
                                if (indexOf != -1) {
                                }
                                JSONObject jSONObject4222 = new JSONObject();
                                String str119222 = str24;
                                String str120222 = str14;
                                String str121222 = str17;
                                String str122222 = str121222;
                                i3 = 0;
                                i4 = 1;
                                while (i4 < 1000) {
                                }
                                String str123222 = str13;
                                JSONObject jSONObject5222 = jSONObject4222;
                                String str124222 = str17;
                                if (i3 != 0) {
                                }
                                sb.append(str29);
                                sb.append(num);
                                QueryData(sb.toString());
                                if (cursor != null) {
                                }
                                if (GetData != null) {
                                }
                            }
                        } else {
                            continue;
                        }
                    }
                } catch (Exception unused8) {
                    fixTinNhan = str92;
                    if (str4.length() > 0) {
                    }
                    String str1042222 = str102;
                    String str1052222 = " x ";
                    String str1062222 = "hc";
                    String str1072222 = str4;
                    if (fixTinNhan.indexOf("Không hiểu") > -1) {
                    }
                    str10 = str;
                    String str1082222 = "xg";
                    String str1092222 = str10;
                    PhanTichTinNhan = Congthuc.PhanTichTinNhan(fixTinNhan);
                    String str1102222 = fixTinNhan;
                    indexOf = str11.indexOf("Không hiểu");
                    String str1112222 = "de dit db";
                    String str1122222 = str11;
                    String str1132222 = "hai cua";
                    String str1142222 = "Không hiểu";
                    String str1152222 = "Không hiểu ";
                    String str1162222 = "</font>";
                    String str1172222 = "ldpro";
                    String str1182222 = "de dit nhat";
                    if (indexOf != -1) {
                    }
                    JSONObject jSONObject42222 = new JSONObject();
                    String str1192222 = str24;
                    String str1202222 = str14;
                    String str1212222 = str17;
                    String str1222222 = str1212222;
                    i3 = 0;
                    i4 = 1;
                    while (i4 < 1000) {
                    }
                    String str1232222 = str13;
                    JSONObject jSONObject52222 = jSONObject42222;
                    String str1242222 = str17;
                    if (i3 != 0) {
                    }
                    sb.append(str29);
                    sb.append(num);
                    QueryData(sb.toString());
                    if (cursor != null) {
                    }
                    if (GetData != null) {
                    }
                } catch (Throwable th3) {
                    str90 = str4;
                    str91 = str90;
                    if (str91.length() > 0) {
                    }
                    th3.printStackTrace();
                }
            }
            if (str4.length() > 0) {
                cursor = GetData("Select * From tbl_tinnhanS WHERE ngay_nhan = '" + GetData.getString(3) + "' AND ten_kh = '" + GetData.getString(1) + "' AND so_tin_nhan = " + str4);
            }
            fixTinNhan = str92;
        }
        String str10422222 = str102;
        String str10522222 = " x ";
        String str10622222 = "hc";
        String str10722222 = str4;
        if (fixTinNhan.indexOf("Không hiểu") > -1) {
            if (fixTinNhan.length() < 8) {
                str5 = str10422222;
                str6 = "";
                str8 = str2;
                str9 = "t";
                str10 = "Không hiểu " + fixTinNhan;
                str7 = str3;
            } else {
                str5 = str10422222;
                str6 = "";
                if (fixTinNhan.substring(0, 5).indexOf("de") == -1 && fixTinNhan.substring(0, 5).indexOf("lo") == -1 && fixTinNhan.substring(0, 5).indexOf("xi") == -1 && fixTinNhan.substring(0, 5).indexOf("xq") == -1 && fixTinNhan.substring(0, 5).indexOf("hc") == -1) {
                    str8 = str2;
                    str9 = "t";
                    if (fixTinNhan.substring(0, 5).indexOf(str8) == -1) {
                        str7 = str3;
                        if (fixTinNhan.substring(0, 5).indexOf(str7) == -1 && fixTinNhan.substring(0, 5).indexOf("xg") == -1) {
                            str10 = "Không hiểu dạng";
                        }
                    } else {
                        str7 = str3;
                    }
                } else {
                    str7 = str3;
                    str8 = str2;
                    str9 = "t";
                }
                i2 = -1;
                if (fixTinNhan.indexOf(" bo ") > -1) {
                    str10 = "Không hiểu bo ";
                    String str10822222 = "xg";
                    String str10922222 = str10;
                    PhanTichTinNhan = Congthuc.PhanTichTinNhan(fixTinNhan);
                    String str11022222 = fixTinNhan;
                    if (PhanTichTinNhan.indexOf("Không hiểu") <= i2) {
                        str11 = PhanTichTinNhan;
                    } else if (PhanTichTinNhan.indexOf("x ") == i2) {
                        str11 = "Không hiểu " + PhanTichTinNhan;
                    } else {
                        str11 = str10922222;
                    }
                    boolean z4 = (Congthuc.CheckTime(this.caidat_tg.getString("tg_loxien")) || Congthuc.CheckTime("18:30")) ? false : true;
                    indexOf = str11.indexOf("Không hiểu");
                    boolean z322222 = z4;
                    String str11122222 = "de dit db";
                    String str11222222 = str11;
                    String str11322222 = "hai cua";
                    String str11422222 = "Không hiểu";
                    String str11522222 = "Không hiểu ";
                    String str11622222 = "</font>";
                    String str11722222 = "ldpro";
                    String str11822222 = "de dit nhat";
                    if (indexOf != -1) {
                        String replaceAll2 = PhanTichTinNhan.replaceAll(" , ", " ").replaceAll(" ,", " ");
                        for (int i12 = 1; i12 < 10; i12++) {
                            replaceAll2 = replaceAll2.replaceAll("  ", " ").replaceAll(",,", ",");
                        }
                        String str126 = replaceAll2.trim() + " ";
                        String str127 = null;
                        String str128 = null;
                        String str129 = null;
                        int i13 = 0;
                        int i14 = -1;
                        int i15 = 0;
                        while (true) {
                            int indexOf2 = str126.indexOf(str10522222, i14 + 1);
                            String str130 = str10522222;
                            if (indexOf2 == -1) {
                                break;
                            }
                            int i16 = indexOf2;
                            String str131 = str6;
                            while (true) {
                                if (i16 >= str126.length()) {
                                    str57 = str11122222;
                                    str58 = str11322222;
                                    break;
                                }
                                str58 = str11322222;
                                if (str126.charAt(i16) == ' ' && str131.length() > 0) {
                                    str57 = str11122222;
                                    break;
                                }
                                int i17 = i16 + 1;
                                String str132 = str11122222;
                                if ("0123456789,tr".indexOf(str126.substring(i16, i17)) > -1) {
                                    str131 = str131 + str126.substring(i16, i17);
                                }
                                i16 = i17;
                                str11322222 = str58;
                                str11122222 = str132;
                            }
                            String str133 = str131;
                            int i18 = i16;
                            String str134 = str6;
                            while (i18 < str126.length() && (Character.isLetter(str126.charAt(i18)) || str134.length() <= 0)) {
                                StringBuilder sb4 = new StringBuilder();
                                sb4.append(str134);
                                int i19 = i18 + 1;
                                sb4.append(str126.substring(i18, i19));
                                str134 = sb4.toString();
                                i18 = i19;
                            }
                            if (i16 == i18) {
                                i18--;
                            }
                            String str135 = str93;
                            if (str134.indexOf("dau") <= -1 && str134.indexOf("dit") <= -1 && str134.indexOf("tong") <= -1 && str134.indexOf("cham") <= -1 && str134.indexOf("dan") <= -1 && str134.indexOf("boj") <= -1 && str134.indexOf("lo") <= -1 && str134.indexOf(str97) <= -1 && str134.indexOf("xi") <= -1 && str134.indexOf(str8) <= -1 && str134.indexOf(str10622222) <= -1 && str134.indexOf(str99) <= -1 && str134.indexOf(str10822222) <= -1 && str134.indexOf(str7) <= -1 && str134.indexOf(" x") <= -1 && str134.indexOf("kep") <= -1 && str134.indexOf("sat") <= -1 && str134.indexOf("to") <= -1 && str134.indexOf("nho") <= -1 && str134.indexOf("chan") <= -1 && str134.indexOf("le") <= -1 && str134.indexOf("ko") <= -1 && str134.indexOf("chia") <= -1 && str134.indexOf("duoi") <= -1 && str134.indexOf("be") <= -1) {
                                if (str134.indexOf("x ") > -1) {
                                    for (int i20 = i16 - 1; i20 > 0; i20--) {
                                        i18 = i20 + 1;
                                        if (!Congthuc.isNumeric(str126.substring(i20, i18))) {
                                            substring = str126.substring(i13, i18);
                                        }
                                    }
                                } else {
                                    substring = str126.substring(i13, i18);
                                    if (substring.trim().length() > 3 && substring.substring(0, 4).indexOf("bor") > -1) {
                                        substring = "de " + substring;
                                    }
                                }
                                i13 = i18;
                                str129 = substring;
                                str127 = str126.substring(i13);
                                break;
                            } else {
                                str129 = str126.substring(i13, i16);
                                str127 = str126.substring(i16);
                                i13 = i16;
                            }
                            int i21 = i15 + 1;
                            try {
                                String trim2 = str129.trim();
                                String str136 = str9;
                                try {
                                    if (trim2.startsWith(str136)) {
                                        try {
                                            if (trim2.length() > 6) {
                                                int i22 = 6;
                                                while (i22 > 0) {
                                                    str69 = str126;
                                                    try {
                                                        String substring3 = trim2.substring(0, i22);
                                                        i6 = i13;
                                                        if (substring3.trim().indexOf(str136) > -1) {
                                                            str79 = str6;
                                                            try {
                                                                str80 = str135;
                                                                try {
                                                                    if (Congthuc.isNumeric(substring3.replaceAll(str136, str79).replaceAll(str94, str79).replaceAll(str80, str79))) {
                                                                        StringBuilder sb5 = new StringBuilder();
                                                                        sb5.append(str94);
                                                                        str75 = str136;
                                                                        try {
                                                                            sb5.append(trim2.substring(i22 + 1));
                                                                            sb5.append(str94);
                                                                            str129 = sb5.toString();
                                                                            i22--;
                                                                            str135 = str80;
                                                                            str126 = str69;
                                                                            str136 = str75;
                                                                            str6 = str79;
                                                                            i13 = i6;
                                                                        } catch (Exception unused9) {
                                                                            database2 = this;
                                                                            str59 = str8;
                                                                            str68 = str10622222;
                                                                            str60 = str99;
                                                                            str61 = str11422222;
                                                                            str62 = str11822222;
                                                                            str70 = str58;
                                                                            str63 = str57;
                                                                            str64 = str7;
                                                                            str65 = str10822222;
                                                                            str71 = str97;
                                                                            str72 = str11522222;
                                                                            str66 = str11622222;
                                                                            str67 = str80;
                                                                            str11522222 = str94;
                                                                            str73 = str11722222;
                                                                            str74 = str79;
                                                                            str76 = str128;
                                                                            str77 = str129;
                                                                            String[][] strArr6 = database2.mang;
                                                                            str78 = str74;
                                                                            strArr6[i21][0] = strArr6[i21][0].replaceAll(str73, str78);
                                                                            String[][] strArr7 = database2.mang;
                                                                            strArr7[i21][0] = strArr7[i21][0].replaceAll(str66, str78);
                                                                            database2.mang[i21][0] = str73 + database2.mang[i21][0] + str66;
                                                                            str128 = str76;
                                                                            str129 = str77;
                                                                            i15 = i21;
                                                                            str11322222 = str70;
                                                                            str11122222 = str63;
                                                                            str11722222 = str73;
                                                                            str126 = str69;
                                                                            str9 = str75;
                                                                            str94 = str11522222;
                                                                            str7 = str64;
                                                                            str10522222 = str130;
                                                                            i14 = i16;
                                                                            str10622222 = str68;
                                                                            i13 = i6;
                                                                            str8 = str59;
                                                                            str6 = str78;
                                                                            str11522222 = str72;
                                                                            str11822222 = str62;
                                                                            str93 = str67;
                                                                            str97 = str71;
                                                                            str99 = str60;
                                                                            str11622222 = str66;
                                                                            str10822222 = str65;
                                                                            str11422222 = str61;
                                                                        }
                                                                    }
                                                                } catch (Exception unused10) {
                                                                    str75 = str136;
                                                                    database2 = this;
                                                                    str59 = str8;
                                                                    str68 = str10622222;
                                                                    str60 = str99;
                                                                    str61 = str11422222;
                                                                    str62 = str11822222;
                                                                    str70 = str58;
                                                                    str63 = str57;
                                                                    str64 = str7;
                                                                    str65 = str10822222;
                                                                    str71 = str97;
                                                                    str72 = str11522222;
                                                                    str66 = str11622222;
                                                                    str67 = str80;
                                                                    str11522222 = str94;
                                                                    str73 = str11722222;
                                                                    str74 = str79;
                                                                    str76 = str128;
                                                                    str77 = str129;
                                                                    String[][] strArr62 = database2.mang;
                                                                    str78 = str74;
                                                                    strArr62[i21][0] = strArr62[i21][0].replaceAll(str73, str78);
                                                                    String[][] strArr72 = database2.mang;
                                                                    strArr72[i21][0] = strArr72[i21][0].replaceAll(str66, str78);
                                                                    database2.mang[i21][0] = str73 + database2.mang[i21][0] + str66;
                                                                    str128 = str76;
                                                                    str129 = str77;
                                                                    i15 = i21;
                                                                    str11322222 = str70;
                                                                    str11122222 = str63;
                                                                    str11722222 = str73;
                                                                    str126 = str69;
                                                                    str9 = str75;
                                                                    str94 = str11522222;
                                                                    str7 = str64;
                                                                    str10522222 = str130;
                                                                    i14 = i16;
                                                                    str10622222 = str68;
                                                                    i13 = i6;
                                                                    str8 = str59;
                                                                    str6 = str78;
                                                                    str11522222 = str72;
                                                                    str11822222 = str62;
                                                                    str93 = str67;
                                                                    str97 = str71;
                                                                    str99 = str60;
                                                                    str11622222 = str66;
                                                                    str10822222 = str65;
                                                                    str11422222 = str61;
                                                                }
                                                            } catch (Exception unused11) {
                                                                str75 = str136;
                                                                str80 = str135;
                                                            }
                                                        } else {
                                                            str79 = str6;
                                                            str80 = str135;
                                                        }
                                                        str75 = str136;
                                                        i22--;
                                                        str135 = str80;
                                                        str126 = str69;
                                                        str136 = str75;
                                                        str6 = str79;
                                                        i13 = i6;
                                                    } catch (Exception unused12) {
                                                        i6 = i13;
                                                        str79 = str6;
                                                        str80 = str135;
                                                        str75 = str136;
                                                        database2 = this;
                                                        str59 = str8;
                                                        str68 = str10622222;
                                                        str60 = str99;
                                                        str61 = str11422222;
                                                        str62 = str11822222;
                                                        str70 = str58;
                                                        str63 = str57;
                                                        str64 = str7;
                                                        str65 = str10822222;
                                                        str71 = str97;
                                                        str72 = str11522222;
                                                        str66 = str11622222;
                                                        str67 = str80;
                                                        str11522222 = str94;
                                                        str73 = str11722222;
                                                        str74 = str79;
                                                        str76 = str128;
                                                        str77 = str129;
                                                        String[][] strArr622 = database2.mang;
                                                        str78 = str74;
                                                        strArr622[i21][0] = strArr622[i21][0].replaceAll(str73, str78);
                                                        String[][] strArr722 = database2.mang;
                                                        strArr722[i21][0] = strArr722[i21][0].replaceAll(str66, str78);
                                                        database2.mang[i21][0] = str73 + database2.mang[i21][0] + str66;
                                                        str128 = str76;
                                                        str129 = str77;
                                                        i15 = i21;
                                                        str11322222 = str70;
                                                        str11122222 = str63;
                                                        str11722222 = str73;
                                                        str126 = str69;
                                                        str9 = str75;
                                                        str94 = str11522222;
                                                        str7 = str64;
                                                        str10522222 = str130;
                                                        i14 = i16;
                                                        str10622222 = str68;
                                                        i13 = i6;
                                                        str8 = str59;
                                                        str6 = str78;
                                                        str11522222 = str72;
                                                        str11822222 = str62;
                                                        str93 = str67;
                                                        str97 = str71;
                                                        str99 = str60;
                                                        str11622222 = str66;
                                                        str10822222 = str65;
                                                        str11422222 = str61;
                                                    }
                                                }
                                            }
                                        } catch (Exception unused13) {
                                            str69 = str126;
                                        }
                                    }
                                    str69 = str126;
                                    i6 = i13;
                                    str81 = str6;
                                    str82 = str135;
                                    str75 = str136;
                                    str83 = str129;
                                    database2 = this;
                                    try {
                                        database2.mang[i21][0] = str83;
                                    } catch (Exception unused14) {
                                        str59 = str8;
                                        str68 = str10622222;
                                        str60 = str99;
                                        str61 = str11422222;
                                        str62 = str11822222;
                                        str70 = str58;
                                        str63 = str57;
                                        str84 = str83;
                                    }
                                } catch (Exception unused15) {
                                    str59 = str8;
                                    str69 = str126;
                                    i6 = i13;
                                    str60 = str99;
                                    str61 = str11422222;
                                    str62 = str11822222;
                                    str70 = str58;
                                    str63 = str57;
                                    str64 = str7;
                                    str65 = str10822222;
                                    str71 = str97;
                                    str72 = str11522222;
                                    str66 = str11622222;
                                    str67 = str135;
                                    str68 = str10622222;
                                    str11522222 = str94;
                                    str73 = str11722222;
                                    str74 = str6;
                                    str75 = str136;
                                    database2 = this;
                                }
                            } catch (Exception unused16) {
                                database2 = this;
                                str59 = str8;
                                i6 = i13;
                                str60 = str99;
                                str61 = str11422222;
                                str62 = str11822222;
                                str63 = str57;
                                str64 = str7;
                                str65 = str10822222;
                                str66 = str11622222;
                                str67 = str135;
                                str68 = str10622222;
                                String str137 = str9;
                                str69 = str126;
                                str70 = str58;
                                str71 = str97;
                                str72 = str11522222;
                                str11522222 = str94;
                                str73 = str11722222;
                                str74 = str6;
                                str75 = str137;
                            }
                            if (str83.indexOf("loa") > -1) {
                                database2.mang[i21][1] = "lo dau";
                            } else if (str83.indexOf("lo") > -1) {
                                database2.mang[i21][1] = "lo";
                            } else if (str83.indexOf("dea") > -1) {
                                database2.mang[i21][1] = "de dau db";
                            } else if (str83.indexOf("deb") > -1) {
                                database2.mang[i21][1] = str57;
                            } else if (str83.indexOf("det") > -1) {
                                database2.mang[i21][1] = "de 8";
                            } else if (str83.indexOf(str10622222) > -1) {
                                database2.mang[i21][1] = str58;
                            } else if (str83.indexOf(str8) > -1) {
                                database2.mang[i21][1] = str8;
                            } else if (str83.indexOf("dec") > -1) {
                                database2.mang[i21][1] = "de dau nhat";
                            } else if (str83.indexOf("ded") > -1) {
                                database2.mang[i21][1] = str11822222;
                            } else if (str83.indexOf("de ") > -1) {
                                database2.mang[i21][1] = str57;
                            } else if (str83.indexOf("bca") > -1) {
                                database2.mang[i21][1] = "bc dau";
                            } else if (str83.indexOf(str7) > -1) {
                                database2.mang[i21][1] = str7;
                            } else if (str83.indexOf("xia") > -1) {
                                database2.mang[i21][1] = "xien dau";
                            } else if (str83.indexOf("xi") > -1) {
                                database2.mang[i21][1] = "xi";
                            } else if (str83.indexOf("xqa") > -1) {
                                database2.mang[i21][1] = "xqa";
                            } else if (str83.indexOf(str99) > -1) {
                                database2.mang[i21][1] = str99;
                            } else {
                                if (str83.indexOf(str10822222) > -1) {
                                    try {
                                        str59 = str8;
                                        str68 = str10622222;
                                        database2.mang[i21][1] = str83.trim().substring(0, 4);
                                    } catch (Exception unused17) {
                                        str59 = str8;
                                        str68 = str10622222;
                                        str84 = str83;
                                        str60 = str99;
                                        str61 = str11422222;
                                        str62 = str11822222;
                                        str70 = str58;
                                        str63 = str57;
                                        str64 = str7;
                                        str65 = str10822222;
                                        str71 = str97;
                                        str72 = str11522222;
                                        str66 = str11622222;
                                        str67 = str82;
                                        str11522222 = str94;
                                        str73 = str11722222;
                                        str74 = str81;
                                        str77 = str84;
                                        str76 = str128;
                                        String[][] strArr6222 = database2.mang;
                                        str78 = str74;
                                        strArr6222[i21][0] = strArr6222[i21][0].replaceAll(str73, str78);
                                        String[][] strArr7222 = database2.mang;
                                        strArr7222[i21][0] = strArr7222[i21][0].replaceAll(str66, str78);
                                        database2.mang[i21][0] = str73 + database2.mang[i21][0] + str66;
                                        str128 = str76;
                                        str129 = str77;
                                        i15 = i21;
                                        str11322222 = str70;
                                        str11122222 = str63;
                                        str11722222 = str73;
                                        str126 = str69;
                                        str9 = str75;
                                        str94 = str11522222;
                                        str7 = str64;
                                        str10522222 = str130;
                                        i14 = i16;
                                        str10622222 = str68;
                                        i13 = i6;
                                        str8 = str59;
                                        str6 = str78;
                                        str11522222 = str72;
                                        str11822222 = str62;
                                        str93 = str67;
                                        str97 = str71;
                                        str99 = str60;
                                        str11622222 = str66;
                                        str10822222 = str65;
                                        str11422222 = str61;
                                    }
                                } else {
                                    str59 = str8;
                                    str68 = str10622222;
                                    try {
                                        String[][] strArr8 = database2.mang;
                                        strArr8[i21][1] = strArr8[i21 - 1][1];
                                    } catch (Exception unused18) {
                                        str84 = str83;
                                        str60 = str99;
                                        str61 = str11422222;
                                        str62 = str11822222;
                                        str70 = str58;
                                        str63 = str57;
                                        str64 = str7;
                                        str65 = str10822222;
                                        str71 = str97;
                                        str72 = str11522222;
                                        str66 = str11622222;
                                        str67 = str82;
                                        str11522222 = str94;
                                        str73 = str11722222;
                                        str74 = str81;
                                        str77 = str84;
                                        str76 = str128;
                                        String[][] strArr62222 = database2.mang;
                                        str78 = str74;
                                        strArr62222[i21][0] = strArr62222[i21][0].replaceAll(str73, str78);
                                        String[][] strArr72222 = database2.mang;
                                        strArr72222[i21][0] = strArr72222[i21][0].replaceAll(str66, str78);
                                        database2.mang[i21][0] = str73 + database2.mang[i21][0] + str66;
                                        str128 = str76;
                                        str129 = str77;
                                        i15 = i21;
                                        str11322222 = str70;
                                        str11122222 = str63;
                                        str11722222 = str73;
                                        str126 = str69;
                                        str9 = str75;
                                        str94 = str11522222;
                                        str7 = str64;
                                        str10522222 = str130;
                                        i14 = i16;
                                        str10622222 = str68;
                                        i13 = i6;
                                        str8 = str59;
                                        str6 = str78;
                                        str11522222 = str72;
                                        str11822222 = str62;
                                        str93 = str67;
                                        str97 = str71;
                                        str99 = str60;
                                        str11622222 = str66;
                                        str10822222 = str65;
                                        str11422222 = str61;
                                    }
                                }
                                if (str83.indexOf(str130) != -1) {
                                    try {
                                    } catch (Exception unused19) {
                                        str130 = str130;
                                        str85 = str83;
                                    }
                                    if (str83.trim().indexOf("x ") >= 2) {
                                        database2.mang[i21][2] = str83.substring(0, str83.indexOf(str130)).trim();
                                        database2.mang[i21][3] = str83.substring(str83.indexOf(str130));
                                        if (i21 > 1) {
                                            try {
                                                i7 = i21 - 1;
                                            } catch (Exception unused20) {
                                                str130 = str130;
                                            }
                                            if ((database2.mang[i7][2].indexOf("xi 2 ") > -1 || database2.mang[i7][2].indexOf("xi 3 ") > -1 || database2.mang[i7][2].indexOf("xi 4 ") > -1) && database2.mang[i21][1].indexOf("xi") > -1 && database2.mang[i21][2].indexOf("xi") == -1) {
                                                String[] strArr9 = database2.mang[i21];
                                                StringBuilder sb6 = new StringBuilder();
                                                str130 = str130;
                                                try {
                                                    str85 = str83;
                                                    try {
                                                        sb6.append(database2.mang[i7][2].substring(0, 5));
                                                        sb6.append(database2.mang[i21][2]);
                                                        strArr9[2] = sb6.toString();
                                                        database2.XulyMang(i21);
                                                        database2.BaoLoiTien(i21);
                                                        strArr = database2.mang;
                                                    } catch (Exception unused21) {
                                                        str70 = str58;
                                                        str71 = str97;
                                                        str60 = str99;
                                                        str61 = str11422222;
                                                        str72 = str11522222;
                                                        str11522222 = str94;
                                                        str65 = str10822222;
                                                        str66 = str11622222;
                                                        str73 = str11722222;
                                                        str74 = str81;
                                                        str67 = str82;
                                                        str84 = str85;
                                                        str62 = str11822222;
                                                        str63 = str57;
                                                        str64 = str7;
                                                        str77 = str84;
                                                        str76 = str128;
                                                        String[][] strArr622222 = database2.mang;
                                                        str78 = str74;
                                                        strArr622222[i21][0] = strArr622222[i21][0].replaceAll(str73, str78);
                                                        String[][] strArr722222 = database2.mang;
                                                        strArr722222[i21][0] = strArr722222[i21][0].replaceAll(str66, str78);
                                                        database2.mang[i21][0] = str73 + database2.mang[i21][0] + str66;
                                                        str128 = str76;
                                                        str129 = str77;
                                                        i15 = i21;
                                                        str11322222 = str70;
                                                        str11122222 = str63;
                                                        str11722222 = str73;
                                                        str126 = str69;
                                                        str9 = str75;
                                                        str94 = str11522222;
                                                        str7 = str64;
                                                        str10522222 = str130;
                                                        i14 = i16;
                                                        str10622222 = str68;
                                                        i13 = i6;
                                                        str8 = str59;
                                                        str6 = str78;
                                                        str11522222 = str72;
                                                        str11822222 = str62;
                                                        str93 = str67;
                                                        str97 = str71;
                                                        str99 = str60;
                                                        str11622222 = str66;
                                                        str10822222 = str65;
                                                        str11422222 = str61;
                                                    }
                                                } catch (Exception unused22) {
                                                    str85 = str83;
                                                    str60 = str99;
                                                    str61 = str11422222;
                                                    str70 = str58;
                                                    str65 = str10822222;
                                                    str71 = str97;
                                                    str72 = str11522222;
                                                    str66 = str11622222;
                                                    str11522222 = str94;
                                                    str73 = str11722222;
                                                    str74 = str81;
                                                    str67 = str82;
                                                    str84 = str85;
                                                    str62 = str11822222;
                                                    str63 = str57;
                                                    str64 = str7;
                                                    str77 = str84;
                                                    str76 = str128;
                                                    String[][] strArr6222222 = database2.mang;
                                                    str78 = str74;
                                                    strArr6222222[i21][0] = strArr6222222[i21][0].replaceAll(str73, str78);
                                                    String[][] strArr7222222 = database2.mang;
                                                    strArr7222222[i21][0] = strArr7222222[i21][0].replaceAll(str66, str78);
                                                    database2.mang[i21][0] = str73 + database2.mang[i21][0] + str66;
                                                    str128 = str76;
                                                    str129 = str77;
                                                    i15 = i21;
                                                    str11322222 = str70;
                                                    str11122222 = str63;
                                                    str11722222 = str73;
                                                    str126 = str69;
                                                    str9 = str75;
                                                    str94 = str11522222;
                                                    str7 = str64;
                                                    str10522222 = str130;
                                                    i14 = i16;
                                                    str10622222 = str68;
                                                    i13 = i6;
                                                    str8 = str59;
                                                    str6 = str78;
                                                    str11522222 = str72;
                                                    str11822222 = str62;
                                                    str93 = str67;
                                                    str97 = str71;
                                                    str99 = str60;
                                                    str11622222 = str66;
                                                    str10822222 = str65;
                                                    str11422222 = str61;
                                                }
                                                if (strArr[i21][1] != "lo") {
                                                    try {
                                                    } catch (Exception unused23) {
                                                        str60 = str99;
                                                        str61 = str11422222;
                                                        str70 = str58;
                                                        str65 = str10822222;
                                                        str71 = str97;
                                                    }
                                                    if (strArr[i21][1].indexOf(str97) <= -1) {
                                                        str70 = str58;
                                                        try {
                                                        } catch (Exception unused24) {
                                                            str71 = str97;
                                                            str60 = str99;
                                                            str61 = str11422222;
                                                            str72 = str11522222;
                                                            str11522222 = str94;
                                                            str65 = str10822222;
                                                        }
                                                        if (database2.mang[i21][1].indexOf(str70) <= -1 && database2.mang[i21][1].indexOf(str7) <= -1) {
                                                            if (!database2.mang[i21][1].startsWith("xi")) {
                                                                try {
                                                                } catch (Exception unused25) {
                                                                    str71 = str97;
                                                                    str60 = str99;
                                                                    str61 = str11422222;
                                                                    str72 = str11522222;
                                                                    str11522222 = str94;
                                                                    str65 = str10822222;
                                                                    str66 = str11622222;
                                                                    str73 = str11722222;
                                                                    str74 = str81;
                                                                    str67 = str82;
                                                                    str84 = str85;
                                                                    str62 = str11822222;
                                                                    str63 = str57;
                                                                    str64 = str7;
                                                                    str77 = str84;
                                                                    str76 = str128;
                                                                    String[][] strArr62222222 = database2.mang;
                                                                    str78 = str74;
                                                                    strArr62222222[i21][0] = strArr62222222[i21][0].replaceAll(str73, str78);
                                                                    String[][] strArr72222222 = database2.mang;
                                                                    strArr72222222[i21][0] = strArr72222222[i21][0].replaceAll(str66, str78);
                                                                    database2.mang[i21][0] = str73 + database2.mang[i21][0] + str66;
                                                                    str128 = str76;
                                                                    str129 = str77;
                                                                    i15 = i21;
                                                                    str11322222 = str70;
                                                                    str11122222 = str63;
                                                                    str11722222 = str73;
                                                                    str126 = str69;
                                                                    str9 = str75;
                                                                    str94 = str11522222;
                                                                    str7 = str64;
                                                                    str10522222 = str130;
                                                                    i14 = i16;
                                                                    str10622222 = str68;
                                                                    i13 = i6;
                                                                    str8 = str59;
                                                                    str6 = str78;
                                                                    str11522222 = str72;
                                                                    str11822222 = str62;
                                                                    str93 = str67;
                                                                    str97 = str71;
                                                                    str99 = str60;
                                                                    str11622222 = str66;
                                                                    str10822222 = str65;
                                                                    str11422222 = str61;
                                                                }
                                                                if (!database2.mang[i21][1].startsWith(str99) && !database2.mang[i21][1].startsWith(str10822222)) {
                                                                    str71 = str97;
                                                                    str60 = str99;
                                                                    str61 = str11422222;
                                                                    str72 = str11522222;
                                                                    str62 = str11822222;
                                                                    str63 = str57;
                                                                    str64 = str7;
                                                                    str11522222 = str94;
                                                                    str65 = str10822222;
                                                                    str66 = str11622222;
                                                                    str73 = str11722222;
                                                                    str74 = str81;
                                                                    str67 = str82;
                                                                    str76 = str128;
                                                                    database2.BaoLoiDan(i21);
                                                                    str84 = str85;
                                                                    str129 = str84;
                                                                    str128 = str76;
                                                                    str78 = str74;
                                                                    i15 = i21;
                                                                    str11322222 = str70;
                                                                    str11122222 = str63;
                                                                    str11722222 = str73;
                                                                    str126 = str69;
                                                                    str9 = str75;
                                                                    str94 = str11522222;
                                                                    str7 = str64;
                                                                    str10522222 = str130;
                                                                    i14 = i16;
                                                                    str10622222 = str68;
                                                                    i13 = i6;
                                                                    str8 = str59;
                                                                    str6 = str78;
                                                                    str11522222 = str72;
                                                                    str11822222 = str62;
                                                                    str93 = str67;
                                                                    str97 = str71;
                                                                    str99 = str60;
                                                                    str11622222 = str66;
                                                                    str10822222 = str65;
                                                                    str11422222 = str61;
                                                                }
                                                            }
                                                            String str138 = str11422222;
                                                            if (database2.mang[i21][4].indexOf(str138) == -1) {
                                                                String str139 = database2.mang[i21][4];
                                                                String[] split = str139.split(str94);
                                                                str65 = str10822222;
                                                                int i23 = 0;
                                                                while (i23 < split.length) {
                                                                    try {
                                                                        try {
                                                                            str71 = str97;
                                                                            if (split[i23].replaceAll(str82, str81).length() == 2) {
                                                                                try {
                                                                                    if (Congthuc.isNumeric(split[i23].replaceAll(str82, str81))) {
                                                                                        i23++;
                                                                                        str97 = str71;
                                                                                    }
                                                                                } catch (Exception unused26) {
                                                                                    str61 = str138;
                                                                                    str60 = str99;
                                                                                    str72 = str11522222;
                                                                                    str11522222 = str94;
                                                                                    str66 = str11622222;
                                                                                    str73 = str11722222;
                                                                                    str62 = str11822222;
                                                                                    str63 = str57;
                                                                                    str74 = str81;
                                                                                    str67 = str82;
                                                                                    str64 = str7;
                                                                                    str84 = str85;
                                                                                    str77 = str84;
                                                                                    str76 = str128;
                                                                                    String[][] strArr622222222 = database2.mang;
                                                                                    str78 = str74;
                                                                                    strArr622222222[i21][0] = strArr622222222[i21][0].replaceAll(str73, str78);
                                                                                    String[][] strArr722222222 = database2.mang;
                                                                                    strArr722222222[i21][0] = strArr722222222[i21][0].replaceAll(str66, str78);
                                                                                    database2.mang[i21][0] = str73 + database2.mang[i21][0] + str66;
                                                                                    str128 = str76;
                                                                                    str129 = str77;
                                                                                    i15 = i21;
                                                                                    str11322222 = str70;
                                                                                    str11122222 = str63;
                                                                                    str11722222 = str73;
                                                                                    str126 = str69;
                                                                                    str9 = str75;
                                                                                    str94 = str11522222;
                                                                                    str7 = str64;
                                                                                    str10522222 = str130;
                                                                                    i14 = i16;
                                                                                    str10622222 = str68;
                                                                                    i13 = i6;
                                                                                    str8 = str59;
                                                                                    str6 = str78;
                                                                                    str11522222 = str72;
                                                                                    str11822222 = str62;
                                                                                    str93 = str67;
                                                                                    str97 = str71;
                                                                                    str99 = str60;
                                                                                    str11622222 = str66;
                                                                                    str10822222 = str65;
                                                                                    str11422222 = str61;
                                                                                }
                                                                            }
                                                                            z2 = true;
                                                                            break;
                                                                        } catch (Exception unused27) {
                                                                            str71 = str97;
                                                                        }
                                                                    } catch (Exception unused28) {
                                                                        str61 = str138;
                                                                        str71 = str97;
                                                                        str60 = str99;
                                                                        str72 = str11522222;
                                                                        str11522222 = str94;
                                                                        str66 = str11622222;
                                                                        str73 = str11722222;
                                                                        str62 = str11822222;
                                                                        str63 = str57;
                                                                        str74 = str81;
                                                                        str67 = str82;
                                                                        str64 = str7;
                                                                        str84 = str85;
                                                                        str77 = str84;
                                                                        str76 = str128;
                                                                        String[][] strArr6222222222 = database2.mang;
                                                                        str78 = str74;
                                                                        strArr6222222222[i21][0] = strArr6222222222[i21][0].replaceAll(str73, str78);
                                                                        String[][] strArr7222222222 = database2.mang;
                                                                        strArr7222222222[i21][0] = strArr7222222222[i21][0].replaceAll(str66, str78);
                                                                        database2.mang[i21][0] = str73 + database2.mang[i21][0] + str66;
                                                                        str128 = str76;
                                                                        str129 = str77;
                                                                        i15 = i21;
                                                                        str11322222 = str70;
                                                                        str11122222 = str63;
                                                                        str11722222 = str73;
                                                                        str126 = str69;
                                                                        str9 = str75;
                                                                        str94 = str11522222;
                                                                        str7 = str64;
                                                                        str10522222 = str130;
                                                                        i14 = i16;
                                                                        str10622222 = str68;
                                                                        i13 = i6;
                                                                        str8 = str59;
                                                                        str6 = str78;
                                                                        str11522222 = str72;
                                                                        str11822222 = str62;
                                                                        str93 = str67;
                                                                        str97 = str71;
                                                                        str99 = str60;
                                                                        str11622222 = str66;
                                                                        str10822222 = str65;
                                                                        str11422222 = str61;
                                                                    }
                                                                }
                                                                str71 = str97;
                                                                z2 = false;
                                                                if (!z2 && split.length < 5) {
                                                                    str139 = Congthuc.XulySo(str139);
                                                                }
                                                                String[] split2 = str139.split(str94);
                                                                int i24 = 0;
                                                                while (i24 < split2.length) {
                                                                    try {
                                                                        String XulySo = Congthuc.XulySo(split2[i24]);
                                                                        if (XulySo.length() >= 5 && XulySo.length() <= 12 && XulySo.indexOf(str138) <= -1) {
                                                                            if (database2.mang[i21][1] == str99) {
                                                                                try {
                                                                                    if (XulySo.length() < 8) {
                                                                                        String[] strArr10 = database2.mang[i21];
                                                                                        StringBuilder sb7 = new StringBuilder();
                                                                                        str72 = str11522222;
                                                                                        try {
                                                                                            sb7.append(str72);
                                                                                            str11522222 = str94;
                                                                                            str89 = str85;
                                                                                        } catch (Exception unused29) {
                                                                                            str11522222 = str94;
                                                                                            str60 = str99;
                                                                                            str61 = str138;
                                                                                            str66 = str11622222;
                                                                                            str73 = str11722222;
                                                                                            str62 = str11822222;
                                                                                            str63 = str57;
                                                                                            str74 = str81;
                                                                                            str67 = str82;
                                                                                            str64 = str7;
                                                                                            str84 = str85;
                                                                                            str77 = str84;
                                                                                            str76 = str128;
                                                                                            String[][] strArr62222222222 = database2.mang;
                                                                                            str78 = str74;
                                                                                            strArr62222222222[i21][0] = strArr62222222222[i21][0].replaceAll(str73, str78);
                                                                                            String[][] strArr72222222222 = database2.mang;
                                                                                            strArr72222222222[i21][0] = strArr72222222222[i21][0].replaceAll(str66, str78);
                                                                                            database2.mang[i21][0] = str73 + database2.mang[i21][0] + str66;
                                                                                            str128 = str76;
                                                                                            str129 = str77;
                                                                                            i15 = i21;
                                                                                            str11322222 = str70;
                                                                                            str11122222 = str63;
                                                                                            str11722222 = str73;
                                                                                            str126 = str69;
                                                                                            str9 = str75;
                                                                                            str94 = str11522222;
                                                                                            str7 = str64;
                                                                                            str10522222 = str130;
                                                                                            i14 = i16;
                                                                                            str10622222 = str68;
                                                                                            i13 = i6;
                                                                                            str8 = str59;
                                                                                            str6 = str78;
                                                                                            str11522222 = str72;
                                                                                            str11822222 = str62;
                                                                                            str93 = str67;
                                                                                            str97 = str71;
                                                                                            str99 = str60;
                                                                                            str11622222 = str66;
                                                                                            str10822222 = str65;
                                                                                            str11422222 = str61;
                                                                                        }
                                                                                        try {
                                                                                            sb7.append(str89);
                                                                                            strArr10[4] = sb7.toString();
                                                                                            str85 = str89;
                                                                                            str60 = str99;
                                                                                            i24++;
                                                                                            str94 = str11522222;
                                                                                            str99 = str60;
                                                                                            str11522222 = str72;
                                                                                        } catch (Exception unused30) {
                                                                                            str85 = str89;
                                                                                            str60 = str99;
                                                                                            str61 = str138;
                                                                                            str66 = str11622222;
                                                                                            str73 = str11722222;
                                                                                            str62 = str11822222;
                                                                                            str63 = str57;
                                                                                            str74 = str81;
                                                                                            str67 = str82;
                                                                                            str64 = str7;
                                                                                            str84 = str85;
                                                                                            str77 = str84;
                                                                                            str76 = str128;
                                                                                            String[][] strArr622222222222 = database2.mang;
                                                                                            str78 = str74;
                                                                                            strArr622222222222[i21][0] = strArr622222222222[i21][0].replaceAll(str73, str78);
                                                                                            String[][] strArr722222222222 = database2.mang;
                                                                                            strArr722222222222[i21][0] = strArr722222222222[i21][0].replaceAll(str66, str78);
                                                                                            database2.mang[i21][0] = str73 + database2.mang[i21][0] + str66;
                                                                                            str128 = str76;
                                                                                            str129 = str77;
                                                                                            i15 = i21;
                                                                                            str11322222 = str70;
                                                                                            str11122222 = str63;
                                                                                            str11722222 = str73;
                                                                                            str126 = str69;
                                                                                            str9 = str75;
                                                                                            str94 = str11522222;
                                                                                            str7 = str64;
                                                                                            str10522222 = str130;
                                                                                            i14 = i16;
                                                                                            str10622222 = str68;
                                                                                            i13 = i6;
                                                                                            str8 = str59;
                                                                                            str6 = str78;
                                                                                            str11522222 = str72;
                                                                                            str11822222 = str62;
                                                                                            str93 = str67;
                                                                                            str97 = str71;
                                                                                            str99 = str60;
                                                                                            str11622222 = str66;
                                                                                            str10822222 = str65;
                                                                                            str11422222 = str61;
                                                                                        }
                                                                                    }
                                                                                } catch (Exception unused31) {
                                                                                    str72 = str11522222;
                                                                                    str11522222 = str94;
                                                                                    str89 = str85;
                                                                                }
                                                                            }
                                                                            str72 = str11522222;
                                                                            str11522222 = str94;
                                                                            String str140 = str85;
                                                                            String[] split3 = XulySo.split(str82);
                                                                            str85 = str140;
                                                                            int i25 = 0;
                                                                            while (i25 < split3.length) {
                                                                                try {
                                                                                    str60 = str99;
                                                                                    if (split3[i25].length() == 2) {
                                                                                        try {
                                                                                            if (Congthuc.isNumeric(split3[i25])) {
                                                                                                i25++;
                                                                                                str99 = str60;
                                                                                            }
                                                                                        } catch (Exception unused32) {
                                                                                        }
                                                                                    }
                                                                                    if (database2.mang[i21][2].length() > 4) {
                                                                                        database2.mang[i21][4] = str72 + database2.mang[i21][2];
                                                                                    } else {
                                                                                        database2.mang[i21][4] = str72 + database2.mang[i21][0];
                                                                                    }
                                                                                    i24++;
                                                                                    str94 = str11522222;
                                                                                    str99 = str60;
                                                                                    str11522222 = str72;
                                                                                } catch (Exception unused33) {
                                                                                    str60 = str99;
                                                                                    str61 = str138;
                                                                                    str66 = str11622222;
                                                                                    str73 = str11722222;
                                                                                    str62 = str11822222;
                                                                                    str63 = str57;
                                                                                    str74 = str81;
                                                                                    str67 = str82;
                                                                                    str64 = str7;
                                                                                    str84 = str85;
                                                                                    str77 = str84;
                                                                                    str76 = str128;
                                                                                    String[][] strArr6222222222222 = database2.mang;
                                                                                    str78 = str74;
                                                                                    strArr6222222222222[i21][0] = strArr6222222222222[i21][0].replaceAll(str73, str78);
                                                                                    String[][] strArr7222222222222 = database2.mang;
                                                                                    strArr7222222222222[i21][0] = strArr7222222222222[i21][0].replaceAll(str66, str78);
                                                                                    database2.mang[i21][0] = str73 + database2.mang[i21][0] + str66;
                                                                                    str128 = str76;
                                                                                    str129 = str77;
                                                                                    i15 = i21;
                                                                                    str11322222 = str70;
                                                                                    str11122222 = str63;
                                                                                    str11722222 = str73;
                                                                                    str126 = str69;
                                                                                    str9 = str75;
                                                                                    str94 = str11522222;
                                                                                    str7 = str64;
                                                                                    str10522222 = str130;
                                                                                    i14 = i16;
                                                                                    str10622222 = str68;
                                                                                    i13 = i6;
                                                                                    str8 = str59;
                                                                                    str6 = str78;
                                                                                    str11522222 = str72;
                                                                                    str11822222 = str62;
                                                                                    str93 = str67;
                                                                                    str97 = str71;
                                                                                    str99 = str60;
                                                                                    str11622222 = str66;
                                                                                    str10822222 = str65;
                                                                                    str11422222 = str61;
                                                                                }
                                                                            }
                                                                            str60 = str99;
                                                                            i24++;
                                                                            str94 = str11522222;
                                                                            str99 = str60;
                                                                            str11522222 = str72;
                                                                        }
                                                                        str60 = str99;
                                                                        str72 = str11522222;
                                                                        str11522222 = str94;
                                                                        if (split2[i24].length() > 4) {
                                                                            database2.mang[i21][4] = str72 + database2.mang[i21][2];
                                                                        } else {
                                                                            database2.mang[i21][4] = str72 + database2.mang[i21][0];
                                                                        }
                                                                    } catch (Exception unused34) {
                                                                        str60 = str99;
                                                                        str72 = str11522222;
                                                                        str11522222 = str94;
                                                                    }
                                                                    if (database2.mang[i21][5].indexOf(str138) == -1) {
                                                                        if (database2.caidat_tg.getInt("xien_nhan") == 1 && database2.mang[i21][3].indexOf("d") > -1) {
                                                                            try {
                                                                                database2.mang[i21][5] = (Integer.parseInt(str133) * 10) + str81;
                                                                            } catch (Exception unused35) {
                                                                                database2.mang[i21][5] = str72 + database2.mang[i21][3];
                                                                            }
                                                                        } else if (database2.caidat_tg.getInt("xien_nhan") == 2) {
                                                                            try {
                                                                                database2.mang[i21][5] = (Integer.parseInt(str133) * 10) + str81;
                                                                            } catch (Exception unused36) {
                                                                                database2.mang[i21][5] = str72 + database2.mang[i21][3];
                                                                            }
                                                                        }
                                                                        str61 = str138;
                                                                        str66 = str11622222;
                                                                        str73 = str11722222;
                                                                        str62 = str11822222;
                                                                        str63 = str57;
                                                                        str74 = str81;
                                                                        str67 = str82;
                                                                        str64 = str7;
                                                                        str84 = str85;
                                                                        str77 = str84;
                                                                        str76 = str128;
                                                                        String[][] strArr62222222222222 = database2.mang;
                                                                        str78 = str74;
                                                                        strArr62222222222222[i21][0] = strArr62222222222222[i21][0].replaceAll(str73, str78);
                                                                        String[][] strArr72222222222222 = database2.mang;
                                                                        strArr72222222222222[i21][0] = strArr72222222222222[i21][0].replaceAll(str66, str78);
                                                                        database2.mang[i21][0] = str73 + database2.mang[i21][0] + str66;
                                                                        str128 = str76;
                                                                        str129 = str77;
                                                                        i15 = i21;
                                                                        str11322222 = str70;
                                                                        str11122222 = str63;
                                                                        str11722222 = str73;
                                                                        str126 = str69;
                                                                        str9 = str75;
                                                                        str94 = str11522222;
                                                                        str7 = str64;
                                                                        str10522222 = str130;
                                                                        i14 = i16;
                                                                        str10622222 = str68;
                                                                        i13 = i6;
                                                                        str8 = str59;
                                                                        str6 = str78;
                                                                        str11522222 = str72;
                                                                        str11822222 = str62;
                                                                        str93 = str67;
                                                                        str97 = str71;
                                                                        str99 = str60;
                                                                        str11622222 = str66;
                                                                        str10822222 = str65;
                                                                        str11422222 = str61;
                                                                    }
                                                                    i8 = 0;
                                                                    while (i8 < split2.length) {
                                                                        try {
                                                                            try {
                                                                                str88 = Congthuc.XulySo(split2[i8]);
                                                                            } catch (Exception unused37) {
                                                                                database2.mang[i21][4] = str72 + split2[i8];
                                                                                str88 = str128;
                                                                            }
                                                                            try {
                                                                                if (str88.indexOf(str138) == -1) {
                                                                                    String[] split4 = str88.split(str82);
                                                                                    int i26 = 0;
                                                                                    boolean z5 = false;
                                                                                    while (true) {
                                                                                        strArr5 = split2;
                                                                                        if (i26 >= split4.length) {
                                                                                            break;
                                                                                        }
                                                                                        str61 = str138;
                                                                                        try {
                                                                                            if (str88.length() - str88.replaceAll(split4[i26], str81).length() > 2) {
                                                                                                z5 = true;
                                                                                            }
                                                                                            i26++;
                                                                                            split2 = strArr5;
                                                                                            str138 = str61;
                                                                                        } catch (Exception unused38) {
                                                                                            str128 = str88;
                                                                                            str77 = str85;
                                                                                            str66 = str11622222;
                                                                                            str73 = str11722222;
                                                                                            str62 = str11822222;
                                                                                            str63 = str57;
                                                                                            str64 = str7;
                                                                                            str74 = str81;
                                                                                            str67 = str82;
                                                                                            str76 = str128;
                                                                                            String[][] strArr622222222222222 = database2.mang;
                                                                                            str78 = str74;
                                                                                            strArr622222222222222[i21][0] = strArr622222222222222[i21][0].replaceAll(str73, str78);
                                                                                            String[][] strArr722222222222222 = database2.mang;
                                                                                            strArr722222222222222[i21][0] = strArr722222222222222[i21][0].replaceAll(str66, str78);
                                                                                            database2.mang[i21][0] = str73 + database2.mang[i21][0] + str66;
                                                                                            str128 = str76;
                                                                                            str129 = str77;
                                                                                            i15 = i21;
                                                                                            str11322222 = str70;
                                                                                            str11122222 = str63;
                                                                                            str11722222 = str73;
                                                                                            str126 = str69;
                                                                                            str9 = str75;
                                                                                            str94 = str11522222;
                                                                                            str7 = str64;
                                                                                            str10522222 = str130;
                                                                                            i14 = i16;
                                                                                            str10622222 = str68;
                                                                                            i13 = i6;
                                                                                            str8 = str59;
                                                                                            str6 = str78;
                                                                                            str11522222 = str72;
                                                                                            str11822222 = str62;
                                                                                            str93 = str67;
                                                                                            str97 = str71;
                                                                                            str99 = str60;
                                                                                            str11622222 = str66;
                                                                                            str10822222 = str65;
                                                                                            str11422222 = str61;
                                                                                        }
                                                                                    }
                                                                                    str61 = str138;
                                                                                    if (str88.length() >= 5 && str88.length() <= 12) {
                                                                                        if (z5) {
                                                                                            database2.mang[i21][4] = str72 + str88.substring(0, 2);
                                                                                        } else {
                                                                                            i8++;
                                                                                            str128 = str88;
                                                                                            split2 = strArr5;
                                                                                            str138 = str61;
                                                                                        }
                                                                                    }
                                                                                    database2.mang[i21][4] = str72 + database2.mang[i21][2];
                                                                                } else {
                                                                                    str61 = str138;
                                                                                    database2.mang[i21][4] = str72 + database2.mang[i21][2];
                                                                                }
                                                                                str128 = str88;
                                                                                break;
                                                                            } catch (Exception unused39) {
                                                                                str61 = str138;
                                                                            }
                                                                        } catch (Exception unused40) {
                                                                            str61 = str138;
                                                                        }
                                                                    }
                                                                    str61 = str138;
                                                                }
                                                                str60 = str99;
                                                                str72 = str11522222;
                                                                str11522222 = str94;
                                                                if (database2.mang[i21][5].indexOf(str138) == -1) {
                                                                }
                                                                i8 = 0;
                                                                while (i8 < split2.length) {
                                                                }
                                                                str61 = str138;
                                                            } else {
                                                                str61 = str138;
                                                                str65 = str10822222;
                                                                str71 = str97;
                                                                str60 = str99;
                                                                str72 = str11522222;
                                                                str11522222 = str94;
                                                            }
                                                            str66 = str11622222;
                                                            str73 = str11722222;
                                                            str62 = str11822222;
                                                            str63 = str57;
                                                            str64 = str7;
                                                            str74 = str81;
                                                            str67 = str82;
                                                            str76 = str128;
                                                            database2.BaoLoiDan(i21);
                                                            str84 = str85;
                                                            str129 = str84;
                                                            str128 = str76;
                                                            str78 = str74;
                                                            i15 = i21;
                                                            str11322222 = str70;
                                                            str11122222 = str63;
                                                            str11722222 = str73;
                                                            str126 = str69;
                                                            str9 = str75;
                                                            str94 = str11522222;
                                                            str7 = str64;
                                                            str10522222 = str130;
                                                            i14 = i16;
                                                            str10622222 = str68;
                                                            i13 = i6;
                                                            str8 = str59;
                                                            str6 = str78;
                                                            str11522222 = str72;
                                                            str11822222 = str62;
                                                            str93 = str67;
                                                            str97 = str71;
                                                            str99 = str60;
                                                            str11622222 = str66;
                                                            str10822222 = str65;
                                                            str11422222 = str61;
                                                        }
                                                        str71 = str97;
                                                        str60 = str99;
                                                        str61 = str11422222;
                                                        str72 = str11522222;
                                                        str11522222 = str94;
                                                        str65 = str10822222;
                                                        strArr2 = database2.mang;
                                                        if (strArr2[i21][1] == "lo") {
                                                            try {
                                                            } catch (Exception unused41) {
                                                                str66 = str11622222;
                                                                str73 = str11722222;
                                                                str74 = str81;
                                                                str67 = str82;
                                                                str84 = str85;
                                                                str62 = str11822222;
                                                                str63 = str57;
                                                                str64 = str7;
                                                                str77 = str84;
                                                                str76 = str128;
                                                                String[][] strArr6222222222222222 = database2.mang;
                                                                str78 = str74;
                                                                strArr6222222222222222[i21][0] = strArr6222222222222222[i21][0].replaceAll(str73, str78);
                                                                String[][] strArr7222222222222222 = database2.mang;
                                                                strArr7222222222222222[i21][0] = strArr7222222222222222[i21][0].replaceAll(str66, str78);
                                                                database2.mang[i21][0] = str73 + database2.mang[i21][0] + str66;
                                                                str128 = str76;
                                                                str129 = str77;
                                                                i15 = i21;
                                                                str11322222 = str70;
                                                                str11122222 = str63;
                                                                str11722222 = str73;
                                                                str126 = str69;
                                                                str9 = str75;
                                                                str94 = str11522222;
                                                                str7 = str64;
                                                                str10522222 = str130;
                                                                i14 = i16;
                                                                str10622222 = str68;
                                                                i13 = i6;
                                                                str8 = str59;
                                                                str6 = str78;
                                                                str11522222 = str72;
                                                                str11822222 = str62;
                                                                str93 = str67;
                                                                str97 = str71;
                                                                str99 = str60;
                                                                str11622222 = str66;
                                                                str10822222 = str65;
                                                                str11422222 = str61;
                                                            }
                                                            if (strArr2[i21][3].indexOf("tr") > -1) {
                                                                database2.mang[i21][5] = str72 + database2.mang[i21][3];
                                                                String[][] charSequenceArr = database2.mang;
                                                                String[] strArr11 = charSequenceArr[i21];
                                                                String str141 = charSequenceArr[i21][0];
                                                                CharSequence charSequence = charSequenceArr[i21][3];
                                                                StringBuilder sb8 = new StringBuilder();
                                                                str73 = str11722222;
                                                                try {
                                                                    sb8.append(str73);
                                                                    sb8.append(database2.mang[i21][3]);
                                                                    str66 = str11622222;
                                                                    try {
                                                                        sb8.append(str66);
                                                                        strArr11[0] = str141.replace(charSequence, sb8.toString());
                                                                    } catch (Exception unused42) {
                                                                        str74 = str81;
                                                                        str67 = str82;
                                                                        str62 = str11822222;
                                                                        str63 = str57;
                                                                    }
                                                                } catch (Exception unused43) {
                                                                    str66 = str11622222;
                                                                    str74 = str81;
                                                                    str67 = str82;
                                                                    str84 = str85;
                                                                    str62 = str11822222;
                                                                    str63 = str57;
                                                                    str64 = str7;
                                                                    str77 = str84;
                                                                    str76 = str128;
                                                                    String[][] strArr62222222222222222 = database2.mang;
                                                                    str78 = str74;
                                                                    strArr62222222222222222[i21][0] = strArr62222222222222222[i21][0].replaceAll(str73, str78);
                                                                    String[][] strArr72222222222222222 = database2.mang;
                                                                    strArr72222222222222222[i21][0] = strArr72222222222222222[i21][0].replaceAll(str66, str78);
                                                                    database2.mang[i21][0] = str73 + database2.mang[i21][0] + str66;
                                                                    str128 = str76;
                                                                    str129 = str77;
                                                                    i15 = i21;
                                                                    str11322222 = str70;
                                                                    str11122222 = str63;
                                                                    str11722222 = str73;
                                                                    str126 = str69;
                                                                    str9 = str75;
                                                                    str94 = str11522222;
                                                                    str7 = str64;
                                                                    str10522222 = str130;
                                                                    i14 = i16;
                                                                    str10622222 = str68;
                                                                    i13 = i6;
                                                                    str8 = str59;
                                                                    str6 = str78;
                                                                    str11522222 = str72;
                                                                    str11822222 = str62;
                                                                    str93 = str67;
                                                                    str97 = str71;
                                                                    str99 = str60;
                                                                    str11622222 = str66;
                                                                    str10822222 = str65;
                                                                    str11422222 = str61;
                                                                }
                                                                if (MainActivity.jSon_Setting.getInt("canhbaodonvi") != 1) {
                                                                    String[][] strArr12 = database2.mang;
                                                                    if (strArr12[i21][1] == "lo" && Integer.parseInt(strArr12[i21][5]) > 1000 && database2.mang[i21][3].indexOf("d") == -1) {
                                                                        database2.mang[i21][5] = str72 + database2.mang[i21][3];
                                                                        String[][] charSequenceArr2 = database2.mang;
                                                                        charSequenceArr2[i21][0] = charSequenceArr2[i21][0].replace(charSequenceArr2[i21][3], str73 + database2.mang[i21][3] + str66);
                                                                    }
                                                                    String[][] strArr13 = database2.mang;
                                                                    str63 = str57;
                                                                    if (strArr13[i21][1] == str63 || strArr13[i21][1] == "de dau db" || strArr13[i21][1] == str70 || strArr13[i21][1] == "de 8") {
                                                                        str62 = str11822222;
                                                                    } else {
                                                                        str62 = str11822222;
                                                                        if (strArr13[i21][1] != str62) {
                                                                        }
                                                                    }
                                                                    try {
                                                                    } catch (Exception unused44) {
                                                                        str64 = str7;
                                                                        str74 = str81;
                                                                        str67 = str82;
                                                                        str86 = str60;
                                                                        str60 = str86;
                                                                        str76 = str128;
                                                                        str77 = str85;
                                                                        String[][] strArr622222222222222222 = database2.mang;
                                                                        str78 = str74;
                                                                        strArr622222222222222222[i21][0] = strArr622222222222222222[i21][0].replaceAll(str73, str78);
                                                                        String[][] strArr722222222222222222 = database2.mang;
                                                                        strArr722222222222222222[i21][0] = strArr722222222222222222[i21][0].replaceAll(str66, str78);
                                                                        database2.mang[i21][0] = str73 + database2.mang[i21][0] + str66;
                                                                        str128 = str76;
                                                                        str129 = str77;
                                                                        i15 = i21;
                                                                        str11322222 = str70;
                                                                        str11122222 = str63;
                                                                        str11722222 = str73;
                                                                        str126 = str69;
                                                                        str9 = str75;
                                                                        str94 = str11522222;
                                                                        str7 = str64;
                                                                        str10522222 = str130;
                                                                        i14 = i16;
                                                                        str10622222 = str68;
                                                                        i13 = i6;
                                                                        str8 = str59;
                                                                        str6 = str78;
                                                                        str11522222 = str72;
                                                                        str11822222 = str62;
                                                                        str93 = str67;
                                                                        str97 = str71;
                                                                        str99 = str60;
                                                                        str11622222 = str66;
                                                                        str10822222 = str65;
                                                                        str11422222 = str61;
                                                                    }
                                                                    if (Integer.parseInt(strArr13[i21][5]) > 5000) {
                                                                        if (database2.mang[i21][3].indexOf("n") == -1 && database2.mang[i21][3].indexOf("tr") == -1 && database2.mang[i21][3].indexOf("k") == -1) {
                                                                            database2.mang[i21][5] = str72 + database2.mang[i21][3];
                                                                            String[][] charSequenceArr3 = database2.mang;
                                                                            String[] strArr14 = charSequenceArr3[i21];
                                                                            String str142 = charSequenceArr3[i21][0];
                                                                            CharSequence charSequence2 = charSequenceArr3[i21][3];
                                                                            str67 = str82;
                                                                            try {
                                                                                StringBuilder sb9 = new StringBuilder();
                                                                                sb9.append(str73);
                                                                                str74 = str81;
                                                                                sb9.append(database2.mang[i21][3]);
                                                                                sb9.append(str66);
                                                                                strArr14[0] = str142.replace(charSequence2, sb9.toString());
                                                                                strArr3 = database2.mang;
                                                                                if (strArr3[i21][1] == str7) {
                                                                                    try {
                                                                                        if (Integer.parseInt(strArr3[i21][5]) > 2000 && database2.mang[i21][3].indexOf("n") == -1 && database2.mang[i21][3].indexOf("tr") == -1 && database2.mang[i21][3].indexOf("k") == -1) {
                                                                                            database2.mang[i21][5] = str72 + database2.mang[i21][3];
                                                                                            String[][] charSequenceArr4 = database2.mang;
                                                                                            charSequenceArr4[i21][0] = charSequenceArr4[i21][0].replace(charSequenceArr4[i21][3], str73 + database2.mang[i21][3] + str66);
                                                                                        }
                                                                                    } catch (Exception unused45) {
                                                                                    }
                                                                                }
                                                                                strArr4 = database2.mang;
                                                                                if (strArr4[i21][1] == "xi") {
                                                                                    str86 = str60;
                                                                                    if (strArr4[i21][1] != str86) {
                                                                                        str87 = str59;
                                                                                    } else {
                                                                                        str87 = str59;
                                                                                    }
                                                                                } else {
                                                                                    str87 = str59;
                                                                                    str86 = str60;
                                                                                }
                                                                            } catch (Exception unused46) {
                                                                                str74 = str81;
                                                                            }
                                                                            if (Integer.parseInt(strArr4[i21][5]) > 2000 && database2.mang[i21][3].indexOf("n") == -1 && database2.mang[i21][3].indexOf("d") == -1 && database2.mang[i21][3].indexOf("tr") == -1 && database2.mang[i21][3].indexOf("k") == -1) {
                                                                                database2.mang[i21][5] = str72 + database2.mang[i21][3];
                                                                                String[][] charSequenceArr5 = database2.mang;
                                                                                String[] strArr15 = charSequenceArr5[i21];
                                                                                String str143 = charSequenceArr5[i21][0];
                                                                                CharSequence charSequence3 = charSequenceArr5[i21][3];
                                                                                str64 = str7;
                                                                                try {
                                                                                    StringBuilder sb10 = new StringBuilder();
                                                                                    sb10.append(str73);
                                                                                    str59 = str87;
                                                                                    sb10.append(database2.mang[i21][3]);
                                                                                    sb10.append(str66);
                                                                                    strArr15[0] = str143.replace(charSequence3, sb10.toString());
                                                                                    if (database2.caidat_tg.getInt("loi_donvi") <= 0) {
                                                                                        try {
                                                                                            String[][] strArr16 = database2.mang;
                                                                                            String str144 = strArr16[i21][3];
                                                                                            if (strArr16[i21][1] != "lo" || (str144.indexOf("n") <= -1 && str144.indexOf("k") <= -1)) {
                                                                                                str60 = str86;
                                                                                            } else {
                                                                                                database2.mang[i21][5] = str72 + database2.mang[i21][3];
                                                                                                String[][] charSequenceArr6 = database2.mang;
                                                                                                String[] strArr17 = charSequenceArr6[i21];
                                                                                                String str145 = charSequenceArr6[i21][0];
                                                                                                CharSequence charSequence4 = charSequenceArr6[i21][3];
                                                                                                StringBuilder sb11 = new StringBuilder();
                                                                                                sb11.append(str73);
                                                                                                str60 = str86;
                                                                                                try {
                                                                                                    sb11.append(database2.mang[i21][3]);
                                                                                                    sb11.append(str66);
                                                                                                    strArr17[0] = str145.replace(charSequence4, sb11.toString());
                                                                                                } catch (Exception unused47) {
                                                                                                    str76 = str128;
                                                                                                    str77 = str85;
                                                                                                    String[][] strArr6222222222222222222 = database2.mang;
                                                                                                    str78 = str74;
                                                                                                    strArr6222222222222222222[i21][0] = strArr6222222222222222222[i21][0].replaceAll(str73, str78);
                                                                                                    String[][] strArr7222222222222222222 = database2.mang;
                                                                                                    strArr7222222222222222222[i21][0] = strArr7222222222222222222[i21][0].replaceAll(str66, str78);
                                                                                                    database2.mang[i21][0] = str73 + database2.mang[i21][0] + str66;
                                                                                                    str128 = str76;
                                                                                                    str129 = str77;
                                                                                                    i15 = i21;
                                                                                                    str11322222 = str70;
                                                                                                    str11122222 = str63;
                                                                                                    str11722222 = str73;
                                                                                                    str126 = str69;
                                                                                                    str9 = str75;
                                                                                                    str94 = str11522222;
                                                                                                    str7 = str64;
                                                                                                    str10522222 = str130;
                                                                                                    i14 = i16;
                                                                                                    str10622222 = str68;
                                                                                                    i13 = i6;
                                                                                                    str8 = str59;
                                                                                                    str6 = str78;
                                                                                                    str11522222 = str72;
                                                                                                    str11822222 = str62;
                                                                                                    str93 = str67;
                                                                                                    str97 = str71;
                                                                                                    str99 = str60;
                                                                                                    str11622222 = str66;
                                                                                                    str10822222 = str65;
                                                                                                    str11422222 = str61;
                                                                                                }
                                                                                            }
                                                                                            String[][] strArr18 = database2.mang;
                                                                                            if ((strArr18[i21][1] == str63 || strArr18[i21][1] == "de dau db" || strArr18[i21][1] == str70 || strArr18[i21][1] == "de 8" || strArr18[i21][1] == str62 || strArr18[i21][1] == "de dau nhat") && str144.indexOf("d") > -1) {
                                                                                                database2.mang[i21][5] = str72 + database2.mang[i21][3];
                                                                                                String[][] charSequenceArr7 = database2.mang;
                                                                                                charSequenceArr7[i21][0] = charSequenceArr7[i21][0].replace(charSequenceArr7[i21][3], str73 + database2.mang[i21][3] + str66);
                                                                                            }
                                                                                        } catch (Exception unused48) {
                                                                                            str60 = str86;
                                                                                            str76 = str128;
                                                                                            str77 = str85;
                                                                                            String[][] strArr62222222222222222222 = database2.mang;
                                                                                            str78 = str74;
                                                                                            strArr62222222222222222222[i21][0] = strArr62222222222222222222[i21][0].replaceAll(str73, str78);
                                                                                            String[][] strArr72222222222222222222 = database2.mang;
                                                                                            strArr72222222222222222222[i21][0] = strArr72222222222222222222[i21][0].replaceAll(str66, str78);
                                                                                            database2.mang[i21][0] = str73 + database2.mang[i21][0] + str66;
                                                                                            str128 = str76;
                                                                                            str129 = str77;
                                                                                            i15 = i21;
                                                                                            str11322222 = str70;
                                                                                            str11122222 = str63;
                                                                                            str11722222 = str73;
                                                                                            str126 = str69;
                                                                                            str9 = str75;
                                                                                            str94 = str11522222;
                                                                                            str7 = str64;
                                                                                            str10522222 = str130;
                                                                                            i14 = i16;
                                                                                            str10622222 = str68;
                                                                                            i13 = i6;
                                                                                            str8 = str59;
                                                                                            str6 = str78;
                                                                                            str11522222 = str72;
                                                                                            str11822222 = str62;
                                                                                            str93 = str67;
                                                                                            str97 = str71;
                                                                                            str99 = str60;
                                                                                            str11622222 = str66;
                                                                                            str10822222 = str65;
                                                                                            str11422222 = str61;
                                                                                        }
                                                                                    } else {
                                                                                        str60 = str86;
                                                                                    }
                                                                                    str76 = str128;
                                                                                    database2.BaoLoiDan(i21);
                                                                                    str84 = str85;
                                                                                    str129 = str84;
                                                                                    str128 = str76;
                                                                                    str78 = str74;
                                                                                } catch (Exception unused49) {
                                                                                    str59 = str87;
                                                                                    str60 = str86;
                                                                                    str76 = str128;
                                                                                    str77 = str85;
                                                                                    String[][] strArr622222222222222222222 = database2.mang;
                                                                                    str78 = str74;
                                                                                    strArr622222222222222222222[i21][0] = strArr622222222222222222222[i21][0].replaceAll(str73, str78);
                                                                                    String[][] strArr722222222222222222222 = database2.mang;
                                                                                    strArr722222222222222222222[i21][0] = strArr722222222222222222222[i21][0].replaceAll(str66, str78);
                                                                                    database2.mang[i21][0] = str73 + database2.mang[i21][0] + str66;
                                                                                    str128 = str76;
                                                                                    str129 = str77;
                                                                                    i15 = i21;
                                                                                    str11322222 = str70;
                                                                                    str11122222 = str63;
                                                                                    str11722222 = str73;
                                                                                    str126 = str69;
                                                                                    str9 = str75;
                                                                                    str94 = str11522222;
                                                                                    str7 = str64;
                                                                                    str10522222 = str130;
                                                                                    i14 = i16;
                                                                                    str10622222 = str68;
                                                                                    i13 = i6;
                                                                                    str8 = str59;
                                                                                    str6 = str78;
                                                                                    str11522222 = str72;
                                                                                    str11822222 = str62;
                                                                                    str93 = str67;
                                                                                    str97 = str71;
                                                                                    str99 = str60;
                                                                                    str11622222 = str66;
                                                                                    str10822222 = str65;
                                                                                    str11422222 = str61;
                                                                                }
                                                                                i15 = i21;
                                                                                str11322222 = str70;
                                                                                str11122222 = str63;
                                                                                str11722222 = str73;
                                                                                str126 = str69;
                                                                                str9 = str75;
                                                                                str94 = str11522222;
                                                                                str7 = str64;
                                                                                str10522222 = str130;
                                                                                i14 = i16;
                                                                                str10622222 = str68;
                                                                                i13 = i6;
                                                                                str8 = str59;
                                                                                str6 = str78;
                                                                                str11522222 = str72;
                                                                                str11822222 = str62;
                                                                                str93 = str67;
                                                                                str97 = str71;
                                                                                str99 = str60;
                                                                                str11622222 = str66;
                                                                                str10822222 = str65;
                                                                                str11422222 = str61;
                                                                            }
                                                                            str59 = str87;
                                                                        }
                                                                    }
                                                                    str74 = str81;
                                                                    str67 = str82;
                                                                    strArr3 = database2.mang;
                                                                    if (strArr3[i21][1] == str7) {
                                                                    }
                                                                    strArr4 = database2.mang;
                                                                    if (strArr4[i21][1] == "xi") {
                                                                    }
                                                                    if (Integer.parseInt(strArr4[i21][5]) > 2000) {
                                                                        database2.mang[i21][5] = str72 + database2.mang[i21][3];
                                                                        String[][] charSequenceArr52 = database2.mang;
                                                                        String[] strArr152 = charSequenceArr52[i21];
                                                                        String str1432 = charSequenceArr52[i21][0];
                                                                        CharSequence charSequence32 = charSequenceArr52[i21][3];
                                                                        str64 = str7;
                                                                        StringBuilder sb102 = new StringBuilder();
                                                                        sb102.append(str73);
                                                                        str59 = str87;
                                                                        sb102.append(database2.mang[i21][3]);
                                                                        sb102.append(str66);
                                                                        strArr152[0] = str1432.replace(charSequence32, sb102.toString());
                                                                        if (database2.caidat_tg.getInt("loi_donvi") <= 0) {
                                                                        }
                                                                        str76 = str128;
                                                                        database2.BaoLoiDan(i21);
                                                                        str84 = str85;
                                                                        str129 = str84;
                                                                        str128 = str76;
                                                                        str78 = str74;
                                                                        i15 = i21;
                                                                        str11322222 = str70;
                                                                        str11122222 = str63;
                                                                        str11722222 = str73;
                                                                        str126 = str69;
                                                                        str9 = str75;
                                                                        str94 = str11522222;
                                                                        str7 = str64;
                                                                        str10522222 = str130;
                                                                        i14 = i16;
                                                                        str10622222 = str68;
                                                                        i13 = i6;
                                                                        str8 = str59;
                                                                        str6 = str78;
                                                                        str11522222 = str72;
                                                                        str11822222 = str62;
                                                                        str93 = str67;
                                                                        str97 = str71;
                                                                        str99 = str60;
                                                                        str11622222 = str66;
                                                                        str10822222 = str65;
                                                                        str11422222 = str61;
                                                                    }
                                                                    str59 = str87;
                                                                } else {
                                                                    str74 = str81;
                                                                    str67 = str82;
                                                                    str62 = str11822222;
                                                                    str63 = str57;
                                                                    str86 = str60;
                                                                }
                                                                str64 = str7;
                                                                if (database2.caidat_tg.getInt("loi_donvi") <= 0) {
                                                                }
                                                                str76 = str128;
                                                                database2.BaoLoiDan(i21);
                                                                str84 = str85;
                                                                str129 = str84;
                                                                str128 = str76;
                                                                str78 = str74;
                                                                i15 = i21;
                                                                str11322222 = str70;
                                                                str11122222 = str63;
                                                                str11722222 = str73;
                                                                str126 = str69;
                                                                str9 = str75;
                                                                str94 = str11522222;
                                                                str7 = str64;
                                                                str10522222 = str130;
                                                                i14 = i16;
                                                                str10622222 = str68;
                                                                i13 = i6;
                                                                str8 = str59;
                                                                str6 = str78;
                                                                str11522222 = str72;
                                                                str11822222 = str62;
                                                                str93 = str67;
                                                                str97 = str71;
                                                                str99 = str60;
                                                                str11622222 = str66;
                                                                str10822222 = str65;
                                                                str11422222 = str61;
                                                            }
                                                        }
                                                        str66 = str11622222;
                                                        str73 = str11722222;
                                                        if (MainActivity.jSon_Setting.getInt("canhbaodonvi") != 1) {
                                                        }
                                                        str64 = str7;
                                                        if (database2.caidat_tg.getInt("loi_donvi") <= 0) {
                                                        }
                                                        str76 = str128;
                                                        database2.BaoLoiDan(i21);
                                                        str84 = str85;
                                                        str129 = str84;
                                                        str128 = str76;
                                                        str78 = str74;
                                                        i15 = i21;
                                                        str11322222 = str70;
                                                        str11122222 = str63;
                                                        str11722222 = str73;
                                                        str126 = str69;
                                                        str9 = str75;
                                                        str94 = str11522222;
                                                        str7 = str64;
                                                        str10522222 = str130;
                                                        i14 = i16;
                                                        str10622222 = str68;
                                                        i13 = i6;
                                                        str8 = str59;
                                                        str6 = str78;
                                                        str11522222 = str72;
                                                        str11822222 = str62;
                                                        str93 = str67;
                                                        str97 = str71;
                                                        str99 = str60;
                                                        str11622222 = str66;
                                                        str10822222 = str65;
                                                        str11422222 = str61;
                                                    }
                                                }
                                                str60 = str99;
                                                str61 = str11422222;
                                                str70 = str58;
                                                str65 = str10822222;
                                                str71 = str97;
                                                str72 = str11522222;
                                                str11522222 = str94;
                                                strArr2 = database2.mang;
                                                if (strArr2[i21][1] == "lo") {
                                                }
                                                str66 = str11622222;
                                                str73 = str11722222;
                                                if (MainActivity.jSon_Setting.getInt("canhbaodonvi") != 1) {
                                                }
                                                str64 = str7;
                                                if (database2.caidat_tg.getInt("loi_donvi") <= 0) {
                                                }
                                                str76 = str128;
                                                database2.BaoLoiDan(i21);
                                                str84 = str85;
                                                str129 = str84;
                                                str128 = str76;
                                                str78 = str74;
                                                i15 = i21;
                                                str11322222 = str70;
                                                str11122222 = str63;
                                                str11722222 = str73;
                                                str126 = str69;
                                                str9 = str75;
                                                str94 = str11522222;
                                                str7 = str64;
                                                str10522222 = str130;
                                                i14 = i16;
                                                str10622222 = str68;
                                                i13 = i6;
                                                str8 = str59;
                                                str6 = str78;
                                                str11522222 = str72;
                                                str11822222 = str62;
                                                str93 = str67;
                                                str97 = str71;
                                                str99 = str60;
                                                str11622222 = str66;
                                                str10822222 = str65;
                                                str11422222 = str61;
                                            }
                                        }
                                        str130 = str130;
                                        str85 = str83;
                                        database2.XulyMang(i21);
                                        database2.BaoLoiTien(i21);
                                        strArr = database2.mang;
                                        if (strArr[i21][1] != "lo") {
                                        }
                                        str60 = str99;
                                        str61 = str11422222;
                                        str70 = str58;
                                        str65 = str10822222;
                                        str71 = str97;
                                        str72 = str11522222;
                                        str11522222 = str94;
                                        strArr2 = database2.mang;
                                        if (strArr2[i21][1] == "lo") {
                                        }
                                        str66 = str11622222;
                                        str73 = str11722222;
                                        if (MainActivity.jSon_Setting.getInt("canhbaodonvi") != 1) {
                                        }
                                        str64 = str7;
                                        if (database2.caidat_tg.getInt("loi_donvi") <= 0) {
                                        }
                                        str76 = str128;
                                        database2.BaoLoiDan(i21);
                                        str84 = str85;
                                        str129 = str84;
                                        str128 = str76;
                                        str78 = str74;
                                        i15 = i21;
                                        str11322222 = str70;
                                        str11122222 = str63;
                                        str11722222 = str73;
                                        str126 = str69;
                                        str9 = str75;
                                        str94 = str11522222;
                                        str7 = str64;
                                        str10522222 = str130;
                                        i14 = i16;
                                        str10622222 = str68;
                                        i13 = i6;
                                        str8 = str59;
                                        str6 = str78;
                                        str11522222 = str72;
                                        str11822222 = str62;
                                        str93 = str67;
                                        str97 = str71;
                                        str99 = str60;
                                        str11622222 = str66;
                                        str10822222 = str65;
                                        str11422222 = str61;
                                    }
                                }
                                str130 = str130;
                                str85 = str83;
                                str60 = str99;
                                str61 = str11422222;
                                str62 = str11822222;
                                str70 = str58;
                                str63 = str57;
                                str64 = str7;
                                str65 = str10822222;
                                str71 = str97;
                                str72 = str11522222;
                                str66 = str11622222;
                                str67 = str82;
                                str11522222 = str94;
                                str73 = str11722222;
                                str74 = str81;
                                String[][] strArr19 = database2.mang;
                                strArr19[i21][2] = str85;
                                strArr19[i21][3] = str74;
                                String[] strArr20 = strArr19[i21];
                                StringBuilder sb12 = new StringBuilder();
                                sb12.append(str72);
                                str84 = str85;
                                sb12.append(str84);
                                strArr20[4] = sb12.toString();
                                database2.BaoLoiDan(i21);
                                str76 = str128;
                                str129 = str84;
                                str128 = str76;
                                str78 = str74;
                                i15 = i21;
                                str11322222 = str70;
                                str11122222 = str63;
                                str11722222 = str73;
                                str126 = str69;
                                str9 = str75;
                                str94 = str11522222;
                                str7 = str64;
                                str10522222 = str130;
                                i14 = i16;
                                str10622222 = str68;
                                i13 = i6;
                                str8 = str59;
                                str6 = str78;
                                str11522222 = str72;
                                str11822222 = str62;
                                str93 = str67;
                                str97 = str71;
                                str99 = str60;
                                str11622222 = str66;
                                str10822222 = str65;
                                str11422222 = str61;
                            }
                            str59 = str8;
                            str68 = str10622222;
                            if (str83.indexOf(str130) != -1) {
                            }
                            str130 = str130;
                            str85 = str83;
                            str60 = str99;
                            str61 = str11422222;
                            str62 = str11822222;
                            str70 = str58;
                            str63 = str57;
                            str64 = str7;
                            str65 = str10822222;
                            str71 = str97;
                            str72 = str11522222;
                            str66 = str11622222;
                            str67 = str82;
                            str11522222 = str94;
                            str73 = str11722222;
                            str74 = str81;
                            String[][] strArr192 = database2.mang;
                            strArr192[i21][2] = str85;
                            strArr192[i21][3] = str74;
                            String[] strArr202 = strArr192[i21];
                            StringBuilder sb122 = new StringBuilder();
                            sb122.append(str72);
                            str84 = str85;
                            sb122.append(str84);
                            strArr202[4] = sb122.toString();
                            database2.BaoLoiDan(i21);
                            str76 = str128;
                            str129 = str84;
                            str128 = str76;
                            str78 = str74;
                            i15 = i21;
                            str11322222 = str70;
                            str11122222 = str63;
                            str11722222 = str73;
                            str126 = str69;
                            str9 = str75;
                            str94 = str11522222;
                            str7 = str64;
                            str10522222 = str130;
                            i14 = i16;
                            str10622222 = str68;
                            i13 = i6;
                            str8 = str59;
                            str6 = str78;
                            str11522222 = str72;
                            str11822222 = str62;
                            str93 = str67;
                            str97 = str71;
                            str99 = str60;
                            str11622222 = str66;
                            str10822222 = str65;
                            str11422222 = str61;
                        }
                        str12 = str8;
                        String str146 = str11122222;
                        String str147 = str11322222;
                        String str148 = str10622222;
                        str15 = str97;
                        str16 = str99;
                        str18 = str11422222;
                        String str149 = str11522222;
                        str21 = str11822222;
                        str22 = str7;
                        String str150 = str94;
                        String str151 = str10822222;
                        str19 = str11622222;
                        str20 = str11722222;
                        String str152 = str93;
                        str17 = str6;
                        str14 = str150;
                        if (str127.length() > 0) {
                            String str153 = str127;
                            str13 = str152;
                            if (str153.replaceAll(str14, str17).replaceAll("\\.", str17).replaceAll(str13, str17).replaceAll(";", str17).length() > 0) {
                                String[][] strArr21 = mang;
                                int i27 = i15 + 1;
                                strArr21[i27][0] = str153;
                                strArr21[i27][2] = str153;
                                strArr21[i27][3] = str153;
                                strArr21[i27][4] = str149 + str153;
                                BaoLoiDan(i27);
                                str27 = str147;
                                str25 = str146;
                                str26 = str151;
                                str24 = str148;
                            }
                        } else {
                            str13 = str152;
                        }
                        str27 = str147;
                        str25 = str146;
                        str26 = str151;
                        str24 = str148;
                    } else {
                        str12 = str8;
                        str13 = ",";
                        str14 = " ";
                        str15 = "de";
                        str16 = "xq";
                        str17 = str6;
                        str18 = str11422222;
                        str19 = str11622222;
                        str20 = str11722222;
                        str21 = str11822222;
                        str22 = str7;
                        String[][] strArr22 = mang;
                        strArr22[1][0] = str11022222;
                        strArr22[1][4] = str11222222;
                        if (str11222222.indexOf("Không hiểu dạng") > -1) {
                            String[] strArr23 = mang[1];
                            StringBuilder sb13 = new StringBuilder();
                            sb13.append(str11522222);
                            str23 = str11022222;
                            sb13.append(str23.substring(0, 5));
                            strArr23[4] = sb13.toString();
                        } else {
                            str23 = str11022222;
                        }
                        String[][] strArr24 = mang;
                        strArr24[1][2] = str23;
                        strArr24[1][3] = str17;
                        BaoLoiDan(1);
                        str24 = "hc";
                        str25 = "de dit db";
                        str26 = "xg";
                        str27 = "hai cua";
                    }
                    JSONObject jSONObject422222 = new JSONObject();
                    String str11922222 = str24;
                    String str12022222 = str14;
                    String str12122222 = str17;
                    String str12222222 = str12122222;
                    i3 = 0;
                    i4 = 1;
                    while (i4 < 1000) {
                        String[][] strArr25 = mang;
                        if (strArr25[i4][0] == null) {
                            break;
                        }
                        JSONObject jSONObject6 = jSONObject422222;
                        String str154 = str18;
                        String str155 = str13;
                        if (strArr25[i4][4].indexOf(str154) > -1 || mang[i4][5].indexOf(str154) > -1) {
                            if (mang[i4][4].indexOf(str154) > -1) {
                                String[][] strArr26 = mang;
                                str53 = strArr26[i4][4];
                                str54 = strArr26[i4][4];
                            } else {
                                String[][] strArr27 = mang;
                                str53 = strArr27[i4][5];
                                str54 = strArr27[i4][5];
                            }
                            str12222222 = str53;
                            String trim3 = str54.replaceAll(str154, str17).trim();
                            c = 0;
                            str55 = str154;
                            if (mang[i4][0].indexOf(str20) == -1) {
                                String[][] strArr28 = mang;
                                str56 = str17;
                                strArr28[i4][0] = strArr28[i4][0].replaceAll(trim3, str20 + trim3 + str19);
                            } else {
                                str56 = str17;
                            }
                            i3++;
                        } else {
                            str55 = str154;
                            str56 = str17;
                            c = 0;
                        }
                        str12122222 = str12122222 + mang[i4][c];
                        i4++;
                        jSONObject422222 = jSONObject6;
                        str17 = str56;
                        str18 = str55;
                        str13 = str155;
                    }
                    String str12322222 = str13;
                    JSONObject jSONObject522222 = jSONObject422222;
                    String str12422222 = str17;
                    if (i3 != 0) {
                        String str156 = str12422222;
                        int i28 = 0;
                        int i29 = 1;
                        boolean z6 = false;
                        boolean z7 = false;
                        boolean z8 = false;
                        while (i29 < 1000 && mang[i29][0] != null) {
                            JSONObject jSONObject7 = new JSONObject();
                            jSONObject7.put("du_lieu", mang[i29][0]);
                            jSONObject7.put("the_loai", mang[i29][1]);
                            boolean z9 = z8;
                            jSONObject7.put("dan_so", mang[i29][4]);
                            jSONObject7.put("so_tien", mang[i29][5]);
                            if (mang[i29][1].indexOf(str96) > -1) {
                                str31 = str96;
                                z = z9;
                                str32 = str16;
                                z6 = true;
                            } else {
                                if (mang[i29][1].indexOf(str98) <= -1) {
                                    str31 = str96;
                                    str32 = str16;
                                    if (mang[i29][1].indexOf(str32) <= -1) {
                                        boolean z10 = z7;
                                        String str157 = str12;
                                        if (mang[i29][1].indexOf(str157) > -1 || mang[i29][1].indexOf(str26) > -1) {
                                            str12 = str157;
                                        } else {
                                            str12 = str157;
                                            if (mang[i29][1].indexOf("de dau nhat") > -1 || mang[i29][1].indexOf(str21) > -1 || mang[i29][1].indexOf(str27) > -1) {
                                                z7 = z10;
                                                z = true;
                                            } else {
                                                z = z9;
                                                z7 = z10;
                                            }
                                        }
                                    }
                                } else {
                                    str31 = str96;
                                    str32 = str16;
                                }
                                z = z9;
                                z7 = true;
                            }
                            boolean z11 = z6;
                            boolean z12 = z7;
                            boolean z13 = z;
                            if (z322222 && i == 1) {
                                if (!mang[i29][1].contains(str25) && !mang[i29][1].contains("de dau db")) {
                                    String str158 = str22;
                                    if (mang[i29][1].contains(str158)) {
                                        str22 = str158;
                                    } else {
                                        str22 = str158;
                                        if (!mang[i29][1].contains("de 8")) {
                                            if (mang[i29][1].indexOf(str27) > -1) {
                                                StringBuilder sb14 = new StringBuilder();
                                                sb14.append(str156);
                                                sb14.append("de dit db:");
                                                sb14.append(mang[i29][4].trim());
                                                sb14.append("x");
                                                sb14.append(mang[i29][5]);
                                                str33 = str5;
                                                sb14.append(str33);
                                                str48 = sb14.toString();
                                                jSONObject7.put("the_loai", str25);
                                                str52 = str12322222;
                                                String[] split5 = mang[i29][4].split(str52);
                                                sb2 = new StringBuilder();
                                                length = split5.length;
                                                sb2.append(length);
                                                sb2.append(" số.");
                                                jSONObject7.put("so_luong", sb2.toString());
                                                jSONObject2 = jSONObject522222;
                                                jSONObject2.put(String.valueOf(i28), jSONObject7);
                                                str37 = str27;
                                                str34 = str26;
                                                jSONObject = jSONObject2;
                                                str35 = str32;
                                                str50 = str52;
                                                str36 = str98;
                                                str51 = str21;
                                                str38 = str11922222;
                                            } else {
                                                str33 = str5;
                                                str52 = str12322222;
                                                str48 = str156;
                                                jSONObject2 = jSONObject522222;
                                                str37 = str27;
                                                str34 = str26;
                                                jSONObject = jSONObject2;
                                                str35 = str32;
                                                str50 = str52;
                                                str36 = str98;
                                                str51 = str21;
                                                str38 = str11922222;
                                            }
                                        }
                                    }
                                }
                                str33 = str5;
                                str52 = str12322222;
                                str48 = str156 + mang[i29][1] + ":" + mang[i29][4].trim() + "x" + mang[i29][5] + str33;
                                String[] split6 = mang[i29][4].split(str52);
                                sb2 = new StringBuilder();
                                length = split6.length;
                                sb2.append(length);
                                sb2.append(" số.");
                                jSONObject7.put("so_luong", sb2.toString());
                                jSONObject2 = jSONObject522222;
                                jSONObject2.put(String.valueOf(i28), jSONObject7);
                                str37 = str27;
                                str34 = str26;
                                jSONObject = jSONObject2;
                                str35 = str32;
                                str50 = str52;
                                str36 = str98;
                                str51 = str21;
                                str38 = str11922222;
                            } else {
                                jSONObject = jSONObject522222;
                                str33 = str5;
                                str34 = str26;
                                String str159 = str12322222;
                                str35 = str32;
                                String[][] strArr29 = mang;
                                str36 = str98;
                                if (strArr29[i29][1] == str27) {
                                    String str160 = str156 + "de dit db:" + mang[i29][4].trim() + "x" + mang[i29][5] + str33;
                                    String[] split7 = mang[i29][4].split(str159);
                                    JSONObject jSONObject8 = new JSONObject();
                                    str37 = str27;
                                    str50 = str159;
                                    str38 = str11922222;
                                    jSONObject8.put("du_lieu", mang[i29][0].replaceFirst(str38, str15));
                                    jSONObject8.put("the_loai", str25);
                                    jSONObject8.put("dan_so", mang[i29][4]);
                                    jSONObject8.put("so_tien", mang[i29][5]);
                                    jSONObject8.put("so_luong", split7.length + " số.");
                                    jSONObject.put(String.valueOf(i28), jSONObject8);
                                    i28++;
                                    str48 = str160 + "de dit nhat:" + mang[i29][4].trim() + "x" + mang[i29][5] + str33;
                                    jSONObject7.put("du_lieu", mang[i29][0].replaceFirst(str38, "nhat"));
                                    str51 = str21;
                                    jSONObject7.put("the_loai", str51);
                                    jSONObject7.put("so_luong", split7.length + " số.");
                                    jSONObject.put(String.valueOf(i28), jSONObject7);
                                } else {
                                    str37 = str27;
                                    String str161 = str21;
                                    str38 = str11922222;
                                    str98 = str36;
                                    if (strArr29[i29][1].indexOf(str98) <= -1) {
                                        str39 = str161;
                                        str43 = str35;
                                        if (mang[i29][1].indexOf(str43) <= -1) {
                                            str40 = str25;
                                            str41 = str34;
                                            if (mang[i29][1].indexOf(str41) <= -1) {
                                                str48 = str156 + mang[i29][1] + ":" + mang[i29][4].trim() + "x" + mang[i29][5] + str33;
                                                str49 = str159;
                                                jSONObject7.put("so_luong", mang[i29][4].split(str49).length + " số.");
                                                jSONObject.put(String.valueOf(i28), jSONObject7);
                                                i5 = i28;
                                                str16 = str43;
                                                str156 = str48;
                                                str11922222 = str38;
                                                str45 = str33;
                                                str12322222 = str49;
                                                str44 = str12022222;
                                                i29++;
                                                str12022222 = str44;
                                                i28 = i5 + 1;
                                                str26 = str41;
                                                jSONObject522222 = jSONObject;
                                                z6 = z11;
                                                str96 = str31;
                                                str27 = str37;
                                                z7 = z12;
                                                z8 = z13;
                                                str21 = str39;
                                                str25 = str40;
                                                str5 = str45;
                                            }
                                        } else {
                                            str40 = str25;
                                            str41 = str34;
                                        }
                                        str42 = str159;
                                    } else {
                                        str39 = str161;
                                        str40 = str25;
                                        str41 = str34;
                                        str42 = str159;
                                        str43 = str35;
                                    }
                                    i5 = i28;
                                    str44 = str12022222;
                                    String[] split8 = mang[i29][4].split(str44);
                                    str11922222 = str38;
                                    String str162 = str156;
                                    str12322222 = str42;
                                    int i30 = 0;
                                    while (i30 < split8.length) {
                                        String str163 = str162 + mang[i29][1] + ":" + split8[i30] + "x" + mang[i29][5] + str33;
                                        String str164 = str43;
                                        if (mang[i29][1].indexOf(str43) > -1) {
                                            String[] split9 = xuly_Xq(split8[i30]).split(str44);
                                            JSONObject jSONObject9 = new JSONObject();
                                            str46 = str163;
                                            StringBuilder sb15 = new StringBuilder();
                                            str47 = str33;
                                            sb15.append(mang[i29][1]);
                                            sb15.append(":");
                                            sb15.append(split8[i30]);
                                            sb15.append("x");
                                            sb15.append(mang[i29][5]);
                                            jSONObject9.put("du_lieu", sb15.toString());
                                            if (mang[i29][1].indexOf("xq dau") > -1) {
                                                jSONObject9.put("the_loai", "xien dau");
                                            } else {
                                                jSONObject9.put("the_loai", str98);
                                            }
                                            jSONObject9.put("dan_so", xuly_Xq(split8[i30]));
                                            jSONObject9.put("so_tien", mang[i29][5]);
                                            jSONObject9.put("so_luong", split9.length + " cặp.");
                                            jSONObject.put(String.valueOf(i5), jSONObject9);
                                            i5++;
                                        } else {
                                            str46 = str163;
                                            str47 = str33;
                                            if (mang[i29][1].indexOf(str41) > -1) {
                                                jSONObject7.put("so_luong", mang[i29][4].split(str44).length + " cặp.");
                                                jSONObject.put(String.valueOf(i5), jSONObject7);
                                            } else {
                                                jSONObject7.put("so_luong", mang[i29][4].split(str44).length + " cặp.");
                                                jSONObject.put(String.valueOf(i5), jSONObject7);
                                                i30++;
                                                str162 = str46;
                                                str33 = str47;
                                                str43 = str164;
                                            }
                                        }
                                        i30++;
                                        str162 = str46;
                                        str33 = str47;
                                        str43 = str164;
                                    }
                                    str16 = str43;
                                    str45 = str33;
                                    str156 = str162;
                                    i29++;
                                    str12022222 = str44;
                                    i28 = i5 + 1;
                                    str26 = str41;
                                    jSONObject522222 = jSONObject;
                                    z6 = z11;
                                    str96 = str31;
                                    str27 = str37;
                                    z7 = z12;
                                    z8 = z13;
                                    str21 = str39;
                                    str25 = str40;
                                    str5 = str45;
                                }
                            }
                            str40 = str25;
                            str41 = str34;
                            str98 = str36;
                            str49 = str50;
                            str39 = str51;
                            str43 = str35;
                            i5 = i28;
                            str16 = str43;
                            str156 = str48;
                            str11922222 = str38;
                            str45 = str33;
                            str12322222 = str49;
                            str44 = str12022222;
                            i29++;
                            str12022222 = str44;
                            i28 = i5 + 1;
                            str26 = str41;
                            jSONObject522222 = jSONObject;
                            z6 = z11;
                            str96 = str31;
                            str27 = str37;
                            z7 = z12;
                            z8 = z13;
                            str21 = str39;
                            str25 = str40;
                            str5 = str45;
                        }
                        boolean z14 = z7;
                        boolean z15 = z8;
                        JSONObject jSONObject10 = jSONObject522222;
                        if (z322222 && i == 1 && (z6 || z14 || z15)) {
                            String str165 = z6 ? "Bỏ lô," : "Bỏ ";
                            if (z14) {
                                str165 = str165 + "xiên,";
                            }
                            if (z15) {
                                str165 = str165 + "giải nhất";
                            }
                            str156 = str165 + " vì quá giờ!\n" + str156;
                        }
                        if (str156 != null) {
                            str156 = str156.replaceAll("xg 2:", "xi:").replaceAll("xg 3:", "xi:").replaceAll("xg 4:", "xi:");
                        }
                        if (str10722222 != str12422222) {
                            sb = new StringBuilder();
                            sb.append("Update tbl_tinnhanS set so_tin_nhan = ");
                            sb.append(str10722222);
                            str30 = ",  nd_phantich='";
                        } else {
                            sb = new StringBuilder();
                            str30 = "Update tbl_tinnhanS set nd_phantich='";
                        }
                        sb.append(str30);
                        sb.append(str156);
                        sb.append("', phan_tich = '");
                        sb.append(jSONObject10.toString());
                        str29 = "', phat_hien_loi ='ok' Where id =";
                    } else {
                        if (str10722222 == str12422222) {
                            sb = new StringBuilder();
                            str28 = "Update tbl_tinnhanS set nd_phantich='";
                        } else {
                            sb = new StringBuilder();
                            sb.append("Update tbl_tinnhanS set so_tin_nhan = ");
                            sb.append(str10722222);
                            str28 = ", nd_phantich='";
                        }
                        sb.append(str28);
                        sb.append(str12122222);
                        sb.append("', phat_hien_loi = '");
                        sb.append(str12222222);
                        str29 = "'  Where id =";
                    }
                    sb.append(str29);
                    sb.append(num);
                    QueryData(sb.toString());
                    if (cursor != null && !cursor.isClosed()) {
                        cursor.close();
                    }
                    if (GetData != null || GetData.isClosed()) {
                        return;
                    }
                    GetData.close();
                    return;
                }
            }
            i2 = -1;
            String str108222222 = "xg";
            String str109222222 = str10;
            PhanTichTinNhan = Congthuc.PhanTichTinNhan(fixTinNhan);
            String str110222222 = fixTinNhan;
            if (PhanTichTinNhan.indexOf("Không hiểu") <= i2) {
            }
            if (Congthuc.CheckTime(this.caidat_tg.getString("tg_loxien"))) {
            }
            indexOf = str11.indexOf("Không hiểu");
            String str111222222 = "de dit db";
            String str112222222 = str11;
            String str113222222 = "hai cua";
            String str114222222 = "Không hiểu";
            String str115222222 = "Không hiểu ";
            String str116222222 = "</font>";
            String str117222222 = "ldpro";
            String str118222222 = "de dit nhat";
            if (indexOf != -1) {
            }
            JSONObject jSONObject4222222 = new JSONObject();
            String str119222222 = str24;
            String str120222222 = str14;
            String str121222222 = str17;
            String str122222222 = str121222222;
            i3 = 0;
            i4 = 1;
            while (i4 < 1000) {
            }
            String str123222222 = str13;
            JSONObject jSONObject5222222 = jSONObject4222222;
            String str124222222 = str17;
            if (i3 != 0) {
            }
            sb.append(str29);
            sb.append(num);
            QueryData(sb.toString());
            if (cursor != null) {
                cursor.close();
            }
            if (GetData != null) {
                return;
            } else {
                return;
            }
        }
        str5 = str10422222;
        str6 = "";
        str7 = str3;
        str8 = str2;
        i2 = -1;
        str9 = "t";
        str10 = str;
        String str1082222222 = "xg";
        String str1092222222 = str10;
        PhanTichTinNhan = Congthuc.PhanTichTinNhan(fixTinNhan);
        String str1102222222 = fixTinNhan;
        if (PhanTichTinNhan.indexOf("Không hiểu") <= i2) {
        }
        if (Congthuc.CheckTime(this.caidat_tg.getString("tg_loxien"))) {
        }
        indexOf = str11.indexOf("Không hiểu");
        String str1112222222 = "de dit db";
        String str1122222222 = str11;
        String str1132222222 = "hai cua";
        String str1142222222 = "Không hiểu";
        String str1152222222 = "Không hiểu ";
        String str1162222222 = "</font>";
        String str1172222222 = "ldpro";
        String str1182222222 = "de dit nhat";
        if (indexOf != -1) {
        }
        JSONObject jSONObject42222222 = new JSONObject();
        String str1192222222 = str24;
        String str1202222222 = str14;
        String str1212222222 = str17;
        String str1222222222 = str1212222222;
        i3 = 0;
        i4 = 1;
        while (i4 < 1000) {
        }
        String str1232222222 = str13;
        JSONObject jSONObject52222222 = jSONObject42222222;
        String str1242222222 = str17;
        if (i3 != 0) {
        }
        sb.append(str29);
        sb.append(num);
        QueryData(sb.toString());
        if (cursor != null) {
        }
        if (GetData != null) {
        }
    }

    public void NhapSoChiTiet(int i) {
        SQLiteDatabase sQLiteDatabase = getWritableDatabase();;
        DatabaseUtils.InsertHelper insertHelper = new DatabaseUtils.InsertHelper(sQLiteDatabase, "tbl_soctS");;
        Cursor cursor = null;
        Cursor cursor2 = null;
        Cursor cursor3 = null;
        String str;
        String str2;
        JSONObject jSONObject;
        double d;
        String str3;
        double d2;
        double d3;
        double d4 = 0;
        JSONObject jSONObject2 = new JSONObject();
        String str4 = "";
        double parseInt;
        double d5 = 0;
        double d6 = 0;
        int i2 = 0;
        int i3 = 0;
        double d7;
        double d8;
        int length;
        int i4;
        double d9;
        JSONObject jSONObject3;
        String str5;
        double d10;
        String str6;
        String str7 = "the_loai";
        Cursor GetData = GetData("Select * From tbl_tinnhanS WHERE id = " + i);
        GetData.moveToFirst();
        Cursor GetData2 = GetData("Select * From tbl_soctS where ten_kh = '" + GetData.getString(4) + "' And ngay_nhan = '" + GetData.getString(1) + "' And type_kh = " + GetData.getString(3) + " And so_tin_nhan = " + GetData.getString(7));
        if (GetData2.getCount() == 0) {
            try {
                jsonDanSo = new JSONObject(GetData.getString(15));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String string = GetData.getString(4);
            String string2 = GetData.getString(1);
            Cursor GetData3 = GetData("Select * From tbl_KH_new where ten_kh = '" + string + "'");
            GetData3.moveToFirst();
            try {
                JSONObject jSONObject4 = new JSONObject(GetData3.getString(5));
                json = jSONObject4;
                caidat_gia = jSONObject4.getJSONObject("caidat_gia");
                caidat_tg = json.getJSONObject("caidat_tg");
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            try {
                sQLiteDatabase.beginTransaction();
                Iterator<String> keys = jsonDanSo.keys();
                String str8 = "";
                double d11 = 0.0d;
                double d12 = 0.0d;
                while (keys.hasNext()) {
                    JSONObject jSONObject5 = new JSONObject(jsonDanSo.getString(keys.next()));
                    String string3 = jSONObject5.getString("dan_so");
                    String string4 = jSONObject5.getString("so_tien");
                    Iterator<String> it = keys;
                    String str9 = str8;
                    cursor2 = GetData2;
                    Cursor cursor4 = GetData3;
                    String str10 = string2;
                    Cursor cursor5 = GetData;
                    if (jSONObject5.getString(str7).indexOf("de dau db") > -1) {
                        str = "dea";
                    } else if (jSONObject5.getString(str7).indexOf("de dit db") > -1) {
                        str = "deb";
                    } else if (jSONObject5.getString(str7).indexOf("de 8") > -1) {
                        str = "det";
                    } else if (jSONObject5.getString(str7).indexOf("de dau nhat") > -1) {
                        str = "dec";
                    } else if (jSONObject5.getString(str7).indexOf("de dit nhat") > -1) {
                        str = "ded";
                    } else if (jSONObject5.getString(str7).indexOf("bc dau") > -1) {
                        str = "bca";
                    } else if (jSONObject5.getString(str7).indexOf("bc") > -1) {
                        str = "bc";
                    } else if (jSONObject5.getString(str7).indexOf("lo dau") > -1) {
                        str = "loa";
                    } else if (jSONObject5.getString(str7).indexOf("lo") > -1) {
                        str = "lo";
                    } else if (jSONObject5.getString(str7).indexOf("xien dau") > -1) {
                        str = "xia";
                    } else {
                        if (jSONObject5.getString(str7).indexOf("xi") <= -1 && jSONObject5.getString(str7).indexOf("xg") <= -1) {
                            str = jSONObject5.getString(str7).indexOf("xn") > -1 ? "xn" : str9;
                        }
                        str = "xi";
                    }
                    try {
                        try {
                            try {
                                if (str.indexOf("dea") <= -1 && str.indexOf("deb") <= -1 && str.indexOf("dec") <= -1 && str.indexOf("ded") <= -1 && str.indexOf("det") <= -1) {
                                    if (str.indexOf("lo") > -1) {
                                        str2 = string4;
                                        d10 = caidat_tg.getInt("khgiu_lo");
                                        jSONObject = caidat_tg;
                                        str6 = "dlgiu_lo";
                                    } else {
                                        str2 = string4;
                                        if (str.indexOf("xi") <= -1 && str.indexOf("xq") <= -1) {
                                            if (str.indexOf("xn") > -1) {
                                                d10 = caidat_tg.getInt("khgiu_xn");
                                                jSONObject = caidat_tg;
                                                str6 = "dlgiu_xn";
                                            } else if (str.indexOf("bc") > -1) {
                                                d10 = caidat_tg.getInt("khgiu_bc");
                                                jSONObject = caidat_tg;
                                                str6 = "dlgiu_bc";
                                            } else {
                                                d3 = 0.0d;
                                                d2 = 0.0d;
                                                String str11 = str7;
                                                String str12 = "gia_x2";
                                                double d13 = d3;
                                                if (str.indexOf("dea") > -1) {
                                                    d4 = caidat_gia.getDouble("dea");
                                                    jSONObject2 = caidat_gia;
                                                    str4 = "an_dea";
                                                } else if (str.indexOf("deb") > -1) {
                                                    d4 = caidat_gia.getDouble("deb");
                                                    jSONObject2 = caidat_gia;
                                                    str4 = "an_deb";
                                                } else if (str.indexOf("dec") > -1) {
                                                    d4 = caidat_gia.getDouble("dec");
                                                    jSONObject2 = caidat_gia;
                                                    str4 = "an_dec";
                                                } else if (str.indexOf("ded") > -1) {
                                                    d4 = caidat_gia.getDouble("ded");
                                                    jSONObject2 = caidat_gia;
                                                    str4 = "an_ded";
                                                } else if (str.indexOf("det") > -1) {
                                                    d4 = caidat_gia.getDouble("det");
                                                    jSONObject2 = caidat_gia;
                                                    str4 = "an_det";
                                                } else if (str.indexOf("lo") > -1) {
                                                    d4 = caidat_gia.getDouble("lo");
                                                    jSONObject2 = caidat_gia;
                                                    str4 = "an_lo";
                                                } else if (str.indexOf("xi") > -1 && string3.length() == 5) {
                                                    d4 = caidat_gia.getDouble("gia_x2");
                                                    jSONObject2 = caidat_gia;
                                                    str4 = "an_x2";
                                                } else if (str.indexOf("xi") > -1 && string3.length() == 8) {
                                                    d4 = caidat_gia.getDouble("gia_x3");
                                                    jSONObject2 = caidat_gia;
                                                    str4 = "an_x3";
                                                } else if (str.indexOf("xi") > -1 && string3.length() == 11) {
                                                    d4 = caidat_gia.getDouble("gia_x4");
                                                    jSONObject2 = caidat_gia;
                                                    str4 = "an_x4";
                                                } else if (str.indexOf("xn") > -1) {
                                                    d4 = caidat_gia.getDouble("gia_xn");
                                                    jSONObject2 = caidat_gia;
                                                    str4 = "an_xn";
                                                } else {
                                                    if (str.indexOf("bc") > -1) {
                                                        d4 = caidat_gia.getDouble("gia_bc");
                                                        jSONObject2 = caidat_gia;
                                                        str4 = "an_bc";
                                                    }
                                                    parseInt = Integer.parseInt(str2);
                                                    if (str.indexOf("deb") <= -1 && caidat_tg.getInt("heso_de") == 2) {
                                                        Double.isNaN(parseInt);
                                                        d6 = 0.875d;
                                                    } else if (str.indexOf("det") > -1 || caidat_tg.getInt("heso_de") != 1) {
                                                        d5 = parseInt;
                                                        cursor = cursor5;
                                                        if (cursor.getInt(3) == 1) {
                                                            i2 = (int) ((d5 * d13) / 100.0d);
                                                            i3 = (int) ((d5 * d2) / 100.0d);
                                                            d8 = d2;
                                                            d7 = d13;
                                                        } else {
                                                            i2 = 0;
                                                            i3 = 0;
                                                            d7 = 0.0d;
                                                            d8 = 0.0d;
                                                        }
                                                        double d14 = i2;
                                                        Double.isNaN(d14);
                                                        double d15 = i3;
                                                        Double.isNaN(d15);
                                                        Double.isNaN(d14);
                                                        Double.isNaN(d15);
                                                        Double.isNaN(d14);
                                                        Double.isNaN(d15);
                                                        Double.isNaN(d14);
                                                        Double.isNaN(d15);
                                                        int i5 = (int) ((d5 - d14) - d15);
                                                        String[] split = string3.split("dea,deb,dec,ded,det,lo,loa,bc,bca".indexOf(str) > -1 ? "," : " ");
                                                        length = split.length;
                                                        i4 = 0;
                                                        while (i4 < length) {
                                                            String trim = split[i4].trim();
                                                            if (trim.endsWith(",")) {
                                                                trim = trim.substring(0, trim.length() - 1);
                                                            }
                                                            try {
                                                                try {
                                                                    try {
                                                                        if (str.indexOf("xi") > -1 && trim.length() == 5) {
                                                                            d9 = caidat_gia.getDouble(str12);
                                                                            jSONObject3 = caidat_gia;
                                                                            str5 = "an_x2";
                                                                        } else if (str.indexOf("xi") <= -1 || trim.length() != 8) {
                                                                            if (str.indexOf("xi") > -1 && trim.length() == 11) {
                                                                                d9 = caidat_gia.getDouble("gia_x4");
                                                                                jSONObject3 = caidat_gia;
                                                                                str5 = "an_x4";
                                                                            }
                                                                            Double.isNaN(parseInt);
                                                                            insertHelper.prepareForInsert();
                                                                            insertHelper.bind(insertHelper.getColumnIndex("ID"), (byte[]) null);
                                                                            String str13 = str10;
                                                                            insertHelper.bind(insertHelper.getColumnIndex("ngay_nhan"), str13);
                                                                            str10 = str13;
                                                                            String str14 = str12;
                                                                            insertHelper.bind(insertHelper.getColumnIndex("type_kh"), cursor.getInt(3));
                                                                            String[] strArr = split;
                                                                            Cursor cursor6 = cursor4;
                                                                            insertHelper.bind(insertHelper.getColumnIndex("ten_kh"), cursor6.getString(0));
                                                                            insertHelper.bind(insertHelper.getColumnIndex("so_dienthoai"), cursor.getString(5));
                                                                            insertHelper.bind(insertHelper.getColumnIndex("so_tin_nhan"), cursor.getInt(7));
                                                                            String str15 = str11;
                                                                            insertHelper.bind(insertHelper.getColumnIndex(str15), str);
                                                                            insertHelper.bind(insertHelper.getColumnIndex("so_chon"), trim);
                                                                            insertHelper.bind(insertHelper.getColumnIndex("diem"), parseInt);
                                                                            insertHelper.bind(insertHelper.getColumnIndex("diem_quydoi"), d5);
                                                                            String str16 = str;
                                                                            double d16 = d7;
                                                                            insertHelper.bind(insertHelper.getColumnIndex("diem_khachgiu"), d16);
                                                                            d7 = d16;
                                                                            double d17 = d8;
                                                                            insertHelper.bind(insertHelper.getColumnIndex("diem_dly_giu"), d17);
                                                                            insertHelper.bind(insertHelper.getColumnIndex("diem_ton"), i5);
                                                                            insertHelper.bind(insertHelper.getColumnIndex("gia"), d11 * 1000.0d);
                                                                            insertHelper.bind(insertHelper.getColumnIndex("lan_an"), d12 * 1000.0d);
                                                                            insertHelper.bind(insertHelper.getColumnIndex("so_nhay"), 0);
                                                                            int columnIndex = insertHelper.getColumnIndex("tong_tien");
                                                                            Double.isNaN(parseInt);
                                                                            Double.isNaN(parseInt);
                                                                            Double.isNaN(parseInt);
                                                                            cursor3 = cursor6;
                                                                            insertHelper.bind(columnIndex, parseInt * d11 * 1000.0d);
                                                                            insertHelper.bind(insertHelper.getColumnIndex("ket_qua"), 0);
                                                                            insertHelper.execute();
                                                                            i4++;

                                                                            cursor4 = cursor3;
                                                                            str11 = str15;
                                                                            str = str16;
                                                                            str12 = str14;
                                                                            split = strArr;
                                                                            d8 = d17;
                                                                        } else {
                                                                            d9 = caidat_gia.getDouble("gia_x3");
                                                                            jSONObject3 = caidat_gia;
                                                                            str5 = "an_x3";
                                                                        }
                                                                        insertHelper.bind(insertHelper.getColumnIndex("tong_tien"), parseInt * d11 * 1000.0d);
                                                                        insertHelper.bind(insertHelper.getColumnIndex("ket_qua"), 0);
                                                                        insertHelper.execute();
                                                                        i4++;

                                                                        cursor4 = cursor3;
                                                                    } catch (Exception e3) {
                                                                        e3.printStackTrace();
                                                                        sQLiteDatabase.endTransaction();
                                                                        insertHelper.close();
                                                                        sQLiteDatabase.close();
                                                                        if (!cursor.isClosed()) {
                                                                        }
                                                                        if (!cursor3.isClosed()) {
                                                                        }
                                                                        cursor2.close();
                                                                    }
                                                                    insertHelper.bind(insertHelper.getColumnIndex("ID"), (byte[]) null);
                                                                    String str132 = str10;
                                                                    insertHelper.bind(insertHelper.getColumnIndex("ngay_nhan"), str132);
                                                                    str10 = str132;
                                                                    String str142 = str12;
                                                                    insertHelper.bind(insertHelper.getColumnIndex("type_kh"), cursor.getInt(3));
                                                                    String[] strArr2 = split;
                                                                    Cursor cursor62 = cursor4;
                                                                    insertHelper.bind(insertHelper.getColumnIndex("ten_kh"), cursor62.getString(0));
                                                                    insertHelper.bind(insertHelper.getColumnIndex("so_dienthoai"), cursor.getString(5));
                                                                    insertHelper.bind(insertHelper.getColumnIndex("so_tin_nhan"), cursor.getInt(7));
                                                                    String str152 = str11;
                                                                    insertHelper.bind(insertHelper.getColumnIndex(str152), str);
                                                                    insertHelper.bind(insertHelper.getColumnIndex("so_chon"), trim);
                                                                    insertHelper.bind(insertHelper.getColumnIndex("diem"), parseInt);
                                                                    insertHelper.bind(insertHelper.getColumnIndex("diem_quydoi"), d5);
                                                                    String str162 = str;
                                                                    double d162 = d7;
                                                                    insertHelper.bind(insertHelper.getColumnIndex("diem_khachgiu"), d162);
                                                                    d7 = d162;
                                                                    double d172 = d8;
                                                                    insertHelper.bind(insertHelper.getColumnIndex("diem_dly_giu"), d172);
                                                                    insertHelper.bind(insertHelper.getColumnIndex("diem_ton"), i5);
                                                                    insertHelper.bind(insertHelper.getColumnIndex("gia"), d11 * 1000.0d);
                                                                    insertHelper.bind(insertHelper.getColumnIndex("lan_an"), d12 * 1000.0d);
                                                                    insertHelper.bind(insertHelper.getColumnIndex("so_nhay"), 0);
                                                                    int columnIndex2 = insertHelper.getColumnIndex("tong_tien");
                                                                    Double.isNaN(parseInt);
                                                                    Double.isNaN(parseInt);
                                                                    Double.isNaN(parseInt);
                                                                    cursor3 = cursor62;
                                                                } catch (Throwable th) {
                                                                    th = th;
                                                                    sQLiteDatabase.endTransaction();
                                                                    insertHelper.close();
                                                                    sQLiteDatabase.close();
                                                                    throw th;
                                                                }
                                                            } catch (Exception e4) {
                                                                e4.printStackTrace();
                                                            }
                                                        }
                                                        String str17 = str;

                                                        GetData3 = cursor4;
                                                        GetData = cursor;
                                                        keys = it;
                                                        GetData2 = cursor2;
                                                        string2 = str10;
                                                        str7 = str11;
                                                        str8 = str17;
                                                    } else {
                                                        Double.isNaN(parseInt);
                                                        d6 = 1.143d;
                                                    }
                                                    Double.isNaN(parseInt);
                                                    Double.isNaN(parseInt);
                                                    Double.isNaN(parseInt);
                                                    d5 = (int) (d6 * parseInt);
                                                    cursor = cursor5;
                                                    if (cursor.getInt(3) == 1) {
                                                    }
                                                    double d142 = i2;
                                                    Double.isNaN(d142);
                                                    double d152 = i3;
                                                    Double.isNaN(d152);
                                                    Double.isNaN(d142);
                                                    Double.isNaN(d152);
                                                    Double.isNaN(d142);
                                                    Double.isNaN(d152);
                                                    Double.isNaN(d142);
                                                    Double.isNaN(d152);
                                                    int i52 = (int) ((d5 - d142) - d152);
                                                    String[] split2 = string3.split("dea,deb,dec,ded,det,lo,loa,bc,bca".indexOf(str) > -1 ? "," : " ");
                                                    length = split2.length;
                                                    i4 = 0;
                                                    while (i4 < length) {
                                                    }
                                                    String str172 = str;

                                                    GetData3 = cursor4;
                                                    GetData = cursor;
                                                    keys = it;
                                                    GetData2 = cursor2;
                                                    string2 = str10;
                                                    str7 = str11;
                                                    str8 = str172;
                                                }
                                                d11 = d4;
                                                d12 = jSONObject2.getDouble(str4);
                                                parseInt = Integer.parseInt(str2);
                                                if (str.indexOf("deb") <= -1) {
                                                }
                                                if (str.indexOf("det") > -1) {
                                                }
                                                d5 = parseInt;
                                                cursor = cursor5;
                                                if (cursor.getInt(3) == 1) {
                                                }
                                                double d1422 = i2;
                                                Double.isNaN(d1422);
                                                double d1522 = i3;
                                                Double.isNaN(d1522);
                                                Double.isNaN(d1422);
                                                Double.isNaN(d1522);
                                                Double.isNaN(d1422);
                                                Double.isNaN(d1522);
                                                Double.isNaN(d1422);
                                                Double.isNaN(d1522);
                                                int i522 = (int) ((d5 - d1422) - d1522);
                                                String[] split22 = string3.split("dea,deb,dec,ded,det,lo,loa,bc,bca".indexOf(str) > -1 ? "," : " ");
                                                length = split22.length;
                                                i4 = 0;
                                                while (i4 < length) {
                                                }
                                                String str1722 = str;

                                                GetData3 = cursor4;
                                                GetData = cursor;
                                                keys = it;
                                                GetData2 = cursor2;
                                                string2 = str10;
                                                str7 = str11;
                                                str8 = str1722;
                                            }
                                        }
                                        d10 = caidat_tg.getInt("khgiu_xi");
                                        jSONObject = caidat_tg;
                                        str6 = "dlgiu_xi";
                                    }
                                    double d18 = d10;
                                    str3 = str6;
                                    d = d18;
                                    d3 = d;
                                    d2 = jSONObject.getInt(str3);
                                    String str112 = str7;
                                    String str122 = "gia_x2";
                                    double d132 = d3;
                                    if (str.indexOf("dea") > -1) {
                                    }
                                    d11 = d4;
                                    d12 = jSONObject2.getDouble(str4);
                                    parseInt = Integer.parseInt(str2);
                                    if (str.indexOf("deb") <= -1) {
                                    }
                                    if (str.indexOf("det") > -1) {
                                    }
                                    d5 = parseInt;
                                    cursor = cursor5;
                                    if (cursor.getInt(3) == 1) {
                                    }
                                    double d14222 = i2;
                                    Double.isNaN(d14222);
                                    double d15222 = i3;
                                    Double.isNaN(d15222);
                                    Double.isNaN(d14222);
                                    Double.isNaN(d15222);
                                    Double.isNaN(d14222);
                                    Double.isNaN(d15222);
                                    Double.isNaN(d14222);
                                    Double.isNaN(d15222);
                                    int i5222 = (int) ((d5 - d14222) - d15222);
                                    String[] split222 = string3.split("dea,deb,dec,ded,det,lo,loa,bc,bca".indexOf(str) > -1 ? "," : " ");
                                    length = split222.length;
                                    i4 = 0;
                                    while (i4 < length) {
                                    }
                                    String str17222 = str;

                                    GetData3 = cursor4;
                                    GetData = cursor;
                                    keys = it;
                                    GetData2 = cursor2;
                                    string2 = str10;
                                    str7 = str112;
                                    str8 = str17222;
                                }
                                if (cursor.getInt(3) == 1) {
                                }
                                double d142222 = i2;
                                Double.isNaN(d142222);
                                double d152222 = i3;
                                Double.isNaN(d152222);
                                Double.isNaN(d142222);
                                Double.isNaN(d152222);
                                Double.isNaN(d142222);
                                Double.isNaN(d152222);
                                Double.isNaN(d142222);
                                Double.isNaN(d152222);
                                int i52222 = (int) ((d5 - d142222) - d152222);
                                String[] split2222 = string3.split("dea,deb,dec,ded,det,lo,loa,bc,bca".indexOf(str) > -1 ? "," : " ");
                                length = split2222.length;
                                i4 = 0;
                                while (i4 < length) {
                                }
                                String str172222 = str;

                                GetData3 = cursor4;
                                GetData = cursor;
                                keys = it;
                                GetData2 = cursor2;
                                string2 = str10;
                                str8 = str172222;
                            } catch (Exception e5) {
                                cursor3 = cursor4;
                            }
                            str2 = string4;
                            double d19 = caidat_tg.getInt("khgiu_de");
                            jSONObject = caidat_tg;
                            d = d19;
                            str3 = "dlgiu_de";
                            d3 = d;
                            d2 = jSONObject.getInt(str3);
                            String str1122 = str7;
                            String str1222 = "gia_x2";
                            double d1322 = d3;
                            if (str.indexOf("dea") > -1) {
                            }
                            d11 = d4;
                            d12 = jSONObject2.getDouble(str4);
                            parseInt = Integer.parseInt(str2);
                            if (str.indexOf("deb") <= -1) {
                            }
                            if (str.indexOf("det") > -1) {
                            }
                            d5 = parseInt;
                            cursor = cursor5;
                        } catch (Exception e6) {
                        }
                    } catch (Throwable th2) {
                        sQLiteDatabase.endTransaction();
                        insertHelper.close();
                        sQLiteDatabase.close();
                    }
                }
                cursor = GetData;
                cursor2 = GetData2;
                cursor3 = GetData3;
                sQLiteDatabase.setTransactionSuccessful();
            } catch (Exception e7) {
                cursor = GetData;
                cursor2 = GetData2;
                cursor3 = GetData3;
            } catch (Throwable th3) {
            }
            sQLiteDatabase.endTransaction();
            insertHelper.close();
            sQLiteDatabase.close();
            if (!cursor.isClosed()) {
                cursor.close();
            }
            if (!cursor3.isClosed()) {
                cursor3.close();
            }
        } else {
            cursor2 = GetData2;
        }
        cursor2.close();
    }

    public void QueryData(String str) {
        getWritableDatabase().execSQL(str);
    }

    public void Save_Setting(String str, int i) {
        Cursor GetData = GetData("Select * From tbl_Setting WHERE ID = 1");
        if (GetData != null && GetData.moveToFirst()) {
            try {
                MainActivity.jSon_Setting.put(str, i);
                QueryData("Update tbl_Setting set Setting = '" + MainActivity.jSon_Setting.toString() + "'");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        GetData.close();
    }

    public void SendSMS(String str, String str2) {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendMultipartTextMessage(str, null, smsManager.divideMessage(str2), null, null);
    }

    public void ThayThePhu() {
        QueryData("CREATE TABLE IF NOT EXISTS thay_the_phu(  ID INTEGER PRIMARY KEY AUTOINCREMENT,  str VARCHAR(20) NOT NULL,  str_rpl VARCHAR(20) NOT NULL)");
    }

    public String Tin_Chottien(String str) throws JSONException {
        String str2="";
        Cursor GetData;
        Cursor cursor;
        String str3="";
        String str4="";
        String str5="";
        String str6 ="";
        String str7="";
        String str8="";
        String str9="";
        Database database = null;
        String str10="";
        String str11="";
        DecimalFormat decimalFormat = null;
        Iterator<String> keys;
        Cursor GetData2;
        String str12="";
        StringBuilder sb = new StringBuilder();
        String format="";
        StringBuilder sb2= new StringBuilder();
        String str13="";
        String str14="";
        Iterator<String> it;
        String str15="";
        String str16="";
        double d;
        JSONObject jSONObject;
        String str17="";
        DecimalFormat decimalFormat2;
        JSONObject jSONObject2 = null;
        String str18="";
        String str19="";
        int i = 0;
        Database database2 = this;
        String str20 = "AnChuyen";
        String str21 = "AnNhan";
        String str22 = "PhanTram";
        new MainActivity();
        String Get_date = MainActivity.Get_date();
        String Get_ngay = MainActivity.Get_ngay();
        DecimalFormat decimalFormat3 = new DecimalFormat("###,###");
        Cursor GetData3 = database2.GetData("Select * From tbl_kh_new Where ten_kh = '" + str + "'");
        GetData3.moveToFirst();
        str2 = Get_ngay;
        try {
            JSONObject jSONObject3 = new JSONObject(GetData3.getString(5));
            database2.json = jSONObject3;
            database2.caidat_tg = jSONObject3.getJSONObject("caidat_tg");
        } catch (JSONException e2) {
            e2.printStackTrace();
            Cursor GetData4 = database2.GetData("Select ten_kh, so_dienthoai \n, SUM((ngay_nhan < '" + Get_date + "') * ket_qua * (100-diem_khachgiu)/100)/1000  as NoCu \n, SUM((ngay_nhan <= '" + Get_date + "')*ket_qua*(100-diem_khachgiu)/100)/1000 as SoCuoi  \n FROM tbl_soctS WHERE ten_kh = '" + str + "'  GROUP BY ten_kh");
            GetData4.moveToFirst();
            StringBuilder sb3 = new StringBuilder();
            sb3.append("So cu: ");
            String str23 = "xi";
            String str24 = "lo";
            sb3.append(decimalFormat3.format(GetData4.getDouble(2)));
            String sb4 = sb3.toString();
            StringBuilder sb5 = new StringBuilder();
            sb5.append("So cuoi: ");
            String str25 = "DiemChuyen";
            String str26 = "KQChuyen";
            String str27 = "bc";
            String str28 = "xn";
            sb5.append(decimalFormat3.format(GetData4.getDouble(3)));
            String sb6 = sb5.toString();
            GetData = database2.GetData("Select ten_kh, so_dienthoai, the_loai\n, sum((type_kh = 1)*diem) as mDiem\n, CASE WHEN the_loai = 'xi' OR the_loai = 'xia' \n THEN sum((type_kh = 1)*diem*so_nhay*lan_an/1000) \n ELSE sum((type_kh = 1)*diem*so_nhay)  END nAn\n, sum((type_kh = 1)*ket_qua/1000) as mKetqua\n, sum((type_kh = 2)*diem) as mDiem\n, CASE WHEN the_loai = 'xi' OR the_loai = 'xia' \n THEN sum((type_kh = 2)*diem*so_nhay*lan_an/1000) \n ELSE sum((type_kh = 2)*diem*so_nhay)  END nAn\n, sum((type_kh = 2)*ket_qua/1000) as mKetqua\n, 100-(diem_khachgiu*(type_kh=1)) as PT\n  From tbl_soctS Where ngay_nhan = '" + Get_date + "' AND ten_kh = '" + str + "'\n  AND the_loai <> 'tt' GROUP by ten_kh, the_loai");
            JSONObject jSONObject4 = new JSONObject();
            double d2 = 0.0d;
            double d3 = 0.0d;
            while (GetData.moveToNext()) {
            }
            cursor = GetData4;
            str3 = sb6;
            str4 = Get_date;
            str5 = str23;
            str6 = str27;
            str7 = str20;
            str8 = str22;
            str9 = str28;
            database = database2;
            str10 = str25;
            str11 = str24;
            decimalFormat = decimalFormat3;
            double d4 = d2;
            double d5 = d3;
            JSONObject jSONObject5 = new JSONObject();
            jSONObject5.put("dea", "Dau DB: ");
            jSONObject5.put("deb", "De: ");
            jSONObject5.put("det", "De 8: ");
            jSONObject5.put("dec", "Dau Nhat: ");
            jSONObject5.put("ded", "Dit Nhat: ");
            jSONObject5.put(str11, "Lo: ");
            jSONObject5.put(str5, "Xien: ");
            jSONObject5.put(str9, "X.nhay: ");
            jSONObject5.put(str6, "3Cang: ");
            jSONObject5.put("loa", "Lo dau: ");
            jSONObject5.put("xia", "Xien dau: ");
            jSONObject5.put("bca", "Cang dau: ");
            keys = jSONObject5.keys();
            String str29 = "";
            String str30 = "";
            String str31 = str30;
            while (keys.hasNext()) {
            }
            String str32 = str29;
            double d6 = d4;
            DecimalFormat decimalFormat4 = decimalFormat;
            GetData2 = GetData("SELECT SUM((the_loai = 'tt') * ket_qua) AS Ttoan \n FROM tbl_soctS WHERE ten_kh = '" + str + "' AND ngay_nhan ='" + str4 + "'");
            GetData2.moveToFirst();
            if (GetData2.getInt(0) == 0) {
            }
            if (caidat_tg.getInt("chot_sodu") != 0) {
            }
            if (!cursor.isClosed()) {
            }
            if (!GetData3.isClosed()) {
            }
            return str14;
        }
        Cursor GetData42 = database2.GetData("Select ten_kh, so_dienthoai \n, SUM((ngay_nhan < '" + Get_date + "') * ket_qua * (100-diem_khachgiu)/100)/1000  as NoCu \n, SUM((ngay_nhan <= '" + Get_date + "')*ket_qua*(100-diem_khachgiu)/100)/1000 as SoCuoi  \n FROM tbl_soctS WHERE ten_kh = '" + str + "'  GROUP BY ten_kh");
        GetData42.moveToFirst();
        StringBuilder sb32 = new StringBuilder();
        sb32.append("So cu: ");
        String str232 = "xi";
        String str242 = "lo";
        sb32.append(decimalFormat3.format(GetData42.getDouble(2)));
        String sb42 = sb32.toString();
        StringBuilder sb52 = new StringBuilder();
        sb52.append("So cuoi: ");
        String str252 = "DiemChuyen";
        String str262 = "KQChuyen";
        String str272 = "bc";
        String str282 = "xn";
        sb52.append(decimalFormat3.format(GetData42.getDouble(3)));
        String sb62 = sb52.toString();
        GetData = database2.GetData("Select ten_kh, so_dienthoai, the_loai\n, sum((type_kh = 1)*diem) as mDiem\n, CASE WHEN the_loai = 'xi' OR the_loai = 'xia' \n THEN sum((type_kh = 1)*diem*so_nhay*lan_an/1000) \n ELSE sum((type_kh = 1)*diem*so_nhay)  END nAn\n, sum((type_kh = 1)*ket_qua/1000) as mKetqua\n, sum((type_kh = 2)*diem) as mDiem\n, CASE WHEN the_loai = 'xi' OR the_loai = 'xia' \n THEN sum((type_kh = 2)*diem*so_nhay*lan_an/1000) \n ELSE sum((type_kh = 2)*diem*so_nhay)  END nAn\n, sum((type_kh = 2)*ket_qua/1000) as mKetqua\n, 100-(diem_khachgiu*(type_kh=1)) as PT\n  From tbl_soctS Where ngay_nhan = '" + Get_date + "' AND ten_kh = '" + str + "'\n  AND the_loai <> 'tt' GROUP by ten_kh, the_loai");
        JSONObject jSONObject42 = new JSONObject();
        double d22 = 0.0d;
        double d32 = 0.0d;
        while (GetData.moveToNext()) {
            JSONObject jSONObject6 = new JSONObject();
            cursor = GetData42;
            String str33 = str22;
            str4 = Get_date;
            try {
                jSONObject6.put("DiemNhan", GetData.getDouble(3));
                jSONObject6.put("AnNhan", GetData.getDouble(4));
                try {
                    jSONObject6.put("KQNhan", GetData.getDouble(5));
                    jSONObject6.put(str252, GetData.getDouble(6));
                    jSONObject6.put(str20, GetData.getDouble(7));
                    jSONObject6.put("KQChuyen", GetData.getDouble(8));
                } catch (JSONException unused) {

                }
            } catch (JSONException unused2) {
                database = database2;
            }
            try {
                try {
                    if (GetData.getString(2).indexOf("de") > -1) {

                        try {
                            i = caidat_tg.getInt("khgiu_de");
                            str11 = str242;
                        } catch (JSONException unused3) {
                            str5 = str232;
                            str11 = str242;
                            str8 = str33;
                            str7 = str20;
                            str3 = sb62;
                            decimalFormat = decimalFormat3;
                            str9 = str282;
                            str6 = str272;
                            str10 = str252;
                            double d42 = d22;
                            double d52 = d32;
                            JSONObject jSONObject52 = new JSONObject();
                            jSONObject52.put("dea", "Dau DB: ");
                            jSONObject52.put("deb", "De: ");
                            jSONObject52.put("det", "De 8: ");
                            jSONObject52.put("dec", "Dau Nhat: ");
                            jSONObject52.put("ded", "Dit Nhat: ");
                            jSONObject52.put(str11, "Lo: ");
                            jSONObject52.put(str5, "Xien: ");
                            jSONObject52.put(str9, "X.nhay: ");
                            jSONObject52.put(str6, "3Cang: ");
                            jSONObject52.put("loa", "Lo dau: ");
                            jSONObject52.put("xia", "Xien dau: ");
                            jSONObject52.put("bca", "Cang dau: ");
                            keys = jSONObject52.keys();
                            String str292 = "";
                            String str302 = "";
                            String str312 = str302;
                            while (keys.hasNext()) {
                            }
                            String str322 = str292;
                            double d62 = d42;
                            DecimalFormat decimalFormat42 = decimalFormat;
                            GetData2 = GetData("SELECT SUM((the_loai = 'tt') * ket_qua) AS Ttoan \n FROM tbl_soctS WHERE ten_kh = '" + str + "' AND ngay_nhan ='" + str4 + "'");
                            GetData2.moveToFirst();
                            if (GetData2.getInt(0) == 0) {
                            }
                            if (caidat_tg.getInt("chot_sodu") != 0) {
                            }
                            if (!cursor.isClosed()) {
                            }
                            if (!GetData3.isClosed()) {
                            }
                            return str14;
                        }
                    } else {

                        str11 = str242;
                        try {
                            if (GetData.getString(2).indexOf(str11) > -1) {
                                try {
                                    i = caidat_tg.getInt("khgiu_lo");
                                } catch (JSONException unused4) {
                                    str8 = str33;
                                    str3 = sb62;
                                    decimalFormat = decimalFormat3;
                                    str5 = str232;
                                    str6 = str272;
                                    str7 = str20;
                                    str9 = str282;
                                    str10 = str252;
                                    double d422 = d22;
                                    double d522 = d32;
                                    JSONObject jSONObject522 = new JSONObject();
                                    jSONObject522.put("dea", "Dau DB: ");
                                    jSONObject522.put("deb", "De: ");
                                    jSONObject522.put("det", "De 8: ");
                                    jSONObject522.put("dec", "Dau Nhat: ");
                                    jSONObject522.put("ded", "Dit Nhat: ");
                                    jSONObject522.put(str11, "Lo: ");
                                    jSONObject522.put(str5, "Xien: ");
                                    jSONObject522.put(str9, "X.nhay: ");
                                    jSONObject522.put(str6, "3Cang: ");
                                    jSONObject522.put("loa", "Lo dau: ");
                                    jSONObject522.put("xia", "Xien dau: ");
                                    jSONObject522.put("bca", "Cang dau: ");
                                    keys = jSONObject522.keys();
                                    String str2922 = "";
                                    String str3022 = "";
                                    String str3122 = str3022;
                                    while (keys.hasNext()) {
                                    }
                                    String str3222 = str2922;
                                    double d622 = d422;
                                    DecimalFormat decimalFormat422 = decimalFormat;
                                    GetData2 = GetData("SELECT SUM((the_loai = 'tt') * ket_qua) AS Ttoan \n FROM tbl_soctS WHERE ten_kh = '" + str + "' AND ngay_nhan ='" + str4 + "'");
                                    GetData2.moveToFirst();
                                    if (GetData2.getInt(0) == 0) {
                                    }
                                    if (caidat_tg.getInt("chot_sodu") != 0) {
                                    }
                                    if (!cursor.isClosed()) {
                                    }
                                    if (!GetData3.isClosed()) {
                                    }
                                    return str14;
                                }
                            } else {
                                str8 = str33;
                                str3 = sb62;
                                str5 = str232;
                                try {
                                    if (GetData.getString(2).indexOf(str5) > -1) {
                                        try {
                                            jSONObject6.put(str8, 100 - caidat_tg.getInt("khgiu_xi"));
                                            str7 = str20;
                                            str6 = str272;
                                        } catch (JSONException unused6) {
                                            str7 = str20;
                                            str6 = str272;
                                            decimalFormat = decimalFormat3;
                                            str9 = str282;
                                            str10 = str252;
                                            double d42222 = d22;
                                            double d52222 = d32;
                                            JSONObject jSONObject52222 = new JSONObject();
                                            jSONObject52222.put("dea", "Dau DB: ");
                                            jSONObject52222.put("deb", "De: ");
                                            jSONObject52222.put("det", "De 8: ");
                                            jSONObject52222.put("dec", "Dau Nhat: ");
                                            jSONObject52222.put("ded", "Dit Nhat: ");
                                            jSONObject52222.put(str11, "Lo: ");
                                            jSONObject52222.put(str5, "Xien: ");
                                            jSONObject52222.put(str9, "X.nhay: ");
                                            jSONObject52222.put(str6, "3Cang: ");
                                            jSONObject52222.put("loa", "Lo dau: ");
                                            jSONObject52222.put("xia", "Xien dau: ");
                                            jSONObject52222.put("bca", "Cang dau: ");
                                            keys = jSONObject52222.keys();
                                            String str292222 = "";
                                            String str302222 = "";
                                            String str312222 = str302222;
                                            while (keys.hasNext()) {
                                            }
                                            String str322222 = str292222;
                                            double d62222 = d42222;
                                            DecimalFormat decimalFormat42222 = decimalFormat;
                                            GetData2 = GetData("SELECT SUM((the_loai = 'tt') * ket_qua) AS Ttoan \n FROM tbl_soctS WHERE ten_kh = '" + str + "' AND ngay_nhan ='" + str4 + "'");
                                            GetData2.moveToFirst();
                                            if (GetData2.getInt(0) == 0) {
                                            }
                                            if (caidat_tg.getInt("chot_sodu") != 0) {
                                            }
                                            if (!cursor.isClosed()) {
                                            }
                                            if (!GetData3.isClosed()) {
                                            }
                                            return str14;
                                        }
                                    } else {
                                        str6 = str272;
                                        try {
                                            str7 = str20;
                                            if (GetData.getString(2).indexOf(str6) > -1) {
                                                try {
                                                    jSONObject6.put(str8, 100 - caidat_tg.getInt("khgiu_bc"));
                                                } catch (JSONException unused7) {
                                                    decimalFormat = decimalFormat3;
                                                    str9 = str282;
                                                    str10 = str252;
                                                    double d422222 = d22;
                                                    double d522222 = d32;
                                                    JSONObject jSONObject522222 = new JSONObject();
                                                    jSONObject522222.put("dea", "Dau DB: ");
                                                    jSONObject522222.put("deb", "De: ");
                                                    jSONObject522222.put("det", "De 8: ");
                                                    jSONObject522222.put("dec", "Dau Nhat: ");
                                                    jSONObject522222.put("ded", "Dit Nhat: ");
                                                    jSONObject522222.put(str11, "Lo: ");
                                                    jSONObject522222.put(str5, "Xien: ");
                                                    jSONObject522222.put(str9, "X.nhay: ");
                                                    jSONObject522222.put(str6, "3Cang: ");
                                                    jSONObject522222.put("loa", "Lo dau: ");
                                                    jSONObject522222.put("xia", "Xien dau: ");
                                                    jSONObject522222.put("bca", "Cang dau: ");
                                                    keys = jSONObject522222.keys();
                                                    String str2922222 = "";
                                                    String str3022222 = "";
                                                    String str3122222 = str3022222;
                                                    while (keys.hasNext()) {
                                                    }
                                                    String str3222222 = str2922222;
                                                    double d622222 = d422222;
                                                    DecimalFormat decimalFormat422222 = decimalFormat;
                                                    GetData2 = GetData("SELECT SUM((the_loai = 'tt') * ket_qua) AS Ttoan \n FROM tbl_soctS WHERE ten_kh = '" + str + "' AND ngay_nhan ='" + str4 + "'");
                                                    GetData2.moveToFirst();
                                                    if (GetData2.getInt(0) == 0) {
                                                    }
                                                    if (caidat_tg.getInt("chot_sodu") != 0) {
                                                    }
                                                    if (!cursor.isClosed()) {
                                                    }
                                                    if (!GetData3.isClosed()) {
                                                    }
                                                    return str14;
                                                }
                                            } else {
                                                str9 = str282;
                                                try {
                                                    str10 = str252;
                                                    if (GetData.getString(2).indexOf(str9) > -1) {
                                                        try {
                                                            jSONObject6.put(str8, 100 - caidat_tg.getInt("khgiu_xn"));
                                                            decimalFormat = decimalFormat3;
                                                        } catch (JSONException unused8) {
                                                        }
                                                    } else {
                                                        decimalFormat = decimalFormat3;
                                                        jSONObject6.put(str8, GetData.getDouble(9));
                                                    }
                                                    double d7 = (jSONObject6.getInt("KQNhan") * jSONObject6.getInt(str8)) / 100;
                                                    Double.isNaN(d7);
                                                    Double.isNaN(d7);
                                                    Double.isNaN(d7);
                                                    Double.isNaN(d7);
                                                    d22 += d7;
                                                    double d8 = jSONObject6.getInt("KQChuyen");
                                                    Double.isNaN(d8);
                                                    Double.isNaN(d8);
                                                    Double.isNaN(d8);
                                                    Double.isNaN(d8);
                                                    d32 += d8;
                                                    jSONObject42.put(GetData.getString(2), jSONObject6.toString());
                                                    str272 = str6;
                                                    decimalFormat3 = decimalFormat;
                                                    str252 = str10;
                                                    sb62 = str3;
                                                    GetData42 = cursor;
                                                    str242 = str11;
                                                    str282 = str9;
                                                    database2 = database;
                                                    str22 = str8;
                                                    str20 = str7;
                                                    Get_date = str4;
                                                    str232 = str5;
                                                } catch (JSONException unused9) {
                                                    str10 = str252;
                                                }
                                            }
                                        } catch (JSONException unused10) {
                                            str7 = str20;
                                        }
                                    }
                                    decimalFormat = decimalFormat3;
                                    str9 = str282;
                                    str10 = str252;
                                    double d72 = (jSONObject6.getInt("KQNhan") * jSONObject6.getInt(str8)) / 100;
                                    Double.isNaN(d72);
                                    Double.isNaN(d72);
                                    Double.isNaN(d72);
                                    Double.isNaN(d72);
                                    d22 += d72;
                                    double d82 = jSONObject6.getInt("KQChuyen");
                                    Double.isNaN(d82);
                                    Double.isNaN(d82);
                                    Double.isNaN(d82);
                                    Double.isNaN(d82);
                                    d32 += d82;
                                    jSONObject42.put(GetData.getString(2), jSONObject6.toString());
                                    str272 = str6;
                                    decimalFormat3 = decimalFormat;
                                    str252 = str10;
                                    sb62 = str3;
                                    GetData42 = cursor;
                                    str242 = str11;
                                    str282 = str9;
                                    database2 = database;
                                    str22 = str8;
                                    str20 = str7;
                                    Get_date = str4;
                                    str232 = str5;
                                } catch (JSONException unused11) {
                                    str7 = str20;
                                    decimalFormat = decimalFormat3;
                                    str9 = str282;
                                    str6 = str272;
                                    str10 = str252;
                                    double d4222222 = d22;
                                    double d5222222 = d32;
                                    JSONObject jSONObject5222222 = new JSONObject();
                                    jSONObject5222222.put("dea", "Dau DB: ");
                                    jSONObject5222222.put("deb", "De: ");
                                    jSONObject5222222.put("det", "De 8: ");
                                    jSONObject5222222.put("dec", "Dau Nhat: ");
                                    jSONObject5222222.put("ded", "Dit Nhat: ");
                                    jSONObject5222222.put(str11, "Lo: ");
                                    jSONObject5222222.put(str5, "Xien: ");
                                    jSONObject5222222.put(str9, "X.nhay: ");
                                    jSONObject5222222.put(str6, "3Cang: ");
                                    jSONObject5222222.put("loa", "Lo dau: ");
                                    jSONObject5222222.put("xia", "Xien dau: ");
                                    jSONObject5222222.put("bca", "Cang dau: ");
                                    keys = jSONObject5222222.keys();
                                    String str29222222 = "";
                                    String str30222222 = "";
                                    String str31222222 = str30222222;
                                    while (keys.hasNext()) {
                                    }
                                    String str32222222 = str29222222;
                                    double d6222222 = d4222222;
                                    DecimalFormat decimalFormat4222222 = decimalFormat;
                                    GetData2 = GetData("SELECT SUM((the_loai = 'tt') * ket_qua) AS Ttoan \n FROM tbl_soctS WHERE ten_kh = '" + str + "' AND ngay_nhan ='" + str4 + "'");
                                    GetData2.moveToFirst();
                                    if (GetData2.getInt(0) == 0) {
                                    }
                                    if (caidat_tg.getInt("chot_sodu") != 0) {
                                    }
                                    if (!cursor.isClosed()) {
                                    }
                                    if (!GetData3.isClosed()) {
                                    }
                                    return str14;
                                }
                            }
                        } catch (JSONException unused12) {
                            decimalFormat = decimalFormat3;
                            str5 = str232;
                            str8 = str33;
                            str7 = str20;
                            str3 = sb62;
                            str9 = str282;
                            str6 = str272;
                            str10 = str252;
                            double d42222222 = d22;
                            double d52222222 = d32;
                            JSONObject jSONObject52222222 = new JSONObject();
                            jSONObject52222222.put("dea", "Dau DB: ");
                            jSONObject52222222.put("deb", "De: ");
                            jSONObject52222222.put("det", "De 8: ");
                            jSONObject52222222.put("dec", "Dau Nhat: ");
                            jSONObject52222222.put("ded", "Dit Nhat: ");
                            jSONObject52222222.put(str11, "Lo: ");
                            jSONObject52222222.put(str5, "Xien: ");
                            jSONObject52222222.put(str9, "X.nhay: ");
                            jSONObject52222222.put(str6, "3Cang: ");
                            jSONObject52222222.put("loa", "Lo dau: ");
                            jSONObject52222222.put("xia", "Xien dau: ");
                            jSONObject52222222.put("bca", "Cang dau: ");
                            keys = jSONObject52222222.keys();
                            String str292222222 = "";
                            String str302222222 = "";
                            String str312222222 = str302222222;
                            while (keys.hasNext()) {
                            }
                            String str322222222 = str292222222;
                            double d62222222 = d42222222;
                            DecimalFormat decimalFormat42222222 = decimalFormat;
                            GetData2 = GetData("SELECT SUM((the_loai = 'tt') * ket_qua) AS Ttoan \n FROM tbl_soctS WHERE ten_kh = '" + str + "' AND ngay_nhan ='" + str4 + "'");
                            GetData2.moveToFirst();
                            if (GetData2.getInt(0) == 0) {
                            }
                            if (caidat_tg.getInt("chot_sodu") != 0) {
                            }
                            if (!cursor.isClosed()) {
                            }
                            if (!GetData3.isClosed()) {
                            }
                            return str14;
                        }
                    }
                    double d822 = jSONObject6.getInt("KQChuyen");
                    Double.isNaN(d822);
                    Double.isNaN(d822);
                    Double.isNaN(d822);
                    Double.isNaN(d822);
                    d32 += d822;
                    jSONObject42.put(GetData.getString(2), jSONObject6.toString());
                    str272 = str6;
                    decimalFormat3 = decimalFormat;
                    str252 = str10;
                    sb62 = str3;
                    GetData42 = cursor;
                    str242 = str11;
                    str282 = str9;
                    database2 = database;
                    str22 = str8;
                    str20 = str7;
                    Get_date = str4;
                    str232 = str5;
                } catch (JSONException unused13) {
                }
                jSONObject6.put(str8, 100 - i);
                str3 = sb62;
                str5 = str232;
                str6 = str272;
                str7 = str20;
                decimalFormat = decimalFormat3;
                str9 = str282;
                str10 = str252;
                double d722 = (jSONObject6.getInt("KQNhan") * jSONObject6.getInt(str8)) / 100;
                Double.isNaN(d722);
                Double.isNaN(d722);
                Double.isNaN(d722);
                Double.isNaN(d722);
                d22 += d722;
            } catch (JSONException unused14) {
                str3 = sb62;
                decimalFormat = decimalFormat3;
                str5 = str232;
                str6 = str272;
                str7 = str20;
                str9 = str282;
                str10 = str252;
                double d422222222 = d22;
                double d522222222 = d32;
                JSONObject jSONObject522222222 = new JSONObject();
                jSONObject522222222.put("dea", "Dau DB: ");
                jSONObject522222222.put("deb", "De: ");
                jSONObject522222222.put("det", "De 8: ");
                jSONObject522222222.put("dec", "Dau Nhat: ");
                jSONObject522222222.put("ded", "Dit Nhat: ");
                jSONObject522222222.put(str11, "Lo: ");
                jSONObject522222222.put(str5, "Xien: ");
                jSONObject522222222.put(str9, "X.nhay: ");
                jSONObject522222222.put(str6, "3Cang: ");
                jSONObject522222222.put("loa", "Lo dau: ");
                jSONObject522222222.put("xia", "Xien dau: ");
                jSONObject522222222.put("bca", "Cang dau: ");
                keys = jSONObject522222222.keys();
                String str2922222222 = "";
                String str3022222222 = "";
                String str3122222222 = str3022222222;
                while (keys.hasNext()) {
                }
                String str3222222222 = str2922222222;
                double d622222222 = d422222222;
                DecimalFormat decimalFormat422222222 = decimalFormat;
                GetData2 = GetData("SELECT SUM((the_loai = 'tt') * ket_qua) AS Ttoan \n FROM tbl_soctS WHERE ten_kh = '" + str + "' AND ngay_nhan ='" + str4 + "'");
                GetData2.moveToFirst();
                if (GetData2.getInt(0) == 0) {
                }
                if (caidat_tg.getInt("chot_sodu") != 0) {
                }
                if (!cursor.isClosed()) {
                }
                if (!GetData3.isClosed()) {
                }
                return str14;
            }
            str8 = str33;
        }
        cursor = GetData42;
        str3 = sb62;
        str4 = Get_date;
        str5 = str232;
        str6 = str272;
        str7 = str20;
        str8 = str22;
        str9 = str282;
        database = database2;
        str10 = str252;
        str11 = str242;
        decimalFormat = decimalFormat3;
        double d4222222222 = d22;
        double d5222222222 = d32;
        JSONObject jSONObject5222222222 = new JSONObject();
        jSONObject5222222222.put("dea", "Dau DB: ");
        jSONObject5222222222.put("deb", "De: ");
        jSONObject5222222222.put("det", "De 8: ");
        jSONObject5222222222.put("dec", "Dau Nhat: ");
        jSONObject5222222222.put("ded", "Dit Nhat: ");
        jSONObject5222222222.put(str11, "Lo: ");
        jSONObject5222222222.put(str5, "Xien: ");
        jSONObject5222222222.put(str9, "X.nhay: ");
        jSONObject5222222222.put(str6, "3Cang: ");
        jSONObject5222222222.put("loa", "Lo dau: ");
        jSONObject5222222222.put("xia", "Xien dau: ");
        jSONObject5222222222.put("bca", "Cang dau: ");
        keys = jSONObject5222222222.keys();
        String str29222222222 = "";
        String str30222222222 = "";
        String str31222222222 = str30222222222;
        while (keys.hasNext()) {
            String next = keys.next();
            if (jSONObject42.has(next)) {
                it = keys;
                str15 = str29222222222;
                JSONObject jSONObject7 = new JSONObject(jSONObject42.getString(next));
                jSONObject = jSONObject42;
                d = d4222222222;
                if (jSONObject7.getInt(str8) != 100) {
                    decimalFormat2 = decimalFormat;
                    if (jSONObject7.getDouble("DiemNhan") > 0.0d) {
                        StringBuilder sb7 = new StringBuilder();
                        sb7.append(str30222222222);
                        next = next;
                        sb7.append(jSONObject5222222222.getString(next));
                        str18 = str262;
                        sb7.append(decimalFormat2.format(jSONObject7.getDouble("DiemNhan")));
                        sb7.append("(");
                        sb7.append(decimalFormat2.format(jSONObject7.getDouble(str21)));
                        sb7.append(") =");
                        sb7.append(decimalFormat2.format(jSONObject7.getDouble("KQNhan")));
                        sb7.append("x");
                        sb7.append(jSONObject7.getString(str8));
                        sb7.append("%=");
                        sb7.append(decimalFormat2.format((jSONObject7.getDouble("KQNhan") * jSONObject7.getDouble(str8)) / 100.0d));
                        sb7.append("\n");
                        str30222222222 = sb7.toString();
                    } else {
                        str18 = str262;
                        next = next;
                    }
                } else if (jSONObject7.getDouble("DiemNhan") > 0.0d) {
                    StringBuilder sb8 = new StringBuilder();
                    sb8.append(str30222222222);
                    sb8.append(jSONObject5222222222.getString(next));
                    DecimalFormat decimalFormat5 = decimalFormat;
                    sb8.append(decimalFormat5.format(jSONObject7.getDouble("DiemNhan")));
                    sb8.append("(");
                    sb8.append(decimalFormat5.format(jSONObject7.getDouble(str21)));
                    sb8.append(")=");
                    sb8.append(decimalFormat5.format(jSONObject7.getDouble("KQNhan")));
                    sb8.append("\n");
                    str18 = str262;
                    str19 = str10;
                    next = next;
                    str30222222222 = sb8.toString();
                    decimalFormat2 = decimalFormat5;
                    if (jSONObject7.getDouble(str19) <= 0.0d) {
                        StringBuilder sb9 = new StringBuilder();
                        sb9.append(str31222222222);
                        sb9.append(jSONObject5222222222.getString(next));
                        str16 = str21;
                        sb9.append(decimalFormat2.format(jSONObject7.getDouble(str19)));
                        sb9.append("(");
                        str10 = str19;
                        str17 = str7;
                        sb9.append(decimalFormat2.format(jSONObject7.getDouble(str17)));
                        sb9.append(")=");
                        jSONObject2 = jSONObject5222222222;
                        str262 = str18;
                        sb9.append(decimalFormat2.format(jSONObject7.getDouble(str262)));
                        sb9.append("\n");
                        str31222222222 = sb9.toString();
                        str30222222222 = str30222222222;
                    } else {
                        jSONObject2 = jSONObject5222222222;
                        str10 = str19;
                        str16 = str21;
                        str17 = str7;
                        str262 = str18;
                    }
                } else {
                    decimalFormat2 = decimalFormat;
                    str18 = str262;
                }
                str19 = str10;
                if (jSONObject7.getDouble(str19) <= 0.0d) {
                }
            } else {
                it = keys;
                str15 = str29222222222;
                str16 = str21;
                d = d4222222222;
                jSONObject = jSONObject42;
                str17 = str7;
                decimalFormat2 = decimalFormat;
                jSONObject2 = jSONObject5222222222;
            }
            decimalFormat = decimalFormat2;
            str7 = str17;
            str21 = str16;
            jSONObject5222222222 = jSONObject2;
            keys = it;
            str29222222222 = str15;
            jSONObject42 = jSONObject;
            d4222222222 = d;
        }
        String str32222222222 = str29222222222;
        double d6222222222 = d4222222222;
        DecimalFormat decimalFormat4222222222 = decimalFormat;
        GetData2 = GetData("SELECT SUM((the_loai = 'tt') * ket_qua) AS Ttoan \n FROM tbl_soctS WHERE ten_kh = '" + str + "' AND ngay_nhan ='" + str4 + "'");
        GetData2.moveToFirst();
        if (GetData2.getInt(0) == 0) {
            str12 = "T.toan: " + decimalFormat4222222222.format(GetData2.getDouble(0) / 1000.0d) + "\n";
        } else {
            str12 = str32222222222;
        }
        if (caidat_tg.getInt("chot_sodu") != 0) {
            if (str30222222222.length() <= 0 || str31222222222.length() <= 0) {
                String str34 = str2;
                if (str30222222222.length() > 0) {
                    sb2 = new StringBuilder();
                    sb2.append(str34);
                    sb2.append(":\n");
                    sb2.append(str30222222222);
                    sb2.append("Tong nhan:");
                    str13 = decimalFormat4222222222.format(d6222222222);
                } else {
                    if (str31222222222.length() > 0) {
                        sb2 = new StringBuilder();
                        sb2.append(str34);
                        sb2.append(":\n");
                        sb2.append(str31222222222);
                        sb2.append("Tong chuyen:");
                        str13 = decimalFormat4222222222.format(d5222222222);
                    }
                    str14 = null;
                }
            } else {
                sb2 = new StringBuilder();
                sb2.append(str2);
                sb2.append(":\n");
                sb2.append(str30222222222);
                sb2.append("Tong nhan:");
                sb2.append(decimalFormat4222222222.format(d6222222222));
                sb2.append("\n\n");
                sb2.append(str31222222222);
                sb2.append("Tong chuyen:");
                sb2.append(decimalFormat4222222222.format(d5222222222));
                sb2.append("\nTong tien: ");
                str13 = decimalFormat4222222222.format(d6222222222 + d5222222222);
            }
            sb2.append(str13);
            str14 = sb2.toString();
        } else {
            String str35 = str2;
            if (str30222222222.length() <= 0 || str31222222222.length() <= 0) {
                if (str30222222222.length() > 0) {
                    sb = new StringBuilder();
                    sb.append(str35);
                    sb.append(":\n");
                    sb.append(sb42);
                    sb.append("\n");
                    sb.append(str30222222222);
                    sb.append("Tong chuyen:");
                    format = decimalFormat4222222222.format(d6222222222);
                } else {
                    if (str31222222222.length() > 0) {
                        sb = new StringBuilder();
                        sb.append(str35);
                        sb.append(":\n");
                        sb.append(sb42);
                        sb.append("\n");
                        sb.append(str31222222222);
                        sb.append("Tong chuyen:");
                        format = decimalFormat4222222222.format(d5222222222);
                    }
                    str14 = null;
                }
                sb.append(format);
                sb.append("\n");
                sb.append(str12);
                sb2 = sb;
            } else {
                StringBuilder sb10 = new StringBuilder();
                sb10.append(str35);
                sb10.append(":\n");
                sb10.append(sb42);
                sb10.append("\n");
                sb10.append(str30222222222);
                sb10.append("Tong nhan:");
                sb10.append(decimalFormat4222222222.format(d6222222222));
                sb10.append("\n\n");
                sb10.append(str31222222222);
                sb10.append("Tong chuyen:");
                sb10.append(decimalFormat4222222222.format(d5222222222));
                sb10.append("\nTong tien: ");
                sb10.append(decimalFormat4222222222.format(d5222222222 + d6222222222));
                sb10.append("\n");
                sb10.append(str12);
                sb2 = sb10;
            }
            str13 = str3;
            sb2.append(str13);
            str14 = sb2.toString();
        }
        if (!cursor.isClosed()) {
            cursor.close();
        }
        if (!GetData3.isClosed()) {
            GetData3.close();
        }
        return str14;
    }

    public String Tin_Chottien_CT(String str) {
        String str2;
        String str3;
        int i;
        int i2;
        String str4;
        StringBuilder sb;
        String format;
        new MainActivity();
        String Get_date = MainActivity.Get_date();
        MainActivity.Get_ngay();
        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        str2 = "";
        Cursor GetData = GetData("Select so_tin_nhan, CASE \nWHEN the_loai = 'xi' And length(so_chon) = 5 THEN 'xi2' \nWHEN the_loai = 'xi' And length(so_chon) = 8 THEN 'xi3' \nWHEN the_loai = 'xi' And length(so_chon) = 11 THEN 'xi4' \nWHEN the_loai = 'xia' And length(so_chon) = 5 THEN 'xia2' \nWHEN the_loai = 'xia' And length(so_chon) = 8 THEN 'xia3' \nWHEN the_loai = 'xia' And length(so_chon) = 11 THEN 'xia4' \nELSE the_loai END m_theloai\n, sum(diem) as mDiem\n, sum(diem*so_nhay) as mAn \n, sum(ket_qua) as ThanhTien \nFrom tbl_soctS Where ngay_nhan = '" + Get_date + "' And ten_kh = '" + str + "' and the_loai <> 'tt' AND type_kh = 1\nGROUP by so_tin_nhan, m_theloai ORDER by type_kh DESC, ten_kh");
        Cursor GetData2 = GetData("Select so_tin_nhan, CASE \nWHEN the_loai = 'xi' And length(so_chon) = 5 THEN 'xi2' \nWHEN the_loai = 'xi' And length(so_chon) = 8 THEN 'xi3' \nWHEN the_loai = 'xi' And length(so_chon) = 11 THEN 'xi4' \nWHEN the_loai = 'xia' And length(so_chon) = 5 THEN 'xia2' \nWHEN the_loai = 'xia' And length(so_chon) = 8 THEN 'xia3' \nWHEN the_loai = 'xia' And length(so_chon) = 11 THEN 'xia4' \nELSE the_loai END m_theloai\n, sum(diem) as mDiem\n, sum(diem*so_nhay) as mAn \n, sum(ket_qua) as ThanhTien \nFrom tbl_soctS Where ngay_nhan = '" + Get_date + "' And ten_kh = '" + str + "' and the_loai <> 'tt' AND type_kh = 2\nGROUP by so_tin_nhan, m_theloai ORDER by type_kh DESC, ten_kh");
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("dea", "Dau DB: ");
            jSONObject.put("deb", "De: ");
            jSONObject.put("det", "De 8: ");
            jSONObject.put("dec", "Dau Nhat: ");
            jSONObject.put("ded", "Dit Nhat: ");
            jSONObject.put("lo", "Lo: ");
            jSONObject.put("xi2", "Xien 2: ");
            jSONObject.put("xi3", "Xien 3: ");
            jSONObject.put("xi4", "Xien 4: ");
            jSONObject.put("xn", "X.nhay: ");
            jSONObject.put("bc", "3Cang: ");
            jSONObject.put("loa", "Lo dau: ");
            jSONObject.put("xia2", "Xia 2: ");
            jSONObject.put("xia3", "Xia 3: ");
            jSONObject.put("xia4", "Xia 4: ");
            jSONObject.put("bca", "3Cang dau: ");
            str2 = GetData.getCount() > 0 ? "\nTin nhan:" : "";
            int i3 = 0;
            int i4 = 0;
            while (true) {
                str3 = "(";
                i = 2;
                i2 = 1;
                if (!GetData.moveToNext()) {
                    break;
                }
                if (i4 != GetData.getInt(0)) {
                    i4 = GetData.getInt(0);
                    String str5 = str2 + "\nTin " + GetData.getString(0) + ":\n";
                    sb = new StringBuilder();
                    sb.append(str5);
                    sb.append(jSONObject.getString(GetData.getString(1)));
                    sb.append(decimalFormat.format(GetData.getDouble(2)));
                    sb.append("(");
                    format = decimalFormat.format(GetData.getDouble(3));
                } else {
                    sb = new StringBuilder();
                    sb.append(str2);
                    sb.append(jSONObject.getString(GetData.getString(1)));
                    sb.append(decimalFormat.format(GetData.getDouble(2)));
                    sb.append("(");
                    format = decimalFormat.format(GetData.getDouble(3));
                }
                sb.append(format);
                sb.append(")\n");
                str2 = sb.toString();
            }
            if (GetData2.getCount() > 0) {
                str2 = str2 + "\n\nTin Chuyen:";
            }
            int i5 = 0;
            while (GetData2.moveToNext()) {
                if (i5 != GetData2.getInt(i3)) {
                    int i6 = GetData2.getInt(i3);
                    str2 = (str2 + "\nTin " + GetData2.getString(i3) + ":\n") + jSONObject.getString(GetData2.getString(i2)) + decimalFormat.format(GetData2.getDouble(i)) + str3 + decimalFormat.format(GetData2.getDouble(3)) + ")\n";
                    i5 = i6;
                    str4 = str3;
                } else {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(str2);
                    sb2.append(jSONObject.getString(GetData2.getString(i2)));
                    sb2.append(decimalFormat.format(GetData2.getDouble(i)));
                    str4 = str3;
                    sb2.append(str4);
                    sb2.append(decimalFormat.format(GetData2.getDouble(3)));
                    sb2.append(")\n");
                    str2 = sb2.toString();
                }
                str3 = str4;
                i3 = 0;
                i = 2;
                i2 = 1;
            }
        } catch (JSONException unused) {
        }
        if (GetData != null && !GetData.isClosed()) {
            GetData.close();
        }
        if (GetData2 != null && !GetData2.isClosed()) {
            GetData2.close();
        }
        return str2;
    }

    public String Tin_Chottien_CT11(String str) {
        Cursor cursor;
        String str2;
        JSONObject jSONObject = null;
        String str3 = "";
        String str4 = "";
        double d = 0;
        int i = 0;
        double d2 = 0;
        String str5= "";
        String str6= "";
        String str7= "";
        String str8= "";
        String str9= "";
        Cursor cursor2;
        double d3;
        String str10= "";
        String str11= "";
        String str12= "";
        String str13= "";
        String str14= "";
        String str15= "";
        String str16 = "";
        String str17= "";
        String str18= "";
        String str19= "";
        String str20= "";
        int i2 = 0;
        JSONObject jSONObject2 = null;
        String str21= "";
        String str22= "";
        int i3 = 0;
        String str23 = "";
        new MainActivity();
        String Get_date = MainActivity.Get_date();
        MainActivity.Get_ngay();
        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        Cursor GetData = GetData("Select so_tin_nhan, the_loai\n, sum(diem) as mDiem\n, CASE WHEN the_loai = 'xi' Then sum(diem*so_nhay*lan_an/1000) ELSE sum(diem*so_nhay) END as mAn \n, sum(ket_qua) as ThanhTien \nFrom tbl_soctS Where ngay_nhan = '" + Get_date + "' And ten_kh = '" + str + "' and the_loai <> 'tt' AND type_kh = 1\nGROUP by so_tin_nhan, the_loai ORDER by type_kh DESC, ten_kh");
        Cursor GetData2 = GetData("Select so_tin_nhan, the_loai \n, sum(diem) as mDiem\n, CASE WHEN the_loai = 'xi' Then sum(diem*so_nhay*lan_an/1000) ELSE sum(diem*so_nhay) END as mAn \n, sum(ket_qua) as ThanhTien \nFrom tbl_soctS Where ngay_nhan = '" + Get_date + "' And ten_kh = '" + str + "' and the_loai <> 'tt' AND type_kh = 2\nGROUP by so_tin_nhan, the_loai ORDER by type_kh DESC, ten_kh");
        try {
            jSONObject = new JSONObject();
            jSONObject.put("dea", "Dau DB: ");
            jSONObject.put("deb", "De: ");
            jSONObject.put("det", "De 8: ");
            jSONObject.put("dec", "Dau Nhat: ");
            jSONObject.put("ded", "Dit Nhat: ");
            jSONObject.put("lo", "Lo: ");
            jSONObject.put("xi", "Xien: ");
            jSONObject.put("xn", "X.nhay: ");
            jSONObject.put("bc", "3Cang: ");
            jSONObject.put("loa", "Lo dau: ");
            jSONObject.put("xia", "Xia: ");
            jSONObject.put("bca", "3Cang dau: ");
            str3 = GetData.getCount() > 0 ? "\nTin nhan:" : "";
            str4 = "";
            d = 0.0d;
            i = 0;
            d2 = 0.0d;
        } catch (JSONException unused) {
            cursor = GetData2;
            str2 = "";
        }
        while (true) {
            str5 = "\nTin ";
            str6 = ":\n";
            str7 = "Tong tin ";
            str8 = str23;
            str9 = ":";
            cursor2 = GetData2;
            d3 = d2;
            str10 = "(";
            str11 = "\n";
            str12 = "=";
            if (!GetData.moveToNext()) {
                break;
            }
            try {
                if (i != GetData.getInt(0)) {
                    if (i > 0) {
                        str3 = str3 + str4 + decimalFormat.format(d) + "\n";
                        d = 0.0d;
                    }
                    i = GetData.getInt(0);
                    String str24 = str3 + "\nTin " + GetData.getString(0) + ":\n";
                    try {
                        str4 = "Tong tin " + GetData.getString(0) + ":";
                        str24 = str24 + jSONObject.getString(GetData.getString(1)) + decimalFormat.format(GetData.getDouble(2)) + "(" + decimalFormat.format(GetData.getDouble(3)) + ")";
                        str3 = str24 + str12 + decimalFormat.format(GetData.getDouble(4) / 1000.0d) + "\n";
                        d += GetData.getDouble(4) / 1000.0d;
                        i3 = 4;
                    } catch (JSONException unused2) {
                        str2 = str24;
                    }
                } else {
                    str3 = (str3 + jSONObject.getString(GetData.getString(1)) + decimalFormat.format(GetData.getDouble(2)) + "(" + decimalFormat.format(GetData.getDouble(3)) + ")") + str12 + decimalFormat.format(GetData.getDouble(4) / 1000.0d) + "\n";
                    i3 = 4;
                    d += GetData.getDouble(4) / 1000.0d;
                }
                d2 = d3 + (GetData.getDouble(i3) / 1000.0d);
                str23 = str8;
                GetData2 = cursor2;
            } catch (JSONException unused3) {
                str2 = str3;
            }
            str2 = str3;
            cursor = cursor2;
            if (GetData != null && !GetData.isClosed()) {
                GetData.close();
            }
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            return str2;
        }
        String str25 = "\n\n";
        if (d > 0.0d) {
            str13 = ")";
            str3 = (str3 + str4 + decimalFormat.format(d) + "\n\n") + "Tong cong:" + decimalFormat.format(d3);
        } else {
            str13 = ")";
        }
        if (cursor2.getCount() > 0) {
            str3 = str3 + "\n\nTin Chuyen:";
        }
        String str26 = "Tong cong:";
        String str27 = str8;
        int i4 = 0;
        double d4 = 0.0d;
        double d5 = 0.0d;
        while (cursor2.moveToNext()) {
            String str28 = str26;
            String str29 = str10;
            cursor = cursor2;
            String str30 = str25;
            try {
                if (i4 != cursor.getInt(0)) {
                    if (i4 > 0) {
                        str3 = str3 + str27 + decimalFormat.format(d4) + str11;
                        d4 = 0.0d;
                    }
                    int i5 = cursor.getInt(0);
                    String str31 = str3 + str5 + cursor.getString(0) + str6;
                    try {
                        String str32 = str7 + GetData.getString(0) + str9;
                        StringBuilder sb = new StringBuilder();
                        sb.append(str31);
                        str14 = str7;
                        sb.append(jSONObject.getString(cursor.getString(1)));
                        str22 = str31;
                        String str33 = str9;
                        sb.append(decimalFormat.format(cursor.getDouble(2)));
                        str16 = str29;
                        sb.append(str16);
                        sb.append(decimalFormat.format(cursor.getDouble(3)));
                        str18 = str13;
                        sb.append(str18);
                        String sb2 = sb.toString();
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append(sb2);
                        String str34 = str12;
                        sb3.append(str34);
                        String str35 = str5;
                        str19 = str6;
                        sb3.append(decimalFormat.format(GetData.getDouble(4) / 1000.0d));
                        sb3.append(str11);
                        str3 = sb3.toString();
                        double d6 = GetData.getDouble(4) / 1000.0d;
                        str15 = str33;
                        d5 += GetData.getDouble(4) / 1000.0d;
                        d4 += d6;
                        jSONObject2 = jSONObject;
                        str17 = str34;
                        str27 = str32;
                        i2 = i5;
                        str21 = str11;
                        str20 = str35;
                    } catch (JSONException unused6) {
                        str22 = str31;
                    }
                } else {
                    str14 = str7;
                    str15 = str9;
                    String str36 = str5;
                    str16 = str29;
                    str17 = str12;
                    str18 = str13;
                    str19 = str6;
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append(str3);
                    str20 = str36;
                    i2 = i4;
                    sb4.append(jSONObject.getString(cursor.getString(1)));
                    jSONObject2 = jSONObject;
                    sb4.append(decimalFormat.format(cursor.getDouble(2)));
                    sb4.append(str16);
                    sb4.append(decimalFormat.format(cursor.getDouble(3)));
                    sb4.append(str18);
                    String sb5 = sb4.toString();
                    StringBuilder sb6 = new StringBuilder();
                    sb6.append(sb5);
                    sb6.append(str17);
                    sb6.append(decimalFormat.format(GetData.getDouble(4) / 1000.0d));
                    str21 = str11;
                    sb6.append(str21);
                    str3 = sb6.toString();
                    d4 += GetData.getDouble(4) / 1000.0d;
                    d5 += GetData.getDouble(4) / 1000.0d;
                }
                str10 = str16;
                str12 = str17;
                str11 = str21;
                str5 = str20;
                str7 = str14;
                jSONObject = jSONObject2;
                i4 = i2;
                str6 = str19;
                str9 = str15;
                str13 = str18;
                str25 = str30;
                cursor2 = cursor;
                str26 = str28;
            } catch (JSONException unused7) {
            }
        }
        String str37 = str26;
        cursor = cursor2;
        double d7 = d5;
        String str38 = str25;
        double d8 = d4;
        if (d8 > 0.0d) {
            String str39 = str3 + str27 + decimalFormat.format(d8) + str38;
            str2 = str39 + str37 + decimalFormat.format(d7);
            if (GetData != null) {
                GetData.close();
            }
            if (cursor != null) {
                cursor.close();
            }
            return str2;
        }
        str2 = str3;
        if (GetData != null) {
        }
        if (cursor != null) {
        }
        return str2;
    }

    public String Tin_Chottien_xien(String str) throws JSONException {
        Cursor cursor;
        String str2= "";
        String str3= "";
        String str4 = "";
        String str5= "";
        String str6= "";
        DecimalFormat decimalFormat = null;
        String str7= "";
        double d;
        Iterator<String> keys;
        String str8= "";
        String str9= "";
        Cursor GetData;
        String str10= "";
        String format= "";
        StringBuilder sb = null;
        String str11= "";
        String str12= "";
        Iterator<String> it;
        double d2;
        JSONObject jSONObject;
        DecimalFormat decimalFormat2;
        String str13= "";
        String str14= "";
        JSONObject jSONObject2;
        int i = 0;
        JSONObject jSONObject3 = null;
        String str15 = "";
        new MainActivity();
        String Get_date = MainActivity.Get_date();
        String Get_ngay = MainActivity.Get_ngay();
        DecimalFormat decimalFormat3 = new DecimalFormat("###,###");
        Cursor GetData2 = GetData("Select * From tbl_kh_new Where ten_kh = '" + str + "'");
        GetData2.moveToFirst();
        try {
            JSONObject jSONObject4 = new JSONObject(GetData2.getString(5));
            this.json = jSONObject4;
            this.caidat_tg = jSONObject4.getJSONObject("caidat_tg");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Cursor GetData3 = GetData("Select ten_kh, so_dienthoai \n, SUM((ngay_nhan < '" + Get_date + "') * ket_qua * (100-diem_khachgiu)/100)/1000  as NoCu \n, SUM((ngay_nhan <= '" + Get_date + "')*ket_qua*(100-diem_khachgiu)/100)/1000 as SoCuoi  \n FROM tbl_soctS WHERE ten_kh = '" + str + "'  GROUP BY ten_kh");
        GetData3.moveToFirst();
        StringBuilder sb2 = new StringBuilder();
        sb2.append("So cu: ");
        String str16 = "DiemChuyen";
        String str17 = "xn";
        sb2.append(decimalFormat3.format(GetData3.getDouble(2)));
        String sb3 = sb2.toString();
        StringBuilder sb4 = new StringBuilder();
        sb4.append("So cuoi: ");
        String str18 = "KQChuyen";
        sb4.append(decimalFormat3.format(GetData3.getDouble(3)));
        String sb5 = sb4.toString();
        Cursor GetData4 = GetData("Select ten_kh, so_dienthoai, CASE \nWHEN the_loai = 'xi' And length(so_chon) = 5 THEN 'xi2' \nWHEN the_loai = 'xi' And length(so_chon) = 8 THEN 'xi3' \nWHEN the_loai = 'xi' And length(so_chon) = 11 THEN 'xi4' \nWHEN the_loai = 'xia' And length(so_chon) = 5 THEN 'xia2' \nWHEN the_loai = 'xia' And length(so_chon) = 8 THEN 'xia3' \nWHEN the_loai = 'xia' And length(so_chon) = 11 THEN 'xia4' \nELSE the_loai END m_theloai\n, sum((type_kh = 1)*diem) as mDiem\n, sum((type_kh = 1)*diem*so_nhay) as mAn \n, sum((type_kh = 1)*ket_qua)/1000 as mKetqua\n, sum((type_kh = 2)*diem) as mDiem\n, sum((type_kh = 2)*diem*so_nhay) as mAn \n, sum((type_kh = 2)*ket_qua)/1000 as mKetqua\n, 100-(diem_khachgiu*(type_kh=1)) as PT\n  From tbl_soctS Where the_loai <> 'tt' AND ten_kh = '" + str + "' and ngay_nhan = '" + Get_date + "'\n  GROUP by m_theloai");
        JSONObject jSONObject5 = new JSONObject();
        double d3 = 0.0d;
        double d4 = 0.0d;
        while (GetData4.moveToNext()) {
            jSONObject2 = new JSONObject();
            cursor = GetData3;
            str2 = Get_date;
            str3 = Get_ngay;
            try {
                jSONObject2.put("DiemNhan", GetData4.getDouble(3));
                jSONObject2.put("AnNhan", GetData4.getDouble(4));
                jSONObject2.put("KQNhan", GetData4.getDouble(5));
                str4 = str16;
                try {
                    jSONObject2.put(str4, GetData4.getDouble(6));
                    decimalFormat = decimalFormat3;
                } catch (JSONException unused) {
                    decimalFormat = decimalFormat3;
                }
            } catch (JSONException unused2) {
            }
            try {
                jSONObject2.put("AnChuyen", GetData4.getDouble(7));
                str6 = str18;
                try {
                    jSONObject2.put(str6, GetData4.getDouble(8));
                    if (GetData4.getString(2).indexOf("de") > -1) {
                        jSONObject2.put("PhanTram", 100 - this.caidat_tg.getInt("khgiu_de"));
                        str5 = str17;
                        str7 = sb5;
                    } else {
                        try {
                            if (GetData4.getString(2).indexOf("lo") > -1) {
                                jSONObject3 = this.caidat_tg;
                                str15 = "khgiu_lo";
                            } else if (GetData4.getString(2).indexOf("xi") > -1) {
                                jSONObject3 = this.caidat_tg;
                                str15 = "khgiu_xi";
                            } else if (GetData4.getString(2).indexOf("bc") > -1) {
                                jSONObject3 = this.caidat_tg;
                                str15 = "khgiu_bc";
                            } else {
                                str5 = str17;
                                try {
                                    str7 = sb5;
                                    if (GetData4.getString(2).indexOf(str5) > -1) {
                                        i = this.caidat_tg.getInt("khgiu_xn");
                                        jSONObject2.put("PhanTram", 100 - i);
                                    } else {
                                        jSONObject2.put("PhanTram", GetData4.getDouble(9));
                                    }
                                } catch (JSONException unused4) {
                                }
                            }
                            jSONObject2.put("PhanTram", 100 - i);
                        } catch (JSONException unused5) {
                        }
                        i = jSONObject3.getInt(str15);
                        str5 = str17;
                        str7 = sb5;
                    }
                    d3 += (jSONObject2.getDouble("KQNhan") * jSONObject2.getDouble("PhanTram")) / 100.0d;
                    d4 += jSONObject2.getDouble(str6);
                    jSONObject5.put(GetData4.getString(2), jSONObject2.toString());
                    str18 = str6;
                    decimalFormat3 = decimalFormat;
                    sb5 = str7;
                    Get_ngay = str3;
                    str17 = str5;
                    str16 = str4;
                    Get_date = str2;
                    GetData3 = cursor;
                } catch (JSONException unused6) {
                    str5 = str17;
                }
            } catch (JSONException unused7) {
                str5 = str17;
                str6 = str18;
                str7 = sb5;
                d = d3;
                double d5 = d4;
                JSONObject jSONObject6 = new JSONObject();
                jSONObject6.put("dea", "Dau DB: ");
                jSONObject6.put("deb", "De: ");
                jSONObject6.put("det", "De 8: ");
                jSONObject6.put("dec", "Dau Nhat: ");
                jSONObject6.put("ded", "Dit Nhat: ");
                jSONObject6.put("lo", "Lo: ");
                jSONObject6.put("xi2", "Xien 2: ");
                jSONObject6.put("xi3", "Xien 3: ");
                jSONObject6.put("xi4", "Xien 4: ");
                jSONObject6.put(str5, "X.nhay: ");
                jSONObject6.put("bc", "3Cang: ");
                jSONObject6.put("loa", "Lo dau: ");
                jSONObject6.put("xia2", "Xia 2: ");
                jSONObject6.put("xia3", "Xia 3: ");
                jSONObject6.put("xia4", "Xia 4: ");
                jSONObject6.put("bca", "3Cang dau: ");
                keys = jSONObject6.keys();
                str8 = "";
            }
        }
        cursor = GetData3;
        str2 = Get_date;
        str3 = Get_ngay;
        str4 = str16;
        str5 = str17;
        str6 = str18;
        decimalFormat = decimalFormat3;
        str7 = sb5;
        d = d3;
        double d52 = d4;
        JSONObject jSONObject62 = new JSONObject();
        jSONObject62.put("dea", "Dau DB: ");
        jSONObject62.put("deb", "De: ");
        jSONObject62.put("det", "De 8: ");
        jSONObject62.put("dec", "Dau Nhat: ");
        jSONObject62.put("ded", "Dit Nhat: ");
        jSONObject62.put("lo", "Lo: ");
        jSONObject62.put("xi2", "Xien 2: ");
        jSONObject62.put("xi3", "Xien 3: ");
        jSONObject62.put("xi4", "Xien 4: ");
        jSONObject62.put(str5, "X.nhay: ");
        jSONObject62.put("bc", "3Cang: ");
        jSONObject62.put("loa", "Lo dau: ");
        jSONObject62.put("xia2", "Xia 2: ");
        jSONObject62.put("xia3", "Xia 3: ");
        jSONObject62.put("xia4", "Xia 4: ");
        jSONObject62.put("bca", "3Cang dau: ");
        keys = jSONObject62.keys();
        str8 = "";
        String str192 = "";
        String str202 = str192;
        while (true) {
            str9 = str8;
            if (keys.hasNext()) {
                break;
            }
            String next = keys.next();
            if (jSONObject5.has(next)) {
                it = keys;
                d2 = d;
                JSONObject jSONObject7 = new JSONObject(jSONObject5.getString(next));
                jSONObject = jSONObject5;
                if (jSONObject7.getInt("PhanTram") != 100) {
                    str13 = "\n";
                    decimalFormat2 = decimalFormat;
                    if (jSONObject7.getDouble("DiemNhan") > 0.0d) {
                        StringBuilder sb6 = new StringBuilder();
                        sb6.append(str192);
                        sb6.append(jSONObject62.getString(next));
                        str14 = str202;
                        sb6.append(decimalFormat2.format(jSONObject7.getDouble("DiemNhan")));
                        sb6.append("(");
                        sb6.append(decimalFormat2.format(jSONObject7.getDouble("AnNhan")));
                        sb6.append(")=");
                        sb6.append(decimalFormat2.format(jSONObject7.getDouble("KQNhan")));
                        sb6.append("x");
                        sb6.append(jSONObject7.getString("PhanTram"));
                        sb6.append("%=");
                        sb6.append(decimalFormat2.format((jSONObject7.getDouble("KQNhan") * jSONObject7.getDouble("PhanTram")) / 100.0d));
                        sb6.append(str13);
                        str192 = sb6.toString();
                        if (jSONObject7.getDouble(str4) <= 0.0d) {
                        }
                    }
                    str14 = str202;
                    if (jSONObject7.getDouble(str4) <= 0.0d) {
                    }
                } else if (jSONObject7.getDouble("DiemNhan") > 0.0d) {
                    StringBuilder sb7 = new StringBuilder();
                    sb7.append(str192);
                    sb7.append(jSONObject62.getString(next));
                    DecimalFormat decimalFormat5 = decimalFormat;
                    sb7.append(decimalFormat5.format(jSONObject7.getDouble("DiemNhan")));
                    sb7.append("(");
                    sb7.append(decimalFormat5.format(jSONObject7.getDouble("AnNhan")));
                    sb7.append(")=");
                    sb7.append(decimalFormat5.format(jSONObject7.getDouble("KQNhan")));
                    str13 = "\n";
                    sb7.append(str13);
                    str14 = str202;
                    str192 = sb7.toString();
                    decimalFormat2 = decimalFormat5;
                    if (jSONObject7.getDouble(str4) <= 0.0d) {
                        str202 = str14 + jSONObject62.getString(next) + decimalFormat2.format(jSONObject7.getDouble(str4)) + "(" + decimalFormat2.format(jSONObject7.getDouble("AnChuyen")) + ")=" + decimalFormat2.format(jSONObject7.getDouble(str6)) + str13;
                    } else {
                        str202 = str14;
                    }
                } else {
                    str13 = "\n";
                    decimalFormat2 = decimalFormat;
                    str14 = str202;
                    if (jSONObject7.getDouble(str4) <= 0.0d) {
                    }
                }
            } else {
                it = keys;
                d2 = d;
                jSONObject = jSONObject5;
                decimalFormat2 = decimalFormat;
            }
            decimalFormat = decimalFormat2;
            str8 = str9;
            keys = it;
            jSONObject5 = jSONObject;
            d = d2;
        }
        double d62 = d;
        DecimalFormat decimalFormat42 = decimalFormat;
        GetData = GetData("SELECT SUM((the_loai = 'tt') * ket_qua) AS Ttoan \n FROM tbl_soctS WHERE ten_kh = '" + str + "' AND ngay_nhan ='" + str2 + "'");
        GetData.moveToFirst();
        if (GetData.getInt(0) == 0) {
            str10 = "T.toan: " + decimalFormat42.format(GetData.getDouble(0) / 1000.0d) + "\n";
        } else {
            str10 = str9;
        }
        if (this.caidat_tg.getInt("chot_sodu") != 0) {
            if (str192.length() <= 0 || str202.length() <= 0) {
                String str21 = str3;
                if (str192.length() > 0) {
                    sb = new StringBuilder();
                    sb.append(str21);
                    sb.append(":\n");
                    sb.append(str192);
                    sb.append("Tong nhan:");
                    str11 = decimalFormat42.format(d62);
                } else {
                    if (str202.length() > 0) {
                        sb = new StringBuilder();
                        sb.append(str21);
                        sb.append(":\n");
                        sb.append(str202);
                        sb.append("Tong chuyen:");
                        str11 = decimalFormat42.format(d52);
                    }
                    str12 = null;
                }
            } else {
                sb = new StringBuilder();
                sb.append(str3);
                sb.append(":\n");
                sb.append(str192);
                sb.append("Tong nhan:");
                sb.append(decimalFormat42.format(d62));
                sb.append("\n\n");
                sb.append(str202);
                sb.append("Tong chuyen:");
                sb.append(decimalFormat42.format(d52));
                sb.append("\nTong tien: ");
                str11 = decimalFormat42.format(d62 + d52);
            }
            sb.append(str11);
            str12 = sb.toString();
        } else {
            String str22 = str10;
            String str23 = str3;
            if (str192.length() <= 0 || str202.length() <= 0) {
                String str24 = str7;
                if (str192.length() > 0) {
                    sb = new StringBuilder();
                    sb.append(str23);
                    sb.append(":\n");
                    sb.append(sb3);
                    sb.append("\n");
                    sb.append(str192);
                    sb.append("Tong chuyen:");
                    format = decimalFormat42.format(d62);
                } else {
                    if (str202.length() > 0) {
                        StringBuilder sb8 = new StringBuilder();
                        sb8.append(str23);
                        sb8.append(":\n");
                        sb8.append(sb3);
                        sb8.append("\n");
                        sb8.append(str202);
                        sb8.append("Tong chuyen:");
                        format = decimalFormat42.format(d52);
                        sb = sb8;
                    }
                    str12 = null;
                }
                sb.append(format);
                sb.append("\n");
                sb.append(str22);
                str11 = str24;
                sb.append(str11);
                str12 = sb.toString();
            } else {
                str12 = str23 + ":\n" + sb3 + "\n" + str192 + "Tong nhan:" + decimalFormat42.format(d62) + "\n\n" + str202 + "Tong chuyen:" + decimalFormat42.format(d52) + "\nTong tien: " + decimalFormat42.format(d52 + d62) + "\n" + str22 + str7;
            }
        }
        if (!cursor.isClosed()) {
            cursor.close();
        }
        if (!GetData2.isClosed()) {
            GetData2.close();
        }
        return str12;
    }

    public void Tinhtien(String str) throws JSONException {
        int i =0;
        boolean z=false;
        int i2=0;
        int i3=0;
        int i4=0;
        String str2="";
        StringBuilder sb = new StringBuilder();
        boolean z2;
        boolean z3;
        String substring="";
        String str3="";
        StringBuilder sb2 = new StringBuilder();
        int i5=0;
        String substring2="";
        String str4="";
        StringBuilder sb3= new StringBuilder();
        String sb4="";
        String substring3="";
        String str5="";
        StringBuilder sb5= new StringBuilder();
        boolean z4;
        boolean z5;
        StringBuilder sb6= new StringBuilder();
        StringBuilder sb7= new StringBuilder();
        String string="";
        int i6=0;
        int i7=0;
        Cursor GetData = GetData("Select * From KetQua WHERE ngay = '" + str + "'");
        GetData.moveToFirst();
        int i8 = 2;
        while (true) {
            i = 1;
            if (i8 >= 29) {
                z = false;
                break;
            } else {
                if (GetData.getString(i8) == null) {
                    z = true;
                    break;
                }
                i8++;
            }
        }
        if (z) {
            return;
        }
        String[][] strArr = (String[][]) Array.newInstance((Class<?>) String.class, 1000, 8);
        Cursor GetData2 = GetData("Select * From KetQua WHERE ngay = '" + str + "'");
        GetData2.moveToFirst();
        int i9 = 0;
        while (true) {
            i2 = 5;
            i3 = 3;
            if (i9 >= strArr.length) {
                break;
            }
            strArr[i9][0] = "";
            strArr[i9][1] = "";
            strArr[i9][2] = "";
            strArr[i9][3] = "";
            strArr[i9][4] = "";
            strArr[i9][5] = "";
            strArr[i9][6] = "";
            strArr[i9][7] = "";
            i9++;
        }
        QueryData("Delete FROM tbl_tinnhanS WHERE Length(nd_phantich) <5 ");
        QueryData("Update tbl_soctS Set so_nhay = 0, ket_qua = 0 WHERE ngay_nhan = '" + str + "' AND the_loai <> 'tt' AND the_loai <> 'cn'");
        int i10 = 2;
        while (i10 < 29) {
            if (i10 > i && i10 < 12) {
                strArr[Integer.parseInt(GetData2.getString(i10).substring(i3, i2))][0] = strArr[Integer.parseInt(GetData2.getString(i10).substring(i3, i2))][0] + "*";
                strArr[Integer.parseInt(GetData2.getString(i10).substring(0, 2))][6] = strArr[Integer.parseInt(GetData2.getString(i10).substring(0, 2))][6] + "*";
                QueryData("Update tbl_soctS Set so_nhay = so_nhay + 1 WHERE ngay_nhan = '" + str + "' AND the_loai = 'lo' AND so_chon = '" + GetData2.getString(i10).substring(3, i2) + "'");
                sb7 = new StringBuilder();
            } else if (i10 > 11 && i10 < 22) {
                strArr[Integer.parseInt(GetData2.getString(i10).substring(2, 4))][0] = strArr[Integer.parseInt(GetData2.getString(i10).substring(2, 4))][0] + "*";
                strArr[Integer.parseInt(GetData2.getString(i10).substring(0, 2))][6] = strArr[Integer.parseInt(GetData2.getString(i10).substring(0, 2))][6] + "*";
                QueryData("Update tbl_soctS Set so_nhay = so_nhay + 1 WHERE ngay_nhan = '" + str + "' AND the_loai = 'lo' AND so_chon = '" + GetData2.getString(i10).substring(2, 4) + "'");
                sb7 = new StringBuilder();
            } else if (i10 <= 21 || i10 >= 25) {
                strArr[Integer.parseInt(GetData2.getString(i10))][0] = strArr[Integer.parseInt(GetData2.getString(i10))][0] + "*";
                strArr[Integer.parseInt(GetData2.getString(i10).substring(0, 2))][6] = strArr[Integer.parseInt(GetData2.getString(i10).substring(0, 2))][6] + "*";
                QueryData("Update tbl_soctS Set so_nhay = so_nhay + 1 WHERE ngay_nhan = '" + str + "' AND the_loai = 'lo' AND so_chon = '" + GetData2.getString(i10) + "'");
                sb7 = new StringBuilder();
                sb7.append("Update tbl_soctS Set so_nhay = so_nhay + 1 WHERE ngay_nhan = '");
                sb7.append(str);
                sb7.append("' AND the_loai = 'loa' AND so_chon = '");
                string = GetData2.getString(i10);
                sb7.append(string);
                sb7.append("'");
                QueryData(sb7.toString());
                if (i10 == 2) {
                    strArr[Integer.parseInt(GetData2.getString(i10).substring(0, 2))][1] = "*";
                    strArr[Integer.parseInt(GetData2.getString(i10).substring(3, 5))][2] = "*";
                    String substring4 = GetData2.getString(i10).substring(2, 5);
                    QueryData("Update tbl_soctS Set so_nhay = 1 WHERE ngay_nhan = '" + str + "' AND the_loai = 'bc' AND so_chon = '" + substring4 + "'");
                    strArr[Integer.parseInt(GetData2.getString(i10).substring(2, 5))][5] = "*";
                    QueryData("Update tbl_soctS Set so_nhay = 1 WHERE ngay_nhan = '" + str + "' AND the_loai = 'bca' AND so_chon = '" + GetData2.getString(i10).substring(0, 3) + "'");
                    strArr[Integer.parseInt(GetData2.getString(i10).substring(0, 3))][7] = "*";
                    if (MainActivity.jSon_Setting.getInt("ap_man") > 0) {
                        for (int i11 = 0; i11 < 10; i11++) {
                            if (Integer.parseInt(i11 + GetData2.getString(i10).substring(3, 5)) != Integer.parseInt(substring4)) {
                                StringBuilder sb8 = new StringBuilder();
                                sb8.append("Update tbl_soctS Set so_nhay = 1, lan_an = ");
                                sb8.append(MainActivity.jSon_Setting.getInt("ap_man") * 1000);
                                sb8.append(" WHERE ngay_nhan = '");
                                sb8.append(str);
                                sb8.append("' AND the_loai = 'bc' AND so_chon = '");
                                sb8.append(i11);
                                i6 = 5;
                                i7 = 3;
                                sb8.append(GetData2.getString(i10).substring(3, 5));
                                sb8.append("'");
                                QueryData(sb8.toString());
                            } else {
                                i6 = 5;
                                i7 = 3;
                            }
                            strArr[Integer.parseInt(i11 + GetData2.getString(i10).substring(i7, i6))][i6] = "*";
                        }
                    }
                }
                if (i10 != 3) {
                    strArr[Integer.parseInt(GetData2.getString(i10).substring(0, 2))][3] = "*";
                    strArr[Integer.parseInt(GetData2.getString(i10).substring(3, 5))][4] = "*";
                }
                i10++;
                i = 1;
                i3 = 3;
                i2 = 5;
            } else {
                strArr[Integer.parseInt(GetData2.getString(i10).substring(1, 3))][0] = strArr[Integer.parseInt(GetData2.getString(i10).substring(1, 3))][0] + "*";
                strArr[Integer.parseInt(GetData2.getString(i10).substring(0, 2))][6] = strArr[Integer.parseInt(GetData2.getString(i10).substring(0, 2))][6] + "*";
                QueryData("Update tbl_soctS Set so_nhay = so_nhay + 1 WHERE ngay_nhan = '" + str + "' AND the_loai = 'lo' AND so_chon = '" + GetData2.getString(i10).substring(1, 3) + "'");
                sb7 = new StringBuilder();
            }
            sb7.append("Update tbl_soctS Set so_nhay = so_nhay + 1 WHERE ngay_nhan = '");
            sb7.append(str);
            sb7.append("' AND the_loai = 'loa' AND so_chon = '");
            string = GetData2.getString(i10).substring(0, 2);
            sb7.append(string);
            sb7.append("'");
            QueryData(sb7.toString());
            if (i10 == 2) {
            }
            if (i10 != 3) {
            }
            i10++;
            i = 1;
            i3 = 3;
            i2 = 5;
        }
        QueryData("Update tbl_soctS Set so_nhay = 0, ket_qua = -tong_tien WHERE ngay_nhan = '" + str + "' AND the_loai = 'dea' AND so_chon <> '" + GetData2.getString(2).substring(0, 2) + "' AND type_kh = 1");
        QueryData("Update tbl_soctS Set so_nhay = 1, ket_qua = diem * lan_an -tong_tien WHERE ngay_nhan = '" + str + "' AND the_loai = 'dea' AND so_chon ='" + GetData2.getString(2).substring(0, 2) + "' AND type_kh = 1");
        QueryData("Update tbl_soctS Set so_nhay = 0, ket_qua = tong_tien WHERE ngay_nhan = '" + str + "' AND the_loai = 'dea' AND so_chon <> '" + GetData2.getString(2).substring(0, 2) + "' AND type_kh = 2");
        QueryData("Update tbl_soctS Set so_nhay = 1, ket_qua = -diem * lan_an +tong_tien WHERE ngay_nhan = '" + str + "' AND the_loai = 'dea' AND so_chon = '" + GetData2.getString(2).substring(0, 2) + "' AND type_kh = 2");
        QueryData("Update tbl_soctS Set so_nhay = 0, ket_qua = -tong_tien WHERE ngay_nhan = '" + str + "' AND the_loai = 'deb' AND so_chon <> '" + GetData2.getString(2).substring(3, 5) + "' AND type_kh = 1");
        QueryData("Update tbl_soctS Set so_nhay = 1, ket_qua = diem * lan_an -tong_tien WHERE ngay_nhan = '" + str + "' AND the_loai = 'deb' AND so_chon ='" + GetData2.getString(2).substring(3, 5) + "' AND type_kh = 1");
        QueryData("Update tbl_soctS Set so_nhay = 0, ket_qua = tong_tien WHERE ngay_nhan = '" + str + "' AND the_loai = 'deb' AND so_chon <> '" + GetData2.getString(2).substring(3, 5) + "' AND type_kh = 2");
        QueryData("Update tbl_soctS Set so_nhay = 1, ket_qua = -diem * lan_an +tong_tien WHERE ngay_nhan = '" + str + "' AND the_loai = 'deb' AND so_chon = '" + GetData2.getString(2).substring(3, 5) + "' AND type_kh = 2");
        QueryData("Update tbl_soctS Set so_nhay = 0, ket_qua = -tong_tien WHERE ngay_nhan = '" + str + "' AND the_loai = 'dec' AND so_chon <> '" + GetData2.getString(3).substring(0, 2) + "' AND type_kh = 1");
        QueryData("Update tbl_soctS Set so_nhay = 1, ket_qua = diem * lan_an -tong_tien WHERE ngay_nhan = '" + str + "' AND the_loai = 'dec' AND so_chon ='" + GetData2.getString(3).substring(0, 2) + "' AND type_kh = 1");
        QueryData("Update tbl_soctS Set so_nhay = 0, ket_qua = tong_tien WHERE ngay_nhan = '" + str + "' AND the_loai = 'dec' AND so_chon <> '" + GetData2.getString(3).substring(0, 2) + "' AND type_kh = 2");
        QueryData("Update tbl_soctS Set so_nhay = 1, ket_qua = -diem * lan_an +tong_tien WHERE ngay_nhan = '" + str + "' AND the_loai = 'dec' AND so_chon = '" + GetData2.getString(3).substring(0, 2) + "' AND type_kh = 2");
        QueryData("Update tbl_soctS Set so_nhay = 0, ket_qua = -tong_tien WHERE ngay_nhan = '" + str + "' AND the_loai = 'ded' AND so_chon <> '" + GetData2.getString(3).substring(3, 5) + "' AND type_kh = 1");
        QueryData("Update tbl_soctS Set so_nhay = 1, ket_qua = diem * lan_an -tong_tien WHERE ngay_nhan = '" + str + "' AND the_loai = 'ded' AND so_chon ='" + GetData2.getString(3).substring(3, 5) + "' AND type_kh = 1");
        QueryData("Update tbl_soctS Set so_nhay = 0, ket_qua = tong_tien WHERE ngay_nhan = '" + str + "' AND the_loai = 'ded' AND so_chon <> '" + GetData2.getString(3).substring(3, 5) + "' AND type_kh = 2");
        QueryData("Update tbl_soctS Set so_nhay = 1, ket_qua = -diem * lan_an +tong_tien WHERE ngay_nhan = '" + str + "' AND the_loai = 'ded' AND so_chon = '" + GetData2.getString(3).substring(3, 5) + "' AND type_kh = 2");
        QueryData("Update tbl_soctS Set so_nhay = 0, ket_qua = -tong_tien WHERE ngay_nhan = '" + str + "' AND the_loai = 'det' AND so_chon <> '" + GetData2.getString(2).substring(3, 5) + "' AND type_kh = 1");
        QueryData("Update tbl_soctS Set so_nhay = 1, ket_qua = diem * lan_an-tong_tien WHERE ngay_nhan = '" + str + "' AND the_loai = 'det' AND so_chon ='" + GetData2.getString(2).substring(3, 5) + "' AND type_kh = 1");
        QueryData("Update tbl_soctS Set so_nhay = 0, ket_qua = tong_tien WHERE ngay_nhan = '" + str + "' AND the_loai = 'det' AND so_chon <> '" + GetData2.getString(2).substring(3, 5) + "' AND type_kh = 2");
        QueryData("Update tbl_soctS Set so_nhay = 1, ket_qua = -diem * lan_an+tong_tien WHERE ngay_nhan = '" + str + "' AND the_loai = 'det' AND so_chon = '" + GetData2.getString(2).substring(3, 5) + "' AND type_kh = 2");
        StringBuilder sb9 = new StringBuilder();
        sb9.append("Select * From tbl_soctS Where ngay_nhan = '");
        sb9.append(str);
        sb9.append("' AND (the_loai = 'xn' OR the_loai = 'xi')");
        Cursor GetData3 = GetData(sb9.toString());
        while (true) {
            i4 = -1;
            if (!GetData3.moveToNext()) {
                break;
            }
            if ("xi".indexOf(GetData3.getString(6)) > -1) {
                String[] split = GetData3.getString(7).split(",");
                int i12 = 0;
                while (true) {
                    if (i12 >= split.length) {
                        z5 = true;
                        break;
                    } else {
                        if (strArr[Integer.parseInt(split[i12])][0].length() == 0) {
                            z5 = false;
                            break;
                        }
                        i12++;
                    }
                }
                if (z5) {
                    sb6 = new StringBuilder();
                    sb6.append("Update tbl_soctS Set so_nhay = 1 WHERE ID = ");
                    sb6.append(GetData3.getString(0));
                    QueryData(sb6.toString());
                }
            } else if ("xn".indexOf(GetData3.getString(6)) > -1) {
                String[] split2 = GetData3.getString(7).split(",");
                if (strArr[Integer.parseInt(split2[0])][0].length() > 1 || strArr[Integer.parseInt(split2[1])][0].length() > 1 || (strArr[Integer.parseInt(split2[0])][0].length() > 0 && strArr[Integer.parseInt(split2[1])][0].length() > 0)) {
                    sb6 = new StringBuilder();
                    sb6.append("Update tbl_soctS Set so_nhay = 1 WHERE ID = ");
                    sb6.append(GetData3.getString(0));
                    QueryData(sb6.toString());
                }
            }
        }
        Cursor GetData4 = GetData("Select * From tbl_soctS Where ngay_nhan = '" + str + "' AND the_loai = 'xia'");
        while (GetData4.moveToNext()) {
            String[] split3 = GetData4.getString(7).split(",");
            int i13 = 0;
            while (true) {
                if (i13 >= split3.length) {
                    z4 = true;
                    break;
                } else {
                    if (strArr[Integer.parseInt(split3[i13])][6].length() == 0) {
                        z4 = false;
                        break;
                    }
                    i13++;
                }
            }
            if (z4) {
                QueryData("Update tbl_soctS Set so_nhay = 1 WHERE ID = " + GetData4.getString(0));
            }
        }
        if (MainActivity.jSon_Setting.getInt("tra_thuong_lo") > 0) {
            int i14 = MainActivity.jSon_Setting.getInt("tra_thuong_lo") + 1;
            QueryData("Update tbl_soctS Set so_nhay = " + i14 + " Where so_nhay > " + i14);
        }
        QueryData("Update tbl_soctS set ket_qua = diem * lan_an * so_nhay - tong_tien WHERE ngay_nhan = '" + str + "' AND type_kh = 1 AND the_loai <> 'tt' AND the_loai <> 'cn'");
        QueryData("Update tbl_soctS set ket_qua = -diem * lan_an * so_nhay + tong_tien WHERE ngay_nhan = '" + str + "' AND type_kh = 2 AND the_loai <> 'tt' AND the_loai <> 'cn'");
        QueryData("UPDATE tbl_tinnhanS set tinh_tien = 0 Where ngay_nhan = '" + str + "'");
        Cursor GetData5 = GetData("Select * From tbl_tinnhanS Where ngay_nhan = '" + str + "' AND tinh_tien = 0 AND phat_hien_loi = 'ok'");
        while (GetData5.moveToNext()) {
            if (GetData5.getInt(12) == 0) {
                String string2 = GetData5.getString(10);
                if (string2.indexOf("Bỏ ") == 0) {
                    string2 = string2.substring(string2.indexOf("\n") + 1);
                }
                for (int i15 = 0; i15 < 9; i15++) {
                    string2 = string2.replaceAll("\\*", "");
                }
                String str6 = "";
                int i16 = -1;
                int i17 = 1;
                int i18 = 0;
                while (true) {
                    i16 = string2.indexOf("\n", i16 + i17);
                    if (i16 == i4) {
                        break;
                    }
                    String substring5 = string2.substring(i18);
                    String substring6 = substring5.substring(0, substring5.indexOf("\n") + i17);
                    int indexOf = substring6.indexOf("\n");
                    String str7 = string2;
                    if (substring6.indexOf("de dau db") > i4) {
                        String[] split4 = substring6.substring(10, substring6.indexOf(",x")).split(",");
                        substring3 = substring6.substring(substring6.indexOf(",x") + 2, substring6.indexOf("\n"));
                        str5 = str6 + "de dau db:";
                        for (int i19 = 0; i19 < split4.length; i19++) {
                            str5 = str5 + split4[i19] + strArr[Integer.parseInt(split4[i19])][1] + ",";
                        }
                        sb5 = new StringBuilder();
                    } else if (substring6.indexOf("de dit db") > -1) {
                        String[] split5 = substring6.substring(10, substring6.indexOf(",x")).split(",");
                        substring3 = substring6.substring(substring6.indexOf(",x") + 2, substring6.indexOf("\n"));
                        str5 = str6 + "de dit db:";
                        for (int i20 = 0; i20 < split5.length; i20++) {
                            str5 = str5 + split5[i20] + strArr[Integer.parseInt(split5[i20])][2] + ",";
                        }
                        sb5 = new StringBuilder();
                    } else if (substring6.indexOf("de 8") > -1) {
                        String[] split6 = substring6.substring(5, substring6.indexOf(",x")).split(",");
                        substring3 = substring6.substring(substring6.indexOf(",x") + 2, substring6.indexOf("\n"));
                        str5 = str6 + "de 8:";
                        for (int i21 = 0; i21 < split6.length; i21++) {
                            str5 = str5 + split6[i21] + strArr[Integer.parseInt(split6[i21])][2] + ",";
                        }
                        sb5 = new StringBuilder();
                    } else if (substring6.indexOf("de dau nhat") > -1) {
                        String[] split7 = substring6.substring(12, substring6.indexOf(",x")).split(",");
                        substring3 = substring6.substring(substring6.indexOf(",x") + 2, substring6.indexOf("\n"));
                        str5 = str6 + "de dau nhat:";
                        for (int i22 = 0; i22 < split7.length; i22++) {
                            str5 = str5 + split7[i22] + strArr[Integer.parseInt(split7[i22])][3] + ",";
                        }
                        sb5 = new StringBuilder();
                    } else {
                        if (substring6.indexOf("de dit nhat") > -1) {
                            String[] split8 = substring6.substring(12, substring6.indexOf(",x")).split(",");
                            substring2 = substring6.substring(substring6.indexOf(",x") + 2, substring6.indexOf("\n"));
                            str4 = str6 + "de dit nhat:";
                            for (int i23 = 0; i23 < split8.length; i23++) {
                                str4 = str4 + split8[i23] + strArr[Integer.parseInt(split8[i23])][4] + ",";
                            }
                            sb3 = new StringBuilder();
                        } else if (substring6.indexOf("bc dau") > -1) {
                            String[] split9 = substring6.substring(7, substring6.indexOf(",x")).split(",");
                            substring2 = substring6.substring(substring6.indexOf(",x") + 2, substring6.indexOf("\n"));
                            str4 = str6 + "bc dau:";
                            for (int i24 = 0; i24 < split9.length; i24++) {
                                str4 = str4 + split9[i24] + strArr[Integer.parseInt(split9[i24])][7] + ",";
                            }
                            sb3 = new StringBuilder();
                        } else {
                            if (substring6.indexOf("bc") > -1) {
                                String[] split10 = substring6.substring(3, substring6.indexOf(",x")).split(",");
                                substring = substring6.substring(substring6.indexOf(",x") + 2, substring6.indexOf("\n"));
                                str3 = str6 + "bc:";
                                for (int i25 = 0; i25 < split10.length; i25++) {
                                    str3 = str3 + split10[i25] + strArr[Integer.parseInt(split10[i25])][5] + ",";
                                }
                                sb2 = new StringBuilder();
                            } else if (substring6.indexOf("lo dau") > -1) {
                                String[] split11 = substring6.substring(7, substring6.indexOf(",x")).split(",");
                                substring = substring6.substring(substring6.indexOf(",x") + 2, substring6.indexOf("\n"));
                                str3 = str6 + "lo dau:";
                                for (int i26 = 0; i26 < split11.length; i26++) {
                                    str3 = str3 + split11[i26] + strArr[Integer.parseInt(split11[i26])][6] + ",";
                                }
                                sb2 = new StringBuilder();
                            } else if (substring6.indexOf("lo") > -1) {
                                String[] split12 = substring6.substring(3, substring6.indexOf(",x")).split(",");
                                substring = substring6.substring(substring6.indexOf(",x") + 2, substring6.indexOf("\n"));
                                str3 = str6 + "lo:";
                                for (int i27 = 0; i27 < split12.length; i27++) {
                                    str3 = str3 + split12[i27] + strArr[Integer.parseInt(split12[i27])][0] + ",";
                                }
                                sb2 = new StringBuilder();
                            } else {
                                if (substring6.indexOf("xien dau") > -1) {
                                    String substring7 = substring6.substring(9, substring6.indexOf(",x"));
                                    substring6.substring(substring6.indexOf(",x") + 2, substring6.indexOf("\n"));
                                    String[] split13 = substring7.split(",");
                                    int i28 = 0;
                                    while (true) {
                                        if (i28 >= split13.length) {
                                            z3 = true;
                                            break;
                                        } else {
                                            if (strArr[Integer.parseInt(split13[i28])][6].length() == 0) {
                                                z3 = false;
                                                break;
                                            }
                                            i28++;
                                        }
                                    }
                                    if (z3) {
                                        sb = new StringBuilder();
                                        sb.append(str6);
                                        sb.append(substring6.substring(0, substring6.indexOf("\n")));
                                        sb.append("*\n");
                                    } else {
                                        sb = new StringBuilder();
                                        sb.append(str6);
                                        sb.append(substring6);
                                    }
                                } else if (substring6.indexOf("xi") > -1) {
                                    String substring8 = substring6.substring(3, substring6.indexOf(",x"));
                                    substring6.substring(substring6.indexOf(",x") + 2, substring6.indexOf("\n"));
                                    String[] split14 = substring8.split(",");
                                    int i29 = 0;
                                    while (true) {
                                        if (i29 >= split14.length) {
                                            z2 = true;
                                            break;
                                        } else {
                                            if (strArr[Integer.parseInt(split14[i29])][0].length() == 0) {
                                                z2 = false;
                                                break;
                                            }
                                            i29++;
                                        }
                                    }
                                    if (z2) {
                                        sb = new StringBuilder();
                                        sb.append(str6);
                                        sb.append(substring6.substring(0, substring6.indexOf("\n")));
                                        sb.append("*\n");
                                    } else {
                                        sb = new StringBuilder();
                                        sb.append(str6);
                                        sb.append(substring6);
                                    }
                                } else if (substring6.indexOf("xn") > -1) {
                                    String substring9 = substring6.substring(3, substring6.indexOf(",x"));
                                    substring6.substring(substring6.indexOf(",x") + 2, substring6.indexOf("\n"));
                                    String[] split15 = substring9.split(",");
                                    if (strArr[Integer.parseInt(split15[0])][0].length() > 1 || strArr[Integer.parseInt(split15[1])][0].length() > 1 || (strArr[Integer.parseInt(split15[0])][0].length() > 0 && strArr[Integer.parseInt(split15[1])][0].length() > 0)) {
                                        sb = new StringBuilder();
                                        sb.append(str6);
                                        sb.append(substring6.substring(0, substring6.indexOf("\n")));
                                        sb.append("*\n");
                                    } else {
                                        sb = new StringBuilder();
                                        sb.append(str6);
                                        sb.append(substring6);
                                    }
                                } else {
                                    if (substring6.indexOf("xq dau") > -1) {
                                        String[] split16 = substring6.substring(7, substring6.indexOf(",x")).split(",");
                                        String substring10 = substring6.substring(substring6.indexOf(",x") + 2, substring6.indexOf("\n"));
                                        String str8 = str6 + "xq dau:";
                                        for (int i30 = 0; i30 < split16.length; i30++) {
                                            str8 = str8 + split16[i30] + strArr[Integer.parseInt(split16[i30])][6] + ",";
                                        }
                                        str6 = str8 + "x" + substring10 + "\n";
                                    } else if (substring6.indexOf("xq") > -1) {
                                        String[] split17 = substring6.substring(3, substring6.indexOf(",x")).split(",");
                                        String substring11 = substring6.substring(substring6.indexOf(",x") + 2, substring6.indexOf("\n"));
                                        String str9 = str6 + "xq:";
                                        for (int i31 = 0; i31 < split17.length; i31++) {
                                            str9 = str9 + split17[i31] + strArr[Integer.parseInt(split17[i31])][0] + ",";
                                        }
                                        str2 = str9 + "x" + substring11 + "\n";
                                        str6 = str2;
                                        i5 = 1;
                                        i18 += indexOf + i5;
                                        string2 = str7;
                                        i4 = -1;
                                        i17 = 1;
                                    }
                                    str2 = str6;
                                    str6 = str2;
                                    i5 = 1;
                                    i18 += indexOf + i5;
                                    string2 = str7;
                                    i4 = -1;
                                    i17 = 1;
                                }
                                str6 = sb.toString();
                                str2 = str6;
                                str6 = str2;
                                i5 = 1;
                                i18 += indexOf + i5;
                                string2 = str7;
                                i4 = -1;
                                i17 = 1;
                            }
                            sb2.append(str3);
                            sb2.append("x");
                            sb2.append(substring);
                            sb2.append("\n");
                            str6 = sb2.toString();
                            str2 = str6;
                            str6 = str2;
                            i5 = 1;
                            i18 += indexOf + i5;
                            string2 = str7;
                            i4 = -1;
                            i17 = 1;
                        }
                        sb3.append(str4);
                        sb3.append("x");
                        sb3.append(substring2);
                        sb3.append("\n");
                        sb4 = sb3.toString();
                        str6 = sb4;
                        i5 = 1;
                        i18 += indexOf + i5;
                        string2 = str7;
                        i4 = -1;
                        i17 = 1;
                    }
                    sb5.append(str5);
                    sb5.append("x");
                    sb5.append(substring3);
                    sb5.append("\n");
                    sb4 = sb5.toString();
                    str6 = sb4;
                    i5 = 1;
                    i18 += indexOf + i5;
                    string2 = str7;
                    i4 = -1;
                    i17 = 1;
                }
                QueryData("Update tbl_tinnhanS Set nd_phantich ='" + str6 + "', tinh_tien = 1 WHERE ID = " + GetData5.getString(0));
                i4 = -1;
            }
        }
        if (GetData != null) {
            GetData.close();
        }
        if (GetData5 != null) {
            GetData5.close();
        }
    }

    public String TraCang(String str, int i) {
        String str2 = "";
        String str3= "";
        String str4= "";
        String str5= "";
        String str6= "";
        String str7= "";
        int i2=0;
        String str8= "";
        String str9= "";
        String str10= "";
        String str11= "";
        String str12 = "";
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getDefault());
        simpleDateFormat2.setTimeZone(TimeZone.getDefault());
        String format = simpleDateFormat.format(calendar.getTime());
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        try {
            Cursor GetData = GetData("Select the_loai, so_chon, Sum(diem_ton *(type_kh = 1)) as Nhan, Sum(diem_ton *(type_kh = 2)) As Tra \nFROM tbl_soctS Where ten_kh = '" + str + "' AND ngay_nhan = '" + format + "' AND the_loai = 'bc' Group by so_chon Order by so_chon");
            while (true) {
                str2 = "So_chon";
                str3 = "Se_tra";
                if (!GetData.moveToNext()) {
                    break;
                }
                jSONObject2.put("So_chon", GetData.getString(1));
                jSONObject2.put("Da_nhan", GetData.getInt(2));
                jSONObject2.put("Da_tra", GetData.getInt(3));
                jSONObject2.put("Khong_Tien", i);
                jSONObject2.put("Se_tra", (jSONObject2.getInt("Da_nhan") - jSONObject2.getInt("Da_tra")) - jSONObject2.getInt("Khong_Tien"));
                if (jSONObject2.getInt("Se_tra") > 0) {
                    jSONObject.put(GetData.getString(1), jSONObject2.toString());
                }
            }
        } catch (JSONException e) {
            e = e;
        }
        if (jSONObject.length() > 0) {
            Iterator<String> keys = jSONObject.keys();
            ArrayList arrayList = new ArrayList();
            while (keys.hasNext()) {
                try {
                    arrayList.add(new JSONObject(jSONObject.getString(keys.next())));
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
            }
            Collections.sort(arrayList, new Comparator<JSONObject>() {
                @Override
                public int compare(JSONObject jSONObject3, JSONObject jSONObject4) {
                    Integer num;
                    Integer num2 = 0;
                    try {
                        num = Integer.valueOf(jSONObject3.getInt("Se_tra"));
                        try {
                            num2 = Integer.valueOf(jSONObject4.getInt("Se_tra"));
                        } catch (JSONException unused) {
                        }
                    } catch (JSONException unused2) {
                        num = num2;
                    }
                    return num2.compareTo(num);
                }
            });
            int i3 = 0;
            String str13 = "";
            int i4 = 0;
            while (true) {
                String str14 = str2;
                str5 = str12;
                if (i3 >= arrayList.size()) {
                    break;
                }
                try {
                    JSONObject jSONObject3 = (JSONObject) arrayList.get(i3);
                    ArrayList arrayList2 = arrayList;
                    if (i4 > jSONObject3.getInt(str3)) {
                        JSONObject jSONObject4 = new JSONObject();
                        i2 = i3;
                        String[] split = str13.split(",");
                        String str15 = str3;
                        jSONObject4.put("du_lieu", str13 + "x" + i4 + "n");
                        jSONObject4.put("the_loai", "bc");
                        jSONObject4.put("dan_so", str13);
                        jSONObject4.put("so_tien", i4);
                        jSONObject4.put("so_luong", split.length);
                        JSONObject jSONObject5 = this.json_Tralai;
                        jSONObject5.put(String.valueOf(jSONObject5.length() + 1), jSONObject4.toString());
                        String str16 = str13 + "x" + i4 + "n ";
                        StringBuilder sb = new StringBuilder();
                        str4 = str5;
                        sb.append(str4);
                        sb.append(str16);
                        str7 = sb.toString();
                        try {
                            StringBuilder sb2 = new StringBuilder();
                            str9 = str14;
                            sb2.append(jSONObject3.getString(str9));
                            sb2.append(",");
                            str11 = sb2.toString();
                            str8 = str15;
                            str10 = str7;
                            i4 = jSONObject3.getInt(str8);
                        } catch (JSONException e4) {
                            str12 = str7;
                            e4.printStackTrace();
                            return str12;
                        }
                    } else {
                        i2 = i3;
                        str8 = str3;
                        str9 = str14;
                        str10 = str5;
                        str11 = str13 + jSONObject3.getString(str9) + ",";
                        i4 = jSONObject3.getInt(str8);
                    }
                    str13 = str11;
                    i3 = i2 + 1;
                    str2 = str9;
                    arrayList = arrayList2;
                    String str17 = str10;
                    str3 = str8;
                    str12 = str17;
                } catch (JSONException e5) {
                    str4 = str5;
                }
                str12 = str4;
            }
            str4 = str5;
            try {
                if (str13.length() > 0) {
                    JSONObject jSONObject6 = new JSONObject();
                    String[] split2 = str13.split(",");
                    jSONObject6.put("du_lieu", str13 + "x" + i4 + "n");
                    jSONObject6.put("the_loai", "bc");
                    jSONObject6.put("dan_so", str13);
                    jSONObject6.put("so_tien", i4);
                    jSONObject6.put("so_luong", split2.length);
                    if (i4 > 0) {
                        JSONObject jSONObject7 = this.json_Tralai;
                        jSONObject7.put(String.valueOf(jSONObject7.length() + 1), jSONObject6.toString());
                        str6 = str4 + str13 + "x" + i4 + "n ";
                        return "Cang: " + str6;
                    }
                }
                return "Cang: " + str6;
            } catch (JSONException e7) {
                str7 = str6;
                str12 = str7;
                e7.printStackTrace();
                return str12;
            }
        }
        return str12;
    }

    public String TraDe(String str, String str2) throws JSONException {
        String str3= "";
        String str4= "";
        String str5= "";
        Cursor GetData;
        String str6= "";
        String str7= "";
        String str8= "";
        String str9= "";
        String str10= "";
        String str11= "";
        int i;
        String str12= "";
        String str13= "";
        String str14 = "";
        String str15 = "";
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getDefault());
        simpleDateFormat2.setTimeZone(TimeZone.getDefault());
        String format = simpleDateFormat.format(calendar.getTime());
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        if (this.caidat_tg.getInt("khach_de") == 1) {
            str3 = "Det";
            str4 = "det";
            str5 = "de 8";
            JSONObject jSONObject3 = new JSONObject(str2);
            GetData = GetData("Select the_loai, so_chon, Sum(diem_ton*(type_kh = 1)) as Nhan, Sum(diem_ton *(type_kh = 2)) As Tra \nFROM tbl_soctS Where ten_kh = '" + str + "' AND ngay_nhan = '" + format + "' AND the_loai = '" + str4 + "' Group by so_chon Order by so_chon");
            while (true) {
                str6 = "So_chon";
                str7 = "Se_tra";
                if (GetData.moveToNext()) {
                    break;
                }
                if (jSONObject3.has(GetData.getString(1))) {
                    jSONObject2.put("So_chon", GetData.getString(1));
                    jSONObject2.put("Da_nhan", GetData.getInt(2));
                    jSONObject2.put("Da_tra", GetData.getInt(3));
                    jSONObject2.put("Khong_Tien", jSONObject3.getInt(GetData.getString(1)));
                    jSONObject2.put("Se_tra", (jSONObject2.getInt("Da_nhan") - jSONObject2.getInt("Da_tra")) - jSONObject2.getInt("Khong_Tien"));
                    if (jSONObject2.getInt("Se_tra") > 0) {
                        jSONObject.put(GetData.getString(1), jSONObject2.toString());
                    }
                }
            }
            if (jSONObject.length() > 0) {
                Iterator<String> keys = jSONObject.keys();
                ArrayList arrayList = new ArrayList();
                while (keys.hasNext()) {
                    try {
                        arrayList.add(new JSONObject(jSONObject.getString(keys.next())));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Collections.sort(arrayList, new Comparator<JSONObject>() {
                    @Override
                    public int compare(JSONObject jSONObject4, JSONObject jSONObject5) {
                        Integer num;
                        Integer num2 = 0;
                        try {
                            num = Integer.valueOf(jSONObject4.getInt("Se_tra"));
                            try {
                                num2 = Integer.valueOf(jSONObject5.getInt("Se_tra"));
                            } catch (JSONException unused) {
                            }
                        } catch (JSONException unused2) {
                            num = num2;
                        }
                        return num2.compareTo(num);
                    }
                });
                int i2 = 0;
                String str16 = "";
                int i3 = 0;
                while (true) {
                    str9 = str3;
                    String str17 = str6;
                    str10 = str15;
                    if (i2 >= arrayList.size()) {
                        break;
                    }
                    try {
                        JSONObject jSONObject4 = (JSONObject) arrayList.get(i2);
                        ArrayList arrayList2 = arrayList;
                        if (i3 > jSONObject4.getInt(str7)) {
                            JSONObject jSONObject5 = new JSONObject();
                            i = i2;
                            String[] split = str16.split(",");
                            String str18 = str7;
                            jSONObject5.put("du_lieu", str16 + "x" + i3 + "n");
                            jSONObject5.put("the_loai", str5);
                            jSONObject5.put("dan_so", str16);
                            jSONObject5.put("so_tien", i3);
                            jSONObject5.put("so_luong", split.length);
                            JSONObject jSONObject6 = this.json_Tralai;
                            jSONObject6.put(String.valueOf(jSONObject6.length() + 1), jSONObject5.toString());
                            String str19 = str16 + "x" + i3 + "n ";
                            StringBuilder sb = new StringBuilder();
                            str8 = str10;
                            sb.append(str8);
                            sb.append(str19);
                            String sb2 = sb.toString();
                            try {
                                StringBuilder sb3 = new StringBuilder();
                                str11 = str17;
                                sb3.append(jSONObject4.getString(str11));
                                sb3.append(",");
                                str14 = sb3.toString();
                                str12 = str18;
                                str13 = sb2;
                                i3 = jSONObject4.getInt(str12);
                            } catch (JSONException e2) {
                                str15 = sb2;
                            }
                        } else {
                            str11 = str17;
                            i = i2;
                            str12 = str7;
                            str13 = str10;
                            str14 = str16 + jSONObject4.getString(str11) + ",";
                            i3 = jSONObject4.getInt(str12);
                        }
                        str16 = str14;
                        i2 = i + 1;
                        str6 = str11;
                        str3 = str9;
                        arrayList = arrayList2;
                        String str20 = str13;
                        str7 = str12;
                        str15 = str20;
                    } catch (JSONException e4) {
                        str8 = str10;
                    }
                    str15 = str8;
                }
                str8 = str10;
                if (str16.length() > 0) {
                    JSONObject jSONObject7 = new JSONObject();
                    String[] split2 = str16.split(",");
                    jSONObject7.put("du_lieu", str16 + "x" + i3 + "n");
                    jSONObject7.put("the_loai", str5);
                    jSONObject7.put("dan_so", str16);
                    jSONObject7.put("so_tien", i3);
                    jSONObject7.put("so_luong", split2.length);
                    if (i3 > 0) {
                        JSONObject jSONObject8 = this.json_Tralai;
                        jSONObject8.put(String.valueOf(jSONObject8.length() + 1), jSONObject7.toString());
                        str15 = str8 + str16 + "x" + i3 + "n ";
                        return str9 + ": " + str15;
                    }
                }
                str15 = str8;
                return str9 + ": " + str15;
            }
            return str15;
        }
        str4 = "deb";

        return str3;
    }

    public String TraLo(String str, String str2) throws JSONException {
        String str3 = "";
        String str4 = "";
        String str5 = "";
        String str6 = "";
        String str7 = "";
        int i=0;
        String str8 = "";
        String str9 = "";
        String str10 = "";
        String str11 = "";
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getDefault());
        simpleDateFormat2.setTimeZone(TimeZone.getDefault());
        String format = simpleDateFormat.format(calendar.getTime());
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        try {
            JSONObject jSONObject3 = new JSONObject(str2);
            Cursor GetData = GetData("Select the_loai, so_chon, Sum(diem_ton *(type_kh = 1)) as Nhan, Sum(diem_ton *(type_kh = 2)) As Tra \nFROM tbl_soctS Where ten_kh = '" + str + "' AND ngay_nhan = '" + format + "' AND the_loai = 'lo' Group by so_chon Order by so_chon");
            while (true) {
                str3 = "So_chon";
                str4 = "Se_tra";
                if (!GetData.moveToNext()) {
                    break;
                }
                if (jSONObject3.has(GetData.getString(1))) {
                    jSONObject2.put("So_chon", GetData.getString(1));
                    jSONObject2.put("Da_nhan", GetData.getInt(2));
                    jSONObject2.put("Da_tra", GetData.getInt(3));
                    jSONObject2.put("Khong_Tien", jSONObject3.getInt(GetData.getString(1)));
                    jSONObject2.put("Se_tra", (jSONObject2.getInt("Da_nhan") - jSONObject2.getInt("Da_tra")) - jSONObject2.getInt("Khong_Tien"));
                    if (jSONObject2.getInt("Se_tra") > 0) {
                        jSONObject.put(GetData.getString(1), jSONObject2.toString());
                    }
                }
            }
        } catch (JSONException e) {
            e = e;
        }
        if (jSONObject.length() > 0) {
            Iterator<String> keys = jSONObject.keys();
            ArrayList arrayList = new ArrayList();
            while (keys.hasNext()) {
                try {
                    arrayList.add(new JSONObject(jSONObject.getString(keys.next())));
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
            }
            Collections.sort(arrayList, new Comparator<JSONObject>() {
                @Override
                public int compare(JSONObject jSONObject4, JSONObject jSONObject5) {
                    Integer num;
                    Integer num2 = 0;
                    try {
                        num = Integer.valueOf(jSONObject4.getInt("Se_tra"));
                        try {
                            num2 = Integer.valueOf(jSONObject5.getInt("Se_tra"));
                        } catch (JSONException unused) {
                        }
                    } catch (JSONException unused2) {
                        num = num2;
                    }
                    return num2.compareTo(num);
                }
            });
            int i2 = 0;
            String str12 = "";
            int i3 = 0;
            while (true) {
                String str13 = str3;
                str6 = str11;
                if (i2 >= arrayList.size()) {
                    break;
                }
                try {
                    JSONObject jSONObject4 = (JSONObject) arrayList.get(i2);
                    ArrayList arrayList2 = arrayList;
                    if (i3 > jSONObject4.getInt(str4)) {
                        JSONObject jSONObject5 = new JSONObject();
                        i = i2;
                        String[] split = str12.split(",");
                        String str14 = str4;
                        jSONObject5.put("du_lieu", str12 + "x" + i3 + "d");
                        jSONObject5.put("the_loai", "lo");
                        jSONObject5.put("dan_so", str12);
                        jSONObject5.put("so_tien", i3);
                        jSONObject5.put("so_luong", split.length);
                        JSONObject jSONObject6 = this.json_Tralai;
                        jSONObject6.put(String.valueOf(jSONObject6.length() + 1), jSONObject5.toString());
                        String str15 = str12 + "x" + i3 + "d ";
                        StringBuilder sb = new StringBuilder();
                        str5 = str6;
                        sb.append(str5);
                        sb.append(str15);
                        String sb2 = sb.toString();
                        try {
                            StringBuilder sb3 = new StringBuilder();
                            str7 = str13;
                            sb3.append(jSONObject4.getString(str7));
                            sb3.append(",");
                            str10 = sb3.toString();
                            str8 = str14;
                            str9 = sb2;
                            i3 = jSONObject4.getInt(str8);
                        } catch (JSONException e3) {
                            str11 = sb2;
                        }
                    } else {
                        str7 = str13;
                        i = i2;
                        str8 = str4;
                        str9 = str6;
                        str10 = str12 + jSONObject4.getString(str7) + ",";
                        i3 = jSONObject4.getInt(str8);
                    }
                    str12 = str10;
                    i2 = i + 1;
                    str3 = str7;
                    arrayList = arrayList2;
                    String str16 = str9;
                    str4 = str8;
                    str11 = str16;
                } catch (JSONException e5) {
                    str5 = str6;
                }
                str11 = str5;
            }
            str5 = str6;
            if (str12.length() > 0) {
                JSONObject jSONObject7 = new JSONObject();
                String[] split2 = str12.split(",");
                jSONObject7.put("du_lieu", str12 + "x" + i3 + "d");
                jSONObject7.put("the_loai", "lo");
                jSONObject7.put("dan_so", str12);
                jSONObject7.put("so_tien", i3);
                jSONObject7.put("so_luong", split2.length);
                if (i3 > 0) {
                    JSONObject jSONObject8 = this.json_Tralai;
                    jSONObject8.put(String.valueOf(jSONObject8.length() + 1), jSONObject7.toString());
                    str11 = str5 + str12 + "x" + i3 + "d ";
                    return "Lo: " + str11;
                }
            }
            str11 = str5;
            return "Lo: " + str11;
        }
        return str11;
    }

    public String TraXi(String str, String str2) throws JSONException {
        String str3= "";
        String str4 = "";
        String str5= "";
        String str6= "";
        String str7= "";
        String str8= "";
        String str9= "";
        String str10= "";
        int i = 0;
        String str11 = "";
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getDefault());
        simpleDateFormat2.setTimeZone(TimeZone.getDefault());
        String format = simpleDateFormat.format(calendar.getTime());
        JSONObject jSONObject = new JSONObject();
        try {
            JSONObject jSONObject2 = new JSONObject(str2);
            Cursor GetData = GetData("Select the_loai, so_chon, Sum(diem_ton *(type_kh = 1)) as Nhan, Sum(diem_ton *(type_kh = 2)) As Tra \nFROM tbl_soctS Where ten_kh = '" + str + "' AND ngay_nhan = '" + format + "' AND the_loai = 'xi' Group by so_chon");
            while (true) {
                str3 = "Se_tra";
                str4 = "So_chon";
                if (!GetData.moveToNext()) {
                    break;
                }
                JSONObject jSONObject3 = new JSONObject();
                jSONObject3.put("So_chon", GetData.getString(1));
                jSONObject3.put("Da_nhan", GetData.getInt(2));
                jSONObject3.put("Da_tra", GetData.getInt(3));
                if (jSONObject2.has("xien2") && jSONObject3.getString("So_chon").length() == 5) {
                    jSONObject3.put("Khong_Tien", jSONObject2.getInt("xien2"));
                    i = jSONObject3.getInt("Da_nhan");
                } else if (jSONObject2.has("xien3") && jSONObject3.getString("So_chon").length() == 8) {
                    jSONObject3.put("Khong_Tien", jSONObject2.getInt("xien3"));
                    i = jSONObject3.getInt("Da_nhan");
                } else {
                    if (jSONObject2.has("xien4") && jSONObject3.getString("So_chon").length() == 11) {
                        jSONObject3.put("Khong_Tien", jSONObject2.getInt("xien4"));
                        i = jSONObject3.getInt("Da_nhan");
                    }
                    if (jSONObject3.getInt("Se_tra") <= 0) {
                        jSONObject.put(GetData.getString(1), jSONObject3.toString());
                    }
                }
                jSONObject3.put("Se_tra", (i - jSONObject3.getInt("Da_tra")) - jSONObject3.getInt("Khong_Tien"));
                if (jSONObject3.getInt("Se_tra") <= 0) {
                }
            }
        } catch (JSONException e) {
            e = e;
        }
        if (jSONObject.length() > 0) {
            Iterator<String> keys = jSONObject.keys();
            ArrayList arrayList = new ArrayList();
            while (keys.hasNext()) {
                try {
                    arrayList.add(new JSONObject(jSONObject.getString(keys.next())));
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
            }
            Collections.sort(arrayList, new Comparator<JSONObject>() {
                @Override
                public int compare(JSONObject jSONObject4, JSONObject jSONObject5) {
                    Integer num;
                    Integer num2 = 0;
                    try {
                        num = Integer.valueOf(jSONObject4.getInt("Se_tra"));
                        try {
                            num2 = Integer.valueOf(jSONObject5.getInt("Se_tra"));
                        } catch (JSONException unused) {
                        }
                    } catch (JSONException unused2) {
                        num = num2;
                    }
                    return num2.compareTo(num);
                }
            });
            int i2 = 0;
            String str12 = "";
            String str13 = str12;
            int i3 = 0;
            while (true) {
                str6 = str11;
                String str14 = str4;
                str7 = str13;
                if (i2 >= arrayList.size()) {
                    break;
                }
                try {
                    JSONObject jSONObject4 = (JSONObject) arrayList.get(i2);
                    ArrayList arrayList2 = arrayList;
                    int i4 = i2;
                    if (i3 > jSONObject4.getInt(str3)) {
                        JSONObject jSONObject5 = new JSONObject();
                        String str15 = str3;
                        String[] split = str12.split(" ");
                        jSONObject5.put("du_lieu", str12 + "x" + i3 + "n");
                        jSONObject5.put("the_loai", "xi");
                        jSONObject5.put("dan_so", str12);
                        jSONObject5.put("so_tien", i3);
                        jSONObject5.put("so_luong", split.length);
                        JSONObject jSONObject6 = this.json_Tralai;
                        jSONObject6.put(String.valueOf(jSONObject6.length() + 1), jSONObject5.toString());
                        String str16 = str12 + "x" + i3 + "n\n";
                        StringBuilder sb = new StringBuilder();
                        sb.append(str7);
                        str9 = str14;
                        sb.append(jSONObject4.getString(str9));
                        sb.append("x");
                        str8 = str15;
                        sb.append(jSONObject4.getInt(str8));
                        sb.append("n\n");
                        String sb2 = sb.toString();
                        StringBuilder sb3 = new StringBuilder();
                        str5 = str6;
                        sb3.append(str5);
                        sb3.append(str16);
                        String sb4 = sb3.toString();
                        try {
                            String str17 = jSONObject4.getString(str9) + " ";
                            str13 = sb2;
                            str10 = sb4;
                            i3 = jSONObject4.getInt(str8);
                            str12 = str17;
                        } catch (JSONException e3) {
                            str11 = sb4;
                        }
                    } else {
                        str8 = str3;
                        str9 = str14;
                        str10 = str6;
                        String str18 = str12 + jSONObject4.getString(str9) + " ";
                        str13 = str7 + jSONObject4.getString(str9) + "x" + jSONObject4.getInt(str8) + "n\n";
                        str12 = str18;
                        i3 = jSONObject4.getInt(str8);
                    }
                    i2 = i4 + 1;
                    str3 = str8;
                    arrayList = arrayList2;
                    String str19 = str10;
                    str4 = str9;
                    str11 = str19;
                } catch (JSONException e5) {
                    str5 = str6;
                }
                str11 = str5;
            }
            str5 = str6;
            if (str12.length() > 0) {
                JSONObject jSONObject7 = new JSONObject();
                String[] split2 = str12.split(" ");
                jSONObject7.put("du_lieu", str12 + "x" + i3 + "n");
                jSONObject7.put("the_loai", "xi");
                jSONObject7.put("dan_so", str12);
                jSONObject7.put("so_tien", i3);
                jSONObject7.put("so_luong", split2.length);
                if (i3 > 0) {
                    JSONObject jSONObject8 = this.json_Tralai;
                    jSONObject8.put(String.valueOf(jSONObject8.length() + 1), jSONObject7.toString());
                    str11 = str5 + jSONObject7.getString("du_lieu") + " \n";
                    return "Xien:\n" + str7 + "\n";
                }
            }
            str11 = str5;
            return "Xien:\n" + str7 + "\n";
        }
        return str11;
    }

    public void TralaiSO(int i) {
        this.json_Tralai = new JSONObject();
        Cursor GetData = GetData("Select * from tbl_tinnhanS where id = " + i);
        GetData.moveToFirst();
        try {
            Cursor GetData2 = GetData("Select * From tbl_kh_new Where ten_kh = '" + GetData.getString(4) + "'");
            if (GetData2.getCount() > 0 && GetData2.moveToFirst()) {
                JSONObject jSONObject = new JSONObject(GetData2.getString(6));
                this.json = jSONObject;
                String str = "";
                if (jSONObject.getString("danDe").length() > 0) {
                    String TraDe = TraDe(GetData2.getString(0), this.json.getString("soDe"));
                    if (TraDe.length() > 0) {
                        str = "\n" + TraDe;
                    }
                }
                if (this.json.getString("danLo").length() > 0) {
                    String TraLo = TraLo(GetData2.getString(0), this.json.getString("soLo"));
                    if (TraLo.length() > 0) {
                        str = str + "\n" + TraLo;
                    }
                }
                if (this.json.getInt("xien2") > 0 || this.json.getInt("xien3") > 0 || this.json.getInt("xien4") > 0) {
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.put("xien2", this.json.getInt("xien2"));
                    jSONObject2.put("xien3", this.json.getInt("xien3"));
                    jSONObject2.put("xien4", this.json.getInt("xien4"));
                    String TraXi = TraXi(GetData2.getString(0), jSONObject2.toString());
                    if (TraXi.length() > 0) {
                        str = str + "\n" + TraXi;
                    }
                }
                if (this.json.getInt("cang") > 0) {
                    String TraCang = TraCang(GetData2.getString(0), this.json.getInt("cang"));
                    if (TraCang.length() > 0) {
                        str = str + "\n" + TraCang;
                    }
                }
                if (this.json_Tralai.length() > 0) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(new Date());
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("HH:mm:ss");
                    simpleDateFormat.setTimeZone(TimeZone.getDefault());
                    simpleDateFormat2.setTimeZone(TimeZone.getDefault());
                    String format = simpleDateFormat.format(calendar.getTime());
                    String format2 = simpleDateFormat2.format(calendar.getTime());
                    Cursor GetData3 = GetData("Select max(so_tin_nhan) from tbl_tinnhanS WHERE ngay_nhan = '" + format + "' AND ten_kh = '" + GetData2.getString(0) + "' and type_kh = 2");
                    GetData3.moveToFirst();
                    int i2 = GetData3.getInt(0) + 1;
                    final String str2 = "Tra lai " + i2 + ":" + str;
                    QueryData("Insert Into tbl_tinnhanS values (null, '" + format + "', '" + format2 + "',2, '" + GetData2.getString(0) + "', '" + GetData2.getString(1) + "','" + GetData2.getString(2) + "', " + i2 + ", '" + str2.trim() + "','" + str2.substring(str2.indexOf(":") + 1) + "','" + str2.substring(str2.indexOf(":") + 1) + "', 'ko',0,0,0, '" + this.json_Tralai.toString() + "')");
                    StringBuilder sb = new StringBuilder();
                    sb.append("Select id From tbl_tinnhanS where ngay_nhan = '");
                    sb.append(format);
                    sb.append("' AND type_kh = 2 AND ten_kh ='");
                    sb.append(GetData2.getString(0));
                    sb.append("' AND nd_goc = '");
                    sb.append(str2.trim());
                    sb.append("'");
                    Cursor GetData4 = GetData(sb.toString());
                    GetData4.moveToFirst();
                    Update_TinNhanGoc(GetData4.getInt(0), 2);
                    if (GetData2.getString(2).indexOf("TL") > -1) {
                        long j = GetData2.getLong(1);
                        Handler handler = new Handler(Looper.getMainLooper());
                        final Long valueOf = Long.valueOf(j);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                new MainActivity();
                                MainActivity.sendMessage(valueOf.longValue(), str2);
                            }
                        });
                    } else if (GetData2.getString(2).indexOf("sms") > -1) {
                        SendSMS(GetData2.getString(1), str2);
                    } else {
                        JSONObject jSONObject3 = new JSONObject(MainActivity.json_Tinnhan.getString(GetData2.getString(1)));
                        if (jSONObject3.getInt("Time") > 3) {
                            new NotificationNewReader().NotificationWearReader(GetData2.getString(1), str2);
                        } else {
                            jSONObject3.put(str2, "OK");
                            MainActivity.json_Tinnhan.put(GetData2.getString(1), jSONObject3);
                        }
                    }
                    NhapSoChiTiet(GetData4.getInt(0));
                    GetData4.close();
                }
            }
            GetData2.close();
        } catch (Exception e) {
            e.getMessage();
        }
        GetData.close();
    }

    public void Update_TinNhanGoc(int i, int i2) throws JSONException {
        Cursor GetData = GetData("Select * From tbl_tinnhanS WHERE id = " + i);
        GetData.moveToFirst();
        if (GetData.getString(11).indexOf("ok1") > -1) {
            Cursor GetData2 = GetData("Select nd_phantich FROM tbl_tinnhanS WHERE id = " + i);
            GetData2.moveToFirst();
            String string = GetData2.getString(0);
            for (int i3 = 1; i3 < 6; i3++) {
                string = string.replaceAll("\\*", "");
            }
            QueryData("Update tbl_tinnhanS set nd_phantich = '" + string + "', nd_sua = '" + string + "' WHERE id = " + i);
            NhapSoChiTiet(i);
            StringBuilder sb = new StringBuilder();
            sb.append("Update tbl_tinnhanS set phat_hien_loi = 'ok' WHERE id = ");
            sb.append(i);
            QueryData(sb.toString());
        } else {
            String fixTinNhan1 = Congthuc.fixTinNhan1(Congthuc.convertKhongDau(GetData.getString(10)));
            Cursor GetData3 = GetData("Select * From thay_the_phu");
            while (GetData3.moveToNext()) {
                fixTinNhan1 = fixTinNhan1.replaceAll(GetData3.getString(1), GetData3.getString(2)).replace("  ", " ");
            }
            if (GetData3 != null && !GetData3.isClosed()) {
                GetData3.close();
            }
            String fixTinNhan12 = Congthuc.fixTinNhan1(fixTinNhan1);
            QueryData("Update tbl_tinnhanS set nd_phantich = '" + fixTinNhan12 + "', nd_sua = '" + fixTinNhan12 + "' WHERE id = " + i);
            String str = null;
            if (fixTinNhan12.indexOf("bo") > -1 && fixTinNhan12.indexOf("bor") == -1) {
                int indexOf = fixTinNhan12.indexOf("bo") + 3;
                while (indexOf < fixTinNhan12.length()) {
                    int i4 = indexOf + 1;
                    if (fixTinNhan12.substring(indexOf, i4).indexOf(" ") == -1 && !Congthuc.isNumeric(fixTinNhan12.substring(indexOf, i4))) {
                        str = "Không hiểu " + fixTinNhan12.substring(fixTinNhan12.indexOf("bo"), fixTinNhan12.length());
                    }
                    indexOf = i4;
                }
            }
            if (fixTinNhan12.indexOf("Không hiểu") > -1) {
                QueryData("Update tbl_tinnhanS set nd_phantich = '" + fixTinNhan12 + "', nd_sua = '" + fixTinNhan12 + "',  phat_hien_loi ='" + str + "' Where id = " + i);
                createNotification(fixTinNhan12, this.mcontext);
            } else {
                NhanTinNhan(Integer.valueOf(i), i2);
                GetData = GetData("Select * From tbl_tinnhanS WHERE id = " + i);
                GetData.moveToFirst();
                String string2 = GetData.getString(11);
                if (string2.indexOf("Không hiểu") > -1) {
                    createNotification(string2, this.mcontext);
                } else {
                    NhapSoChiTiet(i);
                }
            }
        }
        if (GetData == null || GetData.isClosed()) {
            return;
        }
        GetData.close();
    }

    public String XuatDanTon2(String str, String str2, int i, int i2) throws JSONException {
        String str3;
        String str4;
        String str5;
        String str6;
        String str7;
        Cursor GetData;
        int i3 = 1;
        int i4;
        int i5;
        int i6 = 0;
        int i7;
        int i8;
        String str8;
        int i9;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        simpleDateFormat.setTimeZone(TimeZone.getDefault());
        String format = simpleDateFormat.format(calendar.getTime());
        String str9 = "";
        int parseInt = Integer.parseInt((str2.length() == 0 ? "0" : str2).replaceAll("%", "").replaceAll("n", "").replaceAll("k", "").replaceAll("d", "").replaceAll(">", ""));
        String str10 = "n ";
        if (str == "deb") {
            str6 = "De:";
            str9 = "Om_deB";
            str7 = "(the_loai = 'deb' or the_loai = 'det')";
        } else if (str == "dea") {
            str6 = "Dau DB:";
            str9 = "Om_deA";
            str7 = "the_loai = 'dea'";
        } else if (str == "dec") {
            str6 = "Dau nhat:";
            str9 = "Om_deC";
            str7 = "the_loai = 'dec'";
        } else if (str == "ded") {
            str6 = "Dit nhat:";
            str9 = "Om_deD";
            str7 = "the_loai = 'ded'";
        } else {
            if (str != "lo") {
                str3 = null;
                str4 = "n ";
                str5 = "";
                GetData = GetData("Select tbl_soctS.So_chon\n, Sum((tbl_soctS.type_kh = 1) * (100-tbl_soctS.diem_khachgiu)*diem_quydoi/100) as diem\n, so_om." + str9 + " + sum(tbl_soctS.diem_dly_giu*tbl_soctS.diem_quydoi/100) as So_Om\n, Sum((tbl_soctS.type_kh =2) * tbl_soctS.diem_quydoi) as chuyen\n, Sum((tbl_soctS.type_kh =1) * (100-tbl_soctS.diem_khachgiu-tbl_soctS.diem_dly_giu)*diem_quydoi/100) - Sum((tbl_soctS.type_kh =2) * tbl_soctS.diem_quydoi) - so_om." + str9 + " as ton\n, so_nhay  From so_om Left Join tbl_soctS On tbl_soctS.so_chon = so_om.So\n Where tbl_soctS.ngay_nhan='" + format + "' AND " + str3 + " GROUP by so_om.So Order by ton DESC, diem DESC");
                if (MainActivity.jSon_Setting.getInt("lam_tron") != 0) {
                    if (MainActivity.jSon_Setting.getInt("lam_tron") == 1) {
                        i3 = 10;
                    } else if (MainActivity.jSon_Setting.getInt("lam_tron") == 2) {
                        i3 = 50;
                    } else if (MainActivity.jSon_Setting.getInt("lam_tron") == 3) {
                        i3 = 100;
                    }
                    int i10 = i > 0 ? i - 1 : i;
                    int i11 = 0;
                    int i12 = 0;
                    i4 = 0;
                    while (GetData.moveToNext()) {
                        if (i11 < i10 || i11 > i2 - 1) {
                            i5 = i10;
                        } else {
                            if (parseInt == 0) {
                                i8 = (GetData.getInt(4) / i3) * i3;
                                i5 = i10;
                            } else {
                                i5 = i10;
                                if (str2.indexOf("%") > -1) {
                                    i7 = ((GetData.getInt(4) * parseInt) / i3) / 100;
                                } else {
                                    if (str2.indexOf(">") > -1) {
                                        i6 = GetData.getInt(4) - parseInt;
                                    } else if (GetData.getInt(4) > parseInt) {
                                        i7 = parseInt / i3;
                                    } else {
                                        i6 = GetData.getInt(4);
                                    }
                                    i7 = i6 / i3;
                                }
                                i8 = i7 * i3;
                            }
                            if (i8 > 0) {
                                if (i4 > i8) {
                                    str8 = (str5 + "x" + i4 + str4) + GetData.getString(0) + ",";
                                    i9 = 1;
                                    i12 = 0;
                                } else {
                                    str8 = str5 + GetData.getString(0) + ",";
                                    i9 = 1;
                                }
                                i12 += i9;
                                str5 = str8;
                                i4 = i8;
                                i11++;
                                i10 = i5;
                            }
                        }
                        i11++;
                        i10 = i5;
                    }
                    if (str5.length() > 4 && i12 > 0) {
                        str5 = str5 + "x" + i4 + str4;
                    }
                    if (GetData != null) {
                        GetData.close();
                    }
                    return str5;
                }
                i3 = 1;
                if (i > 0) {
                }
                int i112 = 0;
                int i122 = 0;
                i4 = 0;
                while (GetData.moveToNext()) {
                }
                if (str5.length() > 4) {
                    str5 = str5 + "x" + i4 + str4;
                }
                if (GetData != null) {
                }
                return str5;
            }
            str6 = "Lo:";
            str9 = "Om_lo";
            str7 = "the_loai = 'lo'";
            str10 = "d ";
        }
        String str11 = str10;
        str5 = str6;
        str3 = str7;
        str4 = str11;
        GetData = GetData("Select tbl_soctS.So_chon\n, Sum((tbl_soctS.type_kh = 1) * (100-tbl_soctS.diem_khachgiu)*diem_quydoi/100) as diem\n, so_om." + str9 + " + sum(tbl_soctS.diem_dly_giu*tbl_soctS.diem_quydoi/100) as So_Om\n, Sum((tbl_soctS.type_kh =2) * tbl_soctS.diem_quydoi) as chuyen\n, Sum((tbl_soctS.type_kh =1) * (100-tbl_soctS.diem_khachgiu-tbl_soctS.diem_dly_giu)*diem_quydoi/100) - Sum((tbl_soctS.type_kh =2) * tbl_soctS.diem_quydoi) - so_om." + str9 + " as ton\n, so_nhay  From so_om Left Join tbl_soctS On tbl_soctS.so_chon = so_om.So\n Where tbl_soctS.ngay_nhan='" + format + "' AND " + str3 + " GROUP by so_om.So Order by ton DESC, diem DESC");
        if (MainActivity.jSon_Setting.getInt("lam_tron") != 0) {
        }
        i3 = 1;
        if (i > 0) {
        }
        int i1122 = 0;
        int i1222 = 0;
        i4 = 0;
        while (GetData.moveToNext()) {
        }
        if (str5.length() > 4) {
        }
        if (GetData != null) {
        }
        return str5;
    }

    @Override
    public void onCreate(SQLiteDatabase sQLiteDatabase) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }
}