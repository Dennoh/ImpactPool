package tcds.or.tcdsapp;


import android.app.Activity;
import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hover.sdk.api.HoverParameters;

import java.text.DecimalFormat;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import de.mateware.snacky.Snacky;
import tcds.or.tcdsapp.mainapp.MainActivity;

import static android.content.Context.CLIPBOARD_SERVICE;
import static tcds.or.tcdsapp.PlaceOrderActivity.OrderPAY_PREFERENCES;


public class DialogPaymentSuccess extends DialogFragment {

    private View root_view;
    Object clipboardService ;
    ClipboardManager clipboardManager;
    LinearLayout linearMpesa,linearTigoPesa,linearAirtel,lineaHalopesa;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root_view = inflater.inflate(R.layout.dialog_payment_success, container, false);
        //final MaterialEditText materialEditUSerProblem = register_layout.findViewById(R.id.editTextName);
        TextView txt_date=root_view.findViewById(R.id.txt_date);
        final TextView sum_price=root_view.findViewById(R.id.sum_price);
        linearMpesa=root_view.findViewById(R.id.linearMpesa);
        lineaHalopesa=root_view.findViewById(R.id.lineaHalopesa);
        linearAirtel=root_view.findViewById(R.id.linearAirtel);
        linearTigoPesa=root_view.findViewById(R.id.linearTigoPesa);
//        TextView location=root_view.findViewById(R.id.location);
//        TextView phone_user=root_view.findViewById(R.id.phone_user);

        // Get clipboard manager object.
        clipboardService =getContext().getSystemService(CLIPBOARD_SERVICE);
        clipboardManager =(ClipboardManager)clipboardService;

        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        decimalFormat.setGroupingUsed(true);
        decimalFormat.setGroupingSize(3);

        SharedPreferences location_sharedpreferences = getActivity().getSharedPreferences(OrderPAY_PREFERENCES, Context.MODE_PRIVATE);
       String userdate = location_sharedpreferences.getString("orderDate", "no date");
       final String user_price = location_sharedpreferences.getString("sumPrice", "no price");
       String user_locationn = location_sharedpreferences.getString("orderaddress", "no location");
       String user_simu = location_sharedpreferences.getString("verifiedphone", "no phone");

        txt_date.setText(userdate);
        if (user_price != null) {
            sum_price.setText(decimalFormat.format(Float.parseFloat(user_price)));
        }
//        location.setText(user_locationn);
//        phone_user.setText(user_simu);

        ((FloatingActionButton) root_view.findViewById(R.id.fab)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MainActivity.class));
                dismiss();
                getActivity().finish();
            }
        });

        linearMpesa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new HoverParameters.Builder(getActivity())
                        .request("ee70d395")
//                        .extra("accountname","42810007227")
                        .extra("amount", user_price)
                        .buildIntent();
                startActivityForResult(i, 0);
            }
        });

        linearTigoPesa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new HoverParameters.Builder(getActivity())
                        .request("c6a5f142")
//                        .extra("accountname","42810007227")
                        .extra("amount", user_price)
                        .buildIntent();
                startActivityForResult(i, 0);

            }
        });


        linearAirtel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new HoverParameters.Builder(getActivity())
                        .request("c76f6dcc")
//                        .extra("accountname","42810007227")
                        .extra("amount", user_price)
                        .buildIntent();
                startActivityForResult(i, 0);
            }
        });

        lineaHalopesa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new HoverParameters.Builder(getActivity())
                        .request("bd7b14ab")
//                        .extra("accountname","42810007227")
                        .extra("amount", user_price)
                        .buildIntent();
                startActivityForResult(i, 0);
            }
        });

//        ((FloatingActionButton) root_view.findViewById(R.id.fab2)).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                copyToClipper("0753453518",v);
//
//
//            }
//        });




        return root_view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


//    private void copyToClipper(String generatedPassword,View v) {
//        // Create a new ClipData.
//        ClipData clipData = ClipData.newPlainText("Source Text", generatedPassword);
//        // Set it as primary clip data to copy text to system clipboard.
//        clipboardManager.setPrimaryClip(clipData);
//        // Popup a snackbar.
////        Snackbar snackbar = Snackbar.make(v, generatedPassword+" Password: text has been copied to system clipboard.", Snackbar.LENGTH_LONG);
////        snackbar.show();
//        //showSpinner(generatedPassword +" "+" has been copied to system clipboard.");
//
//        Toasty.success(getContext(), generatedPassword +" "+" has been copied to system clipboard.", Toast.LENGTH_LONG).show();
//        startActivity(new Intent(getActivity(), MainActivity.class));
//
//        dismiss();
//        getActivity().finish();
//
//    }

    private void showSpinner(String message){
        Snacky.builder()

                .setActivity((Activity) getContext())
                .setMaxLines(4)
                .centerText()
                .setText(message)
                .setBackgroundColor(Color.parseColor("#00B873"))
                .setTextColor(Color.parseColor("#FFFFFF"))
                .setActionTextSize(20)
                .setMaxLines(4)
                .centerText()

                .setActionTextTypefaceStyle(Typeface.BOLD)
                .setDuration(Snacky.LENGTH_LONG)
                .success()
                .show();

    }

}