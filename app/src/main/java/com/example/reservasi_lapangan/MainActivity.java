package com.example.reservasi_lapangan;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.reservasi_lapangan.DatabaseHelper;
import com.example.reservasi_lapangan.Lapangan;
import com.example.reservasi_lapangan.LapanganAdapter;
import com.example.reservasi_lapangan.R;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LapanganAdapter lapanganAdapter;
    private List<Lapangan> lapanganList;
    private List<Lapangan> lapanganListFiltered;

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
        lapanganListFiltered = new ArrayList<>(lapanganList); // Initialize filtered list

        // Set up the adapter
        lapanganAdapter = new LapanganAdapter(this, lapanganListFiltered);
        recyclerView.setAdapter(lapanganAdapter);

        // Set item click listener
        lapanganAdapter.setOnItemClickListener(lapangan -> {
            // Create intent to navigate to DetailsActivity
            Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
            intent.putExtra("idLapangan", lapangan.getId());  // Ensure lapangan.getId() returns a valid ID
            intent.putExtra("namaLapangan", lapangan.getNamaLapangan());
            intent.putExtra("lokasi", lapangan.getLokasi());
            intent.putExtra("harga", lapangan.getHarga());
            intent.putExtra("deskripsi", lapangan.getDeskripsi());
            intent.putExtra("gambar", lapangan.getGambar());
            startActivity(intent);
        });

        // Set up the search functionality
        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterLapangan(newText);  // Call the filter function
                return false;
            }
        });
    }

    private void filterLapangan(String query) {
        List<Lapangan> filteredList = new ArrayList<>();
        for (Lapangan lapangan : lapanganList) {
            if (lapangan.getNamaLapangan().toLowerCase().contains(query.toLowerCase()) ||
                    lapangan.getLokasi().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(lapangan);
            }
        }
        // Update the filtered list and notify the adapter
        lapanganListFiltered.clear();
        lapanganListFiltered.addAll(filteredList);
        lapanganAdapter.notifyDataSetChanged();
    }
}
