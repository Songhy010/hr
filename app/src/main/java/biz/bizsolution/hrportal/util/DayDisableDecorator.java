package biz.bizsolution.hrportal.util;


import android.content.Context;
import android.text.style.ForegroundColorSpan;
import android.util.Log;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import biz.bizsolution.hrportal.R;

public class DayDisableDecorator implements DayViewDecorator {
    private HashSet<CalendarDay> dates;
    private Context context;
    private ArrayList<String> status = new ArrayList<>();

    public DayDisableDecorator(Context context, Collection<CalendarDay> dates, final ArrayList<String> status) {
        this.dates = new HashSet<>(dates);
        this.context = context;
        this.status.addAll(status);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setDaysDisabled(true);
        view.addSpan(new CustomTypefaceSpan(MyFont.getInstance().getFont(context,1)));
        for(int i = 0 ;i < status.size();i++){
            switch (status.get(i)) {
                case "1":
                    view.addSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.red_400)));
                    break;
                default:
                    view.addSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.grey)));
                    break;
            }
        }
        Log.e("Decor","1");
    }
}