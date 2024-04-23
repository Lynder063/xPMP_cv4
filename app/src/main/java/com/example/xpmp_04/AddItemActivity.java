package com.example.xpmp_04;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class AddItemActivity extends AppCompatActivity {

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item);

        // Initialize DBHelper
        dbHelper = new DBHelper(this);

        // Update stupneSpinner with data
        updateStupneSpinner();

        // Find the confirmButton by its ID
        Button confirmButton = findViewById(R.id.confirmButton);

        // Set an OnClickListener for the confirmButton
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call the saveDataToDatabase() method when the confirmButton is clicked
                saveDataToDatabase();
            }
        });
    }

    private void updateStupneSpinner() {
        // Define the list of stupne texts
        List<String> stupneList = new ArrayList<>();
        stupneList.add("10°");
        stupneList.add("11°");
        stupneList.add("12°");
        stupneList.add("13°");
        stupneList.add("14°");
        stupneList.add("15°");

        // Set up stupneSpinner with provided data
        Spinner stupneSpinner = findViewById(R.id.stupneSpinner);

        // If stupneList is not empty, set it as the adapter for the spinner
        if (!stupneList.isEmpty()) {
            ArrayAdapter<String> stupneAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, stupneList);
            stupneAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            stupneSpinner.setAdapter(stupneAdapter);
        } else {
            // If stupneList is empty, display a default value
            stupneList.add("No stupne available");
            ArrayAdapter<String> stupneAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, stupneList);
            stupneAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            stupneSpinner.setAdapter(stupneAdapter);
        }
    }

    private void saveDataToDatabase() {
        // Initialize amountEditText and stupneSpinner
        EditText amountEditText = findViewById(R.id.amountEditText);
        Spinner stupneSpinner = findViewById(R.id.stupneSpinner);

        // Get the selected stupen and amount
        String selectedStupen = stupneSpinner.getSelectedItem().toString();
        String amountString = amountEditText.getText().toString();

        // Check if amount is not empty
        if (!amountString.isEmpty()) {
            int amount = Integer.parseInt(amountString); // Convert amount to integer
            // Insert data into SQLite database
            boolean success = dbHelper.insertBeer(selectedStupen, amount);
            if (success) {
                Toast.makeText(AddItemActivity.this, "Data saved successfully", Toast.LENGTH_SHORT).show();
                // Optionally, you can finish this activity after data is saved
                finish();
            } else {
                Toast.makeText(AddItemActivity.this, "Failed to save data", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(AddItemActivity.this, "Please enter amount", Toast.LENGTH_SHORT).show();
        }
    }
}
