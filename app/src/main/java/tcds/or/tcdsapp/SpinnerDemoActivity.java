package tcds.or.tcdsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class SpinnerDemoActivity extends AppCompatActivity {
    String sector_data[];
    SearchableSpinner spinnerSector;
    SearchableSpinner spinnerRegion;
    SearchableSpinner spinnerInstitution;
    TextView textViewSearchResults, textViewClickToSearch;
    ArrayAdapter<String> adapterInstitution;
    ArrayAdapter<String> adapterRegion;
    ArrayAdapter<String> adapterSector;
    String search_sector, search_region, search_institution;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner_demo);
        Button buttonSearch = findViewById(R.id.buttonSearch);

        spinnerSector = findViewById(R.id.spinnerSector);
        spinnerRegion = findViewById(R.id.spinnerRegion);
        spinnerInstitution = findViewById(R.id.spinnerDistrict);
        spinnerSector.setTitle("Select Sector");
        spinnerRegion.setTitle("Select Region");
        spinnerInstitution.setTitle("Select District");

        new GetSectors().execute();
        new GetInstitution().execute();
        new GetRegion().execute();


        spinnerSector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                search_sector = sector_data[position];
                // new GetFilters().execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                search_sector = "All";

            }
        });
        spinnerRegion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                search_region = region_data[position];
                // new GetFilters().execute();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                search_region = "All";

            }
        });
        spinnerInstitution.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                search_institution = institution_data[position];
                //   new GetFilters().execute();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                search_institution = "All";

            }
        });


        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("yeeeeeee34", search_sector + "");
                Log.e("yeeeeeee34", search_region + "");
                Log.e("yeeeeeee34", search_institution + "");
                new GetFilters().execute();
            }
        });
    }


    String php_response_sector;

    class GetSectors extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {

            try {
                DefaultHttpClient httpclient = new DefaultHttpClient();

                HttpPost httppost = new HttpPost("http://mbinitiative.com/impactpoolMobile/getsector_under.php");
                // Execute HTTP Post Request
                HttpResponse response = httpclient.execute(httppost);
                InputStream inputStream = response.getEntity().getContent();

                BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream), 4096);
                String line;
                StringBuilder sb = new StringBuilder();

                while ((line = rd.readLine()) != null) {
                    sb.append(line);
                }
                rd.close();
                php_response_sector = sb.toString();

                inputStream.close();

            } catch (Exception e) {
                Toast.makeText(getApplicationContext(),
                        "Error inside set:" + e.toString(), Toast.LENGTH_LONG)
                        .show();
            }

            return php_response_sector;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e("pooo9898", "here");
            php_response_sector = "All#" + php_response_sector;
            sector_data = php_response_sector.split("#");
            adapterSector = new ArrayAdapter<String>(SpinnerDemoActivity.this, R.layout.row_spinner, R.id.textViewDealerName, sector_data);
            spinnerSector.setAdapter(adapterSector);

        }
    }

    String region_data[];
    String php_response_region;

    class GetRegion extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {

            try {
                DefaultHttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://mbinitiative.com/impactpoolMobile/getregion_under.php");
                // Execute HTTP Post Request
                HttpResponse response = httpclient.execute(httppost);
                InputStream inputStream = response.getEntity().getContent();
                BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream), 4096);
                String line;
                StringBuilder sb = new StringBuilder();

                while ((line = rd.readLine()) != null) {
                    sb.append(line);
                }
                rd.close();
                php_response_region = sb.toString();

                inputStream.close();

            } catch (Exception e) {
                Toast.makeText(getApplicationContext(),
                        "Error inside set:" + e.toString(), Toast.LENGTH_LONG)
                        .show();
            }
            return php_response_region;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            php_response_region = "All#" + php_response_region;
            region_data = php_response_region.split("#");
            adapterRegion = new ArrayAdapter<String>(SpinnerDemoActivity.this, R.layout.row_spinner, R.id.textViewDealerName, region_data);
            spinnerRegion.setAdapter(adapterRegion);


        }
    }

    String institution_data[];
    String php_response_institution;

    class GetInstitution extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            try {
                DefaultHttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://mbinitiative.com/impactpoolMobile/getinstitution_under.php");
                // Execute HTTP Post Request
                HttpResponse response = httpclient.execute(httppost);
                InputStream inputStream = response.getEntity().getContent();
                BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream), 4096);
                String line;
                StringBuilder sb = new StringBuilder();

                while ((line = rd.readLine()) != null) {
                    sb.append(line);
                }
                rd.close();
                php_response_institution = sb.toString();
                inputStream.close();

            } catch (Exception e) {
                Toast.makeText(getApplicationContext(),
                        "Error inside set:" + e.toString(), Toast.LENGTH_LONG)
                        .show();
            }
            return php_response_institution;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            php_response_institution = "All#" + php_response_institution;
            institution_data = php_response_institution.split("#");
            adapterInstitution = new ArrayAdapter<String>(SpinnerDemoActivity.this, R.layout.row_spinner, R.id.textViewDealerName, institution_data);
            spinnerInstitution.setAdapter(adapterInstitution);

        }
    }


    String filter_results;
    String[] dataz;

    class GetFilters extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            try {

                /* seting up the connection and send data with url */
                // create a http default client - initialize the HTTp client

                DefaultHttpClient httpclient = new DefaultHttpClient();

                HttpPost httppost = new HttpPost("http://mbinitiative.com/impactpoolMobile/getfilters.php");

                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
                nameValuePairs.add(new BasicNameValuePair("sector", search_sector));
                nameValuePairs.add(new BasicNameValuePair("region", search_region));
                nameValuePairs.add(new BasicNameValuePair("inst", search_institution));

                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);

                InputStream inputStream = response.getEntity().getContent();

                BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream), 4096);
                String line;
                StringBuilder sb = new StringBuilder();
                while ((line = rd.readLine()) != null) {
                    sb.append(line);
                }
                rd.close();
                filter_results = sb.toString();
                inputStream.close();

            } catch (Exception e) {
                Toast.makeText(SpinnerDemoActivity.this, "Try Again", Toast.LENGTH_LONG).show();
            }

            return filter_results;


        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dataz = filter_results.split("@");
            Log.e("gegege34", filter_results);

            php_response_sector = dataz[0];
            Log.e("gegege34", php_response_sector);
            sector_data = php_response_sector.split("#");
            adapterSector = new ArrayAdapter<String>(SpinnerDemoActivity.this, R.layout.row_spinner, R.id.textViewDealerName, sector_data);
            spinnerSector.setAdapter(adapterSector);

            php_response_region = dataz[1];
            Log.e("gegege34", php_response_region);
            region_data = php_response_region.split("#");
            adapterRegion = new ArrayAdapter<String>(SpinnerDemoActivity.this, R.layout.row_spinner, R.id.textViewDealerName, region_data);
            spinnerRegion.setAdapter(adapterRegion);

            php_response_institution = dataz[2];
            Log.e("gegege34", php_response_institution);
            institution_data = php_response_institution.split("#");
            adapterInstitution = new ArrayAdapter<String>(SpinnerDemoActivity.this, R.layout.row_spinner, R.id.textViewDealerName, institution_data);
            spinnerInstitution.setAdapter(adapterInstitution);


        }
    }


}
