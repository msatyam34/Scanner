package com.example.scanner.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ScannedItemDao {

    @Query("SELECT * FROM scanned_table")
    List<ScannedItem> getAllScannedItem();

    @Insert
    void insertScannedItem(ScannedItem... scannedItems);






}
