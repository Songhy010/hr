package biz.bizsolution.hrportal.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import biz.bizsolution.hrportal.R;
import biz.bizsolution.hrportal.activity.ActivityHome;
import biz.bizsolution.hrportal.activity.ActivityProfile;
import biz.bizsolution.hrportal.util.MyFunction;
import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterNavigation extends RecyclerView.Adapter<AdapterNavigation.ViewHolder> {

    private Context context;
    private JSONArray array;
    private static final int HEADER_TYPE = 0;
    private static final int BODY_TYPE = 1;

    public AdapterNavigation(Context context, JSONArray array) {
        this.context = context;
        this.array = array;
    }

    @Override
    public int getItemViewType(int position) {
        try {
            final JSONObject object = array.getJSONObject(position);
            if (object.getInt("type") == 0)
                return HEADER_TYPE;
            else
                return BODY_TYPE;
        } catch (Exception e) {
            Log.e("Err", e.getMessage() + "");
        }
        return BODY_TYPE;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == HEADER_TYPE) {
            final View view = LayoutInflater.from(context).inflate(R.layout.item_header_nav, parent, false);
            return new ViewHolder(view);
        } else {
            final View view = LayoutInflater.from(context).inflate(R.layout.item_nav, parent, false);
            return new ViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            final JSONObject object = array.getJSONObject(position);
            if (holder.getItemViewType() == HEADER_TYPE) {
                holder.tvName.setText(object.getString("name"));
                holder.tvId.setText(object.getString("id"));
                int img = context.getResources().getIdentifier("biz.bizsolution.hrportal:drawable/" + object.getString("icon"), null, null);
                holder.ivProfile.setImageResource(img);
                holder.profileLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ((ActivityHome) context).hideNav();
                        MyFunction.getInstance().openActivity(context, ActivityProfile.class);

                    }
                });
            } else {
                holder.tvTitle.setText(object.getString("title"));
                int img = context.getResources().getIdentifier("biz.bizsolution.hrportal:drawable/" + object.getString("icon"), null, null);
                holder.ivIcon.setImageResource(img);
            }
        } catch (Exception e) {
            Log.e("Err", e.getMessage() + "");
        }
    }

    @Override
    public int getItemCount() {
        return array.length();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ConstraintLayout profileLayout;
        private LinearLayout containerLayout;
        private CircleImageView ivProfile;
        private TextView tvName;
        private TextView tvId;
        private TextView tvTitle;
        private ImageView ivIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profileLayout = itemView.findViewById(R.id.profile_layout);
            containerLayout = itemView.findViewById(R.id.container_layout);
            ivProfile = itemView.findViewById(R.id.iv_profile);
            tvName = itemView.findViewById(R.id.tv_name);
            tvId = itemView.findViewById(R.id.tv_id);
            ivIcon = itemView.findViewById(R.id.iv_icon);
            tvTitle = itemView.findViewById(R.id.tv_title);
        }
    }
}
