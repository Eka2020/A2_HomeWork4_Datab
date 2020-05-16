package com.geektech.a2_homework4_database;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import com.geektech.a2_homework4_database.models.Task;

public class FormActivity extends AppCompatActivity {
    EditText editTitle;
    EditText editDesc;
    Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Новая задача");
        }
        editTitle = findViewById(R.id.edit_title);
        editDesc = findViewById(R.id.edit_desc);
        edit();
    }

    public void edit() {
        task = (Task) getIntent().getSerializableExtra("task");
        if (task != null) {
            editTitle.setText(task.getTitle());
            editDesc.setText(task.getDesc());
        }  return;
    }

    public void onClick(View view) {
        String title = editTitle.getText().toString().trim();
        String desc = editDesc.getText().toString().trim();
        if (task != null) {
            task.setTitle(editTitle.getText().toString());
            task.setDesc(editDesc.getText().toString());
            App.getInstance().getDatabase().taskDao().upDate(task);
        } else {
            task = new Task(editTitle.getText().toString(), editDesc.getText().toString());
            App.getInstance().getDatabase().taskDao().insert(task);
        }
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_HomeSecondFragment_to_HomeFragment) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}


