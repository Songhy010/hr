package biz.bizsolution.hrportal.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import biz.bizsolution.hrportal.R;
import biz.bizsolution.hrportal.adapter.AdapterApproval;


public class FragmentRequest extends Fragment {

    private final String TAG = "Fm NewStore";
    private View root_view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (root_view == null) {
            root_view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_request, container, false);
        }
        return root_view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //initView();

    }

    private void initView() {
        initRecyclerApproval();
    }

    private void initRecyclerApproval() {
        final RecyclerView recyclerApproval = root_view.findViewById(R.id.recycler_approval);
        final LinearLayoutManager manager = new LinearLayoutManager(root_view.getContext(), RecyclerView.VERTICAL, false);
        recyclerApproval.setLayoutManager(manager);
        recyclerApproval.setAdapter(new AdapterApproval(null, root_view.getContext()));
    }
}
