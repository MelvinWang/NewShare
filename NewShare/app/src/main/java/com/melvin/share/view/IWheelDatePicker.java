package com.melvin.share.view;

import android.widget.TextView;

import com.aigestudio.wheelpicker.widgets.WheelDayPicker;
import com.aigestudio.wheelpicker.widgets.WheelMonthPicker;
import com.aigestudio.wheelpicker.widgets.WheelYearPicker;

import java.util.Date;

public interface IWheelDatePicker {
    void setOnDateSelectedListener(MyWheelDatePicker.OnDateSelectedListener listener);

    Date getCurrentDate();

    int getItemAlignYear();

    void setItemAlignYear(int align);

    int getItemAlignMonth();

    void setItemAlignMonth(int align);

    int getItemAlignDay();

    void setItemAlignDay(int align);

    WheelYearPicker getWheelYearPicker();

    WheelMonthPicker getWheelMonthPicker();

    WheelDayPicker getWheelDayPicker();

    TextView getTextViewYear();

    TextView getTextViewMonth();

    TextView getTextViewDay();
}