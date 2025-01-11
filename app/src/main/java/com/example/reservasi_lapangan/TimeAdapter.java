package com.example.reservasi_lapangan;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.util.ArrayList;

public class TimeAdapter extends BaseAdapter {

    private Context context;
    private String[] data;
    private ArrayList<String> selectedTimes;

    public TimeAdapter(Context context, String[] data, ArrayList<String> selectedTimes) {
        this.context = context;
        this.data = data;
        this.selectedTimes = selectedTimes;
    }

    @Override
    public int getCount() {
        return data.length;
    }

    @Override
    public Object getItem(int position) {
        return data[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.grid_item, parent, false);
        }

        Button button = convertView.findViewById(R.id.btnGridItem);
        button.setText(data[position]);

        // Tentukan warna berdasarkan status
        if (selectedTimes.contains(data[position])) {
            button.setBackgroundColor(Color.parseColor("#009688")); // Warna teal
            button.setTextColor(Color.WHITE);
        } else {
            button.setBackgroundColor(Color.WHITE);
            button.setTextColor(Color.BLACK);
        }

        // Atur klik listener untuk memperbarui status pemilihan
        button.setOnClickListener(v -> {
            if (selectedTimes.contains(data[position])) {
                selectedTimes.remove(data[position]);
                button.setBackgroundColor(Color.WHITE);
                button.setTextColor(Color.BLACK);
            } else {
                selectedTimes.add(data[position]);
                button.setBackgroundColor(Color.parseColor("#009688")); // Warna teal
                button.setTextColor(Color.WHITE);
            }
        });

        return convertView;
    }
}
