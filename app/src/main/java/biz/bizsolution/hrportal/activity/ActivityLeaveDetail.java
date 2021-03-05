package biz.bizsolution.hrportal.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import biz.bizsolution.hrportal.R;

public class ActivityLeaveDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_detail);
        initView();
    }

    private void initView(){
        initToolbar();
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
}