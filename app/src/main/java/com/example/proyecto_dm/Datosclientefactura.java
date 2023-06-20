package com.example.proyecto_dm;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Datosclientefactura extends AppCompatActivity {

    private EditText nombresconsumidorEditText;
    private EditText apellidosconsumidorEditText;
    private EditText direccionconsumidorEditText;
    private EditText cedularucconsumidorEditText;
    private EditText correoconsumidorEditText;
    private EditText telefonoconsumidorEditText;
    private EditText formadepagoEditText;
    private EditText numerocomandaEditText;
    private Button generarButton;
    private Button regresar2Button;
    private Button buscarButton;
    private Button agregarButton;

    private Button limpiarcamposButton;

    private DatabaseHelper databaseHelper;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datosclientefactura);

        nombresconsumidorEditText = findViewById(R.id.nombresconsumidorEditText);
        apellidosconsumidorEditText = findViewById(R.id.apellidosconsumidorEditText);
        telefonoconsumidorEditText = findViewById(R.id.telefonoconsumidorEditText);
        direccionconsumidorEditText = findViewById(R.id.direccionconsumidorEditText);
        correoconsumidorEditText = findViewById(R.id.correoconsumidorEditText);
        cedularucconsumidorEditText = findViewById(R.id.cedularucconsumidorEditText);
        formadepagoEditText = findViewById(R.id.formadepagoEditText);
        numerocomandaEditText = findViewById(R.id.numerocomandaEditText);
        generarButton = findViewById(R.id.generarFacturaButton);
        regresar2Button = findViewById(R.id.regresar2Button);
        buscarButton = findViewById(R.id.buscarButton);
        agregarButton = findViewById(R.id.agregarButton);
        limpiarcamposButton = findViewById(R.id.btn_limpiarcampos);

        Toolbar toolbar = findViewById(R.id.toolbarfac);
        setSupportActionBar(toolbar);

        databaseHelper = new DatabaseHelper(this);

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);



        agregarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cedula = cedularucconsumidorEditText.getText().toString();
                boolean cedulaexiste = verificarcedulaexiste(cedula);

                if (cedulaexiste) {
                    mensaje2();
                } else {
                    String cedularucconsumidor = cedularucconsumidorEditText.getText().toString();
                    String nombresconsumidor = nombresconsumidorEditText.getText().toString();
                    String apellidosconsumidor = apellidosconsumidorEditText.getText().toString();
                    String direccionconsumidor = direccionconsumidorEditText.getText().toString();
                    String telefonoconsumidor = telefonoconsumidorEditText.getText().toString();
                    String correoconsumidor = correoconsumidorEditText.getText().toString();
                    if (validarCampos(cedularucconsumidor, nombresconsumidor, apellidosconsumidor, direccionconsumidor, telefonoconsumidor, correoconsumidor)) {
                        guardarDatosConsumidor(cedularucconsumidor, nombresconsumidor, apellidosconsumidor, direccionconsumidor, telefonoconsumidor, correoconsumidor);
                    }
                }
            }
        });

        regresar2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Datosclientefactura.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        buscarButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("Range")
            @Override
            public void onClick(View v) {
                String cedularucconsumidor = cedularucconsumidorEditText.getText().toString();
                final SQLiteDatabase db = databaseHelper.getReadableDatabase();
                if (db != null) {
                    EditText cedularucconsumidortemp = findViewById(R.id.cedularucconsumidorEditText);
                    EditText nombresconsumidortemp = findViewById(R.id.nombresconsumidorEditText);
                    EditText apellidosconsumidortemp = findViewById(R.id.apellidosconsumidorEditText);
                    EditText telefonoconsumidortemp = findViewById(R.id.telefonoconsumidorEditText);
                    EditText direccionconsumidortemp = findViewById(R.id.direccionconsumidorEditText);
                    EditText correoconsumidortemp = findViewById(R.id.correoconsumidorEditText);

                    if (validarcedula(cedularucconsumidor)) {
                        Cursor c = db.rawQuery("SELECT cedularuc, nombresconsumidor, apellidosconsumidor, direccionconsumidor, telefonoconsumidor, correoconsumidor FROM consumidor WHERE cedularuc = '" + cedularucconsumidor + "'", null);
                        if (c != null && c.moveToFirst()) {
                            cedularucconsumidortemp.setText(c.getString(c.getColumnIndex("cedularuc")).toString());
                            nombresconsumidortemp.setText(c.getString(c.getColumnIndex("nombresconsumidor")).toString());
                            apellidosconsumidortemp.setText(c.getString(c.getColumnIndex("apellidosconsumidor")).toString());
                            direccionconsumidortemp.setText(c.getString(c.getColumnIndex("direccionconsumidor")).toString());
                            telefonoconsumidortemp.setText(c.getString(c.getColumnIndex("telefonoconsumidor")).toString());
                            correoconsumidortemp.setText(c.getString(c.getColumnIndex("correoconsumidor")).toString());
                        }else{
                            mensaje7();
                        }

                        if (c != null) {
                            c.close();
                        }
                        db.close();
                    }
                }
            }
        });

        limpiarcamposButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limpiarcampos();
            }
        });

        generarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cedularucconsumidor = cedularucconsumidorEditText.getText().toString();
                //String nombresconsumidor = nombresconsumidorEditText.getText().toString();
                //String apellidosconsumidor = apellidosconsumidorEditText.getText().toString();
                String formadepago = formadepagoEditText.getText().toString();
                String numerocomanda = numerocomandaEditText.getText().toString();

                //if (validarCamposfac(cedularucconsumidor, nombresconsumidor, apellidosconsumidor, formadepago, numerocomanda)) {
                if (validarCamposfac(cedularucconsumidor, formadepago, numerocomanda)) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("CEDULA", cedularucconsumidor);
                    //editor.putString("NOMBRE",nombresconsumidor);
                    //editor.putString("APELLIDO",apellidosconsumidor);
                    editor.putString("FORMAPAGO", formadepago);
                    editor.putString("COMANDA", numerocomanda);

                    editor.apply();
                    Intent intent = new Intent(Datosclientefactura.this, FacturaActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void limpiarcampos() {
        EditText cedularucconsumidortemp = findViewById(R.id.cedularucconsumidorEditText);
        EditText nombresconsumidortemp = findViewById(R.id.nombresconsumidorEditText);
        EditText apellidosconsumidortemp = findViewById(R.id.apellidosconsumidorEditText);
        EditText telefonoconsumidortemp = findViewById(R.id.telefonoconsumidorEditText);
        EditText direccionconsumidortemp = findViewById(R.id.direccionconsumidorEditText);
        EditText correoconsumidortemp = findViewById(R.id.correoconsumidorEditText);
        EditText formapagotemp = findViewById(R.id.formadepagoEditText);
        EditText numerocomandatemp = findViewById(R.id.numerocomandaEditText);

        cedularucconsumidortemp.setText("");
        nombresconsumidortemp.setText("");
        apellidosconsumidortemp.setText("");
        direccionconsumidortemp.setText("");
        telefonoconsumidortemp.setText("");
        correoconsumidortemp.setText("");
        formapagotemp.setText("");
        numerocomandatemp.setText("");
    }

    private void mensaje7() {
        Toast.makeText(this, "La Cedula no se encuentra registrada en la base de datos procesada a resgitrar", Toast.LENGTH_SHORT).show();
    }

    private void mensaje5() {
        Toast.makeText(this, "La base de datos esta vacia", Toast.LENGTH_SHORT).show();
    }

    //private boolean validarCamposfac(String cedularucconsumidor, String nombresconsumidor, String apellidosconsumidor, String formadepago, String numerocomanda) {
    private boolean validarCamposfac(String cedularucconsumidor,  String formadepago, String numerocomanda) {
        if (TextUtils.isEmpty(cedularucconsumidor) || !cedularucconsumidor.matches("[0-9]+") || cedularucconsumidor.length() <= 9 || cedularucconsumidor.length() > 15) {
            Toast.makeText(this, "Ingrese una cédula o R.U.C. válido (solo números, entre 10 y 15 caracteres)", Toast.LENGTH_SHORT).show();
            return false;
        }

       /* if (TextUtils.isEmpty(nombresconsumidor) || !nombresconsumidor.matches("[a-zA-Z ]+") || nombresconsumidor.length() > 30) {
            Toast.makeText(this, "Ingrese un nombre válido (solo letras y espacios, máximo 30 caracteres)", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(apellidosconsumidor) || !apellidosconsumidor.matches("[a-zA-Z ]+") || apellidosconsumidor.length() > 30) {
            Toast.makeText(this, "Ingrese un apellido válido (solo letras y espacios, máximo 30 caracteres)", Toast.LENGTH_SHORT).show();
            return false;
        }*/

        if (TextUtils.isEmpty(formadepago) || !formadepago.matches("[a-zA-Z ]+") || formadepago.length() > 30) {
            Toast.makeText(this, "Ingrese una forma de pago válida (solo letras y espacios, máximo 30 caracteres)", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(numerocomanda) || !numerocomanda.matches("[0-9]+") || numerocomanda.length() <= 0 || numerocomanda.length() > 11) {
            Toast.makeText(this, "Ingrese un número de comanda válido (solo números, máximo 10 caracteres)", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    private void mensaje4() {
        Toast.makeText(this, "La cédula no está registrada no se puede eliminar de la base de datos", Toast.LENGTH_SHORT).show();
    }

    private void mensaje3() {
        Toast.makeText(this, "La cédula no está registrada no se puede actualizar los datos", Toast.LENGTH_SHORT).show();
    }

    private void mensaje2() {
        Toast.makeText(this, "La cédula ya está registrada no se puede volver a registrar", Toast.LENGTH_SHORT).show();
    }

    private boolean verificarcedulaexiste(String cedula) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String[] columns = {"cedularuc"};
        String selection = "cedularuc= ?";
        String[] selectionArgs = {cedula};

        Cursor cursor = db.query("consumidor", columns, selection, selectionArgs, null, null, null);

        boolean cedulaExiste = false;

        if (cursor != null && cursor.moveToFirst()) {
            cedulaExiste = true;
        }

        if (cursor != null) {
            cursor.close();
        }

        db.close();

        return cedulaExiste;
    }

    private boolean validarCampos(String cedularucconsumidor, String nombresconsumidor, String apellidosconsumidor, String direccionconsumidor, String telefonoconsumidor, String correoconsumidor) {
        if (TextUtils.isEmpty(cedularucconsumidor) || !cedularucconsumidor.matches("[0-9]+") || cedularucconsumidor.length() <= 9 || cedularucconsumidor.length() > 15) {
            Toast.makeText(this, "Ingrese una cédula o R.U.C. válido (solo números, entre 10 y 15 caracteres)", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(nombresconsumidor) || !nombresconsumidor.matches("[a-zA-Z ]+") || nombresconsumidor.length() > 30) {
            Toast.makeText(this, "Ingrese un nombre válido (solo letras y espacios, máximo 30 caracteres)", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(apellidosconsumidor) || !apellidosconsumidor.matches("[a-zA-Z ]+") || apellidosconsumidor.length() > 30) {
            Toast.makeText(this, "Ingrese un apellido válido (solo letras y espacios, máximo 30 caracteres)", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(direccionconsumidor) || !direccionconsumidor.matches("[a-zA-Z0-9 ]+") || direccionconsumidor.length() > 40) {
            Toast.makeText(this, "Ingrese una dirección válida (solo letras, espacios y números, máximo 40 caracteres)", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(telefonoconsumidor) || !telefonoconsumidor.matches("[0-9]+") || telefonoconsumidor.length() <= 9 || telefonoconsumidor.length() > 11) {
            Toast.makeText(this, "Ingrese un número telefónico válido (solo números, máximo 10 caracteres)", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(correoconsumidor) || !android.util.Patterns.EMAIL_ADDRESS.matcher(correoconsumidor).matches()) {
            Toast.makeText(this, "Ingrese un correo electrónico válido", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void guardarDatosConsumidor(String cedula, String nombresconsumidor, String apellidosconsumidor, String direccionconsumidor, String telefonoconsumidor, String correoconsumidor) {
        final SQLiteDatabase db = databaseHelper.getWritableDatabase();

        ContentValues values3 = new ContentValues();
        values3.put(DatabaseHelper.COLUMN_CEDULARUCCONSUMIDOR, cedula);
        values3.put(DatabaseHelper.COLUMN_NOMBRESCONSUMIDOR, nombresconsumidor);
        values3.put(DatabaseHelper.COLUMN_APELLIDOSCONSUMIDOR, apellidosconsumidor);
        values3.put(DatabaseHelper.COLUMN_DIRECCIONCONSUMIDOR, direccionconsumidor);
        values3.put(DatabaseHelper.COLUMN_TELEFONOCONSUMIDOR, telefonoconsumidor);
        values3.put(DatabaseHelper.COLUMN_CORREOCONSUMIDOR, correoconsumidor);

        long resultado = db.insert(DatabaseHelper.TABLE_NAME3, null, values3);

        if (resultado != -1) {
            Toast.makeText(this, "Los datos del Cliente se guardaron exitosamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error al Guardar los datos", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }

    private boolean validarcedula(String cedularucconsumidor){
        if (TextUtils.isEmpty(cedularucconsumidor) || !cedularucconsumidor.matches("[0-9]+") || cedularucconsumidor.length() <= 9 || cedularucconsumidor.length() > 15) {
            Toast.makeText(this, "Ingrese una cédula o R.U.C. válido (solo números, entre 10 y 15 caracteres)", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void actualizarDatosConsumidor(String cedularucconsumidor, String nombresconsumidor, String apellidosconsumidor, String direccionconsumidor, String telefonoconsumidor, String correoconsumidor) {
        final SQLiteDatabase db = databaseHelper.getWritableDatabase();
        String cedulacon = cedularucconsumidor;

        ContentValues values3 = new ContentValues();
        values3.put(DatabaseHelper.COLUMN_CEDULARUCCONSUMIDOR, cedularucconsumidor);
        values3.put(DatabaseHelper.COLUMN_NOMBRESCONSUMIDOR, nombresconsumidor);
        values3.put(DatabaseHelper.COLUMN_APELLIDOSCONSUMIDOR, apellidosconsumidor);
        values3.put(DatabaseHelper.COLUMN_DIRECCIONCONSUMIDOR, direccionconsumidor);
        values3.put(DatabaseHelper.COLUMN_TELEFONOCONSUMIDOR, telefonoconsumidor);
        values3.put(DatabaseHelper.COLUMN_CORREOCONSUMIDOR, correoconsumidor);

        long resultado = db.update(DatabaseHelper.TABLE_NAME3, values3, DatabaseHelper.COLUMN_CEDULARUCCONSUMIDOR + " = ?", new String[]{cedulacon});

        if (resultado != -1) {
            mensaje1();
        } else {
            mensaje6();
        }
        db.close();
    }

    private void mensaje6() {
        Toast.makeText(this, "Error al guardar los datos", Toast.LENGTH_SHORT).show();
    }

    private void mensaje1() {
        Toast.makeText(this, "Los datos del Cliente se actualizaron exitosamente", Toast.LENGTH_SHORT).show();
    }

    private void eliminarPorCedula(String cedula) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        String whereClause = "cedularuc = ?";
        String[] whereArgs = {cedula};
        int resultado = db.delete("consumidor", whereClause, whereArgs);

        Toast.makeText(this, "Registro eliminado exitosamente", Toast.LENGTH_SHORT).show();

        db.close();

        if (resultado > 0) {
            Toast.makeText(this, "Registro eliminado exitosamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Ocurrio un error al  eliminar", Toast.LENGTH_SHORT).show();
        }
    }

   /* public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater barra = getMenuInflater();
        barra.inflate(R.menu.menu_fac, menu);
        return true;
    }*/


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_fac, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_custom_item) {
            // Acción a realizar cuando se selecciona el elemento del menú
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void eliminardatosfac() {
        String cedula = cedularucconsumidorEditText.getText().toString();
        if (validarcedula(cedula)) {

            boolean cedulaexiste = verificarcedulaexiste(cedula);

            final SQLiteDatabase db = databaseHelper.getReadableDatabase();
            if (db != null) {
                if (cedulaexiste) {

                    eliminarPorCedula(cedula);

                    limpiarcampos();

                } else {
                    mensaje4();
                }

            }else{
                mensaje5();
            }
        }
    }

    private void actualizardatosfac() {
        String cedula = cedularucconsumidorEditText.getText().toString();
        boolean cedulaexiste = verificarcedulaexiste(cedula);

        if (cedulaexiste) {
            String cedularucconsumidor = cedularucconsumidorEditText.getText().toString();
            String nombresconsumidor = nombresconsumidorEditText.getText().toString();
            String apellidosconsumidor = apellidosconsumidorEditText.getText().toString();
            String direccionconsumidor = direccionconsumidorEditText.getText().toString();
            String telefonoconsumidor = telefonoconsumidorEditText.getText().toString();
            String correoconsumidor = correoconsumidorEditText.getText().toString();

            if (validarCampos(cedularucconsumidor, nombresconsumidor, apellidosconsumidor, direccionconsumidor, telefonoconsumidor, correoconsumidor)) {
                actualizarDatosConsumidor(cedularucconsumidor, nombresconsumidor, apellidosconsumidor, direccionconsumidor, telefonoconsumidor, correoconsumidor);
            }
        } else {
            mensaje3();
        }
    }


}