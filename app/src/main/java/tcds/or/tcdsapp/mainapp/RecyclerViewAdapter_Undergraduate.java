package tcds.or.tcdsapp.mainapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
public class RecyclerViewAdapter_Undergraduate extends RecyclerView.Adapter<RecyclerViewAdapter_Undergraduate.StockViewHolder> {
    List<Getter_Undergraduate> productGetters;
    private final Context context;

    public RecyclerViewAdapter_Undergraduate(List<Getter_Undergraduate> paramList, Context context) {
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
        paramStockViewHolder.tvProgramme.setText(paramInt+1 +". "+ productGetters.get(paramInt).getProgramme());
        paramStockViewHolder.tvuniversity.setText(" "+productGetters.get(paramInt).getUniversity());
        paramStockViewHolder.tvMinInstAdmPoints.setText("   Min Adm Pnts: " + productGetters.get(paramInt).getMinInstAdmPoints());
        paramStockViewHolder.tvAdmCapacity.setText("    Capacity: " + productGetters.get(paramInt).getAdmCapacity());
        paramStockViewHolder.tvProgDuration.setText("   Duration: " + productGetters.get(paramInt).getProgDuration());
        paramStockViewHolder.tvregion.setText("   "+productGetters.get(paramInt).getRegion());

        paramStockViewHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UnderGraduateDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("Programme", productGetters.get(paramInt).getProgramme() + "");
                bundle.putString("Code", productGetters.get(paramInt).getCode() + "");
                bundle.putString("AdmReq", productGetters.get(paramInt).getAdmReq() + "");
                bundle.putString("MinInstAdmPoints", productGetters.get(paramInt).getMinInstAdmPoints() + "");
                bundle.putString("AdmCapacity", productGetters.get(paramInt).getAdmCapacity() + "");
                bundle.putString("ProgDuration", productGetters.get(paramInt).getProgDuration() + "");
                bundle.putString("university", productGetters.get(paramInt).getUniversity() + "");
                bundle.putString("region", productGetters.get(paramInt).getRegion() + "");
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    public StockViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt) {
        return new StockViewHolder(LayoutInflater.from(paramViewGroup.getContext()).inflate(R.layout.row_undergraduate, paramViewGroup, false));
    }

    public static class StockViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView tvProgramme, tvuniversity, tvMinInstAdmPoints, tvAdmCapacity, tvProgDuration, tvregion;

        StockViewHolder(View paramView) {
            super(paramView);
            cv = paramView.findViewById(R.id.c_view);
            tvProgramme = paramView.findViewById(R.id.tvProgramme);
            tvuniversity = paramView.findViewById(R.id.tvuniversity);
            tvMinInstAdmPoints = paramView.findViewById(R.id.tvMinInstAdmPoints);
            tvAdmCapacity = paramView.findViewById(R.id.tvAdmCapacity);
            tvProgDuration = paramView.findViewById(R.id.tvProgDuration);
            tvregion = paramView.findViewById(R.id.tvregion);
        }
    }
}

