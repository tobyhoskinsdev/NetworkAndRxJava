package examples.aaronhoskins.com.networkandrxjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import examples.aaronhoskins.com.networkandrxjava.events.UserEvent;
import examples.aaronhoskins.com.networkandrxjava.model.datasource.httpUrlConnection.HttpUrlConnTask;
import examples.aaronhoskins.com.networkandrxjava.model.user.UserResponse;

public class MainActivity extends AppCompatActivity {
    TextView tvDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvDisplay = findViewById(R.id.tvDisplay);

        HttpUrlConnTask httpUrlConnTask = new HttpUrlConnTask();
        httpUrlConnTask.execute();
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
            Log.d("TAG", "userEvent: "+ userResponse.getResults().get(0).getEmail());
            tvDisplay.setText(userResponse.getResults().get(0).getEmail());
        }
    }
}
