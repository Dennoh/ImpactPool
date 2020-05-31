package tcds.or.tcdsapp.mainapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import tcds.or.tcdsapp.R;

/**
 * Created by DENC on 6/1/2016.
 */
public class RecyclerViewAdapter_EconomicSectors extends RecyclerView.Adapter<RecyclerViewAdapter_EconomicSectors.StockViewHolder> {
    List<Getter_EconomicSectors> productGetters;
    private final Context context;


    public RecyclerViewAdapter_EconomicSectors(List<Getter_EconomicSectors> paramList, Context context) {
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
        paramStockViewHolder.tvactivitygroup.setText(paramInt + 1 + ". " + productGetters.get(paramInt).getActivitygroup());
        paramStockViewHolder.tvactivityclass.setText(productGetters.get(paramInt).getActivityclass());
    }

    public StockViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt) {
        return new StockViewHolder(LayoutInflater.from(paramViewGroup.getContext()).inflate(R.layout.row_economic_sector, paramViewGroup, false));
    }

    public static class StockViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView tvactivitygroup, tvactivityclass;

        StockViewHolder(View paramView) {
            super(paramView);
            cv = paramView.findViewById(R.id.c_view);
            tvactivitygroup = paramView.findViewById(R.id.tvactivitygroup);
            tvactivityclass = paramView.findViewById(R.id.tvactivityclass);

        }
    }


}

