package com.example.proyecto_dm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private Button cerrarSesionButton;
    private Button ayudaButton;
    private Button datoscButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        cerrarSesionButton = findViewById(R.id.cerrarSesionButton);
        ayudaButton = findViewById(R.id.ayudaButton);

        cerrarSesionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cerrarSesion();
            }
        });


        ayudaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarMensajeAyuda();
            }
        });
    }

    private void cerrarSesion() {
        // Implementa aquí la lógica para cerrar sesión
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }


    private void mostrarMensajeAyuda() {
        Toast.makeText(this, "El mesero está en camino", Toast.LENGTH_SHORT).show();
    }
}



