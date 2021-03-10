package biz.bizsolution.hrportal.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import biz.bizsolution.hrportal.R;
import biz.bizsolution.hrportal.util.MyFunction;
import biz.bizsolution.hrportal.util.Tools;

public class ActivityRequestLeave extends ActivityController implements AdapterView.OnItemSelectedListener {
    private JSONArray array;
    private int hour, minute, day, month, year, mhour;
    private int employeeID, leaveID, durationID;
    private int startHour = 0, endHour = 0, startMinute = 0, endMinute = 0;
    private JSONArray arrayEmployee, arrayDuration;
    private EditText edtStartDate, edtEndDate, edtDate;
    private String dateFormat, durations;
    private TimePickerDialog timeDlg;
    private Spinner spinner, spinnerEmployee, spinnerDuration;
    private TextView durationDays;
    private long startTimeInMillis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_leave);
        Tools.setSystemBarLight(this, R.color.white);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        spinner = findViewById(R.id.spinner_type_of_leave);
        spinner.setOnItemSelectedListener(this);

        //employee spinner
        spinnerEmployee = findViewById(R.id.spinner_server);
        spinnerEmployee.setOnItemSelectedListener(this);

        //duration spinner
        spinnerDuration = findViewById(R.id.spinner_day);
        spinnerDuration.setOnItemSelectedListener(this);

        initView();
    }

    private void initView() {

        initFindView();

        try {
            dateFormat = MyFunction.getInstance().formatDate(MyFunction.getInstance().getCurrentDate(), "EEE, MMM dd, yyyy", "dd-MMM-yyyy");
            Log.e("currentDate", dateFormat + "kk");
            edtStartDate.setText(dateFormat);
            edtEndDate.setText(dateFormat);

            initToolbar();
            initTypeLeave();
            initEmployee();
            initDuration();
            initSelectStartDate();
            initSelectEndDate();
        } catch (Exception e) {
            Log.e("Err", e.getMessage() + "");
        }
    }

    private void initFindView() {
        edtStartDate = findViewById(R.id.edt_start_date);
        edtEndDate = findViewById(R.id.edt_end_date);
        durationDays = findViewById(R.id.tv_duration_days);
    }

    private void initToolbar() {
        final ConstraintLayout toolLayout = findViewById(R.id.tool_layout);
        toolLayout.setBackgroundColor(getResources().getColor(R.color.white));
        final ImageView ivBack = findViewById(R.id.iv_back);
        ivBack.setImageDrawable(getResources().getDrawable(R.drawable.ic_back));
        final TextView tvTitle = findViewById(R.id.tv_title);
        tvTitle.setTextColor(getResources().getColor(R.color.black));
        tvTitle.setText(getString(R.string.request_leave));
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

        try {
            switch (parent.getId()) {
                case R.id.spinner_type_of_leave:
                    JSONObject objLeave = array.getJSONObject(position);
                    leaveID = objLeave.getInt("id");
                    break;
                case R.id.spinner_server:
                    JSONObject objEmployee = arrayEmployee.getJSONObject(position);
                    employeeID = objEmployee.getInt("id");
                    break;
                case R.id.spinner_day:
                    JSONObject objDuration = arrayDuration.getJSONObject(position);
                    durationID = objDuration.getInt("id");
                    durations = objDuration.getString("day");
                    //set value to text
                    durationDays.setText(durations);
                    break;
            }

        } catch (JSONException e) {
            Log.e("Err", e.getMessage() + "");
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void initSpinner(final int pos) {
        try {
            final List<String> categories = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                categories.add(obj.getString("title"));
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

    private void initSpinnerEmployee(final int pos) {
        try {
            final List<String> categories = new ArrayList<>();
            for (int i = 0; i < arrayEmployee.length(); i++) {
                JSONObject obj = arrayEmployee.getJSONObject(i);
                categories.add(obj.getString("title"));
            }
            // Creating adapter for spinner
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);

            // Drop down layout style - list view with radio button
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // attaching data adapter to spinner
            spinnerEmployee.setAdapter(dataAdapter);
            spinnerEmployee.setSelection(pos);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initSpinnerDuration(final int pos) {
        try {
            final List<String> categories = new ArrayList<>();
            for (int i = 0; i < arrayDuration.length(); i++) {
                JSONObject obj = arrayDuration.getJSONObject(i);
                categories.add(obj.getString("day"));
            }
            // Creating adapter for spinner
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);

            // Drop down layout style - list view with radio button
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // attaching data adapter to spinner
            spinnerDuration.setAdapter(dataAdapter);
            spinnerDuration.setSelection(pos);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initDuration() {
        try {
            final String request = MyFunction.getInstance().readFileAsset(this, "duration.json");
            arrayDuration = new JSONArray(request);
            initSpinnerDuration(0);

        } catch (Exception e) {
            Log.e("Err", e.getMessage() + "");
        }
    }


    private void initTypeLeave() {
        try {
            final String request = MyFunction.getInstance().readFileAsset(this, "type_of_leave.json");
            array = new JSONArray(request);
            initSpinner(0);

        } catch (Exception e) {
            Log.e("Err", e.getMessage() + "");
        }
    }

    private void initEmployee() {
        try {
            final String request1 = MyFunction.getInstance().readFileAsset(this, "employee.json");
            arrayEmployee = new JSONArray(request1);
            initSpinnerEmployee(0);

        } catch (Exception e) {
            Log.e("Err", e.getMessage() + "");
        }
    }

    private void initSelectStartDate() {
        edtStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogStartDate();
                hideKeyboard(v);
            }
        });
    }

    private void initSelectEndDate() {
        edtEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogEndDate();
                hideKeyboard(v);
            }
        });
    }

    public void dialogStartDate() {
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
                    edtStartDate.setText(dateFormat);

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

    public void dialogEndDate() {
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
                    edtEndDate.setText(dateFormat);

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

    public void dialogTime(final int timeID) {

        final Calendar c = Calendar.getInstance();

        //check default time
        if (timeID == 0) {
            if (startHour == 0 && startMinute == 0) {
                hour = c.get(Calendar.HOUR_OF_DAY);
                minute = c.get(Calendar.MINUTE);
            } else {
                hour = startHour;
                minute = startMinute;
            }
        } else {
            if (endHour == 0 && endMinute == 0) {
                hour = c.get(Calendar.HOUR_OF_DAY);
                minute = c.get(Calendar.MINUTE);
            } else {
                hour = endHour;
                minute = endMinute;
            }
        }

        TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {

            public void onTimeSet(TimePicker view, int hourOfDay, int minutes) {

                Calendar datetime = Calendar.getInstance();
                Calendar c = Calendar.getInstance();

                hour = hourOfDay;
                minute = minutes;
                mhour = c.get(Calendar.HOUR_OF_DAY);

                datetime.set(Calendar.HOUR_OF_DAY, hour);
                datetime.set(Calendar.MINUTE, minute);

                try {
                    String aTime = new StringBuilder().append(hour).append(':').append(minute).toString();
                    String TimeFormat = MyFunction.getInstance().formatDate(aTime, "HH:mm", "hh:mm a");
                } catch (Exception e) {
                    Log.e("Err", e.getMessage() + "");
                }


                if (timeID == 0) {
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        Date strDate = sdf.parse(MyFunction.getInstance().formatDate(dateFormat, "dd-MMM-yyyy", "dd/MM/yyyy"));
                        if (new Date().after(strDate)) {
                            if (datetime.getTimeInMillis() >= c.getTimeInMillis()) {
                                startHour = hour;
                                startMinute = minute;
                                //edtStartTime.setText(TimeFormat);
                                startTimeInMillis = c.getTimeInMillis();

                            } else {
                                //it's before current'
                                Toast.makeText(ActivityRequestLeave.this, "can't bigger than current time", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            startHour = hour;
                            startMinute = minute;
                            //edtStartTime.setText(TimeFormat);
                            startTimeInMillis = c.getTimeInMillis();
                        }
                    } catch (Exception e) {
                        Log.e("Err", e.getMessage() + "");
                    }

                } else {
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        Date strDate = sdf.parse(MyFunction.getInstance().formatDate(dateFormat, "dd-MMM-yyyy", "dd/MM/yyyy"));
                        if (new Date().after(strDate)) {
                            if (datetime.getTimeInMillis() > startTimeInMillis) {
                                endHour = hour;
                                endMinute = minute;
                                //edtEndTime.setText(TimeFormat);
                            } else {
                                //new DialogAlertCustom(ActivitySetupMeeting.this, "", getString(R.string.start_time)).show();
                            }
                        } else {
                            endHour = hour;
                            endMinute = minute;
                            //edtEndTime.setText(TimeFormat);
                        }
                    } catch (Exception e) {
                        Log.e("Err", e.getMessage() + "");
                    }
                }
            }
        };

        timeDlg = new TimePickerDialog(this, mTimeSetListener, hour, minute, true);

        timeDlg.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == DialogInterface.BUTTON_NEGATIVE) {
                    dialog.dismiss();
                    if (timeID == 0) {

              /*          if (edtStartTime.getText().toString().equals(""))
                            edtStartTime.setText("");

                    } else {

                        if (edtEndTime.getText().toString().equals(""))
                            edtEndTime.setText("");
*/
                    }
                }
            }
        });

        timeDlg.show();
        timeDlg.setCancelable(false);

    }

    private void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }
}