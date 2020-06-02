package tcds.or.tcdsapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import tcds.or.tcdsapp.Interface.ItemClickListener;
import tcds.or.tcdsapp.Model.Order;
import tcds.or.tcdsapp.OrderDetailActivity;
import tcds.or.tcdsapp.R;
import tcds.or.tcdsapp.Utils.Common;


public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

        Context context;
    List<Order> orderList;

    public OrderAdapter(Context context, List<Order> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.order_item_layout,parent,false);

        return new OrderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, final int position) {
       // holder.txt_order_date.setText(orderList.get(position).getOrderDate());

        final DecimalFormat decimalFormat = new DecimalFormat("#");
        decimalFormat.setGroupingUsed(true);
        decimalFormat.setGroupingSize(3);

        holder.txt_order_deliverycost.setText(new StringBuilder("Tsh ").append(orderList.get(position).getDeliveryPrice()));
        holder.txt_order_productPrice.setText(new StringBuilder("Tsh ").append(orderList.get(position).getOriginalPrice()));
       // holder.txt_order_price.setText(new StringBuilder("Tsh ").append(orderList.get(position).getOrderPrice()));
        holder.txt_order_price.setText(new StringBuilder("Tsh ").append(decimalFormat.format((orderList.get(position).getOrderPrice()))));
        holder.txt_order_address.setText(orderList.get(position).getOrderAddress());
        //holder.txt_order_status.setText((Common.convertCodeToStatus(orderList.get(position).getOrderStatus())));

        SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
        Date MyDate = null;
        try {
            MyDate = newDateFormat.parse(orderList.get(position).getOrderDate());
        } catch (ParseException e) {
            e. printStackTrace();
        }
        newDateFormat.applyPattern("EE, d MMM yyyy hh:mm ");

        holder.txt_order_date.setText(newDateFormat.format(MyDate));


        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date past = format.parse(orderList.get(position).getOrderDate());
            Date now = new Date();
            long seconds = TimeUnit.MILLISECONDS.toSeconds(now.getTime() - past.getTime());
            long minutes = TimeUnit.MILLISECONDS.toMinutes(now.getTime() - past.getTime());
            long hours = TimeUnit.MILLISECONDS.toHours(now.getTime() - past.getTime());
            long days = TimeUnit.MILLISECONDS.toDays(now.getTime() - past.getTime());

            if (seconds < 60) {
                //System.out.println(seconds+" seconds ago");
                holder.txt_order_date2.setText(seconds + " seconds ago");
                Log.e("mdaaaaaa", seconds + " seconds ago");
            } else if (minutes < 60) {
                // System.out.println(minutes+" minutes ago");
                holder.txt_order_date2.setText(minutes + " minutes ago");

                Log.e("mdaaaaaa", minutes + " minutes ago");

            } else if (hours < 24) {
                // System.out.println(hours+" hours ago");

                holder.txt_order_date2.setText(hours + " hours ago");
                Log.e("mdaaaaaa", hours + " hours ago");

            } else {
                if (days == 1) {
                    holder.txt_order_date2.setText(days + " day ago");

                    Log.e("mdaaaaaa", days + " day ago");

                } else {

                    holder.txt_order_date2.setText(days + " days ago");
                    Log.e("mdaaaaaa", days + " days ago");
                }

            }
        } catch (Exception j) {
            j.printStackTrace();
        }


        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();
                Common.currentOder=orderList.get(position);
                context.startActivity(new Intent(context, OrderDetailActivity.class));



            }
        });


    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }


    public class OrderViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{


        public TextView txt_order_date,txt_order_status,txt_order_address,txt_order_comment,txt_order_price,txt_order_date2,txt_order_productPrice,txt_order_deliverycost;

        ItemClickListener itemClickListener;
        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }



        public OrderViewHolder(View itemView) {
            super(itemView);
            txt_order_date=itemView.findViewById(R.id.txt_order_date);
            txt_order_status=itemView.findViewById(R.id.txt_order_status);
            txt_order_address=itemView.findViewById(R.id.txt_order_address);
            txt_order_price=itemView.findViewById(R.id.txt_order_price);
            txt_order_date2=itemView.findViewById(R.id.txt_order_date2);
            txt_order_productPrice=itemView.findViewById(R.id.txt_order_productPrice);
            txt_order_deliverycost=itemView.findViewById(R.id.txt_order_deliverycost);
          itemView.setOnClickListener(this);


        }


        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view);

        }
    }

}
