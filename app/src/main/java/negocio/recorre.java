package negocio;

import android.widget.EditText;

/**
 * Created by juanm on 05/08/2017.
 */

public class recorre {
    //Se usa para verificar la ausencia de espacios en blanco
    private EditText[] campos;
    public recorre(EditText[] camp) // constructor
    {
        campos=camp;

    }
    public final EditText[] getCampos()
    {
        return campos;
    }
    public final void setCampos(EditText[] datos)
    {
        campos=datos;
    }

}
