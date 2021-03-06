package tcds.or.tcdsapp.mainapp;

import androidx.appcompat.app.AppCompatActivity;
import es.dmoral.toasty.Toasty;
import tcds.or.tcdsapp.R;
import tcds.or.tcdsapp.mainapp.MainActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        findViewById(R.id.BtnRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toasty.success(getApplicationContext(), "Registration Completed Successfully", Toast.LENGTH_LONG, true).show();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }

}
