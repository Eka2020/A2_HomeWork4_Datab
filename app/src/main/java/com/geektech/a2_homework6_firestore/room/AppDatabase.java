package com.geektech.a2_homework6_firestore.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.geektech.a2_homework6_firestore.models.Task;

@Database(entities={Task.class}, version=1)

public abstract class AppDatabase extends RoomDatabase {

    public  abstract  TaskDao taskDao();

}
