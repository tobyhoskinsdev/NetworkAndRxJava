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

import examples.aaronhoskins.com.networkandrxjava.events.UserEvent;
import examples.aaronhoskins.com.networkandrxjava.model.datasource.OkHttp.OkHttpHelper;
import examples.aaronhoskins.com.networkandrxjava.model.datasource.httpUrlConnection.HttpUrlConnTask;
import examples.aaronhoskins.com.networkandrxjava.model.datasource.retrofit.RetrofitHelper;
import examples.aaronhoskins.com.networkandrxjava.model.user.UserResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static examples.aaronhoskins.com.networkandrxjava.model.Constants.BASE_URL;

public class MainActivity extends AppCompatActivity {
    TextView tvDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvDisplay = findViewById(R.id.tvDisplay);

        //HttpUrlConnTask httpUrlConnTask = new HttpUrlConnTask();
        //httpUrlConnTask.execute();

        OkHttpHelper.asyncOkHttpApiCall(BASE_URL);
        OkHttpHelper.syncOkHttpApiCall(BASE_URL, getApplicationContext());
        Call<UserResponse> responseCall = RetrofitHelper.getUsers();

        Response<UserResponse> response = null;

            responseCall.enqueue(new Callback<UserResponse>() {
                @Override
                public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {

                    System.out.println(response.body().getResults().get(0).getEmail());
                }

                @Override
                public void onFailure(Call<UserResponse> call, Throwable t) {

                }
            });


        String email = response.body().getResults().get(0).getEmail();

        Log.d("TAG", "onCreate: " + email);











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
