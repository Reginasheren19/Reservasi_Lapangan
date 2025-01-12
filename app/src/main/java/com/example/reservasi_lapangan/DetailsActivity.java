package com.example.reservasi_lapangan;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;
import java.util.Locale;

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
        placeHargaTextView = findViewById(R.id.placeharga); // Pastikan ID ini cocok dengan layout
        placeDetailsTextView = findViewById(R.id.placedetails);
        imageDetails = findViewById(R.id.imagedetails);
        ImageView backButton = findViewById(R.id.backButton);
        Button btnPilihJadwal = findViewById(R.id.BtnPilihJadwal);

        // Ambil data dari Intent
        Intent intent = getIntent();
        int idLapangan = intent.getIntExtra("idLapangan", -1); // Default -1 jika tidak ditemukan
        String namaLapangan = intent.getStringExtra("namaLapangan");
        String lokasi = intent.getStringExtra("lokasi");
        String harga = intent.getStringExtra("harga");
        String deskripsi = intent.getStringExtra("deskripsi");
        String gambar = intent.getStringExtra("gambar");

        // Log untuk debug data yang diterima
        Log.d("DetailsActivity", "ID Lapangan diterima: " + idLapangan);
        Log.d("DetailsActivity", "Nama Lapangan: " + namaLapangan);
        Log.d("DetailsActivity", "Lokasi: " + lokasi);
        Log.d("DetailsActivity", "Harga: " + harga);
        Log.d("DetailsActivity", "Deskripsi: " + deskripsi);
        Log.d("DetailsActivity", "Gambar: " + gambar);

        // Tampilkan data ke komponen UI
        if (namaLapangan != null) {
            placeNameTextView.setText(namaLapangan);
        }
        if (lokasi != null) {
            placeLocationTextView.setText(lokasi);
        }

        // Format harga ke dalam format Indonesia (Rp 100.000,00)
        if (harga != null) {
            NumberFormat format = NumberFormat.getNumberInstance(new Locale("id", "ID"));
            try {
                // Hapus simbol non-digit dan koma jika ada pada string harga
                String hargaBersih = harga.replaceAll("[^\\d,]", ""); // Hanya ambil angka dan koma
                double hargaValue = format.parse(hargaBersih).doubleValue();

                // Format ulang harga dan tambahkan ,00
                String formattedHarga = "Rp " + format.format(hargaValue) + ",00";
                placeHargaTextView.setText(formattedHarga);
            } catch (Exception e) {
                Log.e("DetailsActivity", "Error parsing harga: " + e.getMessage());
                placeHargaTextView.setText(harga); // Tampilkan harga asli jika gagal
            }
        }

        if (deskripsi != null) {
            placeDetailsTextView.setText(deskripsi);
        }

        // Menampilkan gambar dari drawable atau default jika tidak ada
        if (gambar != null) {
            int imageResId = getResources().getIdentifier(gambar, "drawable", getPackageName());
            if (imageResId != 0) {
                imageDetails.setImageResource(imageResId);
            } else {
                Log.e("DetailsActivity", "Gambar tidak ditemukan: " + gambar);
                imageDetails.setImageResource(R.drawable.lapangan_badminton); // Gambar default
            }
        } else {
            // Jika gambar tidak ada, set gambar default
            imageDetails.setImageResource(R.drawable.lapangan_badminton); // Gambar default
        }

        // Tambahkan listener pada tombol back
        backButton.setOnClickListener(v -> finish());

        // Tambahkan listener pada tombol pilih jadwal
        btnPilihJadwal.setOnClickListener(v -> {
            Intent bookingIntent = new Intent(DetailsActivity.this, BookingActivity.class);
            bookingIntent.putExtra("idLapangan", idLapangan); // Kirim ID Lapangan
            bookingIntent.putExtra("namaLapangan", namaLapangan); // Kirim data lain ke BookingActivity
            bookingIntent.putExtra("lokasi", lokasi);
            bookingIntent.putExtra("harga", harga); // Harga asli dikirim
            startActivity(bookingIntent);
        });
    }
}
