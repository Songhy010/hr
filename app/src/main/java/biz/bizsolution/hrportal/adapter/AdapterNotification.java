package biz.bizsolution.hrportal.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import biz.bizsolution.hrportal.R;

public class AdapterNotification extends RecyclerView.Adapter<AdapterNotification.ViewHolder> {

    private JSONArray array;
    private Context context;
    private static final int PROHIBITION = 0;
    private static final int ANNOUNCEMENT = 1;

    public AdapterNotification(JSONArray array, Context context) {
        this.array = array;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.item_notification,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try{
            final JSONObject object = array.getJSONObject(position);
            holder.tvDate.setText(object.getString("date"));
            holder.tvTitle.setText(object.getString("title"));
            holder.tvDescription.setText(object.getString("description"));
            switch (object.getInt("type")){
                case PROHIBITION:
                    holder.ivNotification.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_prohibition));
                    break;
                case ANNOUNCEMENT:
                    holder.ivNotification.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_announcement));
                    break;
            }
        }catch (Exception e){
            Log.e("Err",e.getMessage()+"");
        }
    }

    @Override
    public int getItemCount() {
        return array.length();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView ivNotification;
        private TextView tvTitle;
        private TextView tvDescription;
        private TextView tvDate;
        private TextView tvDetail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivNotification = itemView.findViewById(R.id.iv_notification);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDescription = itemView.findViewById(R.id.tv_description);
            tvDate = itemView.findViewById(R.id.tv_date);
            tvDetail = itemView.findViewById(R.id.tv_detail);
        }
    }
}
