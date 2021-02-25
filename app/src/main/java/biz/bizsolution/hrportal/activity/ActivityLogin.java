package biz.bizsolution.hrportal.activity;

import androidx.appcompat.app.AppCompatActivity;
import biz.bizsolution.hrportal.R;
import biz.bizsolution.hrportal.util.MyFunction;
import biz.bizsolution.hrportal.util.Tools;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActivityLogin extends ActivityController {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Tools.setSystemBarLight(this,R.color.white);
        initView();
    }

    private void initView(){
        initLogin();
    }

    private void initLogin(){
        final Button btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyFunction.getInstance().openActivity(ActivityLogin.this,ActivityHome.class);
                finish();
            }
        });
    }
}
