package tcds.or.tcdsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import tcds.or.tcdsapp.Adapter.CartAdapter;
import tcds.or.tcdsapp.Database.ModelDB.Cart;
import tcds.or.tcdsapp.Retrofit.TcdsAPI;
import tcds.or.tcdsapp.Utils.Common;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity implements CartAdapter.OnNoteListener {

    private static final String TAG ="CartActivity" ;
    RecyclerView recycler_cart;
    TcdsAPI mService;
    CartAdapter cartAdapter;
    List<Cart> cartList=new ArrayList<>();
    CompositeDisposable compositeDisposable;
    LinearLayout rootLayout;
    ProgressDialog dialog;
    TextView textViewnothinOnCart;
    TextView txt_PlaceOrder,nothingacarttextview;
    ProgressBar progressBar;

    SharedPreferences mySharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        progressBar = findViewById(R.id.progressBar);
        mService= Common.getAPI();

        compositeDisposable= new CompositeDisposable();

        rootLayout=findViewById(R.id.rootLayout);

        txt_PlaceOrder=findViewById(R.id.txt_PlaceOrder);
        nothingacarttextview=findViewById(R.id.nothingacarttextview);

//        Log.d("qqqwwwww","contCartItemS:  "+Common.cartRepository.countCartItems());

//        if(Common.cartRepository.countCartItems()!=0) //if we do not login,of course Common.currentUser is null
//        {
//
//        }
//
//



        if (Common.cartRepository.countCartItems()==0)
            txt_PlaceOrder.setVisibility(View.INVISIBLE);



        else{
            txt_PlaceOrder.setVisibility(View.VISIBLE);


        }


        if (Common.cartRepository.countCartItems()==0)

            nothingacarttextview.setVisibility(View.VISIBLE);


        else{

            nothingacarttextview.setVisibility(View.INVISIBLE);

        }
        txt_PlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // checkifUserVerified();

                Intent intent = new Intent(CartActivity.this, PlaceOrderActivity.class);
                startActivity(intent);




            }
        });




        recycler_cart=findViewById(R.id.recyclerview_cart);
        recycler_cart.setLayoutManager(new LinearLayoutManager(this));
        recycler_cart.setHasFixedSize(true);
        loardCartItems();
    }

    public void loardCartItems() {
//        progressBar.setVisibility(View.VISIBLE);
//        progressBar.setIndeterminate(true);
        compositeDisposable.add(
                Common.cartRepository.getCartItems()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Consumer<List<Cart>>() {
                                       @Override
                                       public void accept(List<Cart> carts) throws Exception {

                                           displayCartItem(carts);

                                       }
                                   }, new Consumer<Throwable>() {
                                       @Override
                                       public void accept(Throwable throwable) throws Exception {
                                           Log.d(TAG,"ERRORRR : "+throwable.getMessage());
                                           Toast.makeText(CartActivity.this, "Error: "
                                                   +throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                       }
                                   }

                        )
        );
    }


    private void displayCartItem(List<Cart> carts) {
        cartList =carts;
        cartAdapter  = new CartAdapter(this,carts,this);
        recycler_cart.setAdapter(cartAdapter);
        //  progressBar.setVisibility(View.GONE);



    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        compositeDisposable.clear();

        super.onStop();
    }

    @Override
    protected void onResume() {

        super.onResume();
        loardCartItems();
    }

    @Override
    public void onNoteClick(int position) {
        //   Toast.makeText(this, "Imebonyezwa", Toast.LENGTH_SHORT).show();

//        Common.currentMenuCategory = mNotes.get(position);
//        //Toast.makeText(this, "Clicked: "+Common.currentMenuCategory.getName(), Toast.LENGTH_SHORT).show();
//        LoadREstaurantFoodByIdandCategory(Common.currentRestaurant.getId(),Common.currentMenuCategory.getName());


        String name=cartList.get(position).name;
        String jina=cartList.get(position).name;

        // backup of removed item for undo purpose
        final Cart deletedItem=cartList.get(position);
        final int deletedIndex=position;

        // remove the item from recycler view
        // favoriteAdapter.removeItem(viewHolder.getAdapterPosition());


        //delete item fro =m adapter
        cartAdapter.removeItem(deletedIndex);
//
//            //delete from room database
//
        Common.cartRepository.deleteCartItem(deletedItem);

        //showing snack bar with Undo option
        Snackbar snackbar = Snackbar.make(rootLayout,new StringBuilder(name).append("Item Removed From Cart").toString(),
                Snackbar.LENGTH_LONG);
        snackbar.setAction("UNDO", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartAdapter.restoreItem(deletedItem,deletedIndex);
                Common.cartRepository.insertToCart(deletedItem);

            }
        });
        snackbar.setActionTextColor(Color.YELLOW);
        snackbar.show();


    }

//    public  void checkifUserVerified() {
//
//
//        mySharedPreferences = getSharedPreferences(SUCCESSUSER_PREFERENCES, Context.MODE_PRIVATE);
//
//        if (mySharedPreferences.contains("verifiedphone")) {
//            mySharedPreferences = getSharedPreferences(SUCCESSUSER_PREFERENCES, 0);
//            Intent intent = new Intent(CartActivity.this, PlaceOrderActivity.class);
//            startActivity(intent);
//        }else {
//
//            new FancyAlertDialog.Builder(CartActivity.this)
//                    .setTitle("NOT LOGIN?")
//                    .setBackgroundColor(Color.parseColor("#101731"))  //Don't pass R.color.colorvalue
//                    .setMessage("Please Login or Register to Submit order?")
//                    .setNegativeBtnText("No")
//                    .setPositiveBtnBackground(Color.parseColor("#101731"))  //Don't pass R.color.colorvalue
//                    .setPositiveBtnText("Yes")
//                    .setNegativeBtnBackground(Color.parseColor("#101731"))  //Don't pass R.color.colorvalue
//                    .isCancellable(true)
//
//                    .OnPositiveClicked(new FancyAlertDialogListener() {
//                        @Override
//                        public void OnClick() {
//                            startActivity(new Intent(CartActivity.this, PhoneVerificationActivity.class));
//                            finish();
//                            // Toast.makeText(CartActivity.this, "Ok Register ME", Toast.LENGTH_SHORT).show();
//                        }
//                    })
//                    .OnNegativeClicked(new FancyAlertDialogListener() {
//                        @Override
//                        public void OnClick() {
//
//                        }
//                    })
//                    .build();
//        }
//
//
//
//
//    }

}
