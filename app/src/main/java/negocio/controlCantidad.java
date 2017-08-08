package negocio;

import android.widget.EditText;

/**
 * Created by juanm on 06/08/2017.
 */

public class controlCantidad {
    public static String add(EditText ETcantidad){
        String cantidad=ETcantidad.getText().toString();
        int nuevaCantidad=Integer.parseInt(cantidad);
        if(nuevaCantidad<0){
            nuevaCantidad=-1;
        }
        nuevaCantidad++;
        return nuevaCantidad+"";
    }
    public static String restar(EditText ETcantidad){
        String cantidad=ETcantidad.getText().toString();
        int nuevaCantidad=Integer.parseInt(cantidad);
        if(nuevaCantidad>0){
            nuevaCantidad--;
        }
        return nuevaCantidad+"";
    }
}
