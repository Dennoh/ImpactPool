package tcds.or.tcdsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import tcds.or.tcdsapp.Adapter.OrderDetailAdapter;
import tcds.or.tcdsapp.Database.ModelDB.Cart;
import tcds.or.tcdsapp.Utils.Common;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class OrderDetailActivity extends AppCompatActivity {
    TextView txt_order_cost,txt_order_status,txt_order_prodcytPrice,txt_order_delivryCost;
    RecyclerView recycler_order_details;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

//        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//        getSupportActionBar().setDisplayShowCustomEnabled(true);
//        getSupportActionBar().setCustomView(R.layout.my_orders_details_layout);


//        View view =getSupportActionBar().getCustomView();
//        ImageView imageViewBackArrow=view.findViewById(R.id.imageViewBack);
//        imageViewBackArrow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
        txt_order_cost=findViewById(R.id.txt_order_pricee);
        txt_order_status=findViewById(R.id.txt_order_status);
        txt_order_prodcytPrice=findViewById(R.id.txt_order_prodcytPrice);
        txt_order_delivryCost=findViewById(R.id.txt_order_delivryCost);

        recycler_order_details=findViewById(R.id.recycler_order_detailss);
        recycler_order_details.setLayoutManager(new LinearLayoutManager(this));
        recycler_order_details.setHasFixedSize(true);

        txt_order_cost.setText(new StringBuilder("Tsh ").append(Common.currentOder.getOrderPrice())+"");
        txt_order_prodcytPrice.setText(new StringBuilder("Tsh ").append(Common.currentOder.getOriginalPrice()+""));
        txt_order_delivryCost.setText(new StringBuilder("Tsh ").append(Common.currentOder.getDeliveryPrice()+""));
        txt_order_status.setText(Common.convertCodeToStatus(Common.currentOder.getOrderStatus()));

        displayOrderDetail();
    }
    private void displayOrderDetail() {

        Log.e("43444444yeuni","currentOrder:  "+Common.currentOder.getOrderDetail());
        List<Cart> orderDetail = new Gson().fromJson(Common.currentOder.getOrderDetail(),
                new TypeToken<List<Cart>>(){}.getType());
        recycler_order_details.setAdapter(new OrderDetailAdapter(this,orderDetail));





    }
}

