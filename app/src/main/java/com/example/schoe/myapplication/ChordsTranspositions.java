package com.example.schoe.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
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

public class ChordsTranspositions extends Activity {

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

        final List<String> chordsListDurrs = new ArrayList<String>(){{
            add("C");
            add("C#");
            add("D");
            add("D#");
            add("E");
            add("F");
            add("F#");
            add("G");
            add("G#");
            add("A");
            add("B");
            add("H");
        }};

        final List<String> chordsListMolls = new ArrayList<String>(){{
            add("c");
            add("c#");
            add("d");
            add("d#");
            add("e");
            add("f");
            add("f#");
            add("g");
            add("g#");
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
                boolean durr = false;
               for (int i = 0; i < chords.length; i++) {
                 /*   for (int j = 0; j < chordsList.size(); j++){
                       if (chordsList.get(j) == chords[i]){
                           Log.d("Chord", chordsList.get(j));
                       }
                   }

                }
                    */for(String s : chordsListDurrs){
                        if (s.equals(chords[i])){
                            durr = true;
                        }
                       }

                       if(durr) {
                           int index = chordsListDurrs.indexOf(chords[i]) + Integer.parseInt(numberOfTranspositions.getText().toString());
                           newChords.append(chordsListDurrs.get(index % chordsListDurrs.size()) + " ");
                       } else {
                           int index = chordsListMolls.indexOf(chords[i]) + Integer.parseInt(numberOfTranspositions.getText().toString());
                           newChords.append(chordsListMolls.get(index % chordsListMolls.size()) + " ");
                       }
                       durr = false;
               }
            }
        });

        arrowDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newChords.setText("");
                String chords[] = originalChords.getText().toString().split(" ");
                boolean durr = false;
                for (int i = 0; i < chords.length; i++) {
                 /*   for (int j = 0; j < chordsList.size(); j++){
                       if (chordsList.get(j) == chords[i]){
                           Log.d("Chord", chordsList.get(j));
                       }
                   }

                }
                    */
                    for(String s : chordsListDurrs){
                        if (s.equals(chords[i])){
                            durr = true;
                        }
                    }

                    if (durr) {
                        int index = ((((chordsListDurrs.indexOf(chords[i]) - Integer.parseInt(numberOfTranspositions.getText().toString())) % chordsListDurrs.size()) + chordsListDurrs.size()) % chordsListDurrs.size());
                        newChords.append(chordsListDurrs.get(index % chordsListDurrs.size()) + " ");
                    } else {
                        int index = ((((chordsListMolls.indexOf(chords[i]) - Integer.parseInt(numberOfTranspositions.getText().toString())) % chordsListMolls.size()) + chordsListMolls.size()) % chordsListMolls.size());
                        newChords.append(chordsListMolls.get(index % chordsListMolls.size()) + " ");
                    }
                    durr = false;
                }
            }
        });
    }
}
