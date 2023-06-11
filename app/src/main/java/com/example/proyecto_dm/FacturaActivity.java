package com.example.proyecto_dm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FacturaActivity extends AppCompatActivity {

    private TextView faccedulaTextView;
    private TextView nombrefacTextView;
    private TextView apellidofacTextView;
    private TextView fechatextView;
    //private TextView tipodepagoTextView;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factura);

        faccedulaTextView = findViewById(R.id.faccedulaTextView);
        nombrefacTextView = findViewById(R.id.nombrefacTextView);
        apellidofacTextView = findViewById(R.id.apellidofacTextView);
        fechatextView = findViewById(R.id.fechatextView);
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        // Obtener el número de cédula guardado en SharedPreferences
        String cedula = sharedPreferences.getString("CEDULA", "");
        String nombrefac = sharedPreferences.getString("NOMBRE", "");
        String apellidofac = sharedPreferences.getString("APELLIDO", "");
        String formapago = sharedPreferences.getString("FORMAPAGO", "");
        // Mostrar el número de cédula en el TextView
        faccedulaTextView.setText(cedula);
        nombrefacTextView.setText(nombrefac);
        apellidofacTextView.setText(apellidofac);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
        String fechaActual = dateFormat.format(new Date());
        fechatextView.setText(fechaActual);
    }
}