package com.example.reservasi_lapangan;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailsActivity extends AppCompatActivity {

    private TextView placeNameTextView, placeLocationTextView, placeHargaTextView, placeDetailsTextView;
    private ImageView imageDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // Inisialisasi komponen UI
        placeNameTextView = findViewById(R.id.placename);
        placeLocationTextView = findViewById(R.id.placelocation);
        placeHargaTextView = findViewById(R.id.placeharga); // pastikan ID ini cocok dengan layout
        placeDetailsTextView = findViewById(R.id.placedetails);
        imageDetails = findViewById(R.id.imagedetails);
        ImageView backButton = findViewById(R.id.backButton);
        Button btnPilihJadwal = findViewById(R.id.BtnPilihJadwal);


        // Ambil data dari Intent
        Intent intent = getIntent();
        String namaLapangan = intent.getStringExtra("namaLapangan");
        String lokasi = intent.getStringExtra("lokasi");
        String harga = intent.getStringExtra("harga");
        String deskripsi = intent.getStringExtra("deskripsi");
        String gambar = intent.getStringExtra("gambar");

        // Tampilkan data ke komponen UI
        if (namaLapangan != null) {
            placeNameTextView.setText(namaLapangan);
        }
        if (lokasi != null) {
            placeLocationTextView.setText(lokasi);
        }
        if (harga != null) {
            placeHargaTextView.setText(harga);
        }
        if (deskripsi != null) {
            placeDetailsTextView.setText(deskripsi);
        }

        // Menampilkan gambar dari drawable
        if (gambar != null) {
            int imageResId = getResources().getIdentifier(gambar, "drawable", getPackageName());
            if (imageResId != 0) {
                imageDetails.setImageResource(imageResId);
            }
        }

        // Tambahkan listener pada backButton
        backButton.setOnClickListener(v -> finish());

        // Tambahkan listener pada btnPilihJadwal
        btnPilihJadwal.setOnClickListener(v -> {
            Intent bookingIntent = new Intent(DetailsActivity.this, BookingActivity.class);
            bookingIntent.putExtra("namaLapangan", namaLapangan); // Kirim data ke BookingActivity
            bookingIntent.putExtra("lokasi", lokasi);
            bookingIntent.putExtra("harga", harga);
            startActivity(bookingIntent);
        });
    }
}
