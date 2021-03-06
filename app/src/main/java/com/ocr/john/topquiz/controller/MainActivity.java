package com.ocr.john.topquiz.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ocr.john.topquiz.R;
import com.ocr.john.topquiz.model.User;

import java.io.IOException;
import java.io.InputStream;

// IOUtils library for input stream
import org.apache.commons.io.IOUtils;


/**
 * @author https://openclassrooms.com/courses/developpez-votre-premiere-application-android/gerez-les-elements-graphiques-dans-votre-activite
 */
public class MainActivity extends AppCompatActivity {

    // id of activity
    private static final int GAME_ACTIVITY_REQUEST_CODE = 42;

    // BUNDLES :
    public static final String BUNDLE_SCORE_BTN = "scoreButton";
    public static final String BUNDLE_LOG_INFO = "mainActivity";

    // constant to preserve constistancy
    public static final String PREF_KEY_FIRSTNAME = "Firstname";
    public static final String PREF_KEY_SCORE = "Score";

    private TextView mGreetingText;
    private EditText mNameInput;
    private Button mPlayButton;
    private Button mScoreButton;

    private TextView mInfoText;

    private User mUser;



    // to save user and score .
    private SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // title of actionbar :
        setTitle("Hello Guy !");
        Log.i(BUNDLE_LOG_INFO,"onCreate()");

        // initialize the file to save data
        mPreferences = getPreferences(MODE_PRIVATE);


        // on instancie l utilisateur
        mUser = new User();


        mGreetingText = (TextView) findViewById(R.id.activity_main_greeting_txt);
        mNameInput = (EditText) findViewById(R.id.activity_main_name_input);
        mPlayButton = (Button) findViewById(R.id.activity_main_play_btn);
        mScoreButton = (Button) findViewById(R.id.activity_main_score_btn);

        mInfoText = (TextView) findViewById(R.id.activity_main_info_text);

        // loading content from html resource into textview :
        final InputStream stream = getResources().openRawResource(R.raw.content);
        final String text;

        try {

            text = IOUtils.toString(stream);

        } catch (IOException e) {

            Log.i(BUNDLE_LOG_INFO,"Que pasa ?");
            throw new RuntimeException(e);
        }



        // on désactive le bouton tant que le champ de texte n est pas saisi
        mPlayButton.setEnabled(false);

        // display the html text :
        mInfoText.setText(Html.fromHtml(text));

        greetUser();



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

                // on reprend la valeur du champ prénom de l'utilisateur dans notre objet user
                mUser.setFirstName(mNameInput.getText().toString());

                // we give the file the entry to save .
                mPreferences.edit().putString(PREF_KEY_FIRSTNAME,mUser.getFirstName()).apply();

                // on démarre une nouvelle activity :
                Intent gameActivity = new Intent(MainActivity.this, GameActivity.class);

                // we call the gameActivity and expect a result
                //startActivity(gameActivity);
                startActivityForResult(gameActivity,GAME_ACTIVITY_REQUEST_CODE);



            }
        });

        mScoreButton.setOnClickListener(new View.OnClickListener() {
        @Override
            public void onClick(View v) {

            // The user clicked on the score button :
            Log.i(BUNDLE_SCORE_BTN,"Clicked on Score BTN");
            Intent historicActivity = new Intent(MainActivity.this, HistoricActivity.class);

            startActivity(historicActivity);
        }

        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (GAME_ACTIVITY_REQUEST_CODE == requestCode && RESULT_OK == resultCode) {
            // Fetch the score from the Intent
            int score = data.getIntExtra(GameActivity.BUNDLE_EXTRA_SCORE, 0);

            // we save the integer of score into the file
            mPreferences.edit().putInt(PREF_KEY_SCORE,score).apply();

            greetUser();
        }
    }

    private void greetUser() {

        // Let's retrieve saved data if any :

        String firstname = mPreferences.getString(PREF_KEY_FIRSTNAME, null);

        if (null != firstname) {
            int score = mPreferences.getInt(PREF_KEY_SCORE, 0);

            // We put some html :
            String fulltext = "Welcome back, " + firstname
                    + "!\n<b>Your last score was " + score
                    + "</b>, will you do better this time?";

            mGreetingText.setText(Html.fromHtml(fulltext));
            mNameInput.setText(firstname);
            mNameInput.setSelection(firstname.length());
            mPlayButton.setEnabled(true);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.i(BUNDLE_LOG_INFO,":onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.i(BUNDLE_LOG_INFO,"onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.i(BUNDLE_LOG_INFO,"onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.i(BUNDLE_LOG_INFO,"onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.i(BUNDLE_LOG_INFO,"onDestroy()");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {

            case R.id.action_add:
                Log.i(BUNDLE_LOG_INFO,"Clicked on menu ADD");
                return launchListActivity();
            case R.id.action_edit:
                Log.i(BUNDLE_LOG_INFO,"Clicked on menu EDIT");
                return true;
        }


        return super.onOptionsItemSelected(item);
    }


    private boolean launchListActivity () {

        Intent listActivity = new Intent(MainActivity.this, ListActivity.class);

        startActivity(listActivity);

        return true;

    }
}
