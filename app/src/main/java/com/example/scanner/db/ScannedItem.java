package com.example.scanner.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "scanned_table")
public class ScannedItem {

    @PrimaryKey(autoGenerate = true)
    public int sNo;

    @ColumnInfo(name = "barcode")
    public String barcode;



}
