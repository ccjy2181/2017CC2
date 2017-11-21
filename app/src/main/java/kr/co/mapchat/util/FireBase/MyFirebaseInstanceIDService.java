package kr.co.mapchat.util.FireBase;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.util.Date;

import kr.co.mapchat.DTO.UserDTO;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String refreshedToken) {
        UserDTO userDTO = new UserDTO();
        userDTO.setToken(refreshedToken);
        userDTO.setRegdate(new Date());
        MyFirebaseConnector myFirebaseConnector = new MyFirebaseConnector("user");
        myFirebaseConnector.insertData(userDTO);
    }
}
