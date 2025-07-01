package com.jess.utsmobile;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.jess.utsmobile.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    DatabaseHelper databaseHelper;
    private boolean isPasswordVisible = false; // Flag untuk toggle

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseHelper = new DatabaseHelper(this);

        // Menangani klik tombol login
        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Kode BARU yang disarankan
                String email = binding.loginEmail.getText().toString().trim();
                String password = binding.loginPassword.getText().toString().trim();

                if (email.equals("") || password.equals(""))
                    Toast.makeText(LoginActivity.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                else {
                    if (databaseHelper.checkEmailPassword(email, password)) {
                        Log.d("LOGIN", "Login successful for user: " + email);
                        Toast.makeText(LoginActivity.this, "Login Successfully!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("email", email);
                        startActivity(intent);
                    } else {
                        Log.d("LOGIN", "Invalid credentials for user: " + email);
                        Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Redirect ke register
        binding.signupRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        // Menangani tombol mata untuk melihat/sesembunyikan password
        binding.passwordToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPasswordVisible) {
                    // Sembunyikan password
                    binding.loginPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    binding.passwordToggle.setImageResource(R.drawable.baseline_remove_red_eye_24);
                } else {
                    // Tampilkan password
                    binding.loginPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                    binding.passwordToggle.setImageResource(R.drawable.baseline_remove_red_eye_24);
                }
                isPasswordVisible = !isPasswordVisible; // Toggle state
                binding.loginPassword.setSelection(binding.loginPassword.getText().length()); // Pindahkan kursor ke akhir
            }
        });
    }
}
