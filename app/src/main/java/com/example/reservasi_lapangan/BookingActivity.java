package com.example.reservasi_lapangan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BookingActivity extends AppCompatActivity {

    private TextView tvNamaLapangan, tvHarga, tvLokasi;
    private EditText inputNama, inputTelepon, inputDate;
    private DatabaseHelper databaseHelper;


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

        // Inisialisasi tombol back
        ImageView backButtonbook = findViewById(R.id.backButtonbook);

        // Listener untuk tombol back
        backButtonbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // Mengakhiri activity dan kembali ke sebelumnya
            }
        });


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

        // Inisialisasi DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Button bayar booking
        Button btnBayarBooking = findViewById(R.id.btnBayarBooking);
        btnBayarBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Mendapatkan data input dari EditText
                String nama = inputNama.getText().toString().trim();
                String telepon = inputTelepon.getText().toString().trim();
                String tanggalBooking = inputDate.getText().toString().trim();
                String username = "user_example"; // Sesuaikan dengan nama user yang login

                // Ambil ID lapangan dan harga dari Intent
                int idLapangan = intent.getIntExtra("idLapangan", -1);
                double totalHarga = Double.parseDouble(harga);

                // Simpan transaksi booking ke database
                boolean isSuccess = databaseHelper.insertTransaksiBooking(username, idLapangan, tanggalBooking, "09:00", "11:00", totalHarga, "Booked");

                if (isSuccess) {
                    Toast.makeText(BookingActivity.this, "Transaksi berhasil, lanjut ke pembayaran", Toast.LENGTH_SHORT).show();

                    // Lanjut ke PaymentActivity
                    Intent paymentIntent = new Intent(BookingActivity.this, PaymentActivity.class);
                    paymentIntent.putExtra("totalHarga", totalHarga);
                    startActivity(paymentIntent);
                } else {
                    Toast.makeText(BookingActivity.this, "Gagal melakukan transaksi", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
