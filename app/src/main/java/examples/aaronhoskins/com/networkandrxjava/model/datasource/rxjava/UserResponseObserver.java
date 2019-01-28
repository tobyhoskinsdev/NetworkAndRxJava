package examples.aaronhoskins.com.networkandrxjava.model.datasource.rxjava;

import android.util.Log;

import examples.aaronhoskins.com.networkandrxjava.model.user.UserResponse;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class UserResponseObserver implements Observer<UserResponse> {
    Callback callback;
    UserResponse userResponse = new UserResponse();

    public UserResponseObserver(Callback callback) {
        this.callback = callback;
    }

    @Override
    public void onSubscribe(Disposable d) {
        Log.d("TAG", "onSubscribe: SUBSCRIBING");
    }

    @Override
    public void onNext(UserResponse userResponse) {
        this.userResponse = userResponse;
        Log.d("TAG", "onNext: "
                + userResponse.getResults().get(0).getEmail());
    }

    @Override
    public void onError(Throwable e) {
        Log.e("TAG", "onError: ", e);
    }

    @Override
    public void onComplete() {
        callback.onSuccess(userResponse);
    }
}
