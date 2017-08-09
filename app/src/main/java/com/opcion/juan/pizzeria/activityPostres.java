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

public class activityPostres extends AppCompatActivity {
    ArrayList<String[]> elementosCarrito=new ArrayList<String[]>();
    Boolean abiertoya=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postres);
        try {
            SharedPreferences elementosCarr=getSharedPreferences("carrito",0);
            String elemCarrito=elementosCarr.getString("registros","");
            String[] items=elemCarrito.split(",");
            if(!elemCarrito.equals("")){
                for ( int i=0;i<items.length;i+=5){
                    elementosCarrito.add(new String[]{items[i], items[i+1], items[i+2], items[i+3], items[i+4]});
                }
            }
        }catch (Exception e){
            Toast.makeText(activityPostres.this, "Su dispositivo es incompatible", Toast.LENGTH_SHORT).show();
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
    public String[] registrarCarrito(int id, TextView nombre, Double precio, EditText cantidad){
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
            Toast.makeText(activityPostres.this, "Ingresado", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(activityPostres.this, "Su dispositivo es incompatible", Toast.LENGTH_SHORT).show();
        }

    }

    public void abrirCarrito(View v){
        Intent carrito = new Intent(getApplicationContext(),CarritoActivity.class );
        startActivity(carrito);
    }

    public void addCarrPBS(View v){
        TextView nombre=(TextView)findViewById(R.id.tPoBaSp);
        Double precio=4.00d;
        EditText cantidad=(EditText)findViewById(R.id.txtPBS);
        String[] aGuardar=registrarCarrito(elementosCarrito.size()+1, nombre, precio, cantidad);
        elementosCarrito.add(aGuardar);
        guardado(elementosCarrito);
    }
    public void addCarrPCF(View v){
        TextView nombre=(TextView)findViewById(R.id.tPoCoHF);
        Double precio=3.50d;
        EditText cantidad=(EditText)findViewById(R.id.txtPCF);
        String[] aGuardar=registrarCarrito(elementosCarrito.size()+1, nombre, precio, cantidad);
        elementosCarrito.add(aGuardar);
        guardado(elementosCarrito);
    }
    public void addCarrCHD(View v){
        TextView nombre=(TextView)findViewById(R.id.tPoCHD);
        Double precio=2.75d;
        EditText cantidad=(EditText)findViewById(R.id.txtCHD);
        String[] aGuardar=registrarCarrito(elementosCarrito.size()+1, nombre, precio, cantidad);
        elementosCarrito.add(aGuardar);
        guardado(elementosCarrito);
    }
    public void addCarrPHS(View v){
        TextView nombre=(TextView)findViewById(R.id.tPostHamSen);
        Double precio=1.50d;
        EditText cantidad=(EditText)findViewById(R.id.txtPHS);
        String[] aGuardar=registrarCarrito(elementosCarrito.size()+1, nombre, precio, cantidad);
        elementosCarrito.add(aGuardar);
        guardado(elementosCarrito);
    }
    public void addCarrPHE(View v){
        TextView nombre=(TextView)findViewById(R.id.tPostHamEsp);
        Double precio=2.50d;
        EditText cantidad=(EditText)findViewById(R.id.txtPHE);
        String[] aGuardar=registrarCarrito(elementosCarrito.size()+1, nombre, precio, cantidad);
        elementosCarrito.add(aGuardar);
        guardado(elementosCarrito);
    }
    public void addCarrPLH(View v){
        TextView nombre=(TextView)findViewById(R.id.tPoBaLH);
        Double precio=4.00d;
        EditText cantidad=(EditText)findViewById(R.id.txtPLH);
        String[] aGuardar=registrarCarrito(elementosCarrito.size()+1, nombre, precio, cantidad);
        elementosCarrito.add(aGuardar);
        guardado(elementosCarrito);
    }
    public void addCarrPSCH(View v){
        TextView nombre=(TextView)findViewById(R.id.tPoSCH);
        Double precio=2.75d;
        EditText cantidad=(EditText)findViewById(R.id.txtPSCH);
        String[] aGuardar=registrarCarrito(elementosCarrito.size()+1, nombre, precio, cantidad);
        elementosCarrito.add(aGuardar);
        guardado(elementosCarrito);
    }
    public void addCarrPSJ(View v){
        TextView nombre=(TextView)findViewById(R.id.tPoSJ);
        Double precio=2.50d;
        EditText cantidad=(EditText)findViewById(R.id.txtPSJ);
        String[] aGuardar=registrarCarrito(elementosCarrito.size()+1, nombre, precio, cantidad);
        elementosCarrito.add(aGuardar);
        guardado(elementosCarrito);
    }
    public void addCarrPSPV(View v){
        TextView nombre=(TextView)findViewById(R.id.tPostSPV);
        Double precio=3.50d;
        EditText cantidad=(EditText)findViewById(R.id.txtPSPV);
        String[] aGuardar=registrarCarrito(elementosCarrito.size()+1, nombre, precio, cantidad);
        elementosCarrito.add(aGuardar);
        guardado(elementosCarrito);
    }
    public void addCarrPSPLL(View v){
        TextView nombre=(TextView)findViewById(R.id.tPostSPLL);
        Double precio=2.75d;
        EditText cantidad=(EditText)findViewById(R.id.txtPSPLL);
        String[] aGuardar=registrarCarrito(elementosCarrito.size()+1, nombre, precio, cantidad);
        elementosCarrito.add(aGuardar);
        guardado(elementosCarrito);
    }
    public void buscarPostres(View v){
        Filtro busqueda=new Filtro();

        LinearLayout PostreBanSplit=(LinearLayout)findViewById(R.id.PostreBanSplit);
        LinearLayout PostreCoHeFre=(LinearLayout)findViewById(R.id.PostreCoHeFre);
        LinearLayout PostreCoHeDu=(LinearLayout)findViewById(R.id.PostreCoHeDu);
        LinearLayout PostreHambSen=(LinearLayout)findViewById(R.id.PostreHambSen);
        LinearLayout PostreHambEsp=(LinearLayout)findViewById(R.id.PostreHambEsp);

        LinearLayout PostreLitroH=(LinearLayout)findViewById(R.id.PostreLitroH);
        LinearLayout PostreSCH=(LinearLayout)findViewById(R.id.PostreSCH);
        LinearLayout PostreSJ=(LinearLayout)findViewById(R.id.PostreSJ);
        LinearLayout PostreSPV=(LinearLayout)findViewById(R.id.PostreSPV);
        LinearLayout PostreSPLL=(LinearLayout)findViewById(R.id.PostreSPLL);

        busqueda.ocultar(new LinearLayout[]{
                PostreBanSplit,
                PostreCoHeFre,
                PostreCoHeDu,
                PostreHambSen,
                PostreHambEsp,

                PostreLitroH,
                PostreSCH,
                PostreSJ,
                PostreSPV,
                PostreSPLL
        });

        String buscar=((EditText)findViewById(R.id.txtBuscarPostres)).getText().toString().toUpperCase();

        String postreBanSplit="postre banana split bananasplit $4.00 $4,00";
        String postreCoHeFre="postre copa helado fresa $3.50 $3,50";
        String postreCoHeDu="postre copa helado durazno $2.75 $2,75";
        String postreHambSen="hamburguesa sencilla $1.50 $1,50";
        String postreHambEsp="hamburguesa especial $2.50 $2,50";

        String postreLitroH="postre litro helado $4.00 $4,00";
        String postreSCH="sánduche sandwich sanduche de chancho $2.75 $2,75";
        String postreSJ="sánduche sandwich sanduche de jamón jamon $2.50 $2,50";
        String postreSPV="sánduche sandwich sanduche de pavo $3.50 $3,50";
        String postreSPLL="sánduche sandwich sanduche de pollo $2.75 $2,75";

        if(postreBanSplit.toUpperCase().indexOf(buscar)>-1){
            busqueda.mostrar(PostreBanSplit);
        }
        if(postreCoHeFre.toUpperCase().indexOf(buscar)>-1){
            busqueda.mostrar(PostreCoHeFre);
        }
        if (postreCoHeDu.toUpperCase().indexOf(buscar)>-1){
            busqueda.mostrar(PostreCoHeDu);
        }
        if (postreHambSen.toUpperCase().indexOf(buscar)>-1){
            busqueda.mostrar(PostreHambSen);
        }
        if (postreHambEsp.toUpperCase().indexOf(buscar)>-1){
            busqueda.mostrar(PostreHambEsp);
        }
        //Separacion

        if (postreLitroH.toUpperCase().indexOf(buscar)>-1){
            busqueda.mostrar(PostreLitroH);
        }
        if (postreSCH.toUpperCase().indexOf(buscar)>-1){
            busqueda.mostrar(PostreSCH);
        }
        //
        if (postreSJ.toUpperCase().indexOf(buscar)>-1){
            busqueda.mostrar(PostreSJ);
        }
        //
        if (postreSPV.toUpperCase().indexOf(buscar)>-1){
            busqueda.mostrar(PostreSPV);
        }
        if (postreSPLL.toUpperCase().indexOf(buscar)>-1){
            busqueda.mostrar(PostreSPLL);
        }
    }
    //Añadir________________________________________________________________________________________
    public void  addPBS(View v){
        EditText ETcantidad=(EditText)findViewById(R.id.txtPBS);
        ETcantidad.setText(controlCantidad.add(ETcantidad));
        return;
    }
    public  void addPCF(View v){
        EditText ETcantidad=(EditText)findViewById(R.id.txtPCF);
        ETcantidad.setText(controlCantidad.add(ETcantidad));
        return;
    }
    public void addCHD(View v){
        EditText ETcantidad=(EditText)findViewById(R.id.txtCHD);
        ETcantidad.setText(controlCantidad.add(ETcantidad));
        return;
    }
    public void addPHS(View v){
        EditText ETcantidad=(EditText)findViewById(R.id.txtPHS);
        ETcantidad.setText(controlCantidad.add(ETcantidad));
        return;
    }
    public void addPHE(View v){
        EditText ETcantidad=(EditText)findViewById(R.id.txtPHE);
        ETcantidad.setText(controlCantidad.add(ETcantidad));
        return;
    }
    public void addPLH(View v){
        EditText ETcantidad=(EditText)findViewById(R.id.txtPLH);
        ETcantidad.setText(controlCantidad.add(ETcantidad));
        return;
    }
    public void addPSCH(View v){
        EditText ETcantidad=(EditText)findViewById(R.id.txtPSCH);
        ETcantidad.setText(controlCantidad.add(ETcantidad));
        return;
    }
    public  void addPSJ(View v){
        EditText ETcantidad=(EditText)findViewById(R.id.txtPSJ);
        ETcantidad.setText(controlCantidad.add(ETcantidad));
        return;
    }
    public void addPSPV(View v){
        EditText ETcantidad=(EditText)findViewById(R.id.txtPSPV);
        ETcantidad.setText(controlCantidad.add(ETcantidad));
        return;
    }
    public void addPSPLL(View v){
        EditText ETcantidad=(EditText)findViewById(R.id.txtPSPLL);
        ETcantidad.setText(controlCantidad.add(ETcantidad));
        return;
    }

    //Quitar_______________________________________________________________________________________
    public void lessBPS(View v){
        EditText ETcantidad=(EditText)findViewById(R.id.txtPBS);
        ETcantidad.setText(controlCantidad.restar(ETcantidad));
        return;
    }
    public void lessPCF(View v){
        EditText ETcantidad=(EditText)findViewById(R.id.txtPCF);
        ETcantidad.setText(controlCantidad.restar(ETcantidad));
        return;
    }
    public void lessCHD(View v){
        EditText ETcantidad=(EditText)findViewById(R.id.txtCHD);
        ETcantidad.setText(controlCantidad.restar(ETcantidad));
        return;
    }
    public void lessPHS(View v){
        EditText ETcantidad=(EditText)findViewById(R.id.txtPHS);
        ETcantidad.setText(controlCantidad.restar(ETcantidad));
        return;
    }
    public void lessPHE(View v){
        EditText ETcantidad=(EditText)findViewById(R.id.txtPHE);
        ETcantidad.setText(controlCantidad.restar(ETcantidad));
        return;
    }
    public  void  lessPLH(View v){
        EditText ETcantidad=(EditText)findViewById(R.id.txtPLH);
        ETcantidad.setText(controlCantidad.restar(ETcantidad));
        return;
    }
    public void lessPSCH(View v){
        EditText ETcantidad=(EditText)findViewById(R.id.txtPSCH);
        ETcantidad.setText(controlCantidad.restar(ETcantidad));
        return;
    }
    public void lessPSJ(View v){
        EditText ETcantidad=(EditText)findViewById(R.id.txtPSJ);
        ETcantidad.setText(controlCantidad.restar(ETcantidad));
        return;
    }
    public void lessPSPV(View v){
        EditText ETcantidad=(EditText)findViewById(R.id.txtPSPV);
        ETcantidad.setText(controlCantidad.restar(ETcantidad));
        return;
    }
    public void lessPSPLL(View v){
        EditText ETcantidad=(EditText)findViewById(R.id.txtPSPLL);
        ETcantidad.setText(controlCantidad.restar(ETcantidad));
        return;
    }
}
