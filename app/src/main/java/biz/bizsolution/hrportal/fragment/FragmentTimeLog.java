package biz.bizsolution.hrportal.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import biz.bizsolution.hrportal.R;
import biz.bizsolution.hrportal.adapter.AdapterProfile;
import biz.bizsolution.hrportal.adapter.AdapterTimeLogWeekly;


public class FragmentTimeLog extends Fragment {

    private final String TAG = "Weekly";
    private View root_view;
    private JSONArray array;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (root_view == null) {
            root_view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_time_log, container, false);
        }
        return root_view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();

    }

    public static final FragmentTimeLog newInstance(final JSONArray array) {
        FragmentTimeLog fragmentReferral;
        fragmentReferral = new FragmentTimeLog();

        fragmentReferral.array = array;
        return fragmentReferral;
    }

    private void initView(){
        final RecyclerView recyclerTimeLog = root_view.findViewById(R.id.recycler_time_log);
        final LinearLayoutManager manager = new LinearLayoutManager(root_view.getContext(),RecyclerView.VERTICAL,false);
        recyclerTimeLog.setLayoutManager(manager);
        recyclerTimeLog.setAdapter(new AdapterTimeLogWeekly(root_view.getContext(),array));
    }
}
