package tcds.or.tcdsapp.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import tcds.or.tcdsapp.BookDetailsActivity;
import tcds.or.tcdsapp.CustomFilter;
import tcds.or.tcdsapp.Model.Book;
import tcds.or.tcdsapp.R;
import tcds.or.tcdsapp.Utils.Common;


public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder> implements Filterable {
    Context context;
    public List<Book> bookList, filterList;
    CustomFilter filter;


    public ProductsAdapter(Context context, List<Book> bookList, List<Book> filterList) {
        this.context = context;
        this.bookList = bookList;
        this.filterList = filterList;

    }


    @Override
    public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.book_item, null);
        return new ProductsViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ProductsViewHolder holder, final int position) {
        Picasso.with(context)
                .load(bookList.get(position).getLink())
                .into(holder.img_product);

        holder.txtbookname.setText(bookList.get(position).getName());
        holder.txtauthor.setText(bookList.get(position).getBook_publisher());


        holder.img_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Common.currentProduct = bookList.get(position);
                context.startActivity(new Intent(context, BookDetailsActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    //RETURN FILTER OBJ
    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new CustomFilter(filterList, this);
        }

        return filter;
    }


    public class ProductsViewHolder extends RecyclerView.ViewHolder {

        ImageView img_product;
        TextView txtbookname, txtauthor;


        public ProductsViewHolder(View itemView) {
            super(itemView);

            txtbookname = itemView.findViewById(R.id.item_movie_title);
            txtauthor = itemView.findViewById(R.id.textView);
            img_product = itemView.findViewById(R.id.item_movie_img);


        }


    }


    public class BannerViewHolder {

    }
}
