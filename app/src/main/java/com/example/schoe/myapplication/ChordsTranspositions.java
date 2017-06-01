package com.example.schoe.myapplication;

import android.app.AlertDialog;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static java.lang.StrictMath.abs;

public class ChordsTranspositions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chords_transpositions);

        final EditText originalChords = (EditText) findViewById(R.id.chodsEditTextID);
        final EditText numberOfTranspositions = (EditText) findViewById(R.id.transpositionNumber);
        final TextView newChords = (TextView) findViewById(R.id.newChordsView);
        final ImageView arrowUp = (ImageView) findViewById(R.id.arrowUp);
        final ImageView arrowDown = (ImageView) findViewById(R.id.arrowDown);
        final ImageButton helpButton = (ImageButton) findViewById(R.id.helpButtonID);

        final List<String> chordsList = new ArrayList<String>(){{
            add("C");
            add("Cis");
            add("D");
            add("Dis");
            add("E");
            add("F");
            add("Fis");
            add("G");
            add("Gis");
            add("A");
            add("B");
            add("H");
            add("c");
            add("cis");
            add("d");
            add("dis");
            add("e");
            add("f");
            add("fis");
            add("g");
            add("gis");
            add("a");
            add("b");
            add("h");
        }};

        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

                builder.setMessage(R.string.TranspositionsText)
                        .setTitle("How to use");

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


        arrowUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newChords.setText("");
                String chords[] = originalChords.getText().toString().split(" ");
               for (int i = 0; i < chords.length; i++) {
                 /*   for (int j = 0; j < chordsList.size(); j++){
                       if (chordsList.get(j) == chords[i]){
                           Log.d("Chord", chordsList.get(j));
                       }
                   }

                }
                    */

                   int index = chordsList.indexOf(chords[i]) + Integer.parseInt(numberOfTranspositions.getText().toString());
                       newChords.append(chordsList.get(index%chordsList.size()) + " ");

               }
            }
        });

        arrowDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newChords.setText("");
                String chords[] = originalChords.getText().toString().split(" ");
                for (int i = 0; i < chords.length; i++) {
                 /*   for (int j = 0; j < chordsList.size(); j++){
                       if (chordsList.get(j) == chords[i]){
                           Log.d("Chord", chordsList.get(j));
                       }
                   }

                }
                    */

                    int index = ((((chordsList.indexOf(chords[i]) - Integer.parseInt(numberOfTranspositions.getText().toString())) % chordsList.size())+chordsList.size())%chordsList.size());
                    newChords.append(chordsList.get(index%chordsList.size()) + " ");

                }
            }
        });
    }
}
