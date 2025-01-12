package com.example.reservasi_lapangan;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;
import com.bumptech.glide.Glide;  // Import Glide

import com.example.reservasi_lapangan.Lapangan;
import com.example.reservasi_lapangan.R;

import java.util.List;

public class LapanganAdapter extends RecyclerView.Adapter<LapanganAdapter.LapanganViewHolder> {

    private Context context;
    private List<Lapangan> lapanganList;
    private OnItemClickListener onItemClickListener;

    public LapanganAdapter(Context context, List<Lapangan> lapanganList) {
        this.context = context;
        this.lapanganList = lapanganList;
    }

    @NonNull
    @Override
    public LapanganViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_list, parent, false);
        return new LapanganViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LapanganViewHolder holder, int position) {
        Lapangan lapangan = lapanganList.get(position);

        holder.namaLapangan.setText(lapangan.getNamaLapangan());
        holder.lokasiLapangan.setText(lapangan.getLokasi());
        holder.hargaLapangan.setText(lapangan.getHarga());

        int imageResId = context.getResources().getIdentifier(
                lapangan.getGambar(), "drawable", context.getPackageName()
        );
        holder.gambarLapangan.setImageResource(imageResId);

        // Menambahkan listener pada item klik
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(lapangan); // Panggil interface listener
            }
        });
    }

    @Override
    public int getItemCount() {
        return lapanganList.size();
    }

    // Tambahkan setter untuk OnItemClickListener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    // Interface untuk menangani klik item
    public interface OnItemClickListener {
        void onItemClick(Lapangan lapangan);
    }

    public static class LapanganViewHolder extends RecyclerView.ViewHolder {
        TextView namaLapangan, lokasiLapangan, hargaLapangan;
        ImageView gambarLapangan;

        public LapanganViewHolder(@NonNull View itemView) {
            super(itemView);
            namaLapangan = itemView.findViewById(R.id.namaLapangan);
            lokasiLapangan = itemView.findViewById(R.id.lokasiLapangan);
            hargaLapangan = itemView.findViewById(R.id.hargaLapangan);
            gambarLapangan = itemView.findViewById(R.id.gambarLapangan);
        }
    }
}


