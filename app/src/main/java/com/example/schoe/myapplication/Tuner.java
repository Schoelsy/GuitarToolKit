package com.example.schoe.myapplication;

import android.app.Activity;
import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.sqrt;
import org.jtransforms.fft.DoubleFFT_1D;

public class Tuner extends Activity {


    protected TextView _percentField;
    protected InitTask _initTask;
    protected TextView _goodField;

    public AudioRecord audioRecord;
    public int mSamplesRead; //how many samples read
    public int buffersizebytes;
    public int channelConfiguration = AudioFormat.CHANNEL_IN_MONO;
    public int audioEncoding = AudioFormat.ENCODING_PCM_16BIT;
    public static short[] buffer; //+-32767
    public static final int SAMPPERSEC = 44100; //samp per sec 8000, 11025, 22050 44100 or 4800;
    public String string;

    public List<Double> fr = new ArrayList<Double>();

    @Override
    public void onCreate( Bundle savedInstanceState )
    {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_tuner );

        buffersizebytes = AudioRecord.getMinBufferSize(SAMPPERSEC,channelConfiguration,audioEncoding); //4096 on ion
        buffer = new short[buffersizebytes];
        audioRecord = new AudioRecord(android.media.MediaRecorder.AudioSource.MIC,SAMPPERSEC,channelConfiguration,audioEncoding,buffersizebytes); //constructor
        _percentField = ( TextView ) findViewById( R.id.freqTextID );
        _initTask = new InitTask();
        _initTask.execute( this );
        _goodField = (TextView) findViewById(R.id.goodFreqTextID);

        findViewById(R.id.e1Button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _goodField.setText("329.6");
                string = "String e1: ";
            }
        });

        findViewById(R.id.hButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _goodField.setText("246.9");
                string = "String h: ";
            }
        });

        findViewById(R.id.gButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _goodField.setText("196.0");
                string = "String g: ";
            }
        });

        findViewById(R.id.dButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _goodField.setText("146.8");
                string = "String d: ";
            }
        });

        findViewById(R.id.aButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _goodField.setText("110.0");
                string = "String a: ";
            }
        });

        findViewById(R.id.e2Button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _goodField.setText("82.4");
                string = "String e2: ";
            }
        });
    }

    protected class InitTask extends AsyncTask<Context, Double, String>
    {
        @Override
        protected String doInBackground( Context... params )
        {
            audioRecord.startRecording();
            long lastTime = System.currentTimeMillis();
            while( true )
            {
                try{
                    mSamplesRead = audioRecord.read(buffer, 0, buffersizebytes);

                    double dables[] = new double[mSamplesRead];
                    for (int f = 0; f < mSamplesRead; f++){
                        dables[f] = (double)buffer[f];
                    }

                    double magn[] = new double[mSamplesRead];

                    DoubleFFT_1D fft = new DoubleFFT_1D(dables.length-1);
                    fft.realForward(dables);


                    for (int i = 0 ; i < (mSamplesRead / 2) - 1 ; i++) {
                        double re = dables[2 * i];
                        double im = dables[2 * i + 1];
                        magn[i] = sqrt(re * re + im * im);
                    }

                    double max_magnitude = -99999999;
                    int max_index = -1;
                    for (int i = 0 ; i < (mSamplesRead / 2) - 1; i++){
                        if (magn[i] > max_magnitude){
                            max_magnitude = magn[i];
                            max_index = i;
                        }
                    }

                    double freq = max_index * SAMPPERSEC / mSamplesRead;

                    if(freq > 20 && freq < 400){
                        fr.add(freq);

                        if(fr.size() == 5 || System.currentTimeMillis() > lastTime + 1000){
                            double med=0;
                            for (int i = 0; i < 5; i++){
                                med = med + fr.get(i);
                            }
                            med = med / 5;
                            fr.clear();
                            publishProgress(med);
                            lastTime = System.currentTimeMillis();
                        }
                    }
                } catch( Exception e ){
                }
            }
        }


        @Override
        protected void onProgressUpdate(Double... calc){
            super.onProgressUpdate(calc);
            _percentField.setText( String.valueOf(calc[0]));
            TextView commandTextView = (TextView) findViewById(R.id.commandTextView);
            if(Double.valueOf(_percentField.getText().toString()) > Double.valueOf(_goodField.getText().toString()))
            {
                commandTextView.setText(string + "SLACK");
            }
            else if(Double.valueOf(_percentField.getText().toString()) < Double.valueOf(_goodField.getText().toString()))
            {
                commandTextView.setText(string + "PULL UP");
            }
            else
            {
                commandTextView.setText(string + "CORRECT");
            }
        }

        @Override
        protected void onPostExecute( String result )
        {
            super.onPostExecute(result);
        }


    }
}

