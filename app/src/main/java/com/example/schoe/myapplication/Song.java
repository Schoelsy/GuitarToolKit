package com.example.schoe.myapplication;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ToggleButton;

import java.util.Timer;
import java.util.TimerTask;

public class Song extends Activity {

    static int ileRazy = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);




        final ToggleButton onButton = (ToggleButton) findViewById(R.id.buttonSong);
        onButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                WebView web = (WebView) findViewById(R.id.webSong);
                web.getSettings().setJavaScriptEnabled(true);
                EditText chordView = (EditText) findViewById(R.id.chordsSong);
                String chords[] = chordView.getText().toString().split(" ");


                if (isChecked) {


                    web.setVisibility(View.VISIBLE);



                    EditText tactsEdit = (EditText) findViewById(R.id.tactsSong);
                    final MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.beat);
                    EditText bitsEdit = (EditText) findViewById(R.id.rythmSong);
                    try {


                        final int tacts = Integer.valueOf(tactsEdit.getText().toString());
                        final int bits = Integer.valueOf(bitsEdit.getText().toString());
                        final Timer timer = new Timer();
                        final ImageView palka = (ImageView) findViewById(R.id.palkaSong);
                        palka.setRotation(-15);
                        final float rotation = palka.getRotation();

                        timer.scheduleAtFixedRate(new TimerTask() {
                            int counter = 0;
                            @Override
                            public void run() {
                                if (!onButton.isChecked()) {
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
                                                counter++;
                                            } else {
                                                palka.setRotation(palka.getRotation() - 30);
                                                mediaPlayer.start();
                                                counter++;
                                            }
                                        }
                                    });
                                }
                                if (counter % tacts == 0){
                                    ileRazy++;
                                }
                            }
                        }, 0, 60000 / bits);

                        String chord;
                        //String urle = "<h1>Potato</h1>";
                        String url = "<script async type=\"text/javascript\" src=\"https://www.scales-chords.com/api/scales-chords-api.js\"></script>";

                        if(ileRazy <= tacts) {
                            chord = chords[ileRazy];
                        }
                        else {
                            ileRazy = 0;
                            chord = chords[ileRazy];
                        }
                        String query = "<ins class=\"scales_chords_api\" chord=" + chord + "></ins>";
                        String website = url.concat(query);
                        web.loadDataWithBaseURL("https://www.scales-chords.com/api/", website, "text/html", "UTF-8", "");
                    }
                    catch (NumberFormatException e) {
                        onButton.setChecked(false);
                    }
                }
                else {
                    // Toggle disabled
                    web.setVisibility(View.INVISIBLE);
                }
            }
        });
    }
}
