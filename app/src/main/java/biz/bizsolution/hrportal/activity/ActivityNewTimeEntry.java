package biz.bizsolution.hrportal.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
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
import java.util.Calendar;
import java.util.List;

import biz.bizsolution.hrportal.R;
import biz.bizsolution.hrportal.listener.VolleyCallback;
import biz.bizsolution.hrportal.util.MyFunction;
import biz.bizsolution.hrportal.util.Tools;

public class ActivityNewTimeEntry extends ActivityController implements AdapterView.OnItemSelectedListener {
    private Spinner spinner;
    private JSONArray arrayProject;
    private int projectID;
    private EditText edtDate;
    private String dateFormat;
    private int day, month, year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_time_entry);
        Tools.setSystemBarLight(this, R.color.white);

        spinner = findViewById(R.id.spinner_project);
        spinner.setOnItemSelectedListener(this);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        initView();
    }

    private void initView() {

        initFindView();

        try {
            dateFormat = MyFunction.getInstance().formatDate(MyFunction.getInstance().getCurrentDate(), "EEE, MMM dd, yyyy", "dd-MMM-yyyy");
            Log.e("currentDate", dateFormat + "kk");
            edtDate.setText(dateFormat);
        } catch (Exception e) {
            Log.e("Err", e.getMessage() + "");
        }

        initToolbar();
        initProject();
        initSelectDate();
    }

    private void initFindView() {
        edtDate = findViewById(R.id.edt_date);
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
            switch (parent.getId()) {
                case R.id.spinner_project:
                    JSONObject objCustomer = arrayProject.getJSONObject(position);
                    projectID = objCustomer.getInt("id");
                    break;
            }

        } catch (JSONException e) {
            Log.e("Err", e.getMessage() + "");
        }
        Log.e("CusName-checkout ", projectID + " -- " + itemSpinner);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void initSpinnerProject(final int pos) {
        try {
            final List<String> categories = new ArrayList<>();
            for (int i = 0; i < arrayProject.length(); i++) {
                JSONObject obj = arrayProject.getJSONObject(i);
                categories.add(obj.getString("name"));
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

    private void initProject() {
        try {
            final String request = MyFunction.getInstance().readFileAsset(this, "project.json");
            arrayProject = new JSONArray(request);
            initSpinnerProject(0);

        } catch (Exception e) {
            Log.e("Err", e.getMessage() + "");
        }
    }

    public void dialogDate() {
        final DatePickerDialog dateDlg;
        final int cyear, cmonth, cday;

        final Calendar c = Calendar.getInstance();
        cyear = c.get(Calendar.YEAR);
        cmonth = c.get(Calendar.MONTH);
        cday = c.get(Calendar.DAY_OF_MONTH);

        dateDlg = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int dlgYear, int monthOfYear, int dayOfMonth) {
                year = dlgYear;
                month = monthOfYear;
                day = dayOfMonth;

                try {
                    String date = String.valueOf(day) + "-" + (month + 1) + "-" + year + " ";
                    dateFormat = MyFunction.getInstance().formatDate(date, "dd-MM-yyyy", "dd-MMM-yyyy");
                    edtDate.setText(dateFormat);

                } catch (Exception e) {
                    Log.e("Err", e.getMessage() + "");
                }
            }

        }, cyear, cmonth, cday);

        dateDlg.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (which == DialogInterface.BUTTON_NEGATIVE) {

                    dialog.dismiss();

                }
            }
        });

        dateDlg.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

        dateDlg.show();
        dateDlg.setCancelable(false);

    }

    private void initSelectDate() {
        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(v);
                dialogDate();
            }
        });
    }

    private void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }
}