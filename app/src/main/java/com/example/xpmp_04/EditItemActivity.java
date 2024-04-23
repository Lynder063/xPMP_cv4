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

public class EditItemActivity extends AppCompatActivity {

    private Spinner idsSpinner;
    private Spinner editStupneSpinner;
    private EditText editAmountEditText;
    private Button upravitButton;
    private Button deleteButton;

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_item);

        idsSpinner = findViewById(R.id.idsSpinner);
        editStupneSpinner = findViewById(R.id.editstupneSpinner);
        editAmountEditText = findViewById(R.id.editamountEditText);
        upravitButton = findViewById(R.id.upravitButton);
        deleteButton = findViewById(R.id.deleteButton);

        dbHelper = new DBHelper(this);

        // Populate the IDs Spinner with data from the database
        List<String> idList = dbHelper.getAllIDs();
        ArrayAdapter<String> idAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, idList);
        idAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        idsSpinner.setAdapter(idAdapter);

        // Get the selected ID from the Intent
        int selectedId = getIntent().getIntExtra("selected_id", -1);

        // Check if the ID was successfully passed
        if (selectedId != -1) {
            // Load data for editing
            loadItemDataForEdit(selectedId);
        } else {
            // Display an error if the ID was not successfully passed
            Toast.makeText(this, "Error loading data for editing", Toast.LENGTH_SHORT).show();
            // Optionally, you can also finish the activity
            finish();
        }

        // Set listener for the "Upravit" button
        upravitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the selected ID from the Spinner
                int selectedId = Integer.parseInt(idsSpinner.getSelectedItem().toString());
                // Get the selected "stupen" and "amount" values
                String newStupen = editStupneSpinner.getSelectedItem().toString();
                int newAmount = Integer.parseInt(editAmountEditText.getText().toString());
                // Update the beer item in the database
                boolean success = dbHelper.updateBeer(selectedId, newStupen, newAmount);
                if (success) {
                    Toast.makeText(EditItemActivity.this, "Položka byla úspěšně upravena", Toast.LENGTH_SHORT).show();
                    finish(); // Close the activity after successful update
                } else {
                    Toast.makeText(EditItemActivity.this, "Nepodařilo se upravit položku", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Set listener for the "Smazat záznam" button
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the selected ID from the Spinner
                int selectedId = Integer.parseInt(idsSpinner.getSelectedItem().toString());
                // Delete the beer item from the database
                boolean success = dbHelper.deleteBeer(selectedId);
                if (success) {
                    Toast.makeText(EditItemActivity.this, "Položka byla úspěšně smazána", Toast.LENGTH_SHORT).show();
                    finish(); // Close the activity after successful deletion
                } else {
                    Toast.makeText(EditItemActivity.this, "Nepodařilo se smazat položku", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Method to load data for editing
    private void loadItemDataForEdit(int selectedId) {
        // Get the beer item by ID
        BeerItem selectedItem = dbHelper.getBeerItemById(selectedId);
        if (selectedItem != null) {
            // Set values into UI elements
            editAmountEditText.setText(String.valueOf(selectedItem.getAmount()));

            // Define the list of stupne texts
            List<String> stupneList = new ArrayList<>();
            stupneList.add("10°");
            stupneList.add("11°");
            stupneList.add("12°");
            stupneList.add("13°");
            stupneList.add("14°");
            stupneList.add("15°");

            // Set up editStupneSpinner with provided data
            ArrayAdapter<String> stupneAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, stupneList);
            stupneAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            editStupneSpinner.setAdapter(stupneAdapter);

            // Find the index of the selected "stupen" in the list
            int index = stupneAdapter.getPosition(selectedItem.getStupen());
            if (index != -1) {
                // If the selected "stupen" is found, set it as the default selection
                editStupneSpinner.setSelection(index);
            } else {
                // If the selected "stupen" is not found, display a default value
                editStupneSpinner.setSelection(0);
            }
        } else {
            // Display an error if data couldn't be loaded
            Toast.makeText(this, "Error loading data for editing", Toast.LENGTH_SHORT).show();
            // Optionally, you can also finish the activity
            finish();
        }
    }

}
