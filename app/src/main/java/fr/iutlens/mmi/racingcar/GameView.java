package fr.iutlens.mmi.racingcar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import java.util.Arrays;

import fr.iutlens.mmi.racingcar.utils.OrientationProxy;
import fr.iutlens.mmi.racingcar.utils.RefreshHandler;
import fr.iutlens.mmi.racingcar.utils.SpriteSheet;
import fr.iutlens.mmi.racingcar.utils.TimerAction;

public class GameView extends View implements TimerAction,  OrientationProxy.OrientationListener {
    private Track track;
    private Car car;
    private RefreshHandler timer;
    private Menu menu;

    public GameView(Context context) {
        super(context);
        init(null, 0);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public GameView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    /**
     * Initialisation de la vue
     *
     * Tous les constructeurs (au-dessus) renvoient ici.
     *
     * @param attrs
     * @param defStyle
     */
    private void init(AttributeSet attrs, int defStyle) {

        // Chargement des feuilles de sprites
        SpriteSheet.register(R.drawable.lake,3,2,this.getContext());
        SpriteSheet.register(R.drawable.fish,3,2,this.getContext());

        // Création des différents éléments à afficher dans la vue
        track = new Track(null,R.drawable.lake);
        car = new Car(R.drawable.fish,0,2,0);

        // Gestion du rafraichissement de la vue. La méthode update (juste en dessous)
        // sera appelée toutes les 30 ms
        timer = new RefreshHandler(this);

        // Un clic sur la vue lance (ou relance) l'animation
/*        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!timer.isRunning()) */
timer.scheduleRefresh(30);
/*            }
        });*/
    }

    /**
     * Mise à jour (faite toutes les 30 ms)
     */
    @Override
    public void update() {
        if (this.isShown()) { // Si la vue est visible
            if (!car.win) timer.scheduleRefresh(30); // programme le prochain rafraichissement
            int petale_avant = car.nbPetales;
            car.update(track); // mise à jour de la position de la voiture

            if (petale_avant != car.nbPetales){
                if (car.nbPetales == 1){
                    menu.findItem(R.id.action_petal1).setVisible(true);
                } else if (car.nbPetales == 2){
                    menu.findItem(R.id.action_petal2).setVisible(true);
                } else if (car.nbPetales == 3){
                    menu.findItem(R.id.action_petal3).setVisible(true);
                    track.set(29,11,3);
                    track.set(29,12,3);
                    track.set(29,13,3);


                }

            }
            if (car.win){
                Intent intent = new Intent(getContext(),SplitScreenActivity.class);
                getContext().startActivity(intent);
                ((Activity) getContext()).finish();
            }
            /*Intent intent = new Intent(this.getContext(),MainActivity.class);
            getContext().startActivity(intent);*/
            invalidate(); // demande à rafraichir la vue
        }
    }

    /**
     * Méthode appelée (automatiquement) pour afficher la vue
     * C'est là que l'on dessine le décor et les sprites
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // On met une couleur de fond
        canvas.drawColor(0xffcceeff);

        // On choisit la transformation à appliquer à la vue i.e. la position
        // de la "camera"
        setCamera(canvas);

        // Dessin des différents éléments
        track.paint(canvas);
        car.paint(canvas,track.getTileWidth(),track.getTileHeight());

    }

    private void setCamera(Canvas canvas) {

        // On calcul le facteur de zoom nécessaire pour afficher au moins 7 tiles
        // dans chaque direction
        float tiles_x = (1.0f*getWidth())/track.getTileWidth()/30;
        float tiles_y =  (1.0f*getHeight())/track.getTileHeight()/14;
        float min_tiles = Math.min(tiles_x,tiles_y);
        float scale = (min_tiles);

        // La suite de transfomations est à interpréter "à l'envers"

        // On termine par un centrage de l'origine (la voiture donc) dans la fenêtre
        //canvas.translate(getWidth()/2,getHeight()/2);

        // On tourne le tout dans le sens inverse à l'angle de la voiture par rapport à la pise
        // Du coup, la voiture sera toujours orientée pareil à l'écran, c'est le décor qui bougera
//        canvas.rotate(-car.direction);

        // On mets à l'échelle calculée au dessus
        canvas.scale(scale, scale);

        // On centre sur la position actuelle de la voiture (qui se retrouve en 0,0 )
        //canvas.translate(-car.x * track.getTileWidth(),-car.y *track.getTileHeight());
    }


    @Override
    public void onOrientationChanged(float[] angle, long stamp) {
        car.setCommand(Math.toDegrees(angle[1]),Math.toDegrees(angle[2]));

    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public Menu getMenu() {
        return menu;
    }
}
