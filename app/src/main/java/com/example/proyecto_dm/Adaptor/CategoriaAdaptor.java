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


import com.bumptech.glide.Glide;
import com.example.proyecto_dm.Dominio.CategoriaDominio;
import com.example.proyecto_dm.R;

import java.util.ArrayList;



public class CategoriaAdaptor extends RecyclerView.Adapter<CategoriaAdaptor.ViewHolder> {
    ArrayList<CategoriaDominio>categoriaDominios;

    public CategoriaAdaptor(ArrayList<CategoriaDominio> categoriaDominios) {
        this.categoriaDominios = categoriaDominios;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View aumentar = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_categoria, parent, false);
        return new ViewHolder(aumentar);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.categoriaNombre.setText(categoriaDominios.get(position).getTitulo());
        String fotoUrl = "";
        switch (position) {
            case 0:{
                fotoUrl = "cat_1";
                holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(),R.drawable.cat_fondo1));
                break;
            }
            case 1:{
                fotoUrl = "cat_2";
                holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(),R.drawable.cat_fondo2));
                break;
            }
            case 2:{
                fotoUrl = "cat_3";
                holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(),R.drawable.cat_fondo3));
                break;
            }
            case 3:{
                fotoUrl = "cat_4";
                holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(),R.drawable.cat_fondo4));
                break;
            }
            case 4:{
                fotoUrl = "cat_5";
                holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(),R.drawable.cat_fondo5));
                break;
            }
        }
        int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(fotoUrl, "drawable", holder.itemView.getContext().getPackageName());
        Glide.with(holder.itemView.getContext())
                .load(drawableResourceId)
                .into(holder.categoriaFoto);
    }

    @Override
    public int getItemCount() {
        return categoriaDominios.size();
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
