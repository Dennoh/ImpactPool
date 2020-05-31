package tcds.or.tcdsapp.mainapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import tcds.or.tcdsapp.R;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

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

public class SettingsActivity extends AppCompatActivity {
    String user_reportdetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public void settingsClicked(View view) {
        switch (view.getId()) {
            case R.id.buttonInvite:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, "Hey am using TCDS App for my career issues. Download it today \n\nhttps://play.google.com/store/apps/details?id=com.angazadirectory.www.angazadirectory&hl=en");
                startActivity(Intent.createChooser(intent, "Invite Friends"));
                break;
            case R.id.buttonAbout:
                startActivity(new Intent(getApplicationContext(), AboutActivity.class));
                break;
            case R.id.buttonHelp:
                startActivity(new Intent(getApplicationContext(), HelpActivity.class));
                break;
            case R.id.buttonTC:
                startActivity(new Intent(getApplicationContext(), TermsAndConditionsActivity.class));

                break;
            case R.id.buttonPrivacy:
                startActivity(new Intent(getApplicationContext(), PrivacyPolicyActivity.class));

                break;
            case R.id.buttonReportProblem:
                final Dialog dialog = new Dialog(view.getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
                dialog.setContentView(R.layout.report_problem_dialog);
                dialog.getWindow().setLayout(android.app.ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);


                final EditText editTextDetails = (EditText) dialog.findViewById(R.id.editTextDetails);
                Button buttonReport = (Button) dialog.findViewById(R.id.buttonReport);

                buttonReport.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        user_reportdetails = editTextDetails.getText().toString();
                        if (user_reportdetails.isEmpty()) {
                            editTextDetails.setError("Details are required!");
                        } else {
                            //check network
                            if (haveNetworkConnection()) {
                                editTextDetails.setEnabled(false);
                                new ReportAProblem().execute();
                            } else {
                                dialog.dismiss();
                                Snackbar snack = Snackbar.make(findViewById(android.R.id.content), "No internet. Check your Network Settings!", Snackbar.LENGTH_LONG);
                                View v = snack.getView();
                                v.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                                ((TextView) v.findViewById(R.id.snackbar_text)).setTextColor(Color.WHITE);
                                ((TextView) v.findViewById(R.id.snackbar_action)).setTextColor(Color.WHITE);
                                snack.setDuration(Snackbar.LENGTH_INDEFINITE);
                                snack.show();
                            }
                        }
                    }
                });
                dialog.show();
                break;
            case R.id.buttonUserFeedback:
                startActivity(new Intent(getApplicationContext(), FeedbackActivity.class));
                break;
            case R.id.buttonLang:

                break;
            case R.id.buttonLogout:

                break;

        }
    }
    String myresult;
    class ReportAProblem extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... arg0) {
            // TODO Auto-generated method stub
            try {
                DefaultHttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://angazadirectory.com/angazamobile/problem.php");
                // Add your data
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);

                nameValuePairs.add(new BasicNameValuePair("prob", user_reportdetails));

                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
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
                myresult = sb.toString();
                inputStream.close();
            } catch (Exception e) {
                Toast.makeText(SettingsActivity.this, "Error inside set:" + e.toString(), Toast.LENGTH_LONG).show();
            }
            return myresult;

        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            if (myresult.equals("1")) {
                Toast.makeText(getApplicationContext(), "Submitted Successfully", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
            } else {
                Toast.makeText(getApplicationContext(), "Failed.Please Try Again!", Toast.LENGTH_LONG).show();
            }
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
