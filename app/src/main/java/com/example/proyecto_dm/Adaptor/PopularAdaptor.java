package com.example.proyecto_dm.Adaptor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.proyecto_dm.Dominio.ComidaDominio;
import com.example.proyecto_dm.R;

import java.util.ArrayList;


public class PopularAdaptor extends RecyclerView.Adapter<PopularAdaptor.ViewHolder> {
    ArrayList<ComidaDominio> popularComida;

    public PopularAdaptor(ArrayList<ComidaDominio> popularComida) {
        this.popularComida = popularComida;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View aumentar = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_popular, parent, false);
        return new ViewHolder(aumentar);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int posicion) {
        holder.titulo.setText(popularComida.get(posicion).getTitulo());
        holder.cuota.setText(String.valueOf(popularComida.get(posicion).getCuota()));

        int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(popularComida.get(posicion).getFoto(), "drawable", holder.itemView.getContext().getPackageName());
        Glide.with(holder.itemView.getContext())
                .load(drawableResourceId)
                .into(holder.foto);
    }

    @Override
    public int getItemCount() {
        return popularComida.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView titulo, cuota;
        ImageView foto;
        TextView addBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.titulo);
            cuota = itemView.findViewById(R.id.cuota);
            foto = itemView.findViewById(R.id.foto);
            addBtn = itemView.findViewById(R.id.addBtn);
        }
    }
}
