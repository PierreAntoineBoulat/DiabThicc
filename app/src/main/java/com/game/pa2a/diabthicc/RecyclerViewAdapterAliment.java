package com.game.pa2a.diabthicc;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.game.pa2a.diabthicc.models.Aliment;

import java.util.List;

public class RecyclerViewAdapterAliment extends RecyclerView.Adapter<RecyclerViewAdapterAliment.ViewHolderAliment>{

    private List<Aliment> mAliment;
    private Context context;

    public RecyclerViewAdapterAliment(Context context, List<Aliment> mAliment) {
        this.mAliment = mAliment;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderAliment onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_card_aliment, viewGroup, false);
        return new ViewHolderAliment(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull ViewHolderAliment viewHolder, int i) {
        final Aliment item = mAliment.get(i);
        viewHolder.name.setText(item.getName());
    }

    @Override
    public int getItemCount() {
        return mAliment.size();
    }

    public class ViewHolderAliment extends RecyclerView.ViewHolder{
        TextView name;

        public ViewHolderAliment (View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textViewAliment);
        }

    }
}