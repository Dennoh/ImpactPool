package tcds.or.tcdsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.mateware.snacky.Snacky;
import es.dmoral.toasty.Toasty;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import tcds.or.tcdsapp.Adapter.ProductsAdapter;
import tcds.or.tcdsapp.Database.DataSource.CartRepository;
import tcds.or.tcdsapp.Database.Local.CartDataSource;
import tcds.or.tcdsapp.Database.Local.YeuniRoomDatabase;
import tcds.or.tcdsapp.Model.Book;
import tcds.or.tcdsapp.Retrofit.TcdsAPI;
import tcds.or.tcdsapp.Utils.Common;
import tcds.or.tcdsapp.mainapp.MainActivity;
import tcds.or.tcdsapp.mainapp.PhoneVerificationActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Collections;
import java.util.List;

public class MainOnlineShopActivity extends AppCompatActivity {
    RecyclerView recycler_otherbooks;
    CompositeDisposable compositeDisposable;
    ProgressBar progressBar;
    TcdsAPI mService;


    BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.bottomHome:
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    return true;
                case R.id.bottomOrders:
                    startActivity(new Intent(MainOnlineShopActivity.this, MyOrdersActivity.class));
                    return true;
                case R.id.bottomCart:
                    startActivity(new Intent(MainOnlineShopActivity.this, CartActivity.class));
                    return true;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_online_shop);
        BottomNavigationView navigation = findViewById(R.id.bottom_navigation);
        recycler_otherbooks = findViewById(R.id.recycler_otherbooks);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        progressBar = findViewById(R.id.progressBar);


        GridLayoutManager mGridLayoutManager = new GridLayoutManager(MainOnlineShopActivity.this, 2);
        recycler_otherbooks.setLayoutManager(mGridLayoutManager);

        initDB();


        if (haveNetworkConnection()) {
            compositeDisposable = new CompositeDisposable();
            mService = Common.getAPI();

            getLatestBooks();

        } else {
            Snacky.builder()

                    .setActivity(MainOnlineShopActivity.this)
                    .setMaxLines(4)
                    .centerText()
                    .setBackgroundColor(Color.parseColor("#FFFFFF"))
                    .setTextColor(Color.parseColor("#FFFFFF"))
                    .setActionText("Retry!")
                    .setActionTextColor(Color.parseColor("#cd201f"))
                    .setActionClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            //firstLaunch();
                            compositeDisposable = new CompositeDisposable();
                            mService = Common.getAPI();


                            getLatestBooks();


                        }
                    })
                    .setActionTextSize(20)
                    .setMaxLines(4)
                    .centerText()
                    .setActionTextTypefaceStyle(Typeface.BOLD)
                    .setDuration(Snacky.LENGTH_INDEFINITE)
                    .error()
                    .show();

            Toasty.error(MainOnlineShopActivity.this, "No internet Check your settings", Toast.LENGTH_LONG, true).show();
            // Toast.makeText(this, "No intenet Check your settings", Toast.LENGTH_SHORT).show();

        }


    }

    private void initDB() {
        Common.yeuniRoomDatabase = YeuniRoomDatabase.getInstance(this);
        Common.cartRepository = CartRepository.getInstance(CartDataSource.getInstance(Common.yeuniRoomDatabase.cartDAO()));
        //   Common.wishlistRepository= WishlistRepository.getInstance(WishlistDataSource.getInstance(Common.yeuniRoomDatabase.wishlistDAO()));
// Common.favoriteRepository= FavoriteRepository.getInstance(FavoriteDataSource.getInstance(Common.yeuniRoomDatabase.favoriteDAO()));


    }

    private void getLatestBooks() {
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setIndeterminate(true);
        compositeDisposable.add(mService.getAllProducts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Book>>() {
                               @Override
                               public void accept(List<Book> products) throws Exception {
                                   if (products.isEmpty()) {
                                       Toasty.warning(MainOnlineShopActivity.this, "Books Not Available for now", Toast.LENGTH_LONG, true).show();

                                   }

                                   displayLatestBooks(products);
                               }
                           }, new Consumer<Throwable>() {
                               @Override
                               public void accept(Throwable throwable) throws Exception {
                                   Toast.makeText(MainOnlineShopActivity.this, "Error :" + throwable.getMessage(), Toast.LENGTH_LONG).show();

                                   //Log.e(TAG, throwable.getMessage(), throwable);
                               }
                           }
                ));
    }

    private void displayLatestBooks(List<Book> products) {
        ProductsAdapter productsAdapter = new ProductsAdapter(this, products, products);
        recycler_otherbooks.setAdapter(productsAdapter);
        productsAdapter.notifyDataSetChanged();

        Collections.reverse(products);
        progressBar.setVisibility(View.GONE);

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
