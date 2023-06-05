package com.example.proyecto_dm;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Datosclientefactura extends AppCompatActivity {


    private EditText nombresconsumidorEditText;
    private EditText apellidosconsumidorEditText;
    private EditText direccionconsumidorEditText;
    private EditText cedularucconsumidorEditText;
    private EditText correoconsumidorEditText;
    private EditText telefonoconsumidorEditText;
    private Button generarButton, regresar2Button, buscarButton, actualizarButton;

    private DatabaseHelper databaseHelper;

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
        generarButton = findViewById(R.id.generarFacturaButton);
        regresar2Button = findViewById(R.id.regresar2Button);
        buscarButton = findViewById(R.id.buscarButton);
        actualizarButton = findViewById(R.id.actualizarButton);

        databaseHelper = new DatabaseHelper(this);

        generarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cedularucconsumidor = cedularucconsumidorEditText.getText().toString();
                String nombresconsumidor = nombresconsumidorEditText.getText().toString();
                String apellidosconsumidor = apellidosconsumidorEditText.getText().toString();
                String direccionconsumidor = direccionconsumidorEditText.getText().toString();
                String telefonoconsumidor = telefonoconsumidorEditText.getText().toString();
                String correoconsumidor = correoconsumidorEditText.getText().toString();



                if (validarCampos(cedularucconsumidor, nombresconsumidor, apellidosconsumidor, direccionconsumidor, telefonoconsumidor, correoconsumidor)) {
                    guardarDatosConsumidor(cedularucconsumidor, nombresconsumidor, apellidosconsumidor, direccionconsumidor, telefonoconsumidor,  correoconsumidor);
                }
            }
        });

        regresar2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Datosclientefactura.this, MainActivity.class);
                startActivity(intent);
            }
        });

        buscarButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("Range")
            @Override
            public void onClick(View v) {
                String cedularucconsumidor = cedularucconsumidorEditText.getText().toString();
                final SQLiteDatabase db = databaseHelper.getReadableDatabase();
                if(db != null){
                    EditText cedularucconsumidortemp = (EditText)findViewById(R.id.cedularucconsumidorEditText);
                    EditText nombresconsumidortemp = (EditText)findViewById(R.id.nombresconsumidorEditText);
                    EditText apellidosconsumidortemp = (EditText)findViewById(R.id.apellidosconsumidorEditText);
                    EditText telefonoconsumidortemp = (EditText)findViewById(R.id.telefonoconsumidorEditText);
                    EditText direccionconsumidortemp = (EditText)findViewById(R.id.direccionconsumidorEditText);
                    EditText correoconsumidortemp = (EditText)findViewById(R.id.correoconsumidorEditText);

                    if(validarcedula(cedularucconsumidor)){
                        Cursor c = db.rawQuery("SELECT  cedularuc, nombresconsumidor, apellidosconsumidor, direccionconsumidor, telefonoconsumidor, correoconsumidor FROM consumidor WHERE cedularuc =" + cedularucconsumidor, null);
                        if(c != null){
                            c.moveToFirst();
                            cedularucconsumidortemp.setText(c.getString(c.getColumnIndex("cedularuc")).toString());
                            nombresconsumidortemp.setText(c.getString(c.getColumnIndex("nombresconsumidor")).toString());
                            apellidosconsumidortemp.setText(c.getString(c.getColumnIndex("apellidosconsumidor")).toString());
                            direccionconsumidortemp.setText(c.getString(c.getColumnIndex("direccionconsumidor")).toString());
                            telefonoconsumidortemp.setText(c.getString(c.getColumnIndex("telefonoconsumidor")).toString());
                            correoconsumidortemp.setText(c.getString(c.getColumnIndex("correoconsumidor")).toString());
                        }
                        c.close();
                        db.close();
                    }

                }



            }
        });

        actualizarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private boolean validarCampos(String cedularucconsumidor, String nombresconsumidor, String apellidosconsumidor, String direccionconsumidor, String telefonoconsumidor, String correoconsumidor) {
        if (TextUtils.isEmpty(cedularucconsumidor) || !cedularucconsumidor.matches("[0-9]+") || cedularucconsumidor.length() <= 9 || cedularucconsumidor.length() > 15) {
            Toast.makeText(this, "Ingrese una cédula o R.U.C. válido (solo números, entre 10 y 15 caracteres)", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(nombresconsumidor) || !nombresconsumidor.matches("[a-zA-Z ]+") || nombresconsumidor.length() > 30) {
            Toast.makeText(this, "Ingrese un nombre válido (solo letras y espacios, máximo 40 caracteres)", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(apellidosconsumidor) || !apellidosconsumidor.matches("[a-zA-Z ]+") || apellidosconsumidor.length() > 30) {
            Toast.makeText(this, "Ingrese un apellido válido (solo letras y espacios, máximo 40 caracteres)", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(direccionconsumidor) || !direccionconsumidor.matches("[a-zA-Z0-9 ]+") || direccionconsumidor.length() > 40) {
            Toast.makeText(this, "Ingrese una dirección válida (solo letras, espacios y números, máximo 40 caracteres)", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(telefonoconsumidor) || !telefonoconsumidor.matches("[0-9]+") || telefonoconsumidor.length() <= 9 || telefonoconsumidor.length() > 11) {
            Toast.makeText(this, "Ingrese un numero telefonicó válido (solo números, maximó 10 caracteres)", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(correoconsumidor) || !android.util.Patterns.EMAIL_ADDRESS.matcher(correoconsumidor).matches()) {
            Toast.makeText(this, "Ingrese un correo electrónico válido", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void guardarDatosConsumidor(String cedularucconsumidor, String nombresconsumidor, String apellidosconsumidor, String direccionconsumidor, String telefonoconsumidor, String correoconsumidor) {
        final SQLiteDatabase db = databaseHelper.getWritableDatabase();

        ContentValues values3 = new ContentValues();
        values3.put(DatabaseHelper.COLUMN_CEDULARUCCONSUMIDOR, cedularucconsumidor);
        values3.put(DatabaseHelper.COLUMN_NOMBRESCONSUMIDOR, nombresconsumidor);
        values3.put(DatabaseHelper.COLUMN_APELLIDOSCONSUMIDOR, apellidosconsumidor);
        values3.put(DatabaseHelper.COLUMN_DIRECCIONCONSUMIDOR, direccionconsumidor);
        values3.put(DatabaseHelper.COLUMN_TELEFONOCONSUMIDOR, telefonoconsumidor);
        values3.put(DatabaseHelper.COLUMN_CORREOCONSUMIDOR, correoconsumidor);


        long resultado = db.insert(DatabaseHelper.TABLE_NAME3, null, values3);

        if (resultado != -1) {
            Toast.makeText(this, "Los datos del Cliente se guardaron exitosamente", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Datosclientefactura.this, FacturaActivity.class);
            startActivity(intent);
            finish();
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
}