package tcds.or.tcdsapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
import java.util.Collections;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import es.dmoral.toasty.Toasty;

public class StarterActivity extends AppCompatActivity {
    String verified_phonenumber;

    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "NumberVerificationPrefs";
    public static final String verify_number = "number_verified";
    SharedPreferences.Editor editor;
    private static final int RC_SIGN_IN = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_starter);
        verifyUsersPhonenumber();
    }

    public void verifyUsersPhonenumber() {
        Intent intent = AuthUI.getInstance().createSignInIntentBuilder()
                .setIsSmartLockEnabled(true)
                .setAvailableProviders(Collections.singletonList(
                        new AuthUI.IdpConfig.PhoneBuilder()
                                .build()))
                //.setTheme(R.style.LoginTheme)
                .setLogo(R.drawable.ic_blur_on_black_24dp)
                .setTosAndPrivacyPolicyUrls("https://bit.ly/2VJMHs8", "https://bit.ly/2VJMHs8")
                .build();

        startActivityForResult(intent, RC_SIGN_IN);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            IdpResponse idpResponse = IdpResponse.fromResultIntent(data);
            if (resultCode == RESULT_OK) {
                // Successfully signed in
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                //showAlertDialog(user);
                Toasty.success(getBaseContext(), "Successfully Verified", Toast.LENGTH_LONG, true).show();
                Log.d("careeen", "UserVerifiedPhone: " + user.getPhoneNumber());
                verified_phonenumber = user.getPhoneNumber();
                sharedpreferences = getSharedPreferences(MyPREFERENCES, Activity.MODE_PRIVATE);
                editor = sharedpreferences.edit();
                editor.putString("number_verified", verified_phonenumber);
                editor.commit();
                new InsertPhoneNumber().execute();
            } else {
                Toasty.success(getBaseContext(), "Phone Verification Failed", Toast.LENGTH_LONG, true).show();
                Log.d("careeen", "Phone Verification Failed: ");
                /**
                 *   Sign in failed. If response is null the user canceled the
                 *   sign-in flow using the back button. Otherwise check
                 *   response.getError().getErrorCode() and handle the error.
                 */
                // Toast.makeText(getBaseContext(), "Phone Auth Failed", Toast.LENGTH_LONG).show();
            }
        }
    }

    class InsertPhoneNumber extends AsyncTask<String, String, String> {
        String php_response;

        @Override
        protected String doInBackground(String... strings) {

            try {

                /* seting up the connection and send data with url */
                // create a http default client - initialize the HTTp client

                DefaultHttpClient httpclient = new DefaultHttpClient();

                HttpPost httppost = new HttpPost("http://mbinitiative.com/safehub/addphonenumber.php");

                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);

                nameValuePairs.add(new BasicNameValuePair("user_phone", verified_phonenumber));

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
                php_response = sb.toString();
                inputStream.close();

            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Try Again", Toast.LENGTH_LONG).show();
            }

            return php_response;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            startActivity(new Intent(StarterActivity.this, MainActivity.class));
        }
    }

    @Override
    protected void onResume() {
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        if (sharedpreferences.contains("number_verified")) {
            startActivity(new Intent(StarterActivity.this, MainActivity.class));
        }
        super.onResume();
    }
}
