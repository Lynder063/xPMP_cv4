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


        List<String> idList = dbHelper.getAllIDs();
        ArrayAdapter<String> idAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, idList);
        idAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        idsSpinner.setAdapter(idAdapter);


        int selectedId = getIntent().getIntExtra("selected_id", -1);


        if (selectedId != -1) {

            loadItemDataForEdit(selectedId);
        } else {

            Toast.makeText(this, "Error loading data for editing", Toast.LENGTH_SHORT).show();

            finish();
        }


        upravitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectedId = Integer.parseInt(idsSpinner.getSelectedItem().toString());

                String newStupen = editStupneSpinner.getSelectedItem().toString();
                int newAmount = Integer.parseInt(editAmountEditText.getText().toString());

                boolean success = dbHelper.updateBeer(selectedId, newStupen, newAmount);
                if (success) {
                    Toast.makeText(EditItemActivity.this, "Položka byla úspěšně upravena", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(EditItemActivity.this, "Nepodařilo se upravit položku", Toast.LENGTH_SHORT).show();
                }
            }
        });


        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectedId = Integer.parseInt(idsSpinner.getSelectedItem().toString());
                boolean success = dbHelper.deleteBeer(selectedId);
                if (success) {
                    Toast.makeText(EditItemActivity.this, "Položka byla úspěšně smazána", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(EditItemActivity.this, "Nepodařilo se smazat položku", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void loadItemDataForEdit(int selectedId) {
        BeerItem selectedItem = dbHelper.getBeerItemById(selectedId);
        if (selectedItem != null) {
            editAmountEditText.setText(String.valueOf(selectedItem.getAmount()));

            List<String> stupneList = new ArrayList<>();
            stupneList.add("10°");
            stupneList.add("11°");
            stupneList.add("12°");
            stupneList.add("13°");
            stupneList.add("14°");
            stupneList.add("15°");

            ArrayAdapter<String> stupneAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, stupneList);
            stupneAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            editStupneSpinner.setAdapter(stupneAdapter);

            int index = stupneAdapter.getPosition(selectedItem.getStupen());
            if (index != -1) {
                editStupneSpinner.setSelection(index);
            } else {
                editStupneSpinner.setSelection(0);
            }
        } else {
            Toast.makeText(this, "Error loading data for editing", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

}
