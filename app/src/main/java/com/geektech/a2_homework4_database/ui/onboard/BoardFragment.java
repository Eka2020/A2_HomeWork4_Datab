package com.geektech.a2_homework4_database.ui.onboard;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.geektech.a2_homework4_database.MainActivity;
import com.geektech.a2_homework4_database.R;
/**
 * A simple {@link Fragment} subclass.
 */
public class BoardFragment extends Fragment {


    public BoardFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_board, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int pos = getArguments().getInt("pos");
        TextView textTitle = view.findViewById(R.id.text_title);
        ImageView imageView = view.findViewById(R.id.imageView);
        Button button = view.findViewById(R.id.start);
        LinearLayout frg = view.findViewById(R.id.frg_board);


        switch (pos) {
            case 0:
                textTitle.setText("Весна");
                imageView.setImageResource(R.drawable.spring);
                frg.setBackgroundColor(getResources().getColor(R.color.colorRed));
                break;
            case 1:
                textTitle.setText("Лето");
                imageView.setImageResource(R.drawable.summer);
                frg.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                break;
            case 2:
                textTitle.setText("Осень");
                imageView.setImageResource(R.drawable.autumn);
                frg.setBackgroundColor(getResources().getColor(R.color.color));
                break;
            case 3:
                textTitle.setText("Зима");
                imageView.setImageResource(R.drawable.winter);
                frg.setBackgroundColor(getResources().getColor(R.color.colorBlue));
                break;
            case 4:
                textTitle.setText("Пока");
                imageView.setImageResource(R.drawable.ic_launcher_foreground);
                button.setVisibility(View.VISIBLE);
                break;
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveIsShow();
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        });
        return;
}
private void saveIsShow(){
    SharedPreferences preferences= getActivity().getSharedPreferences("settings", Context.MODE_PRIVATE);
   preferences.edit().putBoolean("isShow", true).apply();
    getActivity().finish();
    }
}
