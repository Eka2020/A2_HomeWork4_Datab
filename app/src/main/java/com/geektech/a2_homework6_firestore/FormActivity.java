package com.geektech.a2_homework6_firestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.geektech.a2_homework6_firestore.models.Task;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

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

        String uid= FirebaseAuth.getInstance().getUid();
        Task task1=new Task(title, desc);
        FirebaseFirestore.getInstance().collection("task1")
                .document(uid)
                .set(task1)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<Void> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(FormActivity.this, "Данные сохранены в Firestore", Toast.LENGTH_SHORT).show();
                        } else {Toast.makeText(FormActivity.this, "Ошибка сохранения в Firestore", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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


