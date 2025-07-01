package com.jess.utsmobile;

import android.content.Intent;
import android.os.Bundle;

import android.view.MenuItem;

import android.view.View;
import android.widget.ImageButton;

import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class PilihanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pilihan); // Pastikan ini sesuai dengan nama file XML Anda

        // Inisialisasi Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // Menampilkan tombol kembali
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24); // Ikon kembali
        }

        // Inisialisasi komponen

        // Inisialisasi tombol back
        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Tutup activity ini dan kembali ke activity sebelumnya
            }
        });

        // Inisialisasi komponen lainnya

        ImageView ivIncome = findViewById(R.id.ivIncome);
        ImageView ivExpense = findViewById(R.id.ivExpense);

        // Klik listener untuk Pemasukan
        ivIncome.setOnClickListener(v -> {
            Intent intent = new Intent(PilihanActivity.this, PemasukanActivity.class);
            startActivity(intent);
        });

        // Klik listener untuk Pengeluaran
        ivExpense.setOnClickListener(v -> {
            Intent intent = new Intent(PilihanActivity.this, KeluarActivity.class);
            startActivity(intent);
        });
    }

    // Menangani klik tombol kembali
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // Menutup activity dan kembali ke halaman sebelumnya
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
