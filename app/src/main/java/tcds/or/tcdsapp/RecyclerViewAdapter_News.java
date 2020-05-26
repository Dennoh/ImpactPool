package tcds.or.tcdsapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by DENC on 6/1/2016.
 */
public class RecyclerViewAdapter_News extends RecyclerView.Adapter<RecyclerViewAdapter_News.StockViewHolder> {
    List<Getter_News> productGetters;
    private final Context context;

    public RecyclerViewAdapter_News(List<Getter_News> paramList, Context context) {
        this.productGetters = paramList;
        this.context = context;
    }

    public int getItemCount() {
        return productGetters.size();
    }

    public void onAttachedToRecyclerView(RecyclerView paramRecyclerView) {
        super.onAttachedToRecyclerView(paramRecyclerView);
    }

    public void onBindViewHolder(final StockViewHolder paramStockViewHolder, int paramInt) {
        paramStockViewHolder.tvNewsDetails.setText(productGetters.get(paramInt).getNews_details());
        SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date MyDate = null;
        try {
            MyDate = newDateFormat.parse(productGetters.get(paramInt).getNews_postedOn());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        newDateFormat.applyPattern("EE d MMM yyyy");
        paramStockViewHolder.tvPostedOn.setText("  " + newDateFormat.format(MyDate));
        paramStockViewHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, NewsActivity.class));
            }
        });
    }

    public StockViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt) {
        return new StockViewHolder(LayoutInflater.from(paramViewGroup.getContext()).inflate(R.layout.row_news, paramViewGroup, false));
    }

    public static class StockViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView tvNewsDetails;
        TextView tvPostedOn;
        ImageView imageViewLogo;

        StockViewHolder(View paramView) {
            super(paramView);
            cv = ((CardView) paramView.findViewById(R.id.c_view));
            tvNewsDetails = ((TextView) paramView.findViewById(R.id.tvNewsDetails));
            tvPostedOn = ((TextView) paramView.findViewById(R.id.tvPostedOn));
            imageViewLogo = paramView.findViewById(R.id.imageViewLogo);
        }
    }
}

