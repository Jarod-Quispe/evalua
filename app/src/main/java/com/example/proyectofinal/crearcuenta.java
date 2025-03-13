package com.example.proyectofinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class crearcuenta extends AppCompatActivity {
    EditText etNombre, etEmail, etPassword;
    Button btnRegister, btnIrLogin;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crearcuenta);

        etNombre = findViewById(R.id.etNombreRegister);
        etEmail = findViewById(R.id.etEmailRegister);
        etPassword = findViewById(R.id.etPasswordRegister);
        btnRegister = findViewById(R.id.btnRegister);
        btnIrLogin = findViewById(R.id.btnIrLogin);
        dbHelper = new DatabaseHelper(this);

        // Botón para registrar usuario
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = etNombre.getText().toString();
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                if (nombre.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(crearcuenta.this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
                } else {
                    dbHelper.insertarUsuario(nombre, email, password);
                    Toast.makeText(crearcuenta.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(crearcuenta.this, MainActivity.class)); // Redirigir al inicio de sesión
                    finish();
                }
            }
        });

        // Botón para volver a la pantalla de inicio de sesión
        btnIrLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(crearcuenta.this, MainActivity.class)); // Volver a la pantalla de inicio
                finish();
            }
        });
    }
}