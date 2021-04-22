package com.example.scanner.ScanningActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.scanner.R;
import com.example.scanner.db.ScannedItem;
import com.example.scanner.db.ScannedItemDatabase;
import com.google.android.gms.vision.barcode.Barcode;
import com.notbytes.barcode_reader.BarcodeReaderFragment;

import java.util.ArrayList;
import java.util.List;

public class ScanActivity extends AppCompatActivity implements BarcodeReaderFragment.BarcodeReaderListener {

    //scanned itemList will contain all the scanned barcode
    private List<String> scannedBarcodeList;
    private TextView lastScannedBarcode;

    private BarcodeReaderFragment readerFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        init();
        addBarcodeReaderFragment();
    }




    private void init(){
        scannedBarcodeList = new ArrayList<>();
        lastScannedBarcode = findViewById(R.id.lastScannedItemCode);

    }



    private void addBarcodeReaderFragment() {
        readerFragment = BarcodeReaderFragment.newInstance(true, false, View.VISIBLE);
        readerFragment.setListener(this);
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fm_container,readerFragment);
        fragmentTransaction.commitAllowingStateLoss();

    }


    public void pushToDB(View view) {
        readerFragment.pauseScanning();
        ScannedItemDatabase db = ScannedItemDatabase.getDbInstance(this.getApplicationContext());
        for(int i=0; i<scannedBarcodeList.size(); i++){
            String data = scannedBarcodeList.get(i);
            int serialNo = i+1;
            ScannedItem scannedNewItem = new ScannedItem();
            scannedNewItem.sNo = serialNo;
            scannedNewItem.barcode = data;
            db.scannedItemDao().insertScannedItem(scannedNewItem);

            finish();
//




        }



    }





    @Override
    public void onScanned(Barcode barcode) {
        readerFragment.pauseScanning();
        String barcodeValue = barcode.rawValue;
        if(!scannedBarcodeList.contains(barcodeValue)) {
            scannedBarcodeList.add(barcodeValue);
            lastScannedBarcode.setText(barcodeValue);
            readerFragment.playBeep();
            Toast.makeText(ScanActivity.this,"Total items scanned: "+ scannedBarcodeList.size(),Toast.LENGTH_SHORT).show();
        }

        readerFragment.resumeScanning();
    }





    @Override
    public void onScannedMultiple(List<Barcode> barcodes) {
        readerFragment.pauseScanning();
        String barcodeValue;
        for(Barcode barcode : barcodes){
            barcodeValue = barcode.rawValue;
            if(!scannedBarcodeList.contains(barcodeValue)) {
                scannedBarcodeList.add(barcodeValue);
                lastScannedBarcode.setText(barcodeValue);
                readerFragment.playBeep();
            }
        }
        readerFragment.resumeScanning();
    }



    @Override
    public void onBitmapScanned(SparseArray<Barcode> sparseArray) {

    }

    @Override
    public void onScanError(String errorMessage) {

    }

    @Override
    public void onCameraPermissionDenied() {

    }

}