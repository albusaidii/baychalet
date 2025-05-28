package com.example.baychalet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class cancelBooking extends AppCompatActivity {

    private TextView bookingSummaryText;
    private Button confirmCancelButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_booking);

        bookingSummaryText = findViewById(R.id.booking_summary_text);
        confirmCancelButton = findViewById(R.id.confirm_cancel_button);

        //booking details from Intent extras
        Intent intent = getIntent();
        String userName = intent.getStringExtra("userName");
        String chalet = intent.getStringExtra("chalet");
        String date = intent.getStringExtra("date");
        int duration = intent.getIntExtra("duration", 0);


        if (userName != null && chalet != null && date != null && duration > 0) {
            String summary = "Name: " + userName + "\n"
                    + "Chalet: " + chalet + "\n"
                    + "Date: " + date + "\n"
                    + "Duration: " + duration + " day" + (duration > 1 ? "s" : "");
            bookingSummaryText.setText(summary);
        } else {
            bookingSummaryText.setText("No booking details available.");
            confirmCancelButton.setEnabled(false);
        }

        confirmCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // booking cancellation logic
                Toast.makeText(cancelBooking.this, "Booking cancelled successfully", Toast.LENGTH_SHORT).show();

                Intent intent = getIntent();
                String userName = intent.getStringExtra("userName");
                finish();
            }
        });
    }
}