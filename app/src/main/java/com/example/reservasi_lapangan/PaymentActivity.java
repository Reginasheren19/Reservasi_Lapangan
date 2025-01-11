package com.example.reservasi_lapangan;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PaymentActivity extends AppCompatActivity {

    private TextView tvTotalHarga;
    private Spinner metodePembayaranSpinner;
    private EditText nomorRekeningEditText;
    private TextView virtualAccountTextView;
    private Button konfirmasiPembayaranButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        // Inisialisasi komponen UI
        tvTotalHarga = findViewById(R.id.tvTotalHarga);
        metodePembayaranSpinner = findViewById(R.id.metodePembayaran);
        nomorRekeningEditText = findViewById(R.id.nomorRekening);
        virtualAccountTextView = findViewById(R.id.virtualAccountField);
        konfirmasiPembayaranButton = findViewById(R.id.konfirmasiPembayaran);

        // Ambil data total harga dari Intent
        double totalHarga = getIntent().getDoubleExtra("totalHarga", 0);

        // Tampilkan total harga
        tvTotalHarga.setText("Total Harga: Rp " + totalHarga);

        // Data metode pembayaran
        String[] paymentMethods = {"Transfer Bank", "DANA", "OVO", "GoPay"};

        // Adapter untuk Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, paymentMethods);
        metodePembayaranSpinner.setAdapter(adapter);

        // Listener untuk Spinner
        metodePembayaranSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedMethod = parent.getItemAtPosition(position).toString();
                String virtualAccount = generateVirtualAccount(selectedMethod);

                if (!virtualAccount.isEmpty()) {
                    virtualAccountTextView.setText("Virtual Account: " + virtualAccount);
                    virtualAccountTextView.setVisibility(View.VISIBLE);
                } else {
                    virtualAccountTextView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                virtualAccountTextView.setVisibility(View.GONE);
            }
        });

        // Listener untuk Tombol Konfirmasi Pembayaran
        konfirmasiPembayaranButton.setOnClickListener(v -> {
            String nomorRekening = nomorRekeningEditText.getText().toString();

            if (nomorRekening.isEmpty()) {
                Toast.makeText(PaymentActivity.this, "Nomor rekening tidak boleh kosong!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(PaymentActivity.this, "Pembayaran berhasil dikonfirmasi!", Toast.LENGTH_SHORT).show();
                // Lakukan logika pembayaran di sini
            }
        });
    }

    // Metode untuk Menghasilkan Virtual Account Berdasarkan Metode Pembayaran
    private String generateVirtualAccount(String method) {
        switch (method) {
            case "Transfer Bank":
                return "1234567890";
            case "DANA":
                return "9876543210";
            case "OVO":
                return "1122334455";
            case "GoPay":
                return "5566778899";
            default:
                return "";
        }
    }
}
