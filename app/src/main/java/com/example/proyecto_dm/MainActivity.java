package com.example.proyecto_dm;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText usuarioEditText;
    private EditText contraseñaEditText;
    private Button iniciarSesionButton;
    private Button crearUsuarioButton;
    private Button borrarUsuariosButton;

    private DatabaseHelper databaseHelper;
    public SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ini();

        iniciarSesionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuario = usuarioEditText.getText().toString();
                String contraseña = contraseñaEditText.getText().toString();

                if (validarCredenciales(usuario, contraseña)) {
                    // Inicio de sesión exitoso
                    Toast.makeText(MainActivity.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Credenciales incorrectas
                    Toast.makeText(MainActivity.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
                }
            }
        });

        crearUsuarioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent crearUsuarioIntent = new Intent(MainActivity.this, CrearUsuarioActivity.class);
                startActivity(crearUsuarioIntent);
            }
        });

        borrarUsuariosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                borrarUsuarios();
            }
        });
        Button consultarUsuariosButton = findViewById(R.id.consultarUsuariosButton);
        consultarUsuariosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarUsuarios();
            }
        });
    }

    public void ini() {
        usuarioEditText = findViewById(R.id.et_usuario);
        contraseñaEditText = findViewById(R.id.et_contraseña);
        iniciarSesionButton = findViewById(R.id.btn_login);
        crearUsuarioButton = findViewById(R.id.btn_crear);
        borrarUsuariosButton = findViewById(R.id.borrarUsuariosButton);

        databaseHelper = new DatabaseHelper(this);
        sharedPreferences = getSharedPreferences("my_shared_prefs", Context.MODE_PRIVATE);
    }

    private boolean validarCredenciales(String usuario, String contraseña) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        String[] projection = {
                DatabaseHelper.COLUMN_USUARIO,
                DatabaseHelper.COLUMN_CONTRASEÑA,
                DatabaseHelper.COLUMN_NOMBRES,
                DatabaseHelper.COLUMN_APELLIDOS
        };

        String selection = DatabaseHelper.COLUMN_USUARIO + " = ? AND " + DatabaseHelper.COLUMN_CONTRASEÑA + " = ?";
        String[] selectionArgs = {usuario, contraseña};

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        boolean credencialesCorrectas = cursor.getCount() > 0;

        if (credencialesCorrectas) {
            // Obtener información del usuario
            if (cursor.moveToFirst()) {
                String nombres = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NOMBRES));
                String apellidos = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_APELLIDOS));

                // Guardar información del usuario en SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("usuario", usuario);
                editor.putString("nombres", nombres);
                editor.putString("apellidos", apellidos);
                editor.apply();
            }
        }

        cursor.close();
        db.close();

        return credencialesCorrectas;
    }

    private void borrarUsuarios() {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.delete(DatabaseHelper.TABLE_NAME, null, null);
        Toast.makeText(this, "Usuarios borrados", Toast.LENGTH_SHORT).show();
        db.close();
    }

    private void mostrarUsuarios() {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        String[] projection = {
                DatabaseHelper.COLUMN_ID,
                DatabaseHelper.COLUMN_NOMBRES,
                DatabaseHelper.COLUMN_APELLIDOS,
                DatabaseHelper.COLUMN_EDAD,
                DatabaseHelper.COLUMN_CEDULA,
                DatabaseHelper.COLUMN_CORREO,
                DatabaseHelper.COLUMN_USUARIO,
                DatabaseHelper.COLUMN_CONTRASEÑA
        };

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        StringBuilder usuariosBuilder = new StringBuilder();
        int count = 1;
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID));
            String nombres = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NOMBRES));
            String apellidos = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_APELLIDOS));
            int edad = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_EDAD));
            String cedula = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CEDULA));
            String correo = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CORREO));
            String usuario = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_USUARIO));
            String contraseña = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CONTRASEÑA));

            usuariosBuilder.append(count).append(". ").append("\n").append("ID: ").append(id).append("\n").append("Nombres: ").append(nombres).append("\n")
                    .append("Apellidos: ").append(apellidos).append("\n").append("Edad: ").append(edad).append("\n").append("Cédula: ")
                    .append(cedula).append("\n").append("Correo: ").append(correo).append("\n").append("Usuario: ").append(usuario).append("\n")
                    .append("Contraseña: ").append(contraseña).append("\n");
            count++;
        }

        cursor.close();
        db.close();

        String usuarios = usuariosBuilder.toString();

        if (usuarios.isEmpty()) {
            Toast.makeText(this, "No se encontraron usuarios", Toast.LENGTH_SHORT).show();
        } else {
            // Muestra los usuarios en un cuadro de diálogo, puedes modificar esto según tus necesidades
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Usuarios");
            builder.setMessage(usuarios);
            builder.setPositiveButton("Aceptar", null);
            builder.show();
        }
    }

}

