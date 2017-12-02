package kr.co.mapchat.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import kr.co.mapchat.CodeConfig;
import kr.co.mapchat.MainActivity;
import kr.co.mapchat.R;
import kr.co.mapchat.ReplyActivity;
import kr.co.mapchat.WriteActivity;
import kr.co.mapchat.dto.MessageDTO;
import kr.co.mapchat.util.fireBase.MyFirebaseConnector;

import static android.content.Context.MODE_PRIVATE;

public class FragmentMap extends Fragment implements MapView.MapViewEventListener, MapView.POIItemEventListener{
    private MyFirebaseConnector myFirebaseConnector;

    CodeConfig codeConfig = new CodeConfig();
    MapView mapView;
    RelativeLayout mapViewContainer;
    MapPoint mapPoint;

    String token;

    double[] location = {0,0};

    public FragmentMap(){
        setHasOptionsMenu(true);
    }
    public void onCreate(Bundle a){
        super.onCreate(a);

        SharedPreferences sharedPreferences = this.getContext().getSharedPreferences("user", MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");

        setHasOptionsMenu(true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        mapView = new MapView(this.getContext());
        mapView.setPOIItemEventListener(this);

        mapViewContainer = (RelativeLayout)view.findViewById(R.id.map_view);

        getActivity().supportInvalidateOptionsMenu();
        ((MainActivity)getActivity()).changeTitle(R.id.toolbar, "실시간 질문");

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
        }
        return true;
    }

    public void resetFragment(){
        mapViewContainer.removeView(mapView);
        if(mapViewContainer.indexOfChild(mapView) == -1) {
            initMapView();
        }
    }

    public void initMapView(){
        mapView.setPOIItemEventListener(this);

        mapViewContainer.addView(mapView, 0);

        final MapPOIItem marker = new MapPOIItem();

        myFirebaseConnector = new MyFirebaseConnector("message", this.getContext());
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

    @Override
    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {
        MessageDTO messageDTO = (MessageDTO) mapPOIItem.getUserObject();
        if(!messageDTO.getUser().equals(token)) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("messageDTO", messageDTO);

            Intent intent = new Intent(getContext(), ReplyActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {

    }
}
