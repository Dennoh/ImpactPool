package tcds.or.tcdsapp.mainapp;

import androidx.appcompat.app.AppCompatActivity;
import tcds.or.tcdsapp.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class FeedbackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        final EditText editTextFullname = findViewById(R.id.editFullname);
        final EditText editTextEmail = findViewById(R.id.editEmail);
        final EditText editPhone = findViewById(R.id.editPhone);
        final EditText editTextFeedback = findViewById(R.id.editFeedback);
        findViewById(R.id.buttonSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullname = editTextFullname.getText().toString();
                String email = editTextEmail.getText().toString();
                String phone = editPhone.getText().toString();
                String feedback = editTextFeedback.getText().toString();

                String[] deveoperemails = {"info@tcds.org"};
                if (feedback.isEmpty()) {
                    editTextFeedback.setError("Feedback is required!");
                } else {
                    Intent feedbackintent = new Intent(Intent.ACTION_SEND);
                    feedbackintent.setType("rfc/822");
                    feedbackintent.putExtra(Intent.EXTRA_EMAIL, deveoperemails);
                    feedbackintent.putExtra(Intent.EXTRA_SUBJECT, "Suggestions from " + fullname);
                    feedbackintent.putExtra(Intent.EXTRA_TEXT, feedback + "\n\n" + "Responding Email : " + email+ "Responding Phone Number : " + phone);
                    startActivity(Intent.createChooser(feedbackintent, "Feedback Options"));
                }
            }
        });
    }
}
