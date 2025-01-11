package com.example.reservasi_lapangan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PaymentActivity extends AppCompatActivity {

    private TextView tvTotalHarga;
    private TextView virtualAccountTextView;
    private TextView virtualAccountExpirationTextView;
    private Button backToHomeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        // Inisialisasi komponen UI
        tvTotalHarga = findViewById(R.id.tvTotalHarga);
        virtualAccountTextView = findViewById(R.id.virtualAccountField);
        virtualAccountExpirationTextView = findViewById(R.id.virtualAccountExpiration);
        backToHomeButton = findViewById(R.id.backToHomeButton);

        // Ambil data total harga dari Intent
        double totalHarga = getIntent().getDoubleExtra("totalHarga", 0);

        // Tampilkan total harga
        tvTotalHarga.setText("Total Harga: Rp " + totalHarga);

        // Misalnya, virtual account ID yang diberikan
        String virtualAccount = "1234567890"; // Ganti dengan logika jika perlu

        // Menampilkan Virtual Account
        virtualAccountTextView.setText(virtualAccount);
        virtualAccountTextView.setVisibility(View.VISIBLE);

        // Menampilkan pesan kadaluarsa
        virtualAccountExpirationTextView.setVisibility(View.VISIBLE);

        // Listener untuk Tombol Kembali ke Halaman Awal
        backToHomeButton.setOnClickListener(v -> {
            // Pindah ke halaman awal atau halaman lain
            Intent intent = new Intent(PaymentActivity.this, MainActivity.class); // Ganti dengan halaman awal yang sesuai
            startActivity(intent);
            finish();  // Menutup PaymentActivity
        });
    }
}
