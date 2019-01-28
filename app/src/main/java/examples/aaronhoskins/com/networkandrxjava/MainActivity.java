package examples.aaronhoskins.com.networkandrxjava;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.List;

import examples.aaronhoskins.com.networkandrxjava.events.UserEvent;
import examples.aaronhoskins.com.networkandrxjava.model.datasource.OkHttp.OkHttpHelper;
import examples.aaronhoskins.com.networkandrxjava.model.datasource.retrofit.RetrofitHelper;
import examples.aaronhoskins.com.networkandrxjava.model.datasource.rxjava.DatasourceRepo;
import examples.aaronhoskins.com.networkandrxjava.model.user.UserResponse;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static examples.aaronhoskins.com.networkandrxjava.model.Constants.FULL_EXAMPLE_URL;

public class MainActivity extends AppCompatActivity {
    TextView tvDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvDisplay = findViewById(R.id.tvDisplay);

        //HTTPURLCONNECTION
        //HttpUrlConnTask httpUrlConnTask = new HttpUrlConnTask();
        //httpUrlConnTask.execute();

        //OKHTTP3
        //OkHttpHelper.asyncOkHttpApiCall(FULL_EXAMPLE_URL);
        //OkHttpHelper.syncOkHttpApiCall(FULL_EXAMPLE_URL, getApplicationContext());

        //OkHttpHelper.asyncWithIntercpt(FULL_EXAMPLE_URL);

        //retrofit async
//        Call<UserResponse> responseCall = RetrofitHelper.getUsers();
//
//            responseCall.enqueue(new Callback<UserResponse>() {
//                @Override
//                public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
//
//
//                    String urlUsed = call.request().url().toString(); //get url of requested call
//                    String email =  response.body().getResults().get(0).getEmail();//results from call
//
//                    Log.d("TAG", "onResponse: URL = " + urlUsed);
//                    Log.d("TAG", "onResponse: EMAIL = " + email);
//                }
//
//                @Override
//                public void onFailure(Call<UserResponse> call, Throwable t) {
//                    Log.d("TAG", "onFailure: REQUEST FAILED " + t.getCause().getMessage());
//                }
//            });
//            //Sync retrofit call
//            Thread thread = new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    Call<UserResponse> responseCall = RetrofitHelper.getUsers();
//                    try {
//                        Response<UserResponse> userResponseResponse = responseCall.execute();
//                        String userResponseEmail = userResponseResponse.body().getResults().get(1).getEmail();
//                        Log.d("TAG", "run: " + userResponseEmail);
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//            thread.start();

        DatasourceRepo datasourceRepo = new DatasourceRepo();
        datasourceRepo.getUserResponse(new examples.aaronhoskins.com.networkandrxjava.model.datasource.rxjava.Callback() {
            @Override
            public void onSuccess(UserResponse userResponse) {
                Log.d("TAG_RX", "onSuccess: " + userResponse.getResults().get(0).getEmail());
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userEvent(UserEvent event){
        if(event != null) {
            UserResponse userResponse = event.getUserResponse();
           // Log.d("TAG", "userEvent: "+ userResponse.getResults().get(0).getEmail());
            tvDisplay.setText(userResponse.getResults().get(0).getEmail());
        }
    }

    public static class Reciever extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            String passedJson = intent.getStringExtra("json");
            Log.d("TAG", "onReceive:  " + passedJson);
        }
    }
}
