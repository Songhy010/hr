package biz.bizsolution.hrportal.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import biz.bizsolution.hrportal.R;
import biz.bizsolution.hrportal.adapter.AdapterNavigation;
import biz.bizsolution.hrportal.util.MyFunction;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONObject;

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
}
