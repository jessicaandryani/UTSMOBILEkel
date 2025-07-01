package com.jess.utsmobile;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class KeluarActivity extends AppCompatActivity {

    private EditText etTanggal, etJumlah;
    private Spinner etKategori;
    private Button btnSave;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengeluaran);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // Menampilkan tombol kembali
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24); // Ikon kembali
        }

        etTanggal = findViewById(R.id.ettanggal);
        etKategori = findViewById(R.id.etkategori);
        etJumlah = findViewById(R.id.etjumlah);
        btnSave = findViewById(R.id.btnSave);

        String[] daftarKategori = {"Belanja", "Makanan", "Transportasi", "Hiburan","Tagihan"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, daftarKategori);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        etKategori.setAdapter(adapter);

        db = new DatabaseHelper(this);
        setCurrentDate();
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tanggal = etTanggal.getText().toString();
                String kategori = etKategori.getSelectedItem().toString();
                String jumlahStr = etJumlah.getText().toString();

                if (!tanggal.isEmpty() && !kategori.isEmpty() && !jumlahStr.isEmpty()) {
                    double jumlah = Double.parseDouble(jumlahStr);
                    db.addTransaksi(tanggal, kategori, jumlah, "Pengeluaran");
                    Toast.makeText(KeluarActivity.this, "Pengeluaran disimpan!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(KeluarActivity.this, "Isi semua field!", Toast.LENGTH_SHORT).show();
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