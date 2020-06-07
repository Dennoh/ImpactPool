package tcds.or.tcdsapp;


import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hover.sdk.api.HoverParameters;

import java.text.DecimalFormat;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import de.mateware.snacky.Snacky;
import es.dmoral.toasty.Toasty;
import tcds.or.tcdsapp.mainapp.MainActivity;

import static android.content.Context.CLIPBOARD_SERVICE;
import static tcds.or.tcdsapp.PlaceOrderActivity.OrderPAY_PREFERENCES;


public class DialogPaymentSuccess extends DialogFragment {

    private View root_view;
    Object clipboardService;
    ClipboardManager clipboardManager;
    LinearLayout linearMpesa, linearTigoPesa, linearAirtel, lineaHalopesa;
    String accessCharges;
    TextView textViewMobilePayment;
    CardView cardMobilePay2, cardMobilePay1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root_view = inflater.inflate(R.layout.dialog_payment_success, container, false);

        clipboardService = getContext().getSystemService(CLIPBOARD_SERVICE);
        clipboardManager = (ClipboardManager) clipboardService;

        //final MaterialEditText materialEditUSerProblem = register_layout.findViewById(R.id.editTextName);
        TextView txt_date = root_view.findViewById(R.id.txt_date);
        final TextView sum_price = root_view.findViewById(R.id.sum_price);
        linearMpesa = root_view.findViewById(R.id.linearMpesa);
        lineaHalopesa = root_view.findViewById(R.id.lineaHalopesa);
        linearAirtel = root_view.findViewById(R.id.linearAirtel);
        linearTigoPesa = root_view.findViewById(R.id.linearTigoPesa);

//        TextView location=root_view.findViewById(R.id.location);
//        TextView phone_user=root_view.findViewById(R.id.phone_user)


        textViewMobilePayment = root_view.findViewById(R.id.textViewMobilePayment);
        cardMobilePay1 = root_view.findViewById(R.id.cardMobilePay1);
        cardMobilePay2 = root_view.findViewById(R.id.cardMobilePay2);
//        cardVisaPayment = root_view.findViewById(R.id.cardVisaPayment);
//        textViewVisaPayIntro = root_view.findViewById(R.id.textViewVisaPayIntro);
//        textviewCopy = root_view.findViewById(R.id.textviewCopy);
//
//        radioGroupPaymentOptions = root_view.findViewById(R.id.radioGroupPaymentOptions);
//
//        radioGroupPaymentOptions.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                int selectedId = radioGroupPaymentOptions.getCheckedRadioButtonId();
//
//                if (selectedId == R.id.radioMobilePayment) {
//                    textViewMobilePayment.setVisibility(View.VISIBLE);
//                    cardMobilePay1.setVisibility(View.VISIBLE);
//                    cardMobilePay2.setVisibility(View.VISIBLE);
//                    cardVisaPayment.setVisibility(View.GONE);
//                    textViewVisaPayIntro.setVisibility(View.GONE);
//                }
//
//                if (selectedId == R.id.radioVisa) {
//                    textViewMobilePayment.setVisibility(View.GONE);
//                    cardMobilePay1.setVisibility(View.GONE);
//                    cardMobilePay2.setVisibility(View.GONE);
//                    cardVisaPayment.setVisibility(View.VISIBLE);
//                    textViewVisaPayIntro.setVisibility(View.VISIBLE);
//                }
//            }
//        });


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
            sum_price.setText("Tsh "+decimalFormat.format(Float.parseFloat(user_price)));
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
                        .request("d455c800")
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
                        .request("71cbe3a6")
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
                        .request("55f4348e")
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
                        .request("086122ae")
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

//        textviewCopy.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                copyToClipper("0152241597300", v);
//            }
//        });


        return root_view;
    }


    private void copyToClipper(String generatedPassword, View v) {
        // Create a new ClipData.
        ClipData clipData = ClipData.newPlainText("Source Text", generatedPassword);
        // Set it as primary clip data to copy text to system clipboard.
        clipboardManager.setPrimaryClip(clipData);
        // Popup a snackbar.
//        Snackbar snackbar = Snackbar.make(v, generatedPassword+" Password: text has been copied to system clipboard.", Snackbar.LENGTH_LONG);
//        snackbar.show();
        //showSpinner(generatedPassword +" "+" has been copied to system clipboard.");
        Toasty.success(getContext(), generatedPassword + " " + " has been copied to system clipboard.", Toast.LENGTH_LONG).show();
        startActivity(new Intent(getActivity(), MainActivity.class));
        dismiss();
        getActivity().finish();
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

    private void showSpinner(String message) {
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