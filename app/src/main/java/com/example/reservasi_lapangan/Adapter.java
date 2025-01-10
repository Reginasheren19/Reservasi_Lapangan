package com.example.reservasi_lapangan;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

public class FieldAdapter extends RecyclerView.Adapter<FieldAdapter.FieldViewHolder> {
    private Context context;
    private Cursor cursor;

    // Konstruktor untuk adapter
    public FieldAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
    }

    // Membuat ViewHolder dan menghubungkan dengan item layout
    @Override
    public FieldViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_field, parent, false);
        return new FieldViewHolder(view);
    }

    // Menghubungkan data dari cursor ke UI dalam item
    @Override
    public void onBindViewHolder(FieldViewHolder holder, int position) {
        if (cursor.moveToPosition(position)) {
            String fieldName = cursor.getString(cursor.getColumnIndex("nama_lapangan"));
            String sportType = cursor.getString(cursor.getColumnIndex("sport_type"));
            double price = cursor.getDouble(cursor.getColumnIndex("harga"));

            holder.fieldName.setText(fieldName);
            holder.sportType.setText(sportType);
            holder.price.setText("Harga: " + price);
        }
    }

    @Override
    public int getItemCount() {
        return cursor.getCount(); // Mengembalikan jumlah item dalam daftar
    }

    // ViewHolder untuk item lapangan
    public class FieldViewHolder extends RecyclerView.ViewHolder {
        TextView fieldName, sportType, price;

        public FieldViewHolder(View itemView) {
            super(itemView);
            fieldName = itemView.findViewById(R.id.nama_lapangan);
            sportType = itemView.findViewById(R.id.sportType);
            price = itemView.findViewById(R.id.total_harga);
        }
    }
}
