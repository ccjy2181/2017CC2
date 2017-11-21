package kr.co.mapchat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.github.clans.fab.FloatingActionButton;

import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import static android.view.KeyEvent.ACTION_UP;


/**
 * Created by Dytstudio.
 */

public class FragmentMap extends Fragment implements MapView.MapViewEventListener{
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

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.write_btn);
        FloatingActionButton.OnClickListener mClickListener = new View.OnClickListener() {
            public void onClick(View v) {
                mapPoint = mapView.getMapCenterPoint();
                location[0] = mapPoint.getMapPointGeoCoord().longitude;
                location[1] = mapPoint.getMapPointGeoCoord().latitude;
                Intent intent = new Intent(v.getContext(), WriteActivity.class);
                intent.putExtra("longitude", location[0]);
                intent.putExtra("latitude", location[1]);
                startActivity(intent);
            }
        };
        fab.setOnClickListener(mClickListener);
        return view;
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_edit, menu);
    }

    public void resetFragment(){
        mapViewContainer.removeView(mapView);
        if(mapViewContainer.indexOfChild(mapView) == -1) {
            initMapView();
        }
    }

    public void initMapView(){
        mapView = new MapView(view.getContext());

        mapViewContainer.addView(mapView, 0);
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
