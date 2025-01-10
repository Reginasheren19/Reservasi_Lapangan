package com.example.reservasi_lapangan;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Nama Database dan Versi
    private static final String DATABASE_NAME = "reservasi_lapangan.db";
    private static final int DATABASE_VERSION = 1;

    // SQL untuk Membuat Tabel Users
    private static final String CREATE_TABLE_USERS = "CREATE TABLE users (" +
            "id_user INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "username TEXT NOT NULL, " +
            "email TEXT UNIQUE NOT NULL, " +
            "password TEXT NOT NULL);";

    // SQL untuk Membuat Tabel Fields
    private static final String CREATE_TABLE_LAPANGAN = "CREATE TABLE lapangan (" +
            "id_lapangan INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "nama_lapangan TEXT NOT NULL, " +
            "lokasi TEXT NOT NULL, " +
            "harga REAL NOT NULL, " +
            "deskripsi TEXT);";

    // SQL untuk Membuat Tabel Bookings
    private static final String CREATE_TABLE_BOOKINGS = "CREATE TABLE bookings (" +
            "id_booking INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "id_user INTEGER, " +
            "id_lapangan INTEGER, " +
            "tanggal_booking TEXT NOT NULL, " +
            "start_time TEXT NOT NULL, " +
            "end_time TEXT NOT NULL, " +
            "total_harga REAL NOT NULL, " +
            "status TEXT DEFAULT 'Pending', " +
            "FOREIGN KEY (user_id) REFERENCES users(user_id), " +
            "FOREIGN KEY (field_id) REFERENCES fields(field_id));";

    // SQL untuk Membuat Tabel Payments
    private static final String CREATE_TABLE_PAYMENTS = "CREATE TABLE payments (" +
            "id_bayar INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "id_booking INTEGER, " +
            "total_harga REAL NOT NULL, " +
            "virtual_account TEXT NOT NULL, " +
            "FOREIGN KEY (id_booking) REFERENCES bookings(id_booking));";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Membuat semua tabel
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_LAPANGAN);
        db.execSQL(CREATE_TABLE_BOOKINGS);
        db.execSQL(CREATE_TABLE_PAYMENTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop tabel lama jika ada
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS lapangan");
        db.execSQL("DROP TABLE IF EXISTS bookings");
        db.execSQL("DROP TABLE IF EXISTS payments");
        onCreate(db); // Buat ulang tabel
    }
}
