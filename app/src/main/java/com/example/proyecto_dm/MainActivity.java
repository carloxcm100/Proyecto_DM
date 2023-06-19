package com.example.proyecto_dm;

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
}

