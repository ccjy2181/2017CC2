package kr.co.mapchat.Splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.iid.FirebaseInstanceId;

import kr.co.mapchat.DTO.UserDTO;
import kr.co.mapchat.Map.MapsActivity;
import kr.co.mapchat.Util.fcm.FirebaseConnector;

//로딩화면 2초후 메인엑티비티전환

public class SplashActivity extends Activity {
    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);

        try{
            appInit();
            Thread.sleep(2000); //
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        startActivity(new Intent(this, MapsActivity.class));
        finish();
    }

    public void appInit(){
        String token = FirebaseInstanceId.getInstance().getToken();
        UserDTO userDTO = new UserDTO();
        userDTO.setToken(token);
        FirebaseConnector firebaseConnector = new FirebaseConnector("user");
        firebaseConnector.insertData(userDTO);
    }
}
