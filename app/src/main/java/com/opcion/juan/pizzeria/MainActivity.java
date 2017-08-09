package com.opcion.juan.pizzeria;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.HashMap;
import java.util.Map;

import negocio.recorrido;


public class MainActivity extends AppCompatActivity {
    //Esto es para validar los campos del Login
    DBusuarios dbSQLITE;

    EditText txtCorreoLogin,txtPasswordLogin;


    //Declaramos variables globales
    String nombre;
    String apelllido;
    String cedula;
    String  celular;
    String  correo;
    String  serverURL="http://dulceyfriopizzas.herokuapp.com/users/loginMovil";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbSQLITE = new DBusuarios(this);
        Cursor res = dbSQLITE.selectVerTodos();
        int valor=res.getCount();
        //Si existen datos de usuario guardados inicia automáticamente, caso contrario tendra que logearse
        if(valor>0){
            Intent principal = new Intent(getApplicationContext(),productosActivity.class );
            startActivity(principal);
        }
    }
    //Evita que se regrese a la pantalla principal
    @Override
    public void onBackPressed() {

    }
    //Abre las diferentes actividades del sistema
    public void abrirRecuperar(View v){
        Intent recuperar = new Intent(getApplicationContext(),recuperarActivity.class );
        startActivity(recuperar);
    }

    public void Registrarse(View v){
        Intent registro = new Intent(getApplicationContext(),registroActivity.class );
        startActivity(registro);
    }
    public void IniciarSesion(View v){
        txtCorreoLogin=(EditText)findViewById(R.id.txtCorreoLogin);
        txtPasswordLogin=(EditText)findViewById(R.id.txtPasswordLogin);
        EditText[] campos=new EditText[]{txtCorreoLogin,txtPasswordLogin};
        recorrido recor=new recorrido(campos);
        if(recor.Recorrer(campos)){
            StringRequest stringRequest=new StringRequest(Request.Method.POST, serverURL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String result) {
                            //Verifica que no exista algun error de red
                            if((result.toString()).equals("Error 1")){
                                Toast.makeText(MainActivity.this, "Error de red", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            //Verifica que no exista algun error por contraseña incorrecta
                            if((result.toString()).equals("Error 2")){
                                Toast.makeText(MainActivity.this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            //Verifica que no exista algun por que el usuario no existe
                            if((result.toString()).equals("Error 3")){
                                Toast.makeText(MainActivity.this, "usuario no existe", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            //Guarda los valores de datos del usuario en formato tipo JSON
                            JsonParser parser = new JsonParser();
                            JsonElement elementObject = parser.parse(result);
                            nombre = elementObject.getAsJsonObject().get("nombre").getAsString();
                            apelllido = elementObject.getAsJsonObject().get("apellido").getAsString();
                            cedula= elementObject.getAsJsonObject().get("cedula").getAsString();
                            celular=elementObject.getAsJsonObject().get("celular").getAsString();
                            correo=elementObject.getAsJsonObject().get("username").getAsString();
                            //Si no hay nada entonces muestra el mensaje
                            if(correo.isEmpty()){
                                Toast.makeText(MainActivity.this, "No hay datos", Toast.LENGTH_SHORT).show();
                            }else{
                                //Guarda la sesión del usuario
                                boolean estaInsertado =dbSQLITE.insertarUser(nombre,apelllido,cedula,celular,correo);
                                if(estaInsertado){
                                    //Una vez que inicia sesión sus datos persisstiran
                                    Toast.makeText(MainActivity.this,"Iniciando sesión",Toast.LENGTH_SHORT).show();
                                    Intent principal = new Intent(getApplicationContext(),productosActivity.class );
                                    startActivity(principal);
                                }
                                else{Toast.makeText(MainActivity.this,"Lo sentimos ocurrió un error",Toast.LENGTH_LONG).show();}
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //Si encuentra un error de envío lo muestra
                    Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    error.printStackTrace();
                }
            }
            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    //Estos son los datos que se envian del app al servidor
                    //Estos datos se comparan con los guardados en la BASE DE DATOS NO RELACIONAL MONGODB
                    Map <String,String> params =new HashMap<String, String >();
                    params.put("username",txtCorreoLogin.getText().toString());
                    params.put("password",txtPasswordLogin.getText().toString());
                    return params;
                }
            };
            singletonDatos.getInstancia(MainActivity.this).addToRequest(stringRequest);
        }else {
            //Si hay espacios en blanco muestra el error
            AlertDialog.Builder alertDialogBuilder;
            alertDialogBuilder=new AlertDialog.Builder(MainActivity.this);
            alertDialogBuilder.setTitle("Error");
            alertDialogBuilder.setMessage("Datos necesarios");
            AlertDialog alertDialog=alertDialogBuilder.create();
            alertDialog.show();
        }
    }
}
