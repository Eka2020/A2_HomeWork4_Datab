package com.geektech.a2_homework4_database;

import android.app.Application;
import androidx.room.Room;
import com.geektech.a2_homework4_database.room.AppDatabase;

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
