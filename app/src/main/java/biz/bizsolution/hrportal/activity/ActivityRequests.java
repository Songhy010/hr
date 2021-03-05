package biz.bizsolution.hrportal.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import biz.bizsolution.hrportal.R;
import biz.bizsolution.hrportal.fragment.FragmentApproval;
import biz.bizsolution.hrportal.fragment.FragmentRequest;
import biz.bizsolution.hrportal.util.Tools;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class ActivityRequests extends ActivityController {
    private ViewPagerAdapter adapter;
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
        initPager();
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

    private void initPager() {
        try {
            adapter = new ViewPagerAdapter(getSupportFragmentManager());
            adapter.addFrag(new FragmentRequest());
            adapter.addFrag(new FragmentApproval());
            final ViewPager view_pager = findViewById(R.id.viewPager);
            view_pager.setOffscreenPageLimit(2);
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
            view_pager.setAdapter(adapter);
            tabLayout = findViewById(R.id.tab);
            tabLayout.setupWithViewPager(view_pager);

        } catch (Exception e) {
            Log.e("Err", e.getMessage() + "");
        }
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private String[] titles = {"Request", "Approval"};
        private final List<Fragment> fragments = new ArrayList<>();

        ViewPagerAdapter(final FragmentManager manager) {
            super(manager);
        }

        private void addFrag(Fragment fragment) {
            try {
                fragments.add(fragment);
            } catch (Exception e) {
                Log.e("Err", e.getMessage() + "");
            }
        }

        @Override
        public Fragment getItem(int position) {
            try {
                return fragments.get(position);
            } catch (Exception e) {
                Log.e("Err", e.getMessage() + "");
            }
            return null;
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            try {
                return titles[position];
            } catch (Exception e) {
                Log.e("Err", e.getMessage() + "");
            }
            return "";
        }
    }
}
