package com.example.proyecto_dm;

import android.app.Activity;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_dm.Adaptor.CategoriaAdaptor;
import com.example.proyecto_dm.Adaptor.PopularAdaptor;
import com.example.proyecto_dm.Dominio.CategoriaDominio;
import com.example.proyecto_dm.Dominio.ComidaDominio;

import java.util.ArrayList;

public class Ordenar_menu extends Activity {
    private RecyclerView.Adapter adaptador, adaptador2;
    private RecyclerView recyclerViewCategoriaLista, recyclerViewPupularLista;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordenar_menu);

        recyclerViewCatego();
        recyclerViewPupular();
    }

    private void recyclerViewCatego() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCategoriaLista = findViewById(R.id.recyclerView);
        recyclerViewCategoriaLista.setLayoutManager(linearLayoutManager);

        ArrayList<CategoriaDominio> categoria = new ArrayList<>();
        categoria.add(new CategoriaDominio("Pizza", "cat_1"));
        categoria.add(new CategoriaDominio("Hamburguesa", "cat_2"));
        categoria.add(new CategoriaDominio("Hotdog", "cat_3"));
        categoria.add(new CategoriaDominio("Bebida", "cat_4"));
        categoria.add(new CategoriaDominio("Dona", "cat_5"));

        adaptador = new CategoriaAdaptor(categoria);
        recyclerViewCategoriaLista.setAdapter(adaptador);
    }

    private void recyclerViewPupular() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewPupularLista = findViewById(R.id.recyclerView2);
        recyclerViewPupularLista.setLayoutManager(linearLayoutManager);
        ArrayList<ComidaDominio> comidaList = new ArrayList<>();
        comidaList.add(new ComidaDominio("Pizza de pepperoni", "pizza1", "Rebanadas de pepperoni, Queso mozzarella, Oregano fresco, Pimienta negra, Salsa de pizza", 9.50));
        comidaList.add(new ComidaDominio("Hamburguesa de queso", "burger", "Queso gouda, Salsa especial, Lechuga, Tomate", 8.50));
        comidaList.add(new ComidaDominio("Pizza vegetariana", "pizza2", "Aceite de oliva, Aceitunas, Tomates cherrys, Oregano fresco, Albahaca", 8.50));

        adaptador2 = new PopularAdaptor(comidaList);
        recyclerViewPupularLista.setAdapter(adaptador2);
    }
}
