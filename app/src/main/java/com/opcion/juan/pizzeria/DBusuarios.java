package com.opcion.juan.pizzeria;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.renderscript.Double2;

/**
 * Created by juanm on 05/08/2017.
 */

public class DBusuarios extends SQLiteOpenHelper {
    //Establece los datos para crear la base de datos y la tabla de usuarios
    public static final String DB_NOMBRE = "Dulceyfrio.db";
        public static final String TABLA_NOMBRE = "usuarios";
            public static final String COL_1 = "ID";
            public static final String COL_2 = "NOMBRE";
            public static final String COL_3 = "APELLIDO";
            public static final String COL_4 = "CEDULA";
            public static final String COL_5 = "CELULAR";
            public static final String COL_6 = "EMAIL";

    public DBusuarios(Context context) {
        super(context, DB_NOMBRE, null, 1);
        //Hace la base de datos editable
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Crea la tabla en la base de datos
        db.execSQL(String.format
                ("create table %s (ID INTEGER PRIMARY KEY AUTOINCREMENT,%s TEXT, %s TEXT,%s TEXT, %s TEXT,%s TEXT)",
                        TABLA_NOMBRE,COL_2,COL_3,COL_4,COL_5,COL_6));

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        //En caso de actualización
        db.execSQL(String.format("DROP TABLE IF EXISTS %s",TABLA_NOMBRE));
        onCreate(db);
    }
    public boolean insertarUser(String nombre, String apellido, String cedula, String celular, String email){
        //Guarda los datos del usuario logeado
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,nombre);
        contentValues.put(COL_3,apellido);
        contentValues.put(COL_4,cedula);
        contentValues.put(COL_5,celular);
        contentValues.put(COL_6,email);
        //Inserta los datos que se le han enviado
        long resultado = db.insert(TABLA_NOMBRE,null,contentValues);

        if(resultado == -1)
            return false;
        else
            return true;
    }
    public Cursor selectVerTodos(){
        //Obtiene los datos de la base de datos
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery(String.format("select * from %s",TABLA_NOMBRE),null);
        return  res;
    }
    public void eliminar (){
        //Elimina los datos del usuario para asi poder cerrar sesión
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(TABLA_NOMBRE,null,null);
    }
}
