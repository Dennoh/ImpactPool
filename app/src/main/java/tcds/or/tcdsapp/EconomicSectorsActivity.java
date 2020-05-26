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

public class EconomicSectorsActivity extends AppCompatActivity {
    LinearLayout linearSearchDesign;
    ImageView imageViewCancel;
    String mainsector_data[];
    SearchableSpinner spinnerMainSector;
    SearchableSpinner spinnerSubMainSector;
    SearchableSpinner spinnerSubSector;
    TextView textViewSearchResults, textViewClickToSearch, textViewCount;
    String search_Mainsector;
    String search_SubMainSector;
    String search_SubSector;


    private static String URL_ECONOMICSECTORS = "http://mbinitiative.com/impactpoolMobile/getEconimicsectors.php";
    private List<Getter_EconomicSectors> customList_EconomicSectors;

    private RecyclerView recyclerView;
    private RecyclerViewAdapter_EconomicSectors recyclerViewAdapter_EconomicSectors;
    SwipeRefreshLayout swipeRefreshLayout;
    private MyTask_EconomicSectors taskEconomicSectors;
    private java.net.URL url;
    private HttpURLConnection urlConnection;
    String receivedstate;
    Snackbar snack;
    ProgressBar progressBar;
    String search_activityGroup;
    String searched_activityClass;
    String activitygroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_economic_sectors);

        linearSearchDesign = findViewById(R.id.linearSearchDesign);
        imageViewCancel = findViewById(R.id.imageViewCancel);
        textViewSearchResults = findViewById(R.id.textViewSearchResults);
        textViewClickToSearch = findViewById(R.id.textViewClickToSearch);
//        TextView textviewSearch = findViewById(R.id.textviewSearch);
        textViewCount = findViewById(R.id.textViewCount);
        spinnerMainSector = findViewById(R.id.spinnerSector);
        spinnerSubMainSector = findViewById(R.id.spinnerRegion);
        spinnerSubSector = findViewById(R.id.spinnerDistrict);
        spinnerMainSector.setTitle("Main Sector");
        spinnerSubMainSector.setTitle("Sub Main Sectors");
        spinnerSubSector.setTitle("Sub Sectors");

        progressBar = (ProgressBar) findViewById(R.id.my_progressBar);
        progressBar.setVisibility(View.VISIBLE);

        new GetMainSectors().execute();

        search_Mainsector = "All";
        search_SubMainSector = "All";
        search_SubSector = "All";

        recyclerView = (RecyclerView) findViewById(R.id.recyclerListLearningInst);
        recyclerView.setHasFixedSize(true);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(EconomicSectorsActivity.this);
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
            snack = Snackbar.make(EconomicSectorsActivity.this.findViewById(android.R.id.content), "No internet. Check Network Settings!", Snackbar.LENGTH_LONG).setAction("RETRY", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    snack.dismiss();
                    startActivity(new Intent(EconomicSectorsActivity.this, MainActivity.class));
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
//                if (search_Mainsector.isEmpty()) {
//                    search_Mainsector = "All";
//                }
//                if (search_SubMainSector.isEmpty()) {
//                    search_SubMainSector = "All";
//                }
//                if (search_SubSector.isEmpty()) {
//                    search_SubSector = "All";
//                }
//
//                receivedstate = Boolean.toString(haveNetworkConnection());
//                if (receivedstate.equalsIgnoreCase("true")) {
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
//                    snack = Snackbar.make(EconomicSectorsActivity.this.findViewById(android.R.id.content), "No internet. Check Network Settings!", Snackbar.LENGTH_LONG).setAction("RETRY", new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            snack.dismiss();
//                            startActivity(new Intent(EconomicSectorsActivity.this, MainActivity.class));
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
        if (search_Mainsector.isEmpty()) {
            search_Mainsector = "All";
        }
        if (search_SubMainSector.isEmpty()) {
            search_SubMainSector = "All";
        }
        if (search_SubSector.isEmpty()) {
            search_SubSector = "All";
        }

        receivedstate = Boolean.toString(haveNetworkConnection());
        if (receivedstate.equalsIgnoreCase("true")) {

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
            snack = Snackbar.make(EconomicSectorsActivity.this.findViewById(android.R.id.content), "No internet. Check Network Settings!", Snackbar.LENGTH_LONG).setAction("RETRY", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    snack.dismiss();
                    startActivity(new Intent(EconomicSectorsActivity.this, MainActivity.class));
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




        int tzz = 0;
    JSONArray jsonMainNode;

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
                jsonMainNode = jsonResponse.optJSONArray("economicsector_jsondata");
//                for (int i = 0; i < jsonMainNode.length(); i++) {
//                    JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
//                    String id = jsonChildNode.optString("id");
//                    String mainsector = jsonChildNode.optString("mainsector");
//                    String submainsector = jsonChildNode.optString("submainsector");
//                    String subsector = jsonChildNode.optString("subsector");
//                    activitygroup = jsonChildNode.optString("activitygroup");
//                    String activityclass = jsonChildNode.optString("activityclass");
//                    Log.e("hereee4587", activitygroup + " " + i);
//
//                    new GetActivityGroupDetails().execute();
//                    customList_EconomicSectors.add(new Getter_EconomicSectors(id, mainsector, submainsector, subsector, activitygroup, searched_activityClass));
//                }

                if (jsonMainNode.length() != 0) {
                    getReport(tzz);
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

        }
    }

    String id, mainsector, submainsector, subsector, activityclass;


    public void getReport(int number) {
        if (number < jsonMainNode.length()) {
            JSONObject jsonChildNode = null;
            try {
                jsonChildNode = jsonMainNode.getJSONObject(number);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            id = jsonChildNode.optString("id");
            mainsector = jsonChildNode.optString("mainsector");
            submainsector = jsonChildNode.optString("submainsector");
            subsector = jsonChildNode.optString("subsector");
            activitygroup = jsonChildNode.optString("activitygroup");
            activityclass = jsonChildNode.optString("activityclass");

            new GetActivityGroupDetails().execute();

        } else {
            textViewCount.setVisibility(View.GONE);
            ListDrawer_Undergraduateprog(customList_EconomicSectors);
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
        URL_ECONOMICSECTORS = "http://mbinitiative.com/impactpoolMobile/getEconomicSector_bycategory.php?tmps=" + randomstamp + "&mainsector=" + search_Mainsector + "&subMainSector=" + search_SubMainSector + "&subSector=" + search_SubSector;
        taskEconomicSectors = new MyTask_EconomicSectors();
        taskEconomicSectors.execute(new String[]{URL_ECONOMICSECTORS});
    }

    public void ListDrawer_Undergraduateprog(List<Getter_EconomicSectors> customList) {
        textViewSearchResults.setText(" " + customList.size() + " Results Found");
        recyclerViewAdapter_EconomicSectors = new RecyclerViewAdapter_EconomicSectors(customList, EconomicSectorsActivity.this);
        recyclerViewAdapter_EconomicSectors.notifyDataSetChanged();
        recyclerView.setAdapter(recyclerViewAdapter_EconomicSectors);
    }

    String php_response_mainsector;

    class GetMainSectors extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {

            try {
                DefaultHttpClient httpclient = new DefaultHttpClient();

                HttpPost httppost = new HttpPost("http://mbinitiative.com/impactpoolMobile/getMainSector.php");
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
                php_response_mainsector = sb.toString();

                inputStream.close();

            } catch (Exception e) {
                Toast.makeText(getApplicationContext(),
                        "Error inside set:" + e.toString(), Toast.LENGTH_LONG)
                        .show();
            }

            return php_response_mainsector;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e("dataa12", php_response_mainsector + "");
            php_response_mainsector = "All#" + php_response_mainsector;
            mainsector_data = php_response_mainsector.split("#");


            ArrayAdapter<String> adapterSector = new ArrayAdapter<String>(EconomicSectorsActivity.this, R.layout.row_spinner, R.id.textViewDealerName, mainsector_data);
            spinnerMainSector.setAdapter(adapterSector);
            spinnerMainSector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    search_Mainsector = mainsector_data[position];
                    seachByCategory();
                    new GetSubMainSector().execute();

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    search_Mainsector = "All";
                }
            });


        }
    }

    String subMainsector_data[];
    String php_response_subMainSector;

    class GetSubMainSector extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {

            try {
                DefaultHttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://mbinitiative.com/impactpoolMobile/getSubMainSector.php");
                // Execute HTTP Post Request
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
                nameValuePairs.add(new BasicNameValuePair("mainsector", search_Mainsector));
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
                php_response_subMainSector = sb.toString();

                inputStream.close();

            } catch (Exception e) {
                Toast.makeText(getApplicationContext(),
                        "Error inside set:" + e.toString(), Toast.LENGTH_LONG)
                        .show();
            }
            return php_response_subMainSector;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            php_response_subMainSector = "All#" + php_response_subMainSector;

            subMainsector_data = php_response_subMainSector.split("#");

            ArrayAdapter<String> adapterSubMainSector = new ArrayAdapter<String>(EconomicSectorsActivity.this, R.layout.row_spinner, R.id.textViewDealerName, subMainsector_data);
            spinnerSubMainSector.setAdapter(adapterSubMainSector);
            spinnerSubMainSector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    search_SubMainSector = subMainsector_data[position];
                    seachByCategory();
                    new GetSubSector().execute();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    search_SubMainSector = "All";
                }
            });


        }
    }

    String subSector_data[];
    String php_response_SubSector;

    class GetSubSector extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            try {
                DefaultHttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://mbinitiative.com/impactpoolMobile/getSubSector.php");
                // Execute HTTP Post Request
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
                nameValuePairs.add(new BasicNameValuePair("submainsector", search_SubMainSector));
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
                php_response_SubSector = sb.toString();
                inputStream.close();

            } catch (Exception e) {
                Toast.makeText(getApplicationContext(),
                        "Error inside set:" + e.toString(), Toast.LENGTH_LONG)
                        .show();
            }
            return php_response_SubSector;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            php_response_SubSector = "All#" + php_response_SubSector;

            subSector_data = php_response_SubSector.split("#");

            ArrayAdapter<String> adapterSubSector = new ArrayAdapter<String>(EconomicSectorsActivity.this, R.layout.row_spinner, R.id.textViewDealerName, subSector_data);
            spinnerSubSector.setAdapter(adapterSubSector);
            spinnerSubSector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    search_SubSector = subSector_data[position];
                    seachByCategory();

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    search_SubSector = "All";
                }
            });
        }
    }


    String details_response;

    class GetActivityGroupDetails extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            Log.e("poooooty", activitygroup + " - search");

            try {
                DefaultHttpClient httpclient = new DefaultHttpClient();

                HttpPost httppost = new HttpPost("http://mbinitiative.com/impactpoolMobile/getDetailsEconomicsector.php");
                // Add your data
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                nameValuePairs.add(new BasicNameValuePair("activitygroup", activitygroup));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
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
                details_response = sb.toString();
                inputStream.close();
            } catch (Exception e) {
                Toast.makeText(EconomicSectorsActivity.this, "Error inside set:" + e.toString(), Toast.LENGTH_LONG).show();
            }

            return details_response;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            searched_activityClass = details_response.replace("#", "\n");
            textViewCount.setVisibility(View.VISIBLE);
            textViewCount.setText("Loading Economic Sector " + tzz + "/" + jsonMainNode.length());
            customList_EconomicSectors.add(new Getter_EconomicSectors(id, mainsector, submainsector, subsector, activitygroup, searched_activityClass));
            ListDrawer_Undergraduateprog(customList_EconomicSectors);
            getReport(tzz = tzz + 1);

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

