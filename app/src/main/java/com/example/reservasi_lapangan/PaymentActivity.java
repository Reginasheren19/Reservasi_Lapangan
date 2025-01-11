package com.example.reservasi_lapangan;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PaymentActivity extends AppCompatActivity {

    private TextView tvTotalHarga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        // Inisialisasi komponen UI
        tvTotalHarga = findViewById(R.id.tvTotalHarga);

        // Ambil data total harga dari Intent
        double totalHarga = getIntent().getDoubleExtra("totalHarga", 0);

        // Tampilkan total harga
        tvTotalHarga.setText("Total Harga: Rp " + totalHarga);
    }
}
