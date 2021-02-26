package biz.bizsolution.hrportal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import biz.bizsolution.hrportal.R;
import biz.bizsolution.hrportal.activity.ActivityHome;
import biz.bizsolution.hrportal.activity.ActivityProfile;
import biz.bizsolution.hrportal.util.MyFunction;

public class AdapterNavigation extends RecyclerView.Adapter<AdapterNavigation.ViewHolder>{

    final Context context;
    private static final int HEADER_TYPE = 0;
    private static final int BODY_TYPE = 1;

    public AdapterNavigation(Context context) {
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        if(position  == 0)
            return HEADER_TYPE;
        else
            return BODY_TYPE;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == HEADER_TYPE){
            final View view = LayoutInflater.from(context).inflate(R.layout.item_header_nav,parent,false);
            return new ViewHolder(view);
        }else{
            final View view = LayoutInflater.from(context).inflate(R.layout.item_nav,parent,false);
            return new ViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(holder.getItemViewType() == HEADER_TYPE){
            holder.profileLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((ActivityHome)context).hideNav();
                    MyFunction.getInstance().openActivity(context, ActivityProfile.class);

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ConstraintLayout profileLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profileLayout = itemView.findViewById(R.id.profile_layout);
        }
    }
}
