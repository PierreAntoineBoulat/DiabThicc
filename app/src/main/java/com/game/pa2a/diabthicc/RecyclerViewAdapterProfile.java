package com.game.pa2a.diabthicc;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.game.pa2a.diabthicc.models.Person;
import com.game.pa2a.diabthicc.services.CurrentUserService;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.tweetcomposer.ComposerActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class RecyclerViewAdapterProfile extends RecyclerView.Adapter<RecyclerViewAdapterProfile.ViewHolder>{

    private ArrayList<com.game.pa2a.diabthicc.models.Person> mPersons;
    TextView dialog_name;
    ImageView diag_icon;
    ImageView dialog_tweet;
    private Bitmap image;

    Uri imageUri;

    private Context context;
    private Dialog mDialog;
    private Person currentUser;


    public RecyclerViewAdapterProfile(Context context, ArrayList<com.game.pa2a.diabthicc.models.Person> mPersons) {
        this.mPersons = mPersons;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_card_profile, viewGroup, false);

        mDialog = new Dialog(context);
        mDialog.setContentView(R.layout.fragement_profile);

        diag_icon = mDialog.findViewById(R.id.diag_icon);
        int resId = context.getResources().getIdentifier(
                mPersons.get(i).getIcon(),
                "drawable",
                context.getPackageName()
        );
        diag_icon.setImageResource(resId);
        dialog_tweet = mDialog.findViewById(R.id.btnTweetUser);
        dialog_name = mDialog.findViewById(R.id.fragProfileName);
        dialog_name.setText(mPersons.get(i).getName());

        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final Person item = mPersons.get(i);
        int resId = context.getResources().getIdentifier(
                item.getImage(),
                "drawable",
                context.getPackageName()
        );
        viewHolder.image.setImageResource(resId);
        viewHolder.name.setText(mPersons.get(i).getName());
        viewHolder.surname.setText(mPersons.get(i).getFirstName());
        viewHolder.type.setText(mPersons.get(i).getProfil().getName());
        viewHolder.layoutProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int resId = context.getResources().getIdentifier(
                        item.getIcon(),
                        "drawable",
                        context.getPackageName()
                );
                diag_icon.setImageResource(resId);
                dialog_name.setText(item.getName());
                mDialog.show();

                currentUser = CurrentUserService.currentUser;

                TwitterConfig config = new TwitterConfig.Builder(context)
                        .logger(new DefaultLogger(Log.DEBUG))
                        .twitterAuthConfig(new TwitterAuthConfig(context.getString(R.string.com_twitter_sdk_android_CONSUMER_KEY), context.getString(R.string.com_twitter_sdk_android_CONSUMER_SECRET)))
                        .debug(true)
                        .build();
                Twitter.initialize(config);

                dialog_tweet.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View view){

                        String txt = "L'utilisateur " + currentUser + " partage l'utilisateur: " + item.getName() + "\n-Profile: " + item.getProfil().getName();

                        final TwitterSession session = new TwitterSession(new TwitterAuthToken(context.getString(R.string.com_twitter_sdk_android_ACCESS_KEY), context.getString(R.string.com_twitter_sdk_android_ACCESS_SECRET)), 1125390652588593152L, "DiabThicc");
                        TwitterCore.getInstance().getSessionManager().setActiveSession(session);

                        final Intent intentTweet = new ComposerActivity.Builder(context)
                                .session(session)
                                .text("#DiabThicclUser\n" + txt)
                                //.image(Uri.fromFile())
                                .createIntent();
                        context.startActivity(intentTweet);
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPersons.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView name;
        TextView surname;
        TextView type;
        LinearLayout layoutProfile;

        public ViewHolder (View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageProfile);
            name = itemView.findViewById(R.id.nameProfile);
            surname = itemView.findViewById(R.id.surnameProfile);
            type = itemView.findViewById(R.id.typeProfile);
            layoutProfile = itemView.findViewById(R.id.layoutProfile);
        }

    }
}
