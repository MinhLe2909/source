package tamhoang.ldpro4.formula;

import net.lingala.zip4j.util.InternalZipConstants;

import org.json.JSONException;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import tamhoang.ldpro4.ui.home.MainActivity;

public class Congthuc {
    public static String[][] mang;

    public static boolean CheckDate(String str) {
        if (str == null) {
            str = "01/01/2018";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(simpleDateFormat.parse(str));
            calendar.add(Calendar.DATE, 1);
            return new Date().before(simpleDateFormat.parse(simpleDateFormat.format(calendar.getTime())));
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean CheckTime(String str) {
        Date parseDate = parseDate(str);
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        return parseDate(hour + ":" + minutes).after(parseDate);
    }

    //TODO: 7/3/2024 CongThuc - FixDan
    public static String FixDan(String str) {
        StringBuilder sb = new StringBuilder();
        String str2 = "";
        String[] split = str.replaceAll(":", "").replaceAll(" //. ", "").replaceAll(" , ", "").replaceAll("\\.", ",").split(",");
        for (int i = 0; i < split.length; i++) {
            if (!isNumeric(split[i]) || split[i].length() != 2) {
                if (isNumeric(split[i]) && split[i].length() == 3) {
                    if (split[i].charAt(0) != split[i].charAt(2)) {
                        sb.append("Không hiểu ");
                        str2 = split[i];
                    } else {
                        String str4 = split[i].substring(0, 2) + ",";
                        sb = new StringBuilder();
                        sb.append(str4);
                    }
                } else if (split[i].length() > 0) {
                    sb.append("Không hiểu ");
                    str2 = split[i];
                }
                sb.append(str2);
                return sb.toString();
            }
            sb.append(split[i]);
            sb.append(",");
        }
        return sb.toString();
    }

    public static String GhepBo(String str) {
        String[] strArr = {"00,050,55", "010,060,515,565", "020,070,525,575", "030,080,535,585", "040,090,545,595", "11,66,161", "121,171,626,676", "131,181,636,686", "141,191,646,696", "22,77,272", "232,282,737,787", "242,292,747,797", "33,88,383", "343,393,848,898", "44,494,99"};
        String str2 = "";
        for (int i = 0; i < str.length() - 1; i++) {
            int i2 = i + 2;
            if (isNumeric(str.substring(i, i2))) {
                int i3 = 0;
                while (true) {
                    if (i3 >= 15) {
                        break;
                    }
                    if (strArr[i3].indexOf(str.substring(i, i2)) > -1) {
                        str2 = str2 + strArr[i3] + ",";
                        break;
                    }
                    i3++;
                }
            }
        }
        return FixDan(str2);
    }

    public static String GhepDau(String str) {
        String[] strArr = new String[15];
        if (!xuLymem(str)) {
            return "Không hiểu " + str;
        }
        int i = 0;
        strArr[0] = "00,01,02,03,04,05,06,07,08,09,";
        strArr[1] = "10,11,12,13,14,15,16,17,18,19,";
        strArr[2] = "20,21,22,23,24,25,26,27,28,29,";
        strArr[3] = "30,31,32,33,34,35,36,37,38,39,";
        strArr[4] = "40,41,42,43,44,45,46,47,48,49,";
        strArr[5] = "50,51,52,53,54,55,56,57,58,59,";
        strArr[6] = "60,61,62,63,64,65,66,67,68,69,";
        strArr[7] = "70,71,72,73,74,75,76,77,78,79,";
        strArr[8] = "80,81,82,83,84,85,86,87,88,89,";
        strArr[9] = "90,91,92,93,94,95,96,97,98,99,";
        StringBuilder stringBuilder = new StringBuilder();
        while (i < str.length()) {
            int i2 = i + 1;
            if (isNumeric(str.substring(i, i2))) {
                stringBuilder.append(strArr[Character.getNumericValue(str.charAt(i))]);
            }
            i = i2;
        }
        return stringBuilder.toString();
    }

    public static String GhepDit(String str) {
        String[] strArr = new String[15];
        if (!xuLymem(str)) {
            return "Không hiểu " + str;
        }
        int i = 0;
        strArr[0] = "00,10,20,30,40,50,60,70,80,90,";
        strArr[1] = "01,11,21,31,41,51,61,71,81,91,";
        strArr[2] = "02,12,22,32,42,52,62,72,82,92,";
        strArr[3] = "03,13,23,33,43,53,63,73,83,93,";
        strArr[4] = "04,14,24,34,44,54,64,74,84,94,";
        strArr[5] = "05,15,25,35,45,55,65,75,85,95,";
        strArr[6] = "06,16,26,36,46,56,66,76,86,96,";
        strArr[7] = "07,17,27,37,47,57,67,77,87,97,";
        strArr[8] = "08,18,28,38,48,58,68,78,88,98,";
        strArr[9] = "09,19,29,39,49,59,69,79,89,99,";
        StringBuilder stringBuilder = new StringBuilder();
        while (i < str.length()) {
            int i2 = i + 1;
            if (isNumeric(str.substring(i, i2))) {
                stringBuilder.append(strArr[Character.getNumericValue(str.charAt(i))]);
            }
            i = i2;
        }
        return stringBuilder.toString();
    }

    public static String GhepTong(String str) {
        String[] strArr = new String[15];
        if (!xuLymem(str)) {
            return "Không hiểu " + str;
        }
        int i = 0;
        strArr[0] = "00,19,28,37,46,55,64,73,82,91,";
        strArr[1] = "01,10,29,38,47,56,65,74,83,92,";
        strArr[2] = "02,11,20,39,48,57,66,75,84,93,";
        strArr[3] = "03,12,21,30,49,58,67,76,85,94,";
        strArr[4] = "04,13,22,31,40,59,68,77,86,95,";
        strArr[5] = "05,14,23,32,41,50,69,78,87,96,";
        strArr[6] = "06,15,24,33,42,51,60,79,88,97,";
        strArr[7] = "07,16,25,34,43,52,61,70,89,98,";
        strArr[8] = "08,17,26,35,44,53,62,71,80,99,";
        strArr[9] = "09,18,27,36,45,54,63,72,81,90,";
        StringBuilder stringBuilder = new StringBuilder();
        while (i < str.length()) {
            int i2 = i + 1;
            if (isNumeric(str.substring(i, i2))) {
                stringBuilder.append(strArr[Character.getNumericValue(str.charAt(i))]);
            }
            i = i2;
        }
        return stringBuilder.toString();
    }

    public static String NhanTinNhan(String str) {
        String str2;
        String str3;
        String str4;
        String str5;
        String str6;
        String str7;
        String str8;
        String str9;
        String str22 = "";
        String str23;
        String dayso = "";
        String str10;
        String dayso2;
        int i3;
        String dayso3 = "";
        String dayso4;
        String str24 = "";
        String str11 = "\n";
        String str12 = " ";
        String str13 = "\\.";
        String str14 = ",";
        String str15 = Xuly_DauTN(fixTinNhan1(convertKhongDau(str.replaceAll("\n", " ").replaceAll("\\.", ","))));
        String str16 = "Không hiểu ";
        if (str15.length() < 3) {
            return "Không hiểu " + str15;
        }
        String str17 = fixTinNhan(str15);
        if (str17.indexOf("de") == -1 && str17.indexOf("lo") == -1) {
            return "Không hiểu dạng";
        }
        if (str17.length() < 8) {
            return "Không hiểu " + str17;
        }
        String str18 = PhanTichTinNhan(str17);
        int i32 = 0;
        String dayso5 = "Không hiểu";
        if (str18.indexOf("x ") == -1 && str18.indexOf("Không hiểu") == -1) {
            return "Không hiểu " + str18;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int rw = 0;
        SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
        hourFormat.setTimeZone(TimeZone.getDefault());
        String mGionhan = hourFormat.format(calendar.getTime());
        String theodoi = null;
        if (str18.indexOf("Không hiểu") > -1) {
            return str18;
        }
        String[][] mang2 = (String[][]) Array.newInstance((Class<?>) String.class, 300, 4);
        String str19 = str18.replaceAll(" , ", " ");
        int i = 1;
        while (true) {
            str2 = str24;
            if (i >= 10) {
                break;
            }
            str19 = str19.replaceAll("  ", " ");
            i++;
            str24 = str2;
        }
        StringBuilder sb = new StringBuilder();
        String dayso6 = "";
        String dayso7 = str19.trim();
        sb.append(dayso7);
        sb.append(" ");
        String str20 = sb.toString();
        int k = 0;
        String str25 = str2;
        int i1 = -1;
        while (true) {
            String str26 = str25;
            int i2 = i;
            int indexOf = str20.indexOf(" x ", i1 + 1);
            i1 = indexOf;
            Calendar calendar2 = calendar;
            String mGionhan2 = mGionhan;
            if (indexOf == -1) {
                str3 = str20;
                str4 = str11;
                str5 = str12;
                str6 = str13;
                str7 = str14;
                str8 = str16;
                str9 = dayso5;
                str22 = theodoi;
                str23 = dayso6;
                dayso = str26;
                break;
            }
            String tien = "";
            int i22 = i1;
            while (true) {
                str4 = str11;
                if (i22 >= str20.length()) {
                    str7 = str14;
                    break;
                }
                str7 = str14;
                if (str20.charAt(i22) == ' ' && tien.length() > 0) {
                    break;
                }
                if ("0123456789,tr".indexOf(str20.substring(i22, i22 + 1)) > -1) {
                    tien = tien + str20.substring(i22, i22 + 1);
                }
                i22++;
                str11 = str4;
                str14 = str7;
            }
            String dtien = "";
            int i33 = i22;
            while (true) {
                String tien2 = tien;
                if (i33 >= str20.length()) {
                    str6 = str13;
                    break;
                }
                if (!Character.isLetter(str20.charAt(i33)) && dtien.length() > 0) {
                    str6 = str13;
                    break;
                }
                dtien = dtien + str20.substring(i33, i33 + 1);
                i33++;
                tien = tien2;
                str13 = str13;
            }
            if (i22 == i33) {
                i33--;
            }
            str5 = str12;
            String str21 = str16;
            String str27 = dayso5;
            if (dtien.indexOf("dau") <= -1 && dtien.indexOf("dit") <= -1 && dtien.indexOf("tong") <= -1 && dtien.indexOf("cham") <= -1 && dtien.indexOf("dan") <= -1 && dtien.indexOf("boj") <= -1 && dtien.indexOf("lo") <= -1 && dtien.indexOf("de") <= -1 && dtien.indexOf("xi") <= -1 && dtien.indexOf("xn") <= -1 && dtien.indexOf("hc") <= -1 && dtien.indexOf("xq") <= -1 && dtien.indexOf("xg") <= -1 && dtien.indexOf("bc") <= -1 && dtien.indexOf("kep") <= -1 && dtien.indexOf("sat") <= -1 && dtien.indexOf("to") <= -1 && dtien.indexOf("nho") <= -1 && dtien.indexOf("chan") <= -1 && dtien.indexOf("le") <= -1 && dtien.indexOf("ko") <= -1 && dtien.indexOf("chia") <= -1 && dtien.indexOf("duoi") <= -1 && dtien.indexOf("be") <= -1) {
                if (dtien.indexOf("x ") > -1) {
                    i3 = i22 - 1;
                    while (true) {
                        if (i3 <= 0) {
                            dayso2 = dayso6;
                            break;
                        }
                        if (isNumeric(str20.substring(i3, i3 + 1))) {
                            i3--;
                        } else {
                            String dayso8 = str20.substring(k, i3 + 1);
                            int k2 = i3 + 1;
                            String theodoi2 = str20.substring(k2);
                            theodoi = theodoi2;
                            k = k2;
                            dayso2 = dayso8;
                            break;
                        }
                    }
                } else {
                    String dayso9 = str20.substring(k, i33);
                    if (dayso9.substring(0, 4).indexOf("bor") <= -1) {
                        dayso4 = dayso9;
                    } else {
                        dayso4 = "de " + dayso9;
                    }
                    i3 = i33;
                    k = i3;
                    theodoi = str20.substring(i3);
                    dayso2 = dayso4;
                }
                String str1 = dayso2.replaceAll("\\n", "");
                rw++;
                mang2[rw][0] = dayso2;
                int i34 = i3;
                if (dayso2.indexOf("lo") <= -1) {
                    mang2[rw][1] = "lo";
                    dayso3 = dayso2.replaceAll("lo", "").replaceAll("lo:", "").replaceAll("lo :", "");
                    str3 = str20;
                } else {
                    str3 = str20;
                    if (dayso2.indexOf("dea") > -1) {
                        mang2[rw][1] = "de dau db";
                        dayso3 = dayso2.replaceAll("dea", "").replaceAll("dea:", "").replaceAll("dea :", "");
                    } else if (dayso2.indexOf("deb") > -1) {
                        mang2[rw][1] = "de dit db";
                        dayso3 = dayso2.replaceAll("deb", "").replaceAll("deb:", "").replaceAll("deb :", "");
                    } else if (dayso2.indexOf("dec") > -1) {
                        mang2[rw][1] = "de dau nhat";
                        dayso3 = dayso2.replaceAll("dec", "").replaceAll("dec:", "").replaceAll("dec :", "");
                    } else if (dayso2.indexOf("ded") > -1) {
                        mang2[rw][1] = "de dit nhat";
                        dayso3 = dayso2.replaceAll("ded", "").replaceAll("ded:", "").replaceAll("ded :", "");
                    } else if (dayso2.indexOf("de") > -1) {
                        mang2[rw][1] = "de dit db";
                        dayso3 = dayso2.replaceAll("de", "").replaceAll("de:", "").replaceAll("de :", "");
                    } else if (dayso2.indexOf("bc") > -1) {
                        mang2[rw][1] = "bc";
                        dayso3 = dayso2.replaceAll("bc", "").replaceAll("bc:", "").replaceAll("bc :", "");
                    } else if (dayso2.indexOf("xi") > -1) {
                        mang2[rw][1] = "xi";
                        dayso3 = dayso2.replaceAll("xi", "").replaceAll("xien", "");
                    } else if (dayso2.indexOf("xq") > -1) {
                        mang2[rw][1] = "xq";
                        dayso3 = dayso2.replaceAll("xq", "");
                    } else {
                        mang2[rw][1] = mang2[rw - 1][1];
                        dayso3 = dayso2;
                    }
                }
                mang2[rw][2] = XulyLoDe(dayso3.substring(0, dayso3.indexOf("x")));
                mang2[rw][3] = XulyTien(dayso3.substring(dayso3.indexOf("x"), dayso3.length()));
                str25 = "";
                str9 = str27;
                if (mang2[rw][2].indexOf(str9) <= -1) {
                    str8 = str21;
                    break;
                }
                if (mang2[rw][2].length() < 3) {
                    str8 = str21;
                    break;
                }
                if (mang2[rw][3].indexOf(str9) > -1) {
                    StringBuilder sb2 = new StringBuilder();
                    str8 = str21;
                    sb2.append(str8);
                    sb2.append(dayso3.substring(dayso3.indexOf("x")));
                    String str28 = sb2.toString();
                    str22 = theodoi;
                    str23 = dayso3;
                    dayso = str28;
                    break;
                }
                str16 = str21;
                i32 = i34;
                i = i2;
                calendar = calendar2;
                mGionhan = mGionhan2;
                str11 = str4;
                str14 = str7;
                str12 = str5;
                str20 = str3;
                dayso6 = dayso3;
                dayso5 = str9;
                str13 = str6;
            }
            String dayso10 = str20.substring(k, i22);
            int k3 = i22;
            k = k3;
            theodoi = str20.substring(k3);
            i3 = i33;
            dayso2 = dayso10;
            String str110 = dayso2.replaceAll("\\n", "");
            rw++;
            mang2[rw][0] = dayso2;
            int i342 = i3;
            if (dayso2.indexOf("lo") <= -1) {
            }
            mang2[rw][2] = XulyLoDe(dayso3.substring(0, dayso3.indexOf("x")));
            mang2[rw][3] = XulyTien(dayso3.substring(dayso3.indexOf("x"), dayso3.length()));
            str25 = "";
            str9 = str27;
            if (mang2[rw][2].indexOf(str9) <= -1) {
            }
        }
        String ktra = str22;
        String theodoi3 = str22.replaceAll(str5, "");
        String theodoi4 = theodoi3.replaceAll(str6, "").replaceAll(str7, "");
        if (dayso.indexOf(str9) <= -1) {
            if (theodoi4.length() > 0) {
                String str29 = str8 + ktra;
                return str29;
            }
            String dayso11 = "";
            if (dayso.indexOf(str9) == -1) {
                int rw2 = 1;
                while (rw2 < 300 && (mang2[rw2][3] != null || mang2[rw2][0] != null)) {
                    if (mang2[rw2][3] == null && mang2[rw2][0] != null) {
                        str10 = str4;
                    } else if (mang2[rw2][2].indexOf(str9) != -1 || mang2[rw2][3].indexOf(str9) != -1) {
                        str10 = str4;
                        if (mang2[rw2][2].indexOf(str9) > -1 || mang2[rw2][3].indexOf(str9) > -1) {
                            String str30 = str8 + mang2[rw2][0];
                            break;
                        }
                    } else {
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append(dayso11);
                        sb3.append(mang2[rw2][1]);
                        sb3.append(":");
                        sb3.append(mang2[rw2][2]);
                        sb3.append("x");
                        sb3.append(mang2[rw2][3]);
                        str10 = str4;
                        sb3.append(str10);
                        dayso11 = sb3.toString();
                    }
                    rw2++;
                    str4 = str10;
                }
                String str210 = dayso11;
                return str210;
            }
            return dayso;
        }
        return dayso;
    }

    public static String PhanTichTinNhan(String str) {
        int i;
        String replace = str.replace("  ", " ");
        if (replace.indexOf("Không hiểu") > -1) {
            return replace;
        }
        if (replace.substring(0, 5).indexOf("de") == -1 && replace.substring(0, 5).indexOf("lo") == -1 && replace.substring(0, 5).indexOf("hc") == -1 && replace.substring(0, 5).indexOf("xi") == -1 && replace.substring(0, 5).indexOf("xq") == -1 && replace.substring(0, 5).indexOf("xn") == -1 && replace.substring(0, 5).indexOf("bc") == -1 && replace.substring(0, 5).indexOf("xg") == -1) {
            return "Không hiểu dạng";
        }
        String str2 = replace + "      ";
        str2.toCharArray();
        int i2 = 3;
        while (true) {
            if (i2 >= str2.length() - 4) {
                break;
            }
            int i3 = i2 + 1;
            if (isNumeric(str2.substring(i2, i3)) && str2.charAt(i3) == ' ') {
                int i4 = i2 + 3;
                if ("ndk".indexOf(str2.substring(i2 + 2, i4)) > -1 && str2.charAt(i4) == ' ') {
                    int i5 = i2;
                    while (i5 > 0 && isNumeric(str2.substring(i5, i5 + 1))) {
                        i5--;
                    }
                    str2 = str2.substring(0, i5) + " x " + str2.substring(i5);
                    i2 += 6;
                }
            }
            i2++;
        }
        for (int i6 = 1; i6 < 10; i6++) {
            str2 = str2.replaceAll("  ", " ");
        }
        for (i = 1; i < 4; i++) {
            str2 = str2.replaceAll("  ", " ").replaceAll(": x", " x").replaceAll(":x", " x").replaceAll("x x", "x").replaceAll("xx", "x").replaceAll(", x", " x").replaceAll(",x", " x").replaceAll("-x", " x").replaceAll("- x", " x");
        }
        return str2;
    }

    public static String ToMauError(String str, String str2) {
        String str3 = "LDVN" + str + "</font>";
        if (str2.indexOf(str) > -1) {
            return str2.replace(str, str3);
        }
        return "LDVN" + str2 + "</font>";
    }

    public static String Xu3cang(String str) {
        String str2 = str.length() < 2 ? "Không hiểu" : "";
        if (str.replaceAll(" ", "").length() > 0) {
            String[] split = str.trim().replaceAll(":", " ").replaceAll(" //. ", "").replaceAll(" , ", "").replaceAll(";", " ").replaceAll(InternalZipConstants.ZIP_FILE_SEPARATOR, "").replaceAll("\\.", ",").replaceAll(" ", ",").split(",");
            for (int i = 0; i < split.length; i++) {
                if (isNumeric(split[i]) && split[i].length() == 3) {
                    str2 = str2 + split[i] + ",";
                } else if (split[i].length() > 0) {
                    return "Không hiểu " + split[i];
                }
            }
        }
        return str2;
    }


    public static String XulyLoDe(String str) {
        String DanGoc;
        String sauloc = "";
        if (str.indexOf("bor trung") > -1) {
            String[] ArrBT = str.split("bor trung ");
            String[] strArr = new String[0];
            String DanBo = "";
            String DanTrung = "";
            if (ArrBT[0].indexOf("bor trung") > -1) {
                DanGoc = XulySo(ArrBT[0].replaceAll("bor trung", ""));
            } else {
                DanGoc = XulySo(ArrBT[0]);
            }
            if (DanGoc.indexOf("Không hiểu") > -1) {
                return DanGoc;
            }
            String[] ArrBorTrung = DanGoc.split(",");
            if (ArrBT.length > 1) {
                if (ArrBT[1].length() > 0 && ArrBT[1].indexOf("bor") > -1) {
                    DanBo = XulySo(ArrBT[1].replaceAll("bor", ""));
                    if (DanBo.indexOf("Không hiểu") > -1) {
                        return DanBo;
                    }
                } else if (ArrBT[1].length() > 0 && ArrBT[1].indexOf("trung") > -1) {
                    DanTrung = XulySo(ArrBT[1].replaceAll("trung", ""));
                    if (DanTrung.indexOf("Không hiểu") > -1) {
                        return DanTrung;
                    }
                }
            }
            String sauloc2 = "";
            for (int k2 = 0; k2 < ArrBorTrung.length; k2++) {
                try {
                    if (DanBo.length() == 0 && DanTrung.length() == 0) {
                        if (sauloc2.indexOf(ArrBorTrung[k2]) == -1) {
                            sauloc2 = sauloc2 + ArrBorTrung[k2] + ",";
                        }
                    } else if (DanBo.length() > 0 && DanTrung.length() == 0) {
                        if (sauloc2.indexOf(ArrBorTrung[k2]) == -1 && DanBo.indexOf(ArrBorTrung[k2]) == -1) {
                            sauloc2 = sauloc2 + ArrBorTrung[k2] + ",";
                        }
                    } else if (DanBo.length() == 0 && DanTrung.length() > 0 && sauloc2.indexOf(ArrBorTrung[k2]) == -1 && DanTrung.indexOf(ArrBorTrung[k2]) > -1) {
                        sauloc2 = sauloc2 + ArrBorTrung[k2] + ",";
                    }
                } catch (Exception e) {
                    return "Không hiểu " + str;
                }
            }
            return sauloc2;
        }
        if (str.indexOf("trung") > -1 || str.indexOf("bor") > -1) {
            if (str.indexOf("trung") > -1 && str.indexOf("bor") > -1) {
                if (str.indexOf("trung") < str.indexOf("bor")) {
                    if (str.substring(0, str.indexOf("trung")).length() > 1) {
                        try {
                            String DanGoc2 = XulySo(str.substring(0, str.indexOf("trung")));
                            if (DanGoc2.indexOf("Không hiểu") <= -1) {
                                if (str.substring(str.indexOf("trung") + 5, str.indexOf("bor")).length() > 1) {
                                    try {
                                        String DanTrung2 = XulySo(str.substring(str.indexOf("trung") + 5, str.indexOf("bor")));
                                        if (DanTrung2.indexOf("Không hiểu") <= -1) {
                                            if (str.substring(str.indexOf("bor") + 3).replaceAll("bor", "").length() > 1) {
                                                try {
                                                    String DanBo2 = XulySo(str.substring(str.indexOf("bor") + 3).replaceAll("bor", ""));
                                                    if (DanBo2.indexOf("Không hiểu") > -1) {
                                                        if (DanBo2.length() > 11) {
                                                            return DanBo2;
                                                        }
                                                        return "Không hiểu " + str;
                                                    }
                                                    String[] danlayS = DanGoc2.split(",");
                                                    String sauloc3 = "";
                                                    for (int i = 0; i < danlayS.length; i++) {
                                                        if (DanTrung2.indexOf(danlayS[i]) > -1 && DanBo2.indexOf(danlayS[i]) == -1) {
                                                            sauloc3 = sauloc3 + danlayS[i] + ",";
                                                        }
                                                    }
                                                    return sauloc3;
                                                } catch (Exception e2) {
                                                    return "Không hiểu " + str.substring(str.indexOf("bor") + 3).replaceAll("bor", "");
                                                }
                                            }
                                            return "Không hiểu " + str;
                                        }
                                        if (DanTrung2.length() > 11) {
                                            return DanTrung2;
                                        }
                                        return "Không hiểu " + str;
                                    } catch (Exception e3) {
                                        return "Không hiểu " + str;
                                    }
                                }
                                return "Không hiểu " + str;
                            }
                            if (DanGoc2.length() > 11) {
                                return DanGoc2;
                            }
                            return "Không hiểu " + str;
                        } catch (Exception e4) {
                            return "Không hiểu " + str;
                        }
                    }
                    return "Không hiểu " + str;
                }
                if (str.substring(0, str.indexOf("bor")).length() > 1) {
                    try {
                        String DanGoc3 = XulySo(str.substring(0, str.indexOf("bor")));
                        if (DanGoc3.indexOf("Không hiểu") <= -1) {
                            if (str.substring(str.indexOf("bor") + 4, str.indexOf("trung")).length() > 1) {
                                try {
                                    String DanBo3 = XulySo(str.substring(str.indexOf("bor") + 4, str.indexOf("trung")));
                                    if (DanBo3.indexOf("Không hiểu") <= -1) {
                                        if (str.substring(str.indexOf("trung") + 5).length() > 1) {
                                            try {
                                                String DanTrung3 = XulySo(str.substring(str.indexOf("trung") + 5).replaceAll("trung", ""));
                                                if (DanTrung3.indexOf("Không hiểu") > -1) {
                                                    if (DanTrung3.length() > 11) {
                                                        return DanTrung3;
                                                    }
                                                    return "Không hiểu " + str;
                                                }
                                                String[] danlayS2 = DanGoc3.split(",");
                                                String sauloc4 = "";
                                                for (int i2 = 0; i2 < danlayS2.length; i2++) {
                                                    if (DanTrung3.indexOf(danlayS2[i2]) > -1 && DanBo3.indexOf(danlayS2[i2]) == -1) {
                                                        sauloc4 = sauloc4 + danlayS2[i2] + ",";
                                                    }
                                                }
                                                return sauloc4;
                                            } catch (Exception e5) {
                                                return "Không hiểu " + str;
                                            }
                                        }
                                        return "Không hiểu " + str;
                                    }
                                    if (DanBo3.length() > 11) {
                                        return DanBo3;
                                    }
                                    return "Không hiểu " + str;
                                } catch (Exception e6) {
                                    return "Không hiểu " + str;
                                }
                            }
                            return "Không hiểu " + str;
                        }
                        if (DanGoc3.length() > 11) {
                            return DanGoc3;
                        }
                        return "Không hiểu " + str;
                    } catch (Exception e7) {
                        return "Không hiểu " + str;
                    }
                }
                return "Không hiểu " + str;
            }
            if (str.indexOf("trung") == -1 && str.indexOf("bor") > -1) {
                String[] ArrBor = str.split("bor");
                List<String> mBor = new ArrayList<>();
                for (String str2 : ArrBor) {
                    String ss = XulySo(str2);
                    if (ss.indexOf("Không hiểu") == -1) {
                        mBor.add(ss);
                    } else {
                        return ss;
                    }
                }
                try {
                    String[] ArrSoBor = mBor.get(0).split(",");
                    for (int k22 = 0; k22 < ArrSoBor.length; k22++) {
                        int m_Dem = 0;
                        for (int k3 = 1; k3 < mBor.size() && mBor.get(k3).indexOf(ArrSoBor[k22]) <= -1; k3++) {
                            m_Dem++;
                        }
                        int k32 = mBor.size();
                        if (m_Dem == k32 - 1) {
                            sauloc = sauloc + ArrSoBor[k22] + ",";
                        }
                    }
                    int k23 = sauloc.length();
                    if (k23 > 0) {
                        return sauloc;
                    }
                    return "Không hiểu " + str.substring(str.indexOf("bor"));
                } catch (Exception e8) {
                    return "Không hiểu " + str;
                }
            }
            if (str.indexOf("trung") > -1 && str.indexOf("bor") == -1) {
                String[] ArrTrung = str.split("trung");
                List<String> mTrung = new ArrayList<>();
                for (String str3 : ArrTrung) {
                    String ss2 = XulySo(str3);
                    if (ss2.indexOf("Không hiểu") == -1) {
                        mTrung.add(ss2);
                    } else {
                        return ss2;
                    }
                }
                try {
                    String[] ArrSoTrung = mTrung.get(0).split(",");
                    for (int k24 = 0; k24 < ArrSoTrung.length; k24++) {
                        int m_Dem2 = 0;
                        for (int k33 = 1; k33 < mTrung.size() && mTrung.get(k33).indexOf(ArrSoTrung[k24]) > -1; k33++) {
                            m_Dem2++;
                        }
                        int k34 = mTrung.size();
                        if (m_Dem2 == k34 - 1) {
                            sauloc = sauloc + ArrSoTrung[k24] + ",";
                        }
                    }
                    return sauloc;
                } catch (Exception e9) {
                    return "Không hiểu " + str;
                }
            }
        } else {
            try {
                String mDanSo = XulySo(str);
                if (mDanSo.indexOf("Không hiểu") > -1) {
                    if (mDanSo.length() > 11) {
                        return mDanSo;
                    }
                    return "Không hiểu " + str;
                }
            } catch (Exception e10) {
                return "Không hiểu " + str;
            }
        }
        return XulySo(str);
    }


    public static String XulySo(String str) {
        String str2 = "";
        String str3 = "";
        int i;
        String str4;
        String str5;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        String str6 = "";
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        String substring = "";
        String sb3 = "";
        int i5 = 0;
        StringBuilder sb4;
        int i6 = 0;
        String str7;
        StringBuilder sb5 = new StringBuilder();
        int indexOf;
        int i7;
        String str8 = "";
        StringBuilder sb6 = new StringBuilder();
        String str9;
        String str10;
        String str11;
        String str12;
        StringBuilder sb7 = new StringBuilder();
        String str13;
        String str14;
        String str15;
        StringBuilder sb8 = new StringBuilder();
        int i8 = 0;
        String[] split = null;
        int i9 = 0;
        String str16;
        StringBuilder sb9;
        String str17;
        StringBuilder sb10;
        int i10;
        StringBuilder sb11;
        StringBuilder sb12 = new StringBuilder();
        String replaceAll = str.replaceAll("tong ko chia", "ko chia 3").replaceAll("tong chia 3 du 1", "chia 3 du 1").replaceAll("tong chia 3 du 2", "chia 3 du 2");
        int i11 = -1;
        if (replaceAll.indexOf("tong 10") > -1) {
            return "Không hiểu tong 10";
        }
        String str18 = ", ";
        String replaceAll2 = replaceAll.replaceAll(":", " ").replaceAll(";", " ").replaceAll(" ,", ", ").replaceAll("tong > 10", "so 29,38,39,47,48,49,56,57,58,59,65,66,67,68,69,74,75,76,77,78,79,83,84,85,86,87,88,89,92,93,94,95,96,97,98,99,").replaceAll("tong < 10", "so 01,02,03,04,05,06,07,08,09,10,11,12,13,14,15,16,17,18,20,21,22,23,24,25,26,27,30,31,32,33,34,35,36,40,41,42,43,44,45,50,51,52,53,54,60,61,62,63,70,71,72,80,81,90,").replaceAll("dau > dit", "so 10,20,21,30,31,32,40,41,42,43,50,51,52,53,54,60,61,62,63,64,65,70,71,72,73,74,75,76,80,81,82,83,84,85,86,87,90,91,92,93,94,95,96,97,98,").replaceAll("dit < dau", "so 10,20,21,30,31,32,40,41,42,43,50,51,52,53,54,60,61,62,63,64,65,70,71,72,73,74,75,76,80,81,82,83,84,85,86,87,90,91,92,93,94,95,96,97,98,").replaceAll("dau < dit", "so 01,02,03,04,05,06,07,08,09,12,13,14,15,16,17,18,19,23,24,25,26,27,28,29,34,35,36,37,38,39,45,46,47,48,49,56,57,58,59,67,68,69,78,79,89,").replaceAll("dit > dau", "so 01,02,03,04,05,06,07,08,09,12,13,14,15,16,17,18,19,23,24,25,26,27,28,29,34,35,36,37,38,39,45,46,47,48,49,56,57,58,59,67,68,69,78,79,89,").replaceAll("ko chia 3", "so 00,01,04,07,10,13,16,19,22,25,28,31,34,37,40,43,46,49,52,55,58,61,64,67,70,73,76,79,82,85,88,91,94,97,02,05,08,11,14,17,20,23,26,29,32,35,38,41,44,47,50,53,56,59,62,65,68,71,74,77,80,83,86,89,92,95,98,").replaceAll("tong chia 3", "so 03,06,09,12,15,18,21,24,27,30,33,36,39,42,45,48,51,54,57,60,63,66,69,72,75,78,81,84,87,90,93,96,99, ").replaceAll("chia 3 du 1", "so 01,04,07,10,13,16,19,22,25,28,31,34,37,40,43,46,49,52,55,58,61,64,67,70,73,76,79,82,85,88,91,94,97, ").replaceAll("chia 3 du 2", "so 02,05,08,11,14,17,20,23,26,29,32,35,38,41,44,47,50,53,56,59,62,65,68,71,74,77,80,83,86,89,92,95,98, ");
        if (replaceAll2.trim().length() < 2) {
            return "Không hiểu ";
        }
        int i12 = 0;
        String str19 = "";
        String str20 = str19;
        int i13 = 0;
        loop0:
        while (true) {
            int i14 = i13 + 1;
            if (i14 > 50) {
                return "Không hiểu " + str;
            }
            if ((replaceAll2.indexOf(str19) > i11 && replaceAll2.length() == str19.length()) || replaceAll2.length() == 0) {
                break;
            }
            str2 = " " + replaceAll2.trim() + " ";
            String str21 = "0";
            if (str2.indexOf("den") > i11) {
                if (str2.substring(i12, 3).indexOf("tu") > i11) {
                    str2 = str2.substring(3);
                }
                int indexOf2 = str2.indexOf("den");
                int i15 = indexOf2;
                while (i15 > i11 && !isNumeric(str2.substring(i15, i15 + 2))) {
                    i15--;
                }
                String substring2 = str2.substring(i15, i15 + 2);
                while (indexOf2 < str2.length() && !isNumeric(str2.substring(indexOf2, indexOf2 + 2))) {
                    indexOf2++;
                }
                int i16 = indexOf2 + 2;
                String substring3 = str2.substring(indexOf2, i16);
                str3 = replaceAll2;
                if (Integer.parseInt(substring2) >= Integer.parseInt(substring3) || substring2.length() <= 0 || substring3.length() <= 0) {
                    break;
                }
                for (int parseInt = Integer.parseInt(substring2); parseInt < Integer.parseInt(substring3) + 1; parseInt++) {
                    if (parseInt < 10) {
                        sb12 = new StringBuilder();
                        sb12.append(str20);
                        sb12.append("0");
                    } else if (parseInt > 9) {
                        sb12 = new StringBuilder();
                        sb12.append(str20);
                    }
                    sb12.append(parseInt);
                    sb12.append(",");
                    str20 = sb12.toString();
                }
                str2 = str2.substring(0, i15) + " " + str2.substring(i16);
            } else {
                str3 = replaceAll2;
            }
            if (str2.indexOf("ghep dit") > -1) {
                int indexOf3 = str2.indexOf("ghep dit");
                int i17 = 1;
                do {
                    indexOf3--;
                } while (!str2.substring(indexOf3, indexOf3 + 3).equals("dau"));
                int indexOf4 = str2.indexOf("ghep dit") + 9;
                while (true) {
                    if (indexOf4 >= str2.length() - i17) {
                        break;
                    }
                    int i18 = indexOf4 + 1;
                    if (str18.indexOf(str2.substring(indexOf4, i18)) <= -1 && !isNumeric(str2.substring(indexOf4, i18))) {
                        indexOf4--;
                        break;
                    }
                    indexOf4 = i18;
                    i17 = 1;
                }
                String replaceAll3 = str2.substring(indexOf3, str2.indexOf("ghep dit")).replaceAll("dau", "");
                int i19 = indexOf4 + 1;
                String replaceAll4 = str2.substring(str2.indexOf("ghep dit"), i19).replaceAll("ghep dit", "");
                if (replaceAll3.length() == 0) {
                    return "Không hiểu " + str2;
                }
                if (replaceAll4.length() == 0) {
                    return "Không hiểu " + str;
                }
                if (!xuLymem(replaceAll3).booleanValue()) {
                    return "Không hiểu " + str2.substring(indexOf3, str2.indexOf("ghep dit"));
                }
                if (!xuLymem(replaceAll4).booleanValue()) {
                    return "Không hiểu " + str2.substring(str2.indexOf("ghep dit"), i19);
                }
                if (xuLymem(replaceAll3).booleanValue() && xuLymem(replaceAll4).booleanValue()) {
                    String GhepDau = GhepDau(replaceAll3);
                    if (GhepDau.indexOf("Không hiểu") > -1) {
                        return "Không hiểu " + str;
                    }
                    String GhepDit = GhepDit(replaceAll4);
                    if (GhepDit.indexOf("Không hiểu") > -1) {
                        return "Không hiểu " + str;
                    }
                    int i20 = 0;
                    while (i20 < 100) {
                        if (i20 < 10) {
                            i10 = i14;
                            if (GhepDau.indexOf("0" + i20) > -1) {
                                if (GhepDit.indexOf("0" + i20) > -1) {
                                    sb11 = new StringBuilder();
                                    sb11.append(str20);
                                    sb11.append("0");
                                    sb11.append(i20);
                                    sb11.append(",");
                                    str20 = sb11.toString();
                                    i20++;
                                    i14 = i10;
                                }
                            }
                        } else {
                            i10 = i14;
                        }
                        if (i20 > 9) {
                            if (GhepDau.indexOf("" + i20) > -1) {
                                if (GhepDit.indexOf("" + i20) > -1) {
                                    sb11 = new StringBuilder();
                                    sb11.append(str20);
                                    sb11.append(i20);
                                    sb11.append(",");
                                    str20 = sb11.toString();
                                }
                            }
                        }
                        i20++;
                        i14 = i10;
                    }
                }
                i = i14;
                str4 = "Không hiểu";
                str2 = (str2.substring(0, indexOf3) + " " + str2.substring(i19)).trim();
            } else {
                i = i14;
                str4 = "Không hiểu";
            }
            if (str2.trim().length() == 0) {
                break;
            }
            if (str2.indexOf("ghep dau") > -1) {
                int indexOf5 = str2.indexOf("ghep dau");
                int i21 = 1;
                do {
                    indexOf5--;
                } while (!str2.substring(indexOf5, indexOf5 + 3).equals("dit"));
                int indexOf6 = str2.indexOf("ghep dau") + 9;
                while (true) {
                    if (indexOf6 >= str2.length() - i21) {
                        break;
                    }
                    int i22 = indexOf6 + 1;
                    if (str18.indexOf(str2.substring(indexOf6, i22)) <= -1 && !isNumeric(str2.substring(indexOf6, i22))) {
                        indexOf6--;
                        break;
                    }
                    indexOf6 = i22;
                    i21 = 1;
                }
                String replaceAll5 = str2.substring(indexOf5, str2.indexOf("ghep dau")).replaceAll("dit", "");
                int i23 = indexOf6 + 1;
                String replaceAll6 = str2.substring(str2.indexOf("ghep dau"), i23).replaceAll("ghep dau", "");
                if (replaceAll5.length() == 0) {
                    return "Không hiểu " + str2;
                }
                if (replaceAll6.length() == 0) {
                    return "Không hiểu " + str;
                }
                if (!xuLymem(replaceAll5).booleanValue()) {
                    return "Không hiểu " + str2.substring(indexOf5, str2.indexOf("ghep dau"));
                }
                if (!xuLymem(replaceAll6).booleanValue()) {
                    return "Không hiểu " + str2.substring(str2.indexOf("ghep dau"), i23);
                }
                if (xuLymem(replaceAll5).booleanValue() && xuLymem(replaceAll6).booleanValue()) {
                    String GhepDau2 = GhepDau(replaceAll6);
                    if (GhepDau2.indexOf(str4) > -1) {
                        return "Không hiểu " + str;
                    }
                    String GhepDit2 = GhepDit(replaceAll5);
                    if (GhepDit2.indexOf(str4) > -1) {
                        return "Không hiểu " + str;
                    }
                    int i24 = 0;
                    while (i24 < 100) {
                        if (i24 < 10) {
                            str17 = str4;
                            if (GhepDau2.indexOf("0" + i24) > -1) {
                                if (GhepDit2.indexOf("0" + i24) > -1) {
                                    sb10 = new StringBuilder();
                                    sb10.append(str20);
                                    sb10.append("0");
                                    sb10.append(i24);
                                    sb10.append(",");
                                    str20 = sb10.toString();
                                    i24++;
                                    str4 = str17;
                                }
                            }
                        } else {
                            str17 = str4;
                        }
                        if (i24 > 9) {
                            if (GhepDau2.indexOf("" + i24) > -1) {
                                if (GhepDit2.indexOf("" + i24) > -1) {
                                    sb10 = new StringBuilder();
                                    sb10.append(str20);
                                    sb10.append(i24);
                                    sb10.append(",");
                                    str20 = sb10.toString();
                                }
                            }
                        }
                        i24++;
                        str4 = str17;
                    }
                }
                str5 = str4;
                str2 = (str2.substring(0, indexOf5) + " " + str2.substring(i23)).trim();
            } else {
                str5 = str4;
            }
            if (str2.trim().length() == 0) {
                break;
            }
            if (str2.indexOf("ghepdd") > -1) {
                int indexOf7 = str2.indexOf("ghepdd");
                int indexOf8 = str2.indexOf("ghepdd") + 11;
                while (true) {
                    if (indexOf8 >= str2.length()) {
                        break;
                    }
                    int i25 = indexOf8 + 1;
                    if (str18.indexOf(str2.substring(indexOf8, i25)) <= -1 && !isNumeric(str2.substring(indexOf8, i25))) {
                        indexOf8--;
                        break;
                    }
                    indexOf8 = i25;
                }
                String trim = str2.substring(str2.indexOf("ghepdd"), indexOf8).replaceAll("ghepdd", "").trim();
                if (trim.length() == 0) {
                    return "Không hiểu " + str;
                }
                if (!xuLymem(trim).booleanValue()) {
                    return "Không hiểu " + str2.substring(str2.indexOf("ghepdd"), indexOf8);
                }
                String[] split2 = trim.split(",");
                for (int i26 = 0; i26 < split2.length; i26++) {
                    if (split2[i26].length() == 2 && isNumeric(split2[i26]) && i26 > 0) {
                        return "Không hiểu " + str2.substring(str2.indexOf(split2[i26]), indexOf7);
                    }
                    if (!isNumeric(split2[i26])) {
                        return "Không hiểu " + str2.substring(str2.indexOf(split2[i26]), indexOf7);
                    }
                }
                String GhepDau3 = GhepDau(trim);
                String str22 = str5;
                if (GhepDau3.indexOf(str22) > -1) {
                    return "Không hiểu " + str;
                }
                String GhepDit3 = GhepDit(trim);
                if (GhepDit3.indexOf(str22) > -1) {
                    return "Không hiểu " + str;
                }
                int i27 = 0;
                while (i27 < 100) {
                    if (i27 < 10) {
                        str16 = str22;
                        if (GhepDau3.indexOf("0" + i27) > -1) {
                            if (GhepDit3.indexOf("0" + i27) > -1) {
                                sb9 = new StringBuilder();
                                sb9.append(str20);
                                sb9.append("0");
                                sb9.append(i27);
                                sb9.append(",");
                                str20 = sb9.toString();
                                i27++;
                                str22 = str16;
                            }
                        }
                    } else {
                        str16 = str22;
                    }
                    if (i27 > 9) {
                        if (GhepDau3.indexOf("" + i27) > -1) {
                            if (GhepDit3.indexOf("" + i27) > -1) {
                                sb9 = new StringBuilder();
                                sb9.append(str20);
                                sb9.append(i27);
                                sb9.append(",");
                                str20 = sb9.toString();
                            }
                        }
                    }
                    i27++;
                    str22 = str16;
                }
                str5 = str22;
                str2 = (str2.substring(0, indexOf7) + " " + str2.substring(indexOf8)).trim();
            }
            if (str2.indexOf("dan") > -1 && str2.indexOf("dan") < 5) {
                if (str2.length() < 5) {
                    return "Không hiểu " + str;
                }
                int i28 = -1;
                while (true) {
                    int indexOf9 = str2.indexOf("dan", i28 + 1);
                    if (indexOf9 == -1) {
                        break;
                    }
                    int i29 = indexOf9 + 4;
                    i8 = i29;
                    while (true) {
                        if (i8 >= str2.length()) {
                            break;
                        }
                        int i30 = i8 + 1;
                        if (!isNumeric(str2.substring(i8, i30)) && str18.indexOf(str2.substring(i8, i30)) <= -1) {
                            i8--;
                            break;
                        }
                        i8 = i30;
                    }
                    String substring4 = str2.substring(i29, i8);
                    if (substring4.length() == 0) {
                        return "Không hiểu " + str;
                    }
                    String replaceAll7 = substring4.trim().replaceAll(" ", ",").trim().replaceAll(",,", ",").trim().replaceAll(",,", ",");
                    if (replaceAll7.length() == 3 && replaceAll7.indexOf(",") > -1) {
                        replaceAll7 = replaceAll7.replace(",", "");
                    }
                    split = replaceAll7.split(",");
                    i9 = 0;
                    while (i9 < split.length) {
                        split[i9] = split[i9].replaceAll(" ", "");
                        if (split[i9].length() != 2 || !isNumeric(split[i9])) {
                            break loop0;
                        }
                        String str23 = str20;
                        if (Integer.parseInt(split[i9].substring(0, 1)) >= Integer.parseInt(split[i9].substring(1, 2)) - 1) {
                            return "Không hiểu " + str2.substring(str2.indexOf(split[i9]), i8);
                        }
                        if (!isNumeric(split[i9])) {
                            return "Không hiểu " + str2.substring(indexOf9, i8);
                        }
                        if (split[i9].length() != 2 || !isNumeric(split[i9])) {
                            break loop0;
                        }
                        int parseInt2 = Integer.parseInt(split[i9].substring(0, 1));
                        String str24 = "";
                        String str25 = str24;
                        String str26 = str21;
                        for (int i31 = 1; parseInt2 < Integer.parseInt(split[i9].substring(i31)) + i31; i31 = 1) {
                            str24 = str24 + parseInt2;
                            str25 = str25 + parseInt2;
                            parseInt2++;
                        }
                        String GhepDau4 = GhepDau(str24);
                        String str27 = str5;
                        if (GhepDau4.indexOf(str27) > -1) {
                            return "Không hiểu " + str;
                        }
                        String[] split3 = GhepDau4.split(",");
                        String GhepDit4 = GhepDit(str25);
                        if (GhepDit4.indexOf(str27) > -1) {
                            return "Không hiểu " + str;
                        }
                        str5 = str27;
                        String str28 = str23;
                        int i32 = 0;
                        while (i32 < split3.length) {
                            String str29 = GhepDit4;
                            if (GhepDit4.indexOf(split3[i32]) > -1) {
                                str28 = str28 + split3[i32] + ",";
                            }
                            i32++;
                            GhepDit4 = str29;
                        }
                        i9++;
                        str20 = str28;
                        str21 = str26;
                    }
                    str2 = (str2.substring(0, indexOf9) + " " + str2.substring(i8)).trim();
                    i28 = 0;
                }
            }
            String str30 = str21;
            if (str2.trim().length() == 0) {
                break;
            }
            if (str2.indexOf("boj") > -1 && str2.indexOf("boj") < 5) {
                if (str2.length() < 5) {
                    return "Không hiểu " + str;
                }
                int i33 = -1;
                while (true) {
                    int indexOf10 = str2.indexOf("boj", i33 + 1);
                    int i34 = -1;
                    if (indexOf10 == -1) {
                        break;
                    }
                    int i35 = indexOf10 + 4;
                    while (i35 < str2.length()) {
                        int i36 = i35 + 1;
                        if (str18.indexOf(str2.substring(i35, i36)) <= i34 && !isNumeric(str2.substring(i35, i36))) {
                            i35--;
                            break;
                        }
                        i35 = i36;
                        i34 = -1;
                    }
                    try {
                        String trim2 = str2.substring(indexOf10, i35).replaceAll("boj", "").trim();
                        if (trim2.trim().length() == 0) {
                            return "Không hiểu " + str2;
                        }
                        String[] split4 = trim2.trim().replaceAll(" ", ",").trim().replaceAll(",,", ",").split(",");
                        int i37 = 0;
                        while (i37 < split4.length) {
                            if (!isNumeric(split4[i37]) || split4[i37].length() != 2) {
                                break loop0;
                            }
                            str20 = str20 + GhepBo(split4[i37]);
                            i37++;
                        }
                        str2 = (str2.substring(0, indexOf10) + " " + str2.substring(i35)).trim();
                        i33 = 0;
                    } catch (Exception unused) {
                        return "Không hiểu " + str;
                    }
                }
            }
            if (str2.trim().length() == 0) {
                break;
            }
            int i38 = -1;
            if (str2.indexOf("cham tong") > -1 && str2.indexOf("cham tong") < 5) {
                if (str2.length() <= 10) {
                    return "Không hiểu " + str;
                }
                if (str2.substring(0, 11).indexOf("cham tong") == -1) {
                    return "Không hiểu " + str;
                }
                int i39 = -1;
                while (true) {
                    int indexOf11 = str2.indexOf("cham tong", i39 + 1);
                    if (indexOf11 == i38) {
                        break;
                    }
                    int i40 = indexOf11 + 10;
                    while (true) {
                        if (i40 >= str2.length()) {
                            break;
                        }
                        int i41 = i40 + 1;
                        if ("0123456789, ".indexOf(str2.substring(i40, i41)) <= i38) {
                            i40--;
                            break;
                        }
                        i40 = i41;
                    }
                    String replaceAll8 = str2.length() > 10 ? str2.substring(indexOf11, i40).replaceAll("cham tong", "").trim().replaceAll(" ", ",") : "";
                    if (replaceAll8.length() == 0) {
                        return "Không hiểu " + str2.substring(indexOf11, i40);
                    }
                    String[] split5 = replaceAll8.split(",");
                    for (int i42 = 0; i42 < split5.length; i42++) {
                        if (split5[i42].length() == 2 && isNumeric(split5[i42]) && i42 > 0 && !xuLymem(replaceAll8).booleanValue()) {
                            return "Không hiểu " + str2.substring(str2.indexOf(split5[i42]), i40);
                        }
                        if (split5[i42].length() == 3 && isNumeric(split5[i42]) && i42 > 0 && !xuLymem(replaceAll8).booleanValue()) {
                            return "Không hiểu " + str2.substring(str2.indexOf(split5[i42]), i40);
                        }
                        if (!isNumeric(split5[i42])) {
                            return "Không hiểu " + str2.substring(indexOf11, i40);
                        }
                    }
                    String GhepDau5 = GhepDau(replaceAll8);
                    String str31 = str5;
                    if (GhepDau5.indexOf(str31) > -1) {
                        return "Không hiểu " + str;
                    }
                    String GhepDit5 = GhepDit(replaceAll8);
                    if (GhepDit5.indexOf(str31) > -1) {
                        return "Không hiểu " + str;
                    }
                    String GhepTong = GhepTong(replaceAll8);
                    if (GhepTong.indexOf(str31) > -1) {
                        return "Không hiểu " + str;
                    }
                    int i43 = 0;
                    while (i43 < 100) {
                        if (i43 < 10) {
                            StringBuilder sb13 = new StringBuilder();
                            str13 = str18;
                            str15 = str30;
                            sb13.append(str15);
                            sb13.append(i43);
                            str14 = str31;
                            if (GhepDau5.indexOf(sb13.toString()) <= -1) {
                                if (GhepDit5.indexOf(str15 + i43) <= -1) {
                                }
                            }
                            sb8 = new StringBuilder();
                            sb8.append(str20);
                            sb8.append(str15);
                            sb8.append(i43);
                            sb8.append(",");
                            str20 = sb8.toString();
                            i43++;
                            str30 = str15;
                            str18 = str13;
                            str31 = str14;
                        } else {
                            str13 = str18;
                            str14 = str31;
                            str15 = str30;
                        }
                        if (i43 > 9) {
                            if (GhepDau5.indexOf("" + i43) <= -1) {
                                if (GhepDit5.indexOf("" + i43) <= -1) {
                                    if (GhepTong.indexOf("" + i43) <= -1) {
                                    }
                                }
                            }
                            sb8 = new StringBuilder();
                            sb8.append(str20);
                            sb8.append(i43);
                            sb8.append(",");
                            str20 = sb8.toString();
                        }
                        i43++;
                        str30 = str15;
                        str18 = str13;
                        str31 = str14;
                    }
                    str2 = (str2.substring(0, indexOf11) + " " + str2.substring(i40)).trim();
                    str18 = str18;
                    str5 = str31;
                    i39 = 0;
                    i38 = -1;
                }
            }
            String str32 = str5;
            String str33 = str18;
            String str34 = str30;
            if (str2.trim().length() == 0) {
                break;
            }
            if (str2.indexOf("cham") > -1 && str2.indexOf("cham") < 5) {
                if (str2.length() < 6) {
                    return "Không hiểu " + str;
                }
                int i44 = -1;
                while (true) {
                    int indexOf12 = str2.indexOf("cham", i44 + 1);
                    if (indexOf12 == -1) {
                        break;
                    }
                    int i45 = indexOf12 + 5;
                    while (true) {
                        if (i45 >= str2.length()) {
                            break;
                        }
                        int i46 = i45 + 1;
                        if ("0123456789, ".indexOf(str2.substring(i45, i46)) <= -1) {
                            i45--;
                            break;
                        }
                        i45 = i46;
                    }
                    String replaceAll9 = str2.length() > 5 ? str2.substring(indexOf12, i45).replaceAll("cham", "").trim().replaceAll(" ", ",") : "";
                    if (replaceAll9.length() == 0) {
                        return "Không hiểu " + str2.substring(indexOf12, i45);
                    }
                    String[] split6 = replaceAll9.split(",");
                    for (int i47 = 0; i47 < split6.length; i47++) {
                        if (split6[i47].length() == 2 && isNumeric(split6[i47]) && i47 > 0) {
                            return "Không hiểu " + str2.substring(str2.indexOf(split6[i47]), i45);
                        }
                        if (split6[i47].length() == 3 && isNumeric(split6[i47]) && i47 > 0) {
                            return "Không hiểu " + str2.substring(str2.indexOf(split6[i47]), i45);
                        }
                        if (!isNumeric(split6[i47])) {
                            return "Không hiểu " + str2.substring(indexOf12, i45);
                        }
                    }
                    String GhepDau6 = GhepDau(replaceAll9);
                    String str35 = str32;
                    if (GhepDau6.indexOf(str35) > -1) {
                        return "Không hiểu " + str;
                    }
                    String GhepDit6 = GhepDit(replaceAll9);
                    if (GhepDit6.indexOf(str35) > -1) {
                        return "Không hiểu " + str;
                    }
                    int i48 = 0;
                    while (i48 < 100) {
                        if (i48 < 10) {
                            if (GhepDau6.indexOf(str34 + i48) <= -1) {
                            }
                            sb7 = new StringBuilder();
                            sb7.append(str20);
                            sb7.append(str34);
                            sb7.append(i48);
                            sb7.append(",");
                            str20 = sb7.toString();
                            i48++;
                        }
                        if (i48 > 9) {
                            if (GhepDau6.indexOf("" + i48) <= -1) {
                                if (GhepDit6.indexOf("" + i48) <= -1) {
                                }
                            }
                            sb7 = new StringBuilder();
                            sb7.append(str20);
                            sb7.append(i48);
                            sb7.append(",");
                            str20 = sb7.toString();
                        }
                        i48++;
                    }
                    str2 = (str2.substring(0, indexOf12) + " " + str2.substring(i45)).trim();
                    str32 = str35;
                    i44 = 0;
                }
            }
            String str36 = str32;
            if (str2.trim().length() == 0) {
                break;
            }
            if (str2.indexOf("tong") > -1 && str2.indexOf("tong") < 5) {
                if (str2.length() < 6) {
                    return "Không hiểu " + str;
                }
                int i49 = -1;
                while (true) {
                    int indexOf13 = str2.indexOf("tong", i49 + 1);
                    int i50 = -1;
                    if (indexOf13 == -1) {
                        break;
                    }
                    int i51 = indexOf13 + 5;
                    while (true) {
                        if (i51 >= str2.length()) {
                            str12 = str33;
                            break;
                        }
                        int i52 = i51 + 1;
                        str12 = str33;
                        if (str12.indexOf(str2.substring(i51, i52)) <= i50 && !isNumeric(str2.substring(i51, i52))) {
                            i51--;
                            break;
                        }
                        i51 = i52;
                        str33 = str12;
                        i50 = -1;
                    }
                    String replaceAll10 = str2.substring(indexOf13, i51).length() > 5 ? str2.substring(indexOf13, i51).replaceAll("tong", "").trim().replaceAll(" ", ",") : "";
                    if (replaceAll10.length() == 0) {
                        return "Không hiểu " + str2;
                    }
                    String[] split7 = replaceAll10.split(",");
                    for (int i53 = 0; i53 < split7.length; i53++) {
                        if (split7[i53].length() == 2 && isNumeric(split7[i53]) && i53 > 0) {
                            return "Không hiểu " + str2.substring(str2.indexOf(split7[i53]), i51);
                        }
                        if (split7[i53].length() == 3 && isNumeric(split7[i53]) && i53 > 0) {
                            return "Không hiểu " + str2.substring(str2.indexOf(split7[i53]), i51);
                        }
                        if (!isNumeric(split7[i53])) {
                            return "Không hiểu " + str2.substring(indexOf13, i51);
                        }
                    }
                    String GhepTong2 = GhepTong(replaceAll10);
                    if (GhepTong2.indexOf(str36) > -1) {
                        return "Không hiểu " + str;
                    }
                    str20 = str20 + GhepTong2;
                    str2 = (str2.substring(0, indexOf13) + " " + str2.substring(i51)).trim();
                    str33 = str12;
                    i49 = 0;
                }
            }
            String str37 = str33;
            if (str2.trim().length() == 0) {
                break;
            }
            int i54 = -1;
            if (str2.indexOf("ghep") == -1 && str2.indexOf("dau dit") > -1) {
                int i55 = -1;
                while (true) {
                    int indexOf14 = str2.indexOf("dau dit", i55 + 1);
                    if (indexOf14 == i54) {
                        break;
                    }
                    int i56 = indexOf14 + 8;
                    while (true) {
                        if (i56 >= str2.length()) {
                            break;
                        }
                        int i57 = i56 + 1;
                        if (str37.indexOf(str2.substring(i56, i57)) <= i54 && !isNumeric(str2.substring(i56, i57))) {
                            i56--;
                            break;
                        }
                        i56 = i57;
                        i54 = -1;
                    }
                    if (str2.length() > 8) {
                        str11 = str2.substring(indexOf14, i56);
                        if (str11.substring(0, 8).indexOf("dau dit") > -1) {
                            str11 = str11.replaceAll("dau dit", "").trim().replaceAll(" ", ",");
                        }
                    } else {
                        str11 = "";
                    }
                    if (str11.length() == 0) {
                        return "Không hiểu " + str;
                    }
                    String[] split8 = str11.split(",");
                    for (int i58 = 0; i58 < split8.length; i58++) {
                        if (split8[i58].length() == 2 && isNumeric(split8[i58]) && i58 > 0) {
                            return "Không hiểu " + str2.substring(str2.indexOf(split8[i58]), i56);
                        }
                        if (split8[i58].length() == 3 && isNumeric(split8[i58]) && i58 > 0) {
                            return "Không hiểu " + str2.substring(str2.indexOf(split8[i58]), i56);
                        }
                        if (!isNumeric(split8[i58])) {
                            return "Không hiểu " + str2.substring(indexOf14, i56);
                        }
                    }
                    String GhepDau7 = GhepDau(str11);
                    if (GhepDau7.indexOf(str36) > -1) {
                        return "Không hiểu " + str;
                    }
                    String str38 = str20 + GhepDau7;
                    String GhepDit7 = GhepDit(str11);
                    if (GhepDit7.indexOf(str36) > -1) {
                        return "Không hiểu " + str;
                    }
                    str20 = str38 + GhepDit7;
                    str2 = (str2.substring(0, indexOf14) + " " + str2.substring(i56)).trim();
                    i55 = 0;
                    i54 = -1;
                }
            }
            if (str2.trim().length() == 0) {
                break;
            }
            if (str2.indexOf("dau") > -1 && str2.indexOf("dau") < 5 && str2.indexOf("ghep") == -1 && str2.indexOf("dau dit") == -1) {
                if (str2.length() < 5) {
                    return "Không hiểu " + str;
                }
                int i59 = -1;
                while (true) {
                    int indexOf15 = str2.indexOf("dau", i59 + 1);
                    int i60 = -1;
                    if (indexOf15 == -1) {
                        break;
                    }
                    int i61 = indexOf15 + 5;
                    while (true) {
                        if (i61 >= str2.length()) {
                            break;
                        }
                        int i62 = i61 + 1;
                        if (str37.indexOf(str2.substring(i61, i62)) <= i60 && !isNumeric(str2.substring(i61, i62))) {
                            i61--;
                            break;
                        }
                        i61 = i62;
                        i60 = -1;
                    }
                    if (str2.length() > 4) {
                        str10 = str2.substring(indexOf15, i61);
                        if (str10.substring(0, 4).indexOf("dau") > -1) {
                            str10 = str10.replaceAll("dau", "").trim().replaceAll(" ", ",");
                        }
                    } else {
                        str10 = "";
                    }
                    if (str10.length() == 0) {
                        return "Không hiểu " + str2;
                    }
                    String[] split9 = str10.split(",");
                    for (int i63 = 0; i63 < split9.length; i63++) {
                        if (split9[i63].length() == 2 && isNumeric(split9[i63]) && i63 > 0) {
                            return "Không hiểu " + str2.substring(str2.indexOf(split9[i63]), i61);
                        }
                        if (split9[i63].length() == 3 && isNumeric(split9[i63]) && i63 > 0) {
                            return "Không hiểu " + str2.substring(str2.indexOf(split9[i63]), i61);
                        }
                        if (!isNumeric(split9[i63])) {
                            return "Không hiểu " + str2.substring(indexOf15, i61);
                        }
                    }
                    String GhepDau8 = GhepDau(str10);
                    if (GhepDau8.indexOf(str36) > -1) {
                        return "Không hiểu " + str;
                    }
                    str20 = str20 + GhepDau8;
                    str2 = (str2.substring(0, indexOf15) + " " + str2.substring(i61)).trim();
                    i59 = 0;
                }
            }
            if (str2.trim().length() == 0) {
                break;
            }
            if (str2.indexOf("dit") > -1 && str2.indexOf("dit") < 5 && str2.indexOf("ghep") == -1 && str2.indexOf("dau dit") == -1) {
                if (str2.length() < 5) {
                    return "Không hiểu " + str;
                }
                int i64 = -1;
                while (true) {
                    int indexOf16 = str2.indexOf("dit", i64 + 1);
                    int i65 = -1;
                    if (indexOf16 == -1) {
                        break;
                    }
                    int i66 = indexOf16 + 5;
                    while (true) {
                        if (i66 >= str2.length()) {
                            break;
                        }
                        int i67 = i66 + 1;
                        if (str37.indexOf(str2.substring(i66, i67)) <= i65 && !isNumeric(str2.substring(i66, i67))) {
                            i66--;
                            break;
                        }
                        i66 = i67;
                        i65 = -1;
                    }
                    if (str2.length() > 4) {
                        str9 = str2.substring(indexOf16, i66);
                        if (str9.substring(0, 4).indexOf("dit") > -1) {
                            str9 = str9.replaceAll("dit", "").trim().replaceAll(" ", ",");
                        }
                    } else {
                        str9 = "";
                    }
                    if (str9.length() == 0) {
                        return "Không hiểu " + str;
                    }
                    String[] split10 = str9.split(",");
                    for (int i68 = 0; i68 < split10.length; i68++) {
                        if (split10[i68].length() == 2 && isNumeric(split10[i68]) && i68 > 0) {
                            return "Không hiểu " + str2.substring(str2.indexOf(split10[i68]), i66);
                        }
                        if (split10[i68].length() == 3 && isNumeric(split10[i68]) && i68 > 0) {
                            return "Không hiểu " + str2.substring(str2.indexOf(split10[i68]), i66);
                        }
                        if (!isNumeric(split10[i68])) {
                            return "Không hiểu " + str2.substring(indexOf16, i66);
                        }
                    }
                    String GhepDit8 = GhepDit(str9);
                    if (GhepDit8.indexOf(str36) > -1) {
                        return "Không hiểu " + str;
                    }
                    str20 = str20 + GhepDit8;
                    str2 = (str2.substring(0, indexOf16) + " " + str2.substring(i66)).trim();
                    i64 = 0;
                }
            }
            if (str2.trim().length() == 0) {
                break;
            }
            int i69 = -1;
            if (str2.indexOf("to ") > -1 || str2.indexOf("nho") > -1) {
                if (str2.indexOf("to") <= -1 || str2.indexOf("nho") <= -1) {
                    i2 = i;
                } else {
                    i2 = i;
                    while (true) {
                        int i70 = str2.indexOf("to ") > i69 ? 5 : 5;
                        if (str2.indexOf("nho ") <= i69) {
                            break;
                        }
                        if (str2.indexOf("nho") >= i70) {
                            break;
                        }
                        i2++;
                        if (i2 > 100) {
                            return "Không hiểu " + str;
                        }
                        if (str2.indexOf("to") < str2.indexOf("nho")) {
                            i3 = -1;
                            while (true) {
                                i3 = str2.indexOf("to", i3 + 1);
                                if (i3 == -1) {
                                    break;
                                }
                                int i71 = i3 + 1;
                                i4 = i71;
                                for (int i72 = -1; i4 < str2.length() && str2.substring(i71, i4).indexOf("to") <= i72 && str2.substring(i71, i4).indexOf("nho") <= i72; i72 = -1) {
                                    i4++;
                                }
                                String replaceAll11 = str2.substring(i3, i4).replaceAll(" ", "").replaceAll(",", "");
                                if (replaceAll11.indexOf("toto") > -1) {
                                    str6 = str20 + "55,56,57,58,59,65,66,67,68,69,75,76,77,78,79,85,86,87,88,89,95,96,97,98,99,";
                                    sb = new StringBuilder();
                                    break;
                                }
                                if (replaceAll11.indexOf("tonho") > -1) {
                                    str6 = str20 + "50,51,52,53,54,60,61,62,63,64,70,71,72,73,74,80,81,82,83,84,90,91,92,93,94,";
                                    sb = new StringBuilder();
                                    break;
                                }
                            }
                            sb.append(str2.substring(0, i3));
                            sb.append(" ");
                            sb.append(str2.substring(i4));
                            str2 = sb.toString();
                            str20 = str6;
                            if (str2.indexOf("to") != -1 || str2.indexOf("nho") == -1) {
                                break;
                            }
                            i69 = -1;
                        } else {
                            if (str2.indexOf("nho") < str2.indexOf("to")) {
                                i3 = -1;
                                while (true) {
                                    i3 = str2.indexOf("nho", i3 + 1);
                                    if (i3 == -1) {
                                        break;
                                    }
                                    int i73 = i3 + 1;
                                    i4 = i73;
                                    for (int i74 = -1; i4 < str2.length() && str2.substring(i73, i4).indexOf("to") <= i74 && str2.substring(i73, i4).indexOf("nho") <= i74; i74 = -1) {
                                        i4++;
                                    }
                                    String replaceAll12 = str2.substring(i3, i4).replaceAll(" ", "").replaceAll(",", "");
                                    if (replaceAll12.indexOf("nhoto") > -1) {
                                        str6 = str20 + "05,06,07,08,09,15,16,17,18,19,25,26,27,28,29,35,36,37,38,39,45,46,47,48,49,";
                                        sb = new StringBuilder();
                                        break;
                                    }
                                    if (replaceAll12.indexOf("nhonho") > -1) {
                                        str6 = str20 + "00,01,02,03,04,10,11,12,13,14,20,21,22,23,24,30,31,32,33,34,40,41,42,43,44,";
                                        sb = new StringBuilder();
                                        break;
                                    }
                                }
                            }
                            if (str2.indexOf("to") != -1) {
                                break;
                            }
                            i69 = -1;
                        }
                    }
                }
                if (str2.indexOf("to") > -1 && str2.indexOf("nho") == -1) {
                    if (str2.indexOf("toto") > -1) {
                        str20 = str20 + "55,56,57,58,59,65,66,67,68,69,75,76,77,78,79,85,86,87,88,89,95,96,97,98,99,";
                        str2 = str2.substring(0, str2.indexOf("toto")) + " " + str2.substring(str2.indexOf("toto") + 4);
                    }
                    if (str2.indexOf("to to") > -1) {
                        str20 = str20 + "55,56,57,58,59,65,66,67,68,69,75,76,77,78,79,85,86,87,88,89,95,96,97,98,99,";
                        str2 = str2.substring(0, str2.indexOf("to to")) + " " + str2.substring(str2.indexOf("to to") + 5);
                    }
                }
                if (str2.indexOf("to") == -1 && str2.indexOf("nho") > -1) {
                    if (str2.indexOf("nhonho") > -1) {
                        str20 = str20 + "00,01,02,03,04,10,11,12,13,14,20,21,22,23,24,30,31,32,33,34,40,41,42,43,44,";
                        str2 = str2.substring(0, str2.indexOf("nhonho")) + " " + str2.substring(str2.indexOf("nhonho") + 6);
                    }
                    if (str2.indexOf("nho nho") > -1) {
                        str2 = str2.substring(0, str2.indexOf("nho nho")) + " " + str2.substring(str2.indexOf("nho nho") + 7);
                        str20 = str20 + "00,01,02,03,04,10,11,12,13,14,20,21,22,23,24,30,31,32,33,34,40,41,42,43,44,";
                    }
                }
            } else {
                i2 = i;
            }
            if (str2.trim().length() == 0) {
                break;
            }
            if (str2.indexOf("chan") > -1 || (str2.indexOf("le") > -1 && str2.indexOf("chan") < 5 && str2.indexOf("le") < 5)) {
                int i75 = -1;
                if (str2.indexOf("chan") > -1) {
                }
                if (str2.indexOf("chan") > i75 && str2.indexOf("le") == i75) {
                    if (str2.indexOf("chanchan") > i75) {
                        str20 = str20 + "00,02,04,06,08,20,22,24,26,28,40,42,44,46,48,60,62,64,66,68,80,82,84,86,88,";
                        str2 = str2.substring(0, str2.indexOf("chanchan")) + " " + str2.substring(str2.indexOf("chanchan") + 8);
                    }
                    if (str2.indexOf("chan chan") > -1) {
                        str20 = str20 + "00,02,04,06,08,20,22,24,26,28,40,42,44,46,48,60,62,64,66,68,80,82,84,86,88,";
                        str2 = str2.substring(0, str2.indexOf("chan chan")) + " " + str2.substring(str2.indexOf("chan chan") + 9);
                    }
                }
                if (str2.indexOf("chan") == -1 && str2.indexOf("le") > -1) {
                    if (str2.indexOf("lele") > -1) {
                        str20 = str20 + "11,13,15,17,19,31,33,35,37,39,51,53,55,57,59,71,73,75,77,79,91,93,95,97,99,";
                        str2 = str2.substring(0, str2.indexOf("lele")) + " " + str2.substring(str2.indexOf("lele") + 4);
                    }
                    if (str2.indexOf("le le") > -1) {
                        str2 = str2.substring(0, str2.indexOf("le le")) + " " + str2.substring(str2.indexOf("le le") + 5);
                        str20 = str20 + "11,13,15,17,19,31,33,35,37,39,51,53,55,57,59,71,73,75,77,79,91,93,95,97,99,";
                    }
                }
            }
            if (str2.trim().length() == 0) {
                break;
            }
            int i76 = -1;
            if (str2.indexOf("kep") > -1) {
                str2 = str2.replaceAll("bang", "");
                int i77 = -1;
                while (true) {
                    int indexOf17 = str2.indexOf("kep", i77 + 1);
                    if (indexOf17 == i76) {
                        break;
                    }
                    if (str2.indexOf("sat") <= i76 || str2.indexOf("sat") >= indexOf17 || str2.indexOf("lech") >= indexOf17 + 6 || str2.indexOf("lech") <= -1) {
                        if (str2.indexOf("sat") >= indexOf17 || str2.indexOf("sat") <= -1) {
                            i5 = i2;
                            if (str2.indexOf("lech") <= -1 || str2.indexOf("lech") >= indexOf17 + 5) {
                                if (str2.indexOf("le") <= -1 || str2.indexOf("le") >= indexOf17 + 5) {
                                    if (str2.indexOf("chan") <= -1 || str2.indexOf("chan") >= indexOf17 + 5) {
                                        if (str2.indexOf(" 2 kep") <= -1 || str2.indexOf(" 2 kep") >= (i6 = indexOf17 + 3)) {
                                            str20 = str20 + "00,11,22,33,44,55,66,77,88,99,";
                                            sb4 = new StringBuilder();
                                            sb4.append(str2.substring(0, indexOf17));
                                            sb4.append(" ");
                                            i6 = indexOf17 + 3;
                                        } else {
                                            str20 = str20 + "00,11,22,33,44,55,66,77,88,99,05,50,16,61,27,72,38,83,49,94,";
                                            sb4 = new StringBuilder();
                                            sb4.append(str2.substring(0, indexOf17 - 2));
                                            sb4.append(" ");
                                        }
                                        sb4.append(str2.substring(i6));
                                        str2 = sb4.toString();
                                    } else if (str2.substring(indexOf17, str2.indexOf("chan") + 4).replaceAll(" ", "").replaceAll(",", "").indexOf("kepchan") > -1) {
                                        str7 = str20 + "00,22,44,66,88,";
                                        sb5 = new StringBuilder();
                                        sb5.append(str2.substring(0, indexOf17));
                                        sb5.append(" ");
                                        indexOf = str2.indexOf("chan");
                                        i7 = 4;
                                        sb5.append(str2.substring(indexOf + i7));
                                        str2 = sb5.toString();
                                        str20 = str7;
                                    }
                                } else if (str2.substring(indexOf17, str2.indexOf("le") + 2).replaceAll(" ", "").replaceAll(",", "").indexOf("keple") > -1) {
                                    str7 = str20 + "11,33,55,77,99,";
                                    sb5 = new StringBuilder();
                                    sb5.append(str2.substring(0, indexOf17));
                                    sb5.append(" ");
                                    indexOf = str2.indexOf("le");
                                    i7 = 2;
                                    sb5.append(str2.substring(indexOf + i7));
                                    str2 = sb5.toString();
                                    str20 = str7;
                                }
                            } else if (str2.substring(indexOf17, str2.indexOf("lech") + 4).replaceAll(" ", "").replaceAll(",", "").indexOf("keplech") > -1) {
                                str7 = str20 + "05,50,16,61,27,72,38,83,49,94,";
                                sb5 = new StringBuilder();
                                sb5.append(str2.substring(0, indexOf17));
                                sb5.append(" ");
                                indexOf = str2.indexOf("lech");
                                i7 = 4;
                                sb5.append(str2.substring(indexOf + i7));
                                str2 = sb5.toString();
                                str20 = str7;
                            }
                        } else {
                            int i78 = indexOf17 + 3;
                            String replaceAll13 = str2.substring(str2.indexOf("sat"), i78).replaceAll(" ", "").replaceAll(",", "");
                            i5 = i2;
                            if (replaceAll13.indexOf("satkep") > -1) {
                                str8 = str20 + "01,10,12,21,23,32,34,43,45,54,56,65,67,76,78,87,89,98,";
                                sb6 = new StringBuilder();
                            } else if (replaceAll13.indexOf("sathaikep") > -1) {
                                str8 = str20 + "01,10,12,21,23,32,34,43,45,54,56,65,67,76,78,87,89,98,04,06,51,15,17,60,62,26,28,71,73,37,39,82,84,48,93,95,";
                                sb6 = new StringBuilder();
                            } else if (replaceAll13.indexOf("sat2kep") > -1) {
                                str8 = str20 + "01,10,12,21,23,32,34,43,45,54,56,65,67,76,78,87,89,98,04,06,51,15,17,60,62,26,28,71,73,37,39,82,84,48,93,95,";
                                sb6 = new StringBuilder();
                            }
                            sb6.append(str2.substring(0, str2.indexOf("sat")));
                            sb6.append(" ");
                            sb6.append(str2.substring(i78, str2.length()));
                            str2 = sb6.toString();
                            str20 = str8;
                            indexOf17 = 0;
                        }
                        i77 = indexOf17;
                        i2 = i5;
                    } else {
                        if (str2.substring(str2.indexOf("sat"), str2.indexOf("lech") + 4).replaceAll(" ", "").replaceAll(",", "").indexOf("satkeplech") > -1) {
                            str2 = str2.substring(0, str2.indexOf("sat")) + " " + str2.substring(str2.indexOf("lech") + 4, str2.length());
                            str20 = str20 + "04,06,51,15,17,60,62,26,28,71,73,37,39,82,84,48,93,95,";
                            indexOf17 = 0;
                        }
                        i77 = indexOf17;
                    }
                    i76 = -1;
                }
            }
            int i79 = i2;
            if (str2.trim().length() == 0) {
                break;
            }
            if (str2.indexOf("so") > -1 && str2.indexOf("so") < 7) {
                String trim3 = str2.trim();
                if (trim3.length() <= 3) {
                    return "Không hiểu " + str;
                }
                if (trim3.substring(0, 2).indexOf("so") == -1) {
                    return "Không hiểu " + str;
                }
                str2 = trim3.substring(trim3.indexOf("so") + 2);
            } else if (str2.indexOf("con") > -1 && str2.indexOf("con") < 4) {
                if (str2.trim().length() <= 4) {
                    return "Không hiểu " + str;
                }
                if (str2.substring(0, 5).indexOf("con") == -1) {
                    return "Không hiểu " + str;
                }
                str2 = str2.substring(str2.indexOf("con") + 3);
            }
            int i80 = 0;
            while (true) {
                if (i80 >= str2.length()) {
                    break;
                }
                int i81 = i80 + 1;
                if (str37.indexOf(str2.substring(i80, i81)) <= -1 && !isNumeric(str2.substring(i80, i81))) {
                    i80--;
                    break;
                }
                i80 = i81;
            }
            if (i80 < 0) {
                i80 = 0;
            }
            if (str2.substring(0, i80).length() > 2) {
                String[] split11 = str2.substring(0, i80).trim().replaceAll(" ", ",").split(",");
                int i82 = 0;
                while (i82 < split11.length) {
                    if (isNumeric(split11[i82]) && split11[i82].length() == 2) {
                        sb3 = str20 + split11[i82] + ",";
                    } else {
                        if (isNumeric(split11[i82]) && split11[i82].length() == 4) {
                            if (split11[i82].substring(2, 4).indexOf("00") > -1) {
                                return "Không hiểu " + split11[i82];
                            }
                            String str39 = str20 + split11[i82].substring(0, 2) + ",";
                            sb2 = new StringBuilder();
                            sb2.append(str39);
                            substring = split11[i82].substring(2, 4);
                        } else if (isNumeric(split11[i82]) && split11[i82].length() == 3) {
                            if (split11[i82].charAt(0) != split11[i82].charAt(2)) {
                                return "Không hiểu " + split11[i82];
                            }
                            if (split11[i82].charAt(0) == split11[i82].charAt(1) && split11[i82].charAt(0) == split11[i82].charAt(2)) {
                                return "Không hiểu ," + split11[i82] + ",";
                            }
                            String str40 = str20 + split11[i82].substring(0, 2) + ",";
                            sb2 = new StringBuilder();
                            sb2.append(str40);
                            substring = split11[i82].substring(1, 3);
                        } else {
                            if (split11[i82].length() != 0) {
                                if (split11[i82].length() != 1) {
                                    return "Không hiểu " + split11[i82];
                                }
                                String str41 = str2.indexOf(" " + split11[i82] + " ") > -1 ? " " + split11[i82] + " " : "";
                                if (str2.indexOf(" " + split11[i82] + ",") > -1) {
                                    str41 = " " + split11[i82] + ",";
                                }
                                if (str2.indexOf("," + split11[i82] + ",") > -1) {
                                    str41 = "," + split11[i82] + ",";
                                }
                                if (str2.indexOf("," + split11[i82] + " ") > -1) {
                                    str41 = "," + split11[i82] + " ";
                                }
                                return "Không hiểu " + str41 + "";
                            }
                            i82++;
                        }
                        sb2.append(substring);
                        sb2.append(",");
                        sb3 = sb2.toString();
                    }
                    str20 = sb3;
                    i82++;
                }
                if (i82 < split11.length) {
                    str2 = "  " + str2 + "  ";
                    if (str2.trim().trim().length() <= 10) {
                        if (str2.trim().length() != 1) {
                            return "Không hiểu " + str2.trim();
                        }
                        return "Không hiểu  " + str2.trim() + " ";
                    }
                    int i83 = 1;
                    if (split11[i82].length() != 1) {
                        return "Không hiểu " + split11[i82];
                    }
                    i80 = -1;
                    while (true) {
                        i80 = str2.indexOf(split11[i82], i80 + i83);
                        if (i80 == -1) {
                            break;
                        }
                        if (str2.indexOf(" " + split11[i82] + " ") > -1) {
                            return "Không hiểu  " + split11[i82] + " ";
                        }
                        if (str2.indexOf(" " + split11[i82] + ",") > -1) {
                            return "Không hiểu  " + split11[i82] + ",";
                        }
                        if (str2.indexOf("," + split11[i82] + " ") > -1) {
                            return "Không hiểu ," + split11[i82] + " ";
                        }
                        if (str2.indexOf("," + split11[i82] + ",") > -1) {
                            return "Không hiểu ," + split11[i82] + ",";
                        }
                        i83 = 1;
                    }
                }
                replaceAll2 = str2.substring(i80).trim();
            } else {
                replaceAll2 = str2;
            }
            if (replaceAll2.trim().length() == 0) {
                break;
            }
            str18 = str37;
            i13 = i79;
            str19 = str3;
            i11 = -1;
            i12 = 0;
        }
        if (split != null) {
            return "Không hiểu " + str2.substring(str2.indexOf(split[i9]), i8);
        }
        return "Không hiểu " + str2;
    }

    public static String XulyTien(String str) {
        String str2 = "";
        if (str.length() - str.replaceAll("x", "").length() > 1) {
            return "Không hiểu " + str;
        }
        if (str.length() == 0) {
            return "Không hiểu ";
        }
        String trim = str.replaceAll("x", "").replaceAll(" ", "").trim();
        if (trim.length() <= 0) {
            return "Không hiểu";
        }
        int i = 0;
        if (trim.endsWith("tr")) {
            String replaceAll = trim.replaceAll("tr", "").trim().replaceAll("\\.", "");
            replaceAll.indexOf(",");
            String[] split = replaceAll.split(",");
            if (split.length > 2) {
                return "Không hiểu " + replaceAll;
            }
            if (split.length == 1) {
                return split[0] + "000";
            }
            if (split.length == 2) {
                if (split[1].length() == 0) {
                    return split[0] + "000";
                }
                if (split[1].length() == 1) {
                    return split[0] + split[1] + "00";
                }
                if (split[1].length() == 2) {
                    return split[0] + split[1] + "0";
                }
                if (split[1].length() == 3) {
                    return split[0] + split[1];
                }
                return "Không hiểu " + replaceAll;
            }
        } else {
            while (i < trim.length()) {
                int i2 = i + 1;
                if (isNumeric(trim.substring(i, i2))) {
                    break;
                }
                i = i2;
            }
            String str3 = "";
            while (i < trim.length()) {
                int i3 = i + 1;
                if (!isNumeric(trim.substring(i, i3))) {
                    break;
                }
                str3 = str3 + trim.substring(i, i3);
                i = i3;
            }
            if (trim.replaceAll(str3, "").replaceAll("ng", "").replaceAll("n", "").replaceAll("d", "").replaceAll("k", "").replaceAll(",", "").replaceAll("\\.", "").replaceAll(InternalZipConstants.ZIP_FILE_SEPARATOR, "").replaceAll(" ", "").length() > 0) {
                return "Không hiểu " + str;
            }
            str2 = str3;
        }
        try {
            if (Integer.parseInt(str2) > 0) {
                return str2;
            }
            return "Không hiểu " + str;
        } catch (Exception unused) {
            return "Không hiểu " + str;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x00c6  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x019b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static String XulyXien(String str) {
        String replace;
        String str2 = "";
        boolean z;
        String str3;
        boolean z2;
        boolean z3;
        int i = 2;
        int i2 = 0;
        int i3 = 1;
        if (str.length() > 6) {
            String replaceAll = str.trim().replaceAll("[,.;-]", " ");
            if (replaceAll.startsWith("2 ") || replaceAll.startsWith("3 ") || replaceAll.startsWith("4 ")) {
                int i4 = replaceAll.startsWith("2 ") ? 5 : replaceAll.startsWith("3 ") ? 8 : replaceAll.startsWith("4 ") ? 11 : 0;
                String XulySo = XulySo(replaceAll.substring(2));
                int i5 = 0;
                while (true) {
                    if (XulySo.length() - i5 <= i4) {
                        break;
                    }
                    int i6 = i5 + i4;
                    StringBuilder sb = new StringBuilder();
                    sb.append(XulySo.substring(0, i6));
                    sb.append(" ");
                    i5 = i6 + 1;
                    sb.append(XulySo.substring(i5));
                    XulySo = sb.toString();
                    if (XulySo.substring(i5).length() < i4 && XulySo.substring(i5).length() > 2) {
                        XulySo = "Không hiểu " + XulySo.substring(i5);
                        break;
                    }
                }
                return XulySo.trim();
            }
        } else if (str.trim().startsWith("2 ")) {
            replace = str.trim().replace("2 ", "");
            int i7 = -1;
            int i8 = 3;
            if (replace.indexOf(";") <= -1) {
                String[] split = replace.split(";");
                str2 = "";
                int i9 = 0;
                int i10 = 1;
                while (i9 < split.length) {
                    split[i9] = split[i9].replaceAll(" ", ",");
                    split[i9] = split[i9].replaceAll(",,", ",");
                    String[] split2 = split[i9].split(",");
                    int i11 = 0;
                    while (i11 < split2.length) {
                        if (split2[i11].length() == i8 && isNumeric(split2[i11])) {
                            String substring = split2[i11].substring(i2, i3);
                            String substring2 = split2[i11].substring(i3, 2);
                            String substring3 = split2[i11].substring(2, i8);
                            if (substring.indexOf(substring2) != i7 || substring.indexOf(substring3) <= i7) {
                                str2 = "";
                                i3 = 1;
                                i10 = 0;
                                break;
                            }
                            replace = replace.replaceAll(split2[i11], split2[i11].substring(0, 1) + split2[i11].substring(1, 2) + "," + split2[i11].substring(1, 2) + split2[i11].substring(0, 1));
                            i3 = 1;
                            i11++;
                            i2 = 0;
                            i7 = -1;
                            i8 = 3;
                        } else {
                            if (split2[i11].length() != 2 || !isNumeric(split2[i11])) {
                                i3 = 1;
                                if (split2[i11].length() > 1) {
                                    str2 = "";
                                    i10 = 0;
                                    break;
                                }
                                i11++;
                                i2 = 0;
                                i7 = -1;
                                i8 = 3;
                            }
                            i3 = 1;
                            i11++;
                            i2 = 0;
                            i7 = -1;
                            i8 = 3;
                        }
                    }
                    if (i10 == i3) {
                        str2 = str2 + split[i9] + " ";
                    }
                    i9++;
                    i2 = 0;
                    i3 = 1;
                    i7 = -1;
                    i8 = 3;
                }
            } else {
                String replaceAll2 = replace.replaceAll(" ", "");
                String[] split3 = replaceAll2.split(",");
                str2 = replaceAll2;
                int i12 = 0;
                while (true) {
                    if (i12 >= split3.length) {
                        z = true;
                        break;
                    }
                    if (split3[i12].length() != 3 || !isNumeric(split3[i12])) {
                        if ((split3[i12].length() != 2 || !isNumeric(split3[i12])) && split3[i12].length() > 1) {
                            z = false;
                            break;
                        }
                    } else {
                        String substring4 = split3[i12].substring(0, 1);
                        String substring5 = split3[i12].substring(1, 2);
                        String substring6 = split3[i12].substring(2, 3);
                        if (substring4.indexOf(substring5) == -1 && substring4.indexOf(substring6) > -1) {
                            str2 = str2.replaceAll(split3[i12], split3[i12].substring(0, 1) + split3[i12].substring(1, 2) + "," + split3[i12].substring(1, 2) + split3[i12].substring(0, 1));
                        }
                    }
                    i12++;
                }
                String[] split4 = str2.split(",");
                if (split4.length < 2 || split4.length > 4) {
                    z = false;
                }
                if (!z) {
                    str2 = "";
                    int i13 = 0;
                    while (i13 < i) {
                        int i14 = i13 + 1;
                        String[] split5 = replace.split(" ,".substring(i13, i14));
                        boolean z4 = true;
                        int i15 = 0;
                        while (i15 < split5.length && split5[i15].trim().length() != 0) {
                            String trim = split5[i15].trim();
                            if (trim.split(" ").length == 1) {
                                String[] split6 = trim.split(",");
                                if (split6.length == 1) {
                                    str3 = replace;
                                    str2 = "";
                                    break;
                                }
                                String str4 = trim;
                                int i16 = 0;
                                while (true) {
                                    if (i16 >= split6.length) {
                                        str3 = replace;
                                        z3 = true;
                                        break;
                                    }
                                    if (split6[i16].length() != 3 || !isNumeric(split6[i16])) {
                                        str3 = replace;
                                        if (split6[i16].length() != 2 || !isNumeric(split6[i16])) {
                                            z3 = true;
                                            if (split6[i16].length() > 1) {
                                                str2 = "";
                                                z4 = false;
                                                break;
                                            }
                                            i16++;
                                            replace = str3;
                                            i = 2;
                                        }
                                    } else if (split6[i16].substring(0, 1) == split6[i16].substring(1, i) || split6[i16].substring(0, 1) != split6[i16].substring(i, 3)) {
                                        str3 = replace;
                                    } else {
                                        String str5 = split6[i16];
                                        StringBuilder sb2 = new StringBuilder();
                                        str3 = replace;
                                        sb2.append(split6[i16].substring(0, 1));
                                        sb2.append(split6[i16].substring(1, 2));
                                        sb2.append(",");
                                        sb2.append(split6[i16].substring(1, 2));
                                        sb2.append(split6[i16].substring(0, 1));
                                        str4 = str4.replaceAll(str5, sb2.toString());
                                    }
                                    i16++;
                                    replace = str3;
                                    i = 2;
                                }
                                if (z4 == z3 && str4.length() > 4) {
                                    str2 = str2 + str4.replaceAll(" ", ",") + " ";
                                }
                            } else {
                                str3 = replace;
                                String[] split7 = trim.split(" ");
                                if (split7.length == 1) {
                                    str2 = "";
                                    break;
                                }
                                for (int i17 = 0; i17 < split7.length; i17++) {
                                    if (split7[i17].length() != 3 || !isNumeric(split7[i17])) {
                                        if (split7[i17].length() != 2 || !isNumeric(split7[i17])) {
                                            str2 = "";
                                            z2 = true;
                                            z4 = false;
                                            break;
                                        }
                                    } else if (split7[i17].substring(0, 1) != split7[i17].substring(1, 2) && split7[i17].substring(0, 1) == split7[i17].substring(2, 3)) {
                                        trim = trim.replaceAll(split7[i17], split7[i17].substring(0, 1) + split7[i17].substring(1, 2) + "," + split7[i17].substring(1, 2) + split7[i17].substring(0, 1));
                                    }
                                }
                                z2 = true;
                                if (z4 == z2 && trim.length() > 4) {
                                    str2 = str2 + trim.replaceAll(" ", ",") + " ";
                                }
                            }
                            i15++;
                            replace = str3;
                            i = 2;
                        }
                        str3 = replace;
                        if (str2.length() > 0) {
                            break;
                        }
                        i13 = i14;
                        replace = str3;
                        i = 2;
                    }
                }
            }
            return str2.trim();
        }
        replace = str;
        int i72 = -1;
        int i82 = 3;
        if (replace.indexOf(";") <= -1) {
        }
        return str2.trim();
    }

    public static ArrayList<String> XulyXienGhep(String str, int i) {
        ArrayList<String> arrayList = new ArrayList<>();
        int i2 = 0;
        if (i == 2) {
            String[] split = str.split(",");
            while (i2 < split.length - 1) {
                int i3 = i2 + 1;
                for (int i4 = i3; i4 < split.length; i4++) {
                    if (split[i2] != split[i4]) {
                        arrayList.add(sortXien(split[i2] + "," + split[i4]));
                    }
                }
                i2 = i3;
            }
        } else if (i == 3) {
            String[] split2 = str.split(",");
            while (i2 < split2.length - 2) {
                int i5 = i2 + 1;
                int i6 = i5;
                while (i6 < split2.length - 1) {
                    int i7 = i6 + 1;
                    for (int i8 = i7; i8 < split2.length; i8++) {
                        if (split2[i2] != split2[i6] && split2[i2] != split2[i8] && split2[i6] != split2[i8]) {
                            arrayList.add(sortXien(split2[i2] + "," + split2[i6] + "," + split2[i8]));
                        }
                    }
                    i6 = i7;
                }
                i2 = i5;
            }
        } else if (i == 4) {
            String[] split3 = str.split(",");
            while (i2 < split3.length - 3) {
                int i9 = i2 + 1;
                int i10 = i9;
                while (i10 < split3.length - 2) {
                    int i11 = i10 + 1;
                    int i12 = i11;
                    while (i12 < split3.length - 1) {
                        int i13 = i12 + 1;
                        for (int i14 = i13; i14 < split3.length; i14++) {
                            if (split3[i2] != split3[i10] && split3[i2] != split3[i12] && split3[i2] != split3[i14] && split3[i10] != split3[i12] && split3[i10] != split3[i14] && split3[i12] != split3[i14]) {
                                arrayList.add(sortXien(split3[i2] + "," + split3[i10] + "," + split3[i12] + "," + split3[i14]));
                            }
                        }
                        i12 = i13;
                    }
                    i10 = i11;
                }
                i2 = i9;
            }
        }
        return arrayList;
    }

    public static String Xuly_DauTN(String str) {
        String replaceAll = str.replaceAll(" ̂ ", " ").replaceAll("tong k ", "tong ko ").replaceAll("tong 0 chia", "tong ko chia").replaceAll("botrung", "bor trung").replaceAll(" ̂", "");
        for (int i = 0; i < MainActivity.formList.size(); i++) {
            replaceAll = replaceAll.replaceAll(MainActivity.formList.get(i).get("datas"), MainActivity.formList.get(i).get("type")).replaceAll("  ", " ");
        }
        for (int i2 = 0; i2 < MainActivity.formArray.size(); i2++) {
            replaceAll = replaceAll.replaceAll(MainActivity.formArray.get(i2).get("str"), MainActivity.formArray.get(i2).get("repl_str")).replaceAll("  ", " ");
        }
        for (int i3 = 1; i3 < 10; i3++) {
            replaceAll = replaceAll.replaceAll("  ", " ");
        }
        String replaceAll2 = replaceAll.replaceAll("xie n", "xi").replaceAll("le ch", "lech").replace("\n", " ").replace("\\.", ",").replaceAll(";,", ";").replaceAll("; ,", ";").replaceAll("; lo", "lo").replaceAll("va ", ";").replaceAll(";lo", "lo").replaceAll("; de", "de").replaceAll(";de", "de").replaceAll("; xi", "xi").replaceAll("dedau", "de dau").replaceAll("dedit", "de dit").replaceAll("decham", "de cham").replaceAll("dedinh", "de cham").replaceAll(";xn", "xn").replaceAll(";xi", "xi").replaceAll("; bc", "bc").replaceAll(";bc", "bc");
        replaceAll2.replaceAll("bc", " bc ").replace("dan", " dan ").replace("cua", " trung ").replace("chia", " chia ").replace("dau", " dau ").replace("dit", " dit ").replace("tong", " tong ").replace("cham", " cham ").replace("boj", " boj ").replace("bor", " bor ").replace("dea", " dea ").replaceAll("deb", " deb ").replaceAll("dec", " dec ").replaceAll("ded", " ded ").replace("lo ", " lo ").replaceAll("xg", " xg ").replaceAll("xn", " xn ");
        return (replaceAll2.indexOf("dea") == -1 && replaceAll2.indexOf("deb") == -1 && replaceAll2.indexOf("dec") == -1 && replaceAll2.indexOf("ded") == -1 && replaceAll2.indexOf("det") == -1 && replaceAll2.indexOf("de") > -1) ? replaceAll2.replaceAll("de", "deb ") : replaceAll2;
    }

    public static final String convertKhongDau(String str) {
        String replaceAll = (str.toLowerCase() + " ").replaceAll("bỏ", "bor").replaceAll("bộ", "boj").replaceAll("\\.", ",").replaceAll("́", "").replaceAll("̀", "").replaceAll("̉", "").replaceAll("̣", "").replaceAll("̃", "").replaceAll("\\+", "!");
        for (int i = 0; i < 68; i++) {
            replaceAll = replaceAll.replace("ăâàằầáắấảẳẩãẵẫạặậễẽểẻéêèềếẹệôòồơờóốớỏổởõỗỡọộợưúùứừủửũữụựìíỉĩịỳýỷỹỵđ×".charAt(i), "aaaaaaaaaaaaaaaaaeeeeeeeeeeeooooooooooooooooouuuuuuuuuuuiiiiiyyyyydx".charAt(i));
        }
        String replaceAll2 = replaceAll.replaceAll("\n d", "\nd").replaceAll("  ", " ");
        if (replaceAll2.indexOf("\nđ") > -1 || replaceAll2.indexOf("\nd") > -1) {
            int i2 = -1;
            while (true) {
                i2 = replaceAll2.indexOf("\nd", i2 + 1);
                if (i2 != -1) {
                    if (i2 < replaceAll2.length() - 1) {
                        String substring = replaceAll2.substring(i2 + 2, i2 + 3);
                        if (isNumeric(substring) || substring.indexOf(" ") > -1 || substring.indexOf(":") > -1) {
                            replaceAll2 = replaceAll2.replaceAll("\nd" + substring, "!d" + substring);
                        }
                    }
                }
            }
        }
        return replaceAll2.replaceAll("\\s+", " ").replace("d e", "de").replace("d au", "dau").replace("d it", "dit").replace("ja", "ia").replace("dich", "dit").replace("je", "ie").replace("nde", "n de").replace("nlo", "n lo").replace("nxi", "n xi").replace("nda", "n da").replace("ndi", "n di").replace("nto", "n to").replace("x i", "xi").replace("x j", "xi").replace("xj", "xi").replace("x 3 bc", "x 3, bc");
    }

    public static String fixTinNhan(String str) {
        String str2 = str + " ";
        String str1 = str2.replaceAll(" ", ",");
        str1.replaceAll("\\.", ",").replaceAll(":", ",").replaceAll(";", ",").replaceAll("/", ",").split(",");
        int i = -1;
        if (str2.indexOf("Không hiểu") == -1) {
            for (int i2 = 0; i2 < str2.length(); i2++) {
                if (str2.charAt(i2) > 127 || str2.charAt(i2) < 31) {
                    str2 = str2.substring(0, i2) + " " + str2.substring(i2 + 1);
                }
            }
            String str3 = str2.trim();
            if (str3.charAt(str3.length() - 1) == 'x') {
                str3 = str3.substring(0, str3.length() - 1);
            }
            int dem = -1;
            while (true) {
                int indexOf = str3.indexOf("x ", dem + 1);
                dem = indexOf;
                if (indexOf == -1) {
                    break;
                }
                int i3 = dem + 2;
                while (i3 < str3.length() && !isNumeric(str3.substring(i3, i3 + 1))) {
                    i3++;
                }
                int j = i3;
                while (j < str3.length() && (isNumeric(str3.substring(j, j + 1)) || " tr".indexOf(str3.substring(j, j + 1)) != -1)) {
                    j++;
                }
                if (isNumeric(str3.substring(dem + 1, j).trim()) && str3.substring(dem + 1, j).trim().length() > 1) {
                    str3 = str3.substring(0, j) + " " + str3.substring(j);
                } else if (j - i3 > 1 && str3.substring(dem).indexOf("to") != (j - dem) - 1 && str3.substring(dem).indexOf("tin") != (j - dem) - 1 && str3.substring(dem).indexOf(",") != j - dem) {
                    str3 = str3.substring(0, j) + " " + str3.substring(j);
                } else if (j - i3 == 1 && str3.substring(dem).indexOf("tr") == -1) {
                    str3 = str3.substring(0, j) + " " + str3.substring(j);
                }
            }
            String str4 = str3 + " ";
            int dem2 = str4.length();
            while (dem2 > str4.length() - 9) {
                String Sss = str4.substring(dem2);
                if (Sss.trim().indexOf("t ") > i) {
                    String Sss1 = "";
                    for (int i4 = dem2; i4 > 0; i4--) {
                        Sss1 = str4.substring(i4, dem2);
                        if (!isNumeric(Sss1) && Sss1.trim().length() > 0) {
                            break;
                        }
                    }
                    if (Sss1.trim().length() > 1 || !isNumeric(Sss1)) {
                        String Sss2 = Sss.replaceAll("t", "").replaceAll(" ", "").replaceAll(",", "");
                        if (isNumeric(Sss2) && Integer.parseInt(Sss2) < 99) {
                            str4 = str4.substring(0, dem2);
                        } else {
                            if (Sss2.length() != 0) {
                                break;
                            }
                            str4 = str4.substring(0, dem2 + 1) + "?";
                        }
                    }
                }
                dem2--;
                i = -1;
            }
            String str5 = str4.trim();
            try {
                String SSS = str5.substring(str5.length() - 1);
                if (SSS.indexOf("@") > -1) {
                    int i5 = str5.length() - 2;
                    while (i5 > 0 && str5.substring(i5, i5 + 1).indexOf("@") <= -1) {
                        i5--;
                    }
                    if (i5 > str5.length() - 13) {
                        String SS = str5.substring(i5);
                        if (isNumeric(SS.replaceAll("@", ""))) {
                            str5 = str5.substring(0, i5);
                        }
                    }
                }
            } catch (Exception e) {
            }
            try {
                if (MainActivity.jSon_Setting.getInt("baotinthieu") == 0) {
                    str5 = str5.trim();
                    for (int i6 = 6; i6 > 0; i6--) {
                        String Sss3 = str5.substring(0, i6);
                        if (Sss3.trim().indexOf("t") > -1 && isNumeric(Sss3.replaceAll("t", "").replaceAll(",", ""))) {
                            str5 = str5.substring(i6);
                        }
                    }
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            String str6 = str5 + " ";
            int i1 = -1;
            while (true) {
                try {
                    int indexOf2 = str6.indexOf("tin", i1 + 1);
                    i1 = indexOf2;
                    if (indexOf2 == -1) {
                        break;
                    }
                    int i7 = i1 + 5;
                    while (i7 < i1 + 10 && isNumeric(str6.substring(i1 + 4, i7))) {
                        i7++;
                    }
                    if (i7 - i1 > 5) {
                        str6 = str6.substring(0, i1) + str6.substring(i7);
                    }
                } catch (Exception e3) {
                }
            }
            String str7 = str6.trim();
            if (str7.substring(0, 1).indexOf(",") > -1) {
                return str7.substring(1);
            }
            return str7;
        }
        return str2;
    }

    public static String fixTinNhan1(String str) {
        int i;
        StringBuilder sb;
        String replaceAll = str.replaceAll(" ,", ", ");
        int length = replaceAll.length();
        int i2 = 0;
        while (i2 < length - 1) {
            i2++;
            length = replaceAll.length() - 1;
            if (Character.isLetter(replaceAll.charAt(i2))) {
                i = i2 + 1;
                if (!Character.isLetter(replaceAll.charAt(i))) {
                    sb = new StringBuilder();
                    sb.append(replaceAll.substring(0, i));
                    sb.append(" ");
                    sb.append(replaceAll.substring(i));
                    replaceAll = sb.toString();
                    i2 = i;
                }
            }
            if (!Character.isLetter(replaceAll.charAt(i2))) {
                i = i2 + 1;
                if (Character.isLetter(replaceAll.charAt(i))) {
                    sb = new StringBuilder();
                    sb.append(replaceAll.substring(0, i));
                    sb.append(" ");
                    sb.append(replaceAll.substring(i));
                    replaceAll = sb.toString();
                    i2 = i;
                }
            }
        }
        String str2 = replaceAll + " ";
        for (int i3 = 1; i3 < 10; i3++) {
            str2 = str2.replaceAll("  ", " ");
        }
        if (str2.indexOf("(") > -1 && str2.indexOf(")") > -1) {
            int i4 = -1;
            while (true) {
                i4 = str2.indexOf("(", i4 + 1);
                if (i4 == -1) {
                    break;
                }
                int i5 = i4;
                while (i5 < str2.length()) {
                    int i6 = i5 + 1;
                    if (str2.substring(i5, i6).indexOf(")") > -1) {
                        break;
                    }
                    i5 = i6;
                }
                if (isNumeric(str2.substring(i4 + 1, i5).replaceAll(" ", ""))) {
                    for (int i7 = i4; i7 < i5; i7++) {
                        if (isNumeric(str2.substring(i7 - 1, i7))) {
                            int i8 = i7 + 1;
                            if (str2.substring(i7, i8).indexOf(" ") > -1 && isNumeric(str2.substring(i8, i7 + 2))) {
                                str2 = str2.substring(0, i7) + "," + str2.substring(i8);
                            }
                        }
                    }
                }
            }
        }
        return str2;
    }

    public static boolean isNumeric(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        for (char c : str.toCharArray()) {
            if (c < '0' || c > '9') {
                return false;
            }
        }
        Boolean bool = true;
        return bool.booleanValue();
    }

    private static Date parseDate(String str) {
        try {
            return new SimpleDateFormat("HH:mm", Locale.US).parse(str);
        } catch (ParseException unused) {
            return new Date(0L);
        }
    }

    public static String sortXien(String str) {
        ArrayList arrayList = new ArrayList();
        for (String str2 : str.split(",")) {
            arrayList.add(str2);
        }
        Collections.sort(arrayList);
        String str3 = "";
        for (int i = 0; i < arrayList.size(); i++) {
            str3 = str3 + arrayList.get(i) + ",";
        }
        return str3;
    }

    public static Boolean xuLymem(String str) {
        int i = 0;
        Boolean bool = str.length() != 0;
        String trim = str.replaceAll(",", "").trim();
        while (true) {
            if (i >= 10) {
                break;
            }
            String replaceAll = trim.replaceAll(i + "", "");
            if (trim.length() - replaceAll.length() > 1) {
                bool = false;
                break;
            }
            i++;
            trim = replaceAll;
        }
        if (trim.length() > 0) {
            return false;
        }
        return bool;
    }
}
