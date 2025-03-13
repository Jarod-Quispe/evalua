package com.example.proyectofinal;

import android.content.Intent;
import android.database.Cursor;
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

public class MainActivity extends AppCompatActivity {
    EditText etEmailLogin, etPasswordLogin;
    Button btnLogin, btnIrCrearCuenta;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etEmailLogin = findViewById(R.id.etEmailLogin);
        etPasswordLogin = findViewById(R.id.etPasswordLogin);
        btnLogin = findViewById(R.id.btnLogin);
        btnIrCrearCuenta = findViewById(R.id.btnIrCrearCuenta);
        dbHelper = new DatabaseHelper(this);

        // Botón para ir a la pantalla de crear cuenta
        btnIrCrearCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, crearcuenta.class)); // Ahora abre la pantalla correcta
            }
        });

        // Botón para iniciar sesión
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmailLogin.getText().toString();
                String password = etPasswordLogin.getText().toString();

                if (validarUsuario(email, password)) {
                    Toast.makeText(MainActivity.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, lista_residuos.class)); // Ir a la pantalla principal
                    finish();
                } else {
                    Toast.makeText(MainActivity.this, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Método para validar usuario en SQLite
    private boolean validarUsuario(String email, String password) {
        Cursor cursor = dbHelper.obtenerUsuarios();
        while (cursor.moveToNext()) {
            String dbEmail = cursor.getString(2); // Columna de email
            String dbPassword = cursor.getString(3); // Columna de contraseña
            if (dbEmail.equals(email) && dbPassword.equals(password)) {
                return true;
            }
        }
        return false;
    }
}