package com.example.proyectofinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class editar_residuo extends AppCompatActivity {

    ImageButton btnOrganico, btnInorganico, btnElectrico, btnOtro;
    EditText edtCantidad, etxOtroResiduo;
    Button btnActualizar, btnCancelar, btnEliminar;
    DatabaseHelper databaseHelper;
    int residuoId;
    String tipoResiduoSeleccionado = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_residuo);

        btnOrganico = findViewById(R.id.btnOrganico);
        btnInorganico = findViewById(R.id.btnInorganico);
        btnElectrico = findViewById(R.id.btnElectrico);
        btnOtro = findViewById(R.id.btnOtro);
        edtCantidad = findViewById(R.id.edtCantidad);
        etxOtroResiduo = findViewById(R.id.etxOtroResiduo2);
        btnActualizar = findViewById(R.id.btnActualizar);
        btnCancelar = findViewById(R.id.btnCancelar);
        btnEliminar = findViewById(R.id.btnEliminar);
        databaseHelper = new DatabaseHelper(this);

        // Obtener datos enviados desde el RecyclerView
        Intent intent = getIntent();
        if (intent != null) {
            residuoId = intent.getIntExtra("id", -1);
            tipoResiduoSeleccionado = intent.getStringExtra("tipo");
            double cantidad = intent.getDoubleExtra("cantidad", 0.0);

            edtCantidad.setText(String.valueOf(cantidad));

            // Determinar si es un tipo predefinido o "Otro"
            if (tipoResiduoSeleccionado.equals("Orgánico") || tipoResiduoSeleccionado.equals("Inorgánico") || tipoResiduoSeleccionado.equals("Eléctrico")) {
                seleccionarResiduo(tipoResiduoSeleccionado);
            } else {
                etxOtroResiduo.setVisibility(View.VISIBLE);
                etxOtroResiduo.setText(tipoResiduoSeleccionado);
                tipoResiduoSeleccionado = ""; // No asignar hasta que el usuario confirme
            }
        }

        // Asignar eventos a los botones
        btnOrganico.setOnClickListener(v -> seleccionarResiduo("Orgánico"));
        btnInorganico.setOnClickListener(v -> seleccionarResiduo("Inorgánico"));
        btnElectrico.setOnClickListener(v -> seleccionarResiduo("Eléctrico"));

        btnOtro.setOnClickListener(v -> {
            etxOtroResiduo.setVisibility(View.VISIBLE);
            etxOtroResiduo.requestFocus();
            tipoResiduoSeleccionado = "";
        });

        // Botón para actualizar el residuo
        btnActualizar.setOnClickListener(view -> {
            if (tipoResiduoSeleccionado.isEmpty()) {
                tipoResiduoSeleccionado = etxOtroResiduo.getText().toString().trim();
                if (tipoResiduoSeleccionado.isEmpty()) {
                    Toast.makeText(editar_residuo.this, "Selecciona un tipo de residuo", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            double nuevaCantidad = Double.parseDouble(edtCantidad.getText().toString());

            if (databaseHelper.actualizarResiduo(residuoId, tipoResiduoSeleccionado, nuevaCantidad) > 0) {
                Toast.makeText(this, "Residuo actualizado", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Error al actualizar", Toast.LENGTH_SHORT).show();
            }
        });

        // Botón para cancelar la edición
        btnCancelar.setOnClickListener(view -> finish());

        // Botón para eliminar residuo
        btnEliminar.setOnClickListener(view -> mostrarDialogoConfirmacion());
    }

    private void seleccionarResiduo(String tipo) {
        tipoResiduoSeleccionado = tipo;
        etxOtroResiduo.setVisibility(View.GONE); // Ocultar EditText si se elige una opción predefinida
        Toast.makeText(this, "Seleccionaste: " + tipo, Toast.LENGTH_SHORT).show();
    }

    private void mostrarDialogoConfirmacion() {
        new AlertDialog.Builder(this)
                .setTitle("Eliminar Residuo")
                .setMessage("¿Estás seguro que deseas eliminar este residuo?")
                .setPositiveButton("Sí", (dialog, which) -> {
                    if (databaseHelper.eliminarResiduo(residuoId) > 0) {
                        Toast.makeText(this, "Residuo eliminado", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(this, "Error al eliminar", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}
