package com.jess.utsmobile;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.AccountViewHolder> {

    private List<AccountItem> accountItemList;
    private Context context;

    public AccountAdapter(List<AccountItem> accountItemList, Context context) {
        this.accountItemList = accountItemList;
        this.context = context; // Untuk melakukan Intent
    }

    @NonNull
    @Override
    public AccountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_account, parent, false);
        return new AccountViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountViewHolder holder, int position) {
        AccountItem item = accountItemList.get(position);

        // Bind data ke UI
        holder.icon.setImageResource(item.getIcon());
        holder.title.setText(item.getTitle());

        // Tambahkan OnClickListener
        holder.itemView.setOnClickListener(v -> {
            switch (item.getTitle()) {
                case "Tentang":
                    context.startActivity(new Intent(context, AboutActivity.class));
                    break;
                case "Pengaturan":
                    context.startActivity(new Intent(context, SettingsActivity.class));
                    break;
                case "Keluar":
                    // Logout dan kembali ke LoginActivity
                    Intent logoutIntent = new Intent(context, LoginActivity.class);
                    // Jika ingin menutup semua activity sebelumnya dan memastikan pengguna tidak bisa kembali
                    logoutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    context.startActivity(logoutIntent);
                    break;
                case "Pengingat":
                    context.startActivity(new Intent(context, ReminderActivity.class));
                    break;
                case "Beri Komentar":
                    context.startActivity(new Intent(context, FeedbackActivity.class));
                    break;
            }
        });
    }

    @Override
    public int getItemCount() {
        return accountItemList.size();
    }

    public static class AccountViewHolder extends RecyclerView.ViewHolder {

        ImageView icon;
        TextView title;

        public AccountViewHolder(View itemView) {
            super(itemView);

            icon = itemView.findViewById(R.id.icon);
            title = itemView.findViewById(R.id.title);
        }
    }
}
