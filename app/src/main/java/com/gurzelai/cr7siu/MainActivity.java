package com.gurzelai.cr7siu;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

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
                if (loop && !mp.isPlaying()) {
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

        if (!mp.isPlaying()) {
            dialogo();
        }
    }

    private void dialogo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.titulo_dialogo);
        builder.setIcon(R.drawable.ic_baseline_error_24);
        builder.setMessage(R.string.mensaje_dialogo);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();
    }

    private void pantallaCompleta() {
        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }

    @Override
    public void finish() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            super.finishAndRemoveTask();
        } else {
            super.finish();
        }
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
        if (mp.isPlaying()) mp.pause();
    }
}