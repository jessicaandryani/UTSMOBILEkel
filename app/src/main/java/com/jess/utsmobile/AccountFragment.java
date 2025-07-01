package com.jess.utsmobile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AccountFragment extends Fragment {

    private RecyclerView recyclerView;
    private AccountAdapter accountAdapter;
    private List<AccountItem> accountItemList;
    private DatabaseHelper databaseHelper;
    private TextView akun;

    public AccountFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        akun = view.findViewById(R.id.akun);

        recyclerView = view.findViewById(R.id.recyclerView);
        databaseHelper = new DatabaseHelper(getContext());

        String emailPengguna = "jessicaandryani@gmail.com";
        String namaPengguna = databaseHelper.getNamaByEmail(emailPengguna);

        if (namaPengguna != null) {
            akun.setText(namaPengguna);
        } else {
            akun.setText("Nama tidak ditemukan");
            Log.d("AccountFragment", "Nama tidak ditemukan untuk email: " + emailPengguna);
        }

        // Setup GridLayoutManager
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

        // Populate List
        accountItemList = new ArrayList<>();
        accountItemList.add(new AccountItem("Tentang", R.drawable.info));
        accountItemList.add(new AccountItem("Pengaturan", R.drawable.setting));
        accountItemList.add(new AccountItem("Keluar", R.drawable.sigout));
        accountItemList.add(new AccountItem("Pengingat", R.drawable.alarm));
        accountItemList.add(new AccountItem("Beri Komentar", R.drawable.like));

        // Set Adapter
        accountAdapter = new AccountAdapter(accountItemList, getContext());
        recyclerView.setAdapter(accountAdapter);

        return view;
    }
}
