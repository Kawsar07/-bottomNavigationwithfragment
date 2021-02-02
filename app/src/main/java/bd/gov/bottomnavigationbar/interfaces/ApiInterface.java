package bd.gov.bottomnavigationbar.interfaces;

import bd.gov.bottomnavigationbar.jobDetailsModel.JobDetailsResponse;
import bd.gov.bottomnavigationbar.jobModel.JobResponse;
import bd.gov.bottomnavigationbar.model.ApplyResponse;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface ApiInterface {

    @GET("companies/govt")
    Call<ApplyResponse> getApplyResponse();

    @GET("companies/govt/{id}")
    Call<JobResponse> getJobs(@Path("id") int id);
    //position/2
    @GET("position/{id}")
    Call<JobDetailsResponse> getJobDetails(@Path("id") int id);


}
