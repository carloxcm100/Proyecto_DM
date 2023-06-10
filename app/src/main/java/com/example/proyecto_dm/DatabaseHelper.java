package com.example.proyecto_dm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "usuarios.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "usuarios";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NOMBRES = "nombres";
    public static final String COLUMN_APELLIDOS = "apellidos";
    public static final String COLUMN_EDAD = "edad";
    public static final String COLUMN_CEDULA = "cedula";
    public static final String COLUMN_CORREO = "correo";
    public static final String COLUMN_USUARIO = "usuario";
    public static final String COLUMN_CONTRASEÑA = "contraseña";

    public static final String TABLE_NAME3 = "consumidor";
    public static final String COLUMN_IDCONSUMIDOR = "id";
    public static final String COLUMN_CEDULARUCCONSUMIDOR = "cedula";
    public static final String COLUMN_NOMBRESCONSUMIDOR = "nombresconsumidor";
    public static final String COLUMN_APELLIDOSCONSUMIDOR = "apellidosconsumidor";
    public static final String COLUMN_DIRECCIONCONSUMIDOR = "direccionconsumidor";
    public static final String COLUMN_TELEFONOCONSUMIDOR = "telefonoconsumidor";
    public static final String COLUMN_CORREOCONSUMIDOR = "correoconsumidor";

    private static final String CREATE_TABLE_QUERY = "CREATE TABLE " + TABLE_NAME + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_NOMBRES + " TEXT," +
            COLUMN_APELLIDOS + " TEXT," +
            COLUMN_EDAD + " INTEGER," +
            COLUMN_CEDULA + " TEXT," +
            COLUMN_CORREO + " TEXT," +
            COLUMN_USUARIO + " TEXT," +
            COLUMN_CONTRASEÑA + " TEXT" +
            ")";

    private static final String CREATE_TABLE_QUERY3 = "CREATE TABLE " + TABLE_NAME3 + " (" +
            COLUMN_IDCONSUMIDOR + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_CEDULARUCCONSUMIDOR + " TEXT," +
            COLUMN_NOMBRESCONSUMIDOR + " TEXT," +
            COLUMN_APELLIDOSCONSUMIDOR + " TEXT," +
            COLUMN_DIRECCIONCONSUMIDOR + " TEXT," +
            COLUMN_TELEFONOCONSUMIDOR + " TEXT," +
            COLUMN_CORREOCONSUMIDOR + " TEXT" +
            ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE_QUERY);

        db.execSQL(CREATE_TABLE_QUERY3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Implementa la lógica para actualizar la base de datos si es necesario
    }
}

