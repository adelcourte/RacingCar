package fr.iutlens.mmi.racingcar;

import android.graphics.Canvas;

import fr.iutlens.mmi.racingcar.utils.SpriteSheet;

/**
 *
 *
 * Created by dubois on 27/12/2017.
 */

public class Car {

    public static final int ANGLE = 15;
    private SpriteSheet sprite;

    float x,y,direction;
    float v,dd;
    public boolean win;
    public int nbPetales;
    private int model;
    private int cpt;
    private float precdirection;
    private int ndx;
    private float direction_souhaitee;
    private float direction_actuelle;

    public Car(int sprite_id, float x, float y, float direction){
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.sprite = SpriteSheet.get(sprite_id);
        this.v = 0;
        this.dd = 0;
    }

    public void paint(Canvas canvas, int unit_x, int unit_y){
        canvas.save();
        canvas.translate(x*unit_x,y*unit_y);
        canvas.rotate(direction_actuelle);


        sprite.paint(canvas, ndx,-sprite.w/2 , -sprite.h/2);
        canvas.restore();
    }

/*
    public void angle (dd){
        if(dd < -180)
            dd = dd+180;
        else if(dd > 180)
            dd = dd - 180;

    }*/

    // Vérifier si la voiture peut avancer vers la direction souhaitée
    public void update(Track track) {
        cpt = (cpt+1)%6;
        dd = difference(direction_souhaitee, direction_actuelle);

        if (dd < ANGLE && dd >-ANGLE) direction_actuelle = direction_souhaitee;
        else if(dd < -ANGLE) direction_actuelle-=ANGLE;
        else if(dd > ANGLE) direction_actuelle+= ANGLE;

        //direction_actuelle = direction_souhaitee;
        //precdirection = direction_souhaitee;



        if (dd!= 0)if (dd < -5) ndx = 3;
        else if (dd > 5) ndx = 4;
        else ndx = model%3;

        if (cpt ==0) model = (model+1)%3;
      //  direction += dd*v*sprite.h;
        float x1 = x+(float) (v * sprite.h * Math.cos(Math.toRadians(direction_actuelle - 90)));
        float y1 = y+(float) (v * sprite.h * Math.sin(Math.toRadians(direction_actuelle - 90)));
        if (track.isValid(x1,y1)) {
            x = x1;
            y = y1;
        } else if (track.isValid(x1,y)){
            x= x1;
        } else if (track.isValid(x,y1)){
            y = y1;
        }



        if(track.isPetale(x,y)) {
            nbPetales = nbPetales + 1;
            track.set(x,y,0);
        }

        if(track.isTourbillon(x,y)) {
            x = 0;
            y = 2;
        }

        if (track.isWin(x,y)){
            win = true;
        }


    }

    private float difference(float cible, float valeur) {
        float result = cible - valeur;
        if (result<-180) result+= 360;
        else if (result>180) result-=360;

        return result;
    }

    public double bound(double value, double max){
        if (value >= max) value = max;
        if (value <= -max) value = -max;
        return value;
    }

    public double rescale(double value, double max, double bound){
        value = bound(value, bound);
        value = value * max/ bound;
        return value;
    }

    public void setCommand(double pitch, double roll) {

        this.v =  0.00020f;

        this.direction_souhaitee = 90+(float) Math.toDegrees( -Math.atan2(pitch,roll));
/*        pitch = rescale(pitch,90,15);
        roll = rescale(roll,90,15);

        this.v = (float) (pitch*0.00005);
        this.dd = 0;
        this.dd = (float) (roll/2);
*/
    }
}
