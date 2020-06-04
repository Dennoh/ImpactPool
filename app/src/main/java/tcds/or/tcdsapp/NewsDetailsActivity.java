package tcds.or.tcdsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NewsDetailsActivity extends AppCompatActivity {
    String tittle, details, posteddate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        Bundle bundle = getIntent().getExtras();
        tittle = bundle.getString("tittle");
        details = bundle.getString("details");
        posteddate = bundle.getString("postedon");

        TextView tvNewsTittle = findViewById(R.id.tvNewsTittle);
        TextView tvPostedOn = findViewById(R.id.tvPostedOn);
        TextView tvNewsDetails = findViewById(R.id.tvNewsDetails);
        tvNewsTittle.setText(tittle);
        tvNewsDetails.setText(details);

        SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date MyDate = null;
        try {
            MyDate = newDateFormat.parse(posteddate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        newDateFormat.applyPattern("EE d MMM yyyy");
        tvPostedOn.setText("  " + newDateFormat.format(MyDate));

    }
}
