package biz.bizsolution.hrportal.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;

import biz.bizsolution.hrportal.R;
import biz.bizsolution.hrportal.adapter.AdapterRequest;
import biz.bizsolution.hrportal.fragment.FragmentRequest;
import biz.bizsolution.hrportal.util.MyFunction;
import biz.bizsolution.hrportal.util.Tools;

public class ActivityLeave extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave);
        Tools.setSystemBarLight(this, R.color.white);
        initView();
    }

    private void initView(){
        initToolbar();
        initRecyclerLeave();
        initRequestLeave();
        initSeeAll();
    }

    private void initToolbar() {
        final ConstraintLayout toolLayout = findViewById(R.id.tool_layout);
        toolLayout.setBackgroundColor(getResources().getColor(R.color.white));
        final ImageView ivBack = findViewById(R.id.iv_back);
        ivBack.setImageDrawable(getResources().getDrawable(R.drawable.ic_back));
        final TextView tvTitle = findViewById(R.id.tv_title);
        tvTitle.setTextColor(getResources().getColor(R.color.black));
        tvTitle.setText(getString(R.string.my_leave));
        final ImageView ivSearch = findViewById(R.id.iv_search);
        ivSearch.setVisibility(View.GONE);

        ivBack.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                onBackPressed();
            }
        });
    }

    private void initRecyclerLeave(){
        final RecyclerView recyclerRequest = findViewById(R.id.recycler_leave);
        final LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerRequest.setLayoutManager(manager);
        recyclerRequest.setAdapter(new AdapterRequest(initRequest(), ActivityLeave.this));
    }

    private JSONArray initRequest() {
        try {
            final String request = MyFunction.getInstance().readFileAsset(this, "request.json");
            return new JSONArray(request);
        } catch (Exception e) {
            Log.e("Err", e.getMessage() + "");
            return null;
        }
    }

    private void initRequestLeave() {
        final FloatingActionButton floatingActionButton = findViewById(R.id.fab_setup);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyFunction.getInstance().openActivity(ActivityLeave.this, ActivityRequestLeave.class);
            }
        });
    }

    private void initSeeAll(){
        final TextView tvViewAll = findViewById(R.id.tv_view_all);
        tvViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyFunction.getInstance().openActivity(ActivityLeave.this, ActivityRequest.class);
            }
        });
    }
}