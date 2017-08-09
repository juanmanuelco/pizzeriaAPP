package com.opcion.juan.pizzeria;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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
    //Aqui se guardarán los elementos del carritp
    ArrayList<String> elementosCarrito=new ArrayList<String>();
    //esto es un respaldo de los elementos del carritpo
    ArrayList<String[]>elementosCarritoV2=new ArrayList<String[]>();
    //Crea una instancia para obtener los datos del usuario
    DBusuarios dbSQLITE;
    //Los elementos del carrito se mostrarán en este listview
    private ListView listCarrito;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);
        //Obtenemos los datos de usuario registrados en la base de datos
        dbSQLITE = new DBusuarios(this);
        try{
            //Los elementos del  Shared preferences almacenados son guardados ahora en el arraylist
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
            //Ahora procedemos a guardar estos datos en la vista de lista
            listCarrito= (ListView) findViewById(R.id.itemsCarrito);
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                    this,android.R.layout.simple_list_item_1,elementosCarrito);

            listCarrito.setAdapter(arrayAdapter);
            listCarrito.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    //Si tocamos un elemento de esta lista lo eliminaremos del cartito
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
                    //Después de ue el elementoo este actualizado lo guardaremos nuevamemte
                    SharedPreferences elementosCarr=getSharedPreferences("carrito",0);
                    SharedPreferences.Editor editor=elementosCarr.edit();
                    editor.putString("registros",stringBuilder.toString());
                    editor.commit();
                    //Reiniciamos la actividad
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
            //eliminamos los datos de carrito del shared preferences
            SharedPreferences elementosCarr=getSharedPreferences("carrito",0);
            SharedPreferences.Editor editor=elementosCarr.edit();
            editor.putString("registros","");
            editor.commit();
            //reiniciamos el app
            Intent mIntent = getIntent();
            finish();
            startActivity(mIntent);
        }
    }
    public void enviarPedido(View v){
        //Usamos la libreria de VOLLEY para definir los datos a enviar al servidor
        String  serverURL="http://dulceyfriopizzas.herokuapp.com/pedidos";
        //Obtenemos los valores del carrito
        SharedPreferences elementosCarr=getSharedPreferences("carrito",0);
        String elemCarrito=elementosCarr.getString("registros","");
        EditText direc=(EditText)findViewById(R.id.txtDireccionEntrega);
        final String direccion=direc.getText().toString();
        //Si se especifico una dirección y los elementos del carrito entonces procede a guardar
        if(elemCarrito.equals("") || direccion.trim().equals("")){
            Toast.makeText(CarritoActivity.this, "No hay nada que enviar o no se especificó una dirección", Toast.LENGTH_SHORT).show();
        }else{
            StringRequest stringRequest=new StringRequest(Request.Method.POST, serverURL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String result) {
                            //Depende de las respuestas del servidor hace lo siguiente
                            if(result.toString().equals("Error")){
                                Toast.makeText(CarritoActivity.this, "Error de red", Toast.LENGTH_SHORT).show();
                            }else{
                                //Si se realizo la totalidad bien entonces borra el carrito
                                SharedPreferences elementosCarr=getSharedPreferences("carrito",0);
                                SharedPreferences.Editor editor=elementosCarr.edit();
                                editor.putString("registros","");
                                editor.commit();
                                //Muestra un dialogo informandole al usuario ue este pendiente del email
                                AlertDialog.Builder builder = new AlertDialog.Builder(CarritoActivity.this);
                                builder.setTitle("Estimado usuario")
                                        .setMessage("En breve le llegará un correo electrónico donde se detallará la información de su pedido")
                                        .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                //Cierra el dialogo y reinicia el app
                                                Intent mIntent = getIntent();
                                                finish();
                                                startActivity(mIntent);
                                                Toast.makeText(CarritoActivity.this, "Pedido realizado con éxito", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //Muestra un error
                    Toast.makeText(CarritoActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    error.printStackTrace();
                }
            }
            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    //Define los valores a enviar a la base de datos
                    Cursor res = dbSQLITE.selectVerTodos();
                    String usuario="";
                    int i=5;
                    while (res.moveToNext()){
                        usuario=res.getString(i);
                    }
                    //Estos son los valores del carrito a enviar
                    final SharedPreferences elementCarr=getSharedPreferences("carrito",0);
                    final String carritoEnviar=elementCarr.getString("registros","");
                    //Obtenemos los datos  de fecha actual
                    final Date fecha= new Date();
                    //Generamos un mapa de datos
                    Map <String,String> params =new HashMap<String, String >();
                    params.put("username",usuario);
                    params.put("carrito",carritoEnviar);
                    params.put("fecha",fecha.toString());
                    params.put("direccion",direccion);
                    return params;
                }
            };
            //Usamos un modelo de datos para el envío
            singletonDatos.getInstancia(CarritoActivity.this).addToRequest(stringRequest);
        }
    }
}
