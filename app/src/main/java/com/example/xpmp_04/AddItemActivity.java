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

        dbHelper = new DBHelper(this);

        updateStupneSpinner();

        Button confirmButton = findViewById(R.id.confirmButton);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDataToDatabase();
            }
        });
    }

    private void updateStupneSpinner() {
        List<String> stupneList = new ArrayList<>();
        stupneList.add("10°");
        stupneList.add("11°");
        stupneList.add("12°");
        stupneList.add("13°");
        stupneList.add("14°");
        stupneList.add("15°");

        Spinner stupneSpinner = findViewById(R.id.stupneSpinner);

        if (!stupneList.isEmpty()) {
            ArrayAdapter<String> stupneAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, stupneList);
            stupneAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            stupneSpinner.setAdapter(stupneAdapter);
        } else {
            stupneList.add("No stupne available");
            ArrayAdapter<String> stupneAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, stupneList);
            stupneAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            stupneSpinner.setAdapter(stupneAdapter);
        }
    }

    private void saveDataToDatabase() {
        EditText amountEditText = findViewById(R.id.amountEditText);
        Spinner stupneSpinner = findViewById(R.id.stupneSpinner);

        String selectedStupen = stupneSpinner.getSelectedItem().toString();
        String amountString = amountEditText.getText().toString();

        if (!amountString.isEmpty()) {
            int amount = Integer.parseInt(amountString);
            boolean success = dbHelper.insertBeer(selectedStupen, amount);
            if (success) {
                Toast.makeText(AddItemActivity.this, "Data saved successfully", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(AddItemActivity.this, "Failed to save data", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(AddItemActivity.this, "Please enter amount", Toast.LENGTH_SHORT).show();
        }
    }
}
