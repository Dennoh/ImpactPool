package tcds.or.tcdsapp.mainapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Collections;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import es.dmoral.toasty.Toasty;
import tcds.or.tcdsapp.R;

public class PhoneVerificationActivity extends AppCompatActivity {
    String verified_phonenumber;

    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "NumberVerificationPrefs";
    public static final String verify_number = "number_verified";
    SharedPreferences.Editor editor;
    private static final int RC_SIGN_IN = 101;
    Button otp_form_feedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_verification);
        otp_form_feedback = findViewById(R.id.otp_form_feedback);
        otp_form_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyUsersPhonenumber();
            }
        });
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
                Toasty.success(getApplicationContext(), "Successfully Verified", Toast.LENGTH_LONG, true).show();
                verified_phonenumber = user.getPhoneNumber();
                sharedpreferences = getSharedPreferences(MyPREFERENCES, Activity.MODE_PRIVATE);
                editor = sharedpreferences.edit();
                editor.putString("verifiedphone", verified_phonenumber);
                editor.commit();
                Log.e("careeen", " Successfully");
                Log.e("careeen", verified_phonenumber + " Successfully");

                Intent intent = new Intent(getApplicationContext(), RegistrationActivity.class);
                startActivity(intent);
            } else {
                Toasty.success(getApplicationContext(), "Phone Verification Failed", Toast.LENGTH_LONG, true).show();
                Log.e("careeen", "Phone Verification Failed: ");
                /**
                 *   Sign in failed. If response is null the user canceled the
                 *   sign-in flow using the back button. Otherwise check
                 *   response.getError().getErrorCode() and handle the error.
                 */
//                Toast.makeText(getApplicationContext(), "Phone Auth Failed", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onResume() {
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        if (sharedpreferences.contains("number_verified")) {
            startActivity(new Intent(PhoneVerificationActivity.this, MainActivity.class));
        }
        super.onResume();
    }
}
