package com.example.reservasi_lapangan;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class BookingActivity extends AppCompatActivity {

    private TextView tvNamaLapangan, tvHarga, tvLokasi;
    private EditText inputNama, inputTelepon, inputDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        // Inisialisasi komponen UI
        tvNamaLapangan = findViewById(R.id.tvNamaLapangan);
        tvHarga = findViewById(R.id.tvHarga);
        tvLokasi = findViewById(R.id.tvLokasi);
        inputNama = findViewById(R.id.inputNama);
        inputTelepon = findViewById(R.id.inputTelepon);
        inputDate = findViewById(R.id.InputDate);

        // Ambil data dari Intent
        Intent intent = getIntent();
        String namaLapangan = intent.getStringExtra("namaLapangan");
        String harga = intent.getStringExtra("harga");
        String lokasi = intent.getStringExtra("lokasi");

        // Tampilkan data di TextView
        if (namaLapangan != null) {
            tvNamaLapangan.setText(namaLapangan);
        }
        if (harga != null) {
            tvHarga.setText("Rp " + harga);
        }
        if (lokasi != null) {
            tvLokasi.setText(lokasi);
        }
    }
}
