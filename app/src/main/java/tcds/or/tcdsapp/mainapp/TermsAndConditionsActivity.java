package tcds.or.tcdsapp.mainapp;

import androidx.appcompat.app.AppCompatActivity;
import tcds.or.tcdsapp.R;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class TermsAndConditionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_conditions);
        findViewById(R.id.textviewExpore).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://angazadirectory.com/Angaza%20Directory%20Privacy%20Policy.pdf")));

            }
        });
    }
}
