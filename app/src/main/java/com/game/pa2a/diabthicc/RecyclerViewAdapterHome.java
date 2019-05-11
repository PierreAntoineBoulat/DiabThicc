package com.game.pa2a.diabthicc;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.game.pa2a.diabthicc.models.Meal;

import java.util.List;

public class RecyclerViewAdapterHome extends RecyclerView.Adapter<RecyclerViewAdapterHome.ViewHolderHome> {
    private List<Meal> lMeals;

    public static class ViewHolderHome extends RecyclerView.ViewHolder {
        public TextView textView;
        public ViewHolderHome(View v) {
            super(v);
            textView = v.findViewById(R.id.textViewHomeMeal);
        }
    }

    public RecyclerViewAdapterHome(List<Meal> lMeals) {
        this.lMeals = lMeals;
    }

    @Override
    public RecyclerViewAdapterHome.ViewHolderHome onCreateViewHolder(@NonNull ViewGroup parent,
                                                                     int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_meal_home, parent, false);
        ViewHolderHome vh = new ViewHolderHome(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderHome holder, int position) {
        holder.textView.setText(lMeals.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return lMeals.size();
    }
}

