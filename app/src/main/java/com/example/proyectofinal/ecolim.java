package com.example.proyectofinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ecolim extends AppCompatActivity {

    ImageButton btnOrganico, btnInorganico, btnElectrico, btnOtro;
    EditText etPesoResiduo, etxOtroResiduo;
    Button btnGuardarResiduo, btnVolverLista;
    String tipoResiduoSeleccionado = "";
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecolim);

        btnOrganico = findViewById(R.id.btnOrganico);
        btnInorganico = findViewById(R.id.btnInorganico);
        btnElectrico = findViewById(R.id.btnElectrico);
        btnOtro = findViewById(R.id.btnOtro);
        etPesoResiduo = findViewById(R.id.etPesoResiduo);
        etxOtroResiduo = findViewById(R.id.etxOtroResiduo );
        btnGuardarResiduo = findViewById(R.id.btnGuardarResiduo);
        btnVolverLista = findViewById(R.id.btnVolverLista);
        dbHelper = new DatabaseHelper(this);

        btnOrganico.setOnClickListener(v -> seleccionarResiduo("Orgánico"));
        btnInorganico.setOnClickListener(v -> seleccionarResiduo("Inorgánico"));
        btnElectrico.setOnClickListener(v -> seleccionarResiduo("Eléctrico"));

        btnOtro.setOnClickListener(v -> {
            etxOtroResiduo.setVisibility(View.VISIBLE); // Mostrar el campo de texto
            etxOtroResiduo.requestFocus(); // Enfocar el EditText
            tipoResiduoSeleccionado = ""; // Resetear el tipo seleccionado
        });

        btnGuardarResiduo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tipoResiduoSeleccionado.isEmpty()) {
                    tipoResiduoSeleccionado = etxOtroResiduo.getText().toString().trim(); // Obtener texto del EditText
                    if (tipoResiduoSeleccionado.isEmpty()) {
                        Toast.makeText(ecolim.this, "Selecciona un tipo de residuo", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                String pesoTexto = etPesoResiduo.getText().toString();
                if (pesoTexto.isEmpty()) {
                    Toast.makeText(ecolim.this, "Ingresa el peso del residuo", Toast.LENGTH_SHORT).show();
                    return;
                }

                double peso = Double.parseDouble(pesoTexto);
                dbHelper.insertarResiduo(tipoResiduoSeleccionado, peso);
                Toast.makeText(ecolim.this, "Residuo guardado", Toast.LENGTH_SHORT).show();

                // Redirigir a la lista de residuos
                Intent intent = new Intent(ecolim.this, lista_residuos.class);
                startActivity(intent);
                finish();
            }
        });

        btnVolverLista.setOnClickListener(view -> finish());
    }

    private void seleccionarResiduo(String tipo) {
        tipoResiduoSeleccionado = tipo;
        etxOtroResiduo.setVisibility(View.GONE); // Ocultar el EditText si se elige una opción predefinida
        Toast.makeText(this, "Seleccionaste: " + tipo, Toast.LENGTH_SHORT).show();
    }
}
