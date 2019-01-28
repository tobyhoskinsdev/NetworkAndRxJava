package examples.aaronhoskins.com.networkandrxjava.model.datasource.rxjava;

import examples.aaronhoskins.com.networkandrxjava.model.user.UserResponse;

public interface Callback {
    void onSuccess(UserResponse userResponse);
}
