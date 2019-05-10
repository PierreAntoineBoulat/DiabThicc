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
import android.widget.LinearLayout;
import android.widget.TextView;
import com.game.pa2a.diabthicc.models.Meal;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapterMeal extends RecyclerView.Adapter<RecyclerViewAdapterMeal.ViewHolderMeal>{

    private List<Meal> mMeals;
    TextView dialog_name;
    TextView dialog_glu;
    TextView dialog_lip;
    TextView dialog_prot;
    ImageView dialog_icon;

    private Context context;
    private Dialog mDialog;


    public RecyclerViewAdapterMeal(Context context, List<Meal> mMeals) {
        this.mMeals = mMeals;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderMeal onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_card_meal, viewGroup, false);

        mDialog = new Dialog(context);
        mDialog.setContentView(R.layout.fragement_meal);

        dialog_name = mDialog.findViewById(R.id.fragProfileMeal);
        dialog_name.setText(mMeals.get(i).getName());
        dialog_icon = mDialog.findViewById(R.id.mealIcon);
        //int id = context.getResources().getIdentifier(context.getPackageName()+"drawable/" + mMeals.get(i).getIcon(), null, null);
        int resId = context.getResources().getIdentifier(
                mMeals.get(i).getIcon(),
                "drawable",
                context.getPackageName()
        );
        dialog_icon.setImageResource(resId);
        dialog_glu = mDialog.findViewById(R.id.mealGlu);
        dialog_glu.setText(Integer.toString(mMeals.get(i).getDiet().getCarbsIntake()));
        dialog_lip = mDialog.findViewById(R.id.mealLip);
        dialog_lip.setText(Integer.toString(mMeals.get(i).getDiet().getFatIntake()));
        dialog_prot = mDialog.findViewById(R.id.mealProt);
        dialog_prot.setText(Integer.toString(mMeals.get(i).getDiet().getProteinIntake()));

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
                int resId = context.getResources().getIdentifier(
                        item.getIcon(),
                        "drawable",
                        context.getPackageName()
                );
                dialog_icon.setImageResource(resId);
                mDialog.show();
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
