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

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Locale;

public class BookingActivity extends AppCompatActivity {

    private TextView tvNamaLapangan, tvHarga, tvLokasi, tvTotalHarga;
    private EditText inputNama, inputTelepon, inputDate;
    private DatabaseHelper databaseHelper;
    private ArrayList<String> selectedTimes = new ArrayList<>(); // List untuk menyimpan waktu yang dipilih
    private final String[] waktuMulai = {
            "08:00", "09:00", "10:00", "11:00", "12:00",
            "13:00", "14:00", "15:00", "16:00", "17:00",
            "18:00", "19:00", "20:00", "21:00"
    };

    private double hargaPerJam = 0.0; // Harga per jam yang akan diambil dari Intent

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        // Inisialisasi komponen UI
        tvNamaLapangan = findViewById(R.id.tvNamaLapangan);
        tvHarga = findViewById(R.id.tvHarga);
        tvLokasi = findViewById(R.id.tvLokasi);
        tvTotalHarga = findViewById(R.id.tvTotalHarga);
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

        // Parsing harga menggunakan NumberFormat sesuai locale Indonesia
        NumberFormat format = NumberFormat.getInstance(new Locale("id", "ID"));
        try {
            // Ambil harga dari Intent dan pastikan format yang diterima bersih dari simbol mata uang
            String hargaString = harga.replaceAll("[^\\d,]", "");  // Hapus simbol non-digit dan koma

            // Parsing harga yang telah dibersihkan
            hargaPerJam = format.parse(hargaString).doubleValue();
            Log.d("BookingActivity", "Harga per jam: " + hargaPerJam); // Log untuk memastikan hargaPerJam sudah terisi dengan benar
        } catch (ParseException e) {
            Log.e("BookingActivity", "Error parsing harga: " + e.getMessage());
            Toast.makeText(this, "Terjadi kesalahan pada format harga.", Toast.LENGTH_SHORT).show();
            hargaPerJam = 0.0; // Nilai default jika parsing gagal
        }

        // Tampilkan data di TextView
        tvNamaLapangan.setText(namaLapangan != null ? namaLapangan : "N/A");
        tvHarga.setText(harga != null ? harga : "N/A");
        tvLokasi.setText(lokasi != null ? lokasi : "N/A");
        tvTotalHarga.setText("Total Harga: 0");


        // Inisialisasi DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Setup GridView
        TimeAdapter adapter = new TimeAdapter(this, waktuMulai, selectedTimes);
        gridView.setAdapter(adapter);

        // Listener untuk GridView
        gridView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedTime = waktuMulai[position];

            Button button = (Button) view;
            if (selectedTimes.contains(selectedTime)) {
                // Hapus waktu dari daftar pilihan
                selectedTimes.remove(selectedTime);
                button.setBackgroundColor(getResources().getColor(R.color.white));
                button.setTextColor(getResources().getColor(R.color.black));
                Toast.makeText(this, "Waktu dibatalkan: " + selectedTime, Toast.LENGTH_SHORT).show();
            } else {
                // Tambahkan waktu ke daftar pilihan
                selectedTimes.add(selectedTime);
                button.setBackgroundColor(getResources().getColor(R.color.teal_700));
                button.setTextColor(getResources().getColor(R.color.white));
                Toast.makeText(this, "Waktu terpilih: " + selectedTime, Toast.LENGTH_SHORT).show();
            }

            // Hitung total harga berdasarkan jumlah waktu yang dipilih
            double totalHarga = hargaPerJam * selectedTimes.size();

            // Format total harga menggunakan NumberFormat untuk tampilan yang rapi
            String formattedTotalHarga = NumberFormat.getInstance(new Locale("id", "ID")).format(totalHarga);

            // Perbarui tvTotalHarga dengan total harga yang telah diformat
            tvTotalHarga.setText("Total Harga: " + formattedTotalHarga);
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
            // Hitung total harga
            double totalHarga = hargaPerJam * selectedTimes.size();

            // Simpan data booking ke database
            boolean isInserted = databaseHelper.insertTransaksiBooking(
                    idLapangan, nama, telepon, tanggalBooking, selectedTimes.toString(), totalHarga
            );

                if (isInserted) {
                    Toast.makeText(this, "Booking berhasil! Menuju halaman pembayaran.", Toast.LENGTH_SHORT).show();

                    // Pindah ke PaymentActivity
                    Intent paymentIntent = new Intent(BookingActivity.this, PaymentActivity.class);
                    paymentIntent.putExtra("idLapangan", idLapangan);
                    paymentIntent.putExtra("nama", nama);
                    paymentIntent.putExtra("telepon", telepon);
                    paymentIntent.putExtra("tanggalBooking", tanggalBooking);
                    paymentIntent.putExtra("selectedTimes", selectedTimes);
                    paymentIntent.putExtra("totalHarga", totalHarga);
                    startActivity(paymentIntent);
                    finish();
                } else {
                    Toast.makeText(this, "Gagal menyimpan data booking. Coba lagi.", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(this, "Terjadi kesalahan saat memproses booking.", Toast.LENGTH_SHORT).show();
                Log.e("BookingActivity", "Error: " + e.getMessage());
            }
        });
    }
}
