package com.example.proyecto_dm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FacturaActivity extends AppCompatActivity {

    private TextView faccedulaTextView;
    private TextView nombrefacTextView;
    private TextView apellidofacTextView;

    private TextView fechatextView;
    private TextView tipopagotextView;
    private TextView correofactextView;
    private TextView telefonofactTextView;
    private TextView numerocomandafactextView;
    private TableLayout tlafactura;
    private TableLayout tlfactura;



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
        /*tlafactura = findViewById(R.id.tlfactura);*/
        tlfactura = findViewById(R.id.tlfactura);
        // Obtener la fecha y hora actual
        Date fechaActual = new Date();

        btn_enviarcorreo = findViewById(R.id.btn_enviarcorreo);
        btn_imprimir = findViewById(R.id.btn_imprimir);
        btn_home = findViewById(R.id.btn_home);

        databaseHelper = new DatabaseHelper(this);

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        // Formatear la fecha y hora actual en el formato deseado
        SimpleDateFormat formatoFechaHora = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String fechaHoraFormateada = formatoFechaHora.format(fechaActual);

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
            tipopagotextView.setText(formapago);
            // Establecer la fecha y hora formateadas en el fechatextView
            fechatextView.setText(fechaHoraFormateada);
        }

        // Cierra el cursor
        cursor.close();


        // Cierra la base de datos
        db.close();

        /*llenartabla(cedula);*/
        /*llenartabla2();*/

        llenartabla3();

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

    /*void llenartabla(String cedula){
        // Obtener una referencia a la base de datos
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        // Definir las columnas que se desean obtener
        String[] columns = {"cedularuc", "nombresconsumidor", "apellidosconsumidor", "telefonoconsumidor", "correoconsumidor"};

        // Definir la condición de selección
        String selection = "cedularuc = ?";
        String[] selectionArgs = {cedula};

        // Realizar la consulta a la base de datos
        Cursor cursor = db.query("consumidor", columns, selection, selectionArgs, null, null, null);

        // Obtener las columnas de los datos
        int cedulaIndex = cursor.getColumnIndex("cedularuc");
        int nombreIndex = cursor.getColumnIndex("nombresconsumidor");
        int apellidoIndex = cursor.getColumnIndex("apellidosconsumidor");
        int telefonoIndex = cursor.getColumnIndex("telefonoconsumidor");
        int correoIndex = cursor.getColumnIndex("correoconsumidor");

        // Obtener una referencia al TableRow existente en tu diseño
        TableRow tableRow = findViewById(R.id.trfactura);

        // Limpiar el TableRow antes de llenarlo nuevamente
        tableRow.removeAllViews();

        // Verificar si se encontraron datos
        if (cursor.moveToFirst()) {
            // Obtener los datos de la fila actual
            String cedulaf = cursor.getString(cedulaIndex);
            String nombref = cursor.getString(nombreIndex);
            String apellidof = cursor.getString(apellidoIndex);
            String telefonof = cursor.getString(telefonoIndex);
            String correof = cursor.getString(correoIndex);

            // Crear TextViews para cada columna de datos
            TextView tvcodigo = new TextView(this);
            tvcodigo.setText(cedulaf);

            TextView tvnombre = new TextView(this);
            tvnombre.setText(nombref);

            TextView tvprecio = new TextView(this);
            tvprecio.setText(apellidof);

            TextView tvcantidad = new TextView(this);
            tvcantidad.setText(telefonof);

            TextView tvtotal = new TextView(this);
            tvtotal.setText(correof);

            // Agregar los TextViews al TableRow
            tableRow.addView(tvcodigo);
            tableRow.addView(tvnombre);
            tableRow.addView(tvprecio);
            tableRow.addView(tvcantidad);
            tableRow.addView(tvtotal);
        }

        // Cerrar el cursor y la conexión a la base de datos
        cursor.close();
        db.close();
    }

    void llenartabla2() {
        // Obtener una referencia a la base de datos
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        // Definir las columnas que se desean obtener
        String[] columns = {"cedularuc", "nombresconsumidor", "apellidosconsumidor", "telefonoconsumidor", "correoconsumidor"};

        // Realizar la consulta a la base de datos
        Cursor cursor = db.query("consumidor", columns, null, null, null, null, null);

        // Obtener una referencia al TableRow existente en tu diseño
        TableRow tableRow = findViewById(R.id.trfactura);

        // Limpiar el TableRow antes de llenarlo nuevamente
        tableRow.removeAllViews();

        // Verificar si se encontraron datos
        if (cursor.moveToFirst()) {
            int cedulaIndex = cursor.getColumnIndex("cedularuc");
            int nombreIndex = cursor.getColumnIndex("nombresconsumidor");
            int apellidoIndex = cursor.getColumnIndex("apellidosconsumidor");
            int telefonoIndex = cursor.getColumnIndex("telefonoconsumidor");
            int correoIndex = cursor.getColumnIndex("correoconsumidor");

            // Recorrer el cursor para obtener todos los datos
            do {
                // Obtener los datos de la fila actual
                String cedulaf = cursor.getString(cedulaIndex);
                String nombref = cursor.getString(nombreIndex);
                String apellidof = cursor.getString(apellidoIndex);
                String telefonof = cursor.getString(telefonoIndex);
                String correof = cursor.getString(correoIndex);

                // Crear TextViews para cada columna de datos
                TextView tvcodigo = new TextView(this);
                tvcodigo.setText(cedulaf);

                TextView tvnombre = new TextView(this);
                tvnombre.setText(nombref);

                TextView tvprecio = new TextView(this);
                tvprecio.setText(apellidof);

                TextView tvcantidad = new TextView(this);
                tvcantidad.setText(telefonof);

                TextView tvtotal = new TextView(this);
                tvtotal.setText(correof);

                // Agregar los TextViews al TableRow
                tableRow.addView(tvcodigo);
                tableRow.addView(tvnombre);
                tableRow.addView(tvprecio);
                tableRow.addView(tvcantidad);
                tableRow.addView(tvtotal);

            } while (cursor.moveToNext());
        }

        // Cerrar el cursor y la conexión a la base de datos
        cursor.close();
        db.close();
    }*/
    private void llenartabla3() {
        // Obtener una referencia a la base de datos
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        // Definir las columnas que se desean obtener
        String[] columns = {"cedularuc", "nombresconsumidor", "apellidosconsumidor", "telefonoconsumidor", "correoconsumidor"};

        // Realizar la consulta a la base de datos
        Cursor cursor = db.query("consumidor", columns, null, null, null, null, null);

        // Obtener una referencia al TableLayout existente en tu diseño
        TableLayout tableLayout = findViewById(R.id.tlfactura);

        // Limpiar el TableLayout antes de llenarlo nuevamente
        tableLayout.removeAllViews();

        // Verificar si se encontraron datos
        if (cursor.moveToFirst()) {
            do {
                // Obtener los datos de la fila actual
                String cedulaf = cursor.getString(cursor.getColumnIndex("cedularuc"));
                String nombref = cursor.getString(cursor.getColumnIndex("nombresconsumidor"));
                String apellidof = cursor.getString(cursor.getColumnIndex("apellidosconsumidor"));
                String telefonof = cursor.getString(cursor.getColumnIndex("telefonoconsumidor"));
                String correof = cursor.getString(cursor.getColumnIndex("correoconsumidor"));

                // Inflar el TableRow desde el archivo de diseño
                LayoutInflater inflater = LayoutInflater.from(this);
                TableRow tableRow = (TableRow) inflater.inflate(R.layout.tl_itemsfactura, tableLayout, false);

                // Obtener los TextViews del TableRow inflado
                TextView tvcodigo = tableRow.findViewById(R.id.tvCodigorow);
                TextView tvnombre = tableRow.findViewById(R.id.tvNombrerow);
                TextView tvprecio = tableRow.findViewById(R.id.tvPreciorow);
                TextView tvcantidad = tableRow.findViewById(R.id.tvCantidadrow);
                TextView tvtotal = tableRow.findViewById(R.id.tvTotalrow);

                // Establecer los datos en los TextViews
                tvcodigo.setText(cedulaf);
                tvnombre.setText(nombref);
                tvprecio.setText(apellidof);
                tvcantidad.setText(telefonof);
                tvtotal.setText(correof);

                // Agregar el TableRow al TableLayout
                tableLayout.addView(tableRow);

            } while (cursor.moveToNext());
        }

        // Cerrar el cursor y la conexión a la base de datos
        cursor.close();
        db.close();
    }
}