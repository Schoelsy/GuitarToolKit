package com.example.schoe.myapplication;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ToggleButton;

import java.util.Timer;
import java.util.TimerTask;

public class Metronome extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metronome);

        final ToggleButton onButton = (ToggleButton) findViewById(R.id.bitsToggleButton);
        onButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    final MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.beat);
                    EditText bitsEdit = (EditText) findViewById(R.id.bitsText);
                    try {
                        final int bits = Integer.valueOf(bitsEdit.getText().toString());
                        final Timer timer = new Timer();
                        final ImageView palka = (ImageView) findViewById(R.id.palka);
                        palka.setRotation(-15);

                        final float rotation = palka.getRotation();
                        timer.scheduleAtFixedRate(new TimerTask() {
                            @Override
                            public void run() {
                            if (!onButton.isChecked()){
                                timer.cancel();
                                if (palka.getRotation() > rotation) {
                                    palka.setRotation(palka.getRotation() - 30);
                                    mediaPlayer.start();
                                }
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (palka.getRotation() <= rotation) {
                                            palka.setRotation(palka.getRotation() + 30);
                                            mediaPlayer.start();
                                        } else {
                                            palka.setRotation(palka.getRotation() - 30);
                                            mediaPlayer.start();
                                        }
                                    }
                                });
                            }

                            }
                        }, 0, 60000 / bits);
                    } catch (NumberFormatException e) {
                        onButton.setChecked(false);
                    }
                } else {
                    // The toggle is disabled
                }
            }
        });
    }
}
