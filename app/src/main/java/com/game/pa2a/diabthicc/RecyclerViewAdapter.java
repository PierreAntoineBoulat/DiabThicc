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

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private ArrayList<String> mNames;
    TextView dialog_name;
    //private ArrayList<String> mImages = new ArrayList<>();
    private Context context;
    private Dialog mDialog;

//    public RecyclerViewAdapter(Context context, ArrayList<String> mNames, ArrayList<String> mImages) {
//        this.mNames = mNames;
//        this.mImages = mImages;
//        this.context = context;
//    }

    public RecyclerViewAdapter(Context context, ArrayList<String> mNames) {
        this.mNames = mNames;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_card_profile, viewGroup, false);

        mDialog = new Dialog(context);
        mDialog.setContentView(R.layout.fragement_profile);

        dialog_name = mDialog.findViewById(R.id.fragProfileName);
        dialog_name.setText(mNames.get(i));

        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final String item = mNames.get(i);
        //viewHolder.image.setImageAlpha(R.drawable.ic_person_black_24dp);
        viewHolder.name.setText(mNames.get(i));
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
        return mNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView name;

        public ViewHolder (View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageProfile);
            name = itemView.findViewById(R.id.nameProfile);
        }

    }
}
