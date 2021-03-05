package biz.bizsolution.hrportal.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import biz.bizsolution.hrportal.R;
import biz.bizsolution.hrportal.adapter.AdapterCalendarHoliday;


public class FragmentHolidayAll extends Fragment {

    private final String TAG = "Fm NewStore";
    private View root_view;
    private JSONArray arrPromotion;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (root_view == null) {
            root_view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_holiday_all, container, false);
        }
        return root_view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();

    }

    public static final FragmentHolidayAll newInstance(final JSONArray arrNewStore) {
        FragmentHolidayAll fragmentReferral;
        fragmentReferral = new FragmentHolidayAll();

        fragmentReferral.arrPromotion = arrNewStore;
        return fragmentReferral;
    }

    private void initView(){
        initRecyclerHoliday();
    }

    private void initRecyclerHoliday(){
        final RecyclerView recyclerHoliday = root_view.findViewById(R.id.recycler_holiday);
        final LinearLayoutManager manager = new LinearLayoutManager(root_view.getContext(),RecyclerView.VERTICAL,false);
        recyclerHoliday.setLayoutManager(manager);
        recyclerHoliday.setAdapter(new AdapterCalendarHoliday(root_view.getContext()));
    }
}
