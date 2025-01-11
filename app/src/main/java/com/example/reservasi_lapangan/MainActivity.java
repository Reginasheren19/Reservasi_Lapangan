package com.example.reservasi_lapangan;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reservasi_lapangan.DatabaseHelper;
import com.example.reservasi_lapangan.Lapangan;
import com.example.reservasi_lapangan.LapanganAdapter;
import com.example.reservasi_lapangan.R;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LapanganAdapter lapanganAdapter;
    private List<Lapangan> lapanganList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerViewLapangan);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Fetch data from the database
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        lapanganList = dbHelper.getAllLapangan(); // Assuming this method returns a List<Lapangan>

        // Set up the adapter
        lapanganAdapter = new LapanganAdapter(this, lapanganList);
        recyclerView.setAdapter(lapanganAdapter);

        // Set item click listener
        lapanganAdapter.setOnItemClickListener(lapangan -> {
            // Create intent to navigate to DetailsActivity
            Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
            intent.putExtra("idLapangan", lapangan.getId());  // Pastikan lapangan.getId() mengembalikan ID yang valid
            intent.putExtra("namaLapangan", lapangan.getNamaLapangan());
            intent.putExtra("lokasi", lapangan.getLokasi());
            intent.putExtra("harga", lapangan.getHarga());
            intent.putExtra("deskripsi", lapangan.getDeskripsi());
            intent.putExtra("gambar", lapangan.getGambar());
            startActivity(intent);
        });
    }
}
