package com.example.proyecto_dm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Actividad para crear un nuevo usuario.
 */
public class CrearUsuarioActivity extends AppCompatActivity {
    private EditText nombresEditText;
    private EditText apellidosEditText;
    private EditText edadEditText;
    private EditText cedulaEditText;
    private EditText correoEditText;
    private EditText usuarioEditText;
    private EditText contraseñaEditText;
    private Button guardarButton, regresarButton;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_usuario);

        nombresEditText = findViewById(R.id.nombresEditText);
        apellidosEditText = findViewById(R.id.apellidosEditText);
        edadEditText = findViewById(R.id.edadEditText);
        cedulaEditText = findViewById(R.id.cedulaEditText);
        correoEditText = findViewById(R.id.correoEditText);
        usuarioEditText = findViewById(R.id.usuarioEditText);
        contraseñaEditText = findViewById(R.id.contraseñaEditText);
        guardarButton = findViewById(R.id.guardarButton);
        regresarButton = findViewById(R.id.regresarButton);

        databaseHelper = new DatabaseHelper(this);

        guardarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombres = nombresEditText.getText().toString();
                String apellidos = apellidosEditText.getText().toString();
                int edad = Integer.parseInt(edadEditText.getText().toString());
                String cedula = cedulaEditText.getText().toString();
                String correo = correoEditText.getText().toString();
                String usuario = usuarioEditText.getText().toString();
                String contraseña = contraseñaEditText.getText().toString();

                if (validarCampos(nombres, apellidos, cedula, correo)) {
                    guardarUsuario(nombres, apellidos, edad, cedula, correo, usuario, contraseña);
                }
            }
        });
        regresarButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(CrearUsuarioActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Valida los campos del formulario de creación de usuario.
     *
     * @param nombres  Nombres del usuario.
     * @param apellidos  Apellidos del usuario.
     * @param cedula  Cédula del usuario.
     * @param correo  Correo electrónico del usuario.
     * @return true si los campos son válidos, false de lo contrario.
     */
    private boolean validarCampos(String nombres, String apellidos, String cedula, String correo) {
        if (TextUtils.isEmpty(nombres) || !nombres.matches("[a-zA-Z ]+") || nombres.length() > 20) {
            Toast.makeText(this, "Ingrese un nombre válido (solo letras y espacios, máximo 20 caracteres)", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(apellidos) || !apellidos.matches("[a-zA-Z ]+") || apellidos.length() > 20) {
            Toast.makeText(this, "Ingrese un apellido válido (solo letras y espacios, máximo 20 caracteres)", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(cedula) || !cedula.matches("[0-9]+") || cedula.length() <= 9 || cedula.length() > 15) {
            Toast.makeText(this, "Ingrese una cédula válida (solo números, entre 10 y 15 caracteres)", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(correo) || !android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            Toast.makeText(this, "Ingrese un correo electrónico válido", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    /**
     * Guarda un nuevo usuario en la base de datos.
     *
     * @param nombres  Nombres del usuario.
     * @param apellidos  Apellidos del usuario.
     * @param edad  Edad del usuario.
     * @param cedula  Cédula del usuario.
     * @param correo  Correo electrónico del usuario.
     * @param usuario  Nombre de usuario del usuario.
     * @param contraseña  Contraseña del usuario.
     */
    private void guardarUsuario(String nombres, String apellidos, int edad, String cedula, String correo, String usuario, String contraseña) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NOMBRES, nombres);
        values.put(DatabaseHelper.COLUMN_APELLIDOS, apellidos);
        values.put(DatabaseHelper.COLUMN_EDAD, edad);
        values.put(DatabaseHelper.COLUMN_CEDULA, cedula);
        values.put(DatabaseHelper.COLUMN_CORREO, correo);
        values.put(DatabaseHelper.COLUMN_USUARIO, usuario);
        values.put(DatabaseHelper.COLUMN_CONTRASEÑA, contraseña);

        long resultado = db.insert(DatabaseHelper.TABLE_NAME, null, values);

        if (resultado != -1) {
            Toast.makeText(this, "Usuario creado exitosamente", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Error al crear el usuario", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }
}
