package com.game.pa2a.diabthicc;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.NumberPicker;

import com.game.pa2a.diabthicc.models.Person;
import com.game.pa2a.diabthicc.services.CurrentUserService;

public class WeightDialog extends Dialog implements View.OnClickListener {
    public Activity c;
    public Dialog d;
    public Button yes, no;
    public NumberPicker np;
    Person currentUser;

    public WeightDialog(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fragment_new_weight);
        currentUser = CurrentUserService.currentUser;
        yes = findViewById(R.id.buttonYes);
        no = findViewById(R.id.buttonNo);
        np = findViewById(R.id.numberPicker);
        np.setMinValue(0);
        np.setMaxValue(150);
        np.setWrapSelectorWheel(true);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);
        if(currentUser.getWeight() > 0.0){ // => if not null
            np.setValue((int)currentUser.getWeight());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonYes:
                Person currentUser = CurrentUserService.currentUser;
                currentUser.setWeight(np.getValue());
                break;
            case R.id.buttonNo:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
}
