package com.example.proyecto_dm;

import android.content.ContentValues;
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
    public static final String COLUMN_CEDULARUCCONSUMIDOR = "cedularuc";
    public static final String COLUMN_NOMBRESCONSUMIDOR = "nombresconsumidor";
    public static final String COLUMN_APELLIDOSCONSUMIDOR = "apellidosconsumidor";
    public static final String COLUMN_DIRECCIONCONSUMIDOR = "direccionconsumidor";
    public static final String COLUMN_TELEFONOCONSUMIDOR = "telefonoconsumidor";
    public static final String COLUMN_CORREOCONSUMIDOR = "correoconsumidor";
    public static final String TABLE_NAME4 = "ordenespedidos";
    public static final String COLUMN_IDORDENESPEDIDOS = "id";
    public static final String COLUMN_CODIGOORDENESPEDIDOS = "codigo";
    public static final String COLUMN_NOMBREORDENESPEDIDOS = "nombre";
    public static final String COLUMN_PRECIOORDENESPEDIDOS = "precio";
    public static final String COLUMN_CANTIDADORDENESPEDIDOS = "cantidad";
    public static final String COLUMN_TOTALORDENESPEDIDOS = "total";

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

    private static final String CREATE_TABLE_QUERY4 = "CREATE TABLE " + TABLE_NAME4 + " (" +
            COLUMN_IDORDENESPEDIDOS + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_CODIGOORDENESPEDIDOS + " TEXT," +
            COLUMN_NOMBREORDENESPEDIDOS + " TEXT," +
            COLUMN_PRECIOORDENESPEDIDOS + " TEXT," +
            COLUMN_CANTIDADORDENESPEDIDOS + " TEXT," +
            COLUMN_TOTALORDENESPEDIDOS + " TEXT" +
            ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE_QUERY);

        db.execSQL(CREATE_TABLE_QUERY3);
        // Insertar un usuario por defecto
        insertarUsuario(db, "Nelson Angel", "Zamora Zhingri", 28, "0927256735", "correo@example.com", "Nexonm", "0927256735");

        db.execSQL(CREATE_TABLE_QUERY4);
        insertarordenespedidos(db,"22222","Hamburguesa","5.2","2","10.4");
        insertarordenespedidos(db,"22222","Hotdog","3.3","2","6.6");
        insertarordenespedidos(db,"22222","Coca - Cola","1","3","3");
        insertarordenespedidos(db,"22222","Jugo de Naranja","1.4","1","1.4");
        insertarordenespedidos(db,"22222","Salsas Extras","0.50","4","2");
        insertarordenespedidos(db,"33333","Hotdog","5.2","2","10.4");
        insertarordenespedidos(db,"33333","Coca - Cola","1","2","2");
        insertarordenespedidos(db,"33333","Jugo de Naranja","1.4","5","7");
        insertarordenespedidos(db,"44444","Hamburguesa","5.2","2","10.4");
        insertarordenespedidos(db,"44444","Hotdog","3.2","4","12.8");
        insertarordenespedidos(db,"44444","Coca - Cola","1","5","5");
        insertarordenespedidos(db,"44444","Jugo de Naranja","1.4","2","2.8");
        insertarordenespedidos(db,"44444","Salsas Extras","0.50","4","2");
        insertarordenespedidos(db,"44444","Pizza","5.2","2","10.4");
        insertarordenespedidos(db,"44444","Papas Fritas","2.2","5","11");
        insertarordenespedidos(db,"44444","Arroz con Pollo","3","2","6");
        insertarordenespedidos(db,"44444","Sangria","7.3","2","14.6");
        insertarordenespedidos(db,"44444","Arroz Marinero","5.2","2","10.4");
        insertarordenespedidos(db,"44444","Cazuela","2","4","8");
        insertarordenespedidos(db,"44444","Moro","5.2","2","10.4");
        insertarordenespedidos(db,"44444","Sushi","10.2","7","71.4");
        insertarordenespedidos(db,"44444","Pollo Frito","1.5","2","3");
        insertarordenespedidos(db,"44444","Tacos","1","1","1");
        insertarordenespedidos(db,"44444","Sándwiches de Carne","2.5","2","5");
        insertarordenespedidos(db,"44444","Sándwiches de Pollo","2","2","4");
        insertarordenespedidos(db,"44444","Sándwiches de Atun","1","1","1");
        insertarordenespedidos(db,"44444","Sopa Marinera","1.25","2","2.50");
        insertarordenespedidos(db,"44444","Nuggets de pollo","1","1","1");
        insertarordenespedidos(db,"44444","Shawarma","1.5","2","3");
        insertarordenespedidos(db,"44444","Wraps","2","2","4");
        insertarordenespedidos(db,"44444","Empanadas","1","4","4");
        insertarordenespedidos(db,"44444","Ramen","5","3","15");

    }

    private void insertarordenespedidos(SQLiteDatabase db, String codigo, String nombre, String precio, String cantidad, String total) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_CODIGOORDENESPEDIDOS, codigo);
        values.put(COLUMN_NOMBREORDENESPEDIDOS, nombre);
        values.put(COLUMN_PRECIOORDENESPEDIDOS, precio);
        values.put(COLUMN_CANTIDADORDENESPEDIDOS, cantidad);
        values.put(COLUMN_TOTALORDENESPEDIDOS, total);
        db.insert(TABLE_NAME4, null, values);
    }

    private void insertarUsuario(SQLiteDatabase db, String nombres, String apellidos, int edad, String cedula, String correo, String usuario, String contraseña) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOMBRES, nombres);
        values.put(COLUMN_APELLIDOS, apellidos);
        values.put(COLUMN_EDAD, edad);
        values.put(COLUMN_CEDULA, cedula);
        values.put(COLUMN_CORREO, correo);
        values.put(COLUMN_USUARIO, usuario);
        values.put(COLUMN_CONTRASEÑA, contraseña);
        db.insert(TABLE_NAME, null, values);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Implementa la lógica para actualizar la base de datos si es necesario
    }
}

