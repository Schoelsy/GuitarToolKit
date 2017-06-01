package com.example.schoe.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ToggleButton;

import java.util.Timer;
import java.util.TimerTask;

public class Metronome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metronome);

        final ToggleButton onButton = (ToggleButton) findViewById(R.id.bitsToggleButton);
        onButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    EditText bitsEdit = (EditText) findViewById(R.id.bitsText);
                    try {
                        final int bits = Integer.valueOf(bitsEdit.getText().toString());
                        final Timer timer = new Timer();
                        final ImageView palka = (ImageView) findViewById(R.id.palka);

                        final float rotation = palka.getRotation();
                        timer.scheduleAtFixedRate(new TimerTask() {
                            @Override
                            public void run() {
                            if (!onButton.isChecked()){
                                timer.cancel();
                                if (palka.getRotation() > rotation) {
                                    palka.setRotation(palka.getRotation() - 30);
                                }
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (palka.getRotation() <= rotation) {
                                            palka.setRotation(palka.getRotation() + 30);
                                        } else {
                                            palka.setRotation(palka.getRotation() - 30);
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
