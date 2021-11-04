package com.example.chimeneaminecraft;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    ConstraintLayout background;
    ImageView imvBackground, imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        background = findViewById(R.id.background);
        imageView = findViewById(R.id.imvFogata);
        imvBackground = findViewById(R.id.imageViewBackground);

        imvBackground.setImageResource(R.drawable.background1);
        imvBackground.setScaleType(ImageView.ScaleType.CENTER_CROP);

        //background.setBackgroundResource(R.drawable.background1);

        Glide.with(this).load(R.drawable.campfire).into(imageView);
    }
}