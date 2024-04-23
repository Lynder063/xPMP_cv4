package com.example.xpmp_04;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private Button addButton;
    private Button editButton;
    private DBHelper dbHelper;
    private BeerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        addButton = findViewById(R.id.addButton);
        dbHelper = new DBHelper(this);

        adapter = new BeerAdapter(this, new ArrayList<>());
        listView.setAdapter(adapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddItemActivity();
            }
        });

        updateListView();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BeerItem selectedItem = adapter.getItem(position);
                if (selectedItem != null) {
                    openEditItemActivity((int) selectedItem.getId()); // Převést ID na int
                }
            }
        });
    }

    private void openAddItemActivity() {
        Intent intent = new Intent(MainActivity.this, AddItemActivity.class);
        startActivity(intent);
    }

    private void openEditItemActivity() {
        Intent intent = new Intent(MainActivity.this, EditItemActivity.class);
        startActivity(intent);
    }

    private void openEditItemActivity(int selectedId) {
        Intent intent = new Intent(MainActivity.this, EditItemActivity.class);
        intent.putExtra("selected_id", selectedId);
        startActivity(intent);
    }

    private void updateListView() {
        adapter.clear();
        Cursor cursor = dbHelper.getAllBeers();

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String stupen = cursor.getString(1);
                int amount = cursor.getInt(2);
                adapter.add(new BeerItem(cursor.getLong(0), stupen, amount));
            }
            cursor.close();
        } else {
            Toast.makeText(this, "Error retrieving data from database", Toast.LENGTH_SHORT).show();
        }
    }
}
