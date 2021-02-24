package biz.bizsolution.hrportal.activity;

import androidx.appcompat.app.AppCompatActivity;
import biz.bizsolution.hrportal.R;
import biz.bizsolution.hrportal.util.MyFunction;
import biz.bizsolution.hrportal.util.Tools;

import android.os.Bundle;
import android.os.Handler;

public class ActivityMain extends ActivityController {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Tools.setSystemBarColor(this,R.color.white);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                MyFunction.getInstance().openActivity(ActivityMain.this,ActivityHome.class);
            }
        }, 1000*3);
    }
}
