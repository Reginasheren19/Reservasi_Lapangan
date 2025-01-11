package com.example.reservasi_lapangan;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class BookingActivity extends AppCompatActivity {

    private TextView tvNamaLapangan, tvHarga, tvLokasi;
    private EditText inputNama, inputTelepon, inputDate;
    private DatabaseHelper databaseHelper;
    private ArrayList<String> selectedTimes = new ArrayList<>(); // List untuk menyimpan waktu yang dipilih
    private final String[] waktuMulai = {
            "08:00", "09:00", "10:00", "11:00", "12:00",
            "13:00", "14:00", "15:00", "16:00", "17:00",
            "18:00", "19:00", "20:00", "21:00"
    };

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

        GridView gridView = findViewById(R.id.gridView);
        Button btnBayarBooking = findViewById(R.id.btnBayarBooking);
        ImageView backButtonbook = findViewById(R.id.backButtonbook);

        // Ambil data dari Intent
        Intent intent = getIntent();
        String namaLapangan = intent.getStringExtra("namaLapangan");
        String harga = intent.getStringExtra("harga");
        String lokasi = intent.getStringExtra("lokasi");
        int idLapangan = intent.getIntExtra("idLapangan", -1);

        // Validasi ID lapangan
        if (idLapangan == -1) {
            Toast.makeText(this, "ID lapangan tidak valid.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Tampilkan data di TextView
        tvNamaLapangan.setText(namaLapangan != null ? namaLapangan : "N/A");
        tvHarga.setText(harga != null ? harga : "N/A");
        tvLokasi.setText(lokasi != null ? lokasi : "N/A");

        // Inisialisasi DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Setup GridView
        TimeAdapter adapter = new TimeAdapter(this, waktuMulai, selectedTimes);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedTime = waktuMulai[position];
            if (selectedTimes.contains(selectedTime)) {
                selectedTimes.remove(selectedTime);
                Button button = view.findViewById(R.id.btnGridItem);
                button.setBackgroundColor(getResources().getColor(R.color.white));
                button.setTextColor(getResources().getColor(R.color.black));
                Toast.makeText(this, "Waktu dibatalkan: " + selectedTime, Toast.LENGTH_SHORT).show();
            } else {
                selectedTimes.add(selectedTime);
                Button button = view.findViewById(R.id.btnGridItem);
                button.setBackgroundColor(getResources().getColor(R.color.teal_700));
                button.setTextColor(getResources().getColor(R.color.white));
                Toast.makeText(this, "Waktu terpilih: " + selectedTime, Toast.LENGTH_SHORT).show();
            }
        });

        // Listener tombol kembali
        backButtonbook.setOnClickListener(view -> finish());

        // Listener tombol bayar booking
        btnBayarBooking.setOnClickListener(view -> {
            String nama = inputNama.getText().toString().trim();
            String telepon = inputTelepon.getText().toString().trim();
            String tanggalBooking = inputDate.getText().toString().trim();

            // Validasi input
            if (nama.isEmpty() || telepon.isEmpty() || tanggalBooking.isEmpty() || selectedTimes.isEmpty()) {
                Toast.makeText(this, "Mohon lengkapi semua data booking.", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                double totalHarga = Double.parseDouble(harga);

                // Simpan data booking ke database
                boolean isInserted = databaseHelper.insertTransaksiBooking(
                        nama, telepon, idLapangan, tanggalBooking, selectedTimes.toString(), totalHarga, "Pending"
                );

                if (isInserted) {
                    Toast.makeText(this, "Booking berhasil! Menuju halaman pembayaran.", Toast.LENGTH_SHORT).show();

                    // Pindah ke PaymentActivity
                    Intent paymentIntent = new Intent(BookingActivity.this, PaymentActivity.class);
                    paymentIntent.putExtra("nama", nama);
                    paymentIntent.putExtra("telepon", telepon);
                    paymentIntent.putExtra("tanggalBooking", tanggalBooking);
                    paymentIntent.putExtra("selectedTimes", selectedTimes);
                    paymentIntent.putExtra("totalHarga", totalHarga);
                    paymentIntent.putExtra("idLapangan", idLapangan);
                    startActivity(paymentIntent);
                    finish();
                } else {
                    Toast.makeText(this, "Gagal menyimpan data booking. Coba lagi.", Toast.LENGTH_SHORT).show();
                }
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Format harga tidak valid.", Toast.LENGTH_SHORT).show();
                Log.e("BookingActivity", "Error parsing harga: " + e.getMessage());
            }
        });
    }
}
