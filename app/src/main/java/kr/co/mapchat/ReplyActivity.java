package kr.co.mapchat;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.util.Date;

import kr.co.mapchat.dto.MessageDTO;
import kr.co.mapchat.util.fireBase.MyFirebaseConnector;
import kr.co.mapchat.util.system.ImageManager;
import kr.co.mapchat.util.system.Property;

import static net.daum.mf.map.api.MapPoint.mapPointWithGeoCoord;

public class ReplyActivity  extends Activity{
    double longitude;
    double latitude;

    String title;

    MapPoint mapPoint;

    MyFirebaseConnector myFirebaseConnector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_write);

        this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        Bundle bundle = intent.getExtras();
        MessageDTO messageDTO = (MessageDTO) bundle.getSerializable("messageDTO");

        longitude = messageDTO.getLocation_longitude();
        latitude = messageDTO.getLocation_latitude();
        title = messageDTO.getTitle();

        super.onCreate(savedInstanceState);
        mapPoint = MapPoint.mapPointWithGeoCoord(latitude, longitude);

        ImageView img = (ImageView)findViewById(R.id.reply_map);

        MapPoint.PlainCoordinate wcongMap = mapPoint.getMapPointWCONGCoord();
        ImageManager imageManager = new ImageManager();

        TextView tv_title = (TextView) findViewById(R.id.reply_title);
        tv_title.setText(title);

        img.setImageBitmap(imageManager.decodingImageData(Property.MAP_IMAGE_URL + "&MX=" + (int)wcongMap.x + "&MY=" + (int)wcongMap.y + "&CX=" + (int)wcongMap.x + "&CY=" + (int)wcongMap.y));

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }


    public String getContentInput(){
        EditText content = (EditText)findViewById(R.id.reply_content);
        String content_str = content.getText().toString();
        return content_str;
    }

    public void ButtonWriteClicked(View view) {

        //DTO 새로 해서 넣어야합니당

//        SharedPreferences prefs = getSharedPreferences("user", MODE_PRIVATE);
//        MessageDTO messageDTO = new MessageDTO();
//        messageDTO.setUser(prefs.getString("token", ""));
//        messageDTO.setTitle(getTitleInput());
//        messageDTO.setContents(getContentInput());
//        messageDTO.setRange(getArea());
//        messageDTO.setLocation_latitude(latitude);
//        messageDTO.setLocation_longitude(longitude);
//        messageDTO.setAnswer_cnt(0);
//        messageDTO.setRegdate(new Date());
//        MapPoint.PlainCoordinate wcongMap = mapPoint.getMapPointWCONGCoord();
//        ImageManager imageManager = new ImageManager();
//        messageDTO.setImage_string(imageManager.encodingImageData(Property.MAP_IMAGE_URL + "&MX=" + (int)wcongMap.x + "&MY=" + (int)wcongMap.y + "&CX=" + (int)wcongMap.x + "&CY=" + (int)wcongMap.y));
//        System.out.println();
//
//        myFirebaseConnector = new MyFirebaseConnector("message");
//        myFirebaseConnector.insertData(messageDTO);

        finish();
    }

    public void ButtonCancelClicked(View view) {
        finish();
    }

    @Override
    public void onBackPressed(){
        finish();
    }
}