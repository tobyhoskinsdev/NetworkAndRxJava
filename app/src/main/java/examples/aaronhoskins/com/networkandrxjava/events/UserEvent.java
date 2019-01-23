package examples.aaronhoskins.com.networkandrxjava.events;

import examples.aaronhoskins.com.networkandrxjava.model.user.UserResponse;

public class UserEvent {
    private UserResponse userResponse;

    public UserEvent(UserResponse userResponse) {
        this.userResponse = userResponse;
    }

    public UserResponse getUserResponse() {
        return userResponse;
    }

    public void setUserResponse(UserResponse userResponse) {
        this.userResponse = userResponse;
    }
}
