package com.example.proyectofinal;

import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.proyectofinal.Modelo.Residuo;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "proyecto_final.db";
    private static final int DATABASE_VERSION = 2;

    // Tabla de usuarios
    private static final String TABLE_USUARIOS = "usuarios";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NOMBRE = "nombre";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";

    // Tabla de residuos
    private static final String TABLE_RESIDUOS = "residuos";
    private static final String COLUMN_RESIDUO_ID = "id";
    private static final String COLUMN_TIPO = "tipo";
    private static final String COLUMN_PESO = "peso";

    // Query para crear la tabla de usuarios
    private static final String CREATE_TABLE_USUARIOS =
            "CREATE TABLE " + TABLE_USUARIOS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NOMBRE + " TEXT, " +
                    COLUMN_EMAIL + " TEXT UNIQUE, " +
                    COLUMN_PASSWORD + " TEXT)";

    // Query para crear la tabla de residuos
    private static final String CREATE_TABLE_RESIDUOS =
            "CREATE TABLE " + TABLE_RESIDUOS + " (" +
                    COLUMN_RESIDUO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TIPO + " TEXT, " +
                    COLUMN_PESO + " REAL)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USUARIOS);
        db.execSQL(CREATE_TABLE_RESIDUOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USUARIOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESIDUOS);
        onCreate(db);
    }

    // Método para insertar usuario
    public void insertarUsuario(String nombre, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOMBRE, nombre);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, password);
        db.insert(TABLE_USUARIOS, null, values);
        db.close();
    }

    // Método para obtener usuarios (se usa en el login)
    public Cursor obtenerUsuarios() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_USUARIOS, null);
    }

    // Método para insertar residuo
    public void insertarResiduo(String tipo, double peso) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TIPO, tipo);
        values.put(COLUMN_PESO, peso);
        db.insert(TABLE_RESIDUOS, null, values);
        db.close();
    }

    public List<Residuo> obtenerResiduos() {
        List<Residuo> listaResiduos = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM  residuos", null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String tipo = cursor.getString(1);
                double peso = cursor.getDouble(2);
                listaResiduos.add(new Residuo(id, tipo, peso));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return listaResiduos;
    }

    public int actualizarResiduo(int id, String tipo, double peso) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tipo", tipo);
        values.put("peso", peso);
        return db.update("residuos", values, "id = ?", new String[]{String.valueOf(id)});
    }

    public int eliminarResiduo(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("residuos", "id=?", new String[]{String.valueOf(id)});
    }



    public int contarRegistros() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM residuos", null);
        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();
        return count;
    }

}
