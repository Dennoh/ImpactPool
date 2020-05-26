package tcds.or.tcdsapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        displaySplash();
    }

    public void displaySplash() {
        Thread mythread = new Thread() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                try {
                    int displaytime = 4000;
                    int waittime = 0;
                    while (waittime < displaytime) {
                        sleep(100);
                        waittime = waittime + 100;
                    }
                    super.run();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } finally {
                    Intent a = new Intent(getApplicationContext(), IntroActivity.class);
                    startActivity(a);
                    finish();
                }
            }
        };
        mythread.start();
    }
}
