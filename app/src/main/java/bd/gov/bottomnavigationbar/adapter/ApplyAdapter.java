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
import bd.gov.bottomnavigationbar.R;
import bd.gov.bottomnavigationbar.model.Datum;

public class ApplyAdapter extends RecyclerView.Adapter<ApplyAdapter.ViewHolder> {

    private Context context;
    private List<Datum> dataList;

    public ApplyAdapter(Context context, List<Datum> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Datum data = dataList.get(position);

        Glide.with(context)
                .load("https://ezze.dev/applycafe/media/photos/job_application/"+data.getCompanyLogo())
                .centerCrop()
                .into(holder.imageView);

        holder.textView.setText(data.getCompanyName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, JobActivity.class);
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
        TextView textView;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
