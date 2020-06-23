package tcds.or.tcdsapp.mainapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
        paramStockViewHolder.tvMajor.setText("  " + productGetters.get(paramInt).getMajor_occupation());
        paramStockViewHolder.tvSubMajor.setText("  " + productGetters.get(paramInt).getSub_major_occupation());
        paramStockViewHolder.tvMinor.setText("  " + productGetters.get(paramInt).getMinor_occupation());

        paramStockViewHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OccupationalDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("getMajor_occupation", productGetters.get(paramInt).getMajor_occupation() + "");
                bundle.putString("getSub_major_occupation", productGetters.get(paramInt).getSub_major_occupation() + "");
                bundle.putString("getMinor_occupation", productGetters.get(paramInt).getMinor_occupation() + "");
                bundle.putString("getUnit_label", productGetters.get(paramInt).getUnit_label() + "");
                bundle.putString("getDescriptionsofUnits", productGetters.get(paramInt).getDescriptionsofUnits() + "");
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    public StockViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt) {
        return new StockViewHolder(LayoutInflater.from(paramViewGroup.getContext()).inflate(R.layout.row_occupational, paramViewGroup, false));
    }

    public static class StockViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView tvunit_label;
        TextView tvDescriptionsofUnits;
        TextView tvMajor;
        TextView tvSubMajor;
        TextView tvMinor;

        StockViewHolder(View paramView) {
            super(paramView);
            cv = ((CardView) paramView.findViewById(R.id.c_view));
            tvunit_label = ((TextView) paramView.findViewById(R.id.tvunit_label));
            tvMajor = ((TextView) paramView.findViewById(R.id.tvMajor));
            tvSubMajor = ((TextView) paramView.findViewById(R.id.tvSubMajor));
            tvMinor = ((TextView) paramView.findViewById(R.id.tvMinor));
        }
    }
}

