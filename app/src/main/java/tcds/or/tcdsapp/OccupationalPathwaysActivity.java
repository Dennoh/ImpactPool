package tcds.or.tcdsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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

public class OccupationalPathwaysActivity extends AppCompatActivity {
    LinearLayout linearSearchDesign;
    ImageView imageViewCancel;
    String majoroccupation_data[];
    SearchableSpinner spinnerMajorOccupation;
    SearchableSpinner spinnerSubMajorOccupation;
    SearchableSpinner spinnerMinnorOccupation;
    TextView textViewSearchResults, textViewClickToSearch;
    String search_MajorOccupation;
    String search_SubMajorOccupation;
    String search_MinnorOccupation;


    private static String URL_ECONOMICSECTORS = "http://mbinitiative.com/impactpoolMobile/getOccupationPathways.php";
    private List<Getter_OccupationalPathways> customList_EconomicSectors;

    private RecyclerView recyclerView;
    private RecyclerViewAdapter_Occupational recyclerViewAdapter_EconomicSectors;
    SwipeRefreshLayout swipeRefreshLayout;
    private MyTask_EconomicSectors taskEconomicSectors;
    private java.net.URL url;
    private HttpURLConnection urlConnection;
    String receivedstate;
    Snackbar snack;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_occupational_pathways);

        linearSearchDesign = findViewById(R.id.linearSearchDesign);
        imageViewCancel = findViewById(R.id.imageViewCancel);
        textViewSearchResults = findViewById(R.id.textViewSearchResults);
        textViewClickToSearch = findViewById(R.id.textViewClickToSearch);
//        TextView textviewSearch = findViewById(R.id.textviewSearch);
        spinnerMajorOccupation = findViewById(R.id.spinnerSector);
        spinnerSubMajorOccupation = findViewById(R.id.spinnerRegion);
        spinnerMinnorOccupation = findViewById(R.id.spinnerDistrict);
        spinnerMajorOccupation.setTitle("Main Sector");
        spinnerSubMajorOccupation.setTitle("Sub Main Sectors");
        spinnerMinnorOccupation.setTitle("Sub Sectors");

        progressBar = (ProgressBar) findViewById(R.id.my_progressBar);
        progressBar.setVisibility(View.VISIBLE);

        new GetMajorOccupation().execute();

        search_MajorOccupation = "All";
        search_SubMajorOccupation = "All";
        search_MinnorOccupation = "All";

        recyclerView = (RecyclerView) findViewById(R.id.recyclerListLearningInst);
        recyclerView.setHasFixedSize(true);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(OccupationalPathwaysActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);

        receivedstate = Boolean.toString(haveNetworkConnection());
        if (receivedstate.equalsIgnoreCase("true")) {
            accessWebService_EconomicSectors();
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setIndeterminate(true);
                    accessWebService_EconomicSectors();
                    swipeRefreshLayout.setRefreshing(false);
                }
            });
        } else {
            progressBar.setIndeterminate(false);
            progressBar.setVisibility(View.GONE);
            snack = Snackbar.make(OccupationalPathwaysActivity.this.findViewById(android.R.id.content), "No internet. Check Network Settings!", Snackbar.LENGTH_LONG).setAction("RETRY", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    snack.dismiss();
                    startActivity(new Intent(OccupationalPathwaysActivity.this, MainActivity.class));
                }
            });
            View v = snack.getView();
            v.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            ((TextView) v.findViewById(R.id.snackbar_text)).setTextColor(Color.WHITE);
            ((TextView) v.findViewById(R.id.snackbar_action)).setTextColor(Color.WHITE);
            snack.setDuration(Snackbar.LENGTH_INDEFINITE);
            snack.show();
        }


//        textviewSearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                textViewSearchResults.setVisibility(View.VISIBLE);
//                if (search_MajorOccupation.isEmpty()) {
//                    search_MajorOccupation = "All";
//                }
//                if (search_SubMajorOccupation.isEmpty()) {
//                    search_SubMajorOccupation = "All";
//                }
//                if (search_MinnorOccupation.isEmpty()) {
//                    search_MinnorOccupation = "All";
//                }
//
//                if (haveNetworkConnection()) {
//
//                    accessWebService_EconomicSectors_Category();
//                    swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//                        @Override
//                        public void onRefresh() {
//                            progressBar.setVisibility(View.VISIBLE);
//                            progressBar.setIndeterminate(true);
//                            accessWebService_EconomicSectors_Category();
//                            swipeRefreshLayout.setRefreshing(false);
//                        }
//                    });
//                } else {
//                    progressBar.setIndeterminate(false);
//                    progressBar.setVisibility(View.GONE);
//                    snack = Snackbar.make(OccupationalPathwaysActivity.this.findViewById(android.R.id.content), "No internet. Check Network Settings!", Snackbar.LENGTH_LONG).setAction("RETRY", new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            snack.dismiss();
//                            startActivity(new Intent(OccupationalPathwaysActivity.this, MainActivity.class));
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
        if (search_MajorOccupation.isEmpty()) {
            search_MajorOccupation = "All";
        }
        if (search_SubMajorOccupation.isEmpty()) {
            search_SubMajorOccupation = "All";
        }
        if (search_MinnorOccupation.isEmpty()) {
            search_MinnorOccupation = "All";
        }

        if (haveNetworkConnection()) {

            accessWebService_EconomicSectors_Category();
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setIndeterminate(true);
                    accessWebService_EconomicSectors_Category();
                    swipeRefreshLayout.setRefreshing(false);
                }
            });
        } else {
            progressBar.setIndeterminate(false);
            progressBar.setVisibility(View.GONE);
            snack = Snackbar.make(OccupationalPathwaysActivity.this.findViewById(android.R.id.content), "No internet. Check Network Settings!", Snackbar.LENGTH_LONG).setAction("RETRY", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    snack.dismiss();
                    startActivity(new Intent(OccupationalPathwaysActivity.this, MainActivity.class));
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


    private class MyTask_EconomicSectors extends AsyncTask<String, Void, List> {

        @Override
        protected List doInBackground(String... params) {
            try {
                url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();
                urlConnection.setConnectTimeout(5000);

                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                StringBuilder jsonResult = inputStreamToString(in);
                customList_EconomicSectors = new ArrayList<>();
                JSONObject jsonResponse = new JSONObject(jsonResult.toString());
                JSONArray jsonMainNode = jsonResponse.optJSONArray("occupationalpathways_jsondata");
                for (int i = 0; i < jsonMainNode.length(); i++) {
                    JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                    String id = jsonChildNode.optString("id");
                    String major_occupation = jsonChildNode.optString("major_occupation");
                    String sub_major_occupation = jsonChildNode.optString("sub_major_occupation");
                    String minor_occupation = jsonChildNode.optString("minor_occupation");
                    String unit_label = jsonChildNode.optString("unit_label");
                    String DescriptionsofUnits = jsonChildNode.optString("DescriptionsofUnits");
                    Log.e("here2323", DescriptionsofUnits + "");

                    customList_EconomicSectors.add(new Getter_OccupationalPathways(id, major_occupation, sub_major_occupation, minor_occupation, unit_label, DescriptionsofUnits));
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return customList_EconomicSectors;
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

    private void accessWebService_EconomicSectors() {
        taskEconomicSectors = new MyTask_EconomicSectors();
        taskEconomicSectors.execute(new String[]{URL_ECONOMICSECTORS});
    }

    private void accessWebService_EconomicSectors_Category() {
        Random rand = new Random();
        int n = rand.nextInt(50000000) + 1;
        String randomstamp = "70timestamp" + n;
        URL_ECONOMICSECTORS = "http://mbinitiative.com/impactpoolMobile/getOccupational_bycategory.php?tmps=" + randomstamp + "&majorOccupation=" + search_MajorOccupation + "&subMajorOccupation=" + search_SubMajorOccupation + "&minnorOccupation=" + search_MinnorOccupation;
        Log.e("looooo2", URL_ECONOMICSECTORS + "");
        taskEconomicSectors = new MyTask_EconomicSectors();
        taskEconomicSectors.execute(new String[]{URL_ECONOMICSECTORS});
    }


    public void ListDrawer_Undergraduateprog(List<Getter_OccupationalPathways> customList) {
        textViewSearchResults.setText(" " + customList.size() + " Results Found");
        recyclerViewAdapter_EconomicSectors = new RecyclerViewAdapter_Occupational(customList, OccupationalPathwaysActivity.this);
        recyclerViewAdapter_EconomicSectors.notifyDataSetChanged();
        recyclerView.setAdapter(recyclerViewAdapter_EconomicSectors);
    }


    String php_response_majoroccupation;

    class GetMajorOccupation extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {

            try {
                DefaultHttpClient httpclient = new DefaultHttpClient();

                HttpPost httppost = new HttpPost("http://mbinitiative.com/impactpoolMobile/getmajorOccupation.php");
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
                php_response_majoroccupation = sb.toString();

                inputStream.close();

            } catch (Exception e) {
                Toast.makeText(getApplicationContext(),
                        "Error inside set:" + e.toString(), Toast.LENGTH_LONG)
                        .show();
            }

            return php_response_majoroccupation;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e("dataa12", php_response_majoroccupation + "");
            php_response_majoroccupation = "All#" + php_response_majoroccupation;
            majoroccupation_data = php_response_majoroccupation.split("#");


            ArrayAdapter<String> adapterMajorOccupation = new ArrayAdapter<String>(OccupationalPathwaysActivity.this, R.layout.row_spinner, R.id.textViewDealerName, majoroccupation_data);
            spinnerMajorOccupation.setAdapter(adapterMajorOccupation);
            spinnerMajorOccupation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    search_MajorOccupation = majoroccupation_data[position];
                    seachByCategory();
                    new GetSubMajorOccupation().execute();

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    search_MajorOccupation = "All";
                }
            });


        }
    }

    String subMajorOccupation_data[];
    String php_response_subMajorOccupation;

    class GetSubMajorOccupation extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {

            try {
                DefaultHttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://mbinitiative.com/impactpoolMobile/getsubMajorOccupation.php");
                // Execute HTTP Post Request
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
                nameValuePairs.add(new BasicNameValuePair("majorOccupation", search_MajorOccupation));
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
                php_response_subMajorOccupation = sb.toString();

                inputStream.close();

            } catch (Exception e) {
                Toast.makeText(getApplicationContext(),
                        "Error inside set:" + e.toString(), Toast.LENGTH_LONG)
                        .show();
            }
            return php_response_subMajorOccupation;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            php_response_subMajorOccupation = "All#" + php_response_subMajorOccupation;

            subMajorOccupation_data = php_response_subMajorOccupation.split("#");

            ArrayAdapter<String> adapterSubMajorOccupation = new ArrayAdapter<String>(OccupationalPathwaysActivity.this, R.layout.row_spinner, R.id.textViewDealerName, subMajorOccupation_data);
            spinnerSubMajorOccupation.setAdapter(adapterSubMajorOccupation);
            spinnerSubMajorOccupation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    search_SubMajorOccupation = subMajorOccupation_data[position];
                    seachByCategory();
                    new GetMinnorOccupation().execute();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    search_SubMajorOccupation = "All";
                }
            });


        }
    }

    String minnorOccupation_data[];
    String php_response_MinnorOccupation;

    class GetMinnorOccupation extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            try {
                DefaultHttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://mbinitiative.com/impactpoolMobile/getminorOccupation.php");
                // Execute HTTP Post Request
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
                nameValuePairs.add(new BasicNameValuePair("SubMajorOccupation", search_SubMajorOccupation));
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
                php_response_MinnorOccupation = sb.toString();
                inputStream.close();

            } catch (Exception e) {
                Toast.makeText(getApplicationContext(),
                        "Error inside set:" + e.toString(), Toast.LENGTH_LONG)
                        .show();
            }
            return php_response_MinnorOccupation;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            php_response_MinnorOccupation = "All#" + php_response_MinnorOccupation;

            minnorOccupation_data = php_response_MinnorOccupation.split("#");

            ArrayAdapter<String> adapterMinnorOccupation = new ArrayAdapter<String>(OccupationalPathwaysActivity.this, R.layout.row_spinner, R.id.textViewDealerName, minnorOccupation_data);
            spinnerMinnorOccupation.setAdapter(adapterMinnorOccupation);
            spinnerMinnorOccupation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    seachByCategory();
                    search_MinnorOccupation = minnorOccupation_data[position];
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    search_MinnorOccupation = "All";
                }
            });
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
}
