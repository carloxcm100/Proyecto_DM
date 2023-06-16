package com.example.proyecto_dm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class FacturaActivity extends AppCompatActivity {

    private TextView faccedulaTextView;
    private TextView nombrefacTextView;
    private TextView apellidofacTextView;

    private TextView fechatextView;
    private TextView tipopagotextView;
    private TextView correofactextView;
    private TextView telefonofactTextView;
    private TextView numerocomandafactextView;

    private Button btn_enviarcorreo;
    private Button btn_imprimir;
    private Button btn_home;
    private DatabaseHelper databaseHelper;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factura);

        faccedulaTextView = findViewById(R.id.faccedulaTextView);
        nombrefacTextView = findViewById(R.id.nombrefacTextView);
        apellidofacTextView = findViewById(R.id.apellidofacTextView);
        fechatextView = findViewById(R.id.fechatextView);
        tipopagotextView = findViewById(R.id.tipopagotextView);
        correofactextView = findViewById(R.id.correofacEditText);
        telefonofactTextView = findViewById(R.id.telefonofactextView);
        //numerocomandafacTextView = findViewById(R.id.numerocomandafactextView);

        btn_enviarcorreo = findViewById(R.id.btn_enviarcorreo);
        btn_imprimir = findViewById(R.id.btn_imprimir);
        btn_home = findViewById(R.id.btn_home);

        databaseHelper = new DatabaseHelper(this);

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        // Obtener el número de cédula guardado en SharedPreferences
        String cedula = sharedPreferences.getString("CEDULA", "");
        String formapago = sharedPreferences.getString("FORMAPAGO", "");

        SQLiteDatabase db = databaseHelper.getReadableDatabase();

// Especifica las columnas que deseas recuperar
        String[] columns = {"cedularuc", "nombresconsumidor", "apellidosconsumidor", "telefonoconsumidor", "correoconsumidor"};

// Especifica el criterio de selección
        String selection = "cedularuc = ?";
        String[] selectionArgs = {cedula};

// Realiza la consulta
        Cursor cursor = db.query("consumidor", columns, selection, selectionArgs, null, null, null);

// Obtén los índices de las columnas
        int cedulaIndex = cursor.getColumnIndex("cedularuc");
        int nombreIndex = cursor.getColumnIndex("nombresconsumidor");
        int apellidoIndex = cursor.getColumnIndex("apellidosconsumidor");
        int telefonoIndex = cursor.getColumnIndex("telefonoconsumidor");
        int correoIndex = cursor.getColumnIndex("correoconsumidor");

// Mueve el cursor al primer registro
        if (cursor.moveToFirst()) {
            // Obtén los datos de las columnas para el primer registro
            String cedulax = cursor.getString(cedulaIndex);
            String nombre = cursor.getString(nombreIndex);
            String apellido = cursor.getString(apellidoIndex);
            String telefono = cursor.getString(telefonoIndex);
            String correo = cursor.getString(correoIndex);

            // Actualiza los TextView con los datos obtenidos
            faccedulaTextView.setText(cedulax);
            nombrefacTextView.setText(nombre);
            apellidofacTextView.setText(apellido);
            telefonofactTextView.setText(telefono);
            correofactextView.setText(correo);
        }

// Cierra el cursor y la base de datos
        cursor.close();
        db.close();

        tipopagotextView.setText(formapago);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
        String fechaActual = dateFormat.format(new Date());
        fechatextView.setText(fechaActual);


        btn_enviarcorreo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mensaje1();
            }
        });

        btn_imprimir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mensaje2();
            }
        });

        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FacturaActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void mensaje2() {
        Toast.makeText(this, "La factura se esta imprimiendo", Toast.LENGTH_SHORT).show();
    }

    private void mensaje1() {
        String correofac  = correofactextView.getText().toString();;
        Toast.makeText(this, "Se envio la factura al correo "+correofac, Toast.LENGTH_SHORT).show();
    }
}