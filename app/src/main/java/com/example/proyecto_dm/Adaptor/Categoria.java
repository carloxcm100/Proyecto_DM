package com.example.proyecto_dm.Adaptor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_dm.Dominio.CategoriaDominio;
import com.example.proyecto_dm.R;

import java.util.ArrayList;

public class Categoria extends RecyclerView.Adapter<Categoria.ViewHolder> {
    ArrayList<CategoriaDominio>categoriaDominios;

    public Categoria(ArrayList<CategoriaDominio> categoriaDominios) {
        this.categoriaDominios = categoriaDominios;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View aumentar = LayoutInflater.from(parent.getContext()).inflate(R.layout., parent, false);
        return new ViewHolder(aumentar);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.categoriaNombre.setText(categoriaDominios.get(position).getTitulo());
        String fotoUrl = "";
        switch (position) {
            case 0:{
                fotoUrl = "cat_1";
                holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(),R.drawable.));
            }
        }

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView categoriaNombre;
        ImageView categoriaFoto;
        ConstraintLayout mainLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoriaNombre = itemView.findViewById(R.id.categoriaFoto);
            categoriaFoto = itemView.findViewById(R.id.categoriaFoto);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
