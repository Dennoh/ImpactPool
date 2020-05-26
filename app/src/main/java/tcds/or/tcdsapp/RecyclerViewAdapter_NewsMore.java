package tcds.or.tcdsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class RecyclerViewAdapter_NewsMore extends RecyclerView.Adapter<RecyclerViewAdapter_NewsMore.StockViewHolder> {
    List<Getter_News> productGetters;
    private final Context context;

    public RecyclerViewAdapter_NewsMore(List<Getter_News> paramList, Context context) {
        this.productGetters = paramList;
        this.context = context;
    }

    public int getItemCount() {
        return productGetters.size();
    }

    public void onAttachedToRecyclerView(RecyclerView paramRecyclerView) {
        super.onAttachedToRecyclerView(paramRecyclerView);
    }

    public void onBindViewHolder(final StockViewHolder paramStockViewHolder, final int paramInt) {
        paramStockViewHolder.tvNewsTittle.setText(productGetters.get(paramInt).getNews_tittle());
        paramStockViewHolder.tvNewsDetails.setText(productGetters.get(paramInt).getNews_details());
        paramStockViewHolder.tvNewsDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(context, NewsDetailsActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("tittle", productGetters.get(paramInt).getNews_tittle());
//                bundle.putString("details", productGetters.get(paramInt).getNews_details());
//                bundle.putString("postedon", productGetters.get(paramInt).getNews_postedOn());
//                intent.putExtras(bundle);
//                context.startActivity(intent);
            }
        });
        SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date MyDate = null;
        try {
            MyDate = newDateFormat.parse(productGetters.get(paramInt).getNews_postedOn());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        newDateFormat.applyPattern("EE d MMM yyyy");
        paramStockViewHolder.tvPostedOn.setText("  " + newDateFormat.format(MyDate));
        paramStockViewHolder.tvReadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(context, NewsDetailsActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("tittle", productGetters.get(paramInt).getNews_tittle());
//                bundle.putString("details", productGetters.get(paramInt).getNews_details());
//                bundle.putString("postedon", productGetters.get(paramInt).getNews_postedOn());
//                intent.putExtras(bundle);
//                context.startActivity(intent);
            }
        });
    }

    public StockViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt) {
        return new StockViewHolder(LayoutInflater.from(paramViewGroup.getContext()).inflate(R.layout.row_news_more, paramViewGroup, false));
    }

    public static class StockViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView tvNewsDetails;
        TextView tvPostedOn;
        TextView tvNewsTittle;
        TextView tvReadMore;

        StockViewHolder(View paramView) {
            super(paramView);
            cv = ((CardView) paramView.findViewById(R.id.c_view));
            tvNewsDetails = ((TextView) paramView.findViewById(R.id.tvNewsDetails));
            tvPostedOn = ((TextView) paramView.findViewById(R.id.tvPostedOn));
            tvNewsTittle = ((TextView) paramView.findViewById(R.id.tvNewsTittle));
            tvReadMore = ((TextView) paramView.findViewById(R.id.tvReadMore));
        }
    }
}

