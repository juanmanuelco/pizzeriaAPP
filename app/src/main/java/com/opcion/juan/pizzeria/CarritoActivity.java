package com.opcion.juan.pizzeria;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarritoActivity extends AppCompatActivity {
    ArrayList<String> elementosCarrito=new ArrayList<String>();
    ArrayList<String[]>elementosCarritoV2=new ArrayList<String[]>();
    DBusuarios dbSQLITE;

    private ListView listCarrito;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);
        dbSQLITE = new DBusuarios(this);
        try{
            final SharedPreferences elementosCarr=getSharedPreferences("carrito",0);
            String elemCarrito=elementosCarr.getString("registros","");
            String[] items=elemCarrito.split(",");
            if(!elemCarrito.equals("")){
                for ( int i=0;i<items.length;i+=5){
                    String[] der=new String[]{items[i], items[i+1], items[i+2], items[i+3], items[i+4]};
                    elementosCarrito.add("Pidio "+items[i+3]+" "+items[i+1]+" de precio $"+items[i+2]+", dando el valor de $"+items[i+4]);
                    elementosCarritoV2.add(new String[]{items[i], items[i+1], items[i+2], items[i+3], items[i+4]});
                }
            }
            listCarrito= (ListView) findViewById(R.id.itemsCarrito);
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                    this,android.R.layout.simple_list_item_1,elementosCarrito);

            listCarrito.setAdapter(arrayAdapter);
            listCarrito.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    elementosCarritoV2.remove(i);
                    StringBuilder stringBuilder=new StringBuilder();
                    for(int k=0;k<elementosCarritoV2.size();k++){
                        String [] temporal=elementosCarritoV2.get(k);
                        for (int j=0;j<temporal.length;j++){
                            stringBuilder.append(temporal[j]);
                            if(k==elementosCarritoV2.size() && j==temporal.length){

                            }else{
                                stringBuilder.append(",");
                            }
                        }
                    }
                    SharedPreferences elementosCarr=getSharedPreferences("carrito",0);
                    SharedPreferences.Editor editor=elementosCarr.edit();
                    editor.putString("registros",stringBuilder.toString());
                    editor.commit();
                    Intent mIntent = getIntent();
                    finish();
                    startActivity(mIntent);
                }
            });
        }catch (Exception e){
            Toast.makeText(CarritoActivity.this, "Su dispositivo es incompatible", Toast.LENGTH_SHORT).show();
        }
    }

    public void vaciarCarrito(View v){
        try{
            Toast.makeText(CarritoActivity.this, "Su dispositivo es incompatible", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            SharedPreferences elementosCarr=getSharedPreferences("carrito",0);
            SharedPreferences.Editor editor=elementosCarr.edit();
            editor.putString("registros","");
            editor.commit();
            Intent mIntent = getIntent();
            finish();
            startActivity(mIntent);
        }
    }
    public void enviarPedido(View v){
        String  serverURL="http://dulceyfriopizzas.herokuapp.com/pedidos";
        SharedPreferences elementosCarr=getSharedPreferences("carrito",0);
        String elemCarrito=elementosCarr.getString("registros","");
        if(elemCarrito.equals("")){
            Toast.makeText(CarritoActivity.this, "No hay nada que enviar", Toast.LENGTH_SHORT).show();
        }else{
            StringRequest stringRequest=new StringRequest(Request.Method.POST, serverURL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String result) {
                            if(result.toString().equals("Error")){
                                Toast.makeText(CarritoActivity.this, "Error de red", Toast.LENGTH_SHORT).show();
                            }else{
                                SharedPreferences elementosCarr=getSharedPreferences("carrito",0);
                                SharedPreferences.Editor editor=elementosCarr.edit();
                                editor.putString("registros","");
                                editor.commit();
                                Intent mIntent = getIntent();
                                finish();
                                startActivity(mIntent);
                                Toast.makeText(CarritoActivity.this, "Pedido realizado con éxito", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(CarritoActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    error.printStackTrace();
                }
            }
            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Cursor res = dbSQLITE.selectVerTodos();
                    String usuario="";
                    int i=5;
                    while (res.moveToNext()){
                        usuario=res.getString(i);
                    }
                    final SharedPreferences elementCarr=getSharedPreferences("carrito",0);
                    final String carritoEnviar=elementCarr.getString("registros","");
                    final Date fecha= new Date();
                    Map <String,String> params =new HashMap<String, String >();
                    params.put("username",usuario);
                    params.put("carrito",carritoEnviar);
                    params.put("fecha",fecha.toString());
                    return params;
                }
            };
            singletonDatos.getInstancia(CarritoActivity.this).addToRequest(stringRequest);
        }
    }
}
