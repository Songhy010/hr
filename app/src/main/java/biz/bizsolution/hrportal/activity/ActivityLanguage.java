package biz.bizsolution.hrportal.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import biz.bizsolution.hrportal.R;
import biz.bizsolution.hrportal.listener.AlertCallback;
import biz.bizsolution.hrportal.util.Global;
import biz.bizsolution.hrportal.util.MyFunction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class ActivityLanguage extends ActivityController {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        initView();
    }

    private void initView(){
        initToolbar();
        initRadioOnclick();
        initSwitchChecked();
    }

    private void initToolbar(){
        final ConstraintLayout toolLayout = findViewById(R.id.tool_layout);
        toolLayout.setBackgroundColor(getResources().getColor(R.color.white));
        final ImageView ivBack = findViewById(R.id.iv_back);
        ivBack.setImageDrawable(getResources().getDrawable(R.drawable.ic_back));
        final TextView tvTitle = findViewById(R.id.tv_title);
        tvTitle.setTextColor(getResources().getColor(R.color.black));
        tvTitle.setText(getString(R.string.language));
        final ImageView ivSearch = findViewById(R.id.iv_search);
        ivSearch.setVisibility(View.GONE);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void initSwitchChecked() {
        final RadioButton rdbEnglish, rdbKhmer;

        rdbEnglish = findViewById(R.id.rdb_english);
        rdbKhmer = findViewById(R.id.rdb_khmer);

        final String lang = MyFunction.getInstance().getText(this, Global.LANGUAGE);
        if (lang.equals(Global.KM)|| lang.equals("")) {
            rdbEnglish.setChecked(false);
            rdbKhmer.setChecked(true);
        } else {
            rdbEnglish.setChecked(true);
            rdbKhmer.setChecked(false);
        }
    }

    private void initRadioOnclick() {
        final RadioButton rdbEnglish = findViewById(R.id.rdb_english);
        final RadioButton rdbKhmer = findViewById(R.id.rdb_khmer);
        rdbKhmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String lang = MyFunction.getInstance().getText(ActivityLanguage.this, Global.LANGUAGE);
                if (!lang.equals(Global.KM)) {
                    initChangeLanguage(Global.KM);
                }

            }
        });
        rdbEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String lang = MyFunction.getInstance().getText(ActivityLanguage.this, Global.LANGUAGE);
                if (!lang.equals(Global.EN)) {
                    initChangeLanguage(Global.EN);
                }
            }
        });
    }

    private void initChangeLanguage(final String lang){
        MyFunction.getInstance().alertMessage(ActivityLanguage.this, getString(R.string.change_language), getString(R.string.cancel), new AlertCallback() {
            @Override
            public void onSubmit() {
                MyFunction.getInstance().saveText(ActivityLanguage.this, Global.LANGUAGE, lang);
                initSwitchChecked();
                MyFunction.getInstance().finishActivity(ActivityLanguage.this);
                Intent intent = getBaseContext().getPackageManager()
                        .getLaunchIntentForPackage(getBaseContext().getPackageName());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }

            @Override
            public void onCancel() {
                initSwitchChecked();
            }
        },1);
    }
}
