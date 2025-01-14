package com.example.reservasi_lapangan;

import android.content.Intent;
import android.icu.text.DecimalFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Locale;
import java.util.Random;

public class PaymentActivity extends AppCompatActivity {

    private TextView tvTotalHarga, virtualAccountTextView, virtualAccountExpirationTextView;
    private TextView customerNameTextView, customerPhoneTextView, fieldNameTextView, fieldAddressTextView;
    private TextView bookingDateTextView, bookingTimeTextView;
    private Button backToHomeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        // Inisialisasi komponen UI
        tvTotalHarga = findViewById(R.id.tvTotalHarga);
        virtualAccountTextView = findViewById(R.id.virtualAccountField);
        virtualAccountExpirationTextView = findViewById(R.id.virtualAccountExpiration);
        customerNameTextView = findViewById(R.id.customerName);
        customerPhoneTextView = findViewById(R.id.customerPhone);
        bookingDateTextView = findViewById(R.id.bookingDate);
        bookingTimeTextView = findViewById(R.id.bookingTime);
        backToHomeButton = findViewById(R.id.backToHomeButton);

        ImageView backButtonbook = findViewById(R.id.backButtonbook);

        // Ambil data dari Intent
        int idLapangan = getIntent().getIntExtra("idLapangan", -1); // Default -1 jika tidak ditemukan
        double totalHarga = getIntent().getDoubleExtra("totalHarga", 0);
        String namaPemesan = getIntent().getStringExtra("nama");
        String teleponPemesan = getIntent().getStringExtra("telepon");
        String tanggalBooking = getIntent().getStringExtra("tanggalBooking");
        ArrayList<String> selectedTimes = getIntent().getStringArrayListExtra("selectedTimes");



        // Validasi data yang diterima
        if (idLapangan == -1 || namaPemesan == null || teleponPemesan == null || selectedTimes == null) {
            Toast.makeText(this, "Data pemesanan tidak valid!", Toast.LENGTH_SHORT).show();
            finish(); // Tutup activity jika data tidak valid
            return;
        }

        // Format total harga dalam Rupiah
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String formattedTotalHarga = decimalFormat.format(totalHarga);

        // Tampilkan total harga
        tvTotalHarga.setText("Total Harga: Rp " + formattedTotalHarga);

        // Tampilkan informasi pemesan
        customerNameTextView.setText("Nama Pemesan: " + namaPemesan);
        customerPhoneTextView.setText("Telepon: " + teleponPemesan);

        // Tampilkan tanggal booking
        bookingDateTextView.setText("Tanggal Booking: " + tanggalBooking);

        // Listener tombol kembali
        backButtonbook.setOnClickListener(view -> finish());

        // Tampilkan waktu yang dipilih
        StringBuilder times = new StringBuilder();
        for (String time : selectedTimes) {
            times.append(time).append(" ");
        }
        bookingTimeTextView.setText("Waktu: " + times.toString());

        // Simulasi Virtual Account ID
        String virtualAccount = idLapangan + generateUniqueCode();
        virtualAccountTextView.setText(virtualAccount);
        virtualAccountTextView.setVisibility(View.VISIBLE);

        // Simulasi waktu kadaluarsa Virtual Account (2 jam dari sekarang)
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, 2);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault());
        String expirationTime = "Kadaluarsa pada: " + sdf.format(calendar.getTime());

        virtualAccountExpirationTextView.setText(expirationTime);
        virtualAccountExpirationTextView.setVisibility(View.VISIBLE);

        // Listener untuk Tombol Kembali ke Halaman Awal
        backToHomeButton.setOnClickListener(v -> {
            // Pindah ke halaman awal atau halaman lain
            Intent intent = new Intent(PaymentActivity.this, MainActivity.class); // Sesuaikan dengan halaman awal
            intent.putExtra("idLapangan", idLapangan); // Jika perlu mengirim ulang data idLapangan
            startActivity(intent);
            finish();  // Menutup PaymentActivity
        });
    }

    // Method untuk menghasilkan kode unik 16 digit
    private String generateUniqueCode() {
        Random random = new Random();
        StringBuilder uniqueCode = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            int digit = random.nextInt(10); // Menghasilkan angka acak antara 0-9
            uniqueCode.append(digit);
        }
        return uniqueCode.toString();
    }
}
