package com.example.scanner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.scanner.ScanningActivity.ScanActivity;
import com.example.scanner.db.ScannedItem;
import com.example.scanner.db.ScannedItemDatabase;

import java.io.File;
import java.io.FileOutputStream;
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



    public void onMakeCsv(View view) {
        ScannedItemDatabase db = ScannedItemDatabase.getDbInstance(this.getApplicationContext());
        List<ScannedItem> itemList = db.scannedItemDao().getAllScannedItem();
        StringBuilder data = new StringBuilder();
        data.append("Sno,Barcode");

        for (ScannedItem item : itemList){
            data.append("\n"+item.sNo+ "," +item.barcode);
        }

        try{
            //saving the file into device
            FileOutputStream out = openFileOutput("data.csv", Context.MODE_PRIVATE);
            out.write((data.toString()).getBytes());
            out.close();

            //exporting
            Context context = getApplicationContext();
            File filelocation = new File(getFilesDir(), "data.csv");
            Uri path = FileProvider.getUriForFile(context, "com.example.scanner.fileprovider", filelocation);
            Intent fileIntent = new Intent(Intent.ACTION_SEND);
            fileIntent.setType("text/csv");
            fileIntent.putExtra(Intent.EXTRA_SUBJECT, "Data");
            fileIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            fileIntent.putExtra(Intent.EXTRA_STREAM, path);
            startActivity(Intent.createChooser(fileIntent, "Send mail"));
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}