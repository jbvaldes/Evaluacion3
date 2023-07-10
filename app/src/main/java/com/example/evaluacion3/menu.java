package com.example.evaluacion3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.example.evaluacion3.db.AppContrato;
import com.example.evaluacion3.db.AppDbHelper;
import com.example.evaluacion3.modelo.Automovil;
import com.example.evaluacion3.recyclerview.AutomovilAdapter;

public class menu extends AppCompatActivity {


    private Button btnGuardar;
    private Button btnNuevo;
    private int posicionRecyclerView = RecyclerView.NO_POSITION;
    public enum Accion {
        CREAR,
        EDITAR
    }


    private Accion accion = Accion.CREAR;

    private void setAccion(Accion accion) {
        this.accion = accion;
        if( accion.equals(Accion.CREAR)) {
            btnGuardar.setText("Crear");
        } else {
            btnGuardar.setText("Editar");
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // recupera libros BD
        AppDbHelper dbHelper    = new AppDbHelper(this);
        SQLiteDatabase db       = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(AppContrato.Automovil.TABLE_NAME, null, null, null, null, null, null, null);
        List<Automovil> automoviles = new ArrayList<Automovil>();
        while( cursor.moveToNext()) {
            long codigoId        = cursor.getLong(cursor.getColumnIndexOrThrow(AppContrato.Automovil._ID));
            String automovilModelo  = cursor.getString( cursor.getColumnIndexOrThrow(AppContrato.Automovil.COLUMN_NAME_MODELO) );
            String automovilPatente  = cursor.getString( cursor.getColumnIndexOrThrow(AppContrato.Automovil.COLUMN_NAME_PATENTE) );
            String automovilPrecio  = cursor.getString( cursor.getColumnIndexOrThrow(AppContrato.Automovil.COLUMN_NAME_PRECIO) );
            Automovil automovil         = new Automovil(codigoId, automovilModelo, automovilPatente, automovilPatente);
            automoviles.add(automovil);
        }

        // RecyclerView
        RecyclerView rvAutomoviles = (RecyclerView) findViewById(R.id.recyclerViewAutomoviles);
        rvAutomoviles.setLayoutManager(new LinearLayoutManager(this));
        AutomovilAdapter automovilAdapter = new automovilAdapter( automoviles );
        rvAutomoviles.setAdapter(automovilAdapter);

        // controles del formulario
        EditText etCodigo     = (EditText) findViewById(R.id.etCodigo);
        EditText etModelo = (EditText) findViewById(R.id.etModelo);
        EditText etPatente  = (EditText) findViewById(R.id.etPatente);
        EditText etPrecio  = (EditText) findViewById(R.id.etPrecio);
        btnGuardar = (Button) findViewById(R.id.btnGuardar);
        btnNuevo   = (Button) findViewById(R.id.btnNuevo);



        btnNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etCodigo.setText("");
                etModelo.setText("");
                etPatente.setText("");
                etPrecio.setText("");
                setAccion(Accion.CREAR);
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {

            private void insertar(String modelo, String patente, String precio) {
                // Inserta en BD SQLite
                SQLiteDatabase db  = dbHelper.getWritableDatabase();
                ContentValues cvLibro = new ContentValues();
                cvAutomovil.put(AppContrato.Automovil.COLUMN_NAME_MODELO, modelo);
                cvAutomovil.put(AppContrato.Automovil.COLUMN_NAME_PATENTE, patente);
                cvAutomovil.put(AppContrato.Automovil.COLUMN_NAME_PRECIO, precio);
                long idInsertado = db.insert(AppContrato.Automovil.TABLE_NAME, null, cvLibro);

                // NOTIFICAR AL RECYCLERVIEW
                Automovil automovilNuevo = new Automovil(idInsertado, modelo, patente, precio);
                automovilAdapter.agregarAutomovil( automovilNuevo );

                // NOTIFICAR AL USUARIO
                Toast.makeText(menu.this, "Se insertó libro con ID "+idInsertado +" "+ modelo, Toast.LENGTH_LONG).show();
                // si quisiera ir a otra pantalla (Activity)
                //Intent intent = new Intent(MainActivity.this, OtraPantallaActivity.class);
                //intent.putExtra("mensaje", "Se insertó libro con ID "+idInsertado);
                //startActivity(intent);
            }


            private void editar(int codigo, String modelo, String patente, String precio) {
                SQLiteDatabase db  = dbHelper.getWritableDatabase();
                ContentValues cvAutomovil = new ContentValues();
                cvAutomovil.put(AppContrato.Automovil.COLUMN_NAME_MODELO, modelo);
                cvAutomovil.put(AppContrato.Automovil.COLUMN_NAME_PATENTE, patente);
                cvAutomovil.put(AppContrato.Automovil.COLUMN_NAME_PATENTE, precio);
                String where        = AppContrato.Automovil._ID +" = ?";
                String[] whereArgs  = {""+codigo};
                db.update(AppContrato.Automovil.TABLE_NAME, cvAutomovil, where, whereArgs);

                // NOTIFICAR AL RECYCLERVIEW
                Automovil automoovilModificado = new Automovil(new Long(codigo), modelo, patente, precio);
                automovilAdapter.automoovilModificado( automoovilModificado, posicionRecyclerView );

                // NOTIFICAR AL USUARIO
                Toast.makeText(menu.this, "Se editó el libro con ID "+codigo +" "+ modelo, Toast.LENGTH_LONG).show();
            }



            @Override
            public void onClick(View v) {
                // Recupera datos ingresados por el usuario
                String modelo   = etModelo.getText().toString();
                String patente    = etPatente.getText().toString();
                String precio    = etPrecio.getText().toString();

                if( accion == Accion.CREAR ) {
                    insertar(modelo, patente, precio);
                } else {
                    int id = Integer.parseInt( etCodigo.getText().toString() );
                    editar(codigo, modelo, patente, precio);
                }
            }
        });


        // eventos adapter
        automovilAdapter.setListener(new AutomovilAdapter.OnItemClickListener() {
            @Override
            public void onEditarClick(Automovil automovil, int posicion) {
                etCodigo.setText(automovil.getCodigo().toString());
                etModelo.setText(automovil.getModelo());
                etPatente.setText(automovil.getPatente());
                etPrecio.setText(automovil.getPrecio());
                setAccion(Accion.EDITAR);
                posicionRecyclerView = posicion;
            }


            @Override
            public void onEliminarClick(Automovil automovil, int posicion) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.delete(AppContrato.Automovil.TABLE_NAME, AppContrato.Automovil._ID + " = ?", new String[]{automovil.getId().toString()});
                Toast.makeText(menu.this, "Eliminando libro ID "+automovil.getCodigo(), Toast.LENGTH_LONG).show();
                posicionRecyclerView = posicion;
                automovilAdapter.removerLibro(automovil);
            }
        });
    }
}