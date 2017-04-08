package demo.example.thanhldtse61575.hotelservicetvbox;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class SurveyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_survey);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.layout_actionbar);
        TextView abTitle=(TextView)findViewById(getResources().getIdentifier("action_bar_title", "id", getPackageName()));
        abTitle.setText(getResources().getString(R.string.survey));

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        gotoPage();
    }

    private void gotoPage(){

        WebView webView = (WebView) findViewById(R.id.webview);

        WebSettings webSettings = webView.getSettings();
        webSettings.setBuiltInZoomControls(true);
        webSettings.setJavaScriptEnabled(true);

        webView.setWebViewClient(new Callback());  //HERE IS THE MAIN CHANGE
        String lang = getLang();
        if(lang=="vi") {
            webView.loadUrl("https://docs.google.com/forms/d/e/1FAIpQLScpyuDZlMiKdCX6jljv7LTELuatjI8eJ2I1MOmR1b0FERUaHQ/viewform");
        } else {
            webView.loadUrl("https://docs.google.com/forms/d/e/1FAIpQLScZTCEzBmcHJEUwqks5gk9soMaUojx5r820qXDJ9qIahIhWWQ/viewform?usp=sf_link");
        }

    }

    private class Callback extends WebViewClient {  //HERE IS THE MAIN CHANGE.

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return (false);
        }
    }

    private String getLang(){

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("ShareLang", Context.MODE_PRIVATE);
        String jsonPreferences = sharedPref.getString("LocalePrefs", "");

        return jsonPreferences;
    }
}
