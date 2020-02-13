package fr.iutlens.mmi.goya;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import fr.iutlens.mmi.goya.utils.OrientationProxy;

public class MainActivity extends AppCompatActivity {

    private OrientationProxy proxy;
    private GameView game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // On récupère la vue du jeu
        game = findViewById(R.id.gameView);

        // On configure le jeu pour recevoir les changements d'orientation
        proxy = new OrientationProxy(this, game);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.objectif, menu);

        game.setMenu(menu);
        return true;
    }
    @Override
    protected void onResume() {
        super.onResume();
        proxy.resume(); // On relance l'accéléromètre
    }

    @Override
    protected void onPause() {
        super.onPause();
        proxy.pause(); // On mets en pause l'accéléromètre
    }

}
