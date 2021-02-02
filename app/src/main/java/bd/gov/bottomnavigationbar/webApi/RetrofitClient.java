package bd.gov.bottomnavigationbar.webApi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit = null;


    private static final String BASE_URL = "https://ezze.dev/applycafe/api/position/14";

    public static Retrofit getRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ezze.dev/applycafe/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;

    }

}






