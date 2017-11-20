package com.ocr.john.topquiz.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ocr.john.topquiz.R;
import com.ocr.john.topquiz.model.User;

/**
 * @author https://openclassrooms.com/courses/developpez-votre-premiere-application-android/gerez-les-elements-graphiques-dans-votre-activite
 */
public class MainActivity extends AppCompatActivity {

    // id of activity
    private static final int GAME_ACTIVITY_REQUEST_CODE = 42;

    private TextView mGreetingText;
    private EditText mNameInput;
    private Button mPlayButton;

    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mGreetingText = (TextView) findViewById(R.id.activity_main_greeting_txt);
        mNameInput = (EditText) findViewById(R.id.activity_main_name_input);
        mPlayButton = (Button) findViewById(R.id.activity_main_play_btn);

        // on désactive le bouton tant que le champ de texte n est pas saisi
        mPlayButton.setEnabled(false);

        // on instancie l utilisateur
        mUser = new User();

        // ajout classe anonyme à l'écoute du textfield :
        mNameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // This is where we'll check the user input
                // on re enable le bouton
                mPlayButton.setEnabled(s.toString().length() != 0);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // on applique un listener sur notre bouton
        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // The user just clicked
                // on démarre une nouvelle activity :
                Intent gameActivity = new Intent(MainActivity.this, GameActivity.class);

                // we call the gameActivity and expect a result
                //startActivity(gameActivity);
                startActivityForResult(gameActivity,GAME_ACTIVITY_REQUEST_CODE);

                // on reprend la valeur du champ prénom de l'utilisateur dans notre objet user
                mUser.setFirstName(mNameInput.getText().toString());
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (GAME_ACTIVITY_REQUEST_CODE == requestCode && RESULT_OK == resultCode) {
            // Fetch the score from the Intent
            int score = data.getIntExtra(GameActivity.BUNDLE_EXTRA_SCORE, 0);
        }
    }
}
