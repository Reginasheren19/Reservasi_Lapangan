package com.example.reservasi_lapangan;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Membuat objek DatabaseHelper
        DatabaseHelper dbHelper = new DatabaseHelper(this);

        // Menambahkan data lapangan ke database (ini contoh, Anda bisa menghapusnya setelah data sudah ada di DB)
        // dbHelper.addField("Lapangan Sepak Bola", "Sepak Bola", 100000, "Lapangan sepak bola standar");
        // dbHelper.addField("Lapangan Basket", "Basket", 80000, "Lapangan basket dengan papan ring");


    }
}
