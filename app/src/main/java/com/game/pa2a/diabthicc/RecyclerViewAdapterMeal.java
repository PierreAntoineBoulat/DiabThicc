package com.game.pa2a.diabthicc;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.game.pa2a.diabthicc.models.Meal;
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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapterMeal extends RecyclerView.Adapter<RecyclerViewAdapterMeal.ViewHolderMeal>{

    private List<Meal> mMeals;
    TextView dialog_name;
    TextView dialog_glu;
    TextView dialog_lip;
    TextView dialog_prot;
    ImageView dialog_icon;
    ImageView dialog_tweet;
    Button dialog_bntadd;
    Button dialog_bntback;

    private Context context;
    private Dialog mDialog;
    private Person currentUser;


    public RecyclerViewAdapterMeal(Context context, List<Meal> mMeals) {
        this.mMeals = mMeals;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderMeal onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_card_meal, viewGroup, false);
        final Meal m = mMeals.get(i);

        mDialog = new Dialog(context);
        mDialog.setContentView(R.layout.fragement_meal);

        dialog_name = mDialog.findViewById(R.id.fragProfileMeal);
        dialog_name.setText(mMeals.get(i).getName());
        dialog_icon = mDialog.findViewById(R.id.mealIcon);
        //int id = context.getResources().getIdentifier(context.getPackageName()+"drawable/" + mMeals.get(i).getIcon(), null, null);
        int resId = context.getResources().getIdentifier(
                m.getIcon(),
                "drawable",
                context.getPackageName()
        );

        dialog_icon.setImageResource(resId);
        dialog_tweet = mDialog.findViewById(R.id.btnTweetMeal);
        dialog_glu = mDialog.findViewById(R.id.mealGlu);
        dialog_glu.setText(Integer.toString(mMeals.get(i).getDiet().getCarbsIntake()));
        dialog_lip = mDialog.findViewById(R.id.mealLip);
        dialog_lip.setText(Integer.toString(mMeals.get(i).getDiet().getFatIntake()));
        dialog_prot = mDialog.findViewById(R.id.mealProt);
        dialog_prot.setText(Integer.toString(mMeals.get(i).getDiet().getProteinIntake()));
        dialog_bntadd = mDialog.findViewById(R.id.addMeal);
        dialog_bntadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Log.d("date meal", m.getConsommationDate().toString());

                Intent i = new Intent(context, AddMealActivity.class);
                i.putExtra("meal", m);
                context.startActivity(i);

                /*CharSequence text = "Repas ajouté au menu du jour";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();*/
            }
        });
        dialog_bntback = mDialog.findViewById(R.id.goBack);
        dialog_bntback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
            }
        });

        return new ViewHolderMeal(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull ViewHolderMeal viewHolder, int i) {
        final Meal item = mMeals.get(i);
        //int id = context.getResources().getIdentifier(context.getPackageName()+"drawable/" + item.getImage(), null, null);
        int resId = context.getResources().getIdentifier(
                item.getImage(),
                "drawable",
                context.getPackageName()
        );
        viewHolder.image.setImageResource(resId);
        viewHolder.name.setText(item.getName());
        viewHolder.type.setText(item.getType());
        viewHolder.layoutMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_name.setText(item.getName());
                dialog_glu.setText(Integer.toString(item.getDiet().getCarbsIntake()));
                dialog_lip.setText(Integer.toString(item.getDiet().getFatIntake()));
                dialog_prot.setText(Integer.toString(item.getDiet().getProteinIntake()));
                //int id = context.getResources().getIdentifier(context.getPackageName()+"drawable/" + item.getIcon(), null, null);
                final int resId = context.getResources().getIdentifier(
                        item.getIcon(),
                        "drawable",
                        context.getPackageName()
                );
                dialog_icon.setImageResource(resId);
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

                        String txt = currentUser + " partage un(e) " + item.getName() + "\n-Glucides: " + Integer.toString(item.getDiet().getCarbsIntake()) + "\n-Lipides: " + Integer.toString(item.getDiet().getCarbsIntake()) + "\n-Proteines: " + Integer.toString(item.getDiet().getProteinIntake());

                        final TwitterSession session = new TwitterSession(new TwitterAuthToken(context.getString(R.string.com_twitter_sdk_android_ACCESS_KEY), context.getString(R.string.com_twitter_sdk_android_ACCESS_SECRET)), 1125390652588593152L, "DiabThicc");
                        TwitterCore.getInstance().getSessionManager().setActiveSession(session);

                        final Intent intentTweet = new ComposerActivity.Builder(context)
                                .session(session)
                                .text("#DiabThiccMeal\n" + txt)
                                .createIntent();
                        context.startActivity(intentTweet);
                    }
                });

                dialog_bntadd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent i = new Intent(context, AddMealActivity.class);
                        i.putExtra("meal", item);
                        context.startActivity(i);

                        /*CharSequence text = "Repas ajouté au menu du jour";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();*/
                    }
                });
                dialog_bntback.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mDialog.dismiss();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMeals.size();
    }

    public class ViewHolderMeal extends RecyclerView.ViewHolder{
        ImageView image;
        TextView name;
        TextView type;
        LinearLayout layoutMeal;

        public ViewHolderMeal (View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageMeal);
            name = itemView.findViewById(R.id.nameMeal);
            type = itemView.findViewById(R.id.typeMeal);
            layoutMeal = itemView.findViewById(R.id.layoutMeal);
        }

    }
}
