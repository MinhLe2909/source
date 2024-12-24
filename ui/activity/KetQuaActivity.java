package tamhoang.ldpro4.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import tamhoang.ldpro4.R;
import tamhoang.ldpro4.ui.activity.MyWebViewClient;

public class KetQuaActivity extends Activity {
    String url = "http://sms.vn/Tin-tuc-su-kien/2422/Thong-bao-tong-dai-Xo-so-19001588-chuyen-doi-sang-tong-dai-8085.html";
    private WebView webView;

    @SuppressLint("SetJavaScriptEnabled")
    private void goUrl() {
        this.webView.getSettings().setLoadsImagesAutomatically(true);
        this.webView.getSettings().setJavaScriptEnabled(true);
        this.webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        this.webView.loadUrl(this.url);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_kq);
        WebView webView2 = (WebView) findViewById(R.id.webView);
        this.webView = webView2;
        webView2.setWebViewClient(new MyWebViewClient());
        goUrl();
    }
}
