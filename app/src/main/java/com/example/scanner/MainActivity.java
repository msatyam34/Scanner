package com.example.scanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import com.example.scanner.ScanningActivity.ScanActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerViewAdapter recyclerViewAdapter;
    private List<ItemClass> itemList;
    SQLiteDatabase myDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onResume() {
        super.onResume();
        initItemList();

        initRecyclerView();
    }

    private void initItemList(){
        itemList = new ArrayList<>();
        String serialNo = "S.no";
        String code = "Scanned Code";
        itemList.add(new ItemClass(serialNo,code));
//        try {
//            myDatabase = this.openOrCreateDatabase("scannedDatabase",MODE_PRIVATE,null);
//            Cursor c = myDatabase.rawQuery("SELECT * FROM scannedDatabase", null);
//
//            int serialNoIndex = c.getColumnIndex("serialNo");
//            int codeIndex = c.getColumnIndex("code");
//
//            c.moveToFirst();
//
//            while(c!= null){
//                String sequenceNo = c.getString(serialNoIndex);
//                String barcode = c.getString(codeIndex);
//                itemList.add(new ItemClass(sequenceNo,barcode));
//            }
//
//        } catch (Exception e){
//            e.printStackTrace();
//        }



    }

    private void initRecyclerView(){
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerViewAdapter = new RecyclerViewAdapter(itemList);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();

    }

    public void onScan(View view) {
        Intent intent = new Intent(getApplicationContext(), ScanActivity.class);
        startActivity(intent);

    }
}