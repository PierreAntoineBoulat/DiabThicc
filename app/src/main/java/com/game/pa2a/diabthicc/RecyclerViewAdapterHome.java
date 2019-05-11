package com.game.pa2a.diabthicc;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.game.pa2a.diabthicc.models.Meal;

import java.util.List;

public class RecyclerViewAdapterHome extends RecyclerView.Adapter<RecyclerViewAdapterHome.ViewHolderHome> {
    private List<Meal> lMeals;
    TextView dialog_name;
    TextView dialog_glu;
    TextView dialog_lip;
    TextView dialog_prot;
    ImageView dialog_icon;

    Dialog mDialog;
    Context context;

    public static class ViewHolderHome extends RecyclerView.ViewHolder {
        public TextView textView;
        private Dialog mDialog;
        public ViewHolderHome(View v) {
            super(v);
            textView = v.findViewById(R.id.textViewHomeMeal);
        }
    }

    public RecyclerViewAdapterHome(Context context, List<Meal> lMeals) {
        this.lMeals = lMeals;
        this.context = context;
    }

    @Override
    public RecyclerViewAdapterHome.ViewHolderHome onCreateViewHolder(@NonNull ViewGroup parent,
                                                                     int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_meal_home, parent, false);

        mDialog = new Dialog(context);
        mDialog.setContentView(R.layout.fragement_meal);

        dialog_name = mDialog.findViewById(R.id.fragProfileMeal);
        dialog_name.setText(lMeals.get(viewType).getName());
        dialog_icon = mDialog.findViewById(R.id.mealIcon);
        //int id = context.getResources().getIdentifier(context.getPackageName()+"drawable/" + mMeals.get(i).getIcon(), null, null);
        int resId = context.getResources().getIdentifier(
                lMeals.get(viewType).getIcon(),
                "drawable",
                context.getPackageName()
        );
        dialog_icon.setImageResource(resId);
        dialog_glu = mDialog.findViewById(R.id.mealGlu);
        dialog_glu.setText(Integer.toString(lMeals.get(viewType).getDiet().getCarbsIntake()));
        dialog_lip = mDialog.findViewById(R.id.mealLip);
        dialog_lip.setText(Integer.toString(lMeals.get(viewType).getDiet().getFatIntake()));
        dialog_prot = mDialog.findViewById(R.id.mealProt);
        dialog_prot.setText(Integer.toString(lMeals.get(viewType).getDiet().getProteinIntake()));

        ViewHolderHome vh = new ViewHolderHome(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderHome holder, int position) {
        final Meal item = lMeals.get(position);
        holder.textView.setText(item.getName());

        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO Add the listener to redirect on the meal's details
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
        return lMeals.size();
    }
}

