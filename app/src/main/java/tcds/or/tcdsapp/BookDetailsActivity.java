package tcds.or.tcdsapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import de.mateware.snacky.Snacky;
import tcds.or.tcdsapp.Database.ModelDB.Cart;
import tcds.or.tcdsapp.Utils.Common;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;

public class BookDetailsActivity extends AppCompatActivity {

    TextView txtbooktitle, authorName, txtbookdeatils, productPrice, txt_addtoCart;
    ImageView detail_movie_img;
    double finalPrice;
    String producttype;

    DecimalFormat decimalFormat;
    ImageView category_share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        decimalFormat = new DecimalFormat("#");
        decimalFormat.setGroupingUsed(true);
        decimalFormat.setGroupingSize(3);


        category_share = findViewById(R.id.category_share);
        txtbooktitle = findViewById(R.id.productName);
        txtbookdeatils = findViewById(R.id.txt_productdestails);
        productPrice = findViewById(R.id.productPrice);
        authorName = findViewById(R.id.authorName);
        txt_addtoCart = findViewById(R.id.txt_addtoCart);
        detail_movie_img = findViewById(R.id.category_image);

        txtbooktitle.setText(Common.currentProduct.getName());
        authorName.setText("Book Author: " + Common.currentProduct.getBook_publisher());
        txtbookdeatils.setText(Common.currentProduct.getDescription());
        productPrice.setText("Tsh " + decimalFormat.format(Float.parseFloat(Common.currentProduct.getPrice())));

        Picasso.with(this)
                .load(Common.currentProduct.getLink())
                .into(detail_movie_img);

        txt_addtoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddToCartDialog();
            }
        });
        category_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String applink = "https://play.google.com/store/apps/details?id=tcds.or.tcdsapp";  //www.fursane.com.fursanet
                String productDetails = Common.currentProduct.getDescription();
                String productname = Common.currentProduct.getName();
                String productprice = decimalFormat.format(Float.parseFloat(Common.currentProduct.getPrice()));
                String invitemessage = productname + " " + productprice + "\n" + productDetails + "\n\n SASA UNAWEZA KUNUNUA VITABU KUPITIA TCDS App Online\n\nDownload it today:" + applink;
                if (Build.VERSION.SDK_INT >= 24) {
                    try {
                        Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                        m.invoke(null);
                        shareItem(Common.currentProduct.getLink(), "TCDS BOOKS" + "\n\n" + invitemessage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    public void shareItem(String url, final String message) {
        Picasso.with(BookDetailsActivity.this).load(url).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("image/*");
                i.putExtra(Intent.EXTRA_STREAM, getLocalBitmapUri(bitmap));
                i.putExtra(Intent.EXTRA_TEXT, message);
                startActivity(Intent.createChooser(i, "Share  Product"));
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
            File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }

    private void showAddToCartDialog() {

        final AlertDialog builder = new AlertDialog.Builder(BookDetailsActivity.this).create();
        View itemView = LayoutInflater.from(BookDetailsActivity.this)
                .inflate(R.layout.add_to_cart_layout, null);


        ImageView img_product_dialog = itemView.findViewById(R.id.img_cart_product);
        final ElegantNumberButton txt_count = itemView.findViewById(R.id.txt_count);

        final TextView txt_product_dialog = itemView.findViewById(R.id.txt_cart_product_name);


        final TextView txt_product_price = itemView.findViewById(R.id.txt_cart_product_price);
        Button buttonOkay = itemView.findViewById(R.id.buttonOkay);

        Picasso.with(this).load(Common.currentProduct.getLink()).into(img_product_dialog);


        txt_product_dialog.setText(new StringBuilder(Common.currentProduct.getName())
                .append(" x")
                .append(txt_count.getNumber())

        );


        final double price = (Double.parseDouble(Common.currentProduct.getPrice()) * Double.parseDouble(txt_count.getNumber()));


        finalPrice = Math.round(price);
        txt_product_price.setText(new StringBuilder("Tsh ").append(decimalFormat.format((finalPrice))));
        final double aaa = finalPrice;

        txt_count.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {

                double bb = Double.valueOf(Math.round(aaa * newValue));
                finalPrice = bb;
                String newPrice = String.valueOf(Double.valueOf(finalPrice));


                txt_product_price.setText(new StringBuilder("Tsh ").append(decimalFormat.format(Float.parseFloat(newPrice))));


                txt_product_dialog.setText(new StringBuilder(Common.currentProduct.getName())
                        .append(" x")
                        .append(newValue));

                finalPrice = bb;


            }
        });


        builder.setView(itemView);


        buttonOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Log.e("DAAAAAAAAAAAA", "weka data ");
                //add
                try {

//                   //Add to Sqlite
//                   //Create new Cart Item
//
                    final Cart cartItem = new Cart();

                    cartItem.amount = Integer.parseInt(txt_count.getNumber());
                    cartItem.name = Common.currentProduct.getName();
                    cartItem.price = finalPrice;
                    cartItem.link = Common.currentProduct.getLink();
                    cartItem.productdetails = Common.currentProduct.getDescription();
                    cartItem.booktype = Common.currentProduct.getBook_type();
                    cartItem.bookauthor = Common.currentProduct.getBook_publisher();
                    cartItem.id = Integer.parseInt(Common.currentProduct.getId());


                    Log.d("LOGRESTFOOD", "AMOUNT:  " + txt_count.getNumber());


                    //Add to DB


                    Common.cartRepository.insertToCart(cartItem);

                    Log.d("LOGRESTFOOD", new Gson().toJson(cartItem));

                    //Toast.makeText(ProductDetailsActivity.this, "Success saved to your Cart", Toast.LENGTH_SHORT).show();

//                    Toasty.success(ProductDetailsActivity.this, "Success saved to your Cart", Toast.LENGTH_LONG, true).show();


                    builder.dismiss();
//                        //Toast.makeText(context, "Save item to cart success", Toast.LENGTH_LONG).show();
//                        context.startActivity(new Intent(context, CartActivity.class));


//                    Snacky.builder()
//
//                            .setActivity((Activity) BookDetailsActivity.this)
//                            .setMaxLines(2)
//                            .setDuration(Snacky.LENGTH_INDEFINITE)
//                            .setText("Save item to cart success")
//                            .centerText()
//                            .setBackgroundColor(Color.parseColor("#000000"))
//                            .setTextColor(Color.parseColor("#FFFFFF"))
//                            .setActionText("View Item")
//                            .setActionTextColor(Color.parseColor("#FFFFFF"))
//                            .setActionClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    startActivity(new Intent(BookDetailsActivity.this, CartActivity.class));
//
//                                    //   context.startActivity(new Intent(context, CartActivity.class));
//
//                                }
//                            })
//                            .setActionTextSize(14)
//                            .setMaxLines(4)
//                            .centerText()
//                            .setActionTextTypefaceStyle(Typeface.BOLD)
//                            .info()
//                            .show();

                    // Toast.makeText(context, "Save item to cart success", Toast.LENGTH_LONG).show();
                    //  context.startActivity(new Intent(context, CartActivity.class));
                    startActivity(new Intent(BookDetailsActivity.this, CartActivity.class));


                } catch (Exception ex) {
                    Log.d("LOGRESTFOOD", ex.getMessage());
                    Toast.makeText(BookDetailsActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                }


                // Common.cartRepository.getRestaurantByName("Mwambao restaurant");


            }
        });


        builder.show();


    }
}
