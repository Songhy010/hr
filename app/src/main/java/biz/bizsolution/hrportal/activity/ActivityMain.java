package biz.bizsolution.hrportal.activity;

import androidx.appcompat.app.AppCompatActivity;
import biz.bizsolution.hrportal.R;
import biz.bizsolution.hrportal.util.Global;
import biz.bizsolution.hrportal.util.MyFunction;
import biz.bizsolution.hrportal.util.Tools;

import android.os.Bundle;
import android.os.Handler;

public class ActivityMain extends ActivityController {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Tools.setSystemBarLight(this,R.color.white);

        MyFunction.getInstance().initLocalize(this,MyFunction.getInstance().getText(this, Global.LANGUAGE));

        new Handler().postDelayed(new Runnable() {
            public void run() {
                MyFunction.getInstance().openActivity(ActivityMain.this,ActivityLogin.class);
                finish();
            }
        }, 1000*3);
    }
}
