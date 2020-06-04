package tcds.or.tcdsapp.mainapp;

import androidx.appcompat.app.AppCompatActivity;
import tcds.or.tcdsapp.R;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class ConnectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
    }

    public void tvclicked(View view) {
        switch (view.getId()) {
            case R.id.textViewWebsite:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://tcds.or.tz/#")));
                break;
            case R.id.textViewMobile:
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", "+255752581521", null));
                startActivity(intent);
                break;
            case R.id.textViewTwitter:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/")));
                break;
            case R.id.textViewFacebook:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://facebook.com/")));
                break;
            case R.id.textViewInstagram:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://instagram.com/")));
                break;
        }
    }
}
