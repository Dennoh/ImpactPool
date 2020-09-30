package tcds.or.tcdsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import es.dmoral.toasty.Toasty;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import tcds.or.tcdsapp.Adapter.OrderAdapter;
import tcds.or.tcdsapp.Model.Order;
import tcds.or.tcdsapp.Retrofit.TcdsAPI;
import tcds.or.tcdsapp.Utils.Common;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Collections;
import java.util.List;

import static tcds.or.tcdsapp.mainapp.PhoneVerificationActivity.MyPREFERENCES;

public class MyOrdersActivity extends AppCompatActivity {
    TcdsAPI mService;
    CompositeDisposable compositeDisposable;
    RecyclerView recycler_orders;

    String   usernumberverified;
    public String TAG="ORDERActivity";
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);

        SharedPreferences preferences = getApplicationContext().getSharedPreferences(MyPREFERENCES,
                Context.MODE_PRIVATE);
        usernumberverified = preferences.getString("verifiedphone", null);
        mService= Common.getAPI();
        compositeDisposable = new CompositeDisposable();
        progressBar = findViewById(R.id.progressBar);

        recycler_orders=findViewById(R.id.recyclerview_orders);
        recycler_orders.setLayoutManager(new LinearLayoutManager(this));
        recycler_orders.setHasFixedSize(true);



        loadOrder("0");
    }

    private void loadOrder(String statusCode) {


        Log.d(TAG,"VERIFYDNAMABA: "+usernumberverified);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setIndeterminate(true);
        compositeDisposable.add(mService.getOrder("+255673444029", statusCode)

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Order>>() {
                    @Override
                    public void accept(List<Order> order) throws Exception {

                        if (order.isEmpty()){

                            Toasty.info(MyOrdersActivity.this, "Currently there is no Orders for this Status", Toast.LENGTH_LONG, true).show();
                        }

                        displayOrderList(order);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                        Toast.makeText(MyOrdersActivity.this, "Error: "+throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d(TAG,"ERORRRR: "+throwable.getMessage());

                    }
                })

        );
    }

    private void displayOrderList(List<Order> orders) {
        Collections.reverse(orders);
        OrderAdapter orderApter=new OrderAdapter(this,orders);
        recycler_orders.setAdapter(orderApter);
        progressBar.setVisibility(View.GONE);
    }

}
