package com.gurzelai.cr7siu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaParser;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    int numero;
    ImageView imagen;
    MediaPlayer mp;
    SwitchCompat sw;
    boolean loop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        numero = 1;
        imagen = findViewById(R.id.imagen);
        FloatingActionButton boton = findViewById(R.id.boton);
        boton.setOnClickListener(view -> recargar());
        mp = MediaPlayer.create(getApplicationContext(), R.raw.cr7);
        sw = findViewById(R.id.loop);
        sw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loop = !loop;
                mp.setLooping(loop);
                if(loop && !mp.isPlaying()){
                    mp.start();
                }
            }
        });
        recargar();
    }

    private void recargar() {
        imagen.setImageResource(getResources().getIdentifier("cr7" + numero++, "drawable", getPackageName()));
        if (!loop) {
            if (mp.isPlaying()) {
                mp = MediaPlayer.create(getApplicationContext(), R.raw.cr7);
            }
        }
        mp.start();
        if (numero == 19) numero = 1; // tenemos 18 imagenes asik poner 19
    }

    private void pantallaCompleta() {
        getSupportActionBar().hide();
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }

    @Override
    protected void onResume() {
        super.onResume();
        pantallaCompleta();
        recargar();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mp.isPlaying()) mp.pause();
    }
}