package com.geektech.a2_homework7_firestore;

import android.app.Application;
import androidx.room.Room;
import com.geektech.a2_homework7_firestore.room.AppDatabase;

public class App extends Application {

    private AppDatabase database;
   public static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
        database= Room.databaseBuilder(this, AppDatabase.class, "database")
                .allowMainThreadQueries().build();
    }

    public static App getInstance() {
        return instance;
    }

    public AppDatabase getDatabase() {
        return database;
    }
}
