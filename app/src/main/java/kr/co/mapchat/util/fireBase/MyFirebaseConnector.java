package kr.co.mapchat.util.fireBase;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.util.List;

import kr.co.mapchat.Adapter.MessageADT;
import kr.co.mapchat.DTO.MessageDTO;
import kr.co.mapchat.DTO.UserDTO;

import static android.content.Context.MODE_PRIVATE;

public class MyFirebaseConnector {
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    private String table;
    private String token;

    public MyFirebaseConnector(String table){
        this.table = table;
    }

    public MyFirebaseConnector(String table, Context c){
        this.table = table;
        SharedPreferences sharedPreferences = c.getSharedPreferences("user", MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");
    }

    public DatabaseReference insertData(Object obj){
        DatabaseReference resultData = databaseReference.child(table).push();
        resultData.setValue(obj);
        return resultData;
    }

    public void getMarkerData(MapView mapView, MapPOIItem marker){
        final MapPOIItem item = marker;
        final MapView map = mapView;
        databaseReference.child(table).addChildEventListener(new ChildEventListener() {  // message는 child의 이벤트를 수신합니다.
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                MessageDTO messageDTO = dataSnapshot.getValue(MessageDTO.class);  // chatData를 가져오고
                MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(messageDTO.getLocation_latitude(), messageDTO.getLocation_longitude());

                item.setTag(0);
                // 좌표값 지정
                item.setMapPoint(mapPoint);
                item.setMarkerType(MapPOIItem.MarkerType.BluePin);
                item.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);
                item.setItemName("학관 식당 질문!");

                map.addPOIItem(item);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) { }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) { }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) { }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });
    }

    public void getMyMessage(List<MessageDTO> data, MessageADT adapter){
        final List<MessageDTO> item = data;
        final MessageADT itemAdapter = adapter;

        System.out.println(token);

        databaseReference.child(table).orderByKey().getRef().orderByChild("user").equalTo(token).addChildEventListener(new ChildEventListener() {  // message는 child의 이벤트를 수신합니다.
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                MessageDTO messageDTO = dataSnapshot.getValue(MessageDTO.class);  // chatData를 가져오고
//                MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(messageDTO.getLocation_latitude(), messageDTO.getLocation_longitude());
//
//                item.setTag(0);
//                // 좌표값 지정
//                item.setMapPoint(mapPoint);
//                item.setMarkerType(MapPOIItem.MarkerType.BluePin);
//                item.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);
//                item.setItemName("학관 식당 질문!");
//
//                map.addPOIItem(item);

                item.add(0, messageDTO);
                itemAdapter.notifyDataSetChanged();
                System.out.println(messageDTO);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) { }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) { }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) { }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });
    }
}
