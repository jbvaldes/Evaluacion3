package com.example.evaluacion3.db;
import android.provider.BaseColumns;

public class AppContrato {

    //Constructor Privado
    private AppContrato(){}

    public static class Automovil implements BaseColumns{

        public static final String TABLE_NAME               ="automoviles";
        public static final String  COLUMN_NAME_MODELO      ="modelo";
        public static final String COLUMN_NAME_PATENTE      ="patente";
        public static final String COLUMN_NAME_PRECIO       ="precio";

    }
}
