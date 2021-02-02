package bd.gov.bottomnavigationbar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import bd.gov.bottomnavigationbar.interfaces.ApiInterface;
import bd.gov.bottomnavigationbar.jobDetailsModel.JobDetailsResponse;
import bd.gov.bottomnavigationbar.jobModel.JobResponse;
import bd.gov.bottomnavigationbar.webApi.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobDetailsActivity extends AppCompatActivity {

    private ApiInterface apiService;
    int id;
    private TextView salaryTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);

        Intent intent = getIntent();

        id = intent.getIntExtra("id", 0);
        Toast.makeText(this, ""+id, Toast.LENGTH_SHORT).show();
        getAllDetails(id);
    }

    private void getAllDetails(int id) {
        apiService = RetrofitClient.getRetrofit().create(ApiInterface.class);
        apiService.getJobDetails(id).enqueue(new Callback<JobDetailsResponse>() {
            @Override
            public void onResponse(Call<JobDetailsResponse> call, Response<JobDetailsResponse> response) {
                if (response.code() == 200) {

                    salaryTV = findViewById(R.id.salaryTV);

                    JobDetailsResponse res = response.body();
                    res.getAdditional();
                    Log.d("description",res.getData().getDescription());

                    salaryTV.setText(res.getData().getSalary());

                    Toast.makeText(JobDetailsActivity.this, "" + res.getData().getSalary(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<JobDetailsResponse> call, Throwable t) {

                Log.d("Response Error", t.getMessage());


            }
        });

    }

}