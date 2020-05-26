package tcds.or.tcdsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class UnderGraduateDetailsActivity extends AppCompatActivity {
    String Programme, Code, AdmReq, MinInstAdmPoints, AdmCapacity, ProgDuration, university, region;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_under_graduate_details);

        Bundle bundle = getIntent().getExtras();
        Programme = bundle.getString("Programme");
        Code = bundle.getString("Code");
        AdmReq = bundle.getString("AdmReq");
        MinInstAdmPoints = bundle.getString("MinInstAdmPoints");
        AdmCapacity = bundle.getString("AdmCapacity");
        ProgDuration = bundle.getString("ProgDuration");
        university = bundle.getString("university");
        region = bundle.getString("region");

        TextView tvProgramme = findViewById(R.id.tvProgramme);
        TextView tvuniversity = findViewById(R.id.tvuniversity);
        TextView tvMinInstAdmPoints = findViewById(R.id.tvMinInstAdmPoints);
        TextView tvAdmCapacity = findViewById(R.id.tvAdmCapacity);
        TextView tvProgDuration = findViewById(R.id.tvProgDuration);
        TextView tvregion = findViewById(R.id.tvregion);
        TextView textViewAdmReq = findViewById(R.id.textViewAdmReq);

        tvProgramme.setText("  " + Programme);
        tvuniversity.setText("  " + university);
        tvMinInstAdmPoints.setText("  Min Admission Points: " + MinInstAdmPoints);
        tvAdmCapacity.setText("  Admission Capacity: " + AdmCapacity);
        tvProgDuration.setText("  Program Duration: " + ProgDuration);
        tvregion.setText("  " + region);
        textViewAdmReq.setText(AdmReq + "");

    }
}
