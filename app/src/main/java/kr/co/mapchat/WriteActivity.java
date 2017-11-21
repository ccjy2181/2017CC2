package kr.co.mapchat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import kr.co.mapchat.DTO.MessageDTO;
import kr.co.mapchat.util.FireBase.MyFirebaseConnector;

import static net.daum.mf.map.api.MapPoint.mapPointWithGeoCoord;

public class WriteActivity extends AppCompatActivity {
    double longitude;
    double latitude;

    MapView mapView;
    RelativeLayout mapViewContainer;
    MapPoint mapPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        longitude = intent.getDoubleExtra("longitude",0);
        latitude = intent.getDoubleExtra("latitude",0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        mapView = new MapView(this);
        mapViewContainer = (RelativeLayout)findViewById(R.id.write_map);
        mapViewContainer.addView(mapView);
        mapView.setShowCurrentLocationMarker(true);

        mapPoint = mapPointWithGeoCoord(latitude,longitude);

        mapView.setMapCenterPoint(mapPoint, true);

        Spinner area = (Spinner)findViewById(R.id.area);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,R.array.area,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        area.setAdapter(adapter);

        TextView tv_long = (TextView)findViewById(R.id.longitude);
        TextView tv_lati = (TextView)findViewById(R.id.latitude);
        tv_long.setText(Double.toString(longitude));
        tv_lati.setText(Double.toString(latitude));

        mapView.setOnDragListener(new View.OnDragListener()
        {
            @Override
            public boolean onDrag(View view, DragEvent dragEvent) {
                return false;
            }
        });
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

        MyFirebaseConnector myFirebaseConnector = new MyFirebaseConnector("message");
        myFirebaseConnector.insertData(messageDTO);

        mapViewContainer.removeView(mapView);
        finish();
    }

    public void ButtonCancelClicked(View view) {
        mapViewContainer.removeView(mapView);
        finish();
    }

    @Override
    public void onBackPressed(){
        mapViewContainer.removeView(mapView);
        finish();
    }
}