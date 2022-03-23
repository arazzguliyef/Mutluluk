package com.arazguliyef.mutluluk;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class GameOver extends AppCompatActivity {

    TextView tvPoints;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over);
        tvPoints = findViewById(R.id.tvPoints);
        boolean win = getIntent().getExtras().getBoolean("win");
        if(win){
            tvPoints.setTextColor(Color.GREEN);
            tvPoints.setText("Tebrikler Kazandın. :)");
        }else{
            tvPoints.setTextColor(Color.RED);
            tvPoints.setText("Üzülme tekrar dene :)");
        }
    }

    public void restart(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
