package examples.aaronhoskins.com.networkandrxjava.model.datasource.retrofit;



import examples.aaronhoskins.com.networkandrxjava.model.datasource.OkHttp.OkHttpHelper;
import examples.aaronhoskins.com.networkandrxjava.model.user.UserResponse;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static examples.aaronhoskins.com.networkandrxjava.model.Constants.ACTUAL_BASE_URL;
import static examples.aaronhoskins.com.networkandrxjava.model.Constants.PATH;
import static examples.aaronhoskins.com.networkandrxjava.model.Constants.QUERY_RESULTS;

public class RetrofitHelper {
    public static Retrofit createRetrofitInstance() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ACTUAL_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(OkHttpHelper.okhttpWithIntercepterClient())
                .build();
        return retrofit;
    }

    public static Retrofit createRetrofitForRx() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ACTUAL_BASE_URL)
                .client(OkHttpHelper.okhttpWithIntercepterClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return retrofit;
    }

    public static Call<UserResponse> getUsers() {
        Retrofit retrofit = createRetrofitInstance();
        RemoteService remoteService = retrofit.create(RemoteService.class);
        return remoteService.getUserList("10");
    }

    public static Observable<UserResponse> getUserOb() {
        Retrofit retrofit = createRetrofitForRx();
        RemoteService remoteService = retrofit.create(RemoteService.class);
        return remoteService.getUserObservable("10");
    }

    public interface RemoteService{
        @GET(PATH)
        Call<UserResponse> getUserList(@Query(QUERY_RESULTS) String resultCount);

        @GET(PATH)
        Observable<UserResponse> getUserObservable(@Query(QUERY_RESULTS) String resultCount);
    }
}
