package com.game.pa2a.diabthicc.MPclasses;

import com.game.pa2a.diabthicc.models.CustomDate;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

public class DateXAxisFormat implements IAxisValueFormatter {

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        // value are of type Date.getTime(), and Date is fetched with new CustomDate().getDate().getTime() or new CustomDate().getTime()
        CustomDate cd = CustomDate.build((long) value);
        return cd.dayFormat();
    }
}
