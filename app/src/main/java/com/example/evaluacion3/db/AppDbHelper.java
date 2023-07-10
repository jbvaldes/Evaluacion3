package com.example.evaluacion3.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AppDbHelper  extends  SQLiteOpenHelper{


    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Automoviles.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + AppContrato.Automovil.TABLE_NAME + " (" +
                    AppContrato.Automovil._ID + " INTEGER PRIMARY KEY," +
                    AppContrato.Automovil.COLUMN_NAME_MODELO + " TEXT," +
                    AppContrato.Automovil.COLUMN_NAME_PATENTE+ " TEXT)"+
                    AppContrato.Automovil.COLUMN_NAME_PRECIO + " TEXT," ;


    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + AppContrato.Automovil.TABLE_NAME;

    public AppDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);


        // puedo insertar datos por defecto en la tabla aquí
        ContentValues automovil1 = new ContentValues();
        automovil1.put(AppContrato.Automovil.COLUMN_NAME_MODELO, "Clio");
        automovil1.put(AppContrato.Automovil.COLUMN_NAME_PATENTE, "SN4286");
        automovil1.put(AppContrato.Automovil.COLUMN_NAME_PRECIO, "2.000.000");
        db.insert(AppContrato.Automovil.TABLE_NAME, null, automovil1 );

        //Automovil 2
        // puedo insertar datos por defecto en la tabla aquí
        ContentValues automovil2 = new ContentValues();
        automovil2.put(AppContrato.Automovil.COLUMN_NAME_MODELO, "Siena");
        automovil2.put(AppContrato.Automovil.COLUMN_NAME_PATENTE, "PT4586");
        automovil2.put(AppContrato.Automovil.COLUMN_NAME_PRECIO, "4.000.000");
        db.insert(AppContrato.Automovil.TABLE_NAME, null, automovil2 );

    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // no es necesario de implementar
    }
}