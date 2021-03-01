package biz.bizsolution.hrportal.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import biz.bizsolution.hrportal.R;
import biz.bizsolution.hrportal.adapter.AdapterProfile;
import biz.bizsolution.hrportal.util.MyFunction;
import biz.bizsolution.hrportal.util.Tools;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;

public class ActivityProfile extends ActivityController {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Tools.setSystemBarLight(this,R.color.white);
        initView();
    }

    private void initView(){
        initToolbar();
        initList();
    }

    private void initToolbar(){
        final ConstraintLayout toolLayout = findViewById(R.id.tool_layout);
        toolLayout.setBackgroundColor(getResources().getColor(R.color.white));
        final ImageView ivBack = findViewById(R.id.iv_back);
        ivBack.setImageDrawable(getResources().getDrawable(R.drawable.ic_back));
        final TextView tvTitle = findViewById(R.id.tv_title);
        tvTitle.setTextColor(getResources().getColor(R.color.black));
        tvTitle.setText(getString(R.string.title_pro));
        final ImageView ivSearch = findViewById(R.id.iv_search);
        ivSearch.setVisibility(View.GONE);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void initList(){
        final RecyclerView recyclerView = findViewById(R.id.recycler_profile);
        final LinearLayoutManager manager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(new AdapterProfile(initProfile(),this));
    }

    private JSONArray initProfile() {
        try {
            final String profile = MyFunction.getInstance().readFileAsset(this, "profile.json");
            return new JSONArray(profile);
        } catch (Exception e) {
            Log.e("Err", e.getMessage() + "");
            return null;
        }
    }
}
