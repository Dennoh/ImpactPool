package tcds.or.tcdsapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import tcds.or.tcdsapp.Database.ModelDB.Cart;
import tcds.or.tcdsapp.R;


public class MyOrderNowCartAdapter extends RecyclerView.Adapter<MyOrderNowCartAdapter. MyOrderNowCartViewHolder> {

    Context context;
    List<Cart> cartList;

    public MyOrderNowCartAdapter(Context context, List<Cart> cartList) {
        this.context = context;
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public MyOrderNowCartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(context).inflate(R.layout.mycartordernow_item,parent,false);

        return new MyOrderNowCartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyOrderNowCartViewHolder holder, final int position) {

        final DecimalFormat decimalFormat = new DecimalFormat("#");
        decimalFormat.setGroupingUsed(true);
        decimalFormat.setGroupingSize(3);


        Picasso.with(context)
                .load(cartList.get(position).link)
                .into(holder.img_product);
      // holder.txt_price.setText(new StringBuilder("Tsh ").append(cartList.get(position).price));
       holder.txt_price.setText(new StringBuilder("Tsh ").append(decimalFormat.format((cartList.get(position).price))));
        holder.product_detail.setText(cartList.get(position).productdetails);
        holder.txt_prodct_name.setText(cartList.get(position).name);
//                .append(" x")
//                .append(cartList.get(position).amount)



      //  );
        holder.txt_amount_.setText(String.valueOf(cartList.get(position).amount));


    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class MyOrderNowCartViewHolder extends RecyclerView.ViewHolder{
        ImageView img_product;
        TextView txt_prodct_name,product_detail,txt_price,txt_amount_;


        public MyOrderNowCartViewHolder(@NonNull View itemView) {
            super(itemView);

            img_product=itemView.findViewById(R.id.img_productt);
            txt_prodct_name=itemView.findViewById(R.id.txt_productname);
            txt_amount_=itemView.findViewById(R.id.txt_amount_);
            txt_price=itemView.findViewById(R.id.txtx_price);
            product_detail=itemView.findViewById(R.id.product_detail);
        }
    }
}
