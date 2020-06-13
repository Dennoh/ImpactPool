package tcds.or.tcdsapp.mainapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import tcds.or.tcdsapp.R;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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

public class EconomicSectorsActivity extends AppCompatActivity {
    LinearLayout linearSearchDesign;
    ImageView imageViewCancel;
    String mainsector_data[];
    MaterialSpinner spinnerMainSector;
    MaterialSpinner spinnerSubMainSector;
    MaterialSpinner spinnerSubSector;
    TextView textViewSearchResults, textViewClickToSearch, textViewCount, textViewClearSearch;
    String search_Mainsector;
    String search_SubMainSector;
    String search_SubSector;
    private static String URL_ECONOMICSECTORS = "http://mbinitiative.com/impactpoolMobile/getEconomicSectors_New.php";
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
    ArrayAdapter<String> adapterSector;
    ArrayAdapter<String> adapterSubMainSector;
    ArrayAdapter<String> adapterSubSector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_economic_sectors);

        linearSearchDesign = findViewById(R.id.linearSearchDesign);
        imageViewCancel = findViewById(R.id.imageViewCancel);
        textViewSearchResults = findViewById(R.id.textViewSearchResults);
        textViewClickToSearch = findViewById(R.id.textViewClickToSearch);
        textViewClearSearch = findViewById(R.id.textViewClearSearch);
        textViewCount = findViewById(R.id.textViewCount);
        spinnerMainSector = findViewById(R.id.spinnerSector);
        spinnerSubMainSector = findViewById(R.id.spinnerRegion);
        spinnerSubSector = findViewById(R.id.spinnerDistrict);


        progressBar = (ProgressBar) findViewById(R.id.my_progressBar);
        progressBar.setVisibility(View.VISIBLE);

        new GetMainSectors().execute();
        new GetSubMainSector().execute();
        new GetSubSector().execute();

        search_Mainsector = "All";
        search_SubMainSector = "All";
        search_SubSector = "All";


        spinnerMainSector.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                textViewClearSearch.setVisibility(View.VISIBLE);
                search_Mainsector = item;
                new GetFilters().execute();
                seachByCategory();
            }
        });


        spinnerSubMainSector.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                textViewClearSearch.setVisibility(View.VISIBLE);
                search_SubMainSector = item;
                new GetFilters().execute();
                seachByCategory();
            }
        });

        spinnerSubSector.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                textViewClearSearch.setVisibility(View.VISIBLE);
                search_SubSector = item;
                new GetFilters().execute();
                seachByCategory();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recyclerListLearningInst);
        recyclerView.setHasFixedSize(true);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(EconomicSectorsActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);

        receivedstate = Boolean.toString(haveNetworkConnection());
        if (receivedstate.equalsIgnoreCase("true")) {
            Log.e("herere98", "nikohapa");
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
                jsonMainNode = jsonResponse.optJSONArray("economicsectors_jsondata");
                for (int i = 0; i < jsonMainNode.length(); i++) {
                    JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                    String id = jsonChildNode.optString("id");
                    String mainsector = jsonChildNode.optString("mainsector");
                    String submainsector = jsonChildNode.optString("submainsector");
                    String subsector = jsonChildNode.optString("subsector");
                    String activitygroup = jsonChildNode.optString("activitygroup");
                    String activityclass = jsonChildNode.optString("activityclass");
                    customList_EconomicSectors.add(new Getter_EconomicSectors(id, mainsector, submainsector, subsector, activitygroup, activityclass));
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
            textViewSearchResults.setVisibility(View.VISIBLE);
            progressBar.setIndeterminate(false);
            progressBar.setVisibility(View.GONE);
        }
    }


    private void accessWebService_EconomicSectors() {
        taskEconomicSectors = new MyTask_EconomicSectors();
        taskEconomicSectors.execute(new String[]{"http://mbinitiative.com/impactpoolMobile/getEconomicSectors_New.php"});
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
            spinnerMainSector.setItems(mainsector_data);


//            ArrayAdapter<String> adapterSector = new ArrayAdapter<String>(EconomicSectorsActivity.this, R.layout.row_spinner, R.id.textViewDealerName, mainsector_data);
//            spinnerMainSector.setAdapter(adapterSector);
//            spinnerMainSector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                @Override
//                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                    search_Mainsector = mainsector_data[position];
//                    seachByCategory();
//
//                }
//
//                @Override
//                public void onNothingSelected(AdapterView<?> parent) {
//                    search_Mainsector = "All";
//                }
//            });


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
            spinnerSubMainSector.setItems(subMainsector_data);

//            ArrayAdapter<String> adapterSubMainSector = new ArrayAdapter<String>(EconomicSectorsActivity.this, R.layout.row_spinner, R.id.textViewDealerName, subMainsector_data);
//            spinnerSubMainSector.setAdapter(adapterSubMainSector);
//            spinnerSubMainSector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                @Override
//                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                    search_SubMainSector = subMainsector_data[position];
//                    seachByCategory();
//                }
//
//                @Override
//                public void onNothingSelected(AdapterView<?> parent) {
//                    search_SubMainSector = "All";
//                }
//            });


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
            spinnerSubSector.setItems(subSector_data);

//            ArrayAdapter<String> adapterSubSector = new ArrayAdapter<String>(EconomicSectorsActivity.this, R.layout.row_spinner, R.id.textViewDealerName, subSector_data);
//            spinnerSubSector.setAdapter(adapterSubSector);
//            spinnerSubSector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                @Override
//                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                    search_SubSector = subSector_data[position];
//                    seachByCategory();
//
//                }
//
//                @Override
//                public void onNothingSelected(AdapterView<?> parent) {
//                    search_SubSector = "All";
//                }
//            });
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

    String filter_results;
    String[] dataz;

    class GetFilters extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            try {

                /* seting up the connection and send data with url */
                // create a http default client - initialize the HTTp client

                DefaultHttpClient httpclient = new DefaultHttpClient();

                HttpPost httppost = new HttpPost("http://mbinitiative.com/impactpoolMobile/getfiltersEconomicSectors.php");

                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
                nameValuePairs.add(new BasicNameValuePair("mainsector", search_Mainsector));
                nameValuePairs.add(new BasicNameValuePair("subMainSector", search_SubMainSector));
                nameValuePairs.add(new BasicNameValuePair("subSector", search_SubSector));

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
                Toast.makeText(EconomicSectorsActivity.this, "Try Again", Toast.LENGTH_LONG).show();
            }

            return filter_results;


        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e("yeeeeeee34", "filter_results " + filter_results);

            dataz = filter_results.split("@");

            php_response_mainsector = dataz[0];
            Log.e("gegege34", php_response_mainsector);
            mainsector_data = php_response_mainsector.split("#");
            adapterSector = new ArrayAdapter<String>(EconomicSectorsActivity.this, R.layout.row_spinner, R.id.textViewDealerName, mainsector_data);
            spinnerMainSector.setAdapter(adapterSector);

            php_response_subMainSector = dataz[1];
            Log.e("gegege34", php_response_subMainSector);
            subMainsector_data = php_response_subMainSector.split("#");
            adapterSubMainSector = new ArrayAdapter<String>(EconomicSectorsActivity.this, R.layout.row_spinner, R.id.textViewDealerName, subMainsector_data);
            spinnerSubMainSector.setAdapter(adapterSubMainSector);


            php_response_SubSector = dataz[2];
            Log.e("gegege34", php_response_SubSector);
            subSector_data = php_response_SubSector.split("#");
            adapterSubSector = new ArrayAdapter<String>(EconomicSectorsActivity.this, R.layout.row_spinner, R.id.textViewDealerName, subSector_data);
            spinnerSubSector.setAdapter(adapterSubSector);

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        finish();
        return super.onKeyDown(keyCode, event);
    }

    public void ClearSearch(View view) {
        executeStart();
    }

    private void executeStart() {
        new GetMainSectors().execute();
        new GetSubMainSector().execute();
        new GetSubSector().execute();

        search_Mainsector = "All";
        search_SubMainSector = "All";
        search_SubSector = "All";

        textViewClearSearch.setVisibility(View.GONE);

        receivedstate = Boolean.toString(haveNetworkConnection());
        if (receivedstate.equalsIgnoreCase("true")) {
            accessWebService_EconomicSectors();
            textViewSearchResults.setVisibility(View.VISIBLE);
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
    }
}

