package com.jess.utsmobile;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import androidx.annotation.Nullable;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "UTSMobile.db";
    private static final int DATABASE_VERSION = 2;

    // Tabel Users
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_USERS_EMAIL = "email";
    private static final String COLUMN_USERS_NAMA = "nama";
    private static final String COLUMN_USERS_PASSWORD = "password";

    // Tabel Transaksi
    private static final String TABLE_TRANSAKSI = "transaksi";
    private static final String COLUMN_TRANSAKSI_ID = "id";
    private static final String COLUMN_TRANSAKSI_TANGGAL = "tanggal";
    private static final String COLUMN_TRANSAKSI_KATEGORI = "kategori";
    private static final String COLUMN_TRANSAKSI_JUMLAH = "jumlah";
    private static final String COLUMN_TRANSAKSI_TIPE = "tipe";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USERS_EMAIL + " TEXT UNIQUE, " +
                COLUMN_USERS_NAMA + " TEXT, " +
                COLUMN_USERS_PASSWORD + " TEXT)";
        db.execSQL(CREATE_USERS_TABLE);

        String CREATE_TRANSAKSI_TABLE = "CREATE TABLE " + TABLE_TRANSAKSI + " (" +
                COLUMN_TRANSAKSI_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TRANSAKSI_TANGGAL + " TEXT, " +
                COLUMN_TRANSAKSI_KATEGORI + " TEXT, " +
                COLUMN_TRANSAKSI_JUMLAH + " REAL, " +
                COLUMN_TRANSAKSI_TIPE + " TEXT)";
        db.execSQL(CREATE_TRANSAKSI_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSAKSI);
        onCreate(db);
    }

    public boolean insertData(String email, String nama, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USERS_EMAIL, email);
        contentValues.put(COLUMN_USERS_NAMA, nama);
        contentValues.put(COLUMN_USERS_PASSWORD, password);
        long result = db.insert(TABLE_USERS, null, contentValues);
        return result != -1;
    }

    public boolean checkEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERS_EMAIL + " = ?", new String[]{email});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public boolean checkEmailPassword(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERS_EMAIL + " = ? AND " + COLUMN_USERS_PASSWORD + " = ?", new String[]{email, password});
        boolean valid = cursor.getCount() > 0;
        cursor.close();
        return valid;
    }

    public String getNamaByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_USERS_NAMA + " FROM " + TABLE_USERS + " WHERE " + COLUMN_USERS_EMAIL + " = ?", new String[]{email});
        String nama = null;
        if (cursor.moveToFirst()) {
            nama = cursor.getString(0);
        }
        cursor.close();
        return nama;
    }

    public void addTransaksi(String tanggal, String kategori, double jumlah, String tipe) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TRANSAKSI_TANGGAL, tanggal);
        values.put(COLUMN_TRANSAKSI_KATEGORI, kategori);
        values.put(COLUMN_TRANSAKSI_JUMLAH, jumlah);
        values.put(COLUMN_TRANSAKSI_TIPE, tipe);
        db.insert(TABLE_TRANSAKSI, null, values);
    }

    public double getTotalPemasukan() {
        double total = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_TRANSAKSI_JUMLAH + ") FROM " + TABLE_TRANSAKSI + " WHERE " + COLUMN_TRANSAKSI_TIPE + " = 'Pemasukan'", null);
        if (cursor.moveToFirst()) {
            total = cursor.getDouble(0);
        }
        cursor.close();
        return total;
    }

    public double getTotalPengeluaran() {
        double total = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_TRANSAKSI_JUMLAH + ") FROM " + TABLE_TRANSAKSI + " WHERE " + COLUMN_TRANSAKSI_TIPE + " = 'Pengeluaran'", null);
        if (cursor.moveToFirst()) {
            total = cursor.getDouble(0);
        }
        cursor.close();
        return total;
    }

    public ArrayList<TransaksiModel> getAllTransaksi() {
        ArrayList<TransaksiModel> transaksiList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_TRANSAKSI, null);
        if (cursor.moveToFirst()) {
            do {
                transaksiList.add(new TransaksiModel(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_TRANSAKSI_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TRANSAKSI_TANGGAL)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TRANSAKSI_KATEGORI)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_TRANSAKSI_JUMLAH)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TRANSAKSI_TIPE))
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return transaksiList;
    }

    public void deleteTransaksi(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TRANSAKSI, COLUMN_TRANSAKSI_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public double getTotalSaldo() {
        return getTotalPemasukan() - getTotalPengeluaran();
    }
}