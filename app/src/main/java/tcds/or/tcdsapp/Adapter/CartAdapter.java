package tcds.or.tcdsapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import tcds.or.tcdsapp.BookDetailsActivity;
import tcds.or.tcdsapp.Database.ModelDB.Cart;
import tcds.or.tcdsapp.R;
import tcds.or.tcdsapp.Utils.Common;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    Context context;
    List<Cart> cartList;
    private OnNoteListener mOnNoteListener;

    public CartAdapter(Context context, List<Cart> cartList, OnNoteListener mOnNoteListener) {
        this.context = context;
        this.cartList = cartList;
        this.mOnNoteListener = mOnNoteListener;

    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.cart_item_layout, parent, false);

        return new CartViewHolder(itemView, mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final CartViewHolder holder, final int position) {

        final DecimalFormat decimalFormat = new DecimalFormat("#");
        decimalFormat.setGroupingUsed(true);
        decimalFormat.setGroupingSize(3);

        Picasso.with(context)
                .load(cartList.get(position).link)
                .into(holder.img_product);
        holder.txt_amount.setNumber(String.valueOf(cartList.get(position).amount));
        holder.txt_price.setText(new StringBuilder("Tsh ").append(decimalFormat.format((cartList.get(position).price))));
        holder.product_detail.setText(cartList.get(position).productdetails);
        holder.txt_prodct_name.setText(new StringBuilder(cartList.get(position).name)
                .append(" x")
                .append(cartList.get(position).amount)

        );

        //Log.d("bonafideGorl","CurrentCartRestPHone: "+Common.currentcart.restaurantname);

        //get price of one cup with all options

        final double priceOfOneCup = cartList.get(position).price / cartList.get(position).amount;


        //Auto save to cart when user change the amount
        holder.txt_amount.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                Cart cart = cartList.get(position);
                cart.amount = newValue;
                cart.price = Double.valueOf(Math.round(priceOfOneCup * newValue));
                Common.cartRepository.updateToCart(cart);
                holder.txt_price.setText(new StringBuilder("Tsh ").append(cartList.get(position).price));
            }
        });

        holder.wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String applink = "https://play.google.com/store/apps/details?id=tcds.or.tcdsapp";  //www.fursane.com.fursanet
                String productDetails = cartList.get(position).productdetails;
                String productname = cartList.get(position).name;
                String productprice = decimalFormat.format(Float.parseFloat(String.valueOf(cartList.get(position).price)));
                String invitemessage = productname + " " + productprice + "\n" + productDetails + "\n\n SASA UNAWEZA KUNUNUA VITABU KUPITIA TCDS App Online\n\nDownload it today:" + applink;
                if (Build.VERSION.SDK_INT >= 24) {
                    try {
                        Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                        m.invoke(null);
                        shareItem(cartList.get(position).link, "TCDS BOOKS" + "\n\n" + invitemessage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }


    public void shareItem(String url, final String message) {
        Picasso.with(context).load(url).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("image/*");
                i.putExtra(Intent.EXTRA_STREAM, getLocalBitmapUri(bitmap));
                i.putExtra(Intent.EXTRA_TEXT, message);
                context.startActivity(Intent.createChooser(i, "Share  Product"));
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
            }
        });
    }

    public Uri getLocalBitmapUri(Bitmap bmp) {
        Uri bmpUri = null;
        try {
            File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }


    @Override
    public int getItemCount() {
        return cartList.size();
    }


    public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView img_product;
        FrameLayout remove, wishlist;
        TextView txt_prodct_name, txt_price, product_detail;
        ElegantNumberButton txt_amount;
        public RelativeLayout view_Background;
        public LinearLayout view_Foreground;
        OnNoteListener mOnNoteListener;

        public CartViewHolder(View itemView, OnNoteListener onNoteListener) {
            super(itemView);

            img_product = itemView.findViewById(R.id.img_productt);
            txt_amount = itemView.findViewById(R.id.txt_amount);
            txt_prodct_name = itemView.findViewById(R.id.txt_productname);
            txt_price = itemView.findViewById(R.id.txtx_price);
            remove = itemView.findViewById(R.id.remove);
            wishlist = itemView.findViewById(R.id.wishlist);
            product_detail = itemView.findViewById(R.id.product_detail);
            //txt_restaurantname=itemView.findViewById(R.id.txt_restaurantname);

//            view_Background=itemView.findViewById(R.id.view_background);
//            view_Foreground=itemView.findViewById(R.id.view_foreground);

            mOnNoteListener = onNoteListener;

            //itemView.setOnClickListener(this);
            remove.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            Log.d("yeuww", "onClick: " + getAdapterPosition());
            mOnNoteListener.onNoteClick(getAdapterPosition());
        }
    }

    public void removeItem(int position) {
        cartList.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(Cart item, int position) {
        cartList.add(position, item);
        notifyItemInserted(position);
    }

    public interface OnNoteListener {
        void onNoteClick(int position);
    }

}
