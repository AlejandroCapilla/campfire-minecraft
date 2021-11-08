package com.example.chimeneaminecraft;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private ConstraintLayout background;
    private ImageView imvBackground, imageView;
    private SoundPool soundPool;
    private int idCrackle1, idCrackle2, idCrackle3,idCrackle4, idCrackle5, idCrackle6;
    private ToggleButton btnMute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        background = findViewById(R.id.background);
        imageView = findViewById(R.id.imvFogata);
        imvBackground = findViewById(R.id.imageViewBackground);
        imvBackground.setImageResource(R.drawable.background);
        imvBackground.setScaleType(ImageView.ScaleType.CENTER_CROP);
        btnMute = findViewById(R.id.btnMute);

        Glide.with(this).load(R.drawable.campfire).into(imageView);

        soundPool = new SoundPool(3, AudioManager.STREAM_MUSIC,0);
        idCrackle1 = soundPool.load(this, R.raw.crackle1,1);
        idCrackle2 = soundPool.load(this, R.raw.crackle2, 1);
        idCrackle3 = soundPool.load(this, R.raw.crackle3, 1);
        idCrackle4 = soundPool.load(this, R.raw.crackle4, 1);
        idCrackle5 = soundPool.load(this, R.raw.crackle5, 1);
        idCrackle6 = soundPool.load(this, R.raw.crackle6, 1);

        btnMute.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!btnMute.isChecked()) {
                    soundPool.play(idCrackle1,1,1,1,0,1);
                }else {
                    soundPool.autoPause();
                }
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
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
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );
    }
}