package biz.bizsolution.hrportal.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import biz.bizsolution.hrportal.R;

import android.os.Bundle;

public class ActivityAttendance extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
    }

    private void initView(){

    }

    private void initHistoryRecycler(){
        final RecyclerView recycler = findViewById(R.id.recyclerView);
    }
}
