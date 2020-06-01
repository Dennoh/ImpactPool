package tcds.or.tcdsapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import tcds.or.tcdsapp.Database.ModelDB.Cart;
import tcds.or.tcdsapp.R;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.OrderDetailViewHolder>{

    Context context;
    List<Cart> cartList;


    public OrderDetailAdapter(Context context, List<Cart> cartList) {
        this.context = context;
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public OrderDetailViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView= LayoutInflater.from(context).inflate(R.layout.order_detail_layout,viewGroup,false);

        return new OrderDetailAdapter.OrderDetailViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailViewHolder holder, int position) {


        Picasso.with(context)
                .load(cartList.get(position).link)
                .into(holder.img_product);
        holder.txt_price.setText(new StringBuilder("TSh ").append(cartList.get(position).price));
        holder.txt_restaurantname.setText(cartList.get(position).restaurantname);
        holder.txt_prodct_name.setText(new StringBuilder(cartList.get(position).name)
                        .append(" x")
                        .append(cartList.get(position).amount)
        );




    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }


    class OrderDetailViewHolder extends RecyclerView.ViewHolder{
        ImageView img_product;
        TextView txt_prodct_name,txt_restaurantname,txtx_size,txt_price;


        public OrderDetailViewHolder(View itemView) {
            super(itemView);

            img_product=itemView.findViewById(R.id.img_productt);
            txt_prodct_name=itemView.findViewById(R.id.txt_productname);
            txt_price=itemView.findViewById(R.id.txtx_price);
            txt_restaurantname=itemView.findViewById(R.id.txt_restaurantname);

        }
    }
}
