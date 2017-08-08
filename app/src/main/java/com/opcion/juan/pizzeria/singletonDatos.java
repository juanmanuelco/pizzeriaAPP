package com.opcion.juan.pizzeria;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by juanm on 06/08/2017.
 */

public class singletonDatos {
    private static singletonDatos instanciaSingleton;
    private RequestQueue requestDatos;
    private static Context mCtx;

    private  singletonDatos (Context context){
        mCtx=context;
        requestDatos=getRequestDatos();
    }

    public static synchronized singletonDatos getInstancia(Context context){
        if(instanciaSingleton==null){
            instanciaSingleton=new singletonDatos(context);
        }
        return instanciaSingleton;
    }

    public RequestQueue getRequestDatos(){
        if(requestDatos==null){
            requestDatos= Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return requestDatos;
    }
    public <T>void addToRequest(Request<T> request){
        requestDatos.add(request);
    }
}
