package kr.co.mapchat;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class WriteActivity extends Activity implements MapView.MapViewEventListener{
    double longitude;
    double latitude;

    MapView mapView;
    RelativeLayout mapViewContainer;
    MapPoint mapPoint;

    MyFirebaseConnector myFirebaseConnector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_write);

        this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        longitude = intent.getDoubleExtra("longitude",0);
        latitude = intent.getDoubleExtra("latitude",0);
        super.onCreate(savedInstanceState);

        ImageView map_img = (ImageView) findViewById(R.id.write_map);

        ImageManager imageManager = new ImageManager();

        mapPoint = mapPointWithGeoCoord(latitude,longitude);
        MapPoint.PlainCoordinate wcongMap = mapPoint.getMapPointWCONGCoord();

        map_img.setImageBitmap(imageManager.decodingImageData(imageManager.encodingImageData(Property.MAP_IMAGE_URL + "&MX=" + (int)wcongMap.x + "&MY=" + (int)wcongMap.y + "&CX=" + (int)wcongMap.x + "&CY=" + (int)wcongMap.y)));

        Spinner area = (Spinner)findViewById(R.id.area);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,R.array.area,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        area.setAdapter(adapter);

        TextView tv_long = (TextView)findViewById(R.id.longitude);
        TextView tv_lati = (TextView)findViewById(R.id.latitude);
        tv_long.setText(Double.toString(longitude));
        tv_lati.setText(Double.toString(latitude));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    public int getArea(){
        int area_int = 0;
        Spinner area = (Spinner)findViewById(R.id.area);
        String area_str = area.getSelectedItem().toString();
        if(area_str.equals("100m")){
            area_int = 100;
        } else if (area_str.equals("300m")){
            area_int = 300;
        } else if (area_str.equals("500m")){
            area_int = 500;
        } else if (area_str.equals("1km")){
            area_int = 1000;
        }

        return area_int;
    }


    public String getTitleInput(){
        EditText title = (EditText)findViewById(R.id.write_title);
        String title_str = title.getText().toString();
        return title_str;
    }

    public String getContentInput(){
        EditText content = (EditText)findViewById(R.id.write_content);
        String content_str = content.getText().toString();
        return content_str;
    }

    public void ButtonWriteClicked(View view) {
        SharedPreferences prefs = getSharedPreferences("user", MODE_PRIVATE);
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setUser(prefs.getString("token", ""));
        messageDTO.setTitle(getTitleInput());
        messageDTO.setContents(getContentInput());
        messageDTO.setRange(getArea());
        messageDTO.setLocation_latitude(latitude);
        messageDTO.setLocation_longitude(longitude);
        messageDTO.setRegdate(new Date());
        MapPoint.PlainCoordinate wcongMap = mapPoint.getMapPointWCONGCoord();
        ImageManager imageManager = new ImageManager();
        messageDTO.setImage_string(imageManager.encodingImageData(Property.MAP_IMAGE_URL + "&MX=" + (int)wcongMap.x + "&MY=" + (int)wcongMap.y + "&CX=" + (int)wcongMap.x + "&CY=" + (int)wcongMap.y));

        myFirebaseConnector = new MyFirebaseConnector("message");
        myFirebaseConnector.insertData(messageDTO);

        finish();
    }

    public void ButtonCancelClicked(View view) {
        finish();
    }

    @Override
    public void onBackPressed(){
        finish();
    }

    @Override
    public void onMapViewInitialized(MapView mapView) {

    }

    @Override
    public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewZoomLevelChanged(MapView mapView, int i) {

    }

    @Override
    public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {
    }

    @Override
    public void onMapViewDoubleTapped(MapView mapView, MapPoint mapPoint) {
    }

    @Override
    public void onMapViewLongPressed(MapView mapView, MapPoint mapPoint) {
    }

    @Override
    public void onMapViewDragStarted(MapView mapView, MapPoint mapPoint) {
        return;
    }

    @Override
    public void onMapViewDragEnded(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {

    }
}