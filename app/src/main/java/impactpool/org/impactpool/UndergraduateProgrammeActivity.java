package impactpool.org.impactpool;

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
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_undergraduate_programme);
        progressBar = (ProgressBar) findViewById(R.id.my_progressBar);
        progressBar.setVisibility(View.VISIBLE);

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

                    customList_undergraduateprog.add(new Getter_Undergraduate(id, Programme, Code, AdmReq, MinInstAdmPoints, AdmCapacity, ProgDuration, university, region));
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


    public void ListDrawer_Undergraduateprog(List<Getter_Undergraduate> customList) {
        //textViewSearchResults.setText(" " + customList.size() + " Results Found");
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.learningmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }


}
