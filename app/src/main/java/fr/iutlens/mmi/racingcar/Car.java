package fr.iutlens.mmi.racingcar;

import android.graphics.Canvas;

import fr.iutlens.mmi.racingcar.utils.SpriteSheet;

/**
 *
 *
 * Created by dubois on 27/12/2017.
 */

public class Car {

    private SpriteSheet sprite;

    float x,y,direction;
    float v,dd;
    public boolean win;
    public int nbPetales;

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
        canvas.rotate(direction);
        int model = 0;
/*        if (dd < -5) model = 1;
        if (dd > 5) model = 2;*/
        sprite.paint(canvas,model,-sprite.w/2 , -sprite.h/2);
        canvas.restore();
    }

    // Vérifier si la voiture peut avancer vers la direction souhaitée
    public void update(Track track) {
        direction += dd*v*sprite.h;
        float x1 = x+(float) (v * sprite.h * Math.cos(Math.toRadians(direction - 90)));
        float y1 = y+(float) (v * sprite.h * Math.sin(Math.toRadians(direction - 90)));
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

        this.v =  0.0010f;

        this.direction = 90+(float) Math.toDegrees( -Math.atan2(pitch,roll));
/*        pitch = rescale(pitch,90,15);
        roll = rescale(roll,90,15);

        this.v = (float) (pitch*0.00005);
        this.dd = 0;
        this.dd = (float) (roll/2);
*/
    }
}
