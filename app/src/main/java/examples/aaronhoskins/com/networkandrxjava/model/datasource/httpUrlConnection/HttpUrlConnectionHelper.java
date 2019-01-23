package examples.aaronhoskins.com.networkandrxjava.model.datasource.httpUrlConnection;

import android.util.Log;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import examples.aaronhoskins.com.networkandrxjava.events.UserEvent;
import examples.aaronhoskins.com.networkandrxjava.model.user.UserResponse;

import static examples.aaronhoskins.com.networkandrxjava.model.Constants.BASE_URL;

public class HttpUrlConnectionHelper {

    public static void makeAPICallWithHttpConn(){
        HttpURLConnection httpURLConnection = null;
        URL apiURL;
        String jsonResponse = "";

        try {
            apiURL = new URL(BASE_URL);
            httpURLConnection = (HttpURLConnection)apiURL.openConnection();

            InputStream inputStream = httpURLConnection.getInputStream();

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

            int currentReadCharAsciiValue = inputStreamReader.read();
            while(currentReadCharAsciiValue != -1) {
                char currentChar = (char)currentReadCharAsciiValue;
                currentReadCharAsciiValue = inputStreamReader.read();
                jsonResponse = jsonResponse + currentChar;
                }
            System.out.println(jsonResponse);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(httpURLConnection != null) {
                httpURLConnection.disconnect();
                Gson gson = new Gson();
                UserResponse userResponse = gson.fromJson(jsonResponse, UserResponse.class);
                EventBus.getDefault().post(new UserEvent(userResponse));
                //Log.d("TAG", "makeAPICallWithHttpConn: " + userResponse.getResults().get(0).getEmail());
            }
        }

    }
}
