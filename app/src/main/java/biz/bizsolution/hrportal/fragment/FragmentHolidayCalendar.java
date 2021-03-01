package biz.bizsolution.hrportal.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import org.json.JSONArray;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import biz.bizsolution.hrportal.R;


public class FragmentHolidayCalendar extends Fragment {

    private final String TAG = "Fm NewStore";
    private View root_view;
    private JSONArray arrPromotion;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (root_view == null) {
            root_view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_holiday_calendar, container, false);
        }
        return root_view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    public static final FragmentHolidayCalendar newInstance(final JSONArray arrNewStore) {
        FragmentHolidayCalendar fragmentReferral;
        fragmentReferral = new FragmentHolidayCalendar();

        fragmentReferral.arrPromotion = arrNewStore;
        return fragmentReferral;
    }

}
