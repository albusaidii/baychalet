package com.example.baychalet;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MakeBooking extends AppCompatActivity {

    private EditText editTextUserName;
    private Spinner spinnerChalet;
    private EditText editTextBookingDate;
    private EditText editTextDuration;
    private Button buttonCalculateTotal;
    private TextView textViewTotalCost;
    private Button buttonConfirmBooking;

    private int selectedChaletPrice = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        // Initialize views
        editTextUserName = findViewById(R.id.booking_user_name);
        spinnerChalet = findViewById(R.id.chalet_spinner);
        editTextBookingDate = findViewById(R.id.booking_date);
        editTextDuration = findViewById(R.id.booking_duration);
        buttonCalculateTotal = findViewById(R.id.calculate_total_button);
        textViewTotalCost = findViewById(R.id.total_cost_text);
        buttonConfirmBooking = findViewById(R.id.confirm_booking_button);

        // Setup spinner items (chalet names)
        String[] chaletNames = {"Select Chalet", "Sifah Chalet", "Jabal Akhdar Chalet", "Salalah Chalet"};
        final int[] chaletPrices = {0, 100, 150, 120}; // prices per day for each chalet

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, chaletNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerChalet.setAdapter(adapter);

        spinnerChalet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedChaletPrice = chaletPrices[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedChaletPrice = 0;
            }
        });

        // Date picker dialog for booking date
        editTextBookingDate.setOnClickListener(v -> showDatePicker());

        // Calculate total button click
        buttonCalculateTotal.setOnClickListener(v -> calculateTotal());

        // Confirm booking button click
        buttonConfirmBooking.setOnClickListener(v -> confirmBooking());
    }

    private void showDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    // Note: selectedMonth is zero-based
                    String dateStr = (selectedMonth + 1) + "/" + selectedDay + "/" + selectedYear;
                    editTextBookingDate.setText(dateStr);
                }, year, month, day);

        datePickerDialog.show();
    }

    private void calculateTotal() {
        String durationStr = editTextDuration.getText().toString().trim();

        if (selectedChaletPrice == 0) {
            Toast.makeText(this, "Please select a chalet", Toast.LENGTH_SHORT).show();
            return;
        }

        if (durationStr.isEmpty()) {
            Toast.makeText(this, "Please enter duration in days", Toast.LENGTH_SHORT).show();
            return;
        }

        int duration;
        try {
            duration = Integer.parseInt(durationStr);
            if (duration <= 0) {
                Toast.makeText(this, "Duration must be greater than zero", Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid duration", Toast.LENGTH_SHORT).show();
            return;
        }

        int totalCost = selectedChaletPrice * duration;
        textViewTotalCost.setText("Total: $" + totalCost);
    }

    private void confirmBooking() {
        String userName = editTextUserName.getText().toString().trim();
        String date = editTextBookingDate.getText().toString().trim();
        String durationStr = editTextDuration.getText().toString().trim();
        String totalCostText = textViewTotalCost.getText().toString();

        if (userName.isEmpty()) {
            Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show();
            return;
        }

        if (selectedChaletPrice == 0) {
            Toast.makeText(this, "Please select a chalet", Toast.LENGTH_SHORT).show();
            return;
        }

        if (date.isEmpty()) {
            Toast.makeText(this, "Please select a booking date", Toast.LENGTH_SHORT).show();
            return;
        }

        if (durationStr.isEmpty()) {
            Toast.makeText(this, "Please enter duration", Toast.LENGTH_SHORT).show();
            return;
        }

        // Here you can add more logic to save booking data or move to the next step

        Toast.makeText(this, "Booking confirmed!", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(MakeBooking.this, cancelBooking.class);
        intent.putExtra("userName", userName);
        intent.putExtra("chalet", spinnerChalet.getSelectedItem().toString());
        intent.putExtra("date", date);
        intent.putExtra("duration", Integer.parseInt(durationStr));

        startActivity(intent);
        finish();

    }
}