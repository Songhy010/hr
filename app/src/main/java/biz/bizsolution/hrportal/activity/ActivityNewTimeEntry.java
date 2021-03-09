package biz.bizsolution.hrportal.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import biz.bizsolution.hrportal.R;
import biz.bizsolution.hrportal.listener.VolleyCallback;
import biz.bizsolution.hrportal.util.MyFunction;
import biz.bizsolution.hrportal.util.Tools;

public class ActivityNewTimeEntry extends ActivityController implements AdapterView.OnItemSelectedListener {
    private Spinner spinner;
    private JSONArray array;
    private int projectID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_time_entry);
        Tools.setSystemBarLight(this, R.color.white);

        // Spinner element
        spinner = findViewById(R.id.spinner);
        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        initView();
    }

    private void initView() {
        initToolbar();
    }

    private void initToolbar() {
        final ConstraintLayout toolLayout = findViewById(R.id.tool_layout);
        toolLayout.setBackgroundColor(getResources().getColor(R.color.white));
        final ImageView ivBack = findViewById(R.id.iv_back);
        ivBack.setImageDrawable(getResources().getDrawable(R.drawable.ic_back));
        final TextView tvTitle = findViewById(R.id.tv_title);
        tvTitle.setTextColor(getResources().getColor(R.color.black));
        tvTitle.setText(getString(R.string.new_time_entry));
        final ImageView ivSearch = findViewById(R.id.iv_search);
        ivSearch.setVisibility(View.GONE);

        ivBack.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String itemSpinner = parent.getSelectedItem() + "";
        try {
            JSONObject objCustomer = array.getJSONObject(position);
            projectID = objCustomer.getInt("id");

        } catch (JSONException e) {
            Log.e("Err", e.getMessage() + "");
        }
        Log.e("CusName-checkout ", projectID + " -- " + itemSpinner);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void initSpinner(final int pos) {
        try {
            final List<String> categories = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                categories.add(obj.getString("cus_name"));
            }
            // Creating adapter for spinner
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);

            // Drop down layout style - list view with radio button
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // attaching data adapter to spinner
            spinner.setAdapter(dataAdapter);
            spinner.setSelection(pos);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}