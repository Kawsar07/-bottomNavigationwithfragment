package bd.gov.bottomnavigationbar.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import bd.gov.bottomnavigationbar.JobActivity;
import bd.gov.bottomnavigationbar.JobDetailsActivity;
import bd.gov.bottomnavigationbar.R;
import bd.gov.bottomnavigationbar.jobModel.Datum;


public class JobAdapter extends RecyclerView.Adapter<JobAdapter.ViewHolder> {

    private Context context;
    private List<Datum> dataList;

    public JobAdapter(Context context, List<Datum> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_item_row,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Datum data = dataList.get(position);


        holder.dateTV.setText(data.getLastDate());
        holder.jobSubtitleTV.setText(data.getJobSubtitle());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, JobDetailsActivity.class);
                intent.putExtra("id",data.getId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList != null ? dataList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView dateTV,jobSubtitleTV;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dateTV = itemView.findViewById(R.id.dateTV);
            jobSubtitleTV = itemView.findViewById(R.id.jobSubtitleTV);
        }
    }
}
