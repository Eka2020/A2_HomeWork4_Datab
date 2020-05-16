package com.geektech.a2_homework4_database.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.geektech.a2_homework4_database.App;
import com.geektech.a2_homework4_database.FormActivity;
import com.geektech.a2_homework4_database.R;
import com.geektech.a2_homework4_database.models.Task;
import com.geektech.a2_homework4_database.ui.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private TaskAdapter adapter;
    private ArrayList<Task> list = new ArrayList<>();
    private AlertDialog.Builder ad;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        list.addAll(App.getInstance().getDatabase().taskDao().getAll());
        adapter = new TaskAdapter(list);
        recyclerView.setAdapter(adapter);
        loadData();
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                Task task = list.get(pos);
                Intent intent = new Intent(getActivity(), FormActivity.class);
                intent.putExtra("task", task);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(final int pos) {
                alertDialog(pos);
            }

            public void alertDialog(final int pos){
                ad = new AlertDialog.Builder(getActivity());
                ad.setTitle("Внимание!")
                        .setMessage("Вы уверены что хотите удалить?")
                        .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                App.getInstance().getDatabase().taskDao().delete(list.get(pos));
                            }
                        });
                ad.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                ad.show();
            }
        });}

    private  void loadData(){
        App.getInstance()
                .getDatabase()
                .taskDao()
                .getAllLive()
                .observe(this, new Observer<List<Task>>(){
                    @Override
                    public void onChanged(List<Task> tasks) {
                        list.clear();
                        list.addAll(tasks);
                        adapter.notifyDataSetChanged();

                    }
                });
    }
}