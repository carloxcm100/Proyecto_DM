package com.example.proyecto_dm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.proyecto_dm.Dominio.CategoriaDominio;

import java.util.ArrayList;

public class Ordenar_menu extends AppCompatActivity {
    private RecyclerView.Adapter adaptar;
    private RecyclerView reciclarVistaCategoriaLista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordenar_menu);

        reciclarVistaCategoria();
    }

    private void reciclarVistaCategoria() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        reciclarVistaCategoriaLista = findViewById(R.id.recyclerView);
        reciclarVistaCategoriaLista.setLayoutManager(linearLayoutManager);

        ArrayList<CategoriaDominio> categoria = new ArrayList<>();
        categoria.add(new CategoriaDominio("Pizza", "cat_1"));
        categoria.add(new CategoriaDominio("Hamburguesa", "cat_2"));
        categoria.add(new CategoriaDominio("Hotdog", "cat_3"));
        categoria.add(new CategoriaDominio("Donas", "cat_4"));
    }
}