package kr.co.mapchat.Map;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import net.daum.mf.map.api.MapView;

import kr.co.mapchat.R;
import kr.co.mapchat.UserData.AllQuestion;
import kr.co.mapchat.UserData.MyAnswer;
import kr.co.mapchat.UserData.MyBookMark;
import kr.co.mapchat.UserData.MyInfo;
import kr.co.mapchat.UserData.MyQuestion;
import kr.co.mapchat.UserData.UserRank;

public class MapsActivity extends AppCompatActivity
implements NavigationView.OnNavigationItemSelectedListener {

    private final long FINISH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        MapView mapView = new MapView(this);

        RelativeLayout mapViewContainer = (RelativeLayout)findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);
        mapView.setMapTilePersistentCacheEnabled(true);


        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.setDrawerListener(toggle);
        toggle.syncState();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                    drawer.closeDrawer(Gravity.RIGHT);
                } else {
                    drawer.openDrawer(Gravity.RIGHT);
                }
            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.sidebar, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_all_question) {
            Intent all_question = new Intent(this, AllQuestion.class);
            startActivity(all_question);
        } else if (id == R.id.nav_my_question) {
            Intent my_question = new Intent(this, MyQuestion.class);
            startActivity(my_question);
        } else if (id == R.id.nav_my_answer) {
            Intent my_answer = new Intent(this, MyAnswer.class);
            startActivity(my_answer);
        } else if (id == R.id.nav_bookmark) {
            Intent my_bookmark = new Intent(this, MyBookMark.class);
            startActivity(my_bookmark);
        } else if (id == R.id.nav_rank) {
            Intent user_rank = new Intent(this, UserRank.class);
            startActivity(user_rank);
        } else if (id == R.id.nav_manage) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(Gravity.RIGHT);
        return true;
    }

    public boolean onHeaderItemSelected(View view) {
        // Handle navigation view item clicks here.
        Intent my_info = new Intent(this, MyInfo.class);
        startActivity(my_info);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(Gravity.RIGHT);
        return true;
    }


    @Override
    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(Gravity.RIGHT)) {
            drawer.closeDrawer(Gravity.RIGHT);
        } else {
            if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime) {
                super.onBackPressed();
            }
            else{
                backPressedTime = tempTime;
                Toast.makeText(getApplicationContext(), "뒤로가기 버튼을 2번 누르면 종료합니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
