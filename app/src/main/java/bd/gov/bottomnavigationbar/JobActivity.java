package bd.gov.bottomnavigationbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import bd.gov.bottomnavigationbar.adapter.ApplyAdapter;
import bd.gov.bottomnavigationbar.adapter.JobAdapter;
import bd.gov.bottomnavigationbar.interfaces.ApiInterface;
import bd.gov.bottomnavigationbar.jobModel.Datum;
import bd.gov.bottomnavigationbar.jobModel.JobResponse;
import bd.gov.bottomnavigationbar.model.ApplyResponse;

import bd.gov.bottomnavigationbar.webApi.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobActivity extends AppCompatActivity {


    private RecyclerView jobRV;
    private JobAdapter applyAdapter;
    private List<Datum> applyList = new ArrayList<>();
    private ApiInterface apiService;
    private Context context;
    private SwipeRefreshLayout swipeRefreshLayout;

    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        jobRV = findViewById(R.id.jobRV);

        initSwipeLayout();
        initApply();
        getAllApplyData(id);


    }

    private void initApply() {

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        applyAdapter = new JobAdapter(this, applyList);
        jobRV.setLayoutManager(layoutManager);
        jobRV.setAdapter(applyAdapter);
        swipeRefreshLayout.setRefreshing(false);
        applyAdapter.notifyDataSetChanged();

    }


    private void getAllApplyData(int id) {
        apiService = RetrofitClient.getRetrofit().create(ApiInterface.class);
        apiService.getJobs(id).enqueue(new Callback<JobResponse>() {
            @Override
            public void onResponse(Call<JobResponse> call, Response<JobResponse> response) {
                if (response.code() == 200) {

                    JobResponse res = response.body();
                    applyList = res.getData();
                    initApply();
                }

            }

            @Override
            public void onFailure(Call<JobResponse> call, Throwable t) {

                Log.d("Response Error",t.getMessage());


            }
        });

    }


    private void initSwipeLayout() {
        //view
        swipeRefreshLayout = findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initApply();
                swipeRefreshLayout.setRefreshing(false);
            }
        });


        //Default, load for first time
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                initApply();

                swipeRefreshLayout.setRefreshing(true);
            }

        });


    }

    public void backBtn(View view) {
        onBackPressed();
    }
}