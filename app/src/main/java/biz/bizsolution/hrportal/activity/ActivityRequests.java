package biz.bizsolution.hrportal.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import biz.bizsolution.hrportal.R;
import biz.bizsolution.hrportal.adapter.AdapterPagerHoliday;
import biz.bizsolution.hrportal.adapter.AdapterPagerRequest;
import biz.bizsolution.hrportal.fragment.FragmentApproval;
import biz.bizsolution.hrportal.fragment.FragmentHolidayAll;
import biz.bizsolution.hrportal.fragment.FragmentHolidayCalendar;
import biz.bizsolution.hrportal.fragment.FragmentRequest;
import biz.bizsolution.hrportal.util.Tools;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class ActivityRequests extends ActivityController {
    private AdapterPagerRequest adapterPagerRequest;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests);
        Tools.setSystemBarLight(this, R.color.white);
        initView();
    }

    private void initView() {
        initToolbar();
        initPager(null);
    }

    private void initToolbar() {
        final ConstraintLayout toolLayout = findViewById(R.id.tool_layout);
        toolLayout.setBackgroundColor(getResources().getColor(R.color.white));
        final ImageView ivBack = findViewById(R.id.iv_back);
        ivBack.setImageDrawable(getResources().getDrawable(R.drawable.ic_back));
        final TextView tvTitle = findViewById(R.id.tv_title);
        tvTitle.setTextColor(getResources().getColor(R.color.black));
        tvTitle.setText(getString(R.string.my_request));
        final ImageView ivSearch = findViewById(R.id.iv_search);
        ivSearch.setVisibility(View.GONE);

        ivBack.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                onBackPressed();
            }
        });
    }

    private void initPager(final JSONArray array) {
        try {
            tabLayout = findViewById(R.id.tab);
            final String[] titles = new String[2];
            titles[0] = getString(R.string.request);
            titles[1] = getString(R.string.approval);
            adapterPagerRequest = new AdapterPagerRequest(getSupportFragmentManager(), ActivityRequests.this, titles);
            adapterPagerRequest.addFrag(new FragmentRequest());
            adapterPagerRequest.addFrag(new FragmentApproval());

            final ViewPager view_pager = findViewById(R.id.viewPager);
            view_pager.setOffscreenPageLimit(2);

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
            view_pager.setAdapter(adapterPagerRequest);
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
            tab.setCustomView(adapterPagerRequest.getTabView(i));
        }
    }
}
