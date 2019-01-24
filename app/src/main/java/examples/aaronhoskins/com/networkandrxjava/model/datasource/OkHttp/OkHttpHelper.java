package examples.aaronhoskins.com.networkandrxjava.model.datasource.OkHttp;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import java.io.IOException;

import examples.aaronhoskins.com.networkandrxjava.MainActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpHelper {



    public static void asyncOkHttpApiCall(String url) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            String jsonResponse;
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("TAG", "onFailure: ", e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                jsonResponse = response.body().string();
                Log.d("TAG", "onResponse: " + jsonResponse);
            }
        });
    }

    public static void syncOkHttpApiCall(String url, final Context context) {
        final OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .build();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = okHttpClient.newCall(request).execute();
                    String jsonResponse = response.body().string();
                    IntentFilter intentFilter = new IntentFilter();
                    intentFilter.addAction("pass_info");
                    context.registerReceiver(new MainActivity.Reciever(), intentFilter);
                    Intent intent = new Intent("pass_info");
                    intent.putExtra("json", jsonResponse);
                    context.sendBroadcast(intent);
                    Log.d("TAG", "run: " + jsonResponse);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
}
