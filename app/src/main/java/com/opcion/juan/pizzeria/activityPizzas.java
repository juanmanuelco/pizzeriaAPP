package com.opcion.juan.pizzeria;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;

import negocio.Filtro;
import negocio.controlCantidad;

public class activityPizzas extends AppCompatActivity {
    ArrayList<String[]>elementosCarrito=new ArrayList<String[]>();
    Boolean abiertoya=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pizzas);
        try{
            SharedPreferences elementosCarr=getSharedPreferences("carrito",0);
            String elemCarrito=elementosCarr.getString("registros","");
            String[] items=elemCarrito.split(",");
            if(!elemCarrito.equals("")){
                for ( int i=0;i<items.length;i+=5){
                    elementosCarrito.add(new String[]{items[i], items[i+1], items[i+2], items[i+3], items[i+4]});
                }
            }
        }catch (Exception e){
            Toast.makeText(activityPizzas.this, "Su dispositivo es incompatible", Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    protected void onResume() {
        super.onResume();
        if(abiertoya){
            Intent mIntent = getIntent();
            finish();
            startActivity(mIntent);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        abiertoya=true;
    }
    public void abrirCarrito(View v){
        Intent carrito = new Intent(getApplicationContext(),CarritoActivity.class );
        startActivity(carrito);
    }

    public String[] registrarCarrito(int id,TextView nombre,Double precio, EditText cantidad){
        String Nombre=nombre.getText().toString();
        String Cantidad=cantidad.getText().toString();
        String subtotal=Double.toString(precio* (Integer.parseInt(Cantidad)));
        return new String[]{Integer.toString(id), Nombre, Double.toString(precio), Cantidad, subtotal};
    }
    public void guardado(ArrayList<String[]>elementosCarrito ){
        try{
            StringBuilder stringBuilder=new StringBuilder();
            for(int i=0;i<elementosCarrito.size();i++){
                String [] temporal=elementosCarrito.get(i);
                for (int j=0;j<temporal.length;j++){
                    stringBuilder.append(temporal[j]);
                    if(i==elementosCarrito.size() && j==temporal.length){

                    }else{
                        stringBuilder.append(",");
                    }
                }
            }
            SharedPreferences elementosCarr=getSharedPreferences("carrito",0);
            SharedPreferences.Editor editor=elementosCarr.edit();
            editor.putString("registros",stringBuilder.toString());
            editor.commit();
            Toast.makeText(activityPizzas.this, "Ingresado", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(activityPizzas.this, "Su dispositivo es incompatible", Toast.LENGTH_SHORT).show();
        }

    }
    public void addCarrPCM(View v){
        TextView nombre=(TextView)findViewById(R.id.tPiClaMed);
        Double precio=9.00d;
        EditText cantidad=(EditText)findViewById(R.id.txtPCM);
        String[] aGuardar=registrarCarrito(elementosCarrito.size()+1, nombre, precio, cantidad);
        elementosCarrito.add(aGuardar);
        guardado(elementosCarrito);
    }
    public void addCarrPCG(View v){
        TextView nombre=(TextView)findViewById(R.id.tPiClaG);
        Double precio=17.00d;
        EditText cantidad=(EditText)findViewById(R.id.txtPCG);
        String[] aGuardar=registrarCarrito(elementosCarrito.size()+1, nombre, precio, cantidad);
        elementosCarrito.add(aGuardar);
        guardado(elementosCarrito);
    }
    public void addCarrPHG(View v){
        TextView nombre=(TextView)findViewById(R.id.tPiHaG);
        Double precio=22.00d;
        EditText cantidad=(EditText)findViewById(R.id.txtPHG);
        String[] aGuardar=registrarCarrito(elementosCarrito.size()+1, nombre, precio, cantidad);
        elementosCarrito.add(aGuardar);
        guardado(elementosCarrito);
    }
    public void buscarPizzas(View v){
        Filtro busqueda=new Filtro();
        LinearLayout pizzaClasicaM=(LinearLayout)findViewById(R.id.PizzaClasicaMediana);
        LinearLayout pizzaClasicaG=(LinearLayout)findViewById(R.id.PizzaClasicaGrande);
        LinearLayout pizzaHawaianaG=(LinearLayout)findViewById(R.id.pizzaHawayanaGrande);
        busqueda.ocultar(new LinearLayout[]{pizzaClasicaM,pizzaClasicaG,pizzaHawaianaG});
        String buscar=((EditText)findViewById(R.id.txtBuscarPizzas)).getText().toString().toUpperCase();
        String pizzaClasicaMediana="pizza clásica clasica mediana $9.00 $9,00  ";
        String pizzaClasicaGrande="pizza clásica clasica grande $17.00 $17,00  ";
        String pizzaHawaianaGrande="pizza hawaiana grande $22.00 $22,00";
        if(pizzaClasicaMediana.toUpperCase().indexOf(buscar)>-1){
            busqueda.mostrar(pizzaClasicaM);
        }
        if(pizzaClasicaGrande.toUpperCase().indexOf(buscar)>-1){
            busqueda.mostrar(pizzaClasicaG);
        }
        if (pizzaHawaianaGrande.toUpperCase().indexOf(buscar)>-1){
            busqueda.mostrar(pizzaHawaianaG);
        }
    }
    public void addPCM(View v){
        EditText ETcantidad=(EditText)findViewById(R.id.txtPCM);
        ETcantidad.setText(controlCantidad.add(ETcantidad));
        return;
    }
    public void lessPCM(View v){
        EditText ETcantidad=(EditText)findViewById(R.id.txtPCM);
        ETcantidad.setText(controlCantidad.restar(ETcantidad));
        return;
    }
    public void addPCG(View v){
        EditText ETcantidad=(EditText)findViewById(R.id.txtPCG);
        ETcantidad.setText(controlCantidad.add(ETcantidad));
        return;
    }
    public void lessPCG(View v){
        EditText ETcantidad=(EditText)findViewById(R.id.txtPCG);
        ETcantidad.setText(controlCantidad.restar(ETcantidad));
        return;
    }
    public void addPHG(View v){
        EditText ETcantidad=(EditText)findViewById(R.id.txtPHG);
        ETcantidad.setText(controlCantidad.add(ETcantidad));
        return;
    }
    public void lessPHG(View v){
        EditText ETcantidad=(EditText)findViewById(R.id.txtPHG);
        ETcantidad.setText(controlCantidad.restar(ETcantidad));
        return;
    }
}
