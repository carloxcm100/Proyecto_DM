package com.example.proyecto_dm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Actividad que representa la pantalla de inicio de sesión.
 */
public class LoginActivity extends AppCompatActivity {

    private Button cerrarSesionButton;
    private Button ayudaButton;
    private Button ordenarButton;
    private Button datosfacturaButton;
    private TextView nombresApellidosTextView;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        cerrarSesionButton = findViewById(R.id.cerrarSesionButton);
        ayudaButton = findViewById(R.id.ayudaButton);
        ordenarButton = findViewById(R.id.ordenarButton);
        datosfacturaButton = findViewById(R.id.datosfacturaButton);
        nombresApellidosTextView = findViewById(R.id.nombresApellidosTextView);

        // Obtener los nombres y apellidos del usuario desde SharedPreferences
        sharedPreferences = getSharedPreferences("my_shared_prefs", MODE_PRIVATE);
        String nombres = sharedPreferences.getString("nombres", "");
        String apellidos = sharedPreferences.getString("apellidos", "");

        // Establecer los nombres y apellidos en el TextView
        nombresApellidosTextView.setText("El Mesero "+ nombres + " " + apellidos+" les da la Bienvenida");

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

        ordenarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, Ordenar_menu.class);
                startActivity(intent);
                finish();
            }
        });
        datosfacturaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, Datosclientefactura.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Cierra la sesión del usuario y redirige a la actividad de inicio de sesión.
     */
    private void cerrarSesion() {
        // Implementa aquí la lógica para cerrar sesión
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    /**
     * Muestra un mensaje de ayuda al usuario.
     */
    private void mostrarMensajeAyuda() {
        Toast.makeText(this, "El mesero está en camino", Toast.LENGTH_SHORT).show();
    }
}





