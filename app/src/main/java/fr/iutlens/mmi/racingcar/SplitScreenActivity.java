package fr.iutlens.mmi.racingcar;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SplitScreenActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_split_screen);
    }

    public void start(View view) {
        Intent intent = new Intent (this,MainActivity.class);
        startActivity(intent);
    }

    /*public void BagroundSound(View view){
        Intent intent = new Intent(MainActivity.this, BackgroundSound.class);

    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        Context context;
        MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.son);
        mediaPlayer.start();
        this.mediaPlayer = mediaPlayer;
    }*/


}
