package biz.bizsolution.hrportal.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import biz.bizsolution.hrportal.R;
import biz.bizsolution.hrportal.adapter.AdapterNavigation;
import biz.bizsolution.hrportal.util.MyFunction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.json.JSONArray;

public class ActivityHome extends AppCompatActivity {

    private static final int MANAGER = 1;
    private static final int EMP = 0;
    private static final int USER_STATUS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
    }

    private void initView() {
        initToolbar();
        initNavMenu();
        initHomeMenu();
    }

    private void initToolbar() {
        final ImageView ivBack = findViewById(R.id.iv_back);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        final ImageView ivSearch = findViewById(R.id.iv_search);
        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyFunction.getInstance().openActivity(ActivityHome.this,ActivityNotification.class);
            }
        });
    }

    private void initNavMenu(){
        final RecyclerView recyclerNav = findViewById(R.id.recycler_nav);
        final LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerNav.setLayoutManager(manager);
        recyclerNav.setAdapter(new AdapterNavigation(this,initMenu()));
    }

    @SuppressLint("NewApi")
    private JSONArray initMenu() {
        try {
            final String menu = MyFunction.getInstance().readFileAsset(this, "hamburger.json");
            final JSONArray array = new JSONArray(menu);
            if(USER_STATUS == EMP){
                array.remove(1);
            }
            return array;
        } catch (Exception e) {
            Log.e("Err", e.getMessage() + "");
            return null;
        }
    }

    public void hideNav(){
        final DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    private void initHomeMenu(){
        final LinearLayout calendarLayout = findViewById(R.id.calendar_layout);
        calendarLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyFunction.getInstance().openActivity(ActivityHome.this,ActivityHoliday.class);
            }
        });

        final LinearLayout timeSheetLayout = findViewById(R.id.time_sheet_layout);
        timeSheetLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyFunction.getInstance().openActivity(ActivityHome.this,ActivityTimeSheet.class);
            }
        });

        final LinearLayout leaveLayout = findViewById(R.id.leave_layout);
        leaveLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyFunction.getInstance().openActivity(ActivityHome.this, ActivityRequests.class);
            }
        });
    }
}
