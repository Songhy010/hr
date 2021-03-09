package biz.bizsolution.hrportal.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

import biz.bizsolution.hrportal.R;
import biz.bizsolution.hrportal.activity.ActivityHome;
import biz.bizsolution.hrportal.activity.ActivityLeaveDetail;
import biz.bizsolution.hrportal.activity.ActivityProfile;
import biz.bizsolution.hrportal.util.MyFunction;

public class AdapterRequest extends RecyclerView.Adapter<AdapterRequest.ItemHolder> {
    private JSONArray array;
    private Context context;
    private static final int HEADER_TYPE = 0;
    private static final int BODY_TYPE = 1;

    public AdapterRequest(JSONArray array, Context context) {
        this.array = array;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        try {
            final JSONObject object = array.getJSONObject(position);
            if (object.getInt("type") == 0) {
                return HEADER_TYPE;
            } else {
                return BODY_TYPE;
            }
        } catch (Exception e) {

        }
        return BODY_TYPE;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == HEADER_TYPE) {
            final View view = LayoutInflater.from(context).inflate(R.layout.item_profile_header, parent, false);
            return new ItemHolder(view);
        } else {
            final View view = LayoutInflater.from(context).inflate(R.layout.item_request, parent, false);
            return new ItemHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        try {
            final JSONObject object = array.getJSONObject(position);

            if (holder.getItemViewType() == HEADER_TYPE) {
                holder.tvItem.setText(object.getString("date"));
            } else {
                String status = object.getString("status");
                holder.tvStatus.setText(status);
                if (status.equals("Approval")) {
                    holder.tvApproval.setVisibility(View.VISIBLE);
                    holder.tvStatus.setVisibility(View.GONE);
                } else {
                    holder.tvApproval.setVisibility(View.GONE);
                    holder.tvStatus.setVisibility(View.VISIBLE);
                }

                holder.tvLeaveType.setText(object.getString("title"));
                holder.tvReason.setText(object.getString("reason"));
                holder.tvDate.setText(object.getString("date_from") + " to " + object.getString("date_to"));

            }

            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final HashMap<String, String> map = new HashMap<>();
                    map.put("employee", "0");
                    MyFunction.getInstance().openActivity(context, ActivityLeaveDetail.class, map);
                }
            });

        } catch (Exception e) {
            Log.e("Err", e.getMessage() + "");
        }
    }

    @Override
    public int getItemCount() {
        return array.length();
    }

    static class ItemHolder extends RecyclerView.ViewHolder {
        private TextView tvDate, tvStatus, tvReason, tvLeaveType, tvItem, tvApproval;
        private CardView cardView;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card);
            tvItem = itemView.findViewById(R.id.tv_item);
            tvDate = itemView.findViewById(R.id.tv_date);
            tvStatus = itemView.findViewById(R.id.tv_status);
            tvReason = itemView.findViewById(R.id.tv_reason);
            tvApproval = itemView.findViewById(R.id.tv_status_approval);
            tvLeaveType = itemView.findViewById(R.id.tv_leave_type);
        }
    }
}
