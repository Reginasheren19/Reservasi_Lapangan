package com.example.reservasi_lapangan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
    private String[] waktuBooking = {
            "08:00", "09:00", "10:00", "11:00", "12:00",
            "13:00", "14:00", "15:00", "16:00", "17:00"
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

        // Inisialisasi GridView
        GridView gridView = findViewById(R.id.gridView);

        // Inisialisasi GridView
        GridView gridView = findViewById(R.id.gridView);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.grid_item, R.id.btnGridItem, waktuBooking);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedTime = waktuBooking[position];

                // Jika waktu sudah dipilih, hapus dari daftar; jika belum, tambahkan ke daftar
                if (selectedTimes.contains(selectedTime)) {
                    selectedTimes.remove(selectedTime);
                    Button button = view.findViewById(R.id.btnGridItem);
                    button.setBackgroundColor(getResources().getColor(R.color.white)); // Reset background color
                    button.setTextColor(getResources().getColor(R.color.black)); // Reset text color
                    Toast.makeText(BookingActivity.this, "Waktu dibatalkan: " + selectedTime, Toast.LENGTH_SHORT).show();
                } else {
                    selectedTimes.add(selectedTime);
                    Button button = view.findViewById(R.id.btnGridItem);
                    button.setBackgroundColor(getResources().getColor(R.color.teal_700)); // Highlight selected time
                    button.setTextColor(getResources().getColor(R.color.white));
                    Toast.makeText(BookingActivity.this, "Waktu terpilih: " + selectedTime, Toast.LENGTH_SHORT).show();
                }
            }
        });

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
            tvHarga.setText(harga);
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
