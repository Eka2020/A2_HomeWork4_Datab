package com.geektech.a2_homework6_firestore.ui.header;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.geektech.a2_homework6_firestore.MainActivity;
import com.geektech.a2_homework6_firestore.R;
import com.geektech.a2_homework6_firestore.models.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.io.FileNotFoundException;

public class ProfileActivity extends AppCompatActivity {

    private EditText edit_name, edit_age, edit_phone, edit_email, edit_address;
    ImageView image;
    int REQUEST_CODE=1;
    TextView textTargetUri;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        image = findViewById(R.id.profile_image);
        textTargetUri=findViewById(R.id.text_profile);
//        image.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setType("image/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(Intent.createChooser(intent, "Select Picture"),SELECT_IMAGE);
//
//            }});
//    }

//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == SELECT_IMAGE) {
//            if (resultCode == Activity.RESULT_OK) {
//                if (data != null) {
//                    try {
//                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            } else if (resultCode == Activity.RESULT_CANCELED)  {
//                Toast.makeText(getActivity(), "Canceled", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }

        edit_name = findViewById(R.id.edit_name);
        edit_age = findViewById(R.id.edit_age);
        edit_phone = findViewById(R.id.edit_phone);
        edit_email = findViewById(R.id.edit_email);
        edit_address = findViewById(R.id.edit_address);
//        getData();
        getData2();
        final Button close = findViewById(R.id.btn_profile_close);
        Button save = findViewById(R.id.btn_profile_save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uid = FirebaseAuth.getInstance().getUid();
                String name = edit_name.getText().toString().trim();
                String age = edit_age.getText().toString().trim();
                String phone = edit_phone.getText().toString().trim();
                String email = edit_email.getText().toString().trim();
                String address = edit_address.getText().toString().trim();
                Users users = new Users(name, age, phone, email, address);
                FirebaseFirestore.getInstance().collection("users")
                        .document(uid)
                        .set(users)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ProfileActivity.this, "Данные сохранены",
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(ProfileActivity.this, "Ошибка сохранения",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                close.setVisibility(View.VISIBLE);
                Button save = findViewById(R.id.btn_profile_save);
                save.setVisibility(View.GONE);
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
            }
        });

        Button change = findViewById(R.id.btn_profile_change);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button save = findViewById(R.id.btn_profile_save);
                save.setVisibility(View.VISIBLE);
                Button close = findViewById(R.id.btn_profile_close);
                close.setVisibility(View.GONE);
            }
        });
    }

    private void getData2() {
        String uid = FirebaseAuth.getInstance().getUid();
        FirebaseFirestore.getInstance().collection("users")
                .document(uid)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                        if (documentSnapshot.exists()) {
                            Users users = documentSnapshot.toObject(Users.class);
                            edit_name.setText(users.getName());
                            edit_age.setText(users.getAge());
                            edit_phone.setText(users.getPhone());
                            edit_email.setText(users.getEmail());
                            edit_address.setText(users.getAddress());
                        }
                    }
                });
    }

}

//    private void getData() {
//        String uid= FirebaseAuth.getInstance().getUid();
//        FirebaseFirestore.getInstance().collection("users")
//                .document(uid)
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                        if (task.isSuccessful()){
//                            Users users=task.getResult().toObject(Users.class);
//                            edit_name.setText(users.getName());
//                            edit_age.setText(users.getAge());
//                            edit_phone.setText(users.getPhone());
//                            edit_email.setText(users.getEmail());
//                            edit_address.setText(users.getAddress());
//
//                        }
//                    }
//                });

//    }





