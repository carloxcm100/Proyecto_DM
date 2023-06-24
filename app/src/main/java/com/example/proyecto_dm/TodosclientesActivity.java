package com.example.proyecto_dm;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class TodosclientesActivity extends AppCompatActivity {

    private Button regresar3Button;
    private TableLayout tldatosclientes;

    private DatabaseHelper databaseHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todosclientes);

        regresar3Button = findViewById(R.id.regresar3Button);
        tldatosclientes = findViewById(R.id.tldatosclientes);

        databaseHelper = new DatabaseHelper(this);

        regresar3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(TodosclientesActivity.this, Datosclientefactura.class);
                startActivity(intent);
            }
        });

        llenartabla();


    }

    private void llenartabla() {
        // Obtener una referencia a la base de datos
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        // Definir las columnas que se desean obtener
        String[] columns = {"cedularuc", "nombresconsumidor", "apellidosconsumidor", "direccionconsumidor", "telefonoconsumidor", "correoconsumidor"};
        // Realizar la consulta a la base de datos
        Cursor cursor = db.query("consumidor", columns, null, null, null, null, null);
        // Obtener una referencia al TableLayout existente en tu diseño
        TableLayout tableLayout = findViewById(R.id.tldatosclientes);
        // Limpiar el TableLayout antes de llenarlo nuevamente
        tableLayout.removeAllViews();
        // Verificar si se encontraron datos
        if (cursor.moveToFirst()) {
            do {
                // Obtener los datos de la fila actual
                String cedulac = cursor.getString(cursor.getColumnIndex("cedularuc"));
                String nombrec = cursor.getString(cursor.getColumnIndex("nombresconsumidor"));
                String apellidoc = cursor.getString(cursor.getColumnIndex("apellidosconsumidor"));
                String direccionc = cursor.getString(cursor.getColumnIndex("direccionconsumidor"));
                String telefonoc = cursor.getString(cursor.getColumnIndex("telefonoconsumidor"));
                String correoc = cursor.getString(cursor.getColumnIndex("correoconsumidor"));
                // Inflar el TableRow desde el archivo de diseño
                LayoutInflater inflater = LayoutInflater.from(this);
                TableRow tableRow2 = (TableRow) inflater.inflate(R.layout.tl_datosclientes, tableLayout, false);
                // Obtener los TextViews del TableRow inflado
                TextView tvcedulacliente = tableRow2.findViewById(R.id.tvCedulaClienterow);
                TextView tvnombrecliente = tableRow2.findViewById(R.id.tvNombreClienterow);
                TextView tvapellidocliente = tableRow2.findViewById(R.id.tvApellidoClienterow);
                TextView tvdireccioncliente = tableRow2.findViewById(R.id.tvDireccionClienterow);
                TextView tvtelefonocliente = tableRow2.findViewById(R.id.tvTelefonoClienterow);
                TextView tvcorreocliente = tableRow2.findViewById(R.id.tvCorreoClienterow);
                // Establecer los datos en los TextViews
                tvcedulacliente.setText(cedulac);
                tvnombrecliente.setText(nombrec);
                tvapellidocliente.setText(apellidoc);
                tvdireccioncliente.setText(direccionc);
                tvtelefonocliente.setText(telefonoc);
                tvcorreocliente.setText(correoc);
                // Agregar el TableRow al TableLayout
                tableLayout.addView(tableRow2);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
    }
}