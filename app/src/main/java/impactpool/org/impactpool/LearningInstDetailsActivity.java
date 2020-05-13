package impactpool.org.impactpool;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class LearningInstDetailsActivity extends AppCompatActivity {
    String offeringInstitution, sector, district, region, details, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning_inst_details);
        Bundle bundle = getIntent().getExtras();
        offeringInstitution = bundle.getString("offeringInstitution");
        sector = bundle.getString("sector");
        district = bundle.getString("district");
        region = bundle.getString("region");
        details = bundle.getString("details");
        id = bundle.getString("id");
        TextView textViewDetails = findViewById(R.id.textViewDetails);
        textViewDetails.setText(
                offeringInstitution + "\n\n" +
                        "Sector:" + sector + "\n\n" +
                        "district:" + district + "\n\n" +
                        "region:" + region + "\n\n" +
                        "details:" + details + "\n\n"
        );
    }
}
