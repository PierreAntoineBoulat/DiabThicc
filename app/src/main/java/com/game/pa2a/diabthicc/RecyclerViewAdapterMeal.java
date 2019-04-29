package com.game.pa2a.diabthicc;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerViewAdapterMeal extends RecyclerView.Adapter<RecyclerViewAdapterMeal.ViewHolderMeal>{

    private ArrayList<String> mMeals;
    TextView dialog_name;
    //private ArrayList<String> mImages = new ArrayList<>();
    private Context context;
    private Dialog mDialog;

//    public RecyclerViewAdapter(Context context, ArrayList<String> mMeals, ArrayList<String> mImages) {
//        this.mMeals = mMeals;
//        this.mImages = mImages;
//        this.context = context;
//    }

    public RecyclerViewAdapterMeal(Context context, ArrayList<String> mNames) {
        this.mMeals = mNames;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderMeal onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_card_meal, viewGroup, false);

        mDialog = new Dialog(context);
        mDialog.setContentView(R.layout.fragement_meal);

        dialog_name = mDialog.findViewById(R.id.fragProfileMeal);
        dialog_name.setText(mMeals.get(i));

        return new ViewHolderMeal(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull ViewHolderMeal viewHolder, int i) {
        final String item = mMeals.get(i);
        //viewHolder.image.setImageAlpha(R.drawable.ic_person_black_24dp);
        viewHolder.name.setText(mMeals.get(i));
        viewHolder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Bundle args = new Bundle();
//                //args.putParcelable("my_custom_object", myObject);
//                args.putString("name", item);
//                ProfileFragment profileFragment = ProfileFragment.newInstance(item);
//                profileFragment.setArguments(args);
//                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//                transaction.replace(R.id.fragProfileContainer, profileFragment);
//                transaction.commit();
                dialog_name.setText(item);
                mDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        //return mImages.size();
        return mMeals.size();
    }

    public class ViewHolderMeal extends RecyclerView.ViewHolder{
        ImageView image;
        TextView name;

        public ViewHolderMeal (View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageMeal);
            name = itemView.findViewById(R.id.nameMeal);
        }

    }
}
