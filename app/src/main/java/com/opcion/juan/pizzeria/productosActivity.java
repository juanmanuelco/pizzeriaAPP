package com.opcion.juan.pizzeria;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class productosActivity extends AppCompatActivity {
    DBusuarios dbSQLITE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);
        dbSQLITE = new DBusuarios(this);
        Cursor res = dbSQLITE.selectVerTodos();
        int valor=res.getCount();

        //Si existen datos de usuario guardados inicia automáticamente, caso contrario tendra que logearse
        if(valor<1){
            Intent login = new Intent(getApplicationContext(),MainActivity.class );
            startActivity(login);
        }
    }
    @Override
    public void onBackPressed() {

    }
    public void cerrarSesion(View v ){
        dbSQLITE.eliminar();
        Intent login = new Intent(getApplicationContext(),MainActivity.class );
        Toast.makeText(productosActivity.this, "Cerrando sesión", Toast.LENGTH_SHORT).show();
        startActivity(login);
        SharedPreferences elementosCarr=getSharedPreferences("carrito",0);
        SharedPreferences.Editor editor=elementosCarr.edit();
        editor.putString("registros","");
        editor.commit();
    }
    public void abrirPizzas(View v){
        Intent pizzas = new Intent(getApplicationContext(),activityPizzas.class );
        startActivity(pizzas);
    }
    public void abrirBebidas(View v){
        Intent bebidas = new Intent(getApplicationContext(),activityBebidas.class );
        startActivity(bebidas);
    }
    public void abrirPostres(View v){
        Intent postres = new Intent(getApplicationContext(),activityPostres.class );
        startActivity(postres);
    }
    public void abrirCarrito(View v){
        Intent carrito = new Intent(getApplicationContext(),CarritoActivity.class );
        startActivity(carrito);
    }
}
