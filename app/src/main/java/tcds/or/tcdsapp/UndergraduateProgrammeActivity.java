package tcds.or.tcdsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import tcds.or.tcdsapp.mainapp.Getter_Undergraduate;
import tcds.or.tcdsapp.mainapp.MainActivity;
import tcds.or.tcdsapp.mainapp.RecyclerViewAdapter_Undergraduate;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UndergraduateProgrammeActivity extends AppCompatActivity {
    private static String URL_UNDERGRADUATE = "http://mbinitiative.com/impactpoolMobile/getProgrammes.php";
    private List<Getter_Undergraduate> customList_undergraduateprog;

    private RecyclerView recyclerView;
    private RecyclerViewAdapter_Undergraduate recyclerViewAdapter_undergraduateprog;
    SwipeRefreshLayout swipeRefreshLayout;
    private MyTask_Undergraduateprog taskUndergraduateprog;
    private java.net.URL url;
    private HttpURLConnection urlConnection;
    String receivedstate;
    Snackbar snack;
    ProgressBar progressBar;
    String search_sector;
    String search_region;
    String search_district;
    LinearLayout linearSearchDesign;
    ImageView imageViewCancel;
    String sector_data[];
    SearchableSpinner spinnerSector;
    SearchableSpinner spinnerRegion;
    SearchableSpinner spinnerDistrict;
    TextView textViewSearchResults, textViewClickToSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_undergraduate_programme);
        linearSearchDesign = findViewById(R.id.linearSearchDesign);
        imageViewCancel = findViewById(R.id.imageViewCancel);
        textViewSearchResults = findViewById(R.id.textViewSearchResults);
        textViewClickToSearch = findViewById(R.id.textViewClickToSearch);

        progressBar = (ProgressBar) findViewById(R.id.my_progressBar);
        progressBar.setVisibility(View.VISIBLE);

        new GetSectors().execute();
        new GetDistrict().execute();
        new GetRegion().execute();

        textViewClickToSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearSearchDesign.setVisibility(View.VISIBLE);
                imageViewCancel.setVisibility(View.VISIBLE);
            }
        });
        imageViewCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearSearchDesign.setVisibility(View.GONE);
                imageViewCancel.setVisibility(View.GONE);

            }
        });
        search_sector = "All";
        search_region = "All";
        search_district = "All";

        recyclerView = (RecyclerView) findViewById(R.id.recyclerListLearningInst);
        recyclerView.setHasFixedSize(true);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(UndergraduateProgrammeActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);

        receivedstate = Boolean.toString(haveNetworkConnection());
        if (receivedstate.equalsIgnoreCase("true")) {

            accessWebService_Undergraduateprog();
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setIndeterminate(true);
                    accessWebService_Undergraduateprog();
                    swipeRefreshLayout.setRefreshing(false);
                }
            });
        } else {
            progressBar.setIndeterminate(false);
            progressBar.setVisibility(View.GONE);
            snack = Snackbar.make(UndergraduateProgrammeActivity.this.findViewById(android.R.id.content), "No internet. Check Network Settings!", Snackbar.LENGTH_LONG).setAction("RETRY", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    snack.dismiss();
                    startActivity(new Intent(UndergraduateProgrammeActivity.this, MainActivity.class));
                }
            });
            View v = snack.getView();
            v.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            ((TextView) v.findViewById(R.id.snackbar_text)).setTextColor(Color.WHITE);
            ((TextView) v.findViewById(R.id.snackbar_action)).setTextColor(Color.WHITE);
            snack.setDuration(Snackbar.LENGTH_INDEFINITE);
            snack.show();
        }

        spinnerSector = findViewById(R.id.spinnerSector);
        spinnerRegion = findViewById(R.id.spinnerRegion);
        spinnerDistrict = findViewById(R.id.spinnerDistrict);
        spinnerSector.setTitle("Select Sector");
        spinnerRegion.setTitle("Select Region");
        spinnerDistrict.setTitle("Select District");

//        textviewSearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                textViewSearchResults.setVisibility(View.VISIBLE);
//                if (search_district.isEmpty()) {
//                    search_district = "All";
//                }
//                if (search_region.isEmpty()) {
//                    search_region = "All";
//                }
//                if (search_sector.isEmpty()) {
//                    search_sector = "All";
//                }
//
//                receivedstate = Boolean.toString(haveNetworkConnection());
//                if (receivedstate.equalsIgnoreCase("true")) {
//
//                    accessWebService_LearningInst_Category();
//                    swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//                        @Override
//                        public void onRefresh() {
//                            progressBar.setVisibility(View.VISIBLE);
//                            progressBar.setIndeterminate(true);
//                            accessWebService_LearningInst_Category();
//                            swipeRefreshLayout.setRefreshing(false);
//                        }
//                    });
//                } else {
//                    progressBar.setIndeterminate(false);
//                    progressBar.setVisibility(View.GONE);
//                    snack = Snackbar.make(UndergraduateProgrammeActivity.this.findViewById(android.R.id.content), "No internet. Check Network Settings!", Snackbar.LENGTH_LONG).setAction("RETRY", new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            snack.dismiss();
//                            startActivity(new Intent(UndergraduateProgrammeActivity.this, MainActivity.class));
//                        }
//                    });
//                    View myv = snack.getView();
//                    myv.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
//                    ((TextView) myv.findViewById(R.id.snackbar_text)).setTextColor(Color.WHITE);
//                    ((TextView) myv.findViewById(R.id.snackbar_action)).setTextColor(Color.WHITE);
//                    snack.setDuration(Snackbar.LENGTH_INDEFINITE);
//                    snack.show();
//                }
//            }
//        });


    }

    public void seachByCategory() {
        textViewSearchResults.setVisibility(View.VISIBLE);
        if (search_district.isEmpty()) {
            search_district = "All";
        }
        if (search_region.isEmpty()) {
            search_region = "All";
        }
        if (search_sector.isEmpty()) {
            search_sector = "All";
        }

        receivedstate = Boolean.toString(haveNetworkConnection());
        if (receivedstate.equalsIgnoreCase("true")) {

            accessWebService_LearningInst_Category();
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setIndeterminate(true);
                    accessWebService_LearningInst_Category();
                    swipeRefreshLayout.setRefreshing(false);
                }
            });
        } else {
            progressBar.setIndeterminate(false);
            progressBar.setVisibility(View.GONE);
            snack = Snackbar.make(UndergraduateProgrammeActivity.this.findViewById(android.R.id.content), "No internet. Check Network Settings!", Snackbar.LENGTH_LONG).setAction("RETRY", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    snack.dismiss();
                    startActivity(new Intent(UndergraduateProgrammeActivity.this, MainActivity.class));
                }
            });
            View myv = snack.getView();
            myv.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            ((TextView) myv.findViewById(R.id.snackbar_text)).setTextColor(Color.WHITE);
            ((TextView) myv.findViewById(R.id.snackbar_action)).setTextColor(Color.WHITE);
            snack.setDuration(Snackbar.LENGTH_INDEFINITE);
            snack.show();
        }
    }

    private class MyTask_Undergraduateprog extends AsyncTask<String, Void, List> {

        @Override
        protected List doInBackground(String... params) {
            try {
                url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();
                urlConnection.setConnectTimeout(5000);

                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                StringBuilder jsonResult = inputStreamToString(in);
                customList_undergraduateprog = new ArrayList<>();
                JSONObject jsonResponse = new JSONObject(jsonResult.toString());
                JSONArray jsonMainNode = jsonResponse.optJSONArray("programmes_jsondata");
                for (int i = 0; i < jsonMainNode.length(); i++) {
                    JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                    String id = jsonChildNode.optString("id");
                    String Programme = jsonChildNode.optString("Programme");
                    String Code = jsonChildNode.optString("Code");
                    String AdmReq = jsonChildNode.optString("AdmReq");
                    String MinInstAdmPoints = jsonChildNode.optString("MinInstAdmPoints");
                    String AdmCapacity = jsonChildNode.optString("AdmCapacity");
                    String ProgDuration = jsonChildNode.optString("ProgDuration");
                    String university = jsonChildNode.optString("university");
                    String region = jsonChildNode.optString("region");
                    String sector = jsonChildNode.optString("sector");

                    customList_undergraduateprog.add(new Getter_Undergraduate(id, Programme, Code, AdmReq, MinInstAdmPoints, AdmCapacity, ProgDuration, university, region, sector));
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return customList_undergraduateprog;
        }

        private StringBuilder inputStreamToString(InputStream is) {
            String rLine = "";
            StringBuilder answer = new StringBuilder();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            try {
                while ((rLine = rd.readLine()) != null) {
                    answer.append(rLine);
                }
            } catch (Exception e) {
                finish();
            }
            return answer;
        }

        @Override
        protected void onPostExecute(List list) {
            ListDrawer_Undergraduateprog(list);
            progressBar.setIndeterminate(false);
            progressBar.setVisibility(View.GONE);
        }
    }

    private void accessWebService_Undergraduateprog() {
        taskUndergraduateprog = new MyTask_Undergraduateprog();
        taskUndergraduateprog.execute(new String[]{URL_UNDERGRADUATE});
    }

    private void accessWebService_LearningInst_Category() {
        Random rand = new Random();
        int n = rand.nextInt(50000000) + 1;
        String randomstamp = "70timestamp" + n;
        URL_UNDERGRADUATE = "http://mbinitiative.com/impactpoolMobile/getundergraduate_bycategory.php?tmps=" + randomstamp + "&sector=" + search_sector + "&region=" + search_region + "&district=" + search_district;
        taskUndergraduateprog = new MyTask_Undergraduateprog();
        taskUndergraduateprog.execute(new String[]{URL_UNDERGRADUATE});
    }


    public void ListDrawer_Undergraduateprog(List<Getter_Undergraduate> customList) {
        textViewSearchResults.setText(" " + customList.size() + " Results Found");
        recyclerViewAdapter_undergraduateprog = new RecyclerViewAdapter_Undergraduate(customList, UndergraduateProgrammeActivity.this);
        recyclerViewAdapter_undergraduateprog.notifyDataSetChanged();
        recyclerView.setAdapter(recyclerViewAdapter_undergraduateprog);
    }

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.learningmenu, menu);
//        return super.onCreateOptionsMenu(menu);
//    }


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

                BufferedReader rd = new BufferedReader(new InputStreamReader(
                        inputStream), 4096);
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
            php_response_sector = "All#" + php_response_sector;
            sector_data = php_response_sector.split("#");

            ArrayAdapter<String> adapterSector = new ArrayAdapter<String>(UndergraduateProgrammeActivity.this, R.layout.row_spinner, R.id.textViewDealerName, sector_data);
            spinnerSector.setAdapter(adapterSector);
            spinnerSector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    search_sector = sector_data[position];
                    seachByCategory();
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    search_sector = "All";
                }
            });


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

            ArrayAdapter<String> adapterRegion = new ArrayAdapter<String>(UndergraduateProgrammeActivity.this, R.layout.row_spinner, R.id.textViewDealerName, region_data);
            spinnerRegion.setAdapter(adapterRegion);
            spinnerRegion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    search_region = region_data[position];
                    seachByCategory();

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    search_region = "All";

                }
            });


        }
    }

    String district_data[];
    String php_response_district;

    class GetDistrict extends AsyncTask<String, String, String> {
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
                php_response_district = sb.toString();
                inputStream.close();

            } catch (Exception e) {
                Toast.makeText(getApplicationContext(),
                        "Error inside set:" + e.toString(), Toast.LENGTH_LONG)
                        .show();
            }
            return php_response_district;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            php_response_district = "All#" + php_response_district;

            district_data = php_response_district.split("#");


            ArrayAdapter<String> adapterDistrict = new ArrayAdapter<String>(UndergraduateProgrammeActivity.this, R.layout.row_spinner, R.id.textViewDealerName, district_data);
            spinnerDistrict.setAdapter(adapterDistrict);
            spinnerDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    search_district = district_data[position];
                    seachByCategory();

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    search_district = "All";
                }
            });
        }
    }
}
