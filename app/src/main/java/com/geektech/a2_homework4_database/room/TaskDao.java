package com.geektech.a2_homework4_database.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.geektech.a2_homework4_database.models.Task;
import java.util.List;

@Dao
public interface TaskDao {


    @Query("SELECT * FROM task")
    List<Task> getAll();


    @Query("SELECT * FROM task")
    LiveData<List<Task>> getAllLive();

    @Delete
    void delete(Task task);

    @Insert
    void insert(Task task);

    @Update
    void upDate(Task task);

}

