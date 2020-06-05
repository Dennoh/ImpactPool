package tcds.or.tcdsapp.mainapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import de.mateware.snacky.Snacky;
import tcds.or.tcdsapp.MainOnlineShopActivity;
import tcds.or.tcdsapp.PlaceOrderActivity;
import tcds.or.tcdsapp.R;
import tcds.or.tcdsapp.ThankYouAccessActivity;
import tcds.or.tcdsapp.ThankYouActivity;
import tcds.or.tcdsapp.UndergraduateProgrammeActivity;

public class MainActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener {
    SliderLayout sliderLayout;

    private static String URL_BANNERS = "http://mbinitiative.com/impactpoolMobile/getbanner.php";
    private List<GetterBanner> customList_banner;

    private java.net.URL url;
    private HttpURLConnection urlConnection;
    private MyTask_Banners taskBanners;


    private static final String URL_NEWS = "http://mbinitiative.com/impactpoolMobile/getNews.php";
    private List<Getter_News> customList_news;
    private String id;
    private String news_title;
    private String details;
    private String posted_date;
    private String updated_on;

    private RecyclerView recyclerView;
    private RecyclerViewAdapter_News recyclerViewAdapterNews;
    private MyTask_News taskNews;
    ProgressBar progressBar;
    String receivedstate;
    Snackbar snack;

    public static final String MYPREFERENCES_ACCESSDIALOG = "MyPreferences_ACCESSDIALOG";
    SharedPreferences.Editor editor;
    SharedPreferences sharedpreferences_AccessDialog;
    Dialog accessDialog;
    String startdatetime;
    public static final String MYPREFERENCES_ACCESSPAYMENT = "MyPreferences_ACCESSPAYMENT";
    SharedPreferences sharedpreferences_AccessPayment;
    long totalDays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        sharedpreferences_AccessPayment = getSharedPreferences(MYPREFERENCES_ACCESSPAYMENT, Context.MODE_PRIVATE);
        if (sharedpreferences_AccessPayment.contains("startDate")) {
            SharedPreferences sharedpreferences = getSharedPreferences(MYPREFERENCES_ACCESSPAYMENT, Context.MODE_PRIVATE);
            startdatetime = sharedpreferences.getString("startDate", null);
//            startdatetime = "2020-5-26 11:12:45";
            try {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Date past = format.parse(startdatetime);
                Date now = new Date();
                totalDays = TimeUnit.MILLISECONDS.toDays(now.getTime() - past.getTime());
            } catch (Exception j) {
                j.printStackTrace();
            }

            if (totalDays >= 7) {
                startActivity(new Intent(MainActivity.this, ThankYouAccessActivity.class));
                finish();
                Log.e("mdaaaaaa", totalDays + " start charge");

            } else {

                Log.e("mdaaaaaa", totalDays + " continue using");

            }

        } else {
            DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String startdate = dateFormat2.format(new Date()).toString();
            sharedpreferences_AccessPayment = getSharedPreferences(MYPREFERENCES_ACCESSPAYMENT, Activity.MODE_PRIVATE);
            editor = sharedpreferences_AccessPayment.edit();
            editor.putString("startDate", startdate);
            editor.commit();
        }


        sliderLayout = findViewById(R.id.slider);
        progressBar = (ProgressBar) findViewById(R.id.my_progressBar);
        progressBar.setVisibility(View.VISIBLE);

        accessWebService_banners();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerListNews);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        //start from the first item of recycler
        linearLayoutManager.setStackFromEnd(false);

        recyclerView.setLayoutManager(linearLayoutManager);
        SnapHelper helper = new LinearSnapHelper();
        helper.attachToRecyclerView(recyclerView);

        receivedstate = Boolean.toString(haveNetworkConnection());
        if (receivedstate.equalsIgnoreCase("true")) {
            accessWebService_news();
        } else {

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuInvite:
                Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
                String applink = "https://play.google.com/store/apps/details?id=tcds.or.tcdsapp";  //www.fursane.com.fursanet
                String invitemessage = "Am using TCDS to explore Career opportunities and much more.\n\nDownload it Today:" + applink;
                String imageLink = "http://mbinitiative.com/impactpoolMobile/tcdstranslogo.png";
                if (Build.VERSION.SDK_INT >= 24) {
                    try {
                        Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                        m.invoke(null);
                        shareItem(imageLink, invitemessage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public void shareItem(String url, final String message) {
        Picasso.with(MainActivity.this).load(url).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("image/*");
                i.putExtra(Intent.EXTRA_STREAM, getLocalBitmapUri(bitmap));
                i.putExtra(Intent.EXTRA_TEXT, message);
                startActivity(Intent.createChooser(i, "Share  Product"));
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
            }
        });
    }

    public Uri getLocalBitmapUri(Bitmap bmp) {
        Uri bmpUri = null;
        try {
            File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }


    private void dislayBanners(List<GetterBanner> banners) {
        HashMap<String, String> bannerMap = new HashMap<>();

        for (GetterBanner item : banners)
            bannerMap.put(item.getName(), item.getLink());
        for (String name : bannerMap.keySet()) {
            TextSliderView textSliderView = new TextSliderView(this);
            textSliderView.setOnSliderClickListener(this);

            textSliderView.description(name)
                    .image(bannerMap.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit);

            sliderLayout.addSlider(textSliderView);
        }
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Fade);
        //sliderLayout.setCustomIndicator((PagerIndicator) findViewById(R.id.banner_slider_indicator));
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(8400);


    }

    public void onSliderClick(BaseSliderView sliderview) {
        Intent myNewIntent = new Intent(getApplicationContext(), MainOnlineShopActivity.class);
        startActivity(myNewIntent);
    }

    private void accessWebService_banners() {
        Random rand = new Random();
        int n = rand.nextInt(50000000) + 1;
        String randomstamp = "70timestamp" + n;
        URL_BANNERS = "http://mbinitiative.com/impactpoolMobile/getbanner.php?tmps=" + randomstamp;
        URL_BANNERS = URL_BANNERS.replaceAll("\\s+", "%20");
        taskBanners = new MyTask_Banners();
        taskBanners.execute(new String[]{URL_BANNERS});
    }

    public void mainClicked(View view) {

        switch (view.getId()) {
            case R.id.home_img:
                startActivity(new Intent(getApplicationContext(), MainActivity.class));

                break;
            case R.id.home_txt:
                startActivity(new Intent(getApplicationContext(), MainActivity.class));

                break;
//            case R.id.Coaching_img:
//                startActivity(new Intent(getApplicationContext(), CoachingActivity.class));
//
//                break;
//            case R.id.Coaching_txt:
//                startActivity(new Intent(getApplicationContext(), CoachingActivity.class));
//
//                break;
            case R.id.Connect_img:
                startActivity(new Intent(getApplicationContext(), ConnectActivity.class));

                break;
            case R.id.Connect_txt:
                startActivity(new Intent(getApplicationContext(), ConnectActivity.class));

                break;
            case R.id.settings_img:
                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));

                break;
            case R.id.settings_txt:
                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));

                break;
            case R.id.buttonViewallNews:
                startActivity(new Intent(getApplicationContext(), NewsActivity.class));
                break;
            case R.id.textViewLatestNews:
                startActivity(new Intent(getApplicationContext(), NewsActivity.class));
                break;
        }
    }

    SharedPreferences mySharedPreferences;

    public void checkifUserVerified(String activityName) {
        Log.e("jOHN DOEEEEEE", "checkifUserVerifieddddddddddddddddddddddddddddddddddddd");
        mySharedPreferences = getSharedPreferences(PhoneVerificationActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        if (mySharedPreferences.contains("verifiedphone")) {
            if (activityName.equals("HighLearning")) {
                startActivity(new Intent(getApplicationContext(), HighLearningInstitutionActivity.class));
            } else if (activityName.equals("Occupational")) {
                startActivity(new Intent(getApplicationContext(), OccupationalPathwaysActivity.class));
            } else if (activityName.equals("Undergraduate")) {
                startActivity(new Intent(getApplicationContext(), UndergraduateProgrammeActivity.class));
            } else if (activityName.equals("EconomicSector")) {
                startActivity(new Intent(getApplicationContext(), EconomicSectorsActivity.class));
            }
        } else {
            //Toast.makeText(this, "No Verified,Please Login", Toast.LENGTH_LONG.show());
//            Toast.makeText(this, "Not Verified,Please Login", Toast.LENGTH_LONG).show();
            Snacky.builder()
                    .setActivity((Activity) MainActivity.this)
                    .setMaxLines(2)
                    .setDuration(Snacky.LENGTH_INDEFINITE)
                    .setText("NOT LOGIN?")
                    .setText("Please Login or Register to view Contents")
                    .centerText()
                    .setBackgroundColor(Color.parseColor("#002160"))
                    .setTextColor(Color.parseColor("#FFFFFF"))
                    .setActionText("REGISTER")
                    .setActionTextColor(Color.parseColor("#FFFFFF"))
                    .setActionClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //Common.currentProduct.getLink(
                            startActivity(new Intent(MainActivity.this, PhoneVerificationActivity.class));
                            //   context.startActivity(new Intent(context, CartActivity.class));
                        }
                    })
                    .setActionTextSize(14)
                    .setMaxLines(4)
                    .centerText()
                    .setActionTextTypefaceStyle(Typeface.BOLD)
                    .info()
                    .show();
        }
    }


    public void categoryClicked(View view) {
        switch (view.getId()) {
            case R.id.cardViewHighLearning:
                sharedpreferences_AccessDialog = getSharedPreferences(MYPREFERENCES_ACCESSDIALOG, Context.MODE_PRIVATE);
                if (sharedpreferences_AccessDialog.contains("NameHighLearning")) {
                    mySharedPreferences = getSharedPreferences(MYPREFERENCES_ACCESSDIALOG, 0);
                    startActivity(new Intent(getApplicationContext(), HighLearningInstitutionActivity.class));
                } else {
                    accessDialog = new Dialog(MainActivity.this);
                    accessDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    accessDialog.setContentView(R.layout.charges_dialog);
                    accessDialog.getWindow().setLayout(android.app.ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
                    TextView textviewOKAY = accessDialog.findViewById(R.id.textviewOKAY);
                    textviewOKAY.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mySharedPreferences = getSharedPreferences(MYPREFERENCES_ACCESSDIALOG, Activity.MODE_PRIVATE);
                            editor = mySharedPreferences.edit();
                            editor.putString("NameHighLearning", "HighLearning");
                            editor.commit();
                            accessDialog.dismiss();
                            startActivity(new Intent(getApplicationContext(), HighLearningInstitutionActivity.class));
                        }
                    });
                    accessDialog.show();
                }

//                checkifUserVerified("HighLearning");
                break;
            case R.id.cardViewOccupational:
                sharedpreferences_AccessDialog = getSharedPreferences(MYPREFERENCES_ACCESSDIALOG, Context.MODE_PRIVATE);
                if (sharedpreferences_AccessDialog.contains("NameOccupational")) {
                    mySharedPreferences = getSharedPreferences(MYPREFERENCES_ACCESSDIALOG, 0);
                    startActivity(new Intent(getApplicationContext(), HighLearningInstitutionActivity.class));
                } else {
                    accessDialog = new Dialog(MainActivity.this);
                    accessDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    accessDialog.setContentView(R.layout.charges_dialog);
                    accessDialog.getWindow().setLayout(android.app.ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
                    TextView textviewOccupational = accessDialog.findViewById(R.id.textviewOKAY);
                    textviewOccupational.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mySharedPreferences = getSharedPreferences(MYPREFERENCES_ACCESSDIALOG, Activity.MODE_PRIVATE);
                            editor = mySharedPreferences.edit();
                            editor.putString("NameOccupational", "Occupational");
                            editor.commit();
                            accessDialog.dismiss();
                            startActivity(new Intent(getApplicationContext(), OccupationalPathwaysActivity.class));
                        }
                    });
                    accessDialog.show();
                }
                break;
            case R.id.cardViewUndergraduate:
                sharedpreferences_AccessDialog = getSharedPreferences(MYPREFERENCES_ACCESSDIALOG, Context.MODE_PRIVATE);
                if (sharedpreferences_AccessDialog.contains("NameUndergraduate")) {
                    mySharedPreferences = getSharedPreferences(MYPREFERENCES_ACCESSDIALOG, 0);
                    startActivity(new Intent(getApplicationContext(), HighLearningInstitutionActivity.class));
                } else {
                    accessDialog = new Dialog(MainActivity.this);
                    accessDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    accessDialog.setContentView(R.layout.charges_dialog);
                    accessDialog.getWindow().setLayout(android.app.ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
                    TextView textviewUndergraduate = accessDialog.findViewById(R.id.textviewOKAY);
                    textviewUndergraduate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mySharedPreferences = getSharedPreferences(MYPREFERENCES_ACCESSDIALOG, Activity.MODE_PRIVATE);
                            editor = mySharedPreferences.edit();
                            editor.putString("NameUndergraduate", "Undergraduate");
                            editor.commit();
                            accessDialog.dismiss();
                            startActivity(new Intent(getApplicationContext(), UndergraduateProgrammeActivity.class));
                        }
                    });
                    accessDialog.show();
                }
                break;
            case R.id.cardViewEconomicSector:
                sharedpreferences_AccessDialog = getSharedPreferences(MYPREFERENCES_ACCESSDIALOG, Context.MODE_PRIVATE);
                if (sharedpreferences_AccessDialog.contains("NameEconomicSector")) {
                    mySharedPreferences = getSharedPreferences(MYPREFERENCES_ACCESSDIALOG, 0);
                    startActivity(new Intent(getApplicationContext(), HighLearningInstitutionActivity.class));
                } else {
                    accessDialog = new Dialog(MainActivity.this);
                    accessDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    accessDialog.setContentView(R.layout.charges_dialog);
                    accessDialog.getWindow().setLayout(android.app.ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
                    TextView textviewEconomicSector = accessDialog.findViewById(R.id.textviewOKAY);
                    textviewEconomicSector.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mySharedPreferences = getSharedPreferences(MYPREFERENCES_ACCESSDIALOG, Activity.MODE_PRIVATE);
                            editor = mySharedPreferences.edit();
                            editor.putString("NameEconomicSector", "EconomicSector");
                            editor.commit();
                            accessDialog.dismiss();
                            startActivity(new Intent(getApplicationContext(), EconomicSectorsActivity.class));
                        }
                    });
                    accessDialog.show();
                }
                break;
        }
    }

    public void cardClicked(View view) {
        switch (view.getId()) {
            case R.id.cardViewCareerDatabase:
                Intent intentCD = new Intent(getApplicationContext(), TCDSServicesActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("servicetype", "Career Database");
                intentCD.putExtras(bundle);
                startActivity(intentCD);
                break;
            case R.id.cardViewCareerServices:
                Intent intentCS = new Intent(getApplicationContext(), TCDSServicesActivity.class);
                Bundle bundleCS = new Bundle();
                bundleCS.putString("servicetype", "Career Services");
                intentCS.putExtras(bundleCS);
                startActivity(intentCS);
                break;
            case R.id.cardViewOnlineShop:
//                Intent intentOS = new Intent(getApplicationContext(), TCDSServicesActivity.class);
//                Bundle bundleOS = new Bundle();
//                bundleOS.putString("servicetype", "Online Shop");
//                intentOS.putExtras(bundleOS);
//                startActivity(intentOS);
                startActivity(new Intent(getApplicationContext(), MainOnlineShopActivity.class));
                break;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    private class MyTask_Banners extends AsyncTask<String, Void, List> {

        @Override
        protected List doInBackground(String... params) {
            try {
                url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();
                urlConnection.setConnectTimeout(5000);

                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                StringBuilder jsonResult = inputStreamToString(in);
                customList_banner = new ArrayList<>();
                JSONObject jsonResponse = new JSONObject(jsonResult.toString());
                JSONArray jsonMainNode = jsonResponse.optJSONArray("banner_jsondata");
                for (int i = 0; i < jsonMainNode.length(); i++) {
                    JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                    String id = jsonChildNode.optString("id");
                    String name = jsonChildNode.optString("name");
                    String link = jsonChildNode.optString("link");
                    customList_banner.add(new GetterBanner(id, name, link));
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return customList_banner;
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
            dislayBanners(customList_banner);
        }
    }


    private class MyTask_News extends AsyncTask<String, Void, List> {

        @Override
        protected List doInBackground(String... params) {
            try {
                url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();
                urlConnection.setConnectTimeout(5000);

                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                StringBuilder jsonResult = inputStreamToString(in);
                customList_news = new ArrayList<>();
                JSONObject jsonResponse = new JSONObject(jsonResult.toString());
                JSONArray jsonMainNode = jsonResponse.optJSONArray("news_jsondata");
                for (int i = 0; i < jsonMainNode.length(); i++) {
                    JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                    id = jsonChildNode.optString("id");
                    news_title = jsonChildNode.optString("news_title");
                    details = jsonChildNode.optString("details");
                    posted_date = jsonChildNode.optString("posted_date");
                    customList_news.add(new Getter_News(id, news_title, details, posted_date));
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return customList_news;
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
            ListDrawer_News(list);
            progressBar.setIndeterminate(false);
            progressBar.setVisibility(View.GONE);
        }
    }

    private void accessWebService_news() {
        taskNews = new MyTask_News();
        taskNews.execute(new String[]{URL_NEWS});
    }

    public void ListDrawer_News(List<Getter_News> customList) {
        recyclerViewAdapterNews = new RecyclerViewAdapter_News(customList, MainActivity.this);
        recyclerViewAdapterNews.notifyDataSetChanged();
        recyclerView.setAdapter(recyclerViewAdapterNews);
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
