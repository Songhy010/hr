package biz.bizsolution.hrportal.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import biz.bizsolution.hrportal.R;

public class AdapterTimeLogWeekly extends RecyclerView.Adapter<AdapterTimeLogWeekly.ViewHolder>{

    private Context context;
    private JSONArray array;

    public AdapterTimeLogWeekly(Context context, JSONArray array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.item_time_log,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            final JSONObject object = array.getJSONObject(position);
            holder.tvTime.setText(object.getString("total_hour"));
            holder.tvProjectName.setText(object.getString("pro_name"));
            holder.tvDescription.setText(object.getString("detail"));
        }catch (Exception e){
            Log.e("Err",e.getMessage()+"");
        }
    }

    @Override
    public int getItemCount() {
        return array.length();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{
        private TextView tvTime;
        private TextView tvProjectName;
        private TextView tvDescription;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvProjectName = itemView.findViewById(R.id.tv_project_name);
            tvDescription = itemView.findViewById(R.id.tv_description);
        }
    }
}