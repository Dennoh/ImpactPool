package tcds.or.tcdsapp.mainapp;

import androidx.appcompat.app.AppCompatActivity;
import tcds.or.tcdsapp.R;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class PrivacyPolicyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);
        findViewById(R.id.textviewExpore).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://mbinitiative.com/impactpoolMobile/privacypolicy.pdf")));
            }
        });
    }
}
