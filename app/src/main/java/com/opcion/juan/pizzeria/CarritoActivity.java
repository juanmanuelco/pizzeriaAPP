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

import java.util.ArrayList;
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
    }

    public void vaciarCarrito(View v){
        SharedPreferences elementosCarr=getSharedPreferences("carrito",0);
        SharedPreferences.Editor editor=elementosCarr.edit();
        editor.putString("registros","");
        editor.commit();
        Intent mIntent = getIntent();
        finish();
        startActivity(mIntent);
    }
    public void enviarPedido(View v){
        String  serverURL="http://dulceyfriopizzas.herokuapp.com/index/pedidos";
        SharedPreferences elementosCarr=getSharedPreferences("carrito",0);
        String elemCarrito=elementosCarr.getString("registros","");
        if(elemCarrito.equals("")){
            Toast.makeText(CarritoActivity.this, "No hay nada que enviar", Toast.LENGTH_SHORT).show();
        }else{
            Cursor res = dbSQLITE.selectVerTodos();
            String usuario="";
            int i=5;
            while (res.moveToNext()){
                usuario=res.getString(i);
            }
            return;

            /*
            StringRequest stringRequest=new StringRequest(Request.Method.POST, serverURL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map <String,String> params =new HashMap<String, String >();
                    params.put("username",txtCorreoLogin.getText().toString());
                    params.put("password",txtPasswordLogin.getText().toString());
                    return params;

                }
            };
            */
        }
    }
}
