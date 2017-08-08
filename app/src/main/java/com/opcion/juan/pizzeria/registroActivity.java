package com.opcion.juan.pizzeria;

import android.app.ProgressDialog;
import android.content.Intent;
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

public class registroActivity extends AppCompatActivity {
    EditText txtNombreRegistro,
            txtApellidoRegistro,
            txtCedulaRegistro,
            txtCelularRegistro,
            txtCorreoRegistro,
            txtPasswordRegistro,
            txtRepPassRegistro;
    String serverURL="http://dulceyfriopizzas.herokuapp.com/users/register";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
    }
    public void  registrarse(View v){
        ProgressDialog progressdialog=new ProgressDialog(registroActivity.this);
        progressdialog.show();
        txtNombreRegistro=(EditText)findViewById(R.id.txtNombreRegistro);
        txtApellidoRegistro=(EditText)findViewById(R.id.txtApellidoRegistro);
        txtCedulaRegistro=(EditText)findViewById(R.id.txtCedulaRegistro);
        txtCelularRegistro=(EditText)findViewById(R.id.txtCelularRegistro);
        txtCorreoRegistro=(EditText)findViewById(R.id.txtCorreoRegistro);
        txtPasswordRegistro=(EditText)findViewById(R.id.txtPasswordRegistro);
        txtRepPassRegistro=(EditText)findViewById(R.id.txtRepPassRegistro);
        EditText[] campos=new EditText[]{
            txtNombreRegistro,
            txtApellidoRegistro,
            txtCedulaRegistro,
            txtCelularRegistro,
            txtCorreoRegistro,
            txtPasswordRegistro,
            txtRepPassRegistro
        };
        recorrido recor=new recorrido(campos);
        if(recor.Recorrer(campos)){
            //Aqui empieza
            StringRequest stringRequest=new StringRequest(Request.Method.POST, serverURL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String result) {
                            //Verifica que no exista algun error de red
                            if((result.toString()).equals("Error 1")){
                                Toast.makeText(registroActivity.this, "Error de red", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            //Verifica que no exista algun error por contraseña incorrecta
                            if((result.toString()).equals("Error 2")){
                                Toast.makeText(registroActivity.this, "Contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            //Verifica que no exista algun por que el usuario no existe
                            if((result.toString()).equals("Error 3")){
                                Toast.makeText(registroActivity.this, "Ese correo ya esta siendo usado", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            Toast.makeText(registroActivity.this, "Regostrado con éxito", Toast.LENGTH_SHORT).show();
                            Intent login = new Intent(getApplicationContext(),MainActivity.class );
                            startActivity(login);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(registroActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    error.printStackTrace();
                }
            }
            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map <String,String> params =new HashMap<String, String >();
                    params.put("nombre",txtNombreRegistro.getText().toString());
                    params.put("apellido",txtApellidoRegistro.getText().toString());
                    params.put("cedula",txtCedulaRegistro.getText().toString());
                    params.put("celular",txtCelularRegistro.getText().toString());
                    params.put("username",txtCorreoRegistro.getText().toString());
                    params.put("password",txtPasswordRegistro.getText().toString());
                    params.put("reppass",txtRepPassRegistro.getText().toString());
                    return params;
                }
            };
            singletonDatos.getInstancia(registroActivity.this).addToRequest(stringRequest);
            ///Aqui termina
        }else {
            AlertDialog.Builder alertDialogBuilder;
            alertDialogBuilder=new AlertDialog.Builder(registroActivity.this);
            alertDialogBuilder.setTitle("Error");
            alertDialogBuilder.setMessage("Datos necesarios");
            AlertDialog alertDialog=alertDialogBuilder.create();
            alertDialog.show();
        }
        if (progressdialog != null) {progressdialog.dismiss();}
    }
}
