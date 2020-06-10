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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
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
    String search_institution;
    LinearLayout linearSearchDesign;
    ImageView imageViewCancel;
    String sector_data[];
    MaterialSpinner spinner;


    MaterialSpinner spinnerSector;
    MaterialSpinner spinnerRegion;
    MaterialSpinner spinnerInstitution;

    TextView textViewSearchResults, textViewClickToSearch;
    ArrayAdapter<String> adapterInstitution;
    ArrayAdapter<String> adapterRegion;
    ArrayAdapter<String> adapterSector;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_undergraduate_programme);
        linearSearchDesign = findViewById(R.id.linearSearchDesign);
        imageViewCancel = findViewById(R.id.imageViewCancel);
        textViewSearchResults = findViewById(R.id.textViewSearchResults);
        textViewClickToSearch = findViewById(R.id.textViewClickToSearch);
        spinnerSector = findViewById(R.id.spinnerSector);
        spinnerRegion = findViewById(R.id.spinnerRegion);
        spinnerInstitution = findViewById(R.id.spinnerDistrict);

        progressBar = (ProgressBar) findViewById(R.id.my_progressBar);
        progressBar.setVisibility(View.VISIBLE);

        new GetSectors().execute();
        new GetInstitution().execute();
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
        search_institution = "All";


        spinnerSector.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
//                search_sector = sector_data[position];
                search_sector = item;
                new GetFilters().execute();
                seachByCategory();
            }
        });


        spinnerRegion.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                search_region = item;
                new GetFilters().execute();
                seachByCategory();
            }
        });

        spinnerInstitution.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                search_institution = item;
                new GetFilters().execute();
                seachByCategory();
            }
        });

//               spinnerRegion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                search_region = region_data[position];
//                Log.e("yeeeeeee34", search_sector + "");
//                Log.e("yeeeeeee34", search_region + "");
//                Log.e("yeeeeeee34", search_institution + "");
//                new GetFilters().execute();
//                seachByCategory();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
////                search_region = "All";
//
//            }
//        });
//
//
//        spinnerInstitution.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                search_institution = institution_data[position];
//                Log.e("yeeeeeee34", search_sector + "");
//                Log.e("yeeeeeee34", search_region + "");
//                Log.e("yeeeeeee34", search_institution + "");
//                new GetFilters().execute();
//                seachByCategory();
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
////                search_institution = "All";
//            }
//        });


        recyclerView = (RecyclerView) findViewById(R.id.recyclerListLearningInst);
        recyclerView.setHasFixedSize(true);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(UndergraduateProgrammeActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);

        receivedstate = Boolean.toString(haveNetworkConnection());
        if (receivedstate.equalsIgnoreCase("true")) {

            accessWebService_Undergraduateprog();
            textViewSearchResults.setVisibility(View.VISIBLE);

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


//        buttonSearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.e("yeeeeeee34", search_sector + "");
//                Log.e("yeeeeeee34", search_region + "");
//                Log.e("yeeeeeee34", search_institution + "");
//                new GetFilters().execute();
//                seachByCategory();
//            }
//        });

    }

    public void seachByCategory() {
        textViewSearchResults.setVisibility(View.VISIBLE);

        if (search_institution.isEmpty()) {
            search_institution = "All";
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
        taskUndergraduateprog.execute(new String[]{"http://mbinitiative.com/impactpoolMobile/getProgrammes.php"});
    }

    private void accessWebService_LearningInst_Category() {
        Random rand = new Random();
        int n = rand.nextInt(50000000) + 1;
        String randomstamp = "70timestamp" + n;
        URL_UNDERGRADUATE = "http://mbinitiative.com/impactpoolMobile/getundergraduate_bycategory.php?tmps=" + randomstamp + "&sector=" + search_sector + "&region=" + search_region + "&district=" + search_institution;
        taskUndergraduateprog = new MyTask_Undergraduateprog();
        taskUndergraduateprog.execute(new String[]{URL_UNDERGRADUATE});
    }


    public void ListDrawer_Undergraduateprog(List<Getter_Undergraduate> customList) {
        textViewSearchResults.setText(" " + customList.size() + " Results Found");
        recyclerViewAdapter_undergraduateprog = new RecyclerViewAdapter_Undergraduate(customList, UndergraduateProgrammeActivity.this);
        recyclerViewAdapter_undergraduateprog.notifyDataSetChanged();
        recyclerView.setAdapter(recyclerViewAdapter_undergraduateprog);
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
            //adapterSector = new ArrayAdapter<String>(UndergraduateProgrammeActivity.this, R.layout.row_spinner, R.id.textViewDealerName, sector_data);
            spinnerSector.setItems(sector_data);


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
//            adapterRegion = new ArrayAdapter<String>(UndergraduateProgrammeActivity.this, R.layout.row_spinner, R.id.textViewDealerName, region_data);
            spinnerRegion.setItems(region_data);
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
//            adapterInstitution = new ArrayAdapter<String>(UndergraduateProgrammeActivity.this, R.layout.row_spinner, R.id.textViewDealerName, institution_data);
            spinnerInstitution.setItems(institution_data);

        }
    }


    public void changeFilterByCategory() {
        textViewSearchResults.setVisibility(View.VISIBLE);
        if (search_institution.isEmpty()) {
            search_institution = "All";
        }
        if (search_region.isEmpty()) {
            search_region = "All";
        }
        if (search_sector.isEmpty()) {
            search_sector = "All";
        }

        receivedstate = Boolean.toString(haveNetworkConnection());
        if (receivedstate.equalsIgnoreCase("true")) {


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
                Toast.makeText(UndergraduateProgrammeActivity.this, "Try Again", Toast.LENGTH_LONG).show();
            }

            return filter_results;


        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e("yeeeeeee34", "filter_results " + filter_results);

            dataz = filter_results.split("@");

            php_response_sector = dataz[0];
            Log.e("gegege34", php_response_sector);
            sector_data = php_response_sector.split("#");
            adapterSector = new ArrayAdapter<String>(UndergraduateProgrammeActivity.this, R.layout.row_spinner, R.id.textViewDealerName, sector_data);
            spinnerSector.setAdapter(adapterSector);

            php_response_region = dataz[1];
            Log.e("gegege34", php_response_region);
            region_data = php_response_region.split("#");
            adapterRegion = new ArrayAdapter<String>(UndergraduateProgrammeActivity.this, R.layout.row_spinner, R.id.textViewDealerName, region_data);
            spinnerRegion.setAdapter(adapterRegion);

            php_response_institution = dataz[2];
            Log.e("gegege34", php_response_institution);
            institution_data = php_response_institution.split("#");
            adapterInstitution = new ArrayAdapter<String>(UndergraduateProgrammeActivity.this, R.layout.row_spinner, R.id.textViewDealerName, institution_data);
            spinnerInstitution.setAdapter(adapterInstitution);

        }
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
//        getMenuInflater().inflate(R.menu.search_menu, menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.menuClearSearch:
//
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }

}
