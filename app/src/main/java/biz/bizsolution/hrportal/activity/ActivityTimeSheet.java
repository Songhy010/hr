package biz.bizsolution.hrportal.activity;

import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import biz.bizsolution.hrportal.R;
import biz.bizsolution.hrportal.adapter.AdapterPagerTimeSheetType;
import biz.bizsolution.hrportal.adapter.AdapterProjectTimeSheet;
import biz.bizsolution.hrportal.fragment.FragmentTimeLogMonthly;
import biz.bizsolution.hrportal.fragment.FragmentTimeLogWeekly;
import biz.bizsolution.hrportal.util.MyFunction;
import biz.bizsolution.hrportal.util.MyViewPager;

import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.TextView;

import org.json.JSONObject;

public class ActivityTimeSheet extends ActivityController {
    private MyViewPager view_pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_sheet);
        initView();
    }


    private void initView() {
        try {
            initProjectList(true);
            initTotalTime(true);
            initPager();
            final SwitchCompat switchCompat = findViewById(R.id.switch_type);
            switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    try {
                        if (b)
                            view_pager.setCurrentItem(1);
                        else
                            view_pager.setCurrentItem(0);
                        initProjectList(!b);
                        initTotalTime(!b);
                    } catch (Exception e) {
                        Log.e("Err", e.getMessage() + "");
                    }
                }
            });
        } catch (Exception e) {
            Log.e("Err", e.getMessage() + "");
        }
    }

    private void initTotalTime(boolean isWeekly) {
        try {
            final TextView tvType = findViewById(R.id.tv_type);
            final TextView tvTotalHour = findViewById(R.id.tv_total_hour);
            final TextView tvCreditHour = findViewById(R.id.tv_credit_hour);
            tvType.setText(R.string.total_per_week);
            tvCreditHour.setText(initTimeSheet(isWeekly).getString("credit_hour"));
            tvTotalHour.setText(initTimeSheet(isWeekly).getString("total_hour"));
        } catch (Exception e) {
            Log.e("Err", e.getMessage() + "");
        }
    }

    private void initProjectList(boolean isWeekly) {
        try {
            final RecyclerView recyclerTimeSheetProject = findViewById(R.id.recycler_time_sheet_project);
            final LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
            recyclerTimeSheetProject.setLayoutManager(manager);
            recyclerTimeSheetProject.setAdapter(new AdapterProjectTimeSheet(this, initTimeSheet(isWeekly).getJSONArray("project")));
        } catch (Exception e) {
            Log.e("Err", e.getMessage() + "");
        }
    }


    private void initPager() {
        try {
            view_pager = findViewById(R.id.viewPager);
            view_pager.setOffscreenPageLimit(2);
            view_pager.setPagingEnabled(false);
            AdapterPagerTimeSheetType adapterPagerTimeSheetType = new AdapterPagerTimeSheetType(getSupportFragmentManager(), this);
            adapterPagerTimeSheetType.addFrag(FragmentTimeLogWeekly.newInstance(initTimeSheet(true).getJSONArray("time_log")));
            adapterPagerTimeSheetType.addFrag(FragmentTimeLogMonthly.newInstance(initTimeSheet(false).getJSONArray("time_log")));

            view_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

            view_pager.setAdapter(adapterPagerTimeSheetType);
        } catch (Exception e) {
            Log.e("Err", e.getMessage() + "");
        }
    }

    private JSONObject initTimeSheet(boolean isWeekly) {
        try {
            if (isWeekly) {
                final String timeSheet = MyFunction.getInstance().readFileAsset(this, "timesheet.json");
                return new JSONObject(timeSheet);
            } else {
                final String timeSheet = MyFunction.getInstance().readFileAsset(this, "timesheet_monthly.json");
                return new JSONObject(timeSheet);
            }
        } catch (Exception e) {
            Log.e("Err", e.getMessage() + "");
            return null;
        }
    }
}
