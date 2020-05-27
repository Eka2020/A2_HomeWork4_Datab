package com.geektech.a2_homework4_database.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.geektech.a2_homework4_database.models.Task;

@Database(entities={Task.class}, version=1)

public abstract class AppDatabase extends RoomDatabase {

    public  abstract  TaskDao taskDao();

}
