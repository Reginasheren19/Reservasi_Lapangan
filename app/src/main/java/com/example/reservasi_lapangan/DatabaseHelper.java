package com.example.reservasi_lapangan;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

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
        // Membuat tabel users dan lapangan
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_LAPANGAN);

        // Menambahkan data awal ke tabel lapangan
        insertInitialLapanganData(db);
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
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", password);

        long result = db.insert("users", null, values);
        db.close();
        return result != -1;
    }

    // Menambahkan data awal ke tabel lapangan
    private void insertInitialLapanganData(SQLiteDatabase db) {
        ContentValues values = new ContentValues();

        // Data lapangan 1
        values.put("nama_lapangan", "Lapangan Futsal");
        values.put("lokasi", "Jl. Raya Malang No.1 Kavling A");
        values.put("harga", 100000);
        values.put("deskripsi", "Lapangan futsal indoor dengan rumput sintetis berkualitas tinggi yang dirancang untuk memberikan pengalaman bermain yang nyaman dan aman bagi pemain. Lapangan dilengkapi dengan pencahayaan LED yang terang sehingga memungkinkan bermain di malam hari tanpa masalah visibilitas. Selain itu, terdapat fasilitas pendukung seperti ruang ganti yang bersih, kamar mandi dengan air panas, serta area parkir yang luas. Cocok untuk kegiatan olahraga rutin maupun turnamen futsal skala kecil. Lokasi strategis dan mudah dijangkau dari berbagai arah.");
        values.put("gambar", "lapangan_futsal");
        db.insert("lapangan", null, values);

        // Data lapangan 2
        values.clear();
        values.put("nama_lapangan", "Lapangan Basket");
        values.put("lokasi", "Jl. Raya Surabaya No.2 Blok B");
        values.put("harga", 120000);
        values.put("deskripsi", "Lapangan basket outdoor dengan permukaan yang dilapisi cat anti-selip, dirancang untuk mengurangi risiko cedera pemain. Lapangan ini memiliki dua ring basket yang kokoh dan memenuhi standar nasional. Fasilitas pendukung meliputi tribun penonton, area duduk untuk istirahat, serta mesin penjual minuman otomatis. Pencahayaan lapangan memadai untuk permainan hingga malam hari, menjadikannya pilihan ideal bagi penggemar basket.");
        values.put("gambar", "lapangan_basket");
        db.insert("lapangan", null, values);

        // Data lapangan 3
        values.clear();
        values.put("nama_lapangan", "Lapangan Badminton");
        values.put("lokasi", "Jl. Raya Bandung No.3 Kompleks C");
        values.put("harga", 90000);
        values.put("deskripsi", "Lapangan badminton indoor dengan lantai kayu yang dirancang khusus untuk meningkatkan kenyamanan pemain serta mengurangi tekanan pada sendi. Terdapat lima lapangan dalam satu ruangan besar dengan pencahayaan yang merata. Fasilitas meliputi ruang tunggu, area loker, dan kamar mandi dengan air panas. Cocok untuk latihan rutin maupun pertandingan persahabatan.");
        values.put("gambar", "lapangan_badminton");
        db.insert("lapangan", null, values);

        // Data lapangan 4
        values.clear();
        values.put("nama_lapangan", "Lapangan Tenis");
        values.put("lokasi", "Jl. Raya Jogja No.4");
        values.put("harga", 150000);
        values.put("deskripsi", "Lapangan tenis dengan permukaan keras berkualitas tinggi yang sesuai dengan standar internasional. Lapangan ini memiliki garis yang jelas dan jaring yang terawat dengan baik. Lokasi strategis dengan pemandangan sekitar yang hijau, cocok untuk latihan profesional maupun turnamen kecil. Fasilitas tambahan mencakup ruang ganti, area parkir luas, dan taman kecil di sekitar lapangan.");
        values.put("gambar", "lapangan_tenis");
        db.insert("lapangan", null, values);

        // Data lapangan 5
        values.clear();
        values.put("nama_lapangan", "Lapangan Voli");
        values.put("lokasi", "Jl. Raya Semarang No.5");
        values.put("harga", 80000);
        values.put("deskripsi", "Lapangan voli dengan pasir pantai berkualitas tinggi yang dirancang khusus untuk memberikan pengalaman bermain voli pantai. Lapangan ini dilengkapi dengan garis pembatas yang mudah terlihat serta net voli yang memenuhi standar turnamen. Area sekitar dilengkapi dengan tempat duduk penonton dan pohon-pohon rindang yang memberikan suasana sejuk. Cocok untuk bermain rekreasional maupun latihan intensif.");
        values.put("gambar", "lapangan_voli");
        db.insert("lapangan", null, values);

        // Data lapangan 6
        values.clear();
        values.put("nama_lapangan", "Lapangan Sepak Bola");
        values.put("lokasi", "Jl. Raya Solo No.6");
        values.put("harga", 200000);
        values.put("deskripsi", "Lapangan sepak bola standar internasional dengan rumput alami yang terawat dengan baik. Lapangan ini memiliki ukuran resmi untuk pertandingan dan dilengkapi dengan dua gawang profesional. Fasilitas tambahan meliputi tribun penonton, ruang ganti, serta kamar mandi dengan air bersih dan air panas. Lapangan ini sering digunakan untuk turnamen antar klub.");
        values.put("gambar", "lapangan_sepakbola");
        db.insert("lapangan", null, values);

        // Data lapangan 7
        values.clear();
        values.put("nama_lapangan", "Lapangan Golf Mini");
        values.put("lokasi", "Jl. Raya Bali No.8");
        values.put("harga", 180000);
        values.put("deskripsi", "Lapangan golf mini dengan 18 hole yang dirancang untuk memberikan pengalaman bermain golf santai bagi semua kalangan. Lapangan ini memiliki berbagai rintangan menarik dan cocok untuk keluarga maupun acara perusahaan. Fasilitas pendukung mencakup area parkir luas, kafe, dan toko perlengkapan golf. Lokasi sangat strategis dan memberikan suasana yang asri.");
        values.put("gambar", "lapangan_golfmini");
        db.insert("lapangan", null, values);

        // Tambahkan data lainnya sesuai kebutuhan...
    }

    // Metode untuk mengambil semua data lapangan
    public List<Lapangan> getAllLapangan() {
        List<Lapangan> lapanganList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM lapangan";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Lapangan lapangan = new Lapangan(
                        cursor.getInt(cursor.getColumnIndexOrThrow("id_lapangan")),
                        cursor.getString(cursor.getColumnIndexOrThrow("nama_lapangan")),
                        cursor.getString(cursor.getColumnIndexOrThrow("lokasi")),
                        cursor.getDouble(cursor.getColumnIndexOrThrow("harga")),
                        cursor.getString(cursor.getColumnIndexOrThrow("deskripsi")),
                        cursor.getString(cursor.getColumnIndexOrThrow("gambar"))
                );
                lapanganList.add(lapangan);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return lapanganList;
    }
}
