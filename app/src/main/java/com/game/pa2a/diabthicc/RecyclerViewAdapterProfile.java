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

import com.game.pa2a.diabthicc.models.Person;

import java.util.ArrayList;

public class RecyclerViewAdapterProfile extends RecyclerView.Adapter<RecyclerViewAdapterProfile.ViewHolder>{

    private ArrayList<com.game.pa2a.diabthicc.models.Person> mPersons;
    TextView dialog_name;
    ImageView diag_icon;

    private Context context;
    private Dialog mDialog;


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
                int resId = context.getResources().getIdentifier(
                        item.getIcon(),
                        "drawable",
                        context.getPackageName()
                );
                diag_icon.setImageResource(resId);
                dialog_name.setText(item.getName());
                mDialog.show();
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
