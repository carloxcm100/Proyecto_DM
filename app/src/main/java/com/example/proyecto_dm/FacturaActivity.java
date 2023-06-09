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

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class FacturaActivity extends AppCompatActivity {

    private TextView faccedulaTextView;
    private TextView nombrefacTextView;
    private TextView apellidofacTextView;
    private TextView fechatextView;
    private TextView tipopagotextView;
    private TextView correofactextView;
    private TextView telefonofactTextView;
    private TextView numerofacturatextView;
    private TextView meserotextView;
    private TextView totalapagartextView;
    private TextView numerocomandafactextView;

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
        meserotextView = findViewById(R.id.meserotextView);
        numerofacturatextView = findViewById(R.id.numerofacturatextView);
        totalapagartextView = findViewById(R.id.totalapagartextView);
        //numerocomandafacTextView = findViewById(R.id.numerocomandafactextView);
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
        String comanda = sharedPreferences.getString("COMANDA","");
        sharedPreferences = getSharedPreferences("my_shared_prefs", MODE_PRIVATE);
        String usuario = sharedPreferences.getString("usuario", "");
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String numerofactura = generarnumerofacturarandom();


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
            double sumaTotal = sumarTotales(comanda);
            // Actualiza los TextView con los datos obtenidos
            faccedulaTextView.setText(cedulax);
            nombrefacTextView.setText(nombre);
            apellidofacTextView.setText(apellido);
            telefonofactTextView.setText(telefono);
            correofactextView.setText(correo);
            tipopagotextView.setText(formapago);
            meserotextView.setText(usuario);
            numerofacturatextView.setText(numerofactura);
            totalapagartextView.setText(String.valueOf(sumaTotal));
            // Establecer la fecha y hora formateadas en el fechatextView
            fechatextView.setText(fechaHoraFormateada);
        }
        // Cierra el cursor
        cursor.close();
        // Cierra la base de datos
        db.close();

        llenartabla3(comanda);

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

    private double sumarTotales(String codigo) {
        // Obtener una referencia a la base de datos
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        // Definir la columna que se desea sumar
        String column = "total";
        // Definir la cláusula WHERE para filtrar por código
        String selection = "codigo = ?";
        String[] selectionArgs = {codigo};
        // Realizar la consulta a la base de datos con la cláusula WHERE
        Cursor cursor = db.query("ordenespedidos", new String[]{column}, selection, selectionArgs, null, null, null);
        // Variable para almacenar la suma de los totales
        double suma = 0;
        // Verificar si se encontraron datos
        if (cursor.moveToFirst()) {
            do {
                // Obtener el valor de la columna "total" como cadena
                String totalStr = cursor.getString(cursor.getColumnIndex(column));
                // Convertir el valor de cadena a double y sumarlo a la variable de suma
                suma += Double.parseDouble(totalStr);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        String sumaconformato = decimalFormat.format(suma);

        // Convertir la suma formateada a double y retornarla
        return Double.parseDouble(sumaconformato);
    }

    private String generarnumerofacturarandom() {
        Random random = new Random();
        // Generar los tres segmentos del número
        int segmento1 = random.nextInt(1000);
        int segmento2 = random.nextInt(1000);
        int segmento3 = random.nextInt(1000000);
        // Formatear los segmentos en el formato deseado
        String numeroRandom = String.format("%03d-%03d-%05d", segmento1, segmento2, segmento3);
        return numeroRandom;
    }

    private void mensaje2() {
        Toast.makeText(this, "La factura se esta imprimiendo", Toast.LENGTH_SHORT).show();
    }

    private void mensaje1() {
        String correofac  = correofactextView.getText().toString();;
        Toast.makeText(this, "Se envio la factura al correo "+correofac, Toast.LENGTH_SHORT).show();
    }

    private void llenartabla3(String codigo) {
        // Obtener una referencia a la base de datos
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        // Definir las columnas que se desean obtener
        String[] columns = {"codigo", "nombre", "precio", "cantidad", "total"};
        // Definir la cláusula WHERE para filtrar por código
        String selection = "codigo = ?";
        String[] selectionArgs = {codigo};
        // Realizar la consulta a la base de datos
        Cursor cursor = db.query("ordenespedidos", columns, selection, selectionArgs, null, null, null);
        // Obtener una referencia al TableLayout existente en tu diseño
        TableLayout tableLayout = findViewById(R.id.tlfactura);
        // Limpiar el TableLayout antes de llenarlo nuevamente
        tableLayout.removeAllViews();
        // Verificar si se encontraron datos
        if (cursor.moveToFirst()) {
            do {
                // Obtener los datos de la fila actual
                String cedulaf = cursor.getString(cursor.getColumnIndex("codigo"));
                String nombref = cursor.getString(cursor.getColumnIndex("nombre"));
                String apellidof = cursor.getString(cursor.getColumnIndex("precio"));
                String telefonof = cursor.getString(cursor.getColumnIndex("cantidad"));
                String correof = cursor.getString(cursor.getColumnIndex("total"));
                // Inflar el TableRow desde el archivo de diseño
                LayoutInflater inflater = LayoutInflater.from(this);
                TableRow tableRow = (TableRow) inflater.inflate(R.layout.tl_itemsfactura, tableLayout, false);
                // Obtener los TextViews del TableRow inflado
                TextView tvcodigo = tableRow.findViewById(R.id.tvcodigorow);
                TextView tvnombre = tableRow.findViewById(R.id.tvnombrerow);
                TextView tvprecio = tableRow.findViewById(R.id.tvpreciorow);
                TextView tvcantidad = tableRow.findViewById(R.id.tvcantidadrow);
                TextView tvtotal = tableRow.findViewById(R.id.tvtotalrow);
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
        cursor.close();
        db.close();
    }
}