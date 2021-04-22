package com.example.scanner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.scanner.ScanningActivity.ScanActivity;
import com.example.scanner.db.ScannedItem;
import com.example.scanner.db.ScannedItemDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerViewAdapter recyclerViewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initRecyclerView();
        loadItemList();

    }
    

    private void initRecyclerView(){
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerViewAdapter = new RecyclerViewAdapter(this);
        recyclerView.setAdapter(recyclerViewAdapter);

    }

    public void onScan(View view) {

        startActivityForResult(new Intent(MainActivity.this,ScanActivity.class),100);
    }

    private void loadItemList(){
        ScannedItemDatabase db = ScannedItemDatabase.getDbInstance(this.getApplicationContext());
        List<ScannedItem> itemList = db.scannedItemDao().getAllScannedItem();
        recyclerViewAdapter.setItemList(itemList);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==100){
            loadItemList();

        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}