package com.example.scanner.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {ScannedItem.class}, version = 1)
public abstract class ScannedItemDatabase extends RoomDatabase {

    public abstract ScannedItemDao scannedItemDao();
    private static ScannedItemDatabase INSTANCE;


    public static ScannedItemDatabase getDbInstance(Context context){
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ScannedItemDatabase.class, "scanned_item_database")
                    .allowMainThreadQueries()
                    .build();

        }
        return INSTANCE;


    }
}
