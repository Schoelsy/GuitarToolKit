package com.example.schoe.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

public class Chords extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chords);


        Button chordButton = (Button) findViewById(R.id.ChordButtonSearch);



        chordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                WebView web = (WebView) findViewById(R.id.webViewID);
                web.getSettings().setJavaScriptEnabled(true);
                EditText chordView = (EditText) findViewById(R.id.chordsTextViewID);
                String chord = chordView.getText().toString();
                String urle = "<h1>Potato</h1>";
                String url = "<script async type=\"text/javascript\" src=\"https://www.scales-chords.com/api/scales-chords-api.js\"></script>";
                String query = "<ins class=\"scales_chords_api\" chord=" + chord + "></ins>";
                String website = url.concat(query);

                //web.loadUrl("http://www.gotyx.pl/");
                web.loadDataWithBaseURL("https://www.scales-chords.com/api/", website, "text/html", "UTF-8","");
                //web.reload();
            }
        });
   }
}
