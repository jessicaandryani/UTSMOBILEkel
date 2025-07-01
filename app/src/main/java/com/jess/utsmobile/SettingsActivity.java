package com.jess.utsmobile;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class SettingsActivity extends AppCompatActivity {

    private Switch darkModeSwitch;
    private Switch notificationSwitch;
    private ImageView languageSetting;
    private ImageView feedbackOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Inisialisasi Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Pengaturan");
        }

        // Aksi pada tombol kembali
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        // Inisialisasi Views
        darkModeSwitch = findViewById(R.id.dark_mode_switch);
        notificationSwitch = findViewById(R.id.notification_switch);
        languageSetting = findViewById(R.id.language_setting);
        feedbackOption = findViewById(R.id.feedback_option);

        // Aksi pada pengaturan Mode Gelap
        darkModeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Toast.makeText(this, "Mode Gelap Diaktifkan", Toast.LENGTH_SHORT).show();
                // Tambahkan logika untuk mengaktifkan mode gelap
            } else {
                Toast.makeText(this, "Mode Gelap Dinonaktifkan", Toast.LENGTH_SHORT).show();
                // Tambahkan logika untuk menonaktifkan mode gelap
            }
        });

        // Aksi pada pengaturan Notifikasi
        notificationSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Toast.makeText(this, "Notifikasi Diaktifkan", Toast.LENGTH_SHORT).show();
                // Tambahkan logika untuk mengaktifkan notifikasi
            } else {
                Toast.makeText(this, "Notifikasi Dinonaktifkan", Toast.LENGTH_SHORT).show();
                // Tambahkan logika untuk menonaktifkan notifikasi
            }
        });

        // Aksi pada pengaturan Bahasa
        languageSetting.setOnClickListener(v -> {
            Toast.makeText(this, "Pilih Bahasa", Toast.LENGTH_SHORT).show();
            // Tambahkan Intent atau dialog untuk mengatur bahasa
        });

        // Aksi pada Masukan Pengguna
        feedbackOption.setOnClickListener(v -> {
            Toast.makeText(this, "Buka Halaman Masukan", Toast.LENGTH_SHORT).show();
            // Tambahkan Intent untuk membuka aktivitas feedback
        });
    }
}
