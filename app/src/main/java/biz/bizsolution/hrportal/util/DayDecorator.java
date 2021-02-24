package biz.bizsolution.hrportal.util;


import android.content.Context;
import android.os.Build;
import android.text.style.ForegroundColorSpan;
import android.util.Log;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.Collection;
import java.util.HashSet;

import androidx.annotation.RequiresApi;
import biz.bizsolution.hrportal.R;

public class DayDecorator implements DayViewDecorator {
    private HashSet<CalendarDay> dates;
    private Context context;

    public DayDecorator(Context context, Collection<CalendarDay> dates) {
        this.dates = new HashSet<>(dates);
        this.context = context;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates.contains(day);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void decorate(DayViewFacade view) {

        view.addSpan(new CustomTypefaceSpan(MyFont.getInstance().getFont(context,1)));
        view.addSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.colorPrimary)));
        Log.e("Decor","1");
    }
}