package kr.co.mapchat.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.github.clans.fab.FloatingActionButton;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import kr.co.mapchat.CodeConfig;
import kr.co.mapchat.MainActivity;
import kr.co.mapchat.R;
import kr.co.mapchat.WriteActivity;
import kr.co.mapchat.util.FireBase.MyFirebaseConnector;

import static android.view.KeyEvent.ACTION_UP;


/**
 * Created by Dytstudio.
 */

public class FragmentMap extends Fragment implements MapView.MapViewEventListener{
    MyFirebaseConnector myFirebaseConnector;

    CodeConfig codeConfig = new CodeConfig();
    MapView mapView;
    RelativeLayout mapViewContainer;
    View view;
    MapPoint mapPoint;

    double[] location = {0,0};

    public FragmentMap(){
        setHasOptionsMenu(true);
    }
    public void onCreate(Bundle a){
        super.onCreate(a);
        setHasOptionsMenu(true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_map, container, false);

        getActivity().supportInvalidateOptionsMenu();
        ((MainActivity)getActivity()).changeTitle(R.id.toolbar, "Map");

        mapViewContainer = (RelativeLayout)view.findViewById(R.id.map_view);

        return view;
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_edit, menu);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.new_chat){
            mapPoint = mapView.getMapCenterPoint();
            location[0] = mapPoint.getMapPointGeoCoord().longitude;
            location[1] = mapPoint.getMapPointGeoCoord().latitude;
            Intent intent = new Intent(getContext(), WriteActivity.class);
            intent.putExtra("longitude", location[0]);
            intent.putExtra("latitude", location[1]);
            startActivity(intent);
            mapViewContainer.removeView(mapView);
        }
        return true;
    }

//    public void addMarker(MapPoint mapPoint){
//        MapPOIItem marker = new MapPOIItem();
//        marker.setItemName("Default Marker");
//        marker.setTag(0);
//        // 좌표값 지정
//        marker.setMapPoint(mapPoint);
//        // 특정 값 주기 (문제에 대한 int 순서값, 제목 등등 Object 전부 가능)
////        marker.setUserObject(code);
//
//        marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
//        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);
//
//        mapView.addPOIItem(marker);
//    }

    public void resetFragment(){
        mapViewContainer.removeView(mapView);
        if(mapViewContainer.indexOfChild(mapView) == -1) {
            initMapView();
        }
    }

    public void initMapView(){
        mapView = new MapView(view.getContext());

        mapViewContainer.addView(mapView, 0);

        MapPOIItem marker = new MapPOIItem();
        myFirebaseConnector = new MyFirebaseConnector("message");
        myFirebaseConnector.getMarkerData(mapView, marker);
    }

    @Override
    public void onResume() {
        if(mapViewContainer.indexOfChild(mapView) == -1) {
            initMapView();
        }
        super.onResume();
    }

    @Override
    public void onPause() {
        mapViewContainer.removeView(mapView);
        super.onPause();
    }

    // MapView.MapViewEventListener
    @Override
    public void onMapViewInitialized(MapView mapView) {
        location[0] = location[0] == 0 ? this.codeConfig.MJU_LATITUDE : location[0];
        location[1] = location[1] == 0 ? this.codeConfig.MJU_LONGITUDE : location[1];
        mapView.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithGeoCoord(location[0], location[1]), 3, true);
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

    }

    @Override
    public void onMapViewDragEnded(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {

    }
}
