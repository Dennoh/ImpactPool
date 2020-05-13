package impactpool.org.impactpool;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class TCDSServicesActivity extends AppCompatActivity {
    WebView webViewMainSite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tcdsservices);
        Bundle bundle = getIntent().getExtras();
        final String serviceType = bundle.getString("servicetype");
        //getSupportActionBar().setTitle(serviceType);

        // Makes Progress bar Visible
        //Get Web view
        webViewMainSite = findViewById(R.id.webviewMain);
        //This is the id you gave to the WebView in the main.xml
        webViewMainSite.getSettings().setJavaScriptEnabled(true);
        webViewMainSite.getSettings().setSupportZoom(true);       //Zoom Control on web (You don't need this
        //if ROM supports Multi-Touch
        webViewMainSite.getSettings().setBuiltInZoomControls(true); //Enable Multitouch if supported by ROM
        webViewMainSite.setWebViewClient(new WebViewClient());

        // Load URL
        webViewMainSite.loadUrl("https://tcds.or.tz/");

        // Sets the Chrome Client, and defines the onProgressChanged
        // This makes the Progress bar be updated.
        final Activity MyActivity = this;
        webViewMainSite.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                //Make the bar disappear after URL is loaded, and changes string to Loading...
                MyActivity.setTitle("Loading...");
                MyActivity.setProgress(progress * 100); //Make the bar disappear after URL is loaded

                // Return the app name after finish loading
                if (progress == 100)
                    MyActivity.setTitle(serviceType);
            }
        });

    }
}
