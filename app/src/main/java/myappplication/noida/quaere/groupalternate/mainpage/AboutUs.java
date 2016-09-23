package myappplication.noida.quaere.groupalternate.mainpage;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

import myappplication.noida.quaere.groupalternate.R;

/**
 * Created by intex on 3/8/2016.
 */
public class AboutUs extends Activity {
    private WebView webView;
    private ProgressBar progress;
    String url = "http://shinecityinfra.com/AboutUs";
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_us);

        webView = (WebView) findViewById(R.id.webView);
        progress = (ProgressBar)findViewById(R.id.progressBar);
        back = (ImageView) findViewById(R.id.about_backBtn);
        progress.setVisibility(View.GONE);
       // progress = new android.widget.ProgressBar(this, null, android.R.attr.progressBarStyle);
       // progress.getIndeterminateDrawable().setColorFilter(0xFF0E2AB3, android.graphics.PorterDuff.Mode.MULTIPLY);

        webView.setWebViewClient(new MyWebViewClient(this, progress));
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private class MyWebViewClient extends WebViewClient {
        private ProgressBar progress;
          Activity activity;

        public MyWebViewClient(Activity activity, ProgressBar progress) {
            this.activity = activity;
            this.progress = progress;
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            progress.setVisibility(View.VISIBLE);
            progress.setProgress(100);
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            progress.setVisibility(View.GONE);
           progress.setProgress(100);
            super.onPageFinished(view, url);
        }

        public void setValue(int progress) {
            this.progress.setProgress(progress);
        }
    }
}

