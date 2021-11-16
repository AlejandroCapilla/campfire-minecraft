package com.example.chimeneaminecraft;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    private ConstraintLayout background;
    private ImageView imvBackground, imageView;
    private SoundPool soundPool;
    private int idCrackle1, idCrackle2, idCrackle3,idCrackle4, idCrackle5, idCrackle6;
    private ImageButton btnMute, btnMusic;
    private byte contBackground = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        background = findViewById(R.id.background);
        imageView = findViewById(R.id.imvFogata);
        imvBackground = findViewById(R.id.imageViewBackground);
        imvBackground.setImageResource(R.drawable.background_1);
        imvBackground.setScaleType(ImageView.ScaleType.CENTER_CROP);
        btnMute = findViewById(R.id.btnMute);
        btnMusic = findViewById(R.id.btnMusic);

        btnMusic.setImageResource(R.drawable.ic_baseline_music_off_24);
        btnMusic.setTag("music_off");
        btnMute.setImageResource(R.drawable.ic_baseline_volume_off_24);
        btnMute.setTag("mute");

        Glide.with(this).load(R.drawable.campfire).into(imageView);
        imageView.setTag("campfire");

        soundPool = new SoundPool(3, AudioManager.STREAM_MUSIC,0);
        idCrackle1 = soundPool.load(this, R.raw.crackle1,1);
        idCrackle2 = soundPool.load(this, R.raw.crackle2, 1);
        idCrackle3 = soundPool.load(this, R.raw.crackle3, 1);
        idCrackle4 = soundPool.load(this, R.raw.crackle4, 1);
        idCrackle5 = soundPool.load(this, R.raw.crackle5, 1);
        idCrackle6 = soundPool.load(this, R.raw.crackle6, 1);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (!btnMute.getTag().equals("mute")) {
                        reproducirSonidoFogata();
                    } else {
                        soundPool.autoPause();
                    }
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException exception) {
                        exception.printStackTrace();
                    }
                }
            }
        }).start();

        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.calm2);

        btnMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btnMusic.getTag().equals("music_off")) {
                    btnMusic.setImageResource(R.drawable.ic_baseline_music_note_24);
                    btnMusic.setTag("music_on");
                    mediaPlayer.start();
                } else {
                    btnMusic.setImageResource(R.drawable.ic_baseline_music_off_24);
                    btnMusic.setTag("music_off");
                    mediaPlayer.pause();
                }
            }
        });

        btnMute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!btnMute.getTag().equals("mute")) {
                    btnMute.setImageResource(R.drawable.ic_baseline_volume_up_24);
                    btnMute.setTag("noMute");
                } else {
                    btnMute.setImageResource(R.drawable.ic_baseline_volume_off_24);
                    btnMute.setTag("mute");
                }
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException exception) {
                    exception.printStackTrace();
                }
            }
        });

        btnMute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btnMute.getTag().equals("mute")) {
                    btnMute.setImageResource(R.drawable.ic_baseline_volume_up_24);
                    btnMute.setTag("sound");
                } else {
                    btnMute.setImageResource(R.drawable.ic_baseline_volume_off_24);
                    btnMute.setTag("mute");
                }
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imageView.getTag().equals("campfire")) {
                    Glide.with(view).load(R.drawable.soul_campfire).into(imageView);
                    imageView.setTag("soul_campfire");
                } else {
                    Glide.with(view).load(R.drawable.campfire).into(imageView);
                    imageView.setTag("campfire");
                }
            }
        });

        //Cambia la imagen del background
        imvBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (contBackground == 1) {
                    imvBackground.setImageResource(R.drawable.background_2);
                    contBackground = 2;
                }else if (contBackground == 2) {
                    imvBackground.setImageResource(R.drawable.background_1);
                    contBackground = 1;
                }
            }
        });

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                View decoreView = getWindow().getDecorView();
                decoreView.setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_FULLSCREEN
                        |View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                );
            }
        }, 1000);
    }

    private void reproducirSonidoFogata() {
        byte sonidoAleatorio = (byte) ((Math.random() * 6)+1);
        int id;
        switch (sonidoAleatorio) {
            case 1:
               id = idCrackle1;
               break;
            case 2:
                id = idCrackle2;
                break;
            case 3:
                id = idCrackle3;
                break;
            case 4:
                id = idCrackle4;
                break;
            case 5:
                id = idCrackle5;
                break;
            case 6:
                id = idCrackle6;
                break;
            default:
                id = idCrackle1;
        }
        soundPool.play(id,0.8f,0.8f,1,0,1);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            esconderSystemUI();
        }
    }

    private void esconderSystemUI() {
        View decoreView = getWindow().getDecorView();
        decoreView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_FULLSCREEN
                |View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );
    }
}