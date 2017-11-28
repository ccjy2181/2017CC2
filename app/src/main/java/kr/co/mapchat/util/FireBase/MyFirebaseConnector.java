package kr.co.mapchat.util.FireBase;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import kr.co.mapchat.recylcerchat.ChatData;

public class MyFirebaseConnector {
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    private String table;

    public MyFirebaseConnector(String table){
        this.table = table;
    }

    public DatabaseReference insertData(Object obj){
        DatabaseReference resultData = databaseReference.child(table).push();
        resultData.setValue(obj);
        return resultData;
    }

    public void getData(){
        databaseReference.child(table).addChildEventListener(new ChildEventListener() {  // message는 child의 이벤트를 수신합니다.
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //ChatData chatData = dataSnapshot.getValue(ChatData.class);  // chatData를 가져오고
                //adapter.add(chatData.getUserName() + ": " + chatData.getMessage());  // adapter에 추가합니다.
                System.out.println("??@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
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
