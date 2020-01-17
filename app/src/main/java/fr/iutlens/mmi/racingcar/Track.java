package fr.iutlens.mmi.racingcar;

import android.graphics.Canvas;
import android.util.Log;

import fr.iutlens.mmi.racingcar.utils.SpriteSheet;

/**
 * Created by dubois on 27/12/2017.
 */

public class Track {

    private int[][] data;
    private final String DIGITS ="012345";
    // 0123
    // 4567
    // 89AB
    // CDEF
    private final String[] def = {
            "111111111111111",
            "100000000000001",
            "100000000000001",
            "100000000000001",
            "100000000000001",
            "100000000000001",
            "111111111111111",
    };

    private final int char2hex(char c){
        return DIGITS.indexOf(c);
    }

    private SpriteSheet sprite;

    public Track(String[] data, int sprite_id){
        sprite = SpriteSheet.get(sprite_id);
        if (data == null) data = def;
        this.data = new int[data.length][data[0].length()];
        for(int i = 0; i < data.length ; ++i){
            for(int j= 0; j< data[i].length();++j){
                this.data[i][j] =char2hex(data[i].charAt(j));
            }
        }
    }

    public int get(int i, int j){
        return data[i][j];
    }

    public int getSizeY(){
        return data.length;
    }
    public int getSizeX(){
        return data[0].length;
    }

    public int getWidth(){
        return getSizeX()*sprite.w;
    }
    public int getHeight(){
        return getSizeY()*sprite.h;
    }

    public int getTileWidth(){
        return sprite.w;
    }
    public int getTileHeight(){
        return sprite.h;
    }

    public void paint(Canvas canvas){
        for(int i = 0; i < data.length ; ++i) {
            for (int j = 0; j < data[i].length; ++j) {
                sprite.paint(canvas, data[i][j], j * sprite.w, i * sprite.h);
            }
        }
    }

    public boolean isValid(float x1, float y1) {
        if (x1 <0 || x1 >= getSizeX()) return false;
        if (y1 <0 || y1 >= getSizeY()) return false;

        Log.d("isValid",x1+":"+y1);
        int x = (int) (x1);
        int y = (int) (y1);
        Log.d("isValid",x+":"+y+" "+data[y][x]);

        return  data[y][x] != 1;
    }
}
