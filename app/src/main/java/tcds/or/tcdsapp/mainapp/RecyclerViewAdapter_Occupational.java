package tcds.or.tcdsapp.mainapp;

import android.content.Context;
import android.util.Log;
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
public class RecyclerViewAdapter_Occupational extends RecyclerView.Adapter<RecyclerViewAdapter_Occupational.StockViewHolder> {
    List<Getter_OccupationalPathways> productGetters;
    private final Context context;

    public RecyclerViewAdapter_Occupational(List<Getter_OccupationalPathways> paramList, Context context) {
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
        paramStockViewHolder.tvunit_label.setText(paramInt + 1 + ". " + productGetters.get(paramInt).getUnit_label());
        paramStockViewHolder.tvDescriptionsofUnits.setText(productGetters.get(paramInt).getUnit_label() + "\n" + productGetters.get(paramInt).getDescriptionsofUnits());
        Log.e("here2323", productGetters.get(paramInt).getDescriptionsofUnits() + "");
    }

    public StockViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt) {
        return new StockViewHolder(LayoutInflater.from(paramViewGroup.getContext()).inflate(R.layout.row_occupational, paramViewGroup, false));
    }

    public static class StockViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView tvunit_label;
        TextView tvDescriptionsofUnits;

        StockViewHolder(View paramView) {
            super(paramView);
            cv = ((CardView) paramView.findViewById(R.id.c_view));
            tvunit_label = ((TextView) paramView.findViewById(R.id.tvunit_label));
            tvDescriptionsofUnits = ((TextView) paramView.findViewById(R.id.tvDescriptionsofUnits));
        }
    }
}

