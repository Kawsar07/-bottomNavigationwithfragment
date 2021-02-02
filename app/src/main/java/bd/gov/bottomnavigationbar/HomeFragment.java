package bd.gov.bottomnavigationbar;

import android.content.Context;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import bd.gov.bottomnavigationbar.adapter.ApplyAdapter;
import bd.gov.bottomnavigationbar.interfaces.ApiInterface;
import bd.gov.bottomnavigationbar.model.ApplyResponse;
import bd.gov.bottomnavigationbar.model.Datum;
import bd.gov.bottomnavigationbar.webApi.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {

    private FragmentActivity activity;

    private RecyclerView applyRecyclerViewRV;
    private ApplyAdapter applyAdapter;
    private List<Datum> applyList = new ArrayList<>();
    private ApiInterface apiService;
    private Context context;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, null);
        applyRecyclerViewRV = view.findViewById(R.id.applyRV);

        return  view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initSwipeLayout(view);
        initApply();
        getAllApplyData();
    }

    private void initApply() {

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        applyAdapter = new ApplyAdapter(getActivity(), applyList);
        applyRecyclerViewRV.setLayoutManager(layoutManager);
        applyRecyclerViewRV.setAdapter(applyAdapter);
        swipeRefreshLayout.setRefreshing(false);
        applyAdapter.notifyDataSetChanged();

    }


    private void getAllApplyData() {
        apiService = RetrofitClient.getRetrofit().create(ApiInterface.class);
        apiService.getApplyResponse().enqueue(new Callback<ApplyResponse>() {
            @Override
            public void onResponse(Call<ApplyResponse> call, Response<ApplyResponse> response) {
                if (response.code() == 200) {

                    ApplyResponse res = response.body();
                    applyList = res.getData();
                    initApply();
                }

            }

            @Override
            public void onFailure(Call<ApplyResponse> call, Throwable t) {

                Log.d("Response Error",t.getMessage());


            }
        });

    }


    private void initSwipeLayout(View view) {
        //view
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh);
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

}
