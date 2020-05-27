package com.geektech.a2_homework6_firestore;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import com.geektech.a2_homework6_firestore.login.PhoneActivity;
import com.geektech.a2_homework6_firestore.ui.header.ProfileActivity;
import com.geektech.a2_homework6_firestore.ui.home.HomeFragment;
import com.geektech.a2_homework6_firestore.ui.onboard.OnBoardActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import static android.view.View.*;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private  boolean sort;
//1. Добавить imageView в ProfileActivity, по нажатию открыть галерею телефона, выбрать картинку и поставить в imageView
//
//2. В FormActivity кроме записи в Room, отправлять еще и в Firestore. (без редактирования)
//
//3. Добавить еще один фрагмент "Firestore"
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!isShow()) {
            startActivity(new Intent(this, OnBoardActivity.class));
            finish();
            return;
        }
        if (FirebaseAuth.getInstance().getCurrentUser()==null){
            startActivity(new Intent(this, PhoneActivity.class));
            finish();
            return;
        }
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, FormActivity.class));
                return;
            }
        });

        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
         header.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                finish();
                return;
            }
        });
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.nav_firestore)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
            }

    private boolean isShow() {
        SharedPreferences preferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
        return preferences.getBoolean("isShow", false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_sort) {
            showSortDialog();
            return true;
        }
                if (id == R.id.action_close) {
                SharedPreferences preferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
                preferences.edit().putBoolean("isShow", false).apply();
                finish();
            }

        return super.onOptionsItemSelected(item);
    }

    private void showSortDialog() {
       String[] options={"по возрастанию", "по убыванию" };
        AlertDialog.Builder ad=new AlertDialog.Builder(this);
        ad.setTitle("Сортировка списка")
                .setIcon(R.drawable.ic_sort_by_24dp)
                .setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which==0){
                            Fragment navHostFragment = getSupportFragmentManager()
                                    .findFragmentById(R.id.nav_host_fragment);
                            ((HomeFragment) navHostFragment
                                    .getChildFragmentManager()
                                    .getFragments().get(0))
                                    .LoadDataSorted();
                            sort = false;
                        }
                        if (which==1){
                            Fragment navHostFragment1 = getSupportFragmentManager()
                                    .findFragmentById(R.id.nav_host_fragment);
                            ((HomeFragment) navHostFragment1
                                    .getChildFragmentManager()
                                    .getFragments().get(0))
                                    .LoadData();

                            sort = true;
                        }
                    }
                });
        ad.create().show();
    }

}

