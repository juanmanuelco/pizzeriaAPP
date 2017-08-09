package negocio;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;

/**
 * Created by juanm on 06/08/2017.
 */

public class Filtro {
    //Muestra los layouts ocultos
    public void mostrar(LinearLayout layoutAnimado)
    {
        if (layoutAnimado.getVisibility() == View.GONE)
        {
            animar(true,layoutAnimado);
            layoutAnimado.setVisibility(View.VISIBLE);
        }
    }
    //Oculta los layouts mostrados
    public void ocultar(LinearLayout[] layoutAnimado)
    {
        for(int i=0;i<layoutAnimado.length;i++){
            if (layoutAnimado[i].getVisibility() == View.VISIBLE)
            {
                animar(false,layoutAnimado[i] );
                layoutAnimado[i].setVisibility(View.GONE);
            }
        }
    }
    //Anima el mostrar y ocultar de loslayouts
    private void animar(boolean mostrar, LinearLayout layoutAnimado)
    {
        AnimationSet set = new AnimationSet(true);
        Animation animation = null;
        if (mostrar)
        {
            //desde la esquina inferior derecha a la superior izquierda
            animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        }
        else
        {    //desde la esquina superior izquierda a la esquina inferior derecha
            animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 1.0f);
        }
        //duración en milisegundos
        animation.setDuration(500);
        set.addAnimation(animation);
        LayoutAnimationController controller = new LayoutAnimationController(set, 0.25f);

        layoutAnimado.setLayoutAnimation(controller);
        layoutAnimado.startAnimation(animation);
    }
}
