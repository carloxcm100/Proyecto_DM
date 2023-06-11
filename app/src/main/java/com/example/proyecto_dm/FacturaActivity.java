package com.example.proyecto_dm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class FacturaActivity extends AppCompatActivity {

    private TextView faccedulaTextView;
    //private TextView tipodepagoTextView;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factura);

        faccedulaTextView = findViewById(R.id.faccedulaTextView);
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        // Obtener el número de cédula guardado en SharedPreferences
        String cedula = sharedPreferences.getString("CEDULA", "");
        String formapago = sharedPreferences.getString("FORMAPAGO", "");
        // Mostrar el número de cédula en el TextView
        faccedulaTextView.setText(cedula);
    }
}