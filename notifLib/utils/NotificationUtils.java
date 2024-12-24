package tamhoang.ldpro4.notifLib.utils;

import android.R;
import android.app.Notification;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RemoteViews;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.RemoteInput;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import tamhoang.ldpro4.notifLib.models.Action;
import tamhoang.ldpro4.notifLib.models.NotificationIds;
import tamhoang.ldpro4.notifLib.utils.VersionUtils;

/* loaded from: classes.dex */
public class NotificationUtils {
    private static final String[] REPLY_KEYWORDS = {"reply", "Trả lời", "android.intent.extra.text"};
    private static final CharSequence REPLY_KEYWORD = "reply";
    private static final CharSequence REPLY_KEYWORD2 = "Trả lời";
    private static final CharSequence INPUT_KEYWORD = "input";

    public static ArrayList<Action> getActions(Notification n, String packageName, ArrayList<Action> actions) {
        NotificationCompat.WearableExtender wearableExtender = new NotificationCompat.WearableExtender(n);
        if (wearableExtender.getActions().size() > 0) {
            for (NotificationCompat.Action action : wearableExtender.getActions()) {
                boolean z = false;
                if (action.title.toString().toLowerCase().contains(REPLY_KEYWORD) || action.title.toString().toLowerCase().contains(REPLY_KEYWORD2)) {
                    z = true;
                }
                actions.add(new Action(action, packageName, z));
            }
        }
        return actions;
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x00ad  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00df  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0111  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0143  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0175  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x01a7  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x01d9  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x020b  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x023d  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x0259  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static String getExpandedText(ViewGroup localView) {
        String str;
        TextView findViewById;
        TextView findViewById2;
        TextView findViewById3;
        TextView findViewById4;
        TextView findViewById5;
        TextView findViewById6;
        TextView findViewById7;
        TextView findViewById8;
        TextView findViewById9;
        TextView findViewById10;
        CharSequence text;
        CharSequence text2;
        CharSequence text3;
        CharSequence text4;
        CharSequence text5;
        CharSequence text6;
        CharSequence text7;
        CharSequence text8;
        CharSequence text9;
        CharSequence text10;
        boolean z;
        String str2 = "";
        if (localView != null) {
            Context context = localView.getContext();
            TextView findViewById11 = localView.findViewById(NotificationIds.getInstance(context).big_notification_content_text);
            if (findViewById11 != null && ((z = findViewById11 instanceof TextView))) {
                str = ( findViewById11).getText().toString();
                if (!str.equals("")) {
                    TextView findViewById12 = localView.findViewById(R.id.title);
                    if (findViewById11 != null && z) {
                        String charSequence = ( findViewById12).getText().toString();
                        if (!charSequence.equals("")) {
                            str = charSequence + " " + str;
                        }
                    }
                    findViewById = localView.findViewById(NotificationIds.getInstance(context).inbox_notification_event_10_id);
                    if (findViewById != null && (findViewById instanceof TextView)) {
                        text10 = ( findViewById).getText();
                        if (!text10.equals("") && !text10.equals("")) {
                            str = str + text10.toString();
                        }
                    }
                    findViewById2 = localView.findViewById(NotificationIds.getInstance(context).inbox_notification_event_9_id);
                    if (findViewById2 != null && (findViewById2 instanceof TextView)) {
                        text9 = ( findViewById2).getText();
                        if (!text9.equals("")) {
                            str = str + "\n" + text9.toString();
                        }
                    }
                    findViewById3 = localView.findViewById(NotificationIds.getInstance(context).inbox_notification_event_8_id);
                    if (findViewById3 != null && (findViewById3 instanceof TextView)) {
                        text8 = ( findViewById3).getText();
                        if (!text8.equals("")) {
                            str = str + "\n" + text8.toString();
                        }
                    }
                    findViewById4 = localView.findViewById(NotificationIds.getInstance(context).inbox_notification_event_7_id);
                    if (findViewById4 != null && (findViewById4 instanceof TextView)) {
                        text7 = ( findViewById4).getText();
                        if (!text7.equals("")) {
                            str = str + "\n" + text7.toString();
                        }
                    }
                    findViewById5 = localView.findViewById(NotificationIds.getInstance(context).inbox_notification_event_6_id);
                    if (findViewById5 != null && (findViewById5 instanceof TextView)) {
                        text6 = ( findViewById5).getText();
                        if (!text6.equals("")) {
                            str = str + "\n" + text6.toString();
                        }
                    }
                    findViewById6 = localView.findViewById(NotificationIds.getInstance(context).inbox_notification_event_5_id);
                    if (findViewById6 != null && (findViewById6 instanceof TextView)) {
                        text5 = ( findViewById6).getText();
                        if (!text5.equals("")) {
                            str = str + "\n" + text5.toString();
                        }
                    }
                    findViewById7 = localView.findViewById(NotificationIds.getInstance(context).inbox_notification_event_4_id);
                    if (findViewById7 != null && (findViewById7 instanceof TextView)) {
                        text4 = ( findViewById7).getText();
                        if (!text4.equals("")) {
                            str = str + "\n" + text4.toString();
                        }
                    }
                    findViewById8 = localView.findViewById(NotificationIds.getInstance(context).inbox_notification_event_3_id);
                    if (findViewById8 != null && (findViewById8 instanceof TextView)) {
                        text3 = ( findViewById8).getText();
                        if (!text3.equals("")) {
                            str = str + "\n" + text3.toString();
                        }
                    }
                    findViewById9 = localView.findViewById(NotificationIds.getInstance(context).inbox_notification_event_2_id);
                    if (findViewById9 != null && (findViewById9 instanceof TextView)) {
                        text2 = ( findViewById9).getText();
                        if (!text2.equals("")) {
                            str = str + "\n" + text2.toString();
                        }
                    }
                    findViewById10 = localView.findViewById(NotificationIds.getInstance(context).inbox_notification_event_1_id);
                    if (findViewById10 != null && (findViewById10 instanceof TextView)) {
                        text = ( findViewById10).getText();
                        if (!text.equals("")) {
                            str = str + "\n" + text.toString();
                        }
                    }
                    if (str.equals("")) {
                        TextView findViewById13 = localView.findViewById(NotificationIds.getInstance(context).notification_title_id);
                        TextView findViewById14 = localView.findViewById(NotificationIds.getInstance(context).big_notification_title_id);
                        TextView findViewById15 = localView.findViewById(NotificationIds.getInstance(context).inbox_notification_title_id);
                        if (findViewById13 != null && (findViewById13 instanceof TextView)) {
                            str = str + ((Object) ( findViewById13).getText()) + " - ";
                        } else if (findViewById14 != null && (findViewById14 instanceof TextView)) {
                            str = str + ((Object) ( findViewById13).getText());
                        } else if (findViewById15 != null && (findViewById15 instanceof TextView)) {
                            str = str + ((Object) ( findViewById13).getText());
                        }
                        TextView findViewById16 = localView.findViewById(NotificationIds.getInstance(context).notification_subtext_id);
                        if (findViewById16 != null && (findViewById16 instanceof TextView)) {
                            CharSequence text11 = ( findViewById16).getText();
                            if (!text11.equals("")) {
                                str2 = str + text11.toString();
                            }
                        }
                    }
                    str2 = str;
                }
            }
            str = "";
            findViewById = localView.findViewById(NotificationIds.getInstance(context).inbox_notification_event_10_id);
            if (findViewById != null) {
                text10 = ( findViewById).getText();
                if (!text10.equals("")) {
                    str = str + text10.toString();
                }
            }
            findViewById2 = localView.findViewById(NotificationIds.getInstance(context).inbox_notification_event_9_id);
            if (findViewById2 != null) {
                text9 = ( findViewById2).getText();
                if (!text9.equals("")) {
                }
            }
            findViewById3 = localView.findViewById(NotificationIds.getInstance(context).inbox_notification_event_8_id);
            if (findViewById3 != null) {
                text8 = ( findViewById3).getText();
                if (!text8.equals("")) {
                }
            }
            findViewById4 = localView.findViewById(NotificationIds.getInstance(context).inbox_notification_event_7_id);
            if (findViewById4 != null) {
                text7 = ( findViewById4).getText();
                if (!text7.equals("")) {
                }
            }
            findViewById5 = localView.findViewById(NotificationIds.getInstance(context).inbox_notification_event_6_id);
            if (findViewById5 != null) {
                text6 = ( findViewById5).getText();
                if (!text6.equals("")) {
                }
            }
            findViewById6 = localView.findViewById(NotificationIds.getInstance(context).inbox_notification_event_5_id);
            if (findViewById6 != null) {
                text5 = ( findViewById6).getText();
                if (!text5.equals("")) {
                }
            }
            findViewById7 = localView.findViewById(NotificationIds.getInstance(context).inbox_notification_event_4_id);
            if (findViewById7 != null) {
                text4 = ( findViewById7).getText();
                if (!text4.equals("")) {
                }
            }
            findViewById8 = localView.findViewById(NotificationIds.getInstance(context).inbox_notification_event_3_id);
            if (findViewById8 != null) {
                text3 = ( findViewById8).getText();
                if (!text3.equals("")) {
                }
            }
            findViewById9 = localView.findViewById(NotificationIds.getInstance(context).inbox_notification_event_2_id);
            if (findViewById9 != null) {
                text2 = ( findViewById9).getText();
                if (!text2.equals("")) {
                }
            }
            findViewById10 = localView.findViewById(NotificationIds.getInstance(context).inbox_notification_event_1_id);
            if (findViewById10 != null) {
                text = ( findViewById10).getText();
                if (!text.equals("")) {
                }
            }
            if (str.equals("")) {
            }
            str2 = str;
        }
        return str2.trim();
    }

    public static String getExtended(Bundle extras, ViewGroup v) {
        Log.d("NOTIFICATIONUTILS", "Getting message from extras..");
        CharSequence[] charSequenceArray = extras.getCharSequenceArray(NotificationCompat.EXTRA_TEXT_LINES);
        if (charSequenceArray == null || charSequenceArray.length <= 0) {
            CharSequence charSequence = extras.getCharSequence(NotificationCompat.EXTRA_BIG_TEXT);
            return !TextUtils.isEmpty(charSequence) ? charSequence.toString() : !VersionUtils.isJellyBeanMR2() ? getExtended(v) : getMessage(extras);
        }
        StringBuilder sb = new StringBuilder();
        for (CharSequence charSequence2 : charSequenceArray) {
            if (!TextUtils.isEmpty(charSequence2)) {
                sb.append(charSequence2.toString());
                sb.append('\n');
            }
        }
        return sb.toString().trim();
    }

    public static String getExtended(ViewGroup localView) {
        Log.d("NOTIFICATIONUTILS", "Getting extended message..");
        Context context = localView.getContext();
        TextView textView =  localView.findViewById(NotificationIds.getInstance(context).EMAIL_0);
        String str = "";
        if (textView != null && !TextUtils.isEmpty(textView.getText())) {
            str = "" + textView.getText().toString() + '\n';
        }
        TextView textView2 =  localView.findViewById(NotificationIds.getInstance(context).EMAIL_1);
        if (textView2 != null && !TextUtils.isEmpty(textView2.getText())) {
            str = str + textView2.getText().toString() + '\n';
        }
        TextView textView3 =  localView.findViewById(NotificationIds.getInstance(context).EMAIL_2);
        if (textView3 != null && !TextUtils.isEmpty(textView3.getText())) {
            str = str + textView3.getText().toString() + '\n';
        }
        TextView textView4 =  localView.findViewById(NotificationIds.getInstance(context).EMAIL_3);
        if (textView4 != null && !TextUtils.isEmpty(textView4.getText())) {
            str = str + textView4.getText().toString() + '\n';
        }
        TextView textView5 =  localView.findViewById(NotificationIds.getInstance(context).EMAIL_4);
        if (textView5 != null && !TextUtils.isEmpty(textView5.getText())) {
            str = str + textView5.getText().toString() + '\n';
        }
        TextView textView6 =  localView.findViewById(NotificationIds.getInstance(context).EMAIL_5);
        if (textView6 != null && !TextUtils.isEmpty(textView6.getText())) {
            str = str + textView6.getText().toString() + '\n';
        }
        TextView textView7 =  localView.findViewById(NotificationIds.getInstance(context).EMAIL_6);
        if (textView7 != null && !TextUtils.isEmpty(textView7.getText())) {
            str = str + textView7.getText().toString() + '\n';
        }
        if (str.isEmpty()) {
            str = getExpandedText(localView);
        }
        if (str.isEmpty()) {
            str = getMessage(localView);
        }
        return str.trim();
    }

    public static ViewGroup getLocalView(Context context, Notification n) {
        ViewGroup viewGroup = null;
        RemoteViews remoteViews = Build.VERSION.SDK_INT >= 16 ? n.bigContentView : null;
        if (remoteViews == null) {
            remoteViews = n.contentView;
        }
        try {
            viewGroup = (ViewGroup) ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(remoteViews.getLayoutId(), (ViewGroup) null);
            remoteViews.reapply(context, viewGroup);
            return viewGroup;
        } catch (Exception unused2) {
            return null;
        }
    }

    public static String getMessage(Bundle extras) {
        Log.d("NOTIFICATIONUTILS", "Getting message from extras..");
        Log.d("Text", "" + ((Object) extras.getCharSequence(NotificationCompat.EXTRA_TEXT)));
        Log.d("Big Text", "" + ((Object) extras.getCharSequence(NotificationCompat.EXTRA_BIG_TEXT)));
        Log.d("Title Big", "" + ((Object) extras.getCharSequence(NotificationCompat.EXTRA_TITLE_BIG)));
        Log.d("Info text", "" + ((Object) extras.getCharSequence(NotificationCompat.EXTRA_INFO_TEXT)));
        Log.d("Info text", "" + ((Object) extras.getCharSequence(NotificationCompat.EXTRA_INFO_TEXT)));
        Log.d("Subtext", "" + ((Object) extras.getCharSequence(NotificationCompat.EXTRA_SUB_TEXT)));
        Log.d("Summary", "" + extras.getString(NotificationCompat.EXTRA_SUMMARY_TEXT));
        CharSequence charSequence = extras.getCharSequence(NotificationCompat.EXTRA_TEXT);
        if (!TextUtils.isEmpty(charSequence)) {
            return charSequence.toString();
        }
        String string = extras.getString(NotificationCompat.EXTRA_SUMMARY_TEXT);
        if (TextUtils.isEmpty(string)) {
            return null;
        }
        return string.toString();
    }

    public static String getMessage(ViewGroup localView) {
        TextView textView;
        Log.d("NOTIFICATIONUTILS", "Getting message..");
        Context context = localView.getContext();
        TextView textView2 =  localView.findViewById(NotificationIds.getInstance(context).BIG_TEXT);
        String charSequence = (textView2 == null || TextUtils.isEmpty(textView2.getText())) ? null : textView2.getText().toString();
        return (!TextUtils.isEmpty(charSequence) || (textView =  localView.findViewById(NotificationIds.getInstance(context).TEXT)) == null) ? charSequence : textView.getText().toString();
    }

    public static ViewGroup getMessageView(Context context, Notification n) {
        Log.d("NOTIFICATIONUTILS", "Getting message view..");
        RemoteViews remoteViews = Build.VERSION.SDK_INT >= 16 ? n.bigContentView : null;
        if (remoteViews == null) {
            remoteViews = n.contentView;
        }
        if (remoteViews == null) {
            return null;
        }
        ViewGroup viewGroup = (ViewGroup) ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(remoteViews.getLayoutId(), (ViewGroup) null);
        remoteViews.reapply(context.getApplicationContext(), viewGroup);
        return viewGroup;
    }

    private static NotificationCompat.Action getQuickReplyAction(Notification n) {
        for (int i = 0; i < NotificationCompat.getActionCount(n); i++) {
            NotificationCompat.Action action = NotificationCompat.getAction(n, i);
            if (action.getRemoteInputs() != null) {
                for (int i2 = 0; i2 < action.getRemoteInputs().length; i2++) {
                    if (isKnownReplyKey(action.getRemoteInputs()[i2].getResultKey())) {
                        return action;
                    }
                }
            }
        }
        return null;
    }

    public static Action getQuickReplyAction(Notification n, String packageName) {
        NotificationCompat.Action quickReplyAction = Build.VERSION.SDK_INT >= 24 ? getQuickReplyAction(n) : null;
        if (quickReplyAction == null) {
            quickReplyAction = getWearReplyAction(n);
        }
        if (quickReplyAction == null) {
            return null;
        }
        return new Action(quickReplyAction, packageName, true);
    }

    public static String getTitle(Bundle extras) {
        Log.d("NOTIFICATIONUTILS", "Getting title from extras..");
        String string = extras.getString(NotificationCompat.EXTRA_TITLE);
        Log.d("Title Big", "" + extras.getString(NotificationCompat.EXTRA_TITLE_BIG));
        return string;
    }

    public static String getTitle(ViewGroup localView) {
        Log.d("NOTIFICATIONUTILS", "Getting title..");
        TextView textView =  localView.findViewById(NotificationIds.getInstance(localView.getContext()).TITLE);
        if (textView != null) {
            return textView.getText().toString();
        }
        return null;
    }

    public static ViewGroup getView(Context context, RemoteViews view) {
        ViewGroup viewGroup = (ViewGroup) ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(view.getLayoutId(), (ViewGroup) null);
        view.reapply(context, viewGroup);
        return viewGroup;
    }

    private static NotificationCompat.Action getWearReplyAction(Notification n) {
        Iterator<NotificationCompat.Action> it = new NotificationCompat.WearableExtender(n).getActions().iterator();
        while (it.hasNext()) {
            NotificationCompat.Action next = it.next();
            if (next.getRemoteInputs() != null) {
                for (int i = 0; i < next.getRemoteInputs().length; i++) {
                    RemoteInput remoteInput = next.getRemoteInputs()[i];
                    if (isKnownReplyKey(remoteInput.getResultKey()) || remoteInput.getResultKey().toLowerCase().contains(INPUT_KEYWORD)) {
                        return next;
                    }
                }
            }
        }
        return null;
    }

    public static boolean isAPriorityMode(int interruptionFilter) {
        return (interruptionFilter == 3 || interruptionFilter == 0) ? false : true;
    }

    private static boolean isKnownReplyKey(String resultKey) {
        if (TextUtils.isEmpty(resultKey)) {
            return false;
        }
        String lowerCase = resultKey.toLowerCase();
        for (String str : REPLY_KEYWORDS) {
            if (lowerCase.contains(str)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isRecent(StatusBarNotification sbn, long recentTimeframeInSecs) {
        return sbn.getNotification().when > 0 && System.currentTimeMillis() - sbn.getNotification().when <= TimeUnit.SECONDS.toMillis(recentTimeframeInSecs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static boolean notificationMatchesFilter(StatusBarNotification sbn, NotificationListenerService.RankingMap rankingMap) {
        NotificationListenerService.Ranking ranking = new NotificationListenerService.Ranking();
        return rankingMap.getRanking(sbn.getKey(), ranking) && ranking.matchesInterruptionFilter();
    }
}
