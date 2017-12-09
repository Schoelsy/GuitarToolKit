package com.example.schoe.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import static android.media.AudioFormat.CHANNEL_IN_MONO;
import static android.media.AudioFormat.CHANNEL_IN_STEREO;
import static android.media.AudioFormat.ENCODING_PCM_16BIT;


public class MainActivity extends Activity {
    int blockSize = 256;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button tuner = (Button) findViewById(R.id.tunerButton);
        tuner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tunerIntent = new Intent(v.getContext(), Tuner.class);
                startActivity(tunerIntent);
            }
        });

        final Button chords = (Button) findViewById(R.id.Chords);
        chords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chordsIntent = new Intent(v.getContext(), Chords.class);
                startActivity(chordsIntent);
            }
        });

        final Button transposition = (Button) findViewById(R.id.chordsButton);
        transposition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent transpositionIntent = new Intent(v.getContext(), ChordsTranspositions.class);
                startActivity(transpositionIntent);
            }
        });

        final Button metronome = (Button) findViewById(R.id.metronomeButton);
        metronome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent metronomeIntent = new Intent(v.getContext(), Metronome.class);
                startActivity(metronomeIntent);
            }
        });

        final Button song = (Button) findViewById(R.id.playASongButton);
        song.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent playASong = new Intent(v.getContext(), Song.class);
                startActivity(playASong);
            }
        });

        final Button exit = (Button) findViewById(R.id.exitButton);
        exit.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
                System.exit(0);
            }
        });

       /* Button tuner = (Button) findViewById(R.id.tunerButton);
        tuner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean permitted = false;
        */
                /*if (ContextCompat.checkSelfPermission(v.getContext(),
                        Manifest.permission.RECORD_AUDIO)
                        != PackageManager.PERMISSION_GRANTED) {

                    if (ActivityCompat.shouldShowRequestPermissionRationale((Activity)v.getContext(),
                            Manifest.permission.RECORD_AUDIO)) {

                        // Show an explanation to the user *asynchronously* -- don't block
                        // this thread waiting for the user's response! After the user
                        // sees the explanation, try again to request the permission.

                    } else {
                        ActivityCompat.requestPermissions((Activity)v.getContext(),
                                new String[]{Manifest.permission.RECORD_AUDIO},
                                8);
                    }


                } else permitted = true;
                */
        /*
                if (permitted) {
                    blockSize = AudioRecord.getMinBufferSize(44100, CHANNEL_IN_MONO, ENCODING_PCM_16BIT);
                    final AudioRecord audioInput = new AudioRecord(MediaRecorder.AudioSource.DEFAULT, 44100, CHANNEL_IN_MONO, ENCODING_PCM_16BIT, blockSize);
                    audioInput.startRecording();


                    final short[] buffer = new short[blockSize];

                    while (true) {
                        audioInput.read(buffer, 0, blockSize);
                        Complex[] cinput = new Complex[buffer.length];

                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        for (int i = 0; i < buffer.length; i++)
                            cinput[i] = new Complex((double) buffer[i] / 32768.0, 0.0);

                        FastFourierTransform.fft(cinput);

                        double[] magnitude = new double[blockSize / 2];

                        for (int i = 0; i < (buffer.length / 2); i++) {
                            magnitude[i] = Math.sqrt(cinput[i].re * cinput[i].re + cinput[i].im * cinput[i].im);
                        }

                        double max_magnitude = -999999999;
                        int max_index = -1;
                        for (int i = 0; i < blockSize / 2; i++) {
                            if (magnitude[i] > max_magnitude) {
                                max_magnitude = magnitude[i];
                                max_index = i;
                            }
                        }

                        double freq = (double) max_index * (audioInput.getSampleRate() / blockSize);
                        Log.d("DZIALAJ: ", "Frequency = " + String.valueOf(freq));
                    }

                }

            }

        });*/
    }


    private static int[] mSampleRates = new int[]{8000, 11025, 16000, 22050, 44100};

    public AudioRecord findAudioRecord() {
        for (int rate : mSampleRates) {
            for (short audioFormat : new short[]{AudioFormat.ENCODING_PCM_16BIT}) {
                for (short channelConfig : new short[]{CHANNEL_IN_MONO, CHANNEL_IN_STEREO}) {
                    try {

                        int bufferSize = AudioRecord.getMinBufferSize(rate, channelConfig, audioFormat);

                        if (bufferSize != AudioRecord.ERROR_BAD_VALUE) {
                            // check if we can instantiate and have a success
                            AudioRecord recorder = new AudioRecord(MediaRecorder.AudioSource.MIC, rate, channelConfig, audioFormat, bufferSize);

                            if (recorder.getState() == AudioRecord.STATE_INITIALIZED)
                                return recorder;
                        }
                    } catch (Exception e) {

                    }
                }
            }
        }
        return null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 8: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    final AudioRecord audioInput = findAudioRecord();

                    audioInput.startRecording();

                    final short[] buffer = new short[blockSize];

                    while (true) {
                        audioInput.read(buffer, 0, blockSize);
                        Complex[] cinput = new Complex[buffer.length];

                        for (int i = 0; i < buffer.length; i++)
                            cinput[i] = new Complex((double)buffer[i], 0.0);

                        FastFourierTransform.fft(cinput);

                        double[] magnitude = new double[blockSize/2];

                        for (int i = 0 ; i < (buffer.length / 2); i++){
                            magnitude[i] = Math.sqrt(cinput[i].re*cinput[i].re+cinput[i].im*cinput[i].im);
                        }

                        double max_magnitude = -999999;
                        int max_index = -1;
                        for(int i = 0; i < blockSize / 2; i++) {
                            if (magnitude[i] > max_magnitude)
                            {
                                max_magnitude = magnitude[i];
                                max_index = i;
                            }
                        }

                        double freq = (max_index * audioInput.getSampleRate()) / blockSize;
                        Log.d("DZIALAJ: ", "Frequency = " + freq);
                    }
                }
                return;
            }
// others?
        }
    }
}