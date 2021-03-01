package biz.bizsolution.hrportal.util;

import android.app.Application;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import androidx.multidex.MultiDex;

public class AppController extends Application {
    private static AppController mInstance;
    private static RequestQueue queue;

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        final String global = MyFunction.getInstance().getDecrypted(this, MyFunction.getInstance().readFileAsset(this, getFilename()));
        Global.arData = global.split(";");

        Global.arData = new String[146];


       /* String s = "";
        for (int i = 0; i < Global.arData.length; i++) {
            s += Global.arData[i] + ";";
        }
        final String encrypted = MyFunction.getInstance().getEncrypted(this, s);
        Log.e("Encrypted Instruction", encrypted + "");*/

        queue = Volley.newRequestQueue(this.getApplicationContext());

    }

    public <T> void addToRequestQueue(Request<T> req) {
        queue.add(req);
    }


    private String getFilename() {
        StringBuilder result = new StringBuilder();
        final int[] st = {100, 97, 116, 97};
        for (int value : st) {
            result.append((char) value);
        }
        return result.toString();
    }
}