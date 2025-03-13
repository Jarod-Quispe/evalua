package com.example.proyectofinal;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.example.proyectofinal.Modelo.Residuo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ResiduoAdapter extends RecyclerView.Adapter<ResiduoAdapter.ViewHolder> {
    private Context context;
    private List<Residuo> listaResiduos;
    public ResiduoAdapter(Context context, List<Residuo> listaResiduos) {
        this.context = context;
        this.listaResiduos = listaResiduos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_residuo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Residuo residuo = listaResiduos.get(position);
        holder.txtTipo.setText("Tipo: " + "Residuo " + residuo.getTipo());
        holder.txtCantidad.setText("Cantidad: " + residuo.getPeso() + " kg");

        // Click corto para editar
        holder.itemView.setOnClickListener(view -> {
            try {
                Intent intent = new Intent(context, editar_residuo.class);
                intent.putExtra("id", residuo.getId());
                intent.putExtra("tipo", residuo.getTipo());
                intent.putExtra("cantidad", residuo.getPeso());
                context.startActivity(intent);
            }catch (Exception e) {
                Log.e("ERROR_INTENT", "Error al abrir EditarProductoActivity", e);
                Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        // Click largo para eliminar
        holder.itemView.setOnLongClickListener(view -> {
            new AlertDialog.Builder(view.getContext())
                    .setTitle("Eliminar Residuo")
                    .setMessage("¿Deseas eliminar este residuo?")
                    .setPositiveButton("Sí", (dialog, which) -> eliminarResiduo(position))
                    .setNegativeButton("No", null)
                    .show();
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return listaResiduos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTipo, txtCantidad;

        public ViewHolder(View itemView) {
            super(itemView);
            txtTipo = itemView.findViewById(R.id.txtTipo);
            txtCantidad = itemView.findViewById(R.id.txtCantidad);
        }
    }

    public void eliminarResiduo(int position) {
        Residuo residuo = listaResiduos.get(position);
        DatabaseHelper db = new DatabaseHelper(context);
        if (db.eliminarResiduo(residuo.getId()) > 0) {
            listaResiduos.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, listaResiduos.size());
            Toast.makeText(context, "Residuo eliminado", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Error al eliminar", Toast.LENGTH_SHORT).show();
        }
    }
}