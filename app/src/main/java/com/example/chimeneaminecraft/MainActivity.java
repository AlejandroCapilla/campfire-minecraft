package com.example.chimeneaminecraft;

import androidx.appcompat.app.AppCompatActivity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    private ImageView imvBackground, imageView;
    private static SoundPool soundPool;
    private static MediaPlayer mediaPlayer;
    private ImageButton btnMute, btnMusic;
    private static int idCrackle1, idCrackle2, idCrackle3,idCrackle4, idCrackle5, idCrackle6;
    private byte contBackground = 1;
    private static String estadoMusica = "music_off";
    private static String estadoSonido = "mute";
    private static boolean banderaCrackle = true;
    private static boolean banderaCrackle2 = true;
    private static boolean banderaMusic = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        imageView = findViewById(R.id.imvFogata);
        imvBackground = findViewById(R.id.imageViewBackground);
        imvBackground.setImageResource(R.drawable.background_1);
        imvBackground.setScaleType(ImageView.ScaleType.CENTER_CROP);
        btnMute = findViewById(R.id.btnMute);
        btnMusic = findViewById(R.id.btnMusic);

        if (estadoMusica.equals("music_off")) {
            btnMusic.setImageResource(R.drawable.ic_baseline_music_off_24);
            btnMusic.setTag("music_off");
        } else {
            btnMusic.setImageResource(R.drawable.ic_baseline_music_note_24);
            btnMusic.setTag("music_on");
        }

        if (estadoSonido.equals("mute")) {
            btnMute.setImageResource(R.drawable.ic_baseline_volume_off_24);
            btnMute.setTag("mute");
        } else {
            btnMute.setImageResource(R.drawable.ic_baseline_volume_up_24);
            btnMute.setTag("sound");
        }

        Glide.with(this).load(R.drawable.campfire).into(imageView);
        imageView.setTag("campfire");

        soundPool = new SoundPool(3, AudioManager.STREAM_MUSIC,0);
        idCrackle1 = soundPool.load(this, R.raw.crackle1,1);
        idCrackle2 = soundPool.load(this, R.raw.crackle2, 1);
        idCrackle3 = soundPool.load(this, R.raw.crackle3, 1);
        idCrackle4 = soundPool.load(this, R.raw.crackle4, 1);
        idCrackle5 = soundPool.load(this, R.raw.crackle5, 1);
        idCrackle6 = soundPool.load(this, R.raw.crackle6, 1);

        btnMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btnMusic.getTag().equals("music_off")) {
                    btnMusic.setImageResource(R.drawable.ic_baseline_music_note_24);
                    estadoMusica = "music_on";
                    btnMusic.setTag(estadoMusica);
                    //Se crea un nuevo Thread para music
                    createMusicThread();
                } else {
                    btnMusic.setImageResource(R.drawable.ic_baseline_music_off_24);
                    estadoMusica = "music_off";
                    btnMusic.setTag(estadoMusica);
                    banderaMusic = false;
                    mediaPlayer.pause();
                }
            }
        });

        btnMute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btnMute.getTag().equals("mute")) {
                    btnMute.setImageResource(R.drawable.ic_baseline_volume_up_24);
                    estadoSonido = "sound";
                    btnMute.setTag(estadoSonido);
                    //Se crea un nuevo Thread para el crackle
                    createCrackleThread();
                    createCrackleThread2();
                } else {
                    btnMute.setImageResource(R.drawable.ic_baseline_volume_off_24);
                    estadoSonido = "mute";
                    btnMute.setTag(estadoSonido);
                    banderaCrackle = false;
                    banderaCrackle2 = false;
                    // Se pausa el soundPool
                    soundPool.autoPause();
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

    private void reproducirSonidoFogata(float volume, int priority) {
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
            default:
                id = idCrackle6;
        }
        soundPool.play(id,volume,volume,priority,0,1);
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

    private void createCrackleThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //El Thread acaba si el boton pasa a mute
                while (banderaCrackle) {
                    reproducirSonidoFogata(0.8f, 1);
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException exception) {
                        exception.printStackTrace();
                    }
                }
                banderaCrackle = true;
            }
        }).start();
    }

    private void createCrackleThread2() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //El Thread acaba si el boton pasa a mute
                while (banderaCrackle2) {
                    reproducirSonidoFogata(0.4f, 2);
                    try {
                        Thread.sleep(3600);
                    } catch (InterruptedException exception) {
                        exception.printStackTrace();
                    }
                }
                banderaCrackle2 = true;
            }
        }).start();
    }

    private void createMusicThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean aux = true;
                //El Thread acaba cuando music este en off
                while (banderaMusic) {
                    if (aux) {
                        mediaPlayer = randomMusic();
                        mediaPlayer.start();
                        aux = false;
                    } else if (!mediaPlayer.isPlaying()){
                        mediaPlayer = randomMusic();
                        mediaPlayer.start();
                    }
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException exception) {
                        exception.printStackTrace();
                    }
                }
                banderaMusic = true;
            }
        }).start();
    }

    private MediaPlayer randomMusic() {
        byte random = (byte) (Math.random()*6);
        MediaPlayer mediaPlayer;
        switch (random) {
            case 0:
                mediaPlayer = MediaPlayer.create(this, R.raw.calm1);
                break;
            case 1:
                mediaPlayer = MediaPlayer.create(this, R.raw.calm2);
                break;
            case 2:
                mediaPlayer = MediaPlayer.create(this, R.raw.calm3);
                break;
            case 3:
                mediaPlayer = MediaPlayer.create(this, R.raw.hal2);
                break;
            case 4:
                mediaPlayer = MediaPlayer.create(this, R.raw.piano1);
                break;
            default:
                mediaPlayer = MediaPlayer.create(this, R.raw.piano2);
        }
        return mediaPlayer;
    }
}