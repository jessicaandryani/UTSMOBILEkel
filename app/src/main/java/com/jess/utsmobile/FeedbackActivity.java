package com.jess.utsmobile;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class FeedbackActivity extends AppCompatActivity {

    private RatingBar ratingBar;
    private EditText commentBox;
    private Button sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_feedback);

        // Menginisialisasi komponen UI
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Menampilkan tombol kembali di toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24);
            getSupportActionBar().setTitle("Beri Komentar");// Ikon kembali
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        ratingBar = findViewById(R.id.rating_bar);
        commentBox = findViewById(R.id.comment_box);
        sendButton = findViewById(R.id.send_button);

        // Mengatur listener untuk tombol kirim
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Mendapatkan nilai rating dan komentar
                float rating = ratingBar.getRating();
                String comment = commentBox.getText().toString();

                if (rating == 0) {
                    Toast.makeText(FeedbackActivity.this, "Harap beri rating terlebih dahulu", Toast.LENGTH_SHORT).show();
                } else {
                    // Simpan atau kirim data rating dan komentar
                    sendFeedback(rating, comment);
                }
            }
        });

        // Menangani inset sistem pada layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void sendFeedback(float rating, String comment) {
        // Misalnya, simpan data ke server atau database
        // Untuk saat ini, kita hanya menunjukkan pesan
        Toast.makeText(this, "Rating: " + rating + "\nKomentar: " + comment, Toast.LENGTH_LONG).show();

        // Reset form setelah feedback terkirim
        ratingBar.setRating(0);
        commentBox.setText("");
    }
}
