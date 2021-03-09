package biz.bizsolution.hrportal.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import biz.bizsolution.hrportal.R;
import biz.bizsolution.hrportal.util.MyFunction;
import biz.bizsolution.hrportal.util.Tools;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

public class ActivityLeaveDetail extends AppCompatActivity {
    private TextView tvSupervisor, tvSupervisor_, tvRequest, tvRequestFor, tvApprove, tvApproveBy, tvApproveName;
    private Button btnReject, btnApprove;
    private ImageView ivApproveName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_detail);
        Tools.setSystemBarLight(this, R.color.white);
        initView();
    }

    private void initView() {
        initToolbar();
        loadDetail();
    }

    private HashMap<String, String> getDataFromIntent() {
        return MyFunction.getInstance().getIntentHashMap(getIntent());
    }

    private void initToolbar() {
        final ConstraintLayout toolLayout = findViewById(R.id.tool_layout);
        toolLayout.setBackgroundColor(getResources().getColor(R.color.white));
        final ImageView ivBack = findViewById(R.id.iv_back);
        ivBack.setImageDrawable(getResources().getDrawable(R.drawable.ic_back));
        final TextView tvTitle = findViewById(R.id.tv_title);
        tvTitle.setTextColor(getResources().getColor(R.color.black));
        tvTitle.setText(getString(R.string.leave_detail));
        final ImageView ivSearch = findViewById(R.id.iv_search);
        ivSearch.setVisibility(View.GONE);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void loadDetail() {
        tvSupervisor = findViewById(R.id.tv_supervisor);
        tvSupervisor_ = findViewById(R.id.tv_supervisor_);

        tvRequest = findViewById(R.id.tv_request);
        tvRequestFor = findViewById(R.id.tv_request_for);

        btnReject = findViewById(R.id.btn_reject);
        btnApprove = findViewById(R.id.btn_approval);

        tvApprove = findViewById(R.id.tv_approve);
        tvApproveBy = findViewById(R.id.tv_approve_by);

        tvApproveName = findViewById(R.id.tv_approve_name);
        ivApproveName = findViewById(R.id.iv_approve_name);

        String employee = getDataFromIntent().get("employee");

        if (employee.equals("0")) {
            tvSupervisor.setVisibility(View.VISIBLE);
            tvSupervisor_.setVisibility(View.VISIBLE);
            ivApproveName.setVisibility(View.GONE);
            tvApproveName.setVisibility(View.GONE);
            btnApprove.setVisibility(View.GONE);
            btnReject.setVisibility(View.GONE);
        } else {
            tvApproveBy.setVisibility(View.GONE);
            tvApprove.setVisibility(View.GONE);
            tvRequestFor.setVisibility(View.VISIBLE);
            tvRequest.setVisibility(View.VISIBLE);
        }
    }
}
