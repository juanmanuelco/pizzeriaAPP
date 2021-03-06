package com.opcion.juan.pizzeria;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import negocio.Filtro;
import negocio.controlCantidad;

public class activityBebidas extends AppCompatActivity {
    //Este es un semaforo
    Boolean abiertoya=false;
    //Aqui se guardarán los datos del carrito
    ArrayList<String[]> elementosCarrito=new ArrayList<String[]>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bebidas);
        try{
            //Obtiene la información del carrito y la guarda en el arraylist
            SharedPreferences elementosCarr=getSharedPreferences("carrito",0);
            String elemCarrito=elementosCarr.getString("registros","");
            String[] items=elemCarrito.split(",");
            //Si es que hay datos los asigna
            if(!elemCarrito.equals("")){
                for ( int i=0;i<items.length;i+=5){
                    elementosCarrito.add(new String[]{items[i], items[i+1], items[i+2], items[i+3], items[i+4]});
                }
            }
        }catch (Exception e){
            Toast.makeText(activityBebidas.this, "Su dispositivo no es compatible", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //El semáforo indica si se debe reiiciar o no el app cuando entra en estado de superposicion
        if(abiertoya){
            Intent mIntent = getIntent();
            finish();
            startActivity(mIntent);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Cambia el estado del semaforlo
        abiertoya=true;
    }

    public String[] registrarCarrito(int id, TextView nombre, Double precio, EditText cantidad){
        //Convierte los datos de las bebidas en un array de datos
        String Nombre=nombre.getText().toString();
        String Cantidad=cantidad.getText().toString();
        String subtotal=Double.toString(precio* (Integer.parseInt(Cantidad)));
        return new String[]{Integer.toString(id), Nombre, Double.toString(precio), Cantidad, subtotal};
    }
    public void guardado(ArrayList<String[]>elementosCarrito ){
        try{
            //Genera un stringBuilder otorgando los datos del carrito
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
            //Ahora guarda los datos como fijos para poder acceder a ellos desde cualquier otra pantalla
            SharedPreferences elementosCarr=getSharedPreferences("carrito",0);
            SharedPreferences.Editor editor=elementosCarr.edit();
            editor.putString("registros",stringBuilder.toString());
            editor.commit();
            //Muestra los mensajes
            Toast.makeText(activityBebidas.this, "Ingresado", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(activityBebidas.this, "Su dispositivo es incompatible", Toast.LENGTH_SHORT).show();
        }
    }
    //Muestra el carrito
    public void abrirCarrito(View v){
        Intent carrito = new Intent(getApplicationContext(),CarritoActivity.class );
        startActivity(carrito);
    }
    //Permite ir agregando valores al carrito
    public void addCarrBCG(View v){
        TextView nombre=(TextView)findViewById(R.id.tBeCoG);
        Double precio=3.00d;
        EditText cantidad=(EditText)findViewById(R.id.txtBCG);
        String[] aGuardar=registrarCarrito(elementosCarrito.size()+1, nombre, precio, cantidad);
        elementosCarrito.add(aGuardar);
        guardado(elementosCarrito);
    }
    public void addCarrBCP(View v){
        TextView nombre=(TextView)findViewById(R.id.tBeCoP);
        Double precio=0.70d;
        EditText cantidad=(EditText)findViewById(R.id.txtBCP);
        String[] aGuardar=registrarCarrito(elementosCarrito.size()+1, nombre, precio, cantidad);
        elementosCarrito.add(aGuardar);
        guardado(elementosCarrito);
    }
    public void addCarrBIP(View v){
        TextView nombre=(TextView)findViewById(R.id.tInkaPer);
        Double precio=0.50d;
        EditText cantidad=(EditText)findViewById(R.id.txtBIP);
        String[] aGuardar=registrarCarrito(elementosCarrito.size()+1, nombre, precio, cantidad);
        elementosCarrito.add(aGuardar);
        guardado(elementosCarrito);
    }
    public void addCarrBPL (View v){
        TextView nombre=(TextView)findViewById(R.id.tPepsiLata);
        Double precio=0.50d;
        EditText cantidad=(EditText)findViewById(R.id.txtBPL);
        String[] aGuardar=registrarCarrito(elementosCarrito.size()+1, nombre, precio, cantidad);
        elementosCarrito.add(aGuardar);
        guardado(elementosCarrito);
    }
    public void addCarrBTL(View v){
        TextView nombre=(TextView)findViewById(R.id.tTropLit);
        Double precio=1.10d;
        EditText cantidad=(EditText)findViewById(R.id.txtBTL);
        String[] aGuardar=registrarCarrito(elementosCarrito.size()+1, nombre, precio, cantidad);
        elementosCarrito.add(aGuardar);
        guardado(elementosCarrito);
    }
    //Con esto el buscador puede funcionar
    public void buscarBebidas(View v){
        //Creo una variable de tipo FIltro (ver paquete Negocio)
        Filtro busqueda=new Filtro();
        //Obytiene los datosde las bebidas en la interfaz
        LinearLayout BebidaCocaColaGrande=(LinearLayout)findViewById(R.id.BebidaCocaColaGrande);
        LinearLayout BebidaCocaColaPer=(LinearLayout)findViewById(R.id.BebidaCocaColaPer);
        LinearLayout BebidaInkaPersonal=(LinearLayout)findViewById(R.id.BebidaInkaPersonal);
        LinearLayout BebidaPepsiLata=(LinearLayout)findViewById(R.id.BebidaPepsiLata);
        LinearLayout BebidaTropicalLitro=(LinearLayout)findViewById(R.id.BebidaTropicalLitro);
        //Oculta todos los productos
        busqueda.ocultar(new LinearLayout[]{
                BebidaCocaColaGrande,
                BebidaCocaColaPer,
                BebidaInkaPersonal,
                BebidaPepsiLata,
                BebidaTropicalLitro
        });
        //Obtiene el parámetro de búsqueda
        String buscar=((EditText)findViewById(R.id.txtBuscarBebidas)).getText().toString().toUpperCase();
        //Estas son las cadenas que analizará el buscador
        String bebidaCocaColaGrande="bebida coca cola cocacola grande $3.00 $3,00  ";
        String bebidaCocaColaPer="bebida coca cola cocacola personal $0.70 $0,70  ";
        String bebidaInkaPersonal="bebida inka cola inkacola personal $0.50 $0,50";
        String bebidaPepsiLata="bebida pepsicola cola pepsi  lata $0.50 $0,50";
        String bebidaTropicalLitro="bebida tropical litro $1.10 $1,10";
        //Mostrará aquellas que coincidan
        if(bebidaCocaColaGrande.toUpperCase().indexOf(buscar)>-1){
            busqueda.mostrar(BebidaCocaColaGrande);
        }
        if(bebidaCocaColaPer.toUpperCase().indexOf(buscar)>-1){
            busqueda.mostrar(BebidaCocaColaPer);
        }
        if (bebidaInkaPersonal.toUpperCase().indexOf(buscar)>-1){
            busqueda.mostrar(BebidaInkaPersonal);
        }
        if (bebidaPepsiLata.toUpperCase().indexOf(buscar)>-1){
            busqueda.mostrar(BebidaPepsiLata);
        }
        if (bebidaTropicalLitro.toUpperCase().indexOf(buscar)>-1){
            busqueda.mostrar(BebidaTropicalLitro);
        }
    }
    //Estos  métodosincrementarán la cantidad a ingresar en el carrito
    //Añadir________________________________________________________________________________________
    public void  addBCG(View v){
        EditText ETcantidad=(EditText)findViewById(R.id.txtBCG);
        ETcantidad.setText(controlCantidad.add(ETcantidad));
        return;
    }
    public void addBCP(View v){
        EditText ETcantidad=(EditText)findViewById(R.id.txtBCP);
        ETcantidad.setText(controlCantidad.add(ETcantidad));
        return;
    }
    public void addBIP(View v){
        EditText ETcantidad=(EditText)findViewById(R.id.txtBIP);
        ETcantidad.setText(controlCantidad.add(ETcantidad));
        return;
    }
    public void addBPL(View v){
        EditText ETcantidad=(EditText)findViewById(R.id.txtBPL);
        ETcantidad.setText(controlCantidad.add(ETcantidad));
        return;
    }
    public void addBTL(View v){
        EditText ETcantidad=(EditText)findViewById(R.id.txtBTL);
        ETcantidad.setText(controlCantidad.add(ETcantidad));
        return;
    }
    //Estos métodos quitarán la existencia de los productos para llenar el carrito
    //Quitar________________________________________________________________________________________
    public void lessBCG(View v){
        EditText ETcantidad=(EditText)findViewById(R.id.txtBCG);
        ETcantidad.setText(controlCantidad.restar(ETcantidad));
        return;
    }

    public void lessBCP(View v){
        EditText ETcantidad=(EditText)findViewById(R.id.txtBCP);
        ETcantidad.setText(controlCantidad.restar(ETcantidad));
        return;
    }
    public void lessBIP(View v){
        EditText ETcantidad=(EditText)findViewById(R.id.txtBIP);
        ETcantidad.setText(controlCantidad.restar(ETcantidad));
        return;
    }
    public void lessBPL(View v){
        EditText ETcantidad=(EditText)findViewById(R.id.txtBPL);
        ETcantidad.setText(controlCantidad.restar(ETcantidad));
        return;
    }
    public void lessBTL(View v){
        EditText ETcantidad=(EditText)findViewById(R.id.txtBTL);
        ETcantidad.setText(controlCantidad.restar(ETcantidad));
        return;
    }
}
