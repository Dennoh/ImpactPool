package tcds.or.tcdsapp.mainapp;

import androidx.appcompat.app.AppCompatActivity;
import tcds.or.tcdsapp.R;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class CoachingActivity extends AppCompatActivity {
    WebView webViewMainSite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coaching);
        if (haveNetworkConnection()) {
            LoadWebView();
        } else {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void LoadWebView() {
        webViewMainSite = findViewById(R.id.webviewMain);
        //This is the id you gave to the WebView in the main.xml
        webViewMainSite.getSettings().setJavaScriptEnabled(true);
        webViewMainSite.getSettings().setSupportZoom(true);       //Zoom Control on web (You don't need this
        //if ROM supports Multi-Touch
        webViewMainSite.getSettings().setBuiltInZoomControls(true); //Enable Multitouch if supported by ROM
        webViewMainSite.setWebViewClient(new WebViewClient());

        // Load URL
        webViewMainSite.loadUrl("https://tcds.or.tz/courses/?stylesheet&activated");

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
                    MyActivity.setTitle("Online Training");
            }
        });
    }

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }
}
