package com.example.proyectofinal;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.proyectofinal.Modelo.Residuo;
import java.util.List;

public class lista_residuos extends AppCompatActivity {

    private TextView txtCerrarSesion;
    private RecyclerView recyclerView;
    private ResiduoAdapter adapter;
    private DatabaseHelper databaseHelper;
    private Button btnAgregarResiduo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_residuos);

        txtCerrarSesion = findViewById(R.id.txtCerrarSesion);

        recyclerView = findViewById(R.id.recyclerView);
        btnAgregarResiduo = findViewById(R.id.btnAgregarResiduo);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        databaseHelper = new DatabaseHelper(this);
        Log.d("DB_DEBUG", "Cantidad de residuos: " + databaseHelper.contarRegistros());
        actualizarLista();

        btnAgregarResiduo.setOnClickListener(v -> {
            startActivity(new Intent(lista_residuos.this, ecolim.class));
        });

        txtCerrarSesion.setOnClickListener(view -> {
            startActivity(new Intent(lista_residuos.this, MainActivity.class));
            finish();
        });
    }

    private void actualizarLista() {
        List<Residuo> listaResiduos = databaseHelper.obtenerResiduos();
        adapter = new ResiduoAdapter(this, listaResiduos);
        recyclerView.setAdapter(adapter);
    }
    @Override
    protected void onResume() {
        super.onResume();
        actualizarLista();
    }
}
