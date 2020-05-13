package impactpool.org.impactpool;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
public class RecyclerViewAdapter_LearningInst extends RecyclerView.Adapter<RecyclerViewAdapter_LearningInst.StockViewHolder> {
    List<Getter_LearningInst> productGetters;
    private final Context context;

    public RecyclerViewAdapter_LearningInst(List<Getter_LearningInst> paramList, Context context) {
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
        paramStockViewHolder.tvOfferingInstitution.setText(productGetters.get(paramInt).getOfferingInstitution());
        paramStockViewHolder.tvSector.setText("Main Sector: "+productGetters.get(paramInt).getSector());
        paramStockViewHolder.tvLocation.setText(productGetters.get(paramInt).getRegion() + ", " + productGetters.get(paramInt).getDistrict());
        paramStockViewHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, LearningInstDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("offeringInstitution", productGetters.get(paramInt).getOfferingInstitution() + "");
                bundle.putString("sector", productGetters.get(paramInt).getSector() + "");
                bundle.putString("district", productGetters.get(paramInt).getDistrict() + "");
                bundle.putString("region", productGetters.get(paramInt).getRegion() + "");
                bundle.putString("details", productGetters.get(paramInt).getDetails() + "");
                bundle.putString("id", productGetters.get(paramInt).getId() + "");
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    public StockViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt) {
        return new StockViewHolder(LayoutInflater.from(paramViewGroup.getContext()).inflate(R.layout.row_learning, paramViewGroup, false));
    }

    public static class StockViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView tvOfferingInstitution;
        TextView tvLocation;
        TextView tvSector;

        StockViewHolder(View paramView) {
            super(paramView);
            cv = ((CardView) paramView.findViewById(R.id.c_view));
            tvOfferingInstitution = ((TextView) paramView.findViewById(R.id.tvOfferingInstitution));
            tvLocation = ((TextView) paramView.findViewById(R.id.tvLocation));
            tvSector = ((TextView) paramView.findViewById(R.id.tvSector));
        }
    }
}

