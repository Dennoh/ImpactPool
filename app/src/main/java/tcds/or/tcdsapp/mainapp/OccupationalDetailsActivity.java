package tcds.or.tcdsapp.mainapp;

import androidx.appcompat.app.AppCompatActivity;
import tcds.or.tcdsapp.R;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class OccupationalDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_occupational_details);

        Bundle bundle = getIntent().getExtras();
        String getMajor_occupation = bundle.getString("getMajor_occupation");
        String getSub_major_occupation = bundle.getString("getSub_major_occupation");
        String getMinor_occupation = bundle.getString("getMinor_occupation");
        String getUnit_label = bundle.getString("getUnit_label");
        String getDescriptionsofUnits = bundle.getString("getDescriptionsofUnits");

        TextView tvunit_label = findViewById(R.id.tvunit_label);
        TextView tvMajor = findViewById(R.id.tvMajor);
        TextView tvSubMajor = findViewById(R.id.tvSubMajor);
        TextView tvMinor = findViewById(R.id.tvMinor);
        TextView tvDetails = findViewById(R.id.tvDetails);
        tvMajor.setText("  " + getMajor_occupation);
        tvSubMajor.setText("  " + getSub_major_occupation);
        tvMinor.setText("  " + getMinor_occupation);
        tvunit_label.setText(getUnit_label);
        tvDetails.setText(getDescriptionsofUnits);


    }
}
