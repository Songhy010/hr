package biz.bizsolution.hrportal.listener;

import com.android.volley.VolleyError;

public interface VolleyCallback {
    void onResponse(String response);
    void onErrorResponse(VolleyError e);
}
