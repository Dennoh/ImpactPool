package tcds.or.tcdsapp.mainapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import tcds.or.tcdsapp.R;
import tcds.or.tcdsapp.ThankYouAccessActivity;

public class IntroActivity extends AppCompatActivity {
    private ViewPager screenPager;
    IntroViewPagerAdapter introViewPagerAdapter;
    TabLayout tabIndicator;
    Button btnNext;
    int position = 0;
    Button btnGetStarted;
    Animation btnAnim;
    TextView tvSkip;

    public static final String MYPREFERENCES_INTROSCREEN = "MyPreferences_INTRO";
    SharedPreferences.Editor editor;
    SharedPreferences sharedpreferences_IntroScreen;
    String introstatus;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


//        // when this activity is about to be launch we need to check if its openened before or not
//        if (restorePrefData()) {
//            Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
//            startActivity(mainActivity);
//            finish();
//        }
        sharedpreferences_IntroScreen = getSharedPreferences(MYPREFERENCES_INTROSCREEN, Context.MODE_PRIVATE);
        if (sharedpreferences_IntroScreen.contains("introscreen")) {
            SharedPreferences sharedpreferences = getSharedPreferences(MYPREFERENCES_INTROSCREEN, Context.MODE_PRIVATE);
            introstatus = sharedpreferences.getString("introscreen", null);
            Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(mainActivity);
            finish();
        }


        setContentView(R.layout.activity_intro);
        // hide the action bar
//        getSupportActionBar().hide();
        // ini views
        btnNext = findViewById(R.id.btn_next);
        btnGetStarted = findViewById(R.id.btn_get_started);
        tabIndicator = findViewById(R.id.tab_indicator);
        btnAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_animation);
        tvSkip = findViewById(R.id.tv_skip);

        // fill list screen
        final List<ScreenItem> mList = new ArrayList<>();
        mList.add(new ScreenItem("Career Database", "Find Occupations Browse groups of similar occupations to explore careers. Choose from industry, field of work, science area, and more. Bright Outlook and make informed decision", R.drawable.tcdstranslogo));
        mList.add(new ScreenItem("TCDS Services", "Tanzanite Career Development Services, provides number of services from an intensive and up to date career database, training to online shop for books.", R.drawable.tcdstranslogo));

        // setup viewpager
        screenPager = findViewById(R.id.screen_viewpager);
        introViewPagerAdapter = new IntroViewPagerAdapter(this, mList);
        screenPager.setAdapter(introViewPagerAdapter);

        // setup tablayout with viewpager

        tabIndicator.setupWithViewPager(screenPager);

        // next button click Listner
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = screenPager.getCurrentItem();
                if (position < mList.size()) {
                    position++;
                    screenPager.setCurrentItem(position);
                }

                if (position == mList.size() - 1) { // when we rech to the last screen
                    // TODO : show the GETSTARTED Button and hide the indicator and the next button
                    loaddLastScreen();
                }
            }
        });

        // tablayout add change listener
        tabIndicator.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if (tab.getPosition() == mList.size() - 1) {
                    loaddLastScreen();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        // Get Started button click listener
        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedpreferences_IntroScreen = getSharedPreferences(MYPREFERENCES_INTROSCREEN, Activity.MODE_PRIVATE);
                editor = sharedpreferences_IntroScreen.edit();
                editor.putString("introscreen", "dooone");
                editor.commit();
                Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mainActivity);
                finish();
            }
        });

        // skip button click listener
        tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                screenPager.setCurrentItem(mList.size());
            }
        });
    }


    // show the GETSTARTED Button and hide the indicator and the next button
    private void loaddLastScreen() {
        btnNext.setVisibility(View.INVISIBLE);
        btnGetStarted.setVisibility(View.VISIBLE);
        tvSkip.setVisibility(View.INVISIBLE);
        tabIndicator.setVisibility(View.INVISIBLE);
        // TODO : ADD an animation the getstarted button
        // setup animation
        btnGetStarted.setAnimation(btnAnim);


    }
}
