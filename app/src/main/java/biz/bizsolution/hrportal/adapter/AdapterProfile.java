package biz.bizsolution.hrportal.adapter;

import android.content.Context;
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

public class AdapterProfile extends RecyclerView.Adapter<AdapterProfile.ViewHolde> {

    private JSONArray array;
    private Context context;
    private static final int HEADER_TYPE = 0;
    private static final int BODY_TYPE = 1;
    private static final String LOGOUT = "LOGOUT";
    private static final String NONE = "NONE";
    private static final String LANGUAGE = "LANGUAGE";
    private static final String CHANGE_PASSWORD = "CHANGE_PASSWORD";

    public AdapterProfile(JSONArray array, Context context) {
        this.array = array;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        try{
            final JSONObject object = array.getJSONObject(position);
            if(object.getInt("type") == 0){
                return HEADER_TYPE;
            }else{
                return BODY_TYPE;
            }
        }catch (Exception e){

        }
        return BODY_TYPE;
    }

    @NonNull
    @Override
    public ViewHolde onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType == HEADER_TYPE){
            final View view = LayoutInflater.from(context).inflate(R.layout.item_profile_header,parent,false);
            return new ViewHolde(view);
        }else {
            final View view = LayoutInflater.from(context).inflate(R.layout.item_profile,parent,false);
            return new ViewHolde(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolde holder, int position) {
        try{

            final JSONObject object = array.getJSONObject(position);
            holder.tvItem.setText(object.getString("title"));
            int id = context.getResources().getIdentifier("biz.bizsolution.hrportal:drawable/"+object.getString("icon"), null, null);
            holder.ivItem.setImageResource(id);
            switch (object.getString("action")){
                case LOGOUT:
                    holder.tvItem.setTextColor(context.getResources().getColor(R.color.red_700));
                    break;
                case LANGUAGE:
                    holder.ivMore.setVisibility(View.VISIBLE);
                    break;
                case CHANGE_PASSWORD:
                    holder.ivMore.setVisibility(View.VISIBLE);
                    break;
                default:
            }

        }catch (Exception e){

        }
    }

    @Override
    public int getItemCount() {
        return array.length();
    }

    public class ViewHolde extends  RecyclerView.ViewHolder{
        private TextView tvItem;
        private ImageView ivItem;
        private ImageView ivMore;
        public ViewHolde(@NonNull View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(R.id.tv_item);
            ivItem = itemView.findViewById(R.id.iv_item);
            ivMore = itemView.findViewById(R.id.iv_more);
        }
    }
}
