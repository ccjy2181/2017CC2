package kr.co.mapchat.util.fireBase;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.co.mapchat.MainActivity;
import kr.co.mapchat.adapter.MessageADT;
import kr.co.mapchat.dto.AnswerDTO;
import kr.co.mapchat.dto.MessageDTO;
import kr.co.mapchat.recylcerchat.ConversationRecyclerView;

import static android.content.Context.MODE_PRIVATE;

public class MyFirebaseConnector {
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    private String table;
    private String token;
    private Context c;
    private Boolean toastAction;

    public MyFirebaseConnector(String table){
        this.table = table;
    }

    public MyFirebaseConnector(String table, Context c){
        this.table = table;
        this.c = c;
        this.toastAction = false;
        SharedPreferences sharedPreferences = c.getSharedPreferences("user", MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");
    }

    public DatabaseReference insertData(Object obj){
        DatabaseReference resultData = databaseReference.child(table).push();
        resultData.setValue(obj);
        return resultData;
    }

    public DatabaseReference insertData(Object obj, String target){
        DatabaseReference resultData = databaseReference.child(table).child(target).push();
        resultData.setValue(obj);
        return resultData;
    }

    public DatabaseReference updateData(String target, Map<String, Object> data){
        DatabaseReference resultData = databaseReference.child(table).child(target);
        for (Map.Entry<String, Object> entry : data.entrySet()){
            resultData.child(entry.getKey()).setValue(entry.getValue());
        }
        return resultData;
    }

    public void getMarkerData(MapView mapView){
        final MapView map = mapView;

        databaseReference.child(table).addChildEventListener(new ChildEventListener() {  // message는 child의 이벤트를 수신합니다.
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                MessageDTO messageDTO = dataSnapshot.getValue(MessageDTO.class);  // chatData를 가져오고
                messageDTO.setKey(dataSnapshot.getKey());
                MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(messageDTO.getLocation_latitude(), messageDTO.getLocation_longitude());
                MapPOIItem item = new MapPOIItem();
                item.setItemName(messageDTO.getTitle());
                item.setTag(map.getPOIItems().length);
                // 좌표값 지정
                item.setMapPoint(mapPoint);
                item.setMarkerType(((messageDTO.getUser()).equals(token)) ? MapPOIItem.MarkerType.YellowPin : MapPOIItem.MarkerType.BluePin);
                item.setSelectedMarkerType(((messageDTO.getUser()).equals(token)) ? MapPOIItem.MarkerType.YellowPin : MapPOIItem.MarkerType.RedPin);
                item.setUserObject(messageDTO);

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

    public void getMyMessageList(List<MessageDTO> data, MessageADT adapter){
        final List<MessageDTO> item = data;
        final MessageADT itemAdapter = adapter;

        databaseReference.child(table).orderByKey().getRef().orderByChild("user").equalTo(token).addChildEventListener(new ChildEventListener() {  // message는 child의 이벤트를 수신합니다.
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                MessageDTO messageDTO = dataSnapshot.getValue(MessageDTO.class);  // chatData를 가져오고\
                messageDTO.setKey(dataSnapshot.getKey());

                item.add(item.size(), messageDTO);
                itemAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//                for(MessageDTO m : item){
//                    if(m.getKey().equals(dataSnapshot.getKey())){
//                        int temp = item.indexOf(m);
//                        m = dataSnapshot.getValue(MessageDTO.class);
//                        item.set(temp, m);
//                        itemAdapter.notifyDataSetChanged();
//                    }
//                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) { }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) { }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });
    }

    public void getMyMessage(List<AnswerDTO> data, ConversationRecyclerView adapter, String messageKey){
        final List<AnswerDTO> item = data;
        final ConversationRecyclerView itemAdapter = adapter;

        databaseReference.child(table+"/"+messageKey+"/answer").addChildEventListener(new ChildEventListener() {  // message는 child의 이벤트를 수신합니다.
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                AnswerDTO answerDTO = dataSnapshot.getValue(AnswerDTO.class);
                item.add(0, answerDTO);
                itemAdapter.notifyDataSetChanged();
//                if (toastAction) {
//                    Toast.makeText(c, "답변이 등록되었습니다!", Toast.LENGTH_LONG).show();
//                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) { }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) { }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });
//        toastAction = true;
    }

    public void getMyAnwser(List<MessageDTO> data, MessageADT adapter){
        final List<MessageDTO> item = data;
        final MessageADT itemAdapter = adapter;

        databaseReference.child(table).addChildEventListener(new ChildEventListener() {  // message는 child의 이벤트를 수신합니다.
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                for(DataSnapshot dsp : dataSnapshot.child("answer").getChildren()){
                    AnswerDTO answerDTO = dsp.getValue(AnswerDTO.class);
                    if(answerDTO.getUser().equals(token)) {
                        MessageDTO messageDTO = dataSnapshot.getValue(MessageDTO.class);  // chatData를 가져오고\
                        messageDTO.setKey(dataSnapshot.getKey());
                        item.add(0, messageDTO);
                        itemAdapter.notifyDataSetChanged();
                    }
                }
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
