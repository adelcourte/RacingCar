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
            "555555555555555555555555555555",
            "000000050000005000050000500005",
            "000000050055505000000050500005",
            "555500250054005005555550005005",
            "520000050255555005000000005005",
            "500000050000000005000005555005",
            "500555550000000005055505000055",
            "500000000050055555000525000005",
            "500000000050000000000505020505",
            "555555555555555555555500000005",
            "540005000000000005000500555005",
            "500205000555500205000550005003",
            "500000000000500000000000025403",
            "555555555555555555555555555003",
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

        return  data[y][x] != 5;
    }

    public boolean isWin(float x, float y) {
        return false;
    }

    public boolean isTourbillon(float x, float y) {
        return data[(int) y][(int) x] == 2;
    }

    public boolean isPetale(float x, float y) {
        return data[(int) y][(int) x] == 4;

    };


    public void set(float x, float y, int type) {
        data[(int) y][(int) x] = type;
    }
}
