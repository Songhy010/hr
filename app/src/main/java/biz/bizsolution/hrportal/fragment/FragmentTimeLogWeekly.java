package biz.bizsolution.hrportal.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import biz.bizsolution.hrportal.R;
import biz.bizsolution.hrportal.activity.ActivityNewTimeEntry;
import biz.bizsolution.hrportal.adapter.AdapterPagerTimeSheet;
import biz.bizsolution.hrportal.util.MyFunction;
import biz.bizsolution.hrportal.util.MyViewPager;


public class FragmentTimeLogWeekly extends Fragment {

    private final String TAG = "Weekly";
    private View root_view;
    private JSONArray array;
    private TabLayout tabLayout;
    private AdapterPagerTimeSheet adapterPagerTimeSheet;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (root_view == null) {
            root_view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_time_log_weekly, container, false);
        }
        return root_view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    public static final FragmentTimeLogWeekly newInstance(final JSONArray array) {
        FragmentTimeLogWeekly fragmentReferral;
        fragmentReferral = new FragmentTimeLogWeekly();

        fragmentReferral.array = array;
        return fragmentReferral;
    }

    private void initView() {
        initPager(array);
        initAddRequest();
    }


    private void initPager(final JSONArray array) {
        try {
            final MyViewPager view_pager = root_view.findViewById(R.id.pager_week);
            view_pager.setOffscreenPageLimit(array.length());
            tabLayout = root_view.findViewById(R.id.tab_layout);
            final String[] titles = new String[array.length()];
            final String[] time = new String[array.length()];
            for (int i = 0; i < titles.length; i++) {
                final JSONObject object = array.getJSONObject(i);
                titles[i] = object.getString("title");
                time[i] = object.getString("total_hour");
            }
            adapterPagerTimeSheet = new AdapterPagerTimeSheet(getFragmentManager(), root_view.getContext(), titles, time);

            for (int i = 0; i < titles.length; i++) {
                final JSONObject object = array.getJSONObject(i);
                adapterPagerTimeSheet.addFrag(FragmentTimeLog.newInstance(object.getJSONArray("detail")));
            }

            view_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    highLightCurrentTab();
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

            view_pager.setAdapter(adapterPagerTimeSheet);
            tabLayout.setupWithViewPager(view_pager);
            highLightCurrentTab();
        } catch (Exception e) {
            Log.e("Err", e.getMessage() + "");
        }
    }

    private void highLightCurrentTab() {
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            assert tab != null;
            tab.setCustomView(null);
            tab.setCustomView(R.layout.custom_tap_in_time);
            adapterPagerTimeSheet.setTabView(i, tab);
        }
    }

    private void initAddRequest() {
        final FloatingActionButton floatingActionButton = root_view.findViewById(R.id.fab_setup);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyFunction.getInstance().openActivity(getContext(), ActivityNewTimeEntry.class);
            }
        });
    }
}
