package tcds.or.tcdsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
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
        TextView textViewTittle = findViewById(R.id.textViewTittle);
        TextView textViewSector = findViewById(R.id.textViewSector);
        TextView textViewDistrict = findViewById(R.id.textViewDistrict);
        TextView textViewRegion = findViewById(R.id.textViewRegion);
        TextView textviewExpore = findViewById(R.id.textviewExpore);
        textViewTittle.setText(offeringInstitution + "");
        textViewSector.setText("    "+sector + "");
        textViewDistrict.setText("    "+district + "");
        textViewRegion.setText("    "+region + "");
        textViewDetails.setText(details + "");
        textviewExpore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://tcds.or.tz/")));

            }
        });


    }
}
