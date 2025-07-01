package com.jess.utsmobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ReminderActivity extends AppCompatActivity {

    private ArrayList<String> reminders;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        // Inisialisasi Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Pengingat");
        }

        // Tombol kembali
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        // Inisialisasi ListView
        ListView reminderList = findViewById(R.id.reminder_list);
        reminders = new ArrayList<>();

        // Menambahkan contoh data
        reminders.add("Reminder 1");
        reminders.add("Reminder 2");
        reminders.add("Reminder 4");
        reminders.add("Reminder 5");
        reminders.add("Reminder 6");
        reminders.add("Reminder 7");
        reminders.add("Reminder 8");
        reminders.add("Reminder 9");
        reminders.add("Reminder 10");
        reminders.add("Reminder 11");
        reminders.add("Reminder 12");
        reminders.add("Reminder 13");
        reminders.add("Reminder 14");
        reminders.add("Reminder 15");
        reminders.add("Reminder 16");

        // Menggunakan ArrayAdapter untuk menampilkan data di ListView
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, reminders);
        reminderList.setAdapter(adapter);

        // Aksi saat item dalam daftar di-klik
        reminderList.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {
            // Tambahkan logika untuk menampilkan detail atau mengedit pengingat
        });

        // Floating Action Button untuk menambah pengingat
        FloatingActionButton addReminderButton = findViewById(R.id.add_reminder_button);
        addReminderButton.setOnClickListener(v -> {
            // Tambahkan Intent untuk membuka form tambah pengingat baru
            Intent intent = new Intent(this, AddRemindesActivity.class);
            startActivity(intent);
        });
    }
}
