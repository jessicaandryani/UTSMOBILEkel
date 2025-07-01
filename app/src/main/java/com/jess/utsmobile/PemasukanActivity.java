package com.jess.utsmobile;


import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PemasukanActivity extends AppCompatActivity {

    private EditText etTanggal, etJumlah;
    private Spinner etKategori; // Spinner untuk kategori
    private Button btnSave;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pemasukan);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Menampilkan tombol kembali
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24); // Ikon kembali
        }

        // Inisialisasi elemen UI
        etTanggal = findViewById(R.id.ettanggal);
        etKategori = findViewById(R.id.etkategori); // Spinner
        etJumlah = findViewById(R.id.etjumlah);
        btnSave = findViewById(R.id.btnSave);

        // Inisialisasi database
        db = new DatabaseHelper(this);



        // Set daftar kategori ke Spinner
        String[] daftarKategori = {"Gaji", "Bonus", "Investasi", "Lainnya"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, daftarKategori);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        etKategori.setAdapter(adapter);

        // Set tanggal saat ini di EditText
        setCurrentDate();

        // Listener untuk tombol simpan
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tanggal = etTanggal.getText().toString();
                String kategori = etKategori.getSelectedItem().toString(); // Ambil nilai dari Spinner
                String jumlahStr = etJumlah.getText().toString();

                // Validasi input
                if (!tanggal.isEmpty() && !kategori.isEmpty() && !jumlahStr.isEmpty()) {

                    try {
                        double jumlah = Double.parseDouble(jumlahStr);

                        // Simpan data ke database
                        db.addTransaksi(tanggal, kategori, jumlah, "Pemasukan");
                        Toast.makeText(PemasukanActivity.this, "Pemasukan disimpan!", Toast.LENGTH_SHORT).show();
                        finish(); // Kembali ke activity sebelumnya
                    } catch (NumberFormatException e) {
                        Toast.makeText(PemasukanActivity.this, "Jumlah harus berupa angka!", Toast.LENGTH_SHORT).show();
                    }

                    double jumlah = Double.parseDouble(jumlahStr);
                    db.addTransaksi(tanggal, kategori, jumlah, "Pemasukan");

                    // Mengirim result ke HomeFragment untuk menambahkan transaksi baru
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("tanggal", tanggal);
                    resultIntent.putExtra("kategori", kategori);
                    resultIntent.putExtra("jumlah", jumlah);
                    setResult(RESULT_OK, resultIntent);

                    Toast.makeText(PemasukanActivity.this, "Pemasukan disimpan!", Toast.LENGTH_SHORT).show();
                    finish();

                } else {
                    Toast.makeText(PemasukanActivity.this, "Isi semua field!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setCurrentDate() {
        // Mendapatkan tanggal saat ini
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = dateFormat.format(calendar.getTime());

        // Menampilkan tanggal saat ini di EditText
        etTanggal.setText(currentDate);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // Menutup activity dan kembali ke halaman sebelumnya
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
