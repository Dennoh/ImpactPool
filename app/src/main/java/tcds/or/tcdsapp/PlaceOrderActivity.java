package tcds.or.tcdsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import es.dmoral.toasty.Toasty;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tcds.or.tcdsapp.Adapter.MyOrderNowCartAdapter;
import tcds.or.tcdsapp.Database.ModelDB.Cart;
import tcds.or.tcdsapp.Retrofit.TcdsAPI;
import tcds.or.tcdsapp.Utils.Common;
import tcds.or.tcdsapp.mainapp.MainActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static tcds.or.tcdsapp.mainapp.PhoneVerificationActivity.MyPREFERENCES;

public class PlaceOrderActivity extends AppCompatActivity {

    private static final String TAG = "PlaceOrderrrre";
    RecyclerView recycler_place_order;
    TcdsAPI mService;
    List<Cart> cartList = new ArrayList<>();
    CompositeDisposable compositeDisposable;
    MyOrderNowCartAdapter cartAdapter;
    ProgressDialog dialog;
    TextView txt_subtotal, txt_total;
    EditText txtview_address;
    MaterialEditText edittxt_comment;
    String usercomment;
    String orderDate;
    TextView btn_orderNow;
    String userlocationFromStart;
    String tokenyavendor;
    float newDeliveryCost = 0;

    float totalAmount;

    float toSendTotalPrice;


    public static final String OrderPAY_PREFERENCES = "ORDERPAY_001";

    SharedPreferences orderpay_sharedpreferences;
    SharedPreferences.Editor orderPay_editor;

    String selectedDelievryLoc;
    TextView delievrycosts, textviewNetPay;
    String deliveryOption;
    DecimalFormat decimalFormat;
    Spinner spinnerDelibery;
    int delivery = 0;
    int net_tt;
    String deliverystatus = "notselected";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);

        txt_subtotal = findViewById(R.id.txt_subtotal);
        txt_total = findViewById(R.id.txt_total);
        txtview_address = findViewById(R.id.txtview_address);
        edittxt_comment = findViewById(R.id.edittxt_comment);
        delievrycosts = findViewById(R.id.delievrycosts);
        textviewNetPay = findViewById(R.id.textviewNetPay);
        btn_orderNow = findViewById(R.id.btn_orderNow);
        spinnerDelibery = findViewById(R.id.spinnerDelibery);
        mService = Common.getAPI();
        decimalFormat = new DecimalFormat("#.00");
        decimalFormat.setGroupingUsed(true);
        decimalFormat.setGroupingSize(3);


        compositeDisposable = new CompositeDisposable();
        recycler_place_order = findViewById(R.id.place_order_recycler);

        recycler_place_order.setLayoutManager(new LinearLayoutManager
                (this, LinearLayoutManager.VERTICAL, false));
        recycler_place_order.setHasFixedSize(true);
        // txt_subtotal.setText(new StringBuilder("Tsh ").append(Common.cartRepository.sumPrice()));

        txt_subtotal.setText(new StringBuilder("Tsh ").append(decimalFormat.format(Float.parseFloat(String.valueOf(Common.cartRepository.sumPrice())))));
        final int sbt = Math.round(Common.cartRepository.sumPrice());
        int tt = sbt + delivery;
        textviewNetPay.setText("Tsh " + decimalFormat.format(tt));

        totalAmount = Common.cartRepository.sumPrice();
        recycler_place_order.setLayoutManager(new LinearLayoutManager(this));
        recycler_place_order.setHasFixedSize(true);

        loardCartItems();

        final String[] deliveryOptions = {"Select Delivery Option", "Delivery to Specific Address", "Arusha Pickup Point", "Dar es Salaam Pickup Point", "Dodoma Pickup Point", "Morogoro Pickup Point", "Iringa Pickup Point"
        };
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(PlaceOrderActivity.this, R.layout.row_spinner, R.id.textViewDealerName, deliveryOptions);
        spinnerDelibery.setAdapter(arrayAdapter);
        spinnerDelibery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                deliveryOption = deliveryOptions[position];
                if (deliveryOption.equals("Delivery to Specific Address")) {
                    deliverystatus = "selected";
                    delievrycosts.setText("Tsh 5,000");
                    delivery = 5000;
                    net_tt = sbt + delivery;
                    textviewNetPay.setText("Tsh " + decimalFormat.format(net_tt));
                    txtview_address.setVisibility(View.VISIBLE);
                } else if (deliveryOption.equals("Arusha Pickup Point")) {
                    deliverystatus = "selected";

                    txtview_address.setVisibility(View.GONE);
                    delievrycosts.setText("Tsh 00");
                    delivery = 0;
                    net_tt = sbt + delivery;
                    textviewNetPay.setText("Tsh " + decimalFormat.format(net_tt));
                } else if (deliveryOption.equals("Dar es Salaam Pickup Point")) {
                    deliverystatus = "selected";

                    txtview_address.setVisibility(View.GONE);
                    delievrycosts.setText("Tsh 00");
                    delivery = 0;
                    net_tt = sbt + delivery;
                    textviewNetPay.setText("Tsh " + decimalFormat.format(net_tt));
                } else if (deliveryOption.equals("Dodoma Pickup Point")) {
                    deliverystatus = "selected";

                    txtview_address.setVisibility(View.GONE);
                    delievrycosts.setText("Tsh 00");
                    delivery = 0;
                    net_tt = sbt + delivery;
                    textviewNetPay.setText("Tsh " + decimalFormat.format(net_tt));
                } else if (deliveryOption.equals("Morogoro Pickup Point")) {
                    deliverystatus = "selected";

                    txtview_address.setVisibility(View.GONE);
                    delievrycosts.setText("Tsh 00");
                    delivery = 0;
                    net_tt = sbt + delivery;
                    textviewNetPay.setText("Tsh " + decimalFormat.format(net_tt));
                } else if (deliveryOption.equals("Iringa Pickup Point")) {
                    deliverystatus = "selected";

                    txtview_address.setVisibility(View.GONE);
                    delievrycosts.setText("Tsh 00");
                    delivery = 0;
                    net_tt = sbt + delivery;
                    textviewNetPay.setText("Tsh " + decimalFormat.format(net_tt));
                } else {

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_orderNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (deliverystatus.equals("notselected")) {
                    Toasty.error(getApplicationContext(), "Selected Delivery Option", Toast.LENGTH_LONG, true).show();

                } else {
                    placeOrderNow();
                }
            }
        });
    }


    private void loardCartItems() {
        compositeDisposable.add(
                Common.cartRepository.getCartItems()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Consumer<List<Cart>>() {
                                       @Override
                                       public void accept(List<Cart> carts) throws Exception {
                                           displayCartItem(carts);

                                       }
                                   }

                        )
        );
    }

    private void displayCartItem(List<Cart> carts) {
        cartList = carts;
        cartAdapter = new MyOrderNowCartAdapter(this, carts);
        recycler_place_order.setAdapter(cartAdapter);


    }


    private void placeOrderNow() {


        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
        Date date = new Date();
        orderDate = dateFormat.format(date);

        Log.e(TAG, orderDate + " : ORDER TAREHEEE");


        Log.d("priceeee", "SUMPRICE: " + Common.cartRepository.sumPrice());

        //  String newUsername,newUserarea,newUserPhone;
//        Log.d(TAG,"userArea: "+userArea);
//        Log.d(TAG,"restaurantJINA: "+restaurantJINA);

        //There is the bug when try to get Common.Current.get Restaurant Name it returns null.
        Common.cartRepository.getCartItems();
        compositeDisposable.add(
                Common.cartRepository.getCartItems()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Consumer<List<Cart>>() {
                            @Override
                            public void accept(List<Cart> carts) throws Exception {
                                sendOrderToServer(orderDate, totalAmount,
                                        carts,
                                        "", "");


//  String newUsername,newUserarea,newUserPhone;

                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Log.d(TAG, "ERRORRRRR333: " + throwable.getMessage());
                                Toasty.error(getApplicationContext(), "Errorr: " + throwable.getMessage(), Toast.LENGTH_LONG, true).show();
                            }
                        })
        );

    }


    private void sendOrderToServer(String orderDate, final float sumPrice, List<Cart> carts, String orderComment,
                                   String orderaddress) {


//        final String   userarea = preferences.getString("userarea", null);
//        final String   username = preferences.getString("username", null);


        dialog = new ProgressDialog(PlaceOrderActivity.this);
        dialog.setMessage("Loading.....");
        dialog.setIndeterminate(true);
        dialog.show();
        if (carts.size() > 0) {
            String orderDetail = new Gson().toJson(carts);
//            for(Cart c : carts){
//                Log.e("natsahaaaaaa","vendorname: "+c.vendorname);
//                Log.e("natsahaaaaaa","vendorId: "+c.vendorId);
//                Log.e("natsahaaaaaa","vendorphone: "+c.vendorphone);
//            }

            SharedPreferences preferences = getApplicationContext().getSharedPreferences(MyPREFERENCES,
                    Context.MODE_PRIVATE);
            String usernumberverified = preferences.getString("verifiedphone", null);


            orderpay_sharedpreferences = getSharedPreferences(OrderPAY_PREFERENCES,
                    Activity.MODE_PRIVATE);
            orderPay_editor = orderpay_sharedpreferences.edit();
            orderPay_editor.putString("verifiedphone", usernumberverified);
            orderPay_editor.putString("orderDate", orderDate);
            orderPay_editor.putString("orderaddress", orderaddress);
            orderPay_editor.putString("sumPrice", net_tt + "");
            orderPay_editor.apply();


            final Cart cart = carts.get(0);


            mService.submitOrder(orderDate, Float.parseFloat(net_tt + ""), orderDetail, edittxt_comment.getText()
                            .toString().trim(), "Delivery Option Selected " + deliveryOption + " " + txtview_address.getText().toString(), "+255673444029",
                    "COD", "No Vendor", "No Vendor")
                    .enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            dialog.dismiss();
                            Log.e("leteeeeee", "yanguuuuq_body: " + response.body());
                            Log.e("leteeeeee", "yanguuuuqmessage: " + response.message());
                            Log.e("leteeeeee", "yanguuuuq_code: " + response.code());


                            //   Toasty.success(PlaceOrderActivity.this, "Order Sent Successful", Toast.LENGTH_LONG, true).show();
                            SendSMS("+255699784877", "Habari" + ", " + " Admin wa TCDS" + " " + "Umepokea Oda mpya. Tafadhali fungua App kuona maelezo zaidi.");
                            //Common.cartRepository.emptyCart();
                            //  startActivity(new Intent(PlaceOrderActivity.this, MainActivity.class));
                            //  finish();

                            Toasty.success(PlaceOrderActivity.this, "Proceed with with payments", Toast.LENGTH_LONG, true).show();
                            Common.cartRepository.emptyCart();
                            startActivity(new Intent(PlaceOrderActivity.this, ThankYouActivity.class));
                            finish();


                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            dialog.dismiss();
                            Log.e("leteeeeee", "ERRORRR111: " + t.getMessage());
                            Toast.makeText(PlaceOrderActivity.this, "Something Went Wrong Please Try again", Toast.LENGTH_LONG).show();

                        }
                    });

        }


    }

    public void SendSMS(String vendorPhone, String txtmessage) {

        compositeDisposable.add(mService.SendSMSBULK(vendorPhone, txtmessage)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<String>() {
                               @Override
                               public void accept(String s) throws Exception {
                                   // Toast.makeText(NewUserProfileRegistrationActivity.this, s, Toast.LENGTH_LONG).show();
                                   Log.e("yaMessage", "MESSAGE: " + s);

                               }
                           }, new Consumer<Throwable>() {
                               @Override
                               public void accept(Throwable throwable) throws Exception {
                                   Log.e("yaMessage", "ERROR MESSAGE: " + throwable.getMessage());


                               }
                           }

                ));


    }

}
