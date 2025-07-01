package com.jess.utsmobile;

import static androidx.appcompat.app.AppCompatActivity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private TextView masukText, keluarText, saldoText;
    private DatabaseHelper db;
    private ArrayList<TransaksiModel> transaksiList;
    private TransaksiAdapter adapter;
    private static final int REQUEST_CODE = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        masukText = view.findViewById(R.id.masuk);
        keluarText = view.findViewById(R.id.keluar);
        saldoText = view.findViewById(R.id.saldo);
        FloatingActionButton tambahButton = view.findViewById(R.id.tambah);

        tambahButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), PilihanActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        db = new DatabaseHelper(getContext());
        transaksiList = db.getAllTransaksi();
        adapter = new TransaksiAdapter(transaksiList);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemDeleteListener(position -> {
            TransaksiModel transaksiDihapus = transaksiList.get(position);
            db.deleteTransaksi(transaksiDihapus.getId()); // Hapus dari database
            transaksiList.remove(position); // Hapus dari list
            adapter.notifyItemRemoved(position); // Beri tahu adapter
            updateTotal(); // Perbarui total saldo
        });

        updateTotal(); // Panggil sekali saat tampilan dibuat
    }

    // Hanya ada satu metode onActivityResult
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            // Cek apakah ada data baru yang perlu ditambahkan atau cukup diperbarui
            boolean needsUpdate = data.getBooleanExtra("NEEDS_UPDATE", false);
            if (needsUpdate) {
                // Muat ulang data dari database dan perbarui tampilan
                transaksiList.clear();
                transaksiList.addAll(db.getAllTransaksi());
                adapter.notifyDataSetChanged();
                updateTotal();
            }
        }
    }

    // Metode updateTotal yang sudah diperbaiki
    private void updateTotal() {
        double totalMasuk = db.getTotalPemasukan();
        double totalKeluar = db.getTotalPengeluaran();
        double totalSaldo = totalMasuk - totalKeluar;

        // Atur format Rupiah sekali saja
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        formatRupiah.setMaximumFractionDigits(0); // Hilangkan desimal

        // Langsung atur teks dengan format yang benar
        masukText.setText(formatRupiah.format(totalMasuk));
        keluarText.setText(formatRupiah.format(totalKeluar));
        saldoText.setText(formatRupiah.format(totalSaldo));
    }
}