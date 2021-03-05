package biz.bizsolution.hrportal.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import biz.bizsolution.hrportal.R;


public class AdapterPagerTimeSheet extends FragmentPagerAdapter {
    private String[] titles;
    private String[] time;
    private final List<Fragment> fragments = new ArrayList<>();
    private Context context;


    public AdapterPagerTimeSheet(final FragmentManager manager, Context context, String[] titles, String[] time) {
        super(manager);
        this.context = context;
        this.titles = titles;
        this.time = time;
    }

    public void addFrag(Fragment fragment) {
        try {
            fragments.add(fragment);
        } catch (Exception e) {
            Log.e("Err", e.getMessage());
        }
    }

    @Override
    public Fragment getItem(int position) {
        try {
            return fragments.get(position);
        } catch (Exception e) {
            Log.e("Err", e.getMessage());
        }
        return null;
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        try {
            return titles[position];
        } catch (Exception e) {
            Log.e("Err", e.getMessage());
        }
        return "";
    }

    public void setTabView(int position, TabLayout.Tab tab) {
        TextView tabTitle = tab.getCustomView().findViewById(R.id.tab_title);
        TextView tabTime = tab.getCustomView().findViewById(R.id.tab_time);
        tabTitle.setText(titles[position]);
        tabTime.setText(time[position]);
    }

    public View getSelectedTabView(int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_tab_time, null);
        TextView tabTextView = view.findViewById(R.id.tab_title);
        TextView tabTime = view.findViewById(R.id.tab_time);
        tabTextView.setText(titles[position]);
        tabTextView.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
        return view;
    }
}