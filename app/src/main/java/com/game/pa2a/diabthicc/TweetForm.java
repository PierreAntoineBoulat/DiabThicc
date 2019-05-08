package com.game.pa2a.diabthicc;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.tweetcomposer.ComposerActivity;


public class TweetForm extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incident_form);

        final EditText editTitle = findViewById(R.id.editTextTweet);

        //a modifier
        final Button btn = findViewById(R.id.btnTweet);

        boolean fromTweet = getIntent().getBooleanExtra("fromTweet", false);
        System.out.println("From Tweet flux? " + fromTweet);
        if (fromTweet) {
            String tft = getIntent().getStringExtra("textFromTweet");
            editTitle.setText(tft.split("\n")[0]);
        }
                TwitterConfig config = new TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig(getString(R.string.com_twitter_sdk_android_CONSUMER_KEY), getString(R.string.com_twitter_sdk_android_CONSUMER_SECRET)))
                .debug(true)
                .build();
        Twitter.initialize(config);

        Log.i("sa", "salut");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){

                Log.i("sa", "salut");

                String txt = editTitle.getText().toString();

                final TwitterSession session = new TwitterSession(new TwitterAuthToken(getString(R.string.com_twitter_sdk_android_ACCESS_KEY), getString(R.string.com_twitter_sdk_android_ACCESS_SECRET)), 1125390652588593152L, "DiabThicc");
                TwitterCore.getInstance().getSessionManager().setActiveSession(session);

                final Intent intentTweet = new ComposerActivity.Builder(TweetForm.this)
                        .session(session)
                        .text("Salut:\n" + txt)
                        .createIntent();
                startActivity(intentTweet);
            }
        });
    }
}
