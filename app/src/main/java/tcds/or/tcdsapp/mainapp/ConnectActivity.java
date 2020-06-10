package tcds.or.tcdsapp.mainapp;

import androidx.appcompat.app.AppCompatActivity;
import tcds.or.tcdsapp.R;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", "+255758002017", null));
                startActivity(intent);
                break;
            case R.id.textViewTwitter:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/CareerTanzanite?s=08")));
                break;
            case R.id.textViewFacebook:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://web.facebook.com/Tanzanite-Career-Development-Services-101624108259902/?_rdc=1&_rdr")));
                break;
            case R.id.textViewInstagram:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/p/CBFN3bZhNtr/?igshid=8zrl37wgiczo")));
                break;
            case R.id.textViewWhatsApp:
                String contact = "+255788123023"; // use country code with your phone number
                String url = "https://api.whatsapp.com/send?phone=" + contact;
                try {
                    PackageManager pm = ConnectActivity.this.getPackageManager();
                    pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                } catch (PackageManager.NameNotFoundException e) {
                    Toast.makeText(ConnectActivity.this, "Whatsapp app not installed in your phone", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                break;
        }
    }
}
