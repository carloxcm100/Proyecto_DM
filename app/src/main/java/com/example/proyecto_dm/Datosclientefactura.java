package com.example.proyecto_dm;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
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
import androidx.core.content.ContextCompat;

/*
 * Esta clase se utiliza para recopilar y manipular los datos del cliente y la factura en una aplicación de Android.
 * Extiende la clase AppCompatActivity, lo que permite crear una actividad en la aplicación.
 */
public class Datosclientefactura extends AppCompatActivity {
    // Declaración de variables de EditText

    private EditText nombresconsumidorEditText;
    private EditText apellidosconsumidorEditText;
    private EditText direccionconsumidorEditText;
    private EditText cedularucconsumidorEditText;
    private EditText correoconsumidorEditText;
    private EditText telefonoconsumidorEditText;
    private EditText formadepagoEditText;
    private EditText numerocomandaEditText;
    // Declaración de variables de botones
    private Button generarButton;
    private Button regresar2Button;
    private Button buscarButton;
    private Button agregarButton;

    private Button limpiarcamposButton;
    // Ayudante de base de datos
    private DatabaseHelper databaseHelper;
    // Preferencias compartidas

    private SharedPreferences sharedPreferences;
    /*
     * Método que se ejecuta al crear la actividad.
     * Se inicializan las vistas y se asignan los listeners a los botones.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datosclientefactura);

        // Inicialización de EditText
        nombresconsumidorEditText = findViewById(R.id.nombresconsumidorEditText);
        apellidosconsumidorEditText = findViewById(R.id.apellidosconsumidorEditText);
        telefonoconsumidorEditText = findViewById(R.id.telefonoconsumidorEditText);
        direccionconsumidorEditText = findViewById(R.id.direccionconsumidorEditText);
        correoconsumidorEditText = findViewById(R.id.correoconsumidorEditText);
        cedularucconsumidorEditText = findViewById(R.id.cedularucconsumidorEditText);
        formadepagoEditText = findViewById(R.id.formadepagoEditText);
        numerocomandaEditText = findViewById(R.id.numerocomandaEditText);
        // Inicialización de botones
        generarButton = findViewById(R.id.generarFacturaButton);
        regresar2Button = findViewById(R.id.regresar2Button);
        buscarButton = findViewById(R.id.buscarButton);
        agregarButton = findViewById(R.id.agregarButton);
        limpiarcamposButton = findViewById(R.id.btn_limpiarcampos);

        Toolbar toolbar = findViewById(R.id.toolbarfac);
        setSupportActionBar(toolbar);
        // Inicialización del ayudante de base de dato
        databaseHelper = new DatabaseHelper(this);
        // Inicialización de preferencias compartidas
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        // Asignar listeners a los botones
        agregarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Se obtiene el valor del campo de texto cedularucconsumidorEditText y se convierte a un String. Esto se almacena en la variable cedula.
                String cedula = cedularucconsumidorEditText.getText().toString();
                //Se verifica si la cédula ingresada ya existe llamando al método verificarcedulaexiste(cedula). El resultado se guarda en la variable cedulaexiste.
                boolean cedulaexiste = verificarcedulaexiste(cedula);

                //Si cedulaexiste es true, se llama al método mensaje2(). Esto mostrará un mensaje relacionado con la existencia de la cédula.
                //Si cedulaexiste es false, se procede a obtener los valores de los otros campos de texto (nombresconsumidorEditText, apellidosconsumidorEditText, etc.) y se almacenan en variables separadas.
                if (cedulaexiste) {
                    mensaje2();
                } else {
                    String cedularucconsumidor = cedularucconsumidorEditText.getText().toString();
                    String nombresconsumidor = nombresconsumidorEditText.getText().toString();
                    String apellidosconsumidor = apellidosconsumidorEditText.getText().toString();
                    String direccionconsumidor = direccionconsumidorEditText.getText().toString();
                    String telefonoconsumidor = telefonoconsumidorEditText.getText().toString();
                    String correoconsumidor = correoconsumidorEditText.getText().toString();
                    //se llama al método validarCampos() pasando los valores obtenidos de los campos de texto como argumentos. Este método se encarga de verificar si los campos cumplen con ciertos criterios de validación.
                    if (validarCampos(cedularucconsumidor, nombresconsumidor, apellidosconsumidor, direccionconsumidor, telefonoconsumidor, correoconsumidor)) {
                        //Si validarCampos() devuelve true, se llama al método guardarDatosConsumidor() pasando los valores de los campos de texto como argumentos. Este método se encarga de guardar los datos del consumidor
                        guardarDatosConsumidor(cedularucconsumidor, nombresconsumidor, apellidosconsumidor, direccionconsumidor, telefonoconsumidor, correoconsumidor);
                    }
                }
            }
        });

        //cuando se hace clic en el botón regresar2Button, se navegará a la actividad LoginActivity, lo que permite al usuario regresar a la pantalla de inicio de sesión.
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
                // Obtener el valor del EditText cedularucconsumidor
                String cedularucconsumidor = cedularucconsumidorEditText.getText().toString();
                // Obtener una instancia de la base de datos en modo lectura
                final SQLiteDatabase db = databaseHelper.getReadableDatabase();
                //verifica si la base de datos esta vacia
                if (db != null) {
                    // Obtener referencias a los EditTexts para mostrar los resultados
                    EditText cedularucconsumidortemp = findViewById(R.id.cedularucconsumidorEditText);
                    EditText nombresconsumidortemp = findViewById(R.id.nombresconsumidorEditText);
                    EditText apellidosconsumidortemp = findViewById(R.id.apellidosconsumidorEditText);
                    EditText telefonoconsumidortemp = findViewById(R.id.telefonoconsumidorEditText);
                    EditText direccionconsumidortemp = findViewById(R.id.direccionconsumidorEditText);
                    EditText correoconsumidortemp = findViewById(R.id.correoconsumidorEditText);

                    // Verificar si la cédula es válida utilizando el método validarcedula()
                    if (validarcedula(cedularucconsumidor)) {
                        // Ejecutar una consulta SQL para obtener los datos del consumidor con la cédula ingresada
                        Cursor c = db.rawQuery("SELECT cedularuc, nombresconsumidor, apellidosconsumidor, direccionconsumidor, telefonoconsumidor, correoconsumidor FROM consumidor WHERE cedularuc = '" + cedularucconsumidor + "'", null);
                        // Si el cursor no está vacío y se pudo mover al primer registro, asignar los valores a los EditText correspondientes
                        if (c != null && c.moveToFirst()) {

                            cedularucconsumidortemp.setText(c.getString(c.getColumnIndex("cedularuc")).toString());
                            nombresconsumidortemp.setText(c.getString(c.getColumnIndex("nombresconsumidor")).toString());
                            apellidosconsumidortemp.setText(c.getString(c.getColumnIndex("apellidosconsumidor")).toString());
                            direccionconsumidortemp.setText(c.getString(c.getColumnIndex("direccionconsumidor")).toString());
                            telefonoconsumidortemp.setText(c.getString(c.getColumnIndex("telefonoconsumidor")).toString());
                            correoconsumidortemp.setText(c.getString(c.getColumnIndex("correoconsumidor")).toString());
                        }else{
                            // Si el cursor está vacío, mostrar un mensaje de error
                            mensaje7();
                        }

                        if (c != null) {
                            // Cerrar el cursor
                            c.close();
                        }
                        // Cerrar la conexión a la base de datos
                        db.close();
                    }
                }
            }
        });

        //cuando se hace clic en el botón limpiarcamposButton, se llamara al metodo limpiarcampos()
        limpiarcamposButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limpiarcampos();
            }
        });

        //cuando se hace clic en el boton generarButton se llamara al layout que mostrara la factura y le dara los valores del usuario que tiene que llenar los datos.
        generarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener los valores de los EditText
                String cedularucconsumidor = cedularucconsumidorEditText.getText().toString();
                String formadepago = formadepagoEditText.getText().toString();
                String numerocomanda = numerocomandaEditText.getText().toString();

                // Verificar si los campos cumplen con los requisitos utilizando el método validarCamposfac()
                if (validarCamposfac(cedularucconsumidor, formadepago, numerocomanda)) {
                    // Guardar los valores en SharedPreferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("CEDULA", cedularucconsumidor);
                    editor.putString("FORMAPAGO", formadepago);
                    editor.putString("COMANDA", numerocomanda);
                    editor.apply();
                    // Iniciar la actividad FacturaActivity
                    Intent intent = new Intent(Datosclientefactura.this, FacturaActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void limpiarcampos() {
        // Obtener referencias a los EditText
        EditText cedularucconsumidortemp = findViewById(R.id.cedularucconsumidorEditText);
        EditText nombresconsumidortemp = findViewById(R.id.nombresconsumidorEditText);
        EditText apellidosconsumidortemp = findViewById(R.id.apellidosconsumidorEditText);
        EditText telefonoconsumidortemp = findViewById(R.id.telefonoconsumidorEditText);
        EditText direccionconsumidortemp = findViewById(R.id.direccionconsumidorEditText);
        EditText correoconsumidortemp = findViewById(R.id.correoconsumidorEditText);
        EditText formapagotemp = findViewById(R.id.formadepagoEditText);
        EditText numerocomandatemp = findViewById(R.id.numerocomandaEditText);

        // Limpiar los campos estableciendo el texto vacío
        cedularucconsumidortemp.setText("");
        nombresconsumidortemp.setText("");
        apellidosconsumidortemp.setText("");
        direccionconsumidortemp.setText("");
        telefonoconsumidortemp.setText("");
        correoconsumidortemp.setText("");
        formapagotemp.setText("");
        numerocomandatemp.setText("");
    }


    //private boolean validarCamposfac(String cedularucconsumidor, String nombresconsumidor, String apellidosconsumidor, String formadepago, String numerocomanda) {
    private boolean validarCamposfac(String cedularucconsumidor,  String formadepago, String numerocomanda) {
        if (TextUtils.isEmpty(cedularucconsumidor) || !cedularucconsumidor.matches("[0-9]+") || cedularucconsumidor.length() <= 9 || cedularucconsumidor.length() > 15) {
            Toast.makeText(this, "Ingrese una cédula o R.U.C. válido (solo números, entre 10 y 15 caracteres)", Toast.LENGTH_SHORT).show();
            return false;
        }
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

    //metodo que muestra un mensaje
    private void mensaje7() {
        Toast.makeText(this, "La Cedula no se encuentra registrada en la base de datos procesada a resgitrar", Toast.LENGTH_SHORT).show();
    }

    //metodo que muestra un mensaje
    private void mensaje5() {
        Toast.makeText(this, "La base de datos esta vacia", Toast.LENGTH_SHORT).show();
    }
    //metodo que muestra un mensaje
    private void mensaje4() {
        Toast.makeText(this, "La cédula no está registrada no se puede eliminar de la base de datos", Toast.LENGTH_SHORT).show();
    }

    //metodo que muestra un mensaje
    private void mensaje3() {
        Toast.makeText(this, "La cédula no está registrada no se puede actualizar los datos", Toast.LENGTH_SHORT).show();
    }

    //metodo que muestra un mensaje
    private void mensaje2() {
        Toast.makeText(this, "La cédula ya está registrada no se puede volver a registrar", Toast.LENGTH_SHORT).show();
    }

    //metodo que muestra un mensaje
    private void mensaje6() {
        Toast.makeText(this, "Error al guardar los datos", Toast.LENGTH_SHORT).show();
    }

    //metodo que muestra un mensaje
    private void mensaje1() {
        Toast.makeText(this, "Los datos del Cliente se actualizaron exitosamente", Toast.LENGTH_SHORT).show();
    }

    private boolean verificarcedulaexiste(String cedula) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        // Definir las columnas a seleccionar en la consulta
        String[] columns = {"cedularuc"};
        // Definir la cláusula WHERE y los argumentos de selección
        String selection = "cedularuc= ?";
        String[] selectionArgs = {cedula};

        // Realizar la consulta a la tabla "consumidor"
        Cursor cursor = db.query("consumidor", columns, selection, selectionArgs, null, null, null);

        boolean cedulaExiste = false;

        // Verificar si el cursor contiene registros y moverse al primer registro
        if (cursor != null && cursor.moveToFirst()) {
            cedulaExiste = true;
        }

        // Cerrar el cursor y la conexión a la base de datos
        if (cursor != null) {
            //cierra cursor
            cursor.close();
        }

        //cierra la conexion a la base de datos
        db.close();

        //retorna un boleano si la cedula existe o no
        return cedulaExiste;
    }

    private boolean validarCampos(String cedularucconsumidor, String nombresconsumidor, String apellidosconsumidor, String direccionconsumidor, String telefonoconsumidor, String correoconsumidor) {
        // Validar campo de cédula o R.U.C.
        if (TextUtils.isEmpty(cedularucconsumidor) || !cedularucconsumidor.matches("[0-9]+") || cedularucconsumidor.length() <= 9 || cedularucconsumidor.length() > 15) {
            Toast.makeText(this, "Ingrese una cédula o R.U.C. válido (solo números, entre 10 y 15 caracteres)", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validar campo de nombres
        if (TextUtils.isEmpty(nombresconsumidor) || !nombresconsumidor.matches("[a-zA-Z ]+") || nombresconsumidor.length() > 30) {
            Toast.makeText(this, "Ingrese un nombre válido (solo letras y espacios, máximo 30 caracteres)", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validar campo de apellidos
        if (TextUtils.isEmpty(apellidosconsumidor) || !apellidosconsumidor.matches("[a-zA-Z ]+") || apellidosconsumidor.length() > 30) {
            Toast.makeText(this, "Ingrese un apellido válido (solo letras y espacios, máximo 30 caracteres)", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validar campo de dirección
        if (TextUtils.isEmpty(direccionconsumidor) || !direccionconsumidor.matches("[a-zA-Z0-9 ]+") || direccionconsumidor.length() > 40) {
            Toast.makeText(this, "Ingrese una dirección válida (solo letras, espacios y números, máximo 40 caracteres)", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validar campo de teléfono
        if (TextUtils.isEmpty(telefonoconsumidor) || !telefonoconsumidor.matches("[0-9]+") || telefonoconsumidor.length() <= 9 || telefonoconsumidor.length() > 11) {
            Toast.makeText(this, "Ingrese un número telefónico válido (solo números, máximo 10 caracteres)", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validar campo de correo electrónico
        if (TextUtils.isEmpty(correoconsumidor) || !android.util.Patterns.EMAIL_ADDRESS.matcher(correoconsumidor).matches()) {
            Toast.makeText(this, "Ingrese un correo electrónico válido", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Todos los campos son válidos
        return true;
    }

    private void guardarDatosConsumidor(String cedula, String nombresconsumidor, String apellidosconsumidor, String direccionconsumidor, String telefonoconsumidor, String correoconsumidor) {
        final SQLiteDatabase db = databaseHelper.getWritableDatabase();

        // Crear un objeto ContentValues para almacenar los valores de los campos
        ContentValues values3 = new ContentValues();
        values3.put(DatabaseHelper.COLUMN_CEDULARUCCONSUMIDOR, cedula);
        values3.put(DatabaseHelper.COLUMN_NOMBRESCONSUMIDOR, nombresconsumidor);
        values3.put(DatabaseHelper.COLUMN_APELLIDOSCONSUMIDOR, apellidosconsumidor);
        values3.put(DatabaseHelper.COLUMN_DIRECCIONCONSUMIDOR, direccionconsumidor);
        values3.put(DatabaseHelper.COLUMN_TELEFONOCONSUMIDOR, telefonoconsumidor);
        values3.put(DatabaseHelper.COLUMN_CORREOCONSUMIDOR, correoconsumidor);

        // Insertar los valores en la tabla de consumidores
        long resultado = db.insert(DatabaseHelper.TABLE_NAME3, null, values3);

        // Verificar el resultado de la inserción
        if (resultado != -1) {
            Toast.makeText(this, "Los datos del Cliente se guardaron exitosamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error al Guardar los datos", Toast.LENGTH_SHORT).show();
        }

        // Cerrar la conexión con la base de datos
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



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_fac, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.menuactualizar) {
            actualizardatosfac();
            return true;
        } else if (id == R.id.menueliminar) {
            eliminardatosfac();
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