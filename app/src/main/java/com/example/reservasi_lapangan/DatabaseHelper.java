package com.example.reservasi_lapangan;

import android.content.Context;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    static final String DATABASE_NAME = "reservasi_lapangan.db";
    private static final int DATABASE_VERSION = 1;

    // SQL untuk membuat tabel users
    private static final String CREATE_TABLE_USERS = "CREATE TABLE users (" +
            "id_user INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "username TEXT NOT NULL UNIQUE, " +
            "password TEXT NOT NULL);";

    // SQL untuk membuat tabel lapangan
    private static final String CREATE_TABLE_LAPANGAN = "CREATE TABLE lapangan (" +
            "id_lapangan INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "nama_lapangan TEXT NOT NULL, " +
            "lokasi TEXT NOT NULL, " +
            "harga REAL NOT NULL, " +
            "deskripsi TEXT, " +
            "gambar TEXT);"; // Menyimpan path atau URI gambar


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Membuat tabel users
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_LAPANGAN);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Menghapus tabel lama jika ada dan membuat ulang
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS lapangan");
        onCreate(db);
    }

    // Metode untuk menyisipkan user ke dalam tabel users
    public boolean insertUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();  // Mendapatkan akses tulis ke database
        ContentValues values = new ContentValues();  // Untuk menyimpan data yang akan disisipkan
        values.put("username", username);  // Menambahkan username ke dalam ContentValues
        values.put("password", password);  // Menambahkan password ke dalam ContentValues

        // Menyisipkan baris baru ke tabel users dan menyimpan ID hasil penyisipan
        long result = db.insert("users", null, values);
        db.close();  // Menutup database setelah operasi selesai
        return result != -1;  // Mengembalikan true jika berhasil (ID lebih besar dari -1)
    }

    // Metode untuk menyisipkan data lapangan dengan gambar
    public boolean insertLapangan(String namaLapangan, String lokasi, double harga, String deskripsi, String gambar) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nama_lapangan", namaLapangan);
        values.put("lokasi", lokasi);
        values.put("harga", harga);
        values.put("deskripsi", deskripsi);
        values.put("gambar", gambar); // Menambahkan path gambar

        long result = db.insert("lapangan", null, values);
        db.close();
        return result != -1;
    }
}
