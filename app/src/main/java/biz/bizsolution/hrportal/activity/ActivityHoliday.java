package biz.bizsolution.hrportal.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import biz.bizsolution.hrportal.R;
import biz.bizsolution.hrportal.adapter.AdapterPagerHoliday;
import biz.bizsolution.hrportal.fragment.FragmentHolidayCalendar;
import biz.bizsolution.hrportal.util.Global;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONObject;

public class ActivityHoliday extends AppCompatActivity {

    private TabLayout tabLayout;
    private AdapterPagerHoliday adapterPagerHoliday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holiday);
        initPager(null);
    }

    private void initPager(final JSONArray array) {
        try {
            tabLayout = findViewById(R.id.tab_layout);
            final String[] titles = new String[2];
            titles[0] = getString(R.string.this_month);
            titles[1] = getString(R.string.all_holiday);
            adapterPagerHoliday = new AdapterPagerHoliday(getSupportFragmentManager(),this,titles);
            adapterPagerHoliday.addFrag(FragmentHolidayCalendar.newInstance(null));

            final ViewPager view_pager = findViewById(R.id.viewPager);
            view_pager.setOffscreenPageLimit(4);

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
            view_pager.setAdapter(adapterPagerHoliday);
            tabLayout.setupWithViewPager(view_pager);
            highLightCurrentTab();
        } catch (Exception e) {
            Log.e("Err", e.getMessage()+"");
        }
    }
    private void highLightCurrentTab() {
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            assert tab != null;
            tab.setCustomView(null);
            tab.setCustomView(adapterPagerHoliday.getTabView(i));
        }
    }
}
