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

    private static int ileRazy = 0;
    private static int ilePalek = 0;
    private WebView web = null;
    private String chords[];
    private boolean isFirstTime = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);

        final ToggleButton onButton = (ToggleButton) findViewById(R.id.buttonSong);
        onButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                web = (WebView) findViewById(R.id.webSong);
                web.getSettings().setJavaScriptEnabled(true);
                EditText chordView = (EditText) findViewById(R.id.chordsSong);
                chords  = chordView.getText().toString().split(" ");

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
                                    isFirstTime = true;
                                    ileRazy = 0;
                                    ilePalek = 0;
                                } else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            String url = "<script async type=\"text/javascript\" src=\"https://www.scales-chords.com/api/scales-chords-api.js\"></script>";

                                            if(isFirstTime) {
                                                String query = "<ins class=\"scales_chords_api\" chord=" + chords[0] + "></ins>";
                                                String website = url.concat(query);
                                                web.loadDataWithBaseURL("https://www.scales-chords.com/api/", website, "text/html", "UTF-8", "");
                                                isFirstTime = false;
                                            }

                                            if (palka.getRotation() <= rotation) {
                                                palka.setRotation(palka.getRotation() + 30);
                                                mediaPlayer.start();
                                                counter++;
                                            } else {
                                                palka.setRotation(palka.getRotation() - 30);
                                                mediaPlayer.start();
                                                counter++;
                                            }


                                            if (ileRazy == tacts) {
                                                ilePalek++;
                                                if(ilePalek == chords.length)
                                                {
                                                    ilePalek = 0;
                                                }

                                                String query = "<ins class=\"scales_chords_api\" chord=" + chords[ilePalek] + "></ins>";
                                                String website = url.concat(query);
                                                web.loadDataWithBaseURL("https://www.scales-chords.com/api/", website, "text/html", "UTF-8", "");

                                                ileRazy = 0;
                                            } else {
                                                ileRazy++;
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
                    // Toggle disabled
                    web.setVisibility(View.INVISIBLE);
                }
            }
        });
    }
}
